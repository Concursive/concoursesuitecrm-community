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
package org.aspcfs.modules.orders.base;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.aspcfs.utils.web.PagedListInfo;

/**
 * This represents a List of Credit Cards
 * 
 * @author Olga.Kaptyug
 * @version $Id: $
 * @created Aug 28, 2006
 */
public class CreditCardList extends ArrayList {
  private PagedListInfo pagedListInfo = null;

  private int orgId = -1;

  private int id = -1;

  private int cardType = -1;

  private String cardNumber = null;

  private String cardSecurityCode = null;

  private int expirationMonth = -1;

  private int expirationYear = -1;

  private String nameOnCard = null;

  private String companyNameOnCard = null;

  private Timestamp entered = null;

  private int enteredBy = -1;

  private Timestamp modified = null;

  private int modifiedBy = -1;

  // payment encryption
  private Key publicKey = null;

  private Key privateKey = null;

  /**
   * Gets the cardNumber attribute of the CreditCardList object
   * 
   * @return cardNumber The cardNumber value
   */
  public String getCardNumber() {
    return this.cardNumber;
  }

  /**
   * Sets the cardNumber attribute of the CreditCardList object
   * 
   * @param cardNumber
   *          The new cardNumber value
   */
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  /**
   * Gets the cardSecurityCode attribute of the CreditCardList object
   * 
   * @return cardSecurityCode The cardSecurityCode value
   */
  public String getCardSecurityCode() {
    return this.cardSecurityCode;
  }

  /**
   * Sets the cardSecurityCode attribute of the CreditCardList object
   * 
   * @param cardSecurityCode
   *          The new cardSecurityCode value
   */
  public void setCardSecurityCode(String cardSecurityCode) {
    this.cardSecurityCode = cardSecurityCode;
  }

  /**
   * Gets the cardType attribute of the CreditCardList object
   * 
   * @return cardType The cardType value
   */
  public int getCardType() {
    return this.cardType;
  }

  /**
   * Sets the cardType attribute of the CreditCardList object
   * 
   * @param cardType
   *          The new cardType value
   */
  public void setCardType(int cardType) {
    this.cardType = cardType;
  }

  /**
   * Gets the companyNameOnCard attribute of the CreditCardList object
   * 
   * @return companyNameOnCard The companyNameOnCard value
   */
  public String getCompanyNameOnCard() {
    return this.companyNameOnCard;
  }

  /**
   * Sets the companyNameOnCard attribute of the CreditCardList object
   * 
   * @param companyNameOnCard
   *          The new companyNameOnCard value
   */
  public void setCompanyNameOnCard(String companyNameOnCard) {
    this.companyNameOnCard = companyNameOnCard;
  }

  /**
   * Gets the entered attribute of the CreditCardList object
   * 
   * @return entered The entered value
   */
  public Timestamp getEntered() {
    return this.entered;
  }

  /**
   * Sets the entered attribute of the CreditCardList object
   * 
   * @param entered
   *          The new entered value
   */
  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

  /**
   * Gets the enteredBy attribute of the CreditCardList object
   * 
   * @return enteredBy The enteredBy value
   */
  public int getEnteredBy() {
    return this.enteredBy;
  }

  /**
   * Sets the enteredBy attribute of the CreditCardList object
   * 
   * @param enteredBy
   *          The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }

  /**
   * Gets the expirationMonth attribute of the CreditCardList object
   * 
   * @return expirationMonth The expirationMonth value
   */
  public int getExpirationMonth() {
    return this.expirationMonth;
  }

  /**
   * Sets the expirationMonth attribute of the CreditCardList object
   * 
   * @param expirationMonth
   *          The new expirationMonth value
   */
  public void setExpirationMonth(int expirationMonth) {
    this.expirationMonth = expirationMonth;
  }

  /**
   * Gets the expirationYear attribute of the CreditCardList object
   * 
   * @return expirationYear The expirationYear value
   */
  public int getExpirationYear() {
    return this.expirationYear;
  }

  /**
   * Sets the expirationYear attribute of the CreditCardList object
   * 
   * @param expirationYear
   *          The new expirationYear value
   */
  public void setExpirationYear(int expirationYear) {
    this.expirationYear = expirationYear;
  }

  /**
   * Gets the id attribute of the CreditCardList object
   * 
   * @return id The id value
   */
  public int getId() {
    return this.id;
  }

  /**
   * Sets the id attribute of the CreditCardList object
   * 
   * @param id
   *          The new id value
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the modified attribute of the CreditCardList object
   * 
   * @return modified The modified value
   */
  public Timestamp getModified() {
    return this.modified;
  }

  /**
   * Sets the modified attribute of the CreditCardList object
   * 
   * @param modified
   *          The new modified value
   */
  public void setModified(Timestamp modified) {
    this.modified = modified;
  }

  /**
   * Gets the modifiedBy attribute of the CreditCardList object
   * 
   * @return modifiedBy The modifiedBy value
   */
  public int getModifiedBy() {
    return this.modifiedBy;
  }

  /**
   * Sets the modifiedBy attribute of the CreditCardList object
   * 
   * @param modifiedBy
   *          The new modifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  /**
   * Gets the nameOnCard attribute of the CreditCardList object
   * 
   * @return nameOnCard The nameOnCard value
   */
  public String getNameOnCard() {
    return this.nameOnCard;
  }

  /**
   * Sets the nameOnCard attribute of the CreditCardList object
   * 
   * @param nameOnCard
   *          The new nameOnCard value
   */
  public void setNameOnCard(String nameOnCard) {
    this.nameOnCard = nameOnCard;
  }

  /**
   * Gets the privateKey attribute of the CreditCardList object
   * 
   * @return privateKey The privateKey value
   */
  public Key getPrivateKey() {
    return this.privateKey;
  }

  /**
   * Sets the privateKey attribute of the CreditCardList object
   * 
   * @param privateKey
   *          The new privateKey value
   */
  public void setPrivateKey(Key privateKey) {
    this.privateKey = privateKey;
  }

  /**
   * Gets the publicKey attribute of the CreditCardList object
   * 
   * @return publicKey The publicKey value
   */
  public Key getPublicKey() {
    return this.publicKey;
  }

  /**
   * Sets the publicKey attribute of the CreditCardList object
   * 
   * @param publicKey
   *          The new publicKey value
   */
  public void setPublicKey(Key publicKey) {
    this.publicKey = publicKey;
  }

  /**
   * Sets the pagedListInfo attribute of the OrderList object
   * 
   * @param tmp
   *          The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }

  /**
   * Sets the orgId attribute of the OrderList object
   * 
   * @param tmp
   *          The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  /**
   * Sets the orgId attribute of the OrderList object
   * 
   * @param tmp
   *          The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  /**
   * Gets the pagedListInfo attribute of the OrderList object
   * 
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  /**
   * Gets the orgId attribute of the OrderList object
   * 
   * @return The orgId value
   */
  public int getOrgId() {
    return orgId;
  }

  /**
   * Constructor for the OrderList object
   */
  public CreditCardList() {
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of the Parameter
   * @throws SQLException
   *           Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    // Build a base SQL statement for counting records
    sqlCount.append(" SELECT COUNT(*) AS recordcount "
        + " FROM credit_card cc " + " WHERE cc.creditcard_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      // Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      // Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
        items = prepareFilter(pst);
        // pst.setString(++items,
        // pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }
      // Determine column to sort by
      pagedListInfo.setDefaultSort("cc.entered", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY cc.creditcard_id");
    }
    // Build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect
        .append("cc.creditcard_id, cc.card_type, cc.card_number,"
            + " cc.card_security_code, cc.expiration_month, cc.expiration_year, "
            + " cc.name_on_card, cc.company_name_on_card, lct.description as card_type_name, cc.entered, cc.enteredby,"
            + " cc.modified, cc.modifiedby FROM credit_card cc "
            + " LEFT JOIN lookup_creditcard_types lct on (cc.card_type = lct.code) "
            + " WHERE cc.creditcard_id>-1 ");

    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
        + sqlOrder.toString());
    items = prepareFilter(pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      CreditCard thisCard = new CreditCard(rs);
      this.add(thisCard);
    }
    rs.close();
    pst.close();
  }

  /**
   * Description of the Method
   * 
   * @param sqlFilter
   *          Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (enteredBy > -1) {
      sqlFilter.append("AND cc.enteredby = ? ");
    }

  }

  /**
   * Description of the Method
   * 
   * @param pst
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }
    return i;
  }
}
