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

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.orders.base.*;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.orders.base.*;

/**
 *  Description of the Class
 *
 * @author     ananth
 * @created    April 23, 2004
 * @version    $Id$
 */
public final class AccountsProducts extends CFSModule {
  /**
   *  Displays a list of customer Products
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandList(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "accounts-accounts-products-view"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;

    try {
      db = this.getConnection(context);
      thisOrg = addOrganization(context, db);

      CustomerProductList customerProductList = new CustomerProductList();
      PagedListInfo searchListInfo = this.getPagedListInfo(context, "SearchCustomerProductListInfo");
      customerProductList.setPagedListInfo(searchListInfo);
      customerProductList.setOrgId(thisOrg.getOrgId());
      customerProductList.buildList(db);
      context.getRequest().setAttribute("CustomerProductList", customerProductList);

    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    addModuleBean(context, "View Accounts", "View Products");
    if (errorMessage == null) {
      return ("ListOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }



  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandAdd(ActionContext context) {
    /*
     *  if (!(hasPermission(context, "accounts-accounts-products-add"))) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;

    try {
      db = getConnection(context);
      thisOrg = addOrganization(context, db);
      String folderId = context.getRequest().getParameter("folderId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Upload Products");
    if (errorMessage == null) {
      return ("AddOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandSVGDetails(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);
      thisOrg = addOrganization(context, db);
      String strcpid = (String) context.getRequest().getParameter("cpid");
      if (strcpid == null) {
        strcpid = (String) context.getRequest().getAttribute("cpid");
      }
      String stritemid = (String) context.getRequest().getParameter("fid");
      if (stritemid == null) {
        stritemid = (String) context.getRequest().getAttribute("fid");
      }
      int cpid = Integer.parseInt(strcpid);
      int itemId = Integer.parseInt(stritemid);
      CustomerProduct customerProduct = new CustomerProduct(db, cpid);
      context.getRequest().setAttribute("CustomerProduct", customerProduct);
      
      FileItem thisItem = new FileItem(db, itemId, customerProduct.getId(), Constants.DOCUMENTS_CUSTOMER_PRODUCT);
      thisItem.buildVersionList(db);
      //TODO: replace the version 1.0 with a constant
      FileItemVersion svgVersion = thisItem.getVersion(Double.parseDouble("1.0"));
      String filePath = this.getPath(context, "accounts") + getDatePath(svgVersion.getModified()) + svgVersion.getFilename();
      File svgFile = new File(filePath);
      SVGUtils svg = new SVGUtils(svgFile.toURL().toString());
      Vector textItems = svg.getTextElements();
      context.getRequest().setAttribute("FileItem", thisItem);
      context.getRequest().setAttribute("textItems", textItems);
    } catch (Exception e) {
      e.printStackTrace();
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "SVGDetailsOK";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandUpload(ActionContext context) {
    /*
     *  if (!hasPermission(context, "accounts-accounts-products-add")) {
     *  return ("PermissionError");
     *  }
     */
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    boolean recordInserted = false;
    try {
      String filePath = this.getPath(context, "accounts");
      //Process the form data
      HttpMultiPartParser multiPart = new HttpMultiPartParser();
      multiPart.setUsePathParam(false);
      multiPart.setUseUniqueName(true);
      multiPart.setUseDateForFolder(true);
      multiPart.setExtensionId(getUserId(context));
      HashMap parts = multiPart.parseData(context.getRequest(), filePath);
      String id = (String) parts.get("id");
      String subject = (String) parts.get("subject");
      String description = (String) parts.get("description");
      String folderId = (String) parts.get("folderId");
      String strproductid = (String) parts.get("productId");
      db = getConnection(context);
      thisOrg = addOrganization(context, db, id);


      // add a dummy order for this new customer product
      Order thisOrder = new Order();
      //TODO: set the order status to closed
      thisOrder.setOrgId(thisOrg.getOrgId());
      thisOrder.setSalesId(this.getUserId(context));
      thisOrder.setDescription("Adding a new Customer Product");
      thisOrder.setEnteredBy(this.getUserId(context));
      thisOrder.setModifiedBy(this.getUserId(context));
      thisOrder.insert(db);

      // add a order product for the above order
      int productId = Integer.parseInt(strproductid);


      OrderProduct thisProduct = new OrderProduct();
      thisProduct.setOrderId(thisOrder.getId());
      thisProduct.setProductId(productId);
      thisProduct.insert(db);


      // add a customer product record
      CustomerProduct customerProduct = new CustomerProduct();
      customerProduct.setOrgId(thisOrg.getOrgId());
      customerProduct.setOrderItemId(thisProduct.getId());
      customerProduct.setDescription(description);
      customerProduct.setEnteredBy(this.getUserId(context));
      customerProduct.setModifiedBy(this.getUserId(context));
      customerProduct.insert(db);


      if ((Object) parts.get("id" + (String) parts.get("id")) instanceof FileInfo) {
        //Update the database with the resulting file
        FileInfo newFileInfo = (FileInfo) parts.get("id" + id);
        //Insert a file description record into the database
        FileItem thisItem = new FileItem();
        thisItem.setLinkModuleId(Constants.DOCUMENTS_CUSTOMER_PRODUCT);
        thisItem.setLinkItemId(customerProduct.getId());
        thisItem.setEnteredBy(getUserId(context));
        thisItem.setModifiedBy(getUserId(context));
        //thisItem.setFolderId(Integer.parseInt(folderId));
        thisItem.setSubject(subject);
        thisItem.setClientFilename(newFileInfo.getClientFileName());
        thisItem.setFilename(newFileInfo.getRealFilename());
        thisItem.setVersion(1.0);
        thisItem.setSize(newFileInfo.getSize());
        recordInserted = thisItem.insert(db);

        if (!recordInserted) {
          processErrors(context, thisItem.getErrors());
        } else {
          //TODO: determine the file type and if an SVG is uploaded then
          // create a full size jpeg and store it
          File svgFile = new File(newFileInfo.getLocalFile().getPath());
          File imageFile = new File(newFileInfo.getLocalFile().getPath() + "FS");
          File thumbnailFile = new File(newFileInfo.getLocalFile().getPath() + "TH");
          SVGUtils svg = new SVGUtils(svgFile.toURL().toString());

          svg.saveAsJPEG(imageFile);
          thisItem.setSubject("fullsize");
          thisItem.setClientFilename("FS" + newFileInfo.getClientFileName());
          thisItem.setFilename(newFileInfo.getRealFilename() + "FS");
          thisItem.setVersion(1.1d);
          thisItem.setSize((int) imageFile.length());
          recordInserted = thisItem.insertVersion(db);

          svg.saveAsJPEG(imageFile, thumbnailFile, 100, 0);
          thisItem.setSubject("thumbnail");
          thisItem.setClientFilename("TH" + newFileInfo.getClientFileName());
          thisItem.setFilename(newFileInfo.getRealFilename() + "TH");
          thisItem.setVersion(1.2d);
          thisItem.setSize((int) thumbnailFile.length());
          recordInserted = thisItem.insertVersion(db);
          context.getRequest().setAttribute("orgId", Integer.toString(thisOrg.getOrgId()));
          context.getRequest().setAttribute("cpid", Integer.toString(customerProduct.getId()));
          context.getRequest().setAttribute("fid", Integer.toString(thisItem.getId()));
          context.getRequest().setAttribute("uploadMsg", "You have successfully uploaded the following svg");
        }

      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        errors.put("actionError", "The file could not be sent by your computer, make sure the file exists");
        processErrors(context, errors);
        context.getRequest().setAttribute("subject", subject);
        context.getRequest().setAttribute("folderId", folderId);
        customerProduct.delete(db);
      }
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      freeConnection(context, db);
    }

    if (errorMessage == null) {
      if (recordInserted) {
        return (executeCommandSVGDetails(context));
      } else {
        return (executeCommandAdd(context));
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Provides details of a particular customer product
   *  and also a list of files associated with this product
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;

    String id = (String) context.getRequest().getParameter("id");
    
    try {
      db = getConnection(context);
      Organization thisOrg = addOrganization(context, db);
      CustomerProduct customerProduct = new CustomerProduct(db, Integer.parseInt(id));
      
      FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.DOCUMENTS_CUSTOMER_PRODUCT);
      documents.setLinkItemId(customerProduct.getId());

      PagedListInfo docListInfo = this.getPagedListInfo(context, "DocListInfo");
      docListInfo.setLink("AccountsProducts.do?command=List&orgId=" + thisOrg.getOrgId());
      documents.setPagedListInfo(docListInfo);
      documents.buildList(db);
      context.getRequest().setAttribute("CustomerProduct", customerProduct);
      context.getRequest().setAttribute("FileItemList", documents);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    addModuleBean(context, "View Accounts", "Document Details");
    if (errorMessage == null) {
      return ("DetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Adds a feature to the Organization attribute of the AccountsProducts object
   *
   * @param  context           The feature to be added to the Organization attribute
   * @param  db                The feature to be added to the Organization attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private Organization addOrganization(ActionContext context, Connection db) throws SQLException {
    String organizationId = (String) context.getRequest().getParameter("orgId");
    if (organizationId == null) {
      organizationId = (String) context.getRequest().getAttribute("orgId");
    }
    return addOrganization(context, db, organizationId);
  }


  /**
   *  Adds a feature to the Organization attribute of the AccountsProducts object
   *
   * @param  context           The feature to be added to the Organization attribute
   * @param  db                The feature to be added to the Organization attribute
   * @param  organizationId    The feature to be added to the Organization attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  private Organization addOrganization(ActionContext context, Connection db, String organizationId) throws SQLException {
    context.getRequest().setAttribute("orgId", organizationId);
    Organization thisOrganization = new Organization(db, Integer.parseInt(organizationId));
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return thisOrganization;
  }
}

