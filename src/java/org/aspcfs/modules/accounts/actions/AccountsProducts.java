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
import com.isavvix.tools.FileInfo;
import com.isavvix.tools.HttpMultiPartParser;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import com.zeroio.iteam.base.FileItemVersion;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.orders.base.Order;
import org.aspcfs.modules.orders.base.OrderProduct;
import org.aspcfs.modules.products.base.CustomerProduct;
import org.aspcfs.modules.products.base.CustomerProductHistory;
import org.aspcfs.modules.products.base.CustomerProductList;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.utils.SVGUtils;
import org.aspcfs.utils.web.HtmlDialog;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.PagedListInfo;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Description of the Class
 *
 * @author ananth
 * @version $Id$
 * @created April 23, 2004
 */
public final class AccountsProducts extends CFSModule {
  /**
   * Displays a list of customer Products
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      PagedListInfo searchListInfo = this.getPagedListInfo(
          context, "SearchCustomerProductListInfo");
      searchListInfo.setLink(
          "AccountsProducts.do?command=List&orgId=" + thisOrg.getOrgId());
      customerProductList.setPagedListInfo(searchListInfo);
      customerProductList.setOrgId(thisOrg.getOrgId());
      customerProductList.setBuildFileList(true);
      customerProductList.setBuildProductCatalog(true);
      customerProductList.buildList(db);
      context.getRequest().setAttribute(
          "CustomerProductList", customerProductList);

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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
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
      String orderItemId = context.getRequest().getParameter("orderItemId");
      if (orderItemId != null && !"".equals(orderItemId)) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setBuildProductOptions(true);
        orderProduct.setBuildProductStatus(true);
        orderProduct.setBuildProduct(true);
        orderProduct.queryRecord(db, Integer.parseInt(orderItemId));
        context.getRequest().setAttribute("orderProduct", orderProduct);

        Order order = new Order();
        order.setBuildProducts(true);
        order.queryRecord(db, orderProduct.getOrderId());

        Quote quote = new Quote(db, order.getQuoteId());

        ProductCatalog product = new ProductCatalog();
        product.setBuildOptions(true);
        product.queryRecord(db, quote.getProductId());
        context.getRequest().setAttribute("Product", product);
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSVGDetails(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = this.getConnection(context);
      thisOrg = addOrganization(context, db);
      String stradId = (String) context.getRequest().getParameter("adId");
      if (stradId == null) {
        stradId = (String) context.getRequest().getAttribute("adId");
      }
      String stritemid = (String) context.getRequest().getParameter("fid");
      if (stritemid == null) {
        stritemid = (String) context.getRequest().getAttribute("fid");
      }
      int adId = Integer.parseInt(stradId);
      int itemId = Integer.parseInt(stritemid);
      CustomerProduct customerProduct = new CustomerProduct(db, adId);
      context.getRequest().setAttribute("CustomerProduct", customerProduct);

      FileItem thisItem = new FileItem(
          db, itemId, customerProduct.getId(), Constants.DOCUMENTS_CUSTOMER_PRODUCT);
      thisItem.buildVersionList(db);
      //TODO: replace the version 1.0 with a constant
      FileItemVersion svgVersion = thisItem.getVersion(
          Double.parseDouble("1.0"));
      String filePath = this.getPath(context, "accounts") + getDatePath(
          svgVersion.getModified()) + svgVersion.getFilename();
      File svgFile = new File(filePath);
      SVGUtils svg = new SVGUtils(svgFile.toURL().toString());
      Vector textItems = svg.getTextValues();
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandUpload(ActionContext context) {
    /*
     *  if (!hasPermission(context, "accounts-accounts-products-add")) {
     *  return ("PermissionError");
     *  }
     */
    Order thisOrder = null;
    OrderProduct thisProduct = null;
    Connection db = null;
    boolean isValid = false;
    Organization thisOrg = null;
    boolean recordInserted = false;
    boolean isSvgFile = false;
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
      String orderItemId = (String) parts.get("orderItemId");
      if (folderId != null) {
        context.getRequest().setAttribute("folderId", folderId);
      }
      if (subject != null) {
        context.getRequest().setAttribute("subject", subject);
      }

      db = getConnection(context);
      thisOrg = addOrganization(context, db, id);
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList list = systemStatus.getLookupList(db, "lookup_order_status");

      // add a order product for the above order
      int productId = Integer.parseInt(strproductid);

      if (orderItemId == null || "".equals(orderItemId)) {
        /*
         *  The order does not exist, hence
         *  add a dummy order, a dummy order product,
         *  and customer product history records, if the file uploaded is of type svg
         *  else upload only the file and do not insert records
         */
        // add a dummy order for this new customer product
        thisOrder = new Order();
        //TODO: set the order status to closed
        thisOrder.setOrgId(thisOrg.getOrgId());
        thisOrder.setSalesId(this.getUserId(context));
        thisOrder.setDescription("Adding a new Customer Product");
        thisOrder.setStatusId(list.getIdFromValue("Closed"));
        thisOrder.setEnteredBy(this.getUserId(context));
        thisOrder.setModifiedBy(this.getUserId(context));
        thisOrder.insert(db);

        thisProduct = new OrderProduct();
        thisProduct.setEnteredBy(this.getUserId(context));
        thisProduct.setModifiedBy(this.getUserId(context));
        thisProduct.setOrderId(thisOrder.getId());
        thisProduct.setStatusId(thisOrder.getStatusId());
        thisProduct.setStatusDate(thisOrder.getModified());
        thisProduct.setProductId(productId);
        thisProduct.insert(db);
      } else {
        //The order product already exists
        thisProduct = new OrderProduct();
        thisProduct.setBuildProductOptions(true);
        thisProduct.setBuildProductStatus(true);
        thisProduct.queryRecord(db, Integer.parseInt(orderItemId));

        thisOrder = new Order();
        thisOrder.setBuildProducts(true);
        thisOrder.queryRecord(db, thisProduct.getOrderId());
      }

      // add a customer product record
      CustomerProduct customerProduct = new CustomerProduct();
      customerProduct.setOrgId(thisOrg.getOrgId());
      customerProduct.setOrderId(thisOrder.getId());
      customerProduct.setOrderItemId(thisProduct.getId());
      customerProduct.setDescription(description);
      customerProduct.setEnteredBy(this.getUserId(context));
      customerProduct.setModifiedBy(this.getUserId(context));
      customerProduct.insert(db);

      CustomerProductHistory history = new CustomerProductHistory();
      history.setCustomerProductId(customerProduct.getId());
      history.setOrgId(thisOrg.getOrgId());
      history.setOrderId(thisOrder.getId());
      history.setOrderItemId(thisProduct.getId());
      history.setEnteredBy(this.getUserId(context));
      history.setModifiedBy(this.getUserId(context));
      history.insert(db);

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
        isValid = this.validateObject(context, db, thisItem);
        if (isValid) {
          recordInserted = thisItem.insert(db);
        }

        if (recordInserted) {
          if (".svg".equals(thisItem.getExtension())) {
            isSvgFile = true;

            File svgFile = new File(newFileInfo.getLocalFile().getPath());
            File imageFile = new File(
                newFileInfo.getLocalFile().getPath() + "FS");
            File thumbnailFile = new File(
                newFileInfo.getLocalFile().getPath() + "TH");
            SVGUtils svg = new SVGUtils(svgFile.toURL().toString());
            /*
             *  Process the SVG being uploaded and determine if there are any broken
             *  text elements and fix them
             */
            if (svg.processSvgFile(svgFile)) {
              /*
               *  If there were any changes made to the SVG due to
               *  malformed text elements, then reload the svg document
               */
              svg = new SVGUtils(svgFile.toURL().toString());
            }
            /*
             *  Save a full size JPEG version of the svg
             */
            svg.saveAsJPEG(imageFile);
            String filename = newFileInfo.getClientFileName();
            filename = filename.substring(
                0, filename.indexOf(thisItem.getExtension()));
            filename += ".jpeg";
            thisItem.setClientFilename(filename);
            thisItem.setFilename(newFileInfo.getRealFilename() + "FS");
            thisItem.setVersion(1.1d);
            thisItem.setSize((int) imageFile.length());
            recordInserted = thisItem.insertVersion(db);
            /*
             *  Save a thumbnail version of the svg as a Jpeg
             */
            svg.saveAsJPEG(imageFile, thumbnailFile, 100, 0);
            thisItem.setClientFilename(newFileInfo.getClientFileName());
            thisItem.setFilename(newFileInfo.getRealFilename() + "TH");
            thisItem.setVersion(1.2d);
            thisItem.setSize((int) thumbnailFile.length());
            recordInserted = thisItem.insertVersion(db);
            context.getRequest().setAttribute(
                "uploadMsg", "You have successfully uploaded the following svg");

          }
          context.getRequest().setAttribute(
              "orgId", Integer.toString(thisOrg.getOrgId()));
          context.getRequest().setAttribute(
              "adId", Integer.toString(customerProduct.getId()));
          context.getRequest().setAttribute(
              "fid", Integer.toString(thisItem.getId()));
        }
      } else {
        recordInserted = false;
        HashMap errors = new HashMap();
        errors.put(
            "actionError", systemStatus.getLabel(
                "object.validation.incorrectFileName"));
        processErrors(context, errors);
        context.getRequest().setAttribute("subject", subject);
        context.getRequest().setAttribute("folderId", folderId);
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return (executeCommandAdd(context));
    } finally {
      freeConnection(context, db);
    }
    if (recordInserted) {
      if (isSvgFile) {
        return (executeCommandSVGDetails(context));
      } else {
        return (executeCommandDetails(context));
      }
    } else {
      return (executeCommandAdd(context));
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandOnlineTool(ActionContext context) {
    Connection db = null;
    Organization thisOrg = null;
    try {
      db = getConnection(context);
      int id = Integer.parseInt(context.getRequest().getParameter("orgId"));
      int adId = Integer.parseInt(context.getRequest().getParameter("adId"));
      thisOrg = new Organization(db, id);
      // Make the following information available in the request
      CustomerProduct customerProduct = new CustomerProduct();
      customerProduct.setBuildFileList(true);
      customerProduct.queryRecord(db, adId);

      Iterator i = customerProduct.getFileItemList().iterator();
      FileItem targetFile = null;
      while (i.hasNext()) {
        targetFile = (FileItem) i.next();
        if (".svg".equals(targetFile.getExtension())) {
          break;
        }
      }

      FileItemVersion svgVersion = targetFile.getVersion(
          Double.parseDouble("1.0"));
      String svgFilePath = this.getPath(context, "accounts") + getDatePath(
          svgVersion.getModified()) + svgVersion.getFilename();
      File svgFile = new File(svgFilePath);
      SVGUtils svg = new SVGUtils(svgFile.toURL().toString());
      Vector textItems = svg.getTextValues();
      context.getRequest().setAttribute("OrgDetails", thisOrg);
      context.getRequest().setAttribute("FileItem", targetFile);
      context.getRequest().setAttribute("textItems", textItems);
      context.getRequest().setAttribute("CustomerProduct", customerProduct);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "OnlineToolOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandStreamAdImage(ActionContext context) {
    Connection db = null;
    try {
      db = getConnection(context);
      int adId = Integer.parseInt(context.getRequest().getParameter("adId"));
      // Make the following information available in the request
      CustomerProduct customerProduct = new CustomerProduct();
      customerProduct.setBuildFileList(true);
      customerProduct.queryRecord(db, adId);

      if (isPortalUser(context)) {
        User user = getUser(context, getUserId(context));
        int userOrgId = user.getContact().getOrgId();
        if (customerProduct.getOrgId() != userOrgId) {
          Exception error = new Exception("Unauthorized Access");
          context.getRequest().setAttribute("Error", error);
          return "SystemError";
        }
      }

      Iterator i = customerProduct.getFileItemList().iterator();
      FileItem targetFile = null;
      while (i.hasNext()) {
        targetFile = (FileItem) i.next();
        if (".svg".equals(targetFile.getExtension())) {
          break;
        }
      }
      FileItemVersion svgVersion = targetFile.getVersion(
          Double.parseDouble("1.0"));
      String svgFilePath = this.getPath(context, "accounts") + getDatePath(
          svgVersion.getModified()) + svgVersion.getFilename();
      File svgFile = new File(svgFilePath);
      SVGUtils svg = new SVGUtils(svgFile.toURL().toString());
      Enumeration e = context.getRequest().getParameterNames();
      Vector replaceValues = new Vector();
      int k = 1;
      while (e.hasMoreElements()) {
        String param = (String) e.nextElement();
        if (param.startsWith("text")) {
          String value = (String) context.getRequest().getParameter(
              "text" + k);
          if (value == null) {
            value = "";
          }
          replaceValues.add(value);
          k++;
        }
      }
      svg.setAllTextElements(replaceValues);
      svg.saveAsJPEG(context);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return ("-none-");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandConfirmDelete(ActionContext context) {
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    String adId = (String) context.getRequest().getParameter("adId");
    Connection db = null;
    Exception errorMessage = null;
    Organization thisOrg = null;
    HtmlDialog htmlDialog = new HtmlDialog();
    try {
      db = getConnection(context);
      thisOrg = addOrganization(context, db);
      CustomerProduct customerProduct = new CustomerProduct();
      customerProduct.setBuildHistoryList(true);
      customerProduct.queryRecord(db, Integer.parseInt(adId));
      htmlDialog.setTitle("Adsjet : Account Management");
      DependencyList dependencies = customerProduct.processDependencies(db);
      if (dependencies.canDelete()) {
        htmlDialog.addMessage(dependencies.getHtmlString());
        htmlDialog.setHeader(
            "This object has the following dependencies within Dark Horse CRM:");
        htmlDialog.addButton(
            "Delete All", "javascript:window.location.href='AccountsProducts.do?command=Delete&orgId=" + thisOrg.getOrgId() + "&adId=" + customerProduct.getId() + "&fid=" + itemId + "'");
      }
      htmlDialog.addButton("Cancel", "javascript:parent.window.close()");
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getSession().setAttribute("Dialog", htmlDialog);
      return ("ConfirmDeleteOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {
    boolean recordDeleted = false;
    String itemId = (String) context.getRequest().getParameter("fid");
    String adId = (String) context.getRequest().getParameter("adId");
    Connection db = null;
    Organization thisOrg = null;
    CustomerProduct customerProduct = null;
    try {
      db = getConnection(context);
      thisOrg = addOrganization(context, db);
      customerProduct = new CustomerProduct(db, Integer.parseInt(adId));
      recordDeleted = customerProduct.delete(
          db, Integer.parseInt(itemId), this.getPath(context, "accounts"));
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    if (recordDeleted) {
      context.getRequest().setAttribute(
          "refreshUrl", "AccountsProducts.do?command=List&orgId=" + thisOrg.getOrgId());
      return "DeleteOK";
    }
    processErrors(context, customerProduct.getErrors());
    context.getRequest().setAttribute(
        "actionError", "Account Products could not be deleted because of referential integrity .");
    context.getRequest().setAttribute(
        "refreshUrl", "AccountsProducts.do?command=List&orgId=" + thisOrg.getOrgId());
    return ("DeleteError");
  }


  /**
   * Provides details of a particular customer product and also a list of files
   * associated with this product
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;

    String adId = (String) context.getRequest().getParameter("adId");
    if (adId == null) {
      adId = (String) context.getRequest().getAttribute("adId");
    }
    try {
      db = getConnection(context);
      Organization thisOrg = addOrganization(context, db);
      CustomerProduct customerProduct = new CustomerProduct(
          db, Integer.parseInt(adId));

      FileItemList documents = new FileItemList();
      documents.setLinkModuleId(Constants.DOCUMENTS_CUSTOMER_PRODUCT);
      documents.setLinkItemId(customerProduct.getId());

      PagedListInfo docListInfo = this.getPagedListInfo(
          context, "DocListInfo");
      docListInfo.setLink(
          "AccountsProducts.do?command=List&orgId=" + thisOrg.getOrgId());
      documents.setPagedListInfo(docListInfo);
      documents.buildList(db);
      context.getRequest().setAttribute("CustomerProduct", customerProduct);
      context.getRequest().setAttribute("FileItemList", documents);
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
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDownload(ActionContext context) {
    Exception errorMessage = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String version = (String) context.getRequest().getParameter("ver");
    String adId = (String) context.getRequest().getParameter("adId");
    FileItem thisItem = null;
    Connection db = null;
    Organization thisOrg = null;

    try {
      db = getConnection(context);
      thisOrg = addOrganization(context, db);
      thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(adId), Constants.DOCUMENTS_CUSTOMER_PRODUCT);
      if (version != null) {
        thisItem.buildVersionList(db);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }

    //Start the download
    try {
      if (version == null) {
        FileItem itemToDownload = thisItem;
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "accounts") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context);
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "AccountProducts -> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", "The requested download no longer exists on the system");
          return (executeCommandSVGDetails(context));
        }
      } else {
        FileItemVersion itemToDownload = thisItem.getVersion(
            Double.parseDouble(version));
        itemToDownload.setEnteredBy(this.getUserId(context));
        String filePath = this.getPath(context, "accounts") + getDatePath(
            itemToDownload.getModified()) + itemToDownload.getFilename();
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(itemToDownload.getClientFilename());
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context);
          //Get a db connection now that the download is complete
          db = getConnection(context);
          itemToDownload.updateCounter(db);
        } else {
          db = null;
          System.err.println(
              "AccountsProducts -> Trying to send a file that does not exist");
          context.getRequest().setAttribute(
              "actionError", "The requested download no longer exists on the system");
          return (executeCommandSVGDetails(context));
        }
      }
    } catch (java.net.SocketException se) {
      //User either cancelled the download or lost connection
      if (System.getProperty("DEBUG") != null) {
        System.out.println(se.toString());
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      if (db != null) {
        this.freeConnection(context, db);
      }
    }

    if (errorMessage == null) {
      return ("-none-");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      addModuleBean(context, "View Accounts", "");
      return ("SystemError");
    }
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandViewProduct(ActionContext context) {
    Connection db = null;
    int adId = Integer.parseInt(
        (String) context.getRequest().getParameter("adId"));
    CustomerProduct customerProduct = null;
    try {
      db = getConnection(context);

      // build the customer Product
      customerProduct = new CustomerProduct();
      customerProduct.setBuildFileList(true);
      customerProduct.queryRecord(db, adId);
      context.getRequest().setAttribute("customerProduct", customerProduct);

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "ViewProductOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandVersionDetails(ActionContext context) {

    Exception errorMessage = null;
    Connection db = null;

    String itemId = (String) context.getRequest().getParameter("fid");
    String adId = (String) context.getRequest().getParameter("adId");

    try {
      db = getConnection(context);
      Organization thisOrg = addOrganization(context, db);

      FileItem thisItem = new FileItem(
          db, Integer.parseInt(itemId), Integer.parseInt(adId), Constants.DOCUMENTS_CUSTOMER_PRODUCT);
      thisItem.buildVersionList(db);
      context.getRequest().setAttribute("OrgDetails", thisOrg);
      context.getRequest().setAttribute("FileItem", thisItem);
      context.getRequest().setAttribute("adId", adId);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return ("VersionDetailsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   * Adds a feature to the Organization attribute of the AccountsProducts
   * object
   *
   * @param context The feature to be added to the Organization
   *                attribute
   * @param db      The feature to be added to the Organization
   *                attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private Organization addOrganization(ActionContext context, Connection db) throws SQLException {
    String organizationId = (String) context.getRequest().getParameter(
        "orgId");
    if (organizationId == null) {
      organizationId = (String) context.getRequest().getAttribute("orgId");
    }
    return addOrganization(context, db, organizationId);
  }


  /**
   * Adds a feature to the Organization attribute of the AccountsProducts
   * object
   *
   * @param context        The feature to be added to the Organization
   *                       attribute
   * @param db             The feature to be added to the Organization
   *                       attribute
   * @param organizationId The feature to be added to the Organization
   *                       attribute
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private Organization addOrganization(ActionContext context, Connection db, String organizationId) throws SQLException {
    context.getRequest().setAttribute("orgId", organizationId);
    Organization thisOrganization = new Organization(
        db, Integer.parseInt(organizationId));
    context.getRequest().setAttribute("OrgDetails", thisOrganization);
    return thisOrganization;
  }
}

