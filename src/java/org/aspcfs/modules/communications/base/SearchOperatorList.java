//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;

/**
 *  Description of the Class
 *
 *@author     Wesley S. Gillette
 *@created    November 1, 2001
 */
public class SearchOperatorList extends Vector {

	/**
	 *  Constructor for the SearchOperatorList object
	 *
	 *@since
	 */
	public SearchOperatorList() { }


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public void buildOperatorList(Connection db) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();
		sqlSelect.append(
				"SELECT id, data_typeid, data_type, operator, display_text " +
				"FROM field_types");
		pst = db.prepareStatement(sqlSelect.toString());
		rs = pst.executeQuery();
		while (rs.next()) {
			SearchOperator thisSearchOperator = new SearchOperator(rs);
			this.addElement(thisSearchOperator);
		}
		rs.close();
		pst.close();
	
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  typeID            Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public void buildOperatorList(Connection db, int typeID) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		StringBuffer sqlSelect = new StringBuffer();

		sqlSelect.append(
				"SELECT id, data_typeid, data_type, operator, display_text " +
				"FROM field_types " +
				"WHERE data_typeid = " + typeID);
		pst = db.prepareStatement(sqlSelect.toString());
		rs = pst.executeQuery();
		while (rs.next()) {
			SearchOperator thisSearchOperator = new SearchOperator(rs);
			this.addElement(thisSearchOperator);
		}
		rs.close();
		pst.close();
	
	}

}

