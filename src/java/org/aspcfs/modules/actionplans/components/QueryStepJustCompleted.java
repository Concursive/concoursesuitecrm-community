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
package org.aspcfs.modules.actionplans.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.actionplans.base.ActionItemWork;

public class QueryStepJustCompleted extends ObjectHookComponent implements ComponentInterface {

  public String getDescription() {
    return "Is the step just completed?";
  }

  public boolean execute(ComponentContext context) {
    ActionItemWork thisActionItemWork = (ActionItemWork) context.getThisObject();
    ActionItemWork previousActionItemWork = (ActionItemWork) context.getPreviousObject();
    if (previousActionItemWork.getId() != thisActionItemWork.getId()) {
      return false;
    }
    return (thisActionItemWork.hasStatus() && !previousActionItemWork.hasStatus());
  }
}

