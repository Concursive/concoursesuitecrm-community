//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    December 5, 2001
 */
public class TicketList extends Vector {

	private PagedListInfo pagedListInfo = null;
	private int enteredBy = -1;
	private boolean onlyOpen = false;
	private boolean onlyClosed = false;
	private int id = -1;
	private int orgId = -1;
	private int department = -1;
	private int assignedTo = -1;
	private boolean unassignedToo = false;
	private int severity = 0;
	private int priority = 0;

	/**
	 *  Constructor for the TicketList object
	 *
	 *@since
	 */
	public TicketList() { }


	/**
	 *  Sets the Id attribute of the TicketList object
	 *
	 *@param  id  The new Id value
	 *@since
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 *  Sets the Id attribute of the TicketList object
	 *
	 *@param  id  The new Id value
	 *@since
	 */
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

public int getAssignedTo() {
	return assignedTo;
}
public void setAssignedTo(int assignedTo) {
	this.assignedTo = assignedTo;
}
public boolean getUnassignedToo() {
	return unassignedToo;
}
public void setUnassignedToo(boolean unassignedToo) {
	this.unassignedToo = unassignedToo;
}
public int getSeverity() { return severity; }
public int getPriority() { return priority; }
public void setSeverity(int tmp) { this.severity = tmp; }
public void setPriority(int tmp) { this.priority = tmp; }
public void setSeverity(String tmp) { this.severity = Integer.parseInt(tmp); }
public void setPriority(String tmp) { this.priority = Integer.parseInt(tmp); }

	/**
	 *  Sets the OrgId attribute of the TicketList object
	 *
	 *@param  orgId  The new OrgId value
	 *@since
	 */
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}


	/**
	 *  Sets the OrgId attribute of the TicketList object
	 *
	 *@param  orgId  The new OrgId value
	 *@since
	 */
	public void setOrgId(String orgId) {
		this.orgId = Integer.parseInt(orgId);
	}


	/**
	 *  Sets the PagedListInfo attribute of the TicketList object
	 *
	 *@param  tmp  The new PagedListInfo value
	 *@since
	 */
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}


	/**
	 *  Sets the EnteredBy attribute of the TicketList object
	 *
	 *@param  tmp  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(int tmp) {
		this.enteredBy = tmp;
	}
public boolean getOnlyClosed() {
	return onlyClosed;
}
public void setOnlyClosed(boolean onlyClosed) {
	this.onlyClosed = onlyClosed;
}


	/**
	 *  Sets the OnlyOpen attribute of the TicketList object
	 *
	 *@param  onlyOpen  The new OnlyOpen value
	 *@since
	 */
	public void setOnlyOpen(boolean onlyOpen) {
		this.onlyOpen = onlyOpen;
	}


	/**
	 *  Sets the Department attribute of the TicketList object
	 *
	 *@param  department  The new Department value
	 *@since
	 */
	public void setDepartment(int department) {
		this.department = department;
	}


	/**
	 *  Gets the OrgId attribute of the TicketList object
	 *
	 *@return    The OrgId value
	 *@since
	 */
	public int getOrgId() {
		return orgId;
	}


	/**
	 *  Gets the Id attribute of the TicketList object
	 *
	 *@return    The Id value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the OnlyOpen attribute of the TicketList object
	 *
	 *@return    The OnlyOpen value
	 *@since
	 */
	public boolean getOnlyOpen() {
		return onlyOpen;
	}


	/**
	 *  Gets the Department attribute of the TicketList object
	 *
	 *@return    The Department value
	 *@since
	 */
	public int getDepartment() {
		return department;
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
				"SELECT t.*, o.name as orgname, ld.description as dept, tp.description as ticpri, ts.description as ticsev, " +
        "tc.description as catname, ct_eb.namelast || ', ' || ct_eb.namefirst as eb_name, ct_mb.namelast || ', ' || ct_mb.namefirst as mb_name, ct_owner.namelast || ', ' || ct_owner.namefirst as owner_name,  " +
				"lu_ts.description as sourcename " +
				"FROM ticket t " +
				"LEFT JOIN organization o ON (t.org_id = o.org_id) " +
				"LEFT JOIN lookup_department ld ON (t.department_code = ld.code) " +
				"LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
				"LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
				"LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
				"LEFT JOIN contact ct_owner ON (t.assigned_to = ct_owner.user_id) " +
				"LEFT JOIN contact ct_eb ON (t.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_mb ON (t.modifiedby = ct_mb.user_id) " +
				"LEFT JOIN lookup_ticketsource lu_ts ON (t.source_code = lu_ts.code) " +
				"WHERE t.ticketid > 0 ");

		//Need to build a base SQL statement for counting records
		sqlCount.append(
				"SELECT COUNT(*) AS recordcount " +
				"FROM ticket t " +
				"WHERE t.ticketid > 0 ");

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
						"AND t.problem < ? ");
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
			if (pagedListInfo.getColumnToSortBy() != null && !pagedListInfo.getColumnToSortBy().equals("")) {
				sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + ", t.pri_code ");
				if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
					sqlOrder.append(pagedListInfo.getSortOrder() + " ");
				}
			}
			else {
				sqlOrder.append("ORDER BY t.pri_code desc ");
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
			Ticket thisTicket = new Ticket(rs);
			this.addElement(thisTicket);
		}
		rs.close();
		pst.close();

		//buildResources(db);
	}


	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since             1.2
	 */
	private void createFilter(StringBuffer sqlFilter) {
		if (enteredBy > -1) {
			sqlFilter.append("AND t.enteredby = ? ");
		}

		if (onlyOpen == true) {
			sqlFilter.append("AND t.closed is null ");
		}
		
		if (onlyClosed == true) {
			sqlFilter.append("AND t.closed is not null ");
		}

		if (id > -1) {
			sqlFilter.append("AND t.ticketid = ? ");
		}

		if (orgId > -1) {
			sqlFilter.append("AND t.org_id = ? ");
		}
		
		if (department > -1) {
			if (unassignedToo == true) {
				sqlFilter.append("AND t.department_code in (?, 0) ");
			} else {
				sqlFilter.append("AND t.department_code = ? ");
			}
		}
		
		if (assignedTo > -1) {
			sqlFilter.append("AND t.assigned_to = ? ");
		}
		
		if (severity > 0) {
			sqlFilter.append("AND t.scode = ? ");
		}
		
		if (priority > 0) {
			sqlFilter.append("AND t.pri_code = ? ");
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

		if (enteredBy > -1) {
			pst.setInt(++i, enteredBy);
		}
		if (id > -1) {
			pst.setInt(++i, id);
		}
		if (orgId > -1) {
			pst.setInt(++i, orgId);
		}
		if (department > -1) {
			pst.setInt(++i, department);
		}
		if (assignedTo > -1) {
			pst.setInt(++i, assignedTo);
		}
		if (severity > 0) {
			pst.setInt(++i, severity);
		}
		if (priority > 0) {
			pst.setInt(++i, priority);
		}
		
		return i;
	}

}

