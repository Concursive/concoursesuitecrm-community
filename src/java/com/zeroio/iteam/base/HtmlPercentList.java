/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import org.aspcfs.utils.web.HtmlSelect;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 6, 2004
 *@version    $Id$
 */
public class HtmlPercentList extends HtmlSelect {

  /**
   *  Constructor for the HtmlPercentList object
   */
  public HtmlPercentList() {
    this.addItem(-1, "0 %");
    this.addItem(10, "10 %");
    this.addItem(20, "20 %");
    this.addItem(30, "30 %");
    this.addItem(40, "40 %");
    this.addItem(50, "50 %");
    this.addItem(60, "60 %");
    this.addItem(70, "70 %");
    this.addItem(80, "80 %");
    this.addItem(90, "90 %");
    this.addItem(100, "100 %");
  }

}

