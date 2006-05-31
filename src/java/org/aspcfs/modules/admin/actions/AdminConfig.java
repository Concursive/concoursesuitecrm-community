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
package org.aspcfs.modules.admin.actions;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.hooks.CustomHook;
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import org.aspcfs.apps.workFlowManager.BusinessProcessImporter;
import org.aspcfs.apps.workFlowManager.BusinessProcessList;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.objectHookManager.ObjectHookList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.service.base.SyncClientList;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 9, 2003
 */
public final class AdminConfig extends CFSModule {

  /**
   * Action that prepares a list of the editable global parameters
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandListGlobalParams(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = getConnection(context);
      // get this company's name, always org_id 0
      Organization myCompany = new Organization(db, 0);
      context.getRequest().setAttribute("myCompany", myCompany);
      // Get the number of events and the number of processes in the system
      BusinessProcessList processList = new BusinessProcessList();
      processList.setEnabled(Constants.TRUE);
      processList.buildList(db);
      ObjectHookList hookList = new ObjectHookList();
      hookList.setEnabled(Constants.TRUE);
      hookList.buildList(db);
      //get the number of http-xml clients 
      SyncClientList syncClientList = new SyncClientList();
      syncClientList.setEnabledOnly(Constants.TRUE);
      syncClientList.buildList(db);
      //get the number of http-xml users
      UserList httpUsers = new UserList();
      httpUsers.setEnabled(Constants.TRUE);
      httpUsers.setHasHttpApiAccess(Constants.TRUE);
      httpUsers.buildList(db);
      //get the number of webdav users
      UserList webdavUsers = new UserList();
      webdavUsers.setEnabled(Constants.TRUE);
      webdavUsers.setHasWebdavAccess(Constants.TRUE);
      webdavUsers.buildList(db);
      context.getRequest().setAttribute(
          "processes", "" + processList.values().size());
      context.getRequest().setAttribute(
          "hooks", "" + hookList.getSizeOfActions());
      context.getRequest().setAttribute(
          "clients", "" + syncClientList.size());
      context.getRequest().setAttribute(
          "httpUsers", "" + httpUsers.size());
      context.getRequest().setAttribute(
          "webdavUsers", "" + webdavUsers.size());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    // get the session timeout
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
        "ConnectionElement");
    int sessionTimeout = ((SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
        "SystemStatus")).get(ce.getUrl())).getSessionTimeout();
    context.getRequest().setAttribute(
        "Timeout", String.valueOf(sessionTimeout / 60));
    // forward to JSP
    addModuleBean(context, "Configuration", "Configuration");
    return ("GlobalParamsOK");
  }


  /**
   * Action that routes to the modify timeout page
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModifyTimeout(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Configuration");
    return ("ModifyTimeoutOK");
  }


  /**
   * Action that updates the global session timeout from form data
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateTimeout(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    int timeout = Integer.parseInt(
        context.getRequest().getParameter("timeout"));
    addModuleBean(context, "Configuration", "Configuration");
    //get the session timeout and update
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
        "ConnectionElement");
    SystemStatus thisSystem = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
        "SystemStatus")).get(ce.getUrl());
    thisSystem.setSessionTimeout(timeout * 60);
    return "UpdateOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Configuration");
    String module = context.getRequest().getParameter("param");
    //Allowable params configured
    if (module == null) {
      return "ModifyError";
    }
    if ("COMPANYINFO".equals(module)) {
      Connection db = null;
      try {
        db = this.getConnection(context);
        SystemStatus systemStatus = this.getSystemStatus(context);

        Organization myOrg = new Organization(db, 0);
        context.getRequest().setAttribute("OrgDetails", myOrg);

        LookupList phoneTypeList = systemStatus.getLookupList(
            db, "lookup_orgphone_types");
        context.getRequest().setAttribute("OrgPhoneTypeList", phoneTypeList);

        LookupList addrTypeList = systemStatus.getLookupList(
            db, "lookup_orgaddress_types");
        context.getRequest().setAttribute("OrgAddressTypeList", addrTypeList);

        LookupList emailTypeList = systemStatus.getLookupList(
            db, "lookup_orgemail_types");
        context.getRequest().setAttribute("OrgEmailTypeList", emailTypeList);

        //Make the StateSelect and CountrySelect drop down menus available in the request. 
        //This needs to be done here to provide the SystemStatus to the constructors, otherwise translation is not possible
        ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute("applicationPrefs");
        StateSelect stateSelect = new StateSelect(systemStatus, myOrg.getAddressList().getCountries()+","+prefs.get("SYSTEM.COUNTRY"));
        stateSelect.setPreviousStates(myOrg.getAddressList().getSelectedStatesHashMap());
        CountrySelect countrySelect = new CountrySelect(systemStatus);
        context.getRequest().setAttribute("StateSelect", stateSelect);
        context.getRequest().setAttribute("CountrySelect", countrySelect);
        context.getRequest().setAttribute("systemStatus", systemStatus);
      } catch (Exception e) {
        context.getRequest().setAttribute("Error", e);
        return ("SystemError");
      } finally {
        this.freeConnection(context, db);
      }
      return "ModifyCompanyInfoOK";
    }
    if ("MAILSERVER".equals(module)) {
      return "ModifyEmailOK";
    }
    if ("WORKFLOW".equals(module)) {
      return "ModifyWorkflowOK";
    }
    if ("EMAILADDRESS".equals(module)) {
      return "ModifyEmailAddressOK";
    }
    if ("ASTERISKSERVER".equals(module)) {
      return "ModifyAsteriskOK";
    }
    if ("XMPPSERVER".equals(module)) {
      return "ModifyXMPPOK";
    }
    if ("LDAPSERVER".equals(module)) {
      return "ModifyLDAPOK";
    }
    if ("FAXSERVER".equals(module)) {
      return "ModifyFaxOK";
    }
    if ("WEBSERVER.URL".equals(module)) {
      return "ModifyUrlOK";
    }
    if ("SYSTEM.TIMEZONE".equals(module)) {
      return "ModifyTimeZoneOK";
    }
    if ("SYSTEM.CURRENCY".equals(module)) {
      return "ModifyCurrencyOK";
    }
    if ("SYSTEM.LANGUAGE".equals(module)) {
      return "ModifyLanguageOK";
    }
    if ("SYSTEM.COUNTRY".equals(module)) {
      return "ModifyCountryOK";
    }
    String value = CustomHook.populateAdminConfig(module);
    if (value != null) {
      return value;
    }
    return "ModifyError";
  }

  public synchronized String executeCommandImportWorkflow(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      HashMap parts = multiPart.parseData(context.getRequest(), null);
      SystemStatus systemStatus = this.getSystemStatus(context);
      String delete = null;
      // Import the file
      FileInfo fileInfo = (FileInfo) parts.get("file");
      delete = (String) parts.get("delete");
      if (fileInfo.getFileContents().length > 0) {
        // Try to parse the XML
        XMLUtils xml = new XMLUtils(new String(fileInfo.getFileContents()));
        // TODO: In a transaction:
        // - build all the objects from XML
        // - delete existing workflow from database
        // - insert new workflow in database
        BusinessProcessImporter importer = new BusinessProcessImporter();
        db = this.getConnection(context);
        if (delete != null && "true".equals(delete)) {
          importer.deleteProcesses(db);
        } else {
          importer.setProcesses(
              systemStatus.getHookManager().getProcessList());
          importer.setHooks(systemStatus.getHookManager().getHookList());
          importer.buildEventList(db);
        }
        int sizeOfProcesses = importer.execute(db, xml.getDocumentElement());
        if (sizeOfProcesses > 0) {
          // TODO: tell the systemStatus to reload business process cached data
          synchronized (systemStatus) {
            //Reload the business processes from the database.
            systemStatus.loadWorkflows(db);
          }
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("actionError", e.getMessage());
      e.printStackTrace(System.out);
      return "ImportERROR";
    } finally {
      this.freeConnection(context, db);
    }
    return ("UpdateOK");
  }

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdate(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    //Prepare to change the prefs
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs == null) {
      return "ModifyError";
    }
    //Process the request
    String email = context.getRequest().getParameter("email");
    if (email != null) {
      prefs.add("MAILSERVER", email);
    }
    //Process the request
    String emailAddress = context.getRequest().getParameter("emailAddress");
    if (emailAddress != null) {
      prefs.add("EMAILADDRESS", emailAddress);
    }
    //Process the request
    String fax = context.getRequest().getParameter("fax");
    if (fax != null) {
      prefs.add("FAXSERVER", fax);
      if (fax != null && !"".equals(fax.trim())) {
        prefs.add("FAXENABLED", "true");
      } else {
        prefs.add("FAXENABLED", "false");
      }
    }
    //Process the request
    String asterisk = context.getRequest().getParameter("asteriskUrl");
    if (asterisk != null) {
      prefs.add("ASTERISK.URL", asterisk);
      prefs.add("ASTERISK.USERNAME", context.getRequest().getParameter("asteriskUsername"));
      prefs.add("ASTERISK.PASSWORD", context.getRequest().getParameter("asteriskPassword"));
      prefs.add("ASTERISK.CONTEXT", context.getRequest().getParameter("asteriskContext"));
      prefs.add("ASTERISK.INBOUND.ENABLED", "true".equals(context.getRequest().getParameter("asteriskInbound")) ? "true" : "false");
      prefs.add("ASTERISK.OUTBOUND.ENABLED", "true".equals(context.getRequest().getParameter("asteriskOutbound")) ? "true" : "false");
      getSystemStatus(context).startServers(context.getServletContext());
    }
    //Process the request
    String xmpp = context.getRequest().getParameter("xmppUrl");
    if (xmpp != null) {
      prefs.add("XMPP.CONNECTION.URL", xmpp);
      prefs.add("XMPP.CONNECTION.PORT", context.getRequest().getParameter("xmppPort"));
      prefs.add("XMPP.MANAGER.USERNAME", context.getRequest().getParameter("xmppUsername"));
      prefs.add("XMPP.MANAGER.PASSWORD", context.getRequest().getParameter("xmppPassword"));
      prefs.add("XMPP.CONNECTION.SSL", "true".equals(context.getRequest().getParameter("xmppSSL")) ? "true" : "false");
      prefs.add("XMPP.ENABLED", "true".equals(context.getRequest().getParameter("xmppEnabled")) ? "true" : "false");
      getSystemStatus(context).startServers(context.getServletContext());
    }
    //Process the request
    String ldap = context.getRequest().getParameter("ldapUrl");
    if (ldap != null) {
      prefs.add("LDAP.SERVER", ldap);
      prefs.add("LDAP.CENTRIC_CRM.FIELD", context.getRequest().getParameter("ldapCentricCRMField"));
      prefs.add("LDAP.FACTORY", context.getRequest().getParameter("ldapFactory"));
      prefs.add("LDAP.SEARCH.USERNAME", context.getRequest().getParameter("ldapSearchUsername"));
      prefs.add("LDAP.SEARCH.PASSWORD", context.getRequest().getParameter("ldapSearchPassword"));
      prefs.add("LDAP.SEARCH.CONTAINER", context.getRequest().getParameter("ldapSearchContainer"));
      prefs.add("LDAP.SEARCH.ORGPERSON", context.getRequest().getParameter("ldapSearchOrgPerson"));
      prefs.add("LDAP.SEARCH.ATTRIBUTE", context.getRequest().getParameter("ldapSearchAttribute"));
      prefs.add("LDAP.SEARCH.PREFIX", context.getRequest().getParameter("ldapSearchPrefix"));
      prefs.add("LDAP.SEARCH.POSTFIX", context.getRequest().getParameter("ldapSearchPostfix"));
      prefs.add("LDAP.SEARCH.BY_ATTRIBUTE", "true".equals(context.getRequest().getParameter("ldapSearchByAttribute")) ? "true" : "false");
      prefs.add("LDAP.SEARCH.SUBTREE", "true".equals(context.getRequest().getParameter("ldapSearchSubtree")) ? "true" : "false");
      prefs.add("LDAP.ENABLED", "true".equals(context.getRequest().getParameter("ldapEnabled")) ? "true" : "false");
    }
    //Process the request
    String url = context.getRequest().getParameter("url");
    if (url != null) {
      prefs.add("WEBSERVER.URL", url);
    }
    //Process the request
    String timeZone = context.getRequest().getParameter("timeZone");
    if (timeZone != null) {
      prefs.add("SYSTEM.TIMEZONE", timeZone);
    }
    //Process the request
    String currency = context.getRequest().getParameter("currency");
    if (currency != null) {
      prefs.add("SYSTEM.CURRENCY", currency);
    }
    //Process the request
    String language = context.getRequest().getParameter("language");
    if (language != null) {
      prefs.add("SYSTEM.LANGUAGE", language);
      getSystemStatus(context).setLanguage(language);
    }
    //Process the request
    String country = context.getRequest().getParameter("country");
    if (country != null) {
      prefs.add("SYSTEM.COUNTRY", country);
    }
    //Save the prefs...
    prefs.save();
    prefs.populateContext(context.getServletContext());
    prefs.loadApplicationDictionary(context.getServletContext());
    return "UpdateOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateLicense(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    return CustomHook.populateLicense(context, getSystemStatus(context));
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateCompanyInfo(ActionContext context) {
    if (!(hasPermission(context, "admin-sysconfig-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int resultCount = -1;
    boolean isValid = false;
    Organization myOrg = (Organization) context.getFormBean();
    myOrg.setOrgId(0);
    myOrg.setEnteredBy(getUserId(context));
    myOrg.setModifiedBy(getUserId(context));
    try {
      db = this.getConnection(context);
      isValid = this.validateObject(context, db, myOrg);
      if (isValid) {
        myOrg.setRequestItems(context);
        resultCount = myOrg.update(db);
      }
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "Modify Account");
    if (resultCount == -1 || !isValid) {
      return ("ModifyCompanyInfoERROR");
    } else if (resultCount == 1) {
      return ("UpdateOK");
    } else {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
      return ("UserError");
    }
  }

}