//Copyright 2002-2003 Dark Horse Ventures

package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    May 23, 2003
 *@version    $id: exp$
 */
public class TicketCategoryDraft extends GenericBean {

  private int id = -1;
  private int actualCatId = -1;
  private int categoryLevel = -1;
  private int parentCode = -1;
  private String description = "";
  private boolean enabled = true;
  private int level = 0;
  private TicketCategoryDraftList shortChildList = new TicketCategoryDraftList();


  /**
   *Constructor for the TicketCategoryDraft object
   */
  public TicketCategoryDraft() { }


  /**
   *Constructor for the TicketCategoryDraft object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraft(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *Constructor for the TicketCategoryDraft object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TicketCategoryDraft(Connection db, int id) throws SQLException {
    if (id < 0) {
      throw new SQLException("Ticket Category not specified");
    }
    String sql =
        "SELECT tc.* " +
        "FROM ticket_category_draft tc " +
        "WHERE tc.id > -1 " +
        "AND tc.id = ? ";
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("Ticket Category Draft record not found.");
    }
  }


  /**
   *  Sets the Code attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new Code value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryLevel attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(int tmp) {
    this.categoryLevel = tmp;
  }


  /**
   *  Sets the categoryLevel attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new categoryLevel value
   */
  public void setCategoryLevel(String tmp) {
    this.categoryLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Level attribute of the TicketCategoryDraft object
   *
   *@param  level  The new Level value
   *@since
   */
  public void setLevel(int level) {
    this.level = level;
  }


  /**
   *  Sets the Level attribute of the TicketCategoryDraft object
   *
   *@param  level  The new Level value
   *@since
   */
  public void setLevel(String level) {
    this.level = Integer.parseInt(level);
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new ParentCode value
   *@since
   */
  public void setParentCode(int tmp) {
    this.parentCode = tmp;
  }


  /**
   *  Sets the ParentCode attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new ParentCode value
   *@since
   */
  public void setParentCode(String tmp) {
    this.parentCode = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Description attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new Description value
   *@since
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the Enabled attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the TicketCategoryDraft object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the actualCatId attribute of the TicketCategoryDraft object
   *
   *@param  actualCatId  The new actualCatId value
   */
  public void setActualCatId(int actualCatId) {
    this.actualCatId = actualCatId;
  }


  /**
   *  Sets the actualCatId attribute of the TicketCategoryDraft object
   *
   *@param  actualCatId  The new actualCatId value
   */
  public void setActualCatId(String actualCatId) {
    this.actualCatId = Integer.parseInt(actualCatId);
  }


  /**
   *  Sets the shortChildList attribute of the TicketCategoryDraft object
   *
   *@param  shortChildList  The new shortChildList value
   */
  public void setShortChildList(TicketCategoryDraftList shortChildList) {
    this.shortChildList = shortChildList;
  }


  /**
   *  Gets the shortChildList attribute of the TicketCategoryDraft object
   *
   *@return    The shortChildList value
   */
  public TicketCategoryDraftList getShortChildList() {
    return shortChildList;
  }


  /**
   *  Gets the actualCatId attribute of the TicketCategoryDraft object
   *
   *@return    The actualCatId value
   */
  public int getActualCatId() {
    return actualCatId;
  }


  /**
   *  Gets the Level attribute of the TicketCategoryDraft object
   *
   *@return    The Level value
   *@since
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the Code attribute of the TicketCategoryDraft object
   *
   *@return    The Code value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the categoryLevel attribute of the TicketCategoryDraft object
   *
   *@return    The categoryLevel value
   */
  public int getCategoryLevel() {
    return categoryLevel;
  }


  /**
   *  Gets the ParentCode attribute of the TicketCategoryDraft object
   *
   *@return    The ParentCode value
   *@since
   */
  public int getParentCode() {
    return parentCode;
  }


  /**
   *  Gets the Description attribute of the TicketCategoryDraft object
   *
   *@return    The Description value
   *@since
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Enabled attribute of the TicketCategoryDraft object
   *
   *@return    The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO ticket_category_draft " +
          "(cat_level, link_id, parent_cat_code, description, level, enabled) " +
          "VALUES (?, ?, ?, ?, ?, ?) "
          );
      pst.setInt(++i, this.getCategoryLevel());
      pst.setInt(++i, this.getActualCatId());
      if (parentCode > 0) {
        pst.setInt(++i, this.getParentCode());
      } else {
        pst.setInt(++i, 0);
      }
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getLevel());
      pst.setBoolean(++i, this.getEnabled());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "ticket_category_draft_id_seq");
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("Id not specified");
    }
    int i = 0;
    int count = 0;
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "UPDATE ticket_category_draft " +
          "SET description = ?, cat_level = ?, level = ?, enabled = ? " +
          "WHERE  id = ? ");
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getCategoryLevel());
      pst.setInt(++i, this.getLevel());
      pst.setBoolean(++i, this.getEnabled());
      pst.setInt(++i, this.getId());
      count = pst.executeUpdate();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return count;
  }


  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Category Id not specified");
    }

    int recordCount = 0;

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM ticket_category_draft " +
        "WHERE id = ? ");
    pst.setInt(1, id);
    recordCount = pst.executeUpdate();
    pst.close();

    if (recordCount == 0) {
      errors.put("actionError", "Category could not be deleted because it no longer exists.");
      return false;
    } else {
      return true;
    }
  }
  
  /**
   *  Description of the Method
   *
   *@param  childId  Description of the Parameter
   */
  public void removeChild(int childId) {
    TicketCategoryDraft child = null;
    Iterator i = shortChildList.iterator();
    while (i.hasNext()) {
      TicketCategoryDraft tmpCategory = (TicketCategoryDraft) i.next();
      if (tmpCategory.getId() == childId) {
        child = tmpCategory;
      }
    }
    if (child != null) {
      shortChildList.remove(child);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    actualCatId = rs.getInt("link_id");
    categoryLevel = rs.getInt("cat_level");
    parentCode = rs.getInt("parent_cat_code");
    description = rs.getString("description");
    level = rs.getInt("level");
    enabled = rs.getBoolean("enabled");
  }
}

