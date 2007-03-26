/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
import org.aspcfs.modules.service.sync.base.SyncPackage;
import org.aspcfs.utils.DatabaseUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    February 3, 2006
 */
public class CFSXMLWriter implements DataWriter {

  private final static Logger logger = Logger.getLogger(org.aspcfs.apps.transfer.writer.CFSXMLWriter.class);

  private String lastResponse = null;
  private String filename = null;
  private PrintWriter out = null;
  private boolean overwrite = true;

  private int recordCount = 0;

  TransformerFactory transformerFactory = null;
  Transformer transformer = null;

  private int recipient = SyncPackage.SYNC_CLIENT;


  /**
   *  Gets the recipient attribute of the CFSXMLWriter object
   *
   * @return    The recipient value
   */
  public int getRecipient() {
    return recipient;
  }


  /**
   *  Sets the recipient attribute of the CFSXMLWriter object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(int tmp) {
    this.recipient = tmp;
  }


  /**
   *  Sets the recipient attribute of the CFSXMLWriter object
   *
   * @param  tmp  The new recipient value
   */
  public void setRecipient(String tmp) {
    this.recipient = Integer.parseInt(tmp);
  }



  /**
   *  Gets the lastResponse attribute of the CFSXMLWriter object
   *
   * @return    The lastResponse value
   */
  public String getLastResponse() {
    return lastResponse;
  }


  /**
   *  Gets the filename attribute of the CFSXMLWriter object
   *
   * @return    The filename value
   */
  public String getFilename() {
    return filename;
  }


  /**
   *  Sets the filename attribute of the CFSXMLWriter object
   *
   * @param  tmp  The new filename value
   */
  public void setFilename(String tmp) {
    this.filename = tmp;
  }


  /**
   *  Gets the overwrite attribute of the CFSXMLWriter object
   *
   * @return    The overwrite value
   */
  public boolean getOverwrite() {
    return overwrite;
  }


  /**
   *  Sets the overwrite attribute of the CFSXMLWriter object
   *
   * @param  tmp  The new overwrite value
   */
  public void setOverwrite(boolean tmp) {
    this.overwrite = tmp;
  }


  /**
   *  Sets the overwrite attribute of the CFSXMLWriter object
   *
   * @param  tmp  The new overwrite value
   */
  public void setOverwrite(String tmp) {
    this.overwrite = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the autoCommit attribute of the CFSXMLWriter object
   *
   * @param  flag  The new autoCommit value
   */
  public void setAutoCommit(boolean flag) {
  }


  /**
   *  Gets the name attribute of the CFSXMLWriter object
   *
   * @return    The name value
   */
  public String getName() {
    return "CFS XML Writer";
  }


  /**
   *  Gets the description attribute of the CFSXMLWriter object
   *
   * @return    The description value
   */
  public String getDescription() {
    return "Generates an XML representation of data as specified";
  }


  /**
   *  Gets the configured attribute of the CFSXMLWriter object
   *
   * @return    The configured value
   */
  public boolean isConfigured() {
    if (filename == null) {
      return false;
    }

    try {
      transformerFactory = TransformerFactory.newInstance();

      transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.setOutputProperty(OutputKeys.METHOD, "xml");
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

      out = new PrintWriter(
          new BufferedWriter(
          new FileWriter(filename, !overwrite)));
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
    out.println("<aspcfs>");
    out.println(" <cfsdata>");

    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  record  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean save(DataRecord record) {
    ++recordCount;
    try {
      Result result = new StreamResult(out);

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element drecord = document.createElement("dataRecord");

      drecord.setAttribute("name", record.getName());
      drecord.setAttribute("action", record.getAction());
      drecord.setAttribute("shareKey", String.valueOf(record.getShareKey()));

      Iterator fields = record.iterator();
      while (fields.hasNext()) {
        DataField thisField = (DataField) fields.next();
        Element dfield = document.createElement("dataField");
        dfield.setAttribute("name", thisField.getName());
        if (thisField.getAlias() != null) {
          dfield.setAttribute("alias", thisField.getAlias());
        }
        if (thisField.getValueLookup() != null) {
          dfield.setAttribute("valueLookup", thisField.getValueLookup());
        }
        if (thisField.getValue() != null) {
          dfield.appendChild(document.createTextNode(thisField.getValue()));
        }
        drecord.appendChild(dfield);
      }
      Source source = new DOMSource(drecord);
      transformer.transform(source, result);
    } catch (Exception e) {
      logger.info(e.toString());
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean commit() {
    return true;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean rollback() {
    return false;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean close() {
    out.println(" </cfsdata>");
    out.println("</aspcfs>");

    if (out != null) {
      out.close();
    }
    return true;
  }


  /**
   *  Description of the Method
   */
  public void initialize() {
  }


  /**
   *  Gets the version attribute of the CFSXMLWriter object
   *
   * @return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Description of the Method
   *
   * @param  record  Description of the Parameter
   * @return         Description of the Return Value
   */
  public boolean load(DataRecord record) {
    return false;
  }
}

