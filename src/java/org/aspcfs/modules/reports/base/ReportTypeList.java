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
package org.aspcfs.modules.reports.base;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.*;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author zhenya.zhidok
 * @version $Id: ReportTypeList.java 24.01.2007 zhenya.zhidok $
 * @created 24.01.2007
 *
 */
public class ReportTypeList extends HtmlSelect {
	
  public static String uniqueField = "code";
  private String tableName = "lookup_report_type";
  private java.sql.Timestamp lastAnchor = null;
  private java.sql.Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  private int code = -1;
  private String description = "";
  private int constant = -1;
  protected PagedListInfo pagedListInfo = null;

  /**
   * Gets the code attribute of the ReportTypeList object
   * 
   * @return code The code value
   */
  public int getCode() {
    return this.code;
  }

  /**
   * Sets the code attribute of the ReportTypeList object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Sets the code attribute of the ReportTypeList object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(String code) {
    this.code = Integer.parseInt(code);
  }

  /**
   * Gets the constant attribute of the ReportTypeList object
   * 
   * @return constant The constant value
   */
  public int getConstant() {
    return this.constant;
  }

  /**
   * Sets the constant attribute of the ReportTypeList object
   * 
   * @param constant
   *          The new constant value
   */
  public void setConstant(int constant) {
    this.constant = constant;
  }

  /**
   * Sets the constant attribute of the ReportTypeList object
   * 
   * @param constant
   *          The new constant value
   */
  public void setConstant(String constant) {
    this.constant = Integer.parseInt(constant);
  }

  /**
   * Gets the description attribute of the ReportTypeList object
   * 
   * @return description The description value
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description attribute of the ReportTypeList object
   * 
   * @param description
   *          The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * Gets the object attribute of the ReportTypeList object
   * 
   * @param rs
   *          Description of Parameter
   * @return The object value
   * @throws SQLException
   *           Description of Exception
   */
  public ReportType getObject(ResultSet rs) throws SQLException {
    ReportType thisElement = new ReportType(rs);
    return thisElement;
  }
  
  /**
   * This method is required for synchronization, it allows for the resultset to
   * be streamed with lower overhead
   * 
   * @param db
   *          Description of the Parameter
   * @param pst
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */

  public ResultSet queryList(Connection db, PreparedStatement pst)
      throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("LookupList-> lookup_report_type");
    }
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * " +
        "FROM lookup_report_type " +
        "ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", description ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    return rs;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of Parameter
   * @throws SQLException
   *           Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      ReportType thisElement = this.getObject(rs);
      this.add(thisElement);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }

  /**
   * Gets the htmlSelect attribute of the ReportTypeList object
   * 
   * @param selectName
   *          Description of the Parameter
   * @param defaultKey
   *          Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    return getHtmlSelect(selectName, defaultKey, false);
  }

  /**
   * Gets the HtmlSelect attribute of the ReportTypeList object
   * 
   * @param selectName
   *          Description of Parameter
   * @param defaultKey
   *          Description of Parameter
   * @param disabled
   *          Description of the Parameter
   * @return The HtmlSelect value
   * @since 1.1
   */
  public String getHtmlSelect(String selectName, int defaultKey,
      boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setMultiple(multiple);
    thisSelect.setDisabled(disabled);
    thisSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;
    while (i.hasNext()) {
      ReportType thisElement = (ReportType) i.next();

      // Add the item to the list
      if (thisElement.getEnabled() == true) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getCode();
        }
      } else if (thisElement.getCode() == defaultKey) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getCode() == defaultKey) {
        keyFound = true;
      }

    }
    if (keyFound) {
      return thisSelect.getHtml(selectName, defaultKey);
    } else {
      return thisSelect.getHtml(selectName, lookupDefault);
    }
  }

  /**
   * Gets the htmlSelectObj attribute of the ReportTypeList object
   * 
   * @param defaultKey
   *          Description of the Parameter
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(int defaultKey) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setMultiple(multiple);
    thisSelect.setJsEvent(jsEvent);

    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;

    while (i.hasNext()) {
      ReportType thisElement = (ReportType) i.next();

      // Add the item
      if (thisElement.getEnabled() == true) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getCode();
        }
      } else if (thisElement.getCode() == defaultKey) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getCode() == defaultKey) {
        keyFound = true;
      }

    }
    if (keyFound) {
      thisSelect.setDefaultKey(defaultKey);
    } else {
      thisSelect.setDefaultKey(lookupDefault);
    }
    return thisSelect;
  }

  /**
   * Gets the htmlSelect attribute of the ReportTypeList object
   * 
   * @param selectName
   *          Description of the Parameter
   * @param defaultValue
   *          Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String defaultValue) {
    return getHtmlSelect(selectName, defaultValue, false);
  }

  /**
   * Gets the HtmlSelect attribute of the ReportTypeList object
   * 
   * @param selectName
   *          Description of Parameter
   * @param defaultValue
   *          Description of Parameter
   * @param disabled
   *          Description of the Parameter
   * @return The HtmlSelect value
   * @since 1.1
   */
  public String getHtmlSelect(String selectName, String defaultValue,
      boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setDisabled(disabled);
    Iterator i = this.iterator();
    boolean keyFound = false;
    String lookupDefault = null;
    while (i.hasNext()) {
      ReportType thisElement = (ReportType) i.next();

      // Add the item
      if (thisElement.getEnabled()) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      } else if (thisElement.getDescription().equals(defaultValue)) {
        keyFound = true;
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getDescription().equals(defaultValue)) {
        keyFound = true;
      }
      if (thisElement.getDefaultItem()) {
        lookupDefault = thisElement.getDescription();

      }
    }
    return thisSelect.getHtml(selectName, defaultValue);
  }

  /**
   * Gets the htmlSelect attribute of the ReportTypeList object
   * 
   * @param selectName
   *          Description of Parameter
   * @param ms
   *          Description of Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, ReportTypeList ms) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setMultiple(multiple);
    Iterator i = this.iterator();
    boolean keyFound = false;
    String lookupDefault = null;
    while (i.hasNext()) {
      ReportType thisElement = (ReportType) i.next();
      // Add the item
      if (thisElement.getEnabled()) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getDefaultItem()) {
        lookupDefault = thisElement.getDescription();
      }

    }
    return thisSelect.getHtml(selectName);
  }

  /**
   *  Gets the idFromValue attribute of the ReportTypeList object
   *
   * @param  value  Description of the Parameter
   * @return        The idFromValue value
   */
  public int getIdFromValue(String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ReportType thisElement = (ReportType) i.next();
      if (value.equals(thisElement.getDescription())) {
        return thisElement.getCode();
      }
    }
    return -1;
  }
  /**
   *  Gets the idFromConstant attribute of the ReportTypeList object
   *
   * @param  value  Description of the Parameter
   * @return        The idFromConstant value
   */
  public int getIdFromConstant(int value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ReportType thisElement = (ReportType) i.next();
      if (value == thisElement.getConstant()) {
        return thisElement.getCode();
      }
    }
    return -1;
  }  

  /**
   *  Gets the selectedValue attribute of the ReportTypeList object
   *
   * @param  selectedId  Description of Parameter
   * @return             The selectedValue value
   */
  public String getValueFromId(String selectedId) {
    return getSelectedValue(selectedId);
  }


  /**
   *  Gets the tableName attribute of the ReportTypeList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }

  /**
   *  Gets the uniqueField attribute of the ReportTypeList object
   *
   * @return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   *  Sets the lastAnchor attribute of the ReportTypeList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the ReportTypeList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }

  /**
   *  Sets the nextAnchor attribute of the ReportTypeList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the ReportTypeList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the ReportTypeList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the ReportTypeList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }

}
