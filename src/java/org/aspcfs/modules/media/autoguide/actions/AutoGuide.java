package com.darkhorseventures.autoguide.module;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.cfsbase.Organization;
import com.darkhorseventures.cfsbase.Constants;
import com.darkhorseventures.cfsmodule.CFSModule;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.autoguide.base.*;
import java.util.ArrayList;
import java.util.HashMap;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;
import javax.imageio.ImageReader;
import java.awt.Image;


/**
 *  Description of the Class
 *
 *@author     matt
 *@created    April 30, 2002
 *@version    $Id$
 */
public final class AutoGuide extends CFSModule {

  /**
   *  Default command --> goes to the List command
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return executeCommandList(context);
  }


  /**
   *  Stand-alone view of the vehicle list
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandList(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    PagedListInfo autoGuideDirectoryInfo = this.getPagedListInfo(context, "AutoGuideDirectoryInfo");
    autoGuideDirectoryInfo.setLink("AutoGuide.do?command=List");

    Connection db = null;
    InventoryList inventoryList = new InventoryList();
    try {
      db = this.getConnection(context);
      //inventoryList.setPagedListInfo(autoGuideDirectoryInfo);
      inventoryList.setBuildOrganizationInfo(true);
      inventoryList.setBuildPictureId(true);
      inventoryList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Auto Guide", "List");
    if (errorMessage == null) {
      context.getRequest().setAttribute("InventoryList", inventoryList);
      if ("slides".equals(autoGuideDirectoryInfo.getListView())) {
        return ("List2OK");
      } else {
        return ("ListOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Stand-alone view of the vehicle details
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    Connection db = null;
    try {
      int id = Integer.parseInt((String) context.getRequest().getParameter("id"));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoGuide-> Details for ID: " + id);
      }
      db = this.getConnection(context);
      Inventory inventoryItem = new Inventory(db, id);
      context.getRequest().setAttribute("InventoryItem", inventoryItem);
      
      String orgId = context.getRequest().getParameter("orgId");
      if (orgId == null) {
        addModuleBean(context, "Auto Guide", "Details");
      } else {
        addModuleBean(context, "View Accounts", "Vehicle Details");
        populateOrganization(context, db, Integer.parseInt(orgId));
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    
    if (errorMessage == null) {
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Stand-alone action to delete a vehicle and return to the list
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDelete(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    Connection db = null;
    try {
      int id = Integer.parseInt((String) context.getRequest().getParameter("id"));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoGuide-> Delete ID: " + id);
      }
      db = this.getConnection(context);
      Inventory inventoryItem = new Inventory(db, id);
      inventoryItem.delete(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Auto Guide", "Delete");
    if (errorMessage == null) {
      return ("DeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Integrated view of the vehicle list under Accounts module
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAccountList(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    PagedListInfo autoGuideAccountInfo = this.getPagedListInfo(context, "AutoGuideAccountInfo");
    autoGuideAccountInfo.setLink("AccountsAutoGuide.do?command=AccountList");

    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    Connection db = null;
    InventoryList inventoryList = new InventoryList();
    try {
      db = this.getConnection(context);
      populateOrganization(context, db, orgId);
      //inventoryList.setPagedListInfo(autoGuideAccountInfo);
      inventoryList.setOrgId(orgId);
      inventoryList.setBuildOrganizationInfo(false);
      inventoryList.setBuildPictureId(true);
      inventoryList.buildList(db);
      context.getRequest().setAttribute("InventoryList", inventoryList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "Auto Guide", "List");
    if (errorMessage == null) {
      if ("slides".equals(autoGuideAccountInfo.getListView())) {
        return ("List2OK");
      } else {
        return ("ListOK");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandAccountAdd(ActionContext context) {
    if (!(hasPermission(context, "autoguide-inventory-add"))) {
	    return ("PermissionError");
    }
    Exception errorMessage = null;

    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      populateOrganization(context, db, orgId);
      populateYearSelect(context, db);
      populateMakeSelect(context, db, null);
      populateModelSelect(context, db, null);
      populateOptionList(context, db);
      
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      addModuleBean(context, "View Accounts", "Add Vehicle");
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandAccountInsert(ActionContext context) {
/*     if (!(hasPermission(context, "contacts-external_contacts-add"))) {
      return ("PermissionError");
    }
 */
    Exception errorMessage = null;
    boolean recordInserted = false;

    Inventory thisItem = (Inventory)context.getFormBean();
    thisItem.setRequestItems(context.getRequest());
    thisItem.setEnteredBy(getUserId(context));
    thisItem.setModifiedBy(getUserId(context));
    
    Connection db = null;
    try {
      db = this.getConnection(context);
      thisItem.generateVehicleId(db);
      recordInserted = thisItem.insert(db);
      if (recordInserted) {
        thisItem = new Inventory(db, thisItem.getId());
        context.getRequest().setAttribute("InventoryDetails", thisItem);
        //addRecentItem(context, thisItem);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Add Vehicle");
    if (errorMessage == null) {
      if (recordInserted) {
        return ("InsertOK");
      } else {
        //processErrors(context, thisItem.getErrors());
        return (executeCommandAccountAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandUpdateMakeList(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      String year = context.getRequest().getParameter("year");
      db = this.getConnection(context);
      MakeList makeList = new MakeList();
      if (year != null) {
        makeList.setYear(year);
      }
      makeList.buildList(db);
      context.getRequest().setAttribute("MakeList", makeList);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("MakeListOK");
  }
  
  public String executeCommandUpdateModelList(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    try {
      int makeId = Integer.parseInt(context.getRequest().getParameter("makeId"));
      String year = context.getRequest().getParameter("year");
      db = this.getConnection(context);
      ModelList modelList = new ModelList();
      if (makeId > -1) {
        modelList.setMakeId(makeId);
        if (year != null) {
          modelList.setYear(year);
        }
        modelList.buildList(db);
      }
      context.getRequest().setAttribute("ModelList", modelList);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    return ("ModelListOK");
  }
  
  public String executeCommandAccountModify(ActionContext context) {
/*     if (!(hasPermission(context, "contacts-external_contacts-edit"))) {
      return ("PermissionError");
    }
 */
    Exception errorMessage = null;
    String orgId = context.getRequest().getParameter("orgId");
    String id = context.getRequest().getParameter("id");
    
    Connection db = null;
    try {
      db = this.getConnection(context);
      
      Inventory thisItem = new Inventory(db, Integer.parseInt(id));
      context.getRequest().setAttribute("InventoryDetails", thisItem);
      
      populateOrganization(context, db, Integer.parseInt(orgId));
      populateYearSelect(context, db);
      populateMakeSelect(context, db, thisItem.getVehicle());
      populateModelSelect(context, db, thisItem.getVehicle());
      populateOptionList(context, db);
      
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Modify Vehicle");
    if (errorMessage == null) {
      return ("ModifyOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandAccountUpdate(ActionContext context) {
/*     if (!(hasPermission(context, "contacts-external_contacts-edit"))) {
      return ("PermissionError");
    }
 */
    Exception errorMessage = null;
    Connection db = null;
    
    String orgid = context.getRequest().getParameter("orgId");
    int resultCount = 0;
    
    try {
      Inventory thisItem = (Inventory)context.getFormBean();
      thisItem.setRequestItems(context.getRequest());
      thisItem.setModifiedBy(getUserId(context));
      
      db = this.getConnection(context);
      thisItem.generateVehicleId(db);
      resultCount = thisItem.update(db);
      if (resultCount == -1) {
        //processErrors(context, thisItem.getErrors());
        populateOrganization(context, db, Integer.parseInt(orgid));
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (resultCount == -1) {
        return ("ModifyOK");
      } else if (resultCount == 1) {
        return ("UpdateOK");
      } else {
        context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
        return ("UserError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandUploadForm(ActionContext context) {

    /*
     *  if (!(hasPermission(context, "autoguide-inventory-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;

    Connection db = null;
    try {
      int id = Integer.parseInt((String) context.getRequest().getParameter("id"));
      db = this.getConnection(context);
      Inventory inventoryItem = new Inventory(db, id);
      context.getRequest().setAttribute("InventoryItem", inventoryItem);
      
      addModuleBean(context, "Auto Guide", "Photo Upload");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    
    if (errorMessage == null) {
      return ("UploadFormOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandUpload(ActionContext context) {
/* 	  
    if (!(hasPermission(context, "accounts-accounts-documents-add"))) {
	    return ("PermissionError");
    }
*/	
    Exception errorMessage = null;
    Connection db = null;

    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "autoguide");
      
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      
      HashMap parts = multiPart.parseData(
        context.getRequest().getInputStream(), "---------------------------", filePath);
      
      String id = (String)parts.get("id");
      
      //Update the database with the resulting file
      db = getConnection(context);
      
      FileItemList previousFiles = new FileItemList();
      previousFiles.setLinkModuleId(Constants.AUTOGUIDE);
      previousFiles.setLinkItemId(Integer.parseInt(id));
      previousFiles.buildList(db);
      previousFiles.delete(db, filePath);
      
      FileInfo newFileInfo = (FileInfo)parts.get("id" + id);
      FileItem thisItem = new FileItem();
      thisItem.setLinkModuleId(Constants.AUTOGUIDE);
      thisItem.setLinkItemId(Integer.parseInt(id));
      thisItem.setEnteredBy(getUserId(context));
      thisItem.setModifiedBy(getUserId(context));
      //thisItem.setFolderId(Integer.parseInt(folderId));
      thisItem.setSubject("vehicle photo");
      thisItem.setClientFilename(newFileInfo.getClientFileName());
      thisItem.setFilename(newFileInfo.getRealFilename());
      thisItem.setVersion(1.0);
      thisItem.setSize(newFileInfo.getSize());
      recordInserted = thisItem.insert(db);
      if (!recordInserted) {
        processErrors(context, thisItem.getErrors());
      } else {
        //TODO: Create a thumbnail
        //Image image = new ImageReader(newFileInfo.getRealFilename()).image;
        
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return ("PopupCloseOK");
      } else {
        return ("SystemError");
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandShowImage(ActionContext context) {
    Exception errorMessage = null;
    String linkId = (String)context.getRequest().getParameter("id");
    String itemId = (String)context.getRequest().getParameter("fid");
    String version = (String)context.getRequest().getParameter("ver");
    FileItem thisItem = null;
    
    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId), Integer.parseInt(linkId), Constants.AUTOGUIDE);
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
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
      String filePath = this.getPath(context, "autoguide") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();
      
      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        String imageType = "gif"; //TODO: do not hard code this
        fileDownload.sendFile(context, "image/" + imageType);
      } else {
        System.err.println("LeadsDocuments-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
			if (System.getProperty("DEBUG") != null) System.out.println(se.toString());
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    }
    
    return ("-none-");
  }
  
  private void populateOrganization(ActionContext context, Connection db, int orgId) throws SQLException {
    Organization thisOrganization = new Organization(db, orgId);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }
  
  private void populateYearSelect(ActionContext context, Connection db) throws SQLException {
    ArrayList yearList = VehicleList.buildYearList(db); 
    HtmlSelect yearSelect = new HtmlSelect(yearList);
    yearSelect.addItem(-1, "--None--", 0);
    context.getRequest().setAttribute("YearSelect", yearSelect);
  }
  
  private void populateMakeSelect(ActionContext context, Connection db, Vehicle thisVehicle) throws SQLException {
    HtmlSelect makeSelect = new HtmlSelect();
    makeSelect.addItem(-1, "--None--");
    MakeList makeList = new MakeList();
    if (thisVehicle != null) {
      makeList.setYear(thisVehicle.getYear());
    }
    PreparedStatement pst = null;
    ResultSet rs = makeList.queryList(db, pst);
    while (rs.next()) {
      Make thisMake = makeList.getObject(rs);
      makeSelect.addItem(thisMake.getId(), thisMake.getName());
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
    context.getRequest().setAttribute("MakeSelect", makeSelect);
  }
  
  private void populateModelSelect(ActionContext context, Connection db, Vehicle thisVehicle) throws SQLException {
    HtmlSelect modelSelect = new HtmlSelect();
    modelSelect.addItem(-1, "--None--");
    ModelList modelList = new ModelList();
    if (thisVehicle != null) {
      PreparedStatement pst = null;
      modelList.setYear(thisVehicle.getYear());
      modelList.setMakeId(thisVehicle.getMakeId());
      ResultSet rs = modelList.queryList(db, pst);
      while (rs.next()) {
        Model thisModel = modelList.getObject(rs);
        modelSelect.addItem(thisModel.getId(), thisModel.getName());
      }
      rs.close();
      if (pst != null) {
        pst.close();
      }
    }
    context.getRequest().setAttribute("ModelSelect", modelSelect);
  }

  private void populateOptionList(ActionContext context, Connection db) throws SQLException {
    OptionList options = new OptionList(db);
    context.getRequest().setAttribute("OptionList", options);
  }
}  
