/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.utils.*;

public final class ProcessMessage extends CFSModule {

  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;

    ConnectionPool sqlDriver = null;
    Connection db = null;
    String id = context.getRequest().getParameter("id");

    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String dbName = st.nextToken();
      String messageId = st.nextToken();

      sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
      ConnectionElement ce = new ConnectionElement();
      //TODO: Remove this hard-coded url
      ce.setUrl("jdbc:postgresql://127.0.0.1:5432/" + dbName);
      ce.setUsername("cfsdba");
      ce.setPassword("");
      ce.setDbName(dbName);
      db = sqlDriver.getConnection(ce);

      Message thisMessage = new Message(db, messageId);
      context.getRequest().setAttribute("Message", thisMessage);

    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      if (sqlDriver != null && db != null) {
        sqlDriver.free(db);
      }
    }

    if (errorMessage != null) {
      return ("SystemError");
    } else {
      return ("PreviewOK");
    }
  }
}

