//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.utils.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    September 13, 2001
 *@version    $Id$
 */
public class Opportunity extends GenericBean {

	private int id = -1;
	private int owner = -1;
	private String description = "";
	private int accountLink = -1;
	private int contactLink = -1;
	private String closeDate = "";
	private float closeProb = 0;
	private float terms = 0;
	private float low = 0;
	private float guess = 0;
	private float high = 0;
	private String units = "";
	private int stage = -1;
	private String stageName = "";
	private String stageDate = "";
	private float commission = 0;
	private String type = "";
	private String alertDate = "";
	private String entered = "";
	private int enteredBy = -1;
	private String modified = "";
	private java.sql.Timestamp modifiedDate = null;
	private int modifiedBy = -1;
	private boolean enabled = true;
	private boolean stageChange = false;
	private boolean closeIt = false;
	private boolean openIt = false;
	private String closed = null;

	private String accountName = "";
	private String contactName = "";
	private String ownerName = "";
	private String enteredByName = "";
	private String modifiedByName = "";


	/**
	 *  Constructor for the Opportunity object
	 *
	 *@since    1.1
	 */
	public Opportunity() { }


	/**
	 *  Constructor for the Contact object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	public Opportunity(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}


	/**
	 *  Constructor for the Employee object, populates all attributes by performing
	 *  a SQL query based on the employeeId parameter
	 *
	 *@param  db                Description of Parameter
	 *@param  oppId             Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since                    1.1
	 */
	public Opportunity(Connection db, String oppId) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();

		sql.append(
				"SELECT x.*, y.description as stagename, org.name as acct_name, ct.namelast as last_name, ct.namefirst as first_name, " +
				"ct_owner.namelast || ', ' || ct_owner.namefirst as o_name, ct_eb.namelast || ', ' || ct_eb.namefirst as eb_name, ct_mb.namelast || ', ' || ct_mb.namefirst as mb_name " +
				"FROM opportunity x " +
				"LEFT JOIN organization org ON (x.acctlink = org.org_id) " +
				"LEFT JOIN contact ct_owner ON (x.owner = ct_owner.user_id) " +
				"LEFT JOIN contact ct_eb ON (x.enteredby = ct_eb.user_id) " +
				"LEFT JOIN contact ct_mb ON (x.modifiedby = ct_mb.user_id) " +
				"LEFT JOIN contact ct ON (x.contactlink = ct.contact_id), " +
				"lookup_stage y " +
				"WHERE y.code = x.stage ");

		if (oppId != null && !oppId.equals("")) {
			sql.append("AND opp_id = " + oppId + " ");
		}
		else {
			throw new SQLException("Opportunity ID not specified.");
		}

		st = db.createStatement();
		rs = st.executeQuery(sql.toString());
		if (rs.next()) {
			buildRecord(rs);
		}
		else {
			rs.close();
			st.close();
			throw new SQLException("Opportunity record not found.");
		}
		rs.close();
		st.close();

	}


	/**
	 *  Sets the EnteredByName attribute of the Opportunity object
	 *
	 *@param  enteredByName  The new EnteredByName value
	 *@since
	 */
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}

public boolean getOpenIt() {
	return openIt;
}
public void setOpenIt(boolean openIt) {
	this.openIt = openIt;
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
public String getClosed() {
	return closed;
}
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
	 *  Sets the ContactName attribute of the Opportunity object
	 *
	 *@param  contactName  The new ContactName value
	 *@since
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
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
	 *  Sets the AlertDate attribute of the Opportunity object
	 *
	 *@param  alertDate  The new AlertDate value
	 *@since
	 */
	public void setAlertDate(String alertDate) {
		this.alertDate = alertDate;
	}


	/**
	 *  Sets the Entered attribute of the Opportunity object
	 *
	 *@param  entered  The new Entered value
	 *@since
	 */
	public void setEntered(String entered) {
		this.entered = entered;
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
		this.terms = Float.parseFloat(terms);
	}


	/**
	 *  Sets the Modified attribute of the Opportunity object
	 *
	 *@param  modified  The new Modified value
	 *@since
	 */
	public void setModified(String modified) {
		this.modified = modified;
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
	 *  Sets the EnteredBy attribute of the Opportunity object
	 *
	 *@param  enteredBy  The new EnteredBy value
	 *@since
	 */
	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
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
	 *  Sets the StageDate attribute of the Opportunity object
	 *
	 *@param  stageDate  The new StageDate value
	 *@since
	 */
	public void setStageDate(String stageDate) {
		this.stageDate = stageDate;
	}


	/**
	 *  Sets the Low attribute of the Opportunity object
	 *
	 *@param  low  The new Low value
	 *@since
	 */
	public void setLow(String low) {
		this.low = Float.parseFloat(low);
	}


	/**
	 *  Sets the Guess attribute of the Opportunity object
	 *
	 *@param  guess  The new Guess value
	 *@since
	 */
	public void setGuess(String guess) {
		this.guess = Float.parseFloat(guess);
	}


	/**
	 *  Sets the High attribute of the Opportunity object
	 *
	 *@param  high  The new High value
	 *@since
	 */
	public void setHigh(String high) {
		this.high = Float.parseFloat(high);
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
	 *  Sets the CloseDate attribute of the Opportunity object
	 *
	 *@param  closeDate  The new CloseDate value
	 *@since
	 */
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
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
		this.closeProb = ((Float.parseFloat(closeProb)) / 100);
	}


	/**
	 *  Sets the Commission attribute of the Opportunity object
	 *
	 *@param  commission  The new Commission value
	 *@since
	 */
	public void setCommission(String commission) {
		this.commission = ((Float.parseFloat(commission)) / 100);
	}


	/**
	 *  Sets the ContactLink attribute of the Opportunity object
	 *
	 *@param  contactLink  The new ContactLink value
	 *@since
	 */
	public void setContactLink(String contactLink) {
		this.contactLink = Integer.parseInt(contactLink);
	}


	/**
	 *  Sets the AccountLink attribute of the Opportunity object
	 *
	 *@param  accountLink  The new AccountLink value
	 *@since
	 */
	public void setAccountLink(String accountLink) {
		this.accountLink = Integer.parseInt(accountLink);
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
	 *  Gets the Entered attribute of the Opportunity object
	 *
	 *@return    The Entered value
	 *@since
	 */
	public String getEntered() {
		return entered;
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
	 *  Gets the ContactName attribute of the Opportunity object
	 *
	 *@return    The ContactName value
	 *@since
	 */
	public String getContactName() {
		return contactName;
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
	public float getTerms() {
		return terms;
	}


	/**
	 *  Gets the TermsString attribute of the Opportunity object
	 *
	 *@return    The TermsString value
	 *@since
	 */
	public String getTermsString() {
		Float tmp = new Float(round(terms, 2));
		return tmp.toString();
	}


	/**
	 *  Gets the AlertDate attribute of the Opportunity object
	 *
	 *@return    The AlertDate value
	 *@since
	 */
	public String getAlertDate() {
		return alertDate;
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
	 *  Gets the StageDate attribute of the Opportunity object
	 *
	 *@return    The StageDate value
	 *@since
	 */
	public String getStageDate() {
		return stageDate;
	}


	/**
	 *  Gets the Modified attribute of the Opportunity object
	 *
	 *@return    The Modified value
	 *@since
	 */
	public String getModified() {
		return modified;
	}


	/**
	 *  Gets the ModifiedDate attribute of the Opportunity object
	 *
	 *@return    The ModifiedDate value
	 *@since
	 */
	public java.sql.Timestamp getModifiedDate() {
		return modifiedDate;
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
public boolean getCloseIt() {
	return closeIt;
}
public void setCloseIt(boolean closeIt) {
	this.closeIt = closeIt;
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
		if (description.length() <= 20) {
			return description;
		}
		else {
			return description.substring(0, 20) + "...";
		}
	}


	/**
	 *  Gets the Low attribute of the Opportunity object
	 *
	 *@return    The Low value
	 *@since
	 */
	public float getLow() {
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
		return thisAmount.toString();
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
	public float getGuess() {
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
		return thisAmount.toString();
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
	 *  Gets the High attribute of the Opportunity object
	 *
	 *@return    The High value
	 *@since
	 */
	public float getHigh() {
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
		return thisAmount.toString();
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
	 *  Gets the CloseDate attribute of the Opportunity object
	 *
	 *@return    The CloseDate value
	 *@since
	 */
	public String getCloseDate() {
		return closeDate;
	}


	/**
	 *  Gets the ContactLink attribute of the Opportunity object
	 *
	 *@return    The ContactLink value
	 *@since
	 */
	public int getContactLink() {
		return contactLink;
	}


	/**
	 *  Gets the CloseProb attribute of the Opportunity object
	 *
	 *@return    The CloseProb value
	 *@since
	 */
	public float getCloseProb() {
		return closeProb;
	}


	/**
	 *  Gets the CloseProbString attribute of the Opportunity object
	 *
	 *@return    The CloseProbString value
	 *@since
	 */
	public String getCloseProbString() {
		String stringOut = (new java.math.BigDecimal("" + closeProb)).toString();
		return stringOut;
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
	 *  Gets the AccountLink attribute of the Opportunity object
	 *
	 *@return    The AccountLink value
	 *@since
	 */
	public int getAccountLink() {
		return accountLink;
	}


	/**
	 *  Gets the AccountName attribute of the Opportunity object
	 *
	 *@return    The AccountName value
	 *@since
	 */
	public String getAccountName() {
		if (accountName != null) {
			return accountName;
		}
		else {
			return this.getContactName();
		}
	}


	/**
	 *  Gets the Commission attribute of the Opportunity object
	 *
	 *@return    The Commission value
	 *@since
	 */
	public float getCommission() {
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
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 *@since     1.29
	 */
	public boolean hasAccountLink() {
		return (accountLink > -1);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 *@since     1.29
	 */
	public boolean hasContactLink() {
		return (contactLink > -1);
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
		}
		else {
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
		ResultSet rs = st.executeQuery("SELECT owner FROM opportunity WHERE opp_id = " + this.getId());
		if (rs.next()) {
			oldId = rs.getInt("owner");
		}
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
		}
		else {
			return false;
		}
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
		out.append("Opportunity: " + description + "\r\n");
		out.append("Close Date: " + closeDate + "\r\n");
		out.append("Stage Date: " + stageDate + "\r\n");
		out.append("Alert Date: " + alertDate + "\r\n");

		return out.toString();
	}


	/**
	 *  Gets the Valid attribute of the Opportunity object
	 *
	 *@param  db                Description of Parameter
	 *@return                   The Valid value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected boolean isValid(Connection db) throws SQLException {
		errors.clear();

		if (description == null || description.trim().equals("")) {
			errors.put("descriptionError", "Description cannot be left blank");
		}

		if (closeProb == 0) {
			errors.put("closeProbError", "Close Probability cannot be left blank");
		}
		else {
			if (closeProb > 100) {
				errors.put("closeProbError", "Close Probability cannot be greater than 100%");
			}
			else if (closeProb < 0) {
				errors.put("closeProbError", "Close Probability cannot be less than 0%");
			}
		}

		if (closeDate == null || closeDate.trim().equals("")) {
			errors.put("closeDateError", "Close Date cannot be left blank");
		}

		if (guess == 0) {
			errors.put("guessError", "Amount needs to be entered");
		}

		if (terms == 0) {
			errors.put("termsError", "Terms needs to be entered");
		}
		else {
			if (terms < 0) {
				errors.put("termsError", "Terms cannot be less than 0");
			}
		}

		if (hasErrors()) {
			return false;
		}
		else {
			return true;
		}
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
	protected boolean insert(Connection db) throws SQLException {

		if (!isValid(db)) {
			return false;
		}

		if (this.getAccountLink() == -1 && this.getContactLink() == -1) {
			throw new SQLException("You must associate an opportunity with an account or contact.");
		}
		
		this.setStageChange(true);

		try {
			db.setAutoCommit(false);

			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO opportunity ");
			sql.append("(acctlink, contactlink, enteredby, modifiedby, owner, closedate, stage, description) ");
			sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");

			int i = 0;
			PreparedStatement pst = db.prepareStatement(sql.toString());

			pst.setInt(++i, this.getAccountLink());
			pst.setInt(++i, this.getContactLink());
			pst.setInt(++i, this.getEnteredBy());
			pst.setInt(++i, this.getModifiedBy());
			pst.setInt(++i, this.getOwner());
			pst.setDate(++i, convertStringToSqlDate(this.getCloseDate(), DateFormat.SHORT));
			pst.setInt(++i, this.getStage());
			pst.setString(++i, this.getDescription());

			pst.execute();
			pst.close();

			Statement st = db.createStatement();
			ResultSet rs = st.executeQuery("select currval('opportunity_opp_id_seq')");
			if (rs.next()) {
				this.setId(rs.getInt(1));
			}
			rs.close();
			st.close();

			this.update(db, true);
			db.commit();
		}
		catch (SQLException e) {
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
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected int update(Connection db) throws SQLException {
		int resultCount = 0;

		if (this.getId() == -1) {
			throw new SQLException("Opportunity ID was not specified");
		}

		if (!isValid(db)) {
			return -1;
		}

		try {
			db.setAutoCommit(false);
			resultCount = this.update(db, false);
			db.commit();
		}
		catch (Exception e) {
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
			throw new SQLException("The Opportunity could not be found.");
		}

		Statement st = db.createStatement();
		try {
			db.setAutoCommit(false);
			st.executeUpdate("DELETE FROM opportunity WHERE opp_id = " + this.getId());
			db.commit();
		}
		catch (SQLException e) {
			db.rollback();
		}
		finally {
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
		this.setId(rs.getInt("opp_id"));
		owner = rs.getInt("owner");
		description = rs.getString("description");
		accountLink = rs.getInt("acctLink");
		accountName = rs.getString("acct_name");
		contactName = rs.getString("first_name") + " " + rs.getString("last_name");
		ownerName = rs.getString("o_name");
		enteredByName = rs.getString("eb_name");
		modifiedByName = rs.getString("mb_name");
		contactLink = rs.getInt("contactLink");
		java.sql.Date thisCloseDate = rs.getDate("closedate");
		if (thisCloseDate != null) {
			closeDate = shortDateFormat.format(thisCloseDate);
		}
		else {
			closeDate = "";
		}
		closeProb = rs.getFloat("closeprob");
		terms = rs.getInt("terms");
		low = rs.getFloat("lowvalue");
		guess = rs.getFloat("guessvalue");
		high = rs.getFloat("highvalue");
		stage = rs.getInt("stage");
		java.sql.Date thisStageDate = rs.getDate("stagedate");
		if (thisStageDate != null) {
			stageDate = shortDateFormat.format(thisStageDate);
		}
		else {
			stageDate = "";
		}
		commission = rs.getFloat("commission");
		type = rs.getString("type");
		java.sql.Date thisAlertDate = rs.getDate("alertdate");
		if (thisAlertDate != null) {
			alertDate = shortDateFormat.format(thisAlertDate);
		}
		else {
			alertDate = "";
		}
		stageName = rs.getString("stagename");
		terms = rs.getFloat("terms");
		units = rs.getString("units");

		java.sql.Timestamp tmpDateCreated = rs.getTimestamp("entered");
		if (tmpDateCreated != null) {
			entered = shortDateTimeFormat.format(tmpDateCreated);
		}
		else {
			entered = "";
		}

		enteredBy = rs.getInt("enteredby");
		closed = rs.getString("closed");

		java.sql.Timestamp tmpLastModified = rs.getTimestamp("modified");
		modified = tmpLastModified.toString();

		modifiedBy = rs.getInt("modifiedby");
		//enabled = rs.getBoolean("enabled");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@param  override          Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	private int update(Connection db, boolean override) throws SQLException {
		int resultCount = 0;

		PreparedStatement pst = null;
		StringBuffer sql = new StringBuffer();

		sql.append(
				"UPDATE opportunity " +
				"SET lowvalue = ?, guessvalue = ?, highvalue = ?, closeprob = ?, " +
				"commission = ?, ");
				
				if (this.getStageChange() == true) {
					sql.append( "stagedate = CURRENT_TIMESTAMP, ");
				}
				
		sql.append(     "type = ?, stage = ?, description = ?, " +
				"closedate = ?, alertdate = ?, terms = ?, units = ?, owner = ?, modifiedby = ?, modified = CURRENT_TIMESTAMP ");
				
				if (this.getCloseIt() == true) {
					sql.append(
						", closed = CURRENT_TIMESTAMP ");
				} else if (this.getOpenIt() == true) {
					sql.append(
						", closed = ? ");
				}
				
		sql.append("WHERE opp_id = ? ");

		if (!override) {
			sql.append("AND modified = ? ");
		}

		int i = 0;
		pst = db.prepareStatement(sql.toString());
		pst.setFloat(++i, this.getLow());
		pst.setFloat(++i, this.getGuess());
		pst.setFloat(++i, this.getHigh());
		pst.setFloat(++i, this.getCloseProb());
		pst.setFloat(++i, this.getCommission());


		pst.setString(++i, this.getType());
		pst.setInt(++i, this.getStage());
		pst.setString(++i, this.getDescription());
		pst.setDate(++i, convertStringToSqlDate(this.getCloseDate(), DateFormat.SHORT));
		if (alertDate == null || alertDate.equals("")) {
			pst.setNull(++i, java.sql.Types.DATE);
		}
		else {
			pst.setDate(++i, convertStringToSqlDate(this.getAlertDate(), DateFormat.SHORT));
		}
		pst.setFloat(++i, this.getTerms());
		pst.setString(++i, this.getUnits());
		pst.setInt(++i, this.getOwner());
		pst.setInt(++i, this.getModifiedBy());
		
		if (this.getOpenIt() == true) {
			pst.setNull(++i, java.sql.Types.DATE);
		}
		
		pst.setInt(++i, this.getId());

		if (!override) {
			pst.setTimestamp(++i, java.sql.Timestamp.valueOf(this.getModified()));
		}
		
	
		resultCount = pst.executeUpdate();
		pst.close();

		return resultCount;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  context  Description of Parameter
	 *@since
	 */
	private void invalidateUserData(ActionContext context) {
		invalidateUserData(context, owner);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  context  Description of Parameter
	 *@param  userId   Description of Parameter
	 *@since
	 */
	private void invalidateUserData(ActionContext context, int userId) {
		ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
		SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
		systemStatus.getHierarchyList().getUser(userId).setIsValid(false, true);
	}
}

