/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 19, 2003
 *@version    $Id$
 */
public class DebugHandler extends TagSupport {

  private String value = null;


  /**
   *  Sets the value attribute of the DebugHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("DebugHandler-> " + value);
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

