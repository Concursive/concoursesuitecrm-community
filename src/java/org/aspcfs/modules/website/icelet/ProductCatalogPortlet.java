/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.icelet;

import com.zeroio.iteam.base.FileItem;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.website.base.WebProductAccessLog;
import org.aspcfs.modules.website.utils.PortletUtils;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.SMTPMessage;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.PagedListInfo;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;

/**
 * Description of the Class
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 16, 2006 $Id: Exp $
 */
public class ProductCatalogPortlet extends GenericPortlet {

  //values are of the form ([y]ymmddhh)
  private final static String VIEW_PAGE1 = "/portlets/products/products_summary.jsp";
  private final static String VIEW_PAGE2 = "/portlets/products/product.jsp";
  private final static String VIEW_PAGE3 = "/portlets/products/products_search.jsp";
  private final static String VIEW_PAGE4 = "/portlets/products/products_summary.jsp";
  private final static String VIEW_PAGE5 = "/portlets/products/product_does_not_exist.jsp";


  /**
   * Description of the Field
   */
  public final static String CATEGORY = "6050816";
  public final static String SHOW_SKU = "6051001";
  public final static String SKU_TEXT = "6051002";
  public final static String SHOW_PRICE = "6051003";
  public final static String PRICE_TEXT = "6051004";
  public final static String SHOW_PRICE_SAVINGS = "6051005";
  public final static String ORIGINAL_PRICE_TEXT = "6051007";
  public final static String PRICE_SAVINGS_TEXT = "6051006";
  public final static String PRODUCT_SEARCH = "6060610";
  public final static String PRODUCT_SEARCH_AS_DEFAULT = "6060611";

  //For emailing product information to a friend
  public final static String INCLUDE_EMAIL = "6060717";
  public final static String SITE_URL = "6060718";
  public final static String EMAIL_SUBJECT = "6060719";
  public final static String EMAIL_INTRODUCTION = "6060720";

  public final static String fs = System.getProperty("file.separator");

  /**
   * Description of the Method
   *
   * @param portletConfig Description of the Parameter
   * @throws PortletException Description of the Exception
   */
  public void init(PortletConfig portletConfig) throws PortletException {
    super.init(portletConfig);
  }


  /**
   * Description of the Method
   *
   * @throws PortletException Description of the Exception
   */
  public void init() throws PortletException {
    super.init();
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws PortletException Description of the Exception
   * @throws IOException      Description of the Exception
   */
  public void doView(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {

    try {
      if (System.getProperty("DEBUG") != null) {
				System.out.println("ProductCatalogPortlet-> PortletMode: " + request.getPortletMode());
			}
			String viewType = (String) request.getParameter("viewType");
      boolean isSearchAsDefault = DatabaseUtils.parseBoolean(request.getPreferences().getValue(PRODUCT_SEARCH_AS_DEFAULT,"false"));
			if ("details".equals(viewType)) {
        boolean productExists = buildProduct(request, response);
        if (productExists) {
          PortletRequestDispatcher requestDispatcher =
              getPortletContext().getRequestDispatcher(VIEW_PAGE2);
          requestDispatcher.include(request, response);
        } else {
          PortletRequestDispatcher requestDispatcher =
              getPortletContext().getRequestDispatcher(VIEW_PAGE5);
          requestDispatcher.include(request, response);
        }
      } else if ("summary".equals(viewType)) {
        buildProductCategoryList(request, response);
        PortletRequestDispatcher requestDispatcher =
            getPortletContext().getRequestDispatcher(VIEW_PAGE1);
        requestDispatcher.include(request, response);
      } else if ("searchResult".equals(viewType)) {
        buildSearchResult(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE4);
        requestDispatcher.include(request, response);
      } else
      if (("search".equals(viewType)) || (isSearchAsDefault && viewType == null))
      {
        buildSearch(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE3);
        requestDispatcher.include(request, response);
      } else {
        buildProductCategoryList(request, response);
        PortletRequestDispatcher requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE1);
        requestDispatcher.include(request, response);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new IOException("Exception in ProductCatalogPortlet");
    }
  }

  private void buildSearch(RenderRequest request, RenderResponse response) {
    ProductCategory productCategory = new ProductCategory();
    PagedListInfo productCatalogListInfo = PortletUtils.getPagedListInfo(request, response, "productCatalogListInfo");
    // Set time zone
    String timeZone = PortletUtils.getApplicationPrefs(request, "SYSTEM.TIMEZONE");

    // Preferred Category Id
    String preferredCategoryId = request.getPreferences().getValue(CATEGORY, "-1");
    productCategory.setId(preferredCategoryId);
    request.setAttribute("productCategory", productCategory);
    request.setAttribute("searchProductCatalogListInfo", productCatalogListInfo);
    request.setAttribute("searchName", productCatalogListInfo.getSearchOptionValue("searchName"));
    request.setAttribute("searchAbbreviation", productCatalogListInfo.getSearchOptionValue("searchAbbreviation"));
    request.setAttribute("searchSku", productCatalogListInfo.getSearchOptionValue("searchSku"));
    request.setAttribute("searchcodePriceRangeMin", productCatalogListInfo.getSearchOptionValue("searchcodePriceRangeMin"));
    request.setAttribute("searchcodePriceRangeMax", productCatalogListInfo.getSearchOptionValue("searchcodePriceRangeMax"));
    request.setAttribute("searchtimestampStartDate", productCatalogListInfo.getSearchOptionValue("searchtimestampStartDate"));
    request.setAttribute("searchtimestampEndDate", productCatalogListInfo.getSearchOptionValue("searchtimestampEndDate"));
    request.setAttribute("searchCategoryListIds", productCatalogListInfo.getSearchOptionValue("searchCategoryListIds"));
    request.setAttribute("searchCategoryNames", productCatalogListInfo.getSearchOptionValue("searchCategoryNames"));
    request.setAttribute("timeZone", timeZone);
    request.getPortletSession().removeAttribute("productCatalogListInfo");
  }


  private void prepare(RenderRequest request, RenderResponse response) throws Exception {
    // TODO Auto-generated method stub

  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws PortletException Description of the Exception
   * @throws IOException      Description of the Exception
   */
  public void processAction(ActionRequest request, ActionResponse response)
      throws PortletException, IOException {
    if ("search".equals(request.getParameter("actionType"))) {
      try {
        prepareSearchResult(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
      response.setRenderParameter("viewType", "searchResult");
    }

    if ("sendEmail".equals(request.getParameter("actionType"))) {
      try {
        sendEmail(request, response);
      } catch (Exception e) {
        e.printStackTrace();
      }
      response.setRenderParameter("viewType", "details");
      response.setRenderParameter("productId", request.getParameter("productId"));
      response.setRenderParameter("categoryId", request.getParameter("categoryId"));
      response.setRenderParameter("page", request.getParameter("page"));
      response.setRenderParameter("offset", request.getParameter("offset"));
    }
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private boolean buildProduct(RenderRequest request, RenderResponse response) throws Exception {
    // Retrieve the product preferences
    request.setAttribute("SHOW_SKU", (String) request.getPreferences().getValue(SHOW_SKU, "true"));
    request.setAttribute("SKU_TEXT", (String) request.getPreferences().getValue(SKU_TEXT, "Item #"));
    request.setAttribute("SHOW_PRICE", (String) request.getPreferences().getValue(SHOW_PRICE, "true"));
    request.setAttribute("PRICE_TEXT", (String) request.getPreferences().getValue(PRICE_TEXT, ""));
    request.setAttribute("SHOW_PRICE_SAVINGS", (String) request.getPreferences().getValue(SHOW_PRICE_SAVINGS, "true"));
    request.setAttribute("ORIGINAL_PRICE_TEXT", (String) request.getPreferences().getValue(ORIGINAL_PRICE_TEXT, "Original Price:"));
    request.setAttribute("PRICE_SAVINGS_TEXT", (String) request.getPreferences().getValue(PRICE_SAVINGS_TEXT, "Instant Savings:"));
    request.setAttribute("PRODUCT_SEARCH", (String) request.getPreferences().getValue(PRODUCT_SEARCH, "false"));
    request.setAttribute("INCLUDE_EMAIL", (String) request.getPreferences().getValue(INCLUDE_EMAIL, "false"));
    // Compose a url for emailing
    StringBuffer completeProductURL = new StringBuffer();
    completeProductURL.append(((HttpServletRequest) request).getRequestURL());
    if (((HttpServletRequest) request).getQueryString() != null) {
      completeProductURL.append("?");
      completeProductURL.append(((HttpServletRequest) request).getQueryString());
    }
    request.setAttribute("productURL", completeProductURL.toString());


    Connection db = PortletUtils.getConnection(request);
    boolean checkProductId = false;
    //determine if this page was reached directly (e.g., via a link in the email)
    PagedListInfo tmpInfo = (PagedListInfo) request.getPortletSession().getAttribute("productCatalogListInfo");
    if (tmpInfo == null || tmpInfo.getId() == null) {
      checkProductId = true;
    }
    //Get Paged List handler for product catalog list
    PagedListInfo productCatalogListInfo = PortletUtils.getPagedListInfo(request, response, "productCatalogListInfo", false);
    productCatalogListInfo.setMode(PagedListInfo.DETAILS_VIEW);
    productCatalogListInfo.setCurrentOffset(request.getParameter("offset"));
    //setting the URL
    HashMap renderParams = new HashMap();
    renderParams.put("viewType", new String[]{"details"});
    renderParams.put("productId", new String[]{request.getParameter("productId")});
    renderParams.put("categoryId", new String[]{request.getParameter("categoryId")});
    renderParams.put("offset", new String[]{request.getParameter("offset")});
    renderParams.put("page", new String[]{request.getParameter("page")});
    renderParams.put("searchResults", new String[]{request.getParameter("searchResults")});
    request.setAttribute("page", (String) request.getParameter("page"));
    request.setAttribute("offset", (String) request.getParameter("offset"));
    productCatalogListInfo.setRenderParameters(renderParams);

    String preferredCategoryId = request.getPreferences().getValue(CATEGORY, "-1");
    request.setAttribute("preferredCategoryId", preferredCategoryId);

    //building a single item as a list
    ProductCatalogList productCatalogList = new ProductCatalogList();
    if (!"true".equals(request.getParameter("searchResults"))) {
      productCatalogList.setCategoryId(request.getParameter("categoryId"));
    } else {
      ProductCategoryList productCategoryList = new ProductCategoryList();
      String searchCategoryListIds = productCatalogListInfo.getSearchOptionValue("searchCategoryListIds");
      if (searchCategoryListIds.equals("") || searchCategoryListIds == null) {
        productCategoryList = productCategoryList.buildListFromIds(db, preferredCategoryId);
      } else {
        productCategoryList = productCategoryList.buildListFromIds(db, searchCategoryListIds);
      }
      ProductCategoryList.buildHierarchy(db, productCategoryList);
      productCategoryList.buildCompleteHierarchy();
      productCatalogListInfo.setSearchCriteria(productCatalogList, (HttpServletRequest) request, PortletUtils.getSystemStatus(request), null);
      productCatalogList.setProductCategoryList(productCategoryList);
    }
    productCatalogList.setPagedListInfo(productCatalogListInfo);
    if (productCatalogList.getCategoryId() == -1 && productCatalogListInfo.getSavedCriteria().size() == 0) {
      productCatalogList.setHasCategories(Constants.FALSE);
    } else if (productCatalogList.getCategoryId() == -1 && productCatalogListInfo.getSavedCriteria().size() > 0){
      productCatalogList.setHasCategories(Constants.UNDEFINED);
		}
    productCatalogList.buildList(db);
    request.setAttribute("productCatalogList", productCatalogList);
    ProductCatalog productCatalog = null;
    if (productCatalogList.size() > 0) {
      productCatalog = (ProductCatalog) productCatalogList.get(0);
      if (checkProductId && productCatalog.getId() != Integer.parseInt(request.getParameter("productId")))
      {
        return false;
      }
      request.setAttribute("productCatalog", productCatalog);
    }

    //fetching parent category for the specified product/category
    ProductCategory parentCategory = null;
    if (Integer.parseInt(request.getParameter("categoryId")) != -1) {
      parentCategory = new ProductCategory(db, Integer.parseInt(request.getParameter("categoryId")));
    } else {
      parentCategory = new ProductCategory();
    }
    request.setAttribute("parentCategory", parentCategory);

    // get previous page: ex. searchResult, etc.
    request.setAttribute("previousPage", request.getPortletSession().getAttribute("previousPage"));

    //Add Product Access log
    UserBean userBean = PortletUtils.getUser(request);
    if (productCatalog != null && userBean.getUserId() == -2) {
      WebProductAccessLog webProductAccessLog = new WebProductAccessLog();
      webProductAccessLog.setSiteLogId(Integer.parseInt(userBean.getSessionId()));
      webProductAccessLog.setProductId(productCatalog.getId());
      PortletUtils.addLogItem(request, "webProductAccessLog", webProductAccessLog);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void buildProductCategoryList(RenderRequest request, RenderResponse response) throws Exception {
    Connection db = PortletUtils.getConnection(request);

    String preferredCategoryId = request.getPreferences().getValue(CATEGORY, "-1");
    request.setAttribute("preferredCategoryId", preferredCategoryId);

    //fetching sub categories for the specified category
    ProductCategoryList productCategoryList = new ProductCategoryList();
    productCategoryList.setParentId(preferredCategoryId);
    if (StringUtils.hasText(request.getParameter("categoryId"))) {
      productCategoryList.setParentId(request.getParameter("categoryId"));
    }
    if (productCategoryList.getParentId() == -1) {
      productCategoryList.setTopOnly(Constants.TRUE);
    }
    productCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProductCatalogPortlet.doView() + productCategoryList.size()  ==> " + productCategoryList.size());
    }
    request.setAttribute("productCategoryList", productCategoryList);

    //fetching parent category for the specified category
    ProductCategory parentCategory = null;
    if (productCategoryList.getParentId() != -1) {
      parentCategory = new ProductCategory(db, productCategoryList.getParentId());
    } else {
      parentCategory = new ProductCategory();
    }
    request.setAttribute("parentCategory", parentCategory);

    //Get Paged List handler for product catalog list
    PagedListInfo productCatalogListInfo = PortletUtils.getPagedListInfo(request, response, "productCatalogListInfo");
    productCatalogListInfo.setMode(PagedListInfo.LIST_VIEW);
    //Setting URL
    HashMap renderParams = new HashMap();
    renderParams.put("viewType", new String[]{"list"});
    renderParams.put("categoryId", new String[]{String.valueOf(productCategoryList.getParentId())});
    productCatalogListInfo.setRenderParameters(renderParams);

    //fetching products for the specified category
    ProductCatalogList productCatalogList = new ProductCatalogList();
    productCatalogList.setPagedListInfo(productCatalogListInfo);
    productCatalogList.setCategoryId(preferredCategoryId);
    if (StringUtils.hasText(request.getParameter("categoryId"))) {
      productCatalogList.setCategoryId(request.getParameter("categoryId"));
    }
    if (productCatalogList.getCategoryId() == -1) {
      productCatalogList.setHasCategories(Constants.FALSE);
    }
    productCatalogList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProductCatalogPortlet.doView() + productCatalogList.size()  ==> " + productCatalogList.size());
    }

    // Set previous page
    request.getPortletSession().setAttribute("previousPage", "summary");
    request.setAttribute("productCatalogList", productCatalogList);
    request.setAttribute("PRODUCT_SEARCH", (String) request.getPreferences().getValue(PRODUCT_SEARCH, "false"));
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void buildSearchResult(RenderRequest request, RenderResponse response) throws Exception {
    Connection db = PortletUtils.getConnection(request);

    //fetching products for the specified category
    String preferredCategoryId = request.getPreferences().getValue(CATEGORY, "-1");
    //Get Paged List handler for product catalog list
    PagedListInfo productCatalogListInfo = PortletUtils.getPagedListInfo(request, response, "productCatalogListInfo");
    productCatalogListInfo.setMode(PagedListInfo.LIST_VIEW);
    //Setting URL
    HashMap renderParams = new HashMap();
    renderParams.put("viewType", new String[]{"searchResult"});
    productCatalogListInfo.setRenderParameters(renderParams);
    // Category Hierarchy
    ProductCatalogList productCatalogList = new ProductCatalogList();
    ProductCategoryList productCategoryList = new ProductCategoryList();
    String searchCategoryListIds = productCatalogListInfo.getSearchOptionValue("searchCategoryListIds");
    if (searchCategoryListIds.equals("") || searchCategoryListIds == null) {
      productCategoryList = productCategoryList.buildListFromIds(db, preferredCategoryId);
    } else {
      productCategoryList = productCategoryList.buildListFromIds(db, searchCategoryListIds);
    }
    ProductCategoryList.buildHierarchy(db, productCategoryList);
    productCategoryList.buildCompleteHierarchy();
    productCatalogList.setProductCategoryList(productCategoryList);
    // paging
    productCatalogList.setPagedListInfo(productCatalogListInfo);
    productCatalogListInfo.setSearchCriteria(productCatalogList, (HttpServletRequest) request, PortletUtils.getSystemStatus(request), null);

    // All products
    if (productCatalogList.getCategoryId() == -1) {
      productCatalogList.setHasCategories(Constants.UNDEFINED);
    }
    productCatalogList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProductCatalogPortlet.doView() + productCatalogList.size()  ==> " + productCatalogList.size());
    }
    // Set previous page
    request.getPortletSession().setAttribute("previousPage", "searchResult");
    request.setAttribute("productCatalogList", productCatalogList);
    request.setAttribute("searchResults", "true");
    request.setAttribute("PRODUCT_SEARCH", (String) request.getPreferences().getValue(PRODUCT_SEARCH, "false"));
  }

  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void prepareSearchResult(ActionRequest request, ActionResponse response) throws Exception {

    if (System.getProperty("DEBUG") != null) {
      System.out.println("1.viewtype ==> " + request.getParameter("viewType"));
      System.out.println("1.searchName ==>" + request.getParameter("searchName"));
      System.out.println("1.searchSku ==>" + request.getParameter("searchSku"));
      System.out.println("1.searchcodePriceRangeMin ==>" + request.getParameter("searchcodePriceRangeMin"));
      System.out.println("1.searchcodePriceRangeMax ==>" + request.getParameter("searchcodePriceRangeMax"));
    }
    response.setRenderParameter("searchName", request.getParameter("searchName"));
    response.setRenderParameter("searchSku", request.getParameter("searchAbbreviation"));
    response.setRenderParameter("searchSku", request.getParameter("searchSku"));
    response.setRenderParameter("searchcodePriceRangeMin", request.getParameter("searchcodePriceRangeMin"));
    response.setRenderParameter("searchcodePriceRangeMax", request.getParameter("searchcodePriceRangeMax"));
    response.setRenderParameter("searchtimestampStartDate", request.getParameter("searchtimestampStartDate"));
    response.setRenderParameter("searchtimestampEndDate", request.getParameter("searchtimestampEndDate"));
    response.setRenderParameter("searchCategoryListIds", request.getParameter("searchCategoryListIds"));
    response.setRenderParameter("searchCategoryNames", request.getParameter("searchCategoryNames"));
    response.setRenderParameter("viewType", "searchResult");
  }

  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void sendEmail(ActionRequest request, ActionResponse response) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(request.getParameter("yourName"));
      System.out.println(request.getParameter("yourEmailAddress"));
      System.out.println(request.getParameter("yourFriendsAddress"));
      System.out.println(request.getParameter("comments"));
      System.out.println(request.getParameter("productId"));
      System.out.println(request.getParameter("viewType"));
      System.out.println(PortletUtils.getApplicationPrefs(request, "SYSTEM.LANGUAGE"));
      System.out.println(request.getParameter("requestURL"));
    }

    Connection db = PortletUtils.getConnection(request);

    ProductEmailFormatter productEmailFormatter = new ProductEmailFormatter();
    productEmailFormatter.setIntroduction(request.getPreferences().getValue(EMAIL_INTRODUCTION, "-1"));
    productEmailFormatter.setSiteURL(request.getPreferences().getValue(SITE_URL, ""));
    productEmailFormatter.setProductURL(request.getParameter("productURL"));
    productEmailFormatter.setFromName(request.getParameter("yourName"));
    productEmailFormatter.setShowSku(request.getPreferences().getValue(SHOW_SKU, "false"));
    productEmailFormatter.setSkuText(request.getPreferences().getValue(SKU_TEXT, "Sku:"));
    productEmailFormatter.setShowPrice(request.getPreferences().getValue(SHOW_PRICE_SAVINGS, "false"));
    productEmailFormatter.setPriceText(request.getPreferences().getValue(PRICE_TEXT, "Price:"));
    productEmailFormatter.setShowPriceSavings(request.getPreferences().getValue(SHOW_PRICE_SAVINGS, "false"));
    productEmailFormatter.setPriceSavingsText(request.getPreferences().getValue(PRICE_SAVINGS_TEXT, "Price Savings:"));
    productEmailFormatter.setOriginalPriceText(request.getPreferences().getValue(ORIGINAL_PRICE_TEXT, "Original Price:"));
    String systemLanguage = PortletUtils.getApplicationPrefs(request, "SYSTEM.LANGUAGE");
    Locale locale = new Locale(systemLanguage.split("_")[0], systemLanguage.split("_")[1]);
    productEmailFormatter.setLocale(locale);

    String productId = request.getParameter("productId");

    ProductCatalog productCatalog = new ProductCatalog();
    productCatalog.setBuildPriceList(true);
    productCatalog.setBuildActivePrice(true);
    productCatalog.queryRecord(db, Integer.parseInt(productId));
    productCatalog.setComments(request.getParameter("comments"));
    FileItem imageItem = null;
    if (productCatalog.getLargeImageId() != -1) {
      imageItem = new FileItem(db, productCatalog.getLargeImageId());
    }

    SMTPMessage mail = new SMTPMessage();
    mail.setHost(PortletUtils.getApplicationPrefs(request, "MAILSERVER"));
    mail.setFrom(request.getParameter("yourEmailAddress"));
    mail.setType("text/html");
    mail.setTo(request.getParameter("yourFriendsAddress"));
    if (imageItem != null) {
      imageItem.setDirectory(PortletUtils.getDbNamePath(request) + "products" + fs);
      String imagePath = imageItem.getFullFilePath();
      mail.addImage("productImage", "file://" + imagePath);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(imagePath);
        File file = new File(imagePath);
        if (file.isFile()) {
          System.out.println(" FILE EXISTS ");
        }
      }
    }
    String templateFilePath = PortletUtils.getDbNamePath(request) + "templates_" + PortletUtils.getApplicationPrefs(request, "SYSTEM.LANGUAGE") + ".xml";

    String productInformation = productEmailFormatter.getProductInformation(
        productCatalog, templateFilePath);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ProductCatalogPortlet-> Product Information: " + productInformation);
    }
    mail.setSubject(request.getPreferences().getValue(EMAIL_SUBJECT, "Product Information"));
    mail.setBody(productInformation);
    int mailReturn = mail.send();
    if (mailReturn == 2) {
      System.err.println(mail.getErrorMsg());
    }
  }

}

