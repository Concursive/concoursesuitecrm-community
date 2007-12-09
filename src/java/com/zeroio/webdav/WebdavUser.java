/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.webdav;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created November 2, 2004
 */
public class WebdavUser {
  private int userId = -1;
  private int roleId = -1;
  private String digest = null;
  private String nonce = null;


  /**
   * Sets the nonce attribute of the WebdavUser object
   *
   * @param tmp The new nonce value
   */
  public void setNonce(String tmp) {
    this.nonce = tmp;
  }


  /**
   * Gets the nonce attribute of the WebdavUser object
   *
   * @return The nonce value
   */
  public String getNonce() {
    return nonce;
  }


  /**
   * Sets the digest attribute of the WebdavUser object
   *
   * @param tmp The new digest value
   */
  public void setDigest(String tmp) {
    this.digest = tmp;
  }


  /**
   * Gets the digest attribute of the WebdavUser object
   *
   * @return The digest value
   */
  public String getDigest() {
    return digest;
  }


  /**
   * Sets the userId attribute of the WebdavUser object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }


  /**
   * Sets the userId attribute of the WebdavUser object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }


  /**
   * Sets the roleId attribute of the WebdavUser object
   *
   * @param tmp The new roleId value
   */
  public void setRoleId(int tmp) {
    this.roleId = tmp;
  }


  /**
   * Sets the roleId attribute of the WebdavUser object
   *
   * @param tmp The new roleId value
   */
  public void setRoleId(String tmp) {
    this.roleId = Integer.parseInt(tmp);
  }


  /**
   * Gets the userId attribute of the WebdavUser object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }


  /**
   * Gets the roleId attribute of the WebdavUser object
   *
   * @return The roleId value
   */
  public int getRoleId() {
    return roleId;
  }


  /**
   * Constructor for the WebdavUser object
   */
  public WebdavUser() {
  }


  /**
   * Constructor for the WebdavUser object
   *
   * @param userId Description of the Parameter
   * @param roleId Description of the Parameter
   */
  public WebdavUser(int userId, int roleId) {
    this.userId = userId;
    this.roleId = roleId;
  }
}

