package org.aspcfs.modules.orders.beans;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created May 15, 2004
 */
public class StatusBean extends GenericBean {
  private int id = -1;
  private int statusId = -1;
  private String authorizationRefNumber = null;
  private String authorizationCode = null;
  private Timestamp authorizationDate = null;


  /**
   * Sets the id attribute of the StatusBean object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the StatusBean object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the statusId attribute of the StatusBean object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(int tmp) {
    this.statusId = tmp;
  }


  /**
   * Sets the statusId attribute of the StatusBean object
   *
   * @param tmp The new statusId value
   */
  public void setStatusId(String tmp) {
    this.statusId = Integer.parseInt(tmp);
  }


  /**
   * Sets the authorizationRefNumber attribute of the StatusBean object
   *
   * @param tmp The new authorizationRefNumber value
   */
  public void setAuthorizationRefNumber(String tmp) {
    this.authorizationRefNumber = tmp;
  }


  /**
   * Sets the authorizationCode attribute of the StatusBean object
   *
   * @param tmp The new authorizationCode value
   */
  public void setAuthorizationCode(String tmp) {
    this.authorizationCode = tmp;
  }


  /**
   * Sets the authorizationDate attribute of the StatusBean object
   *
   * @param tmp The new authorizationDate value
   */
  public void setAuthorizationDate(Timestamp tmp) {
    this.authorizationDate = tmp;
  }


  /**
   * Sets the authorizationDate attribute of the StatusBean object
   *
   * @param tmp The new authorizationDate value
   */
  public void setAuthorizationDate(String tmp) {
    this.authorizationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Gets the id attribute of the StatusBean object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the statusId attribute of the StatusBean object
   *
   * @return The statusId value
   */
  public int getStatusId() {
    return statusId;
  }


  /**
   * Gets the authorizationRefNumber attribute of the StatusBean object
   *
   * @return The authorizationRefNumber value
   */
  public String getAuthorizationRefNumber() {
    return authorizationRefNumber;
  }


  /**
   * Gets the authorizationCode attribute of the StatusBean object
   *
   * @return The authorizationCode value
   */
  public String getAuthorizationCode() {
    return authorizationCode;
  }


  /**
   * Gets the authorizationDate attribute of the StatusBean object
   *
   * @return The authorizationDate value
   */
  public Timestamp getAuthorizationDate() {
    return authorizationDate;
  }

  public static ArrayList getTimeZoneParams() {

    ArrayList thisList = new ArrayList();
    thisList.add("authorizationDate");
    return thisList;
  }


}

