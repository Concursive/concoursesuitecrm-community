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
package org.aspcfs.controller.objectHookManager;

import com.darkhorseventures.database.*;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.apps.workFlowManager.*;
import org.aspcfs.utils.*;
import java.sql.*;

/**
 *  Most components will extend this class to provide easy access to database
 *  connections within a component, plus other useful methods.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: ObjectHookComponent.java,v 1.3 2003/01/13 21:41:16 mrajkowski
 *      Exp $
 */
public class ObjectHookComponent {

  /**
   *  Gets a database connection from the connection pool in the context
   *
   *@param  context           Description of the Parameter
   *@return                   The connection value
   *@exception  SQLException  Description of the Exception
   */
  public static Connection getConnection(ComponentContext context) throws SQLException {
    ConnectionPool sqlDriver = (ConnectionPool) context.getAttribute("ConnectionPool");
    ConnectionElement ce = (ConnectionElement) context.getAttribute("ConnectionElement");
    return sqlDriver.getConnection(ce);
  }


  /**
   *  Returns a database connection to the connection pool in the context
   *
   *@param  context  Description of the Parameter
   *@param  db       Description of the Parameter
   */
  public static void freeConnection(ComponentContext context, Connection db) {
    if (db != null) {
      ConnectionPool sqlDriver = (ConnectionPool) context.getAttribute("ConnectionPool");
      sqlDriver.free(db);
    }
    db = null;
  }


  /**
   *  Gets the fileLibraryPath attribute of the ObjectHookComponent class
   *
   *@param  context  Description of the Parameter
   *@return          The fileLibraryPath value
   */
  public static String getFileLibraryPath(ComponentContext context) {
    return context.getParameter("FileLibraryPath");
  }
}

