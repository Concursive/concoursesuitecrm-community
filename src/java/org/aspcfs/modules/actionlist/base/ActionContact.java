/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.actionlist.base;

import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.Contact;

/**
 *  Action Contact of a Action List
 *
 * @author     akhi_m
 * @created    April 23, 2003
 */
public class ActionContact extends ActionItem {
  private Contact contact = null;
  private ActionItemLog mostRecentItem = null;


  /**
   *Constructor for the ActionContact object
   */
  public ActionContact() { }


  /**
   *Constructor for the ActionContact object
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionContact(Connection db, int contactId) throws SQLException {
    queryRecord(db, contactId);
  }


  /**
   *Constructor for the ActionContact object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ActionContact(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the contact attribute of the ActionContact object
   *
   * @param  contact  The new contact value
   */
  public void setContact(Contact contact) {
    this.contact = contact;
  }


  /**
   *  Sets the mostRecentItem attribute of the ActionContact object
   *
   * @param  mostRecentItem  The new mostRecentItem value
   */
  public void setMostRecentItem(ActionItemLog mostRecentItem) {
    this.mostRecentItem = mostRecentItem;
  }


  /**
   *  Gets the mostRecentItem attribute of the ActionContact object
   *
   * @return    The mostRecentItem value
   */
  public ActionItemLog getMostRecentItem() {
    return mostRecentItem;
  }


  /**
   *  Gets the contact attribute of the ActionContact object
   *
   * @return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  contactId         Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildContact(Connection db, int contactId) throws SQLException {
    contact = new Contact(db, contactId);
  }


  /**
   *  Builds the most recent history item added to the log
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildMostRecentHistoryItem(Connection db) throws SQLException {
    if (mostRecentItem == null) {
      mostRecentItem = new ActionItemLog();
    }

    PagedListInfo pagedListInfo = new PagedListInfo();
    pagedListInfo.setItemsPerPage(1);
    ActionItemLogList thisList = new ActionItemLogList();
    thisList.setPagedListInfo(pagedListInfo);
    thisList.setItemId(this.getId());
    thisList.setBuildDetails(true);
    thisList.buildList(db);

    if (thisList.size() > 0) {
      mostRecentItem = (ActionItemLog) thisList.get(0);
    }
  }
}

