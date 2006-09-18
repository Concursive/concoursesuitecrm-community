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
package org.aspcfs.servlets.url.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;

import com.darkhorseventures.framework.beans.GenericBean;

/**
 * Description of the Class
 * 
 * @author holub
 * @version $Id:$
 * @created Jul 17, 2006
 * 
 */
public class URLMap extends GenericBean {

  private static final long serialVersionUID = -5854362050325558717L;

  private int id = -1;

  private long timeInMillis = -1;

  private String url = null;

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Sets the id attribute of the URLMap object
   *
   * @param tmp
   *            The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * @return the timeInMillis
   */
  public long getTimeInMillis() {
    return timeInMillis;
  }

  /**
   * @param timeInMillis
   *          the timeInMillis to set
   */
  public void setTimeInMillis(long timeInMillis) {
    this.timeInMillis = timeInMillis;
  }

  /**
   * Sets the timeInMillis attribute of the URLMap object
   *
   * @param tmp
   *            The new timeInMillis value
   */
  public void setTimeInMillis(String tmp) {
    this.timeInMillis = Long.parseLong(tmp);
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Constructor for the URLMap object
   */
  public URLMap() {
  }

  /**
   * Constructor for the URLMap object
   *
   * @param db
   *            Description of the Parameter
   * @param id
   *            Description of the Parameter
   * @exception SQLException
   *                Description of the Exception
   * @throws SQLException
   *             Description of the Exception
   */
  public URLMap(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }

  /**
   * Constructor for the ProductCatalog object
   *
   * @param rs
   *            Description of the Parameter
   * @exception SQLException
   *                Description of the Exception
   * @throws SQLException
   *             Description of the Exception
   */
  public URLMap(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }

  /**
   * Description of the Method
   *
   * @param db
   *            Description of the Parameter
   * @param id
   *            Description of the Parameter
   * @throws SQLException
   *             Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid URL MAP Number");
    }
    try {
      PreparedStatement pst = db.prepareStatement("SELECT urlmp.* "
          + "FROM url_map urlmp " + "WHERE urlmp.url_id = ? ");
      pst.setInt(1, id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        buildRecord(rs);
      }
      rs.close();
      pst.close();
    } catch (Exception e) {
      // TODO: handle exception
    }
    if (this.getId() == -1) {
      throw new SQLException(Constants.NOT_FOUND_ERROR);
    }
  }

  /**
   * Description of the Method
   *
   * @param rs
   *            Description of the Parameter
   * @throws SQLException
   *             Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // url_map table
    this.setId(rs.getInt("url_id"));
    this.setTimeInMillis(rs.getLong("time_in_millis"));
    this.setUrl(rs.getString("url"));
  }

  /**
   * Description of the Method
   *
   * @param db
   *            Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *             Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "url_map_url_id_seq");
      StringBuffer sql = new StringBuffer();
      sql.append(" INSERT INTO url_map (");
      if (id > -1) {
        sql.append(" url_id,");
      }
      sql.append(" url, time_in_millis )");

      sql.append("VALUES(");
      if (id > -1) {
        sql.append(" ?,");
      }
      sql.append(" ?, ? )");

      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      if (id > -1) {
        pst.setInt(++i, this.getId());
      }
      pst.setString(++i, this.getUrl());
      if (timeInMillis == -1) {
        timeInMillis = System.currentTimeMillis();
      }
      pst.setLong(++i, this.getTimeInMillis());

      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "url_map_url_id_seq", id);
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
   * Description of the Method
   *
   * @return Prepare and return the URL alias
   */
  public String getURLAlias() {
    return "/url" + "/" + (new Long(this.timeInMillis)).toString() + "/"
        + (new Integer(this.id)).toString();
  }
}
