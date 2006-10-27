/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.transfer.reader;

import org.aspcfs.apps.transfer.DataField;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Description of the Class
 *
 * @author Ananth
 * @created February 3, 2006
 */
public class CFSXMLReader implements DataReader {
  protected String xmlDataFile = null;


  /**
   * Gets the xmlDataFile attribute of the CFSXMLReader object
   *
   * @return The xmlDataFile value
   */
  public String getXmlDataFile() {
    return xmlDataFile;
  }


  /**
   * Sets the xmlDataFile attribute of the CFSXMLReader object
   *
   * @param tmp The new xmlDataFile value
   */
  public void setXmlDataFile(String tmp) {
    this.xmlDataFile = tmp;
  }

  //TODO: need import mappings??
  //protected String propertyFile = null;
  //protected PropertyMapList mappings = null;


  /**
   * Gets the version attribute of the CFSXMLReader object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the CFSXMLReader object
   *
   * @return The name value
   */
  public String getName() {
    return "Backup Reader";
  }


  /**
   * Gets the description attribute of the CFSXMLReader object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Coded test data";
  }


  /**
   * Gets the configured attribute of the CFSXMLReader object
   *
   * @return The configured value
   */
  public boolean isConfigured() {
    boolean configOK = true;
    File importFile = new File(xmlDataFile);
    if (!importFile.exists()) {
      logger.info("CFSXMLReader-> Config: file not found: " + xmlDataFile);
      configOK = false;
    }

    //TODO: mappings needed??
    /*
     *  try {
     *  mappings = new PropertyMapList(propertyFile, new ArrayList());
     *  } catch (Exception e) {
     *  logger.info("CFSXMLReader-> Config: mappings could not be loaded");
     *  configOK = false;
     *  }
     */
    return configOK;
  }


  /**
   * Description of the Method
   *
   * @param writer Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    boolean processOK = true;
    try {
      File configFile = new File(xmlDataFile);
      XMLUtils xml = new XMLUtils(configFile);

      ArrayList recordList = new ArrayList();
      XMLUtils.getAllChildren(
          xml.getFirstChild("cfsdata"), "dataRecord", recordList);
      logger.info("Records: " + recordList.size());

      Iterator records = recordList.iterator();
      while (records.hasNext()) {
        Element record = (Element) records.next();
        DataRecord thisRecord = new DataRecord();

        thisRecord.setName((String) record.getAttribute("name"));
        thisRecord.setAction((String) record.getAttribute("action"));
        thisRecord.setShareKey(
            StringUtils.isTrue(
                (String) record.getAttribute("shareKey")));

        //Insert data fields under this record
        ArrayList fieldList = new ArrayList();
        XMLUtils.getAllChildren(record, "dataField", fieldList);
        Iterator fields = fieldList.iterator();
        while (fields.hasNext()) {
          Element field = (Element) fields.next();
          DataField thisField = new DataField();
          thisField.setName((String) field.getAttribute("name"));
          thisField.setAlias((String) field.getAttribute("alias"));
          thisField.setValueLookup((String) field.getAttribute("valueLookup"));
          thisField.setValue(
              XMLUtils.getNodeText(field));

          thisRecord.add(thisField);
        }
        processOK = writer.save(thisRecord);
        if (!processOK) {
          break;
        }
      }
    } catch (Exception e) {
      logger.info(e.toString());
      e.printStackTrace(System.out);
      return false;
    }
    return processOK;
  }
}

