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

public class OrganizationReport extends OrganizationList {
	protected Report rep = new Report();
	//default delimiter is comma
	protected String delimiter = ",";
	protected String header = "CFS Accounts";
	protected String tdFormat = "";
	protected String filePath = "";
	protected String filenameToUse = "";
	protected FileItem thisItem = new FileItem();
	
	protected String subject = "";
	protected int enteredBy = -1;
	protected int modifiedBy = -1;
	protected ArrayList criteria = null;
	String[] params = null;
	
	protected boolean displayId = true;
	protected boolean displayAccountName = true;
	protected boolean displayAccountNumber = true;
	protected boolean displayURL = true;
	protected boolean displayTicker = true;
	protected boolean displayEmployees = true;
	protected boolean displayEntered = true;
	protected boolean displayEnteredBy = true;
	protected boolean displayModified = true;
	protected boolean displayModifiedBy = true;
	protected boolean displayOwner = true;
	protected boolean displayContractEndDate = true;
	protected boolean displayNotes = true;
	
	protected boolean includeFolders = false;
	protected int folderId = -1;

	public OrganizationReport() { }
	
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
	public boolean getDisplayAccountName() { return displayAccountName; }
	public boolean getDisplayAccountNumber() { return displayAccountNumber; }
	public boolean getDisplayURL() { return displayURL; }
	public boolean getDisplayTicker() { return displayTicker; }
	public boolean getDisplayEmployees() { return displayEmployees; }
	public boolean getDisplayEntered() { return displayEntered; }
	public boolean getDisplayEnteredBy() { return displayEnteredBy; }
	public boolean getDisplayModified() { return displayModified; }
	public boolean getDisplayModifiedBy() { return displayModifiedBy; }
	public boolean getDisplayOwner() { return displayOwner; }
	public boolean getDisplayContractEndDate() { return displayContractEndDate; }
	public boolean getDisplayNotes() { return displayNotes; }
	public void setDisplayAccountName(boolean tmp) { this.displayAccountName = tmp; }
	public void setDisplayAccountNumber(boolean tmp) { this.displayAccountNumber = tmp; }
	public void setDisplayURL(boolean tmp) { this.displayURL = tmp; }
	public void setDisplayTicker(boolean tmp) { this.displayTicker = tmp; }
	public void setDisplayEmployees(boolean tmp) { this.displayEmployees = tmp; }
	public void setDisplayEntered(boolean tmp) { this.displayEntered = tmp; }
	public void setDisplayEnteredBy(boolean tmp) { this.displayEnteredBy = tmp; }
	public void setDisplayModified(boolean tmp) { this.displayModified = tmp; }
	public void setDisplayModifiedBy(boolean tmp) { this.displayModifiedBy = tmp; }
	public void setDisplayOwner(boolean tmp) { this.displayOwner = tmp; }
	public void setDisplayContractEndDate(boolean tmp) { this.displayContractEndDate = tmp; }
	public void setDisplayNotes(boolean tmp) { this.displayNotes = tmp; }
	
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public boolean getDisplayId() {
		return displayId;
	}
	public void setDisplayId(boolean displayId) {
		this.displayId = displayId;
	}
	
	public void setIncludeFolders(boolean includeFolders) {
		this.includeFolders = includeFolders;
	}
	public boolean getIncludeFolders() {
		return includeFolders;
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
		if ( !(criteria.contains("accountName")) ) { displayAccountName = false; }
		if ( !(criteria.contains("accountNumber")) ) { displayAccountNumber = false; }
		if ( !(criteria.contains("url")) ) { displayURL = false; }
		if ( !(criteria.contains("ticker")) ) { displayTicker = false; }
		if ( !(criteria.contains("employees")) ) { displayEmployees = false; }
		if ( !(criteria.contains("entered")) ) { displayEntered = false; }
		if ( !(criteria.contains("enteredBy")) ) { displayEnteredBy = false; }
		if ( !(criteria.contains("modified")) ) { displayModified = false; }
		if ( !(criteria.contains("modifiedBy")) ) { displayModifiedBy = false; }
		if ( !(criteria.contains("owner")) ) { displayOwner = false; }
		if ( !(criteria.contains("contractEndDate")) ) { displayContractEndDate = false; }
		if ( !(criteria.contains("notes")) ) { displayNotes = false; }
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
		if (displayId) { rep.addColumn("Account ID"); }
		if (displayAccountName) { rep.addColumn("Account Name"); }
		if (displayAccountNumber) { rep.addColumn("Account No."); }
		if (displayURL) { rep.addColumn("URL"); }
		if (displayTicker) { rep.addColumn("Ticker"); }
		if (displayEmployees) { rep.addColumn("Employees"); }
		if (displayEntered) { rep.addColumn("Entered"); }
		if (displayEnteredBy) { rep.addColumn("Entered By"); }
		if (displayModified) { rep.addColumn("Modified"); }
		if (displayModifiedBy) { rep.addColumn("Modified By"); }
		if (displayOwner) { rep.addColumn("Owner"); }
		if (displayContractEndDate) { rep.addColumn("Contract End Date"); }
		if (displayNotes) { rep.addColumn("Notes"); }
		
		if (includeFolders) {
			rep.addColumn("Folder Name");
			rep.addColumn("Record Name");
			rep.addColumn("Group Name");
			rep.addColumn("Field Name");
			rep.addColumn("Field Value");
			rep.addColumn("Entered");
			rep.addColumn("Modified");
		}
		
		if (folderId > -1) {
			rep.addColumn("Folder Name");
		}
	}
	
	
	public void buildReportHeaders(Report passedReport) {
		if (displayId) { passedReport.addColumn("Account ID"); }
		if (displayAccountName) { passedReport.addColumn("Account Name"); }
		if (displayAccountNumber) { passedReport.addColumn("Account No."); }
		if (displayURL) { passedReport.addColumn("URL"); }
		if (displayTicker) { passedReport.addColumn("Ticker"); }
		if (displayEmployees) { passedReport.addColumn("Employees"); }
		if (displayEntered) { passedReport.addColumn("Entered"); }
		if (displayEnteredBy) { passedReport.addColumn("Entered By"); }
		if (displayModified) { passedReport.addColumn("Modified"); }
		if (displayModifiedBy) { passedReport.addColumn("Modified By"); }
		if (displayOwner) { passedReport.addColumn("Owner"); }
		if (displayContractEndDate) { passedReport.addColumn("Contract End Date"); }
		if (displayNotes) { passedReport.addColumn("Notes"); }
	}
	
	public void buildReportData(Connection db) throws SQLException {
		this.buildList(db);
		CustomFieldCategoryList thisList = new CustomFieldCategoryList();
		CustomFieldCategory thisCat = null;
		CustomFieldGroup thisGroup = new CustomFieldGroup();
		
		if (includeFolders) {
			thisList.setLinkModuleId(Constants.ACCOUNTS);
			thisList.setIncludeEnabled(Constants.TRUE);
			thisList.setIncludeScheduled(Constants.TRUE);
			thisList.setBuildResources(true);
			thisList.buildList(db);	
		} else if (folderId > -1) {
			thisCat = new CustomFieldCategory(db, folderId);
			thisCat.buildResources(db);
			
			Iterator grp = thisCat.iterator();
			thisGroup = new CustomFieldGroup();
			thisGroup = (CustomFieldGroup)grp.next();
			thisGroup.buildResources(db);
		
			Iterator fields = thisGroup.iterator();
		
			if (fields.hasNext()) {
				while (fields.hasNext()) {
					CustomField thisField = (CustomField)fields.next();
					rep.addColumn(thisField.getNameHtml());
				}
			}
		}
		
		Iterator x = this.iterator();
		while (x.hasNext()) {
			Organization thisOrg = (Organization) x.next();
			
			if (includeFolders) {
				
				CustomFieldRecordList recordList = new CustomFieldRecordList();
				thisGroup = new CustomFieldGroup();
			
				Iterator cat = thisList.iterator();
				while (cat.hasNext()) {
					thisCat = (CustomFieldCategory) cat.next();
				
					recordList = new CustomFieldRecordList();
					recordList.setLinkModuleId(Constants.ACCOUNTS);
					recordList.setLinkItemId(thisOrg.getOrgId());
					recordList.setCategoryId(thisCat.getId());
					recordList.buildList(db);
				
					Iterator rec = recordList.iterator();

					while (rec.hasNext()) {
						
						CustomFieldRecord thisRec = (CustomFieldRecord) rec.next();
						Iterator grp = thisCat.iterator();
							while (grp.hasNext()) {
								thisGroup = new CustomFieldGroup();
								thisGroup = (CustomFieldGroup)grp.next();
								thisGroup.buildResources(db);
				
								Iterator fields = thisGroup.iterator();
								if (fields.hasNext()) {
									while (fields.hasNext()) {
										
										ReportRow thisRow = new ReportRow();
										
										CustomField thisField = (CustomField)fields.next();
				
										thisField.setRecordId(thisRec.getId());
										thisField.buildResources(db);
				
										addDataRow( thisRow, thisOrg );
				
										thisRow.addCell(thisCat.getName());
										thisRow.addCell(thisCat.getName() + " #" + thisRec.getId());
										thisRow.addCell(thisGroup.getName());
										thisRow.addCell(thisField.getNameHtml());
										thisRow.addCell(thisField.getValueHtml());
										thisRow.addCell(thisRec.getEnteredString());
										thisRow.addCell(thisRec.getModifiedDateTimeString());
				
										rep.addRow(thisRow);
				
									}
								}
							}
						}
						
					}
					
			} else if (folderId > -1) {
				CustomFieldRecordList recordList = new CustomFieldRecordList();
				recordList.setLinkModuleId(Constants.ACCOUNTS);
				recordList.setLinkItemId(thisOrg.getOrgId());
				recordList.setCategoryId(thisCat.getId());
				recordList.buildList(db);
				
				Iterator rec = recordList.iterator();
				
				while (rec.hasNext()) {
					
					ReportRow thisRow = new ReportRow();
					addDataRow( thisRow, thisOrg );
					thisRow.addCell(thisCat.getName());
				
					CustomFieldRecord thisRec = (CustomFieldRecord) rec.next();
					Iterator grp = thisCat.iterator();
					while (grp.hasNext()) {
						thisGroup = new CustomFieldGroup();
						thisGroup = (CustomFieldGroup)grp.next();
						thisGroup.buildResources(db);
				
						Iterator fields = thisGroup.iterator();
						if (fields.hasNext()) {
							while (fields.hasNext()) {
				
								CustomField thisField = (CustomField)fields.next();
								thisField.setRecordId(thisRec.getId());
								thisField.buildResources(db);
				
								thisRow.addCell(thisField.getValueHtml());
							}
						}
					}
					
					rep.addRow(thisRow);
				}
			} else {
				ReportRow thisRow = new ReportRow();
				
				if (displayId) { thisRow.addCell(thisOrg.getOrgId());	}
				if (displayAccountName) { thisRow.addCell(thisOrg.getName());	}
				if (displayAccountNumber) { thisRow.addCell(thisOrg.getAccountNumber()); }
				if (displayURL) {	thisRow.addCell(thisOrg.getUrl()); }
				if (displayTicker) {	thisRow.addCell(thisOrg.getTicker()); }
				if (displayEmployees) {	thisRow.addCell(thisOrg.getEmployees());}
				if (displayEntered) {	thisRow.addCell(thisOrg.getEnteredString());}
				if (displayEnteredBy) {	thisRow.addCell(thisOrg.getEnteredByName());}
				if (displayModified) {	thisRow.addCell(thisOrg.getModifiedString());}
				if (displayModifiedBy) {	thisRow.addCell(thisOrg.getModifiedByName());}
				if (displayOwner) { thisRow.addCell(thisOrg.getOwnerName());}
				if (displayContractEndDate) { thisRow.addCell(thisOrg.getContractEndDateString());}
				if (displayNotes) { thisRow.addCell(thisOrg.getNotes());}
				
				rep.addRow(thisRow);
			}
		}
	}
	
	public void addDataRow(ReportRow thisRow, Organization thisOrg) throws SQLException {
		if (displayId) { thisRow.addCell(thisOrg.getOrgId());	}
		if (displayAccountName) { thisRow.addCell(thisOrg.getName());	}
		if (displayAccountNumber) { thisRow.addCell(thisOrg.getAccountNumber()); }
		if (displayURL) {	thisRow.addCell(thisOrg.getUrl()); }
		if (displayTicker) {	thisRow.addCell(thisOrg.getTicker()); }
		if (displayEmployees) {	thisRow.addCell(thisOrg.getEmployees());}
		if (displayEntered) {	thisRow.addCell(thisOrg.getEnteredString());}
		if (displayEnteredBy) {	thisRow.addCell(thisOrg.getEnteredByName());}
		if (displayModified) {	thisRow.addCell(thisOrg.getModifiedString());}
		if (displayModifiedBy) {	thisRow.addCell(thisOrg.getModifiedByName());}
		if (displayOwner) { thisRow.addCell(thisOrg.getOwnerName());}
		if (displayContractEndDate) { thisRow.addCell(thisOrg.getContractEndDateString());}
		if (displayNotes) { thisRow.addCell(thisOrg.getNotes());}
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
		
		thisItem.setLinkModuleId(Constants.ACCOUNTS_REPORTS);
		thisItem.setLinkItemId(0);
		thisItem.setProjectId(-1);
		thisItem.setEnteredBy(enteredBy);
		thisItem.setModifiedBy(modifiedBy);
		thisItem.setSubject(subject);
		thisItem.setClientFilename("accountreport-" + filenameToUse + ".csv");
		thisItem.setFilename(filenameToUse);
		thisItem.setSize((int)fileLink.length());
		thisItem.insert(db);
		
		return true;
	}
}
