//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import javax.servlet.http.*;



public class TicketLogList extends Vector {

	private PagedListInfo pagedListInfo = null;
	private int ticketId = -1;

	public TicketLogList() { }
	
	public TicketLogList(HttpServletRequest request, int userId) {
		int i = 0;
		if (request.getParameter("newticketlogentry") != null) {
			TicketLog thisEntry = new TicketLog();
			
			thisEntry.setEnteredBy(userId);
			thisEntry.buildRecord(request);
			
			if (thisEntry.isValid()) {
				this.addElement(thisEntry);
			} else {
				System.out.println("not inserting" + request.getParameter("refresh"));
			}
		}
	}
	
	public void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}

	public void setTicketId(int tmp) {
		this.ticketId = tmp;
	}

	public void buildList(Connection db) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		int items = -1;
		
		TicketLog prevTicketLog = null;
		boolean systemResult = true;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for returning records
		sqlSelect.append(
				"SELECT t.*, d.description as deptname, ct_eb.namelast || ', ' || ct_eb.namefirst as ebname, ct_at.namelast || ', ' || ct_at.namefirst as atname, tp.description as priorityname, ts.description as severityname " +
				"FROM ticketlog t " +
				"LEFT JOIN ticket_category tc ON (t.cat_code = tc.id) " +
				"LEFT JOIN contact ct_eb ON (t.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_at ON (t.assigned_to = ct_at.user_id) " +
				"LEFT JOIN ticket_priority tp ON (t.pri_code = tp.code) " +
				"LEFT JOIN ticket_severity ts ON (t.scode = ts.code) " +
				"LEFT JOIN lookup_department d ON (t.department_code = d.code) " +
				"WHERE t.id > 0 ");

		//Need to build a base SQL statement for counting records
		sqlCount.append(
				"SELECT COUNT(*) AS recordcount " +
				"FROM ticketlog t " +
				"WHERE t.id > 0 ");

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
						"AND t.comment < ? ");
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
				sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + ", t.entered ");
				if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
					sqlOrder.append(pagedListInfo.getSortOrder() + " ");
				}
			}
			else {
				sqlOrder.append("ORDER BY t.entered desc ");
			}

			//Determine items per page
			if (pagedListInfo.getItemsPerPage() > 0) {
				sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
			}

			sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
		} else {
			sqlOrder.append("ORDER BY t.entered desc ");
		}

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		
		items = prepareFilter(pst);
		rs = pst.executeQuery();
		while (rs.next()) {
			TicketLog thisTicketLog = new TicketLog(rs);
			
			systemResult = this.setSystemMessages(thisTicketLog, prevTicketLog);
			
			if (thisTicketLog.getEntryText() != null && !(thisTicketLog.getEntryText().equals(""))) {
				this.addElement(thisTicketLog);
			}
			
			prevTicketLog = thisTicketLog;
		}
		rs.close();
		pst.close();

		//buildResources(db);
	}
	
	public boolean setSystemMessages( TicketLog current, TicketLog prev ) {
		
		TicketLog tempLog = null;
		
		if ( prev == null ) {
			System.out.println("The first");
			return false;
		} else {
			if (current.getAssignedTo() != prev.getAssignedTo()) {
				tempLog = new TicketLog();
				tempLog.createSysMsg(prev);
				tempLog.setEntryText("[ Re-assigned to " + tempLog.getAssignedToName() + " ]");
				this.addElement(tempLog);
			}
			
			if (current.getDepartmentCode() != prev.getDepartmentCode()) {
				tempLog = new TicketLog();
				tempLog.createSysMsg(prev);
				tempLog.setEntryText("[ Department changed to " + tempLog.getDepartmentName() + " ]");
				this.addElement(tempLog);
			}
			
			if (current.getPriorityCode() != prev.getPriorityCode()) {
				tempLog = new TicketLog();
				tempLog.createSysMsg(prev);
				tempLog.setEntryText("[ Priority changed to " + tempLog.getPriorityName() + " ]");
				this.addElement(tempLog);
			}
			
			if (current.getSeverityCode() != prev.getSeverityCode()) {
				tempLog = new TicketLog();
				tempLog.createSysMsg(prev);
				tempLog.setEntryText("[ Severity changed to " + tempLog.getSeverityName() + " ]");
				this.addElement(tempLog);
			}
			
			if (current.getClosed() != prev.getClosed()) {
				tempLog = new TicketLog();
				tempLog.createSysMsg(prev);
				tempLog.setEntryText("[ Ticket Closed ]");
				this.addElement(tempLog);
			}
			
			return true;
		}
	}


	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since             1.2
	 */
	private void createFilter(StringBuffer sqlFilter) {
		if (ticketId > -1) {
			sqlFilter.append("AND t.ticketid = ? ");
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

		if (ticketId > -1) {
			pst.setInt(++i, ticketId);
		}

		return i;
	}

}

