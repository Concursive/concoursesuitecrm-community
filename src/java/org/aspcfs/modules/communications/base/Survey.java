//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

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

  public Survey() { }
  
  public Survey(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  public SurveyAnswerList getAnswers() {
	return answers;
}
public void setAnswers(SurveyAnswerList answers) {
	this.answers = answers;
}

  public String getEnteredByName() { return enteredByName; }
public String getModifiedByName() { return modifiedByName; }
public void setEnteredByName(String tmp) { this.enteredByName = tmp; }
public void setModifiedByName(String tmp) { this.modifiedByName = tmp; }

  public Survey(Connection db, String surveyId) throws SQLException {
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

    if (surveyId != null && !surveyId.equals("")) {
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

  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }

  public void setRequestItems(HttpServletRequest request) {
    items = new SurveyItemList(request);
  }
  
  public void setAnswerItems(HttpServletRequest request) {
  answers = new SurveyAnswerList(request);
}
  
 public int getItemsId() {
	return itemsId;
}
public void setItemsId(int itemsId) {
	this.itemsId = itemsId;
}
public void setItemsId(String itemsId) {
	this.itemsId = Integer.parseInt(itemsId);
}
public int getMessageId() {
	return messageId;
}
public void setMessageId(int messageId) {
	this.messageId = messageId;
}
public void setMessageId(String messageId) {
	this.messageId = Integer.parseInt(messageId);
}
public String getTypeName() {
	return typeName;
}
public void setTypeName(String typeName) {
	this.typeName = typeName;
}

  public SurveyItemList getItems() {
	return items;
}
public void setItems(SurveyItemList items) {
	this.items = items;
}

public int getId() { return id; }
public String getName() { return name; }
public String getDescription() { return description; }
public String getIntro() { return intro; }
public int getItemLength() { return itemLength; }
public int getType() { return type; }
public int getEnteredBy() { return enteredBy; }
public int getModifiedBy() { return modifiedBy; }
public java.sql.Timestamp getModified() { return modified; }
public java.sql.Timestamp getEntered() { return entered; }
public boolean getEnabled() { return enabled; }
public void setId(int tmp) { this.id = tmp; }
public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
public void setName(String tmp) { this.name = tmp; }
public void setDescription(String tmp) { this.description = tmp; }
public void setIntro(String tmp) { this.intro = tmp; }
public void setItemLength(int tmp) { this.itemLength = tmp; }
public void setItemLength(String tmp) {
	this.itemLength = Integer.parseInt(tmp);
}
public void setType(int tmp) { this.type = tmp; }
public void setType(String tmp) {
	this.type = Integer.parseInt(tmp);
}
public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
public void setEnabled(boolean tmp) { this.enabled = tmp; }


  public String getEnteredDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  
  public String printHidden(HttpServletRequest request) {
	StringBuffer result = new StringBuffer();
	
	if (request != null) {
	
		java.util.Enumeration e = request.getParameterNames();
		
		ArrayList params = new ArrayList();
		
		java.lang.reflect.Field[] f = this.getClass().getDeclaredFields();
		
		for (int i=0; i<f.length; i++) {
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
  
  public String getModifiedString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }

  public String getModifiedDateTimeString() {
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(modified);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  
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
	
	  public boolean delete(Connection db) throws SQLException {

    Statement st = db.createStatement();

    try {
      db.setAutoCommit(false);
      st.executeUpdate("DELETE FROM survey WHERE id = " + this.getId());
      st.executeUpdate("DELETE FROM survey_item WHERE survey_id = " + this.getId());
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      System.out.println(e.toString());
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }
  
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

