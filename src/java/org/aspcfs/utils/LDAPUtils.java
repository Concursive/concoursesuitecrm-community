/*
 *  Copyright(c) 2005 TBWA\Worldwide IT
 *  Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.modules.base.EmailAddress;
import org.aspcfs.modules.contacts.base.ContactEmailAddressList;
import org.aspcfs.modules.login.beans.LoginBean;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.sql.Connection;
import java.util.Hashtable;

/**
 * Description of the Class
 * 
 * @author Peter Kehl
 * @version $Id$
 * @created March 2, 2005
 */
public class LDAPUtils {

  public static int RESULT_NOT_REQUIRED = -1;
  public static int RESULT_INVALID = 0;
  public static int RESULT_VALID = 1;

  public static int authenticateUser(ApplicationPrefs prefs, Connection db,
      LoginBean bean) {
    String searchValue = null;
    // Use Centric CRM Login Id
    if ("username".equals(prefs.get("LDAP.CENTRIC_CRM.FIELD"))) {
      searchValue = bean.getUsername();
    }
    // Use Centric CRM user's email address
    try {
      if ("email".equals(prefs.get("LDAP.CENTRIC_CRM.FIELD"))) {
        ContactEmailAddressList emailAddressList = new ContactEmailAddressList();
        emailAddressList.setType(1);
        emailAddressList.setUsername(bean.getUsername());
        emailAddressList.buildList(db);
        if (emailAddressList.size() == 1) {
          EmailAddress thisEmail = (EmailAddress) emailAddressList.get(0);
          searchValue = thisEmail.getEmail();
        }
      }
    } catch (Exception e) {
      System.out.println("LDAPUtils-> authenticateUser error: "
          + e.getMessage());
      return RESULT_INVALID;
    }
    return authenticateUser(prefs, searchValue, bean.getLdapPassword());
  }

  public static int authenticateUser(ApplicationPrefs prefs, String username,
      String password) {
    // Proceed with LDAP
    if (username != null && isLdapString(username)) {
      String userDN = null;
      boolean searchByAttribute = "true".equals(prefs
          .get("LDAP.SEARCH.BY_ATTRIBUTE"));
      if (searchByAttribute) {
        userDN = searchLDAP(prefs, username);
      } else {
        String compositeDNprefix = prefs.get("LDAP.SEARCH.PREFIX");
        String compositeDNpostfix = prefs.get("LDAP.SEARCH.POSTFIX");
        userDN = compositeDNprefix + username + compositeDNpostfix;
      }
      if (authenticateLDAP(prefs, userDN, password)) {
        return RESULT_VALID;
      }
    } else {
      System.out.println("LDAPUtils-> Username is not a valid string");
    }
    return RESULT_INVALID;
  }


  public static String searchLDAP(ApplicationPrefs prefs, String searchValue) {
    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY, prefs.get("LDAP.FACTORY"));
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, prefs.get("LDAP.SEARCH.USERNAME"));
    env.put(Context.SECURITY_CREDENTIALS, prefs.get("LDAP.SEARCH.PASSWORD"));
    env.put(Context.PROVIDER_URL, prefs.get("LDAP.SERVER"));
    DirContext ldap = null;
    try {
      ldap = new InitialDirContext(env);
      String searchName = prefs.get("LDAP.SEARCH.ATTRIBUTE");
      String searchContainer = prefs.get("LDAP.SEARCH.CONTAINER");
      boolean doSearchSubtree = "true".equals(prefs.get("LDAP.SEARCH.SUBTREE"));
      String filter = "(& (objectclass=" + prefs.get("LDAP.SEARCH.ORGPERSON")
          + ") (" + searchName + "=" + searchValue + ") )";
      SearchControls cons = new SearchControls();
      cons.setSearchScope(doSearchSubtree ? SearchControls.SUBTREE_SCOPE
          : SearchControls.ONELEVEL_SCOPE);
      NamingEnumeration results = ldap.search(searchContainer, filter, cons);
      if (results.hasMore()) {
        System.out.println("LDAPUtils-> Found user with " + searchName + "= "
            + searchValue);
        SearchResult object = (SearchResult) results.next();
        String result = object.getName() + ',' + searchContainer;
        if (results.hasMore()) {
          System.out.println("LDAPUtils-> Several users with same "
              + searchName + "= " + searchValue);
          return null;
        } else {
          return result;
        }
      } else {
        System.out.println("LDAPUtils-> Didn't find user with " + searchName
            + "= " + searchValue);
      }
    } catch (Throwable t) {
      System.out.println("LDAPUtils-> Search in LDAP failed: " + t);
    }
    try {
      if (ldap != null) {
        ldap.close();
      }
    } catch (Exception e) {
    } finally {
      ldap = null;
    }
    return null;
  }

  // @return true if authenticated OK, false otherwise
  private static boolean authenticateLDAP(ApplicationPrefs prefs,
      String userDN, String password) {
    boolean result = false;
    DirContext ldap = null;
    try {
      final Hashtable env = new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY, prefs.get("LDAP.FACTORY"));
      env.put(Context.SECURITY_AUTHENTICATION, "simple");
      env.put(Context.SECURITY_PRINCIPAL, userDN);
      env.put(Context.SECURITY_CREDENTIALS, password);
      env.put(Context.PROVIDER_URL, prefs.get("LDAP.SERVER"));
      ldap = new InitialDirContext(env);
      result = true;
    } catch (Throwable t) {
      result = false;
      System.out.println("LDAPUtils-> LDAP authentication failed: " + t);
    } finally {
      try {
        ldap.close();
      } catch (Throwable t) {
      }
      ldap = null;
      System.out.println("LDAPUtils-> Authentication to LDAP - result: "
          + result);
    }
    return result;
  }

  // Check for apostrophes, parenthesis and other special characters which could
  // be unsafe to put into LDAP query.
  // @return true if value can be passed to LDAP safely, false otherwise
  public static boolean isLdapString(String value) {
    for (int i = 0; i < value.length(); i++) {
      final char c = value.charAt(i);
      if (c == '(' || c == ')' || c == '&' || c == '\'') {
        return false;
      }
      if (c == '@' || c == '-' || c == '+' || c == '.') {
        continue;
      }
      if ('0' <= c && c <= '9') {
        continue;
      }
      if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || c == ' ') {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }
}
