package org.aspcfs.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Class to execute ftp commands using the NCFTP application -> www.ncftp.com.
 *  The "ncftp" files need to be in the system path.
 *
 *@author     matt rajkowski
 *@created    October 23, 2002
 *@version    $Id$
 */
public class NCFTPApp {

  private String appExecutable = "ncftp";
  private int result = 0;
  private ArrayList files = new ArrayList();
  private String stdOut = null;
  private String stdErr = null;
  private boolean deleteSourceFilesAfterSend = false;
  private boolean makeRemoteDir = false;


  /**
   *  Constructor for the NCFTPApp object
   */
  public NCFTPApp() { }


  /**
   *  Sets the appExecutable attribute of the NCFTPApp object, this is the base
   *  name for all ncftp commands that will be appended, ncftpput, etc.
   *
   *@param  tmp  The new appExecutable value
   */
  public void setAppExecutable(String tmp) {
    this.appExecutable = tmp;
  }


  /**
   *  Sets the result attribute of the NCFTPApp object after executing a method
   *
   *@param  tmp  The new result value
   */
  public void setResult(int tmp) {
    this.result = tmp;
  }


  /**
   *  Sets the files attribute of the NCFTPApp object
   *
   *@param  tmp  The new files value
   */
  public void setFiles(ArrayList tmp) {
    this.files = tmp;
  }


  /**
   *  Sets the stdOut attribute of the NCFTPApp object
   *
   *@param  tmp  The new stdOut value
   */
  public void setStdOut(String tmp) {
    this.stdOut = tmp;
  }


  /**
   *  Sets the stdErr attribute of the NCFTPApp object
   *
   *@param  tmp  The new stdErr value
   */
  public void setStdErr(String tmp) {
    this.stdErr = tmp;
  }


  /**
   *  Sets the deleteSourceFilesAfterSend attribute of the NCFTPApp object
   *
   *@param  tmp  The new deleteSourceFilesAfterSend value
   */
  public void setDeleteSourceFilesAfterSend(boolean tmp) {
    this.deleteSourceFilesAfterSend = tmp;
  }


  /**
   *  Sets the makeRemoteDir attribute of the NCFTPApp object
   *
   *@param  tmp  The new makeRemoteDir value
   */
  public void setMakeRemoteDir(boolean tmp) {
    this.makeRemoteDir = tmp;
  }


  /**
   *  Gets the appExecutable attribute of the NCFTPApp object
   *
   *@return    The appExecutable value
   */
  public String getAppExecutable() {
    return appExecutable;
  }


  /**
   *  Gets the result attribute of the NCFTPApp object
   *
   *@return    The result value
   */
  public int getResult() {
    return result;
  }


  /**
   *  Gets the files attribute of the NCFTPApp object
   *
   *@return    The files value
   */
  public ArrayList getFiles() {
    return files;
  }


  /**
   *  Gets the stdOut attribute of the NCFTPApp object
   *
   *@return    The stdOut value
   */
  public String getStdOut() {
    return stdOut;
  }


  /**
   *  Gets the stdErr attribute of the NCFTPApp object
   *
   *@return    The stdErr value
   */
  public String getStdErr() {
    return stdErr;
  }


  /**
   *  Gets the deleteSourceFilesAfterSend attribute of the NCFTPApp object
   *
   *@return    The deleteSourceFilesAfterSend value
   */
  public boolean getDeleteSourceFilesAfterSend() {
    return deleteSourceFilesAfterSend;
  }


  /**
   *  Adds a filename to the File attribute of the NCFTPApp object
   *
   *@param  tmp  The feature to be added to the File attribute
   */
  public void addFile(String tmp) {
    files.add(tmp);
  }


  /**
   *  Uses the supplied ftp url and uploads files to the host using any
   *  parameters that have been set
   *
   *@param  ftpUrl  Description of the Parameter
   *@return         Description of the Return Value
   */
  public int put(String ftpUrl) {
    stdOut = null;
    stdErr = null;
    StringBuffer out = new StringBuffer();
    StringBuffer err = new StringBuffer();

    try {
      ArrayList command = new ArrayList();
      command.add(appExecutable + "put");
      if (this.getFtpUser(ftpUrl) != null) {
        command.add("-u");
        command.add(this.getFtpUser(ftpUrl));
      }
      if (this.getFtpPassword(ftpUrl) != null) {
        command.add("-p");
        command.add(this.getFtpPassword(ftpUrl));
      }
      //Do not try to resume
      command.add("-Z");
      //Upload as a tmp file, then rename
      command.add("-S");
      command.add(".tmp");
      //Make sure the remote directory exists, or create it
      if (makeRemoteDir) {
        command.add("-m");
      }
      //Delete the source files when done
      if (deleteSourceFilesAfterSend) {
        command.add("-DD");
      }
      //Set the host
      command.add(this.getFtpHost(ftpUrl));
      //Set the remote dir
      command.add(this.getFtpRemoteDir(ftpUrl));
      //Add the files
      Iterator fileList = files.iterator();
      while (fileList.hasNext()) {
        String file = (String) fileList.next();
        command.add(file);
      }

      String[] commands = (String[]) command.toArray(new String[0]);
      Process process = Runtime.getRuntime().exec(commands);
      BufferedReader brErr
           = new BufferedReader(
          new InputStreamReader(process.getErrorStream()));
      /*
       *  BufferedReader brOut
       *  = new BufferedReader(
       *  new InputStreamReader(process.getOutputStream()));
       */
      String tmp = null;
      while ((tmp = brErr.readLine()) != null) {
        err.append(tmp);
      }
      stdErr = err.toString();
      /*
       *  while ((tmp = brOut.readLine()) != null) {
       *  out.append(tmp);
       *  }
       */
      process.waitFor();
      result = process.exitValue();
    } catch (Exception e) {
      result = -1;
      e.printStackTrace(System.out);
    }

    return result;
  }


  /**
   *  Decodes an ftp url to get the username
   *
   *@param  ftpUrl  Description of the Parameter
   *@return         The ftpUser value
   */
  private static String getFtpUser(String ftpUrl) {
    String tmp = ftpUrl.substring(
        ftpUrl.indexOf("ftp://") + 6, ftpUrl.indexOf("@"));
    if (tmp.indexOf(":") > -1) {
      return tmp.substring(0, tmp.indexOf(":"));
    } else {
      return tmp;
    }
  }


  /**
   *  Decodes an ftp url to get the password
   *
   *@param  ftpUrl  Description of the Parameter
   *@return         The ftpPassword value
   */
  private static String getFtpPassword(String ftpUrl) {
    String tmp = ftpUrl.substring(ftpUrl.indexOf("ftp://") + 6, ftpUrl.indexOf("@"));
    if (tmp.indexOf(":") > -1) {
      return tmp.substring(tmp.indexOf(":") + 1);
    } else {
      return null;
    }
  }


  /**
   *  Decodes an ftp url to get the host
   *
   *@param  ftpUrl  Description of the Parameter
   *@return         The ftpHost value
   */
  private static String getFtpHost(String ftpUrl) {
    String tmp = ftpUrl.substring(ftpUrl.indexOf("@") + 1);
    if (tmp.indexOf(":") > -1) {
      return tmp.substring(0, tmp.indexOf(":"));
    } else {
      return tmp;
    }
  }


  /**
   *  Decodes an ftp url to get the remote dir to begin with
   *
   *@param  ftpUrl  Description of the Parameter
   *@return         The ftpRemoteDir value
   */
  private static String getFtpRemoteDir(String ftpUrl) {
    String tmp = ftpUrl.substring(ftpUrl.lastIndexOf(":") + 1);
    if (tmp.indexOf("@") > -1) {
      return "./";
    } else {
      return tmp;
    }
  }
}

