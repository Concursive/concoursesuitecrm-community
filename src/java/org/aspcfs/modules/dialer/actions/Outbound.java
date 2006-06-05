package org.aspcfs.modules.dialer.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import net.sf.asterisk.manager.ManagerConnection;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.dialer.beans.CallClient;
import org.aspcfs.utils.AsteriskManager;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Oct 19, 2005
 */

public class Outbound extends CFSModule {

  public String executeCommandCall(ActionContext context) {
    // If user prefs not configured, or first call this session, then ask for prefs
    CallClient client = (CallClient) context.getFormBean();
    if (!client.isValid()) {
      return ("PrefsRequiredOK");
    } else {
      return ("PrefsOK");
    }
  }

  public String executeCommandPerformCall(ActionContext context) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
        "applicationPrefs");
    String response = "Error";
    CallClient client = (CallClient) context.getFormBean();
    String contextName = getPref(context, "ASTERISK.CONTEXT");
    try {
      SystemStatus systemStatus = this.getSystemStatus(context);
      ManagerConnection connection = AsteriskManager.verifyConnection(systemStatus, prefs, context.getServletContext());
      response = AsteriskManager.call(connection, client.getExtension(), client.getNumber(), contextName);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      // TODO: GO to a useful error page to let user know why call could not be made
      return ("SystemError");
    } finally {
      client.setLastResponse(response);
    }
    return ("PerformCallOK");
  }
}
