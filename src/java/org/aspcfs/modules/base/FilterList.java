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
package org.aspcfs.modules.base;

import javax.servlet.http.*;
import java.util.*;
import java.lang.reflect.Array;
import org.aspcfs.modules.base.Constants;

/**
 *  Creates a custom filter list for the Contacts pop up.<br>
 *  Filters should be seperated by a pipe i.e "|" and should be ones already
 *  supported by this list.
 *
 *@author     Mathur
 *@created    March 5, 2003
 *@version    $Id: FilterList.java,v 1.2.136.1 2004/06/08 18:34:00 kbhoopal Exp
 *      $
 */
public class FilterList extends ArrayList {

  private final static String[] CONTACT_FILTERS = {"all", "employees", "mycontacts", "accountcontacts", "myprojects"};
  private final static String[] ACCOUNT_FILTERS = {"all", "my", "disabled"};
  private final static String[] ASSET_FILTERS = {"allassets", "undercontract"};
  private int source = Constants.CONTACTS;


  /**
   *  Constructor for the FilterList object
   */
  public FilterList() { }


  /**
   *  Constructor for the FilterList object
   *
   *@param  request  Description of the Parameter
   */
  public FilterList(HttpServletRequest request) {
    build(request);
  }


  /**
   *  Sets the source attribute of the FilterList object
   *
   *@param  source  The new source value
   */
  public void setSource(int source) {
    this.source = source;
  }


  /**
   *  Gets the source attribute of the FilterList object
   *
   *@return    The source value
   */
  public int getSource() {
    return source;
  }


  /**
   *  Gets the displayName attribute of the FilterList object
   *
   *@param  name  Description of the Parameter
   *@return       The displayName value
   */
  public String getDisplayName(String name) {
    switch (source) {
        case Constants.CONTACTS:
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
        case Constants.ACCOUNTS:
          if (name.equals("all")) {
            return "All Accounts";
          } else if (name.equals("my")) {
            return "My Accounts";
          } else if (name.equals("disabled")) {
            return "Disabled Accounts";
          }
        case Constants.ASSETS:
          if (name.equals("allassets")) {
            return "All Assets";
          } else if (name.equals("undercontract")) {
            return "Assets under Contract";
          }
        default:
          return "-None-";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  request  Description of the Parameter
   */
  public void build(HttpServletRequest request) {
    String filters = (String) request.getParameter("filters");
    if (filters != null && !"".equals(filters)) {
      StringTokenizer st = new StringTokenizer(filters, "|");
      while (st.hasMoreTokens()) {
        String value = st.nextToken();
        this.add(new Filter(value, getDisplayName(value)));
      }
    } else {
      buildDefaultFilters();
    }
  }


  /**
   *  Description of the Method
   */
  public void buildDefaultFilters() {
    switch (source) {
        case Constants.CONTACTS:
          for (int i = 0; i < Array.getLength(CONTACT_FILTERS); i++) {
            this.add(new Filter(CONTACT_FILTERS[i], getDisplayName(CONTACT_FILTERS[i])));
          }
          break;
        case Constants.ACCOUNTS:
          for (int i = 0; i < Array.getLength(ACCOUNT_FILTERS); i++) {
            this.add(new Filter(ACCOUNT_FILTERS[i], getDisplayName(ACCOUNT_FILTERS[i])));
          }
          break;
        case Constants.ASSETS:
          for (int i = 0; i < Array.getLength(ASSET_FILTERS); i++) {
            this.add(new Filter(ASSET_FILTERS[i], getDisplayName(ASSET_FILTERS[i])));
          }
    }
  }


  /**
   *  Gets the firstFilter attribute of the FilterList object
   *
   *@param  selectedFilter  Description of the Parameter
   *@return                 The firstFilter value
   */
  public String getFirstFilter(String selectedFilter) {
    if (selectedFilter == null || selectedFilter.equals("")) {
      Filter thisFilter = (Filter) this.get(0);
      if (thisFilter != null) {
        return thisFilter.getValue();
      }
    }
    return selectedFilter;
  }
}

