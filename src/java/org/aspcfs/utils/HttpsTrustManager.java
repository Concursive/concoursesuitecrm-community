package org.aspcfs.utils;

import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import javax.net.ssl.X509TrustManager;

/**
 *  This is a replacement trust manager that accepts ANYTHING. This was created
 *  to allow self-signed X509 certificates to work.<p>
 *
 *  This class is a work in progress, which later on should at least detect some
 *  credentials.
 *
 *@author     matt rajkowski
 *@created    March 25, 2003
 *@version    $Id$
 */
public class HttpsTrustManager implements X509TrustManager {
  /**
   *  Constructor for the HttpsTrustManager object
   */
  HttpsTrustManager() { }


  /**
   *  Description of the Method
   *
   *@param  chain                     Description of the Parameter
   *@param  authType                  Description of the Parameter
   *@exception  CertificateException  Description of the Exception
   */
  public void checkClientTrusted(X509Certificate chain[], String authType) throws CertificateException {
  }


  /**
   *  Description of the Method
   *
   *@param  chain                       Description of the Parameter
   *@param  authType                    Description of the Parameter
   *@exception  CertificateException    Description of the Exception
   */
  public void checkServerTrusted(X509Certificate chain[], String authType) throws CertificateException {
  }


  /**
   *  Gets the acceptedIssuers attribute of the HttpsTrustManager object
   *
   *@return    The acceptedIssuers value
   */
  public X509Certificate[] getAcceptedIssuers() {
    return null;
  }
}

