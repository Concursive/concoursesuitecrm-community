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
 *@created    September 20, 2001
 */
public class NoteList extends Vector {

	/**
	 *  Description of the Field
	 */
	protected PagedListInfo pagedListInfo = null;
	/**
	 *  Description of the Field
	 */
	protected int orgId = -1;
	/**
	 *  Description of the Field
	 */
	protected int oppId = -1;
	/**
	 *  Description of the Field
	 */
	protected int contactId = -1;


	/**
	 *  Sets the OrgId attribute of the NoteList object
	 *
	 *@param  tmp  The new OrgId value
	 */
	public void setOrgId(int tmp) {
		this.orgId = tmp;
	}


	/**
	 *  Sets the OppId attribute of the NoteList object
	 *
	 *@param  tmp  The new OppId value
	 */
	public void setOppId(int tmp) {
		this.oppId = tmp;
	}


	/**
	 *  Sets the ContactId attribute of the NoteList object
	 *
	 *@param  tmp  The new ContactId value
	 */
	public void setContactId(int tmp) {
		this.contactId = tmp;
	}


	/**
	 *  Gets the OrgId attribute of the NoteList object
	 *
	 *@return    The OrgId value
	 */
	public int getOrgId() {
		return orgId;
	}


	/**
	 *  Gets the OppId attribute of the NoteList object
	 *
	 *@return    The OppId value
	 */
	public int getOppId() {
		return oppId;
	}


	/**
	 *  Gets the ContactId attribute of the NoteList object
	 *
	 *@return    The ContactId value
	 */
	public int getContactId() {
		return contactId;
	}


	/**
	 *  Sets the PagedListInfo attribute of the NoteList object
	 *
	 *@param  tmp  The new PagedListInfo value
	 */
	protected void setPagedListInfo(PagedListInfo tmp) {
		this.pagedListInfo = tmp;
	}


	/**
	 *  Gets the NoteBody attribute of the NoteList object
	 *
	 *@param  thisItem  Description of Parameter
	 *@return           The NoteBody value
	 */
	protected String getNoteBody(int thisItem) {
		if (thisItem - 1 > -1 && thisItem <= this.size()) {
			Note thisNote = (Note) this.get(thisItem - 1);
			return thisNote.getBody();
		}
		return "";
	}


	/**
	 *  Gets the NoteSubject attribute of the NoteList object
	 *
	 *@param  thisItem  Description of Parameter
	 *@return           The NoteSubject value
	 */
	protected String getNoteSubject(int thisItem) {
		if (thisItem - 1 > -1 && thisItem <= this.size()) {
			Note thisNote = (Note) this.get(thisItem - 1);
			return thisNote.getSubject();
		}
		return "";
	}


	/**
	 *  Gets the NoteDate attribute of the NoteList object
	 *
	 *@param  thisItem  Description of Parameter
	 *@return           The NoteDate value
	 */
	protected String getNoteDate(int thisItem) {
		if (thisItem - 1 > -1 && thisItem <= this.size()) {
			Note thisNote = (Note) this.get(thisItem - 1);
			return thisNote.getDateEntered();
		}
		return "";
	}


	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since             1.1
	 */
	protected void createFilter(StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (orgId != -1) {
			sqlFilter.append("AND org_id = ? ");
		}

		if (oppId != -1) {
			sqlFilter.append("AND opp_id = ? ");
		}

		if (contactId != -1) {
			sqlFilter.append("AND contact_id = ? ");
		}
	}


	/**
	 *  Sets the parameters for the preparedStatement - these items must correspond
	 *  with the createFilter statement
	 *
	 *@param  pst               Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	protected int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;
		if (orgId != -1) {
			pst.setInt(++i, orgId);
		}

		if (oppId != -1) {
			pst.setInt(++i, oppId);
		}

		if (contactId != -1) {
			pst.setInt(++i, contactId);
		}

		return i;
	}

}

