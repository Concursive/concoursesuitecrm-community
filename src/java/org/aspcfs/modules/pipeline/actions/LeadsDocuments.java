/*
 *  Copyright 2002 Dark Horse Ventures
 *  Uses iteam objects from matt@zeroio.com http://www.mavininteractive.com
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;

public final class LeadsDocuments extends CFSModule {

  public String executeCommandView(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;
    
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.PIPELINE);
      documents.setLinkItemId(opportunityId);
      documents.buildList(db);
      context.getRequest().setAttribute("FileItemList", documents);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    addModuleBean(context, "View Opportunities", "View Documents");
    if (errorMessage == null) {
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandAdd(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;

    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Opportunities", "Upload Document");
    if (errorMessage == null) {
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  public String executeCommandUpload(ActionContext context) {
	  
 	if (!(hasPermission(context, "pipeline-opportunities-documents-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;

    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "opportunities");
      
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(true);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      
      Hashtable parts = multiPart.parseData(
        context.getRequest().getInputStream(), "---------------------------", filePath);
      
      String id = (String)parts.get("id");
      String subject = (String)parts.get("subject");
      String folderId = (String)parts.get("folderId");
      
      //Update the database with the resulting file
      FileInfo newFileInfo = (FileInfo)parts.get("id" + id);
      
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db, id);
      
      FileItem thisItem = new FileItem();
      thisItem.setLinkModuleId(Constants.PIPELINE);
      thisItem.setLinkItemId(opportunityId);
      thisItem.setEnteredBy(getUserId(context));
      thisItem.setModifiedBy(getUserId(context));
      thisItem.setFolderId(Integer.parseInt(folderId));
      thisItem.setSubject(subject);
      thisItem.setClientFilename(newFileInfo.getClientFileName());
      thisItem.setFilename(newFileInfo.getRealFilename());
      thisItem.setVersion(1.0);
      thisItem.setSize(newFileInfo.getSize());
      
      recordInserted = thisItem.insert(db);
      if (!recordInserted) {
        processErrors(context, thisItem.getErrors());
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("UploadOK");
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  
  public String executeCommandAddVersion(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    String itemId = (String)context.getRequest().getParameter("fid");
    if (itemId == null) { 
      itemId = (String)context.getRequest().getAttribute("fid");
    }

    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      
      FileItem thisFile = new FileItem(db, Integer.parseInt(itemId), opportunityId, Constants.PIPELINE);
      context.getRequest().setAttribute("FileItem", thisFile);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Opportunities", "Upload New Document Version");
    if (errorMessage == null) {
      return ("AddVersionOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  
  public String executeCommandUploadVersion(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-add"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;

    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "opportunities");
      
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(true);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      
      Hashtable parts = multiPart.parseData(
        context.getRequest().getInputStream(), "---------------------------", filePath);
      
      String id = (String)parts.get("id");
      String itemId = (String)parts.get("fid");
      String subject = (String)parts.get("subject");
      String versionId = (String)parts.get("versionId");
      
      //Update the database with the resulting file
      FileInfo newFileInfo = (FileInfo)parts.get("id" + id);
      
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db, id);
      
      FileItem thisItem = new FileItem();
      thisItem.setLinkModuleId(Constants.PIPELINE);
      thisItem.setLinkItemId(opportunityId);
      thisItem.setId(Integer.parseInt(itemId));
      thisItem.setEnteredBy(getUserId(context));
      thisItem.setModifiedBy(getUserId(context));
      thisItem.setSubject(subject);
      thisItem.setClientFilename(newFileInfo.getClientFileName());
      thisItem.setFilename(newFileInfo.getRealFilename());
      thisItem.setVersion(Double.parseDouble(versionId));
      thisItem.setSize(newFileInfo.getSize());
      
      recordInserted = thisItem.insertVersion(db);
      if (!recordInserted) {
        processErrors(context, thisItem.getErrors());
      }
      context.getRequest().setAttribute("fid", itemId);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("UploadOK");
      } else {
        return (executeCommandAddVersion(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  
  public String executeCommandDetails(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    Connection db = null;
    
    String itemId = (String)context.getRequest().getParameter("fid");
    
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      /*FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.PIPELINE);
      documents.setLinkItemId(opportunityId);
      documents.buildList(db);
      context.getRequest().setAttribute("FileItemList", documents);*/
      
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), opportunityId, Constants.PIPELINE);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Opportunities", "Document Details");
    if (errorMessage == null) {
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  
  public String executeCommandDownload(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-view"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    String itemId = (String)context.getRequest().getParameter("fid");
    String version = (String)context.getRequest().getParameter("ver");
    FileItem thisItem = null;
    
    Connection db = null;
    int opportunityId = -1;
    try {
      db = getConnection(context);
      opportunityId = addOpportunity(context, db);
      thisItem = new FileItem(db, Integer.parseInt(itemId), opportunityId, Constants.PIPELINE);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    //Start the download
    try {
      FileItem itemToDownload = null;
      if (version == null) {
        itemToDownload = thisItem;
      } else {
        itemToDownload = thisItem.getVersion(Double.parseDouble(version));
      }
      
      itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "opportunities", opportunityId) + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
      
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        //Get a db connection now that the download is complete
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println("LeadsDocuments-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
			if (System.getProperty("DEBUG") != null) System.out.println(se.toString());
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      if (db != null) this.freeConnection(context, db);
    }
    
    addModuleBean(context, "View Opportunities", "");
    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  public String executeCommandModify(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-edit"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;

    String itemId = (String)context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), opportunityId, Constants.PIPELINE);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("FileItem", thisItem);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Opportunities", "Modify Document Information");
    if (errorMessage == null) {
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
	
  public String executeCommandUpdate(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-edit"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    boolean recordInserted = false;

    String itemId = (String)context.getRequest().getParameter("fid");
    String subject = (String)context.getRequest().getParameter("subject");
    String filename = (String)context.getRequest().getParameter("clientFilename");

    Connection db = null;
    int opportunityId = -1;
    try {
      db = getConnection(context);
      opportunityId = addOpportunity(context, db);
      
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), opportunityId, Constants.PIPELINE);
      thisItem.setClientFilename(filename);
      thisItem.setSubject(subject);
      recordInserted = thisItem.update(db);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    addModuleBean(context, "View Opportunities", "");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("fid", itemId);
        return (executeCommandModify(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  

  public String executeCommandDelete(ActionContext context) {
	  
	if (!(hasPermission(context, "pipeline-opportunities-documents-delete"))) {
	    return ("PermissionError");
    	}
	
    Exception errorMessage = null;
    boolean recordDeleted = false;

    String itemId = (String)context.getRequest().getParameter("fid");

    Connection db = null;
    try {
      db = getConnection(context);
      int opportunityId = addOpportunity(context, db);
      
      FileItem thisItem = new FileItem(db, Integer.parseInt(itemId), opportunityId, Constants.PIPELINE);
      if (thisItem.getEnteredBy() == this.getUserId(context)) {
        recordDeleted = thisItem.delete(db, this.getPath(context, "opportunities", thisItem.getLinkItemId()));
      }

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    addModuleBean(context, "View Opportunities", "Delete Document");
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

  
  private int addOpportunity(ActionContext context, Connection db) throws SQLException {
    String opportunityId = (String)context.getRequest().getParameter("oppId");
    if (opportunityId == null) { 
      opportunityId = (String)context.getRequest().getAttribute("oppId");
    }
    return addOpportunity(context, db, opportunityId);
  }
  
  private int addOpportunity(ActionContext context, Connection db, String opportunityId) throws SQLException {
    context.getRequest().setAttribute("oppId", opportunityId);
    Opportunity thisOpportunity = new Opportunity(db, opportunityId);
    context.getRequest().setAttribute("OpportunityDetails", thisOpportunity);
    return thisOpportunity.getId();
  }

}

