package org.aspcfs.utils;

import net.sf.asterisk.manager.ManagerConnection;
import net.sf.asterisk.manager.ManagerConnectionFactory;
import net.sf.asterisk.manager.TimeoutException;
import net.sf.asterisk.manager.action.OriginateAction;
import net.sf.asterisk.manager.response.ManagerResponse;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Oct 20, 2005
 */

public class AsteriskManager {

  public static ManagerConnection verifyConnection(SystemStatus systemStatus, ApplicationPrefs prefs, ServletContext context) {
    ManagerConnection asteriskConnection = systemStatus.getAsteriskConnection();
    if (asteriskConnection == null || !asteriskConnection.isConnected()) {
      try {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("AsteriskManager-> Connecting to Asterisk Server");
        }
        ManagerConnectionFactory factory = new ManagerConnectionFactory();
        asteriskConnection = factory.getManagerConnection(
            prefs.get("ASTERISK.URL"),
            prefs.get("ASTERISK.USERNAME"),
            prefs.get("ASTERISK.PASSWORD")
        );
        asteriskConnection.login();
        if ("true".equals(prefs.get("ASTERISK.INBOUND.ENABLED"))) {
          AsteriskListener listener = systemStatus.getAsteriskListener();
          if (listener == null) {
            listener = new AsteriskListener();
            listener.setManagerConnection(asteriskConnection);
            listener.setSystemStatus(systemStatus);
            listener.setServletContext(context);
            listener.setApplicationPrefs(prefs);
            listener.start();
            systemStatus.setAsteriskListener(listener);
          }
        }
      } catch (Exception e) {
        e.printStackTrace(System.out);
        System.out.println(
            "AsteriskManager-> Connection Error: " + e.getMessage());
      }
    }
    return asteriskConnection;
  }

  public static void removeConnection(SystemStatus systemStatus) {
    AsteriskListener listener = systemStatus.getAsteriskListener();
    if (listener != null) {
      listener.setShouldRun(false);
      listener = null;
      systemStatus.setAsteriskListener(null);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AsteriskManager-> Listener stopped");
      }
    }
    ManagerConnection asteriskConnection = systemStatus.getAsteriskConnection();
    if (asteriskConnection != null) {
      if (asteriskConnection.isConnected()) {
        try {
          asteriskConnection.logoff();
          if (System.getProperty("DEBUG") != null) {
            System.out.println("AsteriskManager-> Asterisk Logout");
          }
        } catch (Exception e) {
          // Timeout
        }
      }
      asteriskConnection = null;
    }
  }

  public static String call(ManagerConnection connection, String extension, String phoneNumber, String context) throws IOException,
      TimeoutException {
    OriginateAction originateAction = new OriginateAction();
    originateAction.setChannel("SIP/" + extension);
    originateAction.setContext(context);
    originateAction.setExten(StringUtils.getNumbersOnly(phoneNumber));
    originateAction.setCallerId(extension);
    originateAction.setPriority(new Integer(1));
    originateAction.setAsync(new Boolean(true));
    ManagerResponse originateResponse = connection.sendAction(originateAction, 30000);
    return originateResponse.getResponse();
  }
}
