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
package org.aspcfs.modules.accounts.base;

import com.zeroio.iteam.base.FileItem;
import org.aspcfs.modules.base.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author chris
 * @version $Id: OrganizationReport.java,v 1.8 2002/12/20 14:07:55 mrajkowski
 *          Exp $
 * @created March 8, 2002
 */
public class OrganizationReport extends OrganizationList {
  protected Report rep = new Report();
  //default delimiter is comma
  protected String delimiter = ",";
  protected String header = null;
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


  /**
   * Constructor for the OrganizationReport object
   */
  public OrganizationReport() {
  }


  /**
   * Sets the rep attribute of the OrganizationReport object
   *
   * @param tmp The new rep value
   */
  public void setRep(Report tmp) {
    this.rep = tmp;
  }


  /**
   * Sets the delimiter attribute of the OrganizationReport object
   *
   * @param tmp The new delimiter value
   */
  public void setDelimiter(String tmp) {
    this.delimiter = tmp;
  }


  /**
   * Sets the header attribute of the OrganizationReport object
   *
   * @param tmp The new header value
   */
  public void setHeader(String tmp) {
    this.header = tmp;
  }


  /**
   * Sets the tdFormat attribute of the OrganizationReport object
   *
   * @param tmp The new tdFormat value
   */
  public void setTdFormat(String tmp) {
    this.tdFormat = tmp;
  }


  /**
   * Gets the rep attribute of the OrganizationReport object
   *
   * @return The rep value
   */
  public Report getRep() {
    return rep;
  }


  /**
   * Gets the delimiter attribute of the OrganizationReport object
   *
   * @return The delimiter value
   */
  public String getDelimiter() {
    return delimiter;
  }


  /**
   * Gets the header attribute of the OrganizationReport object
   *
   * @return The header value
   */
  public String getHeader() {
    return header;
  }


  /**
   * Gets the tdFormat attribute of the OrganizationReport object
   *
   * @return The tdFormat value
   */
  public String getTdFormat() {
    return tdFormat;
  }


  /**
   * Gets the filePath attribute of the OrganizationReport object
   *
   * @return The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   * Gets the thisItem attribute of the OrganizationReport object
   *
   * @return The thisItem value
   */
  public FileItem getThisItem() {
    return thisItem;
  }


  /**
   * Sets the filePath attribute of the OrganizationReport object
   *
   * @param tmp The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   * Sets the thisItem attribute of the OrganizationReport object
   *
   * @param tmp The new thisItem value
   */
  public void setThisItem(FileItem tmp) {
    this.thisItem = tmp;
  }


  /**
   * Gets the subject attribute of the OrganizationReport object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the enteredBy attribute of the OrganizationReport object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the OrganizationReport object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Sets the subject attribute of the OrganizationReport object
   *
   * @param tmp The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   * Sets the enteredBy attribute of the OrganizationReport object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the OrganizationReport object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Gets the folderId attribute of the OrganizationReport object
   *
   * @return The folderId value
   */
  public int getFolderId() {
    return folderId;
  }


  /**
   * Sets the folderId attribute of the OrganizationReport object
   *
   * @param folderId The new folderId value
   */
  public void setFolderId(int folderId) {
    this.folderId = folderId;
  }


  /**
   * Sets the includeFolders attribute of the OrganizationReport object
   *
   * @param includeFolders The new includeFolders value
   */
  public void setIncludeFolders(boolean includeFolders) {
    this.includeFolders = includeFolders;
  }


  /**
   * Gets the includeFolders attribute of the OrganizationReport object
   *
   * @return The includeFolders value
   */
  public boolean getIncludeFolders() {
    return includeFolders;
  }


  /**
   * Gets the criteria attribute of the OrganizationReport object
   *
   * @return The criteria value
   */
  public ArrayList getCriteria() {
    return criteria;
  }


  /**
   * Sets the criteria attribute of the OrganizationReport object
   *
   * @param criteriaString The new criteria value
   */
  public void setCriteria(String[] criteriaString) {
    if (criteriaString != null) {
      params = criteriaString;
      criteria = new ArrayList(Arrays.asList(params));
    } else {
      criteria = new ArrayList();
    }
  }


  /**
   * Gets the params attribute of the OrganizationReport object
   *
   * @return The params value
   */
  public String[] getParams() {
    return params;
  }


  /**
   * Sets the params attribute of the OrganizationReport object
   *
   * @param params The new params value
   */
  public void setParams(String[] params) {
    this.params = params;
  }


  /**
   * Gets the filenameToUse attribute of the OrganizationReport object
   *
   * @return The filenameToUse value
   */
  public String getFilenameToUse() {
    return filenameToUse;
  }


  /**
   * Sets the filenameToUse attribute of the OrganizationReport object
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
    Iterator y = criteria.iterator();
    while (y.hasNext()) {
      String param = (String) y.next();

      if (param.equals("id")) {
        rep.addColumn("Account ID");
      }
      if (param.equals("accountName")) {
        rep.addColumn("Account Name");
      }
      if (param.equals("accountNumber")) {
        rep.addColumn("Account No.");
      }
      if (param.equals("url")) {
        rep.addColumn("URL");
      }
      if (param.equals("ticker")) {
        rep.addColumn("Ticker");
      }
      if (param.equals("employees")) {
        rep.addColumn("Employees");
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
      if (param.equals("owner")) {
        rep.addColumn("Owner");
      }
      if (param.equals("contractEndDate")) {
        rep.addColumn("Contract End Date");
      }
      if (param.equals("notes")) {
        rep.addColumn("Notes");
      }
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
  }


  /**
   * Description of the Method
   *
   * @param passedReport Description of the Parameter
   */
  public void buildReportHeaders(Report passedReport) {
    Iterator y = criteria.iterator();
    while (y.hasNext()) {
      String param = (String) y.next();
      if (param.equals("id")) {
        passedReport.addColumn("Account ID");
      }
      if (param.equals("accountName")) {
        passedReport.addColumn("Account Name");
      }
      if (param.equals("accountNumber")) {
        passedReport.addColumn("Account No.");
      }
      if (param.equals("url")) {
        passedReport.addColumn("URL");
      }
      if (param.equals("ticker")) {
        passedReport.addColumn("Ticker");
      }
      if (param.equals("employees")) {
        passedReport.addColumn("Employees");
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
      if (param.equals("owner")) {
        passedReport.addColumn("Owner");
      }
      if (param.equals("contractEndDate")) {
        passedReport.addColumn("Contract End Date");
      }
      if (param.equals("notes")) {
        passedReport.addColumn("Notes");
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
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

      while (grp.hasNext()) {
        thisGroup = (CustomFieldGroup) grp.next();
        thisGroup.buildResources(db);

        Iterator fields = thisGroup.iterator();
        if (fields.hasNext()) {
          while (fields.hasNext()) {
            CustomField thisField = (CustomField) fields.next();
            rep.addColumn(thisField.getNameHtml());
          }
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
              thisGroup = (CustomFieldGroup) grp.next();
              thisGroup.buildResources(db);

              Iterator fields = thisGroup.iterator();
              if (fields.hasNext()) {
                while (fields.hasNext()) {

                  ReportRow thisRow = new ReportRow();

                  CustomField thisField = (CustomField) fields.next();

                  thisField.setRecordId(thisRec.getId());
                  thisField.buildResources(db);

                  addDataRow(thisRow, thisOrg);

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
          addDataRow(thisRow, thisOrg);

          CustomFieldRecord thisRec = (CustomFieldRecord) rec.next();
          Iterator grp = thisCat.iterator();
          while (grp.hasNext()) {
            thisGroup = (CustomFieldGroup) grp.next();
            thisGroup.buildResources(db);

            Iterator fields = thisGroup.iterator();
            if (fields.hasNext()) {
              while (fields.hasNext()) {

                CustomField thisField = (CustomField) fields.next();
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
        addDataRow(thisRow, thisOrg);
        rep.addRow(thisRow);
      }
    }
  }


  /**
   * Adds a feature to the DataRow attribute of the OrganizationReport object
   *
   * @param thisRow The feature to be added to the DataRow attribute
   * @param thisOrg The feature to be added to the DataRow attribute
   * @throws SQLException Description of the Exception
   */
  public void addDataRow(ReportRow thisRow, Organization thisOrg) throws SQLException {
    Iterator y = criteria.iterator();
    while (y.hasNext()) {
      String param = (String) y.next();

      if (param.equals("id")) {
        thisRow.addCell(thisOrg.getOrgId());
      }
      if (param.equals("accountName")) {
        thisRow.addCell(thisOrg.getName());
      }
      if (param.equals("accountNumber")) {
        thisRow.addCell(thisOrg.getAccountNumber());
      }
      if (param.equals("url")) {
        thisRow.addCell(thisOrg.getUrl());
      }
      if (param.equals("ticker")) {
        thisRow.addCell(thisOrg.getTicker());
      }
      if (param.equals("employees")) {
        thisRow.addCell(thisOrg.getEmployees());
      }
      if (param.equals("entered")) {
        thisRow.addCell(thisOrg.getEnteredString());
      }
      if (param.equals("enteredBy")) {
        thisRow.addCell(thisOrg.getEnteredByName());
      }
      if (param.equals("modified")) {
        thisRow.addCell(thisOrg.getModifiedString());
      }
      if (param.equals("modifiedBy")) {
        thisRow.addCell(thisOrg.getModifiedByName());
      }
      if (param.equals("owner")) {
        thisRow.addCell(thisOrg.getOwnerName());
      }
      if (param.equals("contractEndDate")) {
        thisRow.addCell(thisOrg.getContractEndDateString());
      }
      if (param.equals("notes")) {
        thisRow.addCell(thisOrg.getNotes());
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildReportFull(Connection db) throws SQLException {
    buildReportBaseInfo();
    buildReportHeaders();
    buildReportData(db);
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

    thisItem.setLinkModuleId(Constants.DOCUMENTS_ACCOUNTS_REPORTS);
    thisItem.setLinkItemId(0);
    thisItem.setEnteredBy(enteredBy);
    thisItem.setModifiedBy(modifiedBy);
    thisItem.setSubject(subject);
    thisItem.setClientFilename("accountreport-" + filenameToUse + ".csv");
    thisItem.setFilename(filenameToUse);
    thisItem.setSize((int) fileLink.length());
    thisItem.insert(db);

    return true;
  }
}

