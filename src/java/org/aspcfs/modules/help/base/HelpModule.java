//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.help.base;

import com.darkhorseventures.framework.beans.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 *  Represents a Help Tip on a page in CFS
 *
 *@author     kbhoopal
 *@created    Nov 10, 2003
 *@version    $id:exp$
 */
public class HelpModule extends GenericBean {
  //static variables
  public static int DONE = 1;

  //properties
  private int id = -1;
  private int linkCategoryId = -1;
  private String briefDescription = null;  
  private String detailDescription = null;  
  private String moduleName = null;



  /**
   *Constructor for the HelpTip object
   */
  public HelpModule() { }



  /**
   *Constructor for the HelpTip object
   *
   *@param  db                Description of the Parameter
   *@param  thisId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpModule(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Tip ID not specified");
    }

    PreparedStatement pst = db.prepareStatement(
        "SELECT module_brief_description, module_detail_description, category " +
        "FROM help_module hm, permission_category pc " +
        "WHERE module_id = ? " +
	"AND hm.category_id = pc.category_id");

    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();

    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();

    if (System.getProperty("DEBUG") != null) {
      System.out.println("HelpModule-> HelpModule ok");
    }

    if (thisId == -1) {
      throw new SQLException("Module ID not found");
    }
  }


  /**
   *Constructor for the HelpTip object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpModule(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the HelpTip object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the HelpTip object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }
  
  
  /**
   *  Sets the module name attribute of the HelpModule object
   *
   *@param  tmp  The new section value
   */
  public void setModuleName(String tmp) {
    this.moduleName = tmp;
  }




  /**
   *  Sets the module name attribute of the HelpModule object
   *
   *@param  tmp  The new detail description value
   */
  public void setDetailDescription(String tmp) {
    this.detailDescription = tmp;
  }
  


  /**
   *  Sets the module name attribute of the HelpModule object
   *
   *@param  tmp  The new brief description value
   */
  public void setBriefDescription(String tmp) {
    this.briefDescription = tmp;
  }
  

  /**
   *  Gets the module Id of the HelpModule object
   *
   *@return    The module value
   */
  public int getId() {
    return id;
  }

  
  /**
   *  Gets the module attribute of the HelpModule object
   *
   *@return    The module name
   */
  public String getModuleName() {
    return moduleName;
  }

  /**
   *  Gets the module attribute of the HelpModule object
   *
   *@return    The Link Category Id
   */
  public int getLinkCategoryId(){
	return linkCategoryId;	  
  }

  /**
   *  Gets the brief description of the module of the HelpModule object
   *
   *@return    The brief description
   */
  public String getBriefDescription(){
     return briefDescription;
  }


  /**
   *  Gets the detail description of the module of the HelpModule object
   *
   *@return    The detail description
   */
  public String getDetailDescription(){
    return detailDescription;
  }
  
  
  /**
   *  Insert a Help Tip
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
 public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO help_module " +
          "(module_id, category_id , module_brief_description , module_detail_description) " +
          "VALUES (?, ?, ?, ?, ?) "
          );
      pst.setInt(++i, this.getId());
      pst.setInt(++i, this.getLinkCategoryId());
      pst.setString(++i, this.getBriefDescription());
      pst.setString(++i, this.getDetailDescription());
      pst.execute();
      this.id = DatabaseUtils.getCurrVal(db, "help_module_module_id_seq");
      pst.close();
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
   *  Update a Help Tip
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    String sql = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    int count = 0;
    if (id == -1) {
      throw new SQLException("Tip ID not specified");
    }

    try {
      db.setAutoCommit(false);
      HelpTip previousTip = new HelpTip(db, id);
      int i = 0;
      pst = db.prepareStatement(
          "UPDATE help_module " +
          "SET category_id = ?, module_brief_description = ?, module_detail_description = ? " +
          "WHERE module_id = ?"
          );
      pst.setInt(++i, this.getLinkCategoryId());
      pst.setString(++i, this.getBriefDescription());
      pst.setString(++i, this.getDetailDescription());
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


  /**
   *  Delete a Help Tip
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Tip ID not specified");
    }
    try {
      db.setAutoCommit(false);
      PreparedStatement pst = db.prepareStatement(
          "DELETE from help_module " +
          "WHERE module_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
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
   *  Build the record from the database result
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    briefDescription = rs.getString("module_brief_description");
    detailDescription = rs.getString("module_detail_description");
    moduleName = rs.getString("category");
  }
}

