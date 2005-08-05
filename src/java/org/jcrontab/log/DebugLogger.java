/**
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

package org.jcrontab.log;

/**
 * Outputs the log to system.out
 *
 * @author matt rajkowski
 * @version $Id$
 * @created February 3, 2003
 */
public class DebugLogger implements Logger {
  /**
   * Description of the Method
   */
  public void init() {
  }


  /**
   * Description of the Method
   *
   * @param message Description of the Parameter
   */
  public void info(String message) {
    System.out.println("JCrontab-> " + message);
  }


  /**
   * Description of the Method
   *
   * @param message Description of the Parameter
   * @param t       Description of the Parameter
   */
  public void error(String message, Throwable t) {
    t.printStackTrace();
  }


  /**
   * Description of the Method
   *
   * @param message Description of the Parameter
   */
  public void debug(String message) {
  }
}

