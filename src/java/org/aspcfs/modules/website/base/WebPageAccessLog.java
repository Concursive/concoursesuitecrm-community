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
public class WebPageAccessLog extends GenericBean {

	private int pageId = -1;
	private int siteLogId = -1;
	private java.sql.Timestamp entered = null;


	/**
	 *  Constructor for the WebPageAccessLog object
	 */
	public WebPageAccessLog() { }


	/**
	 *  Constructor for the WebPageAccessLog object
	 *
	 *@param  rs                Description of the Parameter
	 *@exception  SQLException  Description of the Exception
	 */
	public WebPageAccessLog(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Sets the pageId attribute of the WebPageAccessLog object
	 *
	 *@param  tmp  The new pageId value
	 */
	public void setPageId(int tmp) {
		this.pageId = tmp;
	}


	/**
	 *  Sets the pageId attribute of the WebPageAccessLog object
	 *
	 *@param  tmp  The new pageId value
	 */
	public void setPageId(String tmp) {
		this.pageId = Integer.parseInt(tmp);
	}


public void setSiteLogId(int tmp) { this.siteLogId = tmp; }
public void setSiteLogId(String tmp) { this.siteLogId = Integer.parseInt(tmp); }


	/**
	 *  Sets the entered attribute of the WebPageAccessLog object
	 *
	 *@param  tmp  The new entered value
	 */
	public void setEntered(java.sql.Timestamp tmp) {
		this.entered = tmp;
	}


	/**
	 *  Sets the entered attribute of the WebPageAccessLog object
	 *
	 *@param  tmp  The new entered value
	 */
	public void setEntered(String tmp) {
		this.entered = DatabaseUtils.parseTimestamp(tmp);
	}


	/**
	 *  Gets the pageId attribute of the WebPageAccessLog object
	 *
	 *@return    The pageId value
	 */
	public int getPageId() {
		return pageId;
	}


public int getSiteLogId() { return siteLogId; }


	/**
	 *  Gets the entered attribute of the WebPageAccessLog object
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
				"INSERT INTO web_page_access_log " +
				"(page_id , " +
				"site_log_id ) " +
				"VALUES (?,?)");
		int i = 0;
		DatabaseUtils.setInt(pst, ++i, pageId);
		DatabaseUtils.setInt(pst, ++i, siteLogId);
		pst.execute();
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
		pageId = rs.getInt("page_id");
		siteLogId = rs.getInt("site_log_id");
		entered = rs.getTimestamp("entered");
	}
}

