/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.apps.transfer.writer;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataField;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.utils.DatabaseUtils;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

/**
 * Writes data records to the specified file; if the file exists the default
 * behavior is to append a new data record set to the existing file
 *
 * @author holub
 * @version $Id$
 * @created Apr 3, 2007
 */
public class CFSXMLWriter implements DataWriter {
  private final static Logger log = Logger.getLogger(org.aspcfs.apps.transfer.writer.CFSXMLWriter.class);

  private String lastResponse = null;
  private String filename = null;
  private boolean append = true;
  XMLStreamWriter writer = null;

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataWriter#getLastResponse()
   */
  public String getLastResponse() {
    return lastResponse;
  }

  /**
   * Sets the filename attribute of the CFSXMLStAXWriter object
   *
   * @param fileName The new filename value
   */
  public void setFilename(String fileName) {
    this.filename = fileName;
  }

  /**
   * Gets the append attribute of the CFSXMLWriter object
   *
   * @return The overwrite value
   */
  public boolean getAppend() {
    return append;
  }


  /**
   * Sets the append attribute of the CFSXMLWriter object
   *
   * @param tmp The new overwrite value
   */
  public void setAppend(boolean tmp) {
    this.append = tmp;
  }


  /**
   * Sets the append attribute of the CFSXMLWriter object
   *
   * @param tmp The new overwrite value
   */
  public void setAppend(String tmp) {
    this.append = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the autoCommit attribute of the CFSXMLWriter object
   *
   * @param flag The new autoCommit value
   */
  public void setAutoCommit(boolean flag) {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataImportHandler#getName()
   */
  public String getName() {
    return "CFS XML StAX Writer";
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataImportHandler#getDescription()
   */
  public String getDescription() {
    return "Generates an XML StAX representation of data as specified";
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataImportHandler#isConfigured()
   */
  public boolean isConfigured() {
    if (filename == null) {
      return false;
    }
    try {
      XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
      writer = outputFactory.createXMLStreamWriter(new java.io.FileOutputStream(filename, append), "utf-8");
      writer.writeStartElement("aspcfs");
      writer.writeStartElement("cfsdata");
    } catch (Exception e) {
      log.error(e.toString());
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataWriter#save(org.aspcfs.apps.transfer.DataRecord)
   */
  public boolean save(DataRecord record) {
    try {
      writer.writeStartElement("dataRecord");
      writer.writeAttribute("name", record.getName());
      writer.writeAttribute("action", record.getAction());
      writer.writeAttribute("shareKey", String.valueOf(record.getShareKey()));

      Iterator fields = record.iterator();
      while (fields.hasNext()) {
        DataField thisField = (DataField) fields.next();
        writer.writeStartElement("dataField");
        writer.writeAttribute("name", thisField.getName());
        if (thisField.getAlias() != null) {
          writer.writeAttribute("alias", thisField.getAlias());
        }
        if (thisField.getValueLookup() != null) {
          writer.writeAttribute("valueLookup", thisField.getValueLookup());
        }
        if (thisField.getValue() != null) {
          writer.writeCharacters(thisField.getValue());
        }
        //close "</dataField>"
        writer.writeEndElement();
      }
      //close "</dataRecord>"
      writer.writeEndElement();
    } catch (Exception e) {
      log.error(e.toString());
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataWriter#commit()
   */
  public boolean commit() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataWriter#rollback()
   */
  public boolean rollback() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataWriter#close()
   */
  public boolean close() {
    try {
      //close "</cfsdata>"
      writer.writeEndElement();
      //close "</aspcfs>"
      writer.writeEndElement();
      writer.flush();
      writer.close();
    } catch (Exception e) {
      log.error(e.toString());
      return false;
    }

    return true;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataWriter#initialize()
   */
  public void initialize() {
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataImportHandler#getVersion()
   */
  public double getVersion() {
    return 1.0d;
  }

  /* (non-Javadoc)
   * @see org.aspcfs.apps.transfer.DataWriter#load(org.aspcfs.apps.transfer.DataRecord)
   */
  public boolean load(DataRecord record) {
    return false;
  }
}
