package com.darkhorseventures.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.servlets.ControllerGlobalItemsHook;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import java.sql.*;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.utils.*;

/**
 *  Configures globally available items for CFS.
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    $Id$
 */
public class GlobalItemsHook implements ControllerGlobalItemsHook {

  /**
   *  Generates all of the HTML for the permissable items.
   *
   *@param  request  Description of Parameter
   *@return          Description of the Returned Value
   *@since           1.0
   */
  public String generateItems(Servlet servlet, HttpServletRequest request) {

    UserBean thisUser = (UserBean)request.getSession().getAttribute("User");
    int userId = thisUser.getUserId();

    StringBuffer items = new StringBuffer();
    int itemCount = 0;

    //Site Search
    if (thisUser.hasPermission("globalitems-search-view")) {
      ++itemCount;
      items.append(
        "<!-- Site Search -->" +
        "<table border='0' width='150' cellpadding='0' cellspacing='1'>" +
        "<form action='/Search.do?command=SiteSearch' method='post'>" +
        "<tr><td valign='top'> <img border='0' src='images/sb-search.gif' width='147' height='20'></td></tr>" +
        "<tr>" +
        "<td bgcolor='#E7E9EB'><p align='center'>Search this site for...<br>" +
        "<input type='text' name='search' size='15'><br>" +
        "<a href='#SearchHelp' class='s'>More/Help</a> <input type='submit' value='Search' name='Search'>" +
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
    if (thisUser.hasPermission("globalitems-myitems-view")) {
      
      ConnectionPool sqlDriver = (ConnectionPool)servlet.getServletConfig().getServletContext().getAttribute("ConnectionPool");
      ConnectionElement ce = (ConnectionElement)request.getSession().getAttribute("ConnectionElement");
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
        if (thisUser.hasPermission("contacts-external_contacts-view")) {
          int callCount = 0;
          sql = 
            "SELECT COUNT(*) as callcount FROM call_log WHERE alertdate = CURRENT_TIMESTAMP AND enteredby = ?";
          pst = db.prepareStatement(sql);
          pst.setInt(1, userId);
          rs = pst.executeQuery();
          if (rs.next()) {
            callCount = rs.getInt("callcount");
            myItems += callCount;
            if (System.getProperty("DEBUG") != null) System.out.println("GlobalItemsHook-> Calls: " + callCount);
          }
          rs.close();
          pst.close();
          items.append("<a href='/MyCFS.do?command=Home' class='s'>Calls to make</a> (" + callCount + ")<br>");
        }
        
        //Project Activities
        if (thisUser.hasPermission("projects-view")) {
          int activityCount = 0;
          sql = 
            "SELECT count(*) as activitycount FROM project_assignments WHERE complete_date IS NULL AND user_assign_id = ?";
          pst = db.prepareStatement(sql);
          pst.setInt(1, userId);
          rs = pst.executeQuery();
          if (rs.next()) {
            activityCount = rs.getInt("activitycount");
            myItems += activityCount;
            if (System.getProperty("DEBUG") != null) System.out.println("GlobalItemsHook-> Activities: " + activityCount);
          }
          rs.close();
          pst.close();
          items.append("<a href='/ProjectManagement.do?command=PersonalView' class='s'>Assigned Activities</a> (" + activityCount + ")<br>");
        }
        
        //Tickets
        if (thisUser.hasPermission("tickets-view")) {
          int ticketCount = 0;
          sql = 
            "SELECT COUNT(*) as ticketcount FROM ticket WHERE assigned_to = ? AND closed IS NULL";
          pst = db.prepareStatement(sql);
          pst.setInt(1, userId);
          rs = pst.executeQuery();
          if (rs.next()) {
            ticketCount = rs.getInt("ticketcount");
            myItems += ticketCount;
            if (System.getProperty("DEBUG") != null) System.out.println("GlobalItemsHook-> Tickets: " + ticketCount);
          }
          rs.close();
          pst.close();
          items.append("<a href='/TroubleTickets.do?command=Home' class='s'>Assigned Tickets</a> (" + ticketCount + ")<br>");
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
    if (thisUser.hasPermission("globalitems-recentitems-view")) {
      ++itemCount;
      items.append(
        "<!-- Recent Items -->" +
        "<table border='0' width='150' cellpadding='0' cellspacing='1'>" +
        "<tr><td valign='top'> <img border='0' src='images/sb-recent.gif' width='147' height='20'></td></tr>" +
        "<tr>" +
        "<td bgcolor='#E7E9EB'>");
        
      ArrayList recentItems = (ArrayList)request.getSession().getAttribute("RecentItems");
      if (recentItems != null) {
        Iterator i = recentItems.iterator();
        while (i.hasNext()) {
          RecentItem thisItem = (RecentItem)i.next();
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
      return ("<td width=150 valign='top'>" + items.toString() + "</td>");
    } else {
      //No global items
      return "";
    }
  }

}

