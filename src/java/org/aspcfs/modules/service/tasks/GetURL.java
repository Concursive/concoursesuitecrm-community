package org.aspcfs.modules.service.tasks;

import java.net.*;

/**
 *  Used for making a connection to a specified url, can be used for executing a
 *  process at the url, testing, debugging, etc.
 *
 *@author     matt rajkowski
 *@created    May 13, 2003
 *@version    $Id$
 */
public class GetURL {

  /**
   *  Makes a URL connection with the specified url, retrieves and discards
   *  content
   *
   *@param  args  Description of the Parameter
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

