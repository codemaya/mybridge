grammar Sql;

@header{
package mybridge.sql.parser;

import mybridge.sql.*;
import java.util.Iterator;
}
@lexer::header{
package mybridge.sql.parser;
}
@members {
    public SqlStatement sql;
    public boolean parseOk = false;
}

sql	:
	{sql = new SqlStatement();parseOk = false;}
	statement 
	{parseOk = true;};
	
statement:select | insert | delete | update;

select	: SELECT {sql.type=SqlStatement.SELECT;} 
	  columns
	  FROM 
	  table
	  where?;
insert 	: INSERT 
	  {sql.type=SqlStatement.INSERT;} 
	  INTO 
	  table
	  '('
	  columns {Iterator<String> it = sql.fields.iterator();}
	  ')' 
	  VALUES
	  '('  
	  e=VALUE 
	  {
	  	if (!it.hasNext())
	  	{
	  	    throw new RecognitionException();
	  	}
	  	sql.values.put(it.next(),$e.text);
	  }
	  (','  
	  e=VALUE
	  {
	  	if (!it.hasNext())
	  	{
	  	    throw new RecognitionException();
	  	}
	  	sql.values.put(it.next(),$e.text);
	  }
	  )* 
	  {
	  	if (it.hasNext()) {
	  	    throw new RecognitionException();
	  	}
	  }
	  ')';
delete 	: DELETE {sql.type=SqlStatement.DELETE;} 
	  FROM
	  table
	  where?; 
update 	: UPDATE {sql.type=SqlStatement.UPDATE;} 
	  table
	  SET e=ID '=' f=VALUE {sql.values.put($e.text,$f.text);}
	   (
	   ',' e=ID '=' f=VALUE {sql.values.put($e.text,$f.text);}
	   )* where?; 
columns: (e=ID)
	 {sql.fields.add($e.text);} 
	 (',' e=ID {sql.fields.add($e.text);})*;
where 	: WHERE ( in | eq ) (OP (in | eq ) )*;
table	: ('`')? e=ID ('`')?  {sql.table = $e.text;}  ('.' ('`')? f=ID  {sql.db = $e.text;sql.table = $f.text;} ('`')?)?;
in :	ID 
	{
		if (sql.where.get($ID.text) == null) {
		  sql.where.put($ID.text,new ArrayList<String>());
		}
	}
	IN '(' 
	e=VALUE {sql.where.get($ID.text).add($e.text);} 
	(','e=VALUE {sql.where.get($ID.text).add($e.text);})*')';
eq :    ID 
	{
		if (sql.where.get($ID.text) == null) {
		  sql.where.put($ID.text,new ArrayList<String>());
		}
	}
	 '=' 
	 e=VALUE {sql.where.get($ID.text).add($e.text);} ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

VALUE	: STRING | NUM;
SET	: ('S'|'s') ('E'|'e') ('T'|'t');
VALUES	: ('V'|'v') ('A'|'a') ('L'|'l') ('U'|'u') ('E'|'e') ('S'|'s');
IN 	: ('I'|'i') ('N'|'n');
INTO	: ('I'|'i') ('N'|'n') ('T'|'t') ('O'|'o');
INSERT : ('I'|'i') ('N'|'n') ('S'|'s') ('E'|'e') ('R'|'r') ('T'|'t');
UPDATE : ('U'|'u') ('P'|'p') ('D'|'d') ('A'|'a') ('T'|'t') ('E'|'e');
DELETE : ('D'|'d') ('E'|'e') ('L'|'l') ('E'|'e') ('T'|'t') ('E'|'e');
OP : (('O'|'o') ('R'|'r')) | (('A'|'a') ('N'|'n') ('D'|'d'));
SELECT	: ('S'|'s') ('E'|'e') ('L'|'l') ('E'|'e') ('C'|'c') ('T'|'t');
WHERE 	:  ('W'|'w') ('H'|'h') ('E'|'e') ('R'|'r') ('E'|'e') ;
FROM	: ('F'|'f') ('R'|'r') ('O'|'o') ('M'|'m');
ID : ('a'..'z'|'A'..'Z'|'_') ('0'..'9'|'a'..'z'|'A'..'Z'|'_')* ;

fragment STRING  :  '"' (~('\\'|'"'))* '"' |  '\'' (~('\\'|'"'))* '\''  ;
fragment NUM	  : ('0'..'9')+('.' ('0'..'9')+)?;