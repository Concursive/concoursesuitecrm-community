package org.aspcfs.modules.pipeline.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.*;
import com.zeroio.iteam.actions.*;

public final class LeadsDocumentsFolders extends CFSModule {

  public String executeCommandAdd(ActionContext context) {
    Exception errorMessage = null;
    //Load project
    Connection db = null;
    try {
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      thisFolder.setParentId(context.getRequest().getParameter("parentId"));
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      //Build array of folder trails
      ProjectManagementFileFolders.buildHierarchy(db, context);
      context.getRequest().setAttribute("fileFolder", thisFolder);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  public String executeCommandSave(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    int resultCount = 0;
    boolean recordInserted = false;
    try {
      db = this.getConnection(context);
      int opportunityId = addOpportunity(context, db);
      //Insert or update the folder
      FileFolder thisFolder = (FileFolder) context.getFormBean();
      boolean newFolder = (thisFolder.getId() == -1);
      if (newFolder) {
        thisFolder.setEnteredBy(getUserId(context));
      }
      thisFolder.setModifiedBy(getUserId(context));
      thisFolder.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
      thisFolder.setLinkItemId(opportunityId);
      if (newFolder) {
        recordInserted = thisFolder.insert(db);
      } else {
        resultCount = thisFolder.update(db);
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


  public String executeCommandDelete(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    //Delete the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      //Load the file folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
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


  public String executeCommandModify(ActionContext context) {
    Exception errorMessage = null;
    boolean recordDeleted = false;
    //TODO: Add some permissions to get here!
    //Modify the itemId, and the folderId will be the location to return to
    String itemId = (String) context.getRequest().getParameter("id");
    String folderId = (String) context.getRequest().getParameter("folderId");
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
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


  public String executeCommandMove(ActionContext context) {
    //Parameters
    String itemId = (String) context.getRequest().getParameter("id");
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      //Load the folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      context.getRequest().setAttribute("FileFolder", thisFolder);
      //Load the current folders
      FileFolderHierarchy hierarchy = new FileFolderHierarchy();
      hierarchy.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
      hierarchy.setLinkItemId(opportunityId);
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


  public String executeCommandSaveMove(ActionContext context) {
    //Parameters
    String newFolderId = (String) context.getRequest().getParameter("folderId");
    String itemId = (String) context.getRequest().getParameter("id");
    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      //Load the current folder
      FileFolder thisFolder = new FileFolder(db, Integer.parseInt(itemId));
      int folderId = Integer.parseInt(newFolderId);
      if (folderId != 0 && folderId != -1) {
        FileFolder newParent = new FileFolder(db, folderId);
        FileFolderHierarchy thisHierarchy = new FileFolderHierarchy();
        thisHierarchy.setLinkModuleId(Constants.DOCUMENTS_OPPORTUNITIES);
        thisHierarchy.setLinkItemId(opportunityId);
        thisHierarchy.build(db, thisFolder.getId());
        if (thisHierarchy.getHierarchy().hasFolder(Integer.parseInt(newFolderId))) {
          thisFolder.buildSubFolders(db);
          Iterator iterator = (Iterator) thisFolder.getSubFolders().iterator();
          while (iterator.hasNext()) {
            FileFolder childFolder = (FileFolder) iterator.next();
            childFolder.updateParentId(db, thisFolder.getParentId());
          }
        }
      }
      thisFolder.updateParentId(db, folderId);
      return "PopupCloseOK";
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
  }


  private int addOpportunity(ActionContext context, Connection db) throws SQLException {
    String opportunityId = (String) context.getRequest().getParameter("headerId");
    if (opportunityId == null) {
      opportunityId = (String) context.getRequest().getAttribute("headerId");
    }
    return addOpportunity(context, db, opportunityId);
  }


  private int addOpportunity(ActionContext context, Connection db, String opportunityId) throws SQLException {
    context.getRequest().setAttribute("headerId", opportunityId);
    OpportunityHeader thisOpportunity = new OpportunityHeader(db, opportunityId);
    context.getRequest().setAttribute("opportunityHeader", thisOpportunity);
    return thisOpportunity.getId();
  }

}

