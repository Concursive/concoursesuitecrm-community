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

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;

/**
 * Wrapper for a secure request and response to an SSL Server. Either p2p can
 * be used by supplying a keystore with private and server keys, or the key can
 * be retrieved from the server if it is signed by a trusted authority.
 *
 * @author Matt Rajkowski
 * @version $Id$
 * @created March 15, 2002
 */
public class SSLMessage {

  private String url = null;
  private int port = -1;
  private String message = null;
  private String keystoreAlias = "aspcfs";
  private String keystoreLocation = null;
  private String keystorePassword = null;
  private StringBuffer response = null;


  /**
   * Constructor for the SSLMessage object
   */
  public SSLMessage() {
  }


  /**
   * Sets the url attribute of the SSLMessage object
   *
   * @param tmp The new url value
   */
  public void setUrl(String tmp) {
    url = tmp;
  }


  /**
   * Sets the port attribute of the SSLMessage object
   *
   * @param tmp The new port value
   */
  public void setPort(int tmp) {
    port = tmp;
  }


  /**
   * When using peer2peer encryption, the local private key is used to identify
   * and encrypt data for the server.
   *
   * @param tmp The new keystoreAlias value
   */
  public void setKeystoreAlias(String tmp) {
    keystoreAlias = tmp;
  }


  /**
   * Sets the keystoreLocation attribute of the SSLMessage object
   *
   * @param tmp The new keystoreLocation value
   */
  public void setKeystoreLocation(String tmp) {
    this.keystoreLocation = tmp;
  }


  /**
   * Sets the keystorePassword attribute of the SSLMessage object
   *
   * @param tmp The new keystorePassword value
   */
  public void setKeystorePassword(String tmp) {
    this.keystorePassword = tmp;
  }


  /**
   * Sets the message attribute of the SSLMessage object
   *
   * @param tmp The new message value
   */
  public void setMessage(String tmp) {
    message = tmp;
  }


  /**
   * Gets the keystoreLocation attribute of the SSLMessage object
   *
   * @return The keystoreLocation value
   */
  public String getKeystoreLocation() {
    return keystoreLocation;
  }


  /**
   * Gets the keystorePassword attribute of the SSLMessage object
   *
   * @return The keystorePassword value
   */
  public String getKeystorePassword() {
    return keystorePassword;
  }


  /**
   * Gets the response attribute of the SSLMessage object
   *
   * @return The response value
   */
  public String getResponse() {
    return response.toString();
  }


  /**
   * Sends a message to an SSL Server and receives a response if there is one.
   * <p/>
   * <p/>
   * This method will instantiate the connection, send data, receive data, and
   * close the connection. If the server doesn't respond, then a timeout will
   * occur, closing the connection.<p>
   * <p/>
   * This method will only connect to servers that have a trusted certificate
   * -- either signed by a trusted authority or a certificate that has been
   * stored locally in the keystore and trusted.
   *
   * @return 0=OK, 1=Fatal Error
   * @throws Exception Description of Exception
   */
  public int send() throws Exception {

    if (url == null || port < 0 || message == null) {
      return 1;
    }

    SSLSocket socket = null;

    try {
      SSLSocketFactory factory;
      if (keystoreLocation != null && keystorePassword != null) {
        //System.setProperty("java.protocol.handler.pkgs",
        //    "com.sun.net.ssl.internal.www.protocol");
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "SSLMessage-> Keystore: " + keystoreLocation + ":" + keystorePassword);
        }

        char[] passphrase = keystorePassword.toCharArray();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keystoreLocation), passphrase);

        if (keyStore.containsAlias(keystoreAlias) == false) {
          System.out.println(
              "Cannot locate identity: local private key not found.");
        }

        //Holds this peer's private key
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
            "SunX509");
        keyManagerFactory.init(keyStore, passphrase);
        KeyManager[] arKeyManager = keyManagerFactory.getKeyManagers();

        //Holds other peers' certificates
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
            "SunX509");
        trustManagerFactory.init(keyStore);
        TrustManager[] arTrustManager = trustManagerFactory.getTrustManagers();

        SSLContext sslContext = SSLContext.getInstance("SSL");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        sslContext.init(arKeyManager, arTrustManager, secureRandom);

        factory = sslContext.getSocketFactory();
      } else {
        factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
      }

      if (System.getProperty("DEBUG") != null) {
        System.out.println("SSLMessage-> Opening SSLSocket");
      }
      socket = (SSLSocket) factory.createSocket(url, port);

      //Untested
      //Get the certificate presented by the server
      /*
       *  SSLSession session = socket.getSession();
       *  X509Certificate cert;
       *  try { cert = (X509Certificate)session.getPeerCertificates()[0]; }
       *  catch(SSLPeerUnverifiedException e) { // If no or invalid certificate
       *  System.err.println(session.getPeerHost() +
       *  " did not present a valid certificate.");
       *  return;
       *  }
       */
      socket.setSoTimeout(30000);
      socket.startHandshake();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SSLMessage-> Sending Data");
      }
      PrintWriter out = new PrintWriter(
          new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
      out.println(message);
      out.println();
      out.flush();

      //Receive response - will timeout if server doesn't close connection
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SSLMessage-> Receiving Data");
      }
      BufferedReader in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        appendResponseLine(inputLine);
      }
      socket.close();
      return 0;
    } catch (java.net.SocketTimeoutException toe) {
      if (socket != null) {
        socket.close();
      }
      return 0;
    } catch (IOException io) {
      io.printStackTrace(System.out);
      return 1;
    }
  }


  /**
   * Description of the Method
   *
   * @param in Description of Parameter
   */
  private void appendResponseLine(String in) {
    if (response == null) {
      response = new StringBuffer();
    }
    response.append(in + System.getProperty("line.separator"));
  }
}

