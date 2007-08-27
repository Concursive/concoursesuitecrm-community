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

import org.aspcfs.controller.SystemStatus;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Creates a custom filter list for the Contacts pop up.<br>
 * Filters should be seperated by a pipe i.e "|" and should be ones already
 * supported by this list.
 *
 * @author Mathur
 * @version $Id: FilterList.java,v 1.2.136.1 2004/06/08 18:34:00 kbhoopal Exp
 *          $
 * @created March 5, 2003
 */
public class FilterList extends ArrayList {

  private final static String[] CONTACT_FILTERS = {"all", "employees", "mycontacts", "accountcontacts", "myprojects","leads"};
  private final static String[] ACCOUNT_FILTERS = {"all", "my", "disabled"};
  private final static String[] ASSET_FILTERS = {"allassets", "undercontract"};
  private int source = Constants.CONTACTS;


  /**
   * Constructor for the FilterList object
   */
  public FilterList() {
  }


  /**
   * Constructor for the FilterList object
   *
   * @param request Description of the Parameter
   */
  public FilterList(SystemStatus thisSystem, HttpServletRequest request) {
    build(thisSystem, request);
  }


  /**
   * Sets the source attribute of the FilterList object
   *
   * @param source The new source value
   */
  public void setSource(int source) {
    this.source = source;
  }


  /**
   * Gets the source attribute of the FilterList object
   *
   * @return The source value
   */
  public int getSource() {
    return source;
  }


  /**
   * Gets the displayName attribute of the FilterList object
   *
   * @param name Description of the Parameter
   * @return The displayName value
   */
  public String getDisplayName(SystemStatus thisSystem, String name) {
    switch (source) {
      case Constants.CONTACTS:
        if (name.equals("all")) {
          return thisSystem.getLabel("actionList.allContacts"); //All Contacts
        } else if (name.equals("employees")) {
          return thisSystem.getLabel("employees.employees"); //Employees
        } else if (name.equals("mycontacts")) {
          return thisSystem.getLabel("contact.myContacts"); //My Contacts
        } else if (name.equals("accountcontacts")) {
          return thisSystem.getLabel("documents.team.accountContacts"); //Account Contacts
        } else if (name.equals("myprojects")) {
          return thisSystem.getLabel("contacts.myProjects"); //My Projects
        } else if (name.equals("leads")) {
          return thisSystem.getLabel("sales.leads"); //My Projects
        }

      case Constants.ACCOUNTS:
        if (name.equals("all")) {
          return thisSystem.getLabel("accounts.all.accounts"); //All Accounts
        } else if (name.equals("my")) {
          return thisSystem.getLabel("accounts.my.accounts"); //My Accounts
        } else if (name.equals("disabled")) {
          return thisSystem.getLabel("accounts.disabledAccounts"); //Disabled Accounts
        }
      case Constants.ASSETS:
        if (name.equals("allassets")) {
          return thisSystem.getLabel("accounts.allAssets"); //All Assets
        } else if (name.equals("undercontract")) {
          return thisSystem.getLabel("accounts.assetsUnderContract"); //Assets under Contract
        }
      default:
        return thisSystem.getLabel("calendar.none.4dashes"); //--None--
    }
  }


  /**
   * Description of the Method
   *
   * @param request Description of the Parameter
   */
  public void build(SystemStatus thisSystem, HttpServletRequest request) {
    String filters = (String) request.getParameter("filters");
    if (filters != null && !"".equals(filters)) {
      StringTokenizer st = new StringTokenizer(filters, "|");
      while (st.hasMoreTokens()) {
        String value = st.nextToken();
        this.add(new Filter(value, getDisplayName(thisSystem, value)));
      }
    } else {
      buildDefaultFilters(thisSystem);
    }
  }


  /**
   * Description of the Method
   */
  public void buildDefaultFilters(SystemStatus thisSystem) {
    switch (source) {
      case Constants.CONTACTS:
        for (int i = 0; i < Array.getLength(CONTACT_FILTERS); i++) {
          this.add(
              new Filter(
                  CONTACT_FILTERS[i], getDisplayName(
                      thisSystem, CONTACT_FILTERS[i])));
        }
        break;
      case Constants.ACCOUNTS:
        for (int i = 0; i < Array.getLength(ACCOUNT_FILTERS); i++) {
          this.add(
              new Filter(
                  ACCOUNT_FILTERS[i], getDisplayName(
                      thisSystem, ACCOUNT_FILTERS[i])));
        }
        break;
      case Constants.ASSETS:
        for (int i = 0; i < Array.getLength(ASSET_FILTERS); i++) {
          this.add(
              new Filter(
                  ASSET_FILTERS[i], getDisplayName(
                      thisSystem, ASSET_FILTERS[i])));
        }
    }
  }


  /**
   * Gets the firstFilter attribute of the FilterList object
   *
   * @param selectedFilter Description of the Parameter
   * @return The firstFilter value
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

