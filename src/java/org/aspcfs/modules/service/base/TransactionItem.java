package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;
import java.sql.*;
import java.lang.reflect.*;
import com.darkhorseventures.cfsbase.*;

/**
 *  Every Transaction can be made of many TransactionItems. TransactionItems
 *  represent objects in which a method will be called upon.<p>
 *
 *  Example:<br>
 *  The TransactionItem is to insert an Organization. So, the object is
 *  Organization, the action is an INSERT, the meta property contains fields
 *  that are to be returned after the insert is executed, any errors that occur
 *  are placed in the errorMessage property.
 *
 *@author     matt
 *@created    April 10, 2002
 *@version    $Id$
 */
public class TransactionItem {

  private final static int INSERT = 1;
  private final static int SELECT = 2;
  private final static int UPDATE = 3;
  private final static int DELETE = 4;

  private String name = null;
  private Object object = null;
  private int action = -1;
  private StringBuffer errorMessage = new StringBuffer();
  private RecordList recordList = null;
  private TransactionMeta meta = null;


  /**
   *  Constructor for the TransactionItem object
   */
  public TransactionItem() { }


  /**
   *  Constructor a TransactionItem Object from an XML element, using the
   *  supplied mapping to translate the XML element tag name to a Class.
   *
   *@param  objectElement  Description of Parameter
   *@param  mapping        Description of Parameter
   */
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


  /**
   *  Gets the specified property from the specified Object.
   *
   *@param  thisObject    Description of Parameter
   *@param  thisProperty  Description of Parameter
   *@return               The value value
   */
  public static String getValue(Object thisObject, String thisProperty) {
    try {
      thisProperty = thisProperty.substring(0, 1).toUpperCase() + thisProperty.substring(1);
      Method method = thisObject.getClass().getDeclaredMethod("get" + thisProperty, null);
      Object result = method.invoke(thisObject, null);
      if (result == null) {
        return null;
      } else if (result instanceof String) {
        return (String) result;
      } else {
        return String.valueOf(result);
      }
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Adds property names and values to the Record object, based on the supplied
   *  meta data
   *
   *@param  thisRecord  The feature to be added to the Fields attribute
   *@param  thisMeta    The feature to be added to the Fields attribute
   *@param  thisObject  The feature to be added to the Fields attribute
   */
  public static void addFields(Record thisRecord, TransactionMeta thisMeta, Object thisObject) {
    if (thisMeta != null && thisMeta.getFields() != null) {
      Iterator fields = thisMeta.getFields().iterator();
      while (fields.hasNext()) {
        String thisField = (String) fields.next();
        String thisValue = getValue(thisObject, thisField);
        if (thisValue == null) {
          thisValue = "";
        }
        thisRecord.put(thisField, thisValue);
      }
    }
  }


  /**
   *  Sets the object attribute of the TransactionItem object
   *
   *@param  tmp  The new object value
   */
  public void setObject(Object tmp) {
    object = tmp;
  }


  /**
   *  Sets the object attribute of the TransactionItem object from XML based on
   *  the mapping data. If the element tag is "contact" and there is a mapping
   *  to "com.darkhorseventures.cfsbase.Contact", then the Object is created and
   *  populated from the XML.
   *
   *@param  element        The new object value
   *@param  mapping        The new object value
   *@exception  Exception  Description of Exception
   */
  public void setObject(Element element, Hashtable mapping) throws Exception {
    name = element.getTagName();
    if (mapping.containsKey(name)) {
      object = Class.forName((String) mapping.get(name)).newInstance();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TransactionItem-> New: " + object.getClass().getName());
      }
    } else {
      appendErrorMessage("Unsupported object specified");
    }
  }


  /**
   *  Sets the action attribute of the TransactionItem object
   *
   *@param  tmp  The new action value
   */
  public void setAction(int tmp) {
    action = tmp;
  }


  /**
   *  Determines the methods that are allowed from a specified action.
   *  These are the methods that can be executed on the new Object.
   *
   *@param  tmp  The new action value
   */
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


  /**
   *  Sets the action attribute of the TransactionItem object
   *
   *@param  objectElement  The new action value
   */
  public void setAction(Element objectElement) {
    if (objectElement.hasAttributes()) {
      String thisAction = objectElement.getAttribute("type");
      if (thisAction == null || thisAction.trim().equals("")) {
        thisAction = objectElement.getAttribute("action");
      }
      this.setAction(thisAction);
    }
  }


  /**
   *  Sets the meta attribute of the TransactionItem object
   *
   *@param  tmp  The new meta value
   */
  public void setMeta(TransactionMeta tmp) {
    this.meta = tmp;
  }


  /**
   *  Gets the errorMessage attribute of the TransactionItem object
   *
   *@return    The errorMessage value
   */
  public String getErrorMessage() {
    return (errorMessage.toString());
  }


  /**
   *  Gets the name attribute of the TransactionItem object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the object attribute of the TransactionItem object
   *
   *@return    The object value
   */
  public Object getObject() {
    return object;
  }


  /**
   *  Gets the recordList attribute of the TransactionItem object
   *
   *@return    The recordList value
   */
  public RecordList getRecordList() {
    return recordList;
  }


  /**
   *  Gets the meta attribute of the TransactionItem object
   *
   *@return    The meta value
   */
  public TransactionMeta getMeta() {
    return meta;
  }


  /**
   *  Assumes that the Object has already been built and populated, now
   *  the specified action will be executed.  A database connection is
   *  passed along since the Object will need it.<p>
   *
   *  Data can be selected, inserted, updated, deleted, and synchronized
   *  with client systems.
   *
   *@param  db             Description of Parameter
   *@exception  Exception  Description of Exception
   */
  public void execute(Connection db) throws Exception {
    String executeMethod = null;
    switch (action) {
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
      if ((action == INSERT && meta != null) || action == SELECT) {
        if (recordList == null) {
          recordList = new RecordList(name);
        }
      }
      
      if (recordList != null) {
        //Need to see if the Object is a collection of Objects, otherwise
        //just process it as a single record.
        if (object instanceof java.util.AbstractList) {
          Iterator objectItems = ((java.util.AbstractList) object).iterator();
          while (objectItems.hasNext()) {
            Object objectItem = objectItems.next();
            Record thisRecord = new Record();
            this.addFields(thisRecord, meta, objectItem);
            recordList.add(thisRecord);
          }
        } else {
          Record thisRecord = new Record();
          this.addFields(thisRecord, meta, object);
          recordList.add(thisRecord);
        }
      }
    }
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
   *@return    Description of the Returned Value
   */
  public boolean hasRecordList() {
    return (recordList != null);
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

