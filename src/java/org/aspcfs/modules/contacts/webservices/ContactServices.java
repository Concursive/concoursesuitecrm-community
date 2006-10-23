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
package org.aspcfs.modules.contacts.webservices;

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.contacts.base.ContactEmailAddress;
import org.aspcfs.modules.contacts.base.ContactPhoneNumber;
import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.utils.CRMConnection;
import org.aspcfs.utils.XMLUtils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    July 21, 2006
 */
public class ContactServices {
  private CRMConnection crm = new CRMConnection();
  
  /**
   *  Sets the authenticationInfo attribute of the CentricServices object
   *
   * @param  auth  The new authenticationInfo value
   */
  public void setAuthenticationInfo(AuthenticationItem auth) {
    crm.setUrl(auth.getUrl());
    crm.setId(auth.getId());
    crm.setSystemId(auth.getSystemId());
    crm.setClientId(auth.getClientId());
    crm.setUsername(auth.getUsername());
    crm.setCode(auth.getCode());
  }
  
  /**
   *  Adds a feature to the Contact attribute of the CentricServices object
   *
   * @param  in0  The feature to be added to the Contact attribute
   * @param  in1  The feature to be added to the Contact attribute
   * @param  in2  The feature to be added to the Contact attribute
   * @param  in3  The feature to be added to the Contact attribute
   * @return      Description of the Return Value
   */
  public boolean addContact(AuthenticationItem in0, Contact in1, ContactEmailAddress in2, 
      ContactPhoneNumber in3, ContactAddress in4) {
    try {
      AuthenticationItem auth = in0;
      Contact contact = in1;
      ContactEmailAddress email = in2;
      ContactPhoneNumber phone = in3;
      ContactAddress address = in4;
      
      //Authentication Info
      this.setAuthenticationInfo(auth);

      // Start a new transaction
      crm.setAutoCommit(false);

      DataRecord contactData = new DataRecord();
      contactData.setName("contact");
      contactData.setAction(DataRecord.INSERT);
      contactData.setShareKey(true);
      contactData.addField("nameSalutation", contact.getNameSalutation());
      contactData.addField("nameFirst", contact.getNameFirst());
      contactData.addField("nameLast", contact.getNameLast());
      contactData.addField("company", contact.getCompany());
      contactData.addField("title", contact.getTitle());
      contactData.addField("source", contact.getSource());
      contactData.addField("rating", contact.getRating());
      contactData.addField("industryTempCode", contact.getIndustryTempCode());
      contactData.addField("comments", contact.getComments());
      contactData.addField("notes", contact.getNotes());
      //Lead parameters
      contactData.addField("isLead", contact.getIsLead());
      contactData.addField("leadStatus", contact.getLeadStatus());
      //If contact is a lead, accessType should be '2'
      contactData.addField("accessType", contact.getAccessType());
      //record keeping data
      contactData.addField("owner", contact.getOwner());
      contactData.addField("enteredBy", contact.getEnteredBy());
      contactData.addField("modifiedBy", contact.getModifiedBy());
      crm.save(contactData);

      //Add email
      if (email.getEmail() != null && !"".equals(email.getEmail().trim())) {
        DataRecord emailData = new DataRecord();
        emailData.setName("contactEmailAddress");
        emailData.setAction(DataRecord.INSERT);
        emailData.addField("contactId", "$C{contact.id}");
        emailData.addField("type", email.getType());
        emailData.addField("email", email.getEmail());
        emailData.addField("enteredBy", email.getEnteredBy());
        emailData.addField("modifiedBy", email.getModifiedBy());
        crm.save(emailData);
      }

      //Add phone
      if (phone.getNumber() != null && !"".equals(phone.getNumber().trim())) {
        DataRecord phoneData = new DataRecord();
        phoneData.setName("contactPhoneNumber");
        phoneData.setAction(DataRecord.INSERT);
        phoneData.addField("contactId", "$C{contact.id}");
        phoneData.addField("type", phone.getType());
        phoneData.addField("number", phone.getNumber());
        phoneData.addField("extension", phone.getExtension());
        phoneData.addField("enteredBy", phone.getEnteredBy());
        phoneData.addField("modifiedBy", phone.getModifiedBy());
        crm.save(phoneData);
      }

      //Add address
      DataRecord addressData = new DataRecord();
      addressData.setName("contactAddress");
      addressData.setAction(DataRecord.INSERT);
      addressData.addField("contactId", "$C{contact.id}");
      addressData.addField("type", address.getType());
      addressData.addField("streetAddressLine1", address.getStreetAddressLine1());
      addressData.addField("streetAddressLine2", address.getStreetAddressLine2());
      addressData.addField("city", address.getCity());
      addressData.addField("state", address.getState());
      addressData.addField("zip", address.getZip());
      addressData.addField("country", address.getCountry());
      addressData.addField("enteredBy", address.getEnteredBy());
      addressData.addField("modifiedBy", address.getModifiedBy());
      crm.save(addressData);
      
      crm.commit();
      
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Response-> " + crm.getLastResponse());
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return (crm.getStatus() == 0);
  }
}

