//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class Revenue extends GenericBean {

  private int id = -1;
  private int orgId = -1;
  private int transactionId = -1;
  private int month = -1;
  private int year = -1;
  private double amount = 0;
  private int type = -1;
  private int owner = -1;
  private String description = "";
  
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private java.sql.Timestamp entered = null;
  
  private String enteredByName = "";
  private String modifiedByName = "";
  private String typeName = "";

  public Revenue() { }
  
  public Revenue(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
 
  public Revenue(Connection db, String revenueId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT r.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, rt.description as typename " +
        "FROM revenue r " +
        "LEFT JOIN contact ct_eb ON (r.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (r.modifiedby = ct_mb.user_id) " +
	"LEFT JOIN lookup_revenue_types rt ON (r.type = rt.code) " +
        "WHERE r.id > -1 ");

    if (revenueId != null && !revenueId.equals("")) {
      sql.append("AND r.id = " + revenueId + " ");
    } else {
      throw new SQLException("Revenue ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Revenue record not found.");
    }
    rs.close();
    st.close();
  }

  public String getEnteredString() {
    try {
      return DateFormat.getDateInstance(DateFormat.SHORT).format(entered);
    } catch (NullPointerException e) {
    }
    return ("");
  }

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
  
  public int getId() { return id; }
public int getOrgId() { return orgId; }
public int getTransactionId() { return transactionId; }
public int getMonth() { return month; }
public int getYear() { return year; }
public double getAmount() { return amount; }
public String getDescription() { return description; }
public int getEnteredBy() { return enteredBy; }
public int getModifiedBy() { return modifiedBy; }
public java.sql.Timestamp getModified() { return modified; }
public java.sql.Timestamp getEntered() { return entered; }
public String getEnteredByName() { return enteredByName; }
public String getModifiedByName() { return modifiedByName; }
public String getTypeName() { return typeName; }
public void setId(int tmp) { this.id = tmp; }
public void setId(String tmp) { this.id = Integer.parseInt(tmp); }
public void setOrgId(int tmp) { this.orgId = tmp; }
public void setOrgId(String tmp) { this.orgId = Integer.parseInt(tmp); }
public void setTransactionId(int tmp) { this.transactionId = tmp; }
public void setTransactionId(String tmp) { this.transactionId = Integer.parseInt(tmp); }
public void setMonth(int tmp) { this.month = tmp; }
public void setMonth(String tmp) { this.month = Integer.parseInt(tmp); }
public void setYear(int tmp) { this.year = tmp; }
public void setYear(String tmp) { this.year = Integer.parseInt(tmp); }
public void setAmount(double tmp) { this.amount = tmp; }
public void setDescription(String tmp) { this.description = tmp; }
public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
public void setEnteredByName(String tmp) { this.enteredByName = tmp; }
public void setModifiedByName(String tmp) { this.modifiedByName = tmp; }
public void setTypeName(String tmp) { this.typeName = tmp; }
public void setType(int tmp) { this.type = tmp; }
public void setOwner(int tmp) { this.owner = tmp; }
public void setType(String tmp) { this.type = Integer.parseInt(tmp); }
public void setOwner(String tmp) { this.owner = Integer.parseInt(tmp); }
public int getType() { return type; }
public int getOwner() { return owner; }

	public boolean insert(Connection db) throws SQLException {
		StringBuffer sql = new StringBuffer();
  
		sql.append(
			"INSERT INTO survey " +
			"(org_id, transaction_id, month, year, amount, type, owner, description, enteredBy, modifiedBy) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		try {
			db.setAutoCommit(false);
		int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, orgId);
			pst.setInt(++i, transactionId);
			pst.setInt(++i, month);
			pst.setInt(++i, year);
			pst.setDouble(++i, amount);
			pst.setInt(++i, type);
			pst.setInt(++i, owner);
			pst.setString(++i, description);
			pst.setInt(++i, enteredBy);
			pst.setInt(++i, modifiedBy);
			pst.execute();
			pst.close();
		
		id = DatabaseUtils.getCurrVal(db, "revenue_id_seq");
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
			st.executeUpdate("DELETE FROM revenue WHERE id = " + this.getId());
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
      throw new SQLException("Revenue ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE revenue " +
        "SET month = ?, year = ?, amount = ?, owner = ?, description = ?, " +
        "type = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE id = ? ");
    //if (!override) {
    //  sql.append("AND modified = ? ");
    //}

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getMonth());
    pst.setInt(++i, this.getYear());
    pst.setDouble(++i, this.getAmount());
    pst.setInt(++i, this.getOwner());
    pst.setString(++i, this.getDescription());
    pst.setInt(++i, this.getType());
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
	id = rs.getInt("id");
	orgId = rs.getInt("org_id");
	transactionId = rs.getInt("transaction_id");
	month = rs.getInt("month");
	year = rs.getInt("year");
	amount = rs.getDouble("amount");
	type = rs.getInt("type");
	owner = rs.getInt("owner");
	description = rs.getString("description");
	
	entered = rs.getTimestamp("entered");
	enteredBy = rs.getInt("enteredby");
	modified = rs.getTimestamp("modified");
	modifiedBy = rs.getInt("modifiedby");
	
	enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
	modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));
	typeName = rs.getString("typename");
  }
  
}

