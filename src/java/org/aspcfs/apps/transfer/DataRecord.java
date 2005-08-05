package org.aspcfs.apps.transfer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A record to be created by a DataReader and saved by a DataWriter, contains
 * DataField objects.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 3, 2002
 */
public class DataRecord extends ArrayList {

  //Requested object actions
  public final static String INSERT = "insert";
  public final static String SELECT = "select";
  public final static String UPDATE = "update";
  public final static String DELETE = "delete";
  public final static String GET_DATETIME = "getDateTime";

  protected String name = null;
  protected String action = INSERT;
  protected boolean shareKey = false;

  /**
   * Gets the name attribute of the DataRecord object
   *
   * @return The name value
   */
  public String getName() {
    return this.name;
  }


  /**
   * Sets the name attribute of the DataRecord object
   *
   * @param name The new name value
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * Sets the action attribute of the DataRecord object
   *
   * @param tmp The new action value
   */
  public void setAction(String tmp) {
    this.action = tmp;
  }


  /**
   * Gets the action attribute of the DataRecord object
   *
   * @return The action value
   */
  public String getAction() {
    return action;
  }

  public boolean getShareKey() {
    return shareKey;
  }

  public void setShareKey(boolean shareKey) {
    this.shareKey = shareKey;
  }

  /**
   * Adds a feature to the Field attribute of the DataRecord object
   *
   * @param thisName  The feature to be added to the Field attribute
   * @param thisValue The feature to be added to the Field attribute
   */
  public void addField(String thisName, String thisValue) {
    this.add(new DataField(thisName, thisValue));
  }


  /**
   * Adds a feature to the Field attribute of the DataRecord object
   *
   * @param thisName  The feature to be added to the Field attribute
   * @param thisValue The feature to be added to the Field attribute
   */
  public void addField(String thisName, int thisValue) {
    this.addField(thisName, String.valueOf(thisValue));
  }


  /**
   * Adds a feature to the Field attribute of the DataRecord object
   *
   * @param thisName  The feature to be added to the Field attribute
   * @param thisValue The feature to be added to the Field attribute
   */
  public void addField(String thisName, double thisValue) {
    this.addField(thisName, String.valueOf(thisValue));
  }


  /**
   * Adds a feature to the Field attribute of the DataRecord object
   *
   * @param thisName        The feature to be added to the Field attribute
   * @param thisValue       The feature to be added to the Field attribute
   * @param thisLookupValue The feature to be added to the Field attribute
   * @param thisAlias       The feature to be added to the Field attribute
   */
  public void addField(String thisName, String thisValue, String thisLookupValue, String thisAlias) {
    this.add(new DataField(thisName, thisValue, thisLookupValue, thisAlias));
  }

  public void addField(String thisName, int thisValue, String thisLookupValue, String thisAlias) {
    this.add(
        new DataField(
            thisName, String.valueOf(thisValue), thisLookupValue, thisAlias));
  }


  /**
   * Description of the Method
   *
   * @param fieldName Description of the Parameter
   * @return Description of the Return Value
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
   * Gets the value attribute of the DataRecord object
   *
   * @param fieldName Description of the Parameter
   * @return The value value
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
   * Gets the intValue attribute of the DataRecord object
   *
   * @param fieldName Description of the Parameter
   * @return The intValue value
   */
  public int getIntValue(String fieldName) {
    String value = this.getValue(fieldName);
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return -1;
    }
  }

}

