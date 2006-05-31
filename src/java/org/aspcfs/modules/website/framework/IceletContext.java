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
package org.aspcfs.modules.website.framework;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

/**
 * Icelet context stores information that allows icelets to be rendered and
 * has common methods that can be used.
 *
 * @author mrajkowski
 * @version $Id: Exp $
 * @created February 21, 2006
 */
public class IceletContext extends HashMap implements PortalContext {

  private String info = "Centric CRM Portal 1.0";
  private HashMap properties = new HashMap();
  private Vector portletModes = null;
  private Vector windowStates = null;

  public String getProperty(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Property name == null");
    }
    return (String) properties.get(name);
  }

  public Enumeration getPropertyNames() {
    Vector names = new Vector(properties.keySet());
    return names.elements();
  }

  public Enumeration getSupportedPortletModes() {
    if (portletModes == null) {
      portletModes = new Vector();
      portletModes.add(new PortletMode("view"));
      portletModes.add(new PortletMode("edit"));
      portletModes.add(new PortletMode("help"));
      portletModes.add(new PortletMode("config"));
    }
    return portletModes.elements();
  }

  public Enumeration getSupportedWindowStates() {
    if (windowStates == null) {
      windowStates = new Vector();
      windowStates.add(new WindowState("normal"));
      windowStates.add(new WindowState("maximized"));
      windowStates.add(new WindowState("minimized"));
    }
    return windowStates.elements();
  }

  public String getPortalInfo() {
    return info;
  }

  public void setProperty(String name, String value) {
    if (name == null) {
      throw new IllegalArgumentException("Property name == null");
    }
    properties.put(name, value);
  }
}

