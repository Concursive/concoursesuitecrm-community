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

