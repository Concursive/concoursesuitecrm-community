//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class RevenueDetail extends GenericBean {

  private int id = -1;
  private int revenueId = -1;
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

  public RevenueDetail() { }
  
  public RevenueDetail(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
 
  public RevenueDetail(Connection db, String revenueDetailId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT rd.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, rdt.description as typename " +
        "FROM revenue_detail rd " +
        "LEFT JOIN contact ct_eb ON (rd.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (rd.modifiedby = ct_mb.user_id) " +
	"LEFT JOIN lookup_revenuedetail_types rdt ON (rd.type = rdt.code) " +
        "WHERE rd.id > -1 ");

    if (revenueDetailId != null && !revenueDetailId.equals("")) {
      sql.append("AND rd.id = " + revenueDetailId + " ");
    } else {
      throw new SQLException("Revenue Detail ID not specified.");
    }

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      st.close();
      throw new SQLException("Revenue Detail record not found.");
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
public int getRevenueId() { return revenueId; }
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
public void setRevenueId(int tmp) { this.revenueId = tmp; }
public void setRevenueId(String tmp) { this.revenueId = Integer.parseInt(tmp); }
public void setAmount(double tmp) { this.amount = tmp; }
public void setDescription(String tmp) { this.description = tmp; }
public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }
public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
public void setEnteredByName(String tmp) { this.enteredByName = tmp; }
public void setModifiedByName(String tmp) { this.modifiedByName = tmp; }
public void setTypeName(String tmp) { this.typeName = tmp; }
public int getType() { return type; }
public int getOwner() { return owner; }
public void setType(int tmp) { this.type = tmp; }
public void setOwner(int tmp) { this.owner = tmp; }

  public boolean insert(Connection db) throws SQLException {
		StringBuffer sql = new StringBuffer();
  
		sql.append(
			"INSERT INTO revenue_detail " +
			"(revenue_id, amount, type, owner, description, enteredBy, modifiedBy) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?) ");
		try {
			db.setAutoCommit(false);
		int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, revenueId);
			pst.setDouble(++i, amount);
			pst.setInt(++i, type);
			pst.setInt(++i, owner);
			pst.setString(++i, description);
			pst.setInt(++i, enteredBy);
			pst.setInt(++i, modifiedBy);
			pst.execute();
			pst.close();
		
		id = DatabaseUtils.getCurrVal(db, "revenue_detail_id_seq");
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
			st.executeUpdate("DELETE FROM revenue_detail WHERE id = " + this.getId());
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
      throw new SQLException("Revenue Detail ID was not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append(
        "UPDATE revenue " +
        "SET amount = ?, owner = ?, description = ?, " +
        "type = ?, " +
        "modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE id = ? ");
    //if (!override) {
    //  sql.append("AND modified = ? ");
    //}

    int i = 0;
    pst = db.prepareStatement(sql.toString());
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
	revenueId = rs.getInt("revenue_id");
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

