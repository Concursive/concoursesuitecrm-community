package org.aspcfs.modules.website.framework;

import org.apache.pluto.PortletContainerException;
import org.apache.pluto.PortletWindow;
import org.apache.pluto.internal.InternalPortletPreference;
import org.apache.pluto.internal.impl.PortletPreferenceImpl;
import org.apache.pluto.spi.optional.PortletPreferencesService;
import org.aspcfs.modules.website.base.IceletProperty;
import org.aspcfs.modules.website.base.IceletPropertyMap;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Description
 *
 * @author mrajkowski, from org.apache.pluto.core.DefaultPortletPreferencesService
 * @version $Id:  Exp $
 * @created Mar 2, 2006
 */

public class IceletPreferencesService implements PortletPreferencesService {

  private Map storage = new HashMap();

  public IceletPreferencesService() {
  }


  public InternalPortletPreference[] getStoredPreferences(PortletWindow portletWindow, PortletRequest request) throws PortletContainerException {
    String key = getFormattedKey(portletWindow);
    InternalPortletPreference[] preferences =
      (InternalPortletPreference[]) storage.get(key);
    if (preferences == null) {
      // Get properties for this site in DB or cached SystemStatus
      Connection db = (Connection) request.getAttribute("connection");
      if (db != null) {
        try {
          if (System.getProperty("DEBUG") != null) {
            System.out.println("IceletPreferencesService-> Building IceletPropertyMap");
          }
          IceletPropertyMap propertyMap = new IceletPropertyMap();
          propertyMap.setIceletRowColumnId(key);
          propertyMap.buildList(db);
          InternalPortletPreference[] prefs = new InternalPortletPreference[propertyMap.size()];
          Iterator i = propertyMap.values().iterator();
          int count = -1;
          while (i.hasNext()) {
            IceletProperty thisProperty = (IceletProperty) i.next();
            ++count;
            InternalPortletPreference thisPref = new PortletPreferenceImpl(String.valueOf(thisProperty.getTypeConstant()), new String[]{thisProperty.getValue()});
            prefs[count] = thisPref;
          }
          return prefs;
        } catch (SQLException se) {
          throw new PortletContainerException("IceletPreferencesService-> SQLError: " + se.getMessage());
        }
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("IceletPreferencesService-> Returning empty prefs because database connection is null");
      }
      return new InternalPortletPreference[0];
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("IceletPreferencesService-> Returning clone of prefs");
      }
      return clonePreferences(preferences);
    }
  }

  public void store(PortletWindow portletWindow, PortletRequest request, InternalPortletPreference[] preferences) throws PortletContainerException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("IceletPreferencesService-> PortletMode: " + portletWindow.getPortletMode());
    }
    if (portletWindow.getPortletMode() != PortletMode.VIEW) {
      String key = getFormattedKey(portletWindow);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("IceletPreferencesService-> Storing preferences for: " + key);
      }


      storage.put(key, clonePreferences(preferences));
    }
  }

  private String getFormattedKey(PortletWindow portletWindow) {
    String uniqueKey = portletWindow.getId().getStringId();
    return (uniqueKey.substring(uniqueKey.lastIndexOf("_") + 1));
  }

  private InternalPortletPreference[] clonePreferences(
    InternalPortletPreference[] preferences) {
    if (preferences == null) {
      return null;
    }
    InternalPortletPreference[] copy =
      new InternalPortletPreference[preferences.length];
    for (int i = 0; i < preferences.length; i++) {
      if (preferences[i] != null) {
        copy[i] = (InternalPortletPreference) preferences[i].clone();
      } else {
        copy[i] = null;
      }
    }
    return copy;
  }
}
