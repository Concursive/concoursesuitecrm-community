//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;
import com.darkhorseventures.utils.ObjectUtils;

public class SyncClientMap {

  private int id = -1;
  private int clientId = -1;
  private int tableId = -1;
  private int recordId = -1;
  private String clientUniqueId = null;
  private boolean complete = false;
  
  public SyncClientMap() { }

  public void setId(int tmp) { this.id = tmp; }
  public void setClientId(int tmp) { this.clientId = tmp; }
  public void setTableId(int tmp) { this.tableId = tmp; }
  public void setRecordId(int tmp) { this.recordId = tmp; }
  public void setClientUniqueId(String tmp) { this.clientUniqueId = tmp; }
  public void setComplete(boolean tmp) { this.complete = tmp; }
  
  public int getId() { return id; }
  public int getClientId() { return clientId; }
  public int getTableId() { return tableId; }
  public int getRecordId() { return recordId; }
  public String getClientUniqueId() { return clientUniqueId; }
  public boolean getComplete() { return complete; }

  public boolean insert(Connection db) throws SQLException {
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO sync_map " +
        "(client_id, table_id, record_id, cuid, complete) " +
        "VALUES (?, ?, ?, ?, ?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, clientId);
    pst.setInt(++i, tableId);
    pst.setInt(++i, recordId);
    pst.setString(++i, clientUniqueId);
    pst.setBoolean(++i, complete);
    pst.execute();
    pst.close();

    id = DatabaseUtils.getCurrVal(db, "sync_map_map_id_seq");
    return true;
  }


  public boolean delete(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID was not specified");
    }

    PreparedStatement pst = null;
    //Delete related records (mappings)

    //Delete the record
    int recordCount = 0;
    pst = db.prepareStatement(
        "DELETE FROM sync_map " +
        "WHERE map_id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();

    if (recordCount == 0) {
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int updateStatus(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Record ID was not specified");
    }

    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE sync_map " +
        "SET complete = ? " +
        "WHERE map_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setBoolean(++i, complete);
    pst.setInt(++i, id);
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("map_id");
    clientId = rs.getInt("client_id");
    tableId = rs.getInt("table_id");
    recordId = rs.getInt("record_id"); 
    clientUniqueId = rs.getString("cuid");
    complete = rs.getBoolean("complete");
  }

}

