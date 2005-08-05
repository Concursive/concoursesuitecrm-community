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
package org.aspcfs.modules.service.base;

import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

/**
 * Represents the status for a processed or attempted to process transaction
 *
 * @author matt rajkowski
 * @version $Id: TransactionStatus.java,v 1.1 2002/04/10 19:38:29 mrajkowski
 *          Exp $
 * @created April 10, 2002
 */
public class TransactionStatus {

  private int id = -1;
  private int statusCode = -1;
  private String message = null;
  private RecordList recordList = null;


  /**
   * Constructor for the TransactionStatus object
   */
  public TransactionStatus() {
  }


  /**
   * Constructor for the TransactionStatus object
   *
   * @param statusCode Description of Parameter
   */
  public TransactionStatus(int statusCode) {
    this.statusCode = statusCode;
  }


  /**
   * Constructor for the TransactionStatus object
   *
   * @param message Description of Parameter
   */
  public TransactionStatus(String message) {
    this.message = message;
  }


  /**
   * Constructor for the TransactionStatus object
   *
   * @param statusCode Description of Parameter
   * @param message    Description of Parameter
   */
  public TransactionStatus(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }


  /**
   * Constructor for the TransactionStatus object
   *
   * @param responseNode Description of the Parameter
   */
  public TransactionStatus(Element responseNode) {
    this.setId(responseNode.getAttribute("id"));
    this.setStatusCode(
        XMLUtils.getNodeText(XMLUtils.getFirstChild(responseNode, "status")));
    this.setMessage(
        XMLUtils.getNodeText(
            XMLUtils.getFirstChild(responseNode, "errorText")));
    //Process the record list if there is one
    Element recordListNode = XMLUtils.getFirstChild(responseNode, "recordSet");
    if (recordListNode != null) {
      recordList = new RecordList(recordListNode);
    }
  }


  /**
   * Sets the id attribute of the TransactionStatus object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    id = tmp;
  }


  /**
   * Sets the id attribute of the TransactionStatus object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    try {
      id = Integer.parseInt(tmp);
    } catch (Exception e) {
      id = -1;
    }
  }


  /**
   * Sets the statusCode attribute of the TransactionStatus object
   *
   * @param tmp The new statusCode value
   */
  public void setStatusCode(int tmp) {
    statusCode = tmp;
  }


  /**
   * Sets the statusCode attribute of the TransactionStatus object
   *
   * @param tmp The new statusCode value
   */
  public void setStatusCode(String tmp) {
    try {
      statusCode = Integer.parseInt(tmp);
    } catch (Exception e) {
      statusCode = -1;
    }
  }


  /**
   * Sets the message attribute of the TransactionStatus object
   *
   * @param tmp The new message value
   */
  public void setMessage(String tmp) {
    message = tmp;
  }


  /**
   * Sets the recordList attribute of the TransactionStatus object
   *
   * @param tmp The new recordList value
   */
  public void setRecordList(RecordList tmp) {
    recordList = tmp;
  }


  /**
   * Gets the id attribute of the TransactionStatus object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the statusCode attribute of the TransactionStatus object
   *
   * @return The statusCode value
   */
  public int getStatusCode() {
    return statusCode;
  }


  /**
   * Gets the message attribute of the TransactionStatus object
   *
   * @return The message value
   */
  public String getMessage() {
    return message;
  }


  /**
   * Gets the recordList attribute of the TransactionStatus object
   *
   * @return The recordList value
   */
  public RecordList getRecordList() {
    return recordList;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public boolean hasError() {
    return statusCode > 0;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public boolean hasRecordList() {
    return (recordList != null);
  }
}

