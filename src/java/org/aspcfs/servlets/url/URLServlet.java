/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.servlets.url;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.asterisk.fastagi.DefaultAGIServer;

import org.apache.catalina.servlets.DefaultServlet;
import org.aspcfs.servlets.url.base.URLMap;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Description of the Class
 * 
 * @author holub
 * @version $Id: Exp $
 * @created Jul 17, 2006
 * 
 */
public class URLServlet extends DefaultServlet {
  private static final long serialVersionUID = 2509011996512101242L;

  public void doGet(ActionContext context)
      throws ServletException, IOException {
    HttpServletRequest request = context.getRequest();
    HttpServletResponse response = context.getResponse();
    URLMap urlMap = new URLMap();
    
    String[] urlAttrs = request.getPathInfo().split("/");

    String returnURL = "";
    Connection db = null;
    ConnectionPool connectionPool = null;
    try {
      connectionPool = new ConnectionPool();
      ConnectionElement ce =retrieveConnectionElement(context);
      db = connectionPool.getConnection(ce);
      urlMap.queryRecord(db, Integer.parseInt(urlAttrs[urlAttrs.length-1]));

      if (Long.parseLong(urlAttrs[urlAttrs.length-2]) == urlMap.getTimeInMillis()){
        returnURL += urlMap.getUrl();        
      }
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      if (db != null && connectionPool != null) {
        connectionPool.free(db);
      }
      db = null;
    }
    response.sendRedirect(returnURL);
  }
}
