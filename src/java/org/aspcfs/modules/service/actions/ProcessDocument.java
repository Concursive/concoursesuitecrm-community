/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security and implements its own
 */
package org.aspcfs.modules.service.actions;

import org.aspcfs.modules.actions.CFSModule;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.base.Constants;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;

/**
 *  Allows a client to upload a file using HTTP/S for any of the modules...
 *  requires authentication
 *
 *@author     matt rajkowski
 *@created    October 9, 2002
 *@version    $Id: ProcessDocument.java,v 1.4 2003/01/13 22:01:24 mrajkowski Exp
 *      $
 */
public final class ProcessDocument extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;
    HashMap errors = new HashMap();

    boolean recordInserted = false;
    try {
      //Store all uploads into a temporary folder for processing
      String filePath = this.getPath(context, "tmp");

      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(false);
      //TODO: multiPart.setExtensionId(...some unique id must go here...);

      HashMap parts = multiPart.parseData(
          context.getRequest().getInputStream(), "---------------------------", filePath);

      //Check client authentication
      AuthenticationItem auth = new AuthenticationItem();
      auth.setId((String) parts.get("id"));
      auth.setCode((String) parts.get("code"));
      auth.setSystemId((String) parts.get("systemId"));
      db = auth.getConnection(context);

      if (db == null) {
        errors.put("authError", "No authorization");
      } else {
        //For the user's filelibrary path
        context.getSession().setAttribute("ConnectionElement", auth.getConnectionElement(context));
      }

      String type = (String) parts.get("type");
      if (type == null) {
        errors.put("typeError", "Object type not specified");
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
        errors.put("idError", "Object id was not specified for uploaded file");
      }
      String enteredBy = (String) parts.get("enteredBy");
      if (enteredBy == null) {
        errors.put("enteredByError", "UserId was not specified");
      }

      String subject = (String) parts.get("subject");
      String folderId = (String) parts.get("folderId");

      if ((Object) parts.get("file1") instanceof FileInfo) {

        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("file1");
        File uploadedFile = new File(filePath + newFileInfo.getRealFilename());

        if (errors.size() > 0) {
          uploadedFile.delete();
        } else {
          //Move the file from tmp to the generated 'type' path
          String finalFilePath = newFilePath + this.getDatePath(newFileInfo.getRealFilename());
          File renamedPath = new File(finalFilePath);
          renamedPath.mkdirs();
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ProcessDocument-> Final path: " + finalFilePath);
          }
          File renamedFile = new File(finalFilePath + newFileInfo.getRealFilename());
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
          recordInserted = thisItem.insert(db);
          if (!recordInserted) {
            processErrors(context, thisItem.getErrors());
          }
        }
      } else {
        recordInserted = false;
        errors.put("actionError", "File not found in posting");
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
      context.getSession().removeAttribute("ConnectionElement");
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("UploadOK");
      } else {
        processErrors(context, errors);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

}

