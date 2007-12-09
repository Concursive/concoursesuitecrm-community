/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.workFlowManager;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.URL;

/**
 * A collection of Scheduled Event objects.
 *
 * @author matt rajkowski
 * @version $Id: ScheduledEventList.java,v 1.1 2003/06/24 15:17:30 mrajkowski
 *          Exp $
 * @created June 23, 2003
 */
public class ScheduledEventList extends ArrayList {

  //Filters for query
  private int businessProcessId = -1;
  private int enabled = Constants.UNDEFINED;


  /**
   * Constructor for the ScheduledEventList object
   */
  public ScheduledEventList() {
  }


  /**
   * Sets the businessProcessId attribute of the ScheduledEventList object
   *
   * @param tmp The new businessProcessId value
   */
  public void setBusinessProcessId(int tmp) {
    this.businessProcessId = tmp;
  }


  /**
   * Sets the businessProcessId attribute of the ScheduledEventList object
   *
   * @param tmp The new businessProcessId value
   */
  public void setBusinessProcessId(String tmp) {
    this.businessProcessId = Integer.parseInt(tmp);
  }


  /**
   * Gets the businessProcessId attribute of the ScheduledEventList object
   *
   * @return The businessProcessId value
   */
  public int getBusinessProcessId() {
    return businessProcessId;
  }


  /**
   * Sets the enabled attribute of the ScheduledEventList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the ScheduledEventList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Gets the enabled attribute of the ScheduledEventList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   * Description of the Method
   *
   * @param xmlFile Description of the Parameter
   * @return Description of the Return Value
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


  public boolean buildList(URL xmlFileURL) {
    try {
      XMLUtils xml = new XMLUtils(xmlFileURL);
      return parse(xml.getDocumentElement());
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }


  /**
   * Description of the Method
   *
   * @param element Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean parse(Element element) {
    if (element == null) {
      return false;
    }
    try {
      //Process all hooks and the corresponding actions
      Element schedules = XMLUtils.getFirstElement(element, "schedules");
      if (schedules != null) {
        ArrayList scheduleNotes = XMLUtils.getElements(schedules, "schedule");
        Iterator scheduleElements = scheduleNotes.iterator();
        while (scheduleElements.hasNext()) {
          Element scheduleElement = (Element) scheduleElements.next();
          Element events = XMLUtils.getFirstElement(scheduleElement, "events");
          if (events != null) {
            ArrayList eventNodes = XMLUtils.getElements(events, "event");
            Iterator eventElements = eventNodes.iterator();
            while (eventElements.hasNext()) {
              Element eventElement = (Element) eventElements.next();
              ScheduledEvent thisEvent = new ScheduledEvent(eventElement);
              // int value = hasDuplicateEvents(thisEvent);
              int value = -1;
              if (value != -1) {
                ScheduledEvent tempEvent = (ScheduledEvent) this.remove(value);
                if (tempEvent.getProcessId() != -1) {
                  thisEvent.setProcessId(tempEvent.getProcessId());
                }
                if (tempEvent.getEntered() != null) {
                  thisEvent.setEntered(tempEvent.getEntered());
                }
                thisEvent.setId(value);
              }
              this.add(thisEvent);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param event Description of the Parameter
   * @return Description of the Return Value
   */
  public int hasDuplicateEvents(ScheduledEvent event) {
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      ScheduledEvent thisEvent = (ScheduledEvent) iterator.next();
      if (event.isDuplicate(thisEvent) && thisEvent.getId() != -1) {
        return thisEvent.getId();
      }
    }
    return -1;
  }


  /**
   * Description of the Method
   *
   * @param list Description of the Parameter
   */
  public void buildResources(BusinessProcessList list) {
    Iterator i = (Iterator) this.iterator();
    while (i.hasNext()) {
      ScheduledEvent event = (ScheduledEvent) i.next();
      event.buildResources(list);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = true;
    Iterator i = (Iterator) this.iterator();
    while (i.hasNext()) {
      ScheduledEvent event = (ScheduledEvent) i.next();
      result = event.insert(db) && result;
    }
    return result;
  }


  /**
   * Loads scheduled events from the database using the specified filters
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    sqlSelect.append(
        "SELECT event_id, " + DatabaseUtils.addQuotes(db, "second") +
        ", " + DatabaseUtils.addQuotes(db, "minute") +
        ", " + DatabaseUtils.addQuotes(db, "hour") +
        ", " + "dayofmonth, " + DatabaseUtils.addQuotes(db, "month")+ ", " + DatabaseUtils.addQuotes(db, "dayofweek")+ ", " + DatabaseUtils.addQuotes(db, "year")+ ", task, extrainfo, " +
        "enabled, entered, process_id " +
        "FROM business_process_events " +
        "WHERE event_id > 0 ");
    createFilter(sqlFilter);
    sqlOrder.append("ORDER BY event_id ");
    PreparedStatement pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    prepareFilter(pst);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ScheduledEvent thisEvent = new ScheduledEvent(rs);
      this.add(thisEvent);
    }
    rs.close();
    pst.close();
  }


  /**
   * Adds filter parameters to the query
   *
   * @param sqlFilter Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (businessProcessId > -1) {
      sqlFilter.append("AND process_id = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
  }


  /**
   * Sets the filter parameters on the query
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (businessProcessId > -1) {
      pst.setInt(++i, businessProcessId);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, enabled == Constants.TRUE);
    }
    return i;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  void delete(Connection db) throws SQLException {
    Iterator itr = this.iterator();
    while (itr.hasNext()) {
      ScheduledEvent tmpScheduledEvent = (ScheduledEvent) itr.next();
      tmpScheduledEvent.delete(db);
    }
  }
}

