package com.darkhorseventures.controller;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import com.darkhorseventures.utils.*;

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


  /**
   *  Queries the database and retrieves the configured hooks from the
   *  system_prefs table
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    String hookData = null;
    PreparedStatement pst = db.prepareStatement(
        "SELECT data " +
        "FROM system_prefs " +
        "WHERE category = ? " +
        "AND enabled = ? ");
    pst.setString(1, "system.objects.hooks");
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      hookData = rs.getString("data");
    }
    rs.close();
    pst.close();
    this.parse(hookData);
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
      ArrayList hookElements = new ArrayList();
      xml.getAllChildren(xml.getDocumentElement(), "hook", hookElements);
      Iterator elements = hookElements.iterator();
      while (elements.hasNext()) {
        Element thisElement = (Element) elements.next();
        String enabled = (String) thisElement.getAttribute("enabled");
        if (enabled == null || !"false".equals(enabled)) {
          String hookId = (String) thisElement.getAttribute("id");
          String hookClass = (String) thisElement.getAttribute("class");
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ObjectHookList-> Hook " + hookId + " executes " + hookClass);
          }
          this.put(hookId, hookClass);
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ObjectHookList-> Hooks added: " + this.size());
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


  /**
   *  Executes the given hook as an insert
   *
   *@param  object     Description of the Parameter
   *@param  sqlDriver  Description of the Parameter
   *@param  ce         Description of the Parameter
   */
  public void processInsert(Object object, ConnectionPool sqlDriver, ConnectionElement ce) {
    try {
      if (this.has(object)) {
        String classHook = (String) this.get(object.getClass().getName());
        ObjectHook thisHook = new ObjectHook(sqlDriver, ce, classHook, object);
        thisHook.setMethod(ObjectHook.INSERT);
        thisHook.start();
      }
    } catch (ClassNotFoundException e) {
    }
  }


  /**
   *  Executes the given hook as an update
   *
   *@param  previousObject  Description of the Parameter
   *@param  object          Description of the Parameter
   *@param  sqlDriver       Description of the Parameter
   *@param  ce              Description of the Parameter
   */
  public void processUpdate(Object previousObject, Object object, ConnectionPool sqlDriver, ConnectionElement ce) {
    try {
      if (this.has(object)) {
        String classHook = (String) this.get(object.getClass().getName());
        ObjectHook thisHook = new ObjectHook(sqlDriver, ce, classHook, previousObject, object);
        thisHook.setMethod(ObjectHook.UPDATE);
        thisHook.start();
      }
    } catch (ClassNotFoundException e) {
    }
  }


  /**
   *  Executes the given hook as a delete
   *
   *@param  previousObject  Description of the Parameter
   *@param  sqlDriver       Description of the Parameter
   *@param  ce              Description of the Parameter
   */
  public void processDelete(Object previousObject, ConnectionPool sqlDriver, ConnectionElement ce) {
    try {
      if (this.has(previousObject)) {
        String classHook = (String) this.get(previousObject.getClass().getName());
        ObjectHook thisHook = new ObjectHook(sqlDriver, ce, classHook, previousObject, null);
        thisHook.setMethod(ObjectHook.DELETE);
        thisHook.start();
      }
    } catch (ClassNotFoundException e) {
    }
  }

}

