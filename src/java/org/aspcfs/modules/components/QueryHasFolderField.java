//Copyright 2002 Dark Horse Ventures

package org.aspcfs.modules.components;

import org.aspcfs.controller.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id$
 */
public class QueryHasFolderField extends ObjectHookComponent implements ComponentInterface {

  public final static String FIELD_ID = "customFieldCategory.fieldId";


  /**
   *  Gets the description attribute of the QueryHasFolderField object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Does this folder contain a specified field?";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    CustomFieldCategory thisCategory = (CustomFieldCategory) context.getThisObject();
    CustomFieldCategory previousCategory = (CustomFieldCategory) context.getPreviousObject();
    if (thisCategory != null) {
      return thisCategory.hasField(context.getParameterAsInt(QueryHasFolderField.FIELD_ID));
    } else if (previousCategory != null) {
      return previousCategory.hasField(context.getParameterAsInt(QueryHasFolderField.FIELD_ID));
    }
    return false;
  }
}

