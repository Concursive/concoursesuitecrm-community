/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    March 16, 2007
 */
public class DatabaseTable {
  private int dbType = DatabaseUtils.POSTGRESQL;

  private String tableName = "";
  private String sequenceName = "";
  private ArrayList columns = new ArrayList();


  /**
   *  Gets the dbType attribute of the DatabaseTable object
   *
   * @return    The dbType value
   */
  public int getDbType() {
    return dbType;
  }


  /**
   *  Sets the dbType attribute of the DatabaseTable object
   *
   * @param  tmp  The new dbType value
   */
  public void setDbType(int tmp) {
    this.dbType = tmp;
  }


  /**
   *  Sets the dbType attribute of the DatabaseTable object
   *
   * @param  tmp  The new dbType value
   */
  public void setDbType(String tmp) {
    this.dbType = Integer.parseInt(tmp);
  }


  /**
   *  Gets the columns attribute of the DatabaseTable object
   *
   * @return    The columns value
   */
  public ArrayList getColumns() {
    return columns;
  }


  /**
   *  Sets the columns attribute of the DatabaseTable object
   *
   * @param  tmp  The new columns value
   */
  public void setColumns(ArrayList tmp) {
    this.columns = tmp;
  }



  /**
   *  Gets the tableName attribute of the DatabaseTable object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Sets the tableName attribute of the DatabaseTable object
   *
   * @param  tmp  The new tableName value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Gets the sequenceName attribute of the DatabaseTable object
   *
   * @return    The sequenceName value
   */
  public String getSequenceName() {
    return sequenceName;
  }


  /**
   *  Sets the sequenceName attribute of the DatabaseTable object
   *
   * @param  tmp  The new sequenceName value
   */
  public void setSequenceName(String tmp) {
    this.sequenceName = tmp;
  }



  /**
   *  Constructor for the DatabaseTable object
   */
  public DatabaseTable() { }


  /**
   *  Constructor for the DatabaseTable object
   *
   * @param  tableName  Description of the Parameter
   */
  public DatabaseTable(String tableName) {
    this.tableName = tableName;
  }


  /**
   *  Constructor for the DatabaseTable object
   *
   * @param  tableName     Description of the Parameter
   * @param  sequenceName  Description of the Parameter
   */
  public DatabaseTable(String tableName, String sequenceName) {
    this.tableName = tableName;
    this.sequenceName = sequenceName;
  }


  /**
   *  Constructor for the DatabaseTable object
   *
   * @param  table  Description of the Parameter
   */
  public DatabaseTable(DatabaseTable table) {
    tableName = table.getTableName();
    sequenceName = table.getSequenceName();
    Iterator i = table.getColumns().iterator();
    while (i.hasNext()) {
      DatabaseColumn thisColumn = (DatabaseColumn) i.next();
      DatabaseColumn column = new DatabaseColumn(thisColumn);
      this.addColumn(column);
    }
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasSequence() {
    return (sequenceName != null &&
        !"".equals(sequenceName.trim()));
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void create(Connection db) throws SQLException {
    if (tableName == null) {
      throw new SQLException("Table Name not specified");
    }

    Statement st = db.createStatement();

    if (dbType == DatabaseUtils.POSTGRESQL) {
      if (hasSequence()) {
        st.executeUpdate("CREATE SEQUENCE " + sequenceName);
      }
    }

    StringBuffer sql = new StringBuffer();
    sql.append("CREATE TABLE " + tableName + " (");

    String primaryKey = "";
    Iterator fields = columns.iterator();
    while (fields.hasNext()) {
      DatabaseColumn thisColumn = (DatabaseColumn) fields.next();
      if (thisColumn.isPrimaryKey()) {
        primaryKey = thisColumn.getName();
        sql.append(thisColumn.getCreateSQL(dbType, sequenceName));
      } else {
        sql.append(thisColumn.getCreateSQL(dbType));
      }
      if (fields.hasNext()) {
        sql.append(",");
      }
    }
    if (dbType == DatabaseUtils.ORACLE) {
      sql.append(", PRIMARY KEY (" + primaryKey + ")");
    }
    sql.append(");");
    st.executeUpdate(sql.toString());
    st.close();
  }


  /**
   *  Adds a feature to the Column attribute of the DatabaseTable object
   *
   * @param  column  The feature to be added to the Column attribute
   */
  public void addColumn(DatabaseColumn column) {
    addColumn(-1, column);
  }


  /**
   *  Adds a feature to the Column attribute of the DatabaseTable object
   *
   * @param  column  The feature to be added to the Column attribute
   * @param  index   The feature to be added to the Column attribute
   */
  public void addColumn(int index, DatabaseColumn column) {
    if (columns == null) {
      columns = new ArrayList();
    }
    if (index > -1) {
      columns.add(index, column);
    } else {
      columns.add(column);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  clone             Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void selectInto(Connection db, DatabaseTable clone) throws SQLException {
    StringBuffer select = new StringBuffer("SELECT ");
    StringBuffer insert = new StringBuffer("INSERT INTO " + clone.getTableName() + "(");
    StringBuffer values = new StringBuffer();

    Iterator i = columns.iterator();
    while (i.hasNext()) {
      DatabaseColumn column = (DatabaseColumn) i.next();
      select.append(column.getName());
      insert.append(column.getName());
      values.append("?");
      if (i.hasNext()) {
        select.append(", ");
        insert.append(", ");
        values.append(", ");
      }
    }
    select.append(" FROM " + tableName);
    insert.append(") VALUES (" + values.toString() + ")");

    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(select.toString());

    PreparedStatement pst = db.prepareStatement(insert.toString());
    while (rs.next()) {
      int j = 0;
      Iterator k = columns.iterator();
      while (k.hasNext()) {
        ++j;
        DatabaseColumn column = (DatabaseColumn) k.next();
        Object object = rs.getObject(j);
        if (object != null) {
          pst.setObject(j, object);
        } else {
          pst.setNull(j, column.getType());
        }
      }
      pst.execute();
    }

    rs.close();
    st.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void drop(Connection db) throws SQLException {
    drop(db, true);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  dropSequence      Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void drop(Connection db, boolean dropSequence) throws SQLException {
    Statement st = db.createStatement();

    //drop the table
    st.executeUpdate("DROP TABLE " + tableName);

    if (dbType == DatabaseUtils.POSTGRESQL) {
      if (hasSequence() && dropSequence) {
        //TODO: Not all versions of postgres supported by centric, supports IF EXISTS. Need a better solution
        //drop the sequence
        st.executeUpdate("DROP SEQUENCE IF EXISTS " + sequenceName);
      }
    }
    st.close();
  }
}

