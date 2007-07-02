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
package org.aspcfs.modules.communications.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.documents.base.DocumentStore;

import java.sql.Connection;

/**
 * A stand alone servlet that sends files. Currently it will stream an image
 * file from a protected image library
 *
 * @author Olga.Kaptyug
 * @version 
 * 
 * @created Jan 22, 2007
 */
public final class ProcessFileItem extends CFSModule {

  public final static String PROCESS_FILE_ITEM = "processFileItem";


  /**
   * Receives a request for a FileItem image and processes it.<br>
   * The user must have access to see the image in their session, then the
   * image is retrieved. Images are stored in the fileLibrary using the cryptic
   * datetime filename without an extension<p>
   * <p/>
   * The url format should be in the following format:<br>
   * <img src="http://ds21.darkhorseventures.com/ProcessFileItem.do?id=">
   * <p/>
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    String documentStoreId = (String) context.getRequest().getParameter(
        "documentStoreId");
    String itemId = (String) context.getRequest().getParameter("fid");
    String view = (String) context.getRequest().getParameter("view");
    FileItem thisItem = null;
    Connection db = null;
    try {
      db = getConnection(context);
      DocumentStore thisDocumentStore = new DocumentStore(
          db, Integer.parseInt(documentStoreId));
     
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), thisDocumentStore.getId(), Constants.DOCUMENTS_DOCUMENTS);
      if (!thisDocumentStore.getPublicStore() && thisItem!=null) {
        if (getUserId(context) == -1 ) {
          context.getRequest().setAttribute("Error", "You have no permissions to acess this item.");
          return "SystemError";
        } else if (!hasDocumentStoreAccess(context, db,
              thisDocumentStore,
              "documentcenter-documents-files-download")) {
                        // When the user is logged in, and the store is not public, he must have access to the document store
               context.getRequest().setAttribute("Error", "You have no permissions to acess this item.");
                        return "SystemError";
            }
      }

      context.getRequest().setAttribute("documentStore", thisDocumentStore);
      context.getRequest().setAttribute(
          "IncludeSection", ("file_library").toLowerCase());
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    //Start the download
    try {
        FileItem itemToDownload = thisItem;
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "documents") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          if (view != null && "true".equals(view)) {
            fileDownload.setFileTimestamp(thisItem.getModificationDate().getTime());
            fileDownload.streamContent(context);
          } else {
            fileDownload.sendFile(context);
          }
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          System.err.println(
              "PMF-> Trying to send a file that does not exist");
        }
      } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

