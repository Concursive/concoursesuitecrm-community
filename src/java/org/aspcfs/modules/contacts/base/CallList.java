//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    January 8, 2002
 */
public class CallList extends Vector {

	private PagedListInfo pagedListInfo = null;
	private int contactId = -1;
	private int orgId = -1;
	private int oppId = -1;


	/**
	 *  Constructor for the CallList object
	 *
	 *@since
	 */
	public CallList() { }


	/**
	 *  Sets the PagedListInfo attribute of the CallList object
	 *
	 *@param  tmp  The new PagedListInfo value
	 *@since
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}


	/**
	 *  Sets the ContactId attribute of the CallList object
	 *
	 *@param  tmp  The new ContactId value
	 *@since
	 */
	public void setContactId(int tmp) {
		this.contactId = tmp;
	}


	/**
	 *  Sets the ContactId attribute of the CallList object
	 *
	 *@param  tmp  The new ContactId value
	 *@since
	 */
	public void setContactId(String tmp) {
		this.contactId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the OrgId attribute of the CallList object
	 *
	 *@param  tmp  The new OrgId value
	 *@since
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}


	/**
	 *  Sets the OppId attribute of the CallList object
	 *
	 *@param  oppId  The new OppId value
	 *@since
	 */
	public void setOppId(int oppId) {
		this.oppId = oppId;
	}


	/**
	 *  Sets the OppId attribute of the CallList object
	 *
	 *@param  oppId  The new OppId value
	 *@since
	 */
	public void setOppId(String oppId) {
		this.oppId = Integer.parseInt(oppId);
	}


	/**
	 *  Gets the PagedListInfo attribute of the CallList object
	 *
	 *@return    The PagedListInfo value
	 *@since
	 */
	public PagedListInfo getPagedListInfo() {
		return pagedListInfo;
	}


	/**
	 *  Gets the ContactId attribute of the CallList object
	 *
	 *@return    The ContactId value
	 *@since
	 */
	public int getContactId() {
		return contactId;
	}


	/**
	 *  Gets the OrgId attribute of the CallList object
	 *
	 *@return    The OrgId value
	 *@since
	 */
	public int getOrgId() {
		return orgId;
	}


	/**
	 *  Gets the OppId attribute of the CallList object
	 *
	 *@return    The OppId value
	 *@since
	 */
	public int getOppId() {
		return oppId;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public void buildList(Connection db) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for returning records
		sqlSelect.append(
				"SELECT c.*, t.*, " +
				"e.namefirst as efirst, e.namelast as elast, " +
				"m.namefirst as mfirst, m.namelast as mlast, " +
				"ct.namefirst as ctfirst, ct.namelast as ctlast " +
				"FROM call_log c " +
				"LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
				"  LEFT JOIN lookup_call_types t ON (c.call_type_id = t.code), " +
				"contact e LEFT JOIN access a1 ON (e.contact_id = a1.contact_id), " +
				"contact m LEFT JOIN access a2 ON (m.contact_id = a2.contact_id) " +
				
				"WHERE c.enteredby = a1.user_id " +
				"AND c.modifiedby = a2.user_id ");

		//Need to build a base SQL statement for counting records
		sqlCount.append(
				"SELECT COUNT(*) as recordcount " +
				"FROM call_log c, " +
				"contact e LEFT JOIN access a1 ON (e.contact_id = a1.contact_id), " +
				"contact m LEFT JOIN access a2 ON (m.contact_id = a2.contact_id), " +
				"lookup_call_types t " +
				"WHERE c.call_type_id = t.code " +
				"AND c.enteredby = a1.user_id " +
				"AND c.modifiedby = a2.user_id ");

		createFilter(sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			pst.close();
			rs.close();

			//Determine column to sort by
			if (pagedListInfo.getColumnToSortBy() == null || pagedListInfo.getColumnToSortBy().equals("")) {
				pagedListInfo.setColumnToSortBy("entered");
				pagedListInfo.setSortOrder("desc");
			}
			sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + " ");
			if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
				sqlOrder.append(pagedListInfo.getSortOrder() + " ");
			}

			//Determine items per page
			if (pagedListInfo.getItemsPerPage() > 0) {
				sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
			}

			sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
		}
		else {
			sqlOrder.append("ORDER BY entered DESC ");
		}

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		rs = pst.executeQuery();
		while (rs.next()) {
			Call thisCall = new Call(rs);
			this.addElement(thisCall);
		}
		rs.close();
		pst.close();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since
	 */
	private void createFilter(StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (contactId != -1) {
			sqlFilter.append("AND c.contact_id = ? ");
		}
		
		if (oppId != -1) {
			sqlFilter.append("AND c.opp_id = ? ");
		}

		if (orgId != -1) {
			sqlFilter.append("AND c.org_id = ? ");
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  pst               Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	private int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		if (contactId != -1) {
			pst.setInt(++i, contactId);
		}
		
		if (oppId != -1) {
			pst.setInt(++i, oppId);
		}

		if (orgId != -1) {
			pst.setInt(++i, orgId);
		}
		return i;
	}
}

