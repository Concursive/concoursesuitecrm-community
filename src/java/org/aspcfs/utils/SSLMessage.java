package com.darkhorseventures.utils;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;

/**
 *  Creates an SSLSocket connection to a remote SSL server, on the 
 *  specified port.
 *
 *@author     Matt Rajkowski
 *@created    March 15, 2002
 */
public class SSLMessage {

  private String url = null;
  private int port = -1;
  private String message = null;


  /**
   *  Constructor for the SSLMessage object
   */
  public SSLMessage() { }


  /**
   *  Sets the url attribute of the SSLMessage object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    url = tmp;
  }


  /**
   *  Sets the port attribute of the SSLMessage object
   *
   *@param  tmp  The new port value
   */
  public void setPort(int tmp) {
    port = tmp;
  }


  /**
   *  Sets the message attribute of the SSLMessage object
   *
   *@param  tmp  The new message value
   */
  public void setMessage(String tmp) {
    message = tmp;
  }


  /**
   *  Description of the Method
   *
   *@return                Description of the Returned Value
   *@exception  Exception  Description of Exception
   */
  public int send() throws Exception {

    if (url == null || port < 0 || message == null) {
      return 1;
    }

    try {
      //Prepare SSL... the host key needs to be in the keyring
      System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
      SSLSocketFactory factory =
          (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket socket = (SSLSocket) factory.createSocket(url, port);
      socket.startHandshake();
      //Send request
      PrintWriter out = new PrintWriter(
          new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
      out.println(message);
      out.println();
      out.flush();

      //Receive response
      /*
       *  BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       *  String inputLine;
       *  while ((inputLine = in.readLine()) != null && inputLine.indexOf("") == -1) {
       *  System.out.println(inputLine);
       *  }
       *  if (inputLine != null) {
       *  System.out.println(inputLine);
       *  }
       *  in.close();
       */
      out.close();
      socket.close();
      return 0;
    } catch (IOException io) {
      io.printStackTrace(System.out);
      return 1;
    }
  }
}

