//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.StringTokenizer;
import java.sql.*;
import com.darkhorseventures.utils.DateUtils;
import com.darkhorseventures.utils.DatabaseUtils;

public class AccountTypeLevel {

  int orgId = -1;
  int typeId = -1;
  int level = -1;
  java.sql.Timestamp entered = null;
  java.sql.Timestamp modified = null;

  public AccountTypeLevel() { }
  
  public AccountTypeLevel(Connection db, String id) throws SQLException {
          queryRecord(db, Integer.parseInt(id));
  }
  
  public AccountTypeLevel(Connection db, int id) throws SQLException {
          queryRecord(db, id);
  }
  
public int getOrgId() {
	return orgId;
}
public void setOrgId(int orgId) {
	this.orgId = orgId;
}

public void setOrgId(String orgId) {
	this.orgId = Integer.parseInt(orgId);
}

public void setTypeId(int tmp) { this.typeId = tmp; }
public void setTypeId(String tmp) { this.typeId = Integer.parseInt(tmp); }

public void setLevel(int tmp) { this.level = tmp; }
public void setLevel(String tmp) { this.level = Integer.parseInt(tmp); }

public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }

  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }
  
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }

public int getTypeId() { return typeId; }
public int getLevel() { return level; }
public java.sql.Timestamp getEntered() { return entered; }
public java.sql.Timestamp getModified() { return modified; }

  
  public void queryRecord(Connection db, int id) throws SQLException {
          if (id == -1) {
                  throw new SQLException("Account Type Level entry not found.");
          }
          
          PreparedStatement pst = db.prepareStatement("SELECT a.* " +
                "FROM account_type_levels a " +
                "WHERE a.org_id = ? and a.type_id = ? ");
          pst.setInt(1, orgId);
          pst.setInt(2, typeId);
          ResultSet rs = pst.executeQuery();
        if (rs.next()) {
                buildRecord(rs);
        } else {
            rs.close();
            throw new SQLException("Account Type Level entry not found.");
        }
            rs.close();
  }  
  
  public boolean insert(Connection db) throws SQLException {
          
    StringBuffer sql = new StringBuffer();

    try {
      db.setAutoCommit(false);
      sql.append(
          "INSERT INTO account_type_levels (org_id, type_id, ");
        if (entered != null) {
                sql.append("entered, ");
        }
        if (modified != null) {
                sql.append("modified, ");
        }
      sql.append("level) ");
      sql.append("VALUES (?, ?, ");
                if (entered != null) {
                        sql.append("?, ");
                }
                if (modified != null) {
                        sql.append("?, ");
                }
      sql.append("?) ");
      
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (orgId > -1) {
	      pst.setInt(++i, this.getOrgId());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (typeId > -1) {
	      pst.setInt(++i, this.getTypeId());
      } else {
	      pst.setNull(++i, java.sql.Types.INTEGER);
      }
      if (entered != null) {
              pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
              pst.setTimestamp(++i, modified);
      }      
      pst.setInt(++i, this.getLevel());
        
      pst.execute();
      pst.close();

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

  protected void buildRecord(ResultSet rs) throws SQLException {
    orgId = rs.getInt("org_id");
    if (rs.wasNull()) {
            orgId = -1;
    }    
    typeId = rs.getInt("type_id");
    if (rs.wasNull()) {
            typeId = -1;
    }
    level = rs.getInt("level");
    entered = rs.getTimestamp("entered");
    modified = rs.getTimestamp("modified");
  }

}

