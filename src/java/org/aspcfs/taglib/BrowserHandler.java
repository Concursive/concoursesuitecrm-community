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
package org.aspcfs.taglib;

import org.aspcfs.modules.login.beans.UserBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id: BrowserHandler.java,v 1.4 2003/03/10 15:22:05 mrajkowski Exp
 *          $
 * @created January 15, 2003
 */
public class BrowserHandler extends TagSupport implements TryCatchFinally {
  private String browserId = null;
  private double minVersion = -1;
  private double maxVersion = -1;
  private String os = null;
  private boolean include = true;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    browserId = null;
    minVersion = -1;
    maxVersion = -1;
    os = null;
    include = true;
  }

  /**
   * Sets the id attribute of the BrowserHandler object
   *
   * @param tmp The new id value
   */
  public final void setId(String tmp) {
    browserId = tmp;
  }


  /**
   * Sets the minVersion attribute of the BrowserHandler object
   *
   * @param tmp The new minVersion value
   */
  public final void setMinVersion(String tmp) {
    minVersion = Double.parseDouble(tmp);
  }


  /**
   * Sets the maxVersion attribute of the BrowserHandler object
   *
   * @param tmp The new maxVersion value
   */
  public final void setMaxVersion(String tmp) {
    maxVersion = Double.parseDouble(tmp);
  }


  /**
   * Sets the os attribute of the BrowserHandler object
   *
   * @param tmp The new os value
   */
  public void setOs(String tmp) {
    this.os = tmp;
  }


  /**
   * Sets the include attribute of the BrowserHandler object
   *
   * @param tmp The new include value
   */
  public final void setInclude(String tmp) {
    include = tmp.equalsIgnoreCase("true");
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public final int doStartTag() throws JspException {
    UserBean thisUser = (UserBean) pageContext.getSession().getAttribute(
        "User");
    if (thisUser.getBrowserId().indexOf(browserId) > -1) {
      if (include) {
        if (versionPasses(thisUser.getBrowserVersion()) && osPasses(
            thisUser.getClientType().getOsString())) {
          return EVAL_BODY_INCLUDE;
        } else {
          return SKIP_BODY;
        }
      } else {
        if (versionPasses(thisUser.getBrowserVersion()) && osPasses(
            thisUser.getClientType().getOsString())) {
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
   * Description of the Method
   *
   * @param userVersion Description of the Parameter
   * @return Description of the Return Value
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
   * Description of the Method
   *
   * @param userOs Description of the Parameter
   * @return Description of the Return Value
   */
  private boolean osPasses(String userOs) {
    if (os != null) {
      return (os.equals(userOs));
    } else {
      return true;
    }
  }
}

