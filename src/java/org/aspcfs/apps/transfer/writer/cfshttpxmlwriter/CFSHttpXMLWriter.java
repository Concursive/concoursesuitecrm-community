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
package org.aspcfs.apps.transfer.writer.cfshttpxmlwriter;

import org.aspcfs.apps.transfer.DataField;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Writes data using an HTTP connection and passing objects as XML to a
 * web server.
 *
 * @author matt rajkowski
 * @version $Id: CFSHttpXMLWriter.java,v 1.6 2002/10/07 19:04:00 mrajkowski
 *          Exp $
 * @created September 3, 2002
 */
public class CFSHttpXMLWriter implements DataWriter {
  private String url = null;
  private String id = null;
  private String code = null;
  private int systemId = -1;
  private int clientId = -1;
  private String username = null;

  private ArrayList transaction = new ArrayList();
  private ArrayList transactionMeta = new ArrayList();

  private boolean autoCommit = true;
  private int transactionCount = 0;
  private String lastResponse = null;

  private boolean ignoreClientId = false;


  /**
   *  Gets the transactionMeta attribute of the CFSHttpXMLWriter object
   *
   * @return    The transactionMeta value
   */
  public ArrayList getTransactionMeta() {
    return transactionMeta;
  }


  /**
   *  Sets the transactionMeta attribute of the CFSHttpXMLWriter object
   *
   * @param  tmp  The new transactionMeta value
   */
  public void setTransactionMeta(ArrayList tmp) {
    this.transactionMeta = tmp;
  }


  /**
   * Gets the username attribute of the CFSHttpXMLWriter object
   *
   * @return The username value
   */
  public String getUsername() {
    return username;
  }


  /**
   * Sets the username attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new username value
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   * Sets the url attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new url value
   */
  public void setUrl(String tmp) {
    if (tmp != null && tmp.indexOf("ProcessPacket.do") == -1) {
      this.url = tmp + "/ProcessPacket.do";
    } else {
      this.url = tmp;
    }
  }


  /**
   * Sets the id attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   * Sets the code attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new code value
   */
  public void setCode(String tmp) {
    this.code = tmp;
  }


  /**
   * Sets the systemId attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   * Sets the systemId attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }


  /**
   * Sets the clientId attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new clientId value
   */
  public void setClientId(int tmp) {
    this.clientId = tmp;
  }


  /**
   * Sets the clientId attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new clientId value
   */
  public void setClientId(String tmp) {
    this.clientId = Integer.parseInt(tmp);
  }


  /**
   * Sets the autoCommit attribute of the CFSHttpXMLWriter object
   *
   * @param flag The new autoCommit value
   */
  public void setAutoCommit(boolean flag) {
    autoCommit = flag;
    if (autoCommit && transaction.size() > 0) {
      commit();
    }
  }


  /**
   * Sets the ignoreClientId attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new ignoreClientId value
   */
  public void setIgnoreClientId(boolean tmp) {
    this.ignoreClientId = tmp;
  }


  /**
   * Sets the ignoreClientId attribute of the CFSHttpXMLWriter object
   *
   * @param tmp The new ignoreClientId value
   */
  public void setIgnoreClientId(String tmp) {
    this.ignoreClientId = "true".equals(tmp);
  }


  /**
   * Gets the ignoreClientId attribute of the CFSHttpXMLWriter object
   *
   * @return The ignoreClientId value
   */
  public boolean getIgnoreClientId() {
    return ignoreClientId;
  }


  /**
   * Gets the url attribute of the CFSHttpXMLWriter object
   *
   * @return The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   * Gets the id attribute of the CFSHttpXMLWriter object
   *
   * @return The id value
   */
  public String getId() {
    return id;
  }


  /**
   * Gets the code attribute of the CFSHttpXMLWriter object
   *
   * @return The code value
   */
  public String getCode() {
    return code;
  }


  /**
   * Gets the systemId attribute of the CFSHttpXMLWriter object
   *
   * @return The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   * Gets the clientId attribute of the CFSHttpXMLWriter object
   *
   * @return The clientId value
   */
  public int getClientId() {
    return clientId;
  }


  /**
   * Gets the version attribute of the CFSHttpXMLWriter object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the CFSHttpXMLWriter object
   *
   * @return The name value
   */
  public String getName() {
    return "Centric CRM Web XML Data Writer";
  }


  /**
   * Gets the description attribute of the CFSHttpXMLWriter object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Writes data to Centric CRM using the XML HTTP Web API";
  }


  /**
   * Gets the lastResponse attribute of the CFSHttpXMLWriter object
   *
   * @return The lastResponse value
   */
  public String getLastResponse() {
    return lastResponse;
  }


  /**
   * Gets the configured attribute of the CFSHttpXMLWriter object
   *
   * @return The configured value
   */
  public boolean isConfigured() {
    if (url == null || id == null || code == null || systemId == -1) {
      logger.info("Not configured correctly");
      return false;
    }

    if (ignoreClientId) {
      return true;
    }

    //Setup a new client id for this data transfer session
    if (clientId == -1) {
      clientId = retrieveNewClientId();
      if (clientId == -1) {
        return false;
      }
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int retrieveNewClientId() {
    ignoreClientId = true;
    if (!isConfigured()) {
      return -1;
    }
    DataRecord clientRecord = new DataRecord();
    clientRecord.setName("syncClient");
    clientRecord.setAction("insert");
    clientRecord.addField("id", "-1");
    clientRecord.addField("type", "Java Centric CRM Http XML Writer");
    clientRecord.addField("code", code);
    clientRecord.addField("version", String.valueOf(this.getVersion()));
    this.save(clientRecord);
    try {
      System.out.println("CFSHttpXMLWriter-> " + lastResponse);
      XMLUtils responseXML = new XMLUtils(lastResponse, true);
      clientId = (Integer.parseInt(
          XMLUtils.getNodeText(responseXML.getFirstElement("id"))));
      ignoreClientId = true;
      return clientId;
    } catch (Exception e) {
      e.printStackTrace(System.err);
      return -1;
    }
  }


  /**
   * Description of the Method
   *
   * @param record Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean save(DataRecord record) {
    transaction.add(record);
    if (autoCommit) {
      return commit();
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasMetaInfo() {
    return (transactionMeta != null 
                && transactionMeta.size() > 0);
  }


  /**
   *  Sets the authenticationInfo attribute of the CFSHttpXMLWriter object
   *
   * @param  auth  The new authenticationInfo value
   */
  public void setAuthenticationInfo(AuthenticationItem auth) {
    url = auth.getUrl();
    id = auth.getId();
    systemId = auth.getSystemId();
    clientId = auth.getClientId();
    username = auth.getUsername();
    code = auth.getCode();
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean commit() {
    try {
      //Construct XML insert
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("app");
      document.appendChild(app);

      //Add the authentication
      Element auth = document.createElement("authentication");
      app.appendChild(auth);

      Element authId = document.createElement("id");
      authId.appendChild(document.createTextNode(id));
      auth.appendChild(authId);

      if (username != null) {
        Element authUsername = document.createElement("username");
        authUsername.appendChild(document.createTextNode(username));
        auth.appendChild(authUsername);
      }

      Element authCode = document.createElement("code");
      authCode.appendChild(document.createTextNode(code));
      auth.appendChild(authCode);

      Element authSystemId = document.createElement("systemId");
      authSystemId.appendChild(
          document.createTextNode(String.valueOf(systemId)));
      auth.appendChild(authSystemId);

      if (clientId > -1) {
        Element authClientId = document.createElement("clientId");
        authClientId.appendChild(
            document.createTextNode(String.valueOf(clientId)));
        auth.appendChild(authClientId);
      }

      //Begin the transaction
      Element trans = document.createElement("transaction");
      trans.setAttribute("id", String.valueOf(++transactionCount));
      app.appendChild(trans);

      //Process explicit meta information if specified
      if (hasMetaInfo()) {
        //Add the meta node: fields that will be returned
        Element meta = document.createElement("meta");
        trans.appendChild(meta);
        Iterator properties = transactionMeta.iterator();
        while (properties.hasNext()) {
          String prop = (String) properties.next();
          Element property = document.createElement("property");
          property.appendChild(document.createTextNode(prop));
          meta.appendChild(property);
        }
      }

      //Process the records to be submitted
      Iterator dataRecordItems = transaction.iterator();
      int recordCount = 0;
      while (dataRecordItems.hasNext()) {
        ++recordCount;
        DataRecord record = (DataRecord) dataRecordItems.next();

        //If meta info not specified then add the meta node: fields that will be returned
        Element meta = document.createElement("meta");
        if (recordCount == 1 && !hasMetaInfo()) {
          trans.appendChild(meta);
        }

        //Add the object node
        Element object = document.createElement(record.getName());
        object.setAttribute("action", record.getAction());
        if (record.getShareKey()) {
          object.setAttribute("shareKey", "true");
        }
        trans.appendChild(object);

        Iterator fieldItems = record.iterator();
        while (fieldItems.hasNext()) {
          DataField thisField = (DataField) fieldItems.next();

          //Add the property to the meta node
          if (recordCount == 1 && !hasMetaInfo()) {
            Element property = document.createElement("property");
            property.appendChild(document.createTextNode(thisField.getName()));
            meta.appendChild(property);
          }

          //Add the field to the object node
          Element field = null;
          if (thisField.hasAlias()) {
            field = document.createElement(thisField.getAlias());
          } else {
            field = document.createElement(thisField.getName());
          }
          if (thisField.hasValueLookup()) {
            field.setAttribute("lookup", thisField.getValueLookup());
          }
          if (thisField.hasValue()) {
            field.appendChild(
                document.createCDATASection(thisField.getValue()));
          }
          object.appendChild(field);
        }
      }
      lastResponse = HTTPUtils.sendPacket(url, XMLUtils.toString(document));
      this.transaction.clear();
      this.setAutoCommit(true);
    } catch (Exception ex) {
      logger.info(ex.toString());
      ex.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean rollback() {
    transaction.clear();
    return true;
  }


  /**
   * Reads data from the writer source instead of saving
   *
   * @param record Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean load(DataRecord record) {
    try {
      //Construct XML insert
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document document = builder.newDocument();
      Element app = document.createElement("app");
      document.appendChild(app);

      //Add the authentication
      Element auth = document.createElement("authentication");
      app.appendChild(auth);

      Element authId = document.createElement("id");
      authId.appendChild(document.createTextNode(id));
      auth.appendChild(authId);

      Element authCode = document.createElement("code");
      authCode.appendChild(document.createTextNode(code));
      auth.appendChild(authCode);

      Element authSystemId = document.createElement("systemId");
      authSystemId.appendChild(
          document.createTextNode(String.valueOf(systemId)));
      auth.appendChild(authSystemId);

      if (clientId > -1) {
        Element authClientId = document.createElement("clientId");
        authClientId.appendChild(
            document.createTextNode(String.valueOf(clientId)));
        auth.appendChild(authClientId);
      }

      //Read the record
      //Begin the transaction
      Element transaction = document.createElement("transaction");
      transaction.setAttribute("id", String.valueOf(++transactionCount));
      app.appendChild(transaction);

      //Add the meta node: fields that will be returned
      Element meta = document.createElement("meta");
      transaction.appendChild(meta);

      //Add the object node
      Element object = document.createElement(record.getName());
      object.setAttribute("action", record.getAction());
      transaction.appendChild(object);

      Iterator fieldItems = record.iterator();
      while (fieldItems.hasNext()) {
        DataField thisField = (DataField) fieldItems.next();

        //Add the property to the meta node
        Element property = document.createElement("property");
        property.appendChild(document.createTextNode(thisField.getName()));
        meta.appendChild(property);

        //Ignore the field to add to the object node
        /*
         *  Element field = null;
         *  if (thisField.hasAlias()) {
         *  field = document.createElement(thisField.getAlias());
         *  } else {
         *  field = document.createElement(thisField.getName());
         *  }
         *  if (thisField.hasValueLookup()) {
         *  field.setAttribute("lookup", thisField.getValueLookup());
         *  }
         *  if (thisField.hasValue()) {
         *  field.appendChild(document.createTextNode(thisField.getValue()));
         *  }
         *  object.appendChild(field);
         */
      }
      lastResponse = HTTPUtils.sendPacket(url, XMLUtils.toString(document));
    } catch (Exception e) {
      e.printStackTrace(System.out);
      lastResponse = "Exception: " + e.getMessage();
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean close() {
    return true;
  }
}

