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

