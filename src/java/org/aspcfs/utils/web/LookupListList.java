//Copyright 2001 Dark Horse Ventures

package org.aspcfs.utils.web;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  LookupListList is a list of lookuplists for a particular module.
 *
 *@author     Mathur
 *@created    December 18, 2002
 *@version    $Id: LookupListList.java,v 1.2 2003/01/10 16:17:48 mrajkowski Exp
 *      $
 */
public class LookupListList extends HtmlSelect {
  protected int moduleId = -1;
  protected int userId;


  /**
   *  Constructor for the LookupListList object
   */
  public LookupListList() { }


  /**
   *  Constructor for the LookupListList object
   *
   *@param  db                Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public LookupListList(Connection db, int moduleId) throws SQLException {
    this.moduleId = moduleId;
    buildList(db);
  }


  /**
   *  Sets the moduleId attribute of the LookupListList object
   *
   *@param  moduleId  The new moduleId value
   */
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }


  /**
   *  Sets the userId attribute of the LookupListList object
   *
   *@param  userId  The new userId value
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }


  /**
   *  Gets the userId attribute of the LookupListList object
   *
   *@return    The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   *  Gets the moduleId attribute of the LookupListList object
   *
   *@return    The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   *  Builds a list of lookuplistElements for a module.
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT * " +
        "FROM lookup_lists_lookup " +
        "WHERE module_id = ? " +
        "ORDER BY level ");
    pst.setInt(1, moduleId);
    rs = pst.executeQuery();
    while (rs.next()) {
      LookupListElement thisElement = new LookupListElement(rs);
      this.add(thisElement);
    }
    rs.close();
    pst.close();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupListElement thisLookup = (LookupListElement) i.next();
      thisLookup.buildLookupList(db, userId);
    }
  }


  /**
   *  Scans the list for a LookupListElement entry.
   *
   *@param  lookupId  Description of the Parameter
   *@return           The element value
   */
  public LookupListElement getElement(int lookupId) {
    LookupListElement thisElement = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupListElement le = (LookupListElement) i.next();
      if (le.getLookupId() == lookupId) {
        thisElement = le;
        break;
      }
    }
    return thisElement;
  }
}

