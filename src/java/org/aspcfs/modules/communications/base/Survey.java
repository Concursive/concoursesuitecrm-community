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
  private int length = -1;
  private int type = -1;
  
  private String q1 = "";
  private String q2 = "";
  private String q3 = "";
  private String q4 = "";
  private String q5 = "";
  private String q6 = "";
  private String q7 = "";
  private String q8 = "";
  private String q9 = "";
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = true;

  public Survey() { }

  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }
  
public int getId() { return id; }
public String getName() { return name; }
public String getDescription() { return description; }
public String getIntro() { return intro; }
public int getLength() { return length; }
public int getType() { return type; }
public String getQ1() { return q1; }
public String getQ2() { return q2; }
public String getQ3() { return q3; }
public String getQ4() { return q4; }
public String getQ5() { return q5; }
public String getQ6() { return q6; }
public String getQ7() { return q7; }
public String getQ8() { return q8; }
public String getQ9() { return q9; }
public int getEnteredBy() { return enteredBy; }
public int getModifiedBy() { return modifiedBy; }
public java.sql.Timestamp getModified() { return modified; }
public java.sql.Timestamp getEntered() { return entered; }
public boolean getEnabled() { return enabled; }
public void setId(int tmp) { this.id = tmp; }
public void setName(String tmp) { this.name = tmp; }
public void setDescription(String tmp) { this.description = tmp; }
public void setIntro(String tmp) { this.intro = tmp; }
public void setLength(int tmp) { this.length = tmp; }
public void setLength(String tmp) {
	this.length = Integer.parseInt(tmp);
}
public void setType(int tmp) { this.type = tmp; }
public void setType(String tmp) {
	this.type = Integer.parseInt(tmp);
}
public void setQ1(String tmp) { this.q1 = tmp; }
public void setQ2(String tmp) { this.q2 = tmp; }
public void setQ3(String tmp) { this.q3 = tmp; }
public void setQ4(String tmp) { this.q4 = tmp; }
public void setQ5(String tmp) { this.q5 = tmp; }
public void setQ6(String tmp) { this.q6 = tmp; }
public void setQ7(String tmp) { this.q7 = tmp; }
public void setQ8(String tmp) { this.q8 = tmp; }
public void setQ9(String tmp) { this.q9 = tmp; }
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

}

