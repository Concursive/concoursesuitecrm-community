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

public class OpportunityReport extends OpportunityList {
	protected Report rep = new Report();
	//default delimiter is comma
	protected String delimiter = ",";
	protected String header = "CFS Pipeline Management";
	protected String tdFormat = "";
	protected String filePath = "";
	protected String filenameToUse = "";
	protected FileItem thisItem = new FileItem();
	protected int limitId = -1;
	
	protected String subject = "";
	protected int enteredBy = -1;
	protected int modifiedBy = -1;
	protected ArrayList criteria = null;
	protected String[] params = new String[] {"id", "description", "contact", "owner", "amount1", "amount2", "amount3", "stageName", "stageDate", "probability", "revenueStart", "terms", "alertDate", "commission", "entered", "enteredBy", "modified", "modifiedBy"};

	
	protected OrganizationReport orgReportJoin = new OrganizationReport();
	protected boolean joinOrgs = false;
	
	public OpportunityReport() { }

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
		rep.setHeader(header + ": " + subject);
	}
	
	public void buildReportHeaders() {
		if (joinOrgs) { orgReportJoin.buildReportHeaders(rep); }
		
		Iterator y = criteria.iterator();
		while (y.hasNext()) {
			String param = (String) y.next();
			
			if ( param.equals("id") ) { rep.addColumn("Opportunity ID"); }
			if ( param.equals("description")) { rep.addColumn("Description"); }
			if ( param.equals("contact") )  { rep.addColumn("Contact/Organization"); }
			if ( param.equals("owner") )   { rep.addColumn("Owner"); }
			if ( param.equals("amount1") )  { rep.addColumn("Low Amount"); }
			if ( param.equals("amount2") )  { rep.addColumn("Best Guess Amount"); }
			if ( param.equals("amount3") )  { rep.addColumn("High Amount"); }
			if ( param.equals("stageName"))  { rep.addColumn("Stage"); }
			if ( param.equals("stageDate"))  { rep.addColumn("Stage Date"); }
			if ( param.equals("probability")) { rep.addColumn("Prob. of Close"); }
			if ( param.equals("revenueStart")) { rep.addColumn("Revenue Start"); }
			if ( param.equals("terms"))  { rep.addColumn("Terms"); }
			if ( param.equals("alertDate")) { rep.addColumn("Alert Date"); }
			if ( param.equals("commission")) { rep.addColumn("Commission"); }
			if ( param.equals("entered"))  { rep.addColumn("Entered"); }
			if ( param.equals("enteredBy")) { rep.addColumn("Entered By"); }
			if ( param.equals("modified"))  { rep.addColumn("Modified"); }
			if ( param.equals("modifiedBy")) { rep.addColumn("Modified By"); }
		}
	}
	
	public void buildReportData(Connection db) throws SQLException {
		this.buildList(db);
		
		boolean writeOut = false;
		Organization tempOrg = null;
		
		Iterator x = this.iterator();
		while (x.hasNext()) {
			Opportunity thisOpp = (Opportunity) x.next();
			ReportRow thisRow = new ReportRow();
			
			if (joinOrgs && thisOpp.getAccountLink() > -1) { 
				tempOrg = new Organization(db, thisOpp.getAccountLink());
				
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
					
					if ( param.equals("id") ) {  thisRow.addCell(thisOpp.getId());	}
					if ( param.equals("description")) { thisRow.addCell(thisOpp.getDescription());	}
					if ( param.equals("contact") )  { thisRow.addCell(thisOpp.getAccountName()); }
					if ( param.equals("owner") )   {	thisRow.addCell(thisOpp.getOwnerName()); }
					if ( param.equals("amount1") )  {	thisRow.addCell("$" + thisOpp.getLowCurrency()); }
					if ( param.equals("amount2") )  {	thisRow.addCell("$" + thisOpp.getGuessCurrency()); }
					if ( param.equals("amount3") )  {	thisRow.addCell("$" + thisOpp.getHighCurrency()); }
					if ( param.equals("stageName"))  {	thisRow.addCell(thisOpp.getStageName());}
					if ( param.equals("stageDate"))  {	thisRow.addCell(thisOpp.getStageDateString());}
					if ( param.equals("probability")) {	thisRow.addCell(thisOpp.getCloseProbValue());}
					if ( param.equals("revenueStart")) {	thisRow.addCell(thisOpp.getCloseDateString());}
					if ( param.equals("terms"))  {	thisRow.addCell(thisOpp.getTermsString());}
					if ( param.equals("alertDate")) { thisRow.addCell(thisOpp.getAlertDateString()); }
					if ( param.equals("commission")) { thisRow.addCell(thisOpp.getCommissionPercent()); }
					if ( param.equals("entered"))  { thisRow.addCell(thisOpp.getEnteredString()); }
					if ( param.equals("enteredBy")) { thisRow.addCell(thisOpp.getEnteredByName()); }
					if ( param.equals("modified"))  { thisRow.addCell(thisOpp.getModifiedString()); }
					if ( param.equals("modifiedBy")) { thisRow.addCell(thisOpp.getModifiedByName()); }
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
			thisItem.setLinkModuleId(Constants.LEADS_REPORTS);
		}
		
		thisItem.setLinkItemId(0);
		thisItem.setProjectId(-1);
		thisItem.setEnteredBy(enteredBy);
		thisItem.setModifiedBy(modifiedBy);
		thisItem.setSubject(subject);
		thisItem.setClientFilename("opportunityreport-" + filenameToUse + ".csv");
		thisItem.setFilename(filenameToUse);
		thisItem.setSize((int)fileLink.length());
		thisItem.insert(db);
		
		return true;
	}
}
