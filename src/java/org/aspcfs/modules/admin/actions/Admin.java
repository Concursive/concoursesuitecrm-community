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

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemVersionList;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.actionplans.base.ActionPlanList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.ContactType;
import org.aspcfs.modules.contacts.base.ContactTypeList;
import org.aspcfs.modules.system.base.ApplicationVersion;
import org.aspcfs.modules.system.base.DatabaseVersion;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.LookupListElement;
import org.aspcfs.utils.web.LookupListList;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;

/**
 * Admin actions
 *
 * @author chris
 * @version $Id$
 * @created January 7, 2002
 */
public final class Admin extends CFSModule {

  /**
   * Default action, calls home action.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    return executeCommandHome(context);
  }


  /**
   * Home.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandHome(ActionContext context) {
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Admin", "Admin");
    return ("HomeOK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandManage(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Management");
    return "ManagementOK";
  }


  /**
   * Action that prepares system usage data
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUsage(ActionContext context) {
    if (!hasPermission(context, "admin-usage-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Usage");
    Connection db = null;
    ArrayList usageList = new ArrayList();
    ArrayList usageList2 = new ArrayList();
    String rangeSelect = context.getRequest().getParameter("rangeSelect");
    java.sql.Timestamp dateStart = null;
    String dateStartParam = context.getRequest().getParameter("dateStart");
    if (dateStartParam != null) {
      dateStart = DatabaseUtils.parseDateToTimestamp(dateStartParam);
      if (dateStart != null) {
        rangeSelect = "custom";
      }
    }
    java.sql.Timestamp dateEnd = null;
    String dateEndParam = context.getRequest().getParameter("dateEnd");
    if (dateEndParam != null) {
      dateEnd = DatabaseUtils.parseDateToTimestamp(dateEndParam);
      if (dateEnd != null) {
        rangeSelect = "custom";
      }
    }
    context.getRequest().setAttribute("rangeSelect", rangeSelect);
    SystemStatus systemStatus = this.getSystemStatus(context);
    try {
      Calendar cal = Calendar.getInstance();
      //Date Start Range
      if (dateStart != null) {
        cal.setTimeInMillis(dateStart.getTime());
      }
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      long startRange = cal.getTimeInMillis();
      context.getRequest().setAttribute(
          "dateStart", new java.sql.Timestamp(cal.getTimeInMillis()));
      //Date End Range
      if (dateStart != null && dateEnd != null && dateEnd.after(dateStart)) {
        cal.setTimeInMillis(dateEnd.getTime());
      }
      cal.set(Calendar.HOUR_OF_DAY, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
      cal.set(Calendar.MILLISECOND, 999);
      long endRange = cal.getTimeInMillis();
      context.getRequest().setAttribute(
          "dateEnd", new java.sql.Timestamp(cal.getTimeInMillis()));

      NumberFormat nf = NumberFormat.getInstance();
      db = this.getConnection(context);

      //Users enabled as of date range
      UserList userList = new UserList();
      userList.setEnabled(Constants.TRUE);
      userList.setRoleType(Constants.ROLETYPE_REGULAR);
      int userListCount = userList.queryRecordCount(db);
      if (systemStatus != null) {
        String logEntry = null;
        if (userListCount == 1) {
          logEntry = systemStatus.getLabel("admin.usage.userEnabled.text");
        } else {
          logEntry = systemStatus.getLabel("admin.usage.usersEnabled.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(userListCount));
          usageList.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList.add(
              nf.format(userListCount) + " enabled user" + StringUtils.addS(
                  userListCount));
        }
      } else {
        usageList.add(
            nf.format(userListCount) + " enabled user" + StringUtils.addS(
                userListCount));
      }

      //File count: x size: x
      FileItemVersionList fileList = new FileItemVersionList();
      int fileCount = fileList.queryRecordCount(db);
      long fileSize = fileList.queryFileSize(db);
      if (systemStatus != null) {
        String logEntry = null;
        if (fileCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.oneFileStoredDocumentLibraryWithSize.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.filesStoredDocumentLibraryWithSize.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(fileCount));
          map.put("${size}", nf.format(fileSize));
          usageList.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList.add(
              nf.format(fileCount) + " file" + StringUtils.addS(fileCount) + " stored in document library using " + nf.format(
                  fileSize) + " megabyte" + StringUtils.addS(fileSize) + " of storage");
        }
      } else {
        usageList.add(
            nf.format(fileCount) + " file" + StringUtils.addS(fileCount) + " stored in document library using " + nf.format(
                fileSize) + " megabyte" + StringUtils.addS(fileSize) + " of storage");
      }

      //Logins
      AccessLogList accessLog = new AccessLogList();
      accessLog.setEnteredRangeStart(new java.sql.Timestamp(startRange));
      accessLog.setEnteredRangeEnd(new java.sql.Timestamp(endRange));
      int logins = accessLog.queryRecordCount(db);
      if (systemStatus != null) {
        String logEntry = null;
        if (logins == 1) {
          logEntry = systemStatus.getLabel("admin.usage.login.text");
        } else {
          logEntry = systemStatus.getLabel("admin.usage.logins.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(logins));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(logins) + " login" + StringUtils.addS(logins));
        }
      } else {
        usageList2.add(
            nf.format(logins) + " login" + StringUtils.addS(logins));
      }

      //Prepare to get usage for several different actions
      UsageList usage = new UsageList();
      usage.setEnteredRangeStart(new java.sql.Timestamp(startRange));
      usage.setEnteredRangeEnd(new java.sql.Timestamp(endRange));

      //Upstream bw: x, the specific files uploaded on date x size
      usage.setAction(Constants.USAGE_FILE_UPLOAD);
      usage.buildUsage(db);
      long fileUploadCount = usage.getCount();
      long fileUploadSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (fileUploadCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.fileUploadedBandwidth.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.filesUploadedBandwidth.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(fileUploadCount));
          map.put("${size}", nf.format(fileUploadSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(fileUploadCount) + " file" + StringUtils.addS(
                  fileUploadCount) + " uploaded, using " + nf.format(
                      fileUploadSize) + " byte" + StringUtils.addS(
                          fileUploadSize) + " of bandwidth");
        }
      } else {
        usageList2.add(
            nf.format(fileUploadCount) + " file" + StringUtils.addS(
                fileUploadCount) + " uploaded, using " + nf.format(
                    fileUploadSize) + " byte" + StringUtils.addS(
                        fileUploadSize) + " of bandwidth");
      }

      //Downstream bw: x
      usage.setAction(Constants.USAGE_FILE_DOWNLOAD);
      usage.buildUsage(db);
      long fileDownloadCount = usage.getCount();
      long fileDownloadSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (fileDownloadCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.fileDownloadedBandwidth.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.filesDownloadedBandwidth.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(fileDownloadCount));
          map.put("${size}", nf.format(fileDownloadSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(fileDownloadCount) + " file" + StringUtils.addS(
                  fileDownloadCount) + " downloaded, using " + nf.format(
                      fileDownloadSize) + " byte" + StringUtils.addS(
                          fileDownloadSize) + " of bandwidth");
        }
      } else {
        usageList2.add(
            nf.format(fileDownloadCount) + " file" + StringUtils.addS(
                fileDownloadCount) + " downloaded, using " + nf.format(
                    fileDownloadSize) + " byte" + StringUtils.addS(
                        fileDownloadSize) + " of bandwidth");
      }

      //Communications Manager emails
      usage.setAction(Constants.USAGE_COMMUNICATIONS_EMAIL);
      usage.buildUsage(db);
      long emailRecipientCount = usage.getCount();
      long emailSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (emailRecipientCount == 1) {
          logEntry = systemStatus.getLabel(
              "admin.usage.communicationManagerOneEmail.text");
        } else {
          logEntry = systemStatus.getLabel(
              "admin.usage.communicationManagerEmails.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(emailRecipientCount));
          map.put("${size}", nf.format(emailSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(emailRecipientCount) + " email" + StringUtils.addS(
                  emailRecipientCount) + " sent, consisting of " + nf.format(
                      emailSize) + " byte" + StringUtils.addS(emailSize));
        }
      } else {
        usageList2.add(
            nf.format(emailRecipientCount) + " email" + StringUtils.addS(
                emailRecipientCount) + " sent, consisting of " + nf.format(
                    emailSize) + " byte" + StringUtils.addS(emailSize));
      }

      //Communications Manager faxes
      usage.setAction(Constants.USAGE_COMMUNICATIONS_FAX);
      usage.buildUsage(db);
      long faxRecipientCount = usage.getCount();
      long faxSize = usage.getSize();
      if (systemStatus != null) {
        String logEntry = null;
        if (faxRecipientCount == 1) {
          logEntry = systemStatus.getLabel("admin.usage.oneFaxSent.text");
        } else {
          logEntry = systemStatus.getLabel("admin.usage.faxsSent.text");
        }
        if (logEntry != null) {
          HashMap map = new HashMap();
          map.put("${number}", nf.format(faxRecipientCount));
          map.put("${size}", nf.format(faxSize));
          usageList2.add(this.getLabel(systemStatus, map, logEntry));
        } else {
          usageList2.add(
              nf.format(faxRecipientCount) + " fax" + StringUtils.addES(
                  faxRecipientCount) + " sent, consisting of  " + nf.format(
                      faxSize) + " byte" + StringUtils.addS(faxSize));
        }
      } else {
        usageList2.add(
            nf.format(faxRecipientCount) + " fax" + StringUtils.addES(
                faxRecipientCount) + " sent, consisting of  " + nf.format(
                    faxSize) + " byte" + StringUtils.addS(faxSize));
      }

      context.getRequest().setAttribute("usageList", usageList);
      context.getRequest().setAttribute("usageList2", usageList2);

      //Application Version
      String appVersion = ApplicationVersion.VERSION;
      context.getRequest().setAttribute("applicationVersion", appVersion);

      //Database Version
      String dbVersion = DatabaseVersion.getLatestVersion(db);
      context.getRequest().setAttribute("databaseVersion", dbVersion);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "UsageOK";
  }


  /**
   * Action that prepares a list of modules that can be configured
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandConfig(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    Exception errorMessage = null;
    addModuleBean(context, "Configuration", "Configuration");
    Connection db = null;
    PermissionCategoryList thisPermCatList = new PermissionCategoryList();

    try {
      db = this.getConnection(context);
      thisPermCatList.setEnabledState(1);
      thisPermCatList.setCustomizableModulesOnly(true);
      thisPermCatList.buildList(db);
      context.getRequest().setAttribute(
          "PermissionCategoryList", thisPermCatList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("ConfigurationOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }

  }


  /**
   * Action that prepares a list of specific module items that can be
   * configured.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfigDetails(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-view")) {
      return ("PermissionError");
    }
    addModuleBean(context, "Configuration", "Configuration");
    Connection db = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      PermissionCategory permCat = new PermissionCategory(
          db, Integer.parseInt(moduleId));
      context.getRequest().setAttribute("PermissionCategory", permCat);
      return ("ConfigDetailsOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Action that prepares a list of the configurable module lookup lists
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandEditLists(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-lists-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    PermissionCategory permCat = null;
    String moduleId = context.getRequest().getParameter("moduleId");
    try {
      db = this.getConnection(context);
      permCat = new PermissionCategory(db, Integer.parseInt(moduleId));
      buildFormElements(context, db);
      context.getRequest().setAttribute("PermissionCategory", permCat);
      addModuleBean(context, "Configuration", "Configuration");
      return ("EditListsOK");
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   * Action that updates a particular list with the new values
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandUpdateList(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-lists-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    String tblName = context.getRequest().getParameter("tableName");
    if ("lookup_contact_types".equals(tblName)) {
      return executeCommandUpdateContactList(context);
    }
    String[] params = context.getRequest().getParameterValues("selectedList");
    String[] names = new String[params.length];
    int j = 0;
    StringTokenizer st = new StringTokenizer(
        context.getRequest().getParameter("selectNames"), "^");
    while (st.hasMoreTokens()) {
      names[j] = (String) st.nextToken();
      j++;
    }
    try {
      db = this.getConnection(context);
      //begin for all lookup lists
      LookupList compareList = new LookupList(db, tblName);
      LookupList newList = new LookupList(params, names);
      Iterator i = compareList.iterator();
      while (i.hasNext()) {
        LookupElement thisElement = (LookupElement) i.next();
        //still there, stay enabled, don't re-insert it
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "Here: " + thisElement.getCode() + " " + newList.getSelectedValue(
                  thisElement.getCode()));
        }
        //not there, disable it, leave it
        if (newList.getSelectedValue(thisElement.getCode()).equals("") ||
            newList.getSelectedValue(thisElement.getCode()) == null) {
          thisElement.disableElement(db, tblName);
        }
      }
      Iterator k = newList.iterator();
      while (k.hasNext()) {
        LookupElement thisElement = (LookupElement) k.next();
        if (thisElement.getCode() == 0) {
          int thisCode = -1;
          if ((thisCode = thisElement.isDisabled(db, tblName)) != -1) {
            thisElement.setCode(thisCode);
            thisElement.enableElement(db, tblName);
            thisElement.setNewOrder(db, tblName);
          } else {
            thisElement.insertElement(db, tblName);
          }
        } else {
          thisElement.setNewOrder(db, tblName);
          if (!thisElement.getDescription().equals(
              compareList.getValueFromId(thisElement.getCode()))) {
            thisElement.setNewDescription(db, tblName);
          }
        }
      }
      //invalidate the cache for this list
      SystemStatus systemStatus = this.getSystemStatus(context);
      systemStatus.removeLookup(tblName);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "moduleId", context.getRequest().getParameter("module"));
    addModuleBean(context, "Configuration", "Configuration");
    return (executeCommandEditLists(context));
  }


  /**
   * Updates the Contact Types list .<br>
   * Note : Treated as a special case of Lookup List as it has category &
   * userId associated.
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpdateContactList(ActionContext context) {
    Connection db = null;
    String category = context.getRequest().getParameter("category");
    String[] params = context.getRequest().getParameterValues("selectedList");
    String[] names = new String[params.length];
    int j = 0;
    StringTokenizer st = new StringTokenizer(
        context.getRequest().getParameter("selectNames"), "^");
    while (st.hasMoreTokens()) {
      names[j] = (String) st.nextToken();
      j++;
    }
    try {
      db = this.getConnection(context);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Admin -- > Updating Contact Types ");
      }
      ContactTypeList compareList = new ContactTypeList();
      compareList.setCategory(category);
      compareList.setIncludeDefinedByUser(this.getUserId(context));
      compareList.buildList(db);
      ContactTypeList newList = new ContactTypeList(params, names);
      Iterator i = compareList.iterator();
      while (i.hasNext()) {
        ContactType thisType = (ContactType) i.next();
        //not there, disable it, leave it
        if (newList.getElement(thisType.getId()) == null) {
          thisType.setEnabled(db, false);
        }
      }
      Iterator k = newList.iterator();
      while (k.hasNext()) {
        ContactType thisType = (ContactType) k.next();
        thisType.setCategory(category);
        if (thisType.getId() == 0) {
          thisType.insert(db);
        } else {
          thisType.setNewOrder(db);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute(
        "moduleId", context.getRequest().getParameter("module"));
    addModuleBean(context, "Configuration", "Configuration");
    return (executeCommandEditLists(context));
  }


  /**
   * Action that prepares the selected lookup list for modification
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandModifyList(ActionContext context) {
    if (!hasPermission(context, "admin-sysconfig-lists-edit")) {
      return ("PermissionError");
    }
    Connection db = null;
    int moduleId = -1;
    int lookupId = -1;
    LookupList selectedList = null;
    PermissionCategory permCat = null;
    try {
      db = this.getConnection(context);
      moduleId = Integer.parseInt(context.getRequest().getParameter("module"));
      lookupId = Integer.parseInt(
          context.getRequest().getParameter("sublist"));
      permCat = new PermissionCategory(db, moduleId);
      LookupListElement thisList = new LookupListElement(
          db, moduleId, lookupId);
      thisList.buildLookupList(
          this.getSystemStatus(context), db, this.getUserId(context));
      selectedList = thisList.getLookupList();
      switch (thisList.getCategoryId()) {
        case PermissionCategory.PERMISSION_CAT_CONTACTS:
          if (lookupId == PermissionCategory.LOOKUP_CONTACTS_TYPE) {
            context.getRequest().setAttribute(
                "category", String.valueOf(ContactType.GENERAL));
          }
          break;
        case PermissionCategory.PERMISSION_CAT_ACCOUNTS:
          if (lookupId == PermissionCategory.LOOKUP_ACCOUNTS_CONTACTS_TYPE) {
            context.getRequest().setAttribute(
                "category", String.valueOf(ContactType.ACCOUNT));
          }
          break;
        default:
          break;
      }
      context.getRequest().setAttribute("moduleId", String.valueOf(moduleId));
      context.getRequest().setAttribute("SelectedList", selectedList);
      context.getRequest().setAttribute("SubTitle", thisList.getDescription());
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (selectedList != null) {
      selectedList.setSelectSize(8);
      selectedList.setMultiple(true);
    }
    addModuleBean(context, "Configuration", "Configuration");
    context.getRequest().setAttribute("PermissionCategory", permCat);
    return ("ModifyListOK");
  }


  /**
   * Build all the necessarry form elements (lists)
   *
   * @param context Description of Parameter
   * @param db      Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildFormElements(ActionContext context, Connection db) throws SQLException {
    int moduleId = Integer.parseInt(
        context.getRequest().getParameter("moduleId"));
    LookupListList thisList = new LookupListList();
    thisList.setUserId(this.getUserId(context));
    thisList.setModuleId(moduleId);
    thisList.buildList(this.getSystemStatus(context), db);
    //NOTE: This is a temporary fix to remove an edit list that is not enabled in the system
    //TODO: Lookup lists could be tied to a specific permission
    if (!hasPermission(context, "accounts-accounts-revenue-view")) {
      thisList.removeList(
          PermissionCategory.PERMISSION_CAT_ACCOUNTS, PermissionCategory.LOOKUP_ACCOUNTS_REVENUE_TYPE);
    }
    context.getRequest().setAttribute("LookupLists", thisList);
  }

  public String getLabel(SystemStatus systemStatus, HashMap map, String input) {
    Template template = new Template(input);
    template.setParseElements(map);
    return template.getParsedText();
  }
}

