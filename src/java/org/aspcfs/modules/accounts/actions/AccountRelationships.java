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
package org.aspcfs.modules.accounts.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.relationships.base.Relationship;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.relationships.base.RelationshipTypeList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  Actions for Account Relationships
 *
 *@author     Mathur
 *@created    August 11, 2004
 *@version    $id:exp$
 */
public final class AccountRelationships extends CFSModule {
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandView(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-relationships-view")) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);

      //add account to the request
      Organization thisOrg = addFormElements(context, db);
      
      //build the relationship list(both from and to mappings)
      RelationshipList thisList = new RelationshipList();
      thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisList.setObjectIdMapsFrom(thisOrg.getOrgId());
      thisList.setBuildDualMappings(true);
      thisList.buildList(db);
      context.getRequest().setAttribute("relationshipList", thisList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "View");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-relationships-add")) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      
      //add account to the request
      addFormElements(context, db);
      
      RelationshipTypeList typeList = new RelationshipTypeList();
      typeList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      typeList.buildList(db);
      context.getRequest().setAttribute("TypeList", typeList);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Add");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-relationships-edit")) {
      return ("PermissionError");
    }

    Connection db = null;
    try {
      db = this.getConnection(context);
      
      //add account to the request
      addFormElements(context, db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    String permission = "accounts-accounts-relationships-add";
    HashMap errors = new HashMap();
    boolean recordInserted = false;
    int resultCount = -1;
    String relTypeId = context.getRequest().getParameter("relTypeId");
    int typeId = -1;
    boolean isValid = false;
    Relationship thisRelationship = (Relationship) context.getFormBean();
    thisRelationship.setModifiedBy(getUserId(context));
    if (thisRelationship.getId() > 0) {
      permission = "accounts-accounts-relationships-edit";
    }
    if (!(hasPermission(context, permission))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      //add account to the request
      Organization thisOrg = addFormElements(context, db);
      
      thisRelationship.setModifiedBy(getUserId(context));
      if(relTypeId.endsWith("_reciprocal")){
        //user chose reciprocal relation, so switch the direction to maintain order
        thisRelationship.setObjectIdMapsFrom(thisRelationship.getObjectIdMapsTo());
        thisRelationship.setObjectIdMapsTo(thisOrg.getOrgId());
        typeId = Integer.parseInt(relTypeId.substring(0, relTypeId.indexOf("_")));
      }else{
        typeId = Integer.parseInt(relTypeId);
      }
       
      thisRelationship.setTypeId(typeId);
      thisRelationship.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisRelationship.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);

      //build relationshipList to check for duplicates
      RelationshipList thisList = new RelationshipList();
      thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisList.setObjectIdMapsFrom(thisRelationship.getObjectIdMapsFrom());
      thisList.setTypeId(typeId);
      thisList.buildList(db);
      isValid = this.validateObject(context, db, thisRelationship);
      if (isValid) {
        if (thisRelationship.getId() > 0) {
        //TODO:implement modify
          if (thisList.checkDuplicateRelationship(db, thisRelationship.getObjectIdMapsTo(), typeId, Constants.ACCOUNT_OBJECT) == 1) {
            resultCount = thisRelationship.update(db);
          } else {
            SystemStatus systemStatus = this.getSystemStatus(context);
            errors.put("actionError", systemStatus.getLabel("object.validation.actionError.relationshipDuplicate"));
          }
        } else {
          thisRelationship.setEnteredBy(this.getUserId(context));
          if (thisList.checkDuplicateRelationship(db, thisRelationship.getObjectIdMapsTo(), typeId, Constants.ACCOUNT_OBJECT) == 0) {
            recordInserted = thisRelationship.insert(db);
          } else {
            SystemStatus systemStatus = this.getSystemStatus(context);
            errors.put("actionError", systemStatus.getLabel("object.validation.actionError.relationshipDuplicate"));
          }
        }
      }
      if (!(recordInserted || resultCount == 1)) {
        if(relTypeId.endsWith("_reciprocal")){
          if (thisRelationship.getObjectIdMapsFrom() != -1) {
            Organization secondOrganization = new Organization(db, thisRelationship.getObjectIdMapsFrom());
            context.getRequest().setAttribute("secondOrganization", secondOrganization);
          }
        } else {
          if (thisRelationship.getObjectIdMapsTo() != -1) {
            Organization secondOrganization = new Organization(db, thisRelationship.getObjectIdMapsTo());
            context.getRequest().setAttribute("secondOrganization", secondOrganization);
          }
        }
        RelationshipTypeList typeList = new RelationshipTypeList();
        typeList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
        typeList.buildList(db);
        context.getRequest().setAttribute("TypeList", typeList);
        context.getRequest().setAttribute("relationship", thisRelationship);
        if (errors.size() > 0) {
          processErrors(context, errors);
        }
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (isValid && (recordInserted || resultCount == 1)) {
      return getReturn(context, "Save");
    }
    if (thisRelationship.getId() > 0) {
      return getReturn(context, "Modify");
    }
    return getReturn(context, "Add");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    if (!hasPermission(context, "accounts-accounts-relationships-delete")) {
      return ("PermissionError");
    }
    SystemStatus systemStatus = this.getSystemStatus(context);
    String relId = context.getRequest().getParameter("id");
    boolean recordDeleted = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      //add account to the request
      addFormElements(context, db);
      
      Relationship thisRelation = new Relationship(db, Integer.parseInt(relId));
      recordDeleted = thisRelation.delete(db);
      if (!recordDeleted) {
        thisRelation.getErrors().put("actionError", systemStatus.getLabel("object.validation.actionError.relationshipDeletion"));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "Delete");
  }
  
  
  public Organization addFormElements(ActionContext context, Connection db) throws SQLException {
    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("OrgDetails", thisOrg);
    return thisOrg;
  }
}

