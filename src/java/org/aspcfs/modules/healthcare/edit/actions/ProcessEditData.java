/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package org.aspcfs.modules.healthcare.edit.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.login.base.AuthenticationItem;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;
import org.aspcfs.utils.*;

/**
 *  Module for processing a generated report and storing into a database
 *
 *@author     chris
 *@created    January 15, 2003
 *@version    $Id$
 */
public final class ProcessEditData extends CFSModule {

  public final static int PAYOR_DETAILS_CATEGORY = 5;
  public final static int PROVIDER_TRANSACTION_CATEGORY = 10;
  public final static int OFFICE_TRANSACTION_CATEGORY = 4;


  /**
   *  This action processes a supplied CSV report and inserts the data
   *  into folder tables
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    String id = (String) context.getRequest().getParameter("id");
    StringBuffer testBuffer = new StringBuffer();
    Connection db = null;
    Organization thisOrganization = null;
    int insertCount = 0;
    int totalsCount = 0;
    ArrayList notFoundIds = new ArrayList();

    ArrayList ids = new ArrayList();
    ArrayList finalIds = new ArrayList();
    ArrayList providerArray = new ArrayList();

    HashMap thisMap = new HashMap();
    HashMap providerMap = new HashMap();

    HashMap orgTotalsMap = new HashMap();
    HashMap perDayTotalsMap = new HashMap();

    try {
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
      //Process the request parameters
      StringTokenizer st = new StringTokenizer(id, "|");
      String dbName = st.nextToken();
      String fileName = st.nextToken();
      //Query folders to get some base data for the conversion
      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
      thisList.setLinkModuleId(Constants.ACCOUNTS);
      thisList.setIncludeEnabled(Constants.TRUE);
      thisList.setIncludeScheduled(Constants.TRUE);
      thisList.buildList(db);
      //Check organizations to cache corresponding folder data
      OrganizationList orgList = new OrganizationList();
      orgList.buildList(db);
      Iterator x = orgList.iterator();
      while (x.hasNext()) {
        thisOrganization = (Organization) x.next();
        //Check the PAYOR_DETAILS folder
        CustomFieldCategory thisCategory = thisList.getCategory(PAYOR_DETAILS_CATEGORY);
        thisCategory.setBuildResources(true);
        thisCategory.buildResources(db);
        //Get the records in this folder
        CustomFieldRecordList recordList = new CustomFieldRecordList();
        recordList.setLinkModuleId(Constants.ACCOUNTS);
        recordList.setLinkItemId(thisOrganization.getOrgId());
        recordList.setCategoryId(thisCategory.getId());
        recordList.buildList(db);
        recordList.buildRecordColumns(db, thisCategory);
        Iterator r = recordList.iterator();
        while (r.hasNext()) {
          //For each record get the specific field we need data for
          CustomFieldRecord thisRecord = (CustomFieldRecord) r.next();
          Iterator groups = thisCategory.iterator();
          while (groups.hasNext()) {
            CustomFieldGroup thisGroup = (CustomFieldGroup) groups.next();
            Iterator fields = thisGroup.iterator();
            if (fields.hasNext()) {
              while (fields.hasNext()) {
                CustomField thisField = (CustomField) fields.next();
                //THIS IS IT - DONT FORGET
                thisField.setRecordId(thisRecord.getId());
                thisField.buildResources(db);
                //!!!!
                if (thisField.getNameHtml().indexOf("Provider ID Number") > -1) {
                  thisMap.put(((Object) thisField.getValueHtml()), (new Integer(thisOrganization.getOrgId())));
                }
              }
            }
          }

        }
      }

      //GET FIELD IDS FOR THE PROVIDER TRANS DETAILS
      CustomFieldCategory thisCategory2 = thisList.getCategory(PROVIDER_TRANSACTION_CATEGORY);
      thisCategory2.setBuildResources(true);
      thisCategory2.buildResources(db);
      Iterator groups2 = thisCategory2.iterator();
      while (groups2.hasNext()) {
        CustomFieldGroup thisGroup2 = (CustomFieldGroup) groups2.next();
        Iterator fields2 = thisGroup2.iterator();
        if (fields2.hasNext()) {
          while (fields2.hasNext()) {
            CustomField thisField2 = (CustomField) fields2.next();
            ids.add(new Integer(thisField2.getId()));
          }
        }
      }

      //GET FINAL IDS
      CustomFieldCategory thisCategory3 = thisList.getCategory(OFFICE_TRANSACTION_CATEGORY);
      thisCategory3.setBuildResources(true);
      thisCategory3.buildResources(db);
      Iterator groups3 = thisCategory3.iterator();
      while (groups3.hasNext()) {
        CustomFieldGroup thisGroup3 = (CustomFieldGroup) groups3.next();
        Iterator fields3 = thisGroup3.iterator();
        if (fields3.hasNext()) {
          while (fields3.hasNext()) {
            CustomField thisField3 = (CustomField) fields3.next();
            System.out.println("adding to finalIds: " + thisField3.getId());
            finalIds.add(new Integer(thisField3.getId()));
          }
        }
      }
      //Now that all data is there, process the uploaded file at the specified path
      String filePath = this.getPath(context) + "cdb_" + dbName + fs + "data" + fs + fileName + ".csv";
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessData-> Sending " + filePath);
      }
      //Prepare some variables
      String t;
      String sub;
      String subsub;
      String compareDate = "";
      int transType = -1;
      boolean moreToProcess = true;
      CustomFieldCategory thisCategory = null;
      //Parse the text file
      BufferedReader in = new BufferedReader(new FileReader(filePath));
      String line = null;
      int lineNumber = 0;
      while ((line = in.readLine()) != null) {
        ++lineNumber;
        //For each line use the parseExcelCSVLine method to get a record
        ArrayList thisRecord = new ArrayList(StringUtils.parseExcelCSVLine(line));
        Iterator values = thisRecord.iterator();
        while (values.hasNext()) {
          t = (String) values.next();
          if (t.indexOf("PROVIDER") > -1) {
            t = t.substring(10).trim();
            if (t.length() > 0) {
              for (int k = 0; k < 5; k++) {
                providerArray.add(k, new Integer(0));
              }
              while (values.hasNext()) {
                sub = (String) values.next();
                if (sub.indexOf("TRANS TYPE") > -1) {
                  moreToProcess = true;
                  sub = (String) values.next();
                  sub = (String) values.next();
                  sub = (String) values.next();
                  //strip header and first ID
                  for (int z = 0; z < 17; z++) {
                    subsub = (String) values.next();
                  }
                  subsub = (String) values.next();
                  compareDate = subsub;
                  if (thisMap.get(t) != null) {
                    thisCategory = new CustomFieldCategory(db, PROVIDER_TRANSACTION_CATEGORY);
                    thisCategory.setLinkModuleId(Constants.ACCOUNTS);
                    thisCategory.setLinkItemId(((Integer) thisMap.get(t)).intValue());
                    thisCategory.setIncludeEnabled(Constants.TRUE);
                    thisCategory.setIncludeScheduled(Constants.TRUE);
                    thisCategory.setEnteredBy(2);
                    thisCategory.setModifiedBy(2);
                    thisCategory.setBuildResources(true);
                    thisCategory.buildResources(db);
                    if (sub.indexOf("Eligibility") != -1) {
                      transType = 0;
                    } else if (sub.indexOf("Claim Status") != -1) {
                      transType = 1;
                    } else if (sub.indexOf("Claim Trans") != -1) {
                      transType = 2;
                    } else if (sub.indexOf("EOB Trans") != -1) {
                      transType = 3;
                    } else if (sub.indexOf("EFT Trans") != -1) {
                      transType = 4;
                    } else {
                      //default
                      transType = 4;
                    }
                    if (providerMap.get(compareDate) != null) {
                      //has this date in the hashmap already
                      ArrayList tempArrayList = (ArrayList) providerMap.get(compareDate);
                      int tempNum = ((Integer) tempArrayList.get(transType)).intValue();
                      ((ArrayList) providerMap.get(compareDate)).set(transType, new Integer(tempNum + 1));
                    } else {
                      //not in hashmap already
                      providerArray = new ArrayList();
                      for (int k = 0; k < 5; k++) {
                        providerArray.add(k, new Integer(0));
                      }
                      providerArray.add(transType, new Integer(((Integer) providerArray.get(transType)).intValue() + 1));
                      providerMap.put(((Object) compareDate), providerArray);
                    }
                    while (moreToProcess) {
                      for (int h = 0; h < 8; h++) {
                        subsub = (String) values.next();
                      }
                      if (subsub.indexOf("/") > -1) {
                        if (subsub.equals(compareDate)) {
                          //second consecutive date.  Increment value.
                          if (providerMap.get(compareDate) != null) {
                            //has this date in the hashmap already
                            ArrayList tempArrayList = (ArrayList) providerMap.get(compareDate);
                            int tempNum = ((Integer) tempArrayList.get(transType)).intValue();
                            ((ArrayList) providerMap.get(compareDate)).set(transType, new Integer(tempNum + 1));
                          } else {
                            //not in hashmap already
                            providerArray.add(transType, new Integer(((Integer) providerArray.get(transType)).intValue() + 1));
                            providerMap.put(((Object) compareDate), providerArray);
                          }
                        } else {
                          //new date, not the same as the previous
                          if (providerMap.get(subsub) != null) {
                            ArrayList tempArrayList = (ArrayList) providerMap.get(subsub);
                            int tempNum = ((Integer) tempArrayList.get(transType)).intValue();
                            ((ArrayList) providerMap.get(subsub)).set(transType, new Integer(tempNum + 1));
                            providerArray = ((ArrayList) providerMap.get(subsub));
                          } else {
                            providerMap.put(compareDate, providerArray);
                            providerArray = new ArrayList();
                            for (int k = 0; k < 5; k++) {
                              providerArray.add(k, new Integer(0));
                            }
                            providerArray.add(transType, new Integer(1));
                            providerMap.put(((Object) subsub), providerArray);
                          }
                          compareDate = subsub;
                        }
                      } else {
                        moreToProcess = false;
                      }
                    }
                  } else {
                    //Provider ID not found in CFS
                    if (!(notFoundIds.contains(t))) {
                      notFoundIds.add(t);
                    }
                    testBuffer.append("Skipping ID " + t + "- could not locate in Dark Horse CRM!\n");
                  }
                } else if (sub.indexOf("TOTAL TRANSACTIONS") > -1) {
                  break;
                }
              }
              //insert
              Iterator hashIterator = providerMap.keySet().iterator();
              while (hashIterator.hasNext()) {
                String tempKey = (String) hashIterator.next();
                ArrayList anotherList = (ArrayList) providerMap.get(tempKey);
                context.getRequest().setAttribute("cf" + ids.get(6), t);
                context.getRequest().setAttribute("cf" + ids.get(0), tempKey);
                for (int h = 0; h < 5; h++) {
                  context.getRequest().setAttribute("cf" + ids.get(h + 1), anotherList.get(h) + "");
                }
                thisCategory.setParameters(context);
                int resultCode = thisCategory.insert(db);
                if (resultCode > 0) {
                  insertCount++;
                }
                for (int z = 1; z < 6; z++) {
                  context.getRequest().setAttribute("cf" + ids.get(z), "");
                }
                if (orgTotalsMap.get(((Integer) thisMap.get(t))) != null) {
                  perDayTotalsMap = (HashMap) (orgTotalsMap.get(((Integer) thisMap.get(t))));
                  ArrayList tempList = null;
                  if (perDayTotalsMap.get(tempKey) != null) {
                    tempList = (ArrayList) perDayTotalsMap.get(tempKey);
                  } else {
                    tempList = new ArrayList();
                    for (int k = 0; k < 5; k++) {
                      tempList.add(k, new Integer(0));
                    }
                  }
                  for (int y = 0; y < 5; y++) {
                    int number = ((Integer) tempList.get(y)).intValue();
                    if (perDayTotalsMap.get(tempKey) != null) {
                      //testBuffer.append(((Integer)thisMap.get(t)) + ", " + new Integer( number + ((Integer)anotherList.get(y)).intValue() ) + "\n");
                      ((ArrayList) perDayTotalsMap.get(tempKey)).set(y, new Integer(number + ((Integer) anotherList.get(y)).intValue()));
                    } else {
                      //testBuffer.append(((Integer)thisMap.get(t)) + ", " + new Integer( number + ((Integer)anotherList.get(y)).intValue() ) + "\n");
                      tempList.set(y, new Integer(number + ((Integer) anotherList.get(y)).intValue()));
                      perDayTotalsMap.put(tempKey, tempList);
                    }
                  }
                  orgTotalsMap.put((Integer) thisMap.get(t), perDayTotalsMap);
                } else {
                  ArrayList tempList = new ArrayList();
                  perDayTotalsMap = new HashMap();
                  for (int y = 0; y < 5; y++) {
                    tempList.add(y, (Integer) anotherList.get(y));
                    //testBuffer.append(tempKey + ": " + y + " " + ((Integer)thisMap.get(t)) + ", " + anotherList.get(y) + "\n");
                  }
                  perDayTotalsMap.put((Object) tempKey, tempList);
                  orgTotalsMap.put(thisMap.get(t), perDayTotalsMap);
                }
                //testBuffer.append("[" + t + "] : " + "INSERTING: " + tempKey + ": " + anotherList.get(0) + ", " + anotherList.get(1) + "\n");
              }
              providerMap.clear();
              providerArray = new ArrayList();
            }
          }
        }
      }
      testBuffer.append(insertCount + " provider transaction records were successfully inserted.\n");
      //totals
      Iterator firstIterator = orgTotalsMap.keySet().iterator();
      while (firstIterator.hasNext()) {
        Integer tempKey = (Integer) firstIterator.next();
        HashMap secondMap = (HashMap) orgTotalsMap.get(tempKey);
        Iterator secondIterator = secondMap.keySet().iterator();
        while (secondIterator.hasNext()) {
          String anotherKey = (String) secondIterator.next();
          CustomFieldCategory thisCat = new CustomFieldCategory(db, OFFICE_TRANSACTION_CATEGORY);
          thisCat.setLinkModuleId(Constants.ACCOUNTS);
          thisCat.setLinkItemId(tempKey.intValue());
          thisCat.setIncludeEnabled(Constants.TRUE);
          thisCat.setIncludeScheduled(Constants.TRUE);
          thisCat.setEnteredBy(2);
          thisCat.setModifiedBy(2);
          thisCat.setBuildResources(true);
          thisCat.buildResources(db);
          context.getRequest().setAttribute("cf" + finalIds.get(0), anotherKey);
          ArrayList finalList = (ArrayList) secondMap.get(anotherKey);
          for (int h = 0; h < 5; h++) {
            context.getRequest().setAttribute("cf" + finalIds.get(h + 1), finalList.get(h) + "");
          }
          thisCat.setParameters(context);
          int resultCode = thisCat.insert(db);
          if (resultCode > 0) {
            totalsCount++;
          }
          for (int z = 0; z < 6; z++) {
            context.getRequest().setAttribute("cf" + finalIds.get(z), "");
          }
        }
      }
      testBuffer.append(totalsCount + " office transaction records (totals) were successfully inserted.\n\n");
      if (notFoundIds.size() > 0) {
        testBuffer.append("The following " + notFoundIds.size() + " provider IDs were not found in Dark Horse CRM:\n\n");
        for (int z = 0; z < notFoundIds.size(); z++) {
          testBuffer.append(notFoundIds.get(z) + "\n");
        }
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("FileString", testBuffer.toString());
    //return ("-none-");
    return ("ProcessDataOK");
  }
}

