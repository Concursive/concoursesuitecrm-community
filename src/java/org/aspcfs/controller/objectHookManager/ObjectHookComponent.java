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

