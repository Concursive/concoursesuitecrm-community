package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.util.HashMap;
import java.util.Iterator;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.utils.*;

public class PropertyMapList extends HashMap {
  public DataRecord createDataRecord(Object object, String action) {
    if (this.containsKey(object.getClass().getName())) {
      PropertyMap thisMap = (PropertyMap)this.get(object.getClass().getName());
      DataRecord record = new DataRecord();
      record.setAction(action);
      
      Iterator properties = thisMap.iterator();
      while (properties.hasNext()) {
        Property thisProperty = (Property)properties.next();
        if (thisProperty.hasLookupValue()) {
          record.addField(thisProperty.getName(), ObjectUtils.getParam(object, thisProperty.getName()), thisProperty.getLookupValue());
        } else {
          record.addField(thisProperty.getName(), ObjectUtils.getParam(object, thisProperty.getName()));
        }
        
      }
      return record;
    } else {
      return null;
    }
  }
}
