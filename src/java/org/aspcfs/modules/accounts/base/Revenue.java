//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.theseus.actions.*;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.utils.DateUtils;

public class Revenue extends GenericBean {

  private int id = -1;
  private int orgId = -1;
  private int transactionId = -1;
  private int month = -1;
  private int year = 0;
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
  
  private String ownerNameFirst = "";
  private String ownerNameLast = "";
  
  private String typeName = "";
  private String orgName = "";

  public Revenue() { }
  
  public Revenue(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }
 
  public Revenue(Connection db, String revenueId) throws SQLException {
          queryRecord(db, Integer.parseInt(revenueId));
  }
  
  public Revenue(Connection db, int revenueId) throws SQLException {
          queryRecord(db, revenueId);
  }  
          
  public void queryRecord(Connection db, int revenueId) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT r.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, ct_own.namelast as own_namelast, ct_own.namefirst as own_namefirst, rt.description as typename, o.name as orgname " +
        "FROM revenue r " +
        "LEFT JOIN contact ct_eb ON (r.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (r.modifiedby = ct_mb.user_id) " +
	"LEFT JOIN contact ct_own ON (r.owner = ct_own.user_id) " +
	"LEFT JOIN organization o ON (r.org_id = o.org_id) " +
	"LEFT JOIN lookup_revenue_types rt ON (r.type = rt.code) " +
        "WHERE r.id > -1 ");

    if (revenueId > -1) {
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

  public String getAmountValue() {
    double value_2dp = (double) Math.round(amount * 100.0) / 100.0;
    String toReturn = "" + value_2dp;
    if (toReturn.endsWith(".0")) {
      toReturn = toReturn.substring(0, toReturn.length() - 2);
    } 
    
    if (Integer.parseInt(toReturn) == 0) 
        toReturn = "";
	
    return toReturn;
  }
  
  public String getAmountCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(amount);
    return amountOut;
  }
  
  protected boolean isValid(Connection db) throws SQLException {
    errors.clear();

    if (description == null || description.trim().equals("")) {
      errors.put("descriptionError", "Description cannot be left blank");
    }

    if (amount == 0) {
      errors.put("amountError", "Amount needs to be entered");
    }
    
    if (hasErrors()) {
      return false;
    } else {
      return true;
    }
  }
public String getOrgName() {
	return orgName;
}
public void setOrgName(String orgName) {
	this.orgName = orgName;
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

public void setAmount(double tmp) { 
	this.amount=tmp;
}

public void setAmount(String tmp) { 
	tmp = replace(tmp, ",", "");
	tmp = replace(tmp, "$", "");
	this.amount = Double.parseDouble(tmp);
}

public String getMonthName() {
	String r = "";
	
	switch (month) {
            case 1:  r = "January"; break;
            case 2:  r = "February"; break;
            case 3:  r = "March"; break;
            case 4:  r = "April"; break;
            case 5:  r = "May"; break;
            case 6:  r = "June"; break;
            case 7:  r = "July"; break;
            case 8:  r = "August"; break;
            case 9:  r = "September"; break;
            case 10: r = "October"; break;
            case 11: r = "November"; break;
            case 12: r = "December"; break;
        }
	
	return r;
}

public void setDescription(String tmp) { this.description = tmp; }
public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
public void setEnteredBy(String tmp) { this.enteredBy = Integer.parseInt(tmp); }
public void setModifiedBy(String tmp) { this.modifiedBy = Integer.parseInt(tmp); }

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
public void setOwnerNameFirst(String tmp) { this.ownerNameFirst = tmp; }
public void setOwnerNameLast(String tmp) { this.ownerNameLast = tmp; }
public String getOwnerNameFirst() { return ownerNameFirst; }
public String getOwnerNameLast() { return ownerNameLast; }

  /**
   *  Sets the entered attribute of the Ticket object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Ticket object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }

public String getOwnerName() {
	return (Contact.getNameLastFirst(this.getOwnerNameLast(), this.getOwnerNameFirst()));
}

public String getOwnerNameAbbr() {
	return (this.getOwnerNameFirst().charAt(0) + ". " + this.getOwnerNameLast());
}

  public boolean insert(Connection db, ActionContext context) throws SQLException {
    if (insert(db)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }
  
  public int update(Connection db, ActionContext context) throws SQLException {
    int oldId = -1;
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
      "SELECT owner " +
      "FROM revenue " +
      "WHERE id = " + this.getId());
    if (rs.next()) {
      oldId = rs.getInt("owner");
    }
    rs.close();
    st.close();
    int result = update(db);
    if (result == 1) {
      invalidateUserData(context);
      if (oldId != this.getOwner()) {
        invalidateUserData(context, oldId);
      }
    }
    return result;
  }
  
    public boolean delete(Connection db, ActionContext context) throws SQLException {
    if (delete(db)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }

	public boolean insert(Connection db) throws SQLException {
		    if (!isValid(db)) {
			    return false;
		    }
    
		StringBuffer sql = new StringBuffer();
  
		sql.append(
			"INSERT INTO revenue " +
			"(org_id, transaction_id, month, year, amount, type, owner, description, ");
                if (entered != null) {
                        sql.append("entered, ");
                }
                if (modified != null) {
                        sql.append("modified, ");
                }
      sql.append("enteredBy, modifiedBy ) ");      
      sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ");
                if (entered != null) {
                        sql.append("?, ");
                }
                if (modified != null) {
                        sql.append("?, ");
                }
      sql.append("?, ?) ");
		try {
			db.setAutoCommit(false);
		int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, orgId);
			pst.setInt(++i, transactionId);
			pst.setInt(++i, month);
			pst.setInt(++i, year);
			pst.setDouble(++i, amount);
			if (type > -1) {
	      pst.setInt(++i, this.getType());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
			if (owner > -1) {
	      pst.setInt(++i, this.getOwner());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.setString(++i, description);
        if (entered != null) {
                pst.setTimestamp(++i, entered);
        }
        if (modified != null) {
                pst.setTimestamp(++i, modified);
        }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
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
    
    		    if (!isValid(db)) {
			    return -1;
		    }

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
       if (type > -1) {
	      pst.setInt(++i, this.getType());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
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
  if (rs.wasNull()) {
          type = -1;
  }
	owner = rs.getInt("owner");
  if (rs.wasNull()) {
          owner = -1;
  }
	description = rs.getString("description");
	
	entered = rs.getTimestamp("entered");
	enteredBy = rs.getInt("enteredby");
	modified = rs.getTimestamp("modified");
	modifiedBy = rs.getInt("modifiedby");
	
	enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
	modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));
	
	ownerNameFirst = rs.getString("own_namefirst");
	ownerNameLast = rs.getString("own_namelast");
	
	typeName = rs.getString("typename");
	orgName = rs.getString("orgname");
  }
  
    /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@since
   */
  public void invalidateUserData(ActionContext context) {
    invalidateUserData(context, owner);
  }
  
    
    /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@param  userId   Description of Parameter
   *@since
   */
  public void invalidateUserData(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.getHierarchyList().getUser(userId).setRevenueIsValid(false, true);
  }
  
}

