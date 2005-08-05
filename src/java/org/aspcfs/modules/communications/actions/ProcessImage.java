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
import com.zeroio.webutils.FileDownload;
import org.aspcfs.modules.actions.CFSModule;

import java.util.StringTokenizer;

/**
 * A stand alone servlet that sends files. Currently it will stream an image
 * file from a customer's protected image library
 *
 * @author mrajkowski
 * @version $Id$
 * @created January 23, 2002
 */
public final class ProcessImage extends CFSModule {

  /**
   * Receives a request for a customer's image and processes it.<br>
   * Images are stored in the fileLibrary using the cryptic datetime filename
   * without an extension<p>
   * <p/>
   * The url format should be any of the following 3:<br>
   * <img src="http://ds21.darkhorseventures.com/ProcessImage.do?id=sitecode|filename|imagetype">
   * <img src="http://sitecode.darkhorseventures.com/ProcessImage.do?id=filename|imagetype">
   * <img src="http://sitecode.darkhorseventures.com/ProcessImage.do?id=filename.imagetype">
   * <p/>
   * <p/>
   * Where sitecode = ds21 or similar (leave out the cdb_)<br>
   * filename is the date generated filename<br>
   * imagetype should be gif or jpeg
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    String id = (String) context.getRequest().getParameter("id");
    //Start the download
    try {
      String dbName = null;
      String fileName = null;
      String imageType = null;
      //Url can be:
      StringTokenizer st = new StringTokenizer(id, "|");
      String item1 = st.nextToken();
      if (st.hasMoreTokens()) {
        String item2 = st.nextToken();
        if (st.hasMoreTokens()) {
          //sitecode.url.com/ProcessImage.do?id=sitecode|imagename|imagetype
          String item3 = st.nextToken();
          dbName = item1;
          fileName = item2;
          imageType = item3;
        } else {
          //sitecode.url.com/ProcessImage.do?id=imagename|imagetype
          String serverName = context.getRequest().getServerName();
          dbName = serverName.substring(0, serverName.indexOf("."));
          fileName = item1;
          imageType = item2;
        }
      } else {
        //sitecode.url.com/ProcessImage.do?id=imagename.imagetype
        String serverName = context.getRequest().getServerName();
        dbName = serverName.substring(0, serverName.indexOf("."));
        fileName = item1.substring(0, item1.indexOf("."));
        imageType = item1.substring(item1.indexOf(".") + 1);
      }
      //Decode the path to the file based on the fileName
      String year = fileName.substring(0, 4);
      String monthday = fileName.substring(4, 8);
      String filePath = this.getPath(context) + "cdb_" + dbName + fs + "images" + fs + year + fs + monthday + fs + fileName;
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Image-> Sending " + filePath);
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Image-> image/" + imageType);
      }
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(fileName);
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context, "image/" + imageType);
      } else {
        System.err.println(
            "Image-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      System.out.println(e.toString());
    }
    return ("-none-");
  }
}

