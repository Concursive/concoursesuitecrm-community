package com.darkhorseventures.apps.dataimport;

/**
 *  Represents an object that will be responsible for reading data from a system
 *  and saving the data to a DataWriter. Classes that implement this interface
 *  should know how to connect to a specific data store, process the supplied
 *  data, then pass the data to a DataWriter by calling the writer.save()
 *  method.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public interface DataReader extends DataImportHandler {
  /**
   *  A required method which processes the DataImport for this DataReader,
   *  given a DataWriter.
   *
   *@param  writer  Description of the Parameter
   *@return         Description of the Return Value
   */
  boolean execute(DataWriter writer);
}

