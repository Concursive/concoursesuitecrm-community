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
import com.darkhorseventures.utils.ImageUtils;
import com.darkhorseventures.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;

/**
 *  Auto Guide CFS Module
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
    if (!(hasPermission(context, "autoguide-adruns-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;

    PagedListInfo autoGuideDirectoryInfo = this.getPagedListInfo(context, "AutoGuideDirectoryInfo");
    autoGuideDirectoryInfo.setLink("AutoGuide.do?command=List");

    Connection db = null;
    InventoryList inventoryList = new InventoryList();
    try {
      db = this.getConnection(context);
      populateListFilters(context, autoGuideDirectoryInfo);
      populateMakeSelect(context, db, null, "All Makes");
      inventoryList.setPagedListInfo(autoGuideDirectoryInfo);
      inventoryList.setBuildOrganizationInfo(true);
      inventoryList.setBuildPictureId(true);
      inventoryList.setShowSold(autoGuideDirectoryInfo.getFilterKey("listFilter1"));
      inventoryList.setShowIncompleteAdRunsOnly(true);
      inventoryList.setShowIncompleteInventoryAds(autoGuideDirectoryInfo.getFilterKey("listFilter2"));
      inventoryList.setMakeId(autoGuideDirectoryInfo.getFilterKey("listFilter3"));
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
   *  Stand-alone AND account view of the vehicle details
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDetails(ActionContext context) {
    if (!(hasPermission(context, "autoguide-adruns-view")) &&
        !(hasPermission(context, "accounts-autoguide-inventory-view"))) {
      return ("PermissionError");
    }

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
  
  public String executeCommandMarkComplete(ActionContext context) {
    if (!(hasPermission(context, "autoguide-adruns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    try {
      int id = Integer.parseInt((String) context.getRequest().getParameter("id"));
      int adId = Integer.parseInt(context.getRequest().getParameter("adId"));
      db = this.getConnection(context);
      Inventory inventoryItem = new Inventory(db, id);
      AdRun thisAdRun = inventoryItem.getAdRuns().getAdRun(adId);
      thisAdRun.setCompletedBy(getUserId(context));
      thisAdRun.markComplete(db);
      addModuleBean(context, "Auto Guide", "Details");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("MarkCompleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandMarkIncomplete(ActionContext context) {
    if (!(hasPermission(context, "autoguide-adruns-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;
    try {
      int id = Integer.parseInt((String) context.getRequest().getParameter("id"));
      int adId = Integer.parseInt(context.getRequest().getParameter("adId"));
      db = this.getConnection(context);
      Inventory inventoryItem = new Inventory(db, id);
      AdRun thisAdRun = inventoryItem.getAdRuns().getAdRun(adId);
      thisAdRun.setCompletedBy(getUserId(context));
      thisAdRun.markIncomplete(db);
      addModuleBean(context, "Auto Guide", "Details");
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("MarkIncompleteOK");
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
    if (!(hasPermission(context, "accounts-autoguide-inventory-delete"))) {
      return ("PermissionError");
    }

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
    if (!(hasPermission(context, "accounts-autoguide-inventory-view"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    
    PagedListInfo autoGuideAccountInfo = this.getPagedListInfo(context, "AutoGuideAccountInfo");
    autoGuideAccountInfo.setLink("AccountsAutoGuide.do?command=AccountList&orgId=" + orgId);

    Connection db = null;
    InventoryList inventoryList = new InventoryList();
    try {
      db = this.getConnection(context);
      populateOrganization(context, db, orgId);
      populateListFilters(context, autoGuideAccountInfo);
      inventoryList.setPagedListInfo(autoGuideAccountInfo);
      inventoryList.setOrgId(orgId);
      inventoryList.setBuildOrganizationInfo(false);
      inventoryList.setBuildPictureId(true);
      inventoryList.setShowSold(autoGuideAccountInfo.getFilterKey("listFilter1"));
      inventoryList.buildList(db);
      context.getRequest().setAttribute("InventoryList", inventoryList);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Vehicle Inventory");
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


  /**
   *  Sets up the form for adding a vehicle
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAccountAdd(ActionContext context) {
    if (!(hasPermission(context, "accounts-autoguide-inventory-add"))) {
      return ("PermissionError");
    }
    Exception errorMessage = null;

    int orgId = Integer.parseInt(context.getRequest().getParameter("orgId"));
    Connection db = null;
    try {
      db = this.getConnection(context);
      populateOrganization(context, db, orgId);
      populateYearSelect(context, db);
      populateMakeSelect(context, db, null, null);
      populateModelSelect(context, db, null);
      populateOptionList(context, db);
      populateAdRunTypeSelect(context, db);

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


  /**
   *  Processes the vehicle form and attempts to insert the vehicle
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAccountInsert(ActionContext context) {
    if (!(hasPermission(context, "accounts-autoguide-inventory-add"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    boolean recordInserted = false;

    Inventory thisItem = (Inventory) context.getFormBean();
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


  /**
   *  Populates a list of makes and stores in the request, used for updating
   *  combo-boxes
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
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


  /**
   *  Populates a list of models and stores in the request, used for updating
   *  combo-boxes
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
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


  /**
   *  Prepares a form for modifying a vehicle
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAccountModify(ActionContext context) {
    if (!(hasPermission(context, "accounts-autoguide-inventory-edit"))) {
      return ("PermissionError");
    }

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
      populateMakeSelect(context, db, thisItem.getVehicle(), null);
      populateModelSelect(context, db, thisItem.getVehicle());
      populateOptionList(context, db);
      populateAdRunTypeSelect(context, db);

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


  /**
   *  Processes the vehicle form and attempts to update the vehicle
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandAccountUpdate(ActionContext context) {
    if (!(hasPermission(context, "accounts-autoguide-inventory-edit"))) {
      return ("PermissionError");
    }

    Exception errorMessage = null;
    Connection db = null;

    String orgid = context.getRequest().getParameter("orgId");
    int resultCount = 0;

    try {
      Inventory thisItem = (Inventory) context.getFormBean();
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


  /**
   *  Prepares a form when the user needs to upload a photo
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandUploadForm(ActionContext context) {
    if (!(hasPermission(context, "accounts-autoguide-inventory-edit"))) {
      return ("PermissionError");
    }

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


  /**
   *  Processes an uploaded photo, creates a thumbnail, and stores both in the
   *  database
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandUpload(ActionContext context) {
    if (!(hasPermission(context, "accounts-autoguide-inventory-edit"))) {
      return ("PermissionError");
    }

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

      String id = (String) parts.get("id");

      db = getConnection(context);

      //Delete the previous files
      FileItemList previousFiles = new FileItemList();
      previousFiles.setLinkModuleId(Constants.AUTOGUIDE);
      previousFiles.setLinkItemId(Integer.parseInt(id));
      previousFiles.buildList(db);
      previousFiles.delete(db, filePath);

      //Update the database with the resulting file
      FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
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
        //Create a thumbnail
        File thumbnail = new File(newFileInfo.getLocalFile().getPath() + "TH");
        ImageUtils.saveThumbnail(newFileInfo.getLocalFile(), thumbnail, 133d, -1d);

        //Store thumbnail in database
        thisItem.setSubject("thumbnail");
        thisItem.setFilename(newFileInfo.getRealFilename() + "TH");
        thisItem.setVersion(1.1d);
        thisItem.setSize((int) thumbnail.length());
        recordInserted = thisItem.insertVersion(db);
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


  /**
   *  Retrieves a specific image and returns the data, used for displaying in a
   *  browser with the <src> tag
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandShowImage(ActionContext context) {
    Exception errorMessage = null;
    String linkId = (String) context.getRequest().getParameter("id");
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;

    Connection db = null;
    try {
      db = getConnection(context);
      thisItem = new FileItem(db, Integer.parseInt(itemId), Integer.parseInt(linkId), Constants.AUTOGUIDE);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
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
        String imageType = "jpg";
        //TODO: do not hard code this
        fileDownload.sendFile(context, "image/" + imageType);
      } else {
        System.err.println("AutoGuide-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        System.out.println(se.toString());
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    }

    return ("-none-");
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDownloadImage(ActionContext context) {
    Exception errorMessage = null;
    String linkId = (String) context.getRequest().getParameter("id");
    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    FileItem thisItem = null;
    Inventory inventoryItem = null;

    Connection db = null;
    try {
      db = getConnection(context);
      inventoryItem = new Inventory(db, Integer.parseInt(linkId));
      thisItem = inventoryItem.getPicture();
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
        //Retrieves the original/raw image instead of latest file
        itemToDownload = thisItem.getVersion(1.0);
      } else {
        itemToDownload = thisItem.getVersion(Double.parseDouble(version));
      }

      itemToDownload.setEnteredBy(this.getUserId(context));
      String filePath = this.getPath(context, "autoguide") + getDatePath(itemToDownload.getModified()) + itemToDownload.getFilename();

      FileDownload fileDownload = new FileDownload();
      fileDownload.setFullPath(filePath);
      fileDownload.setDisplayName(
          StringUtils.toString(inventoryItem.getOrganization().getAccountNumber()) + "_" +
          StringUtils.toString(inventoryItem.getStockNo()) + "_" +
          StringUtils.toString(inventoryItem.getVehicle().getMake().getName()) + ".jpg");

      //itemToDownload.getClientFilename());
      if (fileDownload.fileExists()) {
        fileDownload.sendFile(context);
        db = getConnection(context);
        itemToDownload.updateCounter(db);
      } else {
        System.err.println("AutoGuide-> Trying to send a file that does not exist");
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        se.printStackTrace(System.out);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandDownloadText(ActionContext context) {
    Exception errorMessage = null;
    String linkId = (String) context.getRequest().getParameter("id");
    Inventory inventoryItem = null;

    Connection db = null;
    try {
      db = getConnection(context);
      inventoryItem = new Inventory(db, Integer.parseInt(linkId));
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      FileDownload fileDownload = new FileDownload();
      fileDownload.setDisplayName(
          StringUtils.toString(inventoryItem.getOrganization().getAccountNumber()) + "_" +
          StringUtils.toString(inventoryItem.getStockNo()) + "_" +
          StringUtils.toString(inventoryItem.getVehicle().getMake().getName()) + ".txt");
      fileDownload.sendTextAsFile(context, inventoryItem.toString());
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        se.printStackTrace(System.out);
      }
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace(System.out);
    }

    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of Parameter
   *@param  info     Description of Parameter
   */
  public void populateListFilters(ActionContext context, PagedListInfo info) {
    if (!info.hasListFilters()) {
      //Default to Current Inventory
      info.addFilter(1, "0");
      //Default to Inventory with incomplete ads
      info.addFilter(2, "1");
    }
    HtmlSelect listFilterSelect = new HtmlSelect();
    listFilterSelect.addItem(0, "Current Inventory");
    listFilterSelect.addItem(1, "Sold Inventory");
    listFilterSelect.addItem(-1, "All Inventory");
    context.getRequest().setAttribute("listFilterSelect", listFilterSelect);
    
    HtmlSelect statusFilterSelect = new HtmlSelect();
    statusFilterSelect.addItem(1, "Incomplete Ads");
    statusFilterSelect.addItem(0, "Completed Ads");
    statusFilterSelect.addItem(-1, "All Ads");
    context.getRequest().setAttribute("statusFilterSelect", statusFilterSelect);
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@param  orgId             Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void populateOrganization(ActionContext context, Connection db, int orgId) throws SQLException {
    Organization thisOrganization = new Organization(db, orgId);
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void populateYearSelect(ActionContext context, Connection db) throws SQLException {
    ArrayList yearList = VehicleList.buildYearList(db);
    HtmlSelect yearSelect = new HtmlSelect(yearList);
    yearSelect.addItem(-1, "--None--", 0);
    context.getRequest().setAttribute("YearSelect", yearSelect);
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@param  thisVehicle       Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void populateMakeSelect(ActionContext context, Connection db, Vehicle thisVehicle, String defaultText) throws SQLException {
    HtmlSelect makeSelect = new HtmlSelect();
    if (defaultText == null) {
      makeSelect.addItem(-1, "--None--");
    } else {
      makeSelect.addItem(-1, defaultText);
    }
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


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@param  thisVehicle       Description of Parameter
   *@exception  SQLException  Description of Exception
   */
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


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void populateOptionList(ActionContext context, Connection db) throws SQLException {
    OptionList options = new OptionList(db);
    context.getRequest().setAttribute("OptionList", options);
  }


  /**
   *  Description of the Method
   *
   *@param  context           Description of Parameter
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   */
  private void populateAdRunTypeSelect(ActionContext context, Connection db) throws SQLException {
    LookupList adRunType = new LookupList(db, "autoguide_ad_run_types");
    context.getRequest().setAttribute("adRunTypeList", adRunType);
  }
}

