/**
 *  Copyright(c) 2001 iSavvix Corporation (http://www.isavvix.com/) All rights
 *  reserved Permission to use, copy, modify and distribute this material for
 *  any purpose and without fee is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies, and that
 *  the name of iSavvix Corporation not be used in advertising or publicity
 *  pertaining to this material without the specific, prior written permission
 *  of an authorized representative of iSavvix Corporation. ISAVVIX CORPORATION
 *  MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR IMPLIED, WITH
 *  RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR PURPOSE, AND
 *  THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER INTELLECTUAL PROPERTY
 *  RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO EVENT SHALL ISAVVIX
 *  CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY DAMAGES, INCLUDING
 *  ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL DAMAGES RELATING TO
 *  THE SOFTWARE.
 */

package com.isavvix.tools;

import java.io.File;

/**
 *  This class is used as a data structure by HttpMultiPartParser.
 *
 *@author     Anil Hemrajani
 *@created    December 6, 2001
 *@version    $Id$
 *@see        com.isavvix.tools.HttpMultiPartParser
 */
public class FileInfo {
  private String name = null, 
      clientFileName = null, 
      fileContentType = null;
  private byte[] fileContents = null;
  private File file = null;
  private StringBuffer sb = new StringBuffer(100);
  private double version = 0;
  private int extensionId = 0;
  private int size = -1;


  /**
   *  Sets the Name attribute of the FileInfo object
   *
   *@param  aName  The new Name value
   *@since
   */
  public void setName(String aName) {
    name = aName;
  }


  /**
   *  Sets the ClientFileName attribute of the FileInfo object
   *
   *@param  aClientFileName  The new ClientFileName value
   *@since
   */
  public void setClientFileName(String aClientFileName) {
    clientFileName = aClientFileName;
  }


  /**
   *  Sets the LocalFile attribute of the FileInfo object
   *
   *@param  aFile  The new LocalFile value
   *@since
   */
  public void setLocalFile(File aFile) {
    file = aFile;
  }


  /**
   *  Sets the FileContents attribute of the FileInfo object
   *
   *@param  aByteArray  The new FileContents value
   *@since
   */
  public void setFileContents(byte[] aByteArray) {
    fileContents = new byte[aByteArray.length];
    System.arraycopy(aByteArray, 0, fileContents, 0, aByteArray.length);
  }


  /**
   *  Sets the FileContentType attribute of the FileInfo object
   *
   *@param  aContentType  The new FileContentType value
   *@since
   */
  public void setFileContentType(String aContentType) {
    fileContentType = aContentType;
  }


  /**
   *  Sets the Version attribute of the FileInfo object
   *
   *@param  tmp  The new Version value
   *@since
   */
  public void setVersion(double tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the Size attribute of the FileInfo object
   *
   *@param  tmp  The new Size value
   *@since
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the ExtensionId attribute of the FileInfo object
   *
   *@param  tmp  The new ExtensionId value
   *@since
   */
  public void setExtensionId(int tmp) {
    this.extensionId = tmp;
  }


  /**
   *  Gets the Name attribute of the FileInfo object
   *
   *@return    The Name value
   *@since
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the ClientFileName attribute of the FileInfo object
   *
   *@return    The ClientFileName value
   *@since
   */
  public String getClientFileName() {
    return clientFileName;
  }


  /**
   *  Gets the Filename attribute of the FileInfo object
   *
   *@return    The Filename value
   *@since
   */
  public String getFilename() {
    return file.getName();
  }


  /**
   *  Gets the realFilename attribute of the FileInfo object
   *
   *@return    The realFilename value
   */
  public String getRealFilename() {
    int index = file.getName().lastIndexOf('^');
    if (index >= 0) {
      return (file.getName().substring(0, index));
    } else {
      return (file.getName());
    }
  }


  /**
   *  Gets the LocalFile attribute of the FileInfo object
   *
   *@return    The LocalFile value
   *@since
   */
  public File getLocalFile() {
    return file;
  }


  /**
   *  Gets the FileContents attribute of the FileInfo object
   *
   *@return    The FileContents value
   *@since
   */
  public byte[] getFileContents() {
    return fileContents;
  }


  /**
   *  Gets the FileContentType attribute of the FileInfo object
   *
   *@return    The FileContentType value
   *@since
   */
  public String getFileContentType() {
    return fileContentType;
  }


  /**
   *  Gets the Version attribute of the FileInfo object
   *
   *@return    The Version value
   *@since
   */
  public double getVersion() {
    return version;
  }


  /**
   *  Gets the ExtensionId attribute of the FileInfo object
   *
   *@return    The ExtensionId value
   *@since
   */
  public int getExtensionId() {
    return extensionId;
  }


  /**
   *  Gets the Size attribute of the FileInfo object
   *
   *@return    The Size value
   *@since
   */
  public int getSize() {
    return (size);
  }


  /**
   *  Returns the file path without any trailing - and characters, which are
   *  used to identify a unique upload
   *
   *@return    The OldLocalFilePath value
   *@since
   */
  public String getOldLocalFilePath() {
    int index = file.getPath().lastIndexOf('-');
    if (index >= 0) {
      return (file.getPath().substring(0, index));
    } else {
      return (file.getPath());
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public String toString() {
    sb.setLength(0);
    sb.append("               name = " + name + "\n");
    sb.append("     clientFileName = " + clientFileName + "\n");

    if (file != null) {
      sb.append("      File.toString = " + file +
          " (size=" + file.length() + ")\n");
    } else {
      sb.append("fileContents.length = " + fileContents.length + "\n");
    }
    sb.append("            version = " + version + "\n");

    return sb.toString();
  }
}

