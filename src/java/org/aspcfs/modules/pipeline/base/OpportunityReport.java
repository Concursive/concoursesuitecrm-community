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
	String[] params = null;
	
	protected boolean displayId = true;
	protected boolean displayDescription = true;
	protected boolean displayContact = true;
	protected boolean displayOwner = true;
	protected boolean displayAmount1 = true;
	protected boolean displayAmount2 = true;
	protected boolean displayAmount3 = true;
	protected boolean displayStageName = true;
	protected boolean displayStageDate = true;
	protected boolean displayProbability = true;
	protected boolean displayRevenueStart = true;
	protected boolean displayTerms = true;
	protected boolean displayAlertDate = true;
	protected boolean displayCommission = true;
	protected boolean displayEntered = true;
	protected boolean displayEnteredBy = true;
	protected boolean displayModified = true;
	protected boolean displayModifiedBy = true;
	
	protected OrganizationReport orgReportJoin = new OrganizationReport();
	protected boolean joinOrgs = false;
	
	public OpportunityReport() { }
	
	public boolean getDisplayAmount1() { return displayAmount1; }
	public boolean getDisplayAmount2() { return displayAmount2; }
	public boolean getDisplayAmount3() { return displayAmount3; }
	public void setDisplayAmount1(boolean tmp) { this.displayAmount1 = tmp; }
	public void setDisplayAmount2(boolean tmp) { this.displayAmount2 = tmp; }
	public void setDisplayAmount3(boolean tmp) { this.displayAmount3 = tmp; }

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
	public void setDisplayDescription(boolean tmp) { this.displayDescription = tmp; }
	public void setDisplayContact(boolean tmp) { this.displayContact = tmp; }
	public void setDisplayOwner(boolean tmp) { this.displayOwner = tmp; }
	public void setDisplayStageName(boolean tmp) { this.displayStageName = tmp; }
	public void setDisplayStageDate(boolean tmp) { this.displayStageDate = tmp; }
	public void setDisplayProbability(boolean tmp) { this.displayProbability = tmp; }
	public void setDisplayRevenueStart(boolean tmp) { this.displayRevenueStart = tmp; }
	public void setDisplayTerms(boolean tmp) { this.displayTerms = tmp; }
	public void setDisplayAlertDate(boolean tmp) { this.displayAlertDate = tmp; }
	public void setDisplayCommission(boolean tmp) { this.displayCommission = tmp; }
	public void setDisplayEnteredBy(boolean tmp) { this.displayEnteredBy = tmp; }
	public void setDisplayModified(boolean tmp) { this.displayModified = tmp; }
	public void setDisplayModifiedBy(boolean tmp) { this.displayModifiedBy = tmp; }
	public boolean getDisplayDescription() { return displayDescription; }
	public boolean getDisplayContact() { return displayContact; }
	public boolean getDisplayOwner() { return displayOwner; }
	public boolean getDisplayStageName() { return displayStageName; }
	public boolean getDisplayStageDate() { return displayStageDate; }
	public boolean getDisplayProbability() { return displayProbability; }
	public boolean getDisplayRevenueStart() { return displayRevenueStart; }
	public boolean getDisplayTerms() { return displayTerms; }
	public boolean getDisplayAlertDate() { return displayAlertDate; }
	public boolean getDisplayCommission() { return displayCommission; }
	public boolean getDisplayEnteredBy() { return displayEnteredBy; }
	public boolean getDisplayModified() { return displayModified; }
	public boolean getDisplayModifiedBy() { return displayModifiedBy; }

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

	public boolean getDisplayId() {
		return displayId;
	}
	public void setDisplayId(boolean displayId) {
		this.displayId = displayId;
	}

	public boolean getDisplayEntered() {
		return displayEntered;
	}
	public void setDisplayEntered(boolean displayEntered) {
		this.displayEntered = displayEntered;
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
		if ( !(criteria.contains("description")) ) { displayDescription = false; }
		if ( !(criteria.contains("contact")) ) { displayContact = false; }
		if ( !(criteria.contains("owner")) ) { displayOwner = false; }
		if ( !(criteria.contains("amount1")) ) { displayAmount1 = false; }
		if ( !(criteria.contains("amount2")) ) { displayAmount2 = false; }
		if ( !(criteria.contains("amount3")) ) { displayAmount3 = false; }
		if ( !(criteria.contains("stageName")) ) { displayStageName = false; }
		if ( !(criteria.contains("stageDate")) ) { displayStageDate = false; }
		if ( !(criteria.contains("probability")) ) { displayProbability = false; }
		if ( !(criteria.contains("revenueStart")) ) { displayRevenueStart = false; }
		if ( !(criteria.contains("terms")) ) { displayTerms = false; }
		if ( !(criteria.contains("alertDate")) ) { displayAlertDate = false; }
		if ( !(criteria.contains("commission")) ) { displayCommission = false; }
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
		
		if (displayId) { rep.addColumn("Opportunity ID"); }
		if (displayDescription) { rep.addColumn("Description"); }
		if (displayContact) { rep.addColumn("Contact/Organization"); }
		if (displayOwner) { rep.addColumn("Owner"); }
		if (displayAmount1) { rep.addColumn("Low Amount"); }
		if (displayAmount2) { rep.addColumn("Best Guess Amount"); }
		if (displayAmount3) { rep.addColumn("High Amount"); }
		if (displayStageName) { rep.addColumn("Stage"); }
		if (displayStageDate) { rep.addColumn("Stage Date"); }
		if (displayProbability) { rep.addColumn("Prob. of Close"); }
		if (displayRevenueStart) { rep.addColumn("Revenue Start"); }
		if (displayTerms) { rep.addColumn("Terms"); }
		if (displayAlertDate) { rep.addColumn("Alert Date"); }
		if (displayCommission) { rep.addColumn("Commission"); }
		if (displayEntered) { rep.addColumn("Entered"); }
		if (displayEnteredBy) { rep.addColumn("Entered By"); }
		if (displayModified) { rep.addColumn("Modified"); }
		if (displayModifiedBy) { rep.addColumn("Modified By"); }
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
				if (displayId) { thisRow.addCell(thisOpp.getId());	}
				if (displayDescription) { thisRow.addCell(thisOpp.getDescription());	}
				if (displayContact) { thisRow.addCell(thisOpp.getAccountName()); }
				if (displayOwner) {	thisRow.addCell(thisOpp.getOwnerName()); }
				if (displayAmount1) {	thisRow.addCell("$" + thisOpp.getLowCurrency()); }
				if (displayAmount2) {	thisRow.addCell("$" + thisOpp.getGuessCurrency()); }
				if (displayAmount3) {	thisRow.addCell("$" + thisOpp.getHighCurrency()); }
				if (displayStageName) {	thisRow.addCell(thisOpp.getStageName());}
				if (displayStageDate) {	thisRow.addCell(thisOpp.getStageDateString());}
				if (displayProbability) {	thisRow.addCell(thisOpp.getCloseProbValue());}
				if (displayRevenueStart) {	thisRow.addCell(thisOpp.getCloseDateString());}
				if (displayTerms) {	thisRow.addCell(thisOpp.getTermsString());}
				if (displayAlertDate) { thisRow.addCell(thisOpp.getAlertDateString()); }
				if (displayCommission) { thisRow.addCell(thisOpp.getCommissionPercent()); }
				if (displayEntered) { thisRow.addCell(thisOpp.getEnteredString()); }
				if (displayEnteredBy) { thisRow.addCell(thisOpp.getEnteredByName()); }
				if (displayModified) { thisRow.addCell(thisOpp.getModifiedString()); }
				if (displayModifiedBy) { thisRow.addCell(thisOpp.getModifiedByName()); }
				
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
