/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.apps.transfer.reader;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataField;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.Property;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMap;
import org.aspcfs.apps.transfer.reader.cfsdatabasereader.PropertyMapList;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.service.base.PacketContext;
import org.aspcfs.modules.service.base.Record;
import org.aspcfs.modules.service.base.RecordList;
import org.aspcfs.modules.service.base.SyncClientManager;
import org.aspcfs.modules.service.base.SyncTable;
import org.aspcfs.modules.service.base.SyncTableList;
import org.aspcfs.modules.service.base.Transaction;
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.*;

/**
 *  Reads Data from the database as XML. It uses Centric's Transaction API to
 *  read data from the db. Centric Offline application loads this class to
 *  perform as a Sync Reader. When reading sync records the reader can be
 *  configured to disable storing of client mapping information
 *
 * @author     Ananth
 * @created    November 9, 2006
 * @version
 */
public class CFSXMLDatabaseReader implements DataReader {
  protected ArrayList dataRecords = new ArrayList();
  private Connection connection = null;
  private Connection connectionLookup = null;
  private int clientId = -1;
  private String processConfigFile = null;
  private ArrayList modules = null;
  private PropertyMapList mappings = null;
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int recipient = SyncPackage.SYNC_CLIENT;

  private PacketContext packetContext = new PacketContext();

  private final static Logger log = Logger.getLogger(org.aspcfs.apps.transfer.reader.CFSXMLDatabaseReader.class);


  /**
   *  Gets the recipient attribute of the CFSXMLDatabaseReader object
   *
   * @return    The recipient value
   */
  public int getRecipient() {
    return recipient;
  }


  /**
   *  Sets the recipient attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(int tmp) {
    this.recipient = tmp;
  }


  /**
   *  Sets the recipient attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(String tmp) {
    this.recipient = Integer.parseInt(tmp);
  }


  /**
   *  Gets the lastAnchor attribute of the CFSXMLDatabaseReader object
   *
   * @return    The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Sets the lastAnchor attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the nextAnchor attribute of the CFSXMLDatabaseReader object
   *
   * @return    The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Sets the nextAnchor attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the processConfigFile attribute of the CFSXMLDatabaseReader object
   *
   * @return    The processConfigFile value
   */
  public String getProcessConfigFile() {
    return processConfigFile;
  }


  /**
   *  Sets the processConfigFile attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new processConfigFile value
   */
  public void setProcessConfigFile(String tmp) {
    this.processConfigFile = tmp;
  }


  /**
   *  Gets the connection attribute of the CFSXMLDatabaseReader object
   *
   * @return    The connection value
   */
  public Connection getConnection() {
    return connection;
  }


  /**
   *  Sets the connection attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new connection value
   */
  public void setConnection(Connection tmp) {
    this.connection = tmp;
  }


  /**
   *  Gets the connectionLookup attribute of the CFSXMLDatabaseReader object
   *
   * @return    The connectionLookup value
   */
  public Connection getConnectionLookup() {
    return connectionLookup;
  }


  /**
   *  Sets the connectionLookup attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new connectionLookup value
   */
  public void setConnectionLookup(Connection tmp) {
    this.connectionLookup = tmp;
  }


  /**
   *  Gets the clientId attribute of the CFSXMLDatabaseReader object
   *
   * @return    The clientId value
   */
  public int getClientId() {
    return clientId;
  }


  /**
   *  Sets the clientId attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new clientId value
   */
  public void setClientId(int tmp) {
    this.clientId = tmp;
  }


  /**
   *  Sets the clientId attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new clientId value
   */
  public void setClientId(String tmp) {
    this.clientId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the dataRecords attribute of the CFSXMLDatabaseReader object
   *
   * @return    The dataRecords value
   */
  public ArrayList getDataRecords() {
    return dataRecords;
  }


  /**
   *  Sets the dataRecords attribute of the CFSXMLDatabaseReader object
   *
   * @param  tmp  The new dataRecords value
   */
  public void setDataRecords(ArrayList tmp) {
    this.dataRecords = tmp;
  }


  /**
   *  Gets the version attribute of the CFSXMLDatabaseReader object
   *
   * @return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the CFSXMLDatabaseReader object
   *
   * @return    The name value
   */
  public String getName() {
    return "XML Database Reader";
  }


  /**
   *  Gets the description attribute of the CFSXMLDatabaseReader object
   *
   * @return    The description value
   */
  public String getDescription() {
    return "Reads data from the database as XML";
  }


  /**
   *  Gets the configured attribute of the CFSXMLDatabaseReader object
   *
   * @return    The configured value
   */
  public boolean isConfigured() {
    if (dataRecords.size() == 0) {
      return false;
    }

    modules = new ArrayList();
    try {
      mappings = new PropertyMapList(processConfigFile, modules);
    } catch (Exception e) {
      log.error(e.toString());
      return false;
    }
    return true;
  }


  /**
   *  Gets the objectMap attribute of the CFSXMLDatabaseReader object
   *
   * @param  db    Description of the Parameter
   * @param  auth  Description of the Parameter
   * @return       The objectMap value
   */
  private HashMap getObjectMap(Connection db, AuthenticationItem auth) {
    //TODO: should be cached?
    SyncTableList systemObjectMap = new SyncTableList();
    systemObjectMap.setBuildTextFields(false);
    try {
      systemObjectMap.buildList(db);
    } catch (SQLException e) {
      e.printStackTrace(System.out);
    }

    return systemObjectMap.getObjectMapping(auth.getSystemId());
  }


  /**
   *  Description of the Method
   */
  public void initialize() {
    AuthenticationItem auth = new AuthenticationItem();
    auth.setClientId(clientId);
    auth.setSystemId(4);
    auth.setLastAnchor(lastAnchor);
    auth.setNextAnchor(nextAnchor);
    packetContext.setAuthenticationItem(auth);

    try {
      //Prepare the SyncClientManager
      SyncClientManager clientManager = new SyncClientManager();
      clientManager.addClient(connection, auth.getClientId());
      packetContext.setClientManager(clientManager);
    } catch (SQLException e) {
      e.printStackTrace(System.out);
    }
    
    //Disable sync client server mapping if the sync reader is preparing a package for the server
    if (recipient == SyncPackage.SYNC_SERVER) {
      packetContext.setDisableSyncMap(true);
    }

    //Prepare the objectMap: The allowable objects that can be processed for the given systemId
    HashMap objectMap = this.getObjectMap(connection, auth);
    packetContext.setObjectMap(objectMap);
  }


  /**
   *  Description of the Method
   *
   * @param  writer  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    boolean processOK = true;
    long milies = System.currentTimeMillis();
    log.info("[Begin] XMLDatabase reader.");
    try {
      Iterator i = dataRecords.iterator();
      while (i.hasNext()) {
        DataRecord dataRecord = (DataRecord) i.next();

        Transaction transaction = new Transaction();
        transaction.setPacketContext(packetContext);
        transaction.setValidateObject(false);

        SyncTable metaMapping = new SyncTable();
        metaMapping.setName("meta");
        metaMapping.setMappedClassName(
            "org.aspcfs.modules.service.base.TransactionMeta");
        transaction.addMapping("meta", metaMapping);

        ArrayList<DataRecord> items = new ArrayList<DataRecord>();
        DataRecord meta = getMetaInfo(dataRecord);
        if (meta != null) {
          //continue only if meta information is available
          items.add(meta);
          log.debug("ADDING: " + dataRecord.getName());
          items.add(dataRecord);
          transaction.build(items);

          transaction.execute(connection, connectionLookup);

          if (transaction.hasError()) {
            log.error("ERROR: " + transaction.getErrorMessage());
            break;
          }

          //Process sync records received from the source
          RecordList recordList = transaction.getRecordList();
          Iterator records = recordList.iterator();
          while (records.hasNext()) {
            Record record = (Record) records.next();

            String recordsName = recordList.getName();
            DataRecord dataRec = new DataRecord(
                recordsName.substring(0,
                recordsName.indexOf("List")), record);

            //Based on the sync records recipient the sync record needs to be processed
            processRecord(dataRec);

            processOK = writer.save(dataRec);
            if (!processOK) {
              break;
            }
          }
        }
      }
    } catch (Exception e) {
      log.error(e.toString());
      e.printStackTrace(System.out);
      return false;
    } finally {
      log.info("[End] XMLDatabase reader.");
      log.info("Time: "+ (System.currentTimeMillis() - milies) + " ms.");
    }
    return processOK;
  }


  /**
   *  Gets the metaInfo attribute of the CFSXMLDatabaseReader object
   *
   * @param  dataRecord  Description of the Parameter
   * @return             The metaInfo value
   */
  protected DataRecord getMetaInfo(DataRecord dataRecord) {
    //NOTE: All tables that can be synced, must end with a "List"
    String tableName = dataRecord.getName();
    String objectName = tableName.substring(0, tableName.indexOf("List"));

    SyncTable syncTable = (SyncTable) packetContext.getObjectMap().get(objectName);
    if (syncTable != null && syncTable.getId() != -1) {
      DataRecord meta = new DataRecord();
      meta.setName("meta");

      if ("org.aspcfs.utils.web.LookupElement".equals(syncTable.getMappedClassName())) {
        PropertyMap thisMap = (PropertyMap) mappings.getMap(syncTable.getName());

        dataRecord.addField("tableName", thisMap.getTable());

        if (recipient == SyncPackage.SYNC_CLIENT) {
          meta.addField("property", "guid");
        } else if (recipient == SyncPackage.SYNC_SERVER) {
          meta.addField("property", "code");
        }
        meta.addField("property", "tableName");
        meta.addField("property", "description");
        meta.addField("property", "defaultItem");
        meta.addField("property", "level");
        meta.addField("property", "enabled");
        meta.addField("property", "entered");
        meta.addField("property", "modified");

      } else if ("org.aspcfs.utils.web.CustomLookupElement".equals(syncTable.getMappedClassName())) {
        PropertyMap thisMap = (PropertyMap) mappings.getMap(syncTable.getName());

        dataRecord.addField("tableName", thisMap.getTable());
        dataRecord.addField("uniqueField", thisMap.getUniqueField());

        Iterator properties = thisMap.iterator();
        while (properties.hasNext()) {
          Property thisProperty = (Property) properties.next();

          if (recipient == SyncPackage.SYNC_CLIENT) {
            if (thisProperty.hasAlias() && "guid".equals(thisProperty.getAlias())) {
              meta.addField("property", "guid");
            } else {
              meta.addField("property", thisProperty.getField());
            }
          } else if (recipient == SyncPackage.SYNC_SERVER) {
            meta.addField("property", thisProperty.getField());
          }
          dataRecord.addField("property", thisProperty.getField());
        }
      } else {
        PropertyMap thisMap = (PropertyMap) mappings.get(
            syncTable.getMappedClassName());

        if (thisMap != null) {
          Iterator properties = thisMap.iterator();
          while (properties.hasNext()) {
            Property thisProperty = (Property) properties.next();
            //API expects the <meta> section to have a property whose value will be parsed to determine
            //client mappings. Process property value and prepare meta <property>
            String property = "";
            if (recipient == SyncPackage.SYNC_CLIENT) {
              if (thisProperty.hasAlias()) {
                property = thisProperty.getAlias();
              } else if (thisProperty.hasLookupValue()) {
                property = thisProperty.getName() + "^" + thisProperty.getLookupValue() + "Guid";
              } else {
                property = thisProperty.getName();
              }
            } else if (recipient == SyncPackage.SYNC_SERVER) {
              property = thisProperty.getName();
            }
            meta.addField("property", property);
          }
        }
      }
      return meta;
    }
    return null;
  }

  /**
   *  Raw data exchanged between client and server should handle some of the
   *  operations that are triggered while using CRM in a web browser, which
   *  would cause the processing to fail or put the database in an invalid
   *  state. For eg: When a user record is being restored, the application
   *  should NOT add a contact record by default.
   *
   * @param  dataRecord  Description of the Parameter
   */
  protected void processRecord(DataRecord dataRecord) {
    if ("user".equals(dataRecord.getName())) {
      dataRecord.addField("addContact", "false");
    }

    if ("account".equals(dataRecord.getName())) {
      dataRecord.addField("insertPrimaryContact", "false");
    }

    if ("customFieldCategory".equals(dataRecord.getName())
         && (DataRecord.INSERT).equals(dataRecord.getAction())) {
      dataRecord.setAction("insertCategory");
    }

    if ("customFieldGroup".equals(dataRecord.getName())
         && (DataRecord.INSERT).equals(dataRecord.getAction())) {
      dataRecord.setAction("insertGroup");
    }

    if ("customField".equals(dataRecord.getName())
         && (DataRecord.INSERT).equals(dataRecord.getAction())) {
      dataRecord.setAction("insertField");
    }

    if ("fileItem".equals(dataRecord.getName())) {
      dataRecord.addField("doVersionInsert", "false");
      dataRecord.addField("doLogUploadInsert", "false");
    }

    if ("opportunityComponent".equals(dataRecord.getName())
         && (DataRecord.INSERT).equals(dataRecord.getAction())) {
      //dataRecord.addField("insertLog", "false");
      //dataRecord.addField("resetType", "false");
    }

    if ((DataRecord.SELECT).equals(dataRecord.getAction())) {
      //When a offline client sends the server a SELECT request and the server sent records in response to a SELECT.
      //this record must be saved to the file and later processed and stored by the client.
      dataRecord.setAction(DataRecord.UPDATE);
    }

    SyncTable syncTable = (SyncTable) packetContext.getObjectMap().get(dataRecord.getName());
    PropertyMap thisMap = (PropertyMap) mappings.getMap(syncTable.getName());

    if ("org.aspcfs.utils.web.CustomLookupElement".equals(syncTable.getMappedClassName())) {
      ArrayList dataFields = new ArrayList(dataRecord);
      dataRecord.clear();

      dataRecord.addField("tableName", thisMap.getTable());
      if (thisMap.getUniqueField() != null) {
        dataRecord.addField("uniqueField", thisMap.getUniqueField());
      }

      Iterator i = dataFields.iterator();
      while (i.hasNext()) {
        DataField thisField = (DataField) i.next();
        String fieldName = thisField.getName();
        String value = thisField.getValue();
        int type = this.getType(thisMap, fieldName);
        
        if (recipient == SyncPackage.SYNC_CLIENT) {
          if ("guid".equals(fieldName)) {
            dataRecord.addField("id", value);
          } else {
            dataRecord.addField("field", fieldName);
            dataRecord.addField("data", value);
            dataRecord.addField("type", String.valueOf(type));
          }
        } else if (recipient == SyncPackage.SYNC_SERVER) {
          Property thisProperty = this.getProperty(thisMap, fieldName);
          if (thisProperty != null) {
            if (thisProperty.hasAlias() && "guid".equals(thisProperty.getAlias())) {
              dataRecord.addField(fieldName, value, null, "guid");
            } else {
              dataRecord.addField("field", fieldName);
              dataRecord.addField("data",
                  (fieldName + "=" + (value != null ? value : "-1")), thisProperty.getLookupValue(), thisProperty.getAlias());
              dataRecord.addField("type", String.valueOf(type));
            }
          }
        }
      }
    } else if ("org.aspcfs.utils.web.LookupElement".equals(syncTable.getMappedClassName())) {
      Iterator i = dataRecord.iterator();
      while (i.hasNext()) {
        DataField thisField = (DataField) i.next();
        String fieldName = thisField.getName();
        String value = thisField.getValue();

        if (recipient == SyncPackage.SYNC_CLIENT) {
          if ("guid".equals(fieldName)) {
            thisField.setName("code");
          }
        } else if (recipient == SyncPackage.SYNC_SERVER) {
          Property thisProperty = this.getProperty(thisMap, fieldName);
          if (thisProperty != null) {
            if (thisProperty.hasAlias() && "guid".equals(thisProperty.getAlias())) {
              thisField.setAlias("guid");
            }
          }
        }
      }
    } else {
      //Base object
      Iterator i = dataRecord.iterator();
      while (i.hasNext()) {
        DataField thisField = (DataField) i.next();
        String fieldName = thisField.getName();
        
        if (recipient == SyncPackage.SYNC_CLIENT) {
          if ("guid".equals(fieldName)) {
            thisField.setName("id");
          }
        } else if (recipient == SyncPackage.SYNC_SERVER) {
          Property thisProperty = this.getProperty(thisMap, fieldName);
          if (thisProperty != null) {
            //If the recipient is a sync server then provide the lookup attribute values that will assist
            //sync xml api to store server-client mappings
            if (thisProperty.hasAlias() && "guid".equals(thisProperty.getAlias())) {
              thisField.setAlias("guid");
            }
            if (thisProperty.hasLookupValue() && thisField.hasValue()) {
              if (!"-1".equals(thisField.getValue())) {
                thisField.setValueLookup(thisProperty.getLookupValue());
              }
            }
          }
        }
      }

      //set the offline flag to true
      dataRecord.addField("offline", "true");
    }
  }


  /**
   *  Gets the type attribute of the CFSXMLDatabaseReader object
   *
   * @param  thisMap    Description of the Parameter
   * @param  fieldName  Description of the Parameter
   * @return            The type value
   */
  private int getType(PropertyMap thisMap, String fieldName) {
    if ("id".equals(fieldName)) {
      return java.sql.Types.INTEGER;
    }

    Property thisProperty = this.getProperty(thisMap, fieldName);
    System.out.println("thisProperty: " + thisProperty);
    if (thisProperty != null) {
      if ("string".equals(thisProperty.getType()) || "char".equals(thisProperty.getType())) {
        return java.sql.Types.VARCHAR;
      } else if ("int".equals(thisProperty.getType())) {
        return java.sql.Types.INTEGER;
      } else if ("boolean".equals(thisProperty.getType())) {
        return java.sql.Types.BOOLEAN;
      } else if ("timestamp".equals(thisProperty.getType())) {
        return java.sql.Types.TIMESTAMP;
      }
    }
    return -1;
  }


  /**
   *  Gets the property attribute of the CFSXMLDatabaseReader object
   *
   * @param  thisMap    Description of the Parameter
   * @param  fieldName  Description of the Parameter
   * @return            The property value
   */
  private Property getProperty(PropertyMap thisMap, String fieldName) {
    Iterator properties = thisMap.iterator();
    while (properties.hasNext()) {
      Property thisProperty = (Property) properties.next();
      if (fieldName.equals(thisProperty.getName()) ||
          fieldName.equals(thisProperty.getField())) {
        return thisProperty;
      }
    }
    return null;
  }
}

