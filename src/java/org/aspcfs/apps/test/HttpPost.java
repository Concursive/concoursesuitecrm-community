package org.aspcfs.apps.test;

import java.net.*;
import java.io.*;

/**
 *  Application to test HTTP posting parameters and files
 *
 *@author     --
 *@created    January 21, 2002
 *@version    $Id$
 */
public class HttpPost {

  /**
   *  Description of the Method
   *
   *@param  name      Description of the Parameter
   *@param  value     Description of the Parameter
   *@param  out       Description of the Parameter
   *@param  boundary  Description of the Parameter
   */
  private static void writeParam(String name, String value, DataOutputStream out, String boundary) {
    try {
      out.writeBytes("content-disposition: form-data; name=\"" + name + "\"\r\n\r\n");
      out.writeBytes(value);
      out.writeBytes("\r\n" + "--" + boundary + "\r\n");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }


  /**
   *  Description of the Method
   *
   *@param  name      Description of the Parameter
   *@param  filePath  Description of the Parameter
   *@param  out       Description of the Parameter
   *@param  boundary  Description of the Parameter
   */
  private static void writeFile(String name, String filePath, DataOutputStream out, String boundary) {
    try {
      out.writeBytes("content-disposition: form-data; name=\"" + name + "\"; filename=\""
           + filePath + "\"\r\n");
      out.writeBytes("content-type: application/octet-stream" + "\r\n\r\n");
      FileInputStream fis = new FileInputStream(filePath);
      while (true) {
        synchronized (buffer) {
          int amountRead = fis.read(buffer);
          if (amountRead == -1) {
            break;
          }
          out.write(buffer, 0, amountRead);
        }
      }
      fis.close();
      out.writeBytes("\r\n" + "--" + boundary + "\r\n");
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }


  final static int BUFF_SIZE = 1024;
  final static byte[] buffer = new byte[BUFF_SIZE];


  /**
   *  Creates a new instance
   */
  public HttpPost() { }


  /**
   *@param  args  the command line arguments
   */
  public static void main(String args[]) {
    try {
      URL servlet = new URL("http://127.0.0.1/ProcessDocument.do");
      URLConnection conn = servlet.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setUseCaches(false);
      String boundary = "---------------------------7d226f700d0";
      conn.setRequestProperty("Content-type", "multipart/form-data; boundary=" + boundary);
      //conn.setRequestProperty("Referer", "http://127.0.0.1/index.jsp");
      //conn.setRequestProperty("Cache-Control", "no-cache");

      DataOutputStream out = new DataOutputStream(conn.getOutputStream());
      out.writeBytes("--" + boundary + "\r\n");
      writeParam("id", "127.0.0.1", out, boundary);
      writeParam("code", "TEST", out, boundary);
      writeParam("systemId", "5", out, boundary);
      writeParam("type", "ticket", out, boundary);
      writeParam("subject", "Voice recording", out, boundary);
      writeParam("tid", "140", out, boundary);
      writeParam("enteredBy", "1", out, boundary);
      writeFile("file1", "build.xml", out, boundary);
      out.flush();
      out.close();

      InputStream stream = conn.getInputStream();
      BufferedInputStream in = new BufferedInputStream(stream);
      int i = 0;
      while ((i = in.read()) != -1) {
        System.out.write(i);
      }
      in.close();
    } catch (Exception e) {
      System.out.println(e.toString());
    }
  }
}


