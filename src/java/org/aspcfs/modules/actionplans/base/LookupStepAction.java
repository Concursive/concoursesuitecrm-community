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
package org.aspcfs.modules.actionplans.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *  Represents a record entry in lookup_step_actions
 *
 * @author     mrajkowski
 * @created    February 2, 2006
 * @version    $Id: ActionStep.java,v 1.1.2.17.2.2 2005/09/16 21:12:13 partha
 *      Exp $
 */
public class LookupStepAction extends GenericBean {
  private int code = -1;
  private String description = null;
  private boolean defaultItem = false;
  private int level = 0;
  private boolean enabled = true;
  private int constantId = -1;

  public LookupStepAction() { }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isDefaultItem() {
    return defaultItem;
  }

  public void setDefaultItem(boolean defaultItem) {
    this.defaultItem = defaultItem;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public int getConstantId() {
    return constantId;
  }

  public void setConstantId(int constantId) {
    this.constantId = constantId;
  }

  public void insert(Connection db) throws SQLException {
    try {
      code = DatabaseUtils.getNextSeq(db, "lookup_step_actions_code_seq");
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO lookup_step_actions " +
              "(" + (code > -1 ? "code, " : "") + "description, default_item, " + DatabaseUtils.addQuotes(db, "level") + ", enabled, constant_id) VALUES " +
              "(" + (code > -1 ? "?, " : "") + "?, ?, ?, ?, ?) "
      );
      int i = 0;
      if (code > -1) {
        pst.setInt(++i, code);
      }
      pst.setString(++i, description);
      pst.setBoolean(++i, defaultItem);
      pst.setInt(++i, level);
      pst.setBoolean(++i, enabled);
      pst.setInt(++i, constantId);
      pst.execute();
      pst.close();
      code = DatabaseUtils.getCurrVal(db, "lookup_step_actions_code_seq", code);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("LookupStepAction-> insert error: " + e.getMessage());
      }
      throw new SQLException(e.getMessage());
    }
  }
}

