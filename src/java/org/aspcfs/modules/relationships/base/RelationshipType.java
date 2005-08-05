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
package org.aspcfs.modules.relationships.base;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.ResultSet;

/**
 * Represents a relationship type
 *
 * @author Mathur
 * @version $id:exp$
 * @created August 11, 2004
 */
public class RelationshipType {

  private int typeId = 0;
  private int categoryIdMapsFrom = -1;
  private int categoryIdMapsTo = -1;
  private String reciprocalName1 = null;
  private String reciprocalName2 = null;
  private int level = -1;
  private boolean defaultItem = false;
  private boolean enabled = false;


  /**
   * Sets the typeId attribute of the RelationshipType object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   * Sets the typeId attribute of the RelationshipType object
   *
   * @param tmp The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryIdMapsFrom attribute of the RelationshipType object
   *
   * @param tmp The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(int tmp) {
    this.categoryIdMapsFrom = tmp;
  }


  /**
   * Sets the categoryIdMapsFrom attribute of the RelationshipType object
   *
   * @param tmp The new categoryIdMapsFrom value
   */
  public void setCategoryIdMapsFrom(String tmp) {
    this.categoryIdMapsFrom = Integer.parseInt(tmp);
  }


  /**
   * Sets the categoryIdMapsTo attribute of the RelationshipType object
   *
   * @param tmp The new categoryIdMapsTo value
   */
  public void setCategoryIdMapsTo(int tmp) {
    this.categoryIdMapsTo = tmp;
  }


  /**
   * Sets the categoryIdMapsTo attribute of the RelationshipType object
   *
   * @param tmp The new categoryIdMapsTo value
   */
  public void setCategoryIdMapsTo(String tmp) {
    this.categoryIdMapsTo = Integer.parseInt(tmp);
  }


  /**
   * Sets the reciprocalName1 attribute of the RelationshipType object
   *
   * @param tmp The new reciprocalName1 value
   */
  public void setReciprocalName1(String tmp) {
    this.reciprocalName1 = tmp;
  }


  /**
   * Sets the reciprocalName2 attribute of the RelationshipType object
   *
   * @param tmp The new reciprocalName2 value
   */
  public void setReciprocalName2(String tmp) {
    this.reciprocalName2 = tmp;
  }


  /**
   * Sets the level attribute of the RelationshipType object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the RelationshipType object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Sets the defaultItem attribute of the RelationshipType object
   *
   * @param tmp The new defaultItem value
   */
  public void setDefaultItem(boolean tmp) {
    this.defaultItem = tmp;
  }


  /**
   * Sets the defaultItem attribute of the RelationshipType object
   *
   * @param tmp The new defaultItem value
   */
  public void setDefaultItem(String tmp) {
    this.defaultItem = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the enabled attribute of the RelationshipType object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the RelationshipType object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the enabled attribute of the RelationshipType object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the typeId attribute of the RelationshipType object
   *
   * @return The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   * Gets the categoryIdMapsFrom attribute of the RelationshipType object
   *
   * @return The categoryIdMapsFrom value
   */
  public int getCategoryIdMapsFrom() {
    return categoryIdMapsFrom;
  }


  /**
   * Gets the categoryIdMapsTo attribute of the RelationshipType object
   *
   * @return The categoryIdMapsTo value
   */
  public int getCategoryIdMapsTo() {
    return categoryIdMapsTo;
  }


  /**
   * Gets the reciprocalName1 attribute of the RelationshipType object
   *
   * @return The reciprocalName1 value
   */
  public String getReciprocalName1() {
    return reciprocalName1;
  }


  /**
   * Gets the reciprocalName2 attribute of the RelationshipType object
   *
   * @return The reciprocalName2 value
   */
  public String getReciprocalName2() {
    return reciprocalName2;
  }


  /**
   * Gets the level attribute of the RelationshipType object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Gets the defaultItem attribute of the RelationshipType object
   *
   * @return The defaultItem value
   */
  public boolean getDefaultItem() {
    return defaultItem;
  }


  /**
   * Constructor for the RelationshipType object
   *
   * @param rs Description of the Parameter
   * @throws java.sql.SQLException Description of the Exception
   */
  public RelationshipType(ResultSet rs) throws java.sql.SQLException {
    typeId = rs.getInt("type_id");
    categoryIdMapsFrom = rs.getInt("category_id_maps_from");
    categoryIdMapsTo = rs.getInt("category_id_maps_to");
    reciprocalName1 = rs.getString("reciprocal_name_1");
    reciprocalName2 = rs.getString("reciprocal_name_2");
    level = rs.getInt("level");
    defaultItem = rs.getBoolean("default_item");
    enabled = rs.getBoolean("enabled");
  }
}

