package mybridge.handle.mysqlproxy;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import mybridge.core.handle.IHandle;
import mybridge.core.packet.Packet;
import mybridge.core.packet.PacketCommand;
import mybridge.core.packet.PacketEof;
import mybridge.core.packet.PacketError;
import mybridge.core.packet.PacketField;
import mybridge.core.packet.PacketOk;
import mybridge.core.packet.PacketResultSet;
import mybridge.core.packet.PacketRow;
import mybridge.util.MysqlDefs;
import mybridge.util.MysqlServerDef;

public class MysqlProxyHandle implements IHandle {
	static Log logger = LogFactory.getLog(MysqlProxyHandle.class);
	static ConnectionPool pool;
	static Pattern pattern = Pattern.compile("^(INSERT|UPDATE|DELETE|BEGIN|CREATE|ALTER|REPLACE)", Pattern.CASE_INSENSITIVE);
	String charset = "latin1";
	String db = "";
	Connection master;
	Connection slave;

	static {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate("mybridge/pool", ConnectionPool.class.getName());
		digester.addSetProperties("mybridge/pool");
		digester.addObjectCreate("mybridge/pool/datasource", ComboPooledDataSource.class.getName());
		digester.addSetProperties("mybridge/pool/datasource");
		digester.addSetNext("mybridge/pool/datasource", "addDataSource", ComboPooledDataSource.class.getName());
		try {
			pool = (ConnectionPool) digester.parse(new File("./conf/database.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}

	public List<Packet> executeCommand(PacketCommand cmd) throws Exception {
		List<Packet> packetList = new ArrayList<Packet>();
		if (cmd.type == MysqlServerDef.COM_QUERY) {
			String sql = new String(cmd.value, charset);
			return execute(sql);
		}
		if (cmd.type == MysqlServerDef.COM_QUIT) {
			return null;
		}
		if (cmd.type == MysqlServerDef.COM_FIELD_LIST) {
			packetList.add(new PacketEof());
			return packetList;
			//String table = new String(cmd.value, charset);
			//String sql = "SHOW FULL FIELDS FROM " + table;
			//return execute(sql);
		}
		if (cmd.type == MysqlServerDef.COM_INIT_DB) {
			String db = new String(cmd.value, charset);
			String sql = "USE " + db;
			setDb(db);
			return execute(sql);
		}

		// 其余返回OK
		PacketOk ok = new PacketOk();
		ok.affectedRows = 0;
		packetList.add(ok);
		return packetList;
	}

	List<Packet> execute(String sql) {
		logger.info(sql);
		List<Packet> packetList = new ArrayList<Packet>();
		try {
			packetList = executeSql(sql);
		} catch (Exception e) {
			packetList.add(new PacketError());
		}
		return packetList;
	}

	/**
	 * 获取Connection
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	Connection getConnection(String sql) throws SQLException {
		//如果已经走主库,则后续的都走主库
		if (master != null) {
			return master;
		}

		//判断sql类型
		Connection conn = null;
		boolean updateSql = false;
		Matcher m = pattern.matcher(sql);
		if (m.find()) {
			updateSql = true;
		}

		if (updateSql) {
			master = pool.getMaster();
			conn = master;
			initConnection(conn);
		} else {
			if (slave != null) {
				conn = slave;
			} else {
				slave = pool.getSlave();
				conn = slave;
				initConnection(conn);
			}
		}
		return conn;
	}

	void initConnection(Connection conn) throws SQLException {
		Statement statement = conn.createStatement();
		if (db.length() > 0) {
			statement.execute("USE " + db);
		}
		statement.execute("SET NAMES " + charset);
	}

	/**
	 * 执行sql
	 * 
	 * @param conn
	 * @param sql
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	List<Packet> executeSql(String sql) throws Exception {
		List<Packet> packetList = new ArrayList<Packet>();
		// statement用来执行SQL语句
		Connection conn = getConnection(sql);
		Statement statement = conn.createStatement();
		boolean ret = false;
		try {
			ret = statement.execute(sql);
		} catch (SQLException e) {
			PacketError err = new PacketError();
			err.message = e.getMessage();
			err.sqlstate = e.getSQLState();
			err.errno = e.getErrorCode();
			packetList.add(err);
			return packetList;
		}
		if (ret == false) {
			PacketOk ok = new PacketOk();
			ok.affectedRows = statement.getUpdateCount();
			packetList.add(ok);
			return packetList;
		}

		// 返回结果集
		ResultSet rs = statement.getResultSet();
		ResultSetMetaData meta = rs.getMetaData();
		// result set packet
		PacketResultSet setPacket = new PacketResultSet();
		setPacket.fieldCount = meta.getColumnCount();
		packetList.add(setPacket);

		// field packets
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			PacketField fieldPacket = new PacketField();
			fieldPacket.db = meta.getCatalogName(i);
			fieldPacket.table = meta.getTableName(i);
			fieldPacket.orgTable = meta.getTableName(i);
			fieldPacket.name = meta.getColumnName(i);
			fieldPacket.orgName = meta.getColumnName(i);
			fieldPacket.type = (byte) MysqlDefs.javaTypeToMysql(meta.getColumnType(i));
			fieldPacket.length = meta.getColumnDisplaySize(i);
			packetList.add(fieldPacket);
		}
		packetList.add(new PacketEof());

		while (rs.next()) {
			PacketRow rowPacket = new PacketRow("utf8");
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String value = rs.getString(i);
				rowPacket.addValue(value);
			}
			packetList.add(rowPacket);
		}
		packetList.add(new PacketEof());
		return packetList;
	}

	public void close() {
		logger.info("ENTER");
		if (master != null) {
			try {
				master.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (slave != null) {
			try {
				slave.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void open() {
		logger.info("ENTER");
	}

	public void setCharset(String charset) throws Exception {
		String test = "";
		try {
			test.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("invalid charset " + charset);
		}
		this.charset = charset;

	}

	public void setDb(String db) throws Exception {
		this.db = db;
	}

}
