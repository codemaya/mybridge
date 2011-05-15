package mybridge.core.server;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xnet.core.util.IOBuffer;
import mybridge.core.config.MainConfig;
import mybridge.core.handle.DefaultHandle;
import mybridge.core.packet.*;

public class MyBridgeProtocal {
	static Log logger = LogFactory.getLog(MyBridgeProtocal.class);

	DefaultHandle handle;// 连接
	MyBridgeSession session;// 链接对应的session
	State state;// 当前协议交互状态
	public byte packetNum = 0;// 下一个packet的序列号
	Iterator<Packet> resultIter = null;// 要写的packet迭代器，只要不为空就会一直写状态直到为空

	public MyBridgeProtocal(MyBridgeSession session)
			throws InstantiationException, IllegalAccessException {
		this.session = session;
		state = State.WRITE_INIT;

		handle = MainConfig.getServerConfig().getHandle();
		handle.open();
	}

	/**
	 * 链接建立事件
	 * 
	 * @param readBuf
	 * @param writeBuf
	 */
	public void onSessionOpen(IOBuffer readBuf, IOBuffer writeBuf) {
		PacketInit init = new PacketInit();
		// init.putBytes(PacketInit.defaultPacket);
		writePacket(writeBuf, init);
	}

	/**
	 * session关闭事件
	 */
	public void onSessionClose() {
		handle.close();
	}

	/**
	 * 读完一个packet事件
	 * 
	 * @param readBuf
	 * @param writeBuf
	 */
	public void onPacketReceived(IOBuffer readBuf, IOBuffer writeBuf) {
		logger.debug("DEBUG ENTER");

		switch (state) {
		case READ_AUTH:
			state = State.WRITE_RESULT;
			PacketAuth auth = new PacketAuth();
			auth.putBytes(readBuf.getBytes(0, readBuf.limit()));
			String user = "";
			if (auth.user.length() > 1) {
				user = auth.user.substring(0, auth.user.length() - 1);
			}
			try {
				if (checkAuth(user, auth.pass)) {
					writePacket(writeBuf, new PacketOk());
					break;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			PacketError err = new PacketError();
			err.errno = 1045;
			err.message = "Access denied for user " + auth.user;
			writePacket(writeBuf, err);
			state = State.CLOSE;
			break;
		case READ_COMMOND:
			state = State.WRITE_RESULT;
			PacketCommand cmd = new PacketCommand();
			cmd.putBytes(readBuf.getBytes(0, readBuf.limit()));
			try {
				List<Packet> resultList = handle.executeCommand(cmd);
				if (resultList == null || resultList.size() == 0) {
					session.setNextState(MyBridgeSession.STATE_CLOSE);
					return;
				}
				resultIter = resultList.iterator();
			} catch (Exception e) {
				e.printStackTrace();
				session.setNextState(MyBridgeSession.STATE_CLOSE);
				return;
			}
			if (resultIter.hasNext()) {
				Packet packet = resultIter.next();
				writePacket(writeBuf, packet);
			}
			break;
		case CLOSE:
		default:
			session.setNextState(MyBridgeSession.STATE_CLOSE);
		}
	}

	/**
	 * 写完一个packet事件
	 * 
	 * @param readBuf
	 * @param writeBuf
	 */
	public void onPacketSended(IOBuffer readBuf, IOBuffer writeBuf) {
		logger.debug("DEBUG ENTER");

		switch (state) {
		case WRITE_INIT:
			state = State.READ_AUTH;
			readPacket(readBuf);
			break;
		case WRITE_RESULT:
			if (resultIter != null && resultIter.hasNext()) {
				Packet packet = resultIter.next();
				writePacket(writeBuf, packet);
			} else {
				state = State.READ_COMMOND;
				readPacket(readBuf);
			}
			break;
		case CLOSE:
		default:
			session.setNextState(MyBridgeSession.STATE_CLOSE);
		}
	}

	/**
	 * 写一个packet
	 * 
	 * @param writeBuf
	 * @param packet
	 */
	public void writePacket(IOBuffer writeBuf, Packet packet) {
		logger.debug("DEBUG ENTER");

		byte[] bodyBytes = packet.getBytes();
		PacketHeader header = new PacketHeader();
		header.packetLen = bodyBytes.length;
		header.packetNum = packetNum;
		packetNum++;

		writeBuf.position(0);
		writeBuf.writeBytes(header.getBytes());
		writeBuf.writeBytes(bodyBytes);
		writeBuf.limit(writeBuf.position());
		writeBuf.position(0);
		session.setNextState(MyBridgeSession.STATE_WRITE);
	}

	/**
	 * 读一个packet
	 * 
	 * @param readBuf
	 */
	public void readPacket(IOBuffer readBuf) {
		logger.debug("DEBUG ENTER");

		readBuf.position(0);
		readBuf.limit(4);
		session.setNextState(MyBridgeSession.STATE_READ);
	}

	boolean checkAuth(String clientUser, byte[] cPass) throws Exception {
		if (!MainConfig.getServerConfig().getUserName().equals(clientUser)) {
			return false;
		}
		if (cPass.length ==0 && MainConfig.getServerConfig().getPassword().length() == 0) {
			return true;
		}
		byte[] sPass = encodePassword(MainConfig.getServerConfig().getPassword());
		if (cPass.length != sPass.length) {
			return false;
		}
		for (int i = 0; i < sPass.length; i++) {
			if (cPass[i] != sPass[i]) {
				return false;
			}
		}
		return true;
	}

	byte[] encodePassword(String password) throws Exception {
		MessageDigest md;
		byte[] seed = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1 };
		md = MessageDigest.getInstance("SHA-1");

		byte[] passwordHashStage1 = md.digest(password.getBytes("ASCII"));
		md.reset();

		byte[] passwordHashStage2 = md.digest(passwordHashStage1);
		md.reset();
		md.update(seed);
		md.update(passwordHashStage2);

		byte[] toBeXord = md.digest();

		int numToXor = toBeXord.length;

		for (int i = 0; i < numToXor; i++) {
			toBeXord[i] = (byte) (toBeXord[i] ^ passwordHashStage1[i]);
		}

		return toBeXord;
	}

	/**
	 * 状态
	 * 
	 * @author quanwei
	 * 
	 */
	static enum State {
		WRITE_INIT, READ_AUTH, WRITE_RESULT, READ_COMMOND, CLOSE
	}
}
