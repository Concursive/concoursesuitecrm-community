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

import org.aspcfs.controller.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.controller.objectHookManager.*;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 14, 2003
 *@version    $Id: QueryHasFolderField.java,v 1.3 2003/04/14 02:42:22 mrajkowski
 *      Exp $
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

