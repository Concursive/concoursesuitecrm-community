package org.aspcfs.utils;

import org.aspcfs.apps.transfer.writer.cfshttpxmlwriter.CFSHttpXMLWriter;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Transfers data to/from a Centric CRM server
 *
 * @author     mrajkowski
 * @version    $Id: CRMConnection.java 11311 2005-04-18 09:13:29 -0400 (Mon, 18
 *      Apr 2005) mrajkowski $
 * @created    Apr 11, 2005
 */

public class CRMConnection extends CFSHttpXMLWriter {

  /**
   *  Constructor for the CRMConnection object
   */
  public CRMConnection() {
    this.setId("CRMConnection");
    this.setSystemId(4);
  }


  /**
   *  Gets the recordCount attribute of the CRMConnection object
   *
   * @return    The recordCount value
   */
  public int getRecordCount() {
    try {
      XMLUtils xml = new XMLUtils(getLastResponse());
      Element response = xml.getFirstChild("response");
      Element recordSet = xml.getFirstChild(response, "recordSet");
      if (recordSet != null && recordSet.hasAttributes()) {
        return (Integer.parseInt(recordSet.getAttribute("count")));
      }
    } catch (Exception e) {
    }
    return 0;
  }


  /**
   *  Gets the status attribute of the CRMConnection object
   *
   * @return    The status value
   */
  public int getStatus() {
    try {
      XMLUtils xml = new XMLUtils(getLastResponse());
      Element response = xml.getFirstChild("response");
      Element status = xml.getFirstChild(response, "status");
      if (status != null) {
        return (Integer.parseInt(xml.getNodeText(status)));
      }
    } catch (Exception e) {
    }
    return 1;
  }


  /**
   *  Gets the responseValue attribute of the CRMConnection object
   *
   * @param  fieldName  Description of the Parameter
   * @return            The responseValue value
   */
  public String getResponseValue(String fieldName) {
    try {
      XMLUtils xml = new XMLUtils(getLastResponse());
      Element response = xml.getFirstChild("response");
      Element recordSet = xml.getFirstChild(response, "recordSet");
      Element record = xml.getFirstChild(recordSet, "record");

      NodeList objectElements = record.getChildNodes();
      for (int j = 0; j < objectElements.getLength(); j++) {
        Node theObject = (Node) objectElements.item(j);
        if (theObject.getNodeType() == Node.ELEMENT_NODE) {
          //For each parameter/value pair, try to set the value on the object
          String param = theObject.getNodeName();
          String value = xml.getNodeText(theObject);

          if (param.equals(fieldName)) {
            return value;
          }
        }
      }
    } catch (Exception e) {
    }
    return null;
  }


  /**
   *  Gets the records attribute of the CRMConnection object
   *
   * @param  className  Description of the Parameter
   * @return            The records value
   */
  public ArrayList getRecords(String className) {
    int count = getRecordCount();
    ArrayList records = new ArrayList();
    try {
      ArrayList elements = new ArrayList();

      XMLUtils xml = new XMLUtils(getLastResponse());
      Element response = xml.getFirstChild("response");
      Element recordSet = xml.getFirstChild(response, "recordSet");
      xml.getAllChildren(recordSet, elements);

      Iterator i = elements.iterator();
      while (i.hasNext()) {
        Element element = (Element) i.next();
        Object object = Class.forName(className).newInstance();
        XMLUtils.populateObject(object, element);
        records.add(object);
      }
    } catch (Exception e) {
    }
    return records;
  }
}

