//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;

/**
 *  Contains a list of organizations... currently used to build the list from
 *  the database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    August 30, 2001
 *@version    $Id: OrganizationList.java,v 1.2 2001/08/31 17:33:32 mrajkowski
 *      Exp $
 */
public class OrganizationList extends Vector {

	private PagedListInfo pagedListInfo = null;
	private Boolean minerOnly = null;
	private int enteredBy = -1;
	private String name = null;
	private int ownerId = -1;
	private String HtmlJsEvent = "";
	private boolean showMyCompany = false;
	private String ownerIdRange = null;
	private boolean hasAlertDate = false;


	/**
	 *  Constructor for the OrganizationList object, creates an empty list. After
	 *  setting parameters, call the build method.
	 *
	 *@since    1.1
	 */
	public OrganizationList() { }


	/**
	 *  Sets the PagedListInfo attribute of the OrganizationList object. <p>
	 *
	 *  The query results will be constrained to the PagedListInfo parameters.
	 *
	 *@param  tmp  The new PagedListInfo value
	 *@since       1.1
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}


	/**
	 *  Sets the MinerOnly attribute of the OrganizationList object to limit the
	 *  results to miner only, or non-miner only.
	 *
	 *@param  tmp  The new MinerOnly value
	 *@since       1.1
	 */
	public void setMinerOnly(boolean tmp) {
		this.minerOnly = new Boolean(tmp);
	}


	/**
	 *  Sets the ShowMyCompany attribute of the OrganizationList object
	 *
	 *@param  showMyCompany  The new ShowMyCompany value
	 *@since
	 */
	public void setShowMyCompany(boolean showMyCompany) {
		this.showMyCompany = showMyCompany;
	}

	public boolean getHasAlertDate() {
		return hasAlertDate;
	}
	public void setHasAlertDate(boolean hasAlertDate) {
		this.hasAlertDate = hasAlertDate;
	}

	/**
	 *  Sets the HtmlJsEvent attribute of the OrganizationList object
	 *
	 *@param  HtmlJsEvent  The new HtmlJsEvent value
	 *@since
	 */
	public void setHtmlJsEvent(String HtmlJsEvent) {
		this.HtmlJsEvent = HtmlJsEvent;
	}


	/**
	 *  Sets the EnteredBy attribute of the OrganizationList object to limit
	 *  results to those records entered by that user.
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since       1.1
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}

	public String getOwnerIdRange() { return ownerIdRange; }
	public void setOwnerIdRange(String tmp) { this.ownerIdRange = tmp; }

	/**
	 *  Sets the Name attribute of the OrganizationList object to limit results to
	 *  those records matching the name. Use a % in the name for wildcard matching.
	 *
	 *@param  tmp  The new Name value
	 *@since       1.1
	 */
	public void setName(String tmp) {
		this.name = tmp;
	}


	/**
	 *  Sets the OwnerId attribute of the OrganizationList object
	 *
	 *@param  ownerId  The new OwnerId value
	 *@since
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}


	/**
	 *  Gets the ShowMyCompany attribute of the OrganizationList object
	 *
	 *@return    The ShowMyCompany value
	 *@since
	 */
	public boolean getShowMyCompany() {
		return showMyCompany;
	}


	/**
	 *  Gets the HtmlJsEvent attribute of the OrganizationList object
	 *
	 *@return    The HtmlJsEvent value
	 *@since
	 */
	public String getHtmlJsEvent() {
		return HtmlJsEvent;
	}


	/**
	 *  Gets the OwnerId attribute of the OrganizationList object
	 *
	 *@return    The OwnerId value
	 *@since
	 */
	public int getOwnerId() {
		return ownerId;
	}


	/**
	 *  Gets the HtmlSelect attribute of the ContactList object
	 *
	 *@param  selectName  Description of Parameter
	 *@return             The HtmlSelect value
	 *@since              1.8
	 */
	public String getHtmlSelect(String selectName) {
		return getHtmlSelect(selectName, -1);
	}


	/**
	 *  Gets the HtmlSelect attribute of the ContactList object
	 *
	 *@param  selectName  Description of Parameter
	 *@param  defaultKey  Description of Parameter
	 *@return             The HtmlSelect value
	 *@since              1.8
	 */
	public String getHtmlSelect(String selectName, int defaultKey) {
		HtmlSelect orgListSelect = new HtmlSelect();

		Iterator i = this.iterator();
		while (i.hasNext()) {
			Organization thisOrg = (Organization) i.next();
			orgListSelect.addItem(
					thisOrg.getOrgId(),
					thisOrg.getName()
					);
		}

		if (!(this.getHtmlJsEvent().equals(""))) {
			orgListSelect.setJsEvent(this.getHtmlJsEvent());
		}

		return orgListSelect.getHtml(selectName, defaultKey);
	}


	/**
	 *  Gets the HtmlSelectDefaultNone attribute of the OrganizationList object
	 *
	 *@param  selectName  Description of Parameter
	 *@return             The HtmlSelectDefaultNone value
	 *@since
	 */
	public String getHtmlSelectDefaultNone(String selectName) {
		HtmlSelect orgListSelect = new HtmlSelect();
		orgListSelect.addItem(-1, "-- None --");

		Iterator i = this.iterator();
		while (i.hasNext()) {
			Organization thisOrg = (Organization) i.next();
			orgListSelect.addItem(
					thisOrg.getOrgId(),
					thisOrg.getName()
					);
		}

		if (!(this.getHtmlJsEvent().equals(""))) {
			orgListSelect.setJsEvent(this.getHtmlJsEvent());
		}

		return orgListSelect.getHtml(selectName);
	}


	/**
	 *  Queries the database, using any of the filters, to retrieve a list of
	 *  organizations. The organizations are appended, so build can be run any
	 *  number of times to generate a larger list for a report.
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
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
				"SELECT o.*, " +
				"ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
				"ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
				"ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, " +
				"i.name as industry_name " +
				"FROM organization o " +
				"LEFT JOIN contact ct_owner ON (o.owner = ct_owner.user_id) " +
				"LEFT JOIN contact ct_eb ON (o.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_mb ON (o.modifiedby = ct_mb.user_id) " +
				"LEFT JOIN industry_temp i ON (o.industry_temp_code = i.code) " +
				"WHERE o.org_id >= 0 ");

		//Need to build a base SQL statement for counting records
		sqlCount.append(
				"SELECT COUNT(*) AS recordcount " +
				"FROM organization o " +
				"WHERE o.org_id >= 0 ");

		createFilter(sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +
					sqlFilter.toString());
			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			pst.close();
			rs.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(sqlCount.toString() +
						sqlFilter.toString() +
						"AND o.name < ? ");
				items = prepareFilter(pst);
				pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
				rs = pst.executeQuery();
				if (rs.next()) {
					int offsetCount = rs.getInt("recordcount");
					pagedListInfo.setCurrentOffset(offsetCount);
				}
				rs.close();
				pst.close();
			}

			//Determine column to sort by
			if (pagedListInfo.getColumnToSortBy() == null || pagedListInfo.getColumnToSortBy().equals("")) {
				pagedListInfo.setColumnToSortBy("o.name");
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
			sqlOrder.append("ORDER BY o.name ");
		}

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		rs = pst.executeQuery();
		while (rs.next()) {
			Organization thisOrganization = new Organization(rs);
			this.addElement(thisOrganization);
		}
		rs.close();
		pst.close();

		buildResources(db);
	}


	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since             1.2
	 */
	private void createFilter(StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (minerOnly != null) {
			sqlFilter.append("AND miner_only = ? ");
		}

		if (enteredBy > -1) {
			sqlFilter.append("AND o.enteredby = ? ");
		}

		if (name != null) {
			if (name.indexOf("%") >= 0) {
				sqlFilter.append("AND lower(o.name) like lower(?) ");
			}
			else {
				sqlFilter.append("AND lower(o.name) = lower(?) ");
			}
		}

		if (ownerId > -1) {
			sqlFilter.append("AND o.owner = ? ");
		}
		
		if (ownerIdRange != null) {
			sqlFilter.append("AND o.owner IN (" + ownerIdRange + ") ");
		}
		
		if (showMyCompany == false) {
			sqlFilter.append("AND o.org_id != 0 ");
		}
		
		if (hasAlertDate == true) {
			sqlFilter.append("AND o.alertdate is not null ");
		}

	}


	/**
	 *  Convenience method to get a list of phone numbers for each contact
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.5
	 */
	private void buildResources(Connection db) throws SQLException {
		Iterator i = this.iterator();
		while (i.hasNext()) {
			Organization thisOrg = (Organization) i.next();
			thisOrg.getPhoneNumberList().buildList(db);
			thisOrg.getAddressList().buildList(db);
			thisOrg.getEmailAddressList().buildList(db);
		}
	}


	/**
	 *  Sets the parameters for the preparedStatement - these items must correspond
	 *  with the createFilter statement
	 *
	 *@param  pst               Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since                    1.2
	 */
	private int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		if (minerOnly != null) {
			pst.setBoolean(++i, minerOnly.booleanValue());
		}

		if (enteredBy > -1) {
			pst.setInt(++i, enteredBy);
		}

		if (name != null) {
			pst.setString(++i, name);
		}

		if (ownerId > -1) {
			pst.setInt(++i, ownerId);
		}

		return i;
	}

}

