package com.darkhorseventures.utils;

import java.net.*;
import java.io.*;
import javax.net.ssl.*;

public class SSLMessage {

  private String url = null;
  private int port = -1;
  private String message = null;

  public SSLMessage() { }
  
  public void setUrl(String tmp) {
    url = tmp;
  }
  
  public void setPort(int tmp) {
    port = tmp;
  }
  
  public void setMessage(String tmp) {
    message = tmp;
  }
  
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
/*       BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null && inputLine.indexOf("") == -1) {
        System.out.println(inputLine);
      }
      if (inputLine != null) {
        System.out.println(inputLine);
      }
      in.close();
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
