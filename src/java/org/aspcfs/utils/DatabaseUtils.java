package com.darkhorseventures.utils;

import java.sql.*;

/**
 *  Database server specific sql
 *
 *@author     matt rajkowski
 *@created    March 18, 2002
 *@version    $Id$
 */
public class DatabaseUtils {

  public final static int POSTGRESQL = 1;
  public final static int MSSQL = 2;
  public final static int ORACLE = 3;

  public final static String POSTGRESQL_DRIVER = "postgresql";
  public final static String MSSQL_DRIVER = "sqlserver";
  public final static String ORACLE_DRIVER = "oracle";

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

}

