package org.aspcfs.utils;

import java.io.*;
import javax.activation.DataSource;

/**
 *  Creates a datasource from a String for a mail message
 *
 *@author     Portions Copyright 1999 Sun Microsystems, Inc. All rights
 *      reserved. (adapted from JavaMail demo code)
 *@created    April 26, 2004
 *@version    $Id$
 */
public class ByteArrayDataSource implements DataSource {
  private String fileName = null;
  private byte[] data = null;
  private String mimeType = null;


  /**
   *  Constructor for the ByteArrayDataSource object
   *
   *@param  fileName  Description of the Parameter
   *@param  data      Description of the Parameter
   *@param  mimeType  Description of the Parameter
   */
  ByteArrayDataSource(String fileName, String data, String mimeType) {
    this.fileName = fileName;
    try {
      // Assumption that the string contains only ascii
      // characters
      this.data = data.getBytes("iso-8859-1");
    } catch (UnsupportedEncodingException uex) {
      uex.printStackTrace(System.out);
    }
    this.mimeType = mimeType;
  }


  /**
   *  Constructor for the ByteArrayDataSource object
   *
   *@param  fileName  Description of the Parameter
   *@param  data      Description of the Parameter
   *@param  mimeType  Description of the Parameter
   */
  ByteArrayDataSource(String fileName, byte[] data, String mimeType) {
    this.fileName = fileName;
    this.data = data;
    this.mimeType = mimeType;
  }


  /**
   *  Gets the inputStream attribute of the ByteArrayDataSource object
   *
   *@return                  The inputStream value
   *@exception  IOException  Description of the Exception
   */
  public InputStream getInputStream() throws IOException {
    if (data == null) {
      throw new IOException("no data");
    }
    return new ByteArrayInputStream(data);
  }


  /**
   *  Gets the outputStream attribute of the ByteArrayDataSource object
   *
   *@return                  The outputStream value
   *@exception  IOException  Description of the Exception
   */
  public OutputStream getOutputStream() throws IOException {
    throw new IOException("invalid call");
  }


  /**
   *  Gets the contentType attribute of the ByteArrayDataSource object
   *
   *@return    The contentType value
   */
  public String getContentType() {
    return mimeType;
  }


  /**
   *  Gets the name attribute of the ByteArrayDataSource object
   *
   *@return    The name value
   */
  public String getName() {
    return fileName;
  }
}

