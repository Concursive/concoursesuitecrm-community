package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    October 30, 2001
 */
public class GraphSummaryList extends Hashtable {

	int id = -1;
	boolean isValid = false;
	String lastFileName = "";

	Hashtable values = new Hashtable();


	/**
	 *  Constructor for the GraphSummaryList object
	 *
	 *@since
	 */
	public GraphSummaryList() {
		String[] valKeys = getRange(12);
		Float initialVal = new Float(0.0);

		for (int i = 0; i < 12; i++) {
			this.values.put(valKeys[i], initialVal);
		}
	}


	/**
	 *  Sets the LastFileName attribute of the GraphSummaryList object
	 *
	 *@param  lastFileName  The new LastFileName value
	 *@since
	 */
	public void setLastFileName(String lastFileName) {
		this.lastFileName = lastFileName;
	}


	/**
	 *  Sets the Id attribute of the GraphSummaryList object
	 *
	 *@param  id  The new Id value
	 *@since
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 *  Sets the Value attribute of the GraphSummaryList object
	 *
	 *@param  which  The new Value value
	 *@param  val    The new Value value
	 *@since
	 */
	public void setValue(String which, Float val) {
		if (!(this.values.containsKey(which))) {
			this.values.put(which, val);
		}
		else {
			addToValue(which, val);
		}
	}


	/**
	 *  Sets the IsValid attribute of the GraphSummaryList object
	 *
	 *@param  isValid  The new IsValid value
	 *@since
	 */
	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}


	/**
	 *  Gets the LastFileName attribute of the GraphSummaryList object
	 *
	 *@return    The LastFileName value
	 *@since
	 */
	public String getLastFileName() {
		return lastFileName;
	}


	/**
	 *  Gets the Id attribute of the GraphSummaryList object
	 *
	 *@return    The Id value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the Value attribute of the GraphSummaryList object
	 *
	 *@param  which  Description of Parameter
	 *@return        The Value value
	 *@since
	 */
	public Float getValue(String which) {
		return (Float) this.values.get(which);
	}


	/**
	 *  Gets the Range attribute of the GraphSummaryList object
	 *
	 *@param  size  Description of Parameter
	 *@return       The Range value
	 *@since
	 */
	public String[] getRange(int size) {

		String[] valKeys = new String[size];

		java.util.Date d = new java.util.Date();
		java.util.Calendar rightNow = java.util.Calendar.getInstance();
		rightNow.setTime(d);

		int day = rightNow.get(java.util.Calendar.DATE);
		int year = rightNow.get(java.util.Calendar.YEAR);
		int month = rightNow.get(java.util.Calendar.MONTH);

		for (int x = 0; x < size; x++) {
			valKeys[x] = ("" + year) + ("" + month);
			rightNow.add(java.util.Calendar.MONTH, +1);

			year = rightNow.get(java.util.Calendar.YEAR);
			month = rightNow.get(java.util.Calendar.MONTH);
		}

		return valKeys;
	}


	/**
	 *  Gets the IsValid attribute of the GraphSummaryList object
	 *
	 *@return    The IsValid value
	 *@since
	 */
	public boolean getIsValid() {
		return isValid;
	}


	/**
	 *  Adds a feature to the ToValue attribute of the GraphSummaryList object
	 *
	 *@param  which  The feature to be added to the ToValue attribute
	 *@param  val    The feature to be added to the ToValue attribute
	 *@since
	 */
	public void addToValue(String which, Float val) {
		Float tempValue = (Float) this.values.get(which);
		tempValue = new Float(tempValue.floatValue() + val.floatValue());

		this.values.put(which, tempValue);
	}

}

