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
	
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
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
		} else {
			criteria = new ArrayList();
		}
	
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
		rep.setHeader(header + ": " + subject);
	}
	
	public void buildReportHeaders() {
		Iterator y = criteria.iterator();
		while (y.hasNext()) {
			String param = (String) y.next();

			if (param.equals("id")) { rep.addColumn("Account ID"); }
			if (param.equals("accountName")) { rep.addColumn("Account Name"); }
			if (param.equals("accountNumber")) { rep.addColumn("Account No."); }
			if (param.equals("url")) { rep.addColumn("URL"); }
			if (param.equals("ticker")) { rep.addColumn("Ticker"); }
			if (param.equals("employees")) { rep.addColumn("Employees"); }
			if (param.equals("entered")) { rep.addColumn("Entered"); }
			if (param.equals("enteredBy")) { rep.addColumn("Entered By"); }
			if (param.equals("modified")) { rep.addColumn("Modified"); }
			if (param.equals("modifiedBy")) { rep.addColumn("Modified By"); }
			if (param.equals("owner")) { rep.addColumn("Owner"); }
			if (param.equals("contractEndDate")) { rep.addColumn("Contract End Date"); }
			if (param.equals("notes")) { rep.addColumn("Notes"); }
		}
		
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
		Iterator y = criteria.iterator();
		while (y.hasNext()) {
			String param = (String) y.next();
			if (param.equals("id")) { passedReport.addColumn("Account ID"); }
			if (param.equals("accountName")) { passedReport.addColumn("Account Name"); }
			if (param.equals("accountNumber")) { passedReport.addColumn("Account No."); }
			if (param.equals("url")) { passedReport.addColumn("URL"); }
			if (param.equals("ticker")) { passedReport.addColumn("Ticker"); }
			if (param.equals("employees")) { passedReport.addColumn("Employees"); }
			if (param.equals("entered")) { passedReport.addColumn("Entered"); }
			if (param.equals("enteredBy")) { passedReport.addColumn("Entered By"); }
			if (param.equals("modified")) { passedReport.addColumn("Modified"); }
			if (param.equals("modifiedBy")) { passedReport.addColumn("Modified By"); }
			if (param.equals("owner")) { passedReport.addColumn("Owner"); }
			if (param.equals("contractEndDate")) { passedReport.addColumn("Contract End Date"); }
			if (param.equals("notes")) { passedReport.addColumn("Notes"); }
		}
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
				
				Iterator y = criteria.iterator();
				while (y.hasNext()) {
					String param = (String) y.next();
				
					if (param.equals("id")) { thisRow.addCell(thisOrg.getOrgId());	}
					if (param.equals("accountName")) { thisRow.addCell(thisOrg.getName());	}
					if (param.equals("accountNumber")) { thisRow.addCell(thisOrg.getAccountNumber()); }
					if (param.equals("url")) {	thisRow.addCell(thisOrg.getUrl()); }
					if (param.equals("ticker")) {	thisRow.addCell(thisOrg.getTicker()); }
					if (param.equals("employees")) {	thisRow.addCell(thisOrg.getEmployees());}
					if (param.equals("entered")) {	thisRow.addCell(thisOrg.getEnteredString());}
					if (param.equals("enteredBy")) {	thisRow.addCell(thisOrg.getEnteredByName());}
					if (param.equals("modified")) {	thisRow.addCell(thisOrg.getModifiedString());}
					if (param.equals("modifiedBy")) {	thisRow.addCell(thisOrg.getModifiedByName());}
					if (param.equals("owner")) { thisRow.addCell(thisOrg.getOwnerName());}
					if (param.equals("contractEndDate")) { thisRow.addCell(thisOrg.getContractEndDateString());}
					if (param.equals("notes")) { thisRow.addCell(thisOrg.getNotes());}
				}
				
				rep.addRow(thisRow);
			}
		}
	}
	
	public void addDataRow(ReportRow thisRow, Organization thisOrg) throws SQLException {
			Iterator y = criteria.iterator();
			while (y.hasNext()) {
				String param = (String) y.next();
			
				if (param.equals("id")) { thisRow.addCell(thisOrg.getOrgId());	}
				if (param.equals("accountName")) { thisRow.addCell(thisOrg.getName());	}
				if (param.equals("accountNumber")) { thisRow.addCell(thisOrg.getAccountNumber()); }
				if (param.equals("url")) {	thisRow.addCell(thisOrg.getUrl()); }
				if (param.equals("ticker")) {	thisRow.addCell(thisOrg.getTicker()); }
				if (param.equals("employees")) {	thisRow.addCell(thisOrg.getEmployees());}
				if (param.equals("entered")) {	thisRow.addCell(thisOrg.getEnteredString());}
				if (param.equals("enteredBy")) {	thisRow.addCell(thisOrg.getEnteredByName());}
				if (param.equals("modified")) {	thisRow.addCell(thisOrg.getModifiedString());}
				if (param.equals("modifiedBy")) {	thisRow.addCell(thisOrg.getModifiedByName());}
				if (param.equals("owner")) { thisRow.addCell(thisOrg.getOwnerName());}
				if (param.equals("contractEndDate")) { thisRow.addCell(thisOrg.getContractEndDateString());}
				if (param.equals("notes")) { thisRow.addCell(thisOrg.getNotes());}
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
