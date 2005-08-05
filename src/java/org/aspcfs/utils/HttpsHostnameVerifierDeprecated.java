/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.utils;

import com.sun.net.ssl.HostnameVerifier;

/**
 * This is a replacement hostname verifier that accepts ANYTHING. This was
 * created to allow self-signed X509 certificates to work.
 *
 * @author matt rajkowski
 * @version $Id: HttpsHostnameVerifierDeprecated.java,v 1.2 2003/09/08
 *          20:55:10 mrajkowski Exp $
 * @created August 12, 2003
 */
public class HttpsHostnameVerifierDeprecated implements HostnameVerifier {

  /**
   * Verifies certificate for Java 1.3 and down, specifically for Java 1.4
   * systems that are configured to use old method for authenticating
   *
   * @param hostname Description of the Parameter
   * @param session  Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean verify(String hostname, String session) {
    //accept any name
    return true;
  }
}

