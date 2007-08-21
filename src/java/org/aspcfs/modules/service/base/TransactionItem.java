/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.service.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.controller.objectHookManager.ObjectHookAction;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.UserCentric;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.web.CustomLookupElement;
import org.aspcfs.utils.web.PagedListInfo;

import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
 * @author     matt rajkowski
 * @created    April 10, 2002
 * @version    $Id: TransactionItem.java,v 1.13 2002/04/24 15:39:44 mrajkowski
 *      Exp $
 */
public class TransactionItem {

  private final static Logger log = Logger.getLogger(org.aspcfs.modules.service.base.TransactionItem.class);
  public final static String fs = System.getProperty("file.separator");

  //Requested object actions
  public final static byte INSERT = 1;
  public final static byte SELECT = 2;
  public final static byte UPDATE = 3;
  public final static byte DELETE = 4;
  public final static byte SYNC = 5;
  public final static byte SYNC_START = 6;
  public final static byte SYNC_END = 7;
  public final static byte SYNC_DELETE = 8;
  public final static byte GET_DATETIME = 9;
  public final static byte CUSTOM_ACTION = 10;
  public final static byte GET_SYSTEM_PREFERENCES = 11;
  public final static byte GET_SYSTEM_XML_FILE = 12;
  public final static byte SYNC_PROCESSED = 13;

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
  private SyncClientMap syncClientMap = null;
  private PacketContext packetContext = null;
  private TransactionContext transactionContext = null;
  private boolean shareKey = false;
  private boolean disableSyncMap = false;


  /**
   *  Constructor for the TransactionItem object
   */
  public TransactionItem() { }


  /**
   *  Constructor a TransactionItem Object from an XML element, using the
   *  supplied mapping to translate the XML element tag name to a Class.
   *
   * @param  objectElement  Description of Parameter
   * @param  mapping        Description of Parameter
   * @param  thisUser       Description of the Parameter
   */
  public TransactionItem(Element objectElement, HashMap mapping, UserBean thisUser) {
    try {
      this.setAction(objectElement);
      this.setObject(objectElement, mapping);
      ignoredProperties = XMLUtils.populateObject(object, objectElement);

      //populate the object's user fields using the user that was authenticated
      this.populateUserData(thisUser);
    } catch (Exception e) {
      log.debug("Cannot create: " + objectElement.getTagName());
      e.printStackTrace(System.out);
      appendErrorMessage("Invalid element: " + objectElement.getTagName());
    }
  }


  /**
   *  Constructor for the TransactionItem object
   *
   * @param  record   Description of the Parameter
   * @param  mapping  Description of the Parameter
   */
  public TransactionItem(DataRecord record, HashMap mapping) {
    try {
      this.setAction(record.getAction());
      this.setObject(record.getName(), mapping);
      ignoredProperties = ObjectUtils.populateObject(object, record);

      if (record.hasIdentity()) {
        identity = Integer.parseInt(record.getIdentity());
      }
      if (record.hasOffset() || record.hasItems()) {
        pagedListInfo = new PagedListInfo();
        pagedListInfo.setItemsPerPage(record.getItems());
        pagedListInfo.setCurrentOffset(record.getOffset());
      }
      shareKey = record.getShareKey();
    } catch (Exception e) {
      log.debug("Cannot create: " + record.getName());
      e.printStackTrace(System.out);
      appendErrorMessage("Invalid element: " + record.getName());
    }
  }


  /**
   *  When user logs in through an external client, populate the object's user
   *  related fields (record keeping fields, ownership fields etc).
   *
   * @param  thisUser  The new userData value
   */
  public void populateUserData(UserBean thisUser) {
    try {
      if (thisUser != null) {
        ArrayList userIdParams = (ArrayList) ObjectUtils.getObject(object, "UserIdParams");
        if (userIdParams != null) {
          log.debug("Found userId params");
          Iterator params = userIdParams.iterator();
          while (params.hasNext()) {
            String param = (String) params.next();
            String value = String.valueOf(thisUser.getUserId());
            log.debug("userIdParams trying to set: " + param + "=" + value);
            ObjectUtils.setParam(object, param, value);
          }
        }
      }
    } catch (Exception e) {
      log.debug("Cannot populate User related data: " + object.getClass().getName());
      e.printStackTrace(System.out);
      appendErrorMessage("Cannot populate User related data: " + object.getClass().getName());
    }
  }


  /**
   *  Sets the object attribute of the TransactionItem object
   *
   * @param  tmp  The new object value
   */
  public void setObject(Object tmp) {
    object = tmp;
  }


  /**
   *  Sets the object attribute of the TransactionItem object
   *
   * @param  name        The new object value
   * @param  mapping     The new object value
   * @throws  Exception  Description of the Exception
   */
  public void setObject(String name, HashMap mapping) throws Exception {
    this.name = name;
    if (mapping.containsKey(name)) {
      object = Class.forName(
          ((SyncTable) mapping.get(name)).getMappedClassName()).newInstance();
      log.debug("New: " + object.getClass().getName());
    }
  }


  /**
   *  Sets the object attribute of the TransactionItem object from XML based on
   *  the mapping data. If the element tag is "contact" and there is a mapping
   *  to "org.aspcfs.modules.contacts.base.Contact", then the Object is created
   *  and populated from the XML.
   *
   * @param  element     The new object value
   * @param  mapping     The new object value
   * @throws  Exception  Description of Exception
   */
  public void setObject(Element element, HashMap mapping) throws Exception {
    this.setObject(element.getTagName(), mapping);
  }


  /**
   *  Sets the action attribute of the TransactionItem object
   *
   * @param  tmp  The new action value
   */
  public void setAction(int tmp) {
    action = tmp;
  }


  /**
   *  Determines the methods that are allowed from a specified action. These are
   *  the methods that can be executed on the new Object.
   *
   * @param  tmp  The new action value
   */
  public void setAction(String tmp) {
    if ("insert".equals(tmp) || tmp.startsWith("insert")) {
      setAction(TransactionItem.INSERT);
      if (tmp.length() > 6) {
        actionMethod = tmp;
      }
    } else if ("update".equals(tmp)) {
      setAction(TransactionItem.UPDATE);
    } else if ("select".equals(tmp)) {
      setAction(TransactionItem.SELECT);
    } else if ("delete".equals(tmp)) {
      setAction(TransactionItem.DELETE);
    } else if ("sync".equals(tmp)) {
      setAction(TransactionItem.SYNC);
    } else if ("syncStart".equals(tmp)) {
      setAction(TransactionItem.SYNC_START);
    } else if ("syncEnd".equals(tmp)) {
      setAction(TransactionItem.SYNC_END);
    } else if ("syncProcessed".equals(tmp)) {
      setAction(TransactionItem.SYNC_PROCESSED);
    } else if ("getDateTime".equals(tmp)) {
      setAction(TransactionItem.GET_DATETIME);
    } else if ("syncDelete".equals(tmp)) {
      setAction(TransactionItem.SYNC_DELETE);
    } else if ("getSystemPreferences".equals(tmp)) {
      setAction(TransactionItem.GET_SYSTEM_PREFERENCES);
    } else if ("getSystemXMLFile".equals(tmp)) {
      setAction(TransactionItem.GET_SYSTEM_XML_FILE);
    } else {
      setAction(TransactionItem.CUSTOM_ACTION);
      if ("execute".equals(tmp)) {
        actionMethod = "process";
      } else {
        actionMethod = tmp;
      }
    }
  }


  /**
   *  Sets the action attribute of the TransactionItem object
   *
   * @param  objectElement  The new action value
   */
  public void setAction(Element objectElement) {
    if (objectElement.hasAttributes()) {
      //Get the action for this item (Insert, Update, Delete, Select, etc.)
      String thisAction = objectElement.getAttribute("type");
      if (thisAction == null || thisAction.trim().equals("")) {
        thisAction = objectElement.getAttribute("action");
      }
      this.setAction(thisAction);
      //If specified, get the client's next id that should be used when
      //sending insert statements to the client
      String thisIdentity = objectElement.getAttribute("identity");
      try {
        identity = Integer.parseInt(thisIdentity);
      } catch (Exception e) {
      }
      //If specified, get the number of max records to return, and the offset
      //to begin returning records at -- useful for large datasets
      String thisCurrentOffset = objectElement.getAttribute("offset");
      String thisItemsPerPage = objectElement.getAttribute("items");
      if ((thisCurrentOffset != null && !"".equals(thisCurrentOffset)) ||
          (thisItemsPerPage != null && !"".equals(thisItemsPerPage))) {
        pagedListInfo = new PagedListInfo();
        pagedListInfo.setItemsPerPage(thisItemsPerPage);
        pagedListInfo.setCurrentOffset(thisCurrentOffset);
      }
      //See if the primary key of this object should be exposed to other
      //items within the same transaction
      shareKey = "true".equals(objectElement.getAttribute("shareKey"));
    }
  }


  /**
   *  Gets the disableSyncMap attribute of the TransactionItem object
   *
   * @return    The disableSyncMap value
   */
  public boolean getDisableSyncMap() {
    return disableSyncMap;
  }


  /**
   *  Sets the disableSyncMap attribute of the TransactionItem object
   *
   * @param  tmp  The new disableSyncMap value
   */
  public void setDisableSyncMap(boolean tmp) {
    this.disableSyncMap = tmp;
  }


  /**
   *  Sets the disableSyncMap attribute of the TransactionItem object
   *
   * @param  tmp  The new disableSyncMap value
   */
  public void setDisableSyncMap(String tmp) {
    this.disableSyncMap = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the meta attribute of the TransactionItem object
   *
   * @param  tmp  The new meta value
   */
  public void setMeta(TransactionMeta tmp) {
    this.meta = tmp;
  }


  /**
   *  Sets the packetContext attribute of the TransactionItem object
   *
   * @param  tmp  The new packetContext value
   */
  public void setPacketContext(PacketContext tmp) {
    this.packetContext = tmp;
  }


  /**
   *  Sets the transactionContext attribute of the TransactionItem object
   *
   * @param  tmp  The new transactionContext value
   */
  public void setTransactionContext(TransactionContext tmp) {
    this.transactionContext = tmp;
  }


  /**
   *  Sets the shareKey attribute of the TransactionItem object
   *
   * @param  tmp  The new shareKey value
   */
  public void setShareKey(boolean tmp) {
    this.shareKey = tmp;
  }


  /**
   *  Gets the errorMessage attribute of the TransactionItem object
   *
   * @return    The errorMessage value
   */
  public String getErrorMessage() {
    return (errorMessage.toString());
  }


  /**
   *  Gets the name attribute of the TransactionItem object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the ignoredProperties attribute of the TransactionItem object
   *
   * @return    The ignoredProperties value
   */
  public HashMap getIgnoredProperties() {
    return ignoredProperties;
  }


  /**
   *  Gets the object attribute of the TransactionItem object
   *
   * @return    The object value
   */
  public Object getObject() {
    return object;
  }


  /**
   *  Gets the recordList attribute of the TransactionItem object
   *
   * @return    The recordList value
   */
  public RecordList getRecordList() {
    return recordList;
  }


  /**
   *  Gets the meta attribute of the TransactionItem object
   *
   * @return    The meta value
   */
  public TransactionMeta getMeta() {
    return meta;
  }


  /**
   *  Gets the transactionContext attribute of the TransactionItem object
   *
   * @return    The transactionContext value
   */
  public TransactionContext getTransactionContext() {
    return transactionContext;
  }


  /**
   *  Gets the shareKey attribute of the TransactionItem object
   *
   * @return    The shareKey value
   */
  public boolean getShareKey() {
    return shareKey;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @param  record         Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void insertClientMapping(Connection db, Record record) throws SQLException {
    if (record != null && record.containsKey("guid")) {
      packetContext.getClientManager().insert(
          syncClientMap.getClientId(),
          syncClientMap.getTableId(),
          new Integer(record.getRecordId()),
          new Integer((String) record.get("guid")));
      syncClientMap.setRecordId(record.getRecordId());
      syncClientMap.setClientUniqueId((String) record.get("guid"));
      syncClientMap.insert(db, (String) record.get("modified"));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  modified       Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void insertClientMapping(Connection db, String modified) throws SQLException {
    packetContext.getClientManager().insert(
        syncClientMap.getClientId(),
        syncClientMap.getTableId(),
        new Integer(syncClientMap.getRecordId()),
        new Integer(syncClientMap.getClientUniqueId()));
    syncClientMap.insert(db, modified);
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  record         Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void updateClientMapping(Connection db, Record record) throws SQLException {
    if (record.containsKey("guid")) {
      syncClientMap.setRecordId(record.getRecordId());
      syncClientMap.setClientUniqueId((String) record.get("guid"));
      syncClientMap.updateStatusDate(db, (String) record.get("modified"));
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  record         Description of the Parameter
   * @throws  SQLException  Description of the Exception
   */
  public void deleteClientMapping(Connection db, Record record) throws SQLException {
    if (record.containsKey("guid")) {
      syncClientMap.setRecordId(record.getRecordId());
      syncClientMap.setClientUniqueId((String) record.get("guid"));
      syncClientMap.delete(db);
    }
    packetContext.getClientManager().remove(
        syncClientMap.getClientId(),
        syncClientMap.getTableId(),
        new Integer(record.getRecordId()));
  }


  /**
   *  Gets the objectValid attribute of the TransactionItem object
   *
   * @param  db          Description of the Parameter
   * @return             The objectValid value
   * @throws  Exception  Description of the Exception
   */
  public boolean isObjectValid(Connection db) throws Exception {
    if (action == INSERT || action == UPDATE) {
      log.debug("Validating Object");
      ObjectValidator.validate(packetContext.getSystemStatus(), db, object);
      HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
      //TODO: determine course of action when warnings exist
      //HashMap warnings = (HashMap) ObjectUtils.getObject(object, "warnings");
      if (errors != null && errors.size() > 0) {
        Iterator i = errors.keySet().iterator();
        while (i.hasNext()) {
          String errorKey = (String) i.next();
          String errorMsg = (String) errors.get(errorKey);
          appendErrorMessage(errorMsg);
          log.debug("Object Validation Error-> " + errorKey + "=" + errorMsg);
        }
        return false;
      }
    }
    return true;
  }


  /**
   *  Assumes that the Object has already been built and populated, now the
   *  specified action will be executed. A database connection is passed along
   *  since the Object will need it.<p>
   *  <p/>
   *  <p/>
   *  <p/>
   *  <p/>
   *  <p/>
   *  <p/>
   *  <p/>
   *  Data can be selected, inserted, updated, deleted, and synchronized with
   *  client systems.
   *
   * @param  db          Description of Parameter
   * @param  dbLookup    Description of Parameter
   * @throws  Exception  Description of Exception
   */
  public void execute(Connection db, Connection dbLookup) throws Exception {
    log.debug("Executing transaction");
    //Validate several requirements
    if ((object == null || name == null) && !"system".equals(name)) {
      appendErrorMessage("Unsupported object specified");
      return;
    }
    if (packetContext.getAuthenticationItem().getType() == AuthenticationItem.CENTRIC_USER) {
      //Verify if user has permission to perform the action on this object
      String permission = ObjectUtils.getParam(object, "permission");
      if (!hasPermission(permission)) {
        appendErrorMessage("User denied permission to perform action on the object");
        return;
      }
    }
    //Prepare the SyncClientMap to handle mapping client and server data
    syncClientMap = new SyncClientMap();
    syncClientMap.setClientId(
        packetContext.getAuthenticationItem().getClientId());
    syncClientMap.setTableId(
        ((SyncTable) packetContext.getObjectMap().get(name)).getId());
    //The record list will be returned to the client
    if (recordList == null) {
      recordList = new RecordList(name);
    }
    //A pagedList will allow a subset of a query to be returned if specified by client
    if (pagedListInfo != null) {
      doSetPagedListInfo();
    }
    //Begin action specific processing
    if (action == GET_DATETIME) {
      Record thisRecord = new Record("info");
      thisRecord.put(
          "dateTime", String.valueOf(
          new java.sql.Timestamp(new java.util.Date().getTime())));
      recordList.add(thisRecord);
    } else if (action == GET_SYSTEM_PREFERENCES) {
      Record thisRecord = new Record("system");
      thisRecord.put("language", packetContext.getApplicationPrefs().get("SYSTEM.LANGUAGE"));
      thisRecord.put("country", packetContext.getApplicationPrefs().get("SYSTEM.COUNTRY"));
      thisRecord.put("currency", packetContext.getApplicationPrefs().get("SYSTEM.CURRENCY"));
      thisRecord.put("timezone", packetContext.getApplicationPrefs().get("SYSTEM.TIMEZONE"));
      recordList.add(thisRecord);
    } else if (action == GET_SYSTEM_XML_FILE) {
      Record thisRecord = new Record("system");
      try {
        String systemXMLPath = packetContext.getApplicationPrefs().get("FILELIBRARY");
        systemXMLPath += packetContext.getConnectionElement().getDbName();

        File file = new File(systemXMLPath + fs + "system.xml");

        if (file.exists()) {
          StringBuffer fileData = new StringBuffer(1000);
          BufferedReader reader = new BufferedReader(
              new FileReader(file));
          char[] buf = new char[1024];
          int numRead = 0;
          while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
          }
          reader.close();

          thisRecord.put("file",
              new String(new Base64().encode(
              fileData.toString().getBytes("UTF-8"))));

          recordList.add(thisRecord);
        } else {
          appendErrorMessage("File \"system.xml\" not found!");
        }
      } catch (Exception e) {
        appendErrorMessage(e.toString());
      }
    } else if (action == SYNC_START) {
      ObjectUtils.setParam(
          object, "id", String.valueOf(
          packetContext.getAuthenticationItem().getClientId()));
      ObjectUtils.setParam(
          object, "anchor", packetContext.getAuthenticationItem().getLastAnchor());
      if (!((SyncClient) object).checkNormalSync(db)) {
        appendErrorMessage("Client and server not in sync!");
      }
    } else if (action == SYNC_PROCESSED) {
      String packageDirPath = packetContext.getApplicationPrefs().get("FILELIBRARY");
      packageDirPath += packetContext.getConnectionElement().getDbName();
      packageDirPath += fs + "sync-packages" + fs;

      ObjectUtils.setParam(
          object, "id", String.valueOf(
          packetContext.getAuthenticationItem().getClientId()));
      ((SyncClient) object).queryRecord(db,
          ObjectUtils.getParam(object, "id"));
      if (!((SyncClient) object).syncCleanup(db,
          packetContext.getAuthenticationItem().getLastAnchor(),
          packetContext.getAuthenticationItem().getNextAnchor(),
          packageDirPath)) {
        appendErrorMessage("Server could not clean up client's sync package!");
      }
    } else if (action == SYNC_END) {
      ObjectUtils.setParam(
          object, "id", String.valueOf(
          packetContext.getAuthenticationItem().getClientId()));
      ObjectUtils.setParam(
          object, "anchor", packetContext.getAuthenticationItem().getNextAnchor());
      ((SyncClient) object).updateSyncAnchor(db);
      Record thisRecord = new Record("syncEnd");
      thisRecord.put(
          "endDateTime", String.valueOf(
          new java.sql.Timestamp(new java.util.Date().getTime())));
      recordList.add(thisRecord);
    } else if (action == SYNC) {
      ObjectUtils.setParam(
          object, "lastAnchor", packetContext.getAuthenticationItem().getLastAnchor());
      ObjectUtils.setParam(
          object, "nextAnchor", packetContext.getAuthenticationItem().getNextAnchor());
      //If the client supplied any filters... set them
      setGuidParameters(db);
      //Build inserts for client
      if (packetContext.getAuthenticationItem().getNextAnchor() != null) {
        buildRecords(object, db, dbLookup, Constants.SYNC_INSERTS);
      }
      //Build updates for client
      if (packetContext.getAuthenticationItem().getLastAnchor() != null) {
        buildRecords(object, db, dbLookup, Constants.SYNC_UPDATES);
      }
    } else if (action == SYNC_DELETE) {
      //Build deletes for client
      String uniqueField = ObjectUtils.getParam(object, "uniqueField");
      String tableName = ObjectUtils.getParam(object, "tableName");
      if (uniqueField != null && tableName != null) {
        PreparedStatement pst = null;
        ResultSet rs = syncClientMap.buildSyncDeletes(
            db, pst, uniqueField, tableName, recordList);
        while (rs.next()) {
          Record thisRecord = new Record("delete");
          int cuid = rs.getInt("cuid");
          int recordId = rs.getInt("record_id");
          thisRecord.put("guid", String.valueOf(cuid));
          thisRecord.setRecordId(recordId);
          recordList.add(thisRecord);
        }
        rs.close();
        if (pst != null) {
          pst.close();
        }
      }
    } else if (action == CUSTOM_ACTION) {
      if (object instanceof CustomActionHandler) {
        //Execute a custom action: instantiate the specified object
        //Execute the object if it implements CustomActionHandler

        //public boolean process(PacketContext packetContext, Connection db, HashMap values)
        //public boolean status(PacketContext packetContext, Connection db, HashMap values)

        //Prepare the objects for execution
        Class[] paramClass = new Class[]{packetContext.getClass(), Class.forName(
            "java.sql.Connection"), ignoredProperties.getClass()};
        Object[] paramObject = new Object[]{packetContext, db, ignoredProperties};
        Method method = object.getClass().getMethod(actionMethod, paramClass);
        //Execute
        Object result = (method.invoke(object, paramObject));
        checkResult(result);
        if (result instanceof Boolean && !((Boolean) result).booleanValue()) {
          appendErrorMessage("There was an error while processing the requested action.");
        }
      } else {
        appendErrorMessage("Object does not implement CustomActionHandler and cannot be processed.");
      }
    } else {
      //This is a typical insert, update, delete, select record(s) request
      log.debug("Base request");
      //Determine the method to execute on the object
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
        //Preprocess several items
        if (action == INSERT || action == UPDATE) {
          //Populate any client GUIDs with the correct server ID
          setGuidParameters(db);
          //Populate any items from the TransactionContext
          setContextParameters();
        }
        if (action == DELETE && ignoredProperties.containsKey("guid")) {
          //Set the object's id to be deleted, based on the client guid
          this.setObjectId(db);
          syncClientMap.setRecordId(
              Integer.parseInt(ObjectUtils.getParam(object, "id")));
          syncClientMap.setClientUniqueId(
              (String) ignoredProperties.get("guid"));
        }
        if (action == UPDATE && ignoredProperties.containsKey("guid")) {
          //Set the object's id to be updated, based on the client guid
          this.setObjectId(db);
          //Retrieve the previous modified date to ensure integrity of update
          syncClientMap.setRecordId(
              Integer.parseInt(ObjectUtils.getParam(object, "id")));
          syncClientMap.setClientUniqueId(
              (String) ignoredProperties.get("guid"));
          this.setReferencedTable();
          syncClientMap.buildStatusDate(db);
          ObjectUtils.setParam(
              object, "modified", syncClientMap.getStatusDate());
        }
        //Execute the action
        Object result = doExecute(db, executeMethod);
        checkResult(result);
        log.info(executeMethod);
        if (action == INSERT) {
          //Insert the guid / id into client mapping, at this point, the object will have its
          //newly inserted id, so set the syncMap before the insert
          if (!disableSyncMap) {
            if (ignoredProperties != null && ignoredProperties.containsKey(
                "guid")) {
              syncClientMap.setRecordId(
                  Integer.parseInt(ObjectUtils.getParam(object, "id")));
              if (setReferencedTable()) {
                syncClientMap.setClientUniqueId(
                    (String) ignoredProperties.get("guid"));
              }
              //Need to log the date/time of the new record for later approval of updates
              //Reload the newly inserted object to get its insert/modified date
              Object insertedObject = ObjectUtils.constructObject(
                  object.getClass(), db, Integer.parseInt(
                  ObjectUtils.getParam(object, "id")));
              if (insertedObject == null) {
                //Might be a lookupElement
                insertedObject = ObjectUtils.constructObject(
                    object.getClass(), db, Integer.parseInt(
                    ObjectUtils.getParam(object, "id")), ObjectUtils.getParam(
                    object, "tableName"));
              }
              if (insertedObject == null) {
                //Might be a customLookupElement
                insertedObject = ObjectUtils.constructObject(
                    object.getClass(), db, Integer.parseInt(
                    ObjectUtils.getParam(object, "id")), ObjectUtils.getParam(
                    object, "tableName"), ObjectUtils.getParam(
                    object, "uniqueField"));
              }
              if (insertedObject == null) {
                log.debug("The object was inserted, but could not be reloaded: possible invalid constructor");
              }
              insertClientMapping(db,
                  ObjectUtils.getParam(insertedObject, "modified"));
            }
          }
          addRecords(object, recordList, "processed");
        } else if (action == UPDATE) {
          //If the result is an Integer == 1, then the update is successful, else a "conflict"
          //since someone else updated it first
          if (((Integer) result).intValue() == 1) {
            if (!disableSyncMap) {
              //Update the modified date in client mapping
              if (ignoredProperties != null && ignoredProperties.containsKey(
                  "guid")) {
                Object updatedObject = ObjectUtils.constructObject(
                    object.getClass(), db, Integer.parseInt(
                    ObjectUtils.getParam(object, "id")));
                syncClientMap.updateStatusDate(
                    db, ObjectUtils.getParam(updatedObject, "modified"));
              }
            }
            addRecords(object, recordList, "processed");
          } else {
            addRecords(object, recordList, "conflict");
            if (!disableSyncMap) {
              syncClientMap.insertConflict(db);
            }
          }
        } else if (action == DELETE) {
          Record thisRecord = addRecords(object, recordList, "delete");
          this.setReferencedTable();
          if (thisRecord != null &&
              syncClientMap.lookupClientId(
              packetContext.getClientManager(),
              syncClientMap.getTableId(),
              ObjectUtils.getParam(object, "id")) != -1) {
            if (!disableSyncMap) {
              this.deleteClientMapping(dbLookup, thisRecord);
            }
          } else {
            log.debug("Mapping not found for delete");
          }
        } else {
          //It wasn't an insert, update, or delete...
          addRecords(object, recordList, "select");
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
   * @return    Description of the Returned Value
   */
  public boolean hasError() {
    return (errorMessage.length() > 0);
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public boolean hasRecordList() {
    return (recordList != null);
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
   *  Sets the objectId attribute of the TransactionItem object
   *
   * @param  db             The new objectId value
   * @throws  SQLException  Description of the Exception
   */
  public void setObjectId(Connection db) throws SQLException {
    if (ignoredProperties != null && ignoredProperties.containsKey("guid")) {
      SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
          name + "List");
      if (referencedTable != null) {
        String value = (String) ignoredProperties.get("guid");
        int recordId = syncClientMap.lookupServerId(
            packetContext.getClientManager(), referencedTable.getId(), value);
        ObjectUtils.setParam(object, "id", String.valueOf(recordId));
        log.debug("Setting object id: " + recordId);
      }
    }
  }


  /**
   *  Sets the guidParameters attribute of the TransactionItem object. When a
   *  client is inserting or updating records, the server needs to retrieve the
   *  ids used by the client from the client/server mapping.
   *
   * @param  db             The new guidParameters value
   * @throws  SQLException  Description of Exception
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

            SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
                lookupField + "List");
            if (referencedTable != null) {
              if (object instanceof CustomLookupElement) {
                String field = value.substring(0, value.indexOf("="));
                value = value.substring(value.indexOf("=") + 1);
                int recordId = syncClientMap.lookupServerId(
                    packetContext.getClientManager(), referencedTable.getId(), (value.equals("-1") ? null : value));
                ObjectUtils.setParam(object, "serverMapId", field + "=" + String.valueOf(recordId));
                log.debug("Setting server parameter: " + param + " data: " + recordId);
              } else {
                int recordId = syncClientMap.lookupServerId(
                    packetContext.getClientManager(), referencedTable.getId(), value);
                ObjectUtils.setParam(object, param, String.valueOf(recordId));
                log.debug("Setting server parameter: " + param + " data: " + recordId);
              }
            }
          } else {
            SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
                param + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupServerId(
                  packetContext.getClientManager(), referencedTable.getId(), value);
              ObjectUtils.setParam(
                  object, param + "Id", String.valueOf(recordId));
              log.debug("Setting new parameter: " + param + "Id" + " data: " + recordId);
            } else {
              throw new SQLException(
                  "Sync reference does not exist, you must sync referenced tables first");
            }
          }
        }
      }
    }
  }


  /**
   *  Sets the contextParameters, these are values from other objects within the
   *  same transaction
   */
  private void setContextParameters() {
    //Go through the ignored property values and see if any need data from the context
    if (ignoredProperties != null && ignoredProperties.size() > 0) {
      Iterator ignoredList = ignoredProperties.keySet().iterator();
      while (ignoredList.hasNext()) {
        String param = (String) ignoredList.next();
        if (param != null) {
          String value = (String) ignoredProperties.get(param);
          if (value != null && value.indexOf("$C{") > -1) {
            value = (String) transactionContext.getPropertyMap().get(
                value.substring(value.indexOf("$C{") + 3, value.indexOf("}")));
            if (value != null) {
              ObjectUtils.setParam(object, param, value);
              log.debug("Setting context parameter: " + param + " data: " + value);
            }
          }
        }
      }
    }
  }


  /**
   *  Processes the object according to the executeMethod
   *
   * @param  db             Description of Parameter
   * @param  executeMethod  Description of Parameter
   * @return                Description of the Returned Value
   * @throws  Exception     Description of Exception
   */
  private Object doExecute(Connection db, String executeMethod) throws Exception {
    //Prepare the objects for execution
    Class[] dbClass = new Class[]{Class.forName("java.sql.Connection")};
    Object[] dbObject = new Object[]{db};
    Method method = object.getClass().getMethod(executeMethod, dbClass);
    //Retrieve the previous object before executing an action
    Object previousObject = null;
    if (packetContext.getObjectHookManager() != null) {
      if (action == UPDATE || action == DELETE) {
        previousObject = ObjectUtils.constructObject(
            object.getClass(), db, Integer.parseInt(
            ObjectUtils.getParam(object, "id")));
      }
    }
    //Check to see if object needs to return user records only
    if (object instanceof UserCentric) {
      if (packetContext.getUserBean() != null) {
        ((UserCentric) object).accessedBy(
            packetContext.getUserBean().getUserId());
      }
    }
    //Execute
    Object result = (method.invoke(object, dbObject));
    //Process any hooks
    if (packetContext.getObjectHookManager() != null) {
      switch (action) {
          case INSERT:
            packetContext.getObjectHookManager().process(
                packetContext, ObjectHookAction.INSERT, null, object);
            break;
          case UPDATE:
            packetContext.getObjectHookManager().process(
                packetContext, ObjectHookAction.UPDATE, previousObject, object);
            break;
          case DELETE:
            packetContext.getObjectHookManager().process(
                packetContext, ObjectHookAction.DELETE, previousObject, null);
            break;
          default:
            break;
      }
    }
    return result;
  }


  /**
   *  Processes any errors returned by the object, currently for debugging
   *
   * @param  result  Description of the Parameter
   */
  private void checkResult(Object result) {
    try {
      if (result instanceof Boolean && !((Boolean) result).booleanValue()) {
        log.info("Object failed");
        //TODO: Show error messages from object
        if (object instanceof GenericBean) {
          HashMap errors = ((GenericBean) object).getErrors();
          Iterator i = errors.keySet().iterator();
          while (i.hasNext()) {
            String errorText = (String) errors.get((String) (i.next()));
            log.info("--------> " + errorText);
          }
        }
      }
    } catch (Exception e) {
    }
  }


  /**
   *  Configures the object with a pagedList, used when a subset of objects in a
   *  list will be returned
   */
  private void doSetPagedListInfo() {
    try {
      Class[] theClass = new Class[]{pagedListInfo.getClass()};
      Object[] theObject = new Object[]{pagedListInfo};
      Method method = object.getClass().getMethod(
          "setPagedListInfo", theClass);
      method.invoke(object, theObject);
    } catch (Exception e) {
      //This class must not support the pagedListInfo
      log.debug("Object does not have setPagedListInfo method");
    }
  }


  /**
   *  Adds a feature to the Records attribute of the TransactionItem object
   *
   * @param  object      The feature to be added to the Records attribute
   * @param  db          The feature to be added to the Records attribute
   * @param  syncType    The feature to be added to the Records attribute
   * @param  dbLookup    The feature to be added to the Records attribute
   * @throws  Exception  Description of Exception
   */
  private void buildRecords(Object object, Connection db, Connection dbLookup, int syncType) throws Exception {
    Class[] dbClass = new Class[]{Class.forName("java.sql.Connection")};
    Object[] dbObject = new Object[]{db};
    String executeMethod = "prepareList";
    ObjectUtils.setParam(object, "syncType", String.valueOf(syncType));
    Method method = object.getClass().getMethod(executeMethod, dbClass);
    PreparedStatement pst = (PreparedStatement) method.invoke(object, dbObject);
    ResultSet rs = DatabaseUtils.executeQuery(db, pst, pagedListInfo);
    while (rs.next()) {
      String objectMethod = "getObject";
      Class[] rsClass = new Class[]{Class.forName("java.sql.ResultSet")};
      Object[] rsObject = new Object[]{rs};
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
        if (syncClientMap.lookupClientId(
            packetContext.getClientManager(), syncClientMap.getTableId(), ObjectUtils.getParam(
            thisObject, "id")) == -1) {
          Record thisRecord = addRecords(thisObject, recordList, recordAction);
          if (!disableSyncMap) {
            this.insertClientMapping(dbLookup, thisRecord);
          }
        }
      } else if (syncType == Constants.SYNC_UPDATES) {
        //Update the status date of the client mapping
        Record thisRecord = addRecords(thisObject, recordList, recordAction);
        if (!disableSyncMap) {
          this.updateClientMapping(dbLookup, thisRecord);
        }
      }
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    if (object instanceof java.util.Hashtable) {
      ((java.util.Hashtable) object).clear();
    } else if (object instanceof java.util.AbstractMap) {
      ((java.util.AbstractMap) object).clear();
    } else {
      ((java.util.AbstractList) object).clear();
    }
  }


  /**
   *  Adds a feature to the Records attribute of the TransactionItem object
   *
   * @param  object         The feature to be added to the Records attribute
   * @param  recordList     The feature to be added to the Records attribute
   * @param  recordAction   The feature to be added to the Records attribute
   * @return                Description of the Return Value
   * @throws  SQLException  Description of Exception
   */
  private Record addRecords(Object object, RecordList recordList, String recordAction) throws SQLException {
    if (recordList != null) {
      //Need to see if the Object is a collection of Objects, otherwise
      //just process it as a single record.
      int count = 0;
      if (object instanceof java.util.AbstractList) {
        Iterator objectItems = ((java.util.AbstractList) object).iterator();
        while (objectItems.hasNext()) {
          Object objectItem = objectItems.next();
          Record thisRecord = new Record(recordAction);
          this.addFields(thisRecord, meta, objectItem, recordList.getName());
          recordList.add(thisRecord);
          count++;
        }
      }
      if (count == 0 && !"select".equals(recordAction)) {
        Record thisRecord = new Record(recordAction);
        this.addFields(thisRecord, meta, object, recordList.getName());
        recordList.add(thisRecord);
        return thisRecord;
      }
    }
    return null;
  }


  /**
   *  Adds a feature to the Fields attribute of the TransactionItem object
   *
   * @param  thisRecord        The feature to be added to the Fields attribute
   * @param  thisMeta          The feature to be added to the Fields attribute
   * @param  thisObject        The feature to be added to the Fields attribute
   * @exception  SQLException  Description of the Exception
   */
  private void addFields(Record thisRecord, TransactionMeta thisMeta, Object thisObject) throws SQLException {
    addFields(thisRecord, thisMeta, thisObject, null);
  }


  /**
   *  Adds property names and values to the Record object, based on the supplied
   *  meta data
   *
   * @param  thisRecord     The feature to be added to the Fields attribute
   * @param  thisMeta       The feature to be added to the Fields attribute
   * @param  thisObject     The feature to be added to the Fields attribute
   * @param  recordName     The feature to be added to the Fields attribute
   * @throws  SQLException  Description of Exception
   */
  private void addFields(Record thisRecord, TransactionMeta thisMeta, Object thisObject, String recordName) throws SQLException {
    if (thisMeta != null && thisMeta.getFields() != null) {
      Iterator fields = thisMeta.getFields().iterator();
      while (fields.hasNext()) {
        String thisField = (String) fields.next();
        String thisValue = null;
        if (thisField.endsWith("Guid")) {
          String lookupField = thisField.substring(
              0, thisField.lastIndexOf("Guid"));
          String param = thisField.substring(0, thisField.lastIndexOf("Guid"));
          if (param.indexOf("^") > -1) {
            param = param.substring(param.indexOf("^") + 1);
            lookupField = thisField.substring(0, thisField.indexOf("^"));
            thisField = thisField.substring(0, thisField.indexOf("^"));
            if (param.startsWith("${")) {
              /**
                SPECIAL CASE: param attribute needs to be determined. The field value should be
                retrieved from the client-server mapping based upon the constant specified by the param
                attribute

                For eg: A custom field record has a linkItemId which needs to be mapped
                based on the constant as specified by the record's linkModuleId.
                <property lookup="${linkModuleId}">linkItemId</property>
              */
              String constant = param.substring(2, param.length() - 1);
              try {
                Class[] strClass = new Class[]{Class.forName("java.lang.String")};
                Object[] strObject = new Object[]{constant};
                String executeMethod = "getReferenceTable";
                Method method = thisObject.getClass().getMethod(executeMethod, strClass);

                param = (String) method.invoke(thisObject, strObject);
              } catch (Exception e) {
                e.printStackTrace(System.out);
                log.debug("Object Map specified a field with a special condition. Object does not have supporting method!");
              }
            }
            SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
                param + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupClientId(
                  packetContext.getClientManager(), referencedTable.getId(),
                  ObjectUtils.getParam(thisObject, lookupField));
              thisValue = String.valueOf(recordId);
            }
          } else {
            SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
                param + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupClientId(
                  packetContext.getClientManager(), referencedTable.getId(),
                  ObjectUtils.getParam(thisObject, lookupField + "Id"));
              thisValue = String.valueOf(recordId);
            }
          }
        } else {
          thisValue = ObjectUtils.getParam(thisObject, thisField);
          if (thisObject instanceof CustomLookupElement) {
            thisValue = ((CustomLookupElement) thisObject).getValue(thisField);
          }
          if (thisField.indexOf(".guid") > -1) {
            //This is a sub-object, so get the correct guid for the client
            String lookupField = thisField.substring(
                0, thisField.indexOf(".guid"));
            SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
                lookupField + "List");
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupClientId(
                  packetContext.getClientManager(), referencedTable.getId(), thisValue);
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
          if ("processed".equals(thisRecord.getAction())) {
            thisRecord.put("guid", ignoredProperties.get("guid"));
          } else if ("insert".equals(thisRecord.getAction())) {
            thisRecord.put("guid", String.valueOf(identity++));
          } else if ("update".equals(thisRecord.getAction())) {
            //Sending an update back to client, get the correct guid
            thisRecord.put(
                "guid", syncClientMap.lookupClientId(
                packetContext.getClientManager(), syncClientMap.getTableId(), ObjectUtils.getParam(
                thisObject, "id")));
          } else if ("delete".equals(thisRecord.getAction())) {
            //Let the client know that its record was deleted
            thisRecord.put("guid", ignoredProperties.get("guid"));
          } else if ("conflict".equals(thisRecord.getAction())) {
            thisRecord.put("guid", ignoredProperties.get("guid"));
          } else if ("select".equals(thisRecord.getAction())) {
            //client needs to know the cuid on the client system
            SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
                (recordName.endsWith("List") ? recordName : recordName + "List"));
            if (referencedTable != null) {
              int recordId = syncClientMap.lookupClientId(
                  packetContext.getClientManager(), referencedTable.getId(),
                  ObjectUtils.getParam(thisObject, "id"));
              thisRecord.put("guid", String.valueOf(recordId));
            }
          }
        }
      } catch (java.lang.NumberFormatException e) {
        //This object doesn't have an id, might have multiple keys
      }
    }
  }


  /**
   *  Sets the referencedTable attribute of the TransactionItem object
   *
   * @return    Description of the Return Value
   */
  public boolean setReferencedTable() {
    //The client requested an object, but the mapping is stored as the objectList
    if (!name.endsWith("List")) {
      SyncTable referencedTable = (SyncTable) packetContext.getObjectMap().get(
          name + "List");
      if (referencedTable != null) {
        syncClientMap.setTableId(referencedTable.getId());
        return true;
      }
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @param  permission  Description of the Parameter
   * @return             Description of the Return Value
   */
  protected boolean hasPermission(String permission) {
    if (permission != null && !"".equals(permission.trim())) {
      if (action == SELECT) {
        permission += "-view";
      } else if (action == INSERT) {
        permission += "-add";
      } else if (action == UPDATE) {
        permission += "-edit";
      } else if (action == DELETE) {
        permission += "-delete";
      }

      if (packetContext.getSystemStatus() != null && packetContext.getUserBean() != null) {
        return (packetContext.getSystemStatus().hasPermission(
            packetContext.getUserBean().getUserId(), permission));
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public boolean allowed() {
    if ((object instanceof org.aspcfs.modules.service.base.SyncClient) ||
        (object instanceof org.aspcfs.modules.service.actions.CompileSyncData)) {
      return true;
    }

    return false;
  }
}

