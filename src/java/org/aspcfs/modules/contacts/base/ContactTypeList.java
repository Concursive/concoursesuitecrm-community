package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.webutils.LookupList;

/**
 *  Contains a list of contact types built from the database
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id: ContactTypeList.java,v 1.1 2001/08/29 18:00:17 mrajkowski Exp
 *      $
 */
public class ContactTypeList extends Vector {
	private boolean showEmployees = false;
	private String jsEvent = "";
	private int defaultKey = -1;
	private int size = 1;
	private boolean multiple = false;

	private final static int EMPLOYEE_TYPE = 1;


	/**
	 *  Constructor for the ContactTypeList object
	 *
	 *@since    1.1
	 */
	public ContactTypeList() { }


	/**
	 *  Constructor for the ContactTypeList object
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	public ContactTypeList(Connection db) throws SQLException {
		buildList(db);
	}


	/**
	 *  Sets the Size attribute of the ContactTypeList object
	 *
	 *@param  size  The new Size value
	 *@since
	 */
	public void setSize(int size) {
		this.size = size;
	}


	/**
	 *  Sets the Multiple attribute of the ContactTypeList object
	 *
	 *@param  multiple  The new Multiple value
	 *@since
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}


	/**
	 *  Sets the ShowEmployees attribute of the ContactTypeList object
	 *
	 *@param  tmp  The new ShowEmployees value
	 *@since       1.2
	 */
	public void setShowEmployees(boolean tmp) {
		this.showEmployees = tmp;
	}


	/**
	 *  Sets the JsEvent attribute of the ContactTypeList object
	 *
	 *@param  tmp  The new JsEvent value
	 *@since       1.3
	 */
	public void setJsEvent(String tmp) {
		this.jsEvent = tmp;
	}


	/**
	 *  Sets the DefaultKey attribute of the ContactTypeList object
	 *
	 *@param  tmp  The new DefaultKey value
	 *@since       1.3
	 */
	public void setDefaultKey(int tmp) {
		this.defaultKey = tmp;
	}


	/**
	 *  Sets the DefaultKey attribute of the ContactTypeList object
	 *
	 *@param  tmp  The new DefaultKey value
	 *@since       1.3
	 */
	public void setDefaultKey(String tmp) {
		this.defaultKey = Integer.parseInt(tmp);
	}


	/**
	 *  Gets the Size attribute of the ContactTypeList object
	 *
	 *@return    The Size value
	 *@since
	 */
	public int getSize() {
		return size;
	}


	/**
	 *  Gets the Multiple attribute of the ContactTypeList object
	 *
	 *@return    The Multiple value
	 *@since
	 */
	public boolean getMultiple() {
		return multiple;
	}


	/**
	 *  Gets the ShowEmployees attribute of the ContactTypeList object
	 *
	 *@return    The ShowEmployees value
	 *@since     1.2
	 */
	public boolean getShowEmployees() {
		return showEmployees;
	}


	/**
	 *  Gets the JsEvent attribute of the ContactTypeList object
	 *
	 *@return    The JsEvent value
	 *@since     1.3
	 */
	public String getJsEvent() {
		return jsEvent;
	}


	/**
	 *  Gets the DefaultKey attribute of the ContactTypeList object
	 *
	 *@return    The DefaultKey value
	 *@since     1.3
	 */
	public int getDefaultKey() {
		return defaultKey;
	}


	/**
	 *  Gets the HtmlSelect attribute of the ContactTypeList object
	 *
	 *@param  selectName  Description of Parameter
	 *@return             The HtmlSelect value
	 *@since              1.2
	 */
	public String getHtmlSelect(String selectName) {
		return getHtmlSelect(selectName, defaultKey);
	}


	/**
	 *  Gets the HtmlSelect attribute of the ContactTypeList object
	 *
	 *@param  selectName  Description of Parameter
	 *@param  defaultKey  Description of Parameter
	 *@return             The HtmlSelect value
	 *@since              1.1
	 */
	public String getHtmlSelect(String selectName, int defaultKey) {
		LookupList contactTypeSelect = new LookupList();
		contactTypeSelect = getLookupList(selectName, defaultKey);
		return contactTypeSelect.getHtmlSelect(selectName, defaultKey);
	}
	
	public LookupList getLookupList(String selectName, int defaultKey) {
		LookupList contactTypeSelect = new LookupList();
		contactTypeSelect.setJsEvent(jsEvent);
		contactTypeSelect.setSelectSize(this.getSize());
		contactTypeSelect.setMultiple(this.getMultiple());
		
		Iterator i = this.iterator();
		while (i.hasNext()) {
			ContactType thisContactType = (ContactType) i.next();
			
			if ( thisContactType.getEnabled() == true ) {
				contactTypeSelect.appendItem(thisContactType.getId(), thisContactType.getDescription());
			} else if (thisContactType.getId() == defaultKey ) {
				contactTypeSelect.appendItem(thisContactType.getId(), thisContactType.getDescription() + " (X)");
			}
		}
		
		return contactTypeSelect;
	}

	/**
	 *  Adds a feature to the Item attribute of the ContactTypeList object
	 *
	 *@param  key   The feature to be added to the Item attribute
	 *@param  name  The feature to be added to the Item attribute
	 *@since
	 */
	public void addItem(int key, String name) {
		ContactType thisContactType = new ContactType();
		thisContactType.setId(key);
		thisContactType.setDescription(name);
		this.add(0, thisContactType);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.2
	 */
	public void buildList(Connection db) throws SQLException {
		Statement st = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM lookup_contact_types ");
				//"WHERE enabled = true ");
		if (!showEmployees) {
			sql.append("WHERE code NOT IN (" + EMPLOYEE_TYPE + ") ");
			//sql.append("AND code NOT IN (" + EMPLOYEE_TYPE + ") ");
		}
		
		sql.append("ORDER BY level, description ");
		
		st = db.createStatement();
		rs = st.executeQuery(sql.toString());
		while (rs.next()) {
			ContactType thisContactType = new ContactType(rs);
			this.addElement(thisContactType);
		}
		rs.close();
		st.close();
	}

}

