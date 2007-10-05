package org.aspcfs.modules.orders.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 12, 2004
 */
public class OrderPaymentStatus extends GenericBean {
  private int id = -1;
  private int paymentId = -1;
  private int statusId = -1;

  private Timestamp entered = null;
  private int enteredBy = -1;
  private Timestamp modified = null;
  private int modifiedBy = -1;
  //resources
  private String status = null;


  /**
   * Sets the id attribute of the OrderPaymentStatus object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the OrderPaymentStatus object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the paymentId attribute of the OrderPaymentStatus object
   *
   * @param tmp The new paymentId value
   */
  public void setPaymentId(int tmp) {
    this.paymentId = tmp;
  }


  /**
   * Sets the paymentId attribute of the OrderPaymentStatus object
   *
   * @param tmp The new paymentId value
   */
  public void setPaymentId(String tmp) {
    this.paymentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusId attribute of the OrderPaymentStatus object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the OrderPaymentStatus object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the entered attribute of the OrderPaymentStatus object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the OrderPaymentStatus object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the enteredBy attribute of the OrderPaymentStatus object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   * Sets the enteredBy attribute of the OrderPaymentStatus object
   *
   * @param tmp The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the modified attribute of the OrderPaymentStatus object
   *
   * @param tmp The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the OrderPaymentStatus object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the modifiedBy attribute of the OrderPaymentStatus object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   * Sets the modifiedBy attribute of the OrderPaymentStatus object
   *
   * @param tmp The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   * Sets the status attribute of the OrderPaymentStatus object
   *
   * @param tmp The new status value
   */
  public void setStatus(String tmp) {
    this.status = tmp;
  }


  /**
   * Gets the id attribute of the OrderPaymentStatus object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the paymentId attribute of the OrderPaymentStatus object
   *
   * @return The paymentId value
   */
  public int getPaymentId() {
    return paymentId;
  }


  /**
   * Gets the statusId attribute of the OrderPaymentStatus object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the entered attribute of the OrderPaymentStatus object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the enteredBy attribute of the OrderPaymentStatus object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modified attribute of the OrderPaymentStatus object
   *
   * @return The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the modifiedBy attribute of the OrderPaymentStatus object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the status attribute of the OrderPaymentStatus object
   *
   * @return The status value
   */
  public String getStatus() {
    return status;
  }


  /**
   * Constructor for the OrderPaymentStatus object
   *
   * @throws SQLException Description of the Exception
   */
  public OrderPaymentStatus() throws SQLException {
  }


  /**
   * Constructor for the OrderPaymentStatus object
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderPaymentStatus(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   * Constructor for the OrderPaymentStatus object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public OrderPaymentStatus(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @param id Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id < 0) {
      throw new SQLException("Incorrect Payment Status Number ");
    }

    PreparedStatement pst = db.prepareStatement(
        " SELECT ops.*, ps.description AS status_description " +
        " FROM order_payment_status ops " +
        " LEFT JOIN lookup_payment_status ps " +
        " ON ( ops.status_id = ps.code ) " +
        " WHERE ops.payment_status_id = ? ");
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Payment Status Entry Not Found");
    }
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //order_payment_status table
    this.setId(rs.getInt("payment_status_id"));
    this.setPaymentId(rs.getInt("payment_id"));
    this.setStatusId(DatabaseUtils.getInt(rs, "status_id"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setEnteredBy(DatabaseUtils.getInt(rs, "enteredby"));
    this.setModified(rs.getTimestamp("modified"));
    this.setModifiedBy(DatabaseUtils.getInt(rs, "modifiedby"));

    //lookup_payment_status table
    this.setStatus(rs.getString("status_description"));
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    if (!isValid(db)) {
      return result;
    }

    StringBuffer sql = new StringBuffer();
    id = DatabaseUtils.getNextSeq(
        db, "order_payment_status_payment_status_id_seq");
    sql.append("INSERT INTO order_payment_status (payment_id, status_id, ");
    if (id > -1) {
      sql.append("payment_status_id, ");
    }
    sql.append("entered, ");
    sql.append("enteredby, ");
    sql.append("modified, ");
    sql.append("modifiedby) ");
    sql.append("VALUES( ?, ?, ");
    if (id > -1) {
      sql.append("?, ");
    }
    if (entered != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("?, ");
    if (modified != null) {
      sql.append("?, ");
    } else {
      sql.append(DatabaseUtils.getCurrentTimestamp(db) + ", ");
    }
    sql.append("? ) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getPaymentId());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    if (id > -1) {
      pst.setInt(++i, id);
    }
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
    id = DatabaseUtils.getCurrVal(
        db, "order_payment_status_payment_status_id_seq", id);
    result = true;
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Order Payment Status not specified ");
    }
    try {
      PreparedStatement pst = null;
      pst = db.prepareStatement(
          " DELETE FROM order_payment_status WHERE payment_status_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
    } catch (Exception e) {
      e.printStackTrace(System.out);
      throw new SQLException(e.getMessage());
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE order_payment_status " +
        " SET payment_id = ?, " +
        " status_id = ?, " +
        " modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " +
        " modifiedby = ? " +
        " WHERE payment_status_id = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getPaymentId());
    DatabaseUtils.setInt(pst, ++i, this.getStatusId());
    pst.setInt(++i, this.getModifiedBy());
    pst.setInt(++i, this.getId());

    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   * Gets the valid attribute of the OrderPaymentStatus object
   *
   * @param db Description of the Parameter
   * @return The valid value
   * @throws SQLException Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
    return true;
  }
}

