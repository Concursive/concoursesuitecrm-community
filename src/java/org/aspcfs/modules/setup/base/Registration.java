//Copyright 2001-2003 Dark Horse Ventures

package org.aspcfs.modules.setup.base;

import com.darkhorseventures.framework.beans.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Bean to encapsulate the user's registration information
 *
 *@author     mrajkowski
 *@created    November 20, 2003
 *@version    $Id$
 */
public class Registration extends GenericBean {
  private int id = -1;
  private String nameFirst = null;
  private String nameLast = null;
  private String company = null;
  private String email = null;
  private String profile = null;
  private String text = null;
  private String os = null;
  private String java = null;
  private String webserver = null;
  private String ip = null;
  private String keyFile = null;
  private boolean enabled = true;
  private Timestamp entered = null;


  /**
   *  Sets the id attribute of the Registration object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the Registration object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the keyFile attribute of the Registration object
   *
   *@param  tmp  The new keyFile value
   */
  public void setKeyFile(String tmp) {
    this.keyFile = tmp;
  }


  /**
   *  Sets the nameFirst attribute of the Registration object
   *
   *@param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Sets the nameLast attribute of the Registration object
   *
   *@param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }


  /**
   *  Sets the company attribute of the Registration object
   *
   *@param  tmp  The new company value
   */
  public void setCompany(String tmp) {
    this.company = tmp;
  }


  /**
   *  Sets the email attribute of the Registration object
   *
   *@param  tmp  The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   *  Sets the profile attribute of the Registration object
   *
   *@param  tmp  The new profile value
   */
  public void setProfile(String tmp) {
    this.profile = tmp;
  }


  /**
   *  Sets the text attribute of the Registration object
   *
   *@param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   *  Sets the os attribute of the Registration object
   *
   *@param  tmp  The new os value
   */
  public void setOs(String tmp) {
    this.os = tmp;
  }


  /**
   *  Sets the java attribute of the Registration object
   *
   *@param  tmp  The new java value
   */
  public void setJava(String tmp) {
    this.java = tmp;
  }


  /**
   *  Sets the webserver attribute of the Registration object
   *
   *@param  tmp  The new webserver value
   */
  public void setWebserver(String tmp) {
    this.webserver = tmp;
  }



  /**
   *  Sets the ip attribute of the Registration object
   *
   *@param  tmp  The new ip value
   */
  public void setIp(String tmp) {
    this.ip = tmp;
  }


  /**
   *  Sets the enabled attribute of the Registration object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the Registration object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the entered attribute of the Registration object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the Registration object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Gets the id attribute of the Registration object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the keyFile attribute of the Registration object
   *
   *@return    The keyFile value
   */
  public String getKeyFile() {
    return keyFile;
  }


  /**
   *  Gets the nameFirst attribute of the Registration object
   *
   *@return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Gets the nameLast attribute of the Registration object
   *
   *@return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Gets the company attribute of the Registration object
   *
   *@return    The company value
   */
  public String getCompany() {
    return company;
  }


  /**
   *  Gets the email attribute of the Registration object
   *
   *@return    The email value
   */
  public String getEmail() {
    return email;
  }


  /**
   *  Gets the profile attribute of the Registration object
   *
   *@return    The profile value
   */
  public String getProfile() {
    return profile;
  }


  /**
   *  Gets the text attribute of the Registration object
   *
   *@return    The text value
   */
  public String getText() {
    return text;
  }


  /**
   *  Gets the os attribute of the Registration object
   *
   *@return    The os value
   */
  public String getOs() {
    return os;
  }


  /**
   *  Gets the java attribute of the Registration object
   *
   *@return    The java value
   */
  public String getJava() {
    return java;
  }


  /**
   *  Gets the webserver attribute of the Registration object
   *
   *@return    The webserver value
   */
  public String getWebserver() {
    return webserver;
  }


  /**
   *  Gets the ip attribute of the Registration object
   *
   *@return    The ip value
   */
  public String getIp() {
    return ip;
  }


  /**
   *  Gets the enabled attribute of the Registration object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the entered attribute of the Registration object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO registration (email, profile, name_first, name_last, " +
        "company, registration_text, os_version, java_version, webserver, " +
        "ip_address, " +
        "key_file, enabled) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    int i = 0;
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
    pst.setString(++i, keyFile);
    pst.setBoolean(++i, enabled);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "registration_registration_id_seq");
  }
}

