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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Provides a list of items that have editable categories for the specified
 * module
 *
 * @author matt rajkowski
 * @version $Id$
 * @created February 3, 2004
 */
public class CategoryEditorList extends ArrayList {
  protected int moduleId = -1;


  /**
   * Constructor for the CategoryEditorList object
   */
  public CategoryEditorList() {
  }


  /**
   * Sets the moduleId attribute of the CategoryEditorList object
   *
   * @param moduleId The new moduleId value
   */
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }


  /**
   * Sets the moduleId attribute of the CategoryEditorList object
   *
   * @param moduleId The new moduleId value
   */
  public void setModuleId(String moduleId) {
    this.moduleId = Integer.parseInt(moduleId);
  }


  /**
   * Gets the moduleId attribute of the CategoryEditorList object
   *
   * @return The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    pst = db.prepareStatement(
        "SELECT * " +
        "FROM category_editor_lookup " +
        "WHERE module_id = ? " +
        "ORDER BY \"level\" ");
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
