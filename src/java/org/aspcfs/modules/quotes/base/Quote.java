package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.troubletickets.base.*;
/**
 *  This represents a Quote in the Quote Entry System
 *
 *@author     ananth
 *@created    March 24, 2004
 *@version    $Id$
 */
public class Quote extends GenericBean {
  private int id = -1;
  private int parentId = -1;
  private int orgId = -1;
  private int quoteId = -1;
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


  /**
   *  Sets the id attribute of the Quote object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Quote object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the Quote object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the Quote object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the orgId attribute of the Quote object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the Quote object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quoteId attribute of the Quote object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the Quote object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contactId attribute of the Quote object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }


  /**
   *  Sets the contactId attribute of the Quote object
   *
   *@param  tmp  The new contactId value
   */
  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the sourceId attribute of the Quote object
   *
   *@param  tmp  The new sourceId value
   */
  public void setSourceId(int tmp) {
    this.sourceId = tmp;
  }


  /**
   *  Sets the sourceId attribute of the Quote object
   *
   *@param  tmp  The new sourceId value
   */
  public void setSourceId(String tmp) {
    this.sourceId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the grandTotal attribute of the Quote object
   *
   *@param  tmp  The new grandTotal value
   */
  public void setGrandTotal(double tmp) {
    this.grandTotal = tmp;
  }


  /**
   *  Sets the grandTotal attribute of the Quote object
   *
   *@param  tmp  The new grandTotal value
   */
  public void setGrandTotal(String tmp) {
    this.grandTotal = Double.parseDouble(tmp);
  }


  /**
   *  Sets the statusId attribute of the Quote object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   *  Sets the statusId attribute of the Quote object
   *
   *@param  tmp  The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the statusDate attribute of the Quote object
   *
   *@param  tmp  The new statusDate value
   */
  public void setStatusDate(Timestamp tmp) {
    this.statusDate = tmp;
  }


  /**
   *  Sets the statusDate attribute of the Quote object
   *
   *@param  tmp  The new statusDate value
   */
  public void setStatusDate(String tmp) {
    this.statusDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the issuedDate attribute of the Quote object
   *
   *@param  tmp  The new issuedDate value
   */
  public void setIssuedDate(Timestamp tmp) {
    this.issuedDate = tmp;
  }


  /**
   *  Sets the issuedDate attribute of the Quote object
   *
   *@param  tmp  The new issuedDate value
   */
  public void setIssuedDate(String tmp) {
    this.issuedDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the issuedDate attribute of the Quote object
   *
   *@return    The issuedDate value
   */
  public Timestamp getIssuedDate() {
    return issuedDate;
  }


  /**
   *  Sets the expirationDate attribute of the Quote object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the Quote object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the quoteTermsId attribute of the Quote object
   *
   *@param  tmp  The new quoteTermsId value
   */
  public void setQuoteTermsId(int tmp) {
    this.quoteTermsId = tmp;
  }


  /**
   *  Sets the quoteTermsId attribute of the Quote object
   *
   *@param  tmp  The new quoteTermsId value
   */
  public void setQuoteTermsId(String tmp) {
    this.quoteTermsId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quoteTypeId attribute of the Quote object
   *
   *@param  tmp  The new quoteTypeId value
   */
  public void setQuoteTypeId(int tmp) {
    this.quoteTypeId = tmp;
  }


  /**
   *  Sets the quoteTypeId attribute of the Quote object
   *
   *@param  tmp  The new quoteTypeId value
   */
  public void setQuoteTypeId(String tmp) {
    this.quoteTypeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the shortDescription attribute of the Quote object
   *
   *@param  tmp  The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   *  Sets the notes attribute of the Quote object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the entered attribute of the Quote object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Quote object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the Quote object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the Quote object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the Quote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the Quote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the Quote object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the Quote object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the Quote object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Quote object
   *
   *@param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Quote object
   *
   *@param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameMiddle attribute of the Quote object
   *
   *@param  tmp  The new nameMiddle value
   */
  public void setNameMiddle(String tmp) {
    this.nameMiddle = tmp;
  }


  /**
   *  Sets the buildProducts attribute of the Quote object
   *
   *@param  tmp  The new buildProducts value
   */
  public void setBuildProducts(boolean tmp) {
    this.buildProducts = tmp;
  }


  /**
   *  Sets the buildProducts attribute of the Quote object
   *
   *@param  tmp  The new buildProducts value
   */
  public void setBuildProducts(String tmp) {
    this.buildProducts = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the productList attribute of the Quote object
   *
   *@param  tmp  The new productList value
   */
  public void setProductList(QuoteProductList tmp) {
    this.productList = tmp;
  }


  /**
   *  Sets the ticketId attribute of the Quote object
   *
   *@param  tmp  The new ticketId value
   */
  public void setTicketId(int tmp) {
    this.ticketId = tmp;
  }


  /**
   *  Sets the ticketId attribute of the Quote object
   *
   *@param  tmp  The new ticketId value
   */
  public void setTicketId(String tmp) {
    this.ticketId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ticket attribute of the Quote object
   *
   *@param  tmp  The new ticket value
   */
  public void setTicket(Ticket tmp) {
    this.ticket = tmp;
  }


  /**
   *  Sets the buildTicket attribute of the Quote object
   *
   *@param  tmp  The new buildTicket value
   */
  public void setBuildTicket(boolean tmp) {
    this.buildTicket = tmp;
  }


  /**
   *  Sets the buildTicket attribute of the Quote object
   *
   *@param  tmp  The new buildTicket value
   */
  public void setBuildTicket(String tmp) {
    this.buildTicket = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildTicket attribute of the Quote object
   *
   *@return    The buildTicket value
   */
  public boolean getBuildTicket() {
    return buildTicket;
  }


  /**
   *  Gets the ticket attribute of the Quote object
   *
   *@return    The ticket value
   */
  public Ticket getTicket() {
    return ticket;
  }


  /**
   *  Gets the id attribute of the Quote object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the Quote object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the orgId attribute of the Quote object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the quoteId attribute of the Quote object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Gets the contactId attribute of the Quote object
   *
   *@return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the sourceId attribute of the Quote object
   *
   *@return    The sourceId value
   */
  public int getSourceId() {
    return sourceId;
  }


  /**
   *  Gets the grandTotal attribute of the Quote object
   *
   *@return    The grandTotal value
   */
  public double getGrandTotal() {
    return grandTotal;
  }


  /**
   *  Gets the statusId attribute of the Quote object
   *
   *@return    The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   *  Gets the statusDate attribute of the Quote object
   *
   *@return    The statusDate value
   */
  public Timestamp getStatusDate() {
    return statusDate;
  }


  /**
   *  Gets the expirationDate attribute of the Quote object
   *
   *@return    The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the quoteTermsId attribute of the Quote object
   *
   *@return    The quoteTermsId value
   */
  public int getQuoteTermsId() {
    return quoteTermsId;
  }


  /**
   *  Gets the quoteTypeId attribute of the Quote object
   *
   *@return    The quoteTypeId value
   */
  public int getQuoteTypeId() {
    return quoteTypeId;
  }


  /**
   *  Gets the shortDescription attribute of the Quote object
   *
   *@return    The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   *  Gets the notes attribute of the Quote object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the entered attribute of the Quote object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the enteredBy attribute of the Quote object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modified attribute of the Quote object
   *
   *@return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the modifiedBy attribute of the Quote object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the name attribute of the Quote object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the nameLast attribute of the Quote object
   *
   *@return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the nameFirst attribute of the Quote object
   *
   *@return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameMiddle attribute of the Quote object
   *
   *@return    The nameMiddle value
   */
  public String getNameMiddle() {
    return nameMiddle;
  }


  /**
   *  Gets the buildProducts attribute of the Quote object
   *
   *@return    The buildProducts value
   */
  public boolean getBuildProducts() {
    return buildProducts;
  }


  /**
   *  Gets the productList attribute of the Quote object
   *
   *@return    The productList value
   */
  public QuoteProductList getProductList() {
    return productList;
  }


  /**
   *  Gets the ticketId attribute of the Quote object
   *
   *@return    The ticketId value
   */
  public int getTicketId() {
    return ticketId;
  }


  /**
   *  Constructor for the Quote object
   */
  public Quote() { }


  /**
   *  Constructor for the Quote object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Quote(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the Quote object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public Quote(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Quote ID");
    }
    StringBuffer sb = new StringBuffer(
        " SELECT qe.*, " +
        " org.name, ct.namelast, ct.namefirst, ct.namemiddle " +
        " FROM quote_entry qe " +
        " LEFT JOIN organization org ON (qe.org_id = org.org_id) " +
        " LEFT JOIN contact ct ON (qe.contact_id = ct.contact_id) " +
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
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //quote_entry table
    this.setId(rs.getInt("quote_id"));
    parentId = DatabaseUtils.getInt(rs, "parent_id");
    orgId = rs.getInt("org_id");
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

    //Organization and contact information
    name = rs.getString("name");
    nameFirst = rs.getString("namefirst");
    nameMiddle = rs.getString("namemiddle");
    nameLast = rs.getString("namelast");
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildProducts(Connection db) throws SQLException {
    productList.setQuoteId(this.getId());
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
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    if (!isValid(db)) {
      return result;
    }

    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO quote_entry(parent_id, org_id, contact_id, source_id, grand_total, " +
        "status_id, status_date, expiration_date, quote_terms_id, quote_type_id, " +
        "issued, " +
        "short_description, notes, ticketid, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enteredby, ");
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("modifiedby )");
    sql.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
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
    pst.setInt(++i, this.getOrgId());
    DatabaseUtils.setInt(pst, ++i, this.getContactId());
    DatabaseUtils.setInt(pst, ++i, this.getSourceId());
    DatabaseUtils.setDouble(pst, ++i, this.getGrandTotal());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStatusDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteTermsId());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteTypeId());
    DatabaseUtils.setTimestamp(pst, ++i, this.getIssuedDate());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getNotes());
    DatabaseUtils.setInt(pst, ++i, this.getTicketId());
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

    // insert the quote products
    productList.insert(db);
    result = true;
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote ID not specified");
    }
    try {
      db.setAutoCommit(false);
      // delete all the line items associated with this quote
      this.buildProducts(db);
      productList.delete(db);
      productList = null;

      // delete the quote
      PreparedStatement pst = db.prepareStatement("DELETE FROM quote_entry WHERE quote_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      db.commit();
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();

    sql.append("UPDATE quote_entry " +
        " SET contact_id = ?, " +
        "     source_id = ?, " +
        "     grand_total = ?, " +
        "     status_id = ?, " +
        "     status_date = ?, " +
        "     expiration_date = ?, " +
        "     quote_terms_id = ?, " +
        "     quote_type_id = ?, " +
        "     short_description = ?, " +
        "     notes = ?, " +
        "     ticketid = ?, " +
        "     entered = ?, " +
        "     enteredby = ?, " +
        "     modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        "     modifiedby = ? " +
        " WHERE quote_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getContactId());
    DatabaseUtils.setInt(pst, ++i, this.getSourceId());
    DatabaseUtils.setDouble(pst, ++i, this.getGrandTotal());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setTimestamp(++i, this.getStatusDate());
    pst.setTimestamp(++i, this.getExpirationDate());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteTermsId());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteTypeId());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getNotes());
    DatabaseUtils.setInt(pst, ++i, this.getTicketId());
    pst.setTimestamp(++i, this.getEntered());
    pst.setInt(++i, this.getEnteredBy());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Quote ID not specified");
    }
    String sql = null;
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
          thisDependency.setName("Number of children of this quote ");
          thisDependency.setCount(parentCount);
          thisDependency.setCanDelete(false);
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
          thisDependency.setName("Number of orders based on this quote ");
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
          thisDependency.setName("Number of products associated with this quote ");
          thisDependency.setCount(productCount);
          thisDependency.setCanDelete(false);
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
   *  Gets the valid attribute of the Quote object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  protected boolean isValid(Connection db) throws SQLException {
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void retrieveTicket(Connection db) throws SQLException {
    ticket = new Ticket(db, ticketId);
  }
}

