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

  private ArrayList fields = null;


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
      fields = new ArrayList();
    }
    fields.add(tmp);
  }


  /**
   *  Gets the fields attribute of the TransactionMeta object
   *
   *@return    The fields value
   */
  public ArrayList getFields() {
    return fields;
  }
}

