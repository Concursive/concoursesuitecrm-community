/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import org.aspcfs.utils.web.HtmlSelect;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: HtmlPercentList.java,v 1.2 2004/07/21 19:00:43 mrajkowski Exp
 *          $
 * @created July 6, 2004
 */
public class HtmlPercentList extends HtmlSelect {

  /**
   * Constructor for the HtmlPercentList object
   */
  public HtmlPercentList() {
    this.addItem(-1, "0%");
    this.addItem(10, "10%");
    this.addItem(20, "20%");
    this.addItem(30, "30%");
    this.addItem(40, "40%");
    this.addItem(50, "50%");
    this.addItem(60, "60%");
    this.addItem(70, "70%");
    this.addItem(80, "80%");
    this.addItem(90, "90%");
    this.addItem(100, "100%");
  }

}

