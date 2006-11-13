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
package org.aspcfs.modules.setup.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Bean to encapsulate the user's registration information
 *
 * @author mrajkowski
 * @version $Id$
 * @created November 20, 2003
 */
public class Registration extends GenericBean {
  private int id = -1;
  private String nameFirst = null;
  private String nameLast = null;
  private String company = null;
  private String email = null;
  private String profile = null;
  private String text = null;
  private String text2 = null;
  private String os = null;
  private String java = null;
  private String webserver = null;
  private String ip = null;
  private String keyFile = null;
  private String keyHex = null;
  private boolean enabled = true;
  private Timestamp entered = null;
  private String edition = null;


  /**
   * Constructor for the Registration object
   */
  public Registration() {
  }


  /**
   * Constructor for the Registration object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Registration(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Sets the id attribute of the Registration object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the Registration object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the keyFile attribute of the Registration object
   *
   * @param tmp The new keyFile value
   */
  public void setKeyFile(String tmp) {
    this.keyFile = tmp;
  }


  public void setKeyHex(String keyHex) {
    this.keyHex = keyHex;
  }

  /**
   * Sets the nameFirst attribute of the Registration object
   *
   * @param tmp The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   * Sets the nameLast attribute of the Registration object
   *
   * @param tmp The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   * Sets the company attribute of the Registration object
   *
   * @param tmp The new company value
   */
  public void setCompany(String tmp) {
    this.company = tmp;
  }


  /**
   * Sets the email attribute of the Registration object
   *
   * @param tmp The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   * Sets the profile attribute of the Registration object
   *
   * @param tmp The new profile value
   */
  public void setProfile(String tmp) {
    this.profile = tmp;
  }


  /**
   * Sets the text attribute of the Registration object
   *
   * @param tmp The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   * Sets the text2 attribute of the Registration object
   *
   * @param tmp The new text2 value
   */
  public void setText2(String tmp) {
    this.text2 = tmp;
  }


  /**
   * Sets the os attribute of the Registration object
   *
   * @param tmp The new os value
   */
  public void setOs(String tmp) {
    this.os = tmp;
  }


  /**
   * Sets the java attribute of the Registration object
   *
   * @param tmp The new java value
   */
  public void setJava(String tmp) {
    this.java = tmp;
  }


  /**
   * Sets the webserver attribute of the Registration object
   *
   * @param tmp The new webserver value
   */
  public void setWebserver(String tmp) {
    this.webserver = tmp;
  }


  /**
   * Sets the ip attribute of the Registration object
   *
   * @param tmp The new ip value
   */
  public void setIp(String tmp) {
    this.ip = tmp;
  }


  /**
   * Sets the enabled attribute of the Registration object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the Registration object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the entered attribute of the Registration object
   *
   * @param tmp The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the Registration object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   * Sets the edition attribute of the Registration object
   *
   * @param tmp The new edition value
   */
  public void setEdition(String tmp) {
    this.edition = tmp;
  }


  /**
   * Gets the id attribute of the Registration object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the keyFile attribute of the Registration object
   *
   * @return The keyFile value
   */
  public String getKeyFile() {
    return keyFile;
  }


  public String getKeyHex() {
    return keyHex;
  }

  /**
   * Gets the nameFirst attribute of the Registration object
   *
   * @return The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   * Gets the nameLast attribute of the Registration object
   *
   * @return The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   * Gets the company attribute of the Registration object
   *
   * @return The company value
   */
  public String getCompany() {
    return company;
  }


  /**
   * Gets the email attribute of the Registration object
   *
   * @return The email value
   */
  public String getEmail() {
    return email;
  }


  /**
   * Gets the profile attribute of the Registration object
   *
   * @return The profile value
   */
  public String getProfile() {
    return profile;
  }


  /**
   * Gets the text attribute of the Registration object
   *
   * @return The text value
   */
  public String getText() {
    return text;
  }


  /**
   * Gets the text2 attribute of the Registration object
   *
   * @return The text2 value
   */
  public String getText2() {
    return text2;
  }


  /**
   * Gets the os attribute of the Registration object
   *
   * @return The os value
   */
  public String getOs() {
    return os;
  }


  /**
   * Gets the java attribute of the Registration object
   *
   * @return The java value
   */
  public String getJava() {
    return java;
  }


  /**
   * Gets the webserver attribute of the Registration object
   *
   * @return The webserver value
   */
  public String getWebserver() {
    return webserver;
  }


  /**
   * Gets the ip attribute of the Registration object
   *
   * @return The ip value
   */
  public String getIp() {
    return ip;
  }


  /**
   * Gets the enabled attribute of the Registration object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the entered attribute of the Registration object
   *
   * @return The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the edition attribute of the Registration object
   *
   * @return The edition value
   */
  public String getEdition() {
    return edition;
  }


  /**
   * Description of the Method
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    //registration table
    id = rs.getInt("registration_id");
    email = rs.getString("email");
    profile = rs.getString("profile");
    nameFirst = rs.getString("name_first");
    nameLast = rs.getString("name_last");
    company = rs.getString("company");
    text = rs.getString("registration_text");
    os = rs.getString("os_version");
    java = rs.getString("java_version");
    webserver = rs.getString("webserver");
    ip = rs.getString("ip_address");
    edition = rs.getString("edition");
    text2 = rs.getString("crc");
    keyFile = rs.getString("key_file");
    enabled = rs.getBoolean("enabled");
    entered = rs.getTimestamp("entered");
    keyHex = rs.getString("key_hex");
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "registration_registration_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO registration " +
        "(" + (id > -1 ? "registration_id, " : "") + "email, profile, name_first, name_last, " +
        "company, registration_text, os_version, java_version, webserver, " +
        "ip_address, edition, crc, " +
        "key_file, key_hex, enabled) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setString(++i, email);
    pst.setString(++i, profile);
    pst.setString(++i, nameFirst);
    pst.setString(++i, nameLast);
    pst.setString(++i, company);
    pst.setString(++i, text);
    pst.setString(++i, os);
    pst.setString(++i, java);
    pst.setString(++i, webserver);
    pst.setString(++i, ip);
    pst.setString(++i, edition);
    pst.setString(++i, text2);
    pst.setString(++i, keyFile);
    pst.setString(++i, keyHex);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "registration_registration_id_seq", id);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void updateEnabled(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE registration " +
        "SET enabled = ? " +
        "WHERE registration_id = ? ");
    pst.setBoolean(1, enabled);
    pst.setInt(2, id);
    pst.executeUpdate();
    pst.close();
  }
}
