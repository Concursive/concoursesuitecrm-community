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

import java.io.Serializable;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.HtmlSelect;
import java.lang.reflect.*;
import java.text.DateFormat;

/**
 *  Allows information to be stored in an object for the pagedlist. <p>
 *
 *  When a web user visits a page, store this object in the session and call it
 *  the name of the Module and View the user is looking at. Retrieve it to
 *  resume where the user left off.
 *
 *@author     mrajkowski
 *@created    July 12, 2001
 *@version    $Id: PagedListInfo.java,v 1.10 2001/08/31 17:36:15 mrajkowski Exp
 *      $
 */
public class PagedListInfo implements Serializable {
  public final String[] lettersArray = {"0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
  public final static int DEFAULT_ITEMS_PER_PAGE = 10;
  String link = "";
  String id = null;
  String columnToSortBy = null;
  String sortOrder = null;
  int itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
  int maxRecords = 0;
  String currentLetter = "";
  int currentOffset = 0;
  String listView = null;
  HashMap listFilters = new HashMap();
  private int iteration = 0;
  boolean enableJScript = false;
  boolean showForm = true;
  boolean resetList = true;
  String alternateSort = null;
  HashMap savedCriteria = new HashMap();

  //specifically for modules using the contactsList
  String parentFieldType = "";
  String parentFormName = "";

  boolean expandedSelection = false;
  boolean scrollReload = false;


  /**
   *  Constructor for the PagedListInfo object
   *
   *@since    1.0
   */
  public PagedListInfo() { }


  /**
   *  Sets the ColumnToSortBy attribute of the PagedListInfo object
   *
   *@param  tmp  The new ColumnToSortBy value
   *@since       1.0
   */
  public void setColumnToSortBy(String tmp) {
    this.columnToSortBy = tmp;
  }


  /**
   *  Sets the ColumnToSortBy attribute of the PagedListInfo object
   *
   *@param  enableJScript  The new enableJScript value
   *@since                 1.0
   */
  public void setEnableJScript(boolean enableJScript) {
    this.enableJScript = enableJScript;
  }


  /**
   *  Sets the SortOrder attribute of the PagedListInfo object
   *
   *@param  tmp  The new SortOrder value
   *@since       1.0
   */
  public void setSortOrder(String tmp) {
    this.sortOrder = tmp;
  }



  /**
   *  Sets the showForm attribute of the PagedListInfo object
   *
   *@param  showForm  The new showForm value
   */
  public void setShowForm(boolean showForm) {
    this.showForm = showForm;
  }


  /**
   *  Sets the resetList attribute of the PagedListInfo object
   *
   *@param  resetList  The new resetList value
   */
  public void setResetList(boolean resetList) {
    this.resetList = resetList;
  }


  /**
   *  Gets the expandedSelection attribute of the PagedListInfo object
   *
   *@return    The expandedSelection value
   */
  public boolean getExpandedSelection() {
    return expandedSelection;
  }


  /**
   *  Sets the expandedSelection attribute of the PagedListInfo object
   *
   *@param  expandedSelection  The new expandedSelection value
   */
  public void setExpandedSelection(boolean expandedSelection) {
    this.expandedSelection = expandedSelection;
    this.setItemsPerPage(DEFAULT_ITEMS_PER_PAGE);
  }


  /**
   *  Gets the scrollReload attribute of the PagedListInfo object
   *
   *@return    The scrollReload value
   */
  public boolean getScrollReload() {
    return scrollReload;
  }


  /**
   *  Sets the scrollReload attribute of the PagedListInfo object
   *
   *@param  tmp  The new scrollReload value
   */
  public void setScrollReload(boolean tmp) {
    this.scrollReload = tmp;
  }


  /**
   *  Sets the ItemsPerPage attribute of the PagedListInfo object
   *
   *@param  tmp  The new ItemsPerPage value
   *@since       1.0
   */
  public void setItemsPerPage(int tmp) {
    if (tmp > itemsPerPage) {
      resetList();
    }
    this.itemsPerPage = tmp;
  }


  /**
   *  Gets the savedCriteria attribute of the PagedListInfo object
   *
   *@return    The savedCriteria value
   */
  public HashMap getSavedCriteria() {
    return savedCriteria;
  }


  /**
   *  Sets the savedCriteria attribute of the PagedListInfo object
   *
   *@param  savedCriteria  The new savedCriteria value
   */
  public void setSavedCriteria(HashMap savedCriteria) {
    this.savedCriteria = savedCriteria;
  }



  /**
   *  Sets the parentFieldType attribute of the PagedListInfo object
   *
   *@param  parentFieldType  The new parentFieldType value
   */
  public void setParentFieldType(String parentFieldType) {
    this.parentFieldType = parentFieldType;
  }


  /**
   *  Gets the id attribute of the PagedListInfo object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the PagedListInfo object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   *  Sets the parentFormName attribute of the PagedListInfo object
   *
   *@param  parentFormName  The new parentFormName value
   */
  public void setParentFormName(String parentFormName) {
    this.parentFormName = parentFormName;
  }


  /**
   *  Gets the parentFormName attribute of the PagedListInfo object
   *
   *@return    The parentFormName value
   */
  public String getParentFormName() {
    return parentFormName;
  }


  /**
   *  Gets the parentFieldType attribute of the PagedListInfo object
   *
   *@return    The parentFieldType value
   */
  public String getParentFieldType() {
    return parentFieldType;
  }


  /**
   *  Gets the alternateSort attribute of the PagedListInfo object
   *
   *@return    The alternateSort value
   */
  public String getAlternateSort() {
    return alternateSort;
  }


  /**
   *  Sets the alternateSort attribute of the PagedListInfo object
   *
   *@param  alternateSort  The new alternateSort value
   */
  public void setAlternateSort(String alternateSort) {
    this.alternateSort = alternateSort;
  }


  /**
   *  Sets the ItemsPerPage attribute of the PagedListInfo object
   *
   *@param  tmp  The new ItemsPerPage value
   *@since       1.0
   */
  public void setItemsPerPage(String tmp) {
    try {
      this.setItemsPerPage(Integer.parseInt(tmp));
    } catch (Exception e) {
    }
  }



  /**
   *  Sets the Link attribute of the PagedListInfo object
   *
   *@param  tmp  The new Link value
   *@since       1.0
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   *  Sets the MaxRecords attribute of the PagedListInfo object
   *
   *@param  tmp  The new MaxRecords value
   *@since       1.0
   */
  public void setMaxRecords(int tmp) {
    maxRecords = tmp;
    if (System.getProperty("DEBUG") != null) {
      System.out.println("PagedListInfo-> Records in table: " + maxRecords);
    }
    //Check to see if the currentOffset is greater than maxRecords,
    //if so, find a nice page break to stop on
    if (maxRecords <= currentOffset && maxRecords > 0) {
      currentOffset = maxRecords;

      while (((currentOffset % itemsPerPage) != 0) && (currentOffset > 0)) {
        --currentOffset;
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("PagedListInfo-> Offset reduced to: " + currentOffset);
      }
      //Check to see if the page break has any records to display, otherwise
      //go back a page
      if (currentOffset == maxRecords) {
        currentOffset = currentOffset - itemsPerPage;
      }
    }
  }


  /**
   *  Sets the CurrentLetter attribute of the PagedListInfo object
   *
   *@param  tmp  The new CurrentLetter value
   *@since       1.1
   */
  public void setCurrentLetter(String tmp) {
    this.currentLetter = tmp;
  }


  /**
   *  Sets the CurrentOffset attribute of the PagedListInfo object
   *
   *@param  tmp  The new CurrentOffset value
   *@since       1.1
   */
  public void setCurrentOffset(int tmp) {

    if (tmp < 0) {
      this.currentOffset = 0;
    } else {
      this.currentOffset = tmp;
    }
  }


  /**
   *  Sets the CurrentOffset attribute of the PagedListInfo object
   *
   *@param  tmp  The new CurrentOffset value
   *@since       1.1
   */
  public void setCurrentOffset(String tmp) {
    try {
      this.setCurrentOffset(Integer.parseInt(tmp));
    } catch (Exception e) {
    }
  }


  /**
   *  Sets the ListView attribute of the PagedListInfo object. The ListView
   *  property stores what view the user has selected.
   *
   *@param  tmp  The new ListView value
   *@since       1.11
   */
  public void setListView(String tmp) {
    this.listView = tmp;
  }



  /**
   *  Sets the Parameters attribute of the PagedListInfo object
   *
   *@param  context  The new Parameters value
   *@return          Description of the Return Value
   *@since           1.1
   */
  public boolean setParameters(ActionContext context) {

    //check for multiple pagedLists on a single page
    if (context.getRequest().getParameter("pagedListInfoId") != null && !(context.getRequest().getParameter("pagedListInfoId").equals("")) &&
        !(context.getRequest().getParameter("pagedListInfoId").equals("null")) &&
        !(context.getRequest().getParameter("pagedListInfoId").equals(this.getId()))) {
      return false;
    }

    Enumeration parameters = context.getRequest().getParameterNames();
    boolean reset = false;

    String tmpSortOrder = context.getRequest().getParameter("order");
    if (tmpSortOrder != null) {
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
      this.setColumnToSortBy(tmpColumnToSortBy);
    }

    //User has specified a page number to view
    String tmpCurrentPage = context.getRequest().getParameter("page");
    if (tmpCurrentPage != null) {
      try {
        this.setCurrentOffset((Integer.parseInt(tmpCurrentPage) - 1) * itemsPerPage);
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
    while (context.getRequest().getParameter("listFilter" + (++filter)) != null) {
      String tmpListFilter = context.getRequest().getParameter("listFilter" + filter);
      addFilter(filter, tmpListFilter);
    }

    if (context.getRequest().getParameter("listFilter1") != null && resetList) {
      String thisFilter = context.getRequest().getParameter("listFilter1");
      if (listFilters.get("listFilter1") != null && !thisFilter.equals(listFilters.get("listFilter1"))) {
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

        this.getSavedCriteria().put(param, context.getRequest().getParameter(param));
      }
    }
    return true;
  }


  /**
   *  Sets the searchCriteria attribute of the PagedListInfo object
   *
   *@param  obj     The new searchCriteria value
   *@param  locale  The new searchCriteria value
   *@return         Description of the Return Value
   */
  public boolean setSearchCriteria(Object obj, Locale locale) {
    if (!this.getSavedCriteria().isEmpty()) {

      Iterator hashIterator = this.getSavedCriteria().keySet().iterator();

      while (hashIterator.hasNext()) {
        String tempKey = (String) hashIterator.next();

        if (this.getCriteriaValue(tempKey) != null && !(this.getCriteriaValue(tempKey).trim().equals(""))) {

          //its an int
          if (System.getProperty("DEBUG") != null) {
            System.out.println("PagedListInfo-> Setting: " + tempKey + "=" + getCriteriaValue(tempKey));
          }
          if (tempKey.startsWith("searchcode")) {
            ObjectUtils.setParam(obj, tempKey.substring(10), this.getCriteriaValue(tempKey));
          } else if (tempKey.startsWith("searchdate")) {
            Timestamp tmpTimestamp = DateUtils.getUserToServerDateTime(null, DateFormat.SHORT, DateFormat.LONG, this.getCriteriaValue(tempKey), locale);
            if (tmpTimestamp != null) {
              boolean modified = false;
              java.sql.Date tmpDate = new java.sql.Date(tmpTimestamp.getTime());
              modified = ObjectUtils.setParam(obj, tempKey.substring(10), tmpDate);
              if (!modified && this.getCriteriaValue(tempKey) != null && !"".equals(this.getCriteriaValue(tempKey))) {
                addError(obj, "getErrors", tempKey);
              }
            }
            if (tmpTimestamp == null && this.getCriteriaValue(tempKey) != null && !"".equals(this.getCriteriaValue(tempKey))) {
              addError(obj, "getErrors", tempKey);
            }
          } else {
            ObjectUtils.setParam(obj, tempKey.substring(6), "%" + this.getCriteriaValue(tempKey) + "%");
          }
        }
      }
    }
    return true;
  }


  /**
   *  Adds a feature to the Error attribute of the PagedListInfo class
   *
   *@param  obj    The feature to be added to the Error attribute
   *@param  param  The feature to be added to the Error attribute
   *@param  field  The feature to be added to the Error attribute
   */
  private static void addError(Object obj, String param, String field) {
    try {
      Method method = obj.getClass().getMethod(param, null);
      ((HashMap) method.invoke(obj, null)).put(field.substring(0, 1).toLowerCase() + field.substring(1) + "Error", "Invalid Date");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Adding an error -->" + field + "Error");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *  Gets the searchOptionValue attribute of the PagedListInfo object
   *
   *@param  field  Description of the Parameter
   *@return        The searchOptionValue value
   */
  public String getSearchOptionValue(String field) {
    if (this.getSavedCriteria() != null && this.getSavedCriteria().get(field) != null) {
      return (String) savedCriteria.get(field);
    }
    return "";
  }


  /**
   *  Sets the defaultSort attribute of the PagedListInfo object
   *
   *@param  column  The new defaultSort value
   *@param  order   Ex. "desc" or null
   */
  public void setDefaultSort(String column, String order) {
    if (!this.hasSortConfigured()) {
      this.setColumnToSortBy(column);
      this.setSortOrder(order);
    }
  }


  /**
   *  Gets the ColumnToSortBy attribute of the PagedListInfo object
   *
   *@return    The ColumnToSortBy value
   *@since     1.0
   */
  public String getColumnToSortBy() {
    return columnToSortBy;
  }


  /**
   *  Gets the SortOrder attribute of the PagedListInfo object
   *
   *@return    The SortOrder value
   *@since     1.0
   */
  public String getSortOrder() {
    return sortOrder;
  }


  /**
   *  Gets the ItemsPerPage attribute of the PagedListInfo object
   *
   *@return    The ItemsPerPage value
   *@since     1.0
   */
  public int getItemsPerPage() {
    return itemsPerPage;
  }


  /**
   *  Gets the MaxRecords attribute of the PagedListInfo object
   *
   *@return    The MaxRecords value
   *@since     1.11
   */
  public int getMaxRecords() {
    return maxRecords;
  }


  /**
   *  Gets the CurrentLetter attribute of the PagedListInfo object
   *
   *@return    The CurrentLetter value
   *@since     1.1
   */
  public String getCurrentLetter() {
    return currentLetter;
  }


  /**
   *  Gets the CurrentOffset attribute of the PagedListInfo object
   *
   *@return    The CurrentOffset value
   *@since     1.1
   */
  public int getCurrentOffset() {
    return currentOffset;
  }


  /**
   *  Gets the CurrentOffset attribute of the PagedListInfo object
   *
   *@return    The CurrentOffset value
   *@since     1.1
   */
  public boolean getEnableJScript() {
    return enableJScript;
  }



  /**
   *  Gets the PageLinks attribute of the PagedListInfo object <p>
   *
   *  The pages are numbered from 1 to the highest page
   *
   *@return    The PageLinks value
   *@since     1.0
   */
  public String getNumericalPageLinks() {
    int numPages = this.getNumberOfPages();
    StringBuffer links = new StringBuffer();
    links.append(numPages + " page" + ((numPages == 1) ? "" : "s") + " in this view ");
    if (numPages > 1) {
      links.append("[");
      for (int i = 1; i < (numPages + 1); i++) {
        if ((i - 1) * itemsPerPage == currentOffset) {
          links.append(" <b>" + i + "</b> ");
        } else {
          links.append("<a href=\"" + link + "&offset=" + ((i - 1) * itemsPerPage) + "\"> " + i + " </a>");
        }
      }
      links.append("]");
    }
    return links.toString();
  }


  /**
   *  Gets the listPropertiesHeader attribute of the PagedListInfo object
   *
   *@param  id  Description of Parameter
   *@return     The listPropertiesHeader value
   */
  public String getListPropertiesHeader(String id) {
    if (showForm) {
      if (expandedSelection) {
        link += "&pagedListSectionId=" + id;
      }

      return ("<form name=\"" + id + "\" action=\"" + link + "\" method=\"post\">");
    } else {
      return "";
    }

  }


  /**
   *  Gets the listPropertiesFooter attribute of the PagedListInfo object
   *
   *@return    The listPropertiesFooter value
   */
  public String getListPropertiesFooter() {
    if (showForm) {
      return ("</form>");
    } else {
      return "";
    }
  }


  /**
   *  Gets the numericalPageEntry attribute of the PagedListInfo object
   *
   *@return    The numericalPageEntry value
   */
  public String getNumericalPageEntry() {
    int numPages = this.getNumberOfPages();
    return ("<input type=\"text\" name=\"page\" value=\"" + ((currentOffset / itemsPerPage) + 1) + "\" size=\"3\">");
  }


  /**
   *  Gets the itemsPerPageEntry attribute of the PagedListInfo object
   *
   *@return    The itemsPerPageEntry value
   */
  public String getItemsPerPageEntry() {
    HtmlSelect itemSelect = new HtmlSelect();
    itemSelect.addItem("6");
    itemSelect.addItem("10");
    itemSelect.addItem("12");
    itemSelect.addItem("20");
    itemSelect.addItem("30");
    itemSelect.addItem("50");
    itemSelect.addItem("100");
    itemSelect.setJsEvent("onChange='submit();'");
    return (itemSelect.getHtml("items", itemsPerPage));
    //return("Items per page <input type=\"text\" name=\"items\" value=\"" + itemsPerPage + "\" size=\"3\">");
  }


  /**
   *  Gets the numberOfPages attribute of the PagedListInfo object
   *
   *@return    The numberOfPages value
   */
  public int getNumberOfPages() {
    return (int) Math.ceil((double) maxRecords / (double) itemsPerPage);
  }


  /**
   *  Gets the AlphebeticalPageLinks attribute of the PagedListInfo object
   *
   *@return    The AlphebeticalPageLinks value
   *@since     1.1
   */
  public String getAlphabeticalPageLinks() {
    StringBuffer links = new StringBuffer();
    for (int i = 0; i < lettersArray.length; i++) {
      String thisLetter = lettersArray[i];
      if (thisLetter.equals(currentLetter)) {
        links.append(" <b>" + thisLetter + "</b> ");
      } else {
        links.append("<a href='" + link + "&letter=" + thisLetter + "'> " + thisLetter + " </a>");
      }
    }
    return links.toString();
  }



  /**
   *  Gets the AlphebeticalPageLinks attribute of the PagedListInfo object
   *
   *@param  javaScript  Description of the Parameter
   *@param  formName    Description of the Parameter
   *@return             The AlphebeticalPageLinks value
   *@since              1.1
   */
  public String getAlphabeticalPageLinks(String javaScript, String formName) {
    StringBuffer links = new StringBuffer();
    for (int i = 0; i < lettersArray.length; i++) {
      String thisLetter = lettersArray[i];
      if (thisLetter.equals(currentLetter)) {
        links.append(" <b>" + thisLetter + "</b> ");
      } else {
        links.append("<a href=\"javascript:" + javaScript + "('letter','" + thisLetter + "','" + formName + "');\"> " + thisLetter + " </a>");
      }
    }
    return links.toString();
  }


  /**
   *  Gets the PreviousPageLink attribute of the PagedListInfo object
   *
   *@return    The PreviousPageLink value
   *@since     1.1
   */
  public String getPreviousPageLink() {
    return getPreviousPageLink("&laquo;");
  }


  /**
   *  Gets the PreviousPageLink attribute of the PagedListInfo object
   *
   *@param  linkInfo  Description of Parameter
   *@return           The PreviousPageLink value
   *@since            1.8
   */
  public String getPreviousPageLink(String linkInfo) {
    return getPreviousPageLink(linkInfo, linkInfo);
  }


  /**
   *  Gets the PreviousPageLink attribute of the PagedListInfo object
   *
   *@param  linkOn   Description of Parameter
   *@param  linkOff  Description of Parameter
   *@return          The PreviousPageLink value
   *@since           1.8
   */
  public String getPreviousPageLink(String linkOn, String linkOff) {

    StringBuffer result = new StringBuffer();

    if (currentOffset > 0) {
      int newOffset = currentOffset - itemsPerPage;
      //Handle scroll reload
      String scrollStart = "";
      String scrollEnd = "";
      if (scrollReload) {
        scrollStart = "javascript:scrollReload('";
        scrollEnd = "');";
      }
      if (!getEnableJScript()) {
        //Normal link
        result.append("<a href=\"" + scrollStart + link + "&pagedListInfoId=" + this.getId());
        if (getExpandedSelection()) {
          result.append("&pagedListSectionId=" + this.getId());
        }
        result.append("&offset=" + (newOffset > 0 ? newOffset : 0) + scrollEnd + "\">" + linkOn + "</a>");
        return result.toString();
      } else {
        //Use javascript for constructing the link
        result.append("<a href=\"javascript:offsetsubmit('" + (newOffset > 0 ? newOffset : 0) + "');\">" + linkOn + "</a>");
        return result.toString();
      }
    } else {
      return linkOff;
    }
  }



  /**
   *  Gets the NextPageLink attribute of the PagedListInfo object
   *
   *@return    The NextPageLink value
   *@since     1.1
   */
  public String getNextPageLink() {
    return getNextPageLink("&raquo;");
  }


  /**
   *  Gets the NextPageLink attribute of the PagedListInfo object
   *
   *@param  linkInfo  Description of Parameter
   *@return           The NextPageLink value
   *@since            1.8
   */
  public String getNextPageLink(String linkInfo) {
    return getNextPageLink(linkInfo, linkInfo);
  }


  /**
   *  Gets the NextPageLink attribute of the PagedListInfo object
   *
   *@param  linkOn   Description of Parameter
   *@param  linkOff  Description of Parameter
   *@return          The NextPageLink value
   *@since           1.8
   */
  public String getNextPageLink(String linkOn, String linkOff) {

    StringBuffer result = new StringBuffer();

    if ((currentOffset + itemsPerPage) < maxRecords) {
      //Handle scroll reload
      String scrollStart = "";
      String scrollEnd = "";
      if (scrollReload) {
        scrollStart = "javascript:scrollReload('";
        scrollEnd = "');";
      }
      if (!getEnableJScript()) {
        //Normal link
        result.append("<a href=\"" + scrollStart + link + "&pagedListInfoId=" + this.getId());
        if (getExpandedSelection()) {
          result.append("&pagedListSectionId=" + this.getId());
        }
        result.append("&offset=" + (currentOffset + itemsPerPage) + scrollEnd + "\">" + linkOn + "</a>");
        return result.toString();
      } else {
        //Use javascript for constructing the link
        result.append("<a href=\"javascript:offsetsubmit('" + (currentOffset + itemsPerPage) + "');\">" + linkOn + "</a>");
        return result.toString();
      }
    } else {
      return linkOff;
    }
  }


  /**
   *  Gets the expandLink attribute of the PagedListInfo object
   *
   *@param  linkOn        Description of the Parameter
   *@param  linkOff       Description of the Parameter
   *@param  collapseLink  Description of the Parameter
   *@return               The expandLink value
   */
  public String getExpandLink(String linkOn, String linkOff, String collapseLink) {
    if ((currentOffset + itemsPerPage) < maxRecords && !expandedSelection) {
      return "<a href='" + link + "&pagedListInfoId=" + this.getId() + "&pagedListSectionId=" + this.getId() + "'>" + linkOn + "</a>";
    } else if ((currentOffset + itemsPerPage) >= maxRecords && !expandedSelection) {
      return linkOff;
    } else {
      return "<a href='" + link + "&resetList=true&pagedListInfoId=" + this.getId() + "'>" + collapseLink + "</a>";
    }
  }


  /**
   *  Gets the expandLink attribute of the PagedListInfo object
   *
   *@param  expandLink    Description of the Parameter
   *@param  collapseLink  Description of the Parameter
   *@return               The expandLink value
   */
  public String getExpandLink(String expandLink, String collapseLink) {
    if (!expandedSelection) {
      return "<a href=\"" + link + "&pagedListInfoId=" + this.getId() + "&pagedListSectionId=" + this.getId() + "\">" + expandLink + "</a>";
    } else {
      return "<a href=\"" + link + "&resetList=true&pagedListInfoId=" + this.getId() + "\">" + collapseLink + "</a>";
    }
  }


  /**
   *  Gets the sortIcon attribute of the PagedListInfo object
   *
   *@param  columnName  Description of Parameter
   *@return             The sortIcon value
   */
  public String getSortIcon(String columnName) {
    if (columnName.equals(columnToSortBy)) {
      if (sortOrder != null && sortOrder.indexOf("desc") > -1) {
        return "<img border=0 src=\"images/down.gif\" align=\"bottom\">";
      } else {
        return "<img border=0 src=\"images/up.gif\" align=\"bottom\">";
      }
    } else {
      return "";
    }
  }


  /**
   *  Gets the ListView attribute of the PagedListInfo object
   *
   *@return    The ListView value
   *@since     1.11
   */
  public String getListView() {
    return listView;
  }


  /**
   *  Creates the value and selected information for an HTML combo-box.<p>
   *
   *  In the HTML you would have:<br>
   *  <option <%= info.getOptionValue("my") %>>Text </option> <p>
   *
   *  To display:<br>
   *  <option value="my" selected>Text</option>
   *
   *@param  tmp  Description of Parameter
   *@return      The OptionValue value
   *@since       1.11
   */
  public String getOptionValue(String tmp) {
    return ("value=\"" + tmp + "\"" + (tmp.equals(listView) ? " selected" : ""));
  }


  /**
   *  Gets the filterOption attribute of the PagedListInfo object
   *
   *@param  filterName  Description of the Parameter
   *@param  tmp         Description of the Parameter
   *@return             The filterOption value
   */
  public String getFilterOption(String filterName, String tmp) {
    String current = (String) listFilters.get(filterName);
    return ("value=\"" + tmp + "\"" + (tmp.equals(current) ? " selected" : ""));
  }


  /**
   *  Gets the filterValue attribute of the PagedListInfo object
   *
   *@param  tmp  Description of Parameter
   *@return      The filterValue value
   */
  public String getFilterValue(String tmp) {
    return (String) listFilters.get(tmp);
  }


  /**
   *  Gets the criteriaValue attribute of the PagedListInfo object
   *
   *@param  tmp  Description of the Parameter
   *@return      The criteriaValue value
   */
  public String getCriteriaValue(String tmp) {
    return (String) savedCriteria.get(tmp);
  }


  /**
   *  Gets the filterKey attribute of the PagedListInfo object
   *
   *@param  tmp  Description of Parameter
   *@return      The filterKey value
   */
  public int getFilterKey(String tmp) {
    try {
      return Integer.parseInt((String) listFilters.get(tmp));
    } catch (Exception e) {
      return -1;
    }
  }


  /**
   *  Gets the endOfOffset attribute of the PagedListInfo object
   *
   *@param  db                Description of Parameter
   *@return                   The endOfOffset value
   *@exception  SQLException  Description of Exception
   */
  public boolean isEndOfOffset(Connection db) throws SQLException {
    if (this.getItemsPerPage() > 0 &&
        DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
        iteration >= this.getItemsPerPage()) {
      return true;
    } else {
      ++iteration;
      return false;
    }
  }


  /**
   *  Gets the refreshTag attribute of the PagedListInfo object
   *
   *@param  tmp  Description of the Parameter
   *@return      The refreshTag value
   */
  public String getRefreshTag(String tmp) {
    return ("<a href=\"" + link + "\"> " + tmp + " </a>");
  }


  /**
   *  Adds a feature to the Filter attribute of the PagedListInfo object
   *
   *@param  param  The feature to be added to the Filter attribute
   *@param  value  The feature to be added to the Filter attribute
   */
  public void addFilter(int param, String value) {
    listFilters.put("listFilter" + param, value);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean hasLink() {
    return (link != null && !"".equals(link.trim()));
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasListFilters() {
    return listFilters.size() > 0;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasSortConfigured() {
    return (this.getColumnToSortBy() != null && !this.getColumnToSortBy().equals(""));
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public boolean hasSortOrderConfigured() {
    return (this.getSortOrder() != null && !this.getSortOrder().equals(""));
  }


  /**
   *  Description of the Method
   *
   *@param  db            Description of Parameter
   *@param  sqlStatement  Description of Parameter
   */
  public void appendSqlSelectHead(Connection db, StringBuffer sqlStatement) {
    if (DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
        this.getItemsPerPage() > 0) {
      int x = this.getItemsPerPage() + this.getCurrentOffset();
      sqlStatement.append("SELECT TOP " + x + " ");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("PagedListInfo-> Requesting TOP " + x);
      }
    } else {
      sqlStatement.append("SELECT ");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db            Description of Parameter
   *@param  sqlStatement  Description of Parameter
   */
  public void appendSqlTail(Connection db, StringBuffer sqlStatement) {
    sqlStatement.append("ORDER BY ");
    //Determine sort order
    //If multiple columns are being sorted, then the sort order applies to all columns
    if (this.getColumnToSortBy().indexOf(",") > -1) {
      int count = 0;
      StringTokenizer st = new StringTokenizer(this.getColumnToSortBy(), ",");
      while (st.hasMoreTokens()) {
        ++count;
        String column = st.nextToken();
        sqlStatement.append(column + " ");
//        if (count == 1) {
        if (this.hasSortOrderConfigured()) {
          sqlStatement.append(this.getSortOrder() + " ");
        }
//        }
        if (st.hasMoreTokens()) {
          sqlStatement.append(",");
        }
      }
    } else {
      sqlStatement.append(this.getColumnToSortBy() + " ");
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
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  public void doManualOffset(Connection db, ResultSet rs) throws SQLException {
    iteration = 0;
    if (this.getItemsPerPage() > 0 &&
        DatabaseUtils.getType(db) == DatabaseUtils.MSSQL) {
      for (int skipCount = 0; skipCount < this.getCurrentOffset(); skipCount++) {
        rs.next();
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.9
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("===================================================\r\n");
    sb.append("Link: " + link + "\r\n");
    sb.append("Sort Column: " + columnToSortBy + "\r\n");
    sb.append("SOrt Order: " + sortOrder + "\r\n");
    sb.append("Items per page: " + itemsPerPage + "\r\n");
    sb.append("Total record count: " + maxRecords + "\r\n");
    sb.append("Current offset letter: " + currentLetter + "\r\n");
    sb.append("Current offset record: " + currentOffset + "\r\n");
    sb.append("List View: " + listView + "\r\n");
    return sb.toString();
  }


  /**
   *  Description of the Method
   */
  private void resetList() {
    this.setCurrentLetter("");
    this.setCurrentOffset(0);
  }


  /**
   *  Gets the pageSize attribute of the PagedListInfo object
   *
   *@return    The pageSize value
   */
  public int getPageSize() {
    if ((currentOffset + itemsPerPage) < maxRecords) {
      // current = 0
      // items = 10
      // max = 17
      // 0 + 10 < 17
      return (currentOffset + itemsPerPage);
    } else {
      // current = 10
      // items = 10
      // max = 17
      return (maxRecords);
    }
  }
}

