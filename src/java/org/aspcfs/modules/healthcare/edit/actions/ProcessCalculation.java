package org.aspcfs.modules.healthcare.edit.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.database.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.base.CustomFieldCategoryList;
import org.aspcfs.modules.base.CustomFieldRecordList;
import org.aspcfs.modules.base.CustomFieldRecord;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomFieldGroup;
import java.io.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.healthcare.edit.base.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    February 11, 2003
 *@version    $Id$
 */
public final class ProcessCalculation extends CFSModule {

  //these global IDs need to correspond to the folders for this to work!
  public final static int OFFICE_PROVIDER_DETAILS = 2;
  public final static int OFFICE_PAYOR_DETAILS = 1;
  public final static int PROVIDER_TRANSACTION_DETAILS = 4;
  public final static int OFFICE_TRANSACTION_DETAILS = 3;
  public final static String ERROR_REPORT_ADDRESS = "cprice@hrdenterprises.com";


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Connection db = null;
    StringBuffer sb = new StringBuffer();
    Connection prodDb = null;
    Exception errorMessage = null;
    java.sql.Date dateToProcess = null;
    HashMap errors = new HashMap();
    int providerRecordsInserted = 0;

    //all the transactions from yesterday, from staging server
    TransactionRecordList recordList = null;
    CustomFieldCategoryList fullCategoryList = null;
    CustomFieldCategory providerTransDetails = null;
    CustomFieldCategory officeTransDetails = null;

    //all Accounts folder categories
    fullCategoryList = new CustomFieldCategoryList();
    fullCategoryList.setLinkModuleId(Constants.ACCOUNTS);
    fullCategoryList.setIncludeEnabled(Constants.TRUE);
    fullCategoryList.setIncludeScheduled(Constants.TRUE);

    //maps valid provider IDs with their respective OrgIds in CFS
    HashMap providerOrgMapping = new HashMap();
    //maps valid payor IDs with an ArrayList of Accounts to which they belong in CFS
    HashMap payorOrgMapping = new HashMap();

    HashMap payorTransactions = new HashMap();
    HashMap providerTransactions = new HashMap();
    ConnectionPool sqlDriver = null;
    ConnectionElement connectionElement = null;

    try {
      sqlDriver = new ConnectionPool();
    } catch (SQLException e) {
    }

    //yesterday's date
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    dateToProcess = new java.sql.Date(cal.getTime().getTime());

    int month = cal.get(Calendar.MONTH);
    month = month + 1;

    //records from the staging server
    recordList = new TransactionRecordList();
    recordList.setPerformed(dateToProcess);

    try {
      //get a database connection
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);

      //connection to the production db
      sqlDriver.setMaxConnections(1);
      sqlDriver.setDebug(true);
      connectionElement = new ConnectionElement(
          "jdbc:postgresql://216.54.13.43:5432/cdb_edit",
          "cfsdba",
          "");
      connectionElement.setAllowCloseOnIdle(false);
      connectionElement.setDriver("org.postgresql.Driver");
      prodDb = sqlDriver.getConnection(connectionElement);

      //build list of yesterday's records
      recordList.buildList(db);

      //for specific Accounts folders categories (Provider Transaction Details)
      providerTransDetails = new CustomFieldCategory(prodDb, PROVIDER_TRANSACTION_DETAILS);
      providerTransDetails.setLinkModuleId(Constants.ACCOUNTS);
      providerTransDetails.setIncludeEnabled(Constants.TRUE);
      providerTransDetails.setIncludeScheduled(Constants.TRUE);
      providerTransDetails.setBuildResources(true);
      //need some designated user
      providerTransDetails.setEnteredBy(1);
      providerTransDetails.setModifiedBy(1);

      //for specific Accounts folders categories (Office Transaction Details)
      officeTransDetails = new CustomFieldCategory(prodDb, OFFICE_TRANSACTION_DETAILS);
      officeTransDetails.setLinkModuleId(Constants.ACCOUNTS);
      officeTransDetails.setIncludeEnabled(Constants.TRUE);
      officeTransDetails.setIncludeScheduled(Constants.TRUE);
      officeTransDetails.setBuildResources(true);
      //need some designated user
      officeTransDetails.setEnteredBy(1);
      officeTransDetails.setModifiedBy(1);

      //build
      providerTransDetails.buildResources(prodDb);
      officeTransDetails.buildResources(prodDb);

      //build a list of all the accounts custom categories
      fullCategoryList.buildList(prodDb);

      //get mapping of providers to orgIds
      CustomFieldCategory providerCategory = fullCategoryList.getCategory(OFFICE_PROVIDER_DETAILS);
      //build groups
      providerCategory.setBuildResources(true);
      providerCategory.buildResources(prodDb);

      CustomFieldRecordList providerRecords = new CustomFieldRecordList();
      providerRecords.setLinkModuleId(Constants.ACCOUNTS);
      providerRecords.setCategoryId(providerCategory.getId());
      providerRecords.buildList(prodDb);
      providerRecords.buildRecordColumns(prodDb, providerCategory);

      Iterator p = providerRecords.iterator();
      while (p.hasNext()) {
        CustomFieldRecord rec = (CustomFieldRecord) p.next();
        Iterator grps = providerCategory.iterator();
        while (grps.hasNext()) {
          CustomFieldGroup thisGrp = (CustomFieldGroup) grps.next();
          Iterator fields = thisGrp.iterator();
          if (fields.hasNext()) {
            CustomField thisField = (CustomField) fields.next();
            thisField.setRecordId(rec.getId());
            thisField.buildResources(prodDb);
            //add in providerId => orgId pair
            providerOrgMapping.put(thisField.getValueHtml(), new Integer(rec.getLinkItemId()));
          }
        }
      }
      //end mapping of providers to orgIds

      //get mapping of payors to orgIds
      CustomFieldCategory payorCategory = fullCategoryList.getCategory(OFFICE_PAYOR_DETAILS);
      payorCategory.setBuildResources(true);
      payorCategory.buildResources(prodDb);

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
            CustomField thisField = (CustomField) fields.next();
            thisField.setRecordId(rec.getId());
            thisField.buildResources(prodDb);

            if (payorOrgMapping.get(thisField.getValueHtml()) == null) {
              ArrayList tempArray = new ArrayList();
              tempArray.add(new Integer(rec.getLinkItemId()));
              payorOrgMapping.put(thisField.getValueHtml(), tempArray);
            } else {
              ArrayList tempArray = (ArrayList) payorOrgMapping.get(thisField.getValueHtml());
              tempArray.add(new Integer(rec.getLinkItemId()));
              payorOrgMapping.remove(thisField.getValueHtml());
              //add in payorId => ArrayList of orgIds pair
              payorOrgMapping.put(thisField.getValueHtml(), tempArray);
            }
          }
        }
      }
      //end mapping of payors to orgIds

      //the custom form id numbers cf"X" used to populate context
      ArrayList providerTransactionDetailsIds = providerTransDetails.getFormFieldIds();
      ArrayList officeTransactionDetailsIds = officeTransDetails.getFormFieldIds();

      //build provider-specific information for transactions
      Iterator i = recordList.iterator();
      while (i.hasNext()) {
        boolean hasErrors = false;
        TransactionRecord thisRec = (TransactionRecord) i.next();

        //error checking!
        if (providerOrgMapping.get(thisRec.getTaxId()) == null) {
          hasErrors = true;
          errors.put(new Integer(thisRec.getId()), new String("Error: Provider with tax ID = " + thisRec.getTaxId() + " not found in CFS!"));
        } else if (!payorOrgMapping.containsKey(thisRec.getPayerId())) {
          hasErrors = true;
          errors.put(new Integer(thisRec.getId()), new String("Error: Payor with ID = " + thisRec.getPayerId() + " not found in CFS!"));
        }

        if (!hasErrors) {
          //error checking
          ArrayList tempArray = (ArrayList) payorOrgMapping.get(thisRec.getPayerId());
          if (!(tempArray.contains(((Integer) providerOrgMapping.get(thisRec.getTaxId()))))) {
            hasErrors = true;
            errors.put(new Integer(thisRec.getId()), new String("Error: Payor with ID = " + thisRec.getPayerId() + " not associated with Provider " + thisRec.getTaxId() + " in CFS!"));
          }
        }

        if (!hasErrors) {
          FolderInsertRecord tempFir = new FolderInsertRecord();
          if (!tempFir.process(thisRec)) {
            hasErrors = true;
            errors.put(new Integer(thisRec.getId()), new String("Error: Transaction Type " + thisRec.getType() + " not recognized"));
          }
        }
        //end of error checking


        if (!hasErrors) {
          //if this taxId does not already exist as a key, add it.
          if (!providerTransactions.containsKey(thisRec.getTaxId())) {
            providerTransactions.put(thisRec.getTaxId(), new FolderInsertRecord(thisRec.getTransactionId(), thisRec.getPayerId()));
          }

          if (!payorTransactions.containsKey(thisRec.getPayerId())) {
            payorTransactions.put(thisRec.getPayerId(), new FolderInsertRecord(thisRec.getTransactionId(), thisRec.getPayerId(), thisRec.getTaxId()));
          }

          //provider
          FolderInsertRecord fir = (FolderInsertRecord) providerTransactions.get(thisRec.getTaxId());
          //process totals
          fir.process(thisRec);

          //payer
          FolderInsertRecord pfir = (FolderInsertRecord) payorTransactions.get(thisRec.getPayerId());
          //process totals
          pfir.process(thisRec);
        }
      }

      Iterator payorIterator = payorTransactions.keySet().iterator();
      while (payorIterator.hasNext()) {
        String key = (String) payorIterator.next();
        FolderInsertRecord val = (FolderInsertRecord) payorTransactions.get(key);
        boolean hasErrors = false;

        if (!hasErrors) {
          ArrayList previousValues = new ArrayList();
          int foundId = -1;

          officeTransDetails.setLinkItemId(((Integer) providerOrgMapping.get(val.getProviderId())).intValue());
          officeTransDetails.setBuildResources(true);

          CustomFieldRecordList officeRecords = new CustomFieldRecordList();
          officeRecords.setLinkModuleId(Constants.ACCOUNTS);
          officeRecords.setLinkItemId(((Integer) providerOrgMapping.get(val.getProviderId())).intValue());
          officeRecords.setCategoryId(officeTransDetails.getId());
          officeRecords.buildList(prodDb);
          officeRecords.buildRecordColumns(prodDb, officeTransDetails);

          //try to find if MTD info is in there already.
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
                CustomField payorIdField = (CustomField) fields.next();
                if ((payorIdField.getValueHtml()).equals(key)) {
                  CustomField thisField = (CustomField) fields.next();
                  java.sql.Date tempDate = DateUtils.parseDateString(thisField.getValueHtml(), "MM/dd/yyy");
                  Calendar tempCal = Calendar.getInstance();
                  tempCal.setTime(tempDate);

                  int tempMonth = tempCal.get(Calendar.MONTH);
                  tempMonth = tempMonth + 1;

                  //if there's already a record for this month in Office Trans. Details...
                  if (month == tempMonth && cal.get(Calendar.YEAR) == tempCal.get(Calendar.YEAR)) {
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
          //end of MTD check

          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(0), val.getPayorId());
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(1), recordList.getPerformedString());
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(2), val.getEligibility() + "");
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(3), val.getClaimStatus() + "");
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(4), val.getReferral() + "");
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(5), val.getAdvice() + "");
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(6), val.getDental() + "");
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(7), val.getProfessional() + "");
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(8), val.getInstitutional() + "");
          context.getRequest().setAttribute("cf" + officeTransactionDetailsIds.get(9), val.getClaimRemittance() + "");
          officeTransDetails.setParameters(context);
          //if our record exists in the database, lets update the values, otherwise, insert new.
          if (foundId > -1) {
            officeTransDetails.setRecordId(foundId);
            //update
            officeTransDetails.update(prodDb);
          } else {
            //insert
            officeTransDetails.insert(prodDb);
          }
        }
      }
      //end insertion of office transaction records

      //insert some records for provider transaction details
      Iterator it = providerTransactions.keySet().iterator();
      while (it.hasNext()) {
        boolean hasErrors = false;
        boolean payorIsValid = false;

        String key = (String) it.next();
        FolderInsertRecord val = (FolderInsertRecord) providerTransactions.get(key);

        if (System.getProperty("DEBUG") != null) {
          System.out.println("ProcessCalculation-> attempting to insert provider transactions");
        }

        //this gets us the CFS org_id of the Office to which this provider belongs
        //if the provider belongs to an Account/Office, insert it
        providerTransDetails.setLinkItemId(((Integer) providerOrgMapping.get(key)).intValue());

        //ID must be first on form!
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(0), key);
        //set the date
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(1), recordList.getPerformedString());
        //set the totals
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(2), val.getEligibility() + "");
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(3), val.getClaimStatus() + "");
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(4), val.getReferral() + "");
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(5), val.getAdvice() + "");
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(6), val.getDental() + "");
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(7), val.getProfessional() + "");
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(8), val.getInstitutional() + "");
        context.getRequest().setAttribute("cf" + providerTransactionDetailsIds.get(9), val.getClaimRemittance() + "");

        //insert
        providerTransDetails.setParameters(context);
        if (providerTransDetails.insert(prodDb) > -1) {
          providerRecordsInserted++;
        }
      }

      sb.append(recordList.size() + " total record(s) processed<br>");
      sb.append(providerRecordsInserted + " Provider Transaction Details record(s) inserted<br>");
      sb.append(errors.size() + " record(s) rejected<br><br>");

      Iterator end = errors.keySet().iterator();
      while (end.hasNext()) {
        Integer k = (Integer) end.next();
        String em = (String) errors.get(k);
        TransactionRecord tempRec = new TransactionRecord(db, k.intValue());
        sb.append("-------------------------<br><br>");
        sb.append("Transaction ID : " + tempRec.getTransactionId() + "<br>");
        sb.append("Provider Tax ID: " + tempRec.getTaxId() + "<br>");
        sb.append("Provider Last Name: " + tempRec.getNameLast() + "<br>");
        sb.append("Vendor (Payer) ID: " + tempRec.getPayerId() + "<br><br>");

        sb.append(em + "<br><br>");
      }

    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
      sqlDriver.free(prodDb);
    }

    SMTPMessage mail = new SMTPMessage();
    mail.setHost((String) System.getProperty("MailServer"));
    mail.setFrom("cfs-messenger@darkhorseventures.com");
    mail.setType("text/html");
    mail.setTo(ERROR_REPORT_ADDRESS);

    mail.setSubject("EDIT transaction data summary: " + month + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));

    mail.setBody(sb.toString());

    if (mail.send() == 2) {
      System.err.println(mail.getErrorMsg());
    } else {
      System.err.println("Error sending message to " + ERROR_REPORT_ADDRESS);
    }

    return ("-none-");
  }
}

