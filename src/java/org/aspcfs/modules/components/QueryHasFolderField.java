//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.folders.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;

public class QueryHasFolderField extends ObjectHookComponent implements ComponentInterface {
  
  public static final String FIELD_ID = "customFieldCategory.fieldId";
  
  public String getDescription() {
    return "Does this folder field have the specified field?";
  }
  
  public boolean execute(ComponentContext context) {
    CustomFieldCategory thisCategory = (CustomFieldCategory)context.getThisObject();
    CustomFieldCategory previousCategory = (CustomFieldCategory)context.getPreviousObject();
    if (thisCategory != null) {
      return thisCategory.hasField(context.getParameterAsInt(QueryHasFolderField.FIELD_ID));
    } else if (previousCategory != null) {
      return previousCategory.hasField(context.getParameterAsInt(QueryHasFolderField.FIELD_ID));
    }
    return false;
  }
}
