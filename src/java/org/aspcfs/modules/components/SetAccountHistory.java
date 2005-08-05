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
import org.aspcfs.modules.accounts.base.OrganizationHistory;

import java.sql.Connection;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 27, 2005
 */
public class SetAccountHistory extends ObjectHookComponent implements ComponentInterface {
  public final static String LEVEL = "history.level";
  public final static String ORG_ID = "history.orgId";
  public final static String LINK_OBJECT_ID = "history.linkObjectId";
  public final static String LINK_ITEM_ID = "history.linkItemId";
  public final static String DESCRIPTION = "history.description";
  public final static String ENABLED = "history.enabled";
  public final static String STATUS = "history.status";
  public final static String TYPE = "history.type";
  public final static String ENTERED_BY = "history.enteredby";
  public final static String MODIFIED_BY = "history.modifiedby";


  /**
   * Gets the description attribute of the SetAccountHistory object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Set the account's history";
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
      history.setLevel(context.getParameterAsInt(LEVEL));
      history.setOrgId(context.getParameterAsInt(ORG_ID));
      history.setLinkObjectId(context.getParameterAsInt(LINK_OBJECT_ID));
      history.setLinkItemId(context.getParameterAsInt(LINK_ITEM_ID));
      history.setDescription(context.getParameter(DESCRIPTION));
      history.setStatus(context.getParameter(STATUS));
      history.setType(context.getParameter(TYPE));
      history.setEnabled(context.getParameter(ENABLED));
      history.setEnteredBy(context.getParameter(ENTERED_BY));
      history.setModifiedBy(context.getParameter(MODIFIED_BY));
      result = history.insert(db);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      freeConnection(context, db);
    }
    return result;
  }
}

