//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.help.base;

import com.darkhorseventures.framework.beans.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    November 11, 2003
 *@version    $Id$
 */
public class HelpTableOfContentItem extends GenericBean {
  private int id = -1;
  private String displayText = null;
  private int firstChild = -1;
  private int nextSibling = -1;
  private int parent = -1;
  private int linkToCategoryId = -1;
  private int contentLevel = -1;
  private int contentOrder = -1;
  private int enteredBy = -1;
  private java.sql.Timestamp entered = null;
  private int modifiedBy = -1;
  private java.sql.Timestamp modified = null;
  private boolean enabled = true;

  private HelpTableOfContentItemLinks hTOCLinks;


  /**
   *  Constructor for the HelpTableOfContentItem object
   */
  public HelpTableOfContentItem() { }


  /**
   *  Constructor for the HelpTableOfContentItem object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpTableOfContentItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the HelpTableOfContentItem object
   *
   *@param  db                Description of the Parameter
   *@param  thisId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpTableOfContentItem(Connection db, int thisId) throws SQLException {
    if (thisId == -1) {
      throw new SQLException("Content ID not specified");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT t.* " +
        "FROM help_tableof_contents t " +
        "WHERE content_id = ? ");
    int i = 0;
    pst.setInt(++i, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (thisId == -1) {
      throw new SQLException("Content ID not found");
    }
  }


  /**
   *  Sets the id attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the displayText attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new displayText value
   */
  public void setDisplayText(String tmp) {
    this.displayText = tmp;
  }


  /**
   *  Sets the firstChild attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new firstChild value
   */
  public void setFirstChild(int tmp) {
    this.firstChild = tmp;
  }


  /**
   *  Sets the firstChild attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new firstChild value
   */
  public void setFirstChild(String tmp) {
    this.firstChild = Integer.parseInt(tmp);
  }


  /**
   *  Sets the nextSibling attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new nextSibling value
   */
  public void setNextSibling(int tmp) {
    this.nextSibling = tmp;
  }


  /**
   *  Sets the nextSibling attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new nextSibling value
   */
  public void setNextSibling(String tmp) {
    this.nextSibling = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parent attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new parent value
   */
  public void setParent(int tmp) {
    this.parent = tmp;
  }


  /**
   *  Sets the parent attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new parent value
   */
  public void setParent(String tmp) {
    this.parent = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkToCategoryId attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new linkToCategoryId value
   */
  public void setLinkToCategoryId(int tmp) {
    this.linkToCategoryId = tmp;
  }


  /**
   *  Sets the linkToCategoryId attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new linkToCategoryId value
   */
  public void setLinkToCategoryId(String tmp) {
    this.linkToCategoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contentLevel attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new contentLevel value
   */
  public void setContentLevel(int tmp) {
    this.contentLevel = tmp;
  }


  /**
   *  Sets the contentLevel attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new contentLevel value
   */
  public void setContentLevel(String tmp) {
    this.contentLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contentOrder attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new contentOrder value
   */
  public void setContentOrder(int tmp) {
    this.contentOrder = tmp;
  }


  /**
   *  Sets the contentOrder attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new contentOrder value
   */
  public void setContentOrder(String tmp) {
    this.contentOrder = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enabled attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the hTOCLinks attribute of the HelpTableOfContentItem object
   *
   *@param  tmp  The new hTOCLinks value
   */
  public void setHTOCLinks(HelpTableOfContentItemLinks tmp) {
    this.hTOCLinks = tmp;
  }


  /**
   *  Gets the id attribute of the HelpTableOfContentItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the displayText attribute of the HelpTableOfContentItem object
   *
   *@return    The displayText value
   */
  public String getDisplayText() {
    return displayText;
  }


  /**
   *  Gets the firstChild attribute of the HelpTableOfContentItem object
   *
   *@return    The firstChild value
   */
  public int getFirstChild() {
    return firstChild;
  }


  /**
   *  Gets the nextSibling attribute of the HelpTableOfContentItem object
   *
   *@return    The nextSibling value
   */
  public int getNextSibling() {
    return nextSibling;
  }


  /**
   *  Gets the parent attribute of the HelpTableOfContentItem object
   *
   *@return    The parent value
   */
  public int getParent() {
    return parent;
  }


  /**
   *  Gets the linkToCategoryId attribute of the HelpTableOfContentItem object
   *
   *@return    The linkToCategoryId value
   */
  public int getLinkToCategoryId() {
    return linkToCategoryId;
  }


  /**
   *  Gets the contentLevel attribute of the HelpTableOfContentItem object
   *
   *@return    The contentLevel value
   */
  public int getContentLevel() {
    return contentLevel;
  }


  /**
   *  Gets the contentOrder attribute of the HelpTableOfContentItem object
   *
   *@return    The contentOrder value
   */
  public int getContentOrder() {
    return contentOrder;
  }


  /**
   *  Gets the enteredBy attribute of the HelpTableOfContentItem object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the HelpTableOfContentItem object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the HelpTableOfContentItem object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the HelpTableOfContentItem object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the enabled attribute of the HelpTableOfContentItem object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the hTOCLinks attribute of the HelpTableOfContentItem object
   *
   *@return    The hTOCLinks value
   */
  public HelpTableOfContentItemLinks getHTOCLinks() {
    return hTOCLinks;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildHTOCLinks(Connection db) throws SQLException {
    hTOCLinks = new HelpTableOfContentItemLinks(db, this.id);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("content_id");
    displayText = rs.getString("displaytext");
    firstChild = rs.getInt("firstchild");
    nextSibling = rs.getInt("nextsibling");
    parent = rs.getInt("parent");
    linkToCategoryId = rs.getInt("category_id");
    contentLevel = rs.getInt("contentlevel");
    contentOrder = rs.getInt("contentorder");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
    enabled = rs.getBoolean("enabled");
  }
}

