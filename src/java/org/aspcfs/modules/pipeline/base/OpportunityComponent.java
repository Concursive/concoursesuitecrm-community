//Copyright 2002-2003 Dark Horse Ventures

package org.aspcfs.modules.pipeline.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.controller.SystemStatus;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.contacts.base.*;

/**
 *  An OpportunityComponent makes up 1 or more elements of an Opportunity. An
 *  OpportunityHeader is a top level description for all of the components that
 *  make up the Opportunity.
 *
 *@author     chris
 *@created    December, 2002
 *@version    $Id: OpportunityComponent.java,v 1.5 2002/12/24 17:02:33 akhi_m
 *      Exp $
 */

public class OpportunityComponent extends GenericBean {
  protected int id = -1;
  protected int oppId = -1;
  protected int owner = -1;
  protected String description = null;
  protected java.sql.Date closeDate = null;
  protected double closeProb = 0;
  protected double terms = 0;
  protected String units = null;
  protected double low = 0;
  protected double guess = 0;
  protected double high = 0;
  protected int stage = -1;
  protected String stageName = null;
  protected java.sql.Date stageDate = null;
  protected double commission = 0;
  protected String type = null;
  protected java.sql.Date alertDate = null;
  protected String alertText = null;
  protected String notes = null;
  protected java.sql.Timestamp entered = null;
  protected java.sql.Timestamp modified = null;
  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected boolean enabled = true;

  protected boolean stageChange = false;
  protected boolean closeIt = false;
  protected boolean openIt = false;
  protected String closed = null;
  protected String ownerName = null;
  protected String enteredByName = null;
  protected String modifiedByName = null;
  protected String accountName = null;

  protected boolean hasEnabledOwnerAccount = true;
  protected ArrayList typeList = null;
  protected LookupList types = new LookupList();


  /**
   *  Constructor for the OpportunityComponent object
   */
  public OpportunityComponent() { }


  /**
   *  Constructor for the OpportunityComponent object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public OpportunityComponent(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the OpportunityComponent object
   *
   *@param  db                Description of Parameter
   *@param  id                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public OpportunityComponent(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the OpportunityComponent object
   *
   *@param  db                Description of Parameter
   *@param  id                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public OpportunityComponent(Connection db, String id) throws SQLException {
    queryRecord(db, Integer.parseInt(id));
  }


  /**
   *  Sets the EnteredByName attribute of the OpportunityComponent object
   *
   *@param  enteredByName  The new EnteredByName value
   */
  public void setEnteredByName(String enteredByName) {
    this.enteredByName = enteredByName;
  }


  /**
   *  Sets the TypeList attribute of the OpportunityComponent object
   *
   *@param  typeList  The new TypeList value
   */
  public void setTypeList(ArrayList typeList) {
    this.typeList = typeList;
  }


  /**
   *  Sets the TypeList attribute of the OpportunityComponent object
   *
   *@param  criteriaString  The new TypeList value
   */
  public void setTypeList(String[] criteriaString) {
    if (criteriaString != null) {
      String[] params = criteriaString;
      typeList = new ArrayList(Arrays.asList(params));
    } else {
      typeList = new ArrayList();
    }
    this.typeList = typeList;
  }


  /**
   *  Sets the OppId attribute of the OpportunityComponent object
   *
   *@param  oppId  The new OppId value
   */
  public void setOppId(int oppId) {
    this.oppId = oppId;
  }


  /**
   *  Sets the OppId attribute of the OpportunityComponent object
   *
   *@param  oppId  The new OppId value
   */
  public void setOppId(String oppId) {
    this.oppId = Integer.parseInt(oppId);
  }


  /**
   *  Sets the alertText attribute of the Opportunity object
   *
   *@param  alertText  The new alertText value
   */
  public void setAlertText(String alertText) {
    this.alertText = alertText;
  }


  /**
   *  Sets the HasEnabledOwnerAccount attribute of the OpportunityComponent
   *  object
   *
   *@param  hasEnabledOwnerAccount  The new HasEnabledOwnerAccount value
   */
  public void setHasEnabledOwnerAccount(boolean hasEnabledOwnerAccount) {
    this.hasEnabledOwnerAccount = hasEnabledOwnerAccount;
  }


  /**
   *  Sets the OpenIt attribute of the Opportunity object
   *
   *@param  openIt  The new OpenIt value
   *@since
   */
  public void setOpenIt(boolean openIt) {
    this.openIt = openIt;
  }


  /**
   *  Sets the Types attribute of the OpportunityComponent object
   *
   *@param  types  The new Types value
   */
  public void setTypes(LookupList types) {
    this.types = types;
  }


  /**
   *  Sets the alertDate attribute of the Opportunity object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(java.sql.Date tmp) {
    this.alertDate = tmp;
  }


  /**
   *  Sets the alertDate attribute of the Opportunity object
   *
   *@param  tmp  The new alertDate value
   */
  public void setAlertDate(String tmp) {
    this.alertDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the Notes attribute of the OpportunityComponent object
   *
   *@param  notes  The new Notes value
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }


  /**
   *  Sets the closeDate attribute of the Opportunity object
   *
   *@param  tmp  The new closeDate value
   */
  public void setCloseDate(java.sql.Date tmp) {
    this.closeDate = tmp;
  }


  /**
   *  Sets the stageDate attribute of the Opportunity object
   *
   *@param  tmp  The new stageDate value
   */
  public void setStageDate(java.sql.Date tmp) {
    this.stageDate = tmp;
  }


  /**
   *  Sets the enabled attribute of the Opportunity object
   *
   *@param  enabled  The new enabled value
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }


  /**
   *  Sets the enabled attribute of the Opportunity object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the closeDate attribute of the Opportunity object
   *
   *@param  tmp  The new closeDate value
   */
  public void setCloseDate(String tmp) {
    this.closeDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the stageDate attribute of the Opportunity object
   *
   *@param  tmp  The new stageDate value
   */
  public void setStageDate(String tmp) {
    this.stageDate = DateUtils.parseDateString(tmp);
  }


  /**
   *  Sets the Entered attribute of the Opportunity object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the Modified attribute of the Opportunity object
   *
   *@param  tmp  The new Modified value
   *@since
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the entered attribute of the Opportunity object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the modified attribute of the Opportunity object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Sets the Owner attribute of the Opportunity object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Sets the StageChange attribute of the Opportunity object
   *
   *@param  stageChange  The new StageChange value
   *@since
   */
  public void setStageChange(boolean stageChange) {
    this.stageChange = stageChange;
  }


  /**
   *  Sets the Closed attribute of the Opportunity object
   *
   *@param  closed  The new Closed value
   *@since
   */
  public void setClosed(String closed) {
    this.closed = closed;
  }


  /**
   *  Sets the Owner attribute of the Opportunity object
   *
   *@param  owner  The new Owner value
   *@since
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the Id attribute of the Opportunity object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the Opportunity object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the StageName attribute of the Opportunity object
   *
   *@param  stageName  The new StageName value
   *@since
   */
  public void setStageName(String stageName) {
    this.stageName = stageName;
  }


  /**
   *  Sets the Terms attribute of the Opportunity object
   *
   *@param  terms  The new Terms value
   *@since
   */
  public void setTerms(String terms) {
    try {
      this.terms = Double.parseDouble(terms);
    } catch (NumberFormatException ne) {
      errors.put("termsError", terms + " is invalid input for this field");
    }

  }


  /**
   *  Sets the ModifiedBy attribute of the Opportunity object
   *
   *@param  modifiedBy  The new ModifiedBy value
   *@since
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   *  Sets the modifiedBy attribute of the Opportunity object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the EnteredBy attribute of the Opportunity object
   *
   *@param  enteredBy  The new EnteredBy value
   *@since
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   *  Sets the enteredBy attribute of the Opportunity object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the OwnerName attribute of the Opportunity object
   *
   *@param  ownerName  The new OwnerName value
   *@since
   */
  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }


  /**
   *  Sets the Description attribute of the Opportunity object
   *
   *@param  description  The new Description value
   *@since
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   *  Sets the Low attribute of the Opportunity object
   *
   *@param  low  The new Low value
   *@since
   */
  public void setLow(String low) {
    low = replace(low, ",", "");
    low = replace(low, "$", "");

    this.low = Double.parseDouble(low);
  }


  /**
   *  Sets the Guess attribute of the Opportunity object
   *
   *@param  guess  The new Guess value
   *@since
   */
  public void setGuess(String guess) {
    guess = replace(guess, ",", "");
    guess = replace(guess, "$", "");

    try {
      this.guess = Double.parseDouble(guess);
    } catch (NumberFormatException ne) {
      errors.put("guessError", guess + " is invalid input for this field");
    }
  }


  /**
   *  Sets the High attribute of the Opportunity object
   *
   *@param  high  The new High value
   *@since
   */
  public void setHigh(String high) {
    high = replace(high, ",", "");
    high = replace(high, "$", "");

    this.high = Double.parseDouble(high);
  }


  /**
   *  Sets the ModifiedByName attribute of the Opportunity object
   *
   *@param  modifiedByName  The new ModifiedByName value
   *@since
   */
  public void setModifiedByName(String modifiedByName) {
    this.modifiedByName = modifiedByName;
  }


  /**
   *  Sets the Type attribute of the Opportunity object
   *
   *@param  type  The new Type value
   *@since
   */
  public void setType(String type) {
    this.type = type;
  }


  /**
   *  Sets the Units attribute of the Opportunity object
   *
   *@param  units  The new Units value
   *@since
   */
  public void setUnits(String units) {
    this.units = units;
  }


  /**
   *  Sets the CloseProb attribute of the Opportunity object
   *
   *@param  closeProb  The new CloseProb value
   *@since
   */
  public void setCloseProb(String closeProb) {
    if (closeProb != null && closeProb.endsWith("%")) {
      closeProb = closeProb.substring(0, closeProb.length() - 1);
    }

    try {
      this.closeProb = ((Double.parseDouble(closeProb)) / 100);
    } catch (NumberFormatException ne) {
      errors.put("closeProbError", closeProb + " is invalid input for this field");
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity-> Close prob: " + closeProb);
    }
  }


  /**
   *  Sets the Commission attribute of the Opportunity object
   *
   *@param  commission  The new Commission value
   *@since
   */
  public void setCommission(String commission) {
    if (commission != null && commission.endsWith("%")) {
      commission = commission.substring(0, commission.length() - 1);
    }
    this.commission = ((Double.parseDouble(commission)) / 100);
  }


  /**
   *  Sets the Stage attribute of the Opportunity object
   *
   *@param  stage  The new Stage value
   *@since
   */
  public void setStage(String stage) {
    this.stage = Integer.parseInt(stage);
  }


  /**
   *  Sets the CloseIt attribute of the Opportunity object
   *
   *@param  closeIt  The new CloseIt value
   *@since
   */
  public void setCloseIt(boolean closeIt) {
    this.closeIt = closeIt;
  }


  /**
   *  Sets the closeNow attribute of the Opportunity object
   *
   *@param  tmp  The new closeNow value
   */
  public void setCloseNow(String tmp) {
    this.closeIt = ("ON".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Sets the accountName attribute of the OpportunityComponent object
   *
   *@param  accountName  The new accountName value
   */
  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }


  /**
   *  Gets the accountName attribute of the OpportunityComponent object
   *
   *@return    The accountName value
   */
  public String getAccountName() {
    return accountName;
  }


  /**
   *  Gets the TypeList attribute of the OpportunityComponent object
   *
   *@return    The TypeList value
   */
  public ArrayList getTypeList() {
    return typeList;
  }


  /**
   *  Gets the alertText attribute of the Opportunity object
   *
   *@return    The alertText value
   */
  public String getAlertText() {
    return alertText;
  }


  /**
   *  Gets the OppId attribute of the OpportunityComponent object
   *
   *@return        The OppId value
   *@deprecated    Too confusing, an opportunity header is the parent now
   */
  public int getOppId() {
    return oppId;
  }


  /**
   *  Gets the headerId attribute of the OpportunityComponent object
   *
   *@return    The headerId value
   */
  public int getHeaderId() {
    return oppId;
  }


  /**
   *  Gets the HasEnabledOwnerAccount attribute of the OpportunityComponent
   *  object
   *
   *@return    The HasEnabledOwnerAccount value
   */
  public boolean getHasEnabledOwnerAccount() {
    return hasEnabledOwnerAccount;
  }


  /**
   *  Gets the Types attribute of the OpportunityComponent object
   *
   *@return    The Types value
   */
  public LookupList getTypes() {
    return types;
  }


  /**
   *  Gets the Notes attribute of the OpportunityComponent object
   *
   *@return    The Notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the alertDate attribute of the Opportunity object
   *
   *@return    The alertDate value
   */
  public java.sql.Date getAlertDate() {
    return alertDate;
  }


  /**
   *  Gets the closeDate attribute of the Opportunity object
   *
   *@return    The closeDate value
   */
  public java.sql.Date getCloseDate() {
    return closeDate;
  }


  /**
   *  Gets the stageDate attribute of the Opportunity object
   *
   *@return    The stageDate value
   */
  public java.sql.Date getStageDate() {
    return stageDate;
  }


  /**
   *  Gets the alertDateString attribute of the Opportunity object
   *
   *@return    The alertDateString value
   */
  public String getAlertDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the alertDateStringLongYear attribute of the Opportunity object
   *
   *@return    The alertDateStringLongYear value
   */
  public String getAlertDateStringLongYear() {
    String tmp = "";
    try {
      SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG);
      formatter.applyPattern("M/d/yyyy");
      return formatter.format(alertDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the closeDateString attribute of the Opportunity object
   *
   *@return    The closeDateString value
   */
  public String getCloseDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(closeDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the stageDateString attribute of the Opportunity object
   *
   *@return    The stageDateString value
   */
  public String getStageDateString() {
    String tmp = "";
    try {
      return DateFormat.getDateInstance(3).format(stageDate);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the OpenIt attribute of the Opportunity object
   *
   *@return    The OpenIt value
   *@since
   */
  public boolean getOpenIt() {
    return openIt;
  }


  /**
   *  Gets the Entered attribute of the Opportunity object
   *
   *@return    The Entered value
   *@since
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Modified attribute of the Opportunity object
   *
   *@return    The Modified value
   *@since
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the ModifiedString attribute of the Opportunity object
   *
   *@return    The ModifiedString value
   *@since
   */
  public String getModifiedString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(modified);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the EnteredString attribute of the Opportunity object
   *
   *@return    The EnteredString value
   *@since
   */
  public String getEnteredString() {
    String tmp = "";
    try {
      return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG).format(entered);
    } catch (NullPointerException e) {
    }
    return tmp;
  }


  /**
   *  Gets the Closed attribute of the Opportunity object
   *
   *@return    The Closed value
   *@since
   */
  public String getClosed() {
    return closed;
  }


  /**
   *  Gets the StageChange attribute of the Opportunity object
   *
   *@return    The StageChange value
   *@since
   */
  public boolean getStageChange() {
    return stageChange;
  }


  /**
   *  Gets the ModifiedByName attribute of the Opportunity object
   *
   *@return    The ModifiedByName value
   *@since
   */
  public String getModifiedByName() {
    return modifiedByName;
  }


  /**
   *  Gets the EnteredByName attribute of the Opportunity object
   *
   *@return    The EnteredByName value
   *@since
   */
  public String getEnteredByName() {
    return enteredByName;
  }


  /**
   *  Gets the OwnerName attribute of the Opportunity object
   *
   *@return    The OwnerName value
   *@since
   */
  public String getOwnerName() {
    return ownerName;
  }


  /**
   *  Gets the Units attribute of the Opportunity object
   *
   *@return    The Units value
   *@since
   */
  public String getUnits() {
    return units;
  }


  /**
   *  Gets the Terms attribute of the Opportunity object
   *
   *@return    The Terms value
   *@since
   */
  public double getTerms() {
    return terms;
  }


  /**
   *  Gets the TermsString attribute of the Opportunity object
   *
   *@return    The TermsString value
   *@since
   */
  public String getTermsString() {
    Double tmp = new Double(round(terms, 2));
    String toReturn = "" + tmp;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the StageName attribute of the Opportunity object
   *
   *@return    The StageName value
   *@since
   */
  public String getStageName() {
    if (this.getClosed() != null) {
      this.setStageName("Closed");
    }

    return stageName;
  }


  /**
   *  Gets the Type attribute of the Opportunity object
   *
   *@return    The Type value
   *@since
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the Id attribute of the Opportunity object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the Owner attribute of the Opportunity object
   *
   *@return    The Owner value
   *@since
   */
  public int getOwner() {
    return owner;
  }



  /**
   *  Gets the Stage attribute of the Opportunity object
   *
   *@return    The Stage value
   *@since
   */
  public int getStage() {
    return stage;
  }


  /**
   *  Gets the ModifiedBy attribute of the Opportunity object
   *
   *@return    The ModifiedBy value
   *@since
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the CloseIt attribute of the Opportunity object
   *
   *@return    The CloseIt value
   *@since
   */
  public boolean getCloseIt() {
    return closeIt;
  }


  /**
   *  Gets the EnteredBy attribute of the Opportunity object
   *
   *@return    The EnteredBy value
   *@since
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the Description attribute of the Opportunity object
   *
   *@return    The Description value
   *@since
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the Description attribute of the Opportunity object
   *
   *@return    The Description value
   *@since
   */
  public String getShortDescription() {
    if (description.length() <= 40) {
      return description;
    } else {
      return description.substring(0, 40) + "...";
    }
  }


  /**
   *  Gets the Low attribute of the Opportunity object
   *
   *@return    The Low value
   *@since
   */
  public double getLow() {
    return low;
  }


  /**
   *  Gets the LowAmount attribute of the Opportunity object
   *
   *@return    The LowAmount value
   *@since
   */
  public String getLowAmount() {
    Double thisAmount = new Double(round(low, 2));
    String toReturn = "" + thisAmount;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the LowCurrency attribute of the Opportunity object
   *
   *@return    The LowCurrency value
   *@since
   */
  public String getLowCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(low);
    return amountOut;
  }


  /**
   *  Gets the Guess attribute of the Opportunity object
   *
   *@return    The Guess value
   *@since
   */
  public double getGuess() {
    return guess;
  }


  /**
   *  Gets the GuessAmount attribute of the Opportunity object
   *
   *@return    The GuessAmount value
   *@since
   */
  public String getGuessAmount() {
    Double thisAmount = new Double(round(guess, 2));
    String toReturn = "" + thisAmount;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the GuessCurrency attribute of the Opportunity object
   *
   *@return    The GuessCurrency value
   *@since
   */
  public String getGuessCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(guess);
    return amountOut;
  }


  /**
   *  Gets the guessCurrency attribute of the Opportunity object
   *
   *@param  divisor  Description of the Parameter
   *@return          The guessCurrency value
   */
  public String getGuessCurrency(int divisor) {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    double tempValue = (guess / divisor);
    String amountOut = "";

    if (tempValue < 1) {
      amountOut = "<1";
    } else {
      amountOut = numberFormatter.format(tempValue);
    }

    return amountOut;
  }


  /**
   *  Gets the High attribute of the Opportunity object
   *
   *@return    The High value
   *@since
   */
  public double getHigh() {
    return high;
  }


  /**
   *  Gets the HighAmount attribute of the Opportunity object
   *
   *@return    The HighAmount value
   *@since
   */
  public String getHighAmount() {
    Double thisAmount = new Double(round(high, 2));
    String toReturn = "" + thisAmount;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the HighCurrency attribute of the Opportunity object
   *
   *@return    The HighCurrency value
   *@since
   */
  public String getHighCurrency() {
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    String amountOut = numberFormatter.format(high);
    return amountOut;
  }


  /**
   *  Gets the CloseProb attribute of the Opportunity object
   *
   *@return    The CloseProb value
   *@since
   */
  public double getCloseProb() {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity-> Close Prob: " + closeProb);
    }
    return closeProb;
  }


  /**
   *  Gets the CloseProbString attribute of the Opportunity object
   *
   *@return    The CloseProbString value
   *@since
   */
  public String getCloseProbString() {
    //String stringOut = (new java.math.BigDecimal("" + closeProb)).toString();
    return "" + closeProb;
  }


  /**
   *  Gets the closeProbValue attribute of the Opportunity object
   *
   *@return    The closeProbValue value
   */
  public String getCloseProbValue() {
    double value_2dp = (double) Math.round(closeProb * 100.0 * 100.0) / 100.0;
    String toReturn = "" + value_2dp;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Gets the CloseProbPercent attribute of the Opportunity object
   *
   *@return    The CloseProbPercent value
   *@since
   */
  public String getCloseProbPercent() {
    NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.US);
    String percentOut = percentFormatter.format(closeProb);
    return percentOut;
  }


  /**
   *  Gets the Commission attribute of the Opportunity object
   *
   *@return    The Commission value
   *@since
   */
  public double getCommission() {
    return commission;
  }


  /**
   *  Gets the CommissionString attribute of the Opportunity object
   *
   *@return    The CommissionString value
   *@since
   */
  public String getCommissionString() {
    String stringOut = (new java.math.BigDecimal("" + commission)).toString();
    return stringOut;
  }


  /**
   *  Gets the CommissionPercent attribute of the Opportunity object
   *
   *@return    The CommissionPercent value
   *@since
   */
  public String getCommissionPercent() {
    NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.US);
    String percentOut = percentFormatter.format(commission);
    return percentOut;
  }


  /**
   *  Gets the commissionValue attribute of the Opportunity object
   *
   *@return    The commissionValue value
   */
  public String getCommissionValue() {
    double value_2dp = (double) Math.round(commission * 100.0 * 100.0) / 100.0;
    String toReturn = "" + value_2dp;
    if (toReturn.endsWith(".0")) {
      return (toReturn.substring(0, toReturn.length() - 2));
    } else {
      return toReturn;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  id                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Opportunity Component ID not specified.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT oc.*, y.description as stagename, " +
        "ct_owner.namelast as o_namelast, ct_owner.namefirst as o_namefirst, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst " +
        "FROM opportunity_component oc " +
        "LEFT JOIN contact ct_owner ON (oc.owner = ct_owner.user_id) " +
        "LEFT JOIN contact ct_eb ON (oc.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (oc.modifiedby = ct_mb.user_id), " +
        "lookup_stage y " +
        "WHERE y.code = oc.stage " +
        "AND id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("Opportunity Component record not found.");
    }
    rs.close();
    pst.close();
    buildTypes(db);`
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void checkEnabledOwnerAccount(Connection db) throws SQLException {
    if (this.getOwner() == -1) {
      throw new SQLException("ID not specified for lookup.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM access " +
        "WHERE user_id = ? AND enabled = ? ");
    pst.setInt(1, this.getOwner());
    pst.setBoolean(2, true);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      this.setHasEnabledOwnerAccount(true);
    } else {
      this.setHasEnabledOwnerAccount(false);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    if (insert(db)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public int update(Connection db, ActionContext context) throws SQLException {
    int oldId = -1;
    Statement st = db.createStatement();
    ResultSet rs = st.executeQuery(
        "SELECT owner " +
        "FROM opportunity_component " +
        "WHERE id = " + this.getId());
    if (rs.next()) {
      oldId = rs.getInt("owner");
    }
    rs.close();
    st.close();
    int result = update(db);
    if (result == 1) {
      invalidateUserData(context);
      if (oldId != this.getOwner()) {
        invalidateUserData(context, oldId);
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean delete(Connection db, ActionContext context) throws SQLException {
    if (delete(db)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, ActionContext context, String baseFilePath) throws SQLException {
    if (delete(db, baseFilePath)) {
      invalidateUserData(context);
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean disable(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Opportunity Component ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    boolean success = false;

    sql.append(
        "UPDATE opportunity_component set enabled = " + DatabaseUtils.getFalse(db) + " " +
        "WHERE id = ? ");

    sql.append("AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, id);

    pst.setTimestamp(++i, this.getModified());

    int resultCount = pst.executeUpdate();
    pst.close();

    if (resultCount == 1) {
      success = true;
    }

    return success;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public int updateHeaderModified(Connection db) throws SQLException {
    if (this.getHeaderId() == -1) {
      throw new SQLException("Opportunity Header ID not specified");
    }

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    int resultCount = -1;

    sql.append(
        "UPDATE opportunity_header set modified = CURRENT_TIMESTAMP, modifiedby = ? " +
        "WHERE opp_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getHeaderId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since
   */
  public String toString() {
    StringBuffer out = new StringBuffer();

    out.append("===========================================\r\n");
    out.append("Id: " + id + "\r\n");
    out.append("Opportunity Component: " + description + "\r\n");
    out.append("Close Date: " + getCloseDateString() + "\r\n");
    out.append("Stage Date: " + getStageDateString() + "\r\n");
    out.append("Alert Date: " + getAlertDateString() + "\r\n");

    return out.toString();
  }


  /**
   *  Inserts this object into the database, and populates this Id. For
   *  maintenance, only the required fields are inserted, then an update is
   *  executed to finish the record.
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid(db)) {
      return false;
    }
    if (this.getHeaderId() == -1) {
      throw new SQLException("You must associate an opportunity component with an opportunity.");
    }

    try {
      db.setAutoCommit(false);
      StringBuffer sql = new StringBuffer();
      sql.append(
          "INSERT INTO opportunity_component " +
          "(owner, closedate, stage, description, opp_id, ");
      if (stageDate != null) {
        sql.append("stagedate, ");
      }
      if (entered != null) {
        sql.append("entered, ");
      }
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("enteredBy, modifiedBy ) ");
      sql.append("VALUES (?, ?, ?, ?, ?, ");
      if (stageDate != null) {
        sql.append("?, ");
      }
      if (entered != null) {
        sql.append("?, ");
      }
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("?, ?) ");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getOwner());
      pst.setDate(++i, this.getCloseDate());
      pst.setInt(++i, this.getStage());
      pst.setString(++i, this.getDescription());
      pst.setInt(++i, this.getHeaderId());
      if (stageDate != null) {
        pst.setDate(++i, stageDate);
      }
      if (entered != null) {
        pst.setTimestamp(++i, entered);
      }
      if (modified != null) {
        pst.setTimestamp(++i, modified);
      }
      pst.setInt(++i, this.getEnteredBy());
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();

      id = DatabaseUtils.getCurrVal(db, "opportunity_component_id_seq");
      this.update(db, true);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void buildTypes(Connection db) throws SQLException {
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT otl.type_id " +
        "FROM opportunity_component_levels otl " +
        "WHERE otl.opp_id = ? ORDER BY otl.level ");

    PreparedStatement pst = db.prepareStatement(sql.toString());
    int i = 0;
    pst.setInt(++i, id);
    rs = pst.executeQuery();

    while (rs.next()) {
      types.add(new LookupElement(db, rs.getInt("type_id"), "lookup_opportunity_types"));
    }

    rs.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean resetType(Connection db) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID not specified");
    }
    String sql = "DELETE FROM opportunity_component_levels WHERE opp_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getId());
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  type_id           Description of Parameter
   *@param  level             Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean insertType(Connection db, int type_id, int level) throws SQLException {
    if (id == -1) {
      throw new SQLException("ID not specified");
    }
    String sql =
        "INSERT INTO opportunity_component_levels " +
        "(opp_id, type_id, level) " +
        "VALUES (?, ?, ?) ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, this.getId());
    pst.setInt(++i, type_id);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@since
   */
  public void invalidateUserData(ActionContext context) {
    invalidateUserData(context, owner);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  newOwner          Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  public boolean reassign(Connection db, int newOwner) throws SQLException {
    int result = -1;
    this.setOwner(newOwner);
    result = this.update(db);

    if (result == -1) {
      return false;
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@param  userId   Description of Parameter
   *@since
   */
  public void invalidateUserData(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.getHierarchyList().getUser(userId).setIsValid(false, true);

  }


  /**
   *  Gets the Valid attribute of the Opportunity object
   *
   *@param  db                Description of Parameter
   *@return                   The Valid value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean isValid(Connection db) throws SQLException {
    //errors.clear();

    if (description == null || description.trim().equals("")) {
      errors.put("componentDescriptionError", "Description cannot be left blank");
    }

    if (!this.getCloseIt()) {
      if (closeProb == 0 && !(errors.containsKey("closeProbError"))) {
        errors.put("closeProbError", "Close Probability cannot be left blank");
      } else {
        if (closeProb > 100) {
          errors.put("closeProbError", "Close Probability cannot be greater than 100%");
        } else if (closeProb < 0) {
          errors.put("closeProbError", "Close Probability cannot be less than 0%");
        }
      }
    }
    if (closeDate == null || getCloseDateString().trim().equals("")) {
      errors.put("closeDateError", "Close Date cannot be left blank");
    }

    if (guess == 0 && !(errors.containsKey("guessError"))) {
      errors.put("guessError", "Amount needs to be entered");
    }

    if (terms == 0 && !(errors.containsKey("termsError"))) {
      errors.put("termsError", "Terms needs to be entered");
    } else {
      if (terms < 0) {
        errors.put("termsError", "Terms cannot be less than 0");
      }
    }

    if (hasErrors()) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Opportunity Component-> Cannot insert: object is not valid");
      }
      return false;
    } else {
      return true;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected int update(Connection db) throws SQLException {
    int resultCount = 0;

    if (this.getId() == -1) {
      throw new SQLException("Opportunity Component ID was not specified");
    }

    if (!isValid(db)) {
      return -1;
    }

    try {
      db.setAutoCommit(false);
      resultCount = this.update(db, false);
      db.commit();
    } catch (Exception e) {
      db.rollback();
      db.setAutoCommit(true);
      throw new SQLException(e.getMessage());
    }
    db.setAutoCommit(true);

    return resultCount;
  }


  /**
   *  Delete the current object from the database
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("The Opportunity Component could not be found.");
    }

    Statement st = null;

    try {
      db.setAutoCommit(false);
      this.resetType(db);

      st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM opportunity_component WHERE id = " + this.getId());
      st.close();

      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }


  /**
   *  Deletes an opportunity component, and its associated documents and calls
   *
   *@param  db                Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean delete(Connection db, String baseFilePath) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("The Opportunity Component could not be found.");
    }

    Statement st = null;

    try {
      db.setAutoCommit(false);
      this.resetType(db);

      CallList callList = new CallList();
      callList.setOppId(this.getId());
      callList.buildList(db);
      callList.delete(db);
      callList = null;

      FileItemList fileList = new FileItemList();
      fileList.setLinkModuleId(Constants.PIPELINE);
      fileList.setLinkItemId(this.getId());
      fileList.buildList(db);
      fileList.delete(db, baseFilePath);
      fileList = null;

      st = db.createStatement();
      st.executeUpdate(
          "DELETE FROM opportunity_component WHERE id = " + this.getId());
      st.close();
      this.updateHeaderModified(db);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
      st.close();
    }
    return true;
  }


  /**
   *  Populates this object from a result set
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //opportunity table
    this.setId(rs.getInt("id"));
    oppId = rs.getInt("opp_id");
    owner = rs.getInt("owner");
    description = rs.getString("description");
    closeDate = rs.getDate("closedate");
    closeProb = rs.getDouble("closeprob");
    terms = rs.getInt("terms");
    units = rs.getString("units");
    low = rs.getDouble("lowvalue");
    guess = rs.getDouble("guessvalue");
    high = rs.getDouble("highvalue");
    stage = rs.getInt("stage");
    stageDate = rs.getDate("stagedate");
    commission = rs.getDouble("commission");
    type = rs.getString("type");
    alertDate = rs.getDate("alertdate");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    closed = rs.getString("closed");
    alertText = rs.getString("alert");
    enabled = rs.getBoolean("enabled");
    notes = rs.getString("notes");

    //table
    stageName = rs.getString("stagename");

    //contact table
    ownerName = Contact.getNameLastFirst(rs.getString("o_namelast"), rs.getString("o_namefirst"));
    enteredByName = Contact.getNameLastFirst(rs.getString("eb_namelast"), rs.getString("eb_namefirst"));
    modifiedByName = Contact.getNameLastFirst(rs.getString("mb_namelast"), rs.getString("mb_namefirst"));
  }


  /**
   *  Update the database with changes to this Opportunity Component
   *
   *@param  db                Opened database connection
   *@param  override          Set to true on an Insert only
   *@return                   The number of records updated
   *@exception  SQLException  update error
   *@since
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;

    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    if (!override) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Opportunity Component-> Retrieving values from previous Opportunity Component");
      }
      sql.append(
          "SELECT stage, closed " +
          "FROM opportunity_component " +
          "WHERE id = ? ");
      pst = db.prepareStatement(sql.toString());
      pst.setInt(1, this.getId());
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int currentStage = rs.getInt("stage");
        if (currentStage != stage || this.getCloseIt()) {
          this.setStageChange(true);
        } else {
          this.setStageChange(false);
        }
        //check if opp is reopened
        closed = rs.getString("closed");
        if (!rs.wasNull() && !this.getCloseIt()) {
          this.setOpenIt(true);
        }
      }
      rs.close();
      pst.close();
      sql.setLength(0);
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity Component-> Updating the opportunity component");
    }
    sql.append(
        "UPDATE opportunity_component " +
        "SET lowvalue = ?, guessvalue = ?, highvalue = ?, closeprob = ?, " +
        "commission = ?, ");

    if ((this.getStageChange() == true && override == false)) {
      sql.append("stagedate = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append("type = ?, stage = ?, description = ?, " +
        "closedate = ?, alertdate = ?, alert = ?, terms = ?, units = ?, owner = ?, notes = ?, ");

    if (override == false) {
      sql.append("modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }

    sql.append("modifiedby = ? ");

    if (this.getCloseIt() == true) {
      sql.append(
          ", closed = CURRENT_TIMESTAMP ");
    } else if (this.getOpenIt() == true) {
      sql.append(
          ", closed = ? ");
    }

    sql.append("WHERE id = ? ");

    if (!override) {
      sql.append("AND modified = ? ");
    }

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setDouble(++i, this.getLow());
    pst.setDouble(++i, this.getGuess());
    pst.setDouble(++i, this.getHigh());
    pst.setDouble(++i, this.getCloseProb());
    pst.setDouble(++i, this.getCommission());
    pst.setString(++i, this.getType());
    pst.setInt(++i, this.getStage());
    pst.setString(++i, this.getDescription());

    if (closeDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getCloseDate());
    }

    if (alertDate == null) {
      pst.setNull(++i, java.sql.Types.DATE);
    } else {
      pst.setDate(++i, this.getAlertDate());
    }

    pst.setString(++i, this.getAlertText());
    pst.setDouble(++i, this.getTerms());
    pst.setString(++i, this.getUnits());
    pst.setInt(++i, this.getOwner());
    pst.setString(++i, this.getNotes());
    pst.setInt(++i, this.getModifiedBy());

    if (this.getOpenIt() == true) {
      pst.setNull(++i, java.sql.Types.DATE);
    }

    pst.setInt(++i, this.getId());

    if (!override) {
      pst.setTimestamp(++i, this.getModified());
    }

    resultCount = pst.executeUpdate();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity Component-> ResultCount: " + resultCount);
    }
    pst.close();

    if (resultCount == 1) {
      //Remove all opp types, add new list
      if (typeList != null) {
        resetType(db);
        int lvlcount = 0;
        for (int k = 0; k < typeList.size(); k++) {
          String val = (String) typeList.get(k);
          if (val != null && !(val.equals(""))) {
            int type_id = Integer.parseInt((String) typeList.get(k));
            lvlcount++;
            insertType(db, type_id, lvlcount);
          } else {
            lvlcount--;
          }
        }
      }
      this.updateHeaderModified(db);
    }

    if (System.getProperty("DEBUG") != null) {
      System.out.println("Opportunity Component-> Closing PreparedStatement");
    }
    return resultCount;
  }

}

