package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import java.util.*;
import org.aspcfs.apps.transfer.*;
import org.aspcfs.utils.*;
import java.util.logging.*;

/**
 *  Contains a list of PropertyMaps, used to translate objects into XML
 *
 *@author     matt rajkowski
 *@created    September 18, 2002
 *@version    $Id$
 */
public class PropertyMapList extends HashMap {
  public static Logger logger = Logger.getLogger(Transfer.class.getName());


  /**
   *  Description of the Method
   *
   *@param  object  Description of the Parameter
   *@param  action  Description of the Parameter
   *@return         Description of the Return Value
   */
  public DataRecord createDataRecord(Object object, String action) {
    if (this.containsKey(object.getClass().getName())) {
      PropertyMap thisMap = (PropertyMap) this.get(object.getClass().getName());
      DataRecord record = new DataRecord();
      record.setName(thisMap.getId());
      record.setAction(action);

      Iterator properties = thisMap.iterator();
      while (properties.hasNext()) {
        Property thisProperty = (Property) properties.next();
        if (thisProperty.hasValue()) {
          record.addField(thisProperty.getName(), thisProperty.getValue(), thisProperty.getLookupValue(), thisProperty.getAlias());
        } else {
          record.addField(thisProperty.getName(), ObjectUtils.getParam(object, thisProperty.getName()), thisProperty.getLookupValue(), thisProperty.getAlias());
        }
      }
      return record;
    } else {
      logger.info("PropertyMapList-> Object mapping not found");
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  writer  Description of the Parameter
   *@param  list    Description of the Parameter
   *@param  action  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean saveList(DataWriter writer, AbstractList list, String action) {
    logger.info("Class: " + list.getClass().getName() + " Record count: " + list.size());
    boolean processOK = true;
    Iterator i = list.iterator();
    while (i.hasNext() && processOK) {
      Object object = i.next();
      DataRecord thisRecord = createDataRecord(object, action);
      processOK = writer.save(thisRecord);
    }
    return processOK;
  }


  /**
   *  Gets the map attribute of the PropertyMapList object
   *
   *@param  mapId  Description of the Parameter
   *@return        The map value
   */
  public PropertyMap getMap(String mapId) {
    Iterator maps = this.keySet().iterator();
    while (maps.hasNext()) {
      String thisMapName = (String) maps.next();
      PropertyMap thisMap = (PropertyMap) this.get(thisMapName);
      if (mapId.equals(thisMap.getId())) {
        return thisMap;
      }
    }
    return null;
  }
}

