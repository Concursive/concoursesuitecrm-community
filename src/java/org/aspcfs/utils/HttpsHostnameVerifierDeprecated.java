package org.aspcfs.utils;

import com.sun.net.ssl.*;

/**
 *  This is a replacement hostname verifier that accepts ANYTHING. This was
 *  created to allow self-signed X509 certificates to work.
 *
 *@author     matt rajkowski
 *@created    August 12, 2003
 *@version    $Id$
 */
public class HttpsHostnameVerifierDeprecated implements HostnameVerifier {

  /**
   *  Verifies certificate for Java 1.3 and down, specifically for Java 1.4
   *  systems that are configured to use old method for authenticating
   *
   *@param  hostname  Description of the Parameter
   *@param  session   Description of the Parameter
   *@return           Description of the Return Value
   */
  public boolean verify(String hostname, String session) {
    //accept any name
    return true;
  }
}

