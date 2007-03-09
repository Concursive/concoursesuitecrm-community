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
import org.aspcfs.modules.products.base.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @created    February 10, 2006
 * @version    $Id: Exp $
 */
public class IceletProperty extends GenericBean {

  public final static String HTML_EDITOR = "htmleditor";
  public final static String TEXT_AREA = "textarea";
  public final static String TEXT = "text";
  public final static String CHECKBOX = "checkbox";
  public final static String INTEGER = "integer";
  public final static String PORTFOLIO_CATEGORY = "portfolioCategory";
  public final static String PRODUCT_CATEGORY = "productCategory";
  public final static String LEAD_SOURCE = "leadsource";
  public final static String PORTAL_ROLELIST = "portal-rolelist";

  private int id = -1;
  private int typeConstant = -1;
  private String value = null;
  private int rowColumnId = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;

  //helper attributes to store values specified in icelet_<LOCALE_NAME>.xml for this property
  private String description = null;
  private String label = null;
  private String type = null;
  private String defaultValue = null;
  private String additionalText = null;
  private boolean autoAdd = false;
  private String valueString = null;
  private boolean buildValueString = false;


  /**
   *  Constructor for the icelet object
   */
  public IceletProperty() { }


  /**
   *  Constructor for the icelet object
   *
   * @param  db                   Description of the Parameter
   * @param  tmpIceletPropertyId  Description of the Parameter
   * @exception  SQLException     Description of the Exception
   * @throws  SQLException        Description of the Exception
   */
  public IceletProperty(Connection db, int tmpIceletPropertyId) throws SQLException {
    queryRecord(db, tmpIceletPropertyId);
  }


  /**
   *  Constructor for the icelet object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public IceletProperty(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the IceletProperty object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the IceletProperty object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeConstant attribute of the IceletProperty object
   *
   * @param  tmp  The new typeConstant value
   */
  public void setTypeConstant(int tmp) {
    this.typeConstant = tmp;
  }


  /**
   *  Sets the typeConstant attribute of the IceletProperty object
   *
   * @param  tmp  The new typeConstant value
   */
  public void setTypeConstant(String tmp) {
    this.typeConstant = Integer.parseInt(tmp);
  }


  /**
   *  Sets the iceletValue attribute of the IceletProperty object
   *
   * @param  tmp  The new iceletValue value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the iceletRowColumnId attribute of the IceletProperty object
   *
   * @param  tmp  The new iceletRowColumnId value
   */
  public void setRowColumnId(int tmp) {
    this.rowColumnId = tmp;
  }


  /**
   *  Sets the rowColumnId attribute of the IceletProperty object
   *
   * @param  tmp  The new rowColumnId value
   */
  public void setRowColumnId(String tmp) {
    this.rowColumnId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the IceletProperty object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the IceletProperty object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the IceletProperty object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the IceletProperty object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the IceletProperty object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the IceletProperty object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the IceletProperty object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the IceletProperty object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the description attribute of the IceletProperty object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the label attribute of the IceletProperty object
   *
   * @param  tmp  The new label value
   */
  public void setLabel(String tmp) {
    this.label = tmp;
  }


  /**
   *  Sets the type attribute of the IceletProperty object
   *
   * @param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the defaultValue attribute of the IceletProperty object
   *
   * @param  tmp  The new defaultValue value
   */
  public void setDefaultValue(String tmp) {
    this.defaultValue = tmp;
  }


  /**
   *  Sets the additionalText attribute of the IceletProperty object
   *
   * @param  tmp  The new additionalText value
   */
  public void setAdditionalText(String tmp) {
    this.additionalText = tmp;
  }


  /**
   *  Sets the autoAdd attribute of the IceletProperty object
   *
   * @param  tmp  The new autoAdd value
   */
  public void setAutoAdd(boolean tmp) {
    this.autoAdd = tmp;
  }


  /**
   *  Sets the autoAdd attribute of the IceletProperty object
   *
   * @param  tmp  The new autoAdd value
   */
  public void setAutoAdd(String tmp) {
    this.autoAdd = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the IceletProperty object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the iceletPropertyTypeConstant attribute of the IceletProperty object
   *
   * @return    The iceletPropertyTypeConstant value
   */
  public int getTypeConstant() {
    return typeConstant;
  }


  /**
   *  Gets the iceletValue attribute of the IceletProperty object
   *
   * @return    The iceletValue value
   */
  public String getValue() {
    return value;
  }


  /**
   *  Gets the iceletRowColumnId attribute of the IceletProperty object
   *
   * @return    The iceletRowColumnId value
   */
  public int getRowColumnId() {
    return rowColumnId;
  }


  /**
   *  Gets the enteredBy attribute of the IceletProperty object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the IceletProperty object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the IceletProperty object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the IceletProperty object
   *
   * @return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the description attribute of the IceletProperty object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the label attribute of the IceletProperty object
   *
   * @return    The label value
   */
  public String getLabel() {
    return label;
  }


  /**
   *  Gets the type attribute of the IceletProperty object
   *
   * @return    The type value
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the defaultValue attribute of the IceletProperty object
   *
   * @return    The defaultValue value
   */
  public String getDefaultValue() {
    return defaultValue;
  }


  /**
   *  Gets the additionalText attribute of the IceletProperty object
   *
   * @return    The additionalText value
   */
  public String getAdditionalText() {
    return additionalText;
  }


  /**
   *  Gets the autoAdd attribute of the IceletProperty object
   *
   * @return    The autoAdd value
   */
  public boolean getAutoAdd() {
    return autoAdd;
  }


  /**
   *  Gets the valueString attribute of the IceletProperty object
   *
   * @return    The valueString value
   */
  public String getValueString() {
    return valueString;
  }


  /**
   *  Sets the valueString attribute of the IceletProperty object
   *
   * @param  tmp  The new valueString value
   */
  public void setValueString(String tmp) {
    this.valueString = tmp;
  }


  /**
   *  Description of the Method
   *
   * @param  db                   Description of the Parameter
   * @param  tmpIceletPropertyId  Description of the Parameter
   * @return                      Description of the Return Value
   * @throws  SQLException        Description of the Exception
   */
  public boolean queryRecord(Connection db, int tmpIceletPropertyId) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        " SELECT * " +
        " FROM web_icelet_property " +
        " WHERE property_id = ? ");
    pst.setInt(1, tmpIceletPropertyId);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (buildValueString) {
      buildValueString(db);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildValueString(Connection db) throws SQLException {
    if (this.getType() != null && this.getType().equals(PORTFOLIO_CATEGORY)) {
      if (this.getValue() != null && !"".equals(this.getValue()) && !"-1".equals(this.getValue())) {
        PortfolioCategory portfolioCategory = new PortfolioCategory(db, Integer.parseInt(this.getValue()));
        if (portfolioCategory != null && portfolioCategory.getName() != null) {
          this.setValueString(portfolioCategory.getName());
        }
      }
    }
    if (this.getType() != null && this.getType().equals(PRODUCT_CATEGORY)) {
      if (this.getValue() != null && !"".equals(this.getValue()) && !"-1".equals(this.getValue())) {
        ProductCategory productCategory = new ProductCategory(db, Integer.parseInt(this.getValue()));
        if (productCategory != null && productCategory.getName() != null) {
          this.setValueString(productCategory.getName());
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "web_icelet_property_property_id_seq");

    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO web_icelet_property " +
        "(" + (id > -1 ? "property_id, " : "") +
        "property_type_constant , " +
        "property_value , " +
        "row_column_id , " +
        "enteredby , " +
        "modifiedby ) " +
        "VALUES (" + (id > -1 ? "?," : "") + "?,?,?,?,?)");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, typeConstant);
    pst.setString(++i, value);
    DatabaseUtils.setInt(pst, ++i, rowColumnId);
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, modifiedBy);
    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "web_icelet_property_property_id_seq", id);
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean update(Connection db) throws SQLException {

    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {

    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }

      PreparedStatement pst = null;
      StringBuffer sql = new StringBuffer();
      sql.append(
          "DELETE FROM web_icelet_property " +
          "WHERE property_id = ? ");

      pst = db.prepareStatement(sql.toString());
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
   * @param  rs             Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("property_id");
    typeConstant = rs.getInt("property_type_constant");
    value = rs.getString("property_value");
    rowColumnId = rs.getInt("row_column_id");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
  }

}

