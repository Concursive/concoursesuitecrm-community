package com.darkhorseventures.framework.hooks;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import com.darkhorseventures.database.*;
import com.darkhorseventures.framework.servlets.ControllerGlobalItemsHook;

/**
 *  Configures globally available items for CFS.
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    $Id: GlobalItemsHook.java,v 1.15 2002/12/23 16:12:28 mrajkowski
 *      Exp $
 */
public class GlobalItemsHook implements ControllerGlobalItemsHook {

  /**
   *  Generates all of the HTML for the permissable items.
   *
   *@param  request  Description of Parameter
   *@param  servlet  Description of the Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String generateItems(Servlet servlet, HttpServletRequest request) {
    ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) servlet.getServletConfig().getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
    int userId = thisUser.getUserId();
    int departmentId = thisUser.getUserRecord().getContact().getDepartment();
    int contactId = thisUser.getUserRecord().getContact().getId();

    StringBuffer items = new StringBuffer();
    int itemCount = 0;

    //Site Search
    if (systemStatus.hasPermission(userId, "globalitems-search-view")) {
      ++itemCount;
      items.append(
          "<!-- Site Search -->" +
          "<table border='0' width='150' cellpadding='0' cellspacing='1'>" +
          "<form action='Search.do?command=SiteSearch' method='post'>" +
          "<tr><td valign='top'> <img border='0' src='images/sb-search.gif' width='147' height='20'></td></tr>" +
          "<tr>" +
          "<td bgcolor='#E7E9EB'><p align='center'>Search this site for...<br>" +
          "<input type=text size=15 name=search>" +
          "<input type='submit' value='go' name='Search'>" +
          "</p>" +
          "</td>" +
          "</tr>" +
          "</form>" +
          "</table>");

      if (itemCount > 0) {
        items.append("&nbsp;\r\n");
      }
    }

    //My Items
    if (systemStatus.hasPermission(userId, "globalitems-myitems-view")) {
      ConnectionPool sqlDriver = (ConnectionPool) servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool");
      Connection db = null;

      //Output
      ++itemCount;
      items.append(
          "<!-- My Items -->" +
          "<table border='0' width='150' cellpadding='0' cellspacing='1'>" +
          "<tr><td valign='top'> <img border='0' src='images/sb-myitems.gif' width='147' height='20'></td></tr>" +
          "<tr>" +
          "<td bgcolor='#E7E9EB'>");

      try {
        int myItems = 0;
        db = sqlDriver.getConnection(ce);
        String sql = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        //External Contact Calls
        if (systemStatus.hasPermission(userId, "contacts-external_contacts-calls-view")) {
          int callCount = 0;
          sql =
              "SELECT COUNT(*) as callcount FROM call_log WHERE alertdate = ? AND enteredby = ?";
          pst = db.prepareStatement(sql);
          pst.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
          pst.setInt(2, userId);
          rs = pst.executeQuery();
          if (rs.next()) {
            callCount = rs.getInt("callcount");
            if (System.getProperty("DEBUG") != null) {
              System.out.println("GlobalItemsHook-> Calls: " + callCount);
            }
          }
          rs.close();
          pst.close();
          items.append("<a href='MyCFS.do?command=Home' class='s'>Calls to make</a> (" + paint(callCount) + ")<br>");
          ++myItems;
        }

        //Project Activities
        if (systemStatus.hasPermission(userId, "projects-view")) {
          int activityCount = 0;
          sql =
              "SELECT count(*) as activitycount FROM project_assignments WHERE complete_date IS NULL AND user_assign_id = ?";
          pst = db.prepareStatement(sql);
          pst.setInt(1, userId);
          rs = pst.executeQuery();
          if (rs.next()) {
            activityCount = rs.getInt("activitycount");
            if (System.getProperty("DEBUG") != null) {
              System.out.println("GlobalItemsHook-> Activities: " + activityCount);
            }
          }
          rs.close();
          pst.close();
          items.append("<a href='ProjectManagement.do?command=PersonalView' class='s'>Assigned Activities</a> (" + paint(activityCount) + ")<br>");
          ++myItems;
        }

        //Tickets Assigned to me
        if (systemStatus.hasPermission(userId, "tickets-view")) {
          int ticketCount = 0;
          sql =
              "SELECT COUNT(*) as ticketcount FROM ticket WHERE assigned_to = ? AND closed IS NULL";
          pst = db.prepareStatement(sql);
          pst.setInt(1, userId);
          rs = pst.executeQuery();
          if (rs.next()) {
            ticketCount = rs.getInt("ticketcount");
            if (System.getProperty("DEBUG") != null) {
              System.out.println("GlobalItemsHook-> Tickets: " + ticketCount);
            }
          }
          rs.close();
          pst.close();
          items.append("<a href='TroubleTickets.do?command=Home' class='s'>Assigned Tickets</a> (" + paint(ticketCount) + ")<br>");
          ++myItems;
        }

        //Tickets Unassigned
        if (systemStatus.hasPermission(userId, "tickets-view")) {
          int ticketCount = 0;
          sql =
              "SELECT COUNT(*) as ticketcount " +
              "FROM ticket " +
              "WHERE assigned_to = -1 AND closed IS NULL AND (department_code = ? OR department_code in (0, -1))";
          pst = db.prepareStatement(sql);
          pst.setInt(1, departmentId);
          rs = pst.executeQuery();
          if (rs.next()) {
            ticketCount = rs.getInt("ticketcount");
            if (System.getProperty("DEBUG") != null) {
              System.out.println("GlobalItemsHook-> Tickets (Unassigned): " + ticketCount);
            }
          }
          rs.close();
          pst.close();
          items.append("<a href='TroubleTickets.do?command=Home' class='s'>Unassigned Tickets</a> (" + paint(ticketCount) + ")<br>");
          ++myItems;
        }

        //CFS Inbox Items
        if (systemStatus.hasPermission(userId, "myhomepage-inbox-view")) {
          int inboxCount = 0;
          CFSNoteList newMessages = new CFSNoteList();
          newMessages.setSentTo(contactId);
          newMessages.setNewMessagesOnly(true);
          newMessages.buildList(db);
          inboxCount = newMessages.size();
          items.append("<a href='MyCFSInbox.do?command=Inbox&return=1' class='s'>Inbox</a> (" + paint(inboxCount) + " new)<br>");
          ++myItems;
        }

        //Tasks Items
        if (systemStatus.hasPermission(userId, "myhomepage-inbox-view")) {
          int taskCount = TaskList.queryPendingCount(db, contactId);
          items.append("<a href='MyTasks.do?command=ListTasks' class='s'>Tasks</a> (" + paint(taskCount) + " incomplete)<br>");
          ++myItems;
        }

        //Default no items
        if (myItems == 0) {
          items.append("No Items Found<br>&nbsp;<br>");
        }
      } catch (Exception e) {
        System.out.println("GlobalItemsHook Error-> " + e.toString());
        e.printStackTrace(System.out);
      }
      sqlDriver.free(db);

      items.append(
          "</td>" +
          "</tr>" +
          "</table>");

      if (itemCount > 0) {
        items.append("&nbsp;\r\n");
      }
    }

    //Recent Items
    if (systemStatus.hasPermission(userId, "globalitems-recentitems-view")) {
      ++itemCount;
      items.append(
          "<!-- Recent Items -->" +
          "<table border='0' width='150' cellpadding='0' cellspacing='1'>" +
          "<tr><td valign='top'> <img border='0' src='images/sb-recent.gif' width='147' height='20'></td></tr>" +
          "<tr>" +
          "<td bgcolor='#E7E9EB'>");

      ArrayList recentItems = (ArrayList) request.getSession().getAttribute("RecentItems");
      if (recentItems != null) {
        Iterator i = recentItems.iterator();
        while (i.hasNext()) {
          RecentItem thisItem = (RecentItem) i.next();
          items.append(thisItem.getHtml());
          if (i.hasNext()) {
            items.append("<br>");
            //items.append("<hr color=\"#BFBFBB\" noshade>");
          }
        }
      } else {
        items.append("No recent items<br>&nbsp;<br>");
      }

      items.append(
          "</td>" +
          "</tr>" +
          "</table>");
    }

    if (items.length() > 0) {
      //If they have any modules, then create a cell to hold them...
      return ("<td width=\"150\" valign=\"top\">" + items.toString() + "</td>");
    } else {
      //No global items
      return "";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  count  Description of the Parameter
   *@return        Description of the Return Value
   */
  private static String paint(int count) {
    if (count > 0) {
      return "<font color=\"red\">" + count + "</font>";
    } else {
      return "" + count;
    }
  }

}

