/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.servicecontracts.base;

import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    December 31, 2003
 *@version    $Id: ServiceContract.java,v 1.1.2.2 2004/01/08 18:50:49 kbhoopal
 *      Exp $
 */
public class ServiceContractProduct extends GenericBean {

  private int id = -1;
  private int contractId = -1;
  private int productId = -1;
  private String productSku = null;
  private String productName = null;


  /**
   *  Constructor for the ServiceContractProduct object
   */
  public ServiceContractProduct() { }


  /**
   *  Constructor for the ServiceContractProduct object
   *
   *@param  db                Description of the Parameter
   *@param  tmpId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ServiceContractProduct(Connection db, int tmpId) throws SQLException {
    this.id = tmpId;
    queryRecord(db);
  }


  /**
   *  Constructor for the ServiceContractProduct object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ServiceContractProduct(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contractId attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(int tmp) {
    this.contractId = tmp;
  }


  /**
   *  Sets the contractId attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new contractId value
   */
  public void setContractId(String tmp) {
    this.contractId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productId attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the productSku attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new productSku value
   */
  public void setProductSku(String tmp) {
    this.productSku = tmp;
  }


  /**
   *  Sets the productName attribute of the ServiceContractProduct object
   *
   *@param  tmp  The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   *  Gets the id attribute of the ServiceContractProduct object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the contractId attribute of the ServiceContractProduct object
   *
   *@return    The contractId value
   */
  public int getContractId() {
    return contractId;
  }


  /**
   *  Gets the productId attribute of the ServiceContractProduct object
   *
   *@return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Gets the productSku attribute of the ServiceContractProduct object
   *
   *@return    The productSku value
   */
  public String getProductSku() {
    return productSku;
  }


  /**
   *  Gets the productName attribute of the ServiceContractProduct object
   *
   *@return    The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "DELETE  " +
        "FROM service_contract_products " +
        "WHERE id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);
    pst.execute();
    pst.close();

  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " SELECT scp.* , " +
        " pc.sku AS productsku , " +
        " pc.product_name AS productname " +
        " FROM service_contract_products scp " +
        " LEFT JOIN product_catalog pc ON (pc.product_id = scp.link_product_id) " +
        " WHERE id = ? ");

    pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);
    rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }

    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid()) {
      return false;
    }
    int resultCount = -1;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO service_contract_products " +
        "(link_contract_id,  " +
        " link_product_id ) " +
        "VALUES (? , ? ) ");

    pst = db.prepareStatement(sql.toString());

    int i = 0;
    pst.setInt(++i, contractId);
    pst.setInt(++i, productId);

    pst.execute();
    id = DatabaseUtils.getCurrVal(db, "service_contract_products_id_seq");
    pst.close();

    return true;
  }


  /**
   *  Gets the valid attribute of the ServiceContractProduct object
   *
   *@return    The valid value
   */
  boolean isValid() {

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    contractId = rs.getInt("link_contract_id");
    productId = rs.getInt("link_product_id");
    productSku = rs.getString("productsku");
    productName = rs.getString("productname");
  }

}

