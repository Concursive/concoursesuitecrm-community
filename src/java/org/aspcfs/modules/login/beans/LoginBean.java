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
package org.aspcfs.modules.login.beans;

import com.darkhorseventures.framework.beans.*;
import org.aspcfs.utils.PasswordHash;

/**
 *  Used by the Login module for passing formatted data to the Login JSP
 *
 *@author     chris
 *@created    July 2, 2001
 *@version    $Id$
 */
public class LoginBean extends GenericBean {
  private String message = "";
  private String username = "";
  private String password = "";


  /**
   *  Default constructor.
   *
   *@since    1.0
   */
  public LoginBean() { }


  /**
   *  Sets the Username attribute of the object.
   *
   *@param  tmp  The new Username value
   *@since       1.0
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   *  Sets the Password attribute of the object to the encrypted value of the
   *  given value.
   *
   *@param  tmp  The new Password value
   *@since       1.0
   */
  public void setPassword(String tmp) {
    PasswordHash pwh = new PasswordHash();
    this.password = pwh.encrypt(tmp);
  }


  /**
   *  Sets the Message attribute of the LoginBean object
   *
   *@param  tmp  The new Message value
   *@since       1.1
   */
  public void setMessage(String tmp) {
    this.message = tmp;
  }



  /**
   *  Gets the Username attribute of the object.
   *
   *@return    The Username value
   *@since     1.0
   */
  public String getUsername() {
    return username;
  }


  /**
   *  Gets the password attribute of the object. The value stored in this object
   *  is encrypted -- this method returns the encrypted value. The original
   *  value is not stored.
   *
   *@return    The Password value
   *@since     1.0
   */
  public String getPassword() {
    return password;
  }


  /**
   *  Gets the Message attribute of the LoginBean object
   *
   *@return    The Message value
   *@since     1.1
   */
  public String getMessage() {
    return message;
  }

}

