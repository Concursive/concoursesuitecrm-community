//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.healthcare.edit.base;

import java.sql.*;
import java.util.*;
import java.text.DateFormat;
import java.text.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.framework.servlets.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.controller.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.*;
import javax.servlet.http.*;

/**
 *  An object to help with the management of EDIT records when transferring from
 *  the staging server to CFS
 *
 *@author     chris
 *@created    February 6, 2003
 *@version    $Id$
 */
public class FolderInsertRecord extends GenericBean {
  private String id = null;
  private String payorId = null;
  private String providerId = null;
  private int eligibility = 0;
  private int claimStatus = 0;
  //278 Requests
  private int referral = 0;
  //824 Requests
  private int advice = 0;
  private int dental = 0;
  private int professional = 0;
  private int institutional = 0;
  private int claimRemittance = 0;


  /**
   *  Constructor for the FolderInsertRecord object
   */

  public FolderInsertRecord() { }


  /**
   *  Constructor for the FolderInsertRecord object
   *
   *@param  transId  Description of the Parameter
   *@param  payor    Description of the Parameter
   */
  public FolderInsertRecord(String transId, String payor) {
    id = transId;
    payorId = payor;
  }


  /**
   *  Constructor for the FolderInsertRecord object
   *
   *@param  transId   Description of the Parameter
   *@param  payor     Description of the Parameter
   *@param  provider  Description of the Parameter
   */
  public FolderInsertRecord(String transId, String payor, String provider) {
    id = transId;
    payorId = payor;
    providerId = provider;
  }


  /**
   *  Gets the id attribute of the FolderInsertRecord object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the FolderInsertRecord object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   *  Gets the payorId attribute of the FolderInsertRecord object
   *
   *@return    The payorId value
   */
  public String getPayorId() {
    return payorId;
  }


  /**
   *  Sets the payorId attribute of the FolderInsertRecord object
   *
   *@param  payorId  The new payorId value
   */
  public void setPayorId(String payorId) {
    this.payorId = payorId;
  }


  /**
   *  Increment the counts based on what transactions this TransactionRecord has
   *
   *@param  tr  Description of the Parameter
   *@return     Description of the Return Value
   */
  public boolean process(TransactionRecord tr) {
    if (tr.getType().equals("270")) {
      this.setEligibility(this.getEligibility() + 1);
    } else if (tr.getType().equals("276")) {
      this.setClaimStatus(this.getClaimStatus() + 1);
    } else if (tr.getType().equals("278REQ")) {
      this.setReferral(this.getReferral() + 1);
    } else if (tr.getType().equals("824")) {
      this.setAdvice(this.getAdvice() + 1);
    } else if (tr.getType().equals("837D")) {
      this.setDental(this.getDental() + 1);
    } else if (tr.getType().equals("837P")) {
      this.setProfessional(this.getProfessional() + 1);
    } else if (tr.getType().equals("837I")) {
      this.setInstitutional(this.getInstitutional() + 1);
    } else if (tr.getType().equals("835")) {
      this.setClaimRemittance(this.getClaimRemittance() + 1);
    } else {
      return false;
    }

    return true;
  }


  /**
   *  Gets the eligibility attribute of the FolderInsertRecord object
   *
   *@return    The eligibility value
   */
  public int getEligibility() {
    return eligibility;
  }


  /**
   *  Sets the eligibility attribute of the FolderInsertRecord object
   *
   *@param  eligibility  The new eligibility value
   */
  public void setEligibility(int eligibility) {
    this.eligibility = eligibility;
  }


  /**
   *  Sets the eligibility attribute of the FolderInsertRecord object
   *
   *@param  eligibility  The new eligibility value
   */
  public void setEligibility(String eligibility) {
    this.eligibility = Integer.parseInt(eligibility);
  }


  /**
   *  Gets the claimStatus attribute of the FolderInsertRecord object
   *
   *@return    The claimStatus value
   */
  public int getClaimStatus() {
    return claimStatus;
  }


  /**
   *  Sets the claimStatus attribute of the FolderInsertRecord object
   *
   *@param  claimStatus  The new claimStatus value
   */
  public void setClaimStatus(int claimStatus) {
    this.claimStatus = claimStatus;
  }


  /**
   *  Sets the claimStatus attribute of the FolderInsertRecord object
   *
   *@param  claimStatus  The new claimStatus value
   */
  public void setClaimStatus(String claimStatus) {
    this.claimStatus = Integer.parseInt(claimStatus);
  }


  /**
   *  Gets the referral attribute of the FolderInsertRecord object
   *
   *@return    The referral value
   */
  public int getReferral() {
    return referral;
  }


  /**
   *  Sets the referral attribute of the FolderInsertRecord object
   *
   *@param  referral  The new referral value
   */
  public void setReferral(int referral) {
    this.referral = referral;
  }


  /**
   *  Sets the referral attribute of the FolderInsertRecord object
   *
   *@param  referral  The new referral value
   */
  public void setReferral(String referral) {
    this.referral = Integer.parseInt(referral);
  }


  /**
   *  Gets the providerId attribute of the FolderInsertRecord object
   *
   *@return    The providerId value
   */
  public String getProviderId() {
    return providerId;
  }


  /**
   *  Sets the providerId attribute of the FolderInsertRecord object
   *
   *@param  providerId  The new providerId value
   */
  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }


  /**
   *  Gets the dental attribute of the FolderInsertRecord object
   *
   *@return    The dental value
   */
  public int getDental() {
    return dental;
  }


  /**
   *  Sets the dental attribute of the FolderInsertRecord object
   *
   *@param  dental  The new dental value
   */
  public void setDental(int dental) {
    this.dental = dental;
  }


  /**
   *  Sets the dental attribute of the FolderInsertRecord object
   *
   *@param  dental  The new dental value
   */
  public void setDental(String dental) {
    this.dental = Integer.parseInt(dental);
  }


  /**
   *  Gets the professional attribute of the FolderInsertRecord object
   *
   *@return    The professional value
   */
  public int getProfessional() {
    return professional;
  }


  /**
   *  Sets the professional attribute of the FolderInsertRecord object
   *
   *@param  professional  The new professional value
   */
  public void setProfessional(int professional) {
    this.professional = professional;
  }


  /**
   *  Sets the professional attribute of the FolderInsertRecord object
   *
   *@param  professional  The new professional value
   */
  public void setProfessional(String professional) {
    this.professional = Integer.parseInt(professional);
  }


  /**
   *  Gets the institutional attribute of the FolderInsertRecord object
   *
   *@return    The institutional value
   */
  public int getInstitutional() {
    return institutional;
  }


  /**
   *  Sets the institutional attribute of the FolderInsertRecord object
   *
   *@param  institutional  The new institutional value
   */
  public void setInstitutional(int institutional) {
    this.institutional = institutional;
  }


  /**
   *  Sets the institutional attribute of the FolderInsertRecord object
   *
   *@param  institutional  The new institutional value
   */
  public void setInstitutional(String institutional) {
    this.institutional = Integer.parseInt(institutional);
  }


  /**
   *  Gets the claimRemittance attribute of the FolderInsertRecord object
   *
   *@return    The claimRemittance value
   */
  public int getClaimRemittance() {
    return claimRemittance;
  }


  /**
   *  Sets the claimRemittance attribute of the FolderInsertRecord object
   *
   *@param  claimRemittance  The new claimRemittance value
   */
  public void setClaimRemittance(int claimRemittance) {
    this.claimRemittance = claimRemittance;
  }


  /**
   *  Sets the claimRemittance attribute of the FolderInsertRecord object
   *
   *@param  claimRemittance  The new claimRemittance value
   */
  public void setClaimRemittance(String claimRemittance) {
    this.claimRemittance = Integer.parseInt(claimRemittance);
  }


  /**
   *  Gets the advice attribute of the FolderInsertRecord object
   *
   *@return    The advice value
   */
  public int getAdvice() {
    return advice;
  }


  /**
   *  Sets the advice attribute of the FolderInsertRecord object
   *
   *@param  advice  The new advice value
   */
  public void setAdvice(int advice) {
    this.advice = advice;
  }


  /**
   *  Sets the advice attribute of the FolderInsertRecord object
   *
   *@param  advice  The new advice value
   */
  public void setAdvice(String advice) {
    this.advice = Integer.parseInt(advice);
  }


  /**
   *  Update the totals when an ArrayList of previous MTD values are passed in
   *
   *@param  oldVals  Description of the Parameter
   */
  public void updateTotals(ArrayList oldVals) {
    eligibility = this.getEligibility() + Integer.parseInt((String) oldVals.get(0));
    claimStatus = this.getClaimStatus() + Integer.parseInt((String) oldVals.get(1));
    referral = this.getReferral() + Integer.parseInt((String) oldVals.get(2));
    advice = this.getAdvice() + Integer.parseInt((String) oldVals.get(3));
    dental = this.getDental() + Integer.parseInt((String) oldVals.get(4));
    professional = this.getProfessional() + Integer.parseInt((String) oldVals.get(5));
    institutional = this.getInstitutional() + Integer.parseInt((String) oldVals.get(6));
    claimRemittance = this.getClaimRemittance() + Integer.parseInt((String) oldVals.get(7));
  }
}

