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
package org.aspcfs.modules.mycfs.base;

/**
 *  Represents an Alert.
 *
 *@author     Mathur
 *@created    January 20, 2003
 *@version    $Id$
 */
public class AlertType {

  protected String name = null;
  protected String displayName = null;
  protected String className = null;



  /**
   *  Constructor for the AlertType object
   *
   *@param  name         Description of the Parameter
   *@param  className    Description of the Parameter
   *@param  displayName  Description of the Parameter
   */
  public AlertType(String name, String className, String displayName) {
    this.name = name;
    this.displayName = displayName;
    this.className = className;
  }


  /**
   *  Sets the name attribute of the AlertType object
   *
   *@param  name  The new name value
   */
  public void setName(String name) {
    this.name = name;
  }



  /**
   *  Sets the className attribute of the AlertType object
   *
   *@param  className  The new className value
   */
  public void setClassName(String className) {
    this.className = className;
  }


  /**
   *  Sets the displayName attribute of the AlertType object
   *
   *@param  displayName  The new displayName value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  /**
   *  Gets the displayName attribute of the AlertType object
   *
   *@return    The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }


  /**
   *  Gets the name attribute of the AlertType object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }



  /**
   *  Gets the className attribute of the AlertType object
   *
   *@return    The className value
   */
  public String getClassName() {
    return className;
  }

}


