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
package org.aspcfs.modules.troubletickets.webservices.beans;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Represents a Ticket as exposed for web services
 *
 * @author     Ananth
 * @version
 * @created    August 1, 2006
 */
public class WSTicketBean extends GenericBean {

  private int id = -1;
  private String problem = null;
  private String location = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp closed = null;
  private String cause = null;
  private String companyName = null;
  private String departmentName = null;
  private String priorityName = null;
  private String severityName = null;
  private String categoryName = null;
  private String sourceName = null;
  private java.sql.Timestamp estimatedResolutionDate = null;
  private java.sql.Timestamp resolutionDate = null;


  /**
   *  Gets the id attribute of the WSTicketBean object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the WSTicketBean object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the WSTicketBean object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the problem attribute of the WSTicketBean object
   *
   * @return    The problem value
   */
  public String getProblem() {
    return problem;
  }


  /**
   *  Sets the problem attribute of the WSTicketBean object
   *
   * @param  tmp  The new problem value
   */
  public void setProblem(String tmp) {
    this.problem = tmp;
  }


  /**
   *  Gets the location attribute of the WSTicketBean object
   *
   * @return    The location value
   */
  public String getLocation() {
    return location;
  }


  /**
   *  Sets the location attribute of the WSTicketBean object
   *
   * @param  tmp  The new location value
   */
  public void setLocation(String tmp) {
    this.location = tmp;
  }


  /**
   *  Gets the entered attribute of the WSTicketBean object
   *
   * @return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Sets the entered attribute of the WSTicketBean object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the WSTicketBean object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the closed attribute of the WSTicketBean object
   *
   * @return    The closed value
   */
  public java.sql.Timestamp getClosed() {
    return closed;
  }


  /**
   *  Sets the closed attribute of the WSTicketBean object
   *
   * @param  tmp  The new closed value
   */
  public void setClosed(java.sql.Timestamp tmp) {
    this.closed = tmp;
  }


  /**
   *  Sets the closed attribute of the WSTicketBean object
   *
   * @param  tmp  The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the cause attribute of the WSTicketBean object
   *
   * @return    The cause value
   */
  public String getCause() {
    return cause;
  }


  /**
   *  Sets the cause attribute of the WSTicketBean object
   *
   * @param  tmp  The new cause value
   */
  public void setCause(String tmp) {
    this.cause = tmp;
  }


  /**
   *  Gets the estimatedResolutionDate attribute of the WSTicketBean object
   *
   * @return    The estimatedResolutionDate value
   */
  public java.sql.Timestamp getEstimatedResolutionDate() {
    return estimatedResolutionDate;
  }


  /**
   *  Sets the estimatedResolutionDate attribute of the WSTicketBean object
   *
   * @param  tmp  The new estimatedResolutionDate value
   */
  public void setEstimatedResolutionDate(java.sql.Timestamp tmp) {
    this.estimatedResolutionDate = tmp;
  }


  /**
   *  Sets the estimatedResolutionDate attribute of the WSTicketBean object
   *
   * @param  tmp  The new estimatedResolutionDate value
   */
  public void setEstimatedResolutionDate(String tmp) {
    this.estimatedResolutionDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the resolutionDate attribute of the WSTicketBean object
   *
   * @return    The resolutionDate value
   */
  public java.sql.Timestamp getResolutionDate() {
    return resolutionDate;
  }


  /**
   *  Sets the resolutionDate attribute of the WSTicketBean object
   *
   * @param  tmp  The new resolutionDate value
   */
  public void setResolutionDate(java.sql.Timestamp tmp) {
    this.resolutionDate = tmp;
  }


  /**
   *  Sets the resolutionDate attribute of the WSTicketBean object
   *
   * @param  tmp  The new resolutionDate value
   */
  public void setResolutionDate(String tmp) {
    this.resolutionDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the companyName attribute of the WSTicketBean object
   *
   * @return    The companyName value
   */
  public String getCompanyName() {
    return companyName;
  }


  /**
   *  Sets the companyName attribute of the WSTicketBean object
   *
   * @param  tmp  The new companyName value
   */
  public void setCompanyName(String tmp) {
    this.companyName = tmp;
  }


  /**
   *  Gets the departmentName attribute of the WSTicketBean object
   *
   * @return    The departmentName value
   */
  public String getDepartmentName() {
    return departmentName;
  }


  /**
   *  Sets the departmentName attribute of the WSTicketBean object
   *
   * @param  tmp  The new departmentName value
   */
  public void setDepartmentName(String tmp) {
    this.departmentName = tmp;
  }


  /**
   *  Gets the priorityName attribute of the WSTicketBean object
   *
   * @return    The priorityName value
   */
  public String getPriorityName() {
    return priorityName;
  }


  /**
   *  Sets the priorityName attribute of the WSTicketBean object
   *
   * @param  tmp  The new priorityName value
   */
  public void setPriorityName(String tmp) {
    this.priorityName = tmp;
  }


  /**
   *  Gets the severityName attribute of the WSTicketBean object
   *
   * @return    The severityName value
   */
  public String getSeverityName() {
    return severityName;
  }


  /**
   *  Sets the severityName attribute of the WSTicketBean object
   *
   * @param  tmp  The new severityName value
   */
  public void setSeverityName(String tmp) {
    this.severityName = tmp;
  }


  /**
   *  Gets the categoryName attribute of the WSTicketBean object
   *
   * @return    The categoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   *  Sets the categoryName attribute of the WSTicketBean object
   *
   * @param  tmp  The new categoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }


  /**
   *  Gets the sourceName attribute of the WSTicketBean object
   *
   * @return    The sourceName value
   */
  public String getSourceName() {
    return sourceName;
  }


  /**
   *  Sets the sourceName attribute of the WSTicketBean object
   *
   * @param  tmp  The new sourceName value
   */
  public void setSourceName(String tmp) {
    this.sourceName = tmp;
  }
}

