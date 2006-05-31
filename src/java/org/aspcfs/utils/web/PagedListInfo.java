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
package org.aspcfs.utils.web;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.UserUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;

/**
 * Allows information to be stored in an object for the pagedlist. <p>
 * <p/>
 * When a web user visits a page, store this object in the session and call it
 * the name of the Module and View the user is looking at. Retrieve it to
 * resume where the user left off.
 *
 * @author mrajkowski
 * @version $Id: PagedListInfo.java,v 1.10 2001/08/31 17:36:15 mrajkowski Exp
 *          $
 * @created July 12, 2001
 */
public class PagedListInfo implements Serializable {

  public static String allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.,0123456789_";
  public String[] lettersArray = {"0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
  public final static int DEFAULT_ITEMS_PER_PAGE = 10;
  public final static int LIST_VIEW = 1;
  public final static int DETAILS_VIEW = 2;

  private int mode = LIST_VIEW;
  private String link = "";
  private String id = null;
  private String columnToSortBy = null;
  private String sortOrder = null;
  private int itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
  private int maxRecords = 0;
  private String currentLetter = "";
  private int currentOffset = 0;
  private int previousOffset = 0;
  private String listView = null;
  private HashMap listFilters = new HashMap();
  private boolean enableJScript = false;
  private boolean showForm = true;
  private boolean resetList = true;
  private String alternateSort = null;
  private HashMap savedCriteria = new HashMap();

  //specifically for modules using the contactsList
  private String parentFieldType = "";
  private String parentFormName = "";

  private boolean expandedSelection = false;
  private boolean scrollReload = false;
  private boolean isValid = false;
  private SystemStatus systemStatus = null;


  /**
   * Constructor for the PagedListInfo object
   *
   * @since 1.0
   */
  public PagedListInfo() {
  }


  /**
   * Gets the mode attribute of the PagedListInfo object
   *
   * @return The mode value
   */
  public int getMode() {
    return mode;
  }


  /**
   * Sets the mode attribute of the PagedListInfo object
   *
   * @param tmp The new mode value
   */
  public void setMode(int tmp) {
    if (mode == LIST_VIEW && tmp == DETAILS_VIEW) {
      previousOffset = currentOffset;
    }
    if (mode == DETAILS_VIEW && tmp == LIST_VIEW) {
      currentOffset = previousOffset;
    }
    this.mode = tmp;
  }


  /**
   * Sets the ColumnToSortBy attribute of the PagedListInfo object
   *
   * @param tmp The new ColumnToSortBy value
   * @since 1.0
   */
  public void setColumnToSortBy(String tmp) {
    this.columnToSortBy = tmp;
  }


  /**
   * Sets the ColumnToSortBy attribute of the PagedListInfo object
   *
   * @param enableJScript The new enableJScript value
   * @since 1.0
   */
  public void setEnableJScript(boolean enableJScript) {
    this.enableJScript = enableJScript;
  }


  /**
   * Sets the SortOrder attribute of the PagedListInfo object
   *
   * @param tmp The new SortOrder value
   * @since 1.0
   */
  public void setSortOrder(String tmp) {
    this.sortOrder = tmp;
  }


  /**
   * Sets the showForm attribute of the PagedListInfo object
   *
   * @param showForm The new showForm value
   */
  public void setShowForm(boolean showForm) {
    this.showForm = showForm;
  }


  /**
   * Sets the resetList attribute of the PagedListInfo object
   *
   * @param resetList The new resetList value
   */
  public void setResetList(boolean resetList) {
    this.resetList = resetList;
  }


  /**
   * Sets the isValid attribute of the PagedListInfo object
   *
   * @param tmp The new isValid value
   */
  public void setIsValid(boolean tmp) {
    this.isValid = tmp;
  }


  /**
   * Sets the isValid attribute of the PagedListInfo object
   *
   * @param tmp The new isValid value
   */
  public void setIsValid(String tmp) {
    this.isValid = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the systemStatus attribute of the PagedListInfo object
   *
   * @param tmp The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   * Sets the lettersArray attribute of the PagedListInfo object
   *
   * @param tmp The new lettersArray value
   */
  public void setLettersArray(String[] tmp) {
    this.lettersArray = tmp;
  }


  /**
   * Gets the lettersArray attribute of the PagedListInfo object
   *
   * @return The lettersArray value
   */
  public String[] getLettersArray() {
    return lettersArray;
  }


  /**
   * Gets the systemStatus attribute of the PagedListInfo object
   *
   * @return The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   * Gets the isValid attribute of the PagedListInfo object
   *
   * @return The isValid value
   */
  public boolean getIsValid() {
    return isValid;
  }


  /**
   * Gets the expandedSelection attribute of the PagedListInfo object
   *
   * @return The expandedSelection value
   */
  public boolean getExpandedSelection() {
    return expandedSelection;
  }


  /**
   * Sets the expandedSelection attribute of the PagedListInfo object
   *
   * @param expandedSelection The new expandedSelection value
   */
  public void setExpandedSelection(boolean expandedSelection) {
    this.expandedSelection = expandedSelection;
    this.setItemsPerPage(DEFAULT_ITEMS_PER_PAGE);
  }


  /**
   * Gets the scrollReload attribute of the PagedListInfo object
   *
   * @return The scrollReload value
   */
  public boolean getScrollReload() {
    return scrollReload;
  }


  /**
   * Sets the scrollReload attribute of the PagedListInfo object
   *
   * @param tmp The new scrollReload value
   */
  public void setScrollReload(boolean tmp) {
    this.scrollReload = tmp;
  }


  /**
   * Sets the ItemsPerPage attribute of the PagedListInfo object
   *
   * @param tmp The new ItemsPerPage value
   * @since 1.0
   */
  public void setItemsPerPage(int tmp) {
    if (tmp > itemsPerPage || tmp == -1) {
      resetList();
    }
    this.itemsPerPage = tmp;
  }


  /**
   * Gets the savedCriteria attribute of the PagedListInfo object
   *
   * @return The savedCriteria value
   */
  public HashMap getSavedCriteria() {
    return savedCriteria;
  }


  /**
   * Sets the savedCriteria attribute of the PagedListInfo object
   *
   * @param savedCriteria The new savedCriteria value
   */
  public void setSavedCriteria(HashMap savedCriteria) {
    this.savedCriteria = savedCriteria;
  }


  /**
   * Sets the parentFieldType attribute of the PagedListInfo object
   *
   * @param parentFieldType The new parentFieldType value
   */
  public void setParentFieldType(String parentFieldType) {
    this.parentFieldType = parentFieldType;
  }


  /**
   * Gets the id attribute of the PagedListInfo object
   *
   * @return The id value
   */
  public String getId() {
    return id;
  }


  /**
   * Sets the id attribute of the PagedListInfo object
   *
   * @param id The new id value
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * Sets the parentFormName attribute of the PagedListInfo object
   *
   * @param parentFormName The new parentFormName value
   */
  public void setParentFormName(String parentFormName) {
    this.parentFormName = parentFormName;
  }


  /**
   * Gets the parentFormName attribute of the PagedListInfo object
   *
   * @return The parentFormName value
   */
  public String getParentFormName() {
    return parentFormName;
  }


  /**
   * Gets the parentFieldType attribute of the PagedListInfo object
   *
   * @return The parentFieldType value
   */
  public String getParentFieldType() {
    return parentFieldType;
  }


  /**
   * Gets the alternateSort attribute of the PagedListInfo object
   *
   * @return The alternateSort value
   */
  public String getAlternateSort() {
    return alternateSort;
  }


  /**
   * Sets the alternateSort attribute of the PagedListInfo object
   *
   * @param alternateSort The new alternateSort value
   */
  public void setAlternateSort(String alternateSort) {
    this.alternateSort = alternateSort;
  }


  /**
   * Sets the ItemsPerPage attribute of the PagedListInfo object
   *
   * @param tmp The new ItemsPerPage value
   * @since 1.0
   */
  public void setItemsPerPage(String tmp) {
    try {
      this.setItemsPerPage(Integer.parseInt(tmp));
    } catch (Exception e) {
    }
  }


  /**
   * Sets the Link attribute of the PagedListInfo object
   *
   * @param tmp The new Link value
   * @since 1.0
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }

  public String getLink() {
    return link;
  }

  /**
   * Sets the MaxRecords attribute of the PagedListInfo object
   *
   * @param tmp The new MaxRecords value
   * @since 1.0
   */
  public void setMaxRecords(int tmp) {
    maxRecords = tmp;
    if (System.getProperty("DEBUG") != null) {
      System.out.println("PagedListInfo-> Records in table: " + maxRecords);
    }
    //Check to see if the currentOffset is greater than maxRecords,
    //if so, find a nice page break to stop on
    if (maxRecords <= currentOffset && maxRecords > 0 && getItemsPerPage() != -1)
    {
      currentOffset = maxRecords;

      while (((currentOffset % getItemsPerPage()) > 0) && (currentOffset > 0)) {
        --currentOffset;
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "PagedListInfo-> Offset reduced to: " + currentOffset);
      }
      //Check to see if the page break has any records to display, otherwise
      //go back a page
      if (currentOffset == maxRecords) {
        currentOffset = currentOffset - getItemsPerPage();
      }
    }
  }


  /**
   * Sets the CurrentLetter attribute of the PagedListInfo object
   *
   * @param tmp The new CurrentLetter value
   * @since 1.1
   */
  public void setCurrentLetter(String tmp) {
    this.currentLetter = tmp;
  }


  /**
   * Sets the CurrentOffset attribute of the PagedListInfo object
   *
   * @param tmp The new CurrentOffset value
   * @since 1.1
   */
  public void setCurrentOffset(int tmp) {
    if (tmp < 0) {
      this.currentOffset = 0;
    } else {
      this.currentOffset = tmp;
    }
  }


  /**
   * Sets the CurrentOffset attribute of the PagedListInfo object
   *
   * @param tmp The new CurrentOffset value
   * @since 1.1
   */
  public void setCurrentOffset(String tmp) {
    try {
      this.setCurrentOffset(Integer.parseInt(tmp));
    } catch (Exception e) {
    }
  }


  /**
   * Sets the ListView attribute of the PagedListInfo object. The ListView
   * property stores what view the user has selected.
   *
   * @param tmp The new ListView value
   * @since 1.11
   */
  public void setListView(String tmp) {
    this.listView = tmp;
  }


  /**
   * Sets the Parameters attribute of the PagedListInfo object
   *
   * @param context The new Parameters value
   * @return Description of the Return Value
   * @since 1.1
   */
  public boolean setParameters(ActionContext context) {

    //check for multiple pagedLists on a single page
    if (context.getRequest().getParameter("pagedListInfoId") != null && !(context.getRequest().getParameter(
        "pagedListInfoId").equals("")) && this.getId() != null && !"".equals(this.getId().trim()) &&
        !(context.getRequest().getParameter("pagedListInfoId").equals("null")) &&
        !(context.getRequest().getParameter("pagedListInfoId").equals(
            this.getId()))) {
      return false;
    }

    Enumeration parameters = context.getRequest().getParameterNames();
    boolean reset = false;

    String tmpSortOrder = context.getRequest().getParameter("order");
    if (tmpSortOrder != null && checkAllowed(tmpSortOrder)) {
      this.setSortOrder(tmpSortOrder);
    }

    //Check to see if the user is changing the sort column, or clicking on the
    //same column again
    String tmpColumnToSortBy = context.getRequest().getParameter("column");
    if (tmpColumnToSortBy != null) {
      if (columnToSortBy != null) {
        if (tmpColumnToSortBy.equals(columnToSortBy)) {
          if (sortOrder == null) {
            this.setSortOrder("desc");
          } else {
            this.setSortOrder(null);
          }
        } else {
          if (sortOrder != null && sortOrder.equals("desc")) {
            this.setSortOrder(null);
          }
        }
      }
      // Security check so that arbitrary queries cannot be executed
      if (checkAllowed(tmpColumnToSortBy)) {
        this.setColumnToSortBy(tmpColumnToSortBy);
      }
    }

    // User has specified a page number to view
    // This will be used as a parameter in the query
    String tmpCurrentPage = context.getRequest().getParameter("page");
    if (tmpCurrentPage != null) {
      try {
        if (getItemsPerPage() == -1) {
          throw new java.lang.NumberFormatException("All records in one page");
        }
        this.setCurrentOffset(
            (Integer.parseInt(tmpCurrentPage) - 1) * getItemsPerPage());
      } catch (java.lang.NumberFormatException e) {
        this.setCurrentOffset(0);
      }
    }

    //User is changing the number of items to display -- needs to be done after the
    //page select
    String tmpItemsPerPage = context.getRequest().getParameter("items");
    if (tmpItemsPerPage != null) {
      this.setItemsPerPage(tmpItemsPerPage);
    }

    //The user wants to jump to a specific letter of the alphabet...
    //The alphabet is currently tuned to a specific field that is identified
    //by the object... maybe in the future it will use the column being
    //sorted on.
    String tmpCurrentLetter = context.getRequest().getParameter("letter");
    if (tmpCurrentLetter != null) {
      this.setCurrentLetter(tmpCurrentLetter);
      //Need to reset the sort because it is configured by the underlying query object
      this.setColumnToSortBy(null);
      this.setSortOrder(null);
    } else {
      this.setCurrentLetter("");
    }

    //The user has selected an offset to go to... could be through a
    //page element that calculates the offset per page
    String tmpCurrentOffset = context.getRequest().getParameter("offset");
    if (tmpCurrentOffset != null) {
      this.setCurrentOffset(tmpCurrentOffset);
    }

    //The user has changed the view of the pagedList
    String tmpListView = context.getRequest().getParameter("listView");
    if (tmpListView != null) {
      if (listView != null && !listView.equals(tmpListView)) {
        resetList();
      }
      this.setListView(tmpListView);
    }

    //Populate the PagedListInfo with data filters, reset the list view since
    //the user is changing the filter
    int filter = 0;
    while (context.getRequest().getParameter("listFilter" + (++filter)) != null)
    {
      String tmpListFilter = context.getRequest().getParameter(
          "listFilter" + filter);
      addFilter(filter, tmpListFilter);
    }
    if (context.getRequest().getParameter("listFilter2") != null && filter < 2)
    {
      String thisFilter = context.getRequest().getParameter("listFilter2");
      addFilter(2, thisFilter);
    }

    if (context.getRequest().getParameter("listFilter1") != null && resetList) {
      String thisFilter = context.getRequest().getParameter("listFilter1");
      if (listFilters.get("listFilter1") != null && !thisFilter.equals(
          listFilters.get("listFilter1"))) {
        resetList();
      }
    }

    while (parameters.hasMoreElements()) {
      String param = (String) parameters.nextElement();
      if (param.startsWith("search")) {
        if (!(reset)) {
          this.getSavedCriteria().clear();
          reset = true;
        }
        this.getSavedCriteria().put(
            param, context.getRequest().getParameter(param));
      }
    }
    return true;
  }


  /**
   * Sets the searchCriteria attribute of the PagedListInfo object
   *
   * @param obj     The new searchCriteria value
   * @param context The new searchCriteria value
   * @return Description of the Return Value
   */
  public boolean setSearchCriteria(Object obj, ActionContext context) {
    ConnectionElement ce = null;
    if (context != null) {
      ce = (ConnectionElement) context.getSession().getAttribute(
          "ConnectionElement");
    }
    if (ce != null) {
      this.setSystemStatus(
          (SystemStatus) ((Hashtable) context.getSession().getServletContext().getAttribute(
              "SystemStatus")).get(ce.getUrl()));
    }
    Locale locale = UserUtils.getUserLocale(context.getRequest());
    if (!this.getSavedCriteria().isEmpty()) {
      Iterator hashIterator = this.getSavedCriteria().keySet().iterator();
      while (hashIterator.hasNext()) {
        String tempKey = (String) hashIterator.next();
        if (this.getCriteriaValue(tempKey) != null && !(this.getCriteriaValue(
            tempKey).trim().equals(""))) {
          //its an int
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "PagedListInfo-> Setting: " + tempKey + "=" + getCriteriaValue(
                    tempKey));
          }
          if (tempKey.startsWith("searchcode")) {
            ObjectUtils.setParam(
                obj, tempKey.substring(10), this.getCriteriaValue(tempKey));
          } else if (tempKey.startsWith("searchdate")) {
            Timestamp tmpTimestamp = DateUtils.getUserToServerDateTime(
                null, DateFormat.SHORT, DateFormat.LONG, this.getCriteriaValue(
                tempKey), locale);
            if (tmpTimestamp != null) {
              boolean modified = false;
              java.sql.Date tmpDate = new java.sql.Date(
                  tmpTimestamp.getTime());
              modified = ObjectUtils.setParam(
                  obj, tempKey.substring(10), tmpDate);
              if (!modified && this.getCriteriaValue(tempKey) != null && !"".equals(
                  this.getCriteriaValue(tempKey))) {
                isValid = false;
                addError(obj, "getErrors", tempKey, context);
              }
            }
            if (tmpTimestamp == null && this.getCriteriaValue(tempKey) != null && !"".equals(
                this.getCriteriaValue(tempKey))) {
              isValid = false;
              addError(obj, "getErrors", tempKey, context);
            }
          } else {
            ObjectUtils.setParam(
                obj, tempKey.substring(6), "%" + this.getCriteriaValue(
                tempKey) + "%");
          }
        }
      }
    }
    return true;
  }


  /**
   * Adds a feature to the Error attribute of the PagedListInfo class
   *
   * @param obj     The feature to be added to the Error attribute
   * @param param   The feature to be added to the Error attribute
   * @param field   The feature to be added to the Error attribute
   * @param context The feature to be added to the Error attribute
   */
  private static void addError(Object obj, String param, String field, ActionContext context) {
    try {
      ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
          "ConnectionElement");
      SystemStatus systemStatus = null;
      if (ce != null) {
        systemStatus = (SystemStatus) ((Hashtable) context.getSession().getServletContext().getAttribute(
            "SystemStatus")).get(ce.getUrl());
      }
      Method method = obj.getClass().getMethod(
          param, (java.lang.Class[]) null);
      if (systemStatus == null) {
        ((HashMap) method.invoke(obj, (java.lang.Object[]) null)).put(
            field.substring(0, 1).toLowerCase() + field.substring(1) + "Error", "Invalid Date");
      } else {
        ((HashMap) method.invoke(obj, (java.lang.Object[]) null)).put(
            field.substring(0, 1).toLowerCase() + field.substring(1) + "Error", systemStatus.getLabel(
            "object.validation.incorrectDateFormat"));
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "PagedListInfo-> Adding an error: " + field + "Error");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Gets the searchOptionValue attribute of the PagedListInfo object
   *
   * @param field Description of the Parameter
   * @return The searchOptionValue value
   */
  public String getSearchOptionValue(String field) {
    if (this.getSavedCriteria() != null && this.getSavedCriteria().get(field) != null)
    {
      return (String) savedCriteria.get(field);
    }
    return "";
  }


  /**
   * Sets the defaultSort attribute of the PagedListInfo object
   *
   * @param column The new defaultSort value
   * @param order  Ex. "desc" or null
   */
  public void setDefaultSort(String column, String order) {
    if (!this.hasSortConfigured()) {
      this.setColumnToSortBy(column);
      this.setSortOrder(order);
    }
  }


  /**
   * Gets the ColumnToSortBy attribute of the PagedListInfo object
   *
   * @return The ColumnToSortBy value
   * @since 1.0
   */
  public String getColumnToSortBy() {
    return columnToSortBy;
  }


  /**
   * Gets the SortOrder attribute of the PagedListInfo object
   *
   * @return The SortOrder value
   * @since 1.0
   */
  public String getSortOrder() {
    return sortOrder;
  }


  /**
   * Gets the ItemsPerPage attribute of the PagedListInfo object
   *
   * @return The ItemsPerPage value
   * @since 1.0
   */
  public int getItemsPerPage() {
    if (mode == DETAILS_VIEW) {
      return 1;
    }
    return itemsPerPage;
  }


  /**
   * Gets the MaxRecords attribute of the PagedListInfo object
   *
   * @return The MaxRecords value
   * @since 1.11
   */
  public int getMaxRecords() {
    return maxRecords;
  }


  /**
   * Gets the CurrentLetter attribute of the PagedListInfo object
   *
   * @return The CurrentLetter value
   * @since 1.1
   */
  public String getCurrentLetter() {
    return currentLetter;
  }


  /**
   * Gets the CurrentOffset attribute of the PagedListInfo object
   *
   * @return The CurrentOffset value
   * @since 1.1
   */
  public int getCurrentOffset() {
    return currentOffset;
  }


  /**
   * Gets the CurrentOffset attribute of the PagedListInfo object
   *
   * @return The CurrentOffset value
   * @since 1.1
   */
  public boolean getEnableJScript() {
    return enableJScript;
  }


  /**
   * Gets the PageLinks attribute of the PagedListInfo object <p>
   * <p/>
   * The pages are numbered from 1 to the highest page
   *
   * @return The PageLinks value
   * @since 1.0
   */
  public String getNumericalPageLinks() {
    int numPages = this.getNumberOfPages();
    StringBuffer links = new StringBuffer();
    links.append(
        numPages + " page" + ((numPages == 1) ? "" : "s") + " in this view ");
    if (numPages > 1) {
      links.append("[");
      for (int i = 1; i < (numPages + 1); i++) {
        if ((i - 1) * getItemsPerPage() == currentOffset) {
          links.append(" <b>" + i + "</b> ");
        } else {
          links.append(
              "<a href=\"" + link + "&offset=" + ((i - 1) * getItemsPerPage()) + "\"> " + i + " </a>");
        }
      }
      links.append("]");
    }
    return links.toString();
  }


  /**
   * Gets the listPropertiesHeader attribute of the PagedListInfo object
   *
   * @param formName Description of Parameter
   * @return The listPropertiesHeader value
   */
  public String getListPropertiesHeader(String formName) {
    if (showForm) {
      if (expandedSelection) {
        link += "&pagedListSectionId=" + id;
      }
      return ("<form name=\"" + formName + "\" action=\"" + link + "\" method=\"post\">");
    } else {
      return "";
    }

  }


  /**
   * Gets the listPropertiesFooter attribute of the PagedListInfo object
   *
   * @return The listPropertiesFooter value
   */
  public String getListPropertiesFooter() {
    if (showForm) {
      return ("</form>");
    } else {
      return "";
    }
  }


  /**
   * Gets the numericalPageEntry attribute of the PagedListInfo object
   *
   * @return The numericalPageEntry value
   */
  public String getNumericalPageEntry() {
    return ("<input type=\"text\" name=\"page\" value=\"" + (getItemsPerPage() != -1 ? ((currentOffset / getItemsPerPage()) + 1) : 1) + "\" size=\"3\">");
  }


  /**
   * Gets the itemsPerPageEntry attribute of the PagedListInfo object
   *
   * @return The itemsPerPageEntry value
   */
  public String getItemsPerPageEntry(String allLabel) {
    HtmlSelect itemSelect = new HtmlSelect();
    itemSelect.addItem("6");
    itemSelect.addItem("10");
    itemSelect.addItem("12");
    itemSelect.addItem("20");
    itemSelect.addItem("30");
    itemSelect.addItem("50");
    itemSelect.addItem("100");
    itemSelect.addItem("-1", allLabel);
    itemSelect.setJsEvent("onChange='submit();'");
    return (itemSelect.getHtml("items", getItemsPerPage()));
    //return("Items per page <input type=\"text\" name=\"items\" value=\"" + getItemsPerPage() + "\" size=\"3\">");
  }


  /**
   * Gets the numberOfPages attribute of the PagedListInfo object
   *
   * @return The numberOfPages value
   */
  public int getNumberOfPages() {
    if (getItemsPerPage() != -1) {
      return (int) Math.ceil((double) maxRecords / (double) getItemsPerPage());
    }
    return 1;
  }


  /**
   * Gets the AlphebeticalPageLinks attribute of the PagedListInfo object
   *
   * @return The AlphebeticalPageLinks value
   * @since 1.1
   */
  public String getAlphabeticalPageLinks() {
    StringBuffer links = new StringBuffer();
    for (int i = 0; i < lettersArray.length; i++) {
      String thisLetter = lettersArray[i];
      if (thisLetter.equals(currentLetter)) {
        links.append(" <b>" + thisLetter + "</b> ");
      } else {
        links.append(
            "<a href='" + link + "&letter=" + thisLetter + "'> " + thisLetter + " </a>");
      }
    }
    return links.toString();
  }


  /**
   * Gets the AlphebeticalPageLinks attribute of the PagedListInfo object
   *
   * @param javaScript Description of the Parameter
   * @param formName   Description of the Parameter
   * @return The AlphebeticalPageLinks value
   * @since 1.1
   */
  public String getAlphabeticalPageLinks(String javaScript, String formName) {
    StringBuffer links = new StringBuffer();
    for (int i = 0; i < lettersArray.length; i++) {
      String thisLetter = lettersArray[i];
      if (thisLetter.equals(currentLetter)) {
        links.append(" <b>" + thisLetter + "</b> ");
      } else {
        links.append(
            "<a href=\"javascript:" + javaScript + "('letter','" + thisLetter + "','" + formName + "');\"> " + thisLetter + " </a>");
      }
    }
    return links.toString();
  }


  /**
   * Gets the PreviousPageLink attribute of the PagedListInfo object
   *
   * @return The PreviousPageLink value
   * @since 1.1
   */
  public String getPreviousPageLink() {
    return getPreviousPageLink("&laquo;");
  }


  /**
   * Gets the PreviousPageLink attribute of the PagedListInfo object
   *
   * @param linkInfo Description of Parameter
   * @return The PreviousPageLink value
   * @since 1.8
   */
  public String getPreviousPageLink(String linkInfo) {
    return getPreviousPageLink(linkInfo, linkInfo);
  }

  public String getPreviousPageLink(String linkOn, String linkOff) {
    return getPreviousPageLink(linkOn, linkOff, "0");
  }

  /**
   * Gets the PreviousPageLink attribute of the PagedListInfo object
   *
   * @param linkOn  Description of Parameter
   * @param linkOff Description of Parameter
   * @return The PreviousPageLink value
   * @since 1.8
   */
  public String getPreviousPageLink(String linkOn, String linkOff, String formName) {
    StringBuffer result = new StringBuffer();
    if (currentOffset > 0 && getItemsPerPage() != -1) {
      int newOffset = currentOffset - getItemsPerPage();
      //Handle scroll reload
      String scrollStart = "";
      String scrollEnd = "";
      if (scrollReload) {
        scrollStart = "javascript:scrollReload('";
        scrollEnd = "');";
      }
      if (!getEnableJScript()) {
        //Normal link
        result.append(
            "<a href=\"" + scrollStart + link + "&pagedListInfoId=" + this.getId());
        if (getExpandedSelection()) {
          result.append("&pagedListSectionId=" + this.getId());
        }
        result.append(
            "&offset=" + (newOffset > 0 ? newOffset : 0) + scrollEnd + "\">" + linkOn + "</a>");
        return result.toString();
      } else {
        //Use javascript for constructing the link
        result.append(
            "<a href=\"javascript:offsetsubmit('" + formName + "','" + (newOffset > 0 ? newOffset : 0) + "');\">" + linkOn + "</a>");
        return result.toString();
      }
    } else {
      return linkOff;
    }
  }


  /**
   * Gets the NextPageLink attribute of the PagedListInfo object
   *
   * @return The NextPageLink value
   * @since 1.1
   */
  public String getNextPageLink() {
    return getNextPageLink("&raquo;");
  }


  /**
   * Gets the NextPageLink attribute of the PagedListInfo object
   *
   * @param linkInfo Description of Parameter
   * @return The NextPageLink value
   * @since 1.8
   */
  public String getNextPageLink(String linkInfo) {
    return getNextPageLink(linkInfo, linkInfo);
  }

  public String getNextPageLink(String linkOn, String linkOff) {
    return getNextPageLink(linkOn, linkOff, "0");
  }

  /**
   * Gets the NextPageLink attribute of the PagedListInfo object
   *
   * @param linkOn  Description of Parameter
   * @param linkOff Description of Parameter
   * @return The NextPageLink value
   * @since 1.8
   */
  public String getNextPageLink(String linkOn, String linkOff, String formName) {

    StringBuffer result = new StringBuffer();

    if ((currentOffset + getItemsPerPage()) < maxRecords && getItemsPerPage() != -1)
    {
      //Handle scroll reload
      String scrollStart = "";
      String scrollEnd = "";
      if (scrollReload) {
        scrollStart = "javascript:scrollReload('";
        scrollEnd = "');";
      }
      if (!getEnableJScript()) {
        //Normal link
        result.append(
            "<a href=\"" + scrollStart + link + "&pagedListInfoId=" + this.getId());
        if (getExpandedSelection()) {
          result.append("&pagedListSectionId=" + this.getId());
        }
        result.append(
            "&offset=" + (currentOffset + getItemsPerPage()) + scrollEnd + "\">" + linkOn + "</a>");
        return result.toString();
      } else {
        //Use javascript for constructing the link
        result.append(
            "<a href=\"javascript:offsetsubmit('" + formName + "','" + (currentOffset + getItemsPerPage()) + "');\">" + linkOn + "</a>");
        return result.toString();
      }
    } else {
      return linkOff;
    }
  }


  /**
   * Gets the expandLink attribute of the PagedListInfo object
   *
   * @param linkOn       Description of the Parameter
   * @param linkOff      Description of the Parameter
   * @param collapseLink Description of the Parameter
   * @return The expandLink value
   */
   /*
  public String getExpandLink(String linkOn, String linkOff, String collapseLink) {
    if ((currentOffset + getItemsPerPage()) < maxRecords && !expandedSelection && getItemsPerPage() != -1)
    {
      return "<a href='" + link + "&pagedListInfoId=" + this.getId() + "&pagedListSectionId=" + this.getId() + "'>" + linkOn + "</a>";
    } else
    if ((currentOffset + getItemsPerPage()) >= maxRecords && !expandedSelection && getItemsPerPage() != -1)
    {
      return linkOff;
    } else {
      return "<a href='" + link + "&resetList=true&pagedListInfoId=" + this.getId() + "'>" + collapseLink + "</a>";
    }
  }
  */

  /**
   * Gets the expandLink attribute of the PagedListInfo object
   *
   * @param expandLink   Description of the Parameter
   * @param collapseLink Description of the Parameter
   * @return The expandLink value
   */
  public String getExpandLink(String expandLink, String collapseLink, String tmpParams) {
    if (!expandedSelection) {
      return "<a href=\"" + link + "&pagedListInfoId=" + this.getId() + "&pagedListSectionId=" + this.getId() + tmpParams +"\">" + expandLink + "</a>";
    } else {
      return "<a href=\"" + link + "&resetList=true&pagedListInfoId=" + this.getId() + tmpParams + "\">" + collapseLink + "</a>";
    }
  }


  /**
   * Gets the sortIcon attribute of the PagedListInfo object
   *
   * @param columnName Description of Parameter
   * @return The sortIcon value
   */
  public String getSortIcon(String columnName) {
    if (columnName.equals(columnToSortBy)) {
      if (sortOrder != null && sortOrder.indexOf("desc") > -1) {
        return "<img border=0 src=\"images/layout/sort-dn.gif\" align=\"bottom\" width=\"12\" height=\"10\" />";
      } else {
        return "<img border=0 src=\"images/layout/sort-up.gif\" align=\"bottom\" width=\"12\" height=\"10\" />";
      }
    } else {
      return "";
    }
  }


  /**
   * Gets the ListView attribute of the PagedListInfo object
   *
   * @return The ListView value
   * @since 1.11
   */
  public String getListView() {
    return listView;
  }


  /**
   * Creates the value and selected information for an HTML combo-box.<p>
   * <p/>
   * In the HTML you would have:<br>
   * <option <%= info.getOptionValue("my") %>>Text </option> <p>
   * <p/>
   * To display:<br>
   * <option value="my" selected>Text</option>
   *
   * @param tmp Description of Parameter
   * @return The OptionValue value
   * @since 1.11
   */
  public String getOptionValue(String tmp) {
    return ("value=\"" + tmp + "\"" + (tmp.equals(listView) ? " selected" : ""));
  }


  /**
   * Gets the filterOption attribute of the PagedListInfo object
   *
   * @param filterName Description of the Parameter
   * @param tmp        Description of the Parameter
   * @return The filterOption value
   */
  public String getFilterOption(String filterName, String tmp) {
    String current = (String) listFilters.get(filterName);
    return ("value=\"" + tmp + "\"" + (tmp.equals(current) ? " selected" : ""));
  }


  /**
   * Gets the filterValue attribute of the PagedListInfo object
   *
   * @param tmp Description of Parameter
   * @return The filterValue value
   */
  public String getFilterValue(String tmp) {
    return (String) listFilters.get(tmp);
  }


  /**
   * Gets the criteriaValue attribute of the PagedListInfo object
   *
   * @param tmp Description of the Parameter
   * @return The criteriaValue value
   */
  public String getCriteriaValue(String tmp) {
    return (String) savedCriteria.get(tmp);
  }


  /**
   * Gets the filterKey attribute of the PagedListInfo object
   *
   * @param tmp Description of Parameter
   * @return The filterKey value
   */
  public int getFilterKey(String tmp) {
    try {
      return Integer.parseInt((String) listFilters.get(tmp));
    } catch (Exception e) {
      return -1;
    }
  }


  /**
   * Gets the refreshTag attribute of the PagedListInfo object
   *
   * @param tmp Description of the Parameter
   * @return The refreshTag value
   */
  public String getRefreshTag(String tmp) {
    return ("<a href=\"" + link + "\"> " + tmp + " </a>");
  }


  /**
   * Adds a feature to the Filter attribute of the PagedListInfo object
   *
   * @param param The feature to be added to the Filter attribute
   * @param value The feature to be added to the Filter attribute
   */
  public void addFilter(int param, String value) {
    listFilters.put("listFilter" + param, value);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasLink() {
    return (link != null && !"".equals(link.trim()));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public boolean hasListFilters() {
    return listFilters.size() > 0;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public boolean hasSortConfigured() {
    return (this.getColumnToSortBy() != null && !"".equals(this.getColumnToSortBy()));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public boolean hasSortOrderConfigured() {
    return (this.getSortOrder() != null && !"".equals(this.getSortOrder()));
  }


  /**
   * Description of the Method
   *
   * @param db           Description of Parameter
   * @param sqlStatement Description of Parameter
   */
  public void appendSqlSelectHead(Connection db, StringBuffer sqlStatement) {
    if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
        this.getItemsPerPage() > 0) {
      int x = this.getItemsPerPage() + this.getCurrentOffset();
      sqlStatement.append("SELECT TOP " + x + " ");
    } else if (DatabaseUtils.getType(db) == DatabaseUtils.ORACLE &&
        this.getItemsPerPage() > 0) {
      sqlStatement.append("SELECT * FROM (SELECT ");
    } else if (DatabaseUtils.getType(db) == DatabaseUtils.FIREBIRD &&
        this.getItemsPerPage() > 0) {
      sqlStatement.append("SELECT FIRST " + this.getItemsPerPage() + " ");
      sqlStatement.append("SKIP " + this.getCurrentOffset() + " ");
    } else if (DatabaseUtils.getType(db) == DatabaseUtils.DAFFODILDB &&
        this.getItemsPerPage() > 0) {
      int x = this.getItemsPerPage() + this.getCurrentOffset();
      sqlStatement.append("SELECT TOP (" + x + ") ");
    } else {
      sqlStatement.append("SELECT ");
    }
  }


  /**
   * Description of the Method
   *
   * @param db           Description of Parameter
   * @param sqlStatement Description of Parameter
   */
  public void appendSqlTail(Connection db, StringBuffer sqlStatement) {
    sqlStatement.append("ORDER BY ");
    //Determine sort order
    //If multiple columns are being sorted, then the sort order applies to all columns
    if (this.getColumnToSortBy().indexOf(",") > -1) {
      StringTokenizer st = new StringTokenizer(this.getColumnToSortBy(), ",");
      while (st.hasMoreTokens()) {
        String column = st.nextToken();
        sqlStatement.append(DatabaseUtils.parseReservedWord(db, column) + " ");
        if (this.hasSortOrderConfigured()) {
          sqlStatement.append(this.getSortOrder() + " ");
        }
        if (st.hasMoreTokens()) {
          sqlStatement.append(",");
        }
      }
    } else {
      sqlStatement.append(DatabaseUtils.parseReservedWord(db, this.getColumnToSortBy()) + " ");
      if (this.hasSortOrderConfigured()) {
        sqlStatement.append(this.getSortOrder() + " ");
      }
    }

    //Determine items per page for PostgreSQL
    if (DatabaseUtils.getType(db) == DatabaseUtils.POSTGRESQL) {
      if (this.getItemsPerPage() > 0) {
        sqlStatement.append("LIMIT " + this.getItemsPerPage() + " ");
      }
      sqlStatement.append("OFFSET " + this.getCurrentOffset() + " ");
    } else if (DatabaseUtils.getType(db) == DatabaseUtils.ORACLE) {
      if (this.getItemsPerPage() > 0) {
        //sqlStatement.append(") " +
        //    "WHERE ROWNUM BETWEEN " + this.getCurrentOffset() + " AND " +
        //    (this.getCurrentOffset() + this.getItemsPerPage()) + " ");
        sqlStatement.append(") " +
            "WHERE ROWNUM <= " +
            (this.getCurrentOffset() + this.getItemsPerPage()) + " ");

      }
    } else if (DatabaseUtils.getType(db) == DatabaseUtils.DB2) {
      if (this.getItemsPerPage() > 0) {
        sqlStatement.append(
            "FETCH FIRST " + this.getItemsPerPage() + " ROWS ONLY ");
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void doManualOffset(Connection db, ResultSet rs) throws SQLException {
    if (this.getItemsPerPage() > 0 &&
        (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL ||
            DatabaseUtils.getType(db) == DatabaseUtils.DAFFODILDB ||
            DatabaseUtils.getType(db) == DatabaseUtils.DB2 ||
            DatabaseUtils.getType(db) == DatabaseUtils.ORACLE)) {
      for (int skipCount = 0; skipCount < this.getCurrentOffset(); skipCount++)
      {
        rs.next();
      }
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   * @since 1.9
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("===================================================\r\n");
    sb.append("Link: " + link + "\r\n");
    sb.append("Sort Column: " + columnToSortBy + "\r\n");
    sb.append("SOrt Order: " + sortOrder + "\r\n");
    sb.append("Items per page: " + getItemsPerPage() + "\r\n");
    sb.append("Total record count: " + maxRecords + "\r\n");
    sb.append("Current offset letter: " + currentLetter + "\r\n");
    sb.append("Current offset record: " + currentOffset + "\r\n");
    sb.append("List View: " + listView + "\r\n");
    return sb.toString();
  }


  /**
   * Description of the Method
   */
  private void resetList() {
    this.setCurrentLetter("");
    this.setCurrentOffset(0);
    previousOffset = 0;
  }


  /**
   * Gets the pageSize attribute of the PagedListInfo object
   *
   * @return The pageSize value
   */
  public int getPageSize() {
    if ((currentOffset + getItemsPerPage()) < maxRecords && getItemsPerPage() != -1)
    {
      // current = 0
      // items = 10
      // max = 17
      // 0 + 10 < 17
      return (currentOffset + getItemsPerPage());
    } else {
      // current = 10
      // items = 10
      // max = 17
      return (maxRecords);
    }
  }

  private static boolean checkAllowed(String in) {
    if (in == null || in.length() == 0) {
      return true;
    }
    for (int i = 0; i < in.length(); i++) {
      if (allowed.indexOf(in.charAt(i)) == -1) {
        return false;
      }
    }
    return true;
  }
}

