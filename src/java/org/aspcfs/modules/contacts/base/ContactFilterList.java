package org.aspcfs.modules.contacts.base;

import javax.servlet.http.*;
import java.util.*;
import java.lang.reflect.Array;

/**
 *  Creates a custom filter list for the Contacts pop up.<br>
 *  Filters should be seperated by a pipe i.e "|" and should be ones already
 *  supported by this list.
 *
 *@author     Mathur
 *@created    March 5, 2003
 *@version    $Id$
 */
public class ContactFilterList extends ArrayList {

  public final static String[] FILTERS = {"all", "employees", "mycontacts", "accountcontacts", "myprojects"};


  /**
   *  Constructor for the ContactFilterList object
   */
  public ContactFilterList() { }


  /**
   *  Constructor for the ContactFilterList object
   *
   *@param  request  Description of the Parameter
   */
  public ContactFilterList(HttpServletRequest request) {
    String filters = (String) request.getParameter("filters");
    if (filters != null && !"".equals(filters)) {
      StringTokenizer st = new StringTokenizer(filters, "|");
      while (st.hasMoreTokens()) {
        String value = st.nextToken();
        this.add(new ContactFilter(value, getDisplayName(value)));
      }
    } else {
      buildDefaultFilters();
    }
  }


  /**
   *  Gets the displayName attribute of the ContactFilterList object
   *
   *@param  name  Description of the Parameter
   *@return       The displayName value
   */
  public String getDisplayName(String name) {
    if (name.equals("all")) {
      return "All Contacts";
    } else if (name.equals("employees")) {
      return "Employees";
    } else if (name.equals("mycontacts")) {
      return "My Contacts";
    } else if (name.equals("accountcontacts")) {
      return "Account Contacts";
    } else if (name.equals("myprojects")) {
      return "My Projects";
    }
    return "-None-";
  }


  /**
   *  Description of the Method
   */
  public void buildDefaultFilters() {
    for (int i = 0; i < Array.getLength(FILTERS); i++) {
      this.add(new ContactFilter(FILTERS[i], getDisplayName(FILTERS[i])));
    }
  }


  /**
   *  Gets the firstFilter attribute of the ContactFilterList object
   *
   *@param  selectedFilter  Description of the Parameter
   *@return                 The firstFilter value
   */
  public String getFirstFilter(String selectedFilter) {
    if (selectedFilter == null || selectedFilter.equals("")) {
      ContactFilter thisFilter = (ContactFilter) this.get(0);
      if (thisFilter != null) {
        return thisFilter.getValue();
      }
    }
    return selectedFilter;
  }
}

