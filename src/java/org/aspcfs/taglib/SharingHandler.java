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
package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.contacts.base.Call;
import com.darkhorseventures.database.ConnectionElement;
import java.util.StringTokenizer;
import java.util.Hashtable;

/**
 *  Implements sharing based on objects<br>
 *  NOTES: Can be expanded to account for complex cases like Account -> Contact
 *  -> Opportunity -> Tasks <br>
 *  by allowing for multiple object passing in the tag. For example <dhv:sharing
 *  name=<CallDetails, ContactDetails, OpportunityHeader> action=<VIEW>/>
 *
 *@author     Mathur
 *@created    August 20, 2003
 *@version    $id:exp$
 */
public class SharingHandler extends TagSupport {

  //constants for actions
  public final static int VIEW = 1;
  public final static int ADD = 2;
  public final static int EDIT = 3;
  public final static int DELETE = 4;

  //attributes
  private String primaryComponent = null;
  private String secondaryComponents = null;
  private int action = 1;
  private boolean allRequired = false;
  private boolean hasNone = false;


  /**
   *  Sets the Name attribute of the PermissionHandler object
   *
   *@param  tmp  The new Name value
   *@since       1.1
   */
  public final void setPrimaryBean(String tmp) {
    primaryComponent = tmp;
  }


  /**
   *  Sets the secondaryBeans attribute of the SharingHandler object
   *
   *@param  tmp  The new secondaryBeans value
   */
  public final void setSecondaryBeans(String tmp) {
    secondaryComponents = tmp;
  }


  /**
   *  Sets the action attribute of the SharingHandler object
   *
   *@param  tmp  The new action value
   */
  public final void setAction(String tmp) {
    if ("view".equalsIgnoreCase(tmp)) {
      action = VIEW;
    } else if ("edit".equalsIgnoreCase(tmp)) {
      action = EDIT;
    } else if ("delete".equalsIgnoreCase(tmp)) {
      action = DELETE;
    }
  }


  /**
   *  Sets the All attribute of the PermissionHandler object. If set to true
   *  then the user must have all permissions passed in.
   *
   *@param  tmp  The new All value
   *@since       1.1
   */
  public void setAll(String tmp) {
    Boolean checkAll = new Boolean(tmp);
    this.allRequired = checkAll.booleanValue();
  }


  /**
   *  Sets the None attribute of the PermissionHandler object
   *
   *@param  tmp  The new None value
   *@since       1.1
   */
  public void setNone(String tmp) {
    Boolean checkNone = new Boolean(tmp);
    this.hasNone = checkNone.booleanValue();
  }


  /**
   *  Checks to see if the user has permission to access the body of the tag. A
   *  comma-separated list of permissions can be used to match any of the
   *  permissions.
   *
   *@return                   Description of the Returned Value
   *@exception  JspException  Description of Exception
   *@since                    1.1
   */
  public final int doStartTag() throws JspException {
    boolean result = false;
    Object obj = null;
    if (primaryComponent != null && !"".equals(primaryComponent)) {
      obj = pageContext.getRequest().getAttribute(primaryComponent);
    }

    result = hasPermission(createPermission(obj));

    if (result) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }


  /**
   *  Creates permission based on the Business Object<br>
   *  Can be expanded to use for other Business Objects like Opportunities as
   *  well
   *
   *@param  obj  Description of the Parameter
   *@return      Description of the Return Value
   */
  public final String createPermission(Object obj) {
    String permission = null;

    //Inspect the object
    if (obj instanceof Contact) {
      Contact thisContact = (Contact) obj;
      if (thisContact.getOrgId() > 0) {
        allRequired = true;
      }
      //handle contact type cases(account and general)
      switch (action) {
          case VIEW:
            if (thisContact.getOrgId() > 0) {
              permission = "contacts-external_contacts-view,accounts-accounts-contacts-view";
            } else {
              permission = "contacts-external_contacts-view";
            }
            break;
          case EDIT:
            if (thisContact.getOrgId() > 0) {
              permission = "contacts-external_contacts-edit,accounts-accounts-contacts-edit";
            } else {
              permission = "contacts-external_contacts-edit";
            }
            break;
          case DELETE:
            if (thisContact.getOrgId() > 0) {
              permission = "contacts-external_contacts-delete,accounts-accounts-contacts-delete";
            } else {
              permission = "contacts-external_contacts-delete";
            }
            break;
          default:
            break;
      }
    }

    //Call
    if (obj instanceof Call) {
      Call thisCall = (Call) obj;
      Contact thisContact = null;
      //get required components to validate permission
      //NOTE: Required secondary components needed for validating a permission have to be passed
      StringTokenizer st = new StringTokenizer(secondaryComponents, ",");
      while (st.hasMoreTokens()) {
        String thisObj = st.nextToken();
        Object tmp = pageContext.getRequest().getAttribute(thisObj);
        if (tmp instanceof Contact) {
          thisContact = (Contact) tmp;
        }
      }

      //handle call types
      switch (action) {
          case VIEW:
            if (thisCall.getContactId() > 0) {
              //Contact Calls
              if (thisContact != null) {
                if (thisContact.getOrgId() > -1) {
                  //Account Contact Calls
                  permission = "contacts-external_contacts-calls-view,accounts-accounts-contacts-calls-view";
                  allRequired = true;
                } else {
                  //General Contact Call
                  permission = "contacts-external_contacts-calls-view";
                }
              }
            } else if (thisCall.getOrgId() > 0) {
              //Account Calls
            } else if (thisCall.getOppHeaderId() > 0) {
              //Opportunity Calls
            }
            break;
          case EDIT:
            if (thisCall.getContactId() > 0) {
              //Contact Calls
              if (thisContact != null) {
                if (thisContact.getOrgId() > -1) {
                  //Account Contact Calls
                  permission = "contacts-external_contacts-calls-edit,accounts-accounts-contacts-calls-edit";
                  allRequired = true;
                } else {
                  //General Contact Call
                  permission = "contacts-external_contacts-calls-edit";
                }
              }
            } else if (thisCall.getOrgId() > 0) {
              //Account Calls
            } else if (thisCall.getOppHeaderId() > 0) {
              //Opportunity Calls
            }
            break;
          case DELETE:
            if (thisCall.getContactId() > 0) {
              //Contact Calls
              if (thisContact != null) {
                if (thisContact.getOrgId() > -1) {
                  //Account Contact Calls
                  permission = "contacts-external_contacts-calls-delete,accounts-accounts-contacts-calls-delete";
                  allRequired = true;
                } else {
                  //General Contact Call
                  permission = "contacts-external_contacts-calls-delete";
                }
              }
            } else if (thisCall.getOrgId() > 0) {
              //Account Calls
            } else if (thisCall.getOppHeaderId() > 0) {
              //Opportunity Calls
            }
            break;
          default:
            break;
      }
    }

    //Opportunity
    if (obj instanceof OpportunityHeader) {
      OpportunityHeader thisOpp = (OpportunityHeader) obj;
      Contact thisContact = null;
      //get required components to validate permission
      //NOTE: Required secondary components needed for validating a permission have to be passed
      StringTokenizer st = new StringTokenizer(secondaryComponents, ",");
      while (st.hasMoreTokens()) {
        String thisObj = st.nextToken();
        Object tmp = pageContext.getRequest().getAttribute(thisObj);
        if (tmp instanceof Contact) {
          thisContact = (Contact) tmp;
        }
      }

      //handle opportunity types
      switch (action) {
          case VIEW:
            if (thisOpp.getContactLink() > 0) {
              //Contact Opportunity
              if (thisContact != null) {
                if (thisContact.getOrgId() > -1) {
                  //Account Contact Opportunity
                  permission = "contacts-external_contacts-opportunities-view,accounts-accounts-contacts-opportunities-view";
                  allRequired = true;
                } else {
                  //General Contact Opportunity
                  permission = "contacts-external_contacts-opportunities-view";
                }
              }
            } else if (thisOpp.getAccountLink() > 0) {
              //Account Opportunities
            }
            break;
          case EDIT:
            if (thisOpp.getContactLink() > 0) {
              //Contact Calls
              if (thisContact != null) {
                if (thisContact.getOrgId() > -1) {
                  //Account Contact Calls
                  permission = "contacts-external_contacts-opportunities-edit,accounts-accounts-contacts-opportunities-edit";
                  allRequired = true;
                } else {
                  //General Contact Call
                  permission = "contacts-external_contacts-opportunities-edit";
                }
              } else if (thisOpp.getAccountLink() > 0) {
                //Account Opportunities
              }
            }
            break;
          case DELETE:
            if (thisOpp.getContactLink() > 0) {
              //Contact Opportunity
              if (thisContact != null) {
                if (thisContact.getOrgId() > -1) {
                  //Account Contact Opportunity
                  permission = "contacts-external_contacts-opportunities-delete,accounts-accounts-contacts-opportunities-delete";
                  allRequired = true;
                } else {
                  //General Contact Opportunity
                  permission = "contacts-external_contacts-opportunities-delete";
                }
              } else if (thisOpp.getAccountLink() > 0) {
                //Account Opportunity
              }
            }
            break;
          default:
            break;
      }
    }
    return permission;
  }


  /**
   *  Checks to see if the user has the specified permissions
   *
   *@param  permissionName  Description of the Parameter
   *@return                 Description of the Return Value
   */
  public final boolean hasPermission(String permissionName) {
    int matches = 0;
    int checks = 0;
    boolean result = false;
    UserBean thisUser = (UserBean) pageContext.getSession().getAttribute("User");
    if (thisUser != null) {
      ConnectionElement ce = thisUser.getConnectionElement();
      SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      StringTokenizer st = new StringTokenizer(permissionName, ",");
      while (st.hasMoreTokens()) {
        String thisPermission = st.nextToken();
        ++checks;
        if (systemStatus.hasPermission(thisUser.getUserId(), thisPermission.trim())) {
          ++matches;
        }
      }
      if ((allRequired && matches > 0 && matches == checks) ||
          (!allRequired && matches > 0)) {
        result = true;
      }
    }

    //The request wants to know if the user does not have the permissions
    if (hasNone) {
      result = !result;
    }

    return result;
  }

}


