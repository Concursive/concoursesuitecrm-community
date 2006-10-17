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
package org.aspcfs.modules.communications.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.database.ConnectionElement;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.website.icelet.HtmlContentPortlet;
import org.aspcfs.modules.website.base.IceletPropertyMap;
import org.aspcfs.modules.website.base.IceletProperty;
import org.aspcfs.modules.website.base.PageRow;
import org.aspcfs.modules.website.base.RowColumn;
import org.aspcfs.modules.website.base.RowColumnList;
import org.aspcfs.modules.website.base.Site;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

/**
 * A stand alone servlet that sends files. Currently it will stream an image
 * file from a protected image library
 *
 * @author mrajkowski
 * @version $Id: ProcessFileItemImage.java,v 1.3 2004/08/04 20:01:57
 *          mrajkowski Exp $
 * @created April 1, 2004
 */
public final class ProcessFileItemImage extends CFSModule {

  public final static String PROCESS_FILE_ITEM_NAME = "processFileItemImages";


  /**
   * Receives a request for a FileItem image and processes it.<br>
   * The user must have access to see the image in their session, then the
   * image is retrieved. Images are stored in the fileLibrary using the cryptic
   * datetime filename without an extension<p>
   * <p/>
   * The url format should be in the following format:<br>
   * <img src="http://ds21.darkhorseventures.com/ProcessFileItemImage.do?id=">
   * <p/>
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    String id = (String) context.getRequest().getParameter("id");
    String path = (String) context.getRequest().getParameter("path");
    String version = (String) context.getRequest().getParameter("version");
    String thumbnail = (String) context.getRequest().getParameter("thumbnail");
    FileItem thisItem = null;
    // Lookup the file, start the download
    Connection db = null;
    try {
      // See if the id is in the user's session
      ArrayList allowedImages = (ArrayList) context.getSession().getAttribute(
          PROCESS_FILE_ITEM_NAME);
      if (allowedImages == null || !allowedImages.contains(
          id + (version != null ? "-" + version : "") + (thumbnail != null ? "TH" : ""))) {
        return ("-none-");
      }
      // Get a database connection using the virtual host context info
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      // Load the fileItem
      FileItem fileItem = new FileItem(db, Integer.parseInt(id));
      // Prepare to download
      String filePath = null;
      Timestamp lastModified = null;
      if (version != null) {
        fileItem.buildVersionList(db);
        FileItemVersion thisVersion = fileItem.getVersion(
            Double.parseDouble(version));
        filePath = this.getPath(context, path) + getDatePath(
            thisVersion.getModified()) + thisVersion.getFilename();
        lastModified = thisVersion.getModificationDate();
      } else {
        filePath = this.getPath(context, path) + getDatePath(
            fileItem.getModified()) + fileItem.getFilename();
        lastModified = fileItem.getModificationDate();
      }
      if (thumbnail != null) {
        filePath += "TH";
      }
      // Finished with connection
      this.freeConnection(context, db);
      db = null;

      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(fileItem.getClientFilename());
      if (fileDownload.fileExists()) {
        if (lastModified != null) {
          fileDownload.setFileTimestamp(lastModified.getTime());
        }
        fileDownload.streamContent(context);
      } else {
        System.err.println(
            "ProcessFileItemImage-> Trying to send a file that does not exist: " + filePath + fileItem.getClientFilename());
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      //System.out.println(e.toString());
      e.printStackTrace(System.out);
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
    return ("-none-");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandStreamImage(ActionContext context) {
    String id = (String) context.getRequest().getParameter("id");
    String path = (String) context.getRequest().getParameter("path");
    String version = (String) context.getRequest().getParameter("version");
    String thumbnail = (String) context.getRequest().getParameter("thumbnail");
    String rowIdString = (String) context.getRequest().getParameter("row");
    FileItem thisItem = null;

		Connection db = null;
		ConnectionElement ce = null;
		if (!"website".equals(path)){
			return ("-none-");
		}
    try {
			AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);

			//TODO:this method gets called twice.. need to create a getter for ConnectionElement in AuthenticationItem
			ce = auth.getConnectionElement(context);
      // TODO: Something is causing FileItemImage to fail... testing this now
      if (1==1) {
        streamImage(context, ce, db, id, path, version, thumbnail);
        return ("-none-");
      }

      //If the image id is already in the session, stream the image and return
      // See if the id is in the user's session
      ArrayList allowedImages = (ArrayList) context.getSession().getAttribute(
          PROCESS_FILE_ITEM_NAME);
			if (allowedImages != null && allowedImages.contains(
				id + (version != null ? "-" + version : "") + (thumbnail != null ? "TH" : ""))) {
				streamImage(context, ce, db, id, path, version, thumbnail);
				return ("-none-");
			}
			int mode = Site.PORTAL_MODE;
			//is user logged in.
			if (this.getUserId(context) > 0){
				mode = Site.EDIT_MODE;
				// check permissions
				if (!hasPermission(context, "website-view")) {
					return ("PermissionError");
				}
				addImageToAllowedImages(context,id, version, thumbnail);
				streamImage(context, ce, db, id, path, version, thumbnail);
			} else {
				//Only portal access hence only enabled icelets should be checked
				PageRow pageRow =  new PageRow(db, Integer.parseInt(rowIdString));
				if (!pageRow.getEnabled()) {
					return ("PermissionError");
				}
				//fetch page rowcolumns for the page row
				RowColumnList rowColumnList = new RowColumnList();
				rowColumnList.setPageRowId(rowIdString);
				rowColumnList.buildList(db);
				if (rowColumnList.size() > 0){
					Iterator rowColumnIterator = rowColumnList.iterator();
					while (rowColumnIterator.hasNext()){
						RowColumn rowColumn = (RowColumn)rowColumnIterator.next();
						IceletPropertyMap iceletPropertyMap = new IceletPropertyMap();
						iceletPropertyMap.setIceletRowColumnId(rowColumn.getId());
						iceletPropertyMap.buildList(db);
						if (iceletPropertyMap.size() == 0){
							continue;
						}
						//icelet property exists
						IceletProperty iceletProperty = (IceletProperty)iceletPropertyMap.get(new Integer(HtmlContentPortlet.PROPERTY_HTMLTEXT));
						//goto next property if html content property does not exist
						if (iceletProperty == null) {
							continue;
						}
						//goto next property if image id does not exist in the value
						if (iceletProperty.getValue().indexOf(id) == -1){
							continue;
						}
						//goto next column if this is not viewable
						if (!RowColumn.isRowColumnViewable(db, rowColumn.getId())){
							continue;
						}
						
						//add image to allowed image and stream if all tests pass
						addImageToAllowedImages(context,id, version, thumbnail);
						streamImage(context, ce, db, id, path, version, thumbnail);
						break;
				 }
				}
			}
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      //System.out.println(e.toString());
      e.printStackTrace(System.out);
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
    return ("-none-");
  }
	
	public static synchronized void addImageToAllowedImages(ActionContext context, String imageId, String version, String thumbnail){
		synchronized (context.getSession()) {
			ArrayList allowedImages = (ArrayList) context.getSession().getAttribute(
					ProcessFileItemImage.PROCESS_FILE_ITEM_NAME);
			if (allowedImages == null) {
				allowedImages = new ArrayList();
				context.getSession().setAttribute(
						ProcessFileItemImage.PROCESS_FILE_ITEM_NAME, allowedImages);
			}
		allowedImages.add(
				imageId + (version != null ? "-" + version : "") + (DatabaseUtils.parseBoolean(thumbnail) ? "TH" : ""));
		}
	}
	
	private void streamImage(ActionContext context, ConnectionElement ce, Connection db, String imageId, String path, String version, String thumbnail) throws Exception {
		// Load the fileItem
		FileItem fileItem = new FileItem(db, Integer.parseInt(imageId));
		String filePath = null;
    Timestamp lastModified = null;
    if (version != null) {
			fileItem.buildVersionList(db);
			FileItemVersion thisVersion = fileItem.getVersion(
					Double.parseDouble(version));
			filePath = this.getPath(context, ce, path) + getDatePath(
					thisVersion.getModified()) + thisVersion.getFilename();
      lastModified = thisVersion.getModificationDate();
    } else {
			filePath = this.getPath(context, ce, path) + getDatePath(
					fileItem.getModified()) + fileItem.getFilename();
      lastModified = fileItem.getModificationDate();
    }
		if (thumbnail != null) {
			filePath += "TH";
		}

		FileDownload fileDownload = new FileDownload();
		fileDownload.setFullPath(filePath);
		fileDownload.setDisplayName(fileItem.getClientFilename());
		if (fileDownload.fileExists()) {
      if (lastModified != null) {
        fileDownload.setFileTimestamp(lastModified.getTime());
      }
      fileDownload.streamContent(context);
		} else {
			System.err.println(
					"ProcessFileItemImage-> Trying to send a file that does not exist: " + filePath + fileItem.getClientFilename());
		}
	}
}

