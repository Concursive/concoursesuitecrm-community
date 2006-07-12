/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.icelet;

import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactEmailAddress;
import org.aspcfs.modules.contacts.base.ContactEmailAddressList;
import org.aspcfs.modules.website.utils.PortletUtils;
import org.aspcfs.utils.web.LookupList;

import javax.portlet.*;
import java.sql.Connection;
import java.io.IOException;
import java.util.Enumeration;

/**
 *  Description of the Class
 *
 * @author     kailash
 * @author     mrajkowski
 * @created    February 16, 2006
 * @version    $Id: Exp $
 */
/*
 *  using the convention yyyymmddhh for constants.
 */
public class ContactFormPortlet extends GenericPortlet {

  public final static String INTRODUCTION = "6022302";
  public final static String THANKYOU = "6022303";
  public final static String SHOW_EMAIL = "6022304";
  public final static String EMAIL_REQUIRED = "6022305";
  public final static String LEAD_SOURCE = "2006051911";

  private final static String VIEW_PAGE1 = "/portlets/contact_form/contact_form.jsp";
  private final static String VIEW_PAGE2 = "/portlets/contact_form/contact_form_submitted.jsp";


  /**
   *  Description of the Method
   *
   * @param  request               Description of the Parameter
   * @param  response              Description of the Parameter
   * @exception  PortletException  Description of the Exception
   * @exception  IOException       Description of the Exception
   */
  public void doView(RenderRequest request, RenderResponse response)
       throws PortletException, IOException {
    // Test param that is submitted when form is posted
    String verified = (String) request.getParameter("verified");
    if (verified == null) {
      // Display an empty contact form since one was not just submitted
      // TODO: if the user has a Centric CRM session, pre-populate the values
      // Show the previously submitted name
      request.setAttribute("nameFirst", request.getPortletSession().getAttribute("nameFirst"));
      // Load the form preferences
      request.setAttribute("introduction", (String) request.getPreferences().getValue(INTRODUCTION, "To contact us, please fill out the following form..."));
      request.setAttribute("showEmail", (String) request.getPreferences().getValue(SHOW_EMAIL, "true"));
      request.setAttribute("emailRequired", (String) request.getPreferences().getValue(EMAIL_REQUIRED, "true"));
      request.setAttribute("source", (String) request.getPreferences().getValue(LEAD_SOURCE, "-1"));
      PortletRequestDispatcher requestDispatcher =
          getPortletContext().getRequestDispatcher(VIEW_PAGE1);
      requestDispatcher.include(request, response);
    } else {
      // Show the thank you page
      request.setAttribute("nameFirst", request.getPortletSession().getAttribute("nameFirst"));
      request.setAttribute("thankyou", (String) request.getPreferences().getValue(THANKYOU, "Thank you, we will contact you soon."));
      PortletRequestDispatcher requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE2);
      requestDispatcher.include(request, response);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  request               Description of the Parameter
   * @param  response              Description of the Parameter
   * @exception  PortletException  Description of the Exception
   * @exception  IOException       Description of the Exception
   */
  public void processAction(ActionRequest request, ActionResponse response)
       throws PortletException, IOException {

    try {
      //Insert the lead
      insertLead(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Process the submitted form, validate and determine if a success
    response.setRenderParameter("verified", "true");
    request.getPortletSession().setAttribute("nameFirst", request.getParameter("nameFirst"));
  }


  /**
   *  Description of the Method
   *
   * @param  request        Description of the Parameter
   * @param  response       Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  private void insertLead(ActionRequest request, ActionResponse response) throws Exception {
    Connection db = PortletUtils.getConnection(request);
    //Insert the lead
    AccessTypeList accessTypeList = new AccessTypeList(db, AccessType.GENERAL_CONTACTS);
    Contact lead = new Contact();
    lead.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
    lead.setIsLead(true);
    lead.setLeadStatus(Contact.LEAD_UNPROCESSED);
    lead.setNameFirst(request.getParameter("nameFirst"));
    lead.setNameLast(request.getParameter("nameLast"));
    lead.setOrgName(request.getParameter("orgName"));
    lead.setComments(request.getParameter("comments"));
    lead.setSource(request.getParameter("source"));
    lead.setEnteredBy(0);
    lead.setModifiedBy(0);
    String emailAddress = (String) request.getParameter("email1address");
    if (emailAddress != null && !"".equals(emailAddress)) {
      LookupList emailTypeList = new LookupList(db, "lookup_contactemail_types");
      ContactEmailAddress email = new ContactEmailAddress();
      email.setType(emailTypeList.getDefaultElementCode());
      email.setEmail(emailAddress);
      lead.setEmailAddressList(new ContactEmailAddressList());
      lead.getEmailAddressList().add(email);
    }
    lead.insert(db);
  }
}

