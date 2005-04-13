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
package org.aspcfs.utils;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    March 14, 2005
 * @version    $Id$
 */
public class ContactUtils {
  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  lastName          Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static String foundDuplicateLastName(Connection db, String lastName) throws SQLException {
    if (lastName != null && !"".equals(lastName)) {
      ContactList contacts = new ContactList();
      contacts.setLastName(lastName);
      contacts.buildList(db);
      if (contacts.size() > 1) {
        return ((Contact) contacts.get(0)).getNameFull();
      }
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  companyName       Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static ContactList foundDuplicateCompany(Connection db, String companyName) throws SQLException {
    ContactList contacts = new ContactList();
    if (companyName != null && !"".equals(companyName)) {
      contacts = new ContactList();
      contacts.setCompany(companyName);
      contacts.buildList(db);
    }
    return contacts;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  emailAddress      Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static String foundDuplicateEmailAddress(Connection db, String emailAddress) throws SQLException {
    if (emailAddress != null && !"".equals(emailAddress)) {
      ContactList contacts = new ContactList();
      contacts.setEmailAddress(emailAddress);
      contacts.buildList(db);
      if (contacts.size() > 1) {
        return ((Contact) contacts.get(0)).getNameFull();
      }
    }
    return "";
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  emailAddresses    Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static HashMap foundDuplicateEmailAddresses(Connection db, EmailAddressList emailAddresses) throws SQLException {
    HashMap map = new HashMap();
    Iterator iterator = emailAddresses.iterator();
    while (iterator.hasNext()) {
      EmailAddress emailAddress = (EmailAddress) iterator.next();
      ContactList contacts = new ContactList();
      contacts.setEmailAddress(emailAddress.getEmail());
      contacts.buildList(db);
      if (contacts.size() > 1) {
        map.put(emailAddress.getEmail(), ((Contact) contacts.get(0)).getNameFull());
      }
    }
    return map;
  }
}

