/*
 *  Developed in partnership with Matt Rajkowski
 */
package org.aspcfs.utils;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.io.*;

/**
 *  Useful methods for working with multiple databases and database fields
 *
 *@author     matt rajkowski
 *@created    March 18, 2002
 *@version    $Id: DatabaseUtils.java,v 1.13 2002/11/04 13:21:16 mrajkowski Exp
 *      $
 */
public class DatabaseUtils {

  public final static int POSTGRESQL = 1;
  public final static int MSSQL = 2;
  public final static int ORACLE = 3;

  public final static String POSTGRESQL_DRIVER = "postgresql";
  public final static String MSSQL_DRIVER = "sqlserver";
  public final static String ORACLE_DRIVER = "oracle";

  public final static String CRLF = System.getProperty("line.separator");

  /**
   *  Gets the true attribute of the DatabaseUtils class
   *
   *@param  db  Description of Parameter
   *@return     The true value
   */
  public static String getTrue(Connection db) {
    String databaseName = db.getClass().getName();
    if (databaseName.indexOf(POSTGRESQL_DRIVER) > -1) {
      return "true";
    } else if (databaseName.indexOf(MSSQL_DRIVER) > -1) {
      return "1";
    } else if (databaseName.indexOf(ORACLE_DRIVER) > -1) {
      return "true";
    } else {
      return "true";
    }
  }


  /**
   *  Gets the false attribute of the DatabaseUtils class
   *
   *@param  db  Description of Parameter
   *@return     The false value
   */
  public static String getFalse(Connection db) {
    String databaseName = db.getClass().getName();
    if (databaseName.indexOf(POSTGRESQL_DRIVER) > -1) {
      return "false";
    } else if (databaseName.indexOf(MSSQL_DRIVER) > -1) {
      return "0";
    } else if (databaseName.indexOf(ORACLE_DRIVER) > -1) {
      return "false";
    } else {
      return "false";
    }
  }


  /**
   *  Gets the currentTimestamp attribute of the DatabaseUtils class
   *
   *@param  db  Description of Parameter
   *@return     The currentTimestamp value
   */
  public static String getCurrentTimestamp(Connection db) {
    String databaseName = db.getClass().getName();
    if (databaseName.indexOf(POSTGRESQL_DRIVER) > -1) {
      return "CURRENT_TIMESTAMP";
    } else if (databaseName.indexOf(MSSQL_DRIVER) > -1) {
      return "CURRENT_TIMESTAMP";
    } else if (databaseName.indexOf(ORACLE_DRIVER) > -1) {
      return "CURRENT_TIMESTAMP";
    } else {
      return "CURRENT_TIMESTAMP";
    }
  }


  /**
   *  Gets the type attribute of the DatabaseUtils class
   *
   *@param  db  Description of Parameter
   *@return     The type value
   */
  public static int getType(Connection db) {
    String databaseName = db.getClass().getName();
    if (databaseName.indexOf(POSTGRESQL_DRIVER) > -1) {
      return POSTGRESQL;
    } else if (databaseName.indexOf(MSSQL_DRIVER) > -1) {
      return MSSQL;
    } else if (databaseName.indexOf(ORACLE_DRIVER) > -1) {
      return ORACLE;
    } else {
      return -1;
    }
  }


  /**
   *  Gets the currVal attribute of the DatabaseUtils class
   *
   *@param  db                Description of the Parameter
   *@param  sequenceName      Description of the Parameter
   *@return                   The currVal value
   *@exception  SQLException  Description of the Exception
   */
  public static int getCurrVal(Connection db, String sequenceName) throws SQLException {
    int id = -1;
    Statement st = db.createStatement();
    ResultSet rs = null;
    switch (DatabaseUtils.getType(db)) {
        case DatabaseUtils.POSTGRESQL:
          rs = st.executeQuery("SELECT currval('" + sequenceName + "')");
          break;
        case DatabaseUtils.MSSQL:
          rs = st.executeQuery("SELECT @@IDENTITY");
          break;
        default:
          break;
    }
    if (rs.next()) {
      id = rs.getInt(1);
    }
    rs.close();
    st.close();
    return id;
  }


  /**
   *  Description of the Method
   *
   *@param  tmp           Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               Description of the Return Value
   */
  public static int parseInt(String tmp, int defaultValue) {
    try {
      return Integer.parseInt(tmp);
    } catch (Exception e) {
      return defaultValue;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of the Parameter
   *@return      Description of the Return Value
   */
  public static boolean parseBoolean(String tmp) {
    return ("ON".equalsIgnoreCase(tmp) ||
        "TRUE".equalsIgnoreCase(tmp) ||
        "1".equals(tmp));
  }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of the Parameter
   *@return      Description of the Return Value
   */
  public static java.sql.Date parseDate(String tmp) {
    java.sql.Date dateValue = null;
    try {
      java.util.Date tmpDate = DateFormat.getDateInstance(DateFormat.SHORT).parse(tmp);
      dateValue = new java.sql.Date(new java.util.Date().getTime());
      dateValue.setTime(tmpDate.getTime());
      return dateValue;
    } catch (Exception e) {
      try {
        return java.sql.Date.valueOf(tmp);
      } catch (Exception e2) {
      }
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of the Parameter
   *@return      Description of the Return Value
   */
  public static java.sql.Timestamp parseTimestamp(String tmp) {
    java.sql.Timestamp timestampValue = null;
    try {
      java.util.Date tmpDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).parse(tmp);
      timestampValue = new java.sql.Timestamp(new java.util.Date().getTime());
      timestampValue.setTime(tmpDate.getTime());
      return timestampValue;
    } catch (Exception e) {
      try {
        return java.sql.Timestamp.valueOf(tmp);
      } catch (Exception e2) {
      }
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of the Parameter
   *@return      Description of the Return Value
   */
  public static java.sql.Timestamp parseDateToTimestamp(String tmp) {
    java.sql.Timestamp timestampValue = DatabaseUtils.parseTimestamp(tmp);
    if (timestampValue == null) {
      try {
        java.util.Date tmpDate = DateFormat.getDateInstance(DateFormat.SHORT).parse(tmp);
        timestampValue = new java.sql.Timestamp(System.currentTimeMillis());
        timestampValue.setTime(tmpDate.getTime());
        timestampValue.setNanos(0);
        return timestampValue;
      } catch (Exception e) {
      }
    }
    return timestampValue;
  }


  /**
   *  Gets the int attribute of the DatabaseUtils class
   *
   *@param  rs                Description of the Parameter
   *@param  column            Description of the Parameter
   *@param  defaultValue      Description of the Parameter
   *@return                   The int value
   *@exception  SQLException  Description of the Exception
   */
  public static int getInt(ResultSet rs, String column, int defaultValue) throws SQLException {
    int fieldValue = rs.getInt(column);
    if (rs.wasNull()) {
      fieldValue = defaultValue;
    }
    return fieldValue;
  }


  /**
   *  Gets the int attribute of the DatabaseUtils class
   *
   *@param  rs                Description of the Parameter
   *@param  column            Description of the Parameter
   *@return                   The int value
   *@exception  SQLException  Description of the Exception
   */
  public static int getInt(ResultSet rs, String column) throws SQLException {
    return DatabaseUtils.getInt(rs, column, -1);
  }


  /**
   *  Gets the long attribute of the DatabaseUtils class
   *
   *@param  rs                Description of the Parameter
   *@param  column            Description of the Parameter
   *@return                   The long value
   *@exception  SQLException  Description of the Exception
   */
  public static long getLong(ResultSet rs, String column) throws SQLException {
    return DatabaseUtils.getLong(rs, column, -1);
  }


  /**
   *  Gets the long attribute of the DatabaseUtils class
   *
   *@param  rs                Description of the Parameter
   *@param  column            Description of the Parameter
   *@param  defaultValue      Description of the Parameter
   *@return                   The long value
   *@exception  SQLException  Description of the Exception
   */
  public static long getLong(ResultSet rs, String column, long defaultValue) throws SQLException {
    long fieldValue = rs.getLong(column);
    if (rs.wasNull()) {
      fieldValue = defaultValue;
    }
    return fieldValue;
  }


  /**
   *  Sets the int attribute of the DatabaseUtils class
   *
   *@param  pst               The new int value
   *@param  paramCount        The new int value
   *@param  value             The new int value
   *@exception  SQLException  Description of the Exception
   */
  public static void setInt(PreparedStatement pst, int paramCount, int value) throws SQLException {
    if (value == -1) {
      pst.setNull(paramCount, java.sql.Types.INTEGER);
    } else {
      pst.setInt(paramCount, value);
    }
  }


  /**
   *  Sets the long attribute of the DatabaseUtils class
   *
   *@param  pst               The new long value
   *@param  paramCount        The new long value
   *@param  value             The new long value
   *@exception  SQLException  Description of the Exception
   */
  public static void setLong(PreparedStatement pst, int paramCount, long value) throws SQLException {
    if (value == -1) {
      pst.setNull(paramCount, java.sql.Types.INTEGER);
    } else {
      pst.setLong(paramCount, value);
    }
  }


  /**
   *  Sets the timestamp attribute of the DatabaseUtils class
   *
   *@param  pst               The new timestamp value
   *@param  paramCount        The new timestamp value
   *@param  value             The new timestamp value
   *@exception  SQLException  Description of the Exception
   */
  public static void setTimestamp(PreparedStatement pst, int paramCount, java.sql.Timestamp value) throws SQLException {
    if (value == null) {
      pst.setNull(paramCount, java.sql.Types.DATE);
    } else {
      pst.setTimestamp(paramCount, value);
    }
  }


  /**
   *  Sets the date attribute of the DatabaseUtils class
   *
   *@param  pst               The new date value
   *@param  paramCount        The new date value
   *@param  value             The new date value
   *@exception  SQLException  Description of the Exception
   */
  public static void setDate(PreparedStatement pst, int paramCount, java.sql.Date value) throws SQLException {
    if (value == null) {
      pst.setNull(paramCount, java.sql.Types.DATE);
    } else {
      pst.setDate(paramCount, value);
    }
  }


  /**
   *  Reads in a text file of SQL statements and executes them
   *
   *@param  db                Description of the Parameter
   *@param  filename          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@exception  IOException   Description of the Exception
   */
  public static void executeSQL(Connection db, String filename) throws SQLException, IOException {
    // Read the file and execute each statement
    StringBuffer sql = new StringBuffer();
    BufferedReader in = new BufferedReader(new FileReader(filename));
    String line = null;
    Statement st = db.createStatement();
    int tCount = 0;
    while ((line = in.readLine()) != null) {
      // SQL Comment
      if (line.startsWith("//")) {
        continue;
      }
      // SQL Comment
      if (line.startsWith("--")) {
        continue;
      }
      sql.append(line);
      // check for delimeter
      if (line.trim().endsWith(";") || line.trim().equals("GO")) {
        // Got a transaction, so execute it
        ++tCount;
        st.execute(sql.toString());
        sql.setLength(0);
      } else {
        // Continue with another line
        sql.append(CRLF);
      }
      if (System.getProperty("DEBUG") != null) {
        if (tCount%10 == 0) {
          System.out.println("Up to " + tCount + " statements");
        }
      }
    }
    // Statement didn't end with a delimiter
    if (sql.length() > 0) {
      ++tCount;
      st.execute(sql.toString());
    }
    st.close();
    in.close();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Executed " + tCount + " total statements");
    }
  }
}

