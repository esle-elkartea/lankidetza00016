package com.code.aon.hsqldb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MySql2Hsql {

    public static String HSQL_URL = "jdbc:hsqldb:hsql://127.0.0.1:9001/aselcar";
    public static String MYSQL_URL = "jdbc:mysql://192.168.2.4:3306/aselcar";

    private Connection hsql;
    private Connection mysql;

    private MySql2Hsql() throws Exception {

        Class.forName("org.hsqldb.jdbcDriver");
        Class.forName("org.gjt.mm.mysql.Driver");

        ArrayList tableList = new ArrayList();

        ResultSet tables = null;

        try {
            hsql = DriverManager.getConnection(HSQL_URL);
            mysql = DriverManager.getConnection(MYSQL_URL, "dbuser", "serubd2000");
    
            DatabaseMetaData dbmd = mysql.getMetaData();
            tables = dbmd.getTables(null, null, "%", new String[] { "TABLE" });
            while (tables.next()) {
    
                String table = tables.getString(3);
                if (!table.startsWith("cms") && !table.startsWith("tmp")) {
                    createTable(tableList, dbmd, table);
                }
                if(table.equals("geozone") || table.equals("warehouse")){
                	insertData(table);
                }
            }
        } finally {
            if (tables != null)
                tables.close();
            hsql.close();
            mysql.close();
        }

    }

    private void createTable(ArrayList tableList, DatabaseMetaData dbmd, String table) 
                throws SQLException {
        ResultSet pks = dbmd.getPrimaryKeys(null, null, table);
        List<String> primaryKeys = new ArrayList<String>();
        while (pks.next()) {
            primaryKeys.add(pks.getString(4));
        }
        pks.close();

        int auto = 0;
        boolean hasAutoIncrement = hasAutoIncrementColumn(table);

        ResultSet cols = dbmd.getColumns(null, null, table, "%");
        StringBuffer defs = new StringBuffer();
        while (cols.next()) {
            int type = cols.getInt(5);
            String dataType = cols.getString(6);
            String def = cols.getString(4);
            switch (type) {
            case Types.INTEGER:
                def += " INTEGER" + ((hasAutoIncrement && auto == 0)? " IDENTITY": "");
                break;
            case Types.VARCHAR:
                def += " " + dataType + "( " + cols.getInt(7) + " ) ";
                break;
            case Types.VARBINARY:
                def += " other ";
                break;
            case Types.LONGVARBINARY:
                def += " LONGVARBINARY";
                break;
            case -1:
                if (dataType.equalsIgnoreCase("TEXT"))
                    def += " longvarchar";
                else
                    def += " varchar( " + cols.getInt(7) + " ) ";
                break;
            default:
                def += " " + dataType;
                break;
            }

            int nullable = cols.getInt(11);
            switch (nullable) {
            case DatabaseMetaData.columnNoNulls:
                def += " NOT NULL ";
                break;
            }

            //              if (primaryKeys.contains(columnname))
            //                  def += " PRIMARY KEY ";

            if (defs.length() > 0)
                defs.append(",\n");
            defs.append("\t" + def);
            auto++;
        }
        cols.close();

        String create = "CREATE " + (tableList.contains(table) ? "CACHED"
                : "") + " TABLE " + table + " ( " + "\n" + defs;

        if (primaryKeys.size() > 0) {
            Iterator iter = primaryKeys.iterator();
            boolean first = true;
            while (iter.hasNext()) {
                create += " , ";
                if (first)
                    create += "\n PRIMARY KEY (";
                create += (String) iter.next();
                first = false;
            }
            create += ")";
        }
        create += "\n" + ")";

        Statement stmt = hsql.createStatement();
        stmt.execute(create);
        stmt.close();
        System.out.println("Table ..: " + table + " ... created\n"+create);
    }

    private boolean hasAutoIncrementColumn(String table) {
        return ( !table.equalsIgnoreCase("customer") && 
                 !table.equalsIgnoreCase("operator") && 
                 !table.equalsIgnoreCase("rjoin") &&
                 !table.equalsIgnoreCase("sales_attach") && 
                 !table.equalsIgnoreCase("seller") &&
                 !table.equalsIgnoreCase("target") );
    }

    private void insertData(String table) throws SQLException {
        ResultSet src = mysql.prepareStatement("SELECT * FROM " + table).executeQuery();
        ResultSetMetaData rsmd = src.getMetaData();
        System.out.print("  inserting .... ");
        int num = 0;
        while (src.next()) {

        	//INSERT INTO table [( column [,...] )] //{ VALUES(Expression [,...]) | SelectStatement};
            int columnCount = rsmd.getColumnCount();
            StringBuffer values = new StringBuffer();
            for (int i = 0; i < columnCount; i++) {
                values.append('?');
                if (i < columnCount - 1)
                    values.append(',');
            }
            PreparedStatement insert = hsql.prepareStatement("INSERT INTO "
                    + table + " VALUES ( " + values + ") ");

            for (int i = 1; i <= columnCount; ++i) {
                int type = rsmd.getColumnType(i);
                Object value = src.getObject(i);
                if (value != null) {
                    insert.setObject(i, value, type);
                } else {
                    insert.setNull(i, type);
                }
            }
            insert.execute();
            insert.close();
            num++;
        }
        System.out.println("[" + num + "]");
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            System.out.println(arg);
            if ("-hsql".equals(arg))
                HSQL_URL = args[++i];
            if ("-mysql".equals(arg))
                MYSQL_URL = args[++i];
        }
        new MySql2Hsql();
    }

}