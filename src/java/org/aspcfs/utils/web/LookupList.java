//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.webutils;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.*;

/**
 *  A generic class that contains a list of LookupElement objects.
 *
 *@author     mrajkowski
 *@created    September 7, 2001
 *@version    $Id$
 */
public class LookupList extends HtmlSelect {

	private String jsEvent = null;
	private int selectSize = 1;
	private boolean multiple = false;


	/**
	 *  Constructor for the LookupList object. Generates an empty list, which is
	 *  not very useful.
	 *
	 *@since    1.1
	 */
	public LookupList() { }


	/**
	 *  Builds a list of elements based on the database connection and the table
	 *  name specified for the lookup. Only retrieves "enabled" items at this time.
	 *
	 *@param  db                Description of Parameter
	 *@param  table             Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public LookupList(Connection db, String table) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT * FROM " + table + " " +
				//"WHERE enabled = true " +
				"ORDER BY level, description ");

		st = db.createStatement();
		rs = st.executeQuery(sql.toString());
		while (rs.next()) {
			LookupElement thisElement = new LookupElement(rs);
			this.addElement(thisElement);
		}
		rs.close();
		st.close();
	}
	
	public LookupList(String[] vals, String[] names) throws SQLException {

		for(int i=0; i<vals.length; i++) {
			LookupElement thisElement = new LookupElement();
			thisElement.setDescription(names[i]);
			
			//as long as it is not a new entry
			
			if (!(vals[i].startsWith("*"))) {
				thisElement.setCode(Integer.parseInt(vals[i]));
			} 
			
			thisElement.setLevel(i);
			this.addElement(thisElement);
		}
	}


	/**
	 *  Constructor for the LookupList object
	 *
	 *@param  db                Description of Parameter
	 *@param  table             Description of Parameter
	 *@param  fieldId           Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public LookupList(Connection db, String table, int fieldId) throws SQLException {
    if (System.getProperty("DEBUG") != null) System.out.println("LookupList-> " + table + ": " + fieldId);
		Statement st = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT * FROM " + table + " " +
				"WHERE field_id = " + fieldId + " " +
				"AND CURRENT_TIMESTAMP > start_date " +
        "AND (CURRENT_TIMESTAMP < end_date OR end_date IS NULL) " +
				"ORDER BY level, description ");

		st = db.createStatement();
		rs = st.executeQuery(sql.toString());
		while (rs.next()) {
			LookupElement thisElement = new LookupElement(rs);
			this.addElement(thisElement);
		}
		rs.close();
		st.close();
	}
	
	/**
	 *  Sets the Multiple attribute of the LookupList object
	 *
	 *@param  multiple  The new Multiple value
	 *@since
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}


	/**
	 *  Sets the JsEvent attribute of the LookupList object
	 *
	 *@param  tmp  The new JsEvent value
	 *@since
	 */
	public void setJsEvent(String tmp) {
		this.jsEvent = tmp;
	}


	/**
	 *  Sets the SelectSize attribute of the LookupList object
	 *
	 *@param  tmp  The new SelectSize value
	 *@since
	 */
	public void setSelectSize(int tmp) {
		this.selectSize = tmp;
	}


	/**
	 *  Gets the Multiple attribute of the LookupList object
	 *
	 *@return    The Multiple value
	 *@since
	 */
	public boolean getMultiple() {
		return multiple;
	}


	/**
	 *  Gets the HtmlSelect attribute of the ContactEmailTypeList object
	 *
	 *@param  selectName  Description of Parameter
	 *@param  defaultKey  Description of Parameter
	 *@return             The HtmlSelect value
	 *@since              1.1
	 */
	public String getHtmlSelect(String selectName, int defaultKey) {
		HtmlSelect thisSelect = new HtmlSelect();
		thisSelect.setSelectSize(selectSize);
		thisSelect.setMultiple(multiple);
		thisSelect.setJsEvent(jsEvent);
		
		Iterator i = this.iterator();
		boolean keyFound = false;
		int lookupDefault = defaultKey;
		
		while (i.hasNext()) {
			LookupElement thisElement = (LookupElement) i.next();
			
			if ( thisElement.getEnabled() == true ) {
				thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getCode();
        }
			} else if (thisElement.getCode() == defaultKey) {
				thisSelect.addItem(thisElement.getCode(), thisElement.getDescription() + " (X)");
			}
      
      if (thisElement.getCode() == defaultKey) {
        keyFound = true;
      }
		}
		
    if (keyFound) {
      return thisSelect.getHtml(selectName, defaultKey);
    } else {
      return thisSelect.getHtml(selectName, lookupDefault);
    }
	}
	
	
	
	public void printVals() {
		Iterator i = this.iterator();
		while (i.hasNext()) {
			LookupElement thisElement = (LookupElement) i.next();
			System.out.println("Level: " + thisElement.getLevel() + ", Desc: " + thisElement.getDescription() + ", Code: " + thisElement.getCode());
		}
	}


	/**
	 *  Gets the HtmlSelect attribute of the ContactEmailTypeList object
	 *
	 *@param  selectName    Description of Parameter
	 *@param  defaultValue  Description of Parameter
	 *@return               The HtmlSelect value
	 *@since                1.1
	 */
	public String getHtmlSelect(String selectName, String defaultValue) {
		HtmlSelect thisSelect = new HtmlSelect();
		thisSelect.setSelectSize(selectSize);
		thisSelect.setJsEvent(jsEvent);
		Iterator i = this.iterator();
		
		boolean keyFound = false;
		String lookupDefault = null;
		
		while (i.hasNext()) {
			LookupElement thisElement = (LookupElement) i.next();
			
			if ( thisElement.getEnabled() == true ) {
				thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
			} else if (defaultValue.equals(thisElement.getDescription())) {
				keyFound = true;
				thisSelect.addItem(thisElement.getCode(), thisElement.getDescription() + " (X)");
			}
			
			if (defaultValue.equals(thisElement.getDescription())) {
				keyFound = true;
			}
			if (thisElement.getDefaultItem()) {
				lookupDefault = thisElement.getDescription();
			}
		}
		
		return thisSelect.getHtml(selectName, defaultValue);
	}
	
	public int getSelectedKey() {
		Iterator i = this.iterator();
		LookupElement keyFound = null;
		int x = 0;
		while (i.hasNext()) {
			++x;
			LookupElement thisElement = (LookupElement)i.next();
			if (x == 1) {
				keyFound = thisElement;
			}
			try {
				if (thisElement.getCode() == Integer.parseInt(defaultKey)) {
				return thisElement.getCode();
			}
			} catch (Exception e) {
			}
			if (thisElement.getDefaultItem()) {
				keyFound = thisElement;
			}
		}
		if (keyFound != null) {
			return keyFound.getCode();
		} else {
			return -1;
		}
	}


	/**
	 *  Gets the SelectedValue attribute of the LookupList object
	 *
	 *@param  selectedId  Description of Parameter
	 *@return             The SelectedValue value
	 *@since
	 */
	public String getSelectedValue(int selectedId) {
		Iterator i = this.iterator();
		LookupElement keyFound = null;
		while (i.hasNext()) {
			LookupElement thisElement = (LookupElement) i.next();
			if (thisElement.getCode() == selectedId) {
				return thisElement.getDescription();
			}
			if (thisElement.getDefaultItem()) {
				keyFound = thisElement;
			}
		}
		if (keyFound != null) {
			return keyFound.getDescription();
		}
		else {
			return "";
		}
	}

	
	  
  public String getSelectedValue(String selectedId) {
    return getSelectedValue(Integer.parseInt(selectedId));
  }
	/**
	 *  Adds a feature to the Item attribute of the LookupList object
	 *
	 *@param  tmp1  The feature to be added to the Item attribute
	 *@param  tmp2  The feature to be added to the Item attribute
	 *@since
	 */
	public void addItem(int tmp1, String tmp2) {
		LookupElement thisElement = new LookupElement();
		thisElement.setCode(tmp1);
		thisElement.setDescription(tmp2);
		if (this.size() > 0) {
			this.add(0, thisElement);
		}
		else {
			this.add(thisElement);
		}
	}

}

