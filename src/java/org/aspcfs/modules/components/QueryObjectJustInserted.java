//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.cfs.component;

import com.darkhorseventures.controller.*;
import com.darkhorseventures.cfsbase.*;

public class QueryObjectJustInserted extends ObjectHookComponent implements ComponentInterface {
  
  public String getDescription() {
    return "Was the object just inserted?";
  }
  
  public boolean execute(ComponentContext context) {
    return ((Object)context.getThisObject() != null &&
            (Object)context.getPreviousObject() == null);
  }
}
