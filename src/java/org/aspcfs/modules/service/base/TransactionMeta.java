package com.darkhorseventures.utils;

import java.util.*;
import org.w3c.dom.*;
import java.sql.*;

/**
 *  TransactionMeta stores data for Transaction Items.  The meta data can
 *  then be used for identifying object properties, for example.
 *
 *@author     matt
 *@created    April 10, 2002
 *@version    $Id$
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
   *@param  tmp  The new field value
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
   *@return    The fields value
   */
  public LinkedList getFields() {
    return fields;
  }
}

