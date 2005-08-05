package org.aspcfs.apps.transfer;



/**
 * Represents an object that will be responsible for saving the data provided
 * by a DataReader. Classes that implement this interface should know how to
 * connect to a specific data store for writing, process the supplied data, and
 * save it to the data store. A separate DataReader will be responsible for
 * actually collecting the data to be saved.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 3, 2002
 */
public interface DataWriter extends DataImportHandler {
  boolean autoCommit = true;
  String lastReponse = null;


  /**
   * Saves provided data to any type of data store.
   *
   * @param record Description of the Parameter
   * @return Description of the Return Value
   */
  boolean save(DataRecord record);


  /**
   * Loads data from a data store
   *
   * @param record Description of the Parameter
   * @return Description of the Return Value
   */
  boolean load(DataRecord record);


  /**
   * Sets the autoCommit attribute of the DataWriter object
   *
   * @param flag The new autoCommit value
   */
  void setAutoCommit(boolean flag);


  /**
   * Forces a stack of writes to be executed at once if autoCommit is false and
   * the writer supports commits.
   *
   * @return Description of the Return Value
   */
  boolean commit();


  /**
   * Forces stacked writes to be cleared
   *
   * @return Description of the Return Value
   */
  boolean rollback();


  /**
   * Gets the lastResponse attribute of the DataWriter object
   *
   * @return The lastResponse value
   */
  String getLastResponse();


  /**
   * Allow the writer to cleanup at the end of its use
   *
   * @return Description of the Return Value
   */
  boolean close();
}

