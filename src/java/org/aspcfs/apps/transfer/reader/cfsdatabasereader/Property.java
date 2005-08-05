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
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

/**
 * Represents a definition for extracting a property from an object for
 * translating to XML
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 18, 2002
 */
public class Property {

  private String name = null;
  private String lookupValue = null;
  private String alias = null;
  private String field = null;
  private String value = null;


  /**
   * Constructor for the Property object
   */
  public Property() {
  }


  /**
   * Constructor for the Property object
   *
   * @param name Description of the Parameter
   */
  public Property(String name) {
    this.name = name;
  }


  /**
   * Sets the name attribute of the Property object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the lookupValue attribute of the Property object
   *
   * @param tmp The new lookupValue value
   */
  public void setLookupValue(String tmp) {
    this.lookupValue = tmp;
  }


  /**
   * Sets the alias attribute of the Property object
   *
   * @param tmp The new alias value
   */
  public void setAlias(String tmp) {
    this.alias = tmp;
  }


  /**
   * Sets the field attribute of the Property object
   *
   * @param tmp The new field value
   */
  public void setField(String tmp) {
    this.field = tmp;
  }


  /**
   * Sets the value attribute of the Property object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   * Gets the name attribute of the Property object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the lookupValue attribute of the Property object
   *
   * @return The lookupValue value
   */
  public String getLookupValue() {
    return lookupValue;
  }


  /**
   * Gets the alias attribute of the Property object
   *
   * @return The alias value
   */
  public String getAlias() {
    return alias;
  }


  /**
   * Gets the field attribute of the Property object
   *
   * @return The field value
   */
  public String getField() {
    return field;
  }


  /**
   * Gets the value attribute of the Property object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasName() {
    return (name != null && !"".equals(name));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasLookupValue() {
    return (lookupValue != null && !"".equals(lookupValue));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasAlias() {
    return (alias != null && !"".equals(alias));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasField() {
    return (field != null && !"".equals(field));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasValue() {
    return (value != null);
  }
}

