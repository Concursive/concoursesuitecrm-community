/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.contacts.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Description of the Class
 *
 * @author Olga.Kaptyug
 * @version $Id: CallParticipantList.java $
 * @created May 3, 2007
 */
public class CallParticipantList extends ArrayList implements SyncableList{

  public final static String tableName = "call_log_participant";
  public final static String uniqueField = "participant_id";
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;

  protected PagedListInfo pagedListInfo = null;
  protected int contactId = -1;
  protected int callId = -1;
  protected int isFollowup = 0;
  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  
  /**
   * Constructor for the CallList object
   */
  public CallParticipantList() {
  }

  /**
   * Description of the Method
   *
   * @param rs
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public static CallParticipant getObject(ResultSet rs) throws SQLException {
    CallParticipant callParticipant = new CallParticipant(rs);
    return callParticipant;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    return tableName;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp lastAnchor) {
    this.lastAnchor = lastAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String lastAnchor) {
    this.lastAnchor = java.sql.Timestamp.valueOf(lastAnchor);
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp nextAnchor) {
    this.nextAnchor = nextAnchor;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String nextAnchor) {
    this.nextAnchor = java.sql.Timestamp.valueOf(nextAnchor);
  }
 
  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int syncType) {
    this.syncType = syncType;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(String)
   */
  public void setSyncType(String syncType) {
    this.syncType = Integer.parseInt(syncType);
  }


  /**
   * Sets the PagedListInfo attribute of the CallList object
   *
   * @param tmp The new PagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the ContactId attribute of the CallList object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   * Sets the ContactId attribute of the CallList object
   *
   * @param tmp The new ContactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }



  /**
   * Sets the EnteredBy attribute of the CallList object
   *
   * @param tmp The new EnteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Gets the EnteredBy attribute of the CallList object
   *
   * @return The EnteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }



  /**
   * Gets the syncType attribute of the CallList object
   *
   * @return The syncType value
   */
  public int getSyncType() {
    return syncType;
  }

  /**
   * Gets the PagedListInfo attribute of the CallList object
   *
   * @return The PagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   * Gets the ContactId attribute of the CallList object
   *
   * @return The ContactId value
   */
  public int getContactId() {
    return contactId;
  }

   
  /**
   * Description of the Method
   *
   * @param db
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db) throws SQLException {
    return prepareList(db, "", "");
  }

  /**
   * Description of the Method
   *
   * @param db
   * @param sqlFilter
   * @param sqlOrder
   * @return
   * @throws SQLException Description of the Returned Value
   */
  public PreparedStatement prepareList(Connection db, String sqlFilter, String sqlOrder) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();

    //Need to build a base SQL statement for returning records
   
    sqlSelect.append( "SELECT "+
        "c.participant_id, c.call_id, c.contact_id, c.is_available, c.entered, c.modified, c.enteredby, c.modifiedby, c.is_followup, "+
            "ct.namelast as namelast, ct.namefirst as namefirst, ctea.email " +
            "FROM " + tableName + " c " +
            "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
            "LEFT JOIN contact_emailaddress ctea ON (ct.contact_id = ctea.contact_id) " +
            "WHERE participant_id > -1 ");
    if (sqlFilter == null || sqlFilter.length() == 0) {
      StringBuffer buff = new StringBuffer();
      createFilter(buff);
      sqlFilter = buff.toString();
    }
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter + sqlOrder);
    prepareFilter(pst);
    return pst;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
            "FROM call_log_participant c " +
            "LEFT JOIN contact ct ON (c.contact_id = ct.contact_id) " +
            "WHERE participant_id > -1 ");
    createFilter(sqlFilter);
    sqlOrder.append(" ORDER BY c.entered DESC ");
    pst = prepareList(db, sqlFilter.toString(), sqlOrder.toString());
    rs = DatabaseUtils.executeQuery(db, pst);
    while (rs.next()) {
   CallParticipant thisParticipant = new CallParticipant(rs);
      this.add(thisParticipant);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator participants = this.iterator();
    while (participants.hasNext()) {
      CallParticipant thisParticipant = (CallParticipant) participants.next();
      thisParticipant.delete(db);
    }
  }


  /**
   * Description of the Method
   *
   * @param sqlFilter Description of Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (callId != -1) {
      sqlFilter.append("AND (c.call_id = ? )");
     
    }
    sqlFilter.append("AND (c.is_followup = ? )");

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND c.entered > ? ");
      }
      sqlFilter.append("AND c.entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND c.modified > ? ");
      sqlFilter.append("AND c.entered < ? ");
      sqlFilter.append("AND c.modified < ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (callId != -1) {
      pst.setInt(++i, callId);
    }
    pst.setInt(++i, isFollowup);

    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }

    return i;
  }


  /**
   * Description of the Method
   *
   * @param db       Description of the Parameter
   * @param context  Description of the Parameter
   * @param newOwner Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int reassignElements(Connection db, ActionContext context, int newOwner) throws SQLException {
    int total = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Call thisActivity = (Call) i.next();
      if (thisActivity.reassign(db, context, newOwner)) {
        total++;
      }
    }
    return total;
  }
  
  
  public String getTokenizedStringEMails(String token) {
  	String result = "";
  	Iterator i = this.iterator();
  	while (i.hasNext()) {
  		CallParticipant participant = (CallParticipant) i.next();
  		result += participant.getEMail();
  		result += i.hasNext() ? token : "";
  	}
  	return result;
  }
  
  public String getTokenizedStringUserIds(String token) {
  	String result = "";
  	Iterator i = this.iterator();
  	while (i.hasNext()) {
  		CallParticipant participant = (CallParticipant) i.next();
  		result += participant.getContactId();
  		result += i.hasNext() ? token : "";
  	}
  	return result;
  }  
  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void select(Connection db) throws SQLException {
    buildList(db);
  }

  /**
   * Gets the callId attribute of the CallParticipantList object
   *
   * @return callId The callId value
   */
  public int getCallId() {
    return this.callId;
  }

  /**
   * Sets the callId attribute of the CallParticipantList object
   *
   * @param callId The new callId value
   */
  public void setCallId(int callId) {
    this.callId = callId;
  }
  /**
   * Sets the avoidDisabledContacts attribute of the CallList object
   *
   * @param tmp The new avoidDisabledContacts value
   */
  public void setCallId(String tmp) {
    this.callId = Integer.parseInt(tmp);
  }
  /**
   * Gets the callId attribute of the CallParticipantList object
   *
   * @return callId The callId value
   */
  public int getIsFollowup() {
    return this.isFollowup;
  }

  /**
   * Sets the callId attribute of the CallParticipantList object
   *
   * @param callId The new callId value
   */
  public void setIsFollowup(int isFollowup) {
    this.isFollowup = isFollowup;
  }
  /**
   * Sets the avoidDisabledContacts attribute of the CallList object
   *
   * @param tmp The new avoidDisabledContacts value
   */
  public void setIsFollowup(String tmp) {
    this.isFollowup = Integer.parseInt(tmp);
  }
  
  public boolean isContainParticipant(CallParticipant participant) {
  	Iterator i = this.iterator();
  	while (i.hasNext()) {
  		CallParticipant tmp = (CallParticipant) i.next(); 
  		if (participant.getParticipantId() == tmp.getParticipantId()) {
  			return true;
  		}
  	}
  	return false;
  }
  
  public void save(Connection db) throws SQLException {
  	Iterator i = this.iterator();
  	while (i.hasNext()) {
  		CallParticipant participant = (CallParticipant) i.next();
 			if (participant.getParticipantId() > -1)
 				participant.delete(db);
  		participant.insert(db);
  	}
  }

	public static CallParticipantList buildList(String tokenizedString, int userId, Connection db, int isFollowup) throws SQLException {
		CallParticipantList result = new CallParticipantList();
    StringTokenizer st = new StringTokenizer(tokenizedString, "|");
    while(st.hasMoreTokens()){
      CallParticipant pers = new CallParticipant();
      pers.setContactId(st.nextToken());
      Contact contact = new Contact(db, pers.getContactId());
      pers.setLastName(contact.getNameLast());
      pers.setFirstName(contact.getNameFirst());
      pers.setEMail(contact.getPrimaryEmailAddress());
      pers.setIsFollowup(CallParticipant.LOGGED_CALL);
      pers.setEnteredBy(userId);
      pers.setModifiedBy(userId);
      pers.setIsFollowup(isFollowup);
      result.add(pers);
    }
		return result;
	}

  public String getValuesAsString() {
    String result = "";
    if(this != null ){
      Iterator iter = this.iterator();
      if(iter.hasNext()){
        while (iter.hasNext()){
          CallParticipant pers = (CallParticipant)iter.next();
          result += pers.getNameLastFirst();
          if (iter.hasNext()) {
            result += System.getProperty("line.separator");
          }
        }
      }
    }
    return result;
  }
  
  public String getHtmlSelectMultiple(String fieldName, int listSize){
	  StringBuffer result = new StringBuffer();
 
	  result.append("<select size='" + listSize + "' name='" + fieldName + "' id='" + fieldName + "Id' multiple>");

	  String ids="";
	  if (this != null ){
      Iterator iter = this.iterator();
      if(iter.hasNext()){
        while (iter.hasNext()){
          CallParticipant pers = (CallParticipant)iter.next();
          ids+=pers.getContactId()+"|";
    
          result.append("<option value='" +
       	  pers.getContactId() + "' > " +
          pers.getNameLastFirst() + "</option>");
        }
      } else {
  
  	  result.append("<option value='none' selected>" +
  	  		"<dhv:label name='accounts.accounts_add.NoneSelected'>None Selected</dhv:label>" +
  	  		"</option>");
  
  	  }
	  }
	  result.append("</select>");
	  result.append("<input type='hidden' name='" +
			  fieldName + "IdValues' id='" +
			  fieldName + "IdValues' value='" + ids + "' />");
	 return result.toString(); 
  }
	  

  
}


