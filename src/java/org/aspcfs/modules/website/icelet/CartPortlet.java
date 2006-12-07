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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddress;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddressList;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactAddress;
import org.aspcfs.modules.contacts.base.ContactAddressList;
import org.aspcfs.modules.contacts.base.ContactEmailAddress;
import org.aspcfs.modules.contacts.base.ContactEmailAddressList;
import org.aspcfs.modules.login.beans.LoginBean;
import org.aspcfs.modules.orders.base.CreditCard;
import org.aspcfs.modules.orders.base.CreditCardList;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.orders.base.OrderAddress;
import org.aspcfs.modules.orders.base.OrderProduct;
import org.aspcfs.modules.orders.base.PaymentCreditCard;
import org.aspcfs.modules.quotes.base.QuoteProductBean;
import org.aspcfs.modules.website.utils.PortletUtils;
import org.aspcfs.utils.LoginUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;

import com.zeroio.iteam.base.FileItem;

/**
 * Description of the Class
 * 
 * @author mrajkowski
 * @version $Id: Exp $
 * @created April 28, 2006
 */
public class CartPortlet extends GenericPortlet {

  /**
   * Description of the Field
   */
  public final static String fs = System.getProperty("file.separator");

  /**
   * Description of the Field
   */
  public static String EMAIL_TO = "6062311";

  /**
   * Description of the Field
   */
  public static String EMAIL_SUBJECT = "6062312";

  /**
   * Description of the Field
   */
  public static String MESSAGE_TO_REVIEWER = "6062313";

  /**
   * Description of the Field
   */
  public static String MESSAGE_TO_REQUESTER = "6062314";

  /**
   * Description of the Field
   */
  public static String QUOTE_SENT_MESSAGE = "6071314";

  /**
   * Description of the Field
   */
  public static String CART_EMPTY_MESSAGE = "6071313";

  /**
   * Description of the Field
   */
  public static String INCLUDE_ORDER = "6080315";

  /**
   * Description of the Field
   */
  public static String WEB_CUSTOMER_ROLE = "6080316";

  /**
   * Description of the Field
   */
  public static String ACCOUNT_CREATE_WELCOME_MESSAGE = "6080317";

  /**
   * Description of the Field
   */
  public static String ACCOUNT_CREATE_THANKYOU_MESSAGE = "6080318";

  /**
   * Description of the Field
   */
  public static String ORDER_THANKYOU_MESSAGE = "6080319";

  /**
   * Description of the Field
   */
  public static String ORDER_ERROR_MESSAGE = "6080320";

  private final static String VIEW_PAGE1 = "/portlets/cart/cart.jsp";

  private final static String VIEW_PAGE2 = "/portlets/cart/cart_sent.jsp";

  private final static String VIEW_PAGE3 = "/portlets/cart/create_account.jsp";

  private final static String VIEW_PAGE4 = "/portlets/cart/add_address.jsp";

  private final static String VIEW_PAGE5 = "/portlets/cart/modify_address.jsp";

  private final static String VIEW_PAGE6 = "/portlets/cart/address_list.jsp";

  private final static String VIEW_PAGE7 = "/portlets/cart/modify_credit_card.jsp";

  private final static String VIEW_PAGE8 = "/portlets/cart/credit_card_list.jsp";

  private final static String VIEW_PAGE9 = "/portlets/cart/confirm_order.jsp";

  private final static String VIEW_PAGE10 = "/portlets/cart/order_confirmation_status.jsp";

  private final static String VIEW_PAGE11 = "/portlets/cart/create_account_success.jsp";

  private static final String VIEW_PAGE12 = "/portlets/cart/cart_quote_request.jsp";

  private static final String VIEW_PAGE13 = "/portlets/cart/customer_login.jsp";

  private static final String VIEW_PAGE14 = "/portlets/cart/state_list.jsp";

  private static final String VIEW_PAGE15 = "/portlets/cart/forgot_password.jsp";

  private static final String VIEW_PAGE16 = "/portlets/cart/password_repare.jsp";

  /**
   * Constructor for the CartPortlet object
   */
  public CartPortlet() {

  }

  /**
   * Description of the Method
   * 
   * @param request
   *          Description of the Parameter
   * @param response
   *          Description of the Parameter
   * @throws PortletException
   *           Description of the Exception
   * @throws IOException
   *           Description of the Exception
   */
  public void doView(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {

    if (System.getProperty("DEBUG") != null) {
      System.out.println("CartPortlet-> PortletMode: "
          + request.getPortletMode());
    }
    String viewType = (String) request.getParameter("viewType");
    if (request.getPortletMode() == PortletMode.VIEW) {
      // Show the HTML content
      if ("email".equals(viewType)) {
        request.setAttribute("QUOTE_SENT_MESSAGE", (String) request
            .getPreferences().getValue(QUOTE_SENT_MESSAGE,
                "Your quote has been sent."));
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE2);
        requestDispatcher.include(request, response);
      } else if ("createAccount".equals(viewType)) {
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE3);
        requestDispatcher.include(request, response);
      } else if ("addAddress".equals(viewType)) {
        modifyAddress(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE4);
        requestDispatcher.include(request, response);
      } else if ("modifyAddress".equals(viewType)) {
        modifyAddress(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE5);
        requestDispatcher.include(request, response);
      } else if ("modifyCard".equals(viewType)) {
        modifyCreditCard(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE7);
        requestDispatcher.include(request, response);
      } else if ("cardList".equals(viewType)) {
        modifyCreditCard(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE8);
        requestDispatcher.include(request, response);
      } else if ("confirmOrder".equals(viewType)) {
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE9);
        requestDispatcher.include(request, response);
      } else if ("confirmOrderStatus".equals(viewType)) {
        if (request.getParameter("error") != null) {
          request.setAttribute("ORDER_ERROR_MESSAGE", (String) request
              .getPreferences().getValue(ORDER_ERROR_MESSAGE,
                  "Your order can not be processed."));
        } else {
          request.setAttribute("ORDER_THANKYOU_MESSAGE", (String) request
              .getPreferences().getValue(ORDER_THANKYOU_MESSAGE,
                  "Your order has been processed successfully."));
        }
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE10);
        requestDispatcher.include(request, response);
      } else if ("createAccountSuccess".equals(viewType)) {
        PortletRequestDispatcher requestDispatcher;
        if (request.getParameter("error") != null) {
          request.setAttribute("error", request.getParameter("error"));
          request.setAttribute("nameFirst", request.getParameter("nameFirst"));
          request.setAttribute("nameLast", request.getParameter("nameLast"));
          request.setAttribute("emailaddress", request
              .getParameter("emailaddress"));
          request.setAttribute("orgName", request.getParameter("orgName"));
          requestDispatcher = getPortletContext().getRequestDispatcher(
              VIEW_PAGE3);
        } else {
          request.setAttribute("ACCOUNT_CREATE_THANKYOU_MESSAGE",
              (String) request.getPreferences().getValue(
                  ACCOUNT_CREATE_THANKYOU_MESSAGE,
                  "Your account has been create."));
          requestDispatcher = getPortletContext().getRequestDispatcher(
              VIEW_PAGE11);
        }
        requestDispatcher.include(request, response);
      } else if ("requestQuote".equals(viewType)) {
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE12);
        requestDispatcher.include(request, response);
      } else if ("customerLogin".equals(viewType)) {
        PortletRequestDispatcher requestDispatcher;
        if (((HttpServletRequest) request).getSession().getAttribute("WebUser") != null) {
          associateAddress(request, response);
          requestDispatcher = getPortletContext().getRequestDispatcher(
              VIEW_PAGE6);
        } else {
          requestDispatcher = getPortletContext().getRequestDispatcher(
              VIEW_PAGE13);
        }
        requestDispatcher.include(request, response);
      } else if ("addressList".equals(viewType)) {
        associateAddress(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE6);
        requestDispatcher.include(request, response);
      } else if ("loginFailed".equals(viewType)) {
        request.setAttribute("LoginFailedMessage", request
            .getParameter("LoginFailedMessage"));
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE13);
        requestDispatcher.include(request, response);
      } else if ("selectState".equals(viewType)) {
        states(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE14);
        requestDispatcher.include(request, response);
      } else if ("forgotPassword".equals(viewType)) {
        request.setAttribute("username", request.getParameter("username"));
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE15);
        requestDispatcher.include(request, response);
      } else if ("passwordRepare".equals(viewType)) {
        request.setAttribute("result", request.getParameter("result"));
        if ("newPasswordSent".equals(request.getParameter("result"))) {
          PortletRequestDispatcher requestDispatcher = getPortletContext()
              .getRequestDispatcher(VIEW_PAGE16);
          requestDispatcher.include(request, response);
        } else {
          PortletRequestDispatcher requestDispatcher = getPortletContext()
              .getRequestDispatcher(VIEW_PAGE15);
          requestDispatcher.include(request, response);
        }
      } else {
        request.setAttribute("CART_EMPTY_MESSAGE", (String) request
            .getPreferences().getValue(CART_EMPTY_MESSAGE,
                "Your cart is empty."));
        request.setAttribute("INCLUDE_ORDER", (String) request
            .getPreferences().getValue(INCLUDE_ORDER,
                "false"));
        PortletRequestDispatcher requestDispatcher = getPortletContext()
            .getRequestDispatcher(VIEW_PAGE1);
        requestDispatcher.include(request, response);
      }
    } else if (request.getPortletMode() == new PortletMode("config")) {

      PortletRequestDispatcher requestDispatcher = getPortletContext()
          .getRequestDispatcher(VIEW_PAGE2);
      requestDispatcher.include(request, response);
    }
  }

  /**
   * Description of the Method
   * 
   * @param request
   *          Description of the Parameter
   * @param response
   *          Description of the Parameter
   * @throws PortletException
   *           Description of the Exception
   * @throws IOException
   *           Description of the Exception
   */
  public void processAction(ActionRequest request, ActionResponse response) {
    if (request.getParameter("login") != null) {
      if (((HttpServletRequest) request).getSession().getAttribute("WebUser") != null) {
        response.setRenderParameter("viewType", "addressList");
      } else {
        siteLogin(request, response);
      }
    } else if (request.getParameter("requestQuote") != null) {
      requestQuote(request, response);
    } else if (request.getParameter("save") != null) {
      saveCart(request, response);
      if (request.getParameter("returnStr") != null
          && "confirmOrder".equals((request.getParameter("returnStr")))) {
        response.setRenderParameter("viewType", "confirmOrder");
      }
    } else if (request.getParameter("clear") != null) {
      clearCart(request, response);
    } else if (request.getParameter("email") != null) {
      emailCart(request, response);
    } else if (request.getParameter("checkout") != null) {
      createAccount(request, response);
    } else if (request.getParameter("forgotPassword") != null) {
      passwordRepare(request, response);
    } else if (request.getParameter("selectAddress") != null) {
      saveAddressPreference(request, response);
      if (request.getParameter("returnStr") != null
          && "confirmOrder".equals((request.getParameter("returnStr")))) {
        response.setRenderParameter("viewType", "confirmOrder");
      } else {
        response.setRenderParameter("viewType", "cardList");
      }
    } else if (request.getParameter("deleteAddress") != null) {
      deleteAddresses(request, response);
      response.setRenderParameter("viewType", "addressList");
    } else if (request.getParameter("createAccount") != null) {
      try {
        saveAccount(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
      response.setRenderParameter("viewType", "createAccountSuccess");
    } else if (request.getParameter("updateAddress") != null) {
      try {
        updateAddress(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
      response.setRenderParameter("viewType", "addressList");
    } else if (request.getParameter("updateCreditCard") != null) {
      try {
        updateCreditCard(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
      response.setRenderParameter("viewType", "cardList");
    } else if (request.getParameter("confirmOrder") != null) {
      try {
        associateCard(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
      response.setRenderParameter("viewType", "confirmOrder");
    } else if (request.getParameter("deleteCard") != null) {
      deleteCard(request, response);
      response.setRenderParameter("viewType", "cardList");
    } else if (request.getParameter("confirmOrderStatus") != null) {
      confirmOrder(request, response);
      if (request.getParameter("error") != null) {
        response.setRenderParameter("error", request.getParameter("error"));
      }
      response.setRenderParameter("viewType", "confirmOrderStatus");
    } else if (request.getParameter("cancelOrder") != null) {
      cancelOrder(request, response);

    }
  }

  /**
   * @param request
   * @param response
   */
  private void passwordRepare(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    String username = request.getParameter("username");
    User user = new User();
    try {
      if (user.exists(db, username)) {
        String password = user.generateRandomPassword(db);
        sendNewPassword(request, response, username, password);
        response.setRenderParameter("result", "newPasswordSent");
      } else {
        response.setRenderParameter("result", "userNotExists");
      }
      response.setRenderParameter("viewType", "passwordRepare");
    } catch (SQLException e) {

      e.printStackTrace();
    }
  }

  /**
   * @param request
   * @param request
   * @param response
   * 
   */
  private void sendNewPassword(ActionRequest request, ActionResponse response,
      String email, String password) {
    HashMap cartBean = (HashMap) ((HttpServletRequest) request).getSession()
        .getAttribute("CartBean");

    if (cartBean != null && cartBean.size() > 0) {

      SMTPMessage mail = new SMTPMessage();
      mail.setHost(PortletUtils.getApplicationPrefs(request, "MAILSERVER"));
      mail.setType("text/html");
      mail.addTo(email);
      mail.setSubject("New Password");
      StringBuffer body = new StringBuffer();
      body.append("Your new password is: ");
      body.append(password);
      mail.setBody(body.toString());
      if (mail.send() == 2) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CartPortlet-> Send error: " + mail.getErrorMsg()
              + "<br><br>");
        }
        System.err.println(mail.getErrorMsg());
      }
    }

  }

  /**
   * @param request
   * @param response
   */
  private void deleteCard(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    try {
      if (request.getParameter("deleteCard") != null) {
        String[] deleteIds = request.getParameterValues("deleteCardId");
        CreditCard card = new CreditCard();
        if (deleteIds != null) {
          for (int i = 0; i < deleteIds.length; i++) {
            card.setId(deleteIds[i]);
            card.delete(db);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param request
   * @param response
   */
  private void deleteAddresses(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    try {
      if (request.getParameter("deleteAddress") != null) {
        String[] deleteIds = request.getParameterValues("deleteAddressId");
        ContactAddress adr = new ContactAddress();
        if (deleteIds != null) {
          for (int i = 0; i < deleteIds.length; i++) {
            adr.setId(deleteIds[i]);
            adr.delete(db);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param request
   * @param response
   */
  private void siteLogin(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    LoginBean login = new LoginBean();
    login.setUsername(request.getParameter("username"));
    login.setPassword(request.getParameter("password"));
    try {
      LoginUtils userUtil = new LoginUtils(db, login);
      if (userUtil.isPortalUserValid(db)) {
        User user = new User(db, userUtil.getUserId());
        ((HttpServletRequest) request).getSession().setAttribute("WebUser",
            user);
        response.setRenderParameter("viewType", "addressList");
      } else {
        response.setRenderParameter("LoginFailedMessage", "".equals(login
            .getMessage()) ? "Login Failed" : login.getMessage());
        response.setRenderParameter("viewType", "loginFailed");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param request
   * @param response
   */
  private void requestQuote(ActionRequest request, ActionResponse response) {
    response.setRenderParameter("viewType", "requestQuote");

  }

  /**
   * @param request
   * @param response
   */
  private void clearCart(ActionRequest request, ActionResponse response) {
    if (((HttpServletRequest) request).getSession().getAttribute("CartBean") != null) {
      ((HttpServletRequest) request).getSession().removeAttribute("CartBean");
    }
  }

  private void saveAccount(ActionRequest request, ActionResponse response)
      throws Exception {
    Connection db = PortletUtils.getConnection(request);
    boolean inserted = false;
    User thisUser = new User();
    thisUser.setUsername(request.getParameter("emailaddress"));
    thisUser.setPassword1(request.getParameter("password"));
    thisUser.setPassword2(request.getParameter("confirmPassword"));
    if (!thisUser.isDuplicate(db)) {
      Contact thisContact = new Contact();
      AccessTypeList accessTypes = PortletUtils.getSystemStatus(request)
          .getAccessTypeList(db, AccessType.ACCOUNT_CONTACTS);
      thisContact.setAccessType(accessTypes.getDefaultItem());
      thisContact.setNameFirst(request.getParameter("nameFirst"));
      thisContact.setNameLast(request.getParameter("nameLast"));
      String emailAddress = (String) request.getParameter("emailaddress");
      if (emailAddress != null && !"".equals(emailAddress)) {
        LookupList emailTypeList = new LookupList(db,
            "lookup_contactemail_types");
        ContactEmailAddress email = new ContactEmailAddress();
        email.setType(emailTypeList.getDefaultElementCode());
        email.setEmail(emailAddress);
        thisContact.setEmailAddressList(new ContactEmailAddressList());
        thisContact.getEmailAddressList().add(email);
      }
      thisContact.setEnteredBy(0);
      thisContact.setModifiedBy(0);
      thisContact.insert(db);

      // Add user
      ApplicationPrefs prefs = (ApplicationPrefs) PortletUtils
          .getApplicationPrefs(request);
      String roleId = (String) request.getPreferences().getValue(
          WEB_CUSTOMER_ROLE, "1");
      thisUser.setContactId(thisContact.getId());
      thisUser.setRoleId(Integer.parseInt(roleId));
      thisUser.setEnteredBy(0);
      thisUser.setModifiedBy(0);
      thisUser.setTimeZone(prefs.get("SYSTEM.TIMEZONE"));
      thisUser.setCurrency(prefs.get("SYSTEM.CURRENCY"));
      thisUser.setLanguage(prefs.get("SYSTEM.LANGUAGE"));
      boolean userInserted = thisUser.insert(db);
      if (userInserted) {
        // Add account
        Organization account = new Organization();
        account.setName(request.getParameter("orgName"));
        account.setNameFirst(request.getParameter("nameFirst"));
        account.setNameLast(request.getParameter("nameLast"));
        emailAddress = (String) request.getParameter("emailaddress");
        if (emailAddress != null && !"".equals(emailAddress)) {
          LookupList emailTypeList = new LookupList(db,
              "lookup_contactemail_types");
          OrganizationEmailAddress email = new OrganizationEmailAddress();
          email.setType(emailTypeList.getDefaultElementCode());
          email.setEmail(emailAddress);
          account.setEmailAddressList(new OrganizationEmailAddressList());
          account.getEmailAddressList().add(email);
        }
        account.setEnteredBy(thisUser.getId());
        account.setModifiedBy(thisUser.getId());
        account.setOwner(thisUser.getId());
        account.setInsertPrimaryContact(false);
        inserted = account.insert(db);
        if (inserted) {
          thisContact.setOrgId(account.getId());
          thisContact.setOrgName(account.getName());
          thisContact.setModifiedBy(thisUser.getId());
          thisContact.update(db);
        }
        sendCreateAccountNotification(request, response, thisUser.getUsername());
        LoginBean login = new LoginBean();
        login.setUsername(request.getParameter("emailaddress"));
        login.setPassword(request.getParameter("password"));
        try {
          LoginUtils userUtil = new LoginUtils(db, login);
          if (userUtil.isPortalUserValid(db)) {
            User user = new User(db, userUtil.getUserId());
            ((HttpServletRequest) request).getSession().setAttribute("WebUser",
                user);
          } else {
            response.setRenderParameter("LoginFailedMessage", "".equals(login
                .getMessage()) ? "Login Failed" : login.getMessage());
            response.setRenderParameter("viewType", "loginFailed");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } else {
      response.setRenderParameter("error", "Name is duplicate.");
      response.setRenderParameter("nameFirst", request
          .getParameter("nameFirst"));
      response.setRenderParameter("nameLast", request.getParameter("nameLast"));
      response.setRenderParameter("emailaddress", request
          .getParameter("emailaddress"));
      response.setRenderParameter("orgName", request.getParameter("nameFirst"));
    }

  }

  /**
   * 
   */
  private void sendCreateAccountNotification(ActionRequest request,
      ActionResponse response, String email) {

    SMTPMessage mail = new SMTPMessage();
    mail.setHost(PortletUtils.getApplicationPrefs(request, "MAILSERVER"));
    mail.setType("text/html");
    mail.addTo(email);
    mail.setSubject("New account");
    StringBuffer body = new StringBuffer();
    body.append((String) request.getPreferences().getValue(
        ACCOUNT_CREATE_WELCOME_MESSAGE,
        "Welcome to our website. You have just created new account"));
    mail.setBody(body.toString());
    if (mail.send() == 2) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("CartPortlet-> Send error: " + mail.getErrorMsg()
            + "<br><br>");
      }
      System.err.println(mail.getErrorMsg());
    }
  }

  private void createAccount(ActionRequest request, ActionResponse response) {
    response.setRenderParameter("viewType", "createAccount");

  }

  private void modifyAddress(RenderRequest request, RenderResponse response) {

    SystemStatus systemStatus = PortletUtils.getSystemStatus(request);
    StateSelect stateSelect;

    ApplicationPrefs prefs = (ApplicationPrefs) PortletUtils
        .getApplicationPrefs(request);
    stateSelect = new StateSelect(systemStatus, prefs.get("SYSTEM.COUNTRY"));
    stateSelect.setPreviousStates(new HashMap());
    CountrySelect countrySelect = new CountrySelect(systemStatus);

    request.setAttribute("StateSelect", stateSelect);
    request.setAttribute("CountrySelect", countrySelect);
    request.setAttribute("applicationPrefs", prefs);

    Connection db = PortletUtils.getConnection(request);
    try {
      LookupList addrTypeList = new LookupList(db,
          "lookup_contactaddress_types");
      request.setAttribute("typeList", addrTypeList);

      ContactAddress thisAddress = new ContactAddress();
      if (request.getParameter("addressId") != null) {
        thisAddress = new ContactAddress(db, Integer.parseInt(request
            .getParameter("addressId")));
      }
      request.setAttribute("addressItem", thisAddress);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void updateAddress(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    User user = (User) ((HttpServletRequest) request).getSession()
        .getAttribute("WebUser");
    ContactAddress address = new ContactAddress();
    try {
      address.setStreetAddressLine1(request.getParameter("address1"));
      address.setStreetAddressLine2(request.getParameter("address2"));
      address.setStreetAddressLine3(request.getParameter("address3"));
      address.setStreetAddressLine4(request.getParameter("address4"));
      address.setCity(request.getParameter("city"));
      address.setType(request.getParameter("type"));
      address.setState(request.getParameter("state1"));
      address.setOtherState(request.getParameter("state2"));
      address.setCountry(request.getParameter("countryId"));
      address.setContactId(user.getContactId());
      address.setEnabled(true);
      if (request.getParameter("addressId") == null
          || "-1".equals(request.getParameter("addressId"))) {
        address.setEnteredBy(user.getId());
        address.setModifiedBy(user.getId());
        address.insert(db);
      } else {
        address.setId(request.getParameter("addressId"));
        address.setModifiedBy(user.getId());
        address.update(db, user.getId());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void modifyCreditCard(RenderRequest request, RenderResponse response) {

    Connection db = PortletUtils.getConnection(request);
    User user = (User) ((HttpServletRequest) request).getSession()
        .getAttribute("WebUser");
    try {
      LookupList cardTypeList = new LookupList(db, "lookup_creditcard_types");
      request.setAttribute("cardTypeList", cardTypeList);

      CreditCard thisCard = new CreditCard();
      if (request.getParameter("cardId") != null
          && !"-1".equals(request.getParameter("cardId"))) {
        thisCard = new CreditCard(db, Integer.parseInt(request
            .getParameter("cardId")));
      }
      CreditCardList cardList = new CreditCardList();
      cardList.setEnteredBy(user.getId());
      cardList.buildList(db);
      request.setAttribute("creditCard", thisCard);
      request.setAttribute("creditCardList", cardList);
      request.setAttribute("return", request.getParameter("return"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void updateCreditCard(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    User user = (User) ((HttpServletRequest) request).getSession()
        .getAttribute("WebUser");
    CreditCard card = new CreditCard();
    try {
      card.setCardType(request.getParameter("cardType"));
      card.setCardNumber(request.getParameter("cardNumber"));
      card.setNameOnCard(request.getParameter("nameOnCard"));
      card.setExpirationMonth(request.getParameter("expirationMonth"));
      card.setExpirationYear(request.getParameter("expirationYear"));

      if (request.getParameter("cardId") == null
          || "-1".equals(request.getParameter("cardId"))) {
        card.setEnteredBy(user.getId());
        card.setModifiedBy(user.getId());
        card.insert(db);
      } else {
        card.setId(request.getParameter("cardId"));
        card.setModifiedBy(user.getId());
        card.update(db);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void associateCard(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);

    String useInOrder = request.getParameter("useInOrder");
    CreditCard card = null;
    try {
      if (useInOrder != null && !"".equals(useInOrder)
          && !"-1".equals(useInOrder)) {
        card = new CreditCard(db, Integer.parseInt(useInOrder));
        ((HttpServletRequest) request).getSession().setAttribute(
            "creditCardInOrder", card);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // private void saveCardPreference(ActionRequest request, ActionResponse
  // response) {
  // // (saves the card chosen for the order. The card details are copied from
  // // the table creditcard to payment_creditcard)
  // }

  private void associateAddress(RenderRequest request, RenderResponse response) {

    User user = (User) ((HttpServletRequest) request).getSession()
        .getAttribute("WebUser");
    Connection db = PortletUtils.getConnection(request);
    try {

      ContactAddressList thisList = new ContactAddressList();
      thisList.setContactId(user.getContactId());
      thisList.buildList(db);
      request.setAttribute("addressList", thisList);

    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setAttribute("returnStr", request.getParameter("returnStr"));
  }

  private void saveAddressPreference(ActionRequest request,
      ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    String billing = request.getParameter("billing");
    String shipping = request.getParameter("shipping");
    ContactAddress address = new ContactAddress();
    try {
      if (billing != null && !"".equals(billing) && !"-1".equals(billing)) {
        address = new ContactAddress(db, Integer.parseInt(billing));
        ((HttpServletRequest) request).getSession().setAttribute(
            "billingAddress", address);
      }
      if (shipping != null && !"".equals(shipping) && !"-1".equals(shipping)) {
        address = new ContactAddress(db, Integer.parseInt(shipping));
        ((HttpServletRequest) request).getSession().setAttribute(
            "shippingAddress", address);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    request.setAttribute("returnStr", request.getParameter("returnStr"));
  }

  // private void reviewOrder(ActionRequest request, ActionResponse response) {
  // // (Fetches all the information required for the order and sets them as
  // // request
  // // attributes)
  // }

  /**
   * i. Creates a record in order_entry. ii. Copies addresses from the tables
   * contact_address to order_address. iii. Copies the credit card information
   * from credit_card to payment_creditcard. iv. copies the products in cart
   * from the table product_catalog into table order_product and associates the
   * records in it to the appropriate order_entry record. Please refer to the
   * QuotesProducts.java to get an idea of this may be done.
   */
  private void confirmOrder(ActionRequest request, ActionResponse response) {
    Connection db = PortletUtils.getConnection(request);
    User user = (User) ((HttpServletRequest) request).getSession()
        .getAttribute("WebUser");
    HashMap cartBean = (HashMap) ((HttpServletRequest) request).getSession()
        .getAttribute("CartBean");
    ContactAddress billingAddress = (ContactAddress) ((HttpServletRequest) request)
        .getSession().getAttribute("billingAddress");
    OrderAddress orderBillingAddress = new OrderAddress();
    ContactAddress shippingAddress = (ContactAddress) ((HttpServletRequest) request)
        .getSession().getAttribute("shippingAddress");
    OrderAddress orderShippingAddress = new OrderAddress();
    CreditCard card = (CreditCard) ((HttpServletRequest) request).getSession()
        .getAttribute("creditCardInOrder");
    Order order = new Order();
    try {
      order.setBillingContactId(user.getId());
      order.setOrgId(0);
      order.setOrderedBy(user.getId());
      order.setEnteredBy(user.getId());
      order.setModifiedBy(user.getId());
      boolean inserted = order.insert(db);

      if (inserted) {
        if (cartBean.size() > 0) {
          Iterator quoteProductIterator = cartBean.keySet().iterator();
          while (quoteProductIterator.hasNext()) {
            String productId = (String) quoteProductIterator.next();
            QuoteProductBean quoteProductBean = (QuoteProductBean) cartBean
                .get(productId);
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(quoteProductBean.getProduct().getId());
            orderProduct.setQuantity(quoteProductBean.getQuantity());
            orderProduct.setModifiedBy(user.getId());
            orderProduct.setEnteredBy(user.getId());
            orderProduct.setStatusId(1);
            orderProduct.setOrderId(order.getId());
            orderProduct.insert(db);
          }
        }
        PaymentCreditCard pCard = new PaymentCreditCard();
        pCard.setCardNumber(card.getCardNumber());
        pCard.setCardSecurityCode(card.getCardSecurityCode());
        pCard.setCardType(card.getCardType());
        pCard.setCompanyNameOnCard(card.getCompanyNameOnCard());
        pCard.setExpirationMonth(card.getExpirationMonth());
        pCard.setExpirationYear(card.getExpirationYear());
        pCard.setNameOnCard(card.getNameOnCard());
        pCard.setOrderId(order.getId());
        pCard.setModifiedBy(user.getId());
        pCard.setEnteredBy(user.getId());
        pCard.insert(db);

        BeanUtils.copyProperties(billingAddress, orderBillingAddress);
        orderBillingAddress.setOrderId(order.getId());
        orderBillingAddress.setModifiedBy(user.getId());
        orderBillingAddress.setEnteredBy(user.getId());
        orderBillingAddress.insert(db);

        BeanUtils.copyProperties(orderShippingAddress, shippingAddress);
        orderShippingAddress.setOrderId(order.getId());
        orderShippingAddress.setModifiedBy(user.getId());
        orderShippingAddress.setEnteredBy(user.getId());
        orderShippingAddress.insert(db);
        clearCart(request, response);
      }
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e.getMessage());
    }

  }

  /**
   * @param product
   * @param orderProduct
   */

  private void cancelOrder(ActionRequest request, ActionResponse response) {
    clearCart(request, response);
    if (((HttpServletRequest) request).getSession().getAttribute(
        "billingAddress") != null) {
      ((HttpServletRequest) request).getSession().removeAttribute(
          "billingAddress");
    }
    if (((HttpServletRequest) request).getSession().getAttribute(
        "shippingAddress") != null) {
      ((HttpServletRequest) request).getSession().removeAttribute(
          "shippingAddress");
    }
    if (((HttpServletRequest) request).getSession().getAttribute(
        "creditCardInOrder") != null) {
      ((HttpServletRequest) request).getSession().removeAttribute(
          "CreditCardInOrder");
    }
  }

  /**
   * Description of the Method
   * 
   * @param request
   *          Description of the Parameter
   * @param response
   *          Description of the Parameter
   */
  private void emailCart(ActionRequest request, ActionResponse response) {
    String userName = request.getParameter("userName");
    String userEmail = request.getParameter("userEmail");
    HashMap cartBean = (HashMap) ((HttpServletRequest) request).getSession()
        .getAttribute("CartBean");

    if (cartBean != null && cartBean.size() > 0) {

      SMTPMessage mailToReviewer = new SMTPMessage();
      mailToReviewer.setHost(PortletUtils.getApplicationPrefs(request,
          "MAILSERVER"));
      mailToReviewer.setType("text/html");
      mailToReviewer.addTo(request.getPreferences().getValue(EMAIL_TO, "-1"));
      mailToReviewer.setFrom(PortletUtils.getApplicationPrefs(request,
          "EMAILADDRESS"));
      mailToReviewer.addReplyTo(userEmail);
      mailToReviewer.setSubject(request.getPreferences().getValue(
          EMAIL_SUBJECT, "Quote request"));

      Template template = new Template();
      template.setText((String) request.getPreferences().getValue(
          MESSAGE_TO_REVIEWER,
          userName + " has requested a quote for the following items:"));
      template.addParseElement("${quoteRequester.name}", userName);
      String messageHeader = template.getParsedText();
      getMailBody(cartBean, mailToReviewer, request, messageHeader);
      if (mailToReviewer.send() == 2) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CartPortlet-> Send message to reviewer error: "
              + mailToReviewer.getErrorMsg());
        }
        System.err.println(mailToReviewer.getErrorMsg());
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CartPortlet -> Sending message to reviewer...");
        }
      }

      SMTPMessage mailToRequester = new SMTPMessage();
      mailToRequester.setHost(PortletUtils.getApplicationPrefs(request,
          "MAILSERVER"));
      mailToRequester.setType("text/html");
      mailToRequester.setFrom(PortletUtils.getApplicationPrefs(request,
          "EMAILADDRESS"));
      mailToRequester.addTo(userEmail);
      mailToRequester.setSubject((String) request.getPreferences().getValue(
          EMAIL_SUBJECT, "Quote request"));
      template = new Template();
      template.setText(request.getPreferences().getValue(MESSAGE_TO_REQUESTER,
          userName + " has requested a quote for the following items:"));
      template.addParseElement("${quoteReviewer.email}", request
          .getPreferences().getValue(EMAIL_TO, "-1"));
      messageHeader = template.getParsedText();

      getMailBody(cartBean, mailToRequester, request, messageHeader);
      if (mailToRequester.send() == 2) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CartPortlet-> Send  message to requester error: "
              + mailToRequester.getErrorMsg());
        }
        System.err.println(mailToRequester.getErrorMsg());
      } else {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("CartPortlet -> Sending message to requester...");
        }
        ((HttpServletRequest) request).getSession().removeAttribute("CartBean");
        response.setRenderParameter("viewType", "email");
      }
    }
  }

  /**
   * @param cartBean
   * @param mail
   * @param request
   * @param message
   *          Description of the Parameter
   */
  private void getMailBody(HashMap cartBean, SMTPMessage mail,
      ActionRequest request, String message) {
    StringBuffer body = new StringBuffer();
    Connection db = PortletUtils.getConnection(request);

    body.append(message);
    body.append("<table>");
    Iterator iterator = cartBean.keySet().iterator();
    body.append("<tr>");
    body.append("<th width=\"140\">Image</th>");
    body.append("<th width=\"140\">Name</th>");
    body.append("<th width=\"140\">Quantity</th>");
    body.append("</tr>");
    while (iterator.hasNext()) {
      String productId = (String) iterator.next();
      QuoteProductBean element = (QuoteProductBean) cartBean.get(productId);
      body.append("<tr>");
      body.append("<td width=\"140\">");
      FileItem fileItem;
      try {
        if (element.getProduct().getLargeImageId() != -1) {
          fileItem = new FileItem(db, element.getProduct().getLargeImageId());
          fileItem.setDirectory(PortletUtils.getDbNamePath(request)
              + "products" + fs);
          String imagePath = fileItem.getFullFilePath();
          mail.addImage("productImage" + productId, "file:///" + imagePath);
          body.append("<img src=\"cid:productImage" + productId
              + "\" border=\"0\"><BR>");
        } else {
          body.append("Image not available");
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      body.append("</td>");
      body.append("<td>");
      body.append("<div>");
      body.append(element.getProduct().getName());
      body.append("</div>");
      body.append("</td>");
      body.append("<td width=\"10\">");
      body.append(element.getQuantity());
      body.append("</td>");
      body.append("</tr>");
    }
    body.append("</table>");
    body.append("<br/>");
    body.append(request.getParameter("comments"));
    mail.setBody(body.toString());
  }

  /**
   * Description of the Method
   * 
   * @param request
   *          Description of the Parameter
   * @param response
   *          Description of the Parameter
   */
  private void saveCart(ActionRequest request, ActionResponse response) {
    HashMap cartBean = (HashMap) ((HttpServletRequest) request).getSession()
        .getAttribute("CartBean");
    String[] deleteIds = request.getParameterValues("quoteProductId");
    if (deleteIds != null) {
      for (int i = 0; i < deleteIds.length; i++) {
        cartBean.remove(deleteIds[i]);
      }
    }
    if (cartBean.size() > 0) {
      Iterator quoteProductIterator = cartBean.keySet().iterator();
      while (quoteProductIterator.hasNext()) {
        String productId = (String) quoteProductIterator.next();
        QuoteProductBean quoteProductBean = (QuoteProductBean) cartBean
            .get(productId);
        String item = request.getParameter("quantity_" + productId);
        quoteProductBean.setQuantity(StringUtils.getIntegerNumber(item));
      }
    } else {
      ((HttpServletRequest) request).getSession().removeAttribute("CartBean");
    }
    request.setAttribute("returnStr", request.getParameter("returnStr"));
  }

  public void states(RenderRequest request, RenderResponse response) {

    SystemStatus systemStatus = PortletUtils.getSystemStatus(request);
    String country = request.getParameter("countryId");
    StateSelect stateSelect = new StateSelect(systemStatus, country);
    request.setAttribute("stateSelect", stateSelect.getHtmlSelectObj(country));
    request.setAttribute("obj", "state1");

  }
}
