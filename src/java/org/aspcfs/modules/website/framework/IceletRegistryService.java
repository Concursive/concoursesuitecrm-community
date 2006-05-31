package org.aspcfs.modules.website.framework;

import org.apache.pluto.driver.config.DriverConfigurationException;
import org.apache.pluto.driver.services.portal.PortletApplicationConfig;
import org.apache.pluto.driver.services.portal.PortletRegistryService;
import org.apache.pluto.driver.services.portal.PortletWindowConfig;
import org.apache.pluto.driver.services.portal.admin.DriverAdministrationException;
import org.apache.pluto.driver.services.portal.admin.PortletRegistryAdminService;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id:  Exp $
 * @created Mar 6, 2006
 */

public class IceletRegistryService implements PortletRegistryService, PortletRegistryAdminService {

  private PortletApplicationConfig app = null;
  private ServletContext servletContext = null;

  public IceletRegistryService() {}

  public void init(ServletContext context) throws DriverConfigurationException {
    servletContext = context;
    if (app == null) {
      app = new PortletApplicationConfig();
      String contextPath = (String) context.getAttribute("ContextPath");
      if (contextPath == null) {
        contextPath = "";
      }
      app.setContextPath(contextPath + "/PlutoInvoker");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("IceletRegistryService-> app contextPath: " + app.getContextPath());
      }
      try {
        // Read the list of portlets from portlet.xml and add a single config for each one
        XMLUtils xml = new XMLUtils(context, "WEB-INF/portlet.xml");
        ArrayList portlets = new ArrayList();
        XMLUtils.getAllChildren(xml.getDocumentElement(), "portlet", portlets);
        Iterator i = portlets.iterator();
        while (i.hasNext()) {
          Element thisPortlet = (Element) i.next();
          PortletWindowConfig portletWindowConfig = new PortletWindowConfig();
          portletWindowConfig.setPortletName(XMLUtils.getNodeText(XMLUtils.getFirstElement(thisPortlet, "portlet-name")));
          if (System.getProperty("DEBUG") != null) {
            System.out.println("IceletRegistryService-> Adding portlet: " + portletWindowConfig.getPortletName());
          }
          app.addPortlet(portletWindowConfig);
        }
      } catch (Exception e) {
        throw new DriverConfigurationException(e);
      }
    }
  }

  public void destroy() throws DriverConfigurationException {
    app = null;
    servletContext = null;
  }

  public Collection getPortlets() {
    return app.getPortlets();
  }

  public Set getPortletApplications() {
    return new HashSet(app.getPortlets());
  }

  public PortletApplicationConfig getPortletApplication(String string) {
    return app;
  }

  public PortletWindowConfig getPortlet(String id) {
    if (id == null) {
      return null;
    }
    // TODO: Implement multiple application contexts
    String uniqueId = getContextFromPortletId(id);
    String portlet = getPortletNameFromPortletId(id);
    if (app == null) {
      return null;
    }
    PortletWindowConfig thisPortlet = app.getPortlet(portlet);
    return thisPortlet;
  }

  public void addPortletApplication(String contextPath) throws DriverConfigurationException {
    // NOTE: Currently not used.
    // TODO: Update for dynamic deployment
    if (contextPath == null) {
      throw new IllegalArgumentException(
          "Portlet application context path cannot be null.");
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IceletRegistryService-> addPortletApplication: " + contextPath);
    }
    try {
      PortletApplicationConfig portletAppConfig = new PortletApplicationConfig();
      portletAppConfig.setContextPath(contextPath);

      ServletContext portletAppServletContext = servletContext.getContext(contextPath);
      if (portletAppServletContext == null) {
        throw new DriverAdministrationException(
            "Unable to locate servlet context: " + contextPath
                + ": ensure that crossContext support is enabled "
                + "and the portlet application has been deployed.");
      }
    } catch (Exception ex) {
      throw new DriverConfigurationException(
          "Unable to add portlet application from " + contextPath, ex);
    }
  }


  private String getContextFromPortletId(String portletId) {
    int idx = portletId.indexOf(".");
    if (idx < 0) {
      return null;
    }
    return portletId.substring(0, idx);
  }

  private String getPortletNameFromPortletId(String portletId) {
    int idx = portletId.indexOf(".");
    if (idx < 0) {
      return null;
    }
    return portletId.substring(idx + 1);
  }
}
