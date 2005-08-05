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
package org.aspcfs.apps.workFlowManager;

import org.aspcfs.controller.objectHookManager.ObjectHookList;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created July 5, 2005
 */
public class BusinessProcessImporter {
  private ObjectHookList hooks = null;
  private BusinessProcessList processes = null;
  private ScheduledEventList events = null;


  /**
   * Constructor for the BusinessProcessImporter object
   */
  public BusinessProcessImporter() {
  }


  /**
   * Gets the hooks attribute of the BusinessProcessImporter object
   *
   * @return The hooks value
   */
  public ObjectHookList getHooks() {
    return hooks;
  }


  /**
   * Sets the hooks attribute of the BusinessProcessImporter object
   *
   * @param tmp The new hooks value
   */
  public void setHooks(ObjectHookList tmp) {
    this.hooks = tmp;
  }


  /**
   * Gets the processes attribute of the BusinessProcessImporter object
   *
   * @return The processes value
   */
  public BusinessProcessList getProcesses() {
    return processes;
  }


  /**
   * Sets the processes attribute of the BusinessProcessImporter object
   *
   * @param tmp The new processes value
   */
  public void setProcesses(BusinessProcessList tmp) {
    this.processes = tmp;
  }


  /**
   * Gets the events attribute of the BusinessProcessImporter object
   *
   * @return The events value
   */
  public ScheduledEventList getEvents() {
    return events;
  }


  /**
   * Sets the events attribute of the BusinessProcessImporter object
   *
   * @param tmp The new events value
   */
  public void setEvents(ScheduledEventList tmp) {
    this.events = tmp;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public synchronized boolean deleteProcesses(Connection db) throws SQLException {
    //Delete user processes from database.
    processes = new BusinessProcessList();
    processes.buildList(db);
    processes.delete(db);
    processes = null;
    return true;
  }


  /**
   * Description of the Method
   *
   * @param element Description of the Parameter
   * @return Description of the Return Value
   */
  public int parseXML(Element element) {
    //parse the business processes and events from the xml file
    processes.parse(element, false);
    int finalSize = processes.values().size();
    events.parse(element);
    events.buildResources(processes);
    //parse the hook actions from the xml file
    hooks.parse(element, false);
    return (finalSize);
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param element Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public synchronized int execute(Connection db, Element element) throws SQLException {
    if (processes == null) {
      processes = new BusinessProcessList();
    }
    if (hooks == null) {
      hooks = new ObjectHookList();
    }
    if (events == null) {
      events = new ScheduledEventList();
    }

    int numberOfProcesses = parseXML(element);
    if (numberOfProcesses > 0) {
      //Insert the processes and the events
      processes.insert(db);
      //insert the hook actions
      hooks.insert(db);
    }
    return numberOfProcesses;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int buildEventList(Connection db) throws SQLException {
    events = new ScheduledEventList();
    events.buildList(db);
    return events.size();
  }
}

