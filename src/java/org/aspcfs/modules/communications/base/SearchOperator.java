//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     Wesley S. Gillette
 *@created    November 1, 2001
 */
public class SearchOperator extends GenericBean {

	private int id = -1;
	private int dataTypeId = -1;
	private String dataType = "";
	private String operator = "";
	private String displayText = "";


	/**
	 *  Constructor for the SearchOperator object
	 *
	 *@since
	 */
	public SearchOperator() { }


	/**
	 *  Constructor for the SearchOperator object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public SearchOperator(ResultSet rs) throws SQLException {
		buildSearchOperator(rs);
	}


	/**
	 *  Sets the id attribute of the SearchOperator object
	 *
	 *@param  tmp  The new id value
	 *@since
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the dataTypeId attribute of the SearchOperator object
	 *
	 *@param  tmp  The new dataTypeId value
	 *@since
	 */
	public void setDataTypeId(int tmp) {
		this.dataTypeId = tmp;
	}


	/**
	 *  Sets the dataType attribute of the SearchOperator object
	 *
	 *@param  tmp  The new dataType value
	 *@since
	 */
	public void setDataType(String tmp) {
		this.dataType = tmp;
	}


	/**
	 *  Sets the operator attribute of the SearchOperator object
	 *
	 *@param  tmp  The new operator value
	 *@since
	 */
	public void setOperator(String tmp) {
		this.operator = tmp;
	}


	/**
	 *  Sets the displayText attribute of the SearchOperator object
	 *
	 *@param  tmp  The new displayText value
	 *@since
	 */
	public void setDisplayText(String tmp) {
		this.displayText = tmp;
	}


	/**
	 *  Gets the id attribute of the SearchOperator object
	 *
	 *@return    The id value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the dataTypeId attribute of the SearchOperator object
	 *
	 *@return    The dataTypeId value
	 *@since
	 */
	public int getDataTypeId() {
		return dataTypeId;
	}


	/**
	 *  Gets the dataType attribute of the SearchOperator object
	 *
	 *@return    The dataType value
	 *@since
	 */
	public String getDataType() {
		return dataType;
	}


	/**
	 *  Gets the operator attribute of the SearchOperator object
	 *
	 *@return    The operator value
	 *@since
	 */
	public String getOperator() {
		return operator;
	}


	/**
	 *  Gets the displayText attribute of the SearchOperator object
	 *
	 *@return    The displayText value
	 *@since
	 */
	public String getDisplayText() {
		return displayText;
	}


	/**
	 *  Populates this object from a result set
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	protected void buildSearchOperator(ResultSet rs) throws SQLException {
		this.setId(rs.getInt("id"));
		dataTypeId = rs.getInt("data_typeid");
		dataType = rs.getString("data_type");
		operator = rs.getString("operator");
		displayText = rs.getString("display_text");
	}

}

