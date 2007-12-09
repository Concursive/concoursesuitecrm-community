/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.pipeline.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationReport;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Report;
import org.aspcfs.modules.base.ReportRow;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Takes a list of opportunities and formats them for output using the
 * specified parameters.
 *
 * @author chris
 * @version $Id: OpportunityReport.java,v 1.12 2003/03/07 14:41:52 mrajkowski
 *          Exp $
 * @created March 7, 2002
 */
public class OpportunityReport extends OpportunityList {
  protected Report rep = new Report();
  //default delimiter is comma
  protected String delimiter = ",";
  protected String header = null;
  protected String tdFormat = "";
  protected String filePath = "";
  protected String filenameToUse = "";
  protected FileItem thisItem = new FileItem();
  protected int limitId = -1;

  protected String subject = "";
  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected ArrayList criteria = null;
  protected String[] params = new String[]{"id", "type", "description", "organization", "contact", "owner", "amount1", "amount2", "amount3", "stageName", "stageDate", "probability", "revenueStart", "terms", "alertDate", "commission", "entered", "enteredBy", "modified", "modifiedBy"};

  protected OrganizationReport orgReportJoin = new OrganizationReport();
  protected boolean joinOrgs = false;


  /**
   * Constructor for the OpportunityReport object
   */
  public OpportunityReport() {
  }


  /**
   * Sets the rep attribute of the OpportunityReport object
   *
   * @param tmp The new rep value
   */
  public void setRep(Report tmp) {
    this.rep = tmp;
  }


  /**
   * Sets the delimiter attribute of the OpportunityReport object
   *
   * @param tmp The new delimiter value
   */
  public void setDelimiter(String tmp) {
    this.delimiter = tmp;
  }


  /**
   * Sets the header attribute of the OpportunityReport object
   *
   * @param tmp The new header value
   */
  public void setHeader(String tmp) {
    this.header = tmp;
  }


  /**
   * Sets the tdFormat attribute of the OpportunityReport object
   *
   * @param tmp The new tdFormat value
   */
  public void setTdFormat(String tmp) {
    this.tdFormat = tmp;
  }


  /**
   * Gets the rep attribute of the OpportunityReport object
   *
   * @return The rep value
   */
  public Report getRep() {
    return rep;
  }


  /**
   * Gets the delimiter attribute of the OpportunityReport object
   *
   * @return The delimiter value
   */
  public String getDelimiter() {
    return delimiter;
  }


  /**
   * Gets the header attribute of the OpportunityReport object
   *
   * @return The header value
   */
  public String getHeader() {
    return header;
  }


  /**
   * Gets the tdFormat attribute of the OpportunityReport object
   *
   * @return The tdFormat value
   */
  public String getTdFormat() {
    return tdFormat;
  }


  /**
   * Gets the filePath attribute of the OpportunityReport object
   *
   * @return The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   * Gets the thisItem attribute of the OpportunityReport object
   *
   * @return The thisItem value
   */
  public FileItem getThisItem() {
    return thisItem;
  }


  /**
   * Sets the filePath attribute of the OpportunityReport object
   *
   * @param tmp The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   * Sets the thisItem attribute of the OpportunityReport object
   *
   * @param tmp The new thisItem value
   */
  public void setThisItem(FileItem tmp) {
    this.thisItem = tmp;
  }


  /**
   * Gets the subject attribute of the OpportunityReport object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the enteredBy attribute of the OpportunityReport object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the OpportunityReport object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the subject attribute of the OpportunityReport object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Sets the enteredBy attribute of the OpportunityReport object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the OpportunityReport object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Gets the orgReportJoin attribute of the OpportunityReport object
   *
   * @return The orgReportJoin value
   */
  public OrganizationReport getOrgReportJoin() {
    return orgReportJoin;
  }


  /**
   * Gets the joinOrgs attribute of the OpportunityReport object
   *
   * @return The joinOrgs value
   */
  public boolean getJoinOrgs() {
    return joinOrgs;
  }


  /**
   * Sets the orgReportJoin attribute of the OpportunityReport object
   *
   * @param tmp The new orgReportJoin value
   */
  public void setOrgReportJoin(OrganizationReport tmp) {
    this.orgReportJoin = tmp;
  }


  /**
   * Sets the joinOrgs attribute of the OpportunityReport object
   *
   * @param tmp The new joinOrgs value
   */
  public void setJoinOrgs(boolean tmp) {
    this.joinOrgs = tmp;
  }


  /**
   * Gets the limitId attribute of the OpportunityReport object
   *
   * @return The limitId value
   */
  public int getLimitId() {
    return limitId;
  }


  /**
   * Sets the limitId attribute of the OpportunityReport object
   *
   * @param limitId The new limitId value
   */
  public void setLimitId(int limitId) {
    this.limitId = limitId;
  }


  /**
   * Gets the criteria attribute of the OpportunityReport object
   *
   * @return The criteria value
   */
  public ArrayList getCriteria() {
    return criteria;
  }


  /**
   * Sets the criteria attribute of the OpportunityReport object
   *
   * @param criteriaString The new criteria value
   */
  public void setCriteria(String[] criteriaString) {
    if (criteriaString != null) {
      params = criteriaString;
    }

    this.criteria = new ArrayList(Arrays.asList(params));
  }


  /**
   * Gets the params attribute of the OpportunityReport object
   *
   * @return The params value
   */
  public String[] getParams() {
    return params;
  }


  /**
   * Sets the params attribute of the OpportunityReport object
   *
   * @param params The new params value
   */
  public void setParams(String[] params) {
    this.params = params;
  }


  /**
   * Gets the filenameToUse attribute of the OpportunityReport object
   *
   * @return The filenameToUse value
   */
  public String getFilenameToUse() {
    return filenameToUse;
  }


  /**
   * Sets the filenameToUse attribute of the OpportunityReport object
   *
   * @param filenameToUse The new filenameToUse value
   */
  public void setFilenameToUse(String filenameToUse) {
    this.filenameToUse = filenameToUse;
  }


  /**
   * Description of the Method
   */
  public void buildReportBaseInfo() {
    rep.setDelimitedCharacter(delimiter);
    if (header != null) {
      rep.setHeader(header + ": " + subject);
    }
  }


  /**
   * Description of the Method
   */
  public void buildReportHeaders() {
    if (joinOrgs) {
      orgReportJoin.buildReportHeaders(rep);
    }

    Iterator y = criteria.iterator();
    while (y.hasNext()) {
      String param = (String) y.next();

      if (param.equals("id")) {
        rep.addColumn("Opportunity ID");
      }
      if (param.equals("type")) {
        rep.addColumn("Type(s)");
      }
      if (param.equals("description")) {
        rep.addColumn("Description");
      }
      if (param.equals("organization")) {
        rep.addColumn("Organization");
      }
      if (param.equals("contact")) {
        rep.addColumn("Contact");
      }
      if (param.equals("owner")) {
        rep.addColumn("Owner");
      }
      if (param.equals("amount1")) {
        rep.addColumn("Low Amount");
      }
      if (param.equals("amount2")) {
        rep.addColumn("Best Guess Amount");
      }
      if (param.equals("amount3")) {
        rep.addColumn("High Amount");
      }
      if (param.equals("stageName")) {
        rep.addColumn("Stage");
      }
      if (param.equals("stageDate")) {
        rep.addColumn("Stage Date");
      }
      if (param.equals("probability")) {
        rep.addColumn("Prob. of Close");
      }
      if (param.equals("revenueStart")) {
        rep.addColumn("Revenue Start");
      }
      if (param.equals("terms")) {
        rep.addColumn("Terms");
      }
      if (param.equals("alertDate")) {
        rep.addColumn("Alert Date");
      }
      if (param.equals("commission")) {
        rep.addColumn("Commission");
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
    }
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildReportData(Connection db, ActionContext context) throws SQLException {
    this.buildList(db);
    boolean writeOut = false;
    Organization tempOrg = null;
    Iterator x = this.iterator();
    while (x.hasNext()) {
      OpportunityBean oppBean = (OpportunityBean) x.next();
      ReportRow thisRow = new ReportRow();
      if (joinOrgs && oppBean.getHeader().getAccountLink() > -1) {
        tempOrg = new Organization(db, oppBean.getHeader().getAccountLink());
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
          if (param.equals("id")) {
            thisRow.addCell(oppBean.getComponent().getId());
          }
          if (param.equals("type")) {
            thisRow.addCell(
                oppBean.getComponent().getTypes().valuesAsString());
          }
          if (param.equals("description")) {
            thisRow.addCell(oppBean.getComponent().getDescription());
          }
          if (param.equals("organization")) {
            if (oppBean.getHeader().getContactLink() > -1) {
              thisRow.addCell(oppBean.getHeader().getContactCompanyName());
            } else {
              thisRow.addCell(oppBean.getHeader().getAccountName());
            }
          }
          if (param.equals("contact")) {
            thisRow.addCell(oppBean.getHeader().getContactName());
          }
          if (param.equals("owner")) {
            thisRow.addCell(
                UserList.retrieveUserContact(
                    context, oppBean.getComponent().getOwner()).getNameLastFirst());
          }
          if (param.equals("amount1")) {
            thisRow.addCell(String.valueOf(oppBean.getComponent().getLow()));
          }
          if (param.equals("amount2")) {
            thisRow.addCell(String.valueOf(oppBean.getComponent().getGuess()));
          }
          if (param.equals("amount3")) {
            thisRow.addCell(String.valueOf(oppBean.getComponent().getHigh()));
          }
          if (param.equals("stageName")) {
            thisRow.addCell(oppBean.getComponent().getStageName());
          }
          if (param.equals("stageDate")) {
            thisRow.addCell(oppBean.getComponent().getStageDateString());
          }
          if (param.equals("probability")) {
            thisRow.addCell(
                String.valueOf(oppBean.getComponent().getCloseProb()));
          }
          if (param.equals("revenueStart")) {
            thisRow.addCell(oppBean.getComponent().getCloseDateString());
          }
          if (param.equals("terms")) {
            thisRow.addCell(String.valueOf(oppBean.getComponent().getTerms()));
          }
          if (param.equals("alertDate")) {
            thisRow.addCell(oppBean.getComponent().getAlertDateString());
          }
          if (param.equals("commission")) {
            thisRow.addCell(
                String.valueOf(oppBean.getComponent().getCommission()));
          }
          if (param.equals("entered")) {
            thisRow.addCell(oppBean.getComponent().getEnteredString());
          }
          if (param.equals("enteredBy")) {
            thisRow.addCell(
                UserList.retrieveUserContact(
                    context, oppBean.getComponent().getEnteredBy()).getNameLastFirst());
          }
          if (param.equals("modified")) {
            thisRow.addCell(oppBean.getComponent().getModifiedString());
          }
          if (param.equals("modifiedBy")) {
            thisRow.addCell(
                UserList.retrieveUserContact(
                    context, oppBean.getComponent().getModifiedBy()).getNameLastFirst());
          }
        }
        rep.addRow(thisRow);
      }
      writeOut = false;
    }
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildReportFull(Connection db, ActionContext context) throws SQLException {
    buildReportBaseInfo();
    buildReportHeaders();
    buildReportData(db, context);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
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
      thisItem.setLinkModuleId(Constants.DOCUMENTS_LEADS_REPORTS);
    }
    thisItem.setLinkItemId(0);
    thisItem.setEnteredBy(enteredBy);
    thisItem.setModifiedBy(modifiedBy);
    thisItem.setSubject(subject);
    thisItem.setClientFilename("opportunityreport-" + filenameToUse + ".csv");
    thisItem.setFilename(filenameToUse);
    thisItem.setSize((int) fileLink.length());
    thisItem.insert(db);
    return true;
  }
}

