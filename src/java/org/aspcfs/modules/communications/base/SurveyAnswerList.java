//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.communications.base;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;

/**
 *  Contains a list of survey answers for a campaign
 *
 *@author     chris price
 *@created    August 13, 2002
 *@version    $Id: SurveyAnswerList.java,v 1.4 2002/08/27 19:28:31 mrajkowski
 *      Exp $
 */
public class SurveyAnswerList extends Vector {

  private int questionId = -1;
  private int hasComments = -1;
  private int responseId = -1;
  private int contactId = -1;
  private boolean lastAnswers = false;
  private int itemsPerPage = 10;
  private HashMap contacts = null;
  protected PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the SurveyAnswerList object
   */
  public SurveyAnswerList() { }


  /**
   *  Constructor for the SurveyAnswerList object
   *
   *@param  db                Description of the Parameter
   *@param  questionId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public SurveyAnswerList(Connection db, int questionId) throws SQLException {
    this.questionId = questionId;
    buildList(db);
  }


  /**
   *  Constructor for the SurveyAnswerList object
   *
   *@param  request  Description of the Parameter
   */
  public SurveyAnswerList(HttpServletRequest request) {
    int i = 0;
    while (request.getParameter("quest" + (++i) + "id") != null) {
      SurveyAnswer thisAnswer = new SurveyAnswer();
      thisAnswer.buildRecord(request, i);
      this.addElement(thisAnswer);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Added an answer: " + thisAnswer.getQuestionId());
      }
    }
  }


  /**
   *  Gets the questionId attribute of the SurveyAnswerList object
   *
   *@return    The questionId value
   */
  public int getQuestionId() {
    return questionId;
  }


  /**
   *  Sets the questionId attribute of the SurveyAnswerList object
   *
   *@param  questionId  The new questionId value
   */
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }


  /**
   *  Sets the pagedListInfo attribute of the SurveyAnswerList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the hasComments attribute of the SurveyAnswerList object
   *
   *@param  tmp  The new hasComments value
   */
  public void setHasComments(int tmp) {
    this.hasComments = tmp;
  }


  /**
   *  Set if Most recently entered survey answers are needed
   *
   *@param  lastAnswers  The new lastAnswers value
   */
  public void setLastAnswers(boolean lastAnswers) {
    this.lastAnswers = lastAnswers;
  }


  /**
   *  Sets the itemsPerPage attribute of the SurveyAnswerList object
   *
   *@param  itemsPerPage  The new itemsPerPage value
   */
  public void setItemsPerPage(int itemsPerPage) {
    this.itemsPerPage = itemsPerPage;
  }


  /**
   *  Contacts HashMap for storing Contact Id and Names
   *
   *@param  contacts  The new contacts value
   */
  public void setContacts(HashMap contacts) {
    this.contacts = contacts;
  }


  /**
   *  Sets the responseId attribute of the SurveyAnswerList object
   *
   *@param  responseId  The new responseId value
   */
  public void setResponseId(int responseId) {
    this.responseId = responseId;
  }

public void setContactId(int contactId) {
	this.contactId = contactId;
}
public int getContactId() {
	return contactId;
}

  /**
   *  Gets the responseId attribute of the SurveyAnswerList object
   *
   *@return    The responseId value
   */
  public int getResponseId() {
    return responseId;
  }


  /**
   *  Gets the contacts HashMap
   *
   *@return    The contacts value
   */
  public HashMap getContacts() {
    return contacts;
  }


  /**
   *  Gets the hasComments attribute of the SurveyAnswerList object
   *
   *@return    The hasComments value
   */
  public int getHasComments() {
    return hasComments;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM active_survey_answers sa, active_survey_responses sr " +
        "WHERE sa.question_id > -1 ");

    createFilter(sqlFilter);

    if (pagedListInfo == null && lastAnswers) {
      pagedListInfo = new PagedListInfo();
      pagedListInfo.setItemsPerPage(itemsPerPage);
    }

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

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND sa.comments < ? ");
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
      pagedListInfo.setDefaultSort("sr.entered DESC", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY response_id, question_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("sa.*, c.namelast as lastname, c.namefirst as firstname, c.contact_id as contactid, sr.entered as entered " +
        "FROM active_survey_answers sa, active_survey_responses sr " +
        "LEFT JOIN contact c ON (c.contact_id = sr.contact_id) " +
        "WHERE sa.question_id > -1 "
        );
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      SurveyAnswer thisAnswer = new SurveyAnswer(rs);
      this.add(thisAnswer);
      if (contacts == null) {
        contacts = new HashMap();
      }
      String contactName = Contact.getNameLastFirst(rs.getString("lastname"), rs.getString("firstname"));
      contacts.put(new Integer(rs.getInt("contactid")), contactName);
    }
    rs.close();
    pst.close();

    Iterator ans = this.iterator();
    while (ans.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) ans.next();
      thisAnswer.setContactId(db);
      thisAnswer.buildItems(db, thisAnswer.getId());
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (questionId != -1) {
      sqlFilter.append("AND sa.question_id = ? ");
    }

    if (responseId != -1) {
      sqlFilter.append("AND sa.response_id = ? ");
    } else {
      sqlFilter.append("AND sa.response_id = sr.response_id ");
    }

    if(contactId != -1){
      sqlFilter.append("AND sr.contact_id = ? ");
    }
    
    if (hasComments > -1) {
      if (hasComments == Constants.TRUE) {
        sqlFilter.append("AND sa.comments <> '' ");
      } else {
        sqlFilter.append("AND sa.comments = '' ");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (questionId != -1) {
      pst.setInt(++i, questionId);
    }

    if (responseId != -1) {
      pst.setInt(++i, responseId);
    }
    
    if(contactId != -1){
      pst.setInt(++i, contactId);
    }
    
    return i;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  responseId        Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, int responseId) throws SQLException {
    Iterator ans = this.iterator();
    while (ans.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) ans.next();
      thisAnswer.insert(db, responseId);
    }
    return true;
  }
}

