package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    August 3, 2001
 */
public class NewsArticle extends GenericBean {


	protected String errorMessage = "";
	protected int recId = -1;
	protected int orgId = -1;
	protected String base = "";
	protected String headline = "";
	
	protected String dateEntered = "";
	protected String dateCreated = "";
	
	protected String url = "";
	
	protected SimpleDateFormat shortDateFormat = new SimpleDateFormat("M/d/yyyy");
	protected SimpleDateFormat shortTimeFormat = new SimpleDateFormat("h:mm a");
	protected SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
	protected SimpleDateFormat longDateTimeFormat = new SimpleDateFormat("MMMMM d, yyyy hh:mm a");
	protected SimpleDateFormat longDateFormat = new SimpleDateFormat("MMMMM d, yyyy");
	protected SimpleDateFormat longTimeFormat = new SimpleDateFormat("hh:mm a");


	/**
	 *  Constructor for the NewsArticle object
	 */
	public NewsArticle() {
	}


	/**
	 *  Constructor for the NewsArticle object
	 *
	 *@param  rs  Description of Parameter
	 */
	public NewsArticle(ResultSet rs) {
		try {
			recId = rs.getInt("rec_id");
			orgId = rs.getInt("org_id");
			base = rs.getString("base");
			headline = rs.getString("headline");
			dateEntered = rs.getString("dateentered");
			url = rs.getString("url");

			java.sql.Timestamp tmpDateCreated = rs.getTimestamp("created");
			if (tmpDateCreated != null) {
				dateEntered = shortDateTimeFormat.format(tmpDateCreated);
			}
			else {
				dateEntered = "";
			}

		}
		catch (SQLException e) {
			errorMessage = e.toString();
		}
	}


	/**
	 *  Sets the ErrorMessage attribute of the NewsArticle object
	 *
	 *@param  tmp  The new ErrorMessage value
	 */
	public void setErrorMessage(String tmp) {
		this.errorMessage = tmp;
	}
	 
	public void setUrl(String tmp) { this.url = tmp; }

	/**
	 *  Sets the Base attribute of the NewsArticle object
	 *
	 *@param  tmp  The new Base value
	 */
	public void setBase(String tmp) {
		this.base = tmp;
	}
	
	public int getRecId() { return recId; }
	public int getOrgId() { return orgId; }
	public void setRecId(int tmp) { this.recId = tmp; }
	public void setOrgId(int tmp) { this.orgId = tmp; }

	/**
	 *  Sets the Headline attribute of the NewsArticle object
	 *
	 *@param  tmp  The new Headline value
	 */
	public void setHeadline(String tmp) {
		this.headline = tmp;
	}


	/**
	 *  Sets the DateEntered attribute of the NewsArticle object
	 *
	 *@param  tmp  The new DateEntered value
	 */
	public void setDateEntered(String tmp) {
		this.dateEntered = tmp;
	}


	/**
	 *  Sets the DateCreated attribute of the NewsArticle object
	 *
	 *@param  tmp  The new DateCreated value
	 */
	public void setDateCreated(String tmp) {
		this.dateCreated = tmp;
	}


	/**
	 *  Gets the ErrorMessage attribute of the NewsArticle object
	 *
	 *@return    The ErrorMessage value
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 *  Gets the Base attribute of the NewsArticle object
	 *
	 *@return    The Base value
	 */
	public String getBase() {
		return base;
	}


	/**
	 *  Gets the Headline attribute of the NewsArticle object
	 *
	 *@return    The Headline value
	 */
	public String getHeadline() {
		return headline;
	}


	/**
	 *  Gets the DateEntered attribute of the NewsArticle object
	 *
	 *@return    The DateEntered value
	 */
	public String getDateEntered() {
		return dateEntered;
	}


	/**
	 *  Gets the DateCreated attribute of the NewsArticle object
	 *
	 *@return    The DateCreated value
	 */
	public String getDateCreated() {
		return dateCreated;
	}
	
		/**
	 *  Gets the DateCreated attribute of the NewsArticle object
	 *
	 *@return    The DateCreated value
	 */
	public String getUrl() { return url; }





}

