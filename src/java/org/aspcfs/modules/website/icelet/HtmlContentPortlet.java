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

import javax.portlet.*;
import java.io.IOException;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: Exp $
 * @created April 28, 2006
 */
public class HtmlContentPortlet extends GenericPortlet {

  public static String PROPERTY_HTMLTEXT = "6022301";

  private static final String VIEW_PAGE = "/portlets/html_content/html_content-view.jsp";
  private static final String CONFIG_PAGE = "/portlets/html_content/html_content-config.jsp";

  public HtmlContentPortlet() {
  }

  public void doView(RenderRequest request, RenderResponse response)
    throws PortletException, IOException {

    if (System.getProperty("DEBUG") != null) {
      System.out.println("HtmlContentPortlet-> PortletMode: " + request.getPortletMode());
    }

    if (request.getPortletMode() == PortletMode.VIEW) {
      // Show the HTML content
      request.setAttribute("content", (String) request.getPreferences().getValue(PROPERTY_HTMLTEXT, "This is the default content."));
      PortletRequestDispatcher requestDispatcher =
        getPortletContext().getRequestDispatcher(VIEW_PAGE);
      requestDispatcher.include(request, response);
    } else if (request.getPortletMode() == new PortletMode("config")) {
      // Show the HTML content editor
      // TODO: Use the PROPERTY_HTMLTEXT
      PortletRequestDispatcher requestDispatcher =
        getPortletContext().getRequestDispatcher(CONFIG_PAGE);
      requestDispatcher.include(request, response);
    }
  }

  public void processAction(ActionRequest request, ActionResponse response)
    throws PortletException, IOException {

  }
}

