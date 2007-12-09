/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.service.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.base.AuthenticationItem;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Allows a client to upload a file using HTTP/S for any of the modules...
 * requires authentication
 *
 * @author matt rajkowski
 * @version $Id: ProcessDocument.java,v 1.4 2003/01/13 22:01:24 mrajkowski Exp
 *          $
 * @created October 9, 2002
 */
public final class ProcessDocument extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;
    HashMap errors = new HashMap();
    boolean isValid = false;
    boolean recordInserted = false;
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
      auth.setId((String) parts.get("id"));
      auth.setCode((String) parts.get("code"));
      auth.setSystemId((String) parts.get("systemId"));
      db = auth.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);
      if (db == null) {
        errors.put(
            "authError", systemStatus.getLabel(
                "object.validation.noAuthorization"));
      } else {
        //For the user's filelibrary path
        context.getSession().setAttribute(
            "ConnectionElement", auth.getConnectionElement(context));
      }
      String type = (String) parts.get("type");
      if (type == null) {
        errors.put(
            "typeError", systemStatus.getLabel(
                "object.validation.objectTypeRequired"));
      }
      String id = null;
      String newFilePath = null;
      int linkModuleId = -1;
      if ("ticket".equals(type)) {
        id = (String) parts.get("tid");
        newFilePath = this.getPath(context, "tickets");
        linkModuleId = Constants.DOCUMENTS_TICKETS;
      }
      if (id == null) {
        errors.put(
            "idError", systemStatus.getLabel(
                "object.validation.objectIdNotSpecified"));
      }
      String enteredBy = (String) parts.get("enteredBy");
      if (enteredBy == null) {
        errors.put(
            "enteredByError", systemStatus.getLabel(
                "object.validation.required"));
      }

      String subject = (String) parts.get("subject");
      String folderId = (String) parts.get("folderId");

      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }
      if ((Object) parts.get("file1") instanceof FileInfo) {

        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("file1");
        File uploadedFile = new File(filePath + newFileInfo.getRealFilename());

        if (errors.size() > 0) {
          uploadedFile.delete();
        } else {
          //Move the file from tmp to the generated 'type' path
          String finalFilePath = newFilePath + getDatePath(
              newFileInfo.getRealFilename());
          File renamedPath = new File(finalFilePath);
          renamedPath.mkdirs();
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "ProcessDocument-> Final path: " + finalFilePath);
          }
          File renamedFile = new File(
              finalFilePath + newFileInfo.getRealFilename());
          uploadedFile.renameTo(renamedFile);

          //Insert record into database
          FileItem thisItem = new FileItem();
          thisItem.setLinkModuleId(linkModuleId);
          thisItem.setLinkItemId(id);
          thisItem.setEnteredBy(enteredBy);
          thisItem.setModifiedBy(enteredBy);
          //thisItem.setFolderId(Integer.parseInt(folderId));
          thisItem.setSubject(subject);
          thisItem.setClientFilename(newFileInfo.getClientFileName());
          thisItem.setFilename(newFileInfo.getRealFilename());
          thisItem.setVersion(1.0);
          thisItem.setSize(newFileInfo.getSize());
          isValid = this.validateObject(context, db, thisItem);
          if (isValid) {
            recordInserted = thisItem.insert(db);
          }
        }
      } else {
        recordInserted = false;
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
      context.getSession().removeAttribute("ConnectionElement");
    }
    if (recordInserted) {
      return ("UploadOK");
    } else {
      processErrors(context, errors);
      return ("UserError");
    }
  }
}

