package org.aspcfs.modules.service.base;

/**
 *  Description of the Class
 *
 *@author     matt
 *@created    April 10, 2002
 *@version    $Id: TransactionStatus.java,v 1.1 2002/04/10 19:38:29 mrajkowski
 *      Exp $
 */
public class TransactionStatus {

  private int id = -1;
  private int statusCode = -1;
  private String message = null;
  private RecordList recordList = null;


  /**
   *  Constructor for the TransactionStatus object
   */
  public TransactionStatus() { }


  /**
   *  Constructor for the TransactionStatus object
   *
   *@param  statusCode  Description of Parameter
   */
  public TransactionStatus(int statusCode) {
    this.statusCode = statusCode;
  }


  /**
   *  Constructor for the TransactionStatus object
   *
   *@param  message  Description of Parameter
   */
  public TransactionStatus(String message) {
    this.message = message;
  }


  /**
   *  Constructor for the TransactionStatus object
   *
   *@param  statusCode  Description of Parameter
   *@param  message     Description of Parameter
   */
  public TransactionStatus(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }


  /**
   *  Sets the id attribute of the TransactionStatus object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    id = tmp;
  }


  /**
   *  Sets the statusCode attribute of the TransactionStatus object
   *
   *@param  tmp  The new statusCode value
   */
  public void setStatusCode(int tmp) {
    statusCode = tmp;
  }


  /**
   *  Sets the message attribute of the TransactionStatus object
   *
   *@param  tmp  The new message value
   */
  public void setMessage(String tmp) {
    message = tmp;
  }


  /**
   *  Sets the recordList attribute of the TransactionStatus object
   *
   *@param  tmp  The new recordList value
   */
  public void setRecordList(RecordList tmp) {
    recordList = tmp;
  }


  /**
   *  Gets the id attribute of the TransactionStatus object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the statusCode attribute of the TransactionStatus object
   *
   *@return    The statusCode value
   */
  public int getStatusCode() {
    return statusCode;
  }


  /**
   *  Gets the message attribute of the TransactionStatus object
   *
   *@return    The message value
   */
  public String getMessage() {
    return message;
  }


  /**
   *  Gets the recordList attribute of the TransactionStatus object
   *
   *@return    The recordList value
   */
  public RecordList getRecordList() {
    return recordList;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasError() {
    return statusCode > 0;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasRecordList() {
    return (recordList != null);
  }
}

