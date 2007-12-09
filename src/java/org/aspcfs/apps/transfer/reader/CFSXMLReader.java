/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.transfer.reader;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataField;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.utils.StringUtils;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id$
 * @created Apr 3, 2007
 *
 */
public class CFSXMLReader implements DataReader {
  private static final Logger log = Logger.getLogger(org.aspcfs.apps.transfer.reader.CFSXMLReader.class);
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
    return "Coded test data (StAX)";
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
      log.error("file not found: " + xmlDataFile);
      configOK = false;
    }

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
    long milies = System.currentTimeMillis();
    log.info("[Begin] XML StAX reader.");
    try {
      log.info("xmlDataFile: " + xmlDataFile);
      
      XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(xmlDataFile));
      DataRecord dataRecord = null;
      DataField dataField = null;
      boolean dataFieldClosed = true;
      while(reader.hasNext()) {
       reader.next();
       //"dataField" is most frequently element. Check its first for speedup!
       if(reader.isStartElement() && "dataField".equals(reader.getName().toString())){
         dataFieldClosed = false;
         dataField = new DataField();
         int count = reader.getAttributeCount();
         if(count > 0){
           for(int i = 0; i < count; i++){
             if("name".equals(reader.getAttributeName(i).toString())){
               dataField.setName(reader.getAttributeValue(i));
             }else if("alias".equals(reader.getAttributeName(i).toString())){
               dataField.setAlias(reader.getAttributeValue(i));
             }else if("valueLookup".equals(reader.getAttributeName(i).toString())){
               dataField.setValueLookup(reader.getAttributeValue(i));
             }else{
               log.warn("DataField attribute \"" + reader.getAttributeName(i) + "\"] not supported");
             }
           }
         }
       }else if(reader.isEndElement() && "dataField".equals(reader.getName().toString())){
         dataFieldClosed = true;
         dataRecord.add(dataField);
         dataField = null;
       }else if(!dataFieldClosed && reader.hasText()){
         dataField.setValue(reader.getText());
       }else if(reader.isStartElement() && "dataRecord".equals(reader.getName().toString())){
         dataRecord = new DataRecord();
         int count = reader.getAttributeCount();
         if(count > 0){
           for(int i = 0; i < count; i++){
             if("name".equals(reader.getAttributeName(i).toString())){
               dataRecord.setName(reader.getAttributeValue(i));
             }else if("action".equals(reader.getAttributeName(i).toString())){
               dataRecord.setAction(reader.getAttributeValue(i));
             }else if("shareKey".equals(reader.getAttributeName(i).toString())){
               dataRecord.setShareKey(StringUtils.isTrue(reader.getAttributeValue(i)));
             }else{
               log.warn("DataRecord attribute \"" + reader.getAttributeName(i) + "\"] not supported");
             }
           }
           log.debug(dataRecord.getName() + " -> " + dataRecord.getAction());
         }
       }else if(reader.isEndElement() && "dataRecord".equals(reader.getName().toString())){
         processOK = writer.save(dataRecord);
         dataRecord = null;
         if (!processOK) {
           break;
         }
       }else{
         if(reader.isStartElement() || reader.isEndElement()){
           //Suppress warnings for <aspcfs> and <cfsdata> elements
           if(!"aspcfs".equals(reader.getName().toString()) && !"cfsdata".equals(reader.getName().toString())){
             log.warn("Element Name: " + reader.getName().toString() + " not supported!");
           }
         }else{
           //Suppress warning for document end
           if(reader.getEventType() != XMLEvent.END_DOCUMENT){
             log.warn("Not supported event. Event type: " + reader.getEventType());
           }
         }
       }
      }
    } catch (Exception e) {
      log.error(e.toString());
      e.printStackTrace(System.out);
      return false;
    } finally {
      log.info("[End] XML StAX reader.");
      log.info("Time: "+ (System.currentTimeMillis() - milies) + " ms.");
    }
    return processOK;
  }
}