package org.aspcfs.utils;

import java.io.*;

/**
 *  Helper methods for dealing with file operations
 *
 *@author     matt rajkowski
 *@created    August 25, 2003
 *@version    $Id$
 */
public class FileUtils {

  /**
   *  Copies the specified source file to the destination file
   *
   *@param  sourceFile       Description of the Parameter
   *@param  destinationFile  Description of the Parameter
   *@return                  Description of the Return Value
   */
  public static boolean copyFile(File sourceFile, File destinationFile) {
    return copyFile(sourceFile, destinationFile, true);
  }


  /**
   *  Description of the Method
   *
   *@param  sourceFile       Description of the Parameter
   *@param  destinationFile  Description of the Parameter
   *@param  overwrite        Description of the Parameter
   *@return                  Description of the Return Value
   */
  public static boolean copyFile(File sourceFile, File destinationFile, boolean overwrite) {
    //Check to see if source exists
    if (!sourceFile.exists()) {
      return false;
    }
    //Check to see if source and destination file are the same
    if (sourceFile.equals(destinationFile)) {
      return false;
    }
    if (!overwrite) {
      if (destinationFile.exists()) {
        return true;
      }
    }
    //Copy the file
    FileInputStream source = null;
    FileOutputStream destination = null;
    try {
      source = new FileInputStream(sourceFile);
      destination = new FileOutputStream(destinationFile);
      byte[] buffer = new byte[4096];
      int read = -1;
      while ((read = source.read(buffer)) != -1) {
        destination.write(buffer, 0, read);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    } finally {
      if (destination != null) {
        try {
          destination.close();
        } catch (IOException io) {
        }
      }
      if (source != null) {
        try {
          source.close();
        } catch (IOException io) {
        }
      }
    }
    return true;
  }
}

