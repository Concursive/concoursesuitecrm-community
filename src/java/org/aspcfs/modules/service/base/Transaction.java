/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.service.base;
import org.aspcfs.apps.transfer.DataRecord;

import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  A Transaction is an array of TransactionItems. When a system requests a
 *  transaction to be performed on an object -- for example, inserting records
 *  -- a Transaction is built from XML.<p>
 *
 *  <p/>
 *
 *  After the object is built, the transaction items can be executed.
 *
 * @author     matt rajkowski
 * @version    $Id: Transaction.java 16911 2006-11-01 09:09:22Z
 *      andrei.holub@corratech.com $
 * @created    April 10, 2002
 */
public class Transaction extends ArrayList {

  private static final long serialVersionUID = 4393255075249052599L;
  private int id = -1;
  private StringBuffer errorMessage = new StringBuffer();
  private RecordList recordList = null;
  private TransactionMeta meta = null;
  private PacketContext packetContext = null;
  private boolean validateObject = true;


  /**
   *  Gets the validateObject attribute of the Transaction object
   *
   * @return    The validateObject value
   */
  public boolean getValidateObject() {
    return validateObject;
  }


  /**
   *  Sets the validateObject attribute of the Transaction object
   *
   * @param  tmp  The new validateObject value
   */
  public void setValidateObject(boolean tmp) {
    this.validateObject = tmp;
  }


  /**
   *  Sets the validateObject attribute of the Transaction object
   *
   * @param  tmp  The new validateObject value
   */
  public void setValidateObject(String tmp) {
    this.validateObject = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Constructor for the Transaction object
   */
  public Transaction() { }


  /**
   *  Sets the id attribute of the Transaction object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    id = tmp;
  }


  /**
   *  Sets the id attribute of the Transaction object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    try {
      id = Integer.parseInt(tmp);
    } catch (Exception e) {
      id = -1;
    }
  }


  /**
   *  Sets the packetContext attribute of the Transaction object
   *
   * @param  tmp  The new packetContext value
   */
  public void setPacketContext(PacketContext tmp) {
    packetContext = tmp;
  }


  /**
   *  Gets the id attribute of the Transaction object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the errorMessage attribute of the Transaction object
   *
   * @return    The errorMessage value
   */
  public String getErrorMessage() {
    return errorMessage.toString();
  }


  /**
   *  Gets the recordList attribute of the Transaction object
   *
   * @return    The recordList value
   */
  public RecordList getRecordList() {
    return recordList;
  }


  /**
   *  Builds a list of TransactionItems from XML
   *
   * @param  transactionElement  Description of Parameter
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
      TransactionItem thisItem = new TransactionItem(
          objectElement, packetContext.getObjectMap(), packetContext.getUserBean());
      thisItem.setDisableSyncMap(packetContext.getDisableSyncMap());
      thisItem.setPacketContext(packetContext);
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
   *  Description of the Method
   *
   * @param  record  Description of the Parameter
   */
  public void build(DataRecord record) {
    TransactionItem item = new TransactionItem(record, packetContext.getObjectMap());
    item.setPacketContext(packetContext);
    this.add(item);
  }


  
  /**
   *  Description of the Method
   *
   * @param  records  Description of the Parameter
   */
  public void build(ArrayList records) {
    Iterator i = records.iterator();
    while (i.hasNext()) {
      DataRecord record = (DataRecord) i.next();
      TransactionItem thisItem = new TransactionItem(record,
          packetContext.getObjectMap());
      thisItem.setDisableSyncMap(packetContext.getDisableSyncMap());
      thisItem.setPacketContext(packetContext);
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
   * @param  key    The feature to be added to the Mapping attribute
   * @param  value  The feature to be added to the Mapping attribute
   */
  public void addMapping(String key, SyncTable value) {
    packetContext.getObjectMap().put(key, value);
  }


  /**
   *  Adds a feature to the Transaction attribute of the Transaction object
   *
   * @param  tmp  The feature to be added to the Transaction attribute
   */
  public void addTransaction(TransactionItem tmp) {
    this.add(tmp);
  }


  /**
   *  Executes all of the TransactionItems in the array
   *
   * @param  db             Description of Parameter
   * @param  dbLookup       Description of the Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  public int execute(Connection db, Connection dbLookup) throws SQLException {
    Exception exception = null;
    boolean commit = db.getAutoCommit();
    try {
      if (commit) {
        db.setAutoCommit(false);
      }
      //Create a shared context for items within a transaction
      TransactionContext transactionContext = new TransactionContext();
      //Process the transaction items
      Iterator items = this.iterator();
      while (items.hasNext()) {
        TransactionItem thisItem = (TransactionItem) items.next();
        thisItem.setMeta(meta);
        thisItem.setTransactionContext(transactionContext);
        if (validateObject) {
          //Verify if the Object is valid
          if (!thisItem.isObjectValid(db)) {
            appendErrorMessage("Object validation error");
            throw new Exception("Object Validation Failed");
          }
        }
        thisItem.execute(db, dbLookup);
        //If the item generated an error, then add it to the list to show the client
        if (thisItem.hasError()) {
          appendErrorMessage(thisItem.getErrorMessage());
        }
        //If the item generated a record list, then retrieve it so the records can be
        //returned to the client
        if (thisItem.hasRecordList() && recordList == null) {
          recordList = thisItem.getRecordList();
        }
        //If the item allows its key to be shared with other items, then add it
        //to the transactionContext
        if (thisItem.getShareKey()) {
          String keyName = ((SyncTable) packetContext.getObjectMap().get(
              thisItem.getName())).getKey();
          if (keyName != null) {
            transactionContext.getPropertyMap().put(
                thisItem.getName() + "." + keyName,
                ObjectUtils.getParam(thisItem.getObject(), keyName));
          }
        }
      }
      if (commit) {
        db.commit();
      }
    } catch (Exception e) {
      exception = e;
      e.printStackTrace(System.out);
      appendErrorMessage("Transaction failed");
      if (commit) {
        db.rollback();
      }
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
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
   * @return    Description of the Returned Value
   */
  public boolean hasError() {
    return (errorMessage.length() > 0);
  }


  /**
   *  Description of the Method
   *
   * @param  tmp  Description of Parameter
   */
  public void appendErrorMessage(String tmp) {
    if (tmp != null) {
      if (errorMessage.length() > 0) {
        errorMessage.append(System.getProperty("line.separator"));
      }
      errorMessage.append(tmp);
    }
  }

  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public boolean allowed() {
    Iterator items = this.iterator();
    while (items.hasNext()) {
      TransactionItem thisItem = (TransactionItem) items.next();
      if (!thisItem.allowed()) {
        return false;
      }
    }

    return true;
  }
}

