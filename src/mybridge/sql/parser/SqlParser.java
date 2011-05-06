// $ANTLR 3.3 Nov 30, 2010 12:45:30 G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g 2011-05-06 20:56:48

package mybridge.sql.parser;

import mybridge.sql.statement.*;
import java.util.Iterator;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SqlParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SELECT", "FROM", "ID", "INSERT", "INTO", "VALUES", "VALUE", "DELETE", "UPDATE", "SET", "WHERE", "OP", "IN", "WS", "STRING", "NUM", "'('", "')'", "','", "'='"
    };
    public static final int EOF=-1;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int SELECT=4;
    public static final int FROM=5;
    public static final int ID=6;
    public static final int INSERT=7;
    public static final int INTO=8;
    public static final int VALUES=9;
    public static final int VALUE=10;
    public static final int DELETE=11;
    public static final int UPDATE=12;
    public static final int SET=13;
    public static final int WHERE=14;
    public static final int OP=15;
    public static final int IN=16;
    public static final int WS=17;
    public static final int STRING=18;
    public static final int NUM=19;

    // delegates
    // delegators


        public SqlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SqlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return SqlParser.tokenNames; }
    public String getGrammarFileName() { return "G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g"; }


        public SqlStatement sql;
        public boolean parseOk = false;



    // $ANTLR start "sql"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:17:1: sql : statement ;
    public final void sql() throws RecognitionException {
        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:17:5: ( statement )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:18:2: statement
            {
            sql = new SqlStatement();parseOk = false;
            pushFollow(FOLLOW_statement_in_sql31);
            statement();

            state._fsp--;

            parseOk = true;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "sql"


    // $ANTLR start "statement"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:22:1: statement : ( select | insert | delete | update );
    public final void statement() throws RecognitionException {
        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:22:10: ( select | insert | delete | update )
            int alt1=4;
            switch ( input.LA(1) ) {
            case SELECT:
                {
                alt1=1;
                }
                break;
            case INSERT:
                {
                alt1=2;
                }
                break;
            case DELETE:
                {
                alt1=3;
                }
                break;
            case UPDATE:
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:22:11: select
                    {
                    pushFollow(FOLLOW_select_in_statement42);
                    select();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:22:20: insert
                    {
                    pushFollow(FOLLOW_insert_in_statement46);
                    insert();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:22:29: delete
                    {
                    pushFollow(FOLLOW_delete_in_statement50);
                    delete();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:22:38: update
                    {
                    pushFollow(FOLLOW_update_in_statement54);
                    update();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "statement"


    // $ANTLR start "select"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:24:1: select : SELECT columns FROM e= ID where ;
    public final void select() throws RecognitionException {
        Token e=null;

        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:24:8: ( SELECT columns FROM e= ID where )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:24:10: SELECT columns FROM e= ID where
            {
            match(input,SELECT,FOLLOW_SELECT_in_select62); 
            sql.type=SqlStatement.SELECT;
            pushFollow(FOLLOW_columns_in_select70);
            columns();

            state._fsp--;

            match(input,FROM,FOLLOW_FROM_in_select75); 
            e=(Token)match(input,ID,FOLLOW_ID_in_select83); 
            sql.table = (e!=null?e.getText():null);
            pushFollow(FOLLOW_where_in_select91);
            where();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "select"


    // $ANTLR start "insert"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:29:1: insert : INSERT INTO e= ID '(' columns ')' VALUES '(' e= VALUE ( ',' e= VALUE )* ')' ;
    public final void insert() throws RecognitionException {
        Token e=null;

        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:29:9: ( INSERT INTO e= ID '(' columns ')' VALUES '(' e= VALUE ( ',' e= VALUE )* ')' )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:29:11: INSERT INTO e= ID '(' columns ')' VALUES '(' e= VALUE ( ',' e= VALUE )* ')'
            {
            match(input,INSERT,FOLLOW_INSERT_in_insert99); 
            sql.type=SqlStatement.INSERT;
            match(input,INTO,FOLLOW_INTO_in_insert111); 
            e=(Token)match(input,ID,FOLLOW_ID_in_insert119); 
            sql.table = (e!=null?e.getText():null);
            match(input,20,FOLLOW_20_in_insert127); 
            pushFollow(FOLLOW_columns_in_insert132);
            columns();

            state._fsp--;

            Iterator<String> it = sql.fields.iterator();
            match(input,21,FOLLOW_21_in_insert139); 
            match(input,VALUES,FOLLOW_VALUES_in_insert145); 
            match(input,20,FOLLOW_20_in_insert150); 
            e=(Token)match(input,VALUE,FOLLOW_VALUE_in_insert159); 

            	  	if (!it.hasNext())
            	  	{
            	  	    throw new RecognitionException();
            	  	}
            	  	sql.values.put(it.next(),(e!=null?e.getText():null));
            	  
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:46:4: ( ',' e= VALUE )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==22) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:46:5: ',' e= VALUE
            	    {
            	    match(input,22,FOLLOW_22_in_insert171); 
            	    e=(Token)match(input,VALUE,FOLLOW_VALUE_in_insert180); 

            	    	  	if (!it.hasNext())
            	    	  	{
            	    	  	    throw new RecognitionException();
            	    	  	}
            	    	  	sql.values.put(it.next(),(e!=null?e.getText():null));
            	    	  

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            	  	if (it.hasNext()) {
            	  	    throw new RecognitionException();
            	  	}
            	  
            match(input,21,FOLLOW_21_in_insert202); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "insert"


    // $ANTLR start "delete"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:62:1: delete : DELETE FROM e= ID where ;
    public final void delete() throws RecognitionException {
        Token e=null;

        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:62:9: ( DELETE FROM e= ID where )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:62:11: DELETE FROM e= ID where
            {
            match(input,DELETE,FOLLOW_DELETE_in_delete210); 
            sql.type=SqlStatement.DELETE;
            match(input,FROM,FOLLOW_FROM_in_delete218); 
            e=(Token)match(input,ID,FOLLOW_ID_in_delete225); 
            sql.table = (e!=null?e.getText():null);
            pushFollow(FOLLOW_where_in_delete233);
            where();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "delete"


    // $ANTLR start "update"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:66:1: update : UPDATE e= ID SET e= ID '=' f= VALUE ( ',' e= ID '=' f= VALUE )* where ;
    public final void update() throws RecognitionException {
        Token e=null;
        Token f=null;

        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:66:9: ( UPDATE e= ID SET e= ID '=' f= VALUE ( ',' e= ID '=' f= VALUE )* where )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:66:11: UPDATE e= ID SET e= ID '=' f= VALUE ( ',' e= ID '=' f= VALUE )* where
            {
            match(input,UPDATE,FOLLOW_UPDATE_in_update242); 
            sql.type=SqlStatement.UPDATE;
            e=(Token)match(input,ID,FOLLOW_ID_in_update252); 
            sql.table = (e!=null?e.getText():null);
            match(input,SET,FOLLOW_SET_in_update260); 
            e=(Token)match(input,ID,FOLLOW_ID_in_update264); 
            match(input,23,FOLLOW_23_in_update266); 
            f=(Token)match(input,VALUE,FOLLOW_VALUE_in_update270); 
            sql.values.put((e!=null?e.getText():null),(f!=null?f.getText():null));
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:69:5: ( ',' e= ID '=' f= VALUE )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==22) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:70:5: ',' e= ID '=' f= VALUE
            	    {
            	    match(input,22,FOLLOW_22_in_update284); 
            	    e=(Token)match(input,ID,FOLLOW_ID_in_update288); 
            	    match(input,23,FOLLOW_23_in_update290); 
            	    f=(Token)match(input,VALUE,FOLLOW_VALUE_in_update294); 
            	    sql.values.put((e!=null?e.getText():null),(f!=null?f.getText():null));

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            pushFollow(FOLLOW_where_in_update305);
            where();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "update"


    // $ANTLR start "columns"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:72:1: columns : (e= ID ) ( ',' e= ID )* ;
    public final void columns() throws RecognitionException {
        Token e=null;

        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:72:8: ( (e= ID ) ( ',' e= ID )* )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:72:10: (e= ID ) ( ',' e= ID )*
            {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:72:10: (e= ID )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:72:11: e= ID
            {
            e=(Token)match(input,ID,FOLLOW_ID_in_columns315); 

            }

            sql.fields.add((e!=null?e.getText():null));
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:74:3: ( ',' e= ID )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==22) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:74:4: ',' e= ID
            	    {
            	    match(input,22,FOLLOW_22_in_columns326); 
            	    e=(Token)match(input,ID,FOLLOW_ID_in_columns330); 
            	    sql.fields.add((e!=null?e.getText():null));

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "columns"


    // $ANTLR start "where"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:1: where : WHERE ( in | eq ) ( OP ( in | eq ) )* ;
    public final void where() throws RecognitionException {
        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:8: ( WHERE ( in | eq ) ( OP ( in | eq ) )* )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:10: WHERE ( in | eq ) ( OP ( in | eq ) )*
            {
            match(input,WHERE,FOLLOW_WHERE_in_where342); 
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:16: ( in | eq )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ID) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==IN) ) {
                    alt5=1;
                }
                else if ( (LA5_1==23) ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:18: in
                    {
                    pushFollow(FOLLOW_in_in_where346);
                    in();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:23: eq
                    {
                    pushFollow(FOLLOW_eq_in_where350);
                    eq();

                    state._fsp--;


                    }
                    break;

            }

            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:28: ( OP ( in | eq ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==OP) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:29: OP ( in | eq )
            	    {
            	    match(input,OP,FOLLOW_OP_in_where355); 
            	    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:32: ( in | eq )
            	    int alt6=2;
            	    int LA6_0 = input.LA(1);

            	    if ( (LA6_0==ID) ) {
            	        int LA6_1 = input.LA(2);

            	        if ( (LA6_1==IN) ) {
            	            alt6=1;
            	        }
            	        else if ( (LA6_1==23) ) {
            	            alt6=2;
            	        }
            	        else {
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 6, 1, input);

            	            throw nvae;
            	        }
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 6, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt6) {
            	        case 1 :
            	            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:33: in
            	            {
            	            pushFollow(FOLLOW_in_in_where358);
            	            in();

            	            state._fsp--;


            	            }
            	            break;
            	        case 2 :
            	            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:75:38: eq
            	            {
            	            pushFollow(FOLLOW_eq_in_where362);
            	            eq();

            	            state._fsp--;


            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "where"


    // $ANTLR start "in"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:76:1: in : ID IN '(' e= VALUE ( ',' e= VALUE )* ')' ;
    public final void in() throws RecognitionException {
        Token e=null;
        Token ID1=null;

        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:76:4: ( ID IN '(' e= VALUE ( ',' e= VALUE )* ')' )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:76:6: ID IN '(' e= VALUE ( ',' e= VALUE )* ')'
            {
            ID1=(Token)match(input,ID,FOLLOW_ID_in_in374); 

            		if (sql.where.get((ID1!=null?ID1.getText():null)) == null) {
            		  sql.where.put((ID1!=null?ID1.getText():null),new ArrayList<String>());
            		}
            	
            match(input,IN,FOLLOW_IN_in_in381); 
            match(input,20,FOLLOW_20_in_in383); 
            e=(Token)match(input,VALUE,FOLLOW_VALUE_in_in389); 
            sql.where.get((ID1!=null?ID1.getText():null)).add((e!=null?e.getText():null));
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:84:2: ( ',' e= VALUE )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==22) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:84:3: ',' e= VALUE
            	    {
            	    match(input,22,FOLLOW_22_in_in396); 
            	    e=(Token)match(input,VALUE,FOLLOW_VALUE_in_in399); 
            	    sql.where.get((ID1!=null?ID1.getText():null)).add((e!=null?e.getText():null));

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            match(input,21,FOLLOW_21_in_in404); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "in"


    // $ANTLR start "eq"
    // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:85:1: eq : ID '=' e= VALUE ;
    public final void eq() throws RecognitionException {
        Token e=null;
        Token ID2=null;

        try {
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:85:4: ( ID '=' e= VALUE )
            // G:\\code\\mybridge\\src\\mybridge\\sql\\parser\\Sql.g:85:9: ID '=' e= VALUE
            {
            ID2=(Token)match(input,ID,FOLLOW_ID_in_eq414); 

            		if (sql.where.get((ID2!=null?ID2.getText():null)) == null) {
            		  sql.where.put((ID2!=null?ID2.getText():null),new ArrayList<String>());
            		}
            	
            match(input,23,FOLLOW_23_in_eq422); 
            e=(Token)match(input,VALUE,FOLLOW_VALUE_in_eq429); 
            sql.where.get((ID2!=null?ID2.getText():null)).add((e!=null?e.getText():null));

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "eq"

    // Delegated rules


 

    public static final BitSet FOLLOW_statement_in_sql31 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_select_in_statement42 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insert_in_statement46 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_delete_in_statement50 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_update_in_statement54 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECT_in_select62 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_columns_in_select70 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_FROM_in_select75 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_select83 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_where_in_select91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERT_in_insert99 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_INTO_in_insert111 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_insert119 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_insert127 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_columns_in_insert132 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_21_in_insert139 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_VALUES_in_insert145 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_insert150 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_VALUE_in_insert159 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_insert171 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_VALUE_in_insert180 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_insert202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DELETE_in_delete210 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_FROM_in_delete218 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_delete225 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_where_in_delete233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_UPDATE_in_update242 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_update252 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_SET_in_update260 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_update264 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_update266 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_VALUE_in_update270 = new BitSet(new long[]{0x0000000000404000L});
    public static final BitSet FOLLOW_22_in_update284 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_update288 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_update290 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_VALUE_in_update294 = new BitSet(new long[]{0x0000000000404000L});
    public static final BitSet FOLLOW_where_in_update305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_columns315 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_columns326 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ID_in_columns330 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_WHERE_in_where342 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_in_in_where346 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_eq_in_where350 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_OP_in_where355 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_in_in_where358 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_eq_in_where362 = new BitSet(new long[]{0x0000000000008002L});
    public static final BitSet FOLLOW_ID_in_in374 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_IN_in_in381 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_in383 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_VALUE_in_in389 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_22_in_in396 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_VALUE_in_in399 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_21_in_in404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_eq414 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_eq422 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_VALUE_in_eq429 = new BitSet(new long[]{0x0000000000000002L});

}