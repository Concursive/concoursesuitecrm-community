/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.beans;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.util.*;
import org.aspcfs.utils.StringUtils;
import com.zeroio.utils.SearchUtils;

/**
 *  Contains the properties of a search form
 *
 *@author     matt rajkowski
 *@created    May 17, 2004
 *@version    $Id$
 */
public class SearchBean extends GenericBean {
  public final static int UNDEFINED = -1;
  // constant scopes
  public final static int ALL = 1;
  public final static int THIS = 2;
  // constant sections
  public final static int NEWS = 3;
  public final static int DISCUSSION = 4;
  public final static int DOCUMENTS = 5;
  public final static int LISTS = 6;
  public final static int PLAN = 7;
  public final static int TICKETS = 8;
  public final static int DETAILS = 9;
  // search form
  private String query = null;
  private int scope = UNDEFINED;
  private int section = UNDEFINED;
  private int projectId = -1;
  // helper properties
  private String parsedQuery = null;
  private ArrayList terms = null;
  private int numberFound = 0;
  private boolean valid = false;


  /**
   *  Constructor for the SearchBean object
   */
  public SearchBean() { }


  /**
   *  Gets the query attribute of the SearchBean object
   *
   *@return    The query value
   */
  public String getQuery() {
    return query;
  }


  /**
   *  Sets the query attribute of the SearchBean object
   *
   *@param  tmp  The new query value
   */
  public void setQuery(String tmp) {
    this.query = tmp;
  }


  /**
   *  Gets the scope attribute of the SearchBean object
   *
   *@return    The scope value
   */
  public int getScope() {
    return scope;
  }


  /**
   *  Sets the scope attribute of the SearchBean object
   *
   *@param  tmp  The new scope value
   */
  public void setScope(int tmp) {
    this.scope = tmp;
  }


  /**
   *  Gets the section attribute of the SearchBean object
   *
   *@return    The section value
   */
  public int getSection() {
    return section;
  }


  /**
   *  Sets the section attribute of the SearchBean object
   *
   *@param  tmp  The new section value
   */
  public void setSection(int tmp) {
    this.section = tmp;
  }


  /**
   *  Sets the section attribute of the SearchBean object
   *
   *@param  tmp  The new section value
   */
  public void setSection(String tmp) {
    this.section = Integer.parseInt(tmp);
  }


  /**
   *  Sets the scope attribute of the SearchBean object
   *
   *@param  tmp  The new scope value
   */
  public void setScope(String tmp) {
    // scope
    if (tmp.startsWith("this")) {
      scope = THIS;
    } else if (tmp.startsWith("all")) {
      scope = ALL;
    } else {
      scope = UNDEFINED;
    }
    // section
    if (tmp.endsWith("News")) {
      section = NEWS;
    } else if (tmp.endsWith("Discussion")) {
      section = DISCUSSION;
    } else if (tmp.endsWith("Documents")) {
      section = DOCUMENTS;
    } else if (tmp.endsWith("Lists")) {
      section = LISTS;
    } else if (tmp.endsWith("Plan")) {
      section = PLAN;
    } else if (tmp.endsWith("Tickets")) {
      section = TICKETS;
    } else if (tmp.endsWith("Details")) {
      section = DETAILS;
    } else {
      section = UNDEFINED;
    }
  }


  /**
   *  Gets the projectId attribute of the SearchBean object
   *
   *@return    The projectId value
   */
  public int getProjectId() {
    return projectId;
  }


  /**
   *  Sets the projectId attribute of the SearchBean object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(int tmp) {
    this.projectId = tmp;
  }


  /**
   *  Sets the projectId attribute of the SearchBean object
   *
   *@param  tmp  The new projectId value
   */
  public void setProjectId(String tmp) {
    this.projectId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the valid attribute of the SearchBean object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    return valid;
  }


  /**
   *  Gets the parsedQuery attribute of the SearchBean object
   *
   *@return    The parsedQuery value
   */
  public String getParsedQuery() {
    return parsedQuery;
  }


  /**
   *  Sets the parsedQuery attribute of the SearchBean object
   *
   *@param  tmp  The new parsedQuery value
   */
  public void setParsedQuery(String tmp) {
    this.parsedQuery = tmp;
  }


  /**
   *  Gets the terms attribute of the SearchBean object
   *
   *@return    The terms value
   */
  public ArrayList getTerms() {
    return terms;
  }


  /**
   *  Sets the terms attribute of the SearchBean object
   *
   *@param  tmp  The new terms value
   */
  public void setTerms(ArrayList tmp) {
    this.terms = tmp;
  }


  /**
   *  Adds a feature to the NumberFound attribute of the SearchBean object
   *
   *@param  count  The feature to be added to the NumberFound attribute
   */
  public void addNumberFound(int count) {
    numberFound += count;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean parseQuery() {
    // break up the query string into pieces to be used for building database queries
    if (query == null || "".equals(query.trim())) {
      return false;
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < query.length(); i++) {
      if ("():[]{}".indexOf(query.charAt(i)) == -1) {
        sb.append(query.charAt(i));
      }
    }
    if (sb.length() > 0) {
      valid = true;
      parsedQuery = SearchUtils.parseSearchText(sb.toString(), true);
      terms = SearchUtils.parseSearchTerms(parsedQuery);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SearchBean-> Terms: (" + terms.size() + ") " + terms.toString());
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SearchBean-> " + parsedQuery);
    }
    return true;
  }

}

