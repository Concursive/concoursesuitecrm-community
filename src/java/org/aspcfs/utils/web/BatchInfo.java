/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ObjectUtils;

import java.sql.*;
import java.text.DateFormat;
import java.util.*;

/**
 *  Allows information to be stored in an object for performing batch operations
 *  on a paged list. <p>
 *
 *  </p> When a web user visits a paged list, store this object in the request
 *  and call it
 *
 *@author     Ananth
 *@version
 *@created    February 24, 2006
 */
public class BatchInfo {
  private String name = null;
  private String action = "BatchProcess.do?command=Process";
  private String selected = null;
  private int size = -1;
  private int count = 0;


  /**
   *  Gets the name attribute of the BatchInfo object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the selected attribute of the BatchInfo object
   *
   *@return    The selected value
   */
  public String getSelected() {
    return selected;
  }


  /**
   *  Sets the selected attribute of the BatchInfo object
   *
   *@param  tmp  The new selected value
   */
  public void setSelected(String tmp) {
    selected = tmp;
  }


  /**
   *  Sets the name attribute of the BatchInfo object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the action attribute of the BatchInfo object
   *
   *@return    The action value
   */
  public String getAction() {
    return action;
  }


  /**
   *  Sets the action attribute of the BatchInfo object
   *
   *@param  tmp  The new action value
   */
  public void setAction(String tmp) {
    this.action = tmp;
  }


  /**
   *  Gets the size attribute of the BatchInfo object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Sets the size attribute of the BatchInfo object
   *
   *@param  tmp  The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the size attribute of the BatchInfo object
   *
   *@param  tmp  The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   *  Gets the count attribute of the BatchInfo object
   *
   *@return    The count value
   */
  public int getCount() {
    return ++count;
  }


  /**
   *  Constructor for the BatchInfo object
   */
  public BatchInfo() { }


  /**
   *  Constructor for the BatchInfo object
   *
   *@param  name  Description of the Parameter
   */
  public BatchInfo(String name) {
    this.name = name;
  }


  /**
   *  Sets the parameters attribute of the BatchInfo object
   *
   *@param  context  The new parameters value
   *@return          Description of the Return Value
   */
  public boolean setParameters(ActionContext context) {
    if (context.getRequest().getParameter("selected") != null) {
      selected = context.getRequest().getParameter("selected");
    }
    return true;
  }
}

