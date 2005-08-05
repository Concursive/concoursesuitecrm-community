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
package org.aspcfs.modules.contacts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;

import java.sql.Connection;

/**
 * Basic Contact object.<br>
 * Processes and Builds necessary data for any Contact object.
 *
 * @author Mathur
 * @version $Id$
 * @created April 3, 2003
 */
public final class ContactForm extends CFSModule {
  /**
   * Prepares supporting form data required for any Contact object.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Connection db = null;
    //Get contact object from the request (bean is not accessible here)
    Contact newContact = (Contact) context.getRequest().getAttribute(
        "ContactDetails");
    try {
      db = this.getConnection(context);
      SystemStatus systemStatus = this.getSystemStatus(context);

      LookupList phoneTypeList = systemStatus.getLookupList(
          db, "lookup_contactphone_types");
      context.getRequest().setAttribute("ContactPhoneTypeList", phoneTypeList);

      LookupList emailTypeList = systemStatus.getLookupList(
          db, "lookup_contactemail_types");
      context.getRequest().setAttribute("ContactEmailTypeList", emailTypeList);

      LookupList addressTypeList = systemStatus.getLookupList(
          db, "lookup_contactaddress_types");
      context.getRequest().setAttribute(
          "ContactAddressTypeList", addressTypeList);

      LookupList textMessageAddressTypeList = systemStatus.getLookupList(
          db, "lookup_textmessage_types");
      context.getRequest().setAttribute(
          "ContactTextMessageAddressTypeList", textMessageAddressTypeList);

      LookupList instantMessageAddressTypeList = systemStatus.getLookupList(
          db, "lookup_im_types");
      context.getRequest().setAttribute(
          "ContactInstantMessageAddressTypeList", instantMessageAddressTypeList);

      LookupList instantMessageAddressServiceList = systemStatus.getLookupList(
          db, "lookup_im_services");
      context.getRequest().setAttribute(
          "ContactInstantMessageAddressServiceList", instantMessageAddressServiceList);

      LookupList sources = new LookupList(db, "lookup_contact_source");
      sources.addItem(
          -1, systemStatus.getLabel(
              "accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("SourceList", sources);

      LookupList ratings = new LookupList(db, "lookup_contact_rating");
      ratings.addItem(
          -1, systemStatus.getLabel(
              "accounts.accounts_contacts_calls_details_followup_include.None"));
      context.getRequest().setAttribute("RatingList", ratings);

      //Make the StateSelect and CountrySelect drop down menus available in the request. 
      //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
      StateSelect stateSelect = new StateSelect(systemStatus);
      CountrySelect countrySelect = new CountrySelect(systemStatus);
      context.getRequest().setAttribute("StateSelect", stateSelect);
      context.getRequest().setAttribute("CountrySelect", countrySelect);
      context.getRequest().setAttribute("systemStatus", systemStatus);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (newContact != null && newContact.getId() > 0) {
      return getReturn(context, "PrepareModify");
    } else {
      return getReturn(context, "PrepareAdd");
    }
  }
}

