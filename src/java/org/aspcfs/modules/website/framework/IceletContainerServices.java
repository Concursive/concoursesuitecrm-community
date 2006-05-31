package org.aspcfs.modules.website.framework;

import org.apache.pluto.OptionalContainerServices;
import org.apache.pluto.RequiredContainerServices;
import org.apache.pluto.internal.InternalPortletWindow;
import org.apache.pluto.spi.PortalCallbackService;
import org.apache.pluto.spi.optional.PortletEnvironmentService;
import org.apache.pluto.spi.optional.PortletInvokerService;
import org.apache.pluto.spi.optional.PortletPreferencesService;

import javax.portlet.PortalContext;

/**
 * These methods are used when the container is executing doAction()
 *
 * @author mrajkowski
 * @version $Id:  Exp $
 * @created Mar 1, 2006
 */

public class IceletContainerServices implements RequiredContainerServices, OptionalContainerServices {

  private IceletContext context = new IceletContext();
  private IceletCallbackService callbackService = new IceletCallbackService();
  private IceletPreferencesService preferencesService = new IceletPreferencesService();

  public IceletContainerServices() {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IceletContainerServices-> Constructor");
    }
  }

  // Required
  public PortalContext getPortalContext() {
    return context;
  }

  // Required
  public PortalCallbackService getPortalCallbackService() {
    return callbackService;
  }

  public PortletPreferencesService getPortletPreferencesService() {
    return preferencesService;
  }

  public PortletEnvironmentService getPortletEnvironmentService() {
    // NOTE: Not implemented in Pluto
    return null;
  }

  public PortletInvokerService getPortletInvokerService(InternalPortletWindow internalPortletWindow) {
    // NOTE: Not implemented in Pluto
    return null;
  }
}
