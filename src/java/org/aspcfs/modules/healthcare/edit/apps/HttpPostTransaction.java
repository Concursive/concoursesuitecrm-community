package org.aspcfs.modules.healthcare.edit.apps;

import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.HTTPUtils;

/**
 *  Application to test HTTP posting transaction
 *
 *@author     matt rajkowski
 *@created    March 24, 2003
 *@version    $Id$
 */
public class HttpPostTransaction {

  /**
   *@param  args  the command line arguments
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


