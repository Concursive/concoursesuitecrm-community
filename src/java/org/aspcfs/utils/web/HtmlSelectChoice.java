package org.aspcfs.utils.web;

import org.aspcfs.modules.base.Constants;

/**
 *  Used when asking a user a yes/no question and the result can be unanswered
 *
 *@author     matt rajkowski (matt@zeroio.com)
 *@created    March 31, 2004
 *@version    $Id$
 */
public class HtmlSelectChoice {

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

