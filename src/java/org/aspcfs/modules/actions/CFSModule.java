package org.aspcfs.modules.actions;

import com.darkhorseventures.framework.actions.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.servlets.*;
import org.aspcfs.controller.RecentItem;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.communications.base.Campaign;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.beans.ModuleBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.controller.objectHookManager.*;
import org.aspcfs.controller.*;

import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.reflect.*;
/**
 *  Base class for all modules
 *
 *@author     mrajkowski
 *@created    July 15, 2001
 *@version    $Id: CFSModule.java,v 1.34.2.2 2002/12/20 22:22:40 mrajkowski Exp
 *      $
 */
public class CFSModule {

  public final static String fs = System.getProperty("file.separator");
  public final static String NOT_UPDATED_MESSAGE =
      "<b>This record could not be updated because someone else updated it first.</b><p>" +
      "You can hit the back button to review the changes that could not be committed, " +
      "but you must reload the record and make the changes again.";


  /**
   *  Gets the PagedListInfo attribute of the CFSModule object
   *
   *@param  context   Description of Parameter
   *@param  viewName  Description of Parameter
   *@return           The PagedListInfo value
   *@since            1.1
   */
  protected PagedListInfo getPagedListInfo(ActionContext context, String viewName) {
    PagedListInfo tmpInfo = (PagedListInfo) context.getSession().getAttribute(viewName);
    if (tmpInfo == null) {
      tmpInfo = new PagedListInfo();
      tmpInfo.setId(viewName);
      context.getSession().setAttribute(viewName, tmpInfo);
    }
    tmpInfo.setParameters(context);
    return tmpInfo;
  }


  /**
   *  Returns the ViewpointInfo object setting the required parameters <br>
   *  Creates the object and stores it in the session if it's not already in
   *  there.
   *
   *@param  context   Description of the Parameter
   *@param  viewName  Description of the Parameter
   *@return           The viewpointInfo value
   */
  protected ViewpointInfo getViewpointInfo(ActionContext context, String viewName) {
    SystemStatus systemStatus = this.getSystemStatus(context);
    UserSession thisSession = systemStatus.getSessionManager().getUserSession(this.getActualUserId(context));
    ViewpointInfo tmpInfo = (ViewpointInfo) context.getSession().getAttribute(viewName);
    //if viewpoints were updated then invalidate the reload ViewpointInfo
    if (!thisSession.isViewpointsValid()) {
      tmpInfo = null;
    }
    if (tmpInfo == null) {
      tmpInfo = new ViewpointInfo();
      tmpInfo.setId(viewName);
      context.getSession().setAttribute(viewName, tmpInfo);
    }
    tmpInfo.setParameters(context);
    return tmpInfo;
  }


  /**
   *  Gets the pagedListInfo attribute of the CFSModule object
   *
   *@param  context        Description of the Parameter
   *@param  viewName       Description of the Parameter
   *@param  defaultColumn  Description of the Parameter
   *@param  defaultOrder   Description of the Parameter
   *@return                The pagedListInfo value
   */
  protected PagedListInfo getPagedListInfo(ActionContext context, String viewName, String defaultColumn, String defaultOrder) {
    PagedListInfo tmpInfo = (PagedListInfo) context.getSession().getAttribute(viewName);
    if (tmpInfo == null) {
      tmpInfo = new PagedListInfo();
      tmpInfo.setId(viewName);
      tmpInfo.setColumnToSortBy(defaultColumn);
      tmpInfo.setSortOrder(defaultOrder);
      context.getSession().setAttribute(viewName, tmpInfo);
    }
    tmpInfo.setParameters(context);
    return tmpInfo;
  }


  /**
   *  Retrieves a connection from the connection pool
   *
   *@param  context           Description of Parameter
   *@return                   The Connection value
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  protected Connection getConnection(ActionContext context) throws SQLException {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    return this.getConnection(context, ce);
  }


  /**
   *  Gets the connection attribute of the CFSModule object
   *
   *@param  context           Description of the Parameter
   *@param  ce                Description of the Parameter
   *@return                   The connection value
   *@exception  SQLException  Description of the Exception
   */
  protected Connection getConnection(ActionContext context, ConnectionElement ce) throws SQLException {
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    return sqlDriver.getConnection(ce);
  }


  /**
   *  Gets the UserId attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The UserId value
   *@since           1.7
   */
  protected int getUserId(ActionContext context) {
    return ((UserBean) context.getSession().getAttribute("User")).getUserId();
  }


  /**
   *  Gets the actualUserId attribute of the CFSModule object
   *
   *@param  context  Description of the Parameter
   *@return          The actualUserId value
   */
  protected int getActualUserId(ActionContext context) {
    return ((UserBean) context.getSession().getAttribute("User")).getActualUserId();
  }


  /**
   *  Gets the UserRange attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The UserRange value
   *@since
   */
  protected String getUserRange(ActionContext context) {
    return ((UserBean) context.getSession().getAttribute("User")).getIdRange();
  }


  /**
   *  Gets the userRange attribute of the CFSModule object
   *
   *@param  context  Description of the Parameter
   *@param  userId   Description of the Parameter
   *@return          The userRange value
   */
  protected String getUserRange(ActionContext context, int userId) {
    User userRecord = this.getUser(context, userId);
    UserList shortChildList = userRecord.getShortChildList();
    UserList fullChildList = userRecord.getFullChildList(shortChildList, new UserList());
    return fullChildList.getUserListIds(userRecord.getId());
  }


  /**
   *  Gets the NameLast attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The NameLast value
   *@since
   */
  protected String getNameLast(ActionContext context) {
    return ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getNameLast();
  }


  /**
   *  Gets the NameFirst attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The NameFirst value
   *@since
   */
  protected String getNameFirst(ActionContext context) {
    return ((UserBean) context.getSession().getAttribute("User")).getUserRecord().getContact().getNameFirst();
  }


  /**
   *  Gets the systemStatus attribute of the CFSModule object
   *
   *@param  context  Description of the Parameter
   *@return          The systemStatus value
   */
  protected SystemStatus getSystemStatus(ActionContext context) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    return this.getSystemStatus(context, ce);
  }


  /**
   *  Gets the systemStatus attribute of the CFSModule object
   *
   *@param  context  Description of the Parameter
   *@param  ce       Description of the Parameter
   *@return          The systemStatus value
   */
  protected SystemStatus getSystemStatus(ActionContext context, ConnectionElement ce) {
    return (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
  }


  /**
   *  Gets the User attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@param  userId   Description of Parameter
   *@return          The User value
   *@since
   */
  protected User getUser(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    return systemStatus.getUser(userId);
  }


  /**
   *  Gets the userTable attribute of the CFSModule object
   *
   *@param  context  Description of the Parameter
   *@return          The userTable value
   */
  protected Hashtable getUserTable(ActionContext context) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    return systemStatus.getUserList();
  }


  /**
   *  Description of the Method
   *
   *@param  context     Description of the Parameter
   *@param  permission  Description of the Parameter
   *@return             Description of the Return Value
   */
  protected boolean hasPermission(ActionContext context, String permission) {
    UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
    SystemStatus systemStatus = this.getSystemStatus(context);
    return (systemStatus.hasPermission(thisUser.getUserId(), permission));
  }


  /**
   *  Description of the Method
   *
   *@param  sourceFile  Description of the Parameter
   *@return             Description of the Return Value
   */
  public static String includeFile(String sourceFile) {
    StringBuffer HTMLBuffer = new StringBuffer();
    String desc;
    char[] chars = null;
    int c;
    FileReader in;
    try {
      File inputFile = new File(sourceFile);
      in = new FileReader(inputFile);
      while ((c = in.read()) != -1) {
        HTMLBuffer.append((char) c);
      }
      in.close();
    } catch (IOException ex) {
      HTMLBuffer.append(ex.toString());
    }
    return HTMLBuffer.toString();
  }


  /**
   *  Gets the DbName attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The DbName value
   *@since
   */
  public static String getDbName(ActionContext context) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    if (ce != null) {
      return ce.getDbName();
    } else {
      return null;
    }
  }


  /**
   *  Gets the dbName attribute of the CFSModule class
   *
   *@param  ce  Description of the Parameter
   *@return     The dbName value
   */
  public static String getDbName(ConnectionElement ce) {
    if (ce != null) {
      return ce.getDbName();
    } else {
      return null;
    }
  }


  /**
   *  Gets the Path attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The Path value
   *@since
   */
  protected String getPath(ActionContext context) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs);
  }


  /**
   *  Gets the path attribute of the CFSModule object
   *
   *@param  context           Description of the Parameter
   *@param  moduleFolderName  Description of the Parameter
   *@return                   The path value
   */
  protected String getPath(ActionContext context, String moduleFolderName) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs +
        (this.getDbName(context) == null ? "" : this.getDbName(context) + fs) +
        moduleFolderName + fs);
  }


  /**
   *  Gets the path attribute of the CFSModule object
   *
   *@param  context           Description of the Parameter
   *@param  ce                Description of the Parameter
   *@param  moduleFolderName  Description of the Parameter
   *@return                   The path value
   */
  protected String getPath(ActionContext context, ConnectionElement ce, String moduleFolderName) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs +
        (this.getDbName(ce) == null ? "" : this.getDbName(ce) + fs) +
        moduleFolderName + fs);
  }


  /**
   *  Gets the Path attribute of the CFSModule object, no longer used because
   *  the item id would result in too many directories on a large system
   *
   *@param  context           Description of Parameter
   *@param  moduleFolderName  Description of Parameter
   *@param  moduleItemId      Description of Parameter
   *@return                   The Path value
   *@deprecated
   */
  public static String getPath(ActionContext context, String moduleFolderName, int moduleItemId) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs +
        getDbName(context) + fs +
        moduleFolderName + fs);
  }


  /**
   *  Gets the dbNamePath attribute of the CFSModule class
   *
   *@param  context  Description of the Parameter
   *@return          The dbNamePath value
   */
  public static String getDbNamePath(ActionContext context) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs +
        getDbName(context) + fs);
  }


  /**
   *  Gets the datePath attribute of the CFSModule class
   *
   *@param  fileDate  Description of the Parameter
   *@return           The datePath value
   */
  public static String getDatePath(java.util.Date fileDate) {
    return getDatePath(new java.sql.Timestamp(fileDate.getTime()));
  }


  /**
   *  Gets the DatePath attribute of the CFSModule object
   *
   *@param  fileDate  Description of Parameter
   *@return           The DatePath value
   *@since
   */
  public static String getDatePath(java.sql.Timestamp fileDate) {
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy");
    String datePathToUse1 = formatter1.format(fileDate);
    SimpleDateFormat formatter2 = new SimpleDateFormat("MMdd");
    String datePathToUse2 = formatter2.format(fileDate);
    return datePathToUse1 + fs + datePathToUse2 + fs;
  }


  /**
   *  Gets the datePath attribute of the CFSModule class
   *
   *@param  filenameDate  Description of the Parameter
   *@return               The datePath value
   */
  public static String getDatePath(String filenameDate) {
    if (filenameDate.length() > 7) {
      return (filenameDate.substring(0, 4) + fs +
          filenameDate.substring(4, 8) + fs);
    } else {
      return null;
    }
  }


  /**
   *  Removes an object from the session
   *
   *@param  context   Description of Parameter
   *@param  viewName  Description of Parameter
   *@since            1.6
   */
  protected void deletePagedListInfo(ActionContext context, String viewName) {
    PagedListInfo tmpInfo = (PagedListInfo) context.getSession().getAttribute(viewName);
    if (tmpInfo != null) {
      context.getSession().removeAttribute(viewName);
    }
  }


  /**
   *  Returns a connection back to the connection pool
   *
   *@param  context  Description of Parameter
   *@param  db       Description of Parameter
   *@since           1.1
   */
  protected void freeConnection(ActionContext context, Connection db) {
    if (db != null) {
      ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
      sqlDriver.free(db);
    }
    db = null;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@param  db       Description of the Parameter
   */
  protected void renewConnection(ActionContext context, Connection db) {
    //Connections are usually checked out and expire, this will renew the expiration
    //time
    if (db != null) {
      ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
      sqlDriver.renew(db);
    }
    db = null;
  }


  /**
   *  This method adds module specific information to the request.
   *
   *@param  context     The feature to be added to the ModuleMenu attribute
   *@param  actionName  The feature to be added to the ModuleMenu attribute
   *@param  submenuKey  The feature to be added to the ModuleBean attribute
   *@since              1.4
   */
  protected void addModuleBean(ActionContext context, String submenuKey, String actionName) {
    ModuleBean thisModule = new ModuleBean();
    thisModule.setCurrentAction(actionName);
    thisModule.setSubmenuKey(context, submenuKey);
    context.getRequest().setAttribute("ModuleBean", thisModule);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@param  errors   Description of Parameter
   *@since           1.7
   */
  protected void processErrors(ActionContext context, HashMap errors) {
    Iterator i = errors.keySet().iterator();
    while (i.hasNext()) {
      String errorKey = (String) i.next();
      String errorMsg = (String) errors.get(errorKey);
      context.getRequest().setAttribute(errorKey, errorMsg);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(" Object Validation Error-> " + errorKey + "=" + errorMsg);
      }
    }
    context.getRequest().setAttribute("errors", errors);
    if (errors.size() > 0) {
      if (context.getRequest().getAttribute("actionError") == null) {
        context.getRequest().setAttribute("actionError", "Form could not be submitted, review messages below.");
      }
    }
  }


  /**
   *  Call whenever any module modifies anything to do with permissions, for
   *  example, if a role's permissions are modified.
   *
   *@param  context           Description of Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void updateSystemPermissionCheck(Connection db, ActionContext context) throws SQLException {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.updateRolePermissions(db);
  }


  /**
   *  Call whenever any module modifies anything to do with user's data, for
   *  example, if a user record is updated, or a user's contact info is updated.
   *  TODO: Add a method for updating just the user's contact info and not the
   *  whole hierarchy.
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void updateSystemHierarchyCheck(Connection db, ActionContext context) throws SQLException {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.updateHierarchy(db);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@param  userId   Description of the Parameter
   */
  protected void invalidateUserData(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.getHierarchyList().getUser(userId).setIsValid(false, true);
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@param  userId   Description of the Parameter
   */
  protected void invalidateUserRevenueData(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.getHierarchyList().getUser(userId).setRevenueIsValid(false, true);
  }


  /**
   *  Description of the Method
   *
   *@param  userId   Description of Parameter
   *@param  context  Description of Parameter
   *@since
   */
  protected void invalidateUserInMemory(int userId, ActionContext context) {
    Hashtable globalStatus = (Hashtable) context.getServletContext().getAttribute("SystemStatus");
    Iterator i = globalStatus.values().iterator();
    UserList shortChildList = new UserList();
    UserList fullChildList = new UserList();
    while (i.hasNext()) {
      SystemStatus thisStatus = (SystemStatus) i.next();
      UserList thisList = thisStatus.getHierarchyList();
      Iterator j = thisList.iterator();
      while (j.hasNext()) {
        User thisUser = (User) j.next();
        shortChildList = thisUser.getShortChildList();
        fullChildList = thisUser.getFullChildList(shortChildList, new UserList());
        Iterator k = fullChildList.iterator();
        while (k.hasNext()) {
          User indUser = (User) k.next();
          if (indUser.getId() == userId) {
            indUser.setIsValid(false, true);
            System.out.println("clearing: " + indUser.getId());
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context     Description of the Parameter
   *@param  itemObject  Description of the Parameter
   */
  protected void deleteRecentItem(ActionContext context, Object itemObject) {
    ArrayList recentItems = (ArrayList) context.getSession().getAttribute("RecentItems");
    if (recentItems == null) {
      recentItems = new ArrayList();
      context.getSession().setAttribute("RecentItems", recentItems);
    }

    RecentItem thisItem = this.getRecentItem(itemObject);
    if (thisItem != null) {
      Iterator i = recentItems.iterator();
      while (i.hasNext()) {
        RecentItem existingItem = (RecentItem) i.next();
        if (existingItem.getUrl().equals(thisItem.getUrl())) {
          recentItems.remove(recentItems.indexOf(existingItem));
          break;
        }
      }
    }
  }



  /**
   *  Builds and adds a RecentItem to the RecentItems list
   *
   *@param  context     The feature to be added to the RecentItem attribute
   *@param  itemObject  The feature to be added to the RecentItem attribute
   *@since
   */
  protected void addRecentItem(ActionContext context, Object itemObject) {
    ArrayList recentItems = (ArrayList) context.getSession().getAttribute("RecentItems");
    if (recentItems == null) {
      recentItems = new ArrayList();
      context.getSession().setAttribute("RecentItems", recentItems);
    }

    RecentItem thisItem = this.getRecentItem(itemObject);
    if (thisItem != null) {
      Iterator i = recentItems.iterator();
      while (i.hasNext()) {
        RecentItem existingItem = (RecentItem) i.next();
        if (existingItem.getUrl().equals(thisItem.getUrl())) {
          recentItems.remove(recentItems.indexOf(existingItem));
          break;
        }
      }

      if (recentItems.size() > 0) {
        recentItems.add(0, thisItem);
      } else {
        recentItems.add(thisItem);
      }
      while (recentItems.size() > 10) {
        recentItems.remove(10);
      }
    }
  }


  /**
   *  Gets the recentItem attribute of the CFSModule object
   *
   *@param  itemObject  Description of the Parameter
   *@return             The recentItem value
   */
  public RecentItem getRecentItem(Object itemObject) {
    //Build the recent item object
    RecentItem thisItem = null;
    if (itemObject instanceof Contact) {
      Contact thisContact = (Contact) itemObject;

      if (thisContact.hasType(Contact.EMPLOYEE_TYPE)) {
        thisItem = new RecentItem(
            RecentItem.EMPLOYEE,
            thisContact.getNameFirstLast(),
            "CompanyDirectory.do?command=EmployeeDetails&empid=" + thisContact.getId());
      } else {
        thisItem = new RecentItem(
            RecentItem.CONTACT,
            thisContact.getNameFirstLast(),
            "ExternalContacts.do?command=ContactDetails&id=" + thisContact.getId());
      }

    } else if (itemObject instanceof Organization) {
      Organization thisOrganization = (Organization) itemObject;
      thisItem = new RecentItem(
          RecentItem.ACCOUNT,
          thisOrganization.getName(),
          "Accounts.do?command=Details&orgId=" + thisOrganization.getOrgId());

    } else if (itemObject instanceof User) {
      User thisUser = (User) itemObject;
      thisItem = new RecentItem(
          RecentItem.USER,
          thisUser.getContact().getNameFirstLast(),
          "Users.do?command=UserDetails&id=" + thisUser.getId());
    } else if (itemObject instanceof Ticket) {
      Ticket thisTicket = (Ticket) itemObject;
      thisItem = new RecentItem(
          RecentItem.TICKET,
          thisTicket.getPaddedId(),
          "TroubleTickets.do?command=Details&id=" + thisTicket.getId());
    } else if (itemObject instanceof OpportunityHeader) {
      OpportunityHeader thisOpp = (OpportunityHeader) itemObject;
      thisItem = new RecentItem(
          RecentItem.OPPORTUNITY,
          thisOpp.getShortDescription(),
          "Leads.do?command=DetailsOpp&headerId=" + thisOpp.getId() + "&reset=true");
      /*
       *  /NOTE: This is too granular right now and requires some maintenance
       *  } else if (itemObject instanceof OpportunityComponent) {
       *  OpportunityComponent thisComponent = (OpportunityComponent) itemObject;
       *  thisItem = new RecentItem(
       *  RecentItem.COMPONENT,
       *  thisComponent.getShortDescription(),
       *  "LeadsComponents.do?command=DetailsComponent&id=" + thisComponent.getId());
       */
    } else if (itemObject instanceof com.zeroio.iteam.base.Project) {
      com.zeroio.iteam.base.Project thisProject = (com.zeroio.iteam.base.Project) itemObject;
      thisItem = new RecentItem(
          RecentItem.PROJECT,
          thisProject.getTitle(),
          "ProjectManagement.do?command=ProjectCenter&pid=" + thisProject.getId());
    } else if (itemObject instanceof Campaign) {
      Campaign thisCampaign = (Campaign) itemObject;
      if (thisCampaign.getActive()) {
        thisItem = new RecentItem(
            RecentItem.CAMPAIGN,
            thisCampaign.getSubject(),
            "CampaignManager.do?command=Details&id=" + thisCampaign.getId() + "&reset=true");
      } else {
        thisItem = new RecentItem(
            RecentItem.CAMPAIGN,
            thisCampaign.getSubject(),
            "CampaignManager.do?command=ViewDetails&id=" + thisCampaign.getId() + "&reset=true");
      }
    }
    return thisItem;
  }


  /**
   *  Description of the Method
   *
   *@param  text                     Description of the Parameter
   *@param  filename                 Description of the Parameter
   *@exception  java.io.IOException  Description of the Exception
   */
  public static void saveTextFile(String text, String filename) throws java.io.IOException {
    File outputFile = new File(filename);
    FileWriter out = new FileWriter(outputFile);
    out.write(text);
    out.close();
  }


  /**
   *  Gets the dynamicForm attribute of the CFSModule object
   *
   *@param  context   Description of the Parameter
   *@param  formName  Description of the Parameter
   *@return           The dynamicForm value
   */
  public CustomForm getDynamicForm(ActionContext context, String formName) {
    CustomForm thisForm = new CustomForm();
    if (((CustomFormList) context.getServletContext().getAttribute("DynamicFormList")).containsKey(formName)) {
      thisForm = (CustomForm) (((CustomForm) ((CustomFormList) context.getServletContext().getAttribute("DynamicFormList")).get(formName)).clone());
    }
    return thisForm;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@param  object   Description of the Parameter
   */
  protected void processInsertHook(ActionContext context, Object object) {
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    this.getSystemStatus(context).processHook(context, ObjectHookAction.INSERT, null, object, sqlDriver, ce);
  }


  /**
   *  Description of the Method
   *
   *@param  context         Description of the Parameter
   *@param  previousObject  Description of the Parameter
   *@param  object          Description of the Parameter
   */
  protected void processUpdateHook(ActionContext context, Object previousObject, Object object) {
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    this.getSystemStatus(context).processHook(context, ObjectHookAction.UPDATE, previousObject, object, sqlDriver, ce);
  }


  /**
   *  Description of the Method
   *
   *@param  context         Description of the Parameter
   *@param  previousObject  Description of the Parameter
   */
  protected void processDeleteHook(ActionContext context, Object previousObject) {
    ConnectionPool sqlDriver = (ConnectionPool) context.getServletContext().getAttribute("ConnectionPool");
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    this.getSystemStatus(context).processHook(context, ObjectHookAction.DELETE, previousObject, null, sqlDriver, ce);
  }


  /**
   *  Checks that ownerId is the current user or in the current user's
   *  hierarchy.
   *
   *@param  context  Description of the Parameter
   *@param  ownerId  Description of the Parameter
   *@return          Description of the Return Value
   */
  protected boolean hasAuthority(ActionContext context, int ownerId) {
    int userId = this.getUserId(context);
    if (userId == ownerId) {
      return true;
    }
    User userRecord = this.getUser(context, userId);
    User childRecord = userRecord.getChild(ownerId);
    return (childRecord != null);
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of the Parameter
   *@param  permName          Description of the Parameter
   *@param  owner             Description of the Parameter
   *@param  vpUser            Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean hasViewpointAuthority(Connection db, ActionContext context, String permName, int owner, int vpUser) throws SQLException {
    //check if user has authority
    if (hasAuthority(context, owner) || (vpUser == owner)) {
      return true;
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    UserSession thisSession = systemStatus.getSessionManager().getUserSession(getActualUserId(context));
    HashMap viewpoints = thisSession.getViewpoints(db, permName, this.getUserId(context));
    ArrayList vpUsers = null;
    if (viewpoints.get(permName) != null) {
      vpUsers = (ArrayList) viewpoints.get(permName);
      Iterator i = vpUsers.iterator();
      while (i.hasNext()) {
        int user = ((Integer) i.next()).intValue();
        if (vpUser == user) {
          User userRecord = this.getUser(context, user);
          User childRecord = userRecord.getChild(owner);
          if (childRecord != null) {
            return true;
          }
        }
      }
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@param  thisElt           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean hasAuthority(Connection db, ActionContext context, Object thisElt) throws SQLException {
    try {

      //get all the access types possible for this type of a record
      AccessTypeList accessList = this.getSystemStatus(context).getAccessTypeList(db, AccessType.getLinkModuleId(thisElt));

      //get the access type associated with this record
      Method method = thisElt.getClass().getMethod("getAccessTypeString", null);
      Object result = method.invoke(thisElt, null);
      int accessType = Integer.parseInt((String) result);
      
      //check if record is public
      if (accessList.getCode(AccessType.PUBLIC) == accessType) {
        return true;
      }
      
      //get the owner
      method = thisElt.getClass().getMethod("getOwnerString", null);
      result = method.invoke(thisElt, null);
      int owner = Integer.parseInt((String) result);
      
      //check if user has authority by virtue of the hierarchy
      if(!hasAuthority(context, owner)){
        return false;
      }
      
      //make sure that it is not personal although record is owned by someone in the hierarchy
      if (accessList.getCode(AccessType.PERSONAL) == accessType && owner != this.getUserId(context)) {
        return false;
      }
    } catch (Exception e) {
      System.out.println("hasAuthority - > Error: " + e.getMessage());
    }
    return true;
  }


  /**
   *  Adds a feature to the Viewpoints attribute of the CFSModule object
   *
   *@param  context           The feature to be added to the Viewpoints
   *      attribute
   *@param  permName          The feature to be added to the Viewpoints
   *      attribute
   *@param  db                The feature to be added to the Viewpoints
   *      attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public UserList addViewpoints(Connection db, ActionContext context, String permName) throws SQLException {
    UserList userList = new UserList();
    userList.add(this.getUser(context, this.getUserId(context)));
    SystemStatus systemStatus = this.getSystemStatus(context);
    UserSession thisSession = systemStatus.getSessionManager().getUserSession(this.getActualUserId(context));
    HashMap viewpoints = thisSession.getViewpoints(db, permName, this.getUserId(context));
    ArrayList vpUsers = null;
    if (viewpoints.get(permName) != null) {
      vpUsers = (ArrayList) viewpoints.get(permName);
      Iterator i = vpUsers.iterator();
      while (i.hasNext()) {
        int userId = ((Integer) i.next()).intValue();
        User thisUser = new User();
        thisUser.setBuildContact(true);
        thisUser.setBuildContactDetails(false);
        thisUser.buildRecord(db, userId);
        userList.add(thisUser);
      }
    }
    context.getRequest().setAttribute("Viewpoints", userList);
    return userList;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   */
  public void invalidateViewpoints(ActionContext context) {
    SystemStatus systemStatus = this.getSystemStatus(context);
    UserSession thisSession = systemStatus.getSessionManager().getUserSession(this.getActualUserId(context));
    thisSession.invalidateViewpoints();
  }


  /**
   *  Tells the SystemStatus to reload the specified user's contact information
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void updateUserContact(Connection db, ActionContext context, int id) throws SQLException {
    if (id > -1) {
      ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      systemStatus.updateUserContact(db, id);
    }
  }


  /**
   *  Gets the return attribute of the CFSModule object
   *
   *@param  context       Description of the Parameter
   *@param  returnString  Description of the Parameter
   *@return               The return value
   */
  protected String getReturn(ActionContext context, String returnString) {
    boolean popup = "true".equals(context.getRequest().getParameter("popup"));
    if (popup) {
      return (returnString += "PopupOK");
    }
    return (returnString += "OK");
  }

}

