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
package org.aspcfs.modules.contacts.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public class LoadContactDetails extends ObjectHookComponent implements ComponentInterface {

  public final static String CONTACT = "contact";
  public final static String ENTERED_BY_CONTACT = "contactEnteredByContact";
  public final static String MODIFIED_BY_CONTACT = "contactModifiedByContact";


  /**
   * Gets the description attribute of the LoadAccountDetails object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Load all contact information for use in other steps";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Contact thisContact = (Contact) context.getThisObject();
    Contact previousContact = (Contact) context.getPreviousObject();
    Connection db = null;
    try {
      db = getConnection(context);
      if (thisContact.getModifiedBy() > 0) {
        User user = new User(db, thisContact.getModifiedBy());
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(MODIFIED_BY_CONTACT, contact);
      }
      if (thisContact.getEnteredBy() > 0) {
        User user = null;
        if (previousContact != null) {
          user = new User(db, previousContact.getEnteredBy());
        } else {
          user = new User(db, thisContact.getEnteredBy());
        }
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(ENTERED_BY_CONTACT, contact);
      }
      result = true;
    } catch (Exception e) {

    } finally {
      freeConnection(context, db);
    }
    return result;
  }
}

