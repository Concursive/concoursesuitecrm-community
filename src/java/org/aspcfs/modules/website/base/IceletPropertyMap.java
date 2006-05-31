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

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @created    February 10, 2006
 * @version    $Id: Exp $
 */
public class IceletPropertyMap extends LinkedHashMap {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private int iceletRowColumnId = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean buildNames = false;
  private IceletPropertyMap defaultProperties = null;


  /**
   *  Constructor for the SiteLogList object
   */
  public IceletPropertyMap() { }


  /**
   *  Sets the pagedListInfo attribute of the IceletPropertyList object
   *
   * @param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the id attribute of the IceletPropertyList object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the IceletPropertyList object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the iceletRowColumnId attribute of the IceletPropertyList object
   *
   * @param  tmp  The new iceletRowColumnId value
   */
  public void setIceletRowColumnId(int tmp) {
    this.iceletRowColumnId = tmp;
  }


  /**
   *  Sets the iceletRowColumnId attribute of the IceletPropertyList object
   *
   * @param  tmp  The new iceletRowColumnId value
   */
  public void setIceletRowColumnId(String tmp) {
    this.iceletRowColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the IceletPropertyList object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the IceletPropertyList object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the IceletPropertyList object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the IceletPropertyList object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Gets the pagedListInfo attribute of the IceletPropertyList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the id attribute of the IceletPropertyList object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the iceletRowColumnId attribute of the IceletPropertyList object
   *
   * @return    The iceletRowColumnId value
   */
  public int getIceletRowColumnId() {
    return iceletRowColumnId;
  }


  /**
   *  Gets the enteredBy attribute of the IceletPropertyList object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the IceletPropertyList object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the buildNames attribute of the IceletPropertyMap object
   *
   * @return    The buildNames value
   */
  public boolean getBuildNames() {
    return buildNames;
  }


  /**
   *  Sets the buildNames attribute of the IceletPropertyMap object
   *
   * @param  tmp  The new buildNames value
   */
  public void setBuildNames(boolean tmp) {
    this.buildNames = tmp;
  }


  /**
   *  Sets the buildNames attribute of the IceletPropertyMap object
   *
   * @param  tmp  The new buildNames value
   */
  public void setBuildNames(String tmp) {
    this.buildNames = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the defaultProperties attribute of the IceletPropertyMap object
   *
   * @return    The defaultProperties value
   */
  public IceletPropertyMap getDefaultProperties() {
    return defaultProperties;
  }


  /**
   *  Sets the defaultProperties attribute of the IceletPropertyMap object
   *
   * @param  tmp  The new defaultProperties value
   */
  public void setDefaultProperties(IceletPropertyMap tmp) {
    this.defaultProperties = tmp;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      IceletProperty thisIceletProperty = this.getObject(rs);
      if (defaultProperties != null) {
        IceletProperty defaultProperty = (IceletProperty) defaultProperties.get(new Integer(thisIceletProperty.getTypeConstant()));
        if (defaultProperty != null) {
          thisIceletProperty.setType(defaultProperty.getType());
        }
      }
      this.put(new Integer(thisIceletProperty.getTypeConstant()), thisIceletProperty);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (this.getBuildNames()) {
      Iterator nameIterator = this.keySet().iterator();
      while (nameIterator.hasNext()) {
        Integer name = (Integer) nameIterator.next();
        IceletProperty tmpIceletProperty = (IceletProperty) this.get(name);
        tmpIceletProperty.buildValueString(db);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {

    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM icelet_property " +
        "WHERE property_id > -1 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("property_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY property_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        " * " +
        "FROM icelet_property " +
        "WHERE property_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    return rs;
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter      Description of the Parameter
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
    if (id > -1) {
      sqlFilter.append("AND property_id = ? ");
    }
    if (iceletRowColumnId > -1) {
      sqlFilter.append("AND row_column_id = ? ");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (iceletRowColumnId > -1) {
      pst.setInt(++i, iceletRowColumnId);
    }
    return i;
  }


  /**
   *  Gets the object attribute of the IceletPropertyMap object
   *
   * @param  rs             Description of the Parameter
   * @return                The object value
   * @throws  SQLException  Description of the Exception
   */
  public IceletProperty getObject(ResultSet rs) throws SQLException {
    return new IceletProperty(rs);
  }


  /**
   *  Sets the requestItems attribute of the IceletPropertyMap object
   *
   * @param  context  The new requestItems value
   */
  public void setRequestItems(ActionContext context) {
    Map parameters = context.getRequest().getParameterMap();
    Iterator parameterNameIterator = parameters.keySet().iterator();
    while (parameterNameIterator.hasNext()) {
      String thisParamName = (String) parameterNameIterator.next();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("PARAM NAME  **** " + thisParamName);
      }
      if (thisParamName.indexOf("property_") != -1) {
        String[] values = thisParamName.split("_");
        Integer name = new Integer(values[2]);
        int propertyRowColumn = Integer.parseInt(values[1]);
        String value = context.getRequest().getParameter(thisParamName);
        IceletProperty tmpIceletProperty = new IceletProperty();
        tmpIceletProperty.setTypeConstant(name.intValue());
        tmpIceletProperty.setValue(value);
        tmpIceletProperty.setRowColumnId(iceletRowColumnId);
        tmpIceletProperty.setEnteredBy(enteredBy);
        tmpIceletProperty.setModifiedBy(modifiedBy);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("NAME *** " + name + " VALUE *** " + context.getRequest().getParameter(thisParamName));
        }
        if (propertyRowColumn == iceletRowColumnId) {
          this.put(name, tmpIceletProperty);
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator nameIterator = this.keySet().iterator();
    while (nameIterator.hasNext()) {
      Integer name = (Integer) nameIterator.next();
      IceletProperty tmpIceletProperty = (IceletProperty) this.get(name);
      tmpIceletProperty.delete(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator nameIterator = this.keySet().iterator();
    while (nameIterator.hasNext()) {
      Integer name = (Integer) nameIterator.next();
      IceletProperty tmpIceletProperty = (IceletProperty) this.get(name);
      //if (name.intValue() != FormIcelet.STATE) {
      tmpIceletProperty.insert(db);
      //}
    }
  }


  /**
   *  Adds a feature to the AdditionalInformation attribute of the
   *  IceletPropertyMap object
   *
   * @param  rowColumnId  The feature to be added to the AdditionalInformation
   *      attribute
   * @param  modifiedBy   The feature to be added to the AdditionalInformation
   *      attribute
   */
  public void addAdditionalInformation(int rowColumnId, int modifiedBy) {
    Iterator iter = this.keySet().iterator();
    while (iter.hasNext()) {
      Integer name = (Integer) iter.next();
      IceletProperty tmpIceletProperty = (IceletProperty) this.get(name);
      tmpIceletProperty.setRowColumnId(rowColumnId);
      tmpIceletProperty.setModifiedBy(modifiedBy);
    }
  }


  /**
   *  Gets the valueFromConstant attribute of the IceletPropertyMap object
   *
   * @param  tmpPropertyConstant  Description of the Parameter
   * @return                      The valueFromConstant value
   */
  public String getValueFromConstant(int tmpPropertyConstant) {
    IceletProperty tmpIceletProperty = null;
    String tmpIceletPropertyValue = null;

    tmpIceletProperty = (IceletProperty) this.get(new Integer(tmpPropertyConstant));
    tmpIceletPropertyValue = ((tmpIceletProperty == null) ? "" : tmpIceletProperty.getValue());

    return tmpIceletPropertyValue;
  }


  /**
   *  Gets the property attribute of the IceletPropertyMap object
   *
   * @param  tmpPropertyConstant  Description of the Parameter
   * @return                      The property value
   */
  public IceletProperty getProperty(int tmpPropertyConstant) {
    return (IceletProperty) this.get(new Integer(tmpPropertyConstant));
  }


  /**
   *  Sets the elements attribute of the IceletPropertyMap object
   *
   * @param  str  The new elements value
   */
  public void setElements(String str) {
    StringTokenizer properties = new StringTokenizer(str, "|");
    while (properties.hasMoreTokens()) {
      IceletProperty tmpIceletProperty = new IceletProperty();
      StringTokenizer property = new StringTokenizer(properties.nextToken(), ",");
      tmpIceletProperty.setTypeConstant(property.nextToken());
      //name.intValue());
      tmpIceletProperty.setValue(property.nextToken());
      //value);
//      tmpIceletProperty.setModifiedBy(property.nextToken());//modifiedBy);
//      tmpIceletProperty.setRowColumnId(iceletRowColumnId);
      this.put(tmpIceletProperty.getTypeConstant(), tmpIceletProperty);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  oldMap            Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updateEntries(Connection db, IceletPropertyMap oldMap) throws SQLException {
    Iterator nameIterator = (Iterator) oldMap.keySet().iterator();
    while (nameIterator.hasNext()) {
      Integer name = (Integer) nameIterator.next();
      IceletProperty tmpIceletProperty = (IceletProperty) this.get(name);
      IceletProperty newIcelet = (IceletProperty) this.get(name);
//      tmpIceletProperty.setValue(newIcelet.getValue());
      tmpIceletProperty.setModifiedBy(newIcelet.getModifiedBy());
      tmpIceletProperty.update(db);
    }
  }
}

