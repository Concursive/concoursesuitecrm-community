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
package org.aspcfs.modules.healthcare.edit.apps;

import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.StringUtils;

/**
 * Application to test HTTP posting transaction
 *
 * @author matt rajkowski
 * @version $Id: HttpPostTransaction.java,v 1.1 2003/03/24 19:00:50 mrajkowski
 *          Exp $
 * @created March 24, 2003
 */
public class HttpPostTransaction {

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    if (args.length == 0) {
      System.out.println("USAGE: HttpPostTransaction [url] [file]");
    } else {
      try {
        String xml = StringUtils.loadText(args[1]);
        String response = HTTPUtils.sendPacket(args[0], xml);
        System.out.println(response);
      } catch (Exception e) {
        e.printStackTrace(System.out);
      }
    }
  }
}


