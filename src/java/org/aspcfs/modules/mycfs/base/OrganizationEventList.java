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
package org.aspcfs.modules.mycfs.base;

import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id: OrganizationEventList.java,v 1.2 2004/08/04 20:01:56
 *          mrajkowski Exp $
 * @created October 24, 2003
 */
public class OrganizationEventList {
  public final static int ACCOUNT_EVENT_ALERT = 1;
  public final static int ACCOUNT_CONTRACT_ALERT = 2;

  OrganizationList alertOrgs = new OrganizationList();
  OrganizationList contractEndOrgs = new OrganizationList();
  int size = 0;


  /**
   * Sets the alertOrgs attribute of the OrganizationEventList object
   *
   * @param alertOrgs The new alertOrgs value
   */
  public void setAlertOrgs(OrganizationList alertOrgs) {
    this.alertOrgs = alertOrgs;
  }


  /**
   * Sets the contractEndOrgs attribute of the OrganizationEventList object
   *
   * @param contractEndOrgs The new contractEndOrgs value
   */
  public void setContractEndOrgs(OrganizationList contractEndOrgs) {
    this.contractEndOrgs = contractEndOrgs;
  }


  /**
   * Gets the alertOrgs attribute of the OrganizationEventList object
   *
   * @return The alertOrgs value
   */
  public OrganizationList getAlertOrgs() {
    return alertOrgs;
  }


  /**
   * Gets the contractEndOrgs attribute of the OrganizationEventList object
   *
   * @return The contractEndOrgs value
   */
  public OrganizationList getContractEndOrgs() {
    return contractEndOrgs;
  }


  /**
   * Sets the size attribute of the OrganizationEventList object
   *
   * @param size The new size value
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   * Sets the size attribute of the OrganizationEventList object
   *
   * @param size The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   * Gets the size attribute of the OrganizationEventList object
   *
   * @return The size value
   */
  public int getSize() {
    return size;
  }


  /**
   * Gets the sizeString attribute of the OrganizationEventList object
   *
   * @return The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   * Adds a call to the list depending on the status of the Organization
   *
   * @param thisOrg The feature to be added to the Event attribute
   */
  public void addEvent(Organization thisOrg) {
    if (thisOrg != null) {
      if (thisOrg.getAlertDate() != null) {
        alertOrgs.add(thisOrg);
      }
      if (thisOrg.getContractEndDate() != null) {
        contractEndOrgs.add(thisOrg);
      }
    }
  }
}

