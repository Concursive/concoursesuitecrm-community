package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public class BrowserHandler extends TagSupport {
  private String browserId = null;
  private double minVersion = -1;
  private double maxVersion = -1;
  private String os = null;
  private boolean include = true;


  /**
   *  Sets the id attribute of the BrowserHandler object
   *
   *@param  tmp  The new id value
   */
  public final void setId(String tmp) {
    browserId = tmp;
  }


  /**
   *  Sets the minVersion attribute of the BrowserHandler object
   *
   *@param  tmp  The new minVersion value
   */
  public final void setMinVersion(String tmp) {
    minVersion = Double.parseDouble(tmp);
  }


  /**
   *  Sets the maxVersion attribute of the BrowserHandler object
   *
   *@param  tmp  The new maxVersion value
   */
  public final void setMaxVersion(String tmp) {
    maxVersion = Double.parseDouble(tmp);
  }


  /**
   *  Sets the os attribute of the BrowserHandler object
   *
   *@param  tmp  The new os value
   */
  public void setOs(String tmp) {
    this.os = tmp;
  }


  /**
   *  Sets the include attribute of the BrowserHandler object
   *
   *@param  tmp  The new include value
   */
  public final void setInclude(String tmp) {
    include = tmp.equalsIgnoreCase("true");
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public final int doStartTag() throws JspException {
    UserBean thisUser = (UserBean) pageContext.getSession().getAttribute("User");

    if (thisUser.getBrowserId().equalsIgnoreCase(browserId)) {
      if (include) {
        if (versionPasses(thisUser.getBrowserVersion()) && osPasses(thisUser.getClientType().getOsString())) {
          return EVAL_BODY_INCLUDE;
        } else {
          return SKIP_BODY;
        }
      } else {
        if (versionPasses(thisUser.getBrowserVersion()) && osPasses(thisUser.getClientType().getOsString())) {
          return SKIP_BODY;
        } else {
          return EVAL_BODY_INCLUDE;
        }
      }
    } else {
      if (include) {
        return SKIP_BODY;
      } else {
        return EVAL_BODY_INCLUDE;
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  userVersion  Description of the Parameter
   *@return              Description of the Return Value
   */
  private boolean versionPasses(double userVersion) {
    if ((minVersion == -1 || userVersion >= minVersion) &&
        (maxVersion == -1 || userVersion <= maxVersion)) {
      return true;
    } else {
      return false;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  userOs  Description of the Parameter
   *@return         Description of the Return Value
   */
  private boolean osPasses(String userOs) {
    if (os != null) {
      return (os.equals(userOs));
    } else {
      return true;
    }
  }
}

