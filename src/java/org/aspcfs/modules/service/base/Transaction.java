package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;

public class Transaction extends Vector {

  private int id = -1;
  private Hashtable mapping = new Hashtable();
  
  public Transaction() {}
  
  public void setId(int tmp) { id = tmp; }
  public void setId(String tmp) { 
    try {
      id = Integer.parseInt(tmp);
    } catch (Exception e) {
      id = -1;
    }
  }
  
  public void build(Element transactionElement) {
    if (transactionElement.hasAttributes()) {
      this.setId(transactionElement.getAttribute("id"));
    }
    System.out.println("Transaction-> Id: " + this.id);
    Vector objectElements = new Vector();
    XMLUtils.getAllChildren(transactionElement, objectElements);
    Iterator i = objectElements.iterator();
    while (i.hasNext()) {
      Element objectElement = (Element)i.next();
      TransactionItem thisItem = new TransactionItem(objectElement, mapping);
      if (thisItem.isValid()) {
        this.add(thisItem);
      }
    }
  }
  
  public void addMapping(String key, String value) {
    mapping.put(key, value);
  }
  
  public void addTransaction(TransactionItem tmp) {
    this.add(tmp);
  }

}
