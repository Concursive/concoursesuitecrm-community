package com.darkhorseventures.apps.dataimport.cfshttpxmlwriter;

import com.darkhorseventures.apps.dataimport.*;
import java.util.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *  Writes CFS data using an HTTP connection and passing objects as XML to
 *  a CFS web server.
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class CFSHttpXMLWriter implements DataWriter {
  private String url = null;
  private String id = null;
  private String code = null;
  private int systemId = -1;
  private int clientId = -1;

  private ArrayList transaction = new ArrayList();
  private boolean autoCommit = true;
  private int transactionCount = 0;
  private String lastResponse = null;
  
  /**
   *  Sets the url attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }
  
  /**
   *  Sets the id attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the code attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new code value
   */
  public void setCode(String tmp) {
    this.code = tmp;
  }


  /**
   *  Sets the systemId attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the systemId attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }
  
  public void setClientId(int tmp) {
    this.clientId = tmp;
  }
  
  public void setClientId(String tmp) {
    this.clientId = Integer.parseInt(tmp);
  }

  public void setAutoCommit(boolean flag) {
    autoCommit = flag;
    if (autoCommit && transaction.size() > 0) {
      commit();
    }
  }

  /**
   *  Gets the url attribute of the CFSHttpXMLWriter object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the id attribute of the CFSHttpXMLWriter object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Gets the code attribute of the CFSHttpXMLWriter object
   *
   *@return    The code value
   */
  public String getCode() {
    return code;
  }


  /**
   *  Gets the systemId attribute of the CFSHttpXMLWriter object
   *
   *@return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }
  
  public int getClientId() {
    return clientId;
  }


  /**
   *  Gets the version attribute of the CFSHttpXMLWriter object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the CFSHttpXMLWriter object
   *
   *@return    The name value
   */
  public String getName() {
    return "ASPCFS Web XML Data Writer";
  }


  /**
   *  Gets the description attribute of the CFSHttpXMLWriter object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Writes data to ASPCFS using the XML HTTP Web API";
  }
  
  public String getLastResponse() {
    return lastResponse;
  }


  /**
   *  Gets the configured attribute of the CFSHttpXMLWriter object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    if (url == null || id == null || code == null || systemId == -1) {
      return false;
    }
    
    //Setup a new client id for this data transfer session
    DataRecord clientRecord = new DataRecord();
    clientRecord.setName("syncClient");
    clientRecord.setAction("insert");
    clientRecord.addField("id", "-1");
    clientRecord.addField("type", "Java CFS Http XML Writer");
    clientRecord.addField("version", String.valueOf(this.getVersion()));
    this.save(clientRecord);
    
    try {
      XMLUtils responseXML = new XMLUtils(lastResponse, true);
      clientId = Integer.parseInt(XMLUtils.getNodeText(responseXML.getFirstElement("id")));
      logger.info("CFSHttpXMLWriter-> Client ID: " + clientId);
    } catch (Exception e) {
      e.printStackTrace(System.err);
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  data  Description of the Parameter
   *@return       Description of the Return Value
   */
  public boolean save(DataRecord record) {
    transaction.add(record);
    if (autoCommit) {
      return commit();
    }
    return true;
  }
  
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
      
      Element authCode = document.createElement("code");
      authCode.appendChild(document.createTextNode(code));
      auth.appendChild(authCode);
      
      Element authSystemId = document.createElement("systemId");
      authSystemId.appendChild(document.createTextNode(String.valueOf(systemId)));
      auth.appendChild(authSystemId);
      
      if (clientId > -1) {
        Element authClientId = document.createElement("clientId");
        authClientId.appendChild(document.createTextNode(String.valueOf(clientId)));
        auth.appendChild(authClientId);
      }
      
      //Process the records to be submitted
      Iterator dataRecordItems = transaction.iterator();
      while (dataRecordItems.hasNext()) {
        DataRecord record = (DataRecord)dataRecordItems.next();
        
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
          DataField thisField = (DataField)fieldItems.next();
          
          //Add the property to the meta node
          Element property = document.createElement("property");
          property.appendChild(document.createTextNode(thisField.getName()));
          meta.appendChild(property);
            
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
            field.appendChild(document.createTextNode(thisField.getValue()));
          }
          object.appendChild(field);
        }
      }
      
      lastResponse = HTTPUtils.sendPacket(url, XMLUtils.toString(document));
      this.transaction.clear();
      this.setAutoCommit(true);
    } catch (Exception ex) {
      logger.info(ex.toString());
      return false;
    }
    return true;
  }
  
  public boolean rollback() {
    transaction.clear();
    return true;
  }
}

