package org.aspcfs.utils;

import java.io.*;
import java.util.StringTokenizer;

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


  /**
   *  Gets the bytes free on the system for the specified directory
   *
   *@param  dir  Description of the Parameter
   *@return      The freeBytes value
   */
  public static long getFreeBytes(String dir) {
    long free = -1;
    String[] command = null;
    // Create OS-specific command to get available space
    File osCheckFile = new File("/bin/sh");
    if (osCheckFile.exists()) {
      // Linux
      command = new String[]{"/bin/sh", "-c", "df " + dir};
    } else {
      // Windows
      command = new String[]{"cmd", "/C", "dir", dir};
    }
    // Invoke the OS-specific command and parse the results
    Process process;
    String line = null;
    String thisLine = null;
    try {
      process = Runtime.getRuntime().exec(command);
      BufferedReader in =
          new BufferedReader(
          new InputStreamReader(process.getInputStream()));
      while ((thisLine = in.readLine()) != null) {
        line = thisLine;
        // On Windows NT, last line contains the available space
        if (line.endsWith("bytes free")) {
          // The number is formatted with commas, so extract just the numeric portion
          StringBuffer sb = new StringBuffer();
          for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (Character.isDigit(ch)) {
              sb.append(ch);
            }
          }
          // Convert string to long
          free = Long.parseLong(sb.toString());
          line = null;
          break;
        }
      }
      in.close();
      // On Linux, the last line contains the free space, 3rd from the last
      if (line != null) {
        StringTokenizer st = new StringTokenizer(line, " ");
        int items = st.countTokens();
        for (int i = 0; i < items - 3; i++) {
          st.nextToken();
        }
        //Get the 3rd from the last token
        free = Long.parseLong(st.nextToken()) * 1024;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return free;
  }


  /**
   *  Deletes all files and subdirectories under dir. Returns true if all
   *  deletions were successful. If a deletion fails, the method stops
   *  attempting to delete and returns false.<p>
   *
   *  re: Java Developers Almanac 1.4
   *
   *@param  dir  Description of the Parameter
   *@return      Description of the Return Value
   */
  public static boolean deleteDirectory(File dir) {
    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDirectory(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }
    // The directory is now empty so delete it
    return dir.delete();
  }
}

