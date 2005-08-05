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
package org.aspcfs.utils.formatter;

import org.aspcfs.modules.base.EmailAddress;

/**
 * Takes an email address and formats the various fields to make the records
 * consistent and presentable.
 *
 * @author matt rajkowski
 * @version $Id: EmailAddressFormatter.java,v 1.1.2.2 2003/03/06 17:52:06
 *          mrajkowski Exp $
 * @created March 5, 2003
 */
public class EmailAddressFormatter {

  /**
   * Constructor for the EmailAddressFormatter object
   */
  public EmailAddressFormatter() {
  }


  /**
   * Description of the Method
   */
  public void config() {

  }


  /**
   * Description of the Method
   *
   * @param thisAddress Description of the Parameter
   */
  public void format(EmailAddress thisAddress) {
    String email = thisAddress.getEmail();
    if (email != null) {
      //Remove email address showing twice due to export issue
      if (email.indexOf(" SMTP ") > -1) {
        email = email.substring(0, email.indexOf(" SMTP ")).trim();
      }
      //Remove everything after a space
      if (email.indexOf(" ") > -1) {
        email = email.substring(0, email.indexOf(" ")).trim();
      }
      //Remove ' marks at beginning and end of address
      if (email.length() > 0 && email.charAt(0) == '\'' &&
          email.charAt(email.length() - 1) == '\'') {
        email = email.substring(1, email.length() - 1);
      }
      thisAddress.setEmail(email);
    }
  }
}

