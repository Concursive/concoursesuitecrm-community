package com.darkhorseventures.apps.dataimport.cfsdatabasereader;

import com.darkhorseventures.apps.dataimport.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;
import org.w3c.dom.*;

/**
 *  Reads data by constructing CFS objects from a database connection. This
 *  reader will process objects in a specific order so that data integrity will
 *  be maintained during a copy process.<p>
 *
 *  CFS Objects must have the following:<br>
 *  - A method called: insert(Connection db)<br>
 *  - A constructor like: new Object(Connection db, int id)<br>
 *  - A modified field with a getModified() method<p>
 *
 *  CFS List Objects must have the following fields:<br>
 *  - tableName, uniqueField, lastAnchor, nextAnchor, syncType, pagedListInfo
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id: CFSDatabaseReader.java,v 1.3 2002/09/05 14:49:36 mrajkowski
 *      Exp $
 */
public class CFSDatabaseReader implements DataReader {

  private String driver = null;
  private String url = null;
  private String user = null;
  private String password = null;
  private String processConfigFile = "CFSDatabaseReader.xml";
  private ArrayList modules = null;
  private PropertyMapList mappings = null;

  private int count = 0;

  /**
   *  Sets the driver attribute of the CFSDatabaseReader object
   *
   *@param  tmp  The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   *  Sets the url attribute of the CFSDatabaseReader object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the user attribute of the CFSDatabaseReader object
   *
   *@param  tmp  The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the password attribute of the CFSDatabaseReader object
   *
   *@param  tmp  The new password value
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Sets the processConfigFile attribute of the CFSDatabaseReader object
   *
   *@param  tmp  The new processConfigFile value
   */
  public void setProcessConfigFile(String tmp) {
    this.processConfigFile = tmp;
  }


  /**
   *  Gets the driver attribute of the CFSDatabaseReader object
   *
   *@return    The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   *  Gets the url attribute of the CFSDatabaseReader object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the user attribute of the CFSDatabaseReader object
   *
   *@return    The user value
   */
  public String getUser() {
    return user;
  }


  /**
   *  Gets the password attribute of the CFSDatabaseReader object
   *
   *@return    The password value
   */
  public String getPassword() {
    return password;
  }


  /**
   *  Gets the processConfigFile attribute of the CFSDatabaseReader object
   *
   *@return    The processConfigFile value
   */
  public String getProcessConfigFile() {
    return processConfigFile;
  }


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
      File configFile = new File(processConfigFile);
      XMLUtils xml = new XMLUtils(configFile);

      modules = new ArrayList();
      xml.getAllChildrenText(xml.getFirstChild("processes"), "module", modules);
      logger.info("CFSDatabaseReader module count: " + modules.size());

      mappings = new PropertyMapList();
      ArrayList mapElements = new ArrayList();
      XMLUtils.getAllChildren(xml.getFirstChild("mappings"), "map", mapElements);
      Iterator mapItems = mapElements.iterator();
      while (mapItems.hasNext()) {
        //Get the map node
        Element map = (Element) mapItems.next();
        PropertyMap mapProperties = new PropertyMap();
        mapProperties.setId((String) map.getAttribute("id"));
        mapProperties.setTable((String) map.getAttribute("table"));
        mapProperties.setUniqueField((String) map.getAttribute("uniqueField"));
        
        //Get any property nodes
        NodeList nl = map.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
          Node n = nl.item(i);
          if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals("property")) {
            String nodeText = XMLUtils.getNodeText((Element) n);
            Property thisProperty = null;
            if (nodeText != null) {
              thisProperty = new Property(nodeText);
            } else {
              thisProperty = new Property();
            }

            String lookupValue = ((Element) n).getAttribute("lookup");
            if (lookupValue != null && !"".equals(lookupValue)) {
              thisProperty.setLookupValue(lookupValue);
            }

            String alias = ((Element) n).getAttribute("alias");
            if (alias != null && !"".equals(alias)) {
              thisProperty.setAlias(alias);
            }
            
            String field = ((Element) n).getAttribute("field");
            if (field != null && !"".equals(field)) {
              thisProperty.setField(field);
            }

            mapProperties.add(thisProperty);
          }
        }
        if (mappings.containsKey(map.getAttribute("class"))) {
          mappings.put((String) map.getAttribute("class") + ++count, mapProperties);
        } else {
          mappings.put((String) map.getAttribute("class"), mapProperties);
        }
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
        String moduleClass = (String) moduleList.next();
        logger.info("Processing: " + moduleClass);
        Object module = Class.forName(moduleClass).newInstance();
        processOK = ((CFSDatabaseReaderImportModule) module).process(writer, db, mappings);
        if (!processOK) {
          logger.info("Module failed: " + moduleClass);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace(System.out);
      //logger.info(ex.toString());
      //logger.info(writer.getLastResponse());
    } finally {
      sqlDriver.free(db);
      sqlDriver.destroy();
      sqlDriver = null;
    }

    return true;
  }
}

