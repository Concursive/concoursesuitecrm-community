package org.aspcfs.modules.website.framework;

import org.apache.pluto.spi.PortalCallbackService;
import org.apache.pluto.spi.PortletURLProvider;
import org.apache.pluto.spi.ResourceURLProvider;
import org.apache.pluto.PortletWindow;
import org.apache.pluto.driver.services.container.PortletURLProviderImpl;
import org.apache.pluto.driver.core.ResourceURLProviderImpl;
import org.apache.pluto.driver.AttributeKeys;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Collections;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id:  Exp $
 * @created Mar 1, 2006
 */

public class IceletCallbackService implements PortalCallbackService  {

  public void setTitle(HttpServletRequest request, PortletWindow portletWindow, String title) {
    request.setAttribute("org.apache.pluto.driver.DynamicPortletTitle", title);
  }

  public PortletURLProvider getPortletURLProvider(HttpServletRequest request, PortletWindow portletWindow) {
    // Does not work because the Pluto code looks for PortalEnvironment
    //return new IceletURLProvider(request, portletWindow);
    return new PortletURLProviderImpl(request, portletWindow);
  }

  public ResourceURLProvider getResourceURLProvider(HttpServletRequest request, PortletWindow portletWindow) {
    // Does not work because the Pluto code looks for PortalEnvironment
    //return new IceletResourceURLProvider(request, portletWindow);
    return new ResourceURLProviderImpl(request, portletWindow);
  }

  public Map getRequestProperties(HttpServletRequest httpServletRequest, PortletWindow portletWindow) {
    // TODO: currently this method returns an empty map.
    return Collections.EMPTY_MAP;
  }

  public void setResponseProperty(HttpServletRequest httpServletRequest, PortletWindow portletWindow, String string, String string1) {
    // TODO: currently this method does nothing.
  }

  public void addResponseProperty(HttpServletRequest httpServletRequest, PortletWindow portletWindow, String string, String string1) {
    // TODO: currently this method does nothing.
  }
}
