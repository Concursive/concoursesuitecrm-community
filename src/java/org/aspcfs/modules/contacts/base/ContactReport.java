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
	protected int limitId = -1;
	protected ArrayList criteria = null;
	protected String[] params = new String[] {"id", "nameLast", "nameFirst", "company", "type", "nameMiddle", "title", "department", "entered", "enteredBy", "modified", "modifiedBy", "owner", "businessEmail", "businessPhone", "businessAddress", "city", "state", "zip", "country", "notes"};

	
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
		} 
		
		criteria = new ArrayList(Arrays.asList(params));
		this.criteria = criteria;
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
		rep.setHeader(header + ((!header.equals("") && !subject.equals(""))?": ":"") + subject);
	}
	
	public void buildReportHeaders() {
		if (joinOrgs) { orgReportJoin.buildReportHeaders(rep); }
		
		Iterator y = criteria.iterator();
		while (y.hasNext()) {
			String param = (String) y.next();
		
			if (param.equals("id")) { rep.addColumn("Contact Id"); }
			if (param.equals("type")) { rep.addColumn("Type"); }
			if (param.equals("nameLast")) { rep.addColumn("Last Name", "Last Name"); }
			if (param.equals("nameFirst")) { rep.addColumn("First Name", "First Name"); }
			if (param.equals("nameMiddle")) { rep.addColumn("Middle Name", "Middle Name"); }
			if (param.equals("company")) { rep.addColumn("Company"); }
			if (param.equals("title")) { rep.addColumn("Title"); }
			if (param.equals("department")) { rep.addColumn("Department"); }
			if (param.equals("entered")) { rep.addColumn("Entered"); }
			if (param.equals("enteredBy")) { rep.addColumn("Entered By"); }
			if (param.equals("modified")) { rep.addColumn("Modified"); }
			if (param.equals("modifiedBy")) { rep.addColumn("Modified By"); }
			if (param.equals("owner")) { rep.addColumn("Owner"); }
			if (param.equals("businessEmail")) { rep.addColumn("Business Email"); }
			if (param.equals("businessPhone")) { rep.addColumn("Business Phone"); }
			if (param.equals("businessAddress")) { 
				rep.addColumn("Business Address Line 1"); 
				rep.addColumn("Business Address Line 2"); 
			}
			if (param.equals("city")) { rep.addColumn("City"); }
			if (param.equals("state")) { rep.addColumn("State"); }
			if (param.equals("zip")) { rep.addColumn("Zip"); }
			if (param.equals("country")) { rep.addColumn("Country"); }
			if (param.equals("notes")) { rep.addColumn("Notes"); }
		
		}
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
			
					if (param.equals("id")) { thisRow.addCell(thisContact.getId()); }
					if (param.equals("type")) { thisRow.addCell(thisContact.getTypeName()); }
					if (param.equals("nameLast")) { thisRow.addCell(thisContact.getNameLast()); }
					if (param.equals("nameFirst")) { thisRow.addCell(thisContact.getNameFirst()); }
					if (param.equals("nameMiddle")) { thisRow.addCell(thisContact.getNameMiddle()); }
					if (param.equals("company")) { thisRow.addCell(thisContact.getCompany()); }
					if (param.equals("title")) { thisRow.addCell(thisContact.getTitle()); }
					if (param.equals("department")) { thisRow.addCell(thisContact.getDepartmentName()); }
					if (param.equals("entered")) { thisRow.addCell(thisContact.getEnteredString()); }
					if (param.equals("enteredBy")) { thisRow.addCell(thisContact.getEnteredByName()); }
					if (param.equals("modified")) { thisRow.addCell(thisContact.getModifiedString()); }
					if (param.equals("modifiedBy")) { thisRow.addCell(thisContact.getModifiedByName()); }
					if (param.equals("owner")) { thisRow.addCell(thisContact.getOwnerName()); }
					if (param.equals("businessEmail")) { thisRow.addCell(thisContact.getEmailAddress("Business")); }
					if (param.equals("businessPhone")) { thisRow.addCell(thisContact.getPhoneNumber("Business")); }
					if (param.equals("businessAddress")) { 
						thisRow.addCell(thisContact.getAddress("Business").getStreetAddressLine1());
						thisRow.addCell(thisContact.getAddress("Business").getStreetAddressLine2()); 
					}
					if (param.equals("city")) { thisRow.addCell(thisContact.getAddress("Business").getCity()); }
					if (param.equals("state")) { thisRow.addCell(thisContact.getAddress("Business").getState()); }
					if (param.equals("zip")) { thisRow.addCell(thisContact.getAddress("Business").getZip()); }
					if (param.equals("country")) { thisRow.addCell(thisContact.getAddress("Business").getCountry()); }
					if (param.equals("notes")) { thisRow.addCell(thisContact.getNotes()); }
				}
				
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
		thisItem.setClientFilename("contactreport-" + filenameToUse + ".csv");
		thisItem.setFilename(filenameToUse);
		thisItem.setSize(fileSize);
		thisItem.insert(db);
		
		return true;
	}
  
  public int save() throws Exception {
    this.generateFilename();
		File f = new File(filePath);
		f.mkdirs();
		
		rep.saveHtml(filePath + filenameToUse + ".html");
		rep.saveDelimited(filePath + filenameToUse + ".csv");
    
    File fileLink = new File(filePath + filenameToUse + ".csv");
    return ((int)fileLink.length());
  }
  
  public String generateFilename() throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
		filenameToUse = formatter.format(new java.util.Date());
    return filenameToUse;
  }
}
