//Copyright 2002 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.w3c.dom.Element;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import java.io.*;
import java.sql.*;

/**
 *  Contains a list of BusinessProcess objects and can be used for initially
 *  building the list.
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: BusinessProcessList.java,v 1.4 2003/01/13 21:41:16 mrajkowski
 *      Exp $
 */
public class BusinessProcessList extends HashMap {
  //Filters
  private int enabled = Constants.UNDEFINED;
  private int typeId = -1;
  private int linkModuleId = -1;
  //Object resources
  private boolean buildScheduledEvents = false;


  /**
   *  Constructor for the BusinessProcessList object
   */
  public BusinessProcessList() { }


  /**
   *  Constructor for the BusinessProcessList object
   *
   *@param  processes  Description of the Parameter
   */
  public BusinessProcessList(Element processes) {
    this.process(processes);
  }


  /**
   *  Sets the enabled attribute of the BusinessProcessList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the BusinessProcessList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeId attribute of the BusinessProcessList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the BusinessProcessList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the linkModuleId attribute of the BusinessProcessList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the BusinessProcessList object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the buildScheduledEvents attribute of the BusinessProcessList object
   *
   *@param  tmp  The new buildScheduledEvents value
   */
  public void setBuildScheduledEvents(boolean tmp) {
    this.buildScheduledEvents = tmp;
  }


  /**
   *  Sets the buildScheduledEvents attribute of the BusinessProcessList object
   *
   *@param  tmp  The new buildScheduledEvents value
   */
  public void setBuildScheduledEvents(String tmp) {
    this.buildScheduledEvents = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the enabled attribute of the BusinessProcessList object
   *
   *@return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the typeId attribute of the BusinessProcessList object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the linkModuleId attribute of the BusinessProcessList object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the buildScheduledEvents attribute of the BusinessProcessList object
   *
   *@return    The buildScheduledEvents value
   */
  public boolean getBuildScheduledEvents() {
    return buildScheduledEvents;
  }


  /**
   *  Populate business processes from an XML file
   *
   *@param  xmlFile  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean buildList(File xmlFile) {
    try {
      XMLUtils xml = new XMLUtils(xmlFile);
      return parse(xml.getDocumentElement());
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }


  /**
   *  Builds the business processes, from an XML element.
   *
   *@param  element  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean parse(Element element) {
    if (element == null) {
      return false;
    }
    try {
      Element processes = XMLUtils.getFirstElement(element, "processes");
      if (processes != null) {
        this.process(processes);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }



  /**
   *  Builds the business processes, from an XML element
   *
   *@param  processes  Description of the Parameter
   */
  private void process(Element processes) {
    ArrayList processNodes = XMLUtils.getElements(processes, "process");
    Iterator processElements = processNodes.iterator();
    while (processElements.hasNext()) {
      Element processElement = (Element) processElements.next();
      BusinessProcess thisProcess = new BusinessProcess(processElement);
      this.put(thisProcess.getName(), thisProcess);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("BusinessProcessList-> Added: " + thisProcess.getName());
      }
    }
  }


  /**
   *  Builds the business processes, from a database, with the specified filter
   *  parameters.
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    //Get a list of business processes and store the name and the object
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlSelect.append(
        "SELECT process_id, process_name, description, " +
        "type_id, link_module_id, component_start_id, enabled " +
        "FROM business_process " +
        "WHERE process_id > 0 ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY process_id ");
    PreparedStatement pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      BusinessProcess thisProcess = new BusinessProcess(rs);
      this.put(thisProcess.getName(), thisProcess);
    }
    rs.close();
    pst.close();
    //Build process resources
    Iterator i = this.values().iterator();
    while (i.hasNext()) {
      BusinessProcess thisProcess = (BusinessProcess) i.next();
      thisProcess.setBuildScheduledEvents(buildScheduledEvents);
      thisProcess.buildResources(db);
    }
  }


  /**
   *  Adds WHERE clauses to database query for selected filters
   *
   *@param  sqlFilter  Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (typeId > -1) {
      sqlFilter.append("AND type_id = ? ");
    }
    if (linkModuleId > -1) {
      sqlFilter.append("AND link_module_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
  }


  /**
   *  Sets database parameters for selected filters
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (typeId > -1) {
      pst.setInt(++i, typeId);
    }
    if (linkModuleId > -1) {
      pst.setInt(++i, linkModuleId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    return i;
  }


  /**
   *  Inserts all processes in this collection and related data.
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      Iterator processes = this.values().iterator();
      while (processes.hasNext()) {
        BusinessProcess thisProcess = (BusinessProcess) processes.next();
        thisProcess.insert(db);
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      Iterator processes = this.values().iterator();
      while (processes.hasNext()) {
        BusinessProcess thisProcess = (BusinessProcess) processes.next();
        thisProcess.delete(db);
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
  }
}

