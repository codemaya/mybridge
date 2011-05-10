package mybridge.core.command;

import mybridge.core.table.Field;

public class Condition {
	public static int REL_AND = 1;
	public static int REL_OR = 2;
	
	public Field field;
	public Expresion expr;
	public int rel;
	public Condition next;
}
