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
package org.aspcfs.utils;

import java.util.zip.*;
import java.io.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public class ZipUtils {

  /**
   *  Adds a feature to the TextEntry attribute of the ZipUtils class
   *
   *@param  zip               The feature to be added to the TextEntry attribute
   *@param  fileName          The feature to be added to the TextEntry attribute
   *@param  data              The feature to be added to the TextEntry attribute
   *@exception  ZipException  Description of the Exception
   *@exception  IOException   Description of the Exception
   */
  public static void addTextEntry(ZipOutputStream zip, String fileName, String data) throws ZipException, IOException {
    int bytesRead;
    StringReader file = new StringReader(data);
    ZipEntry entry = new ZipEntry(fileName);
    zip.putNextEntry(entry);
    while ((bytesRead = file.read()) != -1) {
      zip.write(bytesRead);
    }
  }

}

