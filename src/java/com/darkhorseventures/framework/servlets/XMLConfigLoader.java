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
package com.darkhorseventures.framework.servlets;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import com.darkhorseventures.framework.actions.*;
/**
 *  This class is used to load the Hashtable of ActionActions from an XML file.
 *  <p>
 *
 *  The {@link #load()} method should be called whenever the latest version of
 *  the Actions is needed. If the XML file has not been modified since last call
 *  to load(), the file will not be reparsed.</p>
 *
 *@author     Joe Walnes
 *@created    june 1, 2001
 *@version    $Id: XMLConfigLoader.java,v 1.2 2003/01/13 14:42:24 mrajkowski Exp
 *      $
 */
public class XMLConfigLoader
     implements java.io.Serializable {
  final static long serialVersionUID = 536435325324169646L;
  private Map actions;
  private File file;
  private long lastModified = -1;


  /**
   *  Creates a new empty config loader. The values for Actions and file must be
   *  set before a call can be made.
   */
  public XMLConfigLoader() { }


  /**
   *  Sets the actions attribute of the XMLConfigLoader object
   *
   *@param  actions  The new actions value
   */
  public void setActions(Map actions) {
    this.actions = actions;
  }


  /**
   *  Sets the file attribute of the XMLConfigLoader object
   *
   *@param  fileName  The new file value
   */
  public void setFile(String fileName) {
    this.file = new File(fileName);
  }


  /**
   *  Load the ActionActions from the XML. If the file has not been modified
   *  since last load(), file will not be reparsed. All exception stacktraces
   *  will be written to System.err.
   */
  public void load() {
    try {
//      if ( file.lastModified() > lastModified )
      //    {
      Document document = parseDocument();
      loadAllActions(actions, document);
      lastModified = file.lastModified();
//      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *  Parse XML in <code>file</code> and return Document.
   *
   *@return                                   Description of the Return Value
   *@exception  FactoryConfigurationError     Description of the Exception
   *@exception  ParserConfigurationException  Description of the Exception
   *@exception  SAXException                  Description of the Exception
   *@exception  IOException                   Description of the Exception
   */
  private Document parseDocument()
       throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(file);
    return document;
  }


  /**
   *  Load all ActionActions into Hashtable from Document
   *
   *@param  Actions   Description of the Parameter
   *@param  document  Description of the Parameter
   */
  private void loadAllActions(Map Actions, Document document) {
    NodeList actionTags = document.getElementsByTagName("action");
    for (int i = 0; i < actionTags.getLength(); i++) {
      Element actionTag = (Element) actionTags.item(i);
      Action a = loadAction(actionTag);
      actions.put(a.getActionName(), a);
    }
  }


  /**
   *  Return an ActionAction based on an 'action' tag.
   *
   *@param  e  Description of the Parameter
   *@return    Description of the Return Value
   */
  private Action loadAction(Element e) {
    String aName = e.getAttribute("name");
    String aClass = e.getAttribute("class");
    String bName = null;
    String bScope = null;
    String bClass = null;
    Object firstBean = null;
    Map forwardTable = new HashMap();
    Map beansTable = new HashMap();

    NodeList children = e.getChildNodes();
    int len = children.getLength();
    for (int i = 0, cntr = 0; i < len; i++) {
      if (children.item(i).getNodeType() != Element.ELEMENT_NODE) {
        continue;
      }
      Element child = (Element) children.item(i);
      String childName = child.getTagName();
      if (childName.equals("bean")) {
        bName = child.getAttribute("name");
        bScope = child.getAttribute("scope").toLowerCase();
        int beanScope = 2;
        if (bScope.indexOf("request") >= 0) {
          beanScope = 1;
        }
        if (bScope.indexOf("session") >= 0) {
          beanScope = 2;
        }
        if (bScope.indexOf("application") >= 0) {
          beanScope = 3;
        }
        bClass = child.getAttribute("class");
        Beans beans = null;
        if (cntr++ == 0) {
          beans = new Beans(bName, beanScope, bClass, true);
        } else {
          beans = new Beans(bName, beanScope, bClass, false);
        }

        beansTable.put(bName, beans);
      } else
          if (childName.equals("forward")) {
        //Resource r = new Resource( child.getAttribute("resource"), child.getAttribute("xsl") );
        Resource r = new Resource(child.getAttribute("resource"), child.getAttribute("xsl"), child.getAttribute("layout"));
        forwardTable.put(child.getAttribute("name"), r);
      }
    }

    Action action = new Action(aName, aClass);
    Iterator i = forwardTable.keySet().iterator();

    // add all the forwards to this action
    while (i.hasNext()) {
      String fKey = (String) i.next();
      action.addResource(fKey, (Resource) forwardTable.get(fKey));
    }

    // add the beans to this action
    i = beansTable.keySet().iterator();
    while (i.hasNext()) {
      String fKey = (String) i.next();
      action.addBean(fKey, (Beans) beansTable.get(fKey));
    }

    return action;
  }
}

