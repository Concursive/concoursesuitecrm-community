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
	protected ArrayList criteria = null;
	String[] params = null;
	
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
			criteria = new ArrayList(Arrays.asList(params));
			setCriteriaVars();
		} else {
			criteria = new ArrayList();
		}
	
		this.criteria = criteria;
	}
	
	public void setCriteriaVars() {
		if ( !(criteria.contains("id")) ) { displayId = false; }
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
		
		if (displayId) { rep.addColumn("Ticket Id"); }
		if (displayProblem) { rep.addColumn("Problem"); }
		if (displaySourceName) { rep.addColumn("Source"); }
		if (displayContactName) { rep.addColumn("Contact Name"); }
		if (displaySeverity) { rep.addColumn("Severity"); }
		if (displayPriority) { rep.addColumn("Priority"); }
		if (displayCategory) { rep.addColumn("Category"); }
		if (displayDepartment) { rep.addColumn("Department"); }
		if (displayOwner) { rep.addColumn("Owner"); }
		if (displaySolution) { rep.addColumn("Solution"); }
		if (displayClosed) { rep.addColumn("Closed"); }
		if (displayEntered) { rep.addColumn("Entered"); }
		if (displayEnteredBy) { rep.addColumn("Entered By"); }
		if (displayModified) { rep.addColumn("Modified"); }
		if (displayModifiedBy) { rep.addColumn("Modified By"); }
	}
	
	public void buildReportHeaders(Report passedReport) {
		if (displayId) { passedReport.addColumn("Ticket Id"); }
		if (displayProblem) { passedReport.addColumn("Problem"); }
		if (displaySourceName) { passedReport.addColumn("Source"); }
		if (displayContactName) { passedReport.addColumn("Contact Name"); }
		if (displaySeverity) { passedReport.addColumn("Severity"); }
		if (displayPriority) { passedReport.addColumn("Priority"); }
		if (displayCategory) { passedReport.addColumn("Category"); }
		if (displayDepartment) { passedReport.addColumn("Department"); }
		if (displayOwner) { passedReport.addColumn("Owner"); }
		if (displaySolution) { passedReport.addColumn("Solution"); }
		if (displayClosed) { passedReport.addColumn("Closed"); }
		if (displayEntered) { passedReport.addColumn("Entered"); }
		if (displayEnteredBy) { passedReport.addColumn("Entered By"); }
		if (displayModified) { passedReport.addColumn("Modified"); }
		if (displayModifiedBy) { passedReport.addColumn("Modified By"); }
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
				if (displayId) { thisRow.addCell(thisTic.getId());	}
				if (displayProblem) { thisRow.addCell(thisTic.getProblem()); }
				if (displaySourceName) {	thisRow.addCell(thisTic.getSourceName()); }
				if (displayContactName) {	thisRow.addCell(thisTic.getThisContact().getNameLastFirst()); }
				if (displaySeverity) {	thisRow.addCell(thisTic.getSeverityName());}
				if (displayPriority) {	thisRow.addCell(thisTic.getPriorityName());}
				if (displayCategory) {	thisRow.addCell(thisTic.getCategoryName());}
				if (displayDepartment) {	thisRow.addCell(thisTic.getDepartmentName());}
				if (displayOwner) {	thisRow.addCell(thisTic.getOwnerName());}
				if (displaySolution) { thisRow.addCell(thisTic.getSolution());}
				if (displayClosed) { thisRow.addCell(thisTic.getClosedString());}
				if (displayEntered) { thisRow.addCell(thisTic.getEnteredString());}
				if (displayEnteredBy) { thisRow.addCell(thisTic.getEnteredByName());  }
				if (displayModified) { thisRow.addCell(thisTic.getModifiedString()); }
				if (displayModifiedBy) { thisRow.addCell(thisTic.getModifiedByName()); }
			
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
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddhhmmss");
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
