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
 *@created    May 09, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class WebProductAccessLog extends GenericBean {

	private int productId = -1;
	private int siteLogId = -1;
	private java.sql.Timestamp entered = null;


	/**
	 *  Constructor for the WebProductAccessLog object
	 */
	public WebProductAccessLog() { }


	/**
	 *  Constructor for the WebProductAccessLog object
	 *
	 *@param  rs                Description of the Parameter
	 *@exception  SQLException  Description of the Exception
	 */
	public WebProductAccessLog(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Sets the productId attribute of the WebProductAccessLog object
	 *
	 *@param  tmp  The new productId value
	 */
	public void setProductId(int tmp) {
		this.productId = tmp;
	}


	/**
	 *  Sets the productId attribute of the WebProductAccessLog object
	 *
	 *@param  tmp  The new productId value
	 */
	public void setProductId(String tmp) {
		this.productId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the siteLogId attribute of the WebProductAccessLog object
	 *
	 *@param  tmp  The new siteLogId value
	 */
	public void setSiteLogId(int tmp) {
		this.siteLogId = tmp;
	}


	/**
	 *  Sets the siteLogId attribute of the WebProductAccessLog object
	 *
	 *@param  tmp  The new siteLogId value
	 */
	public void setSiteLogId(String tmp) {
		this.siteLogId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the entered attribute of the WebProductAccessLog object
	 *
	 *@param  tmp  The new entered value
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the entered attribute of the WebProductAccessLog object
	 *
	 *@param  tmp  The new entered value
	 */
	public void setEntered(String tmp) {
		this.entered = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 *  Gets the productId attribute of the WebProductAccessLog object
	 *
	 *@return    The productId value
	 */
	public int getProductId() {
		return productId;
	}


	/**
	 *  Gets the siteLogId attribute of the WebProductAccessLog object
	 *
	 *@return    The siteLogId value
	 */
	public int getSiteLogId() {
		return siteLogId;
	}


	/**
	 *  Gets the entered attribute of the WebProductAccessLog object
	 *
	 *@return    The entered value
	 */
	public java.sql.Timestamp getEntered() {
		return entered;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db             Description of the Parameter
	 *@return                Description of the Return Value
	 *@throws  SQLException  Description of the Exception
	 */
	public boolean insert(Connection db) throws SQLException {

		PreparedStatement pst = db.prepareStatement(
				"INSERT INTO web_product_access_log " +
				"(product_id , " +
				"site_log_id ) " +
				"VALUES (?,?)");
		int i = 0;
		DatabaseUtils.setInt(pst, ++i, productId);
		DatabaseUtils.setInt(pst, ++i, siteLogId);
		pst.execute();
		pst.close();

		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of the Parameter
	 *@return                   Description of the Return Value
	 *@exception  SQLException  Description of the Exception
	 */
	public static PreparedStatement prepareInsert(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement(
				"INSERT INTO web_product_access_log " +
				"(product_id , " +
				"site_log_id ) " +
				"VALUES (?,?)");
		return pst;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  pst               Description of the Parameter
	 *@return                   Description of the Return Value
	 *@exception  SQLException  Description of the Exception
	 */
	public boolean insertData(PreparedStatement pst) throws SQLException {
		int i = 0;
		DatabaseUtils.setInt(pst, ++i, productId);
		DatabaseUtils.setInt(pst, ++i, siteLogId);
		pst.execute();
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  pst               Description of the Parameter
	 *@return                   Description of the Return Value
	 *@exception  SQLException  Description of the Exception
	 */
	public static boolean closeInsert(PreparedStatement pst) throws SQLException {
		pst.close();
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  rs             Description of the Parameter
	 *@throws  SQLException  Description of the Exception
	 */
	public void buildRecord(ResultSet rs) throws SQLException {
		productId = rs.getInt("product_id");
		siteLogId = rs.getInt("site_log_id");
		entered = rs.getTimestamp("entered");
	}
}

