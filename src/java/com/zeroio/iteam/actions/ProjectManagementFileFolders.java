/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.actions;

//import org.theseus.actions.ActionContext;
import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.*;
import com.zeroio.iteam.base.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.modules.base.Constants;
/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    April 20, 2003
 *@version    $Id: ProjectManagementFileFolders.java,v 1.1.4.1 2004/07/07
 *      15:12:07 mrajkowski Exp $
 */
public final class ProjectManagementFileFolders extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    //Load project
    String projectId = (String) context.getRequest().getParameter("pid");
    Connection db = null;
    try {
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      thisFolder.setParentId(context.getRequest().getParameter("parentId"));
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-documents-folders-add")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("IncludeSection", ("file_folder").toLowerCase());
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("ProjectCenterOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    String projectId = (String) context.getRequest().getParameter("pid");
    try {
      db = this.getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      thisProject.buildPermissionList(db);
      //Insert or update the folder
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      boolean newFolder = (thisFolder.getId() == -1);
      if (newFolder) {
        thisFolder.setEnteredBy(getUserId(context));
      }
      thisFolder.setModifiedBy(getUserId(context));
      thisFolder.setLinkModuleId(Constants.PROJECTS_FILES);
      thisFolder.setLinkItemId(thisProject.getId());
      if (newFolder) {
        if (!hasProjectAccess(context, db, thisProject, "project-documents-folders-add")) {
          return "PermissionError";
        }
        recordInserted = thisFolder.insert(db);
        //indexAddItem(context, thisFolder);
      } else {
        if (!hasProjectAccess(context, db, thisProject, "project-documents-folders-edit")) {
          return "PermissionError";
        }
        resultCount = thisFolder.update(db);
        //indexAddItem(context, thisFolder);
      }
      if (!recordInserted && resultCount < 1) {
        processErrors(context, thisFolder.getErrors());
      }
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordInserted) {
        return ("InsertOK");
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String projectId = (String) context.getRequest().getParameter("pid");
    //Delete the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-documents-folders-delete")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      context.getRequest().setAttribute("pid", projectId);
      //Load the file folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      thisFolder.setParentId(Integer.parseInt(folderId));
      recordDeleted = thisFolder.delete(db);
      //indexDeleteItem(context, thisFolder);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        return ("DeleteOK");
      } else {
        return ("DeleteERROR");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    String projectId = (String) context.getRequest().getParameter("pid");
    //TODO: Add some permissions to get here!
    //Modify the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Connection db = null;
    try {
      db = getConnection(context);
      //Load the file folder to be modified
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      thisFolder.setId(Integer.parseInt(itemId));
      thisFolder.queryRecord(db, Integer.parseInt(itemId));
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return (executeCommandAdd(context));
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public final static void buildHierarchy(Connection db, ActionContext context) throws SQLException {
    String folderId = context.getRequest().getParameter("folderId");
    if (folderId != null && !"-1".equals(folderId) && !"0".equals(folderId)) {
      LinkedHashMap folderLevels = new LinkedHashMap();
      FileFolder.buildHierarchy(db, folderLevels, Integer.parseInt(folderId));
      context.getRequest().setAttribute("folderLevels", folderLevels);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandMove(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String itemId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-documents-folders-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("FileFolder", thisFolder);
      //Load the current folders
      FileFolderHierarchy hierarchy = new FileFolderHierarchy();
      hierarchy.setLinkModuleId(Constants.PROJECTS_FILES);
      hierarchy.setLinkItemId(thisProject.getId());
      hierarchy.build(db);
      context.getRequest().setAttribute("folderHierarchy", hierarchy);
      return "MoveOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveMove(ActionContext context) {
    Connection db = null;
    //Parameters
    String projectId = (String) context.getRequest().getParameter("pid");
    String newFolderId = (String) context.getRequest().getParameter("folderId");
    String itemId = (String) context.getRequest().getParameter("id");
    try {
      db = getConnection(context);
      //Load the project
      Project thisProject = new Project(db, Integer.parseInt(projectId), getUserRange(context));
      if (thisProject.getId() == -1) {
        throw new Exception("Invalid access to project");
      }
      thisProject.buildPermissionList(db);
      if (!hasProjectAccess(context, db, thisProject, "project-documents-folders-edit")) {
        return "PermissionError";
      }
      context.getRequest().setAttribute("Project", thisProject);
      //Load the current folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      thisFolder.updateParentId(db, Integer.parseInt(newFolderId));
      return "PopupCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }
}

