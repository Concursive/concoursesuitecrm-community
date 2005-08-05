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

import org.aspcfs.modules.contacts.base.Contact;

import java.util.StringTokenizer;

/**
 * Used for converting a person's name into separate fields
 *
 * @author matt rajkowski
 * @version $Id: ContactNameFormatter.java,v 1.1 2004/06/16 17:24:53
 *          mrajkowski Exp $
 * @created June 16, 2004
 */
public class ContactNameFormatter {

  /**
   * Constructor for the ContactFormatter object
   */
  public ContactNameFormatter() {
  }


  /**
   * Description of the Method
   */
  public void config() {
  }


  /**
   * Formats a name, like Rhonda J. Smith, into separate fields
   *
   * @param thisContact Description of the Parameter
   * @param name        Description of the Parameter
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

