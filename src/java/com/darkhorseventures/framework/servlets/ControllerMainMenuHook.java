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
package com.darkhorseventures.framework.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

/**
 * Description of the Interface
 *
 * @author mrajkowski
 * @version $Id: ControllerMainMenuHook.java,v 1.1.1.1 2002/01/14 19:49:29
 *          mrajkowski Exp $
 * @created July 9, 2001
 */
public interface ControllerMainMenuHook {
  /**
   * Initializes the MainMenuHook at servlet startup
   *
   * @param config Description of Parameter
   * @return Description of the Returned Value
   * @since 1.3
   */
  public String executeControllerMainMenu(ServletConfig config);


  /**
   * Executes the MainMenuHook during a request
   *
   * @param request    Description of Parameter
   * @param actionPath Description of Parameter
   * @since 1.0
   */
  public void generateMenu(HttpServletRequest request, String actionPath);
}

