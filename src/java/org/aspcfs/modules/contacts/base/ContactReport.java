//Copyright 2001-2002 Dark Horse Ventures

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

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    March 7, 2002
 *@version    $Id$
 */
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
  protected String[] params = new String[]{"id", "nameLast", "nameFirst", "company", "type", "nameMiddle", "title", "department", "entered", "enteredBy", "modified", "modifiedBy", "owner", "businessEmail", "businessPhone", "businessAddress", "city", "state", "zip", "country", "notes"};

  protected OrganizationReport orgReportJoin = new OrganizationReport();
  protected boolean joinOrgs = false;


  /**
   *  Constructor for the ContactReport object
   */
  public ContactReport() { }


  /**
   *  Sets the rep attribute of the ContactReport object
   *
   *@param  tmp  The new rep value
   */
  public void setRep(Report tmp) {
    this.rep = tmp;
  }


  /**
   *  Sets the delimiter attribute of the ContactReport object
   *
   *@param  tmp  The new delimiter value
   */
  public void setDelimiter(String tmp) {
    this.delimiter = tmp;
  }


  /**
   *  Sets the header attribute of the ContactReport object
   *
   *@param  tmp  The new header value
   */
  public void setHeader(String tmp) {
    this.header = tmp;
  }


  /**
   *  Sets the tdFormat attribute of the ContactReport object
   *
   *@param  tmp  The new tdFormat value
   */
  public void setTdFormat(String tmp) {
    this.tdFormat = tmp;
  }


  /**
   *  Gets the rep attribute of the ContactReport object
   *
   *@return    The rep value
   */
  public Report getRep() {
    return rep;
  }


  /**
   *  Gets the delimiter attribute of the ContactReport object
   *
   *@return    The delimiter value
   */
  public String getDelimiter() {
    return delimiter;
  }


  /**
   *  Gets the header attribute of the ContactReport object
   *
   *@return    The header value
   */
  public String getHeader() {
    return header;
  }


  /**
   *  Gets the tdFormat attribute of the ContactReport object
   *
   *@return    The tdFormat value
   */
  public String getTdFormat() {
    return tdFormat;
  }


  /**
   *  Gets the filePath attribute of the ContactReport object
   *
   *@return    The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   *  Gets the thisItem attribute of the ContactReport object
   *
   *@return    The thisItem value
   */
  public FileItem getThisItem() {
    return thisItem;
  }


  /**
   *  Sets the filePath attribute of the ContactReport object
   *
   *@param  tmp  The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   *  Sets the thisItem attribute of the ContactReport object
   *
   *@param  tmp  The new thisItem value
   */
  public void setThisItem(FileItem tmp) {
    this.thisItem = tmp;
  }


  /**
   *  Gets the subject attribute of the ContactReport object
   *
   *@return    The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   *  Gets the enteredBy attribute of the ContactReport object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the ContactReport object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Sets the subject attribute of the ContactReport object
   *
   *@param  tmp  The new subject value
   */
  public void setSubject(String tmp) {
    this.subject = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ContactReport object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ContactReport object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Gets the orgReportJoin attribute of the ContactReport object
   *
   *@return    The orgReportJoin value
   */
  public OrganizationReport getOrgReportJoin() {
    return orgReportJoin;
  }


  /**
   *  Gets the joinOrgs attribute of the ContactReport object
   *
   *@return    The joinOrgs value
   */
  public boolean getJoinOrgs() {
    return joinOrgs;
  }


  /**
   *  Sets the orgReportJoin attribute of the ContactReport object
   *
   *@param  tmp  The new orgReportJoin value
   */
  public void setOrgReportJoin(OrganizationReport tmp) {
    this.orgReportJoin = tmp;
  }


  /**
   *  Sets the joinOrgs attribute of the ContactReport object
   *
   *@param  tmp  The new joinOrgs value
   */
  public void setJoinOrgs(boolean tmp) {
    this.joinOrgs = tmp;
  }


  /**
   *  Gets the limitId attribute of the ContactReport object
   *
   *@return    The limitId value
   */
  public int getLimitId() {
    return limitId;
  }


  /**
   *  Sets the limitId attribute of the ContactReport object
   *
   *@param  limitId  The new limitId value
   */
  public void setLimitId(int limitId) {
    this.limitId = limitId;
  }


  /**
   *  Gets the criteria attribute of the ContactReport object
   *
   *@return    The criteria value
   */
  public ArrayList getCriteria() {
    return criteria;
  }


  /**
   *  Sets the criteria attribute of the ContactReport object
   *
   *@param  criteriaString  The new criteria value
   */
  public void setCriteria(String[] criteriaString) {
    if (criteriaString != null) {
      params = criteriaString;
    }

    criteria = new ArrayList(Arrays.asList(params));
    this.criteria = criteria;
  }


  /**
   *  Gets the params attribute of the ContactReport object
   *
   *@return    The params value
   */
  public String[] getParams() {
    return params;
  }


  /**
   *  Sets the params attribute of the ContactReport object
   *
   *@param  params  The new params value
   */
  public void setParams(String[] params) {
    this.params = params;
  }


  /**
   *  Gets the filenameToUse attribute of the ContactReport object
   *
   *@return    The filenameToUse value
   */
  public String getFilenameToUse() {
    return filenameToUse;
  }


  /**
   *  Sets the filenameToUse attribute of the ContactReport object
   *
   *@param  filenameToUse  The new filenameToUse value
   */
  public void setFilenameToUse(String filenameToUse) {
    this.filenameToUse = filenameToUse;
  }


  /**
   *  Description of the Method
   */
  public void buildReportBaseInfo() {
    rep.setDelimitedCharacter(delimiter);
    rep.setHeader(header + ((!header.equals("") && !subject.equals("")) ? ": " : "") + subject);
  }


  /**
   *  Description of the Method
   */
  public void buildReportHeaders() {
    if (joinOrgs) {
      orgReportJoin.buildReportHeaders(rep);
    }

    Iterator y = criteria.iterator();
    while (y.hasNext()) {
      String param = (String) y.next();

      if (param.equals("id")) {
        rep.addColumn("Contact Id");
      }
      if (param.equals("type")) {
        rep.addColumn("Type");
      }
      if (param.equals("nameLast")) {
        rep.addColumn("Last Name", "Last Name");
      }
      if (param.equals("nameFirst")) {
        rep.addColumn("First Name", "First Name");
      }
      if (param.equals("nameMiddle")) {
        rep.addColumn("Middle Name", "Middle Name");
      }
      if (param.equals("company")) {
        rep.addColumn("Company");
      }
      if (param.equals("title")) {
        rep.addColumn("Title");
      }
      if (param.equals("department")) {
        rep.addColumn("Department");
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
      if (param.equals("businessEmail")) {
        rep.addColumn("Business Email");
      }
      if (param.equals("businessPhone")) {
        rep.addColumn("Business Phone");
      }
      if (param.equals("businessAddress")) {
        rep.addColumn("Business Address Line 1");
        rep.addColumn("Business Address Line 2");
      }
      if (param.equals("city")) {
        rep.addColumn("City");
      }
      if (param.equals("state")) {
        rep.addColumn("State");
      }
      if (param.equals("zip")) {
        rep.addColumn("Zip");
      }
      if (param.equals("country")) {
        rep.addColumn("Country");
      }
      if (param.equals("notes")) {
        rep.addColumn("Notes");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildData(Connection db) throws SQLException {
    this.buildList(db);
    this.buildReportData(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildReportData(Connection db) throws SQLException {
    boolean writeOut = false;
    Organization tempOrg = null;

    Iterator x = this.iterator();
    while (x.hasNext()) {
      Contact thisContact = (Contact) x.next();
      ReportRow thisRow = new ReportRow();

      if (joinOrgs && thisContact.getOrgId() > 0) {
        tempOrg = new Organization(db, thisContact.getOrgId());

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
            thisRow.addCell(thisContact.getId());
          }
          if (param.equals("type")) {
            thisRow.addCell(thisContact.getTypeName());
          }
          if (param.equals("nameLast")) {
            thisRow.addCell(thisContact.getNameLast());
          }
          if (param.equals("nameFirst")) {
            thisRow.addCell(thisContact.getNameFirst());
          }
          if (param.equals("nameMiddle")) {
            thisRow.addCell(thisContact.getNameMiddle());
          }
          if (param.equals("company")) {
            thisRow.addCell(thisContact.getCompany());
          }
          if (param.equals("title")) {
            thisRow.addCell(thisContact.getTitle());
          }
          if (param.equals("department")) {
            thisRow.addCell(thisContact.getDepartmentName());
          }
          if (param.equals("entered")) {
            thisRow.addCell(thisContact.getEnteredString());
          }
          if (param.equals("enteredBy")) {
            thisRow.addCell(thisContact.getEnteredByName());
          }
          if (param.equals("modified")) {
            thisRow.addCell(thisContact.getModifiedString());
          }
          if (param.equals("modifiedBy")) {
            thisRow.addCell(thisContact.getModifiedByName());
          }
          if (param.equals("owner")) {
            thisRow.addCell(thisContact.getOwnerName());
          }
          if (param.equals("businessEmail")) {
            thisRow.addCell(thisContact.getEmailAddress("Business"));
          }
          if (param.equals("businessPhone")) {
            thisRow.addCell(thisContact.getPhoneNumber("Business"));
          }
          if (param.equals("businessAddress")) {
            thisRow.addCell(thisContact.getAddress("Business").getStreetAddressLine1());
            thisRow.addCell(thisContact.getAddress("Business").getStreetAddressLine2());
          }
          if (param.equals("city")) {
            thisRow.addCell(thisContact.getAddress("Business").getCity());
          }
          if (param.equals("state")) {
            thisRow.addCell(thisContact.getAddress("Business").getState());
          }
          if (param.equals("zip")) {
            thisRow.addCell(thisContact.getAddress("Business").getZip());
          }
          if (param.equals("country")) {
            thisRow.addCell(thisContact.getAddress("Business").getCountry());
          }
          if (param.equals("notes")) {
            thisRow.addCell(thisContact.getNotes());
          }
        }

        rep.addRow(thisRow);
      }

      writeOut = false;

    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildReportFull(Connection db) throws SQLException {
    buildReportBaseInfo();
    buildReportHeaders();
    buildData(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public boolean saveAndInsert(Connection db) throws Exception {
    int fileSize = save();

    if (joinOrgs) {
      thisItem.setLinkModuleId(Constants.DOCUMENTS_ACCOUNTS_REPORTS);
    } else {
      thisItem.setLinkModuleId(Constants.DOCUMENTS_CONTACTS_REPORTS);
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


  /**
   *  Description of the Method
   *
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public int save() throws Exception {
    this.generateFilename();
    File f = new File(filePath);
    f.mkdirs();

    rep.saveHtml(filePath + filenameToUse + ".html");
    rep.saveDelimited(filePath + filenameToUse + ".csv");

    File fileLink = new File(filePath + filenameToUse + ".csv");
    return ((int) fileLink.length());
  }


  /**
   *  Description of the Method
   *
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public String generateFilename() throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    filenameToUse = formatter.format(new java.util.Date());
    return filenameToUse;
  }
}

