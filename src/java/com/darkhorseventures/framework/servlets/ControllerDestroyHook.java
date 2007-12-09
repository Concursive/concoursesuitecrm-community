/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.darkhorseventures.framework.servlets;

import javax.servlet.ServletConfig;

/**
 * Description of the Interface
 *
 * @author mrajkowski
 * @version $Id: ControllerDestroyHook.java,v 1.1.1.1 2002/01/14 19:49:29
 *          mrajkowski Exp $
 * @created July 30, 2001
 */
public interface ControllerDestroyHook {

  /**
   * Description of the Method
   *
   * @param config Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeControllerDestroyInit(ServletConfig config);


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public String executeControllerDestroy();
}

