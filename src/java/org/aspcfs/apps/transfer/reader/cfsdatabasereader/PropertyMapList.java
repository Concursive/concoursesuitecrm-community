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
      record.setName(thisMap.getId());
      record.setAction(action);
      
      Iterator properties = thisMap.iterator();
      while (properties.hasNext()) {
        Property thisProperty = (Property)properties.next();
        
        String propertyName = thisProperty.getName();
        if (thisProperty.hasAlias()) {
          propertyName = thisProperty.getAlias();
        }
        
        if (thisProperty.hasLookupValue()) {
          record.addField(propertyName, ObjectUtils.getParam(object, thisProperty.getName()), thisProperty.getLookupValue());
        } else {
          record.addField(propertyName, ObjectUtils.getParam(object, thisProperty.getName()));
        }
        
      }
      return record;
    } else {
      return null;
    }
  }
}
