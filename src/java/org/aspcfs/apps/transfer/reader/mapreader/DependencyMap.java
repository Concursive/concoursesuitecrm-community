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
package org.aspcfs.apps.transfer.reader.mapreader;

import java.util.*;
import java.io.*;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.DatabaseUtils;
import org.w3c.dom.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    May 7, 2004
 *@version    $id:exp$
 */
public class DependencyMap extends PropertyMap {
  private String dependencyId = null;
  private String displayName = null;
  private boolean hasGroups = false;
  //list of properties specific to this dependency
  private ArrayList requiredProperties = new ArrayList();
  //list of instances of the propertymap
  private ArrayList groupList = new ArrayList();


  /**
   *  Constructor for the DependencyMap object
   *
   *@param  n  Description of the Parameter
   */
  public DependencyMap(Node n) {
    //name
    String tmp = ((Element) n).getAttribute("id");
    if (tmp != null) {
      this.dependencyId = tmp;
    }

    NodeList nl = n.getChildNodes();
    for (int j = 0; j < nl.getLength(); j++) {
      Node p = nl.item(j);
      if (p.getNodeType() == Node.ELEMENT_NODE) {
        //allowed proeperties
        if (((Element) p).getTagName().equals("properties")) {
          tmp = XMLUtils.getNodeText((Element) p);
          if (tmp != null) {
            requiredProperties = StringUtils.toArrayList(tmp, ",");
          }
        }

        //groups
        if (((Element) p).getTagName().equals("enableGroups")) {
          tmp = XMLUtils.getNodeText((Element) p);
          if (tmp != null) {
            this.setHasGroups(tmp);
          }
        }

        //display Name
        if (((Element) p).getTagName().equals("displayName")) {
          tmp = XMLUtils.getNodeText((Element) p);
          if (tmp != null) {
            this.setDisplayName(tmp);
          }
        }
      }
    }
  }


  /**
   *  Sets the properties attribute of the DependencyMap object
   *
   *@param  tmp  The new properties value
   */
  public void setRequiredProperties(ArrayList tmp) {
    this.requiredProperties = tmp;
  }


  /**
   *  Sets the groupList attribute of the DependencyMap object
   *
   *@param  tmp  The new groupList value
   */
  public void setGroupList(ArrayList tmp) {
    this.groupList = tmp;
  }


  /**
   *  Gets the properties attribute of the DependencyMap object
   *
   *@return    The properties value
   */
  public ArrayList getRequiredProperties() {
    return requiredProperties;
  }


  /**
   *  Gets the groupList attribute of the DependencyMap object
   *
   *@return    The groupList value
   */
  public ArrayList getGroupList() {
    return groupList;
  }


  /**
   *  Sets the thisId attribute of the DependencyMap object
   *
   *@param  tmp  The new dName value
   */
  public void setDependencyId(String tmp) {
    this.dependencyId = tmp;
  }


  /**
   *  Sets the hasGroups attribute of the DependencyMap object
   *
   *@param  tmp  The new hasGroups value
   */
  public void setHasGroups(boolean tmp) {
    this.hasGroups = tmp;
  }


  /**
   *  Sets the hasGroups attribute of the DependencyMap object
   *
   *@param  tmp  The new hasGroups value
   */
  public void setHasGroups(String tmp) {
    this.hasGroups = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the hasGroups attribute of the DependencyMap object
   *
   *@return    The hasGroups value
   */
  public boolean getHasGroups() {
    return hasGroups;
  }


  /**
   *  Gets the dName attribute of the DependencyMap object
   *
   *@return    The dName value
   */
  public String getDependencyId() {
    return dependencyId;
  }


  /**
   *  Gets the group attribute of the DependencyMap object
   *
   *@param  groupId  Description of the Parameter
   *@return          The group value
   */
  public PropertyMap getGroup(int groupId) {
    return (PropertyMap) groupList.get(groupId);
  }


  /**
   *  Description of the Method
   *
   *@param  map  Description of the Parameter
   */
  public void add(PropertyMap map) {
    groupList.add(map);
  }


  /**
   *  Constructor for the loadMap object
   *
   *@param  map  Description of the Parameter
   */
  public void loadMap(Element map) {
    PropertyMap dependencyMap = new PropertyMap();
    dependencyMap.setDisplayName(map.getAttribute("displayName"));
    String mapId = map.getAttribute("id");
    dependencyMap.setId(mapId + "_1");
    if (System.getProperty("DEBUG") != null) {
      System.out.println("DependencyMap -> Loading Dependency Map " + map.getAttribute("id"));
    }
    //Process this map
    NodeList nl = map.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);

      //load properties
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals("properties")) {
        NodeList nl1 = n.getChildNodes();
        for (int j = 0; j < nl1.getLength(); j++) {
          Node p = nl1.item(j);
          //get property node
          if (p.getNodeType() == Node.ELEMENT_NODE && ((Element) p).getTagName().equals("property")) {
            String nodeText = XMLUtils.getNodeText((Element) p);
            if (nodeText != null && requiredProperties.contains(nodeText.trim())) {
              Property thisProperty = new Property(p);
              thisProperty.setUniqueName(mapId + "." + thisProperty.getName());
              if (thisProperty.isValid()) {
                dependencyMap.addProperty(thisProperty);
                if (System.getProperty("DEBUG") != null) {
                  System.out.println("DependencyMap -> Adding Property " + thisProperty.getName());
                }
              } else {
                if (System.getProperty("DEBUG") != null) {
                  System.out.println("Invalid Property : " + thisProperty.getName());
                }
              }
            }
          }
        }
      }
    }
    //add dependency to the list
    groupList.add(dependencyMap);
  }


  /**
   *  Gets the map attribute of the DependencyMap object
   *
   *@param  instanceId                      Description of the Parameter
   *@return                                 The map value
   *@exception  CloneNotSupportedException  Description of the Exception
   */
  public PropertyMap getMap(String instanceId) throws CloneNotSupportedException {

    PropertyMap map = null;
    Iterator i = groupList.iterator();
    while (i.hasNext()) {
      PropertyMap thisMap = (PropertyMap) i.next();
      if (thisMap.getId().equals(instanceId)) {
        map = thisMap;
        break;
      }
    }
    if (map == null) {
      PropertyMap thisMap = (PropertyMap) groupList.get(0);
      map = thisMap.duplicate(instanceId);
      groupList.add(map);
    }
    return map;
  }


  /**
   *  Description of the Method
   *
   *@param  field                           Description of the Parameter
   *@return                                 Description of the Return Value
   *@exception  CloneNotSupportedException  Description of the Exception
   */
  public Property mapProperty(String field) throws CloneNotSupportedException {
    Property mappedProperty = null;
    boolean found = false;
    PropertyMap dMap = (PropertyMap) groupList.get(0);
    Iterator props = dMap.getProperties().keySet().iterator();
    while (props.hasNext() && !found) {
      String propertyName = (String) props.next();
      Property thisProperty = (Property) dMap.getProperties().get(propertyName);
      if (thisProperty.hasAlias(field)) {
        int groupId = 1;
        if (thisProperty.getMappedColumn() < 0) {
          //get the actual instance map
          mappedProperty = thisProperty;
        } else {
          groupId = groupList.size() + 1;
          PropertyMap thisMap = getMap(dependencyId + "_" + groupId);
          mappedProperty = (Property) thisMap.getProperties().get(thisProperty.getName());
        }
        mappedProperty.setGroupId(groupId);
        found = true;
      }
    }
    return mappedProperty;
  }

}

