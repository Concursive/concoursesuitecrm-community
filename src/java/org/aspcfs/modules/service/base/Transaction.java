package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;
import java.sql.*;
import com.darkhorseventures.cfsbase.SyncTable;
import com.darkhorseventures.cfsbase.SyncClientManager;
import com.darkhorseventures.controller.ObjectHookList;

/**
 *  A Transaction is an array of TransactionItems.  When a system requests
 *  a transaction to be performed on an object -- for example, 
 *  inserting records -- a Transaction is built from XML.<p>
 *
 *  After the object is built, the transaction items can be executed.
 *
 *@author     matt rajkowski
 *@created    April 10, 2002
 *@version    $Id$
 */
public class Transaction extends ArrayList {

  private int id = -1;
  private HashMap mapping = null;
  private StringBuffer errorMessage = new StringBuffer();
  private RecordList recordList = null;
  private TransactionMeta meta = null;
  private SyncClientManager clientManager = null;
  private AuthenticationItem auth = null;
  private ObjectHookList objectHookList = null;


  /**
   *  Constructor for the Transaction object
   */
  public Transaction() { }


  /**
   *  Sets the id attribute of the Transaction object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    id = tmp;
  }


  /**
   *  Sets the id attribute of the Transaction object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    try {
      id = Integer.parseInt(tmp);
    } catch (Exception e) {
      id = -1;
    }
  }


  /**
   *  Sets the mapping attribute of the Transaction object
   *
   *@param  tmp  The new mapping value
   */
  public void setMapping(HashMap tmp) {
    mapping = tmp;
  }
  
  public void setClientManager(SyncClientManager tmp) {
    clientManager = tmp;
  }
  
  public void setAuth(AuthenticationItem tmp) { this.auth = tmp; }

  public void setObjectHookList(ObjectHookList tmp) { this.objectHookList = tmp; }


  /**
   *  Gets the id attribute of the Transaction object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the errorMessage attribute of the Transaction object
   *
   *@return    The errorMessage value
   */
  public String getErrorMessage() {
    return errorMessage.toString();
  }


  /**
   *  Gets the recordList attribute of the Transaction object
   *
   *@return    The recordList value
   */
  public RecordList getRecordList() {
    return recordList;
  }


  /**
   *  Builds a list of TransactionItems from XML
   *
   *@param  transactionElement  Description of Parameter
   */
  public void build(Element transactionElement) {
    if (transactionElement.hasAttributes()) {
      this.setId(transactionElement.getAttribute("id"));
    }
    ArrayList objectElements = new ArrayList();
    XMLUtils.getAllChildren(transactionElement, objectElements);
    Iterator i = objectElements.iterator();
    while (i.hasNext()) {
      Element objectElement = (Element) i.next();
      TransactionItem thisItem = new TransactionItem(objectElement, mapping);
      thisItem.setClientManager(clientManager);
      thisItem.setMapping(mapping);
      thisItem.setAuth(auth);
      thisItem.setObjectHookList(objectHookList);
      if (thisItem.getName().equals("meta")) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Transaction-> Meta data found");
        }
        meta = (TransactionMeta) thisItem.getObject();
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Transaction-> Adding transaction item");
        }
        this.add(thisItem);
      }
    }
  }


  /**
   *  Adds a feature to the Mapping attribute of the Transaction object
   *
   *@param  key    The feature to be added to the Mapping attribute
   *@param  value  The feature to be added to the Mapping attribute
   */
  public void addMapping(String key, SyncTable value) {
    if (mapping == null) {
      mapping = new HashMap();
    }
    mapping.put(key, value);
  }


  /**
   *  Adds a feature to the Transaction attribute of the Transaction object
   *
   *@param  tmp  The feature to be added to the Transaction attribute
   */
  public void addTransaction(TransactionItem tmp) {
    this.add(tmp);
  }


  /**
   *  Executes all of the TransactionItems in the array
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int execute(Connection db, Connection dbLookup) throws SQLException {
    Exception exception = null;
    try {
      db.setAutoCommit(false);
      Iterator items = this.iterator();
      while (items.hasNext()) {
        TransactionItem thisItem = (TransactionItem) items.next();
        thisItem.setMeta(meta);
        thisItem.execute(db, dbLookup);
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


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasError() {
    return (errorMessage.length() > 0);
  }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of Parameter
   */
  public void appendErrorMessage(String tmp) {
    if (tmp != null) {
      if (errorMessage.length() > 0) {
        errorMessage.append(System.getProperty("line.separator"));
      }
      errorMessage.append(tmp);
    }
  }
}

