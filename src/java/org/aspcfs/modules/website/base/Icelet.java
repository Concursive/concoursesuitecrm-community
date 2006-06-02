/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    February 10, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class Icelet extends GenericBean {

  private int id = -1;
  private String name = null;
  private String description = null;
  private String configuratorClass = null;
  private int version = -1;
  private boolean enabled = false;

  //helper attribute to store properties for the icelet
  private IceletPropertyMap iceletPropertyMap = null;

  /**
   *  Constructor for the icelet object
   */
  public Icelet() { }


  /**
   *  Constructor for the icelet object
   *
   *@param  db                Description of the Parameter
   *@param  tmpIceletId       Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public Icelet(Connection db, int tmpIceletId) throws SQLException {
    queryRecord(db, tmpIceletId);
  }


  /**
   *  Constructor for the icelet object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@throws  SQLException     Description of the Exception
   */
  public Icelet(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the icelet object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the icelet object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the icelet object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the icelet object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the configuratorClass attribute of the icelet object
   *
   *@param  tmp  The new configuratorClass value
   */
  public void setConfiguratorClass(String tmp) {
    this.configuratorClass = tmp;
  }


  /**
   *  Sets the version attribute of the Icelet object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(int tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the version attribute of the Icelet object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(String tmp) {
    this.version = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the icelet object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the icelet object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the iceletPropertyMap attribute of the icelet object
   *
   *@param  tmp  The new iceletPropertyMap value
   */
  public void setIceletPropertyMap(IceletPropertyMap tmp) {
    this.iceletPropertyMap = tmp;
  }


  /**
   *  Gets the id attribute of the icelet object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the icelet object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the icelet object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the configuratorClass attribute of the icelet object
   *
   *@return    The configuratorClass value
   */
  public String getConfiguratorClass() {
    return configuratorClass;
  }


  /**
   *  Gets the version attribute of the Icelet object
   *
   *@return    The version value
   */
  public int getVersion() {
    return version;
  }


  /**
   *  Gets the enabled attribute of the icelet object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the iceletPropertyMap attribute of the icelet object
   *
   *@return    The iceletPropertyMap value
   */
  public IceletPropertyMap getIceletPropertyMap() {
    return iceletPropertyMap;
  }

  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@param  tmpIceletId    Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpIceletId) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_icelet " +
        " WHERE icelet_id = ? ");
    pst.setInt(1, tmpIceletId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {

    id = DatabaseUtils.getNextSeq(db, "web_icelet_icelet_id_seq");

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_icelet " +
        "(" + (id > -1 ? "icelet_id, " : "") +
        "icelet_name , " +
        "icelet_description , " +
        "icelet_configurator_class , " +
        "icelet_version , " +
        "enabled )  " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, name);
    pst.setString(++i, description);
    pst.setString(++i, configuratorClass);
    DatabaseUtils.setInt(pst, ++i, version);
    pst.setBoolean(++i, enabled);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_icelet_icelet_id_seq", id);
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {

    int resultCount = -1;

    PreparedStatement pst = db.prepareStatement(
        "UPDATE web_icelet " +
        "SET " +
        "icelet_name = ? , " +
        "icelet_description = ? , " +
        "icelet_configurator_class = ? , " +
        "icelet_version = ? , " +
        "enabled = ?  " +
        "WHERE icelet_id = ? ");
    int i = 0;
    pst.setString(++i, name);
    pst.setString(++i, description);
    pst.setString(++i, configuratorClass);
    DatabaseUtils.setInt(pst, ++i, version);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      RowColumnList tmpRowColumnList = new RowColumnList();
      tmpRowColumnList.setIceletId(this.getId());
      tmpRowColumnList.buildList(db);
      tmpRowColumnList.delete(db);
      tmpRowColumnList = null;

      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM web_icelet " +
          "WHERE icelet_id = ? ");

      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs             Description of the Parameter
   *@throws  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("icelet_id");
    name = rs.getString("icelet_name");
    description = rs.getString("icelet_description");
    configuratorClass = rs.getString("icelet_configurator_class");
    version = DatabaseUtils.getInt(rs,"icelet_version");
    enabled = rs.getBoolean("enabled");
  }

}

