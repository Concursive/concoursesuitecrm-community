package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import com.darkhorseventures.apps.dataimport.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;
import org.w3c.dom.*;

/**
 *  Reads data by constructing CFS objects from a database connection.
 *  This reader will process objects in a specific order so that data integrity
 *  will be maintained during a copy process.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class CFSDatabaseReader implements DataReader {

  private String driver = null;
  private String url = null;
  private String user = null;
  private String password = null;
  private ArrayList modules = null;
  private PropertyMapList mappings = null;

  public void setDriver(String tmp) { this.driver = tmp; }
  public void setUrl(String tmp) { this.url = tmp; }
  public void setUser(String tmp) { this.user = tmp; }
  public void setPassword(String tmp) { this.password = tmp; }
  public String getDriver() { return driver; }
  public String getUrl() { return url; }
  public String getUser() { return user; }
  public String getPassword() { return password; }


  /**
   *  Gets the version attribute of the CFSDatabaseReader object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the CFSDatabaseReader object
   *
   *@return    The name value
   */
  public String getName() {
    return "CFS 2.x Database Reader";
  }


  /**
   *  Gets the description attribute of the CFSDatabaseReader object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Reads data from an ASPCFS version 2.x database";
  }


  /**
   *  Gets the configured attribute of the CFSDatabaseReader object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    //Check initial settings
    if (driver == null || url == null || user == null) {
      return false;
    }

    //Read in modules and mappings to be processed
    try {
      File configFile = new File("CFSDatabaseReader.xml");
      XMLUtils xml = new XMLUtils(configFile);
      
      modules = new ArrayList();
      xml.getAllChildrenText(xml.getFirstChild("processes"), "module", modules);
      logger.info("CFSDatabaseReader module count: " + modules.size());
      
      mappings = new PropertyMapList();
      ArrayList mapElements = new ArrayList();
      XMLUtils.getAllChildren(xml.getFirstChild("mappings"), "map", mapElements);
      Iterator mapItems = mapElements.iterator();
      while (mapItems.hasNext()) {
        Element map = (Element)mapItems.next();
        PropertyMap mapProperties = new PropertyMap();
        mapProperties.setId((String)map.getAttribute("id"));
        //xml.getAllChildrenText(map, "property", mapProperties);
        
        NodeList nl = map.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
          Node n = nl.item(i);
          if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals("property")) {
            String nodeText = XMLUtils.getNodeText((Element) n);
            if (nodeText != null) {
              String lookupValue = ((Element) n).getAttribute("lookup");
              Property thisProperty = new Property(nodeText);
              if (lookupValue != null && !"".equals(lookupValue)) {
                thisProperty.setLookupValue(lookupValue);
              }
              mapProperties.add(thisProperty);
            }
          }
        }
        mappings.put((String)map.getAttribute("class"), mapProperties);
      }
      
    } catch (Exception e) {
      logger.info("Error reading module configuration-> " + e.toString());
      return false;
    }
    
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  writer  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    //Connect to database
    ConnectionPool sqlDriver = null;
    Connection db = null;
    try {
      sqlDriver = new ConnectionPool();
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(1);
      ConnectionElement thisElement = new ConnectionElement(
          url, user, password);
      thisElement.setDriver(driver);
      db = sqlDriver.getConnection(thisElement);
    } catch (SQLException e) {
      logger.info("Could not get database connection" + e.toString());
      return false;
    }

    try {
      //Process the modules
      boolean processOK = true;
      Iterator moduleList = modules.iterator();
      while (moduleList.hasNext() && processOK) {
        String moduleClass = (String)moduleList.next();
        Object module = Class.forName(moduleClass).newInstance();
        processOK = ((CFSDatabaseReaderImportModule)module).process(writer, db, mappings);
        if (!processOK) {
          logger.info("Module failed: " + moduleClass);
        }
      }
    } catch (Exception ex) {
      logger.info(ex.toString());
    } finally {
      sqlDriver.free(db);
      sqlDriver.destroy();
      sqlDriver = null;
    }

    return true;
  }
}

