/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
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
   *  Receives a request for a customer's image and processes it.<p>
   *
   *  The format should be:<br>
   *  <img src="http://ds21.darkhorseventures.com/ProcessImage.do?id=sitecode|filename|imagetype">
   *  <br>
   *  Where sitecode = ds21 or similar (leave out the cdb_)<br>
   *  filename is the date generated filename<br>
   *  imagetype should be gif or jpeg
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   *@since
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    String id = (String)context.getRequest().getParameter("id");
    FileItem thisItem = null;

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String dbName = st.nextToken();
      String fileName = st.nextToken();
      String imageType = st.nextToken();
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
      //User either cancelled the download or lost connection
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    }

    return ("-none-");
  }
}

