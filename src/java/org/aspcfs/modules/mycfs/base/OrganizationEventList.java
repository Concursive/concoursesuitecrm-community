package org.aspcfs.modules.mycfs.base;

import java.util.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    October 24, 2003
 *@version    $Id$
 */
public class OrganizationEventList {
  OrganizationList alertOrgs = new OrganizationList();
  OrganizationList contractEndOrgs = new OrganizationList();
  int size = 0;


  /**
   *  Sets the alertOrgs attribute of the OrganizationEventList object
   *
   *@param  alertOrgs  The new alertOrgs value
   */
  public void setAlertOrgs(OrganizationList alertOrgs) {
    this.alertOrgs = alertOrgs;
  }


  /**
   *  Sets the contractEndOrgs attribute of the OrganizationEventList object
   *
   *@param  contractEndOrgs  The new contractEndOrgs value
   */
  public void setContractEndOrgs(OrganizationList contractEndOrgs) {
    this.contractEndOrgs = contractEndOrgs;
  }


  /**
   *  Gets the alertOrgs attribute of the OrganizationEventList object
   *
   *@return    The alertOrgs value
   */
  public OrganizationList getAlertOrgs() {
    return alertOrgs;
  }


  /**
   *  Gets the contractEndOrgs attribute of the OrganizationEventList object
   *
   *@return    The contractEndOrgs value
   */
  public OrganizationList getContractEndOrgs() {
    return contractEndOrgs;
  }


  /**
   *  Sets the size attribute of the OrganizationEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   *  Sets the size attribute of the OrganizationEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   *  Gets the size attribute of the OrganizationEventList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the sizeString attribute of the OrganizationEventList object
   *
   *@return    The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   *  Adds a call to the list depending on the status of the Organization
   *
   *@param  thisOrg  The feature to be added to the Event attribute
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

