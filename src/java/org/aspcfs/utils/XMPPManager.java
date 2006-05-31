package org.aspcfs.utils;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SSLXMPPConnection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Oct 20, 2005
 */

public class XMPPManager {

  public static XMPPConnection verifyConnection(SystemStatus systemStatus, ApplicationPrefs prefs) {
    XMPPConnection xmppConnection = systemStatus.getXmppConnection();
    if (xmppConnection == null || !xmppConnection.isConnected()) {
      try {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("XMPPManager-> Connecting to XMPP Server: " + prefs.get("XMPP.CONNECTION.URL"));
        }
        if ("true".equals(prefs.get("XMPP.CONNECTION.SSL"))) {
          xmppConnection = new SSLXMPPConnection(prefs.get("XMPP.CONNECTION.URL"), Integer.parseInt(prefs.get("XMPP.CONNECTION.PORT")));
        } else {
          xmppConnection = new XMPPConnection(prefs.get("XMPP.CONNECTION.URL"), Integer.parseInt(prefs.get("XMPP.CONNECTION.PORT")));
        }
        xmppConnection.login(prefs.get("XMPP.MANAGER.USERNAME"), prefs.get("XMPP.MANAGER.PASSWORD"));
        xmppConnection.getRoster().setSubscriptionMode(Roster.SUBSCRIPTION_ACCEPT_ALL);
        Presence presence = new Presence(Presence.Type.UNAVAILABLE);
        presence.setMode(Presence.Mode.AWAY);
        presence.setStatus("Monitoring Centric CRM");
        xmppConnection.sendPacket(presence);
      } catch (Exception e) {
        e.printStackTrace(System.out);
        System.out.println(
            "XMPPManager-> XMPP Error: " + e.getMessage());
      }
    }
    return xmppConnection;
  }

  public static void removeConnection(SystemStatus systemStatus) {
    XMPPConnection xmppConnection = systemStatus.getXmppConnection();
    if (xmppConnection != null) {
      if (xmppConnection.isConnected()) {
        xmppConnection.close();
        if (System.getProperty("DEBUG") != null) {
          System.out.println("XMPPManager-> XMPP Logged out");
        }
      }
      xmppConnection = null;
    }
  }
}
