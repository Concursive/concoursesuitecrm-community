package org.aspcfs.apps.transfer;

import java.util.logging.Logger;

/**
 * A field of a record
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 3, 2002
 */
public class DataField {
  public static Logger logger = Logger.getLogger(Transfer.class.getName());

  protected String name = null;
  protected String alias = null;
  protected String value = null;
  protected String valueLookup = null;


  /**
   * Constructor for the DataField object
   */
  public DataField() {
  }


  /**
   * Constructor for the DataField object
   *
   * @param thisName  Description of the Parameter
   * @param thisValue Description of the Parameter
   */
  public DataField(String thisName, String thisValue) {
    this.name = thisName;
    this.setValue(thisValue);
  }


  /**
   * Constructor for the DataField object
   *
   * @param thisName        Description of the Parameter
   * @param thisValue       Description of the Parameter
   * @param thisValueLookup Description of the Parameter
   * @param thisAlias       Description of the Parameter
   */
  public DataField(String thisName, String thisValue, String thisValueLookup, String thisAlias) {
    this.name = thisName;
    this.setValue(thisValue);
    this.valueLookup = thisValueLookup;
    this.alias = thisAlias;
  }


  /**
   * Sets the name attribute of the DataField object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the alias attribute of the DataField object
   *
   * @param tmp The new alias value
   */
  public void setAlias(String tmp) {
    this.alias = tmp;
  }


  /**
   * Sets the value attribute of the DataField object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    if (tmp != null) {
      this.value = tmp.trim();
    } else {
      this.value = "";
    }
  }


  /**
   * Sets the valueLookup attribute of the DataField object
   *
   * @param tmp The new valueLookup value
   */
  public void setValueLookup(String tmp) {
    this.valueLookup = tmp;
  }


  /**
   * Gets the name attribute of the DataField object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the alias attribute of the DataField object
   *
   * @return The alias value
   */
  public String getAlias() {
    return alias;
  }


  /**
   * Gets the value attribute of the DataField object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }


  /**
   * Gets the valueLookup attribute of the DataField object
   *
   * @return The valueLookup value
   */
  public String getValueLookup() {
    return valueLookup;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasValue() {
    return (value != null && !"".equals(value));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasValueLookup() {
    return (valueLookup != null && !"".equals(valueLookup));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasAlias() {
    return (alias != null && !"".equals(alias));
  }
}

