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
import org.aspcfs.utils.StringUtils;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.net.URL;
import java.net.URLConnection;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id$
 * @created December 17, 2001
 */
public class FileDownload {

  private String fullPath = null;
  private URL url = null;
  private String displayName = null;
  private long fileTimestamp = 0;


  /**
   * The complete path and filename of the file to be sent.
   *
   * @param tmp The new FullPath value
   */
  public void setFullPath(String tmp) {
    this.fullPath = tmp;
  }

  public void setUrl(URL url) {
    this.url = url;
  }

  /**
   * The filename that should be shown to the user's browser.
   *
   * @param tmp The new DisplayName value
   */
  public void setDisplayName(String tmp) {
    this.displayName = tmp;
  }


  /**
   * The complete path and filename of the file to be sent.
   *
   * @return The FullPath value
   */
  public String getFullPath() {
    return fullPath;
  }

  public URL getUrl() {
    return url;
  }

  /**
   * The filename that should be shown to the user's browser.
   *
   * @return The DisplayName value
   */
  public String getDisplayName() {
    return displayName;
  }

  public long getFileTimestamp() {
    return fileTimestamp;
  }

  public void setFileTimestamp(long fileTimestamp) {
    this.fileTimestamp = fileTimestamp;
  }

  /**
   * Description of the Method
   */
  public FileDownload() {
  }


  /**
   * Returns whether the file exists.
   *
   * @return Description of the Returned Value
   */
  public boolean fileExists() {
    File downloadFile = new File(fullPath);
    return downloadFile.exists();
  }


  public static String getContentType(String filename) {
    String contentType = "application/octet-stream";
    if (filename.endsWith(".bmp")) {
      contentType = "image/bmp";
    } else if (filename.endsWith(".css")) {
      contentType = "text/plain";
    } else if (filename.endsWith(".doc")) {
      contentType = "application/msword";
    } else if (filename.endsWith(".dot")) {
      contentType = "application/msword";
    } else if (filename.endsWith(".eps")) {
      contentType = "application/postscript";
    } else if (filename.endsWith(".gif")) {
      contentType = "image/gif";
    } else if (filename.endsWith(".htm")) {
      contentType = "text/html";
    } else if (filename.endsWith(".html")) {
      contentType = "text/html";
    } else if (filename.endsWith(".java")) {
      contentType = "text/plain";
    } else if (filename.endsWith(".jpeg")) {
      contentType = "image/jpeg";
    } else if (filename.endsWith(".jpg")) {
      contentType = "image/jpeg";
    } else if (filename.endsWith(".js")) {
      contentType = "application/x-javascript";
    } else if (filename.endsWith(".mdb")) {
      contentType = "application/x-msaccess";
    } else if (filename.endsWith(".mid")) {
      contentType = "audio/mid";
    } else if (filename.endsWith(".midi")) {
      contentType = "audio/mid";
    } else if (filename.endsWith(".mp3")) {
      contentType = "audio/mpeg";
    } else if (filename.endsWith(".mpp")) {
      contentType = "application/vnd.ms-project";
    } else if (filename.endsWith(".pdf")) {
      contentType = "application/pdf";
    } else if (filename.endsWith(".png")) {
      contentType = "image/png";
    } else if (filename.endsWith(".pot")) {
      contentType = "application/vnd.ms-powerpoint";
    } else if (filename.endsWith(".pps")) {
      contentType = "application/vnd.ms-powerpoint";
    } else if (filename.endsWith(".ppt")) {
      contentType = "application/vnd.ms-powerpoint";
    } else if (filename.endsWith(".ps")) {
      contentType = "application/postscript";
    } else if (filename.endsWith(".rtf")) {
      contentType = "application/rtf";
    } else if (filename.endsWith(".sql")) {
      contentType = "text/plain";
    } else if (filename.endsWith(".tgz")) {
      contentType = "application/x-compressed";
    } else if (filename.endsWith(".tif")) {
      contentType = "image/tiff";
    } else if (filename.endsWith(".tiff")) {
      contentType = "image/tiff";
    } else if (filename.endsWith(".txt")) {
      contentType = "text/plain";
    } else if (filename.endsWith(".wav")) {
      contentType = "audio/x-wav";
    } else if (filename.endsWith(".wks")) {
      contentType = "application/vnd.ms-works";
    } else if (filename.endsWith(".wps")) {
      contentType = "application/vnd.ms-works";
    } else if (filename.endsWith(".xls")) {
      contentType = "application/vnd.ms-excel";
    } else if (filename.endsWith(".xml")) {
      contentType = "text/xml";
    } else if (filename.endsWith(".xsl")) {
      contentType = "text/xml";
    } else if (filename.endsWith(".zip")) {
      contentType = "application/x-zip-compressed";
    } else if (filename.endsWith(".vcf")) {
      contentType = "text/x-vcard";
    } else if (filename.endsWith(".csv")) {
      contentType = "text/csv";
    } else if (filename.endsWith("readme")) {
      contentType = "text/plain";
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("FileDownload-> File type: " + contentType);
    }
    return contentType;
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void streamContent(ActionContext context) throws Exception {
    if (fullPath.endsWith("TH")) {
      // NOTE: A temporary fix because all thumbnails (that are scaled)
      // are saved as JPG.  Actual size thumbnails match the original
      // filetype (PNG, GIF, JPG)
      context.getResponse().setContentType(getContentType(".jpg"));
    } else {
      context.getResponse().setContentType(getContentType(this.getDisplayName().toLowerCase()));
    }
    if (fileTimestamp > 0) {
      context.getResponse().setDateHeader("Last-Modified", fileTimestamp);
    }
    this.send(context);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void sendFile(ActionContext context) throws Exception {
    sendFile(context, "application/octet-stream");
  }


  /**
   * Description of the Method
   *
   * @param context     Description of the Parameter
   * @param contentType Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void sendFile(ActionContext context, String contentType) throws Exception {
    File downloadFile = new File(fullPath);
    context.getResponse().setContentType(contentType);
    if (contentType.startsWith("application")) {
      context.getResponse().setHeader(
          "Content-Disposition", "attachment; filename=\"" + displayName + "\";");
      context.getResponse().setContentLength((int) downloadFile.length());
    }
    this.send(context);
  }


  public static void sendFile(ActionContext context, URL url, String contentType, String displayName) throws Exception {
    URLConnection urlConnection = url.openConnection();
    InputStream in = urlConnection.getInputStream();
    context.getResponse().setContentType(contentType);
    if (contentType.startsWith("application")) {
      context.getResponse().setHeader(
          "Content-Disposition", "attachment; filename=\"" + displayName + "\";");
      context.getResponse().setContentLength(urlConnection.getContentLength());
    }
    ServletOutputStream outputStream = context.getResponse().getOutputStream();
    BufferedInputStream inputStream = new BufferedInputStream(in);
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
   * Description of the Method
   *
   * @param context     Description of the Parameter
   * @param bytes       Description of the Parameter
   * @param contentType Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void sendFile(ActionContext context, byte[] bytes, String contentType) throws Exception {
    context.getResponse().setContentType(contentType);
    if (contentType.startsWith("application")) {
      context.getResponse().setHeader(
          "Content-Disposition", "attachment; filename=\"" + displayName + "\";");
      context.getResponse().setContentLength(bytes.length);
    }
    ServletOutputStream ouputStream = context.getResponse().getOutputStream();
    ouputStream.write(bytes, 0, bytes.length);
    ouputStream.flush();
    ouputStream.close();
  }


  /**
   * Description of the Method
   *
   * @param context     Description of the Parameter
   * @param bytes       Description of the Parameter
   * @param contentType Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void streamFile(ActionContext context, byte[] bytes, String contentType) throws Exception {
    context.getResponse().setContentType(contentType);
    ServletOutputStream outputStream = context.getResponse().getOutputStream();
    outputStream.write(bytes, 0, bytes.length);
    outputStream.flush();
    outputStream.close();
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @throws Exception Description of the Exception
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param text    Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void sendTextAsFile(ActionContext context, String text) throws Exception {
    context.getResponse().setContentType("application/octet-stream");
    context.getResponse().setHeader(
        "Content-Disposition", "attachment;filename=" + displayName + ";");
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

  public void sendFileFromZip(ActionContext context, String zipFilename, String filename) throws IOException {
    context.getResponse().setContentType(getContentType(filename));
    ZipFile zipFile = new ZipFile(zipFilename);
    ZipEntry zipEntry = zipFile.getEntry(filename);
    // Note: ZipEntry must match the path that was saved in the path
    if (zipEntry == null) {
      zipEntry = zipFile.getEntry(StringUtils.replace(filename, "/", "\\"));
    }
    if (zipEntry == null) {
      zipEntry = zipFile.getEntry(StringUtils.replace(filename, "\\", "/"));
    }
    ServletOutputStream outputStream = context.getResponse().getOutputStream();
    BufferedInputStream inputStream =
        new BufferedInputStream(zipFile.getInputStream(zipEntry));
    byte[] buf = new byte[4 * 1024];
    // 4K buffer
    int len;
    while ((len = inputStream.read(buf, 0, buf.length)) != -1) {
      outputStream.write(buf, 0, len);
    }
    outputStream.flush();
    outputStream.close();
    inputStream.close();
    zipFile.close();
  }
}

