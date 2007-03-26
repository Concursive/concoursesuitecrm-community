package org.aspcfs.apps.transfer;

import org.aspcfs.modules.service.base.Record;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *  A record to be created by a DataReader and saved by a DataWriter, contains
 *  DataField objects.
 *
 * @author     matt rajkowski
 * @created    September 3, 2002
 * @version    $Id: DataRecord.java 16807 2006-10-30 14:42:47Z
 *      andrei.holub@corratech.com $
 */
public class DataRecord extends ArrayList {

  //Requested object actions
  public final static String INSERT = "insert";
  public final static String SELECT = "select";
  public final static String UPDATE = "update";
  public final static String DELETE = "delete";
  public final static String SYNC = "sync";
  public final static String SYNC_DELETE = "syncDelete";
  public final static String GET_DATETIME = "getDateTime";
  public final static String GET_SYSTEM_PREFERENCES = "getSystemPreferences";
  public final static String GET_SYSTEM_XML_FILE = "getSystemXMLFile";

  protected String name = null;
  protected String action = INSERT;
  protected boolean shareKey = false;
  protected String identity = null;
  protected String offset = null;
  protected String items = null;


  /**
   *  Gets the name attribute of the DataRecord object
   *
   * @return    The name value
   */
  public String getName() {
    return this.name;
  }


  /**
   *  Sets the name attribute of the DataRecord object
   *
   * @param  name  The new name value
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   *  Sets the action attribute of the DataRecord object
   *
   * @param  tmp  The new action value
   */
  public void setAction(String tmp) {
    this.action = tmp;
  }


  /**
   *  Gets the action attribute of the DataRecord object
   *
   * @return    The action value
   */
  public String getAction() {
    return action;
  }


  /**
   *  Gets the shareKey attribute of the DataRecord object
   *
   * @return    The shareKey value
   */
  public boolean getShareKey() {
    return shareKey;
  }


  /**
   *  Sets the shareKey attribute of the DataRecord object
   *
   * @param  shareKey  The new shareKey value
   */
  public void setShareKey(boolean shareKey) {
    this.shareKey = shareKey;
  }


  /**
   *  Sets the identity attribute of the DataRecord object
   *
   * @param  tmp  The new identity value
   */
  public void setIdentity(String tmp) {
    this.identity = tmp;
  }


  /**
   *  Sets the offset attribute of the DataRecord object
   *
   * @param  tmp  The new offset value
   */
  public void setOffset(String tmp) {
    this.offset = tmp;
  }


  /**
   *  Sets the items attribute of the DataRecord object
   *
   * @param  tmp  The new items value
   */
  public void setItems(String tmp) {
    this.items = tmp;
  }


  /**
   *  Gets the identity attribute of the DataRecord object
   *
   * @return    The identity value
   */
  public String getIdentity() {
    return identity;
  }


  /**
   *  Gets the offset attribute of the DataRecord object
   *
   * @return    The offset value
   */
  public String getOffset() {
    return offset;
  }


  /**
   *  Gets the items attribute of the DataRecord object
   *
   * @return    The items value
   */
  public String getItems() {
    return items;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasIdentity() {
    return hasText(identity);
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasItems() {
    return hasText(items);
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasOffset() {
    return hasText(offset);
  }


  /**
   *  Description of the Method
   *
   * @param  input  Description of the Parameter
   * @return        Description of the Return Value
   */
  public boolean hasText(String input) {
    return (input != null && !"".equals(input.trim()));
  }


  /**
   *  Adds a feature to the Field attribute of the DataRecord object
   *
   * @param  thisName   The feature to be added to the Field attribute
   * @param  thisValue  The feature to be added to the Field attribute
   */
  public void addField(String thisName, String thisValue) {
    this.add(new DataField(thisName, thisValue));
  }


  /**
   *  Adds a feature to the Field attribute of the DataRecord object
   *
   * @param  thisName   The feature to be added to the Field attribute
   * @param  thisValue  The feature to be added to the Field attribute
   */
  public void addField(String thisName, int thisValue) {
    this.addField(thisName, String.valueOf(thisValue));
  }


  /**
   *  Adds a feature to the Field attribute of the DataRecord object
   *
   * @param  thisName   The feature to be added to the Field attribute
   * @param  thisValue  The feature to be added to the Field attribute
   */
  public void addField(String thisName, double thisValue) {
    this.addField(thisName, String.valueOf(thisValue));
  }


  /**
   *  Adds a feature to the Field attribute of the DataRecord object
   *
   * @param  thisName   The feature to be added to the Field attribute
   * @param  thisValue  The feature to be added to the Field attribute
   */
  public void addField(String thisName, boolean thisValue) {
    this.addField(thisName, String.valueOf(thisValue));
  }


  /**
   *  Adds a feature to the Field attribute of the DataRecord object
   *
   * @param  thisName   The feature to be added to the Field attribute
   * @param  thisValue  The feature to be added to the Field attribute
   */
  public void addField(String thisName, java.sql.Timestamp thisValue) {
    this.addField(thisName, thisValue.toString());
  }


  /**
   *  Adds a feature to the Field attribute of the DataRecord object
   *
   * @param  thisName         The feature to be added to the Field attribute
   * @param  thisValue        The feature to be added to the Field attribute
   * @param  thisLookupValue  The feature to be added to the Field attribute
   * @param  thisAlias        The feature to be added to the Field attribute
   */
  public void addField(String thisName, String thisValue, String thisLookupValue, String thisAlias) {
    this.add(new DataField(thisName, thisValue, thisLookupValue, thisAlias));
  }


  /**
   *  Adds a feature to the Field attribute of the DataRecord object
   *
   * @param  thisName         The feature to be added to the Field attribute
   * @param  thisValue        The feature to be added to the Field attribute
   * @param  thisLookupValue  The feature to be added to the Field attribute
   * @param  thisAlias        The feature to be added to the Field attribute
   */
  public void addField(String thisName, int thisValue, String thisLookupValue, String thisAlias) {
    this.add(
        new DataField(
        thisName, String.valueOf(thisValue), thisLookupValue, thisAlias));
  }


  /**
   *  Description of the Method
   *
   * @param  fieldName  Description of the Parameter
   * @return            Description of the Return Value
   */
  public boolean removeField(String fieldName) {
    Iterator fields = this.iterator();
    while (fields.hasNext()) {
      DataField thisField = (DataField) fields.next();
      if (fieldName.equals(thisField.getName())) {
        fields.remove();
        return true;
      }
    }
    return false;
  }


  /**
   *  Gets the value attribute of the DataRecord object
   *
   * @param  fieldName  Description of the Parameter
   * @return            The value value
   */
  public String getValue(String fieldName) {
    Iterator fields = this.iterator();
    while (fields.hasNext()) {
      DataField thisField = (DataField) fields.next();
      if (fieldName.equals(thisField.getName())) {
        return thisField.getValue();
      }
    }
    return null;
  }


  /**
   *  Gets the intValue attribute of the DataRecord object
   *
   * @param  fieldName  Description of the Parameter
   * @return            The intValue value
   */
  public int getIntValue(String fieldName) {
    String value = this.getValue(fieldName);
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return -1;
    }
  }


  /**
   *  Constructor for the DataRecord object
   */
  public DataRecord() { }


  /**
   *  Constructor for the DataRecord object
   *
   * @param  record      Description of the Parameter
   * @param  recordName  Description of the Parameter
   */
  public DataRecord(String recordName, Record record) {
    this.name = recordName;
    this.action = record.getAction();
    Iterator fields = record.keySet().iterator();
    while (fields.hasNext()) {
      String fieldName = (String) fields.next();
      String fieldValue = (String) record.get(fieldName);
      
      /*
      if ("guid".equals(fieldName)) {
        //online sync record
        this.addField("id", fieldValue);
      } else if ("id".equals(fieldName)) {
        //offline sync record.
        this.addField("id", fieldValue, null, "guid");
      } else {
        this.addField(fieldName,
               (fieldValue == null ? "" : fieldValue));
      }*/
      this.addField(fieldName,
               (fieldValue == null ? "" : fieldValue));
    }
  }
}

