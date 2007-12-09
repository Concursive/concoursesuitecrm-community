/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.base;

import org.aspcfs.utils.web.PagedListInfo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Description of the Class
 *
 * @author chris
 * @version $Id$
 * @created September 20, 2001
 */
public class NoteList extends Vector {

  /**
   * Description of the Field
   */
  protected PagedListInfo pagedListInfo = null;
  /**
   * Description of the Field
   */
  protected int orgId = -1;
  /**
   * Description of the Field
   */
  protected int oppHeaderId = -1;
  /**
   * Description of the Field
   */
  protected int contactId = -1;


  /**
   * Sets the OrgId attribute of the NoteList object
   *
   * @param tmp The new OrgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   * Sets the OppHeaderId attribute of the NoteList object
   *
   * @param tmp The new OppHeaderId value
   */
  public void setOppHeaderId(int tmp) {
    this.oppHeaderId = tmp;
  }


  /**
   * Sets the ContactId attribute of the NoteList object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Gets the OrgId attribute of the NoteList object
   *
   * @return The OrgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   * Gets the OppHeaderId attribute of the NoteList object
   *
   * @return The OppHeaderId value
   */
  public int getOppHeaderId() {
    return oppHeaderId;
  }


  /**
   * Gets the ContactId attribute of the NoteList object
   *
   * @return The ContactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   * Sets the PagedListInfo attribute of the NoteList object
   *
   * @param tmp The new PagedListInfo value
   */
  protected void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Gets the NoteBody attribute of the NoteList object
   *
   * @param thisItem Description of Parameter
   * @return The NoteBody value
   */
  protected String getNoteBody(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      Note thisNote = (Note) this.get(thisItem - 1);
      return thisNote.getBody();
    }
    return "";
  }


  /**
   * Gets the NoteSubject attribute of the NoteList object
   *
   * @param thisItem Description of Parameter
   * @return The NoteSubject value
   */
  protected String getNoteSubject(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      Note thisNote = (Note) this.get(thisItem - 1);
      return thisNote.getSubject();
    }
    return "";
  }


  /**
   * Gets the NoteDate attribute of the NoteList object
   *
   * @param thisItem Description of Parameter
   * @return The NoteDate value
   */
  protected String getNoteDate(int thisItem) {
    if (thisItem - 1 > -1 && thisItem <= this.size()) {
      Note thisNote = (Note) this.get(thisItem - 1);
      return thisNote.getDateEntered();
    }
    return "";
  }


  /**
   * Builds a base SQL where statement for filtering records to be used by
   * sqlSelect and sqlCount
   *
   * @param sqlFilter Description of Parameter
   * @since 1.1
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (orgId != -1) {
      sqlFilter.append("AND org_id = ? ");
    }

    if (oppHeaderId != -1) {
      sqlFilter.append("AND opp_id = ? ");
    }

    if (contactId != -1) {
      sqlFilter.append("AND contact_id = ? ");
    }
  }


  /**
   * Sets the parameters for the preparedStatement - these items must
   * correspond with the createFilter statement
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (orgId != -1) {
      pst.setInt(++i, orgId);
    }
    if (oppHeaderId != -1) {
      pst.setInt(++i, oppHeaderId);
    }
    if (contactId != -1) {
      pst.setInt(++i, contactId);
    }
    return i;
  }

}

