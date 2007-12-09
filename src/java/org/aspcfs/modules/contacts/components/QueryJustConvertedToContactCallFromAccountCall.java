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
package org.aspcfs.modules.contacts.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.contacts.base.Call;

/**
 * Description of the Class
 * 
 * @author Olga.Kaptyug
 * @created Sep 15, 2006
 */
public class QueryJustConvertedToContactCallFromAccountCall extends
    ObjectHookComponent implements ComponentInterface {

  /**
   * Gets the description attribute of the
   * QueryJustConvertedToContactCallFromAccountCall object
   * 
   * @return The description value
   */
  public String getDescription() {
    return "Determine if the current call object is associated with a contact and the previous call object is not,";
  }

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    Call thisCall = (Call) context.getThisObject();
    Call previousCall = (Call) context.getPreviousObject();
    return ((previousCall.getOrgId() != -1 && previousCall.getContactId() == -1 && previousCall
        .getFollowupContactId() == -1) && (thisCall.getContactId() != -1 || thisCall
        .getFollowupContactId() != -1));
  }
}
