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
package org.aspcfs.modules.base;



/**
 * A class that implements SyncableList has the necessary methods in order for
 * generating a list of objects based on synchronization parameters.<p>
 * <p/>
 * The implementing class needs to have nextAnchor and lastAnchor configured
 * correctly in createFilter() and prepareFilter() methods.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 20, 2003
 */
public interface SyncableList {

  /**
   * The lastAnchor represents the timestamp in which the sync last
   * successfully exchanged data, the lastAnchor would be null if no prior sync
   * occurred.
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp);


  /**
   * Sets the lastAnchor attribute of the SyncableList object
   *
   * @param tmp The new lastAnchor value
   */
  public void setLastAnchor(String tmp);


  /**
   * The nextAnchor represents the timestamp in which this sync operation
   * began. By using the lastAnchor and nextAnchor, the data can be pinpointed
   * for this data exchange.
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp);


  /**
   * Sets the nextAnchor attribute of the SyncableList object
   *
   * @param tmp The new nextAnchor value
   */
  public void setNextAnchor(String tmp);


  /**
   * The syncType specifies the transaction type: is the process inserting,
   * updating, deleting, or selecting data. This is important so that a
   * collection knows how to use the lastAnchor and nextAnchor.
   *
   * @param tmp The new syncType value
   */
  public void setSyncType(int tmp);


  /**
   * The tableName refers to the table in which the collection will query from.
   *
   * @return The tableName value
   */
  public String getTableName();


  /**
   * Gets the uniqueField attribute of the SyncableList object
   *
   * @return The uniqueField value
   */
  public String getUniqueField();

}

