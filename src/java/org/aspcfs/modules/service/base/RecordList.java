package org.aspcfs.modules.service.base;

import java.util.*;
import org.w3c.dom.*;
import org.aspcfs.utils.XMLUtils;

/**
 *  Maintains a list of records copied from a ResultSet. Used for the XML API.
 *
 *@author     matt rajkowski
 *@created    May 29, 2002
 *@version    $Id$
 */
public class RecordList extends ArrayList {

  private String name = null;
  private int totalRecords = -1;


  /**
   *  Constructor for the RecordList object
   *
   *@param  tableName  Description of Parameter
   */
  public RecordList(String tableName) {
    name = tableName;
  }


  /**
   *  Constructor for the RecordList object from the specified recordList node;
   *  typically used in constructing a TransactionStatus object
   *
   *@param  recordListNode  Description of the Parameter
   */
  public RecordList(Element recordListNode) {
    //Attributes that a recordList might have
    this.setName(recordListNode.getAttribute("name"));
    this.setTotalRecords(recordListNode.getAttribute("count"));
    //Process each record in the recordList
    ArrayList recordNodes = XMLUtils.getElements(recordListNode, "record");
    Iterator i = recordNodes.iterator();
    while (i.hasNext()) {
      Element recordElement = (Element) i.next();
      Record thisRecord = new Record(recordElement);
      this.add(thisRecord);
    }
  }


  /**
   *  Sets the name attribute of the RecordList object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the totalRecords attribute of the RecordList object
   *
   *@param  tmp  The new totalRecords value
   */
  public void setTotalRecords(int tmp) {
    this.totalRecords = tmp;
  }


  /**
   *  Sets the totalRecords attribute of the RecordList object
   *
   *@param  tmp  The new totalRecords value
   */
  public void setTotalRecords(String tmp) {
    try {
      if (tmp != null) {
        this.totalRecords = Integer.parseInt(tmp);
      }
    } catch (Exception e) {
      this.totalRecords = -1;
    }
  }


  /**
   *  Gets the name attribute of the RecordList object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the totalRecords attribute of the RecordList object
   *
   *@return    The totalRecords value
   */
  public int getTotalRecords() {
    return totalRecords;
  }

}

