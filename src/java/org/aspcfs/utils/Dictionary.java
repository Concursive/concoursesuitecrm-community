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
package org.aspcfs.utils;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletContext;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created February 3, 2005
 */
public class Dictionary {
  private String language = null;
  private String defaultLanguage = "en_US";  //default language
  private Map localizationPrefs = new LinkedHashMap();


  /**
   * Gets the defaultLanguage attribute of the Dictionary object
   *
   * @return The defaultLanguage value
   */
  public String getDefaultLanguage() {
    return defaultLanguage;
  }


  /**
   * Sets the defaultLanguage attribute of the Dictionary object
   *
   * @param tmp The new defaultLanguage value
   */
  public void setDefaultLanguage(String tmp) {
    this.defaultLanguage = tmp;
  }


  /**
   * Gets the language attribute of the Dictionary object
   *
   * @return The language value
   */
  public String getLanguage() {
    return language;
  }


  /**
   * Sets the language attribute of the Dictionary object
   *
   * @param tmp The new language value
   */
  public void setLanguage(String tmp) {
    this.language = tmp;
  }


  /**
   * Gets the localizationPrefs attribute of the Dictionary object
   *
   * @return The localizationPrefs value
   */
  public Map getLocalizationPrefs() {
    return localizationPrefs;
  }


  /**
   * Sets the localizationPrefs attribute of the Dictionary object
   *
   * @param tmp The new localizationPrefs value
   */
  public void setLocalizationPrefs(Map tmp) {
    this.localizationPrefs = tmp;
  }

  public String getTerm(String section, String parameter, String tagName) {
    Map prefGroup = (Map) localizationPrefs.get(section);
    if (prefGroup != null) {
      Node param = (Node) prefGroup.get(parameter);
      if (param != null) {
        return XMLUtils.getNodeText(
            XMLUtils.getFirstChild((Element) param, tagName));
      }
    }
    return null;
  }


  /**
   * Constructor for the Dictionary object
   */
  public Dictionary() {
  }


  public Dictionary(ServletContext context, String languageFilePath, String defaultLanguage) throws Exception {
    this.defaultLanguage = defaultLanguage;
    // Load the default language
    load(context, languageFilePath, defaultLanguage);
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @param languageFilePath Description of the Parameter
   * @param language         Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public void load(ServletContext context, String languageFilePath, String language) throws Exception {
    if (languageFilePath == null) {
      throw new Exception("Dictionary file path not provided");
    }
    if (!languageFilePath.endsWith("/") && !languageFilePath.endsWith(System.getProperty("file.separator"))) {
      languageFilePath += System.getProperty("file.separator");
    }
    String languageFilename = "dictionary_" + language + ".xml";
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "Dictionary-> Loading dictionary preferences: " + languageFilePath + languageFilename);
    }
    XMLUtils xml = new XMLUtils(context, languageFilePath + languageFilename);
    if (xml.getDocument() != null) {
      //Traverse the prefs and add the config nodes to the LinkedHashMap,
      //then for each config, add the param nodes into a child LinkedHashMap.
      //This will provide quick access to the values, and will allow an
      //editor to display the fields as ordered in the XML file
      NodeList configNodes = xml.getDocumentElement().getElementsByTagName(
          "config");
      for (int i = 0; i < configNodes.getLength(); i++) {
        Node configNode = configNodes.item(i);
        if (configNode != null &&
            configNode.getNodeType() == Node.ELEMENT_NODE &&
            "config".equals(((Element) configNode).getTagName()) &&
            (((Element) configNode).getAttribute("enabled") == null ||
            "".equals(((Element) configNode).getAttribute("enabled")) ||
            "true".equals(((Element) configNode).getAttribute("enabled")))) {
          //For each config name, create a map for each of the params
          String configName = ((Element) configNode).getAttribute("name");
          Map preferenceGroup = null;
          if (configName != null) {
            if (localizationPrefs.containsKey(configName)) {
              preferenceGroup = (LinkedHashMap) localizationPrefs.get(
                  configName);
            } else {
              preferenceGroup = new LinkedHashMap();
              localizationPrefs.put(configName, preferenceGroup);
            }
            //Process the params for this config
            NodeList paramNodes = ((Element) configNode).getElementsByTagName(
                "param");
            for (int j = 0; j < paramNodes.getLength(); j++) {
              Node paramNode = paramNodes.item(j);
              if (paramNode != null &&
                  paramNode.getNodeType() == Node.ELEMENT_NODE &&
                  "param".equals(((Element) paramNode).getTagName())) {
                String paramName = ((Element) paramNode).getAttribute("name");
                if (paramName != null) {
                  preferenceGroup.put(paramName, paramNode);
                }
              }
            }
          }
        }
      }
    }
  }
}

