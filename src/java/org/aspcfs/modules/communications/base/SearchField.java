//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import java.sql.*;

/**
 *  The SearchField represents information that is required for performing a
 *  search, and for building a form for allowing the user to build a search.
 *
 *@author     Wesley S. Gillette
 *@created    November 1, 2001
 *@version    $Id$
 */
public class SearchField {

	private int id = -1;
	private String fieldName = "";
	private String description = "";
	private boolean searchable = true;
	private int fieldTypeId = -1;
	private String tableName = "";
	private String objectClass = "";


	/**
	 *  Constructor for the SearchField object
	 *
	 *@since    1.1
	 */
	public SearchField() { }


	/**
	 *  Constructor for the SearchField object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	public SearchField(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Sets the id attribute of the SearchField object
	 *
	 *@param  tmp  The new id value
	 *@since       1.1
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the fieldName attribute of the SearchField object
	 *
	 *@param  tmp  The new fieldName value
	 *@since       1.1
	 */
	public void setFieldName(String tmp) {
		this.fieldName = tmp;
	}


	/**
	 *  Sets the description attribute of the SearchField object
	 *
	 *@param  tmp  The new description value
	 *@since       1.1
	 */
	public void setDescription(String tmp) {
		this.description = tmp;
	}

	/**
	 *  Sets the searchable attribute of the SearchField object
	 *
	 *@param  tmp  The new searchable value
	 *@since       1.1
	 */
	public void setSearchable(boolean tmp) {
		this.searchable = tmp;
	}


	/**
	 *  Sets the fieldTypeId attribute of the SearchField object
	 *
	 *@param  tmp  The new fieldTypeId value
	 *@since       1.1
	 */
	public void setFieldTypeId(int tmp) {
		this.fieldTypeId = tmp;
	}


	/**
	 *  Sets the tableName attribute of the SearchField object
	 *
	 *@param  tmp  The new tableName value
	 *@since       1.1
	 */
	public void setTableName(String tmp) {
		this.tableName = tmp;
	}


	/**
	 *  Sets the objectClass attribute of the SearchField object
	 *
	 *@param  tmp  The new objectClass value
	 *@since       1.1
	 */
	public void setObjectClass(String tmp) {
		this.objectClass = tmp;
	}

	/**
	 *  Gets the id attribute of the SearchField object
	 *
	 *@return    The id value
	 *@since     1.1
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the fieldName attribute of the SearchField object
	 *
	 *@return    The fieldName value
	 *@since     1.1
	 */
	public String getFieldName() {
		return fieldName;
	}


	/**
	 *  Gets the description attribute of the SearchField object
	 *
	 *@return    The description value
	 *@since     1.1
	 */
	public String getDescription() {
		return description;
	}


	/**
	 *  Gets the searchable attribute of the SearchField object
	 *
	 *@return    The searchable value
	 *@since     1.1
	 */
	public boolean getSearchable() {
		return searchable;
	}


	/**
	 *  Gets the fieldTypeId attribute of the SearchField object
	 *
	 *@return    The fieldTypeId value
	 *@since     1.1
	 */
	public int getFieldTypeId() {
		return fieldTypeId;
	}


	/**
	 *  Gets the tableName attribute of the SearchField object
	 *
	 *@return    The tableName value
	 *@since     1.1
	 */
	public String getTableName() {
		return tableName;
	}


	/**
	 *  Gets the objectClass attribute of the SearchField object
	 *
	 *@return    The objectClass value
	 *@since     1.1
	 */
	public String getObjectClass() {
		return objectClass;
	}


	/**
	 *  Populates this object from a result set
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	public void buildRecord(ResultSet rs) throws SQLException {
    //search_fields table
		this.setId(rs.getInt("id"));
		fieldName = rs.getString("field");
		description = rs.getString("description");
		searchable = rs.getBoolean("searchable");
		fieldTypeId = rs.getInt("field_typeid");
		tableName = rs.getString("table_name");
		objectClass = rs.getString("object_class");
	}
}

