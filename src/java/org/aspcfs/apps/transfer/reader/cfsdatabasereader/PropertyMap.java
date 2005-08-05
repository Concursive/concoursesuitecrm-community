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

import java.util.ArrayList;

/**
 * Contains a list of Property objects and defines an object Mapping
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 18, 2002
 */
public class PropertyMap extends ArrayList {
  private String id = null;
  private String table = null;
  private String uniqueField = null;


  /**
   * Sets the id attribute of the PropertyMap object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   * Gets the id attribute of the PropertyMap object
   *
   * @return The id value
   */
  public String getId() {
    return id;
  }


  /**
   * Sets the table attribute of the PropertyMap object
   *
   * @param tmp The new table value
   */
  public void setTable(String tmp) {
    this.table = tmp;
  }


  /**
   * Gets the table attribute of the PropertyMap object
   *
   * @return The table value
   */
  public String getTable() {
    return table;
  }


  /**
   * Sets the uniqueField attribute of the PropertyMap object
   *
   * @param tmp The new uniqueField value
   */
  public void setUniqueField(String tmp) {
    this.uniqueField = tmp;
  }


  /**
   * Gets the uniqueField attribute of the PropertyMap object
   *
   * @return The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasTable() {
    return (table != null && !"".equals(table));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasProperties() {
    return (this.size() > 0);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasUniqueField() {
    return (uniqueField != null && !"".equals(uniqueField));
  }

}

