package org.aspcfs.modules.service.base;

import java.util.*;
import org.w3c.dom.*;
import org.aspcfs.utils.XMLUtils;

/**
 *  Represents a database record, with fields, used in the XML API.
 *
 *@author     matt rajkowski
 *@created    May 29, 2002
 *@version    $Id$
 */
public class Record extends LinkedHashMap {

  private int recordId = -1;
  private String action = null;


  /**
   *  Constructor for the Record object
   */
  public Record() { }


  /**
   *  Constructor for the Record object
   *
   *@param  thisAction  Description of Parameter
   */
  public Record(String thisAction) {
    action = thisAction;
  }


  /**
   *  Constructor for the Record object
   *
   *@param  recordNode  Description of the Parameter
   */
  public Record(Element recordNode) {
    //Attributes that a record might have
    this.setAction(recordNode.getAttribute("action"));
    //Process each property in the record
    ArrayList propertyElements = new ArrayList();
    XMLUtils.getAllChildren(recordNode, propertyElements);
    Iterator i = propertyElements.iterator();
    while (i.hasNext()) {
      Element property = (Element) i.next();
      this.put(property.getNodeName(), XMLUtils.getNodeText(property));
    }
  }


  /**
   *  Sets the action attribute of the Record object
   *
   *@param  tmp  The new action value
   */
  public void setAction(String tmp) {
    this.action = tmp;
  }


  /**
   *  Sets the recordId attribute of the Record object
   *
   *@param  tmp  The new recordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   *  Sets the recordId attribute of the Record object
   *
   *@param  tmp  The new recordId value
   */
  public void setRecordId(Integer tmp) {
    this.recordId = tmp.intValue();
  }


  /**
   *  Sets the recordId attribute of the Record object
   *
   *@param  tmp  The new recordId value
   */
  public void setRecordId(String tmp) {
    this.recordId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the action attribute of the Record object
   *
   *@return    The action value
   */
  public String getAction() {
    return action;
  }


  /**
   *  Gets the recordId attribute of the Record object
   *
   *@return    The recordId value
   */
  public int getRecordId() {
    return recordId;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasAction() {
    return (action != null);
  }


  /**
   *  Description of the Method
   *
   *@param  name   Description of the Parameter
   *@param  value  Description of the Parameter
   */
  public void put(String name, int value) {
    this.put(name, String.valueOf(value));
  }
}

