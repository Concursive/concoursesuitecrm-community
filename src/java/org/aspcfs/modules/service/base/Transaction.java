package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;
import java.sql.*;

public class Transaction extends Vector {

  private int id = -1;
  private Hashtable mapping = null;
  private StringBuffer errorMessage = new StringBuffer();
  private RecordList recordList = null;
  
  public Transaction() {}
  
  public void setId(int tmp) { id = tmp; }
  public void setId(String tmp) { 
    try {
      id = Integer.parseInt(tmp);
    } catch (Exception e) {
      id = -1;
    }
  }
  
  public int getId() { return id; }
  public String getErrorMessage() { return errorMessage.toString(); }
  
  public void build(Element transactionElement) {
    if (transactionElement.hasAttributes()) {
      this.setId(transactionElement.getAttribute("id"));
    }
    Vector objectElements = new Vector();
    XMLUtils.getAllChildren(transactionElement, objectElements);
    Iterator i = objectElements.iterator();
    while (i.hasNext()) {
      Element objectElement = (Element)i.next();
      TransactionItem thisItem = new TransactionItem(objectElement, mapping);
      //if (thisItem.isValid()) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Transaction-> Adding transaction item");
        }
        this.add(thisItem);
      //} else {
      //  appendErrorMessage(thisItem.getErrorMessage());
      //}
    }
  }
  
  public void setMapping(Hashtable tmp) {
    mapping = tmp;
  }
  
  public void addMapping(String key, String value) {
    if (mapping == null) {
      mapping = new Hashtable();
    }
    mapping.put(key, value);
  }
  
  public void addTransaction(TransactionItem tmp) {
    this.add(tmp);
  }
  
  public int execute(Connection db) throws SQLException {
    Exception exception = null;
    try {
      db.setAutoCommit(false);
      Iterator items = this.iterator();
      while (items.hasNext()) {
        TransactionItem thisItem = (TransactionItem)items.next();
        thisItem.execute(db);
        if (thisItem.hasError()) {
          appendErrorMessage(thisItem.getErrorMessage());
        }
        if (thisItem.hasRecordList() && recordList == null) {
          recordList = thisItem.getRecordList();
        }
      }
      db.commit();
    } catch (Exception e) {
      exception = e;
      e.printStackTrace(System.out);
      appendErrorMessage("Transaction failed");
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    
    if (exception == null && errorMessage.length() == 0) {
      return 0;
    } 
    
    if (exception != null) {
      appendErrorMessage(exception.getMessage());
    }
    return 1;
  }
  
  public boolean hasError() {
    return (errorMessage.length() > 0);
  }
  
  public RecordList getRecordList() {
    return recordList;
  }
  
  public void appendErrorMessage(String tmp) {
    if (tmp != null) {
      if (errorMessage.length() > 0) {
        errorMessage.append(System.getProperty("line.separator"));
      }
      errorMessage.append(tmp);
    }
  }
}
