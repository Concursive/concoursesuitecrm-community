//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.components;

import org.aspcfs.controller.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;


/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class QueryObjectJustInserted extends ObjectHookComponent implements ComponentInterface {

  /**
   *  Gets the description attribute of the QueryObjectJustInserted object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Was the object just inserted?";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    return ((Object) context.getThisObject() != null &&
        (Object) context.getPreviousObject() == null);
  }
}

