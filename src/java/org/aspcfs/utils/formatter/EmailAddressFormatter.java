package org.aspcfs.utils.formatter;

import org.aspcfs.modules.base.EmailAddress;

/**
 *  Takes an email address and formats the various fields to make the records
 *  consistent and presentable.
 *
 *@author     matt rajkowski
 *@created    March 5, 2003
 *@version    $Id: EmailAddressFormatter.java,v 1.1.2.2 2003/03/06 17:52:06
 *      mrajkowski Exp $
 */
public class EmailAddressFormatter {

  /**
   *  Constructor for the EmailAddressFormatter object
   */
  public EmailAddressFormatter() { }


  /**
   *  Description of the Method
   */
  public void config() {

  }


  /**
   *  Description of the Method
   *
   *@param  thisAddress  Description of the Parameter
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
        email = email.substring(1, email.length() -1);
      }
      thisAddress.setEmail(email);
    }
  }
}

