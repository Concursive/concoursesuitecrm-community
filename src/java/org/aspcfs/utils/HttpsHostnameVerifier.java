package org.aspcfs.utils;

import javax.net.ssl.*;

/**
 *  This is a replacement hostname verifier that accepts ANYTHING. This was
 *  created to allow self-signed X509 certificates to work.
 *
 *@author     matt rajkowski
 *@created    August 12, 2003
 *@version    $Id$
 */
public class HttpsHostnameVerifier implements HostnameVerifier {

  /**
   *  Verifies certificate for Java 1.4 and up
   *
   *@param  hostname  Description of the Parameter
   *@param  session   Description of the Parameter
   *@return           Description of the Return Value
   */
  public boolean verify(String hostname, SSLSession session) {
    //accept any name
    return true;
  }
}

