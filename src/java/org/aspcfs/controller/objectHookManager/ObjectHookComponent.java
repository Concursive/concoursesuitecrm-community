package com.darkhorseventures.controller;

import com.darkhorseventures.utils.*;
import java.sql.*;

/**
 *  Most components will extend this class to provide easy access to 
 *  database connections within a component, plus other useful methods.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
 */
public class ObjectHookComponent {

  /**
   *  Gets the connection attribute of the ObjectHookComponent class
   *
   *@param  context           Description of the Parameter
   *@return                   The connection value
   *@exception  SQLException  Description of the Exception
   */
  protected static Connection getConnection(ComponentContext context) throws SQLException {
    ConnectionPool sqlDriver = (ConnectionPool) context.getAttribute("ConnectionPool");
    ConnectionElement ce = (ConnectionElement) context.getAttribute("ConnectionElement");
    return sqlDriver.getConnection(ce);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@param  db       Description of the Parameter
   */
  protected static void freeConnection(ComponentContext context, Connection db) {
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
  protected static String getFileLibraryPath(ComponentContext context) {
    return context.getParameter("FileLibraryPath");
  }
}

