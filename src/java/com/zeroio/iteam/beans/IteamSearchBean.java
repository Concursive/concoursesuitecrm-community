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
public class IteamSearchBean extends SearchBean {
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
  private int projectId = -1;


  /**
   *  Constructor for the SearchBean object
   */
  public IteamSearchBean() { }


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
}

