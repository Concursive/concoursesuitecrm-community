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

import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.*;

/**
 * Contains a list of Property objects and defines an object Mapping
 *
 * @author Mathur
 * @version $id:exp$
 * @created April 5, 2004
 */
public class PropertyMap {
  private String id = null;
  private String displayName = null;
  private LinkedHashMap properties = new LinkedHashMap();
  private HashMap dependencies = null;


  /**
   * Constructor for the PropertyMap object
   */
  public PropertyMap() {
  }


  /**
   * Constructor for the PropertyMap object
   *
   * @param mapFile Description of the Parameter
   * @param mapName Description of the Parameter
   */
  public PropertyMap(String mapFile, String mapName) {
    loadMap(mapFile, mapName);
  }

  /**
   * Constructor for the PropertyMap object
   *
   * @param context Description of the Parameter
   * @param mapFile Description of the Parameter
   * @param mapName Description of the Parameter
   */
  public PropertyMap(ServletContext context, String mapFile, String mapName) {
    loadMap(context, mapFile, mapName);
  }


  /**
   * Sets the displayName attribute of the PropertyMap object
   *
   * @param tmp The new displayName value
   */
  public void setDisplayName(String tmp) {
    this.displayName = tmp;
  }


  /**
   * Gets the displayName attribute of the PropertyMap object
   *
   * @return The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }


  /**
   * Sets the id attribute of the PropertyMap object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   * Sets the properties attribute of the PropertyMap object
   *
   * @param tmp The new properties value
   */
  public void setProperties(LinkedHashMap tmp) {
    this.properties = tmp;
  }


  /**
   * Sets the dependencies attribute of the PropertyMap object
   *
   * @param tmp The new dependencies value
   */
  public void setDependencies(HashMap tmp) {
    this.dependencies = tmp;
  }


  /**
   * Gets the properties attribute of the PropertyMap object
   *
   * @return The properties value
   */
  public HashMap getProperties() {
    return properties;
  }


  /**
   * Gets the dependencies attribute of the PropertyMap object
   *
   * @return The dependencies value
   */
  public HashMap getDependencies() {
    return dependencies;
  }


  /**
   * Gets the id attribute of the PropertyMap object
   *
   * @return The id value
   */
  public String getId() {
    return id;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasProperties() {
    return (properties.size() > 0);
  }


  /**
   * Adds a property if it doesn't already exist
   *
   * @param thisProperty The feature to be added to the Property attribute
   * @return Description of the Return Value
   */
  public boolean addProperty(Property thisProperty) {
    if (!properties.containsKey(thisProperty.getName())) {
      properties.put(thisProperty.getName(), thisProperty);
    } else {
      return false;
    }
    return true;
  }


  /**
   * Loads the map with the given name and all its dependencies
   *
   * @param mapFile Description of the Parameter
   * @param mapName Description of the Parameter
   */
  public void loadMap(String mapFile, String mapName) {
    try {
      File configFile = new File(mapFile);
      XMLUtils xml = new XMLUtils(configFile);
      readProperties(xml, mapName);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("PropertyMap -> loadMap EXCEPTION " + e);
      }
    }
  }

  public void loadMap(ServletContext context, String mapFile, String mapName) {
    try {
      XMLUtils xml = new XMLUtils(context, mapFile);
      readProperties(xml, mapName);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("PropertyMap -> loadMap EXCEPTION " + e);
      }
    }
  }

  public void readProperties(XMLUtils xml, String mapName) {
    try {
      ArrayList mapElements = new ArrayList();

      XMLUtils.getAllChildren(
          xml.getFirstChild("mappings"), "map", mapElements);
      Iterator mapItems = mapElements.iterator();

      //load the head map
      while (mapItems.hasNext()) {
        //Get the map node
        Element map = (Element) mapItems.next();
        if (map.getAttribute("id").equals(mapName)) {
          this.setId((String) map.getAttribute("id"));
          this.setDisplayName((String) map.getAttribute("displayName"));
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "PropertyMap -> Adding Map " + map.getAttribute("id"));
          }
          //Process this map
          NodeList nl = map.getChildNodes();
          for (int i = 0; i < nl.getLength(); i++) {
            Node n = nl.item(i);

            //load properties
            if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(
                "properties")) {
              NodeList nl1 = n.getChildNodes();
              for (int j = 0; j < nl1.getLength(); j++) {
                Node p = nl1.item(j);

                //add property
                if (p.getNodeType() == Node.ELEMENT_NODE && ((Element) p).getTagName().equals(
                    "property")) {
                  Property thisProperty = new Property(p);
                  //use [mapName + "." + propertyName] to uniquely identify a property
                  thisProperty.setUniqueName(
                      mapName + "." + thisProperty.getName());
                  if (thisProperty.isValid()) {
                    this.addProperty(thisProperty);
                  }
                }
              }
            }

            //get the dependencies
            if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(
                "dependencies")) {
              dependencies = new HashMap();
              NodeList nl1 = n.getChildNodes();
              for (int j = 0; j < nl1.getLength(); j++) {
                Node d = nl1.item(j);
                if (d.getNodeType() == Node.ELEMENT_NODE && ((Element) d).getTagName().equals(
                    "dependency")) {
                  //add dependency
                  DependencyMap dMap = new DependencyMap(d);
                  if (System.getProperty("DEBUG") != null) {
                    System.out.println(
                        "PropertyMap -> Adding Dependency " + dMap.getDependencyId());
                  }
                  dependencies.put(dMap.getDependencyId(), dMap);
                }
              }
            }
          }
        }
      }

      //load dependencies
      if (dependencies != null && dependencies.size() > 0) {
        mapItems = mapElements.iterator();
        while (mapItems.hasNext()) {
          Element map = (Element) mapItems.next();
          String mapId = map.getAttribute("id");
          if (dependencies.containsKey(mapId)) {
            //load map
            DependencyMap dMap = (DependencyMap) dependencies.get(mapId);
            dMap.loadMap(map);
          }
        }
      }
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("PropertyMap -> EXCEPTION " + e);
      }
    }
  }


  /**
   * Gets the property attribute of the PropertyMap object
   *
   * @param propertyName Description of the Parameter
   * @return The property value
   */
  public Property getProperty(String propertyName) {
    Property p = null;
    if (properties != null && properties.containsKey(propertyName)) {
      p = (Property) properties.get(propertyName);
    }
    return p;
  }
  
  
  /**
   * Gets a dependency from this map
   *
   * @param mapId Description of the Parameter
   * @return The dependencyMap value
   */
  public ArrayList getDependencyMapList(String mapId) {
    ArrayList mapList = null;
    if (dependencies != null && dependencies.containsKey(mapId)) {
      DependencyMap dMap = (DependencyMap) dependencies.get(mapId);
      mapList = dMap.getGroupList();
    }
    return mapList;
  }


  /**
   * Checks to see if the dependency specified has grouping enabled
   *
   * @param dependencyId Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean hasGroups(String dependencyId) {
    if ("".equals(StringUtils.toString(dependencyId))) {
      return false;
    }
    if (dependencies != null && dependencies.containsKey(dependencyId)) {
      DependencyMap dMap = (DependencyMap) dependencies.get(dependencyId);
      if (dMap.getHasGroups()) {
        return true;
      }
    }
    return false;
  }


  /**
   * Tries to map the field to a property based on the alias speic
   *
   * @param field    Description of the Parameter
   * @param position Description of the Parameter
   * @return Description of the Return Value
   * @throws CloneNotSupportedException Description of the Exception
   */
  public Property mapProperty(String field, int position) throws CloneNotSupportedException {
    Property mappedProperty = null;
    boolean found = false;
    Iterator props = properties.keySet().iterator();
    while (props.hasNext() && !found) {
      String propertyName = (String) props.next();
      Property thisProperty = (Property) properties.get(propertyName);
      if (thisProperty.hasAlias(field)) {
        thisProperty.setMappedColumn(position);
        mappedProperty = thisProperty;
        found = true;
      }
    }

    //check dependencies
    if (dependencies != null && !found) {
      Iterator d = dependencies.keySet().iterator();
      while (d.hasNext() && !found) {
        String dKey = (String) d.next();

        DependencyMap thisMap = (DependencyMap) dependencies.get(dKey);
        mappedProperty = thisMap.mapProperty(field);
        if (mappedProperty != null) {
          mappedProperty.setMappedColumn(position);
          found = true;
          break;
        }
      }
    }
    return mappedProperty;
  }


  /**
   *  Retrieves an instance of the dependency map from the list specified
   *
   *@param  field                           Description of the Parameter
   *@param  uniquePropertyName              Description of the Parameter
   *@param  groupId                         Description of the Parameter
   *@return                                 The dependencyMap value
   *@exception  CloneNotSupportedException  Description of the Exception
   */
  /*
   *  public PropertyMap getDependencyMap(ArrayList instanceList, String instanceId) throws CloneNotSupportedException {
   *  PropertyMap map = null;
   *  Iterator i = instanceList.iterator();
   *  while (i.hasNext()) {
   *  PropertyMap thisMap = (PropertyMap) i.next();
   *  if (thisMap.getId().equals(instanceId)) {
   *  map = thisMap;
   *  break;
   *  }
   *  }
   *  if (map == null) {
   *  PropertyMap thisMap = (PropertyMap) instanceList.get(0);
   *  map = thisMap.duplicate(instanceId);
   *  instanceList.add(map);
   *  }
   *  return map;
   *  }
   */
  /**
   * Gets the property attribute of the PropertyMap object
   *
   * @param field              Description of the Parameter
   * @param uniquePropertyName Description of the Parameter
   * @param groupId            Description of the Parameter
   * @return The property value
   * @throws CloneNotSupportedException Description of the Exception
   */
  public Property getProperty(String field, String uniquePropertyName, String groupId) throws CloneNotSupportedException {
    if ("".equals(StringUtils.toString(groupId))) {
      return getProperty(field, uniquePropertyName, -1);
    }
    return getProperty(field, uniquePropertyName, Integer.parseInt(groupId));
  }


  /**
   * Retrieves the property from the map based on the name <br>
   * field parameter is used if it is a dependency property
   *
   * @param field              Description of the Parameter
   * @param uniquePropertyName Description of the Parameter
   * @param groupId            Description of the Parameter
   * @return The property value
   * @throws CloneNotSupportedException Description of the Exception
   */
  public Property getProperty(String field, String uniquePropertyName, int groupId) throws CloneNotSupportedException {
    Property mappedProperty = null;
    String mapId = null;
    String propertyName = null;
    StringTokenizer tokens = new StringTokenizer(uniquePropertyName, ".");
    if (tokens.hasMoreTokens()) {
      mapId = tokens.nextToken();
      propertyName = tokens.nextToken();
    }

    if (this.getId().equals(mapId)) {
      if (properties.containsKey(propertyName)) {
        mappedProperty = (Property) properties.get(propertyName);
      }
    } else {
      //dependencies
      DependencyMap dMap = (DependencyMap) dependencies.get(mapId);
      //String s = getInstanceToken(field);
      if (groupId == -1) {
        groupId = dMap.getGroupList().size() + 1;
      }
      PropertyMap thisMap = dMap.getMap(mapId + "_" + groupId);
      mappedProperty = thisMap.getProperty(propertyName);
      mappedProperty.setGroupId(groupId);
    }
    return mappedProperty;
  }


  /**
   * Prepares a list of required properties in the header map only
   *
   * @return The requiredProperties value
   */
  public ArrayList getRequiredProperties() {
    ArrayList requiredList = new ArrayList();
    Iterator p = properties.keySet().iterator();
    while (p.hasNext()) {
      String propertyName = (String) p.next();
      Property thisProperty = (Property) properties.get(propertyName);
      if (thisProperty.getRequired()) {
        requiredList.add(thisProperty);
      }
    }
    return requiredList;
  }


  /**
   * Retrieves the dependency map for the specified id
   *
   * @param mapId Description of the Parameter
   * @return The dependencyMap value
   */
  public DependencyMap getDependencyMap(String mapId) {
    DependencyMap thisMap = null;
    if (dependencies != null && dependencies.containsKey(mapId)) {
      thisMap = (DependencyMap) dependencies.get(mapId);
    }
    return thisMap;
  }


  /**
   * Clones the map
   *
   * @param instanceId Description of the Parameter
   * @return Description of the Return Value
   * @throws CloneNotSupportedException Description of the Exception
   */
  public PropertyMap duplicate(String instanceId) throws CloneNotSupportedException {
    PropertyMap duplicateMap = new PropertyMap();
    duplicateMap.setId(instanceId);
    Iterator props = properties.keySet().iterator();
    while (props.hasNext()) {
      String propertyName = (String) props.next();
      Property thisProperty = (Property) properties.get(propertyName);
      Property clonedProperty = (Property) thisProperty.duplicate();
      clonedProperty.setMappedColumn(-1);
      duplicateMap.getProperties().put(
          clonedProperty.getName(), clonedProperty);
    }
    return duplicateMap;
  }


  /**
   * Returns a HTML Select drop down list of properties
   *
   * @param field   Description of the Parameter
   * @param mapping Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String field, Property mapping) {
    String defaultKey = "-1";
    if (mapping != null) {
      defaultKey = mapping.getUniqueName();
    }
    HtmlSelect htmlSelect = new HtmlSelect();
    htmlSelect.setSelectName(field);
    htmlSelect.addAttribute(
        "onChange", "javascript:checkPrompt('" + field + "');");
    htmlSelect.addAttribute("id", field);
    htmlSelect.addItem("-1", " ");

    //add head properties
    htmlSelect.addGroup(this.getDisplayName());
    Iterator props = properties.keySet().iterator();
    while (props.hasNext()) {
      String propertyName = (String) props.next();
      Property thisProperty = (Property) properties.get(propertyName);
      if (thisProperty.getIsForPrompting() && !"".equals(
          StringUtils.toString(thisProperty.getDisplayName()))) {
        htmlSelect.addItem(
            thisProperty.getUniqueName(), thisProperty.getDisplayName());
      }
    }

    //add properties of all dependencies TODO: categorize the select entries
    if (dependencies != null) {
      Iterator d = dependencies.keySet().iterator();
      while (d.hasNext()) {
        String dKey = (String) d.next();
        DependencyMap thisMap = (DependencyMap) dependencies.get(dKey);
        ArrayList thisList = (ArrayList) thisMap.getGroupList();
        PropertyMap dMap = (PropertyMap) thisList.get(0);
        htmlSelect.addGroup(dMap.getDisplayName());
        props = dMap.getProperties().keySet().iterator();
        while (props.hasNext()) {
          String propertyName = (String) props.next();
          Property thisProperty = (Property) dMap.getProperties().get(
              propertyName);
          if (thisProperty.getIsForPrompting() && !"".equals(
              StringUtils.toString(thisProperty.getDisplayName()))) {
            htmlSelect.addItem(
                thisProperty.getUniqueName(), thisProperty.getDisplayName());
          }
        }
      }
    }
    return htmlSelect.getHtml(field, defaultKey);
  }


  /**
   * Gets the htmlSelect attribute of the PropertyMap object
   *
   * @param defaultKey Description of the Parameter
   * @param selectName Description of the Parameter
   * @param groupType  Description of the Parameter
   * @return The htmlSelect value
   */
  public String getGroupHtmlSelect(String selectName, int defaultKey, String groupType) {
    HtmlSelect htmlSelect = new HtmlSelect();
    htmlSelect.setSelectName(selectName);
    htmlSelect.addAttribute("id", selectName);

    //allow 10 group id choices
    for (int i = 1; i < 11; i++) {
      htmlSelect.addItem(
          String.valueOf(i), (groupType != null ? groupType : "Group") + i);
    }
    return htmlSelect.getHtml(selectName, defaultKey);
  }
}

