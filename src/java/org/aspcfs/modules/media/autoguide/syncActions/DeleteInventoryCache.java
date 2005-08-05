/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.media.autoguide.actions;

import org.aspcfs.modules.service.base.CustomActionHandler;
import org.aspcfs.modules.service.base.PacketContext;
import org.aspcfs.modules.service.base.SyncTable;
import org.aspcfs.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * The sync process doesn't handle synchronizing partial tables, so this action
 * is used when a PDA syncs and the owner of an account has changed. In that
 * case, the PDA must request that the server deletes the inventory cache for
 * that account.
 *
 * @author matt rajkowski
 * @version $Id: DeleteInventoryCache.java,v 1.2 2003/05/08 13:50:18
 *          mrajkowski Exp $
 * @created April 29, 2003
 */
public class DeleteInventoryCache implements CustomActionHandler {

  /**
   * Deletes the inventory cache for the given account, and related cache data
   * (not the actual records) when a PDA says to.
   *
   * @param packetContext Description of the Parameter
   * @param db            Description of the Parameter
   * @param values        Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean process(PacketContext packetContext, Connection db, HashMap values) throws SQLException {
    System.out.println("DeleteInventoryCache-> BEGIN");
    //Collect the required properties
    int systemId = packetContext.getAuthenticationItem().getSystemId();
    int clientId = packetContext.getAuthenticationItem().getClientId();
    int clientAccountId = StringUtils.parseInt(
        (String) values.get("accountGuid"), -1);
    //Validate the properties
    if (systemId == -1 || clientId == -1 || clientAccountId == -1) {
      System.out.println("DeleteInventoryCache-> Something is not configured");
      return false;
    }
    //Get the table reference to lookup and delete items from the cache
    SyncTable accountTable = (SyncTable) packetContext.getObjectMap().get(
        "accountList");
    SyncTable accountInventoryTable = (SyncTable) packetContext.getObjectMap().get(
        "accountInventoryList");
    SyncTable optionTable = (SyncTable) packetContext.getObjectMap().get(
        "optionList");
    SyncTable adRunTable = (SyncTable) packetContext.getObjectMap().get(
        "adRunList");
    //Begin retrieving and deleting records
    PreparedStatement pst = null;
    ResultSet rs = null;
    int accountId = -1;
    //Get the record_id of the account
    pst = db.prepareStatement(
        "SELECT record_id " +
        "FROM sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND cuid = ? ");
    pst.setInt(1, clientId);
    pst.setInt(2, accountTable.getId());
    pst.setInt(3, clientAccountId);
    rs = pst.executeQuery();
    if (rs.next()) {
      accountId = rs.getInt("record_id");
    }
    rs.close();
    pst.close();
    if (accountId == -1) {
      return false;
    }
    System.out.println(
        "DeleteInventoryCache-> Found server accountId: " + accountId);
    //For this account, delete the ad_run cache for all related records
    pst = db.prepareStatement(
        "DELETE from sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id IN ( " +
        "SELECT ad_run_id " +
        "FROM autoguide_ad_run " +
        "WHERE inventory_id IN (" +
        "SELECT inventory_id " +
        "FROM autoguide_inventory " +
        "WHERE account_id = ? " +
        ") " +
        ") ");
    pst.setInt(1, clientId);
    pst.setInt(2, adRunTable.getId());
    pst.setInt(3, accountId);
    pst.execute();
    pst.close();
    System.out.println("DeleteInventoryCache-> Ad run cache deleted");
    //For this account, delete the option cache for all related records... these are not cached

    //For this account, delete the inventory cache for all related records
    pst = db.prepareStatement(
        "DELETE from sync_map " +
        "WHERE client_id = ? " +
        "AND table_id = ? " +
        "AND record_id IN (" +
        "SELECT inventory_id " +
        "FROM autoguide_inventory " +
        "WHERE account_id = ? " +
        ")");
    pst.setInt(1, clientId);
    pst.setInt(2, accountInventoryTable.getId());
    pst.setInt(3, accountId);
    pst.execute();
    pst.close();
    System.out.println("DeleteInventoryCache-> Inventory cache deleted");
    //The in-memory cache is no longer valid so blow it out
    packetContext.getClientManager().removeClient(clientId);
    return true;
  }
}

