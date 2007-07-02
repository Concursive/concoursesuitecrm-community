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

import org.aspcfs.modules.website.utils.PortletUtils;
import org.aspcfs.utils.FolderUtils;

import javax.portlet.*;
import java.io.IOException;
import java.sql.Connection;

/**
 * spread sheet portlet
 */
public class SpreadsheetPortlet extends GenericPortlet {

  public final static String SELECT_FOLDER = "7031914";
  public final static String FOLDER_RECORD_RANGE = "7032114";
  public final static String ROWS_COLUMNS_COUNT = "7031923";
  public final static String CONFIGURE_SPREADSHEET = "7031924";
  public final static String SPREADSHEET_PREVIEW = "7031925";

  private final static String VIEW_PAGE1 = "/portlets/spreadsheet/spreadsheet_view.jsp";

  /**
   * render method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   */
  public void doView(RenderRequest request, RenderResponse response)
          throws PortletException, IOException {

    PortletRequestDispatcher requestDispatcher = null;
    int folderId = -1;
    int recordRange = 0;
    String rowsColumnsCount = null;
    String configureSpreadsheet = null;
    Connection db = null;
    String preview = null;
    String spreadsheet = null;
    folderId = Integer.parseInt(request.getPreferences().getValue(SELECT_FOLDER, "1"));
    recordRange = Integer.parseInt(request.getPreferences().getValue(FOLDER_RECORD_RANGE, "1"));
    rowsColumnsCount = request.getPreferences().getValue(ROWS_COLUMNS_COUNT, "1");
    configureSpreadsheet = request.getPreferences().getValue(CONFIGURE_SPREADSHEET, "");
    preview = request.getPreferences().getValue(SPREADSHEET_PREVIEW, "");

    try{

      db = PortletUtils.getConnection(request);
      spreadsheet = FolderUtils.buildSpreadSheet(configureSpreadsheet,folderId,recordRange,db);
      }catch(Exception e){
      e.printStackTrace();
  }
    request.setAttribute("spreadsheet", spreadsheet);
    request.setAttribute("rowsColumnsCount", rowsColumnsCount);
    requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE1);
    requestDispatcher.include(request, response);
  }
}


