package org.aspcfs.modules.service.base;

import java.util.HashMap;

/**
 *  Context for sharing data between Transaction Items within a single
 *  Transaction
 *
 *@author     matt rajkowski
 *@created    November 6, 2003
 *@version    $Id$
 */
public class TransactionContext {

  private HashMap propertyMap = null;


  /**
   *  Constructor for the TransactionContext object
   */
  public TransactionContext() { }


  /**
   *  Sets the propertyMap attribute of the TransactionContext object
   *
   *@param  tmp  The new propertyMap value
   */
  public void setPropertyMap(HashMap tmp) {
    this.propertyMap = tmp;
  }


  /**
   *  Gets the propertyMap attribute of the TransactionContext object
   *
   *@return    The propertyMap value
   */
  public HashMap getPropertyMap() {
    if (propertyMap == null) {
      propertyMap = new HashMap();
    }
    return propertyMap;
  }

}

