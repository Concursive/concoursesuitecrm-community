//Copyright 2004 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Provides a list of items that have editable categories for the specified
 *  module
 *
 *@author     matt rajkowski
 *@created    February 3, 2004
 *@version    $Id$
 */
public class CategoryEditorList extends ArrayList {
  protected int moduleId = -1;


  /**
   *  Constructor for the CategoryEditorList object
   */
  public CategoryEditorList() { }


  /**
   *  Sets the moduleId attribute of the CategoryEditorList object
   *
   *@param  moduleId  The new moduleId value
   */
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }


  /**
   *  Sets the moduleId attribute of the CategoryEditorList object
   *
   *@param  moduleId  The new moduleId value
   */
  public void setModuleId(String moduleId) {
    this.moduleId = Integer.parseInt(moduleId);
  }


  /**
   *  Gets the moduleId attribute of the CategoryEditorList object
   *
   *@return    The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT * " +
        "FROM category_editor_lookup " +
        "WHERE module_id = ? " +
        "ORDER BY level ");
    pst.setInt(1, moduleId);
    rs = pst.executeQuery();
    while (rs.next()) {
      CategoryEditor thisEditor = new CategoryEditor(rs);
      this.add(thisEditor);
    }
    rs.close();
    pst.close();
  }
}

