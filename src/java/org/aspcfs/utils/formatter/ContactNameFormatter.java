package org.aspcfs.utils.formatter;

import org.aspcfs.modules.contacts.base.Contact;
import java.util.*;

/**
 *  Used for converting a person's name into separate fields
 *
 *@author     matt rajkowski
 *@created    June 16, 2004
 *@version    $Id$
 */
public class ContactNameFormatter {

  /**
   *  Constructor for the ContactFormatter object
   */
  public ContactNameFormatter() { }


  /**
   *  Description of the Method
   */
  public void config() { }


  /**
   *  Formats a name, like Rhonda J. Smith, into separate fields
   *
   *@param  thisContact  Description of the Parameter
   *@param  name         Description of the Parameter
   */
  public void format(Contact thisContact, String name) {
    if (name != null && !name.equals("")) {
      StringTokenizer st = new StringTokenizer(name, " ");
      if (st.hasMoreTokens()) {
        thisContact.setNameFirst(st.nextToken());
      }
      if (st.hasMoreTokens()) {
        String midCheck = st.nextToken();
        if (st.hasMoreTokens()) {
          thisContact.setNameMiddle(midCheck);
        } else {
          thisContact.setNameLast(midCheck);
        }
      }
      while (st.hasMoreTokens()) {
        String lastCheck = st.nextToken();
        if (thisContact.getNameLast() != null) {
          thisContact.setNameLast(thisContact.getNameLast() + " " + lastCheck);
        } else {
          thisContact.setNameLast(lastCheck);
        }
      }
    }
  }

}

