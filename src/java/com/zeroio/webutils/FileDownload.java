/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.webutils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    December 17, 2001
 *@version    $Id$
 */
public class FileDownload {

  private String fullPath = null;
  private String displayName = null;


  /**
   *  The complete path and filename of the file to be sent.
   *
   *@param  tmp  The new FullPath value
   *@since
   */
  public void setFullPath(String tmp) {
    this.fullPath = tmp;
  }


  /**
   *  The filename that should be shown to the user's browser.
   *
   *@param  tmp  The new DisplayName value
   *@since
   */
  public void setDisplayName(String tmp) {
    this.displayName = tmp;
  }


  /**
   *  The complete path and filename of the file to be sent.
   *
   *@return    The FullPath value
   *@since
   */
  public String getFullPath() {
    return fullPath;
  }


  /**
   *  The filename that should be shown to the user's browser.
   *
   *@return    The DisplayName value
   *@since
   */
  public String getDisplayName() {
    return displayName;
  }


  /**
   *  Description of the Method
   *
   *@since
   */
  public void FileDownload() { }


  /**
   *  Returns whether the file exists.
   *
   *@return    Description of the Returned Value
   *@since
   */
  public boolean fileExists() {
    File downloadFile = new File(fullPath);
    return downloadFile.exists();
  }


  /**
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public void streamContent(ActionContext context) throws Exception {
    String contentType = "application/octet-stream";
    if (this.getDisplayName().toLowerCase().endsWith(".xls")) {
      contentType = "application/vnd.ms-excel";
    } else if (this.getDisplayName().toLowerCase().endsWith(".wav")) {
      contentType = "audio/x-wav";
    } else if (this.getDisplayName().toLowerCase().endsWith(".jpg")) {
      contentType = "image/jpeg";
    } else if (this.getDisplayName().toLowerCase().endsWith(".gif")) {
      contentType = "image/gif";
    } else if (this.getDisplayName().toLowerCase().endsWith(".pdf")) {
      contentType = "application/pdf";
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("FileDownload-> File type: " + contentType);
    }
    context.getResponse().setContentType(contentType);
    this.send(context);
  }


  /**
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public void sendFile(ActionContext context) throws Exception {
    sendFile(context, "application/octet-stream");
  }


  /**
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@param  contentType    Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public void sendFile(ActionContext context, String contentType) throws Exception {
    File downloadFile = new File(fullPath);
    context.getResponse().setContentType(contentType);
    if (contentType.startsWith("application")) {
      context.getResponse().setHeader("Content-Disposition", "attachment; filename=\"" + displayName + "\";");
      context.getResponse().setContentLength((int) downloadFile.length());
    }
    this.send(context);
  }


  public void sendFile(ActionContext context, byte[] bytes, String contentType) throws Exception {
    context.getResponse().setContentType(contentType);
    if (contentType.startsWith("application")) {
      context.getResponse().setHeader("Content-Disposition", "attachment; filename=\"" + displayName + "\";");
    }
    ServletOutputStream ouputStream = context.getResponse().getOutputStream();
    ouputStream.write(bytes, 0, bytes.length);
    ouputStream.flush();
    ouputStream.close();
  }


  /**
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void send(ActionContext context) throws Exception {
    ServletOutputStream outputStream = context.getResponse().getOutputStream();
    BufferedInputStream inputStream =
        new BufferedInputStream(new FileInputStream(fullPath));

    byte[] buf = new byte[4 * 1024];
    // 4K buffer
    int len;
    while ((len = inputStream.read(buf, 0, buf.length)) != -1) {
      outputStream.write(buf, 0, len);
    }
    outputStream.flush();
    outputStream.close();
    inputStream.close();
  }


  /**
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@param  text           Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public void sendTextAsFile(ActionContext context, String text) throws Exception {
    context.getResponse().setContentType("application/octet-stream");
    context.getResponse().setHeader("Content-Disposition", "attachment;filename=" + displayName + ";");
    context.getResponse().setContentLength((int) text.length());

    ServletOutputStream outputStream = context.getResponse().getOutputStream();
    StringReader strReader = new StringReader(text);
    int data;
    while ((data = strReader.read()) != -1) {
      outputStream.write(data);
    }
    strReader.close();
    outputStream.close();
  }

}

