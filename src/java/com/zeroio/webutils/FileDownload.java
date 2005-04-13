/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.webutils;

import com.darkhorseventures.framework.actions.ActionContext;

import javax.servlet.ServletOutputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

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
  public FileDownload() { }


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
    if (this.getDisplayName().toLowerCase().endsWith(".bmp")) {
      contentType = "image/bmp";
    } else if (this.getDisplayName().toLowerCase().endsWith(".css")) {
      contentType = "text/plain";
    } else if (this.getDisplayName().toLowerCase().endsWith(".doc")) {
      contentType = "application/msword";
    } else if (this.getDisplayName().toLowerCase().endsWith(".dot")) {
      contentType = "application/msword";
    } else if (this.getDisplayName().toLowerCase().endsWith(".eps")) {
      contentType = "application/postscript";
    } else if (this.getDisplayName().toLowerCase().endsWith(".gif")) {
      contentType = "image/gif";
    } else if (this.getDisplayName().toLowerCase().endsWith(".htm")) {
      contentType = "text/html";
    } else if (this.getDisplayName().toLowerCase().endsWith(".html")) {
      contentType = "text/html";
    } else if (this.getDisplayName().toLowerCase().endsWith(".java")) {
      contentType = "text/plain";
    } else if (this.getDisplayName().toLowerCase().endsWith(".jpeg")) {
      contentType = "image/jpeg";
    } else if (this.getDisplayName().toLowerCase().endsWith(".jpg")) {
      contentType = "image/jpeg";
    } else if (this.getDisplayName().toLowerCase().endsWith(".js")) {
      contentType = "application/x-javascript";
    } else if (this.getDisplayName().toLowerCase().endsWith(".mdb")) {
      contentType = "application/x-msaccess";
    } else if (this.getDisplayName().toLowerCase().endsWith(".mid")) {
      contentType = "audio/mid";
    } else if (this.getDisplayName().toLowerCase().endsWith(".midi")) {
      contentType = "audio/mid";
    } else if (this.getDisplayName().toLowerCase().endsWith(".mp3")) {
      contentType = "audio/mpeg";
    } else if (this.getDisplayName().toLowerCase().endsWith(".mpp")) {
      contentType = "application/vnd.ms-project";
    } else if (this.getDisplayName().toLowerCase().endsWith(".pdf")) {
      contentType = "application/pdf";
    } else if (this.getDisplayName().toLowerCase().endsWith(".png")) {
      contentType = "image/png";
    } else if (this.getDisplayName().toLowerCase().endsWith(".pot")) {
      contentType = "application/vnd.ms-powerpoint";
    } else if (this.getDisplayName().toLowerCase().endsWith(".pps")) {
      contentType = "application/vnd.ms-powerpoint";
    } else if (this.getDisplayName().toLowerCase().endsWith(".ppt")) {
      contentType = "application/vnd.ms-powerpoint";
    } else if (this.getDisplayName().toLowerCase().endsWith(".ps")) {
      contentType = "application/postscript";
    } else if (this.getDisplayName().toLowerCase().endsWith(".rtf")) {
      contentType = "application/rtf";
    } else if (this.getDisplayName().toLowerCase().endsWith(".sql")) {
      contentType = "text/plain";
    } else if (this.getDisplayName().toLowerCase().endsWith(".tgz")) {
      contentType = "application/x-compressed";
    } else if (this.getDisplayName().toLowerCase().endsWith(".tif")) {
      contentType = "image/tiff";
    } else if (this.getDisplayName().toLowerCase().endsWith(".tiff")) {
      contentType = "image/tiff";
    } else if (this.getDisplayName().toLowerCase().endsWith(".txt")) {
      contentType = "text/plain";
    } else if (this.getDisplayName().toLowerCase().endsWith(".wav")) {
      contentType = "audio/x-wav";
    } else if (this.getDisplayName().toLowerCase().endsWith(".wks")) {
      contentType = "application/vnd.ms-works";
    } else if (this.getDisplayName().toLowerCase().endsWith(".wps")) {
      contentType = "application/vnd.ms-works";
    } else if (this.getDisplayName().toLowerCase().endsWith(".xls")) {
      contentType = "application/vnd.ms-excel";
    } else if (this.getDisplayName().toLowerCase().endsWith(".xml")) {
      contentType = "text/xml";
    } else if (this.getDisplayName().toLowerCase().endsWith(".xsl")) {
      contentType = "text/xml";
    } else if (this.getDisplayName().toLowerCase().endsWith(".zip")) {
      contentType = "application/x-zip-compressed";
    } else if (this.getDisplayName().toLowerCase().endsWith("README")) {
      contentType = "text/plain";
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


  /**
   *  Description of the Method
   *
   *@param  context        Description of the Parameter
   *@param  bytes          Description of the Parameter
   *@param  contentType    Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
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
   *@param  bytes          Description of the Parameter
   *@param  contentType    Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public void streamFile(ActionContext context, byte[] bytes, String contentType) throws Exception {
    context.getResponse().setContentType(contentType);
    ServletOutputStream outputStream = context.getResponse().getOutputStream();
    outputStream.write(bytes, 0, bytes.length);
    outputStream.flush();
    outputStream.close();
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

