package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;

public class TransactionItem {

  private static final int INSERT = 1;
  private static final int SELECT = 2;
  private static final int UPDATE = 3;
  private static final int DELETE = 4;

  private Object object = null;
  private int action = -1;
  
  public TransactionItem() {}
  
  public TransactionItem(Element objectElement, Hashtable mapping) {
    this.setAction(objectElement);
    this.setObject(objectElement, mapping);
    XMLUtils.populateObject(object, objectElement);
  }
  
  public void setObject(Object tmp) {
    object = tmp;
  }
  
  public void setObject(Element element, Hashtable mapping) {
    try {
      String elementTag = element.getTagName();
      if (mapping.containsKey(elementTag)) {
        //Create the object
        object = Class.forName((String)mapping.get(elementTag)).newInstance();
        if (System.getProperty("DEBUG") != null) { 
          System.out.println("TransactionItem-> New: " + object.getClass().getName());
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
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
  
  public boolean isValid() {
    return (object != null && action > -1);
  }

}
