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
package org.aspcfs.modules.industry.spirit.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.quotes.base.QuoteProduct;
import org.aspcfs.modules.quotes.base.QuoteProductList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    November 19, 2003
 *@version    $id:exp$
 */
public final class ProcessMaresa extends CFSModule {
  public static String DATA_SEPERATOR = "|";
  public static String ACTIVE = "C03";
  HashMap errors = new HashMap();


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public String executeCommandUpdateOpportunity(ActionContext context) throws SQLException {
    boolean recordInserted = false;
    int resultCount = -1;
    int userId = -1;
    int contactId = -1;
    ArrayList input = new ArrayList();
    StringBuffer log = new StringBuffer();
    String filePath = null;
    errors.clear();
    OpportunityComponent thisComponent = null;
    OpportunityHeader oppHeader = null;
    Connection db = null;
    ConnectionElement ce = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    try {
      //get a local database connection
      AuthenticationItem auth = new AuthenticationItem();
      ce = auth.getConnectionElement(context);
      db = this.getConnection(context, ce);

      filePath = this.getPath(context) + System.getProperty("file.separator") + ce.getDbName();
      //set auto commit to false to make ensure a Transaction
      db.setAutoCommit(false);

      //process the request
      String opportunityData = null;
      //OpportunityProductList products = new OpportunityProductList();
      QuoteProductList products = new QuoteProductList();
      BufferedReader br = context.getRequest().getReader();
      String line = null;
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Maresa-> Reading from request");
      }
      log.append("\tMaresa-> Reading from request\n");

      //load input into array
      while ((line = br.readLine()) != null) {
        input.add(line.trim());
      }

      //make sure there are atleast three lines
      if (input.size() < 3) {
        errors.put("InsufficientDataERROR", "FATAL: Less than 3 lines found in input");
        throw new Exception("InsufficientDataERROR");
      }

      //get the transaction id
      if (input.get(0) == null) {
        errors.put("TransactionIdERROR", "FATAL: Transaction Id does not exist");
        throw new Exception("TransactionIdERROR");
      }

      //get opportunity details
      if (input.get(1) != null) {
        opportunityData = (String) input.get(1);
      } else {
        errors.put("OpportunityDataERROR", "Opportunity data does not exist");
        throw new Exception("OpportunityDataERROR");
      }
      //get products
      //while ((line = br.readLine()) != null) {
      for (int prodIndex = 2; prodIndex < input.size(); prodIndex++) {
        String productData = (String) input.get(prodIndex);

        if (!"".equals(StringUtils.toString(productData))) {
          StringTokenizer tmp = new StringTokenizer(productData, DATA_SEPERATOR);
          //OpportunityProduct thisProduct = new OpportunityProduct();
          QuoteProduct thisProduct = new QuoteProduct();

          //get the product code mapping from the lookup table
          String codeDescription = tmp.nextToken();
          int productId = -1;
          pst = db.prepareStatement(
              "SELECT product_id " +
              "FROM product_catalog " +
              "WHERE sku = ? ");
          pst.setString(1, codeDescription);
          rs = pst.executeQuery();
          if (rs.next()) {
            productId = rs.getInt("product_id");
          } else {
            errors.put("ProductCodeERROR", "No mapping found for product description " + codeDescription);
            throw new Exception("ProductCodeERROR");
          }
          pst.close();
          rs.close();

          thisProduct.setProductId(productId);
          thisProduct.setQuantity(tmp.nextToken());
	      //ADD BY KAILASH JULY 13TH, 2005
          if ("00031".equals(codeDescription)){
			//deduct if the product is a promotion
            thisProduct.setPriceAmount(-1.0 * Double.parseDouble(tmp.nextToken()));
          } else{
            thisProduct.setPriceAmount(Double.parseDouble(tmp.nextToken()));
          }
         //END ADD
          thisProduct.setComment(tmp.nextToken());
          products.add(thisProduct);
        }
      }

      if (System.getProperty("DEBUG") != null) {
        System.out.println("Maresa-> Product Size " + products.size());
      }
      log.append("\tMaresa-> Product Size " + products.size() + "\n");

      if (products.size() > 0 && !"".equals(StringUtils.toString(opportunityData))) {
        //get opportunity details
        StringTokenizer tmp = new StringTokenizer(opportunityData, DATA_SEPERATOR);
        String description = tmp.nextToken();
        String cruiseDate = tmp.nextToken();
        String source = tmp.nextToken();
        String productValue = tmp.nextToken();
        String countOfPassengers = tmp.nextToken();
        String resNumber = tmp.nextToken();
        String resAction = tmp.nextToken();
        String status = tmp.nextToken();
        String payment = tmp.nextToken();
        String site = tmp.nextToken();
        String maresaContactId = tmp.nextToken();
        String eventType = tmp.nextToken();

        //get the stage code from the lookup table
        int siteCode = -1;
        pst = db.prepareStatement(
            "SELECT code " +
            "FROM lookup_site_id " +
            "WHERE lower(maresa_id) = lower(?) ");
        pst.setString(1, site);
        rs = pst.executeQuery();
        if (rs.next()) {
          siteCode = rs.getInt("code");
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Site Code: " + siteCode);
          }
          log.append("\tMaresa-> Site Code: " + siteCode + "\n");
        } else {
          errors.put("SiteCodeERROR", "No mapping found for site " + site);
          throw new Exception("SiteCodeERROR");
        }
        pst.close();
        rs.close();

        //Map maresa contact to CFS Contact
        pst = db.prepareStatement(
            "SELECT contact_id " +
            "FROM maresa_client " +
            "WHERE client_id = ? AND site_id = ? ");
        pst.setInt(1, Integer.parseInt(maresaContactId));
        pst.setInt(2, siteCode);
        rs = pst.executeQuery();
        if (rs.next()) {
          contactId = rs.getInt("contact_id");
        } else {
          errors.put("ContactIdERROR", "No Mapping for " + maresaContactId + " found in CFS");
          throw new Exception("ContactIdERROR");
        }
        pst.close();
        rs.close();

        //build the contact
        Contact thisContact = new Contact();
        thisContact.setBuildDetails(false);
        thisContact.setBuildTypes(false);
        thisContact.queryRecord(db, contactId);

        //Contact owner is used for all transactions in CFS
        userId = thisContact.getOwner();

        int oppId = -1;
        int componentId = -1;
        if ("1".equals(resAction)) {
          //existing reservation
          pst = db.prepareStatement(
              "SELECT om.header_id, om.component_id " +
              "FROM opportunity_header_maresa om " +
              "WHERE om.res_number = ? AND om.res_site = ? ");
          pst.setInt(1, Integer.parseInt(resNumber));
          pst.setInt(2, siteCode);
          rs = pst.executeQuery();
          if (rs.next()) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Maresa-> Opp HeaderId " + rs.getInt("header_id"));
            }
            oppId = rs.getInt("header_id");
            componentId = rs.getInt("component_id");
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Maresa-> Opp HeaderId " + oppId);
            }
            log.append("\tMaresa-> Opp HeaderId " + oppId + "\n");
          } else {
            errors.put("OpportunityUpdateERROR", "No mapping for reservation: " + resNumber + " and site: " + siteCode + " found in CFS");
            throw new Exception("OpportunityUpdateERROR");
          }
          rs.close();
          pst.close();

          //update details of the opportunity
          int i = 0;
          pst = db.prepareStatement(
              "UPDATE opportunity_header_maresa " +
              "SET res_status = ?, res_payments = ?, event_type = ?, modified = CURRENT_TIMESTAMP " +
              "WHERE res_number = ? ");

          pst.setString(++i, status);
          pst.setDouble(++i, Double.parseDouble(payment));
          pst.setString(++i, eventType);
          pst.setInt(++i, Integer.parseInt(resNumber));
          pst.execute();
          pst.close();
        } else if ("0".equals(resAction)) {
          //new reservation: use contactId and cruise date to get the opportunity
          pst = db.prepareStatement(
              "SELECT oh.opp_id, oc.id " +
              "FROM opportunity_header oh, opportunity_component oc " +
              "WHERE oh.contactlink = ? AND " +
              DatabaseUtils.castDateTimeToDate(db, "oc.closedate") + " = ? " +
              "AND oh.opp_id = oc.opp_id ");
          pst.setInt(1, contactId);
          pst.setDate(2, DateUtils.parseDateString(cruiseDate));
          rs = pst.executeQuery();
          if (rs.next()) {
            //update opportunity
            oppId = rs.getInt("opp_id");
            componentId = rs.getInt("id");
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Maresa-> Opp HeaderId " + oppId);
            }
            log.append("\tMaresa-> Opp HeaderId " + oppId + "\n");
          } else {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("Maresa-> Opportunity Not Found.. Creating new one");
            }
          }
          pst.close();
          rs.close();
        } else {
          errors.put("InvalidActionERROR", "Invalid Action : " + resAction);
          throw new Exception("InvalidActionERROR");
        }

        if (oppId > 0) {
          thisComponent = new OpportunityComponent(db, componentId);
          oppHeader = new OpportunityHeader(db, oppId);
        } else {
          oppHeader = new OpportunityHeader();
          oppHeader.setContactLink(contactId);
          oppHeader.setEnteredBy(userId);
          thisComponent = new OpportunityComponent();
        }

        //set header details
        oppHeader.setDescription(description);
        oppHeader.setModifiedBy(userId);
        //ADD BY KAILASH JULY 14TH, 2005
        oppHeader.setCustom1Integer(countOfPassengers);
        //END ADD

        //set component details
        thisComponent.setDescription(description);
        thisComponent.setType(source);
        thisComponent.setGuess(productValue);
        //CHANGED BY KAILASH JULY 14TH, 2005
        //thisComponent.setLow(countOfPassengers);
        //END CHANGE
        thisComponent.setModifiedBy(userId);

        //ADD BY KAILASH JULY 13TH, 2005
        java.util.Date tmpDate1 = DateUtils.parseDateString(cruiseDate);
        java.sql.Timestamp timestampValue1 = new java.sql.Timestamp(tmpDate1.getTime());
        thisComponent.setCloseDate(timestampValue1);
        //END ADD

        String stage = null;
        if (ACTIVE.equals(status)) {
          //assign probability as 80% if tentative else 100% if active
          if (!"".equals(StringUtils.toString(payment)) && Double.parseDouble(StringUtils.toString(payment)) > 0) {
            thisComponent.addIgnoredValidationField("closeProb");
            thisComponent.setCloseProb("100");
            thisComponent.setCloseIt(false);
            thisComponent.setOpenIt(true);
            //set stage to active
            stage = "Active";
          } else {
            thisComponent.setCloseProb("79");
            thisComponent.setCloseIt(false);
            thisComponent.setOpenIt(true);
            //set stage to tentative
            stage = "Tentative";
          }
        } else {
          //cancelled opportunity, close it
          thisComponent.setCloseIt(true);
          //set stage to cancelled
          stage = "Cancelled";
        }

        //get the stage code from the lookup table
        int stageCode = -1;
        pst = db.prepareStatement(
            "SELECT code " +
            "FROM lookup_stage " +
            "WHERE lower(description) = lower(?) ");
        pst.setString(1, stage);
        rs = pst.executeQuery();
        if (rs.next()) {
          stageCode = rs.getInt("code");
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Stage: " + stage + ";Code: " + stageCode);
          }
          log.append("\tMaresa-> Stage: " + stage + "; Mapped CFS Code: " + stageCode + "\n");
        } else {
          errors.put("StageCodeERROR", "No mapping found for stage " + stage);
          throw new Exception("StageCodeERROR");
        }
        pst.close();
        rs.close();
        thisComponent.setStage(stageCode);
        if (oppId > 0) {
          //update header
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Updating Header");
          }
          log.append("\tMaresa-> Updating Header" + "\n");

          resultCount = oppHeader.update(db);
          if (resultCount == -1) {
            errors.put("OpportunityHeaderUpdateERROR", "Header " + oppHeader.getDescription() + " could not be updated");
            throw new Exception("OpportunityHeaderUpdateERROR");
          }

          resultCount = -1;
          //update component
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Updating Component");
          }
          log.append("\tMaresa-> Updating Component" + "\n");

          resultCount = thisComponent.update(db);
          if (resultCount == -1) {
            errors.put("OpportunityComponentUpdateERROR", "Component " + thisComponent.getDescription() + " could not be updated");
            throw new Exception("OpportunityComponentUpdateERROR");
          }

          //delete all products
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Deleting all products");
          }
          log.append("\tMaresa-> Deleting all products" + "\n");

          /* COMMENTED FOR UPGRADE KAILASH 06/21/05
          //OpportunityProductList.deleteAll(db, thisComponent.getId());
          */
          QuoteList quoteList = new QuoteList();
          quoteList.setHeaderId(thisComponent.getHeaderId());
          quoteList.setBuildResources(true);
          quoteList.buildList(db);
          Iterator quoteIterator = quoteList.iterator();
          if (quoteIterator.hasNext()){
            log.append("\tMaresa-> Fetching old quote products that need to be deleted" + "\n");
            while (quoteIterator.hasNext()){
              Quote quote = (Quote)quoteIterator.next();
              quote.getProductList().delete(db);
            }
          }
        } else {
          //add opp
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Inserting Header");
          }
          log.append("\tMaresa-> Inserting Header" + "\n");

          recordInserted = oppHeader.insert(db);
          if (!recordInserted) {
            errors.put("OpportunityHeaderInsertERROR", "Header " + oppHeader.getDescription() + " could not be inserted");
            throw new Exception("OpportunityHeaderInsertERROR");
          }
          thisComponent.setHeaderId(oppHeader.getId());
          thisComponent.setEnteredBy(userId);
          java.util.Date tmpDate = DateUtils.parseDateString(cruiseDate);
          java.sql.Timestamp timestampValue = new java.sql.Timestamp(tmpDate.getTime());
          thisComponent.setCloseDate(timestampValue);
          thisComponent.setOwner(userId);
          recordInserted = false;
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Inserting Component");
          }
          log.append("\tMaresa-> Inserting Component" + "\n");

          recordInserted = thisComponent.insert(db);
          if (!recordInserted) {
            errors.put("OpportunityComponentInsertERROR", "Component " + thisComponent.getDescription() + " could not be inserted");
            throw new Exception("OpportunityComponentInsertERROR");
          } else {
            invalidateUserData(context, userId);
          }
        }

        //insert maresa opportunity details
        if ("0".equals(resAction)) {
          //make an entry in the opportunity maresa table
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Inserting new reservation");
          }
          log.append("\tMaresa-> Inserting new reservation" + "\n");

          int i = 0;
          pst = db.prepareStatement(
              "INSERT INTO opportunity_header_maresa " +
              "(res_number, res_status, res_payments, res_site, res_contact_id, event_type, header_id, component_id) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
          pst.setInt(++i, Integer.parseInt(resNumber));
          pst.setString(++i, status);
          pst.setDouble(++i, Double.parseDouble(payment));
          pst.setInt(++i, siteCode);
          pst.setInt(++i, contactId);
          pst.setString(++i, eventType);
          pst.setInt(++i, oppHeader.getId());
          pst.setInt(++i, thisComponent.getId());
          pst.execute();
          pst.close();

          //lock the opportunity
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Maresa-> Locking in the opportunity");
          }
          log.append("\tMaresa-> Locking in the opportunity" + "\n");

          pst = db.prepareStatement(
              "UPDATE opportunity_header " +
              "SET \"lock\" = ? WHERE opp_id = ? ");
          pst.setBoolean(1, true);
          pst.setInt(2, oppHeader.getId());
          pst.execute();
          pst.close();
        }

        //add products
        if (System.getProperty("DEBUG") != null) {
          System.out.println("Maresa-> Adding products");
        }
        log.append("\tMaresa-> Adding products" + "\n");
        
        //Fetch quote for this opportunity
        QuoteList quoteList = new QuoteList();
        quoteList.setHeaderId(oppHeader.getId());
        quoteList.buildList(db);
        if ( quoteList.size() == 0){
            //errors.put("quoteERROR", "No Quote for " + oppHeader.getDescription());
            //throw new Exception("quoteERROR");
            Quote quote = new Quote();
            quote.createNewGroup(db);
            quote.setHeaderId(oppHeader.getId());
            quote.setContactId(oppHeader.getContactLink());
            Contact tmpContact = new Contact(db, oppHeader.getContactLink());
            quote.setOrgId(tmpContact.getOrgId());
            quote.setShortDescription("Reservation from Maresa");
            quote.setEnteredBy(0);
            quote.setModifiedBy(0);
            boolean quoteInserted = quote.insert(db);
	    
            if (quoteInserted){
              Iterator i = products.iterator();
              int quoteId = quote.getId();
              while (i.hasNext()) {
                QuoteProduct thisProduct = (QuoteProduct) i.next();
                thisProduct.setQuoteId(quoteId);
                recordInserted = false;
                recordInserted = thisProduct.insert(db);
                if (!recordInserted) {
                  errors.put("ProductAddERROR", "Product " + thisProduct.getComment() + " could not be added");
                  throw new Exception("ProductAddERROR");
                }
              }
            }else{
              errors.put("QuoteAddError", " New quote could not be created");
            }
        } else{
          Quote tmpQuote = (Quote)quoteList.get(0);
          Iterator i = products.iterator();
          while (i.hasNext()) {
            QuoteProduct thisProduct = (QuoteProduct) i.next();
            thisProduct.setQuoteId(tmpQuote.getId());
            recordInserted = false;
            recordInserted = thisProduct.insert(db);
            if (!recordInserted) {
              errors.put("ProductAddERROR", "Product " + thisProduct.getComment() + " could not be added");
              throw new Exception("ProductAddERROR");
            }
          }
        }

        //assumed that Maresa will always send in a opportunity with required products
        if (thisComponent.getStatus() == OpportunityComponent.INCOMPLETE) {
          thisComponent.changeStatus(db, OpportunityComponent.COMPLETE);
        }

        //commit the transaction
        db.commit();

        //create success log
        String logData = createLog(input);
        writeLog(filePath, logData);
      }
    } catch (Exception e) {
      String tmp = createExceptionLog(e, input, log);
      writeLog(filePath, tmp);
      if (db != null) {
        db.rollback();
      }
    } finally {
      if (db != null) {
        db.setAutoCommit(true);
      }

      //release all resources
      if (rs != null) {
        rs.close();
      }
      if (pst != null) {
        pst.close();
      }
      this.freeConnection(context, db);
    }
    return "-none-";
  }


  /**
   *  Description of the Method
   *
   *@param  e      Description of the Parameter
   *@param  input  Description of the Parameter
   *@param  log    Description of the Parameter
   *@return        Description of the Return Value
   */
  private String createExceptionLog(Exception e, ArrayList input, StringBuffer log) {
    StringBuffer error = new StringBuffer();
    error.append(addLogHeader(input));
    error.append("Status: Failure\n");
    //File exceptionFile = new File("MaresaOpportunityError.txt");
    error.append("Errors:\n");
    Iterator i = errors.keySet().iterator();
    while (i.hasNext()) {
      String errorKey = (String) i.next();
      String errorMsg = (String) errors.get(errorKey);
      error.append("\t" + errorKey + ": " + errorMsg);
      error.append("\n");
    }
    error.append("Actual Error:\n\t" + e.toString() + "\n");
    error.append("LOG:\n " + log.toString());
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TRANSACTION FAILED: \n" + error.toString());
    }
    return error.toString();
  }


  /**
   *  Adds a feature to the LogHeader attribute of the ProcessMaresa object
   *
   *@param  input  The feature to be added to the LogHeader attribute
   *@return        Description of the Return Value
   */
  private String addLogHeader(ArrayList input) {
    StringBuffer header = new StringBuffer();
    header.append("====================================================================\n");
    if (input != null && input.size() > 0) {
      header.append("TransactionID: " + (String) input.get(0) + "\n");
      header.append("Time: " + Calendar.getInstance().getTime() + "\n");
      header.append("Input:\n");
      Iterator i = input.iterator();
      while (i.hasNext()) {
        header.append("\t" + (String) i.next() + "\n");
      }
    }
    return header.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  input  Description of the Parameter
   *@return        Description of the Return Value
   */
  private String createLog(ArrayList input) {
    StringBuffer log = new StringBuffer();
    log.append(addLogHeader(input));
    log.append("Status: Success\n");
    if (System.getProperty("DEBUG") != null) {
      System.out.println("TRANSACTION SUCCESSFUL: \n" + log.toString());
    }
    return log.toString();
  }


  /**
   *  Writes to the log
   *
   *@param  log       Description of the Parameter
   *@param  filePath  Description of the Parameter
   */
  private void writeLog(String filePath, String log) {
    try {
      Calendar cal = Calendar.getInstance();
      String today = cal.get(Calendar.YEAR) + "" + (cal.get(Calendar.MONTH) + 1) + "" + cal.get(Calendar.DAY_OF_MONTH);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Maresa-> " + filePath);
      }
      String fileName = today + "maresalog.txt";
      BufferedWriter out = new BufferedWriter(new FileWriter((filePath + System.getProperty("file.separator") + fileName), true));
      out.write(log);
      out.close();
    } catch (Exception e) {
      System.out.println("FATAL: Error writing to log");
    }
  }
}


