//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import java.io.*;
import java.text.*;

public class TicketReport extends TicketList {
	protected Report rep = new Report();
	//default delimiter is comma
	protected String delimiter = ",";
	protected String header = "CFS Tickets";
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
	protected String[] params = new String[] {"ticketid", "organization", "problem" };
	
	protected boolean displayId = true;
	protected boolean displayProblem = true;
	protected boolean displaySourceName = true;
	protected boolean displayContactName = true;
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
  protected boolean displayOrganization = true;
  
	protected OrganizationReport orgReportJoin = new OrganizationReport();
	protected boolean joinOrgs = false;

	public TicketReport() { }
	
	public void setRep(Report tmp) { this.rep = tmp; }
	public void setDelimiter(String tmp) { this.delimiter = tmp; }
	public void setHeader(String tmp) { this.header = tmp; }
	public void setTdFormat(String tmp) { this.tdFormat = tmp; }
	public Report getRep() { return rep; }
	public String getDelimiter() { return delimiter; }
	public String getHeader() { return header; }
	public String getTdFormat() { return tdFormat; }
	public String getFilePath() { return filePath; }
	public FileItem getThisItem() { return thisItem; }
	public void setFilePath(String tmp) { this.filePath = tmp; }
	public void setThisItem(FileItem tmp) { this.thisItem = tmp; }
	public String getSubject() { return subject; }
	public int getEnteredBy() { return enteredBy; }
	public int getModifiedBy() { return modifiedBy; }
	public void setSubject(String tmp) { this.subject = tmp; }
	public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
	public void setModifiedBy(int tmp) { this.modifiedBy = tmp; }
	public boolean getDisplayId() { return displayId; }
	public boolean getDisplayProblem() { return displayProblem; }
	public boolean getDisplaySourceName() { return displaySourceName; }
	public boolean getDisplayContactName() { return displayContactName; }
	public boolean getDisplaySeverity() { return displaySeverity; }
	public boolean getDisplayPriority() { return displayPriority; }
	public boolean getDisplayCategory() { return displayCategory; }
	public boolean getDisplayDepartment() { return displayDepartment; }
	public boolean getDisplayOwner() { return displayOwner; }
	public boolean getDisplaySolution() { return displaySolution; }
	public boolean getDisplayClosed() { return displayClosed; }
	public boolean getDisplayEntered() { return displayEntered; }
	public boolean getDisplayEnteredBy() { return displayEnteredBy; }
	public boolean getDisplayModified() { return displayModified; }
	public boolean getDisplayModifiedBy() { return displayModifiedBy; }
	public void setDisplayId(boolean tmp) { this.displayId = tmp; }
	public void setDisplayProblem(boolean tmp) { this.displayProblem = tmp; }
	public void setDisplaySourceName(boolean tmp) { this.displaySourceName = tmp; }
	public void setDisplayContactName(boolean tmp) { this.displayContactName = tmp; }
	public void setDisplaySeverity(boolean tmp) { this.displaySeverity = tmp; }
	public void setDisplayPriority(boolean tmp) { this.displayPriority = tmp; }
	public void setDisplayCategory(boolean tmp) { this.displayCategory = tmp; }
	public void setDisplayDepartment(boolean tmp) { this.displayDepartment = tmp; }
	public void setDisplayOwner(boolean tmp) { this.displayOwner = tmp; }
	public void setDisplaySolution(boolean tmp) { this.displaySolution = tmp; }
	public void setDisplayClosed(boolean tmp) { this.displayClosed = tmp; }
	public void setDisplayEntered(boolean tmp) { this.displayEntered = tmp; }
	public void setDisplayEnteredBy(boolean tmp) { this.displayEnteredBy = tmp; }
	public void setDisplayModified(boolean tmp) { this.displayModified = tmp; }
	public void setDisplayModifiedBy(boolean tmp) { this.displayModifiedBy = tmp; }
	public OrganizationReport getOrgReportJoin() { return orgReportJoin; }
	public boolean getJoinOrgs() { return joinOrgs; }
	public void setOrgReportJoin(OrganizationReport tmp) { this.orgReportJoin = tmp; }
	public void setJoinOrgs(boolean tmp) { this.joinOrgs = tmp; }
        
        public boolean getDisplayAssignedTo() {
                return displayAssignedTo;
        }
        public void setDisplayAssignedTo(boolean displayAssignedTo) {
                this.displayAssignedTo = displayAssignedTo;
        }
        public boolean getDisplayOrganization() {
	return displayOrganization;
}
public void setDisplayOrganization(boolean displayOrganization) {
	this.displayOrganization = displayOrganization;
}
	
	public int getLimitId() {
		return limitId;
	}
	public void setLimitId(int limitId) {
		this.limitId = limitId;
	}

	public ArrayList getCriteria() {
		return criteria;
	}
	public void setCriteria(String[] criteriaString) {
		if (criteriaString != null) {
			params = criteriaString;
		} 
		
		criteria = new ArrayList(Arrays.asList(params));
		this.criteria = criteria;
	}
  public int getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(int createdBy) {
	this.createdBy = createdBy;
}

	public void setCriteriaVars() {
		if ( !(criteria.contains("ticketid")) ) { displayId = false; }
		if ( !(criteria.contains("problem")) ) { displayProblem = false; }
		if ( !(criteria.contains("source")) ) { displaySourceName = false; }
		if ( !(criteria.contains("contact")) ) { displayContactName = false; }
		if ( !(criteria.contains("severity")) ) { displaySeverity = false; }
		if ( !(criteria.contains("priority")) ) { displayPriority = false; }
		if ( !(criteria.contains("category")) ) { displayCategory = false; }
		if ( !(criteria.contains("department")) ) { displayDepartment = false; }
		if ( !(criteria.contains("owner")) ) { displayOwner = false; }
		if ( !(criteria.contains("solution")) ) { displaySolution = false; }
		if ( !(criteria.contains("closed")) ) { displayClosed = false; }
		if ( !(criteria.contains("entered")) ) { displayEntered = false; }
		if ( !(criteria.contains("enteredBy")) ) { displayEnteredBy = false; }
		if ( !(criteria.contains("modified")) ) { displayModified = false; }
		if ( !(criteria.contains("modifiedBy")) ) { displayModifiedBy = false; }
    if ( !(criteria.contains("assignedTo")) ) { displayAssignedTo = false; }
    if ( !(criteria.contains("organization")) ) { displayOrganization = false; }
	}
	
	public String[] getParams() {
		return params;
	}
	public void setParams(String[] params) {
		this.params = params;
	}

	public String getFilenameToUse() {
		return filenameToUse;
	}
	public void setFilenameToUse(String filenameToUse) {
		this.filenameToUse = filenameToUse;
	}
	
	public void buildReportBaseInfo() {
		rep.setDelimitedCharacter(delimiter);
		rep.setHeader(header + ": " + subject);
	}
	
	public void buildReportHeaders() {
		if (joinOrgs) { orgReportJoin.buildReportHeaders(rep); }
    
    Iterator y = criteria.iterator();
		while (y.hasNext()) {
			String param = (String) y.next();
		
		if (param.equals("ticketid")) { rep.addColumn("Ticket Id"); }
		if (param.equals("problem")) { rep.addColumn("Issue"); }
		if (param.equals("source")) { rep.addColumn("Source"); }
		//if (displayContactName) { rep.addColumn("Contact Name"); }
		if (param.equals("severity")) { rep.addColumn("Severity"); }
		if (param.equals("priority")) { rep.addColumn("Priority"); }
		if (param.equals("category")) { rep.addColumn("Category"); }
		if (param.equals("department")) { rep.addColumn("Department"); }
		//if (displayOwner) { rep.addColumn("Owner"); }
		if (param.equals("solution")) { rep.addColumn("Solution"); }
		if (param.equals("closed")) { rep.addColumn("Closed"); }
		if (param.equals("entered")) { rep.addColumn("Entered"); }
		if (param.equals("enteredBy")) { rep.addColumn("Entered By"); }
		if (param.equals("modified")) { rep.addColumn("Modified"); }
		if (param.equals("modifiedBy")) { rep.addColumn("Modified By"); }
    if (param.equals("assignedTo")) { rep.addColumn("Assigned To"); }
    if (param.equals("organization")) { rep.addColumn("Organization"); }
    
    }
	}
	
	public void buildReportHeaders(Report passedReport) {
    Iterator y = criteria.iterator();
		while (y.hasNext()) {
			String param = (String) y.next();
		
		if (param.equals("ticketid")) { passedReport.addColumn("Ticket Id"); }
		if (param.equals("problem")) { passedReport.addColumn("Issue"); }
		if (param.equals("source")) { passedReport.addColumn("Source"); }
		//if (displayContactName) { passedReport.addColumn("Contact Name"); }
		if (param.equals("severity")) { passedReport.addColumn("Severity"); }
		if (param.equals("priority")) { passedReport.addColumn("Priority"); }
		if (param.equals("category")) { passedReport.addColumn("Category"); }
		if (param.equals("department")) { passedReport.addColumn("Department"); }
		//if (displayOwner) { passedReport.addColumn("Owner"); }
		if (param.equals("solution")) { passedReport.addColumn("Solution"); }
		if (param.equals("closed")) { passedReport.addColumn("Closed"); }
		if (param.equals("entered")) { passedReport.addColumn("Entered"); }
		if (param.equals("enteredBy")) { passedReport.addColumn("Entered By"); }
		if (param.equals("modified")) { passedReport.addColumn("Modified"); }
		if (param.equals("modifiedBy")) { passedReport.addColumn("Modified By"); }
    if (param.equals("assignedTo")) { passedReport.addColumn("Assigned To"); }
    if (param.equals("organization")) { passedReport.addColumn("Organization"); }
    
    }
	}
	
	public void buildReportData(Connection db) throws SQLException {
		this.buildList(db);
		
		boolean writeOut = false;
		Organization tempOrg = null;
		
		Iterator x = this.iterator();
		while (x.hasNext()) {
			Ticket thisTic = (Ticket) x.next();
			thisTic.buildContactInformation(db);
			ReportRow thisRow = new ReportRow();
			
			if (joinOrgs && thisTic.getOrgId() > -1) { 
				tempOrg = new Organization(db, thisTic.getOrgId());
				
				if ( limitId > -1 ) {
					if ( tempOrg.getOwner() == limitId ) {
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
          
				if (param.equals("ticketid")) { thisRow.addCell(thisTic.getId());	}
				if (param.equals("problem")) { thisRow.addCell(thisTic.getProblem()); }
				if (param.equals("source")) {	thisRow.addCell(thisTic.getSourceName()); }
				//if (displayContactName) {	thisRow.addCell(thisTic.getThisContact().getNameLastFirst()); }
				if (param.equals("severity")) {	thisRow.addCell(thisTic.getSeverityName());}
				if (param.equals("priority")) {	thisRow.addCell(thisTic.getPriorityName());}
				if (param.equals("category")) {	thisRow.addCell(thisTic.getCategoryName());}
				if (param.equals("department")) {	thisRow.addCell(thisTic.getDepartmentName());}
				//if (displayOwner) {	thisRow.addCell(thisTic.getOwnerName());}
				if (param.equals("solution")) { thisRow.addCell(thisTic.getSolution());}
				if (param.equals("closed")) { thisRow.addCell(thisTic.getClosedString());}
				if (param.equals("entered")) { thisRow.addCell(thisTic.getEnteredString());}
				if (param.equals("enteredBy")) { thisRow.addCell(thisTic.getEnteredByName());  }
				if (param.equals("modified")) { thisRow.addCell(thisTic.getModifiedString()); }
				if (param.equals("modifiedBy")) { thisRow.addCell(thisTic.getModifiedByName()); }
        if (param.equals("assignedTo")) { thisRow.addCell(thisTic.getOwnerName()); }
        if (param.equals("organization")) { thisRow.addCell(thisTic.getCompanyName()); }
        
        }
			
				rep.addRow(thisRow);
			}
			
			writeOut = false;
		}
	}

	public void buildReportFull(Connection db) throws SQLException {
		buildReportBaseInfo();
		buildReportHeaders();
		buildReportData(db);
	}
	
	public boolean saveAndInsert(Connection db) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		filenameToUse = formatter.format(new java.util.Date());
		File f = new File(filePath);
		f.mkdirs();
		
		File fileLink = new File(filePath + filenameToUse + ".csv");
		
		rep.saveHtml(filePath + filenameToUse + ".html");
		rep.saveDelimited(filePath + filenameToUse + ".csv");
		
		if (joinOrgs) { thisItem.setLinkModuleId(Constants.ACCOUNTS_REPORTS); }
		else {
			thisItem.setLinkModuleId(Constants.TICKETS_REPORTS);
		}
		
		thisItem.setLinkItemId(0);
		thisItem.setProjectId(-1);
		thisItem.setEnteredBy(enteredBy);
		thisItem.setModifiedBy(modifiedBy);
		thisItem.setSubject(subject);
		thisItem.setClientFilename(filenameToUse + ".csv");
		thisItem.setFilename(filenameToUse);
		thisItem.setSize((int)fileLink.length());
		thisItem.insert(db);
		
		return true;
	}
}
