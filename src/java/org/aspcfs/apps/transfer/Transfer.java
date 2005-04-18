package org.aspcfs.apps.transfer;

import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.XMLUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 *  Begins the process of migrating data from a DataReader to a DataWriter.
 *  Transfer is responsible for loading configuration data, instantiating
 *  objects, and executing and monitoring the data import process.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class Transfer {

  public static Logger logger = Logger.getLogger(Transfer.class.getName());


  /**
   *  Application to execute the reader and writer specified by the supplied
   *  config file
   *
   *@param  args  Description of the Parameter
   */
  public static void main(String args[]) {
    if (args.length == 0) {
      System.out.println("Usage: Transfer [config file]");
      System.exit(2);
    }
    Transfer transfer = new Transfer();
    transfer.execute(args[0]);
    System.exit(0);
  }


  /**
   *  This method can be executed by another class because it does not call
   *  System.exit()
   *
   *@param  args  Description of the Parameter
   */
  public static void doTask(String args[]) {
    if (args.length == 0) {
      System.out.println("Usage: Transfer [config file]");
      System.out.println("ExitValue: 2");
    }
    Transfer transfer = new Transfer();
    transfer.execute(args[0]);
    System.out.println("ExitValue: 0");
  }


  /**
   *  Executes a data import based on the given XML configuration file. Each
   *  data import consists of a Reader and Writer (ImportHandlers), plus system
   *  specific configuration.
   *
   *@param  configParam  Description of the Parameter
   */
  public void execute(String configParam) {
    logger.info("Reading configuration file: " + configParam + "...");
    try {
      File configFile = new File(configParam);
      XMLUtils xml = new XMLUtils(configFile);

      //Provide info about the config
      logger.info("Description: " + XMLUtils.getNodeText(XMLUtils.getFirstElement(xml.getDocumentElement(), "description")).trim());

      try {
        String debug = XMLUtils.getNodeText(XMLUtils.getFirstElement(xml.getDocumentElement(), "debug"));
        if (debug != null) {
          System.setProperty("DEBUG", debug);
        }
      } catch (NullPointerException nl) {
      }

      //Make sure the config has valid entries
      String readerClass = (String) XMLUtils.getFirstElement(xml.getDocumentElement(), "reader").getAttribute("class");
      String writerClass = (String) XMLUtils.getFirstElement(xml.getDocumentElement(), "writer").getAttribute("class");

      logger.info("Reader: " + readerClass);
      logger.info("Writer: " + writerClass);

      if (StringUtils.hasText(readerClass) &&
          StringUtils.hasText(writerClass)) {

        //Instantiate the reader
        Object reader = Class.forName(readerClass).newInstance();
        HashMap invalidReaderProperties = XMLUtils.populateObject(reader, XMLUtils.getFirstElement(xml.getDocumentElement(), "reader"));
        displayItems(invalidReaderProperties, "Invalid Reader Property");
        if (!validateHandler(reader)) {
          logger.info("Reader has not been configured");
        } else {

          //Instantiate the writer
          Object writer = Class.forName(writerClass).newInstance();
          HashMap invalidWriterProperties = XMLUtils.populateObject(writer, XMLUtils.getFirstElement(xml.getDocumentElement(), "writer"));
          displayItems(invalidWriterProperties, "Invalid Writer Property");
          if (!validateHandler(writer)) {
            logger.info("Writer has not been configured");
          } else {
            //Execute the read/write process
            ((DataReader) reader).execute((DataWriter) writer);
            ((DataWriter) writer).close();
          }
        }
      } else {
        logger.info("A Reader and Writer need to be specified in the configuration file");
      }
    } catch (Exception e) {
      logger.info("Error: " + e.toString());
    }
  }


  /**
   *  Processes the list of invalid settings for the given ImportHandler.
   *
   *@param  itemList     Description of the Parameter
   *@param  displayText  Description of the Parameter
   */
  private void displayItems(HashMap itemList, String displayText) {
    if (itemList.size() > 0) {
      Iterator i = itemList.keySet().iterator();
      while (i.hasNext()) {
        String param = (String) i.next();
        if (param != null && param.indexOf("^Guid") == -1) {
          logger.info(displayText + ": " + param);
        }
      }
    }
  }


  /**
   *  Returns whether the ImportHandler has been successfully configured before
   *  use. A simple check to make sure all of the required settings have been
   *  set.
   *
   *@param  handler  Description of the Parameter
   *@return          Description of the Return Value
   */
  private boolean validateHandler(Object handler) {
    return (((DataImportHandler) handler).isConfigured());
  }
}

