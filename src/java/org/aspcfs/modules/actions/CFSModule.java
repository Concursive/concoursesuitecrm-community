package com.darkhorseventures.cfsmodule;

import org.theseus.actions.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.controller.SystemStatus;
import com.darkhorseventures.controller.RecentItem;
import com.darkhorseventures.controller.CustomForm;
import com.darkhorseventures.controller.CustomFormList;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;

/**
 *  Base class for all modules
 *
 *@author     mrajkowski
 *@created    July 15, 2001
 *@version    $Id$
 */
public class CFSModule {

  public final static String fs = System.getProperty("file.separator");
  public static final String NOT_UPDATED_MESSAGE =
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
    PagedListInfo tmpInfo = (PagedListInfo)context.getSession().getAttribute(viewName);
    if (tmpInfo == null) {
      tmpInfo = new PagedListInfo();
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
    ConnectionPool sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    return sqlDriver.getConnection(ce);
  }
  
  protected Connection getDefaultConnection(ActionContext context) throws SQLException {
    ConnectionPool sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
    System.out.println(sqlDriver.toString());
    System.out.println(sqlDriver.getUsername() + " " + sqlDriver.getPassword() + " " + sqlDriver.getUrl() );
    return sqlDriver.getConnection();
  }

  /**
   *  Gets the UserId attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The UserId value
   *@since           1.7
   */
  protected int getUserId(ActionContext context) {
    return ((UserBean)context.getSession().getAttribute("User")).getUserId();
  }

  /**
   *  Gets the UserRange attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The UserRange value
   *@since
   */
  protected String getUserRange(ActionContext context) {
    return ((UserBean)context.getSession().getAttribute("User")).getIdRange();
  }
  
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
    return ((UserBean)context.getSession().getAttribute("User")).getUserRecord().getContact().getNameLast();
  }


  /**
   *  Gets the NameFirst attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The NameFirst value
   *@since
   */
  protected String getNameFirst(ActionContext context) {
    return ((UserBean)context.getSession().getAttribute("User")).getUserRecord().getContact().getNameFirst();
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
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus)((Hashtable)context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    UserList thisList = systemStatus.getHierarchyList();
    return thisList.getUser(userId);
  }
  
  protected boolean hasPermission(ActionContext context, String permission) {
    UserBean thisUser = (UserBean)context.getSession().getAttribute("User");
    return (thisUser.hasPermission(permission));
  }
  
  public static String includeFile(String sourceFile){
    StringBuffer HTMLBuffer = new StringBuffer();
    String desc;
    char[] chars = null;
    int c;	
    FileReader in;
    try {
       File inputFile = new File(sourceFile);
       in = new FileReader(inputFile);
      while ((c = in.read()) != -1) HTMLBuffer.append((char) c);
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
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    if (ce != null) {
      return ce.getDbName();
    } else {
      return null;
    }
  }
  
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
   *@param  context           Description of Parameter
   *@param  moduleFolderName  Description of Parameter
   *@return                   The Path value
   *@since
   */
  protected String getPath(ActionContext context) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs);
  }
  
  
  protected String getPath(ActionContext context, String moduleFolderName) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs +
        (this.getDbName(context) == null?"":this.getDbName(context) + fs) +
        moduleFolderName + fs);
  }
  
  protected String getPath(ActionContext context, ConnectionElement ce, String moduleFolderName) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs +
        (this.getDbName(ce) == null?"":this.getDbName(ce) + fs) +
        moduleFolderName + fs);
  }


  /**
   *  Gets the Path attribute of the CFSModule object
   *
   *@param  context           Description of Parameter
   *@param  moduleFolderName  Description of Parameter
   *@param  moduleItemId      Description of Parameter
   *@return                   The Path value
   *@since
   */
  public static String getPath(ActionContext context, String moduleFolderName, int moduleItemId) {
    return (
        context.getServletContext().getRealPath("/") + "WEB-INF" + fs +
        "fileLibrary" + fs +
        getDbName(context) + fs +
        moduleFolderName + fs +
        "id" + moduleItemId + fs);
  }

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
   *  Removes an object from the session
   *
   *@param  context   Description of Parameter
   *@param  viewName  Description of Parameter
   *@since            1.6
   */
  protected void deletePagedListInfo(ActionContext context, String viewName) {
    PagedListInfo tmpInfo = (PagedListInfo)context.getSession().getAttribute(viewName);
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
      ConnectionPool sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
      sqlDriver.free(db);
    }
    db = null;
  }
  
  protected void renewConnection(ActionContext context, Connection db) {
    //Connections are usually checked out and expire, this will renew the expiration
    //time
    if (db != null) {
      ConnectionPool sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
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
      String errorKey = (String)i.next();
      String errorMsg = (String)errors.get(errorKey);
      context.getRequest().setAttribute(errorKey, errorMsg);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(" Object Validation Error-> " + errorKey + "=" + errorMsg);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@since
   */
  protected void updateSystemPermissionCheck(ActionContext context) {
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus)((Hashtable)context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.setPermissionCheck(new java.util.Date());
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  context           Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  protected void updateSystemHierarchyCheck(Connection db, ActionContext context) throws SQLException {
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus)((Hashtable)context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.updateHierarchy(db);
  }
  
  
  protected void invalidateUserData(ActionContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) context.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    systemStatus.getHierarchyList().getUser(userId).setIsValid(false, true);
  }


  /**
   *  Description of the Method
   *
   *@param  userId   Description of Parameter
   *@param  context  Description of Parameter
   *@since
   */
  protected void invalidateUserInMemory(int userId, ActionContext context) {

    Hashtable globalStatus = (Hashtable)context.getServletContext().getAttribute("SystemStatus");

    Iterator i = globalStatus.values().iterator();

    UserList shortChildList = new UserList();
    UserList fullChildList = new UserList();

    while (i.hasNext()) {
      SystemStatus thisStatus = (SystemStatus)i.next();

      UserList thisList = thisStatus.getHierarchyList();
      Iterator j = thisList.iterator();

      while (j.hasNext()) {
        User thisUser = (User)j.next();

        shortChildList = thisUser.getShortChildList();
        fullChildList = thisUser.getFullChildList(shortChildList, new UserList());

        Iterator k = fullChildList.iterator();

        while (k.hasNext()) {
          User indUser = (User)k.next();

          if (indUser.getId() == userId) {
            indUser.setIsValid(false, true);
            System.out.println("clearing: " + indUser.getId());
          }
        }
      }
    }
  }
  
  protected void deleteRecentItem(ActionContext context, Object itemObject) {
	ArrayList recentItems = (ArrayList)context.getSession().getAttribute("RecentItems");
	if (recentItems == null) {
		recentItems = new ArrayList();
		context.getSession().setAttribute("RecentItems", recentItems);
	}
	
	    RecentItem thisItem = null;

    //Build the recent item object
    if (itemObject instanceof Contact) {
      Contact thisContact = (Contact)itemObject;

      if (thisContact.getTypeId() == Contact.EMPLOYEE_TYPE) {
        thisItem = new RecentItem(
            RecentItem.EMPLOYEE,
            thisContact.getNameFirstLast(),
            "/CompanyDirectory.do?command=EmployeeDetails&empid=" + thisContact.getId());
      } else {
        thisItem = new RecentItem(
            RecentItem.CONTACT,
            thisContact.getNameFull(),
            "/ExternalContacts.do?command=ContactDetails&id=" + thisContact.getId());
      }

    } else if (itemObject instanceof Organization) {
      Organization thisOrganization = (Organization)itemObject;
      thisItem = new RecentItem(
          RecentItem.ACCOUNT,
          thisOrganization.getName(),
          "/Accounts.do?command=Details&orgId=" + thisOrganization.getOrgId());

    } else if (itemObject instanceof User) {
      User thisUser = (User)itemObject;
      thisItem = new RecentItem(
          RecentItem.USER,
          thisUser.getContact().getNameFirstLast(),
          "/Users.do?command=UserDetails&id=" + thisUser.getId());
    } else if (itemObject instanceof Ticket) {
      Ticket thisTicket = (Ticket)itemObject;
      thisItem = new RecentItem(
          RecentItem.TICKET,
          thisTicket.getPaddedId(),
          "/TroubleTickets.do?command=Details&id=" + thisTicket.getId());
    } else if (itemObject instanceof Opportunity) {
      Opportunity thisOpp = (Opportunity)itemObject;
      thisItem = new RecentItem(
          RecentItem.OPPORTUNITY,
          thisOpp.getShortDescription(),
          "/Opportunities.do?command=Details&id=" + thisOpp.getId() + "&orgId=" + thisOpp.getAccountLink() + "&contactId=" + thisOpp.getContactLink());
    }
    
	if (thisItem != null) {
		Iterator i = recentItems.iterator();
		while (i.hasNext()) {
			RecentItem existingItem = (RecentItem)i.next();
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
    ArrayList recentItems = (ArrayList)context.getSession().getAttribute("RecentItems");
    if (recentItems == null) {
      recentItems = new ArrayList();
      context.getSession().setAttribute("RecentItems", recentItems);
    }

    RecentItem thisItem = null;

    //Build the recent item object
    if (itemObject instanceof Contact) {
      Contact thisContact = (Contact)itemObject;

      if (thisContact.getTypeId() == Contact.EMPLOYEE_TYPE) {
        thisItem = new RecentItem(
            RecentItem.EMPLOYEE,
            thisContact.getNameFirstLast(),
            "/CompanyDirectory.do?command=EmployeeDetails&empid=" + thisContact.getId());
      } else {
        thisItem = new RecentItem(
            RecentItem.CONTACT,
            thisContact.getNameFirstLast(),
            "/ExternalContacts.do?command=ContactDetails&id=" + thisContact.getId());
      }

    } else if (itemObject instanceof Organization) {
      Organization thisOrganization = (Organization)itemObject;
      thisItem = new RecentItem(
          RecentItem.ACCOUNT,
          thisOrganization.getName(),
          "/Accounts.do?command=Details&orgId=" + thisOrganization.getOrgId());

    } else if (itemObject instanceof User) {
      User thisUser = (User)itemObject;
      thisItem = new RecentItem(
          RecentItem.USER,
          thisUser.getContact().getNameFirstLast(),
          "/Users.do?command=UserDetails&id=" + thisUser.getId());
    } else if (itemObject instanceof Ticket) {
      Ticket thisTicket = (Ticket)itemObject;
      thisItem = new RecentItem(
          RecentItem.TICKET,
          thisTicket.getPaddedId(),
          "/TroubleTickets.do?command=Details&id=" + thisTicket.getId());
    } else if (itemObject instanceof Opportunity) {
      Opportunity thisOpp = (Opportunity)itemObject;
      thisItem = new RecentItem(
          RecentItem.OPPORTUNITY,
          thisOpp.getShortDescription(),
          //"/Opportunities.do?command=Details&id=" + thisOpp.getId() + "&orgId=" + thisOpp.getAccountLink() + "&contactId=" + thisOpp.getContactLink());
	  //"/Opportunities.do?command=Details&id=" + thisOpp.getId() + "&orgId=" + thisOpp.getAccountLink());
	  "/Leads.do?command=DetailsOpp&id=" + thisOpp.getId());
    }

    //Insert the object
    if (thisItem != null) {
      Iterator i = recentItems.iterator();
      while (i.hasNext()) {
        RecentItem existingItem = (RecentItem)i.next();
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
  
  public static void saveTextFile(String text, String filename) throws java.io.IOException {
    File outputFile = new File(filename);
    FileWriter out = new FileWriter(outputFile);
    out.write(text);
    out.close();
  }
  
  public CustomForm getDynamicForm(ActionContext context, String which) {
	CustomForm thisForm = new CustomForm();
	
	if ( ((CustomFormList)context.getServletContext().getAttribute("DynamicFormList")).containsKey(which) ) {
		thisForm = (CustomForm)(((CustomForm)((CustomFormList)context.getServletContext().getAttribute("DynamicFormList")).get(which)).clone());
	}
	
	return thisForm;
  }
}

