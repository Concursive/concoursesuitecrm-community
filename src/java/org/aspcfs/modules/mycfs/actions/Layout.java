/*
 *  Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.mycfs.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.ActionContext;

/**
 *  Layout preference actions
 *
 *@author     matt rajkowski
 *@created    March 13, 2005
 *@version    $Id$
 */
public final class Layout extends CFSModule {

  public String executeCommandResizeGlobalItemsPane(ActionContext context) {
    if ("show".equals(context.getRequest().getParameter("view"))) {
      context.getRequest().getSession().setAttribute("globalItemsPaneState", "SHOW");
    } else {
      context.getRequest().getSession().setAttribute("globalItemsPaneState", "HIDE");
    }
    return ("ResizeGlobalItemsPaneOK");
  }

}
