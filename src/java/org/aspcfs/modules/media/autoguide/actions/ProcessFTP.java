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

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.service.base.SyncClientMap;
import org.aspcfs.modules.service.base.SyncTable;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.media.autoguide.base.*;
import org.aspcfs.utils.ImageUtils;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import com.zeroio.iteam.base.*;
import com.isavvix.tools.*;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 *  Auto Guide FTP module which processes uploaded pictures and moves them into
 *  CFS web site
 *
 *@author     matt rajkowski
 *@created    June 23, 2002
 *@version    $Id$
 */
public final class ProcessFTP extends CFSModule {

  /**
   *  Start the process when triggered by a client app
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    try {
      AuthenticationItem auth = new AuthenticationItem();

      String filePath = this.getPath(context, auth.getConnectionElement(context), "autoguide");
      String ftpPath = filePath + "ftp" + fs;

      //Get a list of .JPGs from the ftpPath
      File stub = new File(ftpPath);
      File[] files = stub.listFiles();
      File datedPath = null;

      if (files.length > 0) {
        //Prepare the destination for new files
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
        String datePathToUse1 = formatter1.format(new java.util.Date());
        SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
        String datePathToUse2 = formatter2.format(new java.util.Date());
        datedPath = new File(filePath + datePathToUse1 + fs + datePathToUse2 + fs);
        datedPath.mkdirs();

        db = auth.getConnection(context, false);
      }

      for (int i = 0; i < files.length; i++) {
        File fileToMove = files[i];
        String filename = fileToMove.getName();
        if (filename.endsWith(".jpg") || filename.endsWith(".JPG")) {
          System.out.println(filename);
          String clientId = filename.substring(0, filename.indexOf("-"));
          String inventoryId = filename.substring(filename.indexOf("-") + 1, filename.indexOf("."));

          //Look up the server's id from the client's id
          SyncClientMap thisClientMap = new SyncClientMap();
          thisClientMap.setClientId(Integer.parseInt(clientId));
          int tableId = SyncTable.lookupTableId(db, 2, "org.aspcfs.modules.media.autoguide.base.InventoryList");
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ProcessFTP-> TableID = " + tableId);
          }
          int recordId = thisClientMap.lookupServerId(db, tableId, inventoryId);
          if (recordId > -1) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String datedFilename = formatter.format(new java.util.Date()) + i;
            System.out.println(datedPath.getPath());
            File thisFile = new File(datedPath.getPath() + fs + datedFilename);
            fileToMove.renameTo(thisFile);

            //Delete the previous files associated to this inventory id
            FileItemList previousFiles = new FileItemList();
            previousFiles.setLinkModuleId(Constants.AUTOGUIDE);
            previousFiles.setLinkItemId(recordId);
            previousFiles.buildList(db);
            previousFiles.delete(db, filePath);

            //Update the database with the new file
            FileItem thisItem = new FileItem();
            thisItem.setLinkModuleId(Constants.AUTOGUIDE);
            thisItem.setLinkItemId(recordId);
            thisItem.setEnteredBy(-1);
            thisItem.setModifiedBy(-1);
            thisItem.setSubject("vehicle photo");
            thisItem.setClientFilename(filename);
            thisItem.setFilename(datedFilename);
            thisItem.setVersion(1.0);
            thisItem.setSize((int) thisFile.length());
            boolean recordInserted = thisItem.insert(db);

            if (!recordInserted) {
              processErrors(context, thisItem.getErrors());
            } else {
              //Create a thumbnail
              File thumbnail = new File(thisFile.getPath() + "TH");
              ImageUtils.saveThumbnail(thisFile, thumbnail, 133d, -1d);

              //Store thumbnail in database
              thisItem.setSubject("thumbnail");
              thisItem.setFilename(datedFilename + "TH");
              thisItem.setVersion(1.1d);
              thisItem.setSize((int) thumbnail.length());
              recordInserted = thisItem.insertVersion(db);
            }
          } else {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("ProcessFTP-> Matching client record not found");
            }
          }
        }
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      if (db != null) {
        freeConnection(context, db);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProcessFTP-> Finished");
    }
    return "-none-";
  }
}

