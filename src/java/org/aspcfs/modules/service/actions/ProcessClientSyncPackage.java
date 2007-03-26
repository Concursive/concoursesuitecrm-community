/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.service.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import org.aspcfs.apps.transfer.reader.CFSXMLReader;
import org.aspcfs.apps.transfer.writer.cfsdatabasewriter.CFSXMLDatabaseWriter;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.service.base.SyncClient;
import org.aspcfs.utils.LoginUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    March 8, 2007
 */
public class ProcessClientSyncPackage extends CFSModule {
  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    ApplicationPrefs applicationPrefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    Exception errorMessage = null;
    Connection db = null;
    Connection dbLookup = null;

    try {
      //Store all uploads into a temporary folder for processing
      String filePath = this.getPath(context, "tmp");

      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(false);
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);

      //Check client authentication
      AuthenticationItem auth = new AuthenticationItem();
      auth.setUsername((String) parts.get("username"));
      auth.setClientId((String) parts.get("clientId"));
      auth.setCode((String) parts.get("code"));
      auth.setLastAnchor((String) parts.get("lastAnchor"));
      auth.setNextAnchor((String) parts.get("nextAnchor"));

      //Initialize the auth by getting the connection element
      ConnectionElement ce = auth.getConnectionElement(context);
      context.getSession().setAttribute("ConnectionElement", ce);

      //TODO: Get rid of using the session for transactions, currently
      //it's being used for the ConnectionElement only.
      //Keep this session low (5 minutes)
      context.getSession().setMaxInactiveInterval(300);

      db = this.getConnection(context);
      dbLookup = this.getConnection(context);

      SyncClient client = new SyncClient(db, auth.getClientId());

      LoginUtils loginUtils = new LoginUtils(db, auth.getUsername(), auth.getCode());
      loginUtils.setApplicationPrefs(applicationPrefs);

      System.out.println("ProcessClientSyncPackage-> Authenticating User");
      if (loginUtils.isUserValid(context, db) && loginUtils.hasHttpApiAccess(db)
           && client.getEnabled()) {
        //Offline client has been successfully authenticated. Determine the file that is being received
        Timestamp lastAnchor = auth.getLastAnchor();
        Timestamp nextAnchor = auth.getNextAnchor();

        if ((Object) parts.get("clientSyncPackage") instanceof FileInfo) {
          FileInfo newFileInfo = (FileInfo) parts.get("clientSyncPackage");
          File uploadedFile = new File(filePath + newFileInfo.getRealFilename());

          System.out.println("ProcessClientSyncPackage (UPLOADED FILE): " + uploadedFile);

          String syncXML = "";
          FileInputStream is = new FileInputStream(uploadedFile);
          File dir = new File(filePath);
          JarInputStream zip = new JarInputStream(is);

          ZipEntry entry = null;
          while ((entry = zip.getNextEntry()) != null) {
            if (entry.isDirectory()) {
              File directory = new File(dir, entry.getName());
              if (!directory.exists()) {
                directory.mkdirs();
              }
            } else {
              syncXML = dir.getAbsolutePath().concat(File.separator) + entry.getName();
              copy(zip, syncXML);
            }
          }
          zip.close();

          boolean processed = false;
          try {
            //Process package
            System.out.println("ProcessClientSyncPackage-> Processing received package");
            CFSXMLReader reader = new CFSXMLReader();
            reader.setXmlDataFile(syncXML);

            CFSXMLDatabaseWriter writer = new CFSXMLDatabaseWriter();
            writer.setClientId(client.getId());
            writer.setConnection(db);
            writer.setConnection(dbLookup);
            writer.initialize();

            if (reader.isConfigured() &&
                writer.isConfigured()) {
              processed = reader.execute(writer);
            }
            writer.close();
          } finally {
            new File(syncXML).delete();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
      freeConnection(context, dbLookup);
      
      context.getSession().removeAttribute("ConnectionElement");
    }
    return ("UploadOK");
  }


  /**
   *  Description of the Method
   *
   * @param  is               Description of the Parameter
   * @param  dest             Description of the Parameter
   * @exception  IOException  Description of the Exception
   */
  private static void copy(InputStream is, String dest) throws IOException {
    byte[] buffer = new byte[1024];
    if (dest.lastIndexOf(File.separator) > -1) {
      String path = dest.substring(0, dest.lastIndexOf(File.separator));
      File directory = new File(path);
      if (!directory.exists()) {
        directory.mkdirs();
      }
    }
    FileOutputStream file = new FileOutputStream(dest);
    int count;
    while ((count = is.read(buffer)) != -1) {
      file.write(buffer, 0, count);
    }
    file.close();
  }
}

