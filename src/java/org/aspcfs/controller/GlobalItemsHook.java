package com.darkhorseventures.controller;

import javax.servlet.http.*;
import org.theseus.servlets.ControllerGlobalItemsHook;
import java.util.*;
import com.darkhorseventures.cfsbase.*;

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
  public String generateItems(HttpServletRequest request) {

    UserBean thisUser = (UserBean)request.getSession().getAttribute("User");

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
      ++itemCount;
      items.append(
        "<!-- My Items -->" +
        "<table border='0' width='150' cellpadding='0' cellspacing='1'>" +
        "<tr><td valign='top'> <img border='0' src='images/sb-myitems.gif' width='147' height='20'></td></tr>" +
        "<tr>" +
        "<td bgcolor='#E7E9EB'>" +
        "<a href='/Leads.do?module=Manage%20Tasks&include=pipeline_managetasks.htm' class='s'>Calls to make</a> (3)<a href='/Leads.do?module=Manage%20Tasks&include=pipeline_assignedtasks.htm' class='s'><br>" +
        "Assigned Tasks</a> (4)<br>" +
        "<a href='/TroubleTickets.do?command=Home' class='s'>Assigned Tickets</a> (4)" +
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

