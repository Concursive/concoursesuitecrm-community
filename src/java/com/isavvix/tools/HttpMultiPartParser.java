/*
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
 *  THE SOFTWARE.<p>
 *
 *  Portions Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package com.isavvix.tools;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * This class provides methods for parsing a HTML multi-part form. Each method
 * returns a HashMap which contains keys for all parameters sent from the web
 * browser. The corresponding values are either type "String" or "FileInfo"
 * depending on the type of data in the corresponding part. <P>
 * <p/>
 * Refer to http://www.ietf.org/rfc/rfc1867.txt<P>
 * <p/>
 * The following is a sample InputStream expected by the methods in this class:
 * <PRE>
 * -----------------------------7ce23a18680
 * Content-Disposition: form-data; name="SomeTextField1"
 * on
 * -----------------------------7ce23a18680
 * Content-Disposition: form-data; name="LocalFile1"; filename="C:\temp\testit.c"
 * Content-Type: text/plain
 * #include <stdlib.h>
 * int main(int argc, char **argv)
 * {
 * printf("Testing\n");
 * return 0;
 * }
 * -----------------------------7ce23a18680--
 * </PRE>
 *
 * @author Anil Hemrajani
 * @version $Id: HttpMultiPartParser.java,v 1.2 2002/04/23 18:39:44 mrajkowski
 *          Exp $
 * @created December 6, 2001
 * @see com.isavvix.tools.FileInfo
 */
public class HttpMultiPartParser {
  private final String fs = System.getProperty("file.separator");
  private final int ONE_MB = 1024 * 1024 * 1;
  private boolean useUniqueName = false;
  private boolean usePathParam = false;
  private boolean useDateForFolder = false;
  private double version = -1;
  private int extensionId = -1;


  /**
   * Sets the useUniqueName attribute of the HttpMultiPartParser object
   *
   * @param tmp The new useUniqueName value
   */
  public void setUseUniqueName(boolean tmp) {
    this.useUniqueName = tmp;
  }


  /**
   * Sets the UsePathParam attribute of the HttpMultiPartParser object
   *
   * @param tmp The new UsePathParam value
   */
  public void setUsePathParam(boolean tmp) {
    this.usePathParam = tmp;
  }


  /**
   * Sets the useDateForFolder attribute of the HttpMultiPartParser object
   *
   * @param tmp The new useDateForFolder value
   */
  public void setUseDateForFolder(boolean tmp) {
    this.useDateForFolder = tmp;
  }


  /**
   * Sets the Version attribute of the HttpMultiPartParser object
   *
   * @param tmp The new Version value
   */
  public void setVersion(double tmp) {
    this.version = tmp;
  }


  /**
   * Sets the extensionId attribute of the HttpMultiPartParser object
   *
   * @param tmp The new extensionId value
   */
  public void setExtensionId(int tmp) {
    this.extensionId = tmp;
  }


  /**
   * Gets the useUniqueName attribute of the HttpMultiPartParser object
   *
   * @return The useUniqueName value
   */
  public boolean getUseUniqueName() {
    return useUniqueName;
  }


  /**
   * Gets the UsePathParam attribute of the HttpMultiPartParser object
   *
   * @return The UsePathParam value
   */
  public boolean getUsePathParam() {
    return usePathParam;
  }


  /**
   * Gets the useDateForFolder attribute of the HttpMultiPartParser object
   *
   * @return The useDateForFolder value
   */
  public boolean getUseDateForFolder() {
    return useDateForFolder;
  }


  /**
   * Gets the Version attribute of the HttpMultiPartParser object
   *
   * @return The Version value
   */
  public double getVersion() {
    return version;
  }


  /**
   * Gets the extensionId attribute of the HttpMultiPartParser object
   *
   * @return The extensionId value
   */
  public int getExtensionId() {
    return extensionId;
  }


  /**
   * Parses the InputStream, separates the various parts and returns them as
   * key=value pairs in a HashMap. Any incoming files are saved in directory
   * "saveInDir" using the client's file name; the file information is stored
   * as java.io.File object in the HashMap ("value" part).
   *
   * @param saveInDir Description of Parameter
   * @param request   Description of the Parameter
   * @return Description of the Returned Value
   * @throws IllegalArgumentException Description of Exception
   * @throws IOException              Description of Exception
   */
  public HashMap parseData(HttpServletRequest request, String saveInDir)
      throws IllegalArgumentException, IOException {
    return processData(request, saveInDir);
  }


  /**
   * Parses the InputStream, separates the various parts and returns them as
   * key=value pairs in a HashMap. Any incoming files are saved as byte arrays;
   * the file information is stored as java.io.File object in the HashMap
   * ("value" part).
   *
   * @param request Description of the Parameter
   * @return Description of the Returned Value
   * @throws IllegalArgumentException Description of Exception
   * @throws IOException              Description of Exception
   */
  public HashMap parseData(HttpServletRequest request)
      throws IllegalArgumentException, IOException {
    return processData(request, null);
  }


  /**
   * Convenience method to read HTTP header lines
   *
   * @param sis Description of Parameter
   * @return The Line value
   * @throws IOException Description of Exception
   */
  private synchronized String getLine(ServletInputStream sis)
      throws IOException {
    byte b[] = new byte[1024];
    int read = sis.readLine(b, 0, b.length);
    int index;
    String line = null;
    if (read != -1) {
      line = new String(b, 0, read);
      if ((index = line.indexOf('\n')) >= 0) {
        line = line.substring(0, index - 1);
      }
    }
    b = null;
    return line;
  }


  /**
   * Concats the directory and file names.
   *
   * @param dir      Description of Parameter
   * @param fileName Description of Parameter
   * @return The FileName value
   * @throws IllegalArgumentException Description of Exception
   */
  private String getFileName(String dir, String fileName)
      throws IllegalArgumentException {
    String path = null;
    if (dir == null || fileName == null) {
      throw new IllegalArgumentException("dir or fileName is null");
    }
    int index = fileName.lastIndexOf('/');
    String name = null;
    if (index >= 0) {
      name = fileName.substring(index + 1);
    } else {
      name = fileName;
    }
    index = name.lastIndexOf('\\');
    if (index >= 0) {
      fileName = name.substring(index + 1);
    }
    path = dir + File.separator + fileName;
    if (File.separatorChar == '/') {
      return path.replace('\\', File.separatorChar);
    } else {
      return path.replace('/', File.separatorChar);
    }
  }


  /**
   * Gets the fileName attribute of the HttpMultiPartParser object
   *
   * @param fileName Description of the Parameter
   * @return The fileName value
   * @throws IllegalArgumentException Description of the Exception
   */
  private String getFileName(String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("dir or fileName is null");
    }
    int index = fileName.lastIndexOf('/');
    String name = null;
    if (index >= 0) {
      name = fileName.substring(index + 1);
    } else {
      name = fileName;
    }
    index = name.lastIndexOf('\\');
    if (index >= 0) {
      fileName = name.substring(index + 1);
    }
    return fileName;
  }


  /**
   * Description of the Method
   *
   * @param saveInDir Description of Parameter
   * @param request   Description of the Parameter
   * @return Description of the Returned Value
   * @throws IllegalArgumentException Description of Exception
   * @throws IOException              Description of Exception
   */
  private HashMap processData(HttpServletRequest request, String saveInDir)
      throws IllegalArgumentException, IOException {
    String contentType = request.getHeader("Content-type");
    //TODO: use the contentLength for a progress bar
    int contentLength = request.getContentLength();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("HttpMultiPartParser-> Length: " + contentLength);
    }
    if ((contentType == null) || (!contentType.startsWith("multipart/"))) {
      throw new IllegalArgumentException("Not a multipart message");
    }
    int boundaryIndex = contentType.indexOf("boundary=");
    String boundary = contentType.substring(boundaryIndex + 9);
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "HttpMultiPartParser-> Request boundary: " + boundary);
    }
    ServletInputStream is = request.getInputStream();
    if (is == null) {
      throw new IllegalArgumentException("InputStream");
    }
    if (boundary == null || boundary.trim().length() < 1) {
      throw new IllegalArgumentException("boundary");
    }
    //Each content will begin with two dashes "--" plus the actual boundary string
    boundary = "--" + boundary;
    //Prepare to read in from request
    StringTokenizer stLine = null;
    StringTokenizer stFields = null;
    FileInfo fileInfo = null;
    HashMap dataTable = new HashMap(5);
    String line = null;
    String field = null;
    String paramName = null;
    boolean saveFiles = (saveInDir != null && saveInDir.trim().length() > 0);
    boolean isFile = false;
    boolean validFile = false;
    int fileCount = 0;
    //First line should be the boundary
    line = getLine(is);
    if (line == null || !line.startsWith(boundary)) {
      throw new IOException(
          "Boundary not found;"
              + " boundary = " + boundary
              + ", line = " + line);
    }
    //Continue with the rest of the lines
    while (line != null) {
      // Process boundary line  ----------------------------------------
      if (line == null || !line.startsWith(boundary)) {
        return dataTable;
      }
      // Process "Content-Disposition: " line --------------------------
      line = getLine(is);
      if (line == null) {
        return dataTable;
      }
      // Split line into the following 3 tokens (or 2 if not a file):
      // 1. Content-Disposition: form-data
      // 2. name="LocalFile1"
      // 3. filename="C:\autoexec.bat"  (only present if this is part of a HTML file INPUT tag) */
      stLine = new StringTokenizer(line, ";\r\n");
      if (stLine.countTokens() < 2) {
        throw new IllegalArgumentException("Bad data in second line");
      }
      // Confirm that this is "form-data"
      line = stLine.nextToken().toLowerCase();
      if (line.indexOf("form-data") < 0) {
        throw new IllegalArgumentException("Bad data in second line");
      }
      // Now split token 2 from above into field "name" and it's "value"
      // e.g. name="LocalFile1"
      stFields = new StringTokenizer(stLine.nextToken(), "=\"");
      if (stFields.countTokens() < 2) {
        throw new IllegalArgumentException("Bad data in second line");
      }
      // Get field name
      fileInfo = new FileInfo();
      fileInfo.setVersion(version);
      fileInfo.setExtensionId(extensionId);
      stFields.nextToken();
      paramName = stFields.nextToken();
      // Now split token 3 from above into file "name" and it's "value"
      // e.g. filename="C:\autoexec.bat"
      isFile = false;
      if (stLine.hasMoreTokens()) {
        field = stLine.nextToken();
        stFields = new StringTokenizer(field, "=\"");
        if (stFields.countTokens() > 1) {
          if (stFields.nextToken().trim().equalsIgnoreCase("filename")) {
            fileInfo.setName(paramName);
            String value = stFields.nextToken();
            if (value != null && value.trim().length() > 0) {
              fileInfo.setClientFileName(value);
              isFile = true;
              ++fileCount;
            } else {
              // An error condition occurred, skip to next boundary
              line = getLine(is);
              // Skip "Content-Type:" line
              line = getLine(is);
              // Skip blank line
              line = getLine(is);
              // Skip blank line
              line = getLine(is);
              // Position to boundary line
              continue;
            }
          }
        } else if (field.toLowerCase().indexOf("filename") >= 0) {
          // An error condition occurred, skip to next boundary
          line = getLine(is);
          // Skip "Content-Type:" line
          line = getLine(is);
          // Skip blank line
          line = getLine(is);
          // Skip blank line
          line = getLine(is);
          // Position to boundary line
          continue;
        }
      }
      // Process "Content-Type: " line ----------------------------------
      // e.g. Content-Type: text/plain
      boolean skipBlankLine = true;
      if (isFile) {
        line = getLine(is);
        if (line == null) {
          return dataTable;
        }
        // "Content-type" line not guaranteed to be sent by the browser
        if (line.trim().length() < 1) {
          skipBlankLine = false;
        } else {
          // Prevent re-skipping below
          stLine = new StringTokenizer(line, ": ");
          if (stLine.countTokens() < 2) {
            throw new IllegalArgumentException("Bad data in third line");
          }
          stLine.nextToken();
          // Content-Type
          fileInfo.setFileContentType(stLine.nextToken());
        }
      }
      // Skip blank line  -----------------------------------------------
      if (skipBlankLine) {
        // Blank line already skipped above?
        line = getLine(is);
        if (line == null) {
          return dataTable;
        }
      }
      // Process data: If not a file, add to hashmap and continue
      if (!isFile) {
        line = getLine(is);
        if (line == null) {
          return dataTable;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(line);
        while (line != null && !line.startsWith(boundary)) {
          line = getLine(is);
          if (line != null && !line.startsWith(boundary)) {
            sb.append(System.getProperty("line.separator"));
            sb.append(line);
          }
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("HttpMultiPartParser-> Adding param: " + paramName);
        }
        dataTable.put(paramName, sb.toString());
        continue;
      }
      // Either save contents in memory or to a local file
      OutputStream os = null;
      String path = null;
      try {
        String tmpPath = null;
        String filenameToUse = null;
        if (saveFiles) {
          if (usePathParam) {
            tmpPath = saveInDir + fileInfo.getName() + fs;
          } else {
            tmpPath = saveInDir;
          }
          if (useDateForFolder) {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
            String datePathToUse1 = formatter1.format(new java.util.Date());
            SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
            String datePathToUse2 = formatter2.format(new java.util.Date());
            tmpPath += datePathToUse1 + fs + datePathToUse2 + fs;
          }
          // Create output directory in case it doesn't exist
          File f = new File(tmpPath);
          f.mkdirs();
          //If specified, store files using a unique name, based on date
          if (useUniqueName) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            filenameToUse = formatter.format(new java.util.Date());
            if (fileCount > 1) {
              filenameToUse += String.valueOf(fileCount);
            }
          } else {
            filenameToUse = fileInfo.getClientFileName();
          }
          fileInfo.setClientFileName(
              getFileName(fileInfo.getClientFileName()));
          //Append a version id for record keeping and uniqueness, prevents
          //multiple uploads from overwriting each other
          filenameToUse +=
              (version == -1 ? "" : "^" + version) +
                  (extensionId == -1 ? "" : "-" + extensionId);
          //Create the file to a file
          os = new FileOutputStream(
              path = getFileName(tmpPath, filenameToUse));
        } else {
          //Store the file in memory
          os = new ByteArrayOutputStream(ONE_MB);
        }
        //Begin reading in the request
        boolean readingContent = true;
        byte previousLine[] = new byte[2 * ONE_MB];
        byte temp[] = null;
        byte currentLine[] = new byte[2 * ONE_MB];
        int read;
        int read3;
        //read in the first line, break out if eof
        if ((read = is.readLine(previousLine, 0, previousLine.length)) == -1) {
          line = null;
          break;
        }
        //read until next boundary and write the contents to OutputStream
        while (readingContent) {
          if ((read3 = is.readLine(currentLine, 0, currentLine.length)) == -1) {
            line = null;
            break;
          }
          //check if current line is a boundary
          if (compareBoundary(boundary, currentLine)) {
            if (read - 2 > 0) {
              validFile = true;
            }
            os.write(previousLine, 0, read - 2);
            os.flush();
            line = new String(currentLine, 0, read3);
            break;
          } else {
            //current line is not a boundary, write previous line
            os.write(previousLine, 0, read);
            validFile = true;
            os.flush();
            //reposition previousLine to be currentLine
            temp = currentLine;
            currentLine = previousLine;
            previousLine = temp;
            read = read3;
          }
        }
        os.close();
        temp = null;
        previousLine = null;
        currentLine = null;
        //Store the completed file somewhere
        if (!saveFiles) {
          ByteArrayOutputStream baos = (ByteArrayOutputStream) os;
          fileInfo.setFileContents(baos.toByteArray());
        } else {
          File thisFile = new File(path);
          if (validFile) {
            fileInfo.setLocalFile(thisFile);
            fileInfo.setSize((int) thisFile.length());
            os = null;
          } else {
            thisFile.delete();
          }
        }
        if (validFile) {
          dataTable.put(paramName, fileInfo);
        }
      } catch (Exception e) {
        System.out.println("HttpMultiPartParser-> error: " + e.getMessage());
        if (os != null) {
          os.close();
        }
        if (saveFiles && path != null) {
          File thisFile = new File(path);
          if (thisFile.exists()) {
            thisFile.delete();
            System.out.println("HttpMultiPartParser-> Temporary file deleted");
          }
        }
      }
    }
    return dataTable;
  }


  /**
   * Compares boundary string to byte array
   *
   * @param boundary Description of Parameter
   * @param ba       Description of Parameter
   * @return Description of the Returned Value
   */
  private boolean compareBoundary(String boundary, byte ba[]) {
    if (boundary == null || ba == null) {
      return false;
    }
    for (int i = 0; i < boundary.length(); i++) {
      if ((byte) boundary.charAt(i) != ba[i]) {
        return false;
      }
    }
    return true;
  }
}

