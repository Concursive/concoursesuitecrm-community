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
import org.aspcfs.modules.website.base.PortfolioCategory;
import org.aspcfs.modules.website.base.PortfolioCategoryList;
import org.aspcfs.modules.website.base.PortfolioItem;
import org.aspcfs.modules.website.base.PortfolioItemList;
import org.aspcfs.modules.website.utils.PortletUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.web.PagedListInfo;

import javax.portlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 16, 2006 $Id: Exp $
 */
public class PortfolioPortlet extends GenericPortlet {

  //values are of the form ([y]ymmddhh)
  private final static String VIEW_PAGE1 = "/portlets/portfolio/portfolio_summary.jsp";
  private final static String VIEW_PAGE2 = "/portlets/portfolio/portfolio_item.jsp";

  /**
   * Description of the Field
   */
  public final static String CATEGORY = "6040316";


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
        System.out.println("PortfolioPortlet-> PortletMode: " + request.getPortletMode());
      }

      String viewType = (String) request.getParameter("viewType");
      if ("details".equals(viewType)) {
        buildPortfolioItem(request, response);

        PortletRequestDispatcher requestDispatcher =
          getPortletContext().getRequestDispatcher(VIEW_PAGE2);
        requestDispatcher.include(request, response);
      } else {
        buildPortfolioList(request, response);

        PortletRequestDispatcher requestDispatcher =
          getPortletContext().getRequestDispatcher(VIEW_PAGE1);
        requestDispatcher.include(request, response);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new IOException("Exception in PortfolioPortlet");
    }
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
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void buildPortfolioItem(RenderRequest request, RenderResponse response) throws Exception {
    Connection db = PortletUtils.getConnection(request);

    //Setting paged list
    PagedListInfo portfolioItemListInfo = PortletUtils.getPagedListInfo(request, response, "portfolioItemListInfo");
    portfolioItemListInfo.setMode(PagedListInfo.DETAILS_VIEW);
    portfolioItemListInfo.setCurrentOffset(request.getParameter("offset"));
    //Setting the URL
    HashMap renderParams = new HashMap();
    renderParams.put("viewType", new String[]{"details"});
    renderParams.put("itemId", new String[]{request.getParameter("itemId")});
    renderParams.put("categoryId", new String[]{request.getParameter("categoryId")});
    renderParams.put("offset", new String[]{request.getParameter("offset")});
    portfolioItemListInfo.setRenderParameters(renderParams);

    //building a single item as a list
    PortfolioItemList portfolioItemList = new PortfolioItemList();
    portfolioItemList.setPagedListInfo(portfolioItemListInfo);
    portfolioItemList.setCategoryId(request.getParameter("categoryId"));
    portfolioItemList.setEnabledOnly(Constants.TRUE);
    portfolioItemList.buildList(db);
    if (portfolioItemList.size() > 0) {
      request.setAttribute("portfolioItem", (PortfolioItem) portfolioItemList.get(0));
    }
  }


  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void buildPortfolioList(RenderRequest request, RenderResponse response) throws Exception {
    Connection db = PortletUtils.getConnection(request);

    String preferredCategoryId = request.getPreferences().getValue(CATEGORY, "-1");
    request.setAttribute("preferredCategoryId", preferredCategoryId);

    //fetching sub categories for the specified category
    PortfolioCategoryList portfolioCategoryList = new PortfolioCategoryList();
    portfolioCategoryList.setParentId(preferredCategoryId);
    if (StringUtils.hasText(request.getParameter("categoryId"))) {
      portfolioCategoryList.setParentId(request.getParameter("categoryId"));
    }
    portfolioCategoryList.setEnabledOnly(Constants.TRUE);
    portfolioCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("PortfolioPortlet.doView() + portfolioCategoryList.size()  ==> " + portfolioCategoryList.size());
    }
    request.setAttribute("portfolioCategoryList", portfolioCategoryList);

    //fetching parent category for the specified category
    PortfolioCategory parentCategory = null;
    if (portfolioCategoryList.getParentId() != -1) {
      parentCategory = new PortfolioCategory(db, portfolioCategoryList.getParentId());
    } else {
      parentCategory = new PortfolioCategory();
    }
    request.setAttribute("parentCategory", parentCategory);

    //Get Paged List handler
    PagedListInfo portfolioItemListInfo = PortletUtils.getPagedListInfo(request, response, "portfolioItemListInfo");
    portfolioItemListInfo.setMode(PagedListInfo.LIST_VIEW);
    //Setting URL
    HashMap renderParams = new HashMap();
    renderParams.put("viewType", new String[]{"list"});
    renderParams.put("categoryId", new String[]{String.valueOf(portfolioCategoryList.getParentId())});
    portfolioItemListInfo.setRenderParameters(renderParams);

    //fetching items for the specified category
    PortfolioItemList portfolioItemList = new PortfolioItemList();
    portfolioItemList.setPagedListInfo(portfolioItemListInfo);
    portfolioItemList.setCategoryId(preferredCategoryId);
    if (StringUtils.hasText(request.getParameter("categoryId"))) {
      portfolioItemList.setCategoryId(request.getParameter("categoryId"));
    }
    portfolioItemList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("PortfolioPortlet.doView() + portfolioItemList.size()  ==> " + portfolioItemList.size());
    }
    request.setAttribute("portfolioItemList", portfolioItemList);
  }

}

