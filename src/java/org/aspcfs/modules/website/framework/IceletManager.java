package org.aspcfs.modules.website.framework;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.login.beans.UserBean;
import org.apache.pluto.PortletContainer;
import org.apache.pluto.PortletContainerException;
import org.apache.pluto.driver.core.PortalEnvironment;
import org.apache.pluto.driver.core.PortalServletRequest;
import org.apache.pluto.driver.core.PortalServletResponse;
import org.apache.pluto.driver.core.PortletWindowImpl;
import org.apache.pluto.driver.services.portal.PortletWindowConfig;
import org.apache.pluto.driver.url.PortalURL;
import org.aspcfs.modules.website.base.*;

import javax.portlet.PortletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.HashMap;
import org.quartz.Scheduler;

/**
 *  Server side preparation for portlets
 *
 * @author     mrajkowski
 * @created    Feb 27, 2006
 * @version    $Id: Exp $
 */

public class IceletManager {

  private PortletContainer container = null;
  private IceletRegistryService registryService = new IceletRegistryService();


  /**
   *  Constructor for the IceletManager object
   */
  public IceletManager() { }


  /**
   *  Constructor for the IceletManager object
   *
   * @param  context  Description of the Parameter
   */
  public IceletManager(ServletContext context) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IceletManager-> Initializing the container");
    }
    // Reference for executing portlets
    container = (PortletContainer) context.getAttribute("PortletContainer");
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IceletManager-> Initializing the registry service");
    }
    // Register the available portlets
    registryService.init(context);
  }


  /**
   *  Gets the manager attribute of the IceletManager class
   *
   * @param  actionContext  Description of the Parameter
   * @return                The manager value
   */
  public static synchronized IceletManager getManager(ActionContext actionContext) {
    ServletContext context = actionContext.getServletContext();
    IceletManager manager = (IceletManager) context.getAttribute("iceletManager");
    if (manager == null || !manager.isConfigured()) {
      context.setAttribute("ContextPath", actionContext.getRequest().getContextPath());
      manager = new IceletManager(context);
      context.setAttribute("iceletManager", manager);
    }
    return manager;
  }


  /**
   *  Gets the configured attribute of the IceletManager object
   *
   * @return    The configured value
   */
  public boolean isConfigured() {
    return container != null;
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   */
  public static void destroy(ServletContext context) {
    IceletManager manager = (IceletManager) context.getAttribute("iceletManager");
    if (manager != null) {
      manager = null;
      context.removeAttribute("iceletManager");
    }
  }


  /**
   *  Description of the Method
   *
   * @param  context        Description of the Parameter
   * @param  site           Description of the Parameter
   * @param  tabId          Description of the Parameter
   * @param  pageId         Description of the Parameter
   * @param  db             Description of the Parameter
   * @return                Description of the Return Value
   * @exception  Exception  Description of the Exception
   */
  public boolean prepare(ActionContext context, Site site, int tabId, int pageId, Connection db) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IceletManager-> Preparing portlets...");
    }

    // Setup the portlets on this page
    PortalEnvironment portalEnvironment = new PortalEnvironment(
        context.getRequest(), context.getResponse());
    PortalURL portalURL = portalEnvironment.getRequestedPortalURL();

    // Does not work because the PortalServletRequest looks for PortalEnvironment
    //IceletEnvironment portalEnvironment = new IceletEnvironment(context.getRequest(), context.getResponse());
    //PortalURL portalURL = IceletURLFactory.getFactory().createPortalURL(context.getRequest());

    // Check to see if there is an action to process
    String actionWindowId = portalURL.getActionWindow();

    Tab tab = site.getTabToDisplay();
    Page page = site.getTabToDisplay().getThisPageToBuild();
    // This is the current page
    boolean result = false;
    PageVersion pageVersion = page.getPageVersionToView();
    Iterator pageRowIterator = pageVersion.getPageRowList().iterator();
    while (pageRowIterator.hasNext()) {
      PageRow pageRow = (PageRow) pageRowIterator.next();
      Iterator rowColumnIterator = pageRow.getRowColumnList().iterator();
      while (rowColumnIterator.hasNext()) {
        RowColumn rowColumn = (RowColumn) rowColumnIterator.next();
        HashMap subColumns = new HashMap();
        rowColumn.buildSubColumns(subColumns);
        if (rowColumn.getIcelet() != null) {
          result = this.buildIceletDetails(context, db, rowColumn, portalURL, portalEnvironment, actionWindowId);
          if (result) {
            return true;
          }
        } else if (subColumns.size() > 0) {
          Iterator iter = (Iterator) subColumns.keySet().iterator();
          while (iter.hasNext()) {
            RowColumn subRowColumn = (RowColumn) subColumns.get(iter.next());
            if (subRowColumn.getIcelet() != null) {
              result = this.buildIceletDetails(context, db, subRowColumn, portalURL, portalEnvironment, actionWindowId);
              if (result) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of the Parameter
   * @param  rowColumn      Description of the Parameter
   * @exception  Exception  Description of the Exception
   */
  public boolean buildIceletDetails(ActionContext context, Connection db, RowColumn rowColumn, 
                                PortalURL portalURL, PortalEnvironment portalEnvironment,String actionWindowId) throws Exception {
    Icelet thisIcelet = rowColumn.getIcelet();
    // Get the portlet
    PortletWindowConfig windowConfig = registryService.getPortlet(rowColumn.getId() + "." + thisIcelet.getConfiguratorClass());
    // Get the specific portletWindow
    PortletWindowImpl portletWindow = new PortletWindowImpl(
        windowConfig, portalURL, rowColumn.getId());
    // Determine if this is a RENDER or ACTION method
    if (actionWindowId != null) {
      // Pass the request to the specified doAction
      if (actionWindowId.equals(portletWindow.getId().getStringId())) {
        try {
					Scheduler scheduler = (Scheduler) context.getServletContext().getAttribute("Scheduler");
          context.getRequest().setAttribute("scheduler", scheduler);

					UserBean userBean = (UserBean)context.getRequest().getSession().getAttribute("User");
          context.getRequest().setAttribute("userBean", userBean);
					
          context.getRequest().setAttribute("connection", db);
          container.doAction(portletWindow, context.getRequest(), context.getResponse());
        } catch (PortletContainerException ex) {
          throw new ServletException(ex);
        } catch (PortletException ex) {
          throw new ServletException(ex);
        }
        if (System.getProperty("DEBUG") != null) {
          System.out.println("IceletManager-> Action request processed.");
        }
        return true;
      }
    } else {
      // Render the portlet internally before display
      try {
        PortalServletRequest portalRequest = new PortalServletRequest(
            context.getRequest(), portletWindow);
				Scheduler scheduler = (Scheduler) context.getServletContext().getAttribute("Scheduler");
				portalRequest.setAttribute("scheduler", scheduler);

				UserBean userBean = (UserBean)context.getRequest().getSession().getAttribute("User");
				portalRequest.setAttribute("userBean", userBean);

        portalRequest.setAttribute("connection", db);
        PortalServletResponse portalResponse = new PortalServletResponse(
            context.getResponse());
        container.doRender(portletWindow, portalRequest, portalResponse);
        context.getRequest().setAttribute("portal_response_" + rowColumn.getId(), portalResponse);
      } catch (Exception e) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("JspClass-> editor_site: Error loading icelet: " + e.getMessage());
          e.printStackTrace(System.out);
        }
      }
    }
    return false;
  }


  /**
   *  Gets the container attribute of the IceletManager object
   *
   * @return    The container value
   */
  public PortletContainer getContainer() {
    return container;
  }


  /**
   *  Gets the registryService attribute of the IceletManager object
   *
   * @return    The registryService value
   */
  public IceletRegistryService getRegistryService() {
    return registryService;
  }
}

