package org.aspcfs.apps.transfer;

import java.util.logging.*;

/**
 *  Represents the necessary items for DataReader and DataWriter classes.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public interface DataImportHandler {
  public static Logger logger = Logger.getLogger(DataImport.class.getName());


  /**
   *  Gets the configured attribute of the DataImportHandler object
   *
   *@return    The configured value
   */
  public boolean isConfigured();


  /**
   *  Gets the version attribute of the DataImportHandler object
   *
   *@return    The version value
   */
  public double getVersion();


  /**
   *  Gets the name attribute of the DataImportHandler object
   *
   *@return    The name value
   */
  public String getName();


  /**
   *  Gets the description attribute of the DataImportHandler object
   *
   *@return    The description value
   */
  public String getDescription();
}

