/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.apps.icelets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.*;
import java.util.zip.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    April 26, 2006
 *@version    $Id: Exp $
 */
public class ExportPortalTemplates {

  /**
   *  Constructor for the ExportPortalTemplates object
   */
  public ExportPortalTemplates() { }


  /**
   *  The main program for the ExportPortalTemplates class
   *
   *@param  args  The command line arguments
   */
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println(
          "Usage: java ExportPortalTemplates [sourcefilepath][destinationfilepath]");
    } else {
      System.setProperty("DEBUG", "1");
      new ExportPortalTemplates(args);
    }
    System.exit(0);
  }


  /**
   *  Constructor for the ExportPortalTemplates object
   *
   *@param  args  Description of the Parameter
   */
  public ExportPortalTemplates(String[] args) {

    try {
      String sourceFilePath = args[0];
      String destinationFilePath = args[1];
      String fs = System.getProperty("file.separator");
      zipTemplates(sourceFilePath + fs + "portal_templates", destinationFilePath + fs + "WEB-INF" + fs + "portal_templates");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(2);
    }
  }


  /**
   *  For each portal templates in the source directory a zip file is created in
   *  the destination directory
   *
   *@param  sourcePath       Description of the Parameter
   *@param  destinationPath  Description of the Parameter
   */
  public void zipTemplates(String sourcePath, String destinationPath) throws Exception{

    String fs = System.getProperty("file.separator");
    
    File sourceDirectory = new File(sourcePath);
    if (sourceDirectory.isDirectory()) {
      String[] locale = sourceDirectory.list();
      int localeCount = 0;
      //Fetch locale subdirectories where locale based portal templates exist
      while (localeCount < locale.length) {
        //System.out.println("0. " + sourcePath + fs + locale[localeCount]);
        if (!locale[localeCount].startsWith(".")){
          File localeDirectory = new File(sourcePath + fs + locale[localeCount]);
          if (localeDirectory.isDirectory()) {
            //System.out.println("1. " + sourcePath + fs + locale[localeCount]);
            //get portal template folders in locale directory
            String[] template = localeDirectory.list();
  
            int templateCount = 0;
            while (templateCount < template.length) {
  
              if (!template[templateCount].startsWith(".")){
                //System.out.println("2a. " + sourcePath + fs + locale[localeCount] + fs + template[templateCount]);
                //System.out.println("2b. " + destinationPath + fs + locale[localeCount] + fs + template[templateCount]);
                
                //creating destination directory
                new File(destinationPath + fs + locale[localeCount]).mkdirs();
                
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(destinationPath + fs + locale[localeCount] + fs + template[templateCount] + ".zip"));
                zipOutputStream.setLevel(Deflater.DEFAULT_COMPRESSION);
                zipAllFiles(new File(sourcePath + fs + locale[localeCount] + fs + template[templateCount]), zipOutputStream);
                zipOutputStream.close();
              }
              templateCount++;
            }
          }
        }
        localeCount++;
      }
    } else {
      System.out.println("Incorrect directory specified for portal templates");
    }
  }


  /**
   *  zips all files in the source tree into a zip file
   *
   *@param  dir            Description of the Parameter
   *@param  out            Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void zipAllFiles(File dir, ZipOutputStream out) throws Exception {

    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        if (!children[i].startsWith(".")){
          zipAllFiles(new File(dir, children[i]), out);
        }
      }
    } else {
      FileInputStream in = new FileInputStream(dir);
      byte[] buffer = new byte[in.available()];

      // determine name of zip entry
      String dirPath = dir.getPath();
      String pathInZip = "";
      String[] paths = dir.getPath().split("website");
      if (paths.length != 2) {
        throw new Exception("Could not create package");
      } else {
        pathInZip = "website" + paths[1];
      }

      // Add ZIP entry to output stream.
      out.putNextEntry(new ZipEntry(pathInZip));
      // Transfer bytes from the current file to the ZIP file
      int len;
      while ((len = in.read(buffer)) > 0) {
        out.write(buffer, 0, len);
      }
      // Close the current entry
      out.closeEntry();
      // Close the current file input stream
      in.close();
    }
  }
}

