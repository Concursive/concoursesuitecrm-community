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
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.troubletickets.base.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    January 29, 2004
 *@version    $Id: TicketReplacementPart.java,v 1.1.2.3 2004/02/03 13:51:33
 *      kbhoopal Exp $
 */
public class TicketReplacementPart extends GenericBean {

  private int id = -1;
  private int linkFormId = -1;
  private String partNumber = null;
  private String partDescription = null;


  /**
   *  Constructor for the TicketReplacePart object
   */
  public TicketReplacementPart() { }


  /**
   *  Constructor for the TicketReplacementPart object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public TicketReplacementPart(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the TicketReplacePart object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the TicketReplacePart object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkFormId attribute of the TicketReplacementPart object
   *
   *@param  tmp  The new linkFormId value
   */
  public void setLinkFormId(int tmp) {
    this.linkFormId = tmp;
  }


  /**
   *  Sets the linkFormId attribute of the TicketReplacementPart object
   *
   *@param  tmp  The new linkFormId value
   */
  public void setLinkFormId(String tmp) {
    this.linkFormId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the partNumber attribute of the TicketReplacePart object
   *
   *@param  tmp  The new partNumber value
   */
  public void setPartNumber(String tmp) {
    this.partNumber = tmp;
  }


  /**
   *  Sets the partDescription attribute of the TicketReplacePart object
   *
   *@param  tmp  The new partDescription value
   */
  public void setPartDescription(String tmp) {
    this.partDescription = tmp;
  }


  /**
   *  Gets the id attribute of the TicketReplacePart object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the partNumber attribute of the TicketReplacePart object
   *
   *@return    The partNumber value
   */
  public String getPartNumber() {
    return partNumber;
  }


  /**
   *  Gets the partDescription attribute of the TicketReplacePart object
   *
   *@return    The partDescription value
   */
  public String getPartDescription() {
    return partDescription;
  }


  /**
   *  Gets the linkFormId attribute of the TicketReplacementPart object
   *
   *@return    The linkFormId value
   */
  public int getLinkFormId() {
    return linkFormId;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    PreparedStatement pst = null;

    pst = db.prepareStatement(
        "INSERT INTO  trouble_asset_replacement " +
        "(link_form_id, " +
        "part_number, " +
        "part_description) " +
        "VALUES (?,?,?)");

    int i = 0;
    pst.setInt(++i, linkFormId);
    pst.setString(++i, partNumber);
    pst.setString(++i, partDescription);

    pst.execute();
    pst.close();

  }


  /**
   *  Description of the Method
   *
   *@param  request    Description of the Parameter
   *@param  parseItem  Description of the Parameter
   */
  public void buildRecord(HttpServletRequest request, int parseItem) {
    this.setPartNumber(request.getParameter("partNumber" + parseItem));
    this.setPartDescription(request.getParameter("partDescription" + parseItem));
    if (request.getParameter("part" + parseItem + "id") != null) {
      this.setId(request.getParameter("part" + parseItem + "id"));
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("replacement_id");
    linkFormId = rs.getInt("link_form_id");
    partNumber = rs.getString("part_number");
    partDescription = rs.getString("part_description");
  }

}

