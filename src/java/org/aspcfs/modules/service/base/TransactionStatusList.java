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

import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 *  Contains TransactionStatus objects
 *
 *@author     matt rajkowski
 *@created    November 26, 2003
 *@version    $Id: TransactionStatusList.java,v 1.1 2003/11/26 21:06:50
 *      mrajkowski Exp $
 */
public class TransactionStatusList extends ArrayList {

  /**
   *  Constructor for the TransactionStatusList object
   */
  public TransactionStatusList() { }


  /**
   *  Description of the Method
   *
   *@param  document  Description of the Parameter
   *@param  root      Description of the Parameter
   *@return           Description of the Return Value
   */
  public int appendResponse(Document document, Element root) {
    int recordCount = 0;
    Iterator messages = this.iterator();
    while (messages.hasNext()) {
      TransactionStatus thisMessage = (TransactionStatus) messages.next();
      //Append the response
      Element response = document.createElement("response");
      if (thisMessage.getId() > -1) {
        response.setAttribute("id", String.valueOf(thisMessage.getId()));
      }
      root.appendChild(response);
      //Append the status code (0=OK, 1=Error)
      Element status = document.createElement("status");
      status.appendChild(document.createTextNode(String.valueOf(thisMessage.getStatusCode())));
      response.appendChild(status);
      //Append the errorText
      Element errorText = document.createElement("errorText");
      if (thisMessage.getStatusCode() > 0) {
        errorText.appendChild(document.createTextNode(thisMessage.getMessage()));
      }
      response.appendChild(errorText);
      //Append any record lists to be returned
      if (!thisMessage.hasError() && thisMessage.hasRecordList()) {
        Element recordSet = document.createElement("recordSet");
        recordSet.setAttribute("name", thisMessage.getRecordList().getName());
        recordSet.setAttribute("count", String.valueOf(thisMessage.getRecordList().size()));
        if (thisMessage.getRecordList().getTotalRecords() > -1) {
          recordSet.setAttribute("total", String.valueOf(thisMessage.getRecordList().getTotalRecords()));
        }
        response.appendChild(recordSet);
        recordCount += thisMessage.getRecordList().size();
        Iterator recordList = thisMessage.getRecordList().iterator();
        while (recordList.hasNext()) {
          Element record = document.createElement("record");
          recordSet.appendChild(record);
          Record thisRecord = (Record) recordList.next();
          if (thisRecord.hasAction()) {
            record.setAttribute("action", thisRecord.getAction());
          }
          Iterator fields = thisRecord.keySet().iterator();
          while (fields.hasNext()) {
            String fieldName = (String) fields.next();
            String fieldValue = (String) thisRecord.get(fieldName);
            if (fieldValue == null) {
              fieldValue = "";
            }
            Element field = document.createElement(fieldName);
            field.appendChild(document.createTextNode(fieldValue));
            record.appendChild(field);
          }
        }
      }
    }
    return recordCount;
  }
}

