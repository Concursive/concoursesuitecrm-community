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

/**
 *  Custom Contact Filter for the Contacts Popup
 *
 *@author     Mathur
 *@created    March 5, 2003
 *@version    $Id$
 */
public class Filter {
  String value = null;
  String displayName = null;


  /**
   *  Constructor for the Filter object
   *
   *@param  value        Description of the Parameter
   *@param  displayName  Description of the Parameter
   */
  public Filter(String value, String displayName) {
    this.value = value;
    this.displayName = displayName;
  }


  /**
   *  Sets the value attribute of the Filter object
   *
   *@param  value  The new value value
   */
  public void setValue(String value) {
    this.value = value;
  }


  /**
   *  Sets the displayName attribute of the Filter object
   *
   *@param  displayName  The new displayName value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  /**
   *  Gets the value attribute of the Filter object
   *
   *@return    The value value
   */
  public String getValue() {
    return value;
  }


  /**
   *  Gets the displayName attribute of the Filter object
   *
   *@return    The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }

}

