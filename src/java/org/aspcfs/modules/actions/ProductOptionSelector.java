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
package org.aspcfs.modules.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.util.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.modules.products.base.ProductOption;
import org.aspcfs.modules.products.base.ProductOptionList;
import org.aspcfs.modules.base.FilterList;
import org.aspcfs.modules.base.Filter;
import org.aspcfs.modules.base.Constants;

/**
 *  Creates a List of Product Options for display within a popup <br>
 *  Can be used in two variants: Single/Multiple<br>
 *  Single and Multiple define if multiple options can be selected or just a
 *  single one
 *
 *@author     ananth
 *@created    October 8, 2004
 *@version    $Id$
 */
public final class ProductOptionSelector extends CFSModule {
  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandListProductOptions(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    boolean listDone = false;
    String listType = context.getRequest().getParameter("listType");
    ProductOptionList optionList = null;
    ProductOptionList finalOptions = null;
    ArrayList selectedList = (ArrayList) context.getRequest().getAttribute("SelectedOptions");

    if (selectedList == null || "true".equals(context.getRequest().getParameter("reset"))) {
      selectedList = new ArrayList();
    }

    String prevSelection = context.getRequest().getParameter("previousSelection");
    if (prevSelection != null) {
      StringTokenizer st = new StringTokenizer(prevSelection, "|");
      while (st.hasMoreTokens()) {
        selectedList.add(String.valueOf(st.nextToken()));
      }
    }

    try {
      db = this.getConnection(context);
      int rowCount = 1;
      optionList = new ProductOptionList();

      if ("list".equals(listType)) {
        while (context.getRequest().getParameter("hiddenOptionId" + rowCount) != null) {
          int optionId = Integer.parseInt(context.getRequest().getParameter("hiddenOptionId" + rowCount));
          if (context.getRequest().getParameter("option" + rowCount) != null) {
            if (!selectedList.contains(String.valueOf(optionId))) {
              selectedList.add(String.valueOf(optionId));
            }
          } else {
            selectedList.remove(String.valueOf(optionId));
          }
          rowCount++;
        }
      }

      if ("true".equals((String) context.getRequest().getParameter("finalsubmit"))) {
        //Handle single selection case
        if ("single".equals(listType)) {
          rowCount = Integer.parseInt(context.getRequest().getParameter("rowcount"));
          int optionId = Integer.parseInt(context.getRequest().getParameter("hiddenOptionId" + rowCount));
          selectedList.clear();
          selectedList.add(String.valueOf(optionId));
        }
        listDone = true;
        if (finalOptions == null) {
          finalOptions = new ProductOptionList();
        }
        for (int i = 0; i < selectedList.size(); i++) {
          int optionId = Integer.parseInt((String) selectedList.get(i));
          finalOptions.add(new ProductOption(db, optionId));
        }
      }

      //set ProductOptionList parameters and build the list
      setParameters(optionList, context);
      optionList.buildList(db);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      context.getRequest().setAttribute("OptionList", optionList);
      context.getRequest().setAttribute("SelectedOptions", selectedList);
      if (listDone) {
        context.getRequest().setAttribute("FinalOptions", finalOptions);
      }
      return ("ListOptionsOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }


  /**
   *  Sets the parameters attribute of the ProductOptionSelector object
   *
   *@param  optionList  The new parameters value
   *@param  context     The new parameters value
   */
  private void setParameters(ProductOptionList optionList, ActionContext context) {
    //check if a text based filter was entered
    String optionName = context.getRequest().getParameter("optionName");
    //String optionSku = context.getRequest().getParameter("optionSku");
    if (optionName != null) {
      if (!"Product Name".equals(optionName) && !"".equals(optionName.trim())) {
        optionList.setName("%" + optionName + "%");
      }
    }
    /*
     *  if (optionSku != null) {
     *  if (!"Product SKU".equals(optionSku) && !"".equals(optionSku.trim())) {
     *  optionList.setSku(optionSku + "%");
     *  }
     *  }
     */
    if ("true".equals(context.getRequest().getParameter("reset"))) {
      context.getSession().removeAttribute("OptionListInfo");
    }

    PagedListInfo optionListInfo = this.getPagedListInfo(context, "OptionListInfo");
    //add filters
    optionList.setPagedListInfo(optionListInfo);
  }
}

