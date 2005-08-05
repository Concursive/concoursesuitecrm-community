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
package org.aspcfs.apps.lookuplists;

import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author kbhoopal
 * @version $Id$
 * @created February 18, 2005
 */
public class LookupLists {

  private HashMap globallyUniqueIds = null;


  /**
   * Constructor for the LookupLists object
   */
  public LookupLists() {
  }


  /**
   * Sets the lgloballyUniqueIds attribute of the LookupLists object
   *
   * @param tmp The new globallyUniqueIds value
   */
  public void setGloballyUniqueIds(HashMap tmp) {
    this.globallyUniqueIds = tmp;
  }


  /**
   * Gets the globallyUniqueIds attribute of the LookupLists object
   *
   * @return The globallyUniqueIds value
   */
  public HashMap getGloballyUniqueIds() {
    return globallyUniqueIds;
  }


  /**
   * Constructs a datastructure that data that needs to be inserted into the
   * lookup tables
   *
   * @param filePath                 Description of the Parameter
   * @param customLookupListHandlers Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public ArrayList buildLookupLists(String filePath, HashMap customLookupListHandlers) throws Exception {
    System.out.println("Reading from file:" + filePath);
    File configFile = new File(filePath);
    XMLUtils xml = new XMLUtils(configFile);
    ArrayList lookupTables = new ArrayList();

    //fetching data by table name
    XMLUtils.getAllChildren(xml.getDocumentElement(), "lookup", lookupTables);

    Iterator lookupTableIterator = lookupTables.iterator();
    ArrayList lookupListList = new ArrayList();
    globallyUniqueIds = new HashMap();
    System.out.println("Reading values for lookup lists");
    while (lookupTableIterator.hasNext()) {
      Element lookupTable = (Element) lookupTableIterator.next();
      String tableName = lookupTable.getAttribute("table");
      String key = lookupTable.getAttribute("key");
      System.out.println(tableName);
      boolean useLevelAsIs = false;
      HashMap lookupListObjects = new HashMap();
      ArrayList lookupElements = new ArrayList();
      ArrayList lookupColumnTypes = new ArrayList();

      //fetching attribute (column types)
      XMLUtils.getAllChildren(lookupTable, "attribute", lookupColumnTypes);
      Iterator lookupColumnTypeIterator = lookupColumnTypes.iterator();
      HashMap columnTypes = new HashMap();
      while (lookupColumnTypeIterator.hasNext()) {
        Element lookupColumn = (Element) lookupColumnTypeIterator.next();
        NamedNodeMap attributes = lookupColumn.getAttributes();
        String name = attributes.getNamedItem("name").getNodeValue();
        String type = attributes.getNamedItem("type").getNodeValue();
        columnTypes.put(name, type);
      }
      lookupListObjects.put("attributes", columnTypes);

      //fecthing each record for a table
      XMLUtils.getAllChildren(lookupTable, "record", lookupElements);
      Iterator lookupElementIterator = lookupElements.iterator();
      ArrayList rowElements = new ArrayList();
      while (lookupElementIterator.hasNext()) {
        Element lookupElement = (Element) lookupElementIterator.next();
        NamedNodeMap attributes = lookupElement.getAttributes();
        int numberOfAttributes = attributes.getLength();
        int count = 0;
        HashMap columnValues = new HashMap();

        /*
         *  fetching all attributes of the 'record' element,
         *  i.e., a columns and values of each row
         */
        while (count < numberOfAttributes) {
          Node attribute = attributes.item(count++);
          if ("level".equals(attribute.getNodeName())) {
            useLevelAsIs = true;
          }
          if ("LUID".equals(attribute.getNodeName())) {
            globallyUniqueIds.put(
                tableName + "." + attribute.getNodeValue(), " ");
          }
          columnValues.put(attribute.getNodeName(), attribute.getNodeValue());
        }

        /*
         *  fetching all text node of the 'record' element,
         *  i.e, the corresponds to the 'description' column in the lookup tables
         */
        if (customLookupListHandlers.containsKey(tableName)) {
          // Use custom handler if the lookup list needs a custom handler
          buildMultipleDescriptions(columnValues, lookupElement);
        } else {
          columnValues.put("description", XMLUtils.getNodeText(lookupElement));
        }
        rowElements.add(columnValues);
      }

      lookupListObjects.put("rows", rowElements);
      lookupListObjects.put("tableName", tableName);
      lookupListObjects.put("key", key);
      lookupListObjects.put("useLevelAsIs", new Boolean(useLevelAsIs));

      lookupListList.add(lookupListObjects);
    }
    return lookupListList;
  }


  /**
   * Custom handler for table lookup_l4
   *
   * @param columnValues  Description of the Parameter
   * @param lookupElement Description of the Parameter
   */
  public void buildMultipleDescriptions(HashMap columnValues, Element lookupElement) {
    ArrayList items = new ArrayList();
    XMLUtils.getAllChildren(lookupElement, items);
    Iterator itemIterator = items.iterator();
    while (itemIterator.hasNext()) {
      Node item = (Node) itemIterator.next();
      columnValues.put(item.getNodeName(), XMLUtils.getNodeText(item));
    }
  }
}

