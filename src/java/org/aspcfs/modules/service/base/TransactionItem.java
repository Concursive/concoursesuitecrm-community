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

  private String name = null;
  private Object object = null;
  private int action = -1;
  private StringBuffer errorMessage = new StringBuffer();
  private RecordList recordList = null;
  private ArrayList fields = null;
  
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
      appendErrorMessage("Invalid element: " + objectElement.getTagName());
    }
  }
  
  public void setObject(Object tmp) {
    object = tmp;
  }
  
  //Create the object
  public void setObject(Element element, Hashtable mapping) throws Exception {
    name = element.getTagName();
    if (mapping.containsKey(name)) {
      object = Class.forName((String)mapping.get(name)).newInstance();
      if (System.getProperty("DEBUG") != null) { 
        System.out.println("TransactionItem-> New: " + object.getClass().getName());
      }
    } else {
      appendErrorMessage("Unsupported object specified");
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
      String thisAction = objectElement.getAttribute("type");
      if (thisAction == null || thisAction.trim().equals("")) {
        thisAction = objectElement.getAttribute("action");
      }
      this.setAction(thisAction);
    }
  }
  
  public void setFields(ArrayList tmp) { this.fields = tmp; }

  
  public String getErrorMessage() {
    return (errorMessage.toString());
  }
  
  public String getName() {
    return name;
  }
  
  public RecordList getRecordList() {
    return recordList;
  }
  
  public ArrayList getFields() { return fields; }

  
  public void execute(Connection db) throws Exception {
    String executeMethod = null;
    switch(action) {
      case -1:
        appendErrorMessage("Action not specified");
        break;
      case INSERT:
        executeMethod = "insert";
        break;
      case UPDATE:
        executeMethod = "update";
        break;
      case DELETE:
        executeMethod = "delete";
        break;
      case SELECT:
        executeMethod = "select";
        break;  
      default:
        appendErrorMessage("Unsupported action specified");
        break;
    }
    
    Object result = null;
    if (executeMethod != null && object != null) {
      Method method = object.getClass().getDeclaredMethod(executeMethod.trim(), new Class[]{Class.forName("java.sql.Connection")});
      result = method.invoke(object, new Object[]{db});
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TransactionItem-> " + object.getClass().getName() + " " + executeMethod);
      }
    }
    
    if (result != null) {
      if (recordList == null) {
        recordList = new RecordList(name);
      }
      if (result instanceof Integer) {
        Record thisRecord = new Record();
        thisRecord.put("id", ((Integer)result).toString());
        recordList.add(thisRecord);
      } else if (result instanceof Boolean) {
        Record thisRecord = new Record();
        thisRecord.put("id", this.getValue(object, "id"));
        recordList.add(thisRecord);
      }
    }
  }
  
  public boolean hasError() {
    return (errorMessage.length() > 0);
  }
  
  public boolean hasRecordList() {
    return (recordList != null);
  }
  
  public void appendErrorMessage(String tmp) {
    if (tmp != null) {
      if (errorMessage.length() > 0) {
        errorMessage.append(System.getProperty("line.separator"));
      }
      errorMessage.append(tmp);
    }
  }
  
  public static String getValue(Object thisObject, String thisProperty) {
    try {
      thisProperty = thisProperty.substring(0, 1).toUpperCase() + thisProperty.substring(1);
      Method method = thisObject.getClass().getDeclaredMethod("get" + thisProperty, null);
      Object result = method.invoke(thisObject, null);
      if (result instanceof String) {
        return (String)result;
      } else {
        return String.valueOf(result);
      }
    } catch (Exception e) {
      return null;
    }
  }
}
