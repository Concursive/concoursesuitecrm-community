package org.jcrontab.log;

/**
 *  Outputs the log to system.out
 *
 *@author     matt rajkowski
 *@created    February 3, 2003
 *@version    $Id$
 */
public class DebugLogger implements Logger {
  /**
   *  Description of the Method
   */
  public void init() { }


  /**
   *  Description of the Method
   *
   *@param  message  Description of the Parameter
   */
  public void info(String message) {
    System.out.println("JCrontab-> " + message);
  }


  /**
   *  Description of the Method
   *
   *@param  message  Description of the Parameter
   *@param  t        Description of the Parameter
   */
  public void error(String message, Throwable t) {
    t.printStackTrace();
  }


  /**
   *  Description of the Method
   *
   *@param  message  Description of the Parameter
   */
  public void debug(String message) { }
}

