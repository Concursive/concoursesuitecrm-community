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
package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 *  This represents a Quote in the Quote Entry System
 *
 * @author     ananth
 * @created    March 24, 2004
 * @version    $Id$
 */
public class Quote extends GenericBean {
  //Quote submitAction types
  public final static int NONE = 2004110800;
  public final static int PRINT = 2004110801;
  public final static int EMAIL = 2004110802;
  public final static int FAX = 2004110803;

  // fields
  private int id = -1;
  private int parentId = -1;
  private int orgId = -1;
  private int contactId = -1;
  private int sourceId = -1;
  private double grandTotal = 0;
  private int statusId = -1;
  private Timestamp statusDate = null;
  private Timestamp issuedDate = null;
  private Timestamp expirationDate = null;
  private int quoteTermsId = -1;
  private int quoteTypeId = -1;
  private String shortDescription = null;
  private String notes = null;
  private int ticketId = -1;
  private int productId = -1;
  private int customerProductId = -1;
  private int headerId = -1;
  private String version = "0.0";
  private int groupId = -1;
  private int deliveryId = -1;
  private String emailAddress = null;
  private String phoneNumber = null;
  private String address = null;
  private String faxNumber = null;
  private int submitAction = -1;
  private Timestamp closed = null;
  private boolean showTotal = false;
  private boolean showSubtotal = false;
  private int logoFileId = -1;
  private String statusName = null;

  // record status
  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;

  // Organization & billing contact info
  private String name = null;
  private String nameLast = null;
  private String nameFirst = null;
  private String nameMiddle = null;

  // Resources
  private boolean buildProducts = false;
  private QuoteProductList productList = new QuoteProductList();
  private boolean buildTicket = false;
  private Ticket ticket = null;
  private boolean buildCompleteVersionList = false;
  private QuoteList versionList = null;
  private boolean buildResources = true;
  private Contact contact = null;
  private Organization organization = null;
  private boolean buildHistory = false;
  private QuoteLogList history = new QuoteLogList();
  private boolean buildConditions = true;
  private QuoteConditionList conditions = new QuoteConditionList();
  private boolean buildRemarks = true;
  private QuoteRemarkList remarks = new QuoteRemarkList();
  private String submitType = null;
  private boolean closeIt = false;
  private String canPrint = "false";
  private boolean canNotCopyExpirationDate = false;



  /**
   *  Gets the logoFileId attribute of the Quote object
   *
   * @return    The logoFileId value
   */
  public int getLogoFileId() {
    return logoFileId;
  }


  /**
   *  Sets the logoFileId attribute of the Quote object
   *
   * @param  tmp  The new logoFileId value
   */
  public void setLogoFileId(int tmp) {
    this.logoFileId = tmp;
  }


  /**
   *  Sets the logoFileId attribute of the Quote object
   *
   * @param  tmp  The new logoFileId value
   */
  public void setLogoFileId(String tmp) {
    this.logoFileId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the id attribute of the Quote object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
    history.setQuoteId(tmp);
  }


  /**
   *  Sets the id attribute of the Quote object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
    history.setQuoteId(tmp);
  }


  /**
   *  Sets the parentId attribute of the Quote object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the Quote object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orgId attribute of the Quote object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the Quote object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactId attribute of the Quote object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the Quote object
   *
   * @param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the sourceId attribute of the Quote object
   *
   * @param  tmp  The new sourceId value
   */
  public void setSourceId(int tmp) {
    this.sourceId = tmp;
  }


  /**
   *  Sets the sourceId attribute of the Quote object
   *
   * @param  tmp  The new sourceId value
   */
  public void setSourceId(String tmp) {
    this.sourceId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the grandTotal attribute of the Quote object
   *
   * @param  tmp  The new grandTotal value
   */
  public void setGrandTotal(double tmp) {
    this.grandTotal = tmp;
  }


  /**
   *  Sets the grandTotal attribute of the Quote object
   *
   * @param  tmp  The new grandTotal value
   */
  public void setGrandTotal(String tmp) {
    this.grandTotal = Double.parseDouble(tmp);
  }


  /**
   *  Sets the statusId attribute of the Quote object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Quote object
   *
   * @param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusDate attribute of the Quote object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the statusDate attribute of the Quote object
   *
   * @param  tmp  The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the issuedDate attribute of the Quote object
   *
   * @param  tmp  The new issuedDate value
   */
  public void setIssuedDate(Timestamp tmp) {
    this.issuedDate = tmp;
  }


  /**
   *  Sets the issuedDate attribute of the Quote object
   *
   * @param  tmp  The new issuedDate value
   */
  public void setIssuedDate(String tmp) {
    this.issuedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the productId attribute of the Quote object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the Quote object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the customerProductId attribute of the Quote object
   *
   * @param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(int tmp) {
    this.customerProductId = tmp;
  }


  /**
   *  Sets the customerProductId attribute of the Quote object
   *
   * @param  tmp  The new customerProductId value
   */
  public void setCustomerProductId(String tmp) {
    this.customerProductId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the headerId attribute of the Quote object
   *
   * @param  tmp  The new headerId value
   */
  public void setHeaderId(int tmp) {
    this.headerId = tmp;
  }


  /**
   *  Sets the headerId attribute of the Quote object
   *
   * @param  tmp  The new headerId value
   */
  public void setHeaderId(String tmp) {
    this.headerId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the version attribute of the Quote object
   *
   * @param  tmp  The new version value
   */
  public void setVersion(String tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the versionList attribute of the Quote object
   *
   * @param  tmp  The new versionList value
   */
  public void setVersionList(QuoteList tmp) {
    this.versionList = tmp;
  }


  /**
   *  Sets the buildCompleteVersionList attribute of the Quote object
   *
   * @param  tmp  The new buildCompleteVersionList value
   */
  public void setBuildCompleteVersionList(boolean tmp) {
    this.buildCompleteVersionList = tmp;
  }


  /**
   *  Sets the buildCompleteVersionList attribute of the Quote object
   *
   * @param  tmp  The new buildCompleteVersionList value
   */
  public void setBuildCompleteVersionList(String tmp) {
    this.buildCompleteVersionList = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the groupId attribute of the Quote object
   *
   * @param  tmp  The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the groupId attribute of the Quote object
   *
   * @param  tmp  The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the groupId attribute of the Quote object
   *
   * @return    The groupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   *  Gets the buildCompleteVersionList attribute of the Quote object
   *
   * @return    The buildCompleteVersionList value
   */
  public boolean getBuildCompleteVersionList() {
    return buildCompleteVersionList;
  }


  /**
   *  Gets the versionList attribute of the Quote object
   *
   * @return    The versionList value
   */
  public QuoteList getVersionList() {
    return versionList;
  }


  /**
   *  Gets the version attribute of the Quote object
   *
   * @return    The version value
   */
  public String getVersion() {
    return version;
  }


  /**
   *  Gets the headerId attribute of the Quote object
   *
   * @return    The headerId value
   */
  public int getHeaderId() {
    return headerId;
  }


  /**
   *  Gets the productId attribute of the Quote object
   *
   * @return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Gets the customerProductId attribute of the Quote object
   *
   * @return    The customerProductId value
   */
  public int getCustomerProductId() {
    return customerProductId;
  }


  /**
   *  Gets the issuedDate attribute of the Quote object
   *
   * @return    The issuedDate value
   */
  public Timestamp getIssuedDate() {
    return issuedDate;
  }


  /**
   *  Sets the expirationDate attribute of the Quote object
   *
   * @param  tmp  The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the Quote object
   *
   * @param  tmp  The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the quoteTermsId attribute of the Quote object
   *
   * @param  tmp  The new quoteTermsId value
   */
  public void setQuoteTermsId(int tmp) {
    this.quoteTermsId = tmp;
  }


  /**
   *  Sets the quoteTermsId attribute of the Quote object
   *
   * @param  tmp  The new quoteTermsId value
   */
  public void setQuoteTermsId(String tmp) {
    this.quoteTermsId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quoteTypeId attribute of the Quote object
   *
   * @param  tmp  The new quoteTypeId value
   */
  public void setQuoteTypeId(int tmp) {
    this.quoteTypeId = tmp;
  }


  /**
   *  Sets the quoteTypeId attribute of the Quote object
   *
   * @param  tmp  The new quoteTypeId value
   */
  public void setQuoteTypeId(String tmp) {
    this.quoteTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the shortDescription attribute of the Quote object
   *
   * @param  tmp  The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   *  Sets the notes attribute of the Quote object
   *
   * @param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the entered attribute of the Quote object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Quote object
   *
   * @param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Quote object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Quote object
   *
   * @param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Quote object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Quote object
   *
   * @param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Quote object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Quote object
   *
   * @param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Quote object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Quote object
   *
   * @param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Quote object
   *
   * @param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameMiddle attribute of the Quote object
   *
   * @param  tmp  The new nameMiddle value
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   *  Sets the buildProducts attribute of the Quote object
   *
   * @param  tmp  The new buildProducts value
   */
  public void setBuildProducts(boolean tmp) {
    this.buildProducts = tmp;
  }


  /**
   *  Sets the buildProducts attribute of the Quote object
   *
   * @param  tmp  The new buildProducts value
   */
  public void setBuildProducts(String tmp) {
    this.buildProducts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the productList attribute of the Quote object
   *
   * @param  tmp  The new productList value
   */
  public void setProductList(QuoteProductList tmp) {
    this.productList = tmp;
  }


  /**
   *  Sets the ticketId attribute of the Quote object
   *
   * @param  tmp  The new ticketId value
   */
  public void setTicketId(int tmp) {
    this.ticketId = tmp;
  }


  /**
   *  Sets the ticketId attribute of the Quote object
   *
   * @param  tmp  The new ticketId value
   */
  public void setTicketId(String tmp) {
    this.ticketId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ticket attribute of the Quote object
   *
   * @param  tmp  The new ticket value
   */
  public void setTicket(Ticket tmp) {
    this.ticket = tmp;
  }


  /**
   *  Sets the buildTicket attribute of the Quote object
   *
   * @param  tmp  The new buildTicket value
   */
  public void setBuildTicket(boolean tmp) {
    this.buildTicket = tmp;
  }


  /**
   *  Sets the buildTicket attribute of the Quote object
   *
   * @param  tmp  The new buildTicket value
   */
  public void setBuildTicket(String tmp) {
    this.buildTicket = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildTicket attribute of the Quote object
   *
   * @return    The buildTicket value
   */
  public boolean getBuildTicket() {
    return buildTicket;
  }


  /**
   *  Gets the ticket attribute of the Quote object
   *
   * @return    The ticket value
   */
  public Ticket getTicket() {
    return ticket;
  }


  /**
   *  Gets the id attribute of the Quote object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the Quote object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the orgId attribute of the Quote object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the contactId attribute of the Quote object
   *
   * @return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the sourceId attribute of the Quote object
   *
   * @return    The sourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   *  Gets the grandTotal attribute of the Quote object
   *
   * @return    The grandTotal value
   */
  public double getGrandTotal() {
    return grandTotal;
  }


  /**
   *  Gets the statusId attribute of the Quote object
   *
   * @return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the statusDate attribute of the Quote object
   *
   * @return    The statusDate value
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the expirationDate attribute of the Quote object
   *
   * @return    The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the quoteTermsId attribute of the Quote object
   *
   * @return    The quoteTermsId value
   */
  public int getQuoteTermsId() {
    return quoteTermsId;
  }


  /**
   *  Gets the quoteTypeId attribute of the Quote object
   *
   * @return    The quoteTypeId value
   */
  public int getQuoteTypeId() {
    return quoteTypeId;
  }


  /**
   *  Gets the shortDescription attribute of the Quote object
   *
   * @return    The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   *  Gets the notes attribute of the Quote object
   *
   * @return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the entered attribute of the Quote object
   *
   * @return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Quote object
   *
   * @return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Quote object
   *
   * @return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Quote object
   *
   * @return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the name attribute of the Quote object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the nameLast attribute of the Quote object
   *
   * @return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the nameFirst attribute of the Quote object
   *
   * @return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameMiddle attribute of the Quote object
   *
   * @return    The nameMiddle value
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   *  Gets the buildProducts attribute of the Quote object
   *
   * @return    The buildProducts value
   */
  public boolean getBuildProducts() {
    return buildProducts;
  }


  /**
   *  Gets the productList attribute of the Quote object
   *
   * @return    The productList value
   */
  public QuoteProductList getProductList() {
    return productList;
  }


  /**
   *  Gets the ticketId attribute of the Quote object
   *
   * @return    The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   *  Gets the deliveryId attribute of the Quote object
   *
   * @return    The deliveryId value
   */
  public int getDeliveryId() {
    return deliveryId;
  }


  /**
   *  Sets the deliveryId attribute of the Quote object
   *
   * @param  tmp  The new deliveryId value
   */
  public void setDeliveryId(int tmp) {
    this.deliveryId = tmp;
  }


  /**
   *  Sets the deliveryId attribute of the Quote object
   *
   * @param  tmp  The new deliveryId value
   */
  public void setDeliveryId(String tmp) {
    this.deliveryId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the contact attribute of the Quote object
   *
   * @return    The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   *  Sets the contact attribute of the Quote object
   *
   * @param  tmp  The new contact value
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   *  Gets the organization attribute of the Quote object
   *
   * @return    The organization value
   */
  public Organization getOrganization() {
    return organization;
  }


  /**
   *  Sets the organization attribute of the Quote object
   *
   * @param  tmp  The new organization value
   */
  public void setOrganization(Organization tmp) {
    this.organization = tmp;
  }


  /**
   *  Gets the buildResources attribute of the Quote object
   *
   * @return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Sets the buildResources attribute of the Quote object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the Quote object
   *
   * @param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the history attribute of the Quote object
   *
   * @return    The history value
   */
  public QuoteLogList getHistory() {
    return history;
  }


  /**
   *  Sets the history attribute of the Quote object
   *
   * @param  tmp  The new history value
   */
  public void setHistory(QuoteLogList tmp) {
    this.history = tmp;
  }


  /**
   *  Gets the buildHistory attribute of the Quote object
   *
   * @return    The buildHistory value
   */
  public boolean getBuildHistory() {
    return buildHistory;
  }


  /**
   *  Sets the buildHistory attribute of the Quote object
   *
   * @param  tmp  The new buildHistory value
   */
  public void setBuildHistory(boolean tmp) {
    this.buildHistory = tmp;
  }


  /**
   *  Sets the buildHistory attribute of the Quote object
   *
   * @param  tmp  The new buildHistory value
   */
  public void setBuildHistory(String tmp) {
    this.buildHistory = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildConditions attribute of the Quote object
   *
   * @return    The buildConditions value
   */
  public boolean getBuildConditions() {
    return buildConditions;
  }


  /**
   *  Sets the buildConditions attribute of the Quote object
   *
   * @param  tmp  The new buildConditions value
   */
  public void setBuildConditions(boolean tmp) {
    this.buildConditions = tmp;
  }


  /**
   *  Sets the buildConditions attribute of the Quote object
   *
   * @param  tmp  The new buildConditions value
   */
  public void setBuildConditions(String tmp) {
    this.buildConditions = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the conditions attribute of the Quote object
   *
   * @return    The conditions value
   */
  public QuoteConditionList getConditions() {
    return conditions;
  }


  /**
   *  Sets the conditions attribute of the Quote object
   *
   * @param  tmp  The new conditions value
   */
  public void setConditions(QuoteConditionList tmp) {
    this.conditions = tmp;
  }


  /**
   *  Gets the buildRemarks attribute of the Quote object
   *
   * @return    The buildRemarks value
   */
  public boolean getBuildRemarks() {
    return buildRemarks;
  }


  /**
   *  Sets the buildRemarks attribute of the Quote object
   *
   * @param  tmp  The new buildRemarks value
   */
  public void setBuildRemarks(boolean tmp) {
    this.buildRemarks = tmp;
  }


  /**
   *  Sets the buildRemarks attribute of the Quote object
   *
   * @param  tmp  The new buildRemarks value
   */
  public void setBuildRemarks(String tmp) {
    this.buildRemarks = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the remarks attribute of the Quote object
   *
   * @return    The remarks value
   */
  public QuoteRemarkList getRemarks() {
    return remarks;
  }


  /**
   *  Sets the remarks attribute of the Quote object
   *
   * @param  tmp  The new remarks value
   */
  public void setRemarks(QuoteRemarkList tmp) {
    this.remarks = tmp;
  }


  /**
   *  Gets the emailAddress attribute of the Quote object
   *
   * @return    The emailAddress value
   */
  public String getEmailAddress() {
    return emailAddress;
  }


  /**
   *  Sets the emailAddress attribute of the Quote object
   *
   * @param  tmp  The new emailAddress value
   */
  public void setEmailAddress(String tmp) {
    this.emailAddress = tmp;
  }


  /**
   *  Gets the phoneNumber attribute of the Quote object
   *
   * @return    The phoneNumber value
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }


  /**
   *  Sets the phoneNumber attribute of the Quote object
   *
   * @param  tmp  The new phoneNumber value
   */
  public void setPhoneNumber(String tmp) {
    this.phoneNumber = tmp;
  }


  /**
   *  Gets the address attribute of the Quote object
   *
   * @return    The address value
   */
  public String getAddress() {
    return address;
  }


  /**
   *  Sets the address attribute of the Quote object
   *
   * @param  tmp  The new address value
   */
  public void setAddress(String tmp) {
    this.address = tmp;
  }


  /**
   *  Gets the faxNumber attribute of the Quote object
   *
   * @return    The faxNumber value
   */
  public String getFaxNumber() {
    return faxNumber;
  }


  /**
   *  Sets the faxNumber attribute of the Quote object
   *
   * @param  tmp  The new faxNumber value
   */
  public void setFaxNumber(String tmp) {
    this.faxNumber = tmp;
  }


  /**
   *  Gets the submitType attribute of the Quote object
   *
   * @return    The submitType value
   */
  public String getSubmitType() {
    return submitType;
  }


  /**
   *  Sets the submitType attribute of the Quote object
   *
   * @param  tmp  The new submitType value
   */
  public void setSubmitType(String tmp) {
    this.submitType = tmp;
  }


  /**
   *  Gets the submitAction attribute of the Quote object
   *
   * @return    The submitAction value
   */
  public int getSubmitAction() {
    return submitAction;
  }


  /**
   *  Sets the submitAction attribute of the Quote object
   *
   * @param  tmp  The new submitAction value
   */
  public void setSubmitAction(int tmp) {
    this.submitAction = tmp;
  }


  /**
   *  Sets the submitAction attribute of the Quote object
   *
   * @param  tmp  The new submitAction value
   */
  public void setSubmitAction(String tmp) {
    this.submitAction = Integer.parseInt(tmp);
  }


  /**
   *  Gets the closeIt attribute of the Quote object
   *
   * @return    The closeIt value
   */
  public boolean getCloseIt() {
    return closeIt;
  }


  /**
   *  Sets the closeIt attribute of the Quote object
   *
   * @param  tmp  The new closeIt value
   */
  public void setCloseIt(boolean tmp) {
    this.closeIt = tmp;
  }


  /**
   *  Sets the closeIt attribute of the Quote object
   *
   * @param  tmp  The new closeIt value
   */
  public void setCloseIt(String tmp) {
    this.closeIt = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the closed attribute of the Quote object
   *
   * @return    The closed value
   */
  public Timestamp getClosed() {
    return closed;
  }


  /**
   *  Sets the closed attribute of the Quote object
   *
   * @param  tmp  The new closed value
   */
  public void setClosed(Timestamp tmp) {
    this.closed = tmp;
  }


  /**
   *  Sets the closed attribute of the Quote object
   *
   * @param  tmp  The new closed value
   */
  public void setClosed(String tmp) {
    this.closed = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the canPrint attribute of the Quote object
   *
   * @return    The canPrint value
   */
  public String getCanPrint() {
    return canPrint;
  }


  /**
   *  Sets the canPrint attribute of the Quote object
   *
   * @param  tmp  The new canPrint value
   */
  public void setCanPrint(String tmp) {
    this.canPrint = tmp;
  }


  /**
   *  Gets the padded groupId attribute of the Quote object
   *
   * @return    The padded groupId value
   */
  public String getPaddedGroupId() {
    String padded = (String.valueOf(this.getGroupId()));
    while (padded.length() < 6) {
      padded = "0" + padded;
    }
    return padded;
  }


  /**
   *  Gets the showTotal attribute of the Quote object
   *
   * @return    The showTotal value
   */
  public boolean getShowTotal() {
    return showTotal;
  }


  /**
   *  Sets the showTotal attribute of the Quote object
   *
   * @param  tmp  The new showTotal value
   */
  public void setShowTotal(boolean tmp) {
    this.showTotal = tmp;
  }


  /**
   *  Sets the showTotal attribute of the Quote object
   *
   * @param  tmp  The new showTotal value
   */
  public void setShowTotal(String tmp) {
    this.showTotal = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the showSubtotal attribute of the Quote object
   *
   * @return    The showSubtotal value
   */
  public boolean getShowSubtotal() {
    return showSubtotal;
  }


  /**
   *  Sets the showSubtotal attribute of the Quote object
   *
   * @param  tmp  The new showSubtotal value
   */
  public void setShowSubtotal(boolean tmp) {
    this.showSubtotal = tmp;
  }


  /**
   *  Sets the showSubtotal attribute of the Quote object
   *
   * @param  tmp  The new showSubtotal value
   */
  public void setShowSubtotal(String tmp) {
    this.showSubtotal = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the canNotCopyExpirationDate attribute of the Quote object
   *
   * @return    The canNotCopyExpirationDate value
   */
  public boolean getCanNotCopyExpirationDate() {
    return canNotCopyExpirationDate;
  }


  /**
   *  Sets the canNotCopyExpirationDate attribute of the Quote object
   *
   * @param  tmp  The new canNotCopyExpirationDate value
   */
  public void setCanNotCopyExpirationDate(boolean tmp) {
    this.canNotCopyExpirationDate = tmp;
  }


  /**
   *  Sets the canNotCopyExpirationDate attribute of the Quote object
   *
   * @param  tmp  The new canNotCopyExpirationDate value
   */
  public void setCanNotCopyExpirationDate(String tmp) {
    this.canNotCopyExpirationDate = DatabaseUtils.parseBoolean(tmp);
  }

public String getStatusName() { return statusName; }
public void setStatusName(String tmp) { this.statusName = tmp; }

  /**
   *  Constructor for the Quote object
   */
  public Quote() { }


  /**
   *  Constructor for the Quote object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public Quote(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the Quote object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public Quote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote ID");
    }
    StringBuffer sb = new StringBuffer(
        " SELECT qe.*, " +
        " org.name, ct.namelast, ct.namefirst, ct.namemiddle, lqs.description AS statusName " +
        " FROM quote_entry qe " +
        " LEFT JOIN quote_group qg ON (qe.group_id = qg.group_id) " +
        " LEFT JOIN organization org ON (qe.org_id = org.org_id) " +
        " LEFT JOIN lookup_quote_status lqs ON ( qe.status_id = lqs.code ) " +
        " LEFT JOIN contact ct ON (qe.contact_id = ct.contact_id) " +
        " LEFT JOIN opportunity_header opp ON (qe.opp_id = opp.opp_id) " +
        " WHERE qe.quote_id = ? "
        );
    PreparedStatement pst = db.prepareStatement(sb.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Quote Entry not found");
    }
    if (buildProducts) {
      this.buildProducts(db);
    }
    if (buildResources) {
      this.buildResources(db);
    }
    if (buildHistory) {
      this.buildHistory(db);
    }
    if (buildConditions) {
      this.buildConditions(db);
    }
    if (buildRemarks) {
      this.buildRemarks(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //quote_entry table
    this.setId(rs.getInt("quote_id"));
    parentId = DatabaseUtils.getInt(rs, "parent_id");
    orgId = DatabaseUtils.getInt(rs, "org_id");
    contactId = DatabaseUtils.getInt(rs, "contact_id");
    sourceId = DatabaseUtils.getInt(rs, "source_id");
    grandTotal = DatabaseUtils.getDouble(rs, "grand_total");
    statusId = DatabaseUtils.getInt(rs, "status_id");
    statusDate = rs.getTimestamp("status_date");
    expirationDate = rs.getTimestamp("expiration_date");
    quoteTermsId = DatabaseUtils.getInt(rs, "quote_terms_id");
    quoteTypeId = DatabaseUtils.getInt(rs, "quote_type_id");
    issuedDate = rs.getTimestamp("issued");
    shortDescription = rs.getString("short_description");
    notes = rs.getString("notes");
    ticketId = DatabaseUtils.getInt(rs, "ticketid");
    entered = rs.getTimestamp("entered");
    enteredBy = rs.getInt("enteredby");
    modified = rs.getTimestamp("modified");
    modifiedBy = rs.getInt("modifiedby");
    productId = DatabaseUtils.getInt(rs, "product_id");
    customerProductId = DatabaseUtils.getInt(rs, "customer_product_id");
    headerId = DatabaseUtils.getInt(rs, "opp_id");
    version = rs.getString("version");
    groupId = DatabaseUtils.getInt(rs, "group_id");
    deliveryId = DatabaseUtils.getInt(rs, "delivery_id");
    emailAddress = rs.getString("email_address");
    phoneNumber = rs.getString("phone_number");
    address = rs.getString("address");
    faxNumber = rs.getString("fax_number");
    submitAction = DatabaseUtils.getInt(rs, "submit_action");
    closed = rs.getTimestamp("closed");
    showTotal = rs.getBoolean("show_total");
    showSubtotal = rs.getBoolean("show_subtotal");
    logoFileId = DatabaseUtils.getInt(rs, "logo_file_id");

    //Organization and contact information
    name = rs.getString("name");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameLast = rs.getString("namelast");
    statusName = rs.getString("statusName");
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildProducts(Connection db) throws SQLException {
    productList.setQuoteId(this.getId());
    productList.setBuildResources(true);
    productList.buildList(db);
    Iterator products = (Iterator) this.getProductList().iterator();
    double total = 0.0;
    while (products.hasNext()) {
      QuoteProduct product = (QuoteProduct) products.next();
      total += product.getTotalPrice();
    }
    this.setGrandTotal(total);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    if (this.getContactId() != -1) {
      contact = new Contact();
      contact.setBuildDetails(true);
      contact.setBuildTypes(true);
      contact.queryRecord(db, this.getContactId());
    }
    if (this.getOrgId() != -1) {
      organization = new Organization(db, this.getOrgId());
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRemarks(Connection db) throws SQLException {
    remarks.setQuoteId(this.getId());
    remarks.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      StringBuffer sql = new StringBuffer();
      sql.append(
          "INSERT INTO quote_entry(parent_id, org_id, contact_id, source_id, grand_total, " +
          "status_id, status_date, expiration_date, show_total, show_subtotal, logo_file_id, ");
      if (this.getCloseIt() || this.getClosed() != null) {
        sql.append("closed, ");
      }
      sql.append("quote_terms_id, quote_type_id, " +
          "issued, short_description, notes, ticketid, product_id, " +
          "customer_product_id, opp_id, version, group_id, delivery_id, " +
          "email_address, phone_number, address, fax_number, submit_action, ");
      if (entered != null) {
        sql.append("entered, ");
      }
      sql.append("enteredby, ");
      if (modified != null) {
        sql.append("modified, ");
      }
      sql.append("modifiedby )");
      sql.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (this.getClosed() != null) {
        sql.append("?, ");
      } else if (this.getCloseIt()) {
        sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
      }
      sql.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
      if (entered != null) {
        sql.append("?, ");
      }
      sql.append("?, ");
      if (modified != null) {
        sql.append("?, ");
      }
      sql.append("? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getParentId());
      DatabaseUtils.setInt(pst, ++i, this.getOrgId());
      DatabaseUtils.setInt(pst, ++i, this.getContactId());
      DatabaseUtils.setInt(pst, ++i, this.getSourceId());
      DatabaseUtils.setDouble(pst, ++i, this.getGrandTotal());
      DatabaseUtils.setInt(pst, ++i, this.getStatusId());
      DatabaseUtils.setTimestamp(pst, ++i, this.getStatusDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
      pst.setBoolean(++i, this.getShowTotal());
      pst.setBoolean(++i, this.getShowSubtotal());
      DatabaseUtils.setInt(pst, ++i, this.getLogoFileId());
      if (this.getClosed() != null) {
        DatabaseUtils.setTimestamp(pst, ++i, this.getClosed());
      }
      DatabaseUtils.setInt(pst, ++i, this.getQuoteTermsId());
      DatabaseUtils.setInt(pst, ++i, this.getQuoteTypeId());
      DatabaseUtils.setTimestamp(pst, ++i, this.getIssuedDate());
      pst.setString(++i, this.getShortDescription());
      pst.setString(++i, this.getNotes());
      DatabaseUtils.setInt(pst, ++i, this.getTicketId());
      DatabaseUtils.setInt(pst, ++i, this.getProductId());
      DatabaseUtils.setInt(pst, ++i, this.getCustomerProductId());
      DatabaseUtils.setInt(pst, ++i, this.getHeaderId());
      pst.setString(++i, this.getVersion());
      DatabaseUtils.setInt(pst, ++i, this.getGroupId());
      DatabaseUtils.setInt(pst, ++i, this.getDeliveryId());
      pst.setString(++i, this.getEmailAddress());
      pst.setString(++i, this.getPhoneNumber());
      pst.setString(++i, this.getAddress());
      pst.setString(++i, this.getFaxNumber());
      DatabaseUtils.setInt(pst, ++i, this.getSubmitAction());
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      pst.setInt(++i, this.getEnteredBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      pst.setInt(++i, this.getModifiedBy());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "quote_entry_quote_id_seq");
      if (this.getEntered() == null) {
        updateEntry(db);
      }

      // insert the quote products
      productList.insert(db);

      // insert the quote notes
      QuoteNote quoteNote = new QuoteNote();
      quoteNote.setQuoteId(this.getId());
      quoteNote.setNotes("New quote created");
      quoteNote.setEnteredBy(this.getEnteredBy());
      quoteNote.setModifiedBy(this.getModifiedBy());
      quoteNote.insert(db);
      if (commit) {
        db.commit();
      }
      result = true;
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote ID not specified");
    }
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      // delete all the line items associated with this quote
      this.buildProducts(db);
      productList.delete(db);
      productList = null;

      //delete the quote notes associated with this quote
      QuoteNoteList noteList = new QuoteNoteList();
      noteList.setQuoteId(this.getId());
      noteList.buildList(db);
      noteList.delete(db);

      //delete the quote history associated with this quote
      history.setQuoteId(this.getId());
      history.setDoSystemMessages(false);
      buildHistory(db);
      this.getHistory().delete(db);

      //delete the quote conditions associated with this quote
      QuoteConditionList conditions = new QuoteConditionList();
      conditions.setQuoteId(this.getId());
      conditions.buildList(db);
      conditions.delete(db);

      //delete the quote remarks associated with this quote
      QuoteRemarkList remarks = new QuoteRemarkList();
      remarks.setQuoteId(this.getId());
      remarks.buildList(db);
      remarks.delete(db);

      PreparedStatement pst = null;
      ResultSet rs = null;

      /*
       *  update the child quote's parent id
       *  The root quote has the highest version number for a quote group.
       *  The child quote is the next numerical version of the current quote.
       *  New versions are added to the root quotes only.
       *  Adding a version to the version 2 will result in a version 5 if there is already a version 4.
       *  On deleting a quote, its parent quote's parent_id (parent of) is set to the next numerical version's quote_id.
       */
      Quote childQuote = new Quote();
      int childQuoteId = -1;
      pst = db.prepareStatement(
          " SELECT qe.quote_id AS quote_id " +
          " FROM quote_entry qe " +
          " LEFT JOIN quote_entry pqe " +
          " ON ( qe.parent_id = pqe.quote_id ) " +
          " WHERE pqe.quote_id = ? "
          );
      pst.setInt(1, this.getId());
      rs = pst.executeQuery();

      if (rs.next()) {
        childQuoteId = rs.getInt("quote_id");
      }
      rs.close();
      pst.close();
      if (childQuoteId != -1) {
        childQuote.queryRecord(db, childQuoteId);
        childQuote.setParentId(this.getParentId());
        childQuote.update(db);
      }

      // delete the quote
      pst = db.prepareStatement("DELETE FROM quote_entry WHERE quote_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      QuoteList quoteList = new QuoteList();
      quoteList.setGroupId(this.getGroupId());
      quoteList.buildList(db);
      if (quoteList.size() == 0) {
        pst = db.prepareStatement("DELETE FROM quote_group WHERE group_id = ? ");
        pst.setInt(1, this.getGroupId());
        pst.execute();
        pst.close();
      }
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE quote_entry " +
        " SET parent_id = ?, " +
        " org_id = ?, " +
        " contact_id = ?, " +
        " source_id = ?, " +
        " grand_total = ?, " +
        " status_id = ?, " +
        " status_date = ?, " +
        " expiration_date = ?, " +
        " show_total = ?, " +
        " show_subtotal = ?, logo_file_id = ?, ");
    if (this.getClosed() != null) {
      sql.append(" closed = ?, ");
    } else if (this.getCloseIt()) {
      sql.append(" closed = " + DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append(" quote_terms_id = ?, " +
        " quote_type_id = ?, " +
        " issued = ?, " +
        " short_description = ?, " +
        " notes = ?, " +
        " ticketid = ?, " +
        " product_id = ?, " +
        " customer_product_id = ?, " +
        " opp_id = ?, " +
        " version = ?, " +
        " group_id = ?, " +
        " delivery_id = ?, " +
        " email_address = ?, " +
        " phone_number = ?, " +
        " address = ?, " +
        " fax_number = ?, " +
        " submit_action = ?, " +
        " modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        " modifiedby = ? " +
        " WHERE quote_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    DatabaseUtils.setInt(pst, ++i, this.getOrgId());
    DatabaseUtils.setInt(pst, ++i, this.getContactId());
    DatabaseUtils.setInt(pst, ++i, this.getSourceId());
    DatabaseUtils.setDouble(pst, ++i, this.getGrandTotal());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setTimestamp(++i, this.getStatusDate());
    pst.setTimestamp(++i, this.getExpirationDate());
    pst.setBoolean(++i, this.getShowTotal());
    pst.setBoolean(++i, this.getShowSubtotal());
    DatabaseUtils.setInt(pst, ++i, this.getLogoFileId());
    if (this.getClosed() != null) {
      pst.setTimestamp(++i, this.getClosed());
    }
    DatabaseUtils.setInt(pst, ++i, this.getQuoteTermsId());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteTypeId());
    pst.setTimestamp(++i, this.getIssuedDate());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getNotes());
    DatabaseUtils.setInt(pst, ++i, this.getTicketId());
    DatabaseUtils.setInt(pst, ++i, this.getProductId());
    DatabaseUtils.setInt(pst, ++i, this.getCustomerProductId());
    DatabaseUtils.setInt(pst, ++i, this.getHeaderId());
    pst.setString(++i, this.getVersion());
    DatabaseUtils.setInt(pst, ++i, this.getGroupId());
    DatabaseUtils.setInt(pst, ++i, this.getDeliveryId());
    pst.setString(++i, this.getEmailAddress());
    pst.setString(++i, this.getPhoneNumber());
    pst.setString(++i, this.getAddress());
    pst.setString(++i, this.getFaxNumber());
    DatabaseUtils.setInt(pst, ++i, this.getSubmitAction());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    updateEntry(db);
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote ID not specified");
    }
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    // Check for this quote's existence in a parent role
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as parentcount " +
          "FROM quote_entry " +
          "WHERE parent_id = ? "
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int parentCount = rs.getInt("parentcount");
        if (parentCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("numberOfParentsOfThisQuote");
          thisDependency.setCount(parentCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    // Check for orders that are based off this quote
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) as recordcount " +
          " FROM order_entry " +
          " WHERE quote_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("numberOfOrdersBasedOnThisQuote");
          thisDependency.setCount(recordCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    // Check for the History of this quote
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) as recordcount " +
          " FROM quotelog " +
          " WHERE quote_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("HistoryOfThisQuote");
          thisDependency.setCount(recordCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    // Check for quote conditions for this quote
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) as recordcount " +
          " FROM quote_condition " +
          " WHERE quote_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int recordCount = rs.getInt("recordcount");
        if (recordCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("NumberOfConditionsForThisQuote");
          thisDependency.setCount(recordCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }

    //Check the products that are associated with this quote
    try {
      i = 0;
      pst = db.prepareStatement(
          " SELECT count(*) AS productcount " +
          " FROM quote_product qp " +
          " WHERE qp.quote_id = ? ");
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int productCount = rs.getInt("productcount");
        if (productCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("numberOfProductsAssociatedWithThisQuote");
          thisDependency.setCount(productCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
      throw new SQLException(e.getMessage());
    }
    return dependencyList;
  }


  /**
   *  Gets the timeZoneParams attribute of the Quote class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {

    ArrayList thisList = new ArrayList();
    thisList.add("statusDate");
    thisList.add("issuedDate");
    thisList.add("closed");
    thisList.add("expirationDate");
    thisList.add("entered");
    thisList.add("modified");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void retrieveTicket(Connection db) throws SQLException {
    if (ticketId != -1) {
      ticket = new Ticket(db, ticketId);
    }
  }


  /**
   *  Gets the orderId attribute of the Quote object
   *
   * @param  db                Description of the Parameter
   * @return                   The orderId value
   * @exception  SQLException  Description of the Exception
   */
  public int getOrderId(Connection db) throws SQLException {
    int orderId = -1;
    if (this.getId() == -1) {
      throw new SQLException("Error: Incorrect usage");
    }
    PreparedStatement pst = db.prepareStatement(
        " SELECT oe.order_id as order_id " +
        " FROM order_entry oe LEFT JOIN quote_entry qe ON ( oe.quote_id = qe.quote_id ) " +
        " WHERE oe.quote_id = ? "
        );
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      orderId = rs.getInt("order_id");
    }
    pst.close();
    rs.close();

    return orderId;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildVersionList(Connection db) throws SQLException {
    versionList = new QuoteList();
    versionList.setParentId(this.getId());
    versionList.setTopOnly(Constants.UNDEFINED);
    versionList.setBuildCompleteVersionList(this.getBuildCompleteVersionList());
    versionList.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  copyQuote         Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public Quote copyQuote(Connection db, Quote copyQuote) throws SQLException {
    copyQuote.setBuildCompleteVersionList(this.getBuildCompleteVersionList());
    copyQuote.setBuildProducts(this.getBuildProducts());
    copyQuote.setBuildTicket(this.getBuildTicket());
    copyQuote.setShowTotal(this.getShowTotal());
    copyQuote.setShowSubtotal(this.getShowSubtotal());
    if (copyQuote.getContactId() == -1) {
      copyQuote.setContactId(this.getContactId());
    }
    if (copyQuote.getCustomerProductId() == -1) {
      copyQuote.setCustomerProductId(this.getCustomerProductId());
    }
    if (copyQuote.getEnteredBy() == -1) {
      copyQuote.setEnteredBy(this.getEnteredBy());
    }
    if (!copyQuote.getCanNotCopyExpirationDate()) {
      copyQuote.setExpirationDate(this.getExpirationDate());
    }
    copyQuote.setGrandTotal(this.getGrandTotal());
    if (copyQuote.getHeaderId() == -1) {
      copyQuote.setHeaderId(this.getHeaderId());
    }
    if (copyQuote.getModifiedBy() == -1) {
      copyQuote.setModifiedBy(this.getModifiedBy());
    }
    copyQuote.setNotes(copyQuote.getNotes() + this.getNotes());
    if (copyQuote.getOrgId() == -1) {
      copyQuote.setOrgId(this.getOrgId());
    }
    if (copyQuote.getProductId() == -1) {
      copyQuote.setProductId(this.getProductId());
    }
    if (copyQuote.getQuoteTermsId() == -1) {
      copyQuote.setQuoteTermsId(this.getQuoteTermsId());
    }
    if (copyQuote.getQuoteTypeId() == -1) {
      copyQuote.setQuoteTypeId(this.getQuoteTypeId());
    }
    if (copyQuote.getShortDescription() == null || "".equals(copyQuote.getShortDescription())) {
      copyQuote.setShortDescription(this.getShortDescription());
    }
    if (copyQuote.getSourceId() == -1) {
      copyQuote.setSourceId(this.getSourceId());
    }
    if (copyQuote.getStatusId() == -1) {
      copyQuote.setStatusId(this.getStatusId());
      copyQuote.setStatusDate(this.getStatusDate());
    }
    if (copyQuote.getTicketId() == -1) {
      copyQuote.setTicketId(this.getTicketId());
    }
    if (copyQuote.getVersion() == null || "".equals(copyQuote.getVersion())) {
      copyQuote.setVersion(this.getVersion());
    }
    if (copyQuote.getDeliveryId() == -1) {
      copyQuote.setDeliveryId(this.getDeliveryId());
    }
    if (copyQuote.getAddress() == null || "".equals(copyQuote.getAddress())) {
      copyQuote.setAddress(this.getAddress());
    }
    if (copyQuote.getEmailAddress() == null || "".equals(copyQuote.getEmailAddress())) {
      copyQuote.setEmailAddress(this.getEmailAddress());
    }
    if (copyQuote.getPhoneNumber() == null || "".equals(copyQuote.getPhoneNumber())) {
      copyQuote.setPhoneNumber(this.getPhoneNumber());
    }
    if (copyQuote.getFaxNumber() == null || "".equals(copyQuote.getFaxNumber())) {
      copyQuote.setFaxNumber(this.getFaxNumber());
    }
    if (copyQuote.getLogoFileId() == -1) {
      copyQuote.setLogoFileId(this.getLogoFileId());
    }

    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      copyQuote.insert(db);
      //copy the quote remarks
      Iterator remarkIterator = (Iterator) remarks.iterator();
      while (remarkIterator.hasNext()) {
        QuoteRemark quoteRemark = (QuoteRemark) remarkIterator.next();
        quoteRemark.copyQuoteRemark(db, copyQuote.getId());
      }
      //copy the quote terms & conditions
      Iterator conditionsIterator = (Iterator) conditions.iterator();
      while (conditionsIterator.hasNext()) {
        QuoteCondition quoteCondition = (QuoteCondition) conditionsIterator.next();
        quoteCondition.copyQuoteCondition(db, copyQuote.getId());
      }
      //copy quote products if they exist
      Iterator productIterator = (Iterator) productList.iterator();
      while (productIterator.hasNext()) {
        QuoteProduct quoteProduct = (QuoteProduct) productIterator.next();
        quoteProduct.copyQuoteProduct(db, copyQuote.getId());
      }
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return copyQuote;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public Quote clone(Connection db) throws SQLException {
    Quote newQuote = null;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      Quote clone = new Quote();
      clone.setVersion(clone.getNewVersion());
      clone.setNotes("Cloned from Quote #" + this.getGroupId() + " version " + this.getVersion() + "\n\n");
      clone.createNewGroup(db);
      newQuote = this.copyQuote(db, clone);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        System.out.println("Quote-> Clone failed..roll back");
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return newQuote;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  clone             Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public Quote clone(Connection db, Quote clone) throws SQLException {
    Quote newQuote = null;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }

      String oldNotes = clone.getNotes();
      clone.setNotes("Cloned from Quote #" + this.getGroupId() + " version " + this.getVersion() + "\n\n");
      if (oldNotes != null && !"".equals(oldNotes)) {
        clone.setNotes(clone.getNotes() + oldNotes + "\n");
      }
      clone.setVersion(clone.getNewVersion());
      clone.createNewGroup(db);
      newQuote = this.copyQuote(db, clone);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        System.out.println("Quote-> CLONE FAILED..ROLL BACK");
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return newQuote;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public Quote addVersion(Connection db) throws SQLException {
    Quote verQuote = new Quote();
    Quote rootQuote = new Quote(db, this.getRootQuote(db, this.getParentId()));
    verQuote.setVersion(rootQuote.getNewVersion());
    verQuote.setNotes("Version derived from Quote #" + this.getGroupId() + " version " + this.getVersion() + "\n\n");
    verQuote.setGroupId(this.getGroupId());
    verQuote = this.copyQuote(db, verQuote);
    rootQuote.setParentId(verQuote.getId());
    rootQuote.update(db);
    return verQuote;
  }


  /**
   *  Adds a feature to the Version attribute of the Quote object
   *
   * @param  db                The feature to be added to the Version attribute
   * @param  verQuote          The feature to be added to the Version attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public Quote addVersion(Connection db, Quote verQuote) throws SQLException {
    Quote rootQuote = new Quote(db, this.getRootQuote(db, this.getParentId()));
    verQuote.setVersion(rootQuote.getNewVersion());
    String oldNotes = verQuote.getNotes();
    verQuote.setNotes("Version derived from Quote #" + this.getGroupId() + " version " + this.getVersion() + "\n\n");
    if (oldNotes != null && !"".equals(oldNotes)) {
      verQuote.setNotes(verQuote.getNotes() + oldNotes + "\n");
    }
    verQuote.setGroupId(this.getGroupId());
    verQuote = this.copyQuote(db, verQuote);
    rootQuote.setParentId(verQuote.getId());
    rootQuote.update(db);
    return verQuote;
  }


  /**
   *  Gets the newVersion attribute of the Quote object
   *
   * @return    The newVersion value
   */
  public String getNewVersion() {
    return new String((getVersionNumber() + 1) + ".0");
  }


  /**
   *  Gets the versionNumber attribute of the Quote object
   *
   * @return    The versionNumber value
   */
  public int getVersionNumber() {
    return (int) Math.round(Double.parseDouble(this.getVersion()));
  }


  /**
   *  Gets the rootQuote attribute of the Quote object
   *
   * @param  db                Description of the Parameter
   * @param  parentId          Description of the Parameter
   * @return                   The rootQuote value
   * @exception  SQLException  Description of the Exception
   */
  public int getRootQuote(Connection db, int parentId) throws SQLException {
    int result = -1;
    if (parentId == -1) {
      result = this.getId();
    } else {
      Quote rootQuote = new Quote(db, parentId);
      if (rootQuote.getParentId() != -1) {
        result = rootQuote.getRootQuote(db, rootQuote.getParentId());
      } else {
        result = rootQuote.getId();
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void createNewGroup(Connection db) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement("INSERT INTO quote_group (unused) VALUES (null) ");
    pst.execute();
    pst.close();
    this.setGroupId(DatabaseUtils.getCurrVal(db, "quote_group_group_id_seq"));
  }


  /**
   *  Gets the numberParams attribute of the Quote class
   *
   * @return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("grand_total");
    return thisList;
  }


  /**
   *  Method to build the history of the quote
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildHistory(Connection db) throws SQLException {
    this.history.setQuoteId(this.getId());
    this.history.buildList(db);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildConditions(Connection db) throws SQLException {
    QuoteConditionList condtns = new QuoteConditionList();
    condtns.setQuoteId(this.getId());
    condtns.buildList(db);
    this.setConditions(condtns);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void updateEntry(Connection db) throws SQLException {
    QuoteLog thisEntry = new QuoteLog();
    thisEntry.setQuoteId(this.getId());
    thisEntry.setEnteredBy(this.getModifiedBy());
    thisEntry.setStatusId(this.getStatusId());
    thisEntry.setSourceId(this.getSourceId());
    thisEntry.setTermsId(this.getQuoteTermsId());
    thisEntry.setTypeId(this.getQuoteTypeId());
    thisEntry.setDeliveryId(this.getDeliveryId());
    thisEntry.setIssuedDate(this.getIssuedDate());
    thisEntry.setGrandTotal(this.getGrandTotal());
    if (this.getCloseIt() && this.getClosed() == null) {
      thisEntry.setClosed(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    } else {
      thisEntry.setClosed(this.getClosed());
    }
    thisEntry.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
    /*
     *  history.add(thisEntry);
     *  Iterator hist = history.iterator();
     *  while (hist.hasNext()) {
     *  QuoteLog thisLog = (QuoteLog) hist.next();
     *  thisLog.process(db, this.getId(), this.getEnteredBy(), this.getModifiedBy());
     *  }
     */
  }


  /**
   *  Gets the submitActionString attribute of the Quote object
   *
   * @return    The submitActionString value
   */
  public String getSubmitActionString() {
    if (this.getSubmitAction() == Quote.EMAIL) {
      return "Email";
    } else if (this.getSubmitAction() == Quote.FAX) {
      return "Fax";
    } else if (this.getSubmitAction() == Quote.PRINT) {
      return "Print";
    }
    return "Error";
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasExpired() {
    if (this.getExpirationDate() != null && this.getExpirationDate().before(Calendar.getInstance().getTime())) {
      return true;
    }
    return false;
  }
}

