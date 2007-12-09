/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import javax.servlet.ServletContext;
import java.io.*;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Helper methods for dealing with file operations
 *
 * @author matt rajkowski
 * @version $Id: FileUtils.java 12404 2005-08-05 13:37:07 -0400 (Fri, 05 Aug
 *          2005) mrajkowski $
 * @created August 25, 2003
 */
public class FileUtils {

  /**
   * Copies the specified source file to the destination file
   *
   * @param sourceFile      Description of the Parameter
   * @param destinationFile Description of the Parameter
   * @return Description of the Return Value
   */
  public static boolean copyFile(File sourceFile, File destinationFile) {
    return copyFile(sourceFile, destinationFile, true);
  }


  /**
   * Description of the Method
   *
   * @param sourceFile      Description of the Parameter
   * @param destinationFile Description of the Parameter
   * @param overwrite       Description of the Parameter
   * @return Description of the Return Value
   */
  public static boolean copyFile(File sourceFile, File destinationFile, boolean overwrite) {
    String fs = System.getProperty("file.separator");
    int wildcardPos = sourceFile.getName().indexOf("*");
    if (wildcardPos > 0) {
      if (!sourceFile.getParentFile().exists()) {
        System.out.println(
            "FileUtils-> Source directory does not exist: " + sourceFile.getParentFile());
        return false;
      }
      File[] fileNames = sourceFile.getParentFile().listFiles();
      int count = fileNames.length;
      if (count > 0) {
        String part1 = sourceFile.getName().substring(0, wildcardPos);
        String part2 = null;
        if (wildcardPos < sourceFile.getName().length()) {
          part2 = sourceFile.getName().substring(wildcardPos + 1);
        }
        for (int i = 0; i < count; i++) {
          File thisFile = fileNames[i];
          if (thisFile.getName().startsWith(part1) &&
              (part2 == null || (part2 != null && thisFile.getName().endsWith(
                  part2)))) {
            copyFile(fileNames[i], destinationFile, overwrite);
          }
        }
      } else {
        System.out.println(
            "FileUtils-> No parent files found in: " + sourceFile.getParentFile());
      }
      return true;
    }
    //Check to see if source exists
    if (!sourceFile.exists()) {
      System.out.println("FileUtils-> Source file not found: " + sourceFile);
      return false;
    }
    //If destination is a directory then set it as a file
    if (destinationFile.isDirectory()) {
      destinationFile = new File(
          destinationFile.getPath() + fs + sourceFile.getName());
    }
    //Check to see if source and destination file are the same
    if (sourceFile.equals(destinationFile)) {
      System.out.println("FileUtils-> Source and destination are the same");
      return false;
    }
    //Skip if overwrite is false
    if (!overwrite) {
      if (destinationFile.exists()) {
        System.out.println("FileUtils-> Destination already exists");
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
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "FileUtils-> Copied: " + sourceFile + " to " + destinationFile);
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
   * Description of the Method
   *
   * @param bytes           Description of the Parameter
   * @param destinationFile Description of the Parameter
   * @param overwrite       Description of the Parameter
   * @return Description of the Return Value
   * @throws IOException Description of the Exception
   */
  public static boolean copyBytesToFile(byte[] bytes, File destinationFile, boolean overwrite) throws IOException {
    //If destination is a directory then set it as a file
    if (destinationFile.isDirectory()) {
      destinationFile = new File(
          destinationFile.getPath());
    }
    //Skip if overwrite is false
    if (!overwrite) {
      if (destinationFile.exists()) {
        System.out.println("FileUtils-> Destination already exists");
        return true;
      }
    }
    FileOutputStream destination = null;
    try {
      destination = new FileOutputStream(destinationFile);
      destination.write(bytes);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "FileUtils-> Copied bytes to " + destinationFile);
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
    }
    return true;
  }


  /**
   * Copies a file from servlet context stream to a file
   *
   * @param context         Description of the Parameter
   * @param filename        Description of the Parameter
   * @param destinationFile Description of the Parameter
   * @param overwrite       Description of the Parameter
   * @return Description of the Return Value
   * @throws IOException Description of the Exception
   */
  public static boolean copyFile(ServletContext context, String filename, File destinationFile, boolean overwrite) throws IOException {
    String fs = System.getProperty("file.separator");
    File sourceFile = new File(filename);
    int wildcardPos = sourceFile.getName().indexOf("*");
    if (wildcardPos > 0) {
      // NOTE: this handles "/WEB-INF/setup/init/workflow_*.xml"
      if (context.getResourcePaths("/" + sourceFile.getParentFile().getPath()) == null) {
        System.out.println(
            "FileUtils-> Source resource does not exist: " + sourceFile.getParentFile());
        return false;
      }
      Set fileNames = context.getResourcePaths("/" + sourceFile.getParentFile().getPath());
      if (!fileNames.isEmpty()) {
        String part1 = sourceFile.getName().substring(0, wildcardPos);
        String part2 = null;
        if (wildcardPos < sourceFile.getName().length()) {
          part2 = sourceFile.getName().substring(wildcardPos + 1);
        }
        Iterator i = fileNames.iterator();
        while (i.hasNext()) {
          String resource = (String) i.next();
          File thisFile = new File(resource);
          if (thisFile.getName().startsWith(part1) &&
              (part2 == null || (thisFile.getName().endsWith(part2)))) {
            copyFile(context, resource, destinationFile, overwrite);
          }
        }
      } else {
        System.out.println(
            "FileUtils-> No parent files found in: " + sourceFile.getParentFile());
      }
      return true;
    }
    //Check to see if source resource exists
    if (context.getResource(filename) == null) {
      System.out.println("FileUtils-> Source file not found: " + filename);
      return false;
    }
    //If destination is a directory then set it as a file
    if (destinationFile.isDirectory()) {
      destinationFile = new File(
          destinationFile.getPath() + fs + sourceFile.getName());
    }
    //Skip if overwrite is false
    if (!overwrite) {
      if (destinationFile.exists()) {
        System.out.println("FileUtils-> Destination already exists");
        return true;
      }
    }
    //Copy the file
    InputStream source = context.getResourceAsStream(filename);
    FileOutputStream destination = null;
    try {
      destination = new FileOutputStream(destinationFile);
      byte[] buffer = new byte[4096];
      int read = -1;
      while ((read = source.read(buffer)) != -1) {
        destination.write(buffer, 0, read);
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "FileUtils-> Copied: " + sourceFile + " to " + destinationFile);
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
   * Gets the bytes free on the system for the specified directory
   *
   * @param dir Description of the Parameter
   * @return The freeBytes value
   */
  public static long getFreeBytes(String dir) {
    long free = -1;
    String[] command = null;
    // Create OS-specific command to get available space
    File osCheckFile = new File("/bin/sh");
    if (osCheckFile.exists()) {
      // Linux, Unix, Mac OSX
      dir = StringUtils.replace(dir, " ", "\\ ");
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
      int blockSize = 1024;
      process = Runtime.getRuntime().exec(command);
      BufferedReader in =
          new BufferedReader(new InputStreamReader(process.getInputStream()));
      while ((thisLine = in.readLine()) != null) {
        line = thisLine;
        // Using df, the block size must be incorporated
        if (line.indexOf("512-blocks") > -1) {
          blockSize = 512;
        }
        // On Windows NT, last line contains the available space
        if (line.endsWith("bytes free")) {
          if (line.indexOf("Dir(s)") > -1) {
            line = line.substring(line.indexOf("Dir(s)"));
          }
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
        free = Long.parseLong(st.nextToken()) * blockSize;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return free;
  }


  /**
   * Deletes all files and subdirectories under dir. Returns true if all
   * deletions were successful. If a deletion fails, the method stops
   * attempting to delete and returns false.<p>
   *
   * <p/>
   *
   * re: Java Developers Almanac 1.4
   *
   * @param dir Description of the Parameter
   * @return Description of the Return Value
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


  /**
   * Checks to see if the file specified exists
   *
   * @param fullPath Description of the Parameter
   * @return Description of the Return Value
   */
  public static boolean fileExists(String fullPath) {
    File thisFile = new File(fullPath);
    return thisFile.exists();
  }


  /**
   * Gets the relativeSize attribute of the FileUtils class
   *
   * @param size   Description of the Parameter
   * @param locale Description of the Parameter
   * @return The relativeSize value
   */
  public static String getRelativeSize(float size, Locale locale) {
    if (size == -1) {
      return ("Could not be determined");
    }
    // Make the numbers look nice
    NumberFormat formatter = null;
    if (locale == null) {
      formatter = NumberFormat.getInstance();
    } else {
      formatter = NumberFormat.getInstance(locale);
    }
    // GB
    if (size > 1000000000) {
      formatter.setMaximumFractionDigits(2);
      return (formatter.format(size / 1000 / 1000 / 1000) + " GB");
    }
    // MB
    if (size > 1000000) {
      formatter.setMaximumFractionDigits(1);
      return (formatter.format(size / 1000 / 1000) + " MB");
    }
    // KB
    if (size > 1000) {
      formatter.setMaximumFractionDigits(0);
      return (formatter.format(size / 1000) + " KB");
    }
    // Bytes
    return (formatter.format(size) + " bytes");
  }


  /**
   * Concats the directory and file names
   *
   * @param dir      Description of the Parameter
   * @param fileName Description of the Parameter
   * @return The fileName value
   * @throws IllegalArgumentException Description of the Exception
   */
  public static String getFileName(String dir, String fileName)
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
   * Gets the fileName attribute of the FileUtils class
   *
   * @param fileName Description of the Parameter
   * @return The fileName value
   * @throws IllegalArgumentException Description of the Exception
   */
  public static String getFileName(String fileName) throws IllegalArgumentException {
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
}

