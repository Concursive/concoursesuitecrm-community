/*
 *  Copyright 2001 Dark Horse Ventures
 *  Uses iteam objects from matt@zeroio.com http://www.mavininteractive.com
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.Vector;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;

/**
 *  Project Management module for CFS
 *
 *@author     matt
 *@created    November 12, 2001
 *@version    $Id$
 */
public final class ProjectManagementRequirements extends CFSModule {

  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    
    String projectId = (String)context.getRequest().getParameter("pid");
    
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("requirements_add").toLowerCase());
      
      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "AddItem", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandInsert(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    
    String projectId = (String)context.getRequest().getParameter("pid");
    
    boolean recordInserted = false;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("requirements_add").toLowerCase());
      
      Requirement thisRequirement = (Requirement)context.getFormBean();
      thisRequirement.setProjectId(thisProject.getId());
      thisRequirement.setEnteredBy(getUserId(context));
      thisRequirement.setModifiedBy(getUserId(context));
      recordInserted = thisRequirement.insert(db);
      if (!recordInserted) {
        processErrors(context, thisRequirement.getErrors());
      } else {
        context.getRequest().setAttribute("pid", projectId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("AddOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    
    String projectId = (String)context.getRequest().getParameter("pid");
    String requirementId = (String)context.getRequest().getParameter("rid");
    
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("requirements_details").toLowerCase());
    
      Requirement thisRequirement = new Requirement(db, Integer.parseInt(requirementId), thisProject.getId());
      context.getRequest().setAttribute("Requirement", thisRequirement);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Details", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    
    String projectId = (String)context.getRequest().getParameter("pid");
    String requirementId = (String)context.getRequest().getParameter("rid");
    
    Connection db = null;
    try {
      db = getConnection(context);
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("requirements_modify").toLowerCase());
    
      Requirement thisRequirement = new Requirement(db, Integer.parseInt(requirementId), thisProject.getId());
      context.getRequest().setAttribute("Requirement", thisRequirement);
      
      LookupList loeList = new LookupList(db, "lookup_project_loe");
      context.getRequest().setAttribute("LoeList", loeList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Details", "");
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  
  public String executeCommandUpdate(ActionContext context) {
    Exception errorMessage = null;

    Requirement thisRequirement = (Requirement)context.getFormBean();
    
    Connection db = null;
    int resultCount = 0;

    try {
      db = this.getConnection(context);
      
      Project thisProject = new Project(db, thisRequirement.getProjectId(), getUserRange(context));
      
      thisRequirement.setProject(thisProject);
      thisRequirement.setProjectId(thisProject.getId());
      thisRequirement.setModifiedBy(getUserId(context));
      resultCount = thisRequirement.update(db, context);
      if (resultCount == -1) {
        processErrors(context, thisRequirement.getErrors());
        context.getRequest().setAttribute("Project", thisProject);
        context.getRequest().setAttribute("Requirement", thisRequirement);
        context.getRequest().setAttribute("IncludeSection", ("requirements_modify").toLowerCase());
      } else {
        context.getRequest().setAttribute("pid", "" + thisProject.getId());
        context.getRequest().setAttribute("IncludeSection", ("requirements").toLowerCase());
      }
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("ProjectCenterOK");
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("Error",
            "<b>This record could not be updated because someone else updated it first.</b><p>" +
            "You can hit the back button to review the changes that could not be committed, " +
            "but you must reload the record and make the changes again.");
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
}
