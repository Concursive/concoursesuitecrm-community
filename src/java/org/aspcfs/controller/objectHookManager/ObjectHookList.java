package com.darkhorseventures.controller;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.Constants;
import java.io.*;

/**
 *  Manages a list of hooks that are available for the given system
 *
 *@author     matt rajkowski
 *@created    October 14, 2002
 *@version    $Id$
 */
public class ObjectHookList extends HashMap {

  /**
   *  Constructor for the ObjectHookList object
   */
  public ObjectHookList() { }
  
  public ObjectHookList(Connection db) throws SQLException {
    this.buildList(db);
  }
  
  /**
   *  Queries the database and retrieves the configured hooks from the
   *  system_prefs table
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT data " +
        "FROM system_prefs " +
        "WHERE category = ? " +
        "AND enabled = ? ");
    pst.setString(1, "system.objects.hooks");
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      this.parse(rs.getString("data"));
    }
    rs.close();
    pst.close();
  }

  public void buildListTest(File xmlFile)  {
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
      //Process all hooks and the corresponding actions
      Element hooks = XMLUtils.getFirstElement(xml.getDocumentElement(), "hooks");
      if (hooks != null) {
        ArrayList hookNodes = XMLUtils.getElements(hooks, "hook");
        Iterator hookElements = hookNodes.iterator();
        while (hookElements.hasNext()) {
          Element hookElement = (Element) hookElements.next();
          String hookClass = (String) hookElement.getAttribute("class");
          String hookEnabled = (String) hookElement.getAttribute("enabled");
          if (hookEnabled != null && "false".equals(hookEnabled)) {
            break;
          }
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ObjectHookList-> Added a hook: " + hookClass);
          }
          ObjectHookActionList actionList = new ObjectHookActionList(hookElement, Constants.TRUE);
          this.put(hookClass, actionList);
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

