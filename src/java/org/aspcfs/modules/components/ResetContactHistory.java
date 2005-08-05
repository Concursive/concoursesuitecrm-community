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
package org.aspcfs.modules.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.contacts.base.ContactHistory;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public class ResetContactHistory extends ObjectHookComponent implements ComponentInterface {
  public final static String LEVEL = "history.level";
  public final static String CONTACT_ID = "history.contactId";
  public final static String LINK_OBJECT_ID = "history.linkObjectId";
  public final static String LINK_ITEM_ID = "history.linkItemId";
  public final static String DESCRIPTION = "history.description";
  public final static String ENABLED = "history.enabled";
  public final static String STATUS = "history.status";
  public final static String TYPE = "history.type";
  public final static String PREVIOUS_CONTACT_ID = "history.previousContactId";
  public final static String MODIFIED_BY = "history.modifiedby";


  /**
   * Gets the description attribute of the ResetContactHistory object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Reset the contact's history entry";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    ContactHistory history = new ContactHistory();
    Connection db = null;
    try {
      db = getConnection(context);
      int previousContactId = context.getParameterAsInt(PREVIOUS_CONTACT_ID);
      int currentContactId = context.getParameterAsInt(CONTACT_ID);
      if (previousContactId != currentContactId && previousContactId != -1) {
        history.setContactId(previousContactId);
      } else {
        history.setContactId(currentContactId);
      }
      history.setLinkObjectId(context.getParameterAsInt(LINK_OBJECT_ID));
      history.setLinkItemId(context.getParameterAsInt(LINK_ITEM_ID));
      history.queryRecord(db);
      history.setLevel(context.getParameterAsInt(LEVEL));
      history.setDescription(context.getParameter(DESCRIPTION));
      history.setStatus(context.getParameter(STATUS));
      history.setType(context.getParameter(TYPE));
      history.setModifiedBy(context.getParameter(MODIFIED_BY));
      history.setEnabled(context.getParameterAsBoolean(ENABLED));
      history.setEnteredBy(context.getParameter(MODIFIED_BY));
      if (previousContactId != currentContactId) {
        history.setContactId(currentContactId);
        history.setReset(true);
      }
      if (history.getId() != -1) {
        history.update(db);
        result = true;
      } else {
        history.insert(db);
        result = true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      freeConnection(context, db);
    }
    return result;
  }
}

