/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package org.aspcfs.modules.healthcare.edit.actions;

import org.aspcfs.modules.actions.CFSModule;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.service.base.*;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.healthcare.edit.base.TransactionRecord;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    February 11, 2003
 *@version    $Id$
 */
public final class ProcessTransaction extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    String values = null;
    TransactionRecord tr = null;
    boolean recordInserted = false;
    //create a new tramsacton record from the request
    tr = new TransactionRecord();
    if (!tr.buildRecord(context.getRequest())) {
      processErrors(context, tr.getErrors());
      return ("PacketOK");
    }
    try {
      //get a database connection
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      //try to insert
      recordInserted = tr.insert(db, context);
      if (recordInserted) {
        context.getRequest().setAttribute("Transaction", tr);
      } else {
        processErrors(context, tr.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }
    return ("PacketOK");
  }
}

