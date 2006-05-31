package org.aspcfs.modules.website.icelet;

import javax.portlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id:  Exp $
 * @created Mar 7, 2006
 */

public class AboutPortlet extends GenericPortlet {
  private static final String VIEW_PAGE = "/portlets/about/about-view.jsp";
  private static final String EDIT_PAGE = "/portlets/about/about-edit.jsp";
  private static final String HELP_PAGE = "/portlets/about/about-help.jsp";

  // GenericPortlet Impl -----------------------------------------------------

  public void init(PortletConfig portletConfig) throws PortletException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AboutPortlet-> Constructor(PortletConfig portletConfig)");
    }
    super.init(portletConfig);
  }

  public void init() throws PortletException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AboutPortlet-> Constructor");
    }
    super.init();
  }

  public void doView(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AboutPortlet-> doView");
    }

    // Generate a URL
    //PortletURL renderURL = response.createRenderURL();
    //renderURL.setParameter("name1", "value1");
    //renderURL.setParameter("name2", "value2");
    //String url = renderURL.toString();

    // Write directly
    //response.setContentType("text/html");
    //PrintWriter out = response.getWriter();
    //out.println("Hello World!");

    // Send a redirect
    //response.setPortletMode(PortletMode.VIEW);
    //response.sendRedirect("/");

    // Dispatch to JSP
    PortletContext context = getPortletContext();

    PortletRequestDispatcher requestDispatcher =
        context.getRequestDispatcher(VIEW_PAGE);

    requestDispatcher.include(request, response);

  }

  protected void doEdit(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AboutPortlet-> doEdit");
    }
    PortletContext context = getPortletContext();
    PortletRequestDispatcher requestDispatcher =
        context.getRequestDispatcher(EDIT_PAGE);
    requestDispatcher.include(request, response);
  }

  protected void doHelp(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AboutPortlet-> doHelp");
    }
    PortletContext context = getPortletContext();
    PortletRequestDispatcher requestDispatcher =
        context.getRequestDispatcher(HELP_PAGE);
    requestDispatcher.include(request, response);
  }

  public void processAction(ActionRequest request, ActionResponse response)
       throws PortletException, IOException{
    if (System.getProperty("DEBUG") != null) {
      System.out.println("AboutPortlet-> processAction");
    }
    //response.setPortletMode(PortletMode.VIEW);
    //response.sendRedirect("/");
  }
}
