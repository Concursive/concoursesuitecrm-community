/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package org.aspcfs.modules.communications.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.base.DependencyList;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;

/**
 *  A stand alone servlet that sends files. Currently it will stream an image
 *  file from a customer's protected image library
 *
 *@author     mrajkowski
 *@created    January 23, 2002
 *@version    $Id$
 */
public final class ProcessImage extends CFSModule {

  /**
   *  Receives a request for a customer's image and processes it.<br>
   *  Images are stored in the fileLibrary using the cryptic datetime filename
   *  without an extension<p>
   *
   *  The url format should be any of the following 3:<br>
   *  <img src="http://ds21.darkhorseventures.com/ProcessImage.do?id=sitecode|filename|imagetype">
   *  <img src="http://sitecode.darkhorseventures.com/ProcessImage.do?id=filename|imagetype">
   *  <img src="http://sitecode.darkhorseventures.com/ProcessImage.do?id=filename.imagetype">
   *  <p>
   *
   *  Where sitecode = ds21 or similar (leave out the cdb_)<br>
   *  filename is the date generated filename<br>
   *  imagetype should be gif or jpeg
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    String id = (String) context.getRequest().getParameter("id");
    FileItem thisItem = null;

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
        System.err.println("Image-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    }

    return ("-none-");
  }
}

