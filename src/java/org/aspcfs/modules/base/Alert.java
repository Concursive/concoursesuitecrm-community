package org.aspcfs.modules.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;

/**
 *  Keeper of the alert info.
 *
 *@author     doug
 *@created    August 7, 2001
 */
public class Alert extends GenericBean {

	/**
	 *  Error message  or empty string.
	 */
	protected String 	errorMessage = "";

	/**
	 *  Description of the Alert.
	 */
	protected String 	m_description = "";

	/**
	 *  Status of the alert.
	 */
	protected String 	m_status = "";

	/**
	 *  Date of the alert.
	 */
	protected java.util.Date 	m_date = null;

	/**
	 *  Email address of the alert source??.
	 */
	protected String 	m_email = "";

	/**
	 *	Id of the associated record (tickets).
	 */
	protected String	m_id = null;

	/**
	 *	Color
	 */
	protected String	m_color = "white";


	/**
	 *  Default Constructor.
	 *	Creates a null alert record.
	 */
	public Alert () {
	}


	/**
	 *  Test Constructor.
	 *	Creates an alert record from the supplied parameters.
	 *  For testing.
	 *
	 *@param description	Description
	 *@param status			Status of the ?
	 *@param email			Resource to get more info.
	 */
	public Alert (String description, String status, String email) {

		m_description = description;
		m_status = status;
		m_date = new java.util.Date();
		m_email = email;
	}


	/**
	 *  Constructor.
	 *	Creates an alert record from the supplied parameters.
	 *  Includes date.
	 *
	 *@param description	Description
	 *@param status			Status of the ?
	 *@param email			Resource to get more info.
     *@param date			date obj
	 */
	public Alert (String description, String status, String email,java.util.Date date) {

		m_description = description;
		m_status = status;
		m_date = date;
		m_email = email;
	}


	/**
	 *  Constructor to create an alert from a ResultSet Object.
	 *	The result set's current record is used (so <code>rs.next()</code>
	 *	should have been called <i>at least once</i> before calling this
	 *	constructor.
	 *
	 *@param  rs  The result set, positioned at a valid record.
	 */
	public Alert (ResultSet rs) {
		try {
			m_description = rs.getString ("description");
			m_status = rs.getString ("status");
			m_date = rs.getDate ("date");
			m_email = rs.getString ("email");

		}
		catch (SQLException e) {
			errorMessage = e.toString();
		}
	}


	/**
	 *  Gets the ErrorMessage.
	 *
	 *@return    The ErrorMessage value
	 */
	public String getErrorMessage () {
		return errorMessage;
	}

	/**
	 *  Sets the description attribute and date.
	 * 	The date field is set to the current time.
	 *
	 *@param  value  Description
	 */
	public void setDescription (String value) {
		m_description = value;
		//m_date = new java.util.Date();
	}
	
	/**
	 *  Sets the status attribute.
	 *
	 *@param  value  Status
	 */
	public void setStatus (String value) {
		m_status = value;
	}
	
	/**
	 *  Sets the email attribute.
	 *
	 *@param  value  Email address
	 */
	public void setEmail (String value) {
		m_email = value;
	}
	
	/**
	 *  Sets the record id value.
	 *
	 *@param  value  id
	 */
	public void setId (String value) {
		m_id = value;
	}
	
	/**
	 *  Sets the status color attribute.
	 *
	 *@param  value  color name / number, etc.
	 */
	public void setColor (String value) {
		m_color = value;
	}
	
	/**
	 *  Sets the date attribute.
	 *
	 *@param  value  Date
	 */
	public void setDate (java.util.Date date) {
		m_date = date;
	}
	

	/**
	 *  Gets the description value.
	 *
	 *@return    String
	 */
	public String getDescription () {
		return m_description;
	}

	/**
	 *  Gets the status value.
	 *
	 *@return    String
	 */
	public String getStatus () {
		return m_status;
	}

	/**
	 *  Gets the date object from the alert.
	 *
	 *@return    Date
	 */
	public java.util.Date getDate () {
		return m_date;
	}


	/**
	 *  Gets the date as a formatted string.
	 *
	 *@return    String
	 */
	public String getShortDateString () {
		SimpleDateFormat formatter = new SimpleDateFormat ("MM/dd");
		return formatter.format (m_date);
	}

	/**
	 *  Gets the email value.
	 *
	 *@return    String
	 */
	public String getEmail () {
		return m_email;
	}

	/**
	 *  Gets the record id value if it exists, else null.
	 *
	 *@return    String
	 */
	public String getId () {
		return m_id;
	}

	/**
	 *  Gets the color, based on status ???.
	 *
	 *@return    String
	 */
	public String getColor () {
		return m_color;
	}



}

