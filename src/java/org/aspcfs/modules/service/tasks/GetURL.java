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
package org.aspcfs.modules.service.tasks;

import java.net.URL;
import java.net.URLConnection;

/**
 * Used for making a connection to a specified url, can be used for executing a
 * process at the url, testing, debugging, etc.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 13, 2003
 */
public class GetURL {

  /**
   * Makes a URL connection with the specified url, retrieves and discards
   * content
   *
   * @param args Description of the Parameter
   */
  public static void doTask(String args[]) {
    if (args.length == 0) {
      System.out.println("GetURL-> No url specified");
      System.out.println("ExitValue: 1");
    } else {
      try {
        URL url = new URL(args[0]);
        URLConnection conn = url.openConnection();
        Object result = conn.getContent();
        System.out.println("ExitValue: 0");
      } catch (Exception e) {
        System.out.println("ExitValue: 1");
      }
    }
  }

}

