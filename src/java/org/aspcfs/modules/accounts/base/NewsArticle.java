package org.aspcfs.modules.accounts.base;

import com.darkhorseventures.database.Connection;
import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    August 3, 2001
 *@version    $Id$
 */
public class NewsArticle extends GenericBean {

  protected String errorMessage = "";
  protected int recId = -1;
  protected int orgId = -1;
  protected String base = "";
  protected String headline = "";
  protected String dateEntered = "";
  protected String dateCreated = "";
  protected String url = "";

  protected SimpleDateFormat shortDateFormat = new SimpleDateFormat("M/d/yyyy");
  protected SimpleDateFormat shortTimeFormat = new SimpleDateFormat("h:mm a");
  protected SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
  protected SimpleDateFormat longDateTimeFormat = new SimpleDateFormat("MMMMM d, yyyy hh:mm a");
  protected SimpleDateFormat longDateFormat = new SimpleDateFormat("MMMMM d, yyyy");
  protected SimpleDateFormat longTimeFormat = new SimpleDateFormat("hh:mm a");


  /**
   *  Constructor for the NewsArticle object
   */
  public NewsArticle() { }


  /**
   *  Constructor for the NewsArticle object
   *
   *@param  db                Description of the Parameter
   *@param  newsId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public NewsArticle(Connection db, String newsId) throws SQLException {
    queryRecord(db, Integer.parseInt(newsId));
  }


  /**
   *  Constructor for the Contact object
   *
   *@param  db                Description of the Parameter
   *@param  newsId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public NewsArticle(Connection db, int newsId) throws SQLException {
    queryRecord(db, newsId);
  }


  /**
   *  Constructor for the NewsArticle object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of the Exception
   */
  public NewsArticle(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  newsId            Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void queryRecord(Connection db, int newsId) throws SQLException {
    if (newsId < 0) {
      throw new SQLException("News ID not specified.");
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT n.* " +
    //"o.name as org_name, o.industry_temp_code as org_industry, o.miner_only as org_mineronly " +
        "FROM news n " +
        "LEFT JOIN organization o ON (n.org_id = o.org_id) " +
        "WHERE n.rec_id = ? ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(1, newsId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    } else {
      rs.close();
      pst.close();
      throw new SQLException("News record not found.");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Sets the ErrorMessage attribute of the NewsArticle object
   *
   *@param  tmp  The new ErrorMessage value
   */
  public void setErrorMessage(String tmp) {
    this.errorMessage = tmp;
  }


  /**
   *  Sets the url attribute of the NewsArticle object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the Base attribute of the NewsArticle object
   *
   *@param  tmp  The new Base value
   */
  public void setBase(String tmp) {
    this.base = tmp;
  }


  /**
   *  Sets the recId attribute of the NewsArticle object
   *
   *@param  tmp  The new recId value
   */
  public void setRecId(int tmp) {
    this.recId = tmp;
  }


  /**
   *  Sets the orgId attribute of the NewsArticle object
   *
   *@param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the Headline attribute of the NewsArticle object
   *
   *@param  tmp  The new Headline value
   */
  public void setHeadline(String tmp) {
    this.headline = tmp;
  }


  /**
   *  Sets the DateEntered attribute of the NewsArticle object
   *
   *@param  tmp  The new DateEntered value
   */
  public void setDateEntered(String tmp) {
    this.dateEntered = tmp;
  }


  /**
   *  Sets the DateCreated attribute of the NewsArticle object
   *
   *@param  tmp  The new DateCreated value
   */
  public void setDateCreated(String tmp) {
    this.dateCreated = tmp;
  }


  /**
   *  Gets the recId attribute of the NewsArticle object
   *
   *@return    The recId value
   */
  public int getRecId() {
    return recId;
  }


  /**
   *  Gets the orgId attribute of the NewsArticle object
   *
   *@return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the ErrorMessage attribute of the NewsArticle object
   *
   *@return    The ErrorMessage value
   */
  public String getErrorMessage() {
    return errorMessage;
  }


  /**
   *  Gets the Base attribute of the NewsArticle object
   *
   *@return    The Base value
   */
  public String getBase() {
    return base;
  }


  /**
   *  Gets the Headline attribute of the NewsArticle object
   *
   *@return    The Headline value
   */
  public String getHeadline() {
    return headline;
  }


  /**
   *  Gets the DateEntered attribute of the NewsArticle object
   *
   *@return    The DateEntered value
   */
  public String getDateEntered() {
    return dateEntered;
  }


  /**
   *  Gets the DateCreated attribute of the NewsArticle object
   *
   *@return    The DateCreated value
   */
  public String getDateCreated() {
    return dateCreated;
  }


  /**
   *  Gets the DateCreated attribute of the NewsArticle object
   *
   *@return    The DateCreated value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    recId = rs.getInt("rec_id");
    orgId = rs.getInt("org_id");
    url = rs.getString("url");
    base = rs.getString("base");
    headline = rs.getString("headline");
    dateEntered = rs.getString("dateentered");
    java.sql.Timestamp tmpDateCreated = rs.getTimestamp("created");
    if (tmpDateCreated != null) {
      dateEntered = shortDateTimeFormat.format(tmpDateCreated);
    } else {
      dateEntered = "";
    }
  }

}

