package com.darkhorseventures.apps.dataimport;

import java.util.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;
import java.io.*;

public class DataImport {
  
  public static Logger logger = Logger.getLogger(DataImport.class.getName());

  public static void main(String args[]) {
    //Initialize app from the config file
    if (args.length == 0) {
      System.out.println("Usage: DataImport [config file]");
      System.exit(0);
    }
    
    DataImport dataImport = new DataImport();
    dataImport.execute(args[0]);
  }

  public void execute(String configParam) {
/*     Handler[] handlers = Logger.getLogger("").getHandlers();
    for ( int index = 0; index < handlers.length; index++ ) {
      handlers[index].setLevel( Level.ALL );
    }
 */    
    logger.info("Reading configuration file: " + configParam + "...");
    try {
      File configFile = new File(configParam);
      XMLUtils xml = new XMLUtils(configFile);
      
      //Provide info about the config
      logger.info("Description: " + xml.getNodeText(xml.getFirstElement(xml.getDocumentElement(), "description")).trim());
      
      //Make sure the config has valid entries
      String readerClass = (String)xml.getFirstElement(xml.getDocumentElement(), "reader").getAttribute("class");
      String writerClass = (String)xml.getFirstElement(xml.getDocumentElement(), "writer").getAttribute("class");
      
      logger.info("Reader: " + readerClass);
      logger.info("Writer: " + writerClass);
      
      if (StringUtils.hasText(readerClass) && 
          StringUtils.hasText(writerClass)) {
      
        //Instantiate the reader
        Object reader = Class.forName(readerClass).newInstance();
        HashMap invalidReaderProperties = xml.populateObject(reader, xml.getFirstElement(xml.getDocumentElement(), "reader"));
        displayItems(invalidReaderProperties, "Invalid Reader Property");
        validateHandler(reader);
        
        //Instantiate the writer
        Object writer = Class.forName(writerClass).newInstance();
        HashMap invalidWriterProperties = xml.populateObject(writer, xml.getFirstElement(xml.getDocumentElement(), "writer"));
        displayItems(invalidWriterProperties, "Invalid Writer Property");
        validateHandler(writer);
        
        //Execute the read/write process
        ((DataReader)reader).execute((DataWriter)writer);
        
      } else {
        logger.info("A Reader and Writer need to be specified in the configuration file");
        System.exit(0);
      }
    } catch (Exception e) {
      logger.info("Error: " + e.toString());
    }
    logger.info("All done.");
    System.exit(0);
  }
  
  private void displayItems(HashMap itemList, String displayText) {
    if (itemList.size() > 0) {
      Iterator i = itemList.keySet().iterator();
      while (i.hasNext()) {
        String param = (String)i.next();
        logger.info(displayText + ": " + param);
      }
    }
  }
  
  private boolean validateHandler(Object handler) {
    return (((DataImportHandler)handler).isConfigured());
  }
}

