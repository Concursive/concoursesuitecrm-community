package org.aspcfs.controller.objectHookManager;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.io.*;

/**
 *  Manages a list of hooks that are available for the given system
 *
 *@author     matt rajkowski
 *@created    October 14, 2002
 *@version    $Id: ObjectHookList.java,v 1.8 2003/01/13 21:41:16 mrajkowski Exp
 *      $
 */
public class ObjectHookList extends HashMap {

  /**
   *  Constructor for the ObjectHookList object
   */
  public ObjectHookList() { }


  /**
   *  Description of the Method
   *
   *@param  xmlFile  Description of the Parameter
   */
  public void buildList(File xmlFile) {
    try {
      BufferedReader in = new BufferedReader(new FileReader(xmlFile));
      StringBuffer config = new StringBuffer();
      String text = null;
      while ((text = in.readLine()) != null) {
        config.append(text);
      }
      this.parse(config.toString());
    } catch (Exception e) {
    }
  }


  /**
   *  Parses the XML hook configuration data into objects
   *
   *@param  hookData  Description of the Parameter
   *@return           Description of the Return Value
   */
  public boolean parse(String hookData) {
    if (hookData == null) {
      return false;
    }
    try {
      XMLUtils xml = new XMLUtils(hookData);
      return this.parse(xml.getDocumentElement());
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  element  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean parse(Element element) {
    if (element == null) {
      return false;
    }
    try {
      //Process all hooks and the corresponding actions
      Element hooks = XMLUtils.getFirstElement(element, "hooks");
      if (hooks != null) {
        ArrayList hookNodes = XMLUtils.getElements(hooks, "hook");
        Iterator hookElements = hookNodes.iterator();
        while (hookElements.hasNext()) {
          Element hookElement = (Element) hookElements.next();
          String hookClass = (String) hookElement.getAttribute("class");
          String hookEnabled = (String) hookElement.getAttribute("enabled");
          if (hookEnabled == null || "true".equals(hookEnabled)) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("ObjectHookList-> Added a hook: " + hookClass);
            }
            ObjectHookActionList actionList = new ObjectHookActionList(hookElement, Constants.TRUE);
            this.put(hookClass, actionList);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   *  Returns whether the given object has a defined hook
   *
   *@param  object                                Description of the Parameter
   *@return                                       Description of the Return
   *      Value
   *@exception  java.lang.ClassNotFoundException  Description of the Exception
   */
  public boolean has(Object object) throws java.lang.ClassNotFoundException {
    return (this.get(object.getClass().getName()) != null);
  }

}

