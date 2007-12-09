/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.communications.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactHistory;

import java.sql.Connection;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public class ModifyMultipleContactHistory extends ObjectHookComponent implements ComponentInterface {
  public final static String LEVEL = "history.level";
  public final static String LINK_OBJECT_ID = "history.linkObjectId";
  public final static String LINK_ITEM_ID = "history.linkItemId";
  public final static String DESCRIPTION = "history.description";
  public final static String ENABLED = "history.enabled";
  public final static String STATUS = "history.status";
  public final static String TYPE = "history.type";
  public final static String ENTERED_BY = "history.enteredby";
  public final static String MODIFIED_BY = "history.modifiedby";


  /**
   * Gets the description attribute of the ModifyMultipleContactHistory object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Update the contact's history";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    ContactHistory history = null;
    Campaign campaign = null;
    Connection db = null;
    try {
      db = getConnection(context);
      campaign = (Campaign) context.getThisObject();
      if (campaign.getActive() && campaign.getContactList().size() > 0) {
        Iterator iterator = (Iterator) campaign.getContactList().iterator();
        while (iterator.hasNext()) {
          Contact contact = (Contact) iterator.next();
          history = new ContactHistory();
          history.setContactId(contact.getId());
          history.setLinkObjectId(context.getParameterAsInt(LINK_OBJECT_ID));
          history.setLinkItemId(context.getParameterAsInt(LINK_ITEM_ID));
          history.queryRecord(db);
          history.setLevel(context.getParameterAsInt(LEVEL));
          history.setStatus(context.getParameter(STATUS));
          history.setEnabled(context.getParameter(ENABLED));
          history.setModifiedBy(context.getParameter(MODIFIED_BY));
          if (history.getId() != -1) {
            result = (history.update(db) == 1);
          } else {
            result = history.insert(db);
          }
        }
      } else {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      freeConnection(context, db);
    }
    return result;
  }
}

