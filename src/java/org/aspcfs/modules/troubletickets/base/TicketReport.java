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
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.*;
import com.zeroio.iteam.base.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.contacts.base.*;
import java.io.*;
import java.text.*;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.admin.base.UserList;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    March 8, 2002
 *@version    $Id$
 */
public class TicketReport extends TicketList {
  protected Report rep = new Report();
  //default delimiter is comma
  protected String delimiter = ",";
  protected String header = "Centric CRM Tickets";
  protected String tdFormat = "";
  protected String filePath = "";
  protected String filenameToUse = "";
  protected FileItem thisItem = new FileItem();
  protected int limitId = -1;

  protected String subject = "";
  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected int createdBy = -1;
  protected ArrayList criteria = null;
  protected String[] params = new String[]{"ticketid", "organization", "problem"};

  protected boolean displayId = true;
  protected boolean displayProblem = true;
  protected boolean displayLocation = true;
  protected boolean displaySourceName = true;
  protected boolean displaySeverity = true;
  protected boolean displayPriority = true;
  protected boolean displayCategory = true;
  protected boolean displayDepartment = true;
  protected boolean displayOwner = true;
  protected boolean displaySolution = true;
  protected boolean displayClosed = true;
  protected boolean displayEntered = true;
  protected boolean displayEnteredBy = true;
  protected boolean displayModified = true;
  protected boolean displayModifiedBy = true;
  protected boolean displayAssignedTo = true;
  protected boolean displayIssueNotes = true;
  protected boolean displayOrganization = true;
  protected boolean displayEstimatedResolutionDate = true;
  protected boolean displayResolutionDate = true;
  protected boolean displayAssignmentDate = true;
  protected boolean displayComment = true;
  protected boolean displayContactName = true;

  protected OrganizationReport orgReportJoin = new OrganizationReport();
  protected boolean joinOrgs = false;


  /**
   *  Constructor for the TicketReport object
   */
  public TicketReport() { }


  /**
   *  Sets the rep attribute of the TicketReport object
   *
   *@param  tmp  The new rep value
   */
  public void setRep(Report tmp) {
    this.rep = tmp;
  }


  /**
   *  Sets the delimiter attribute of the TicketReport object
   *
   *@param  tmp  The new delimiter value
   */
  public void setDelimiter(String tmp) {
    this.delimiter = tmp;
  }


  /**
   *  Sets the header attribute of the TicketReport object
   *
   *@param  tmp  The new header value
   */
  public void setHeader(String tmp) {
    this.header = tmp;
  }


  /**
   *  Sets the tdFormat attribute of the TicketReport object
   *
   *@param  tmp  The new tdFormat value
   */
  public void setTdFormat(String tmp) {
    this.tdFormat = tmp;
  }


  /**
   *  Sets the displayEstimatedResolutionDate attribute of the TicketReport
   *  object
   *
   *@param  tmp  The new displayEstimatedResolutionDate value
   */
  public void setDisplayEstimatedResolutionDate(boolean tmp) {
    this.displayEstimatedResolutionDate = tmp;
  }


  /**
   *  Sets the displayEstimatedResolutionDate attribute of the TicketReport
   *  object
   *
   *@param  tmp  The new displayEstimatedResolutionDate value
   */
  public void setDisplayEstimatedResolutionDate(String tmp) {
    this.displayEstimatedResolutionDate = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the displayResolutionDate attribute of the TicketReport object
   *
   *@param  tmp  The new displayResolutionDate value
   */
  public void setDisplayResolutionDate(boolean tmp) {
    this.displayResolutionDate = tmp;
  }


  /**
   *  Sets the displayResolutionDate attribute of the TicketReport object
   *
   *@param  tmp  The new displayResolutionDate value
   */
  public void setDisplayResolutionDate(String tmp) {
    this.displayResolutionDate = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the displayAssignmentDate attribute of the TicketReport object
   *
   *@param  tmp  The new displayAssignmentDate value
   */
  public void setDisplayAssignmentDate(boolean tmp) {
    this.displayAssignmentDate = tmp;
  }


  /**
   *  Sets the displayAssignmentDate attribute of the TicketReport object
   *
   *@param  tmp  The new displayAssignmentDate value
   */
  public void setDisplayAssignmentDate(String tmp) {
    this.displayAssignmentDate = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the displayComment attribute of the TicketReport object
   *
   *@param  tmp  The new displayComment value
   */
  public void setDisplayComment(boolean tmp) {
    this.displayComment = tmp;
  }


  /**
   *  Sets the displayComment attribute of the TicketReport object
   *
   *@param  tmp  The new displayComment value
   */
  public void setDisplayComment(String tmp) {
    this.displayComment = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the displayIssueNotes attribute of the TicketReport object
   *
   *@param  tmp  The new displayIssueNotes value
   */
  public void setDisplayIssueNotes(boolean tmp) {
    this.displayIssueNotes = tmp;
  }


  /**
   *  Sets the displayIssueNotes attribute of the TicketReport object
   *
   *@param  tmp  The new displayIssueNotes value
   */
  public void setDisplayIssueNotes(String tmp) {
    this.displayIssueNotes = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the displayIssueNotes attribute of the TicketReport object
   *
   *@return    The displayIssueNotes value
   */
  public boolean getDisplayIssueNotes() {
    return displayIssueNotes;
  }


  /**
   *  Gets the displayEstimatedResolutionDate attribute of the TicketReport
   *  object
   *
   *@return    The displayEstimatedResolutionDate value
   */
  public boolean getDisplayEstimatedResolutionDate() {
    return displayEstimatedResolutionDate;
  }


  /**
   *  Gets the displayResolutionDate attribute of the TicketReport object
   *
   *@return    The displayResolutionDate value
   */
  public boolean getDisplayResolutionDate() {
    return displayResolutionDate;
  }


  /**
   *  Gets the displayAssignmentDate attribute of the TicketReport object
   *
   *@return    The displayAssignmentDate value
   */
  public boolean getDisplayAssignmentDate() {
    return displayAssignmentDate;
  }


  /**
   *  Gets the displayComment attribute of the TicketReport object
   *
   *@return    The displayComment value
   */
  public boolean getDisplayComment() {
    return displayComment;
  }


  /**
   *  Gets the rep attribute of the TicketReport object
   *
   *@return    The rep value
   */
  public Report getRep() {
    return rep;
  }


  /**
   *  Gets the delimiter attribute of the TicketReport object
   *
   *@return    The delimiter value
   */
  public String getDelimiter() {
    return delimiter;
  }


  /**
   *  Gets the header attribute of the TicketReport object
   *
   *@return    The header value
   */
  public String getHeader() {
    return header;
  }


  /**
   *  Gets the tdFormat attribute of the TicketReport object
   *
   *@return    The tdFormat value
   */
  public String getTdFormat() {
    return tdFormat;
  }


  /**
   *  Gets the filePath attribute of the TicketReport object
   *
   *@return    The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   *  Gets the thisItem attribute of the TicketReport object
   *
   *@return    The thisItem value
   */
  public FileItem getThisItem() {
    return thisItem;
  }


  /**
   *  Sets the filePath attribute of the TicketReport object
   *
   *@param  tmp  The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   *  Sets the thisItem attribute of the TicketReport object
   *
   *@param  tmp  The new thisItem value
   */
  public void setThisItem(FileItem tmp) {
    this.thisItem = tmp;
  }


  /**
   *  Gets the subject attribute of the TicketReport object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the enteredBy attribute of the TicketReport object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the TicketReport object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the subject attribute of the TicketReport object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the TicketReport object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the TicketReport object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Gets the displayId attribute of the TicketReport object
   *
   *@return    The displayId value
   */
  public boolean getDisplayId() {
    return displayId;
  }


  /**
   *  Gets the displayProblem attribute of the TicketReport object
   *
   *@return    The displayProblem value
   */
  public boolean getDisplayProblem() {
    return displayProblem;
  }


  /**
   *  Gets the displayLocation attribute of the TicketReport object
   *
   *@return    The displayLocation value
   */
  public boolean getDisplayLocation() {
    return displayLocation;
  }


  /**
   *  Gets the displaySourceName attribute of the TicketReport object
   *
   *@return    The displaySourceName value
   */
  public boolean getDisplaySourceName() {
    return displaySourceName;
  }


  /**
   *  Gets the displayContactName attribute of the TicketReport object
   *
   *@return    The displayContactName value
   */
  public boolean getDisplayContactName() {
    return displayContactName;
  }


  /**
   *  Gets the displaySeverity attribute of the TicketReport object
   *
   *@return    The displaySeverity value
   */
  public boolean getDisplaySeverity() {
    return displaySeverity;
  }


  /**
   *  Gets the displayPriority attribute of the TicketReport object
   *
   *@return    The displayPriority value
   */
  public boolean getDisplayPriority() {
    return displayPriority;
  }


  /**
   *  Gets the displayCategory attribute of the TicketReport object
   *
   *@return    The displayCategory value
   */
  public boolean getDisplayCategory() {
    return displayCategory;
  }


  /**
   *  Gets the displayDepartment attribute of the TicketReport object
   *
   *@return    The displayDepartment value
   */
  public boolean getDisplayDepartment() {
    return displayDepartment;
  }


  /**
   *  Gets the displayOwner attribute of the TicketReport object
   *
   *@return    The displayOwner value
   */
  public boolean getDisplayOwner() {
    return displayOwner;
  }


  /**
   *  Gets the displaySolution attribute of the TicketReport object
   *
   *@return    The displaySolution value
   */
  public boolean getDisplaySolution() {
    return displaySolution;
  }


  /**
   *  Gets the displayClosed attribute of the TicketReport object
   *
   *@return    The displayClosed value
   */
  public boolean getDisplayClosed() {
    return displayClosed;
  }


  /**
   *  Gets the displayEntered attribute of the TicketReport object
   *
   *@return    The displayEntered value
   */
  public boolean getDisplayEntered() {
    return displayEntered;
  }


  /**
   *  Gets the displayEnteredBy attribute of the TicketReport object
   *
   *@return    The displayEnteredBy value
   */
  public boolean getDisplayEnteredBy() {
    return displayEnteredBy;
  }


  /**
   *  Gets the displayModified attribute of the TicketReport object
   *
   *@return    The displayModified value
   */
  public boolean getDisplayModified() {
    return displayModified;
  }


  /**
   *  Gets the displayModifiedBy attribute of the TicketReport object
   *
   *@return    The displayModifiedBy value
   */
  public boolean getDisplayModifiedBy() {
    return displayModifiedBy;
  }


  /**
   *  Sets the displayId attribute of the TicketReport object
   *
   *@param  tmp  The new displayId value
   */
  public void setDisplayId(boolean tmp) {
    this.displayId = tmp;
  }


  /**
   *  Sets the displayProblem attribute of the TicketReport object
   *
   *@param  tmp  The new displayProblem value
   */
  public void setDisplayProblem(boolean tmp) {
    this.displayProblem = tmp;
  }


  /**
   *  Sets the displayLocation attribute of the TicketReport object
   *
   *@param  tmp  The new displayLocation value
   */
  public void setDisplayLocation(boolean tmp) {
    this.displayLocation = tmp;
  }


  /**
   *  Sets the displaySourceName attribute of the TicketReport object
   *
   *@param  tmp  The new displaySourceName value
   */
  public void setDisplaySourceName(boolean tmp) {
    this.displaySourceName = tmp;
  }


  /**
   *  Sets the displayContactName attribute of the TicketReport object
   *
   *@param  tmp  The new displayContactName value
   */
  public void setDisplayContactName(boolean tmp) {
    this.displayContactName = tmp;
  }


  /**
   *  Sets the displaySeverity attribute of the TicketReport object
   *
   *@param  tmp  The new displaySeverity value
   */
  public void setDisplaySeverity(boolean tmp) {
    this.displaySeverity = tmp;
  }


  /**
   *  Sets the displayPriority attribute of the TicketReport object
   *
   *@param  tmp  The new displayPriority value
   */
  public void setDisplayPriority(boolean tmp) {
    this.displayPriority = tmp;
  }


  /**
   *  Sets the displayCategory attribute of the TicketReport object
   *
   *@param  tmp  The new displayCategory value
   */
  public void setDisplayCategory(boolean tmp) {
    this.displayCategory = tmp;
  }


  /**
   *  Sets the displayDepartment attribute of the TicketReport object
   *
   *@param  tmp  The new displayDepartment value
   */
  public void setDisplayDepartment(boolean tmp) {
    this.displayDepartment = tmp;
  }


  /**
   *  Sets the displayOwner attribute of the TicketReport object
   *
   *@param  tmp  The new displayOwner value
   */
  public void setDisplayOwner(boolean tmp) {
    this.displayOwner = tmp;
  }


  /**
   *  Sets the displaySolution attribute of the TicketReport object
   *
   *@param  tmp  The new displaySolution value
   */
  public void setDisplaySolution(boolean tmp) {
    this.displaySolution = tmp;
  }


  /**
   *  Sets the displayClosed attribute of the TicketReport object
   *
   *@param  tmp  The new displayClosed value
   */
  public void setDisplayClosed(boolean tmp) {
    this.displayClosed = tmp;
  }


  /**
   *  Sets the displayEntered attribute of the TicketReport object
   *
   *@param  tmp  The new displayEntered value
   */
  public void setDisplayEntered(boolean tmp) {
    this.displayEntered = tmp;
  }


  /**
   *  Sets the displayEnteredBy attribute of the TicketReport object
   *
   *@param  tmp  The new displayEnteredBy value
   */
  public void setDisplayEnteredBy(boolean tmp) {
    this.displayEnteredBy = tmp;
  }


  /**
   *  Sets the displayModified attribute of the TicketReport object
   *
   *@param  tmp  The new displayModified value
   */
  public void setDisplayModified(boolean tmp) {
    this.displayModified = tmp;
  }


  /**
   *  Sets the displayModifiedBy attribute of the TicketReport object
   *
   *@param  tmp  The new displayModifiedBy value
   */
  public void setDisplayModifiedBy(boolean tmp) {
    this.displayModifiedBy = tmp;
  }


  /**
   *  Gets the orgReportJoin attribute of the TicketReport object
   *
   *@return    The orgReportJoin value
   */
  public OrganizationReport getOrgReportJoin() {
    return orgReportJoin;
  }


  /**
   *  Gets the joinOrgs attribute of the TicketReport object
   *
   *@return    The joinOrgs value
   */
  public boolean getJoinOrgs() {
    return joinOrgs;
  }


  /**
   *  Sets the orgReportJoin attribute of the TicketReport object
   *
   *@param  tmp  The new orgReportJoin value
   */
  public void setOrgReportJoin(OrganizationReport tmp) {
    this.orgReportJoin = tmp;
  }


  /**
   *  Sets the joinOrgs attribute of the TicketReport object
   *
   *@param  tmp  The new joinOrgs value
   */
  public void setJoinOrgs(boolean tmp) {
    this.joinOrgs = tmp;
  }


  /**
   *  Gets the displayAssignedTo attribute of the TicketReport object
   *
   *@return    The displayAssignedTo value
   */
  public boolean getDisplayAssignedTo() {
    return displayAssignedTo;
  }


  /**
   *  Sets the displayAssignedTo attribute of the TicketReport object
   *
   *@param  displayAssignedTo  The new displayAssignedTo value
   */
  public void setDisplayAssignedTo(boolean displayAssignedTo) {
    this.displayAssignedTo = displayAssignedTo;
  }


  /**
   *  Gets the displayOrganization attribute of the TicketReport object
   *
   *@return    The displayOrganization value
   */
  public boolean getDisplayOrganization() {
    return displayOrganization;
  }


  /**
   *  Sets the displayOrganization attribute of the TicketReport object
   *
   *@param  displayOrganization  The new displayOrganization value
   */
  public void setDisplayOrganization(boolean displayOrganization) {
    this.displayOrganization = displayOrganization;
  }


  /**
   *  Gets the limitId attribute of the TicketReport object
   *
   *@return    The limitId value
   */
  public int getLimitId() {
    return limitId;
  }


  /**
   *  Sets the limitId attribute of the TicketReport object
   *
   *@param  limitId  The new limitId value
   */
  public void setLimitId(int limitId) {
    this.limitId = limitId;
  }


  /**
   *  Gets the criteria attribute of the TicketReport object
   *
   *@return    The criteria value
   */
  public ArrayList getCriteria() {
    return criteria;
  }


  /**
   *  Sets the criteria attribute of the TicketReport object
   *
   *@param  criteriaString  The new criteria value
   */
  public void setCriteria(String[] criteriaString) {
    if (criteriaString != null) {
      params = criteriaString;
    }

    criteria = new ArrayList(Arrays.asList(params));
    this.criteria = criteria;
  }


  /**
   *  Gets the createdBy attribute of the TicketReport object
   *
   *@return    The createdBy value
   */
  public int getCreatedBy() {
    return createdBy;
  }


  /**
   *  Sets the createdBy attribute of the TicketReport object
   *
   *@param  createdBy  The new createdBy value
   */
  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }


  /**
   *  Sets the criteriaVars attribute of the TicketReport object
   */
  public void setCriteriaVars() {
    if (!(criteria.contains("ticketid"))) {
      displayId = false;
    }
    if (!(criteria.contains("problem"))) {
      displayProblem = false;
    }
    if (!criteria.contains("location")) {
      displayLocation = false;
    }
    if (!(criteria.contains("source"))) {
      displaySourceName = false;
    }
    if (!(criteria.contains("contact"))) {
      displayContactName = false;
    }
    if (!(criteria.contains("severity"))) {
      displaySeverity = false;
    }
    if (!(criteria.contains("priority"))) {
      displayPriority = false;
    }
    if (!(criteria.contains("category"))) {
      displayCategory = false;
    }
    if (!(criteria.contains("department"))) {
      displayDepartment = false;
    }
    if (!(criteria.contains("owner"))) {
      displayOwner = false;
    }
    if (!(criteria.contains("solution"))) {
      displaySolution = false;
    }
    if (!(criteria.contains("closed"))) {
      displayClosed = false;
    }
    if (!(criteria.contains("entered"))) {
      displayEntered = false;
    }
    if (!(criteria.contains("enteredBy"))) {
      displayEnteredBy = false;
    }
    if (!(criteria.contains("modified"))) {
      displayModified = false;
    }
    if (!(criteria.contains("modifiedBy"))) {
      displayModifiedBy = false;
    }
    if (!(criteria.contains("assignedTo"))) {
      displayAssignedTo = false;
    }
    if (!(criteria.contains("contact"))) {
      displayContactName = false;
    }
    if (!(criteria.contains("comment"))) {
      displayComment = false;
    }
    if (!(criteria.contains("assignmentDate"))) {
      displayAssignmentDate = false;
    }
    if (!(criteria.contains("estimatedResolutionDate"))) {
      displayEstimatedResolutionDate = false;
    }
    if (!(criteria.contains("resolutionDate"))) {
      displayResolutionDate = false;
    }
    if (!(criteria.contains("organization"))) {
      displayOrganization = false;
    }
  }


  /**
   *  Gets the params attribute of the TicketReport object
   *
   *@return    The params value
   */
  public String[] getParams() {
    return params;
  }


  /**
   *  Sets the params attribute of the TicketReport object
   *
   *@param  params  The new params value
   */
  public void setParams(String[] params) {
    this.params = params;
  }


  /**
   *  Gets the filenameToUse attribute of the TicketReport object
   *
   *@return    The filenameToUse value
   */
  public String getFilenameToUse() {
    return filenameToUse;
  }


  /**
   *  Sets the filenameToUse attribute of the TicketReport object
   *
   *@param  filenameToUse  The new filenameToUse value
   */
  public void setFilenameToUse(String filenameToUse) {
    this.filenameToUse = filenameToUse;
  }


  /**
   *  Description of the Method
   */
  public void buildReportBaseInfo() {
    rep.setDelimitedCharacter(delimiter);
    rep.setHeader(header + ": " + subject);
  }


  /**
   *  Description of the Method
   */
  public void buildReportHeaders() {
    if (joinOrgs) {
      orgReportJoin.buildReportHeaders(rep);
    }

    Iterator y = criteria.iterator();
    while (y.hasNext()) {
      String param = (String) y.next();

      if (param.equals("ticketid")) {
        rep.addColumn("Ticket Id");
      }
      if (param.equals("problem")) {
        rep.addColumn("Issue");
      }
      if (param.equals("location")) {
        rep.addColumn("Location");
      }
      if (param.equals("source")) {
        rep.addColumn("Source");
      }
      if (param.equals("severity")) {
        rep.addColumn("Severity");
      }
      if (param.equals("priority")) {
        rep.addColumn("Priority");
      }
      if (param.equals("category")) {
        rep.addColumn("Category");
      }
      if (param.equals("department")) {
        rep.addColumn("Department");
      }
      //if (displayOwner) { rep.addColumn("Owner"); }
      if (param.equals("solution")) {
        rep.addColumn("Solution");
      }
      if (param.equals("closed")) {
        rep.addColumn("Closed");
      }
      if (param.equals("entered")) {
        rep.addColumn("Entered");
      }
      if (param.equals("enteredBy")) {
        rep.addColumn("Entered By");
      }
      if (param.equals("modified")) {
        rep.addColumn("Modified");
      }
      if (param.equals("modifiedBy")) {
        rep.addColumn("Modified By");
      }
      if (param.equals("assignedTo")) {
        rep.addColumn("Resource Assigned");
      }
      if (param.equals("contact")) {
        rep.addColumn("Contact Name");
      }
      if (param.equals("comment")) {
        rep.addColumn("Issue Notes");
      }
      if (param.equals("assignmentDate")) {
        rep.addColumn("Assignment Date");
      }
      if (param.equals("estimatedResolutionDate")) {
        rep.addColumn("Estimated Resolution Date");
      }
      if (param.equals("resolutionDate")) {
        rep.addColumn("Resolution Date");
      }
      if (param.equals("organization")) {
        rep.addColumn("Organization");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  passedReport  Description of the Parameter
   */
  public void buildReportHeaders(Report passedReport) {
    Iterator y = criteria.iterator();
    while (y.hasNext()) {
      String param = (String) y.next();

      if (param.equals("ticketid")) {
        passedReport.addColumn("Ticket Id");
      }
      if (param.equals("problem")) {
        passedReport.addColumn("Issue");
      }
      if (param.equals("location")) {
        rep.addColumn("Location");
      }
      if (param.equals("source")) {
        passedReport.addColumn("Source");
      }
      //if (displayContactName) { passedReport.addColumn("Contact Name"); }
      if (param.equals("severity")) {
        passedReport.addColumn("Severity");
      }
      if (param.equals("priority")) {
        passedReport.addColumn("Priority");
      }
      if (param.equals("category")) {
        passedReport.addColumn("Category");
      }
      if (param.equals("department")) {
        passedReport.addColumn("Department");
      }
      //if (displayOwner) { passedReport.addColumn("Owner"); }
      if (param.equals("solution")) {
        passedReport.addColumn("Solution");
      }
      if (param.equals("closed")) {
        passedReport.addColumn("Closed");
      }
      if (param.equals("entered")) {
        passedReport.addColumn("Entered");
      }
      if (param.equals("enteredBy")) {
        passedReport.addColumn("Entered By");
      }
      if (param.equals("modified")) {
        passedReport.addColumn("Modified");
      }
      if (param.equals("modifiedBy")) {
        passedReport.addColumn("Modified By");
      }
      if (param.equals("assignedTo")) {
        passedReport.addColumn("Resource Assigned");
      }
      if (param.equals("contact")) {
        passedReport.addColumn("Contact Name");
      }
      if (param.equals("comment")) {
        passedReport.addColumn("Issue Notes");
      }
      if (param.equals("assignmentDate")) {
        passedReport.addColumn("Assignment Date");
      }
      if (param.equals("estimatedResolutionDate")) {
        passedReport.addColumn("Estimated Resolution Date");
      }
      if (param.equals("resolutionDate")) {
        passedReport.addColumn("Resolution Date");
      }
      if (param.equals("organization")) {
        passedReport.addColumn("Organization");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildReportData(Connection db, ActionContext context) throws SQLException {
    this.buildList(db);

    boolean writeOut = false;
    Organization tempOrg = null;

    Iterator x = this.iterator();
    while (x.hasNext()) {
      Ticket thisTic = (Ticket) x.next();
      thisTic.buildHistory(db);
      thisTic.buildContactInformation(db);
      ReportRow thisRow = new ReportRow();

      if (joinOrgs && thisTic.getOrgId() > -1) {
        tempOrg = new Organization(db, thisTic.getOrgId());

        if (limitId > -1) {
          if (tempOrg.getOwner() == limitId) {
            orgReportJoin.addDataRow(thisRow, tempOrg);
            writeOut = true;
          }
        } else {
          orgReportJoin.addDataRow(thisRow, tempOrg);
          writeOut = true;
        }
      }

      if (!joinOrgs || writeOut == true) {

        Iterator y = criteria.iterator();
        while (y.hasNext()) {
          String param = (String) y.next();

          if (param.equals("ticketid")) {
            thisRow.addCell(thisTic.getId());
          }
          if (param.equals("problem")) {
            thisRow.addCell(thisTic.getProblem());
          }
          if (param.equals("location")) {
            thisRow.addCell(thisTic.getLocation());
          }
          if (param.equals("source")) {
            thisRow.addCell(thisTic.getSourceName());
          }
          if (param.equals("severity")) {
            thisRow.addCell(thisTic.getSeverityName());
          }
          if (param.equals("priority")) {
            thisRow.addCell(thisTic.getPriorityName());
          }
          if (param.equals("category")) {
            thisRow.addCell(thisTic.getCategoryName());
          }
          if (param.equals("department")) {
            thisRow.addCell(thisTic.getDepartmentName());
          }
          if (param.equals("solution")) {
            thisRow.addCell(thisTic.getSolution());
          }
          if (param.equals("closed")) {
            thisRow.addCell(thisTic.getClosedString());
          }
          if (param.equals("entered")) {
            thisRow.addCell(thisTic.getEnteredString());
          }
          if (param.equals("enteredBy")) {
            thisRow.addCell(UserList.retrieveUserContact(context, thisTic.getEnteredBy()).getNameLastFirst());
          }
          if (param.equals("modified")) {
            thisRow.addCell(thisTic.getModifiedString());
          }
          if (param.equals("modifiedBy")) {
            thisRow.addCell(UserList.retrieveUserContact(context, thisTic.getModifiedBy()).getNameLastFirst());
          }
          if (param.equals("assignedTo")) {
            if (thisTic.getAssignedTo() > 0) {
              thisRow.addCell(UserList.retrieveUserContact(context, thisTic.getAssignedTo()).getNameLastFirst());
            } else {
              thisRow.addCell("Unassigned");
            }
          }
          if (param.equals("contact")) {
            thisRow.addCell(thisTic.getThisContact().getNameLastFirst());
          }
          if (param.equals("comment")) {
            thisRow.addCell(thisTic.getHistory().getComments());
          }
          if (param.equals("assignmentDate")) {
            thisRow.addCell(thisTic.getAssignedDateString());
          }
          if (param.equals("estimatedResolutionDate")) {
            thisRow.addCell(thisTic.getEstimatedResolutionDateString());
          }
          if (param.equals("resolutionDate")) {
            thisRow.addCell(thisTic.getResolutionDateString());
          }
          if (param.equals("organization")) {
            thisRow.addCell(thisTic.getCompanyName());
          }
        }
        rep.addRow(thisRow);
      }
      writeOut = false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildReportFull(Connection db, ActionContext context) throws SQLException {
    buildReportBaseInfo();
    buildReportHeaders();
    buildReportData(db, context);
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public boolean saveAndInsert(Connection db) throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    filenameToUse = formatter.format(new java.util.Date());
    File f = new File(filePath);
    f.mkdirs();

    File fileLink = new File(filePath + filenameToUse + ".csv");

    rep.saveHtml(filePath + filenameToUse + ".html");
    rep.saveDelimited(filePath + filenameToUse + ".csv");

    if (joinOrgs) {
      thisItem.setLinkModuleId(Constants.DOCUMENTS_ACCOUNTS_REPORTS);
    } else {
      thisItem.setLinkModuleId(Constants.DOCUMENTS_TICKETS_REPORTS);
    }
    thisItem.setLinkItemId(0);
    thisItem.setEnteredBy(enteredBy);
    thisItem.setModifiedBy(modifiedBy);
    thisItem.setSubject(subject);
    thisItem.setClientFilename(filenameToUse + ".csv");
    thisItem.setFilename(filenameToUse);
    thisItem.setSize((int) fileLink.length());
    thisItem.insert(db);

    return true;
  }
}

