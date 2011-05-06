package mybridge.sql.statement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlStatement {
	public static int INVALID = 0;
	public static int SELECT = 1;
	public static int INSERT = 2;
	public static int UPDATE = 3;
	public static int DELETE = 4;

	public int type = INVALID;
	public String table = "";
	public Map<String, String> values = new HashMap<String, String>();
	public Map<String, List<String>> where = new HashMap<String, List<String>>();
	public Set<String> fields = new HashSet<String>();
	
	public String toString() {
		String out = "";
		out += "type:" + type + "\n";
		out += "table:" + table + "\n";
		out += "fields:" + fields.toString() + "\n";
		out += "where:" + where.toString() + "\n";
		out += "values:" + values.toString() + "\n";
		Iterator<String> it = fields.iterator();
		return out;
	}
	
	
}
