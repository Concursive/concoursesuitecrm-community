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

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import org.aspcfs.controller.ApplicationPrefs;

/**
 *  Useful methods that applications can use
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public class AppUtils {

  /**
   *  Parses either an XML preferences file or a key-value preferences file
   *
   *@param  filename  Description of the Parameter
   *@param  config    Description of the Parameter
   *@return           Description of the Return Value
   */
  public static boolean loadConfig(String filename, HashMap config) {
    if (filename.endsWith(".xml")) {
      File file = new File(filename);
      if (file == null) {
        System.err.println("AppUtils-> Configuration file not found: " + filename);
        return false;
      }
      try {
        Document document = parseDocument(file);
        config.clear();
        NodeList tags = document.getElementsByTagName("init-param");
        for (int i = 0; i < tags.getLength(); i++) {
          Element tag = (Element) tags.item(i);
          NodeList params = tag.getChildNodes();
          String name = null;
          String value = null;
          for (int j = 0; j < params.getLength(); j++) {
            Node param = (Node) params.item(j);
            if (param.hasChildNodes()) {
              NodeList children = param.getChildNodes();
              Node thisNode = (Node) children.item(0);
              if (param.getNodeName().equals("param-name")) {
                name = thisNode.getNodeValue();
              }
              if (param.getNodeName().equals("param-value")) {
                value = thisNode.getNodeValue();
              }
            }
          }
          if (value == null) {
            value = "";
          }
          config.put(name, value);
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
      return true;
    } else {
      ApplicationPrefs prefs = new ApplicationPrefs();
      prefs.load(filename + "build.properties");
      if (!prefs.has("FILELIBRARY")) {
        prefs.add("FILELIBRARY", filename);
      }
      config.putAll(prefs.getPrefs());
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  file                              Description of the Parameter
   *@return                                   Description of the Return Value
   *@exception  FactoryConfigurationError     Description of the Exception
   *@exception  ParserConfigurationException  Description of the Exception
   *@exception  SAXException                  Description of the Exception
   *@exception  IOException                   Description of the Exception
   */
  private static Document parseDocument(File file)
       throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(file);
    return document;
  }

}

