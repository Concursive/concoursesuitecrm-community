package org.aspcfs.modules.accounts.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.util.Vector;
import java.text.DateFormat;
import java.io.*;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.relationships.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;

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
    return this.getReturn(context, "View");
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
    return this.getReturn(context, "Add");
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
    return this.getReturn(context, "Modify");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    String permission = "accounts-accounts-relationships-add";

    boolean recordInserted = false;
    int resultCount = -1;
    String relTypeId = context.getRequest().getParameter("relTypeId");
    int typeId = -1;
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
        thisRelationship.setCategoryIdMapsTo(thisOrg.getOrgId());
        typeId = Integer.parseInt(relTypeId.substring(0, relTypeId.indexOf("_")));
      }else{
        typeId = Integer.parseInt(relTypeId);
      }
       
      thisRelationship.setTypeId(typeId);
      thisRelationship.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
      thisRelationship.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
      if (thisRelationship.getId() > 0) {
        //TODO:implement modify
      } else {
        thisRelationship.setEnteredBy(this.getUserId(context));
        recordInserted = thisRelationship.insert(db);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordInserted || resultCount == 1) {
      return this.getReturn(context, "Save");
    }
    if (thisRelationship.getId() > 0) {
      return this.getReturn(context, "Modify");
    }
    return this.getReturn(context, "Add");
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

    String relId = context.getRequest().getParameter("id");
    boolean recordDeleted = false;
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      //add account to the request
      addFormElements(context, db);
      
      Relationship thisRelation = new Relationship(db, Integer.parseInt(relId));
      recordDeleted = thisRelation.delete(db);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return this.getReturn(context, "Delete");
  }
  
  
  public Organization addFormElements(ActionContext context, Connection db) throws SQLException {
    String orgId = context.getRequest().getParameter("orgId");
    Organization thisOrg = new Organization(db, Integer.parseInt(orgId));
    context.getRequest().setAttribute("OrgDetails", thisOrg);
    return thisOrg;
  }
}

