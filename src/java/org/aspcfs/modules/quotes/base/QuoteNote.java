package org.aspcfs.modules.quotes.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

public class QuoteNote extends GenericBean {
  private int id = -1;
  private int quoteId = -1;
  private String notes = null;
  private int enteredBy = -1;
  private Timestamp entered = null;
  private int modifiedBy = -1;
  private Timestamp modified = null;


  /**
   *  Sets the id attribute of the QuoteNote object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteNote object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the quoteId attribute of the QuoteNote object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(int tmp) {
    this.quoteId = tmp;
  }


  /**
   *  Sets the quoteId attribute of the QuoteNote object
   *
   *@param  tmp  The new quoteId value
   */
  public void setQuoteId(String tmp) {
    this.quoteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the notes attribute of the QuoteNote object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the QuoteNote object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the QuoteNote object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the QuoteNote object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the QuoteNote object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteNote object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the QuoteNote object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modified attribute of the QuoteNote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the QuoteNote object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the id attribute of the QuoteNote object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the quoteId attribute of the QuoteNote object
   *
   *@return    The quoteId value
   */
  public int getQuoteId() {
    return quoteId;
  }


  /**
   *  Gets the notes attribute of the QuoteNote object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the enteredBy attribute of the QuoteNote object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the entered attribute of the QuoteNote object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modifiedBy attribute of the QuoteNote object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the modified attribute of the QuoteNote object
   *
   *@return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Constructor for the QuoteNote object
   *
   */
  public QuoteNote() { }


  /**
   *  Constructor for the QuoteNote object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteNote(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the QuoteNote object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public QuoteNote(ResultSet rs) throws SQLException {
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
      throw new SQLException("Invalid Quote id");
    }
    StringBuffer buffer = new StringBuffer(
        " SELECT n.* " +
        " FROM quote_notes n " +
        " WHERE n.quote_id = ? "
        );
    PreparedStatement pst = db.prepareStatement(buffer.toString());
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Quote Notes entry not found");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //quote_notes table
    this.setId(rs.getInt("notes_id"));
    this.setQuoteId(DatabaseUtils.getInt(rs, "quote_id"));
    this.setNotes(rs.getString("notes"));

    this.setEnteredBy(rs.getInt("enteredby"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setModifiedBy(rs.getInt("modifiedby"));
    this.setModified(rs.getTimestamp("modified"));
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
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO quote_notes( " +
        "quote_id, notes, ");
    if (entered != null) {
      sql.append("entered, ");
    }
    sql.append("enteredby, ");
    if (modified != null) {
      sql.append("modified, ");
    }
    sql.append("modifiedby ) ");
    sql.append("VALUES (?, ?, ");
    if (entered != null) {
      sql.append(" ?, ");
    }
    sql.append(" ?, ");
    if (modified != null) {
      sql.append(" ?, ");
    }
    sql.append(" ? ) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteId());
    pst.setString(++i, this.getNotes());
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
    id = DatabaseUtils.getCurrVal(db, "quote_notes_notes_id_seq");

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
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append("UPDATE quote_notes " +
        " SET quote_id = ?, " +
        " notes = ?, " +
        " modified = " + DatabaseUtils.getCurrentTimestamp(db) + " , " +
        " modifiedby = ? " +
        " WHERE notes_id = ? "
        );
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getQuoteId());
    pst.setString(++i, this.getNotes());
    DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
    DatabaseUtils.setInt(pst, ++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "DELETE FROM quote_notes " +
        "WHERE quote_id = ? " +
        "AND notes_id = ? "
        );
    pst.setInt(1, this.getQuoteId());
    pst.setInt(2, this.getId());
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  quoteId           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void delete(Connection db, int quoteId, int notesId) throws SQLException {
    PreparedStatement pst = null;
    pst = db.prepareStatement(
        "DELETE FROM quote_notes " +
        "WHERE quote_id = ? " +
        "AND notes_id = ? "
    );
    pst.setInt(1, quoteId);
    pst.setInt(2, notesId);
    pst.execute();
    pst.close();
  }
}

