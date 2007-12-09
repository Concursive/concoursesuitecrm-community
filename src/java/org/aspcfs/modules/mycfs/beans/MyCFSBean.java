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
package org.aspcfs.modules.mycfs.beans;

import com.darkhorseventures.framework.beans.GenericBean;

import java.util.Vector;

/**
 * Used by the MyCFS module for passing formatted data to the MyCFS JSP
 *
 * @author mrajkowski
 * @version 1.1
 * @created July 9, 2001
 */
public class MyCFSBean extends GenericBean {

  //Use the User session object instead of firstName here
  private String firstName = "";
  //Use the User session object instead of lastName here
  private String lastName = "";
  private Vector items = new Vector();


  /**
   * Constructor for the myCFSBean object
   *
   * @since 1.0
   */
  public MyCFSBean() {
  }


  /**
   * Sets the FirstName attribute of the myCFSBean object
   *
   * @param tmp The new FirstName value
   * @since 1.0
   * @deprecated
   */
  public void setFirstName(String tmp) {
    this.firstName = tmp;
  }


  /**
   * Sets the LastName attribute of the myCFSBean object
   *
   * @param tmp The new LastName value
   * @since 1.0
   * @deprecated
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }


  /**
   * Gets the FirstName attribute of the myCFSBean object
   *
   * @return The FirstName value
   * @since 1.0
   * @deprecated
   */
  public String getFirstName() {
    return firstName;
  }


  /**
   * Gets the LastName attribute of the myCFSBean object
   *
   * @return The LastName value
   * @since 1.0
   * @deprecated
   */
  public String getLastName() {
    return lastName;
  }


  /**
   * Gets the Items attribute of the MyCFSBean object, this should be a Vector
   * of HTML String objects
   *
   * @return The Items value
   * @since 1.1
   */
  public Vector getItems() {
    return items;
  }


  /**
   * Adds a feature to the Item attribute of the MyCFSBean object, this should
   * be a String object formatted with HTML
   *
   * @param tmp The feature to be added to the Item attribute
   * @since 1.1
   */
  public void addItem(String tmp) {
    this.items.addElement(tmp);
  }

}

