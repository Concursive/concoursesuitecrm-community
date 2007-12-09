/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.service.base;

import java.util.HashMap;

/**
 * Context for sharing data between Transaction Items within a single
 * Transaction
 *
 * @author matt rajkowski
 * @version $Id: TransactionContext.java,v 1.2 2003/11/17 15:26:34 mrajkowski
 *          Exp $
 * @created November 6, 2003
 */
public class TransactionContext {

  private HashMap propertyMap = null;


  /**
   * Constructor for the TransactionContext object
   */
  public TransactionContext() {
  }


  /**
   * Sets the propertyMap attribute of the TransactionContext object
   *
   * @param tmp The new propertyMap value
   */
  public void setPropertyMap(HashMap tmp) {
    this.propertyMap = tmp;
  }


  /**
   * Gets the propertyMap attribute of the TransactionContext object
   *
   * @return The propertyMap value
   */
  public HashMap getPropertyMap() {
    if (propertyMap == null) {
      propertyMap = new HashMap();
    }
    return propertyMap;
  }

}

