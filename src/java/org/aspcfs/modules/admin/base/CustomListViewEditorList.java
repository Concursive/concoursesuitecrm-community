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
package org.aspcfs.modules.admin.base;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    November 9, 2005
 */
public class CustomListViewEditorList extends ArrayList {
  private int moduleId = -1;


  /**
   *  Gets the moduleId attribute of the CustomListViewEditorList object
   *
   * @return    The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   *  Sets the moduleId attribute of the CustomListViewEditorList object
   *
   * @param  tmp  The new moduleId value
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   *  Sets the moduleId attribute of the CustomListViewEditorList object
   *
   * @param  tmp  The new moduleId value
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }



  /**
   *  Constructor for the CustomListViewEditorList object
   */
  public CustomListViewEditorList() { }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT * " +
        "FROM custom_list_view_editor " +
        "WHERE module_id = ? " +
        "ORDER BY \"level\" ");
    pst.setInt(1, moduleId);
    rs = pst.executeQuery();
    while (rs.next()) {
      CustomListViewEditor thisEditor = new CustomListViewEditor(rs);
      this.add(thisEditor);
    }
    rs.close();
    pst.close();
  }
}

