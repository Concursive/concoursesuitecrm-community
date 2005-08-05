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
package org.aspcfs.utils.web;

import org.aspcfs.modules.base.Constants;

/**
 * Used when asking a user a yes/no question and the result can be unanswered
 *
 * @author matt rajkowski (matt@zeroio.com)
 * @version $Id: HtmlSelectChoice.java,v 1.2 2004/07/21 19:00:44 mrajkowski
 *          Exp $
 * @created March 31, 2004
 */
public class HtmlSelectChoice {

  /**
   * Gets the select attribute of the HtmlSelectChoice class
   *
   * @param name         Description of the Parameter
   * @param defaultValue Description of the Parameter
   * @return The select value
   */
  public static HtmlSelect getSelect(String name, int defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    select.addItem(Constants.UNDEFINED, "-- Any --");
    select.addItem(Constants.TRUE, "Yes");
    select.addItem(Constants.FALSE, "No");
    return select;
  }
}

