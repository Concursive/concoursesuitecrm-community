package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import java.util.*;
import com.darkhorseventures.apps.dataimport.*;
import com.darkhorseventures.utils.*;
import java.util.logging.*;

public class PropertyMapList extends HashMap {
  public static Logger logger = Logger.getLogger(DataImport.class.getName());
  
  public DataRecord createDataRecord(Object object, String action) {
    if (this.containsKey(object.getClass().getName())) {
      PropertyMap thisMap = (PropertyMap)this.get(object.getClass().getName());
      DataRecord record = new DataRecord();
      record.setName(thisMap.getId());
      record.setAction(action);
      
      Iterator properties = thisMap.iterator();
      while (properties.hasNext()) {
        Property thisProperty = (Property)properties.next();
        record.addField(thisProperty.getName(), ObjectUtils.getParam(object, thisProperty.getName()), thisProperty.getLookupValue(), thisProperty.getAlias());
      }
      return record;
    } else {
      logger.info("PropertyMapList-> Object mapping not found");
      return null;
    }
  }
  
  public boolean saveList(DataWriter writer, AbstractList list, String action) {
    boolean processOK = true;
    Iterator i = list.iterator();
    while (i.hasNext() && processOK) {
      Object object = i.next();
      DataRecord thisRecord = createDataRecord(object, action);
      processOK = writer.save(thisRecord);
    }
    return processOK;
  }
  
  public PropertyMap getMap(String mapId) {
    Iterator maps = this.keySet().iterator();
    while (maps.hasNext()) {
      PropertyMap thisMap = (PropertyMap)maps.next();
      if (mapId.equals(thisMap.getId())) {
        return thisMap;
      }
    }
    return null;
  }
}
