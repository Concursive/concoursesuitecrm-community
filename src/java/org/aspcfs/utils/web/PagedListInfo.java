package com.darkhorseventures.webutils;

import org.theseus.actions.*;

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
public class PagedListInfo {
  public final String[] lettersArray = {"0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

  String link = "";
  String columnToSortBy = null;
  String sortOrder = null;
  int itemsPerPage = 10;
  int maxRecords = 0;
  String currentLetter = "";
  int currentOffset = 0;
  String listView = null;


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
   *  Sets the SortOrder attribute of the PagedListInfo object
   *
   *@param  tmp  The new SortOrder value
   *@since       1.0
   */
  public void setSortOrder(String tmp) {
    this.sortOrder = tmp;
  }


  /**
   *  Sets the ItemsPerPage attribute of the PagedListInfo object
   *
   *@param  tmp  The new ItemsPerPage value
   *@since       1.0
   */
  public void setItemsPerPage(int tmp) {
    this.itemsPerPage = tmp;
  }


  /**
   *  Sets the ItemsPerPage attribute of the PagedListInfo object
   *
   *@param  tmp  The new ItemsPerPage value
   *@since       1.0
   */
  public void setItemsPerPage(String tmp) {
    itemsPerPage = Integer.parseInt(tmp);
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
    this.setCurrentOffset(Integer.parseInt(tmp));
  }


  /**
   *  Sets the ListView attribute of the PagedListInfo object.  The ListView
   *  property stores what view the user has selected. 
   *
   *@param  tmp  The new ListView value
   *@since 1.11
   */
  public void setListView(String tmp) {
    this.listView = tmp;
  }



  /**
   *  Sets the Parameters attribute of the PagedListInfo object
   *
   *@param  context  The new Parameters value
   *@since           1.1
   */
  public void setParameters(ActionContext context) {

    String tmpSortOrder = context.getRequest().getParameter("order");
    if (tmpSortOrder != null) {
      this.setSortOrder(tmpSortOrder);
    }

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

    String tmpItemsPerPage = context.getRequest().getParameter("items");
    if (tmpItemsPerPage != null) {
      this.setItemsPerPage(tmpItemsPerPage);
    }

    String tmpCurrentLetter = context.getRequest().getParameter("letter");
    if (tmpCurrentLetter != null) {
      this.setCurrentLetter(tmpCurrentLetter);
    } else {
      this.setCurrentLetter("");
    }

    String tmpCurrentOffset = context.getRequest().getParameter("offset");
    if (tmpCurrentOffset != null) {
      this.setCurrentOffset(tmpCurrentOffset);
    }
    
    String tmpListView = context.getRequest().getParameter("listView");
    if (tmpListView != null) {
      if (listView != null && !listView.equals(tmpListView)) {
        this.setCurrentLetter("");
        this.setCurrentOffset(0);
      }
      this.setListView(tmpListView);
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
   *  Gets the PageLinks attribute of the PagedListInfo object <p>
   *
   *  The pages are numbered from 1 to the highest page
   *
   *@return    The PageLinks value
   *@since     1.0
   */
  public String getNumericalPageLinks() {
    int numPages = (int)Math.ceil((double)maxRecords / (double)itemsPerPage);
    StringBuffer links = new StringBuffer();
    links.append(numPages + " page" + ((numPages == 1) ? "" : "s") + " in this view ");
    if (numPages > 1) {
      links.append("[");
      for (int i = 1; i < (numPages + 1); i++) {
        if ((i - 1) * itemsPerPage == currentOffset) {
          links.append(" <b>" + i + "</b> ");
        } else {
          links.append("<a href='" + link + "&offset=" + ((i - 1) * itemsPerPage) + "'> " + i + " </a>");
        }
      }
      links.append("]");
    }
    return links.toString();
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
    if (currentOffset > 0) {
      int newOffset = currentOffset - itemsPerPage;
      return "<a href='" + link + "&offset=" + (newOffset > 0 ? newOffset : 0) + "'>" + linkOn + "</a>";
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
    if ((currentOffset + itemsPerPage) < maxRecords) {
      return "<a href='" + link + "&offset=" + (currentOffset + itemsPerPage) + "'>" + linkOn + "</a>";
    } else {
      return linkOff;
    }
  }
  
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

}

