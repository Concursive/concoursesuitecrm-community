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
package org.aspcfs.modules.accounts.webservices.beans;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

/**
 *  Filters that can be specified to restrict the list of accounts
 *
 * @author     Ananth
 * @version
 * @created    August 1, 2006
 */
public class AccountFilters extends GenericBean {
  public int orgId = -1;
  public int ownerId = -1;
  public String accountName = null;
  public String since = null;
  public String to = null;


  /**
   *  Gets the orgId attribute of the AccountFilters object
   *
   * @return    The orgId value
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Sets the orgId attribute of the AccountFilters object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }


  /**
   *  Sets the orgId attribute of the AccountFilters object
   *
   * @param  tmp  The new orgId value
   */
  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the ownerId attribute of the AccountFilters object
   *
   * @return    The ownerId value
   */
  public int getOwnerId() {
    return ownerId;
  }


  /**
   *  Sets the ownerId attribute of the AccountFilters object
   *
   * @param  tmp  The new ownerId value
   */
  public void setOwnerId(int tmp) {
    this.ownerId = tmp;
  }


  /**
   *  Sets the ownerId attribute of the AccountFilters object
   *
   * @param  tmp  The new ownerId value
   */
  public void setOwnerId(String tmp) {
    this.ownerId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the accountName attribute of the AccountFilters object
   *
   * @return    The accountName value
   */
  public String getAccountName() {
    return accountName;
  }


  /**
   *  Sets the accountName attribute of the AccountFilters object
   *
   * @param  tmp  The new accountName value
   */
  public void setAccountName(String tmp) {
    this.accountName = tmp;
  }


  /**
   *  Gets the since attribute of the AccountFilters object
   *
   * @return    The since value
   */
  public String getSince() {
    return since;
  }


  /**
   *  Sets the since attribute of the AccountFilters object
   *
   * @param  tmp  The new since value
   */
  public void setSince(String tmp) {
    this.since = tmp;
  }


  /**
   *  Gets the to attribute of the AccountFilters object
   *
   * @return    The to value
   */
  public String getTo() {
    return to;
  }


  /**
   *  Sets the to attribute of the AccountFilters object
   *
   * @param  tmp  The new to value
   */
  public void setTo(String tmp) {
    this.to = tmp;
  }


}

