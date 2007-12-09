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
package org.aspcfs.modules.components;

import java.sql.Connection;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.accounts.base.OrganizationHistory;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: DeleteContactHistory.java 12404 2005-08-05 17:37:07Z mrajkowski $
 * @created May 27, 2005
 */
public class DeleteAccountHistory extends ObjectHookComponent implements ComponentInterface {
  public final static String ORG_ID = "history.orgId";
  public final static String LINK_OBJECT_ID = "history.linkObjectId";
  public final static String LINK_ITEM_ID = "history.linkItemId";
  public final static String PREVIOUS_ORG_ID = "history.previousOrgId";


  /**
   * Gets the description attribute of the DeleteContactHistory object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Delete the account's history entry";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    OrganizationHistory history = new OrganizationHistory();
    Connection db = null;
    try {
      db = getConnection(context);
      int currentOrgId = context.getParameterAsInt(ORG_ID);
      if (context.getParameter(PREVIOUS_ORG_ID) != null) {
        int previousContactId = context.getParameterAsInt(PREVIOUS_ORG_ID);
        if (previousContactId != currentOrgId) {
          history.setContactId(previousContactId);
        } else {
          history.setContactId(currentOrgId);
        }
      } else {
        history.setContactId(currentOrgId);
      }
      history.setLinkObjectId(context.getParameterAsInt(LINK_OBJECT_ID));
      history.setLinkItemId(context.getParameterAsInt(LINK_ITEM_ID));
      history.queryRecord(db);
      if (history.getId() != -1) {
        result = history.delete(db);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      freeConnection(context, db);
    }
    return result;
  }
}

