/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import javax.servlet.http.*;

/**
 *  Represents an invitation to a user to join iTeam
 *
 *@author     matt rajkowski
 *@created    October 27, 2003
 *@version    $Id$
 */
public class Invitation {

  //bean properties
  private String email = null;
  private String firstName = null;
  private String lastName = null;
  //helpers
  private boolean sentMail = false;


  /**
   *  Constructor for the Invitation object
   */
  public Invitation() { }


  /**
   *  Constructor for the Invitation object
   *
   *@param  request  Description of the Parameter
   *@param  id       Description of the Parameter
   */
  public Invitation(HttpServletRequest request, int id) {
    email = request.getParameter("email" + id);
    firstName = request.getParameter("firstName" + id);
    lastName = request.getParameter("lastName" + id);
  }


  /**
   *  Sets the email attribute of the Invitation object
   *
   *@param  tmp  The new email value
   */
  public void setEmail(String tmp) {
    this.email = tmp;
  }


  /**
   *  Sets the firstName attribute of the Invitation object
   *
   *@param  tmp  The new firstName value
   */
  public void setFirstName(String tmp) {
    this.firstName = tmp;
  }


  /**
   *  Sets the lastName attribute of the Invitation object
   *
   *@param  tmp  The new lastName value
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }


  /**
   *  Sets the sentMail attribute of the Invitation object
   *
   *@param  tmp  The new sentMail value
   */
  public void setSentMail(boolean tmp) {
    this.sentMail = tmp;
  }


  /**
   *  Gets the email attribute of the Invitation object
   *
   *@return    The email value
   */
  public String getEmail() {
    return email;
  }


  /**
   *  Gets the firstName attribute of the Invitation object
   *
   *@return    The firstName value
   */
  public String getFirstName() {
    return firstName;
  }


  /**
   *  Gets the lastName attribute of the Invitation object
   *
   *@return    The lastName value
   */
  public String getLastName() {
    return lastName;
  }


  /**
   *  Gets the sentMail attribute of the Invitation object
   *
   *@return    The sentMail value
   */
  public boolean getSentMail() {
    return sentMail;
  }


  /**
   *  Gets the valid attribute of the Invitation object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    if (email == null || "".equals(email.trim())) {
      return false;
    }
    if (firstName == null || "".equals(firstName.trim())) {
      return false;
    }
    if (lastName == null || "".equals(lastName.trim())) {
      return false;
    }
    return true;
  }

}

