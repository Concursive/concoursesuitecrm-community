package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;
import java.sql.*;
import java.lang.reflect.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.ObjectUtils;
import com.darkhorseventures.webutils.PagedListInfo;
import org.theseus.beans.*;

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
 *@version    $Id: TransactionItem.java,v 1.13 2002/04/24 15:39:44 mrajkowski
 *      Exp $
 */
public class TransactionItem {

  //Requested object actions
  private final static byte INSERT = 1;
  private final static byte SELECT = 2;
  private final static byte UPDATE = 3;
  private final static byte DELETE = 4;
  private final static byte SYNC = 5;
  private final static byte SYNC_START = 6;
  private final static byte SYNC_END = 7;
  private final static byte SYNC_DELETE = 8;
  private final static byte GET_DATETIME = 9;

  private String name = null;
  private Object object = null;
  private int action = -1;
  private String actionMethod = null;
  private int identity = 1;
  private PagedListInfo pagedListInfo = null;
  private StringBuffer errorMessage = new StringBuffer();
  private RecordList recordList = null;
  private TransactionMeta meta = null;
  private HashMap ignoredProperties = null;
  private SyncClientManager clientManager = null;
  private AuthenticationItem auth = null;
  private HashMap mapping = null;
  private SyncClientMap syncClientMap = null;
  

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
  public TransactionItem(Element objectElement, HashMap mapping) {
    try {
      this.setAction(objectElement);
      this.setObject(objectElement, mapping);
      ignoredProperties = XMLUtils.populateObject(object, objectElement);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TransactionItem-> Cannot create: " + objectElement.getTagName());
        e.printStackTrace(System.out);
      }
      appendErrorMessage("Invalid element: " + objectElement.getTagName());
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
  public void setObject(Element element, HashMap mapping) throws Exception {
    name = element.getTagName();
    if (mapping.containsKey(name)) {
      object = Class.forName(((SyncTable) mapping.get(name)).getMappedClassName()).newInstance();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TransactionItem-> New: " + object.getClass().getName());
      }
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
   *  Determines the methods that are allowed from a specified action. These are
   *  the methods that can be executed on the new Object.
   *
   *@param  tmp  The new action value
   */
  public void setAction(String tmp) {
    if ("insert".equals(tmp) || tmp.startsWith("insert")) {
      setAction(this.INSERT);
      if (tmp.length() > 6) {
        actionMethod = tmp;
      }
    } else if ("update".equals(tmp)) {
      setAction(this.UPDATE);
    } else if ("select".equals(tmp)) {
      setAction(this.SELECT);
    } else if ("delete".equals(tmp)) {
      setAction(this.DELETE);
    } else if ("sync".equals(tmp)) {
      setAction(this.SYNC);
    } else if ("syncStart".equals(tmp)) {
      setAction(this.SYNC_START);
    } else if ("syncEnd".equals(tmp)) {
      setAction(this.SYNC_END);
    } else if ("getDateTime".equals(tmp)) {
      setAction(this.GET_DATETIME);
    } else if ("syncDelete".equals(tmp)) {
      setAction(this.SYNC_DELETE);
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
      String thisIdentity = objectElement.getAttribute("identity");
      try {
        identity = Integer.parseInt(thisIdentity);
      } catch (Exception e) {
      }
      String thisCurrentOffset = objectElement.getAttribute("offset");
      String thisItemsPerPage = objectElement.getAttribute("items");
      if ((thisCurrentOffset != null && !"".equals(thisCurrentOffset)) ||
          (thisItemsPerPage != null && !"".equals(thisItemsPerPage))) {
        pagedListInfo = new PagedListInfo();
        pagedListInfo.setItemsPerPage(thisItemsPerPage);
        pagedListInfo.setCurrentOffset(thisCurrentOffset);
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
  
  public void setClientManager(SyncClientManager tmp) { this.clientManager = tmp; }
  public void setAuth(AuthenticationItem tmp) { this.auth = tmp; }
  public void setMapping(HashMap tmp) { this.mapping = tmp; }


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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  record            Description of Parameter
   *@param  syncClientMap     Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void insertClientMapping(Connection db, Record record) throws SQLException {
    if (record.containsKey("guid")) {
      clientManager.insert(
        syncClientMap.getClientId(),
        syncClientMap.getTableId(),
        new Integer(record.getRecordId()),
        new Integer((String)record.get("guid"))
      );
      syncClientMap.setRecordId(record.getRecordId());
      syncClientMap.setClientUniqueId((String)record.get("guid"));
      syncClientMap.insert(db, (String) record.get("modified"));
    }
  }
  
  public void updateClientMapping(Connection db, Record record) throws SQLException {
    if (record.containsKey("guid")) {
      syncClientMap.setRecordId(record.getRecordId());
      syncClientMap.setClientUniqueId((String)record.get("guid"));
      syncClientMap.updateStatusDate(db, (String) record.get("modified"));
    }
  }
  
  public void deleteClientMapping(Connection db, Record record) throws SQLException {
    if (record.containsKey("guid")) {
      syncClientMap.setRecordId(record.getRecordId());
      syncClientMap.setClientUniqueId((String)record.get("guid"));
      syncClientMap.delete(db);
    }
    clientManager.remove(
      syncClientMap.getClientId(),
      syncClientMap.getTableId(),
      new Integer(record.getRecordId())
    );
  }


  /**
   *  Assumes that the Object has already been built and populated, now the
   *  specified action will be executed. A database connection is passed along
   *  since the Object will need it.<p>
   *
   *  Data can be selected, inserted, updated, deleted, and synchronized with
   *  client systems.
   *
   *@param  db             Description of Parameter
   *@param  auth           Description of Parameter
   *@param  mapping        Description of Parameter
   *@param  dbLookup       Description of Parameter
   *@exception  Exception  Description of Exception
   */
  public void execute(Connection db, Connection dbLookup) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TransactionItem-> Executing transaction");
    }
    if ((object == null || name == null) && !"system".equals(name)) {
      appendErrorMessage("Unsupported object specified");
      return;
    }

    syncClientMap = new SyncClientMap();
    syncClientMap.setClientId(auth.getClientId());
    if (!"system".equals(name)) {
      syncClientMap.setTableId(((SyncTable) mapping.get(name)).getId());
    }

    if (recordList == null) {
      recordList = new RecordList(name);
    }

    if (pagedListInfo != null) {
      doSetPagedListInfo();
    }

    if (action == GET_DATETIME) {
      Record thisRecord = new Record("info");
      thisRecord.put("dateTime", String.valueOf(new java.sql.Timestamp(new java.util.Date().getTime())));
      recordList.add(thisRecord);
    } else if (action == SYNC_START) {
      ObjectUtils.setParam(object, "id", String.valueOf(auth.getClientId()));
      ObjectUtils.setParam(object, "anchor", auth.getLastAnchor());
      if (!((SyncClient) object).checkNormalSync(db)) {
        appendErrorMessage("Client and server not in sync!");
      }
    } else if (action == SYNC_END) {
      ObjectUtils.setParam(object, "id", String.valueOf(auth.getClientId()));
      ObjectUtils.setParam(object, "anchor", auth.getNextAnchor());
      ((SyncClient) object).updateSyncAnchor(db);
      Record thisRecord = new Record("syncEnd");
      thisRecord.put("endDateTime", String.valueOf(new java.sql.Timestamp(new java.util.Date().getTime())));
      recordList.add(thisRecord);
    } else if (action == SYNC) {
      ObjectUtils.setParam(object, "lastAnchor", auth.getLastAnchor());
      ObjectUtils.setParam(object, "nextAnchor", auth.getNextAnchor());
      //If the client supplied any filters... set them
      setGuidParameters(db);
      //Build inserts for client
      if (auth.getNextAnchor() != null) {
        buildRecords(object, db, dbLookup, Constants.SYNC_INSERTS);
      }
      //Build updates for client
      if (auth.getLastAnchor() != null) {
        buildRecords(object, db, dbLookup, Constants.SYNC_UPDATES);
      }
    } else if (action == SYNC_DELETE) {
      //Build deletes for client
      String uniqueField = ObjectUtils.getParam(object, "uniqueField");
      String tableName = ObjectUtils.getParam(object, "tableName");
      if (uniqueField != null && tableName != null) {
        PreparedStatement pst = null;
        ResultSet rs = syncClientMap.buildSyncDeletes(db, pst, uniqueField, tableName, recordList);
        while (rs.next()) {
          Record thisRecord = new Record("delete");
          int cuid = rs.getInt("cuid");
          int recordId = rs.getInt("record_id");
          thisRecord.put("guid", String.valueOf(cuid));
          thisRecord.setRecordId(recordId);
          recordList.add(thisRecord);
          //This can be done when the client sends a sync_end...
          //if (syncClientMap.lookupClientId(clientManager, syncClientMap.getTableId(), String.valueOf(recordId)) == -1) {
          //  this.deleteClientMapping(dbLookup, thisRecord);
          //}
        }
        rs.close();
        if (pst != null) {
          pst.close();
        }
      }
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TransactionItem-> Base request");
      }
      String executeMethod = null;
      switch (action) {
          case -1:
            appendErrorMessage("Action not specified");
            break;
          case INSERT:
            if (actionMethod != null) {
              executeMethod = actionMethod;
            } else {
              executeMethod = "insert";
            }
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
      if (executeMethod != null) {
        if (action == INSERT || action == UPDATE) {
          //Populate any client GUIDs with the correct server ID
          setGuidParameters(db);
        }
        
        if (action == DELETE) {
          //Set the object's id to be deleted, based on the client guid
          this.setObjectId(db);
          syncClientMap.setRecordId(Integer.parseInt(ObjectUtils.getParam(object, "id")));
          syncClientMap.setClientUniqueId((String) ignoredProperties.get("guid"));
        }

        if (action == UPDATE) {
          //Set the object's id to be updated, based on the client guid
          this.setObjectId(db);
          //Retrieve the previous modified date to ensure integrity of update
          syncClientMap.setRecordId(Integer.parseInt(ObjectUtils.getParam(object, "id")));
          syncClientMap.setClientUniqueId((String) ignoredProperties.get("guid"));
          this.setReferencedTable();
          syncClientMap.buildStatusDate(db);
          ObjectUtils.setParam(object, "modified", syncClientMap.getStatusDate());
        }
        Object result = doExecute(db, executeMethod);
        checkResult(result);
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TransactionItem-> " + object.getClass().getName() + " " + executeMethod);
        }
        if (action == INSERT) {
          //Insert the guid / id into client mapping, at this point, the object will have its
          //newly inserted id, so set the syncMap before the insert
          if (ignoredProperties != null && ignoredProperties.containsKey("guid")) {
            syncClientMap.setRecordId(Integer.parseInt(ObjectUtils.getParam(object, "id")));
            if (setReferencedTable()) {
              syncClientMap.setClientUniqueId((String) ignoredProperties.get("guid"));
            }
            //Need to log the date/time of the new record for later approval of updates
            //Reload the newly inserted object to get its insert/modified date
            Object insertedObject = ObjectUtils.constructObject(object.getClass(), db, Integer.parseInt(ObjectUtils.getParam(object, "id")));
            if (insertedObject == null) {
              //Might be a lookupElement
              insertedObject = ObjectUtils.constructObject(object.getClass(), db, Integer.parseInt(ObjectUtils.getParam(object, "id")), ObjectUtils.getParam(object, "tableName"));
            }
            if (insertedObject == null) {
              //Might be a customLookupElement
              insertedObject = ObjectUtils.constructObject(object.getClass(), db, Integer.parseInt(ObjectUtils.getParam(object, "id")), ObjectUtils.getParam(object, "tableName"), ObjectUtils.getParam(object, "uniqueField"));
            }
            if (insertedObject == null) {
              if (System.getProperty("DEBUG") != null) {
                System.out.println("TransactionItem-> The object was inserted, but could not be reloaded: possible invalid constructor");
              }
            }
            syncClientMap.insert(db, ObjectUtils.getParam(insertedObject, "modified"));
          }
          addRecords(object, recordList, "processed");
        } else if (action == UPDATE) {
          //If the result is an Integer == 1, then the update is successful, else a "conflict"
          //since someone else updated it first
          if (((Integer)result).intValue() == 1) {
            //Update the modified date in client mapping
            if (ignoredProperties != null && ignoredProperties.containsKey("guid")) {
              Object updatedObject = ObjectUtils.constructObject(object.getClass(), db, Integer.parseInt(ObjectUtils.getParam(object, "id")));
              syncClientMap.updateStatusDate(db, ObjectUtils.getParam(updatedObject, "modified"));
            }
            addRecords(object, recordList, "processed");
          } else {
            addRecords(object, recordList, "conflict");
            syncClientMap.insertConflict(db);
          }
        } else if (action == DELETE) {
          Record thisRecord = addRecords(object, recordList, "delete");
          this.setReferencedTable();
          if (thisRecord != null && 
              syncClientMap.lookupClientId(
                clientManager, 
                syncClientMap.getTableId(), 
                ObjectUtils.getParam(object, "id")
              ) != -1) {
            this.deleteClientMapping(dbLookup, thisRecord);
          } else {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("TransactionItem-> Mapping not found for delete");
            }
          }
        } else {
          //It wasn't an insert or an update...
          addRecords(object, recordList, null);
        }
      }
    }
    if (pagedListInfo != null) {
      recordList.setTotalRecords(pagedListInfo.getMaxRecords());
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

  public void setObjectId(Connection db) throws SQLException {
    if (ignoredProperties != null && ignoredProperties.containsKey("guid")) {
      SyncTable referencedTable = (SyncTable) mapping.get(name + "List");
      if (referencedTable != null) {
        String value = (String)ignoredProperties.get("guid");
        int recordId = syncClientMap.lookupServerId(clientManager, referencedTable.getId(), value);
        ObjectUtils.setParam(object, "id", String.valueOf(recordId));
        if (System.getProperty("DEBUG") != null) {
          System.out.println("TransactionItem-> Setting object id: " + recordId);
        }
      }
    }
  }

  /**
   *  Sets the guidParameters attribute of the TransactionItem object.
   *  When a client is inserting or updating records, the server needs to
   *  retrieve the ids used by the client from the client/server mapping.
   *
   *@param  db                The new guidParameters value
   *@param  mapping           The new guidParameters value
   *@param  syncClientMap     The new guidParameters value
   *@exception  SQLException  Description of Exception
   */
  private void setGuidParameters(Connection db) throws SQLException {
    if (ignoredProperties != null && ignoredProperties.size() > 0) {
      Iterator ignoredList = ignoredProperties.keySet().iterator();
      while (ignoredList.hasNext()) {
        String param = (String) ignoredList.next();
        if (param != null && param.endsWith("Guid")) {
          String value = (String) ignoredProperties.get(param);
          param = param.substring(0, param.lastIndexOf("Guid"));
          
          if (param.indexOf("^") > -1) {
            String lookupField = param.substring(param.indexOf("^") + 1);
            param = param.substring(0, param.indexOf("^"));
            
            SyncTable referencedTable = (SyncTable) mapping.get(lookupField + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupServerId(clientManager, referencedTable.getId(), value);
              ObjectUtils.setParam(object, param, String.valueOf(recordId));
              if (System.getProperty("DEBUG") != null) {
                System.out.println("TransactionItem-> Setting server parameter: " + param + " data: " + recordId);
              }
            }
          } else {
            SyncTable referencedTable = (SyncTable) mapping.get(param + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupServerId(clientManager, referencedTable.getId(), value);
              ObjectUtils.setParam(object, param + "Id", String.valueOf(recordId));
              if (System.getProperty("DEBUG") != null) {
                System.out.println("TransactionItem-> Setting new parameter: " + param + "Id" + " data: " + recordId);
              }
            } else {
              throw new SQLException("Sync reference does not exist, you must sync referenced tables first");
            }
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of Parameter
   *@param  executeMethod  Description of Parameter
   *@return                Description of the Returned Value
   *@exception  Exception  Description of Exception
   */
  private Object doExecute(Connection db, String executeMethod) throws Exception {
    Class[] dbClass = new Class[]{Class.forName("java.sql.Connection")};
    Object[] dbObject = new Object[]{db};
    Method method = object.getClass().getMethod(executeMethod, dbClass);
    return (method.invoke(object, dbObject));
  }
  
  private void checkResult(Object result) {
    try {
      if (result instanceof Boolean && !((Boolean)result).booleanValue()) {
        System.out.println("Inserting object failed");
        //TODO: Show error messages from object
        if (object instanceof GenericBean) {
          HashMap errors = ((GenericBean)object).getErrors();
          Iterator i = errors.keySet().iterator();
          while (i.hasNext()) {
            String errorText = (String)errors.get((String)(i.next()));
            System.out.println("--------> " + errorText); 
          }
        }
      }
    } catch (Exception e) {
    }
  }


  /**
   *  Description of the Method
   */
  private void doSetPagedListInfo() {
    try {
      Class[] theClass = new Class[]{pagedListInfo.getClass()};
      Object[] theObject = new Object[]{pagedListInfo};
      Method method = object.getClass().getMethod("setPagedListInfo", theClass);
      method.invoke(object, theObject);
    } catch (Exception e) {
      //This class must not support the pagedListInfo
      if (System.getProperty("DEBUG") != null) {
        System.out.println("TransactionItem-> Object does not have setPagedListInfo method");
      }
    }
  }


  /**
   *  Adds a feature to the Records attribute of the TransactionItem object
   *
   *@param  object         The feature to be added to the Records attribute
   *@param  db             The feature to be added to the Records attribute
   *@param  syncType       The feature to be added to the Records attribute
   *@param  dbLookup       The feature to be added to the Records attribute
   *@param  mapping        The feature to be added to the Records attribute
   *@param  syncClientMap  The feature to be added to the Records attribute
   *@exception  Exception  Description of Exception
   */
  private void buildRecords(Object object, Connection db, Connection dbLookup, int syncType) throws Exception {
    PreparedStatement pst = null;
    Class[] dbClass = new Class[]{Class.forName("java.sql.Connection"), Class.forName("java.sql.PreparedStatement")};
    Object[] dbObject = new Object[]{db, pst};
    String executeMethod = "queryList";
    ObjectUtils.setParam(object, "syncType", String.valueOf(syncType));
    Method method = object.getClass().getMethod(executeMethod, dbClass);
    Object result = method.invoke(object, dbObject);
    while (((ResultSet) result).next()) {
      String objectMethod = "getObject";
      Class[] rsClass = new Class[]{Class.forName("java.sql.ResultSet")};
      Object[] rsObject = new Object[]{result};
      Method getObject = object.getClass().getMethod(objectMethod, rsClass);
      Object thisObject = getObject.invoke(object, rsObject);
      String recordAction = null;
      switch (syncType) {
          case Constants.SYNC_INSERTS:
            recordAction = "insert";
            break;
          case Constants.SYNC_UPDATES:
            recordAction = "update";
            break;
          default:
            break;
      }
      if (syncType == Constants.SYNC_INSERTS) {
        //Check to see if the client already has this record...
        if (syncClientMap.lookupClientId(clientManager, syncClientMap.getTableId(), ObjectUtils.getParam(thisObject, "id")) == -1) {
          Record thisRecord = addRecords(thisObject, recordList, recordAction);
          this.insertClientMapping(dbLookup, thisRecord);
        }
      } else if (syncType == Constants.SYNC_UPDATES) {
        //Update the status date of the client mapping
        Record thisRecord = addRecords(thisObject, recordList, recordAction);
        this.updateClientMapping(dbLookup, thisRecord);
      }
    }
    ((ResultSet) result).close();
    if (pst != null) {
      pst.close();
    }
    ((java.util.AbstractList) object).clear();
  }


  /**
   *  Adds a feature to the Records attribute of the TransactionItem object
   *
   *@param  object            The feature to be added to the Records attribute
   *@param  recordList        The feature to be added to the Records attribute
   *@param  recordAction      The feature to be added to the Records attribute
   *@param  mapping           The feature to be added to the Records attribute
   *@param  syncClientMap     The feature to be added to the Records attribute
   *@param  db                The feature to be added to the Records attribute
   *@exception  SQLException  Description of Exception
   */
  private Record addRecords(Object object, RecordList recordList, String recordAction) throws SQLException {
    if (recordList != null) {
      //Need to see if the Object is a collection of Objects, otherwise
      //just process it as a single record.
      if (object instanceof java.util.AbstractList) {
        Iterator objectItems = ((java.util.AbstractList) object).iterator();
        while (objectItems.hasNext()) {
          Object objectItem = objectItems.next();
          Record thisRecord = new Record(recordAction);
          this.addFields(thisRecord, meta, objectItem);
          recordList.add(thisRecord);
        }
        return null;
      } else {
        Record thisRecord = new Record(recordAction);
        this.addFields(thisRecord, meta, object);
        recordList.add(thisRecord);
        return thisRecord;
      }
    }
    return null;
  }
  
  /**
   *  Adds property names and values to the Record object, based on the supplied
   *  meta data
   *
   *@param  thisRecord        The feature to be added to the Fields attribute
   *@param  thisMeta          The feature to be added to the Fields attribute
   *@param  thisObject        The feature to be added to the Fields attribute
   *@param  mapping           The feature to be added to the Fields attribute
   *@param  syncClientMap     The feature to be added to the Fields attribute
   *@exception  SQLException  Description of Exception
   */
  private void addFields(Record thisRecord, TransactionMeta thisMeta, Object thisObject) throws SQLException {
    if (thisMeta != null && thisMeta.getFields() != null) {
      Iterator fields = thisMeta.getFields().iterator();
      while (fields.hasNext()) {
        String thisField = (String) fields.next();
        String thisValue = null;
        if (thisField.endsWith("Guid")) {
          String lookupField = thisField.substring(0, thisField.lastIndexOf("Guid"));
          String param = thisField.substring(0, thisField.lastIndexOf("Guid"));
          if (param.indexOf("^") > -1) {
            param = param.substring(param.indexOf("^") + 1);
            lookupField = thisField.substring(0, thisField.indexOf("^"));
            thisField = thisField.substring(0, thisField.indexOf("^"));
            SyncTable referencedTable = (SyncTable) mapping.get(param + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupClientId(clientManager, referencedTable.getId(),
                  ObjectUtils.getParam(thisObject, lookupField));
              thisValue = String.valueOf(recordId);
            }
          } else {
            SyncTable referencedTable = (SyncTable) mapping.get(param + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupClientId(clientManager, referencedTable.getId(),
                  ObjectUtils.getParam(thisObject, lookupField + "Id"));
              thisValue = String.valueOf(recordId);
            }
          }
        } else {
          thisValue = ObjectUtils.getParam(thisObject, thisField);
          if (thisField.indexOf(".guid") > -1) {
            //This is a sub-object, so get the correct guid for the client
            String lookupField = thisField.substring(0, thisField.indexOf(".guid"));
            SyncTable referencedTable = (SyncTable) mapping.get(lookupField + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupClientId(clientManager, referencedTable.getId(), thisValue);
              thisValue = String.valueOf(recordId);
            }
          }
        }
        if (thisValue == null) {
          thisValue = "";
        }
        thisRecord.put(thisField, thisValue);
      }
      try {
        //Special items when sending back an action to the client...
        thisRecord.setRecordId(ObjectUtils.getParam(thisObject, "id"));
        if (thisRecord.containsKey("guid")) {
          if (thisRecord.getAction().equals("processed")) {
            thisRecord.put("guid", ignoredProperties.get("guid"));
          } else if (thisRecord.getAction().equals("insert")) {
            thisRecord.put("guid", String.valueOf(identity++));
          } else if (thisRecord.getAction().equals("update")) {
            //Sending an update back to client, get the correct guid
            thisRecord.put("guid", syncClientMap.lookupClientId(clientManager, syncClientMap.getTableId(), ObjectUtils.getParam(thisObject, "id")));
          } else if (thisRecord.getAction().equals("delete")) {
            //Let the client know that its record was deleted
            thisRecord.put("guid", ignoredProperties.get("guid"));
          } else if (thisRecord.getAction().equals("conflict")) {
            thisRecord.put("guid", ignoredProperties.get("guid"));
          }
        }
      } catch (java.lang.NumberFormatException e) {
        //This object doesn't have an id, might have multiple keys
      }
    }
  }
  
  public boolean setReferencedTable() {
    //The client requested an object, but the mapping is stored as the objectList
    if (!name.endsWith("List")) {
      SyncTable referencedTable = (SyncTable) mapping.get(name + "List");
      if (referencedTable != null) {
        syncClientMap.setTableId(referencedTable.getId());
        return true;
      }
    }
    return false;
  }

}

