package com.darkhorseventures.cfsmodule;

import org.theseus.actions.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.controller.SystemStatus;
import com.darkhorseventures.controller.RecentItem;
import java.sql.*;
import java.util.*;
import java.text.*;

/**
 *  Base class for all modules
 *
 *@author     mrajkowski
 *@created    July 15, 2001
 *@version    $Id$
 */
public class CFSModule {

  public final static String fs = System.getProperty("file.separator");


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


  /**
   *  Gets the DbName attribute of the CFSModule object
   *
   *@param  context  Description of Parameter
   *@return          The DbName value
   *@since
   */
  protected String getDbName(ActionContext context) {
    ConnectionElement ce = (ConnectionElement)context.getSession().getAttribute("ConnectionElement");
    return ce.getDbName();
  }


  /**
   *  Gets the Path attribute of the CFSModule object
   *
   *@param  context           Description of Parameter
   *@param  moduleFolderName  Description of Parameter
   *@return                   The Path value
   *@since
   */
  protected String getPath(ActionContext context, String moduleFolderName) {
    return (
        context.getServletContext().getRealPath("/") + ".." + fs +
        "fileLibrary" + fs +
        this.getDbName(context) + fs +
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
  protected String getPath(ActionContext context, String moduleFolderName, int moduleItemId) {
    return (
        context.getServletContext().getRealPath("/") + ".." + fs +
        "fileLibrary" + fs +
        this.getDbName(context) + fs +
        moduleFolderName + fs +
        "id" + moduleItemId + fs);
  }


  /**
   *  Gets the DatePath attribute of the CFSModule object
   *
   *@param  fileDate  Description of Parameter
   *@return           The DatePath value
   *@since
   */
  protected String getDatePath(java.sql.Timestamp fileDate) {
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
  protected void processErrors(ActionContext context, Hashtable errors) {
    Iterator i = errors.keySet().iterator();
    while (i.hasNext()) {
      String errorKey = (String)i.next();
      String errorMsg = (String)errors.get(errorKey);
      context.getRequest().setAttribute(errorKey, errorMsg);
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

}

