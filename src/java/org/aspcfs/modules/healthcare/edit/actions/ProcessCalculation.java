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
package org.aspcfs.modules.healthcare.edit.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SecurityHook;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.healthcare.edit.base.FolderInsertRecord;
import org.aspcfs.modules.healthcare.edit.base.TransactionRecord;
import org.aspcfs.modules.healthcare.edit.base.TransactionRecordList;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.Template;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class processes EDIT transactions and stores them into a remote CRM
 * system. This process is intended to run on the transaction server each
 * night, summarizing the transactions, and then storing them in folders.
 *
 * @author chris
 * @version $Id: ProcessCalculation.java,v 1.4 2003/04/04 21:54:47 mrajkowski
 *          Exp $
 * @created February 11, 2003
 */
public final class ProcessCalculation extends CFSModule {

  /**
   * Execute this command to begin the processing, preferences must be set in
   * the system's preferences object
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    String test = context.getRequest().getParameter("test");
    //Declare the typical action objects
    Connection db = null;
    HashMap errors = new HashMap();
    SystemStatus systemStatus = this.getSystemStatus(context);
    int providerRecordsInserted = 0;
    //Staging Server objects: all the transactions from yesterday will be processed
    TransactionRecordList recordList = null;
    //CFS Server objects: folders to insert into
    CustomFieldCategoryList fullCategoryList = null;
    CustomFieldCategory providerTransDetails = null;
    CustomFieldCategory officeTransDetails = null;
    //Prepare all of the accounts folder categories
    fullCategoryList = new CustomFieldCategoryList();
    fullCategoryList.setLinkModuleId(Constants.ACCOUNTS);
    fullCategoryList.setIncludeEnabled(Constants.TRUE);
    fullCategoryList.setIncludeScheduled(Constants.TRUE);
    //Prepare map to store valid provider IDs with their respective OrgIds
    HashMap providerOrgMapping = new HashMap();
    //Prepare map to store valid payor IDs with an ArrayList of Accounts to which they belong
    HashMap payorOrgMapping = new HashMap();
    //More maps to store transactions for summary report
    HashMap providerTransactions = new HashMap();
    HashMap officeTransactions = new HashMap();
    //Objects for connecting to the remote database using the connection pool
    ConnectionPool sqlDriver = null;
    ConnectionElement connectionElement = null;
    Connection prodDb = null;
    //calculate date to process: yesterday's date by default
    java.sql.Date dateToProcess = null;
    Calendar cal = Calendar.getInstance();
    String dateString = context.getRequest().getParameter("date");
    if (dateString != null) {
      dateToProcess = DateUtils.parseDateString(dateString);
      cal.setTime(dateToProcess);
    } else {
      cal.add(Calendar.DATE, -1);
      dateToProcess = new java.sql.Date(cal.getTimeInMillis());
    }
    int month = cal.get(Calendar.MONTH) + 1;
    //Prepare records from the staging server
    recordList = new TransactionRecordList();
    recordList.setPerformed(dateToProcess);
    //The report progress will be stored in a StringBuffer and emailed
    StringBuffer sb = new StringBuffer();
    ConnectionElement ce = null;
    try {
      //get a local database connection
      AuthenticationItem auth = new AuthenticationItem();
      ce = auth.getConnectionElement(context);
      db = this.getConnection(context, ce);
      SystemStatus thisSystem = this.getSystemStatus(context, ce);
      if (thisSystem == null) {
        //Since typical login was bypassed, make sure the system status is in memory
        Site thisSite = SecurityHook.retrieveSite(context.getServletContext(), context.getRequest());
        thisSystem = SecurityHook.retrieveSystemStatus(
            context.getServletContext(), db, ce, thisSite.getLanguage());
      }
      //get connections to the remote production database
      sqlDriver = new ConnectionPool();
      sqlDriver.setMaxConnections(2);
      sqlDriver.setMaxIdleTimeSeconds(60 * 10);
      sqlDriver.setMaxDeadTimeSeconds(60 * 60);
      sqlDriver.setAllowShrinking(true);
      if (System.getProperty("DEBUG") != null) {
        sqlDriver.setDebug(true);
      }
      connectionElement = new ConnectionElement(
          this.getValue(context, ce, "DATABASE.URL"),
          this.getValue(context, ce, "DATABASE.USERNAME"),
          this.getValue(context, ce, "DATABASE.PASSWORD"));
      connectionElement.setAllowCloseOnIdle(true);
      connectionElement.setDriver(
          this.getValue(context, ce, "DATABASE.DRIVER"));
      prodDb = sqlDriver.getConnection(connectionElement);
      //build list of yesterday's records on transaction server
      recordList.buildList(db);
      //Get list of Accounts folders categories (Provider Transaction Details)
      providerTransDetails = new CustomFieldCategory(
          prodDb, getValueAsInt(context, ce, "PROVIDER_TRANSACTION_DETAILS"));
      providerTransDetails.setLinkModuleId(Constants.ACCOUNTS);
      providerTransDetails.setIncludeEnabled(Constants.TRUE);
      providerTransDetails.setIncludeScheduled(Constants.TRUE);
      providerTransDetails.setBuildResources(true);
      providerTransDetails.setEnteredBy(
          getValueAsInt(context, ce, "ENTERED_BY"));
      providerTransDetails.setModifiedBy(
          getValueAsInt(context, ce, "ENTERED_BY"));
      providerTransDetails.buildResources(prodDb);
      //Get list of Accounts folders categories (Office Transaction Details)
      officeTransDetails = new CustomFieldCategory(
          prodDb, getValueAsInt(context, ce, "OFFICE_TRANSACTION_DETAILS"));
      officeTransDetails.setLinkModuleId(Constants.ACCOUNTS);
      officeTransDetails.setIncludeEnabled(Constants.TRUE);
      officeTransDetails.setIncludeScheduled(Constants.TRUE);
      officeTransDetails.setBuildResources(true);
      officeTransDetails.setEnteredBy(
          getValueAsInt(context, ce, "ENTERED_BY"));
      officeTransDetails.setModifiedBy(
          getValueAsInt(context, ce, "ENTERED_BY"));
      officeTransDetails.buildResources(prodDb);
      //build a list of all the accounts custom categories
      fullCategoryList.buildList(prodDb);
      //get mapping of providers to orgIds and build
      CustomFieldCategory providerCategory = fullCategoryList.getCategory(
          getValueAsInt(context, ce, "OFFICE_PROVIDER_DETAILS"));
      providerCategory.setBuildResources(true);
      providerCategory.buildResources(prodDb);
      //Get the actual provider records
      CustomFieldRecordList providerRecords = new CustomFieldRecordList();
      providerRecords.setLinkModuleId(Constants.ACCOUNTS);
      providerRecords.setCategoryId(providerCategory.getId());
      providerRecords.buildList(prodDb);
      providerRecords.buildRecordColumns(prodDb, providerCategory);
      //Store each providerOrgId in a HashMap
      Iterator p = providerRecords.iterator();
      while (p.hasNext()) {
        CustomFieldRecord rec = (CustomFieldRecord) p.next();
        Iterator grps = providerCategory.iterator();
        while (grps.hasNext()) {
          CustomFieldGroup thisGrp = (CustomFieldGroup) grps.next();
          Iterator fields = thisGrp.iterator();
          if (fields.hasNext()) {
            CustomField thisField = ((CustomField) fields.next()).duplicate();
            thisField.setRecordId(rec.getId());
            thisField.buildResources(prodDb);
            //add in providerId => orgId pair
            providerOrgMapping.put(
                thisField.getValueHtml(), new Integer(rec.getLinkItemId()));
            if (System.getProperty("DEBUG") != null) {
              System.out.println(
                  "ProcessCalculation-> Adding to providerOrgMapping: " + thisField.getValueHtml() + " | " + rec.getLinkItemId());
            }
          }
        }
      }
      //Create mapping of payors to orgIds
      CustomFieldCategory payorCategory = fullCategoryList.getCategory(
          getValueAsInt(context, ce, "OFFICE_PAYOR_DETAILS"));
      payorCategory.setBuildResources(true);
      payorCategory.buildResources(prodDb);
      //Create mapping of payors to orgIds
      CustomFieldRecordList payorRecords = new CustomFieldRecordList();
      payorRecords.setLinkModuleId(Constants.ACCOUNTS);
      payorRecords.setCategoryId(payorCategory.getId());
      payorRecords.buildList(prodDb);
      payorRecords.buildRecordColumns(prodDb, payorCategory);
      Iterator ip = payorRecords.iterator();
      while (ip.hasNext()) {
        CustomFieldRecord rec = (CustomFieldRecord) ip.next();
        Iterator grps = payorCategory.iterator();
        while (grps.hasNext()) {
          CustomFieldGroup thisGrp = (CustomFieldGroup) grps.next();
          Iterator fields = thisGrp.iterator();
          if (fields.hasNext()) {
            CustomField thisField = ((CustomField) fields.next()).duplicate();
            thisField.setRecordId(rec.getId());
            thisField.buildResources(prodDb);

            if (payorOrgMapping.get(thisField.getValueHtml()) == null) {
              ArrayList tempArray = new ArrayList();
              tempArray.add(new Integer(rec.getLinkItemId()));
              payorOrgMapping.put(thisField.getValueHtml(), tempArray);
            } else {
              ArrayList tempArray = (ArrayList) payorOrgMapping.get(
                  thisField.getValueHtml());
              tempArray.add(new Integer(rec.getLinkItemId()));
              payorOrgMapping.remove(thisField.getValueHtml());
              //add in payorId => ArrayList of orgIds pair
              payorOrgMapping.put(thisField.getValueHtml(), tempArray);
            }
          }
        }
      }
      //the custom form id numbers cf"X" used to populate context
      ArrayList providerTransactionDetailsIds = providerTransDetails.getFormFieldIds();
      ArrayList officeTransactionDetailsIds = officeTransDetails.getFormFieldIds();
      //Store all transactions for today in HashMaps for calculations
      Iterator i = recordList.iterator();
      while (i.hasNext()) {
        boolean hasErrors = false;
        TransactionRecord thisRec = (TransactionRecord) i.next();
        //error checking
        if (providerOrgMapping.get(thisRec.getTaxId()) == null) {
          hasErrors = true;
          errors.put(
              new Integer(thisRec.getId()), new String(
                  "Error: Provider with tax ID = " + thisRec.getTaxId() + " not found in Concourse Suite Community Edition!"));
        } else if (!payorOrgMapping.containsKey(thisRec.getPayerId())) {
          hasErrors = true;
          errors.put(
              new Integer(thisRec.getId()), new String(
                  "Error: Payor with ID = " + thisRec.getPayerId() + " not found in Concourse Suite Community Edition!"));
        }
        if (!hasErrors) {
          //error checking
          ArrayList tempArray = (ArrayList) payorOrgMapping.get(
              thisRec.getPayerId());
          if (!(tempArray.contains(
              ((Integer) providerOrgMapping.get(thisRec.getTaxId()))))) {
            hasErrors = true;
            errors.put(
                new Integer(thisRec.getId()), new String(
                    "Error: Payor with ID = " + thisRec.getPayerId() + " not associated with Provider " + thisRec.getTaxId() + " in Concourse Suite Community Edition!"));
          }
        }
        if (!hasErrors) {
          //Make sure the type is understood, then throw away the transaction
          FolderInsertRecord tempFir = new FolderInsertRecord();
          if (!tempFir.process(thisRec)) {
            hasErrors = true;
            errors.put(
                new Integer(thisRec.getId()), new String(
                    "Error: Transaction Type " + thisRec.getType() + " not recognized"));
          }
        }
        //Add today's data to the TaxId HashMap and the TaxId/PayorId HashMap
        if (!hasErrors) {
          //Maintain a HashMap of taxIds for today's calculations
          FolderInsertRecord fir = (FolderInsertRecord) providerTransactions.get(
              thisRec.getTaxId());
          if (fir == null) {
            fir = new FolderInsertRecord(
                thisRec.getTransactionId(), thisRec.getPayerId());
            providerTransactions.put(thisRec.getTaxId(), fir);
          }
          fir.process(thisRec);
          //Maintain a HashMap of taxIds/payorIds for MTD calculations
          HashMap officePayors = (HashMap) officeTransactions.get(
              thisRec.getTaxId());
          if (officePayors == null) {
            officePayors = new HashMap();
            officeTransactions.put(thisRec.getTaxId(), officePayors);
          }
          FolderInsertRecord pfir = (FolderInsertRecord) officePayors.get(
              thisRec.getPayerId());
          if (pfir == null) {
            pfir = new FolderInsertRecord(
                thisRec.getTransactionId(), thisRec.getPayerId());
            officePayors.put(thisRec.getPayerId(), pfir);
          }
          pfir.process(thisRec);
        }
      }

      //Update the MTD values for the provider
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessCalculation-> Updating MTD values");
      }
      Iterator officeIterator = officeTransactions.keySet().iterator();
      while (officeIterator.hasNext()) {
        String taxId = (String) officeIterator.next();
        HashMap officePayors = (HashMap) officeTransactions.get(taxId);
        //Build a list of office MTD records for the specified taxId
        officeTransDetails.setLinkItemId(
            ((Integer) providerOrgMapping.get(taxId)).intValue());
        officeTransDetails.setBuildResources(true);
        CustomFieldRecordList officeRecords = new CustomFieldRecordList();
        officeRecords.setLinkModuleId(Constants.ACCOUNTS);
        officeRecords.setLinkItemId(
            ((Integer) providerOrgMapping.get(taxId)).intValue());
        officeRecords.setCategoryId(officeTransDetails.getId());
        officeRecords.buildList(prodDb);
        officeRecords.buildRecordColumns(prodDb, officeTransDetails);
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "ProcessCalculation-> Built list for: " + officeRecords.getLinkItemId() + " (" + officeRecords.size() + ")");
        }
        //Go through all payorIds and add today's transactions with the specified taxId to the MTD totals
        Iterator officePayorIterator = officePayors.keySet().iterator();
        while (officePayorIterator.hasNext()) {
          String payorId = (String) officePayorIterator.next();
          FolderInsertRecord val = (FolderInsertRecord) officePayors.get(
              payorId);
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "ProcessCalculation-> TaxId: " + taxId + " PayorId: " + payorId + " Status: " + (val != null) + " OrgId: " + ((Integer) providerOrgMapping.get(
                    taxId)).intValue());
          }
          ArrayList previousValues = new ArrayList();
          int foundId = -1;

          //try to find if MTD info is in there already and matches the payorId for this office (taxId)
          Iterator op = officeRecords.iterator();
          while (op.hasNext() && foundId == -1) {
            CustomFieldRecord rec = (CustomFieldRecord) op.next();

            officeTransDetails.setRecordId(rec.getId());
            officeTransDetails.buildResources(prodDb);

            Iterator grps = officeTransDetails.iterator();
            while (grps.hasNext() && foundId == -1) {
              CustomFieldGroup thisGrp = (CustomFieldGroup) grps.next();
              Iterator fields = thisGrp.iterator();
              if (fields.hasNext()) {
                CustomField payorIdField = ((CustomField) fields.next()).duplicate();
                //Check to see if this first field matches the PAYOR ID, to update the values...
                if ((payorIdField.getValueHtml()).equals(val.getPayorId())) {
                  CustomField thisField = (CustomField) fields.next();
                  java.sql.Date tempDate = DateUtils.parseDateString(
                      thisField.getValueHtml(), "MM/dd/yyy");
                  Calendar tempCal = Calendar.getInstance();
                  tempCal.setTime(tempDate);

                  int tempMonth = tempCal.get(Calendar.MONTH);
                  tempMonth = tempMonth + 1;

                  //if there's already a record for this month in Office Trans. Details...
                  if (month == tempMonth && cal.get(Calendar.YEAR) == tempCal.get(
                      Calendar.YEAR)) {
                    if (System.getProperty("DEBUG") != null) {
                      System.out.println(
                          "ProcessCalculation-> Date found: " + tempMonth + "/" + tempCal.get(
                              Calendar.YEAR));
                    }
                    for (int z = 0; z < 8; z++) {
                      CustomField innerField = (CustomField) fields.next();
                      previousValues.add(innerField.getEnteredValue());
                    }
                    foundId = rec.getId();
                    //update the MTD totals
                    val.updateTotals(previousValues);
                  }
                }
              }
            }
          }
          //Insert/Update all of the MTD transactions into the database
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(0), val.getPayorId());
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(1), recordList.getPerformedString());
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(2), val.getEligibility() + "");
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(3), val.getClaimStatus() + "");
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(4), val.getReferral() + "");
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(5), val.getAdvice() + "");
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(6), val.getDental() + "");
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(7), val.getProfessional() + "");
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(8), val.getInstitutional() + "");
          context.getRequest().setAttribute(
              "cf" + officeTransactionDetailsIds.get(9), val.getClaimRemittance() + "");
          officeTransDetails.setParameters(context);
          //if our record exists in the database, lets update the values, otherwise, insert new.
          if (foundId > -1) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("ProcessCalculation-> ...updating");
            }
            officeTransDetails.setRecordId(foundId);
            //update
            if (test == null) {
              officeTransDetails.update(prodDb);
            }
          } else {
            //insert
            if (System.getProperty("DEBUG") != null) {
              System.out.println("ProcessCalculation-> ...inserting");
            }
            if (test == null) {
              officeTransDetails.insert(prodDb);
            }
          }
        }
      }
      //Insert all of today's transactions into the database
      Iterator it = providerTransactions.keySet().iterator();
      while (it.hasNext()) {
        String key = (String) it.next();
        FolderInsertRecord val = (FolderInsertRecord) providerTransactions.get(
            key);
        //this gets us the org_id of the Office to which this provider belongs
        //if the provider belongs to an Account/Office, insert it
        providerTransDetails.setLinkItemId(
            ((Integer) providerOrgMapping.get(key)).intValue());
        //ID must be first on form!
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(0), key);
        //set the date
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(1), recordList.getPerformedString());
        //set the totals
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(2), val.getEligibility() + "");
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(3), val.getClaimStatus() + "");
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(4), val.getReferral() + "");
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(5), val.getAdvice() + "");
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(6), val.getDental() + "");
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(7), val.getProfessional() + "");
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(8), val.getInstitutional() + "");
        context.getRequest().setAttribute(
            "cf" + providerTransactionDetailsIds.get(9), val.getClaimRemittance() + "");
        //insert
        providerTransDetails.setParameters(context);
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "ProcessCalculation-> Inserting provider transactions: " + key);
        }
        if (test == null) {
          if (providerTransDetails.insert(prodDb) > -1) {
            providerRecordsInserted++;
          }
        }
      }
      if (systemStatus != null) {
        HashMap map = new HashMap();
        map.put("${recordList.size}", "" + recordList.size());
        map.put("${providerRecordsInserted}", "" + providerRecordsInserted);
        map.put("${errors.size}", "" + errors.size());
        sb.append(
            getLabel(
                map, systemStatus.getLabel("mail.body.transactionDataSet1")));
      } else {
        sb.append(recordList.size() + " total record(s) processed<br>");
        sb.append(
            providerRecordsInserted + " Provider Transaction Details record(s) inserted<br>");
        sb.append(errors.size() + " record(s) rejected<br><br>");
      }
      //Prepare the error messages for emailing
      Iterator end = errors.keySet().iterator();
      while (end.hasNext()) {
        Integer k = (Integer) end.next();
        String em = (String) errors.get(k);
        TransactionRecord tempRec = new TransactionRecord(db, k.intValue());
        if (systemStatus != null) {
          HashMap map = new HashMap();
          map.put("${tempRec.transactionId}", tempRec.getTransactionId());
          map.put("${tempRec.taxId}", "" + tempRec.getTaxId());
          map.put("${tempRec.nameLast}", tempRec.getNameLast());
          map.put("${tempRec.payerId}", "" + tempRec.getPayerId());
          map.put("${em}", em);
          sb.append(
              getLabel(
                  map, systemStatus.getLabel("mail.body.transactionDataSet2")));
        } else {
          sb.append("-------------------------<br><br>");
          sb.append("Transaction ID : " + tempRec.getTransactionId() + "<br>");
          sb.append("Provider Tax ID: " + tempRec.getTaxId() + "<br>");
          sb.append("Provider Last Name: " + tempRec.getNameLast() + "<br>");
          sb.append("Vendor (Payer) ID: " + tempRec.getPayerId() + "<br><br>");
          sb.append(em + "<br><br>");
        }
      }
      prodDb.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (sqlDriver != null) {
        sqlDriver.free(prodDb);
        sqlDriver.closeAllConnections();
        sqlDriver = null;
      }
      this.freeConnection(context, db);
    }
    //Mail the final report
    if (ce != null) {
      SMTPMessage mail = new SMTPMessage();
      mail.setHost(getPref(context, "MAILSERVER"));
      mail.setFrom(getPref(context, "EMAILADDRESS"));
      mail.setType("text/html");
      mail.setTo(this.getValue(context, ce, "ERROR_REPORT_ADDRESS"));
      if (systemStatus != null) {
        HashMap map = new HashMap();
        map.put("${month}", "" + month);
        map.put("${dayOfMonth}", "" + cal.get(Calendar.DAY_OF_MONTH));
        map.put("${year}", "" + cal.get(Calendar.YEAR));
        String subject = systemStatus.getLabel(
            "mail.subject.transactionDataSummery");
        mail.setSubject(getLabel(map, subject));
        if (sb.length() == 0) {
          mail.setBody(systemStatus.getLabel("mail.body.mailProcessingError"));
        } else {
          mail.setBody(sb.toString());
        }
      } else {
        mail.setSubject(
            "EDIT transaction data summary: " + month + "/" + cal.get(
                Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
        if (sb.length() == 0) {
          mail.setBody(
              "* A PROCESSING ERROR HAS OCCURRED, THE APPLICATION NEEDS TO BE CHECKED");
        } else {
          mail.setBody(sb.toString());
        }
      }
      if (mail.send() == 2) {
        System.err.println(mail.getErrorMsg());
      } else {
        System.err.println(
            "ProcessCalculation-> Sending report to " + this.getValue(
                context, ce, "ERROR_REPORT_ADDRESS"));
      }
    }
    return ("-none-");
  }


  /**
   * Gets the requested preference from the System preferences as an integer.
   * Returns -1 if not found
   *
   * @param context Description of the Parameter
   * @param param   Description of the Parameter
   * @param ce      Description of the Parameter
   * @return The value value
   */
  private int getValueAsInt(ActionContext context, ConnectionElement ce, String param) {
    return this.getSystemStatus(context, ce).getValueAsInt(
        "org.aspcfs.modules.healthcare.edit.actions.ProcessCalculation", param);
  }


  /**
   * Gets the requested preference from the System preferences as an integer.
   *
   * @param context Description of the Parameter
   * @param param   Description of the Parameter
   * @param ce      Description of the Parameter
   * @return The value value
   */
  private String getValue(ActionContext context, ConnectionElement ce, String param) {
    return this.getSystemStatus(context, ce).getValue(
        "org.aspcfs.modules.healthcare.edit.actions.ProcessCalculation", param);
  }


  /**
   * Gets the label attribute of the ProcessCalculation object
   *
   * @param map   Description of the Parameter
   * @param input Description of the Parameter
   * @return The label value
   */
  public String getLabel(HashMap map, String input) {
    Template template = new Template(input);
    template.setParseElements(map);
    return template.getParsedText();
  }
}
