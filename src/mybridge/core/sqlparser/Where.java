package mybridge.core.sqlparser;

import java.util.ArrayList;
import java.util.List;

public class Where {
	List<Cond> condList = new ArrayList<Cond>();

	public void add(String col, String op, String value) {
		Cond cond = new Cond(col, op, value);
		condList.add(cond);
	}
}