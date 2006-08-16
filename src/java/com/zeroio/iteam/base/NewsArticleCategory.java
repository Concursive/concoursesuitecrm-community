package com.zeroio.iteam.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: NewsArticleCategory.java,v 1.1.2.1 2004/08/26 15:54:32 matt
 *          Exp $
 * @created August 25, 2004
 */
public class NewsArticleCategory extends GenericBean {
  private int id = -1;
  private int projectId = -1;
  private String name = null;
  private boolean enabled = true;
  private int level = -1;


  /**
   * Constructor for the NewsArticleCategory object
   */
  public NewsArticleCategory() {
  }


  /**
   * Constructor for the NewsArticle object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public NewsArticleCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the id attribute of the NewsArticleCategory object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Sets the id attribute of the NewsArticleCategory object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the NewsArticleCategory object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Gets the projectId attribute of the NewsArticleCategory object
   *
   * @return The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   * Sets the projectId attribute of the NewsArticleCategory object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   * Sets the projectId attribute of the NewsArticleCategory object
   *
   * @param tmp The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   * Gets the name attribute of the NewsArticleCategory object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Sets the name attribute of the NewsArticleCategory object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Gets the enabled attribute of the NewsArticleCategory object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Sets the enabled attribute of the NewsArticleCategory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the NewsArticleCategory object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Gets the level attribute of the NewsArticleCategory object
   *
   * @return The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Sets the level attribute of the NewsArticleCategory object
   *
   * @param tmp The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the NewsArticleCategory object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("category_id");
    projectId = rs.getInt("project_id");
    name = rs.getString("category_name");
    enabled = rs.getBoolean("enabled");
    level = rs.getInt("level");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "project_news_category_category_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO project_news_category " +
        "(" + (id > -1 ? "category_id, " : "") + "project_id, category_name, enabled, " + DatabaseUtils.addQuotes(db, "level") + ") VALUES " +
        "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, projectId);
    pst.setString(++i, name);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, level);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db, "project_news_category_category_id_seq", id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void update(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE project_news_category " +
        "SET project_id = ?, category_name = ?, enabled = ?, " + DatabaseUtils.addQuotes(db, "level") + " = ? " +
        "WHERE category_id = ? ");
    int i = 0;
    pst.setInt(++i, projectId);
    pst.setString(++i, name);
    pst.setBoolean(++i, enabled);
    pst.setInt(++i, level);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
  }
}

