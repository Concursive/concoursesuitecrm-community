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

import java.util.LinkedList;

/**
 *  TransactionMeta stores data for Transaction Items. The meta data can then be
 *  used for identifying object properties, for example.
 *
 * @author     matt
 * @version    $Id: TransactionMeta.java,v 1.2 2002/04/22 15:32:59 mrajkowski
 *      Exp $
 * @created    April 10, 2002
 */
public class TransactionMeta {

  private LinkedList fields = null;
  

  /**
   *  Constructor for the TransactionMeta object
   */
  public TransactionMeta() { }


  /**
   *  Sets the field attribute of the TransactionMeta object
   *
   * @param  tmp  The new field value
   */
  public void setProperty(String tmp) {
    if (fields == null) {
      fields = new LinkedList();
    }
    fields.add(tmp);
  }


  /**
   *  Gets the fields attribute of the TransactionMeta object
   *
   * @return    The fields value
   */
  public LinkedList getFields() {
    return fields;
  }
}

