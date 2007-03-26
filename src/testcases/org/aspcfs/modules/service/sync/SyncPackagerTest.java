/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.service.sync;

import helpers.sqlHelper.SqlEntry;
import helpers.sqlHelper.SqlParser;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.apps.transfer.reader.cfsdatabasereader.Property;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMap;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import junit.framework.TestCase;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Feb 7, 2007
 *
 */
public class SyncPackagerTest extends TestCase {
  private SyncPackager syncPackager = new SyncPackager();
  private static final String mappingFile = "pref/cfs/transfer/import-mappings.xml"; 
  private static final String scriptsDir = "src/sql/postgresql/";

  public void testSyncTables(){
    ArrayList<String> syncTables = syncPackager.getSyncTables();

    PropertyMapList mappings = new PropertyMapList();;
    try{
      mappings.loadMap(mappingFile, new ArrayList());
    }catch(Exception e){
      fail(e.getMessage());
    }

    HashMap<String, String> importMap = getImportMap();
    boolean hasErrorsFinal = false;
    String outputFinal = "";
    for(String tableId : syncTables) {
      boolean hasErrors = false;
      String output = "";
      String realTableId = tableId;
      if(realTableId.endsWith("List")){
        realTableId = realTableId.substring(0, realTableId.indexOf("List"));
      }
      
      PropertyMap map = mappings.getMap(tableId.substring(0, tableId.indexOf("List")));
      output += "[" + tableId + "]\n";
      if(map == null){
        hasErrors = true;
        output += "  Error(1): Mapping not found!\n";
      }else{
        if(map.getTable() == null || map.getTable().length() < 1){
          hasErrors = true;
          output += "  Error(2): Table name not specify!\n";
        }
        if(map.getSequence() == null || map.getSequence().length() < 1){
          hasErrors = true;
          output += "  Error(SEQ1): Sequence not specify!\n";
        }
        if(map.getUniqueField() == null || map.getUniqueField().length() < 1){
          hasErrors = true;
          output += "  Error(4): UniqueField name not specify!\n";
        }
        
        
        SqlParser sqlParser = new SqlParser(scriptsDir);
        SqlEntry entry = sqlParser.getEntries(map.getTable());
        
        //Check sequences
        if(entry.getPrimSequence() != ""){
          if(!entry.getPrimSequence().equals(map.getSequence())){
            hasErrors = true;
            output += "  Error(SEQ2): Sequences are different: " + entry.getPrimSequence() + " & " + map.getSequence() +" \n";
          }
        }else{
          if(!map.getSequence().equals(entry.getTableName()+ "_" + entry.getUniqueField() + "_seq")){
            hasErrors = true;
            output += "  Error(SEQ3): Bad mapping's sequences : " + map.getSequence() + " ( Propose: \"" + entry.getTableName() + "_" + entry.getUniqueField() + "_seq\" ) \n";
          }
        }

        //Check entered/modified field in entry
        if(!hasSqlField(entry, "entered", SqlEntry.Types.TYPE_TIMESTAMP)){
          hasErrors = true;
          output += "  Error(5): Field 'entered' not specify in SQL!\n";
        }
        if(!hasSqlField(entry, "modified", SqlEntry.Types.TYPE_TIMESTAMP)){
          hasErrors = true;
          output += "  Error(6): Field 'modified' not specify in SQL!\n";
        }        
        if(map.size() == 0){
          //org.aspcfs.utils.web.LookupList
          if(!"code".equals(map.getUniqueField())){
            //TODO: Check in entry
            hasErrors = true;
            output += "  Error(7): Bad LookupList unique field!\n";
          }
          if(!(hasSqlField(entry, "code", SqlEntry.Types.TYPE_INT) &&  /*hasSqlField(entry, "order_id", Type.TYPE_INT) &&*/
              hasSqlField(entry, "description", SqlEntry.Types.TYPE_STR) && hasSqlField(entry, "default_item", SqlEntry.Types.TYPE_BOOL) &&
              hasSqlField(entry, "level", SqlEntry.Types.TYPE_INT) && hasSqlField(entry, "enabled", SqlEntry.Types.TYPE_BOOL))
              ){
            hasErrors = true;
            output += "  Error(8): Bad LookupList fields set!\n";
          }
        }else{
          //Custom List
          if(entry.getFields().size() != map.size()){
            System.out.println("Not all the fields are described in import-mapping.xml for " + tableId);
          }
          String refErrors = checkReferences(entry, syncTables, map, mappings);
          if(refErrors.length() > 0){
            hasErrors = true;
            output += refErrors;
          }
          
          if(!importMap.containsKey(realTableId)){
            hasErrors = true;
            output += "  Error(9): No class for " + tableId + "\n";
          }else{
            if(!"org.aspcfs.utils.web.CustomLookupList".equals(importMap.get(realTableId))){
              //TODO: Fix this classes
              String badClasses = new String("" +
                  //Not Empty default list
                  "|user|role|rolePermission|permission|account|" +
                  "|contact|organizationAddress|organizationPhoneNumber|organizationEmailAddress|contactHistory|"+
                  //No propertyFilter
                  "|contactEmailAddress|contactPhoneNumber|contactAddress|||||");
              if(badClasses.lastIndexOf("|" + realTableId + "|") == -1 &&
                !checkPrepareFilter(importMap.get(realTableId) + "List")){
                hasErrors = true;
                output += "  Error(10a): prepareFilter return unempty list or not defined :" + importMap.get(realTableId) + "List" + "\n";
              }
              
              if(!checkSetSyncType(importMap.get(realTableId) + "List")){
                hasErrors = true;
                output += "  Error(10b): setSyncType(String) not defined :" + importMap.get(realTableId) + "List" + "\n";
              }

              if(!checkGetObject(importMap.get(realTableId) + "List", importMap.get(realTableId))){
                hasErrors = true;
                output += "  Error(10c): getObject(ResulSet) not defined :" + importMap.get(realTableId) + "List" + "\n";
              }
            }

            Iterator propIt = map.iterator();
            while(propIt.hasNext()){
              Property prop = (Property) propIt.next();
              if (prop.getName() != null){
                //String cls = getMapClass(tableId);
                if(!checkGetterAndSetter(prop.getName(), importMap.get(realTableId))){
                  hasErrors = true;
                  output += "  Error(11): No method declared " + prop.getName() + "\n";
                }
              }
            }
          }
          
          //TODO: Check entry <-> map mapping
/*          for(Entry field : entry.getFields().entrySet()) {
            boolean found = false;
            Property prop = null;
            Iterator propIt = map.iterator();
            while(propIt.hasNext()){
              prop = (Property) propIt.next();
              if(prop.getField() == null || prop.getField().length() == 0){
                System.out.println(" No field attr for " + field.getKey());
              }
              if(prop.getField().equals(field.getKey())){
                found = true;
                break;
              }
            }
            if(found){
              if(SqlEntry.getType(prop.getType()) != field.getValue()){
                hasErrors = true;
                output += "  Error(5): Bad " + field.getKey() + " field type in import-mapping.xml!\n";
              }
            }else{
              hasErrors = true;
              output += "  Error(6): Field " + field.getKey() + " not found in import-mapping.xml!\n";
            }
          }
*/          
          //TODO: Check entry <-> Class geters & setters (getField(Fieldtype), setField(fieldType), setField(String))
          //TODO: Check class.getUniqueField
          //TODO: Check class.getTableName
        }
      }
      if(hasErrors){
        hasErrorsFinal = true;
        outputFinal += output;
      }
    }

    assertFalse(outputFinal, hasErrorsFinal);
  }
  
  private static boolean checkGetterAndSetter(String propertyName, String className){
    propertyName = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    try {
      Class c = Class.forName(className);
      Class[] csa = {String.class};
      c.getMethod("get" + propertyName);
      c.getMethod("set" + propertyName, csa);
      return true;
    } catch (Exception e) {
    }

    return false;
  }
  
  private static boolean checkPrepareFilter(String className){
    try {
      Class c = Class.forName(className);
      Method m = c.getDeclaredMethod("prepareFilter", new Class[] {PreparedStatement.class});
      m.setAccessible(true);
      int i = ((Integer) m.invoke(c.newInstance(), new Object[] {null})).intValue();
      return i == 0;
    } catch (Exception e) {
    }

    return false;
  }

  private static boolean checkGetObject(String className, String returnTypeName){
    try {
      Class c = Class.forName(className);
      Class r = Class.forName(returnTypeName);
      Method m = c.getDeclaredMethod("getObject", new Class[] {ResultSet.class});

      return m.getGenericReturnType().equals(r);
    } catch (Exception e) {}

    return false;
  }

  private static boolean checkSetSyncType(String className){
    try {
      Class c = Class.forName(className);
      c.getDeclaredMethod("setSyncType", new Class[] {String.class});

      return true;
    } catch (NoSuchMethodException e) {
      try{
        Class c = Class.forName(className).getSuperclass();
        c.getDeclaredMethod("setSyncType", new Class[] {String.class});

        return true;
      } catch (Exception ex) {}
    } catch (Exception e) {
    }

    return false;
  }

  private static boolean hasSqlField(SqlEntry sqlEntry, String fieldName, SqlEntry.Types fieldType){
    if(!sqlEntry.getFields().containsKey(fieldName)){
      return false;
    }
    if(fieldType != null){
      return (fieldType == sqlEntry.getFields().get(fieldName));
    }
    
    return true;
  }
  
  private static String checkReferences(SqlEntry sqlEntry, ArrayList syncTables, PropertyMap mappedProperty, PropertyMapList allMappings){
    int entryRefsCount = sqlEntry.getReferences().size();
    int mapRefsCouint = 0;
    String errors = "";
    
    Iterator propIt = mappedProperty.iterator();
    while(propIt.hasNext()){
      Property prop = (Property) propIt.next();
      if (prop.getLookupValue() != null){
        mapRefsCouint++;
        
        if(!syncTables.contains(prop.getLookupValue() + "List")){
          errors += "  Error(RF1): No reference(" + prop.getLookupValue() + "List) in SyncTables!\n";
        }
      }
    }
    if(entryRefsCount != mapRefsCouint){
      errors += "  Error(RF2): variouse count of references in sql(" + entryRefsCount + ") & import_mappings.xml(" + mapRefsCouint + ")!\n";
    }

    return errors;
  }
  
  private HashMap<String, String> getImportMap(){
    HashMap<String, String> map = new HashMap<String, String>();
    try{
      File configFile = new File(mappingFile);
      XMLUtils xml = new XMLUtils(configFile);
  
      ArrayList mapElements = new ArrayList();
      XMLUtils.getAllChildren(xml.getFirstChild("mappings"), "map", mapElements);
  
      Iterator mapItems = mapElements.iterator();
      while (mapItems.hasNext()) {
        Element mapping = (Element) mapItems.next();

        map.put(mapping.getAttribute("id"), mapping.getAttribute("class"));
      }
      
      return map;
    }catch (Exception e) {}    

    return null;
  }
}