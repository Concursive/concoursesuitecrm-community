//Copyright 2001 Dark Horse Ventures
package com.darkhorseventures.cfsbase;

import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import javax.servlet.http.*;

public class SurveyAnswer {
	private int id = -1;
	private int questionId = -1;
	private String comments = "";
	private int quantAns = -1;
	private String textAns = "";

  public SurveyAnswer() {}


  public SurveyAnswer(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
  
  public void buildRecord(HttpServletRequest request, int parseItem) {
    this.setQuestionId(request.getParameter("quest" + parseItem + "id"));
    
    if (request.getParameter("quest" + parseItem + "comments") != null) {
	    this.setComments(request.getParameter("quest" + parseItem + "comments"));
    }
    
    if (request.getParameter("quest" + parseItem + "qans") != null) {
	    this.setQuantAns(request.getParameter("quest" + parseItem + "qans"));
    }
    
    //this.setTextAns(request.getParameter("address" + parseItem + "country"));
  }
  public void setId(int id) {
	this.id = id;
}
public int getId() {
	return id;
}
  public void setId(String id) {
	this.id = Integer.parseInt(id);
}
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("id"));
    this.setQuestionId(rs.getInt("question_id"));
    this.setComments(rs.getString("comments"));
    this.setQuantAns(rs.getInt("quant_ans"));
    this.setTextAns(rs.getString("text_ans"));
    //this.setEnteredBy(rs.getInt("enteredby"));
  }

  public SurveyAnswer(Connection db, String passedId) throws SQLException {
    if (passedId == null) {
      throw new SQLException("Question Answer ID not specified.");
    }

    Statement st = null;
    ResultSet rs = null;
    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * " +
        "FROM survey_answer s " +
        "WHERE id = " + passedId + " ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Question Answer record not found.");
    }
    rs.close();
    st.close();
  }
	
  private int setNewAverage(Connection db) throws SQLException {
	if (questionId == -1) {
		throw new SQLException("Question ID not specified.");
	}
	
	Statement st = null;
	ResultSet rs = null;
	StringBuffer sql = new StringBuffer();
		sql.append("SELECT avg(quant_ans) as av " +
		"FROM survey_answer s " +
		"WHERE question_id = " + this.getQuestionId() + " ");
	st = db.createStatement();
	rs = st.executeQuery(sql.toString());
	
	if(rs.next()) {
		PreparedStatement pst = db.prepareStatement(
			"UPDATE survey_item " +
			"SET average = ? " +
			"WHERE id = ? ");
		int i = 0;
		pst.setDouble(++i, rs.getDouble("av"));
		pst.setInt(++i, this.getQuestionId());
		pst.execute();
		pst.close();
	}
	
	//newAvg = new Double((double)total/rs.getFetchSize());
	//System.out.println(newAvg + " " + rs.getFetchSize());
	//newAverage = (Double)(newAverage/((Double)count));
	
	rs.close();
	st.close();
		
	return 1;
  }
  
  private void updateTotal(Connection db) throws SQLException {
	if (questionId == -1) {
		throw new SQLException("Question ID not specified.");
	}
	
	PreparedStatement pst = db.prepareStatement(
		"UPDATE survey_item " +
		"SET total" + quantAns + " = total" + quantAns + "+1 " +
		"WHERE id = ? ");
	int i = 0;
	pst.setInt(++i, this.getQuestionId());
	System.out.println(pst.toString());
	pst.execute();
	pst.close();
  }
  
public int getQuestionId() { return questionId; }
public String getComments() { return comments; }
public int getQuantAns() { return quantAns; }
public String getTextAns() { return textAns; }
public void setQuestionId(int tmp) { this.questionId = tmp; }
public void setComments(String tmp) { this.comments = tmp; }
public void setQuantAns(int tmp) { this.quantAns = tmp; }
public void setTextAns(String tmp) { this.textAns = tmp; }

public void setQuestionId(String tmp) { this.questionId = Integer.parseInt(tmp); }
public void setQuantAns(String tmp) { this.quantAns = Integer.parseInt(tmp); }

  public void insert(Connection db, int enteredBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO survey_answer " +
        "(question_id, comments, quant_ans, text_ans, enteredBy) " +
        "VALUES " +
        "(?, ?, ?, ?, ?) ");
    int i = 0;
    pst.setInt(++i, questionId);
    pst.setString(++i, comments);
    pst.setInt(++i, quantAns);
    pst.setString(++i, textAns);
    pst.setInt(++i, enteredBy);
    
    pst.execute();
    pst.close();
    
    //id = DatabaseUtils.getCurrVal(db, "aurvey_answer_id_seq");
    setNewAverage(db);
    updateTotal(db);
  }

  /**
  public void update(Connection db, int modifiedBy) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE contact_emailaddress " +
        "SET emailaddress_type = ?, email = ?, modifiedby = ?, " +
        "modified = CURRENT_TIMESTAMP " +
        "WHERE emailaddress_id = ? ");
    int i = 0;
    pst.setInt(++i, this.getType());
    pst.setString(++i, this.getEmail());
    pst.setInt(++i, modifiedBy);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }
  */

  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM survey_answer " +
        "WHERE id = ? ");
    int i = 0;
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
  }

}

