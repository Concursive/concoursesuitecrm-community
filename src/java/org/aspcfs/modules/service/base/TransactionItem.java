package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;
import java.sql.*;
import java.lang.reflect.*;
import com.darkhorseventures.cfsbase.*;

public class TransactionItem {

  private static final int INSERT = 1;
  private static final int SELECT = 2;
  private static final int UPDATE = 3;
  private static final int DELETE = 4;

  private Object object = null;
  private int action = -1;
  private StringBuffer errorMessage = new StringBuffer();
  
  public TransactionItem() {}
  
  public TransactionItem(Element objectElement, Hashtable mapping) {
    try {
      this.setAction(objectElement);
      this.setObject(objectElement, mapping);
      XMLUtils.populateObject(object, objectElement);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) { 
        System.out.println("TransactionItem-> Cannot create: " + objectElement.getTagName());
      }
      errorMessage.append("Invalid element: " + objectElement.getTagName() + System.getProperty("line.separator"));
    }
  }
  
  public void setObject(Object tmp) {
    object = tmp;
  }
  
  public void setObject(Element element, Hashtable mapping) throws Exception {
    String elementTag = element.getTagName();
    if (mapping.containsKey(elementTag)) {
      //Create the object
      object = Class.forName((String)mapping.get(elementTag)).newInstance();
      if (System.getProperty("DEBUG") != null) { 
        System.out.println("TransactionItem-> New: " + object.getClass().getName());
      }
    }
  }
  
  public void setAction(int tmp) {
    action = tmp;
  }
  
  public void setAction(String tmp) {
    if ("insert".equals(tmp)) {
      setAction(this.INSERT);
    } else if ("update".equals(tmp)) {
      setAction(this.UPDATE);
    } else if ("select".equals(tmp)) {
      setAction(this.SELECT);
    } else if ("delete".equals(tmp)) {
      setAction(this.DELETE);
    }
  }
  
  public void setAction(Element objectElement) {
    if (objectElement.hasAttributes()) {
      if (objectElement.getAttribute("type") != null) {
        this.setAction(objectElement.getAttribute("type"));
      } else {
        this.setAction(objectElement.getAttribute("action"));
      }
    }
  }
  
  public String getErrorMessage() {
    return (errorMessage.toString());
  }
  
  public boolean isValid() {
    if (object == null) {
      errorMessage.append("Invalid object" + System.getProperty("line.separator"));
    }
    
    if (action == -1) {
      errorMessage.append("Action not specified" + System.getProperty("line.separator"));
    }
    
    return (errorMessage.length() == 0);
  }

  public void execute(Connection db) throws Exception {
    String executeMethod = null;
    switch(action) {
      case INSERT:
        executeMethod = "insert";
        break;
      case UPDATE:
        executeMethod = "update";
        break;
      case DELETE:
        executeMethod = "delete";
        break;
      default:
        break;
    }
    
    if (executeMethod != null && object != null) {
        java.sql.Connection conn = db;
        //Class c = Class.forName("java.sql.Connection").newInstance();
        //Class[] argTypes = new Class[]{c.getClass()};
        System.out.println("Class: " + conn.getClass().getName());
        System.out.println("Object: " + object.getClass().getName());
        Method method = object.getClass().getMethod("insert", new Class[]{conn.getClass()});
        System.out.println("step2");        
        method.invoke(object, new Object[]{db});
        if (System.getProperty("DEBUG") != null) System.out.println("TransactionItem-> " + object.getClass().getName() + " " + executeMethod);
    } else {
      errorMessage.append("Action not specified" + System.getProperty("line.separator"));
    }
  }
  
  public boolean hasError() {
    return (errorMessage.length() > 0);
  }
  
}
