package com.darkhorseventures.controller;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import com.darkhorseventures.utils.*;

public class ObjectHookList extends HashMap {

  public ObjectHookList() {}
  
  public void buildList(Connection db) throws SQLException {
    String hookData = null;
    PreparedStatement pst = db.prepareStatement(
      "SELECT data " +
      "FROM system_prefs " +
      "WHERE category = 'hooks' ");
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      hookData = rs.getString("data");
    }
    rs.close();
    pst.close();
    this.parse(hookData);
  }
  
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
        String enabled = (String)thisElement.getAttribute("enabled");
        if (enabled == null || !"false".equals(enabled)) {
          String hookId = (String) thisElement.getAttribute("id");
          String hookClass = (String) thisElement.getAttribute("class");
          if (System.getProperty("DEBUG") != null) {
            System.out.println("ObjectHookList-> Hook " + hookId + " executes " + hookClass);
          }
          this.put(hookId, hookClass);
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }
  
  public boolean has(Object object) throws java.lang.ClassNotFoundException {
    return (this.get(object.getClass().getName()) != null);
  }
  
  public boolean process(Object object, Connection db) {
    try {
      if (!this.has(object)) {
        return false;
      }
      String classHook = (String) this.get(object.getClass().getName());
      Object hook = ObjectUtils.constructObject(Class.forName(classHook), object, db);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

}
