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

public class ContactReport extends ContactList {
	protected Report rep = new Report();
	//default delimiter is comma
	protected String delimiter = ",";
	protected String header = "CFS Contacts and Resources";
	protected String tdFormat = "";
	protected String filePath = "";
	protected String filenameToUse = "";
	protected FileItem thisItem = new FileItem();
	
	protected String subject = "";
	protected int enteredBy = -1;
	protected int modifiedBy = -1;
	protected ArrayList criteria = null;
	String[] params = null;
	
	protected boolean displayType = true;
	protected boolean displayNameLast = true;
	protected boolean displayNameMiddle = true;
	protected boolean displayNameFirst = true;
	protected boolean displayCompany = true;
	protected boolean displayTitle = true;
	protected boolean displayDepartment = true;
	protected boolean displayEntered = true;
	protected boolean displayEnteredBy = true;
	protected boolean displayModified = true;
	protected boolean displayModifiedBy = true;
	protected boolean displayOwner = true;
	protected boolean displayBusinessEmail = true;
	protected boolean displayBusinessPhone = true;
	protected boolean displayBusinessAddress = true;
	protected boolean displayCity = true;
	protected boolean displayState = true;
	protected boolean displayZip = true;
	protected boolean displayCountry = true;
	protected boolean displayNotes = true;
	
	protected OrganizationReport orgReportJoin = new OrganizationReport();
	protected boolean joinOrgs = false;
	
	public ContactReport() { }
	
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
	public boolean getDisplayType() { return displayType; }
	public boolean getDisplayNameLast() { return displayNameLast; }
	public boolean getDisplayNameMiddle() { return displayNameMiddle; }
	public boolean getDisplayNameFirst() { return displayNameFirst; }
	public boolean getDisplayCompany() { return displayCompany; }
	public boolean getDisplayTitle() { return displayTitle; }
	public boolean getDisplayDepartment() { return displayDepartment; }
	public boolean getDisplayEntered() { return displayEntered; }
	public boolean getDisplayEnteredBy() { return displayEnteredBy; }
	public boolean getDisplayModified() { return displayModified; }
	public boolean getDisplayModifiedBy() { return displayModifiedBy; }
	public boolean getDisplayOwner() { return displayOwner; }
	public boolean getDisplayBusinessEmail() { return displayBusinessEmail; }
	public boolean getDisplayBusinessPhone() { return displayBusinessPhone; }
	public boolean getDisplayBusinessAddress() { return displayBusinessAddress; }
	public boolean getDisplayCity() { return displayCity; }
	public boolean getDisplayState() { return displayState; }
	public boolean getDisplayZip() { return displayZip; }
	public boolean getDisplayCountry() { return displayCountry; }
	public boolean getDisplayNotes() { return displayNotes; }
	public void setDisplayType(boolean tmp) { this.displayType = tmp; }
	public void setDisplayNameLast(boolean tmp) { this.displayNameLast = tmp; }
	public void setDisplayNameMiddle(boolean tmp) { this.displayNameMiddle = tmp; }
	public void setDisplayNameFirst(boolean tmp) { this.displayNameFirst = tmp; }
	public void setDisplayCompany(boolean tmp) { this.displayCompany = tmp; }
	public void setDisplayTitle(boolean tmp) { this.displayTitle = tmp; }
	public void setDisplayDepartment(boolean tmp) { this.displayDepartment = tmp; }
	public void setDisplayEntered(boolean tmp) { this.displayEntered = tmp; }
	public void setDisplayEnteredBy(boolean tmp) { this.displayEnteredBy = tmp; }
	public void setDisplayModified(boolean tmp) { this.displayModified = tmp; }
	public void setDisplayModifiedBy(boolean tmp) { this.displayModifiedBy = tmp; }
	public void setDisplayOwner(boolean tmp) { this.displayOwner = tmp; }
	public void setDisplayBusinessEmail(boolean tmp) { this.displayBusinessEmail = tmp; }
	public void setDisplayBusinessPhone(boolean tmp) { this.displayBusinessPhone = tmp; }
	public void setDisplayBusinessAddress(boolean tmp) { this.displayBusinessAddress = tmp; }
	public void setDisplayCity(boolean tmp) { this.displayCity = tmp; }
	public void setDisplayState(boolean tmp) { this.displayState = tmp; }
	public void setDisplayZip(boolean tmp) { this.displayZip = tmp; }
	public void setDisplayCountry(boolean tmp) { this.displayCountry = tmp; }
	public void setDisplayNotes(boolean tmp) { this.displayNotes = tmp; }
	public OrganizationReport getOrgReportJoin() { return orgReportJoin; }
	public boolean getJoinOrgs() { return joinOrgs; }
	public void setOrgReportJoin(OrganizationReport tmp) { this.orgReportJoin = tmp; }
	public void setJoinOrgs(boolean tmp) { this.joinOrgs = tmp; }

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
		if ( !(criteria.contains("type")) ) { displayType = false; }
		if ( !(criteria.contains("nameLast")) ) { displayNameLast = false; }
		if ( !(criteria.contains("nameFirst")) ) { displayNameFirst = false; }
		if ( !(criteria.contains("nameMiddle")) ) { displayNameMiddle = false; }
		if ( !(criteria.contains("company")) ) { displayCompany = false; }
		
		if ( !(criteria.contains("title")) ) { displayTitle = false; }
		if ( !(criteria.contains("department")) ) { displayDepartment = false; }
		if ( !(criteria.contains("entered")) ) { displayEntered = false; }
		if ( !(criteria.contains("enteredBy")) ) { displayEnteredBy = false; }
		if ( !(criteria.contains("modified")) ) { displayModified = false; }
		
		if ( !(criteria.contains("modifiedBy")) ) { displayModifiedBy = false; }
		if ( !(criteria.contains("owner")) ) { displayOwner = false; }
		if ( !(criteria.contains("businessEmail")) ) { displayBusinessEmail = false; }
		if ( !(criteria.contains("businessPhone")) ) { displayBusinessPhone = false; }
		if ( !(criteria.contains("businessAddress")) ) { displayBusinessAddress = false; }
		
		if ( !(criteria.contains("city")) ) { displayCity = false; }
		if ( !(criteria.contains("state")) ) { displayState = false; }
		if ( !(criteria.contains("zip")) ) { displayZip = false; }
		if ( !(criteria.contains("country")) ) { displayCountry = false; }
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
		if (joinOrgs) { orgReportJoin.buildReportHeaders(rep); }
		
		if (displayType) { rep.addColumn("Type"); }
		if (displayNameLast) { rep.addColumn("Last Name", "Last Name"); }
		if (displayNameFirst) { rep.addColumn("First Name", "First Name"); }
		if (displayNameMiddle) { rep.addColumn("Middle Name", "Middle Name"); }
		if (displayCompany) { rep.addColumn("Company"); }
		if (displayTitle) { rep.addColumn("Title"); }
		if (displayDepartment) { rep.addColumn("Department"); }
		if (displayEntered) { rep.addColumn("Entered"); }
		if (displayEnteredBy) { rep.addColumn("Entered By"); }
		if (displayModified) { rep.addColumn("Modified"); }
		if (displayModifiedBy) { rep.addColumn("Modified By"); }
		if (displayOwner) { rep.addColumn("Owner"); }
		if (displayBusinessEmail) { rep.addColumn("Business Email"); }
		if (displayBusinessPhone) { rep.addColumn("Business Phone"); }
		if (displayBusinessAddress) { rep.addColumn("Business Address"); }
		if (displayCity) { rep.addColumn("City"); }
		if (displayState) { rep.addColumn("State"); }
		if (displayZip) { rep.addColumn("Zip"); }
		if (displayCountry) { rep.addColumn("Country"); }
		if (displayNotes) { rep.addColumn("Notes"); }
	}
  
  public void buildData(Connection db) throws SQLException {
		this.buildList(db);
    this.buildReportData(db);
  }
	
	public void buildReportData(Connection db) throws SQLException {
		boolean writeOut = false;
		Organization tempOrg = null;
		
		Iterator x = this.iterator();
		while (x.hasNext()) {
			Contact thisContact = (Contact) x.next();
			ReportRow thisRow = new ReportRow();
			
			if (joinOrgs && thisContact.getOrgId() > 0) { 
				tempOrg = new Organization(db, thisContact.getOrgId());
				orgReportJoin.addDataRow(thisRow, tempOrg);
				writeOut = true;
			}
			
			if (!joinOrgs || writeOut == true) {
				if (displayType) { thisRow.addCell(thisContact.getTypeName()); }
				if (displayNameLast) { thisRow.addCell(thisContact.getNameLast()); }
				if (displayNameFirst) { thisRow.addCell(thisContact.getNameFirst()); }
				if (displayNameMiddle) { thisRow.addCell(thisContact.getNameMiddle()); }
				if (displayCompany) { thisRow.addCell(thisContact.getCompany()); }
				if (displayDepartment) { thisRow.addCell(thisContact.getDepartmentName()); }
				if (displayTitle) { thisRow.addCell(thisContact.getTitle()); }
				if (displayEntered) { thisRow.addCell(thisContact.getEnteredString()); }
				if (displayEnteredBy) { thisRow.addCell(thisContact.getEnteredByName()); }
				if (displayModified) { thisRow.addCell(thisContact.getModifiedString()); }
				if (displayModifiedBy) { thisRow.addCell(thisContact.getModifiedByName()); }
				if (displayOwner) { thisRow.addCell(thisContact.getOwnerName()); }
				if (displayBusinessEmail) { thisRow.addCell(thisContact.getEmailAddress("Business")); }
				if (displayBusinessPhone) { thisRow.addCell(thisContact.getPhoneNumber("Business")); }
				if (displayBusinessAddress) { thisRow.addCell(thisContact.getAddress("Business").getStreetAddressLine1() + " " + thisContact.getAddress("Business").getStreetAddressLine2()); }
				if (displayCity) { thisRow.addCell(thisContact.getAddress("Business").getCity()); }
				if (displayState) { thisRow.addCell(thisContact.getAddress("Business").getState()); }
				if (displayZip) { thisRow.addCell(thisContact.getAddress("Business").getZip()); }
				if (displayCountry) { thisRow.addCell(thisContact.getAddress("Business").getCountry()); }
				if (displayNotes) { thisRow.addCell(thisContact.getNotes()); }
				
				rep.addRow(thisRow);
			}
			
			writeOut = false;
			
		}
	}
		

	public void buildReportFull(Connection db) throws SQLException {
		buildReportBaseInfo();
		buildReportHeaders();
		buildData(db);
	}
	
	public boolean saveAndInsert(Connection db) throws Exception {
		int fileSize = save();
		
		if (joinOrgs) { thisItem.setLinkModuleId(Constants.ACCOUNTS_REPORTS); }
		else {
			thisItem.setLinkModuleId(Constants.CONTACTS_REPORTS);
		}
		
		thisItem.setLinkItemId(0);
		thisItem.setProjectId(-1);
		thisItem.setEnteredBy(enteredBy);
		thisItem.setModifiedBy(modifiedBy);
		thisItem.setSubject(subject);
		thisItem.setClientFilename(filenameToUse + ".csv");
		thisItem.setFilename(filenameToUse);
		thisItem.setSize(fileSize);
		thisItem.insert(db);
		
		return true;
	}
  
  public int save() throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddhhmmss");
		filenameToUse = formatter.format(new java.util.Date());
		File f = new File(filePath);
		f.mkdirs();
		
		File fileLink = new File(filePath + filenameToUse + ".csv");
		
		rep.saveHtml(filePath + filenameToUse + ".html");
		rep.saveDelimited(filePath + filenameToUse + ".csv");
    return ((int)fileLink.length());
  }
}
