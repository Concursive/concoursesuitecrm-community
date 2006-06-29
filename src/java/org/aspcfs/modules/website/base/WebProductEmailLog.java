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
public class WebProductEmailLog extends GenericBean {

	private int productId = -1;
	private String emailsTo = null;
	private String fromName = null;
	private String comments = null;
	private int siteLogId = -1;
	private java.sql.Timestamp entered = null;


	/**
	 *  Constructor for the WebProductEmailLog object
	 */
	public WebProductEmailLog() { }


	/**
	 *  Constructor for the WebProductEmailLog object
	 *
	 *@param  rs                Description of the Parameter
	 *@exception  SQLException  Description of the Exception
	 */
	public WebProductEmailLog(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Sets the productId attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new productId value
	 */
	public void setProductId(int tmp) {
		this.productId = tmp;
	}


	/**
	 *  Sets the productId attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new productId value
	 */
	public void setProductId(String tmp) {
		this.productId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the emailsTo attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new emailsTo value
	 */
	public void setEmailsTo(String tmp) {
		this.emailsTo = tmp;
	}


	/**
	 *  Sets the fromName attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new fromName value
	 */
	public void setFromName(String tmp) {
		this.fromName = tmp;
	}


	/**
	 *  Sets the comments attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new comments value
	 */
	public void setComments(String tmp) {
		this.comments = tmp;
	}


	/**
	 *  Sets the siteLogId attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new siteLogId value
	 */
	public void setSiteLogId(int tmp) {
		this.siteLogId = tmp;
	}


	/**
	 *  Sets the siteLogId attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new siteLogId value
	 */
	public void setSiteLogId(String tmp) {
		this.siteLogId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the entered attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new entered value
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the entered attribute of the WebProductEmailLog object
	 *
	 *@param  tmp  The new entered value
	 */
	public void setEntered(String tmp) {
		this.entered = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 *  Gets the productId attribute of the WebProductEmailLog object
	 *
	 *@return    The productId value
	 */
	public int getProductId() {
		return productId;
	}


	/**
	 *  Gets the emailsTo attribute of the WebProductEmailLog object
	 *
	 *@return    The emailsTo value
	 */
	public String getEmailsTo() {
		return emailsTo;
	}


	/**
	 *  Gets the fromName attribute of the WebProductEmailLog object
	 *
	 *@return    The fromName value
	 */
	public String getFromName() {
		return fromName;
	}


	/**
	 *  Gets the comments attribute of the WebProductEmailLog object
	 *
	 *@return    The comments value
	 */
	public String getComments() {
		return comments;
	}


	/**
	 *  Gets the siteLogId attribute of the WebProductEmailLog object
	 *
	 *@return    The siteLogId value
	 */
	public int getSiteLogId() {
		return siteLogId;
	}


	/**
	 *  Gets the entered attribute of the WebProductEmailLog object
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
				"INSERT INTO web_product_email_log " +
				"(product_id , " +
				"emails_to , " +
				"from_name , " +
				"comments , " +
				"site_log_id ) " +
				"VALUES (?,?,?,?,?)");
		int i = 0;
		DatabaseUtils.setInt(pst, ++i, productId);
		pst.setString(++i, emailsTo);
		pst.setString(++i, fromName);
		pst.setString(++i, comments);
		DatabaseUtils.setInt(pst, ++i, siteLogId);
		pst.execute();
		pst.close();

		return true;
	}

	public static PreparedStatement prepareInsert(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement(
				"INSERT INTO web_product_email_log " +
				"(product_id , " +
				"emails_to , " +
				"from_name , " +
				"comments , " +
				"site_log_id ) " +
				"VALUES (?,?,?,?,?)");
		return pst;
	}

	public boolean insertData(PreparedStatement pst) throws SQLException {
		int i = 0;
		DatabaseUtils.setInt(pst, ++i, productId);
		pst.setString(++i, emailsTo);
		pst.setString(++i, fromName);
		pst.setString(++i, comments);
		DatabaseUtils.setInt(pst, ++i, siteLogId);
		pst.execute();
		return true;
	}

	public static void closeInsert(PreparedStatement pst) throws SQLException {
		pst.close();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  rs             Description of the Parameter
	 *@throws  SQLException  Description of the Exception
	 */
	public void buildRecord(ResultSet rs) throws SQLException {
		productId = DatabaseUtils.getInt(rs, "product_id");
		emailsTo = rs.getString("emails_to");
		fromName = rs.getString("from_name");
		comments = rs.getString("comments");
		siteLogId = DatabaseUtils.getInt(rs, "site_log_id");
		entered = rs.getTimestamp("entered");
	}
}

