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
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import org.aspcfs.apps.workFlowManager.BusinessProcessImporter;
import org.aspcfs.apps.workFlowManager.BusinessProcessList;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.objectHookManager.ObjectHookList;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.service.base.Record;
import org.aspcfs.modules.service.base.RecordList;
import org.aspcfs.modules.service.base.TransactionStatus;
import org.aspcfs.modules.setup.beans.UpdateBean;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.CountrySelect;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;
import sun.misc.BASE64Encoder;

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
      context.getRequest().setAttribute(
          "processes", "" + processList.values().size());
      context.getRequest().setAttribute(
          "hooks", "" + hookList.getSizeOfActions());
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
        StateSelect stateSelect = new StateSelect(systemStatus);
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
    // BEGIN DHV CODE ONLY
    if ("LICENSE".equals(module)) {
      return "ModifyLicenseOK";
    }
    // END DHV CODE ONLY
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
    }
    //Process the request
    String country = context.getRequest().getParameter("country");
    if (country != null) {
      prefs.add("SYSTEM.COUNTRY", country);
    }
    //Save the prefs...
    prefs.save();
    prefs.populateContext(context.getServletContext());
    prefs.loadLocalizationPrefs(context.getServletContext());
    return "UpdateOK";
  }


  // BEGIN DHV CODE ONLY
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
    //Prepare to change the prefs
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    if (prefs == null) {
      return "ModifyError";
    }
    //Process the parameters
    String process = context.getRequest().getParameter("doLicense");
    try {
      //If remote check, then perform steps similar to the initial setup
      if ("internet".equals(process)) {
        UpdateBean bean = new UpdateBean();
        //Send the current key, email address, profile, and crc
        java.security.Key key = org.aspcfs.utils.PrivateString.loadKey(
            getPref(context, "FILELIBRARY") + "init" + fs + "zlib.jar");
        XMLUtils xml = new XMLUtils(
            org.aspcfs.utils.PrivateString.decrypt(
                key,
                StringUtils.loadText(
                    getPref(context, "FILELIBRARY") + "init" + fs + "input.txt")));
        //Encode the license for transmission
        BASE64Encoder encoder = new BASE64Encoder();
        bean.setZlib(encoder.encode(ObjectUtils.toByteArray(key)));
        bean.setEmail(XMLUtils.getNodeText(xml.getFirstChild("email")));
        bean.setProfile(XMLUtils.getNodeText(xml.getFirstChild("profile")));
        bean.setText(PrivateString.encrypt(key, "UPGRADE-1.0"));
        bean.setText2(XMLUtils.getNodeText(xml.getFirstChild("text2")));
        //Make sure the server received the key ok
        String response = null;
        boolean ssl = true;
        if (ssl) {
          response = HTTPUtils.sendPacket(
              "https://registration.centriccrm.com/LicenseServer.do?command=RequestLicense", bean.toXmlString());
        } else {
          response = HTTPUtils.sendPacket(
              "http://registration.centriccrm.com/LicenseServer.do?command=RequestLicense", bean.toXmlString());
        }
        if (response == null) {
          context.getRequest().setAttribute(
              "actionError", this.getSystemStatus(context).getLabel(
                  "object.validation.unspecifiedErrorNoResponse"));
          return "LicenseCheckERROR";
        }
        XMLUtils responseXML = new XMLUtils(response);
        TransactionStatus thisStatus = new TransactionStatus(
            responseXML.getFirstChild("response"));
        if (thisStatus.getStatusCode() != 0) {
          context.getRequest().setAttribute(
              "actionError", this.getSystemStatus(context).getLabel(
                  "object.validation.unspecifiedErrorRequestRejected") + thisStatus.getMessage());
          return "LicenseCheckERROR";
        }
        //Response is good so save the new license
        RecordList recordList = thisStatus.getRecordList();
        if (recordList != null && recordList.getName().equals("license") && !recordList.isEmpty()) {
          Record record = (Record) recordList.get(0);
          if (record != null && record.getAction().equals("update")) {
            String updatedLicense = (String) record.get("license");
            //Validate and save it
            XMLUtils xml2 = new XMLUtils(
                PrivateString.decrypt(key, updatedLicense));
            String entered = XMLUtils.getNodeText(
                xml2.getFirstChild("entered"));
            if (entered == null) {
              return "LicenseCheckERROR";
            }
            StringUtils.saveText(
                getPref(context, "FILELIBRARY") + "init" + fs + "input.txt", updatedLicense);
            context.getServletContext().setAttribute(
                "APP_TEXT", XMLUtils.getNodeText(
                    xml2.getFirstChild("edition")));
            String text2 = XMLUtils.getNodeText(xml2.getFirstChild("text2")).substring(
                7);
            if ("-1".equals(text2)) {
              context.getServletContext().removeAttribute("APP_SIZE");
            } else {
              context.getServletContext().setAttribute("APP_SIZE", text2);
            }
            return "LicenseUpdatedOK";
          }
        }
      }
      //If manual input, then allow user to input the license code
      if ("manual".equals(process)) {

      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return "LicenseCheckERROR";
    }
    return "LicenseCheckOK";
  }
  // END DHV CODE ONLY


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

