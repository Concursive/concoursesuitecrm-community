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

	/**
	 *  Description of the Field
	 */
	protected String errorMessage = "";
	/**
	 *  Description of the Field
	 */
	protected int rec_id = -1;
	/**
	 *  Description of the Field
	 */
	protected int org_id = -1;
	/**
	 *  Description of the Field
	 */
	protected String base = "";
	/**
	 *  Description of the Field
	 */
	protected String headline = "";
	/**
	 *  Description of the Field
	 */
	protected String dateEntered = "";
	/**
	 *  Description of the Field
	 */
	protected String dateCreated = "";
	
	/**
	 *  Description of the Field
	 */
	protected String url = "";

	/**
	 *  Description of the Field
	 */
	protected SimpleDateFormat shortDateFormat = new SimpleDateFormat("M/d/yyyy");
	/**
	 *  Description of the Field
	 */
	protected SimpleDateFormat shortTimeFormat = new SimpleDateFormat("h:mm a");
	/**
	 *  Description of the Field
	 */
	protected SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
	/**
	 *  Description of the Field
	 */
	protected SimpleDateFormat longDateTimeFormat = new SimpleDateFormat("MMMMM d, yyyy hh:mm a");
	/**
	 *  Description of the Field
	 */
	protected SimpleDateFormat longDateFormat = new SimpleDateFormat("MMMMM d, yyyy");
	/**
	 *  Description of the Field
	 */
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
			rec_id = rs.getInt("rec_id");
			org_id = rs.getInt("org_id");
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


	/**
	 *  Sets the Rec_id attribute of the NewsArticle object
	 *
	 *@param  tmp  The new Rec_id value
	 */
	public void setRec_id(int tmp) {
		this.rec_id = tmp;
	}


	/**
	 *  Sets the Org_id attribute of the NewsArticle object
	 *
	 *@param  tmp  The new Org_id value
	 */
	public void setOrg_id(int tmp) {
		this.org_id = tmp;
	}
	
	/**
	 *  Sets the Org_id attribute of the NewsArticle object
	 *
	 *@param  tmp  The new Org_id value
	 */
	 
	public void setUrl(String tmp) { this.url = tmp; }

	/**
	 *  Sets the Base attribute of the NewsArticle object
	 *
	 *@param  tmp  The new Base value
	 */
	public void setBase(String tmp) {
		this.base = tmp;
	}


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
	 *  Gets the Rec_id attribute of the NewsArticle object
	 *
	 *@return    The Rec_id value
	 */
	public int getRec_id() {
		return rec_id;
	}


	/**
	 *  Gets the Org_id attribute of the NewsArticle object
	 *
	 *@return    The Org_id value
	 */
	public int getOrg_id() {
		return org_id;
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

