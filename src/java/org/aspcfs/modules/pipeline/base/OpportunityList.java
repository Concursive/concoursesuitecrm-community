//Copyright 2001 Dark Horse Ventures
// The createFilter method and the prepareFilter method need to have the same
// number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;

/**
 *  Contains a list of contacts... currently used to build the list from the
 *  database with any of the parameters to limit the results.
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id: OpportunityList.java,v 1.7 2001/10/03 21:26:46 mrajkowski Exp
 *      $
 */
public class OpportunityList extends Vector {

	private PagedListInfo pagedListInfo = null;
	private int orgId = -1;
	private int contactId = -1;
	private Vector ignoreTypeIdList = new Vector();
	private String description = null;
	private int enteredBy = -1;
	private boolean hasAlertDate = false;
	private java.sql.Date alertDate = null;
	private int owner = -1;
	private String ownerIdRange = null;
	private String units = null;


	/**
	 *  Constructor for the ContactList object
	 *
	 *@since    1.1
	 */
	public OpportunityList() { }


	/**
	 *  Sets the PagedListInfo attribute of the ContactList object
	 *
	 *@param  tmp  The new PagedListInfo value
	 *@since       1.1
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}


	/**
	 *  Sets the OrgId attribute of the ContactList object
	 *
	 *@param  tmp  The new OrgId value
	 *@since       1.1
	 */
	public void setOrgId(String tmp) {
		this.orgId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the TypeId attribute of the ContactList object
	 *
	 *@param  tmp  The new TypeId value
	 *@since       1.1
	 */
	public void setContactId(String tmp) {
		this.contactId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Description attribute of the OpportunityList object
	 *
	 *@param  description  The new Description value
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 *  Sets the EnteredBy attribute of the OpportunityList object
	 *
	 *@param  tmp  The new EnteredBy value
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}


	/**
	 *  Sets the OwnerIdRange attribute of the OpportunityList object
	 *
	 *@param  ownerIdRange  The new OwnerIdRange value
	 */
	public void setOwnerIdRange(String ownerIdRange) {
		this.ownerIdRange = ownerIdRange;
	}


	/**
	 *  Sets the HasAlertDate attribute of the OpportunityList object
	 *
	 *@param  tmp  The new HasAlertDate value
	 */
	public void setHasAlertDate(boolean tmp) {
		this.hasAlertDate = tmp;
	}


	/**
	 *  Sets the AlertDate attribute of the OpportunityList object
	 *
	 *@param  tmp  The new AlertDate value
	 */
	public void setAlertDate(java.sql.Date tmp) {
		this.alertDate = tmp;
	}



	public void setOwner(int tmp) {
		this.owner = tmp;
	}


	/**
	 *  Sets the Units attribute of the OpportunityList object
	 *
	 *@param  units  The new Units value
	 */
	public void setUnits(String units) {
		this.units = units;
	}


	/**
	 *  Gets the OwnerIdRange attribute of the OpportunityList object
	 *
	 *@return    The OwnerIdRange value
	 */
	public String getOwnerIdRange() {
		return ownerIdRange;
	}


	/**
	 *  Gets the ListSize attribute of the OpportunityList object
	 *
	 *@return    The ListSize value
	 */
	public int getListSize() {
		return this.size();
	}


	/**
	 *  Gets the Units attribute of the OpportunityList object
	 *
	 *@return    The Units value
	 */
	public String getUnits() {
		return units;
	}


	/**
	 *  Gets the EnteredBy attribute of the OpportunityList object
	 *
	 *@return    The EnteredBy value
	 */
	public int getEnteredBy() {
		return enteredBy;
	}


	/**
	 *  Gets the HasAlertDate attribute of the OpportunityList object
	 *
	 *@return    The HasAlertDate value
	 */
	public boolean getHasAlertDate() {
		return hasAlertDate;
	}


	/**
	 *  Gets the Description attribute of the OpportunityList object
	 *
	 *@return    The Description value
	 */
	public String getDescription() {
		return description;
	}


	/**
	 *  Builds a list of contacts based on several parameters. The parameters are
	 *  set after this object is constructed, then the buildList method is called
	 *  to generate the list.
	 *
	 *@param  db                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	public void buildList(Connection db) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		int items = -1;

		String whereClause = "";

		if (orgId == -1) {
			whereClause = " x.acctlink like '%' ";
		}
		else {
			whereClause = " x.acctlink = " + orgId + " ";
		}

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for returning records
		//sqlSelect.append("SELECT c.*, d.department as departmentname " +
		//    "FROM contact c, department d " +
		//    "WHERE c.department = d.department_id ");

		sqlSelect.append(
				"SELECT x.*, y.description as stagename, org.name as acct_name, ct.namelast as last_name, ct.namefirst as first_name, ct.company as ctcompany," +
				"ct_owner.namelast || ', ' || ct_owner.namefirst as o_name, ct_eb.namelast || ', ' || ct_eb.namefirst as eb_name, ct_mb.namelast || ', ' || ct_mb.namefirst as mb_name " +
				"FROM opportunity x " +
				"LEFT JOIN organization org ON (x.acctlink = org.org_id) " +
        
				"LEFT JOIN contact ct_owner ON (x.owner = ct_owner.user_id) " +
				"LEFT JOIN contact ct_eb ON (x.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_mb ON (x.modifiedby = ct_mb.user_id) " +
        
				"LEFT JOIN contact ct ON (x.contactlink = ct.contact_id), " +
        
				"lookup_stage y " +
				"WHERE x.stage = y.code AND " + whereClause);

		//Need to build a base SQL statement for counting records
		//sqlCount.append("SELECT COUNT(*) AS recordcount " +
		//    "FROM contact c, department d " +
		//    "WHERE c.department = d.department_id ");

		sqlCount.append("SELECT COUNT(*) AS recordcount " +
				"FROM opportunity x WHERE " +
				whereClause);

		createFilter(sqlFilter);
		//System.out.println(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());

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
						"AND description < ? ");
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
        pagedListInfo.setColumnToSortBy("description");
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

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());

		items = prepareFilter(pst);
		rs = pst.executeQuery();
		while (rs.next()) {
			Opportunity thisOpp = new Opportunity(rs);
			this.addElement(thisOpp);
		}
		rs.close();
		pst.close();

		buildResources(db);
	}


	/**
	 *  Adds a feature to the IgnoreTypeId attribute of the ContactList object
	 *
	 *@param  tmp  The feature to be added to the IgnoreTypeId attribute
	 *@since       1.2
	 */
	public void addIgnoreTypeId(String tmp) {
		ignoreTypeIdList.addElement(tmp);
	}


	/**
	 *  Adds a feature to the IgnoreTypeId attribute of the ContactList object
	 *
	 *@param  tmp  The feature to be added to the IgnoreTypeId attribute
	 *@since       1.2
	 */
	public void addIgnoreTypeId(int tmp) {
		ignoreTypeIdList.addElement("" + tmp);
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
			Opportunity thisOpp = (Opportunity) i.next();
			//thisOpp.getPhoneNumberList().buildList(db);
			//thisOpp.getAddressList().buildList(db);
			//thisOpp.getEmailAddressList().buildList(db);
		}
	}


	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since             1.3
	 */
	private void createFilter(StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (orgId != -1) {
			sqlFilter.append("AND acctlink = ? ");
		}

		if (contactId != -1) {
			sqlFilter.append("AND contactlink = ? ");
		}

		if (enteredBy != -1) {
			sqlFilter.append("AND x.enteredby = ? ");
		}

		if (hasAlertDate == true) {
			sqlFilter.append("AND alertdate is not null ");
		}

		if (ignoreTypeIdList.size() > 0) {
			Iterator iList = ignoreTypeIdList.iterator();
			sqlFilter.append("AND contactlink not in (");
			while (iList.hasNext()) {
				String placeHolder = (String) iList.next();
				sqlFilter.append("?");
				if (iList.hasNext()) {
					sqlFilter.append(",");
				}
			}
			sqlFilter.append(") ");
		}

		if (description != null) {
			if (description.indexOf("%") >= 0) {
				sqlFilter.append("AND lower(x.description) like lower(?) ");
			}
			else {
				sqlFilter.append("AND lower(x.description) = lower(?) ");
			}
		}

		if (alertDate != null) {
			sqlFilter.append("AND alertdate = ? ");
		}

		if (owner != -1) {
			sqlFilter.append("AND x.owner = ? ");
		}

		if (ownerIdRange != null) {
			sqlFilter.append("AND x.owner in (" + this.ownerIdRange + ") ");
		}

		if (units != null) {
			sqlFilter.append("AND units = ? ");
		}
	}


	/**
	 *  Sets the parameters for the preparedStatement - these items must correspond
	 *  with the createFilter statement
	 *
	 *@param  pst               Description os.gerameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since                    1.3
	 */
	private int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		if (orgId != -1) {
			pst.setInt(++i, orgId);
		}

		if (contactId != -1) {
			pst.setInt(++i, contactId);
		}

		if (enteredBy != -1) {
			pst.setInt(++i, enteredBy);
		}

		if (ignoreTypeIdList.size() > 0) {
			Iterator iList = ignoreTypeIdList.iterator();
			while (iList.hasNext()) {
				int thisType = Integer.parseInt((String) iList.next());
				pst.setInt(++i, thisType);
			}
		}

		if (description != null) {
			pst.setString(++i, description);
		}

		if (alertDate != null) {
			pst.setDate(++i, alertDate);
		}

		if (owner != -1) {
			pst.setInt(++i, owner);
		}

		if (units != null) {
			pst.setString(++i, units);
		}

		return i;
	}

}

