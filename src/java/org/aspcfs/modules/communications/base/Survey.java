//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

/**
 *  Represents a survey that can have items and answers.
 *
 *@author     chris price
 *@created    August 7, 2002
 *@version    $Id$
 */
public class Survey extends GenericBean {

  private int id = -1;
  private String name = "";
  private String description = "";
  private String intro = "";
  private int itemLength = -1;
  private int type = -1;
  private int itemsId = -1;
  private int messageId = -1;

  private SurveyItemList items = new SurveyItemList();
  private SurveyAnswerList answers = new SurveyAnswerList();

  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = true;
  private String enteredByName = "";
  private String modifiedByName = "";
  private String typeName = "";


  /**
   *  Constructor for the Survey object
   */
  public Survey() { }


  /**
   *  Constructor for the Survey object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Survey(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Gets the answers attribute of the Survey object
   *
   *@return    The answers value
   */
  public SurveyAnswerList getAnswers() {
    return answers;
  }


  /**
   *  Sets the answers attribute of the Survey object
   *
   *@param  answers  The new answers value
   */
  public void setAnswers(SurveyAnswerList answers) {
    this.answers = answers;
  }


  /**
   *  Gets the enteredByName attribute of the Survey object
   *
   *@return    The enteredByName value
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the modifiedByName attribute of the Survey object
   *
   *@return    The modifiedByName value
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Sets the enteredByName attribute of the Survey object
   *
   *@param  tmp  The new enteredByName value
   */
  public void setEnteredByName(String tmp) {
    this.enteredByName = tmp;
  }


  /**
   *  Sets the modifiedByName attribute of the Survey object
   *
   *@param  tmp  The new modifiedByName value
   */
  public void setModifiedByName(String tmp) {
    this.modifiedByName = tmp;
  }


  /**
   *  Constructor for the Survey object
   *
   *@param  db                Description of the Parameter
   *@param  surveyId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Survey(Connection db, int surveyId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT s.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, st.description as typename " +
        "FROM survey s " +
        "LEFT JOIN contact ct_eb ON (s.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (s.modifiedby = ct_mb.user_id) " +
        "LEFT JOIN lookup_survey_types st ON (s.type = st.code) " +
        "WHERE s.id > -1 ");

    if (surveyId > -1) {
      sql.append("AND s.id = " + surveyId + " ");
    } else {
      throw new SQLException("Survey ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Survey record not found.");
    }
    rs.close();
    st.close();

    items.setSurveyId(this.getId());
    items.buildList(db);
  }


  /**
   *  Gets the enteredString attribute of the Survey object
   *
   *@return    The enteredString value
   */
  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Sets the requestItems attribute of the Survey object
   *
   *@param  request  The new requestItems value
   */
  public void setRequestItems(HttpServletRequest request) {
    items = new SurveyItemList(request);
  }


  /**
   *  Sets the answerItems attribute of the Survey object
   *
   *@param  request  The new answerItems value
   */
  public void setAnswerItems(HttpServletRequest request) {
    answers = new SurveyAnswerList(request);
  }


  /**
   *  Gets the itemsId attribute of the Survey object
   *
   *@return    The itemsId value
   */
  public int getItemsId() {
    return itemsId;
  }


  /**
   *  Sets the itemsId attribute of the Survey object
   *
   *@param  itemsId  The new itemsId value
   */
  public void setItemsId(int itemsId) {
    this.itemsId = itemsId;
  }


  /**
   *  Sets the itemsId attribute of the Survey object
   *
   *@param  itemsId  The new itemsId value
   */
  public void setItemsId(String itemsId) {
    this.itemsId = Integer.parseInt(itemsId);
  }


  /**
   *  Gets the messageId attribute of the Survey object
   *
   *@return    The messageId value
   */
  public int getMessageId() {
    return messageId;
  }


  /**
   *  Sets the messageId attribute of the Survey object
   *
   *@param  messageId  The new messageId value
   */
  public void setMessageId(int messageId) {
    this.messageId = messageId;
  }


  /**
   *  Sets the messageId attribute of the Survey object
   *
   *@param  messageId  The new messageId value
   */
  public void setMessageId(String messageId) {
    this.messageId = Integer.parseInt(messageId);
  }


  /**
   *  Gets the typeName attribute of the Survey object
   *
   *@return    The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   *  Sets the typeName attribute of the Survey object
   *
   *@param  typeName  The new typeName value
   */
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }


  /**
   *  Gets the items attribute of the Survey object
   *
   *@return    The items value
   */
  public SurveyItemList getItems() {
    return items;
  }


  /**
   *  Sets the items attribute of the Survey object
   *
   *@param  items  The new items value
   */
  public void setItems(SurveyItemList items) {
    this.items = items;
  }


  /**
   *  Gets the id attribute of the Survey object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the Survey object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the Survey object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the intro attribute of the Survey object
   *
   *@return    The intro value
   */
  public String getIntro() {
    return intro;
  }


  /**
   *  Gets the itemLength attribute of the Survey object
   *
   *@return    The itemLength value
   */
  public int getItemLength() {
    return itemLength;
  }


  /**
   *  Gets the type attribute of the Survey object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the enteredBy attribute of the Survey object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the Survey object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the Survey object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the entered attribute of the Survey object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enabled attribute of the Survey object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Sets the id attribute of the Survey object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Survey object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Survey object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the Survey object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the intro attribute of the Survey object
   *
   *@param  tmp  The new intro value
   */
  public void setIntro(String tmp) {
    this.intro = tmp;
  }


  /**
   *  Sets the itemLength attribute of the Survey object
   *
   *@param  tmp  The new itemLength value
   */
  public void setItemLength(int tmp) {
    this.itemLength = tmp;
  }


  /**
   *  Sets the itemLength attribute of the Survey object
   *
   *@param  tmp  The new itemLength value
   */
  public void setItemLength(String tmp) {
    this.itemLength = Integer.parseInt(tmp);
  }


  /**
   *  Sets the type attribute of the Survey object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the type attribute of the Survey object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Survey object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Survey object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modified attribute of the Survey object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Survey object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the enabled attribute of the Survey object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Gets the enteredDateTimeString attribute of the Survey object
   *
   *@return    The enteredDateTimeString value
   */
  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String printHidden(HttpServletRequest request) {
    StringBuffer result = new StringBuffer();

    if (request != null) {

      java.util.Enumeration e = request.getParameterNames();

      ArrayList params = new ArrayList();

      java.lang.reflect.Field[] f = this.getClass().getDeclaredFields();

      for (int i = 0; i < f.length; i++) {
        params.add(f[i].getName());
      }

      while (e.hasMoreElements()) {
        String tempString = e.nextElement().toString();
        String[] parameterValues = request.getParameterValues(tempString);

        if (params.contains(tempString)) {
          result.append("<input type=\"hidden\" name=\"" + tempString + "\" value=\"" + parameterValues[0] + "\">\n");
          System.out.println(tempString);
        }
      }
    }

    return (result.toString());
  }


  /**
   *  Gets the modifiedString attribute of the Survey object
   *
   *@return    The modifiedString value
   */
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Gets the modifiedDateTimeString attribute of the Survey object
   *
   *@return    The modifiedDateTimeString value
   */
  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO survey " +
        "(items_id, name, description, intro, itemLength, type, enteredBy, modifiedBy) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, itemsId);
      pst.setString(++i, name);
      pst.setString(++i, description);
      pst.setString(++i, intro);
      pst.setInt(++i, itemLength);
      pst.setInt(++i, type);
      pst.setInt(++i, enteredBy);
      pst.setInt(++i, modifiedBy);
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "survey_id_seq");

      //Insert the questions
      Iterator x = items.iterator();
      while (x.hasNext()) {
        SurveyItem thisItem = (SurveyItem) x.next();
        thisItem.insert(db, this.getId(), this.getType());
      }

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean commit = true;
    Statement st = null;
    ResultSet rs = null;
    try {
      commit = db.getAutoCommit();
      
      //Check to see if a survey is being used by any Inactive campaigns
      //If so, the survey can't be deleted
      int inactiveCount = 0;
      st = db.createStatement();
      rs = st.executeQuery(
        "SELECT COUNT(*) AS survey_count " +
        "FROM campaign " +
        "WHERE survey_id = " + this.getId() + " " +
        "AND status_id <> " + Campaign.FINISHED);
      rs.next();
      inactiveCount = rs.getInt("survey_count");
      rs.close();
      if (inactiveCount > 0) {
        st.close();
        errors.put("actionError", "Survey could not be deleted because " +
          inactiveCount + " " +
          (inactiveCount == 1?"campaign is":"campaigns are") +
          " being built that " +
          (inactiveCount == 1?"uses":"use") +
          " this survey.");
        return false;
      }
      
      //If not, Check to see if a survey is being used by any Active Campaigns
      //If so, the campaign will be marked disabled and hidden to the user
      int activeCount = 0;
      rs = st.executeQuery(
        "SELECT COUNT(*) AS survey_count " +
        "FROM campaign " +
        "WHERE survey_id = " + this.getId() + " " +
        "AND active = " + DatabaseUtils.getTrue(db));
      rs.next();
      activeCount = rs.getInt("survey_count");
      rs.close();
      
      if (commit) {
        db.setAutoCommit(false);
      }
      if (activeCount > 0) {
        st.executeUpdate(
          "UPDATE survey " +
          "SET enabled = " + DatabaseUtils.getFalse(db) + " " +
          "WHERE id = " + this.getId() + " " +
          "AND enabled = " + DatabaseUtils.getTrue(db));
      } else {
        st.executeUpdate("DELETE FROM survey WHERE id = " + this.getId());
        st.executeUpdate("DELETE FROM survey_answer WHERE question_id in (select id from survey_item where survey_id = " + this.getId() + ") ");
        st.executeUpdate("DELETE FROM survey_item WHERE survey_id = " + this.getId());
      }
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      System.out.println(e.toString());
      throw new SQLException(e.toString());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
      st.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  override          Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("Survey ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE survey " +
        "SET message_id = ?, items_id = ?, name = ?, description = ?, intro = ?, itemlength = ?, " +
        "type = ?, " +
        "enabled = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE id = ? ");
    //if (!override) {
    //  sql.append("AND modified = ? ");
    //}

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getMessageId());
    pst.setInt(++i, this.getItemsId());
    pst.setString(++i, this.getName());
    pst.setString(++i, this.getDescription());
    pst.setString(++i, this.getIntro());
    pst.setInt(++i, this.getItemLength());
    pst.setInt(++i, this.getType());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    //if (!override) {
    //  pst.setTimestamp(++i, modified);
    //}

    resultCount = pst.executeUpdate();
    pst.close();

    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;

    try {
      db.setAutoCommit(false);

      Iterator x = items.iterator();
      while (x.hasNext()) {
        SurveyItem thisItem = (SurveyItem) x.next();
        thisItem.process(db, this.getId(), this.getType());
      }

      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }

    db.setAutoCommit(true);
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    messageId = rs.getInt("message_id");
    itemsId = rs.getInt("items_id");
    name = rs.getString("name");
    description = rs.getString("description");
    intro = rs.getString("intro");
    itemLength = rs.getInt("itemlength");
    type = rs.getInt("type");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");

    enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));
    typeName = rs.getString("typename");
  }

}

