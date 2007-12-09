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
package org.aspcfs.taglib;

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.utils.web.LookupList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author kailash
 * @version $Id$
 * @created
 */
public class DocumentsPermissionHandler extends TagSupport implements TryCatchFinally {

  private String permission = null;
  private String includeIf = "all";

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    permission = null;
    includeIf = "all";
  }


  /**
   * Sets the permission attribute of the PermissionHandler object
   *
   * @param tmp The new permission value
   */
  public void setName(String tmp) {
    this.permission = tmp;
  }


  /**
   * Sets the if attribute of the PermissionHandler object
   *
   * @param tmp The new if value
   */
  public void setIf(String tmp) {
    this.includeIf = tmp;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      //Get team and document store info
      DocumentStoreTeamMember thisMember = (DocumentStoreTeamMember) pageContext.getRequest().getAttribute(
          "currentMember");
      DocumentStore thisDocumentStore = (DocumentStore) pageContext.getRequest().getAttribute(
          "documentStore");
      if (thisMember == null || thisDocumentStore == null) {
        return SKIP_BODY;
      }
      //Return the status of the permission
      if (thisMember.getRoleId() == DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER && "all".equals(
          includeIf)) {
        return EVAL_BODY_INCLUDE;
      }
      boolean doCheck = true;
      int code = -1;
      int roleId = -1;
      //Multiple permissions to check
      StringTokenizer st = new StringTokenizer(permission, ",");
      while (st.hasMoreTokens() || doCheck) {
        doCheck = false;
        if (st.hasMoreTokens()) {
          code = thisDocumentStore.getAccessUserLevel(st.nextToken());
        } else {
          code = thisDocumentStore.getAccessUserLevel(permission);
        }
        roleId = getRoleId(code);
        if (code == -1 || roleId == -1) {
          return SKIP_BODY;
        }
        if ("none".equals(includeIf)) {
          if (thisMember.getRoleId() <= roleId) {
            return SKIP_BODY;
          }
        } else if ("any".equals(includeIf)) {
          if (thisMember.getRoleId() <= roleId) {
            return EVAL_BODY_INCLUDE;
          }
        } else {
          if (thisMember.getRoleId() > roleId) {
            return SKIP_BODY;
          }
        }
      }
      //If the above didn't trigger, then go with the default
      if ("none".equals(includeIf)) {
        return EVAL_BODY_INCLUDE;
      } else if ("any".equals(includeIf)) {
        return SKIP_BODY;
      } else {
        return EVAL_BODY_INCLUDE;
      }
    } catch (Exception e) {
      System.out.println("Permission Error: " + e.getMessage());
      return SKIP_BODY;
    }
  }


  /**
   * Gets the roleId attribute of the PermissionHandler object
   *
   * @param userlevel Description of the Parameter
   * @return The roleId value
   */
  protected int getRoleId(int userlevel) {
    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
        "ConnectionElement");
    if (ce != null) {
      Hashtable systemStatus = (Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus");
      if (systemStatus != null) {
        SystemStatus thisSystem = (SystemStatus) systemStatus.get(ce.getUrl());
        if (thisSystem != null) {
          // NOTE: Lookup list must already exist in system status, which it does at system startup
          Connection db = null;
          try {
            LookupList roleList = thisSystem.getLookupList(
                db, "lookup_document_store_role");
            if (roleList != null) {
              return roleList.getLevelFromId(userlevel);
            }
          } catch (Exception e) {
            return -1;
          }
        }
      }
    }
    return -1;
  }
}

