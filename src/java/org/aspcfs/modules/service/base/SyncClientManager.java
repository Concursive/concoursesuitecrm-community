//Copyright 2002 Dark Horse Ventures
package org.aspcfs.modules.service.base;

import java.sql.*;
import java.util.*;

/**
 *  A cache for keeping track of SyncClient data.  This collection contains
 *  client tables for looking up data.
 *
 *@author     matt
 *@created    June 10, 2002
 *@version    $Id$
 */
public class SyncClientManager extends Hashtable {

  public Hashtable addClient(Connection db, int clientId) throws SQLException {
    if (this.containsKey(new Integer(clientId))) {
      return (Hashtable)this.get(new Integer(clientId));
    } else {
      Hashtable client = new Hashtable();
      this.put(new Integer(clientId), client);
      String sql = 
        "SELECT table_id, record_id, cuid " +
        "FROM sync_map " +
        "WHERE client_id = ? " +
        "ORDER BY table_id ";
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(1, clientId);
      ResultSet rs = pst.executeQuery();
      int lastTableId = -1;
      Hashtable lastTable = null;
      while (rs.next()) {
        int tableId = rs.getInt("table_id");
        int recordId = rs.getInt("record_id");
        int cuid = rs.getInt("cuid");
        if (lastTableId != tableId) {
          lastTableId = tableId;
          lastTable = new Hashtable();
          client.put(new Integer(lastTableId), lastTable);
        }
        lastTable.put(new Integer(recordId), new Integer(cuid));
      }
      rs.close();
      pst.close();
      return client;
    }
  }
  
  public void insert(int clientId, int tableId, Integer recordId, Integer cuid) {
    Hashtable clientLookup = (Hashtable)this.get(new Integer(clientId));
    Hashtable tableLookup = (Hashtable)clientLookup.get(new Integer(tableId));
    if (tableLookup == null) {
      tableLookup = new Hashtable();
      clientLookup.put(new Integer(tableId), tableLookup);
    }
    tableLookup.put(recordId, cuid);
  }
  
  public void remove(int clientId, int tableId, Integer recordId) {
    Hashtable clientLookup = (Hashtable)this.get(new Integer(clientId));
    Hashtable tableLookup = (Hashtable)clientLookup.get(new Integer(tableId));
    if (tableLookup != null) {
      tableLookup.remove(recordId);
    }
  }
}

