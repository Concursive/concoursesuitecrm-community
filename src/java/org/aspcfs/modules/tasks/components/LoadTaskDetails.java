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
package org.aspcfs.modules.tasks.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.tasks.base.Task;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public class LoadTaskDetails extends ObjectHookComponent implements ComponentInterface {

  public final static String TASK = "task";
  public final static String ENTERED_BY_CONTACT = "taskEnteredByContact";
  public final static String MODIFIED_BY_CONTACT = "taskModifiedByContact";


  /**
   * Gets the description attribute of the LoadTaskDetails object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Load all task information for use in other steps";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Task thisTask = (Task) context.getThisObject();
    Task previousTask = (Task) context.getPreviousObject();
    Connection db = null;
    try {
      db = getConnection(context);
      if (thisTask.getModifiedBy() > 0) {
        User user = new User(db, thisTask.getModifiedBy());
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(MODIFIED_BY_CONTACT, contact);
      }
      if (thisTask.getEnteredBy() > 0) {
        User user = null;
        if (previousTask != null) {
          user = new User(db, previousTask.getEnteredBy());
        } else {
          user = new User(db, thisTask.getEnteredBy());
        }
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(ENTERED_BY_CONTACT, contact);
      }
      Contact contact = new Contact(db, thisTask.getContactId());
      if (contact.getEmployee()) {
        return false;
      }
      result = true;
    } catch (Exception e) {
    } finally {
      freeConnection(context, db);
    }
    return result;
  }
}

