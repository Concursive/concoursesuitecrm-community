package com.darkhorseventures.apps.dataimport;

import java.util.HashMap;

/**
 *  Represents an object that will be responsible for saving the data provided
 *  by a DataReader. Classes that implement this interface should know how to
 *  connect to a specific data store for writing, process the supplied data, and
 *  save it to the data store. A separate DataReader will be responsible for
 *  actually collecting the data to be saved.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public interface DataWriter extends DataImportHandler {
  boolean autoCommit = true;


  /**
   *  Saves provided data to any type of data store.
   *
   *@param  record  Description of the Parameter
   *@return         Description of the Return Value
   */
  boolean save(DataRecord record);


  /**
   *  Sets the autoCommit attribute of the DataWriter object
   *
   *@param  flag  The new autoCommit value
   */
  void setAutoCommit(boolean flag);


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  boolean commit();


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  boolean rollback();
}

