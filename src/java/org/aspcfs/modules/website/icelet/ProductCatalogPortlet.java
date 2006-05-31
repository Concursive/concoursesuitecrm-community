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

import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.products.base.*;
import org.aspcfs.modules.website.utils.PortletUtils;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.PagedListInfo;

import javax.portlet.*;
import java.sql.Connection;
import java.io.IOException;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    February 16, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class ProductCatalogPortlet extends GenericPortlet {

	//values are of the form ([y]ymmddhh)
	private final static String VIEW_PAGE1 = "/portlets/products/products_summary.jsp";
	private final static String VIEW_PAGE2 = "/portlets/products/product.jsp";

	/**
	 *  Description of the Field
	 */
	public final static String CATEGORY = "6050816";
	public final static String SHOW_SKU = "6051001";
	public final static String SKU_TEXT = "6051002";
	public final static String SHOW_PRICE = "6051003";
	public final static String PRICE_TEXT = "6051004";
	public final static String SHOW_PRICE_SAVINGS = "6051005";
  public final static String ORIGINAL_PRICE_TEXT = "6051007";
	public final static String PRICE_SAVINGS_TEXT = "6051006";



  /**
	 *  Description of the Method
	 *
	 *@param  portletConfig         Description of the Parameter
	 *@exception  PortletException  Description of the Exception
	 */
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);
	}


	/**
	 *  Description of the Method
	 *
	 *@exception  PortletException  Description of the Exception
	 */
	public void init() throws PortletException {
		super.init();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  request               Description of the Parameter
	 *@param  response              Description of the Parameter
	 *@exception  PortletException  Description of the Exception
	 *@exception  IOException       Description of the Exception
	 */
	public void doView(RenderRequest request, RenderResponse response)
			 throws PortletException, IOException {

		try {
			if (System.getProperty("DEBUG") != null) {
				System.out.println("ProductCatalogPortlet-> PortletMode: " + request.getPortletMode());
			}

			String viewType = (String) request.getParameter("viewType");
			if ("details".equals(viewType)) {
				buildProduct(request, response);

				PortletRequestDispatcher requestDispatcher =
						getPortletContext().getRequestDispatcher(VIEW_PAGE2);
				requestDispatcher.include(request, response);
			} else {
				buildProductCategoryList(request, response);

				PortletRequestDispatcher requestDispatcher =
						getPortletContext().getRequestDispatcher(VIEW_PAGE1);
				requestDispatcher.include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException("Exception in ProductCatalogPortlet");
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  request               Description of the Parameter
	 *@param  response              Description of the Parameter
	 *@exception  PortletException  Description of the Exception
	 *@exception  IOException       Description of the Exception
	 */
	public void processAction(ActionRequest request, ActionResponse response)
			 throws PortletException, IOException {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  request        Description of the Parameter
	 *@param  response       Description of the Parameter
	 *@exception  Exception  Description of the Exception
	 */
	private void buildProduct(RenderRequest request, RenderResponse response) throws Exception {
		Connection db = PortletUtils.getConnection(request);

		//Get Paged List handler for product catalog list
		PagedListInfo productCatalogListInfo = PortletUtils.getPagedListInfo(request, response, "productCatalogListInfo");
		productCatalogListInfo.setMode(PagedListInfo.DETAILS_VIEW);
		productCatalogListInfo.setCurrentOffset(request.getParameter("offset"));
		//setting the URL
    HashMap renderParams = new HashMap();
		renderParams.put("viewType", new String[]{"details"});
		renderParams.put("productId", new String[]{request.getParameter("productId")});
		renderParams.put("categoryId", new String[]{request.getParameter("categoryId")});
		renderParams.put("offset", new String[]{request.getParameter("offset")});
		renderParams.put("page", new String[]{request.getParameter("page")});
    request.setAttribute("page", (String) request.getParameter("page"));
System.out.println("ProductCatalogPortlet::buildProduct the offset is "+ request.getParameter("offset"));
		productCatalogListInfo.setRenderParameters(renderParams);

		//building a single item as a list
		ProductCatalogList productCatalogList = new ProductCatalogList();
		productCatalogList.setPagedListInfo(productCatalogListInfo);
		productCatalogList.setCategoryId(request.getParameter("categoryId"));
		productCatalogList.buildList(db);
		request.setAttribute("productCatalogList", productCatalogList);
		if (productCatalogList.size() > 0){
			request.setAttribute("productCatalog",(ProductCatalog) productCatalogList.get(0));
		}

		//fetching parent category for the specified product/category
		ProductCategory parentCategory = null;
		if (Integer.parseInt(request.getParameter("categoryId")) != -1) {
			parentCategory = new ProductCategory(db, Integer.parseInt(request.getParameter("categoryId")));
		} else {
			parentCategory = new ProductCategory();
		}
		request.setAttribute("parentCategory", parentCategory);

    // Retrieve the product preferences
    request.setAttribute("SHOW_SKU", (String) request.getPreferences().getValue(SHOW_SKU, "true"));
    request.setAttribute("SKU_TEXT", (String) request.getPreferences().getValue(SKU_TEXT, "Item #"));
    request.setAttribute("SHOW_PRICE", (String) request.getPreferences().getValue(SHOW_PRICE, "true"));
    request.setAttribute("PRICE_TEXT", (String) request.getPreferences().getValue(PRICE_TEXT, ""));
    request.setAttribute("SHOW_PRICE_SAVINGS", (String) request.getPreferences().getValue(SHOW_PRICE_SAVINGS, "true"));
    request.setAttribute("ORIGINAL_PRICE_TEXT", (String) request.getPreferences().getValue(ORIGINAL_PRICE_TEXT, "Original Price:"));
    request.setAttribute("PRICE_SAVINGS_TEXT", (String) request.getPreferences().getValue(PRICE_SAVINGS_TEXT, "Instant Savings:"));
  }


	/**
	 *  Description of the Method
	 *
	 *@param  request        Description of the Parameter
	 *@param  response       Description of the Parameter
	 *@exception  Exception  Description of the Exception
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
System.out.println("ProductCatalogPortlet::buildProductCategoryList the offset is "+ productCatalogListInfo.getCurrentOffset());
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
		request.setAttribute("productCatalogList", productCatalogList);
	}

}

