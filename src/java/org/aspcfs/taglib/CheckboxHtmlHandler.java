package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 *  This tag returns an HTML checkbox string based on the given parameters.
 *
 *@author     matt rajkowski
 *@created    August 13, 2003
 *@version    $Id$
 */
public class CheckboxHtmlHandler extends TagSupport {

  private String name = null;
  private String value = null;
  private boolean checked = false;


  /**
   *  Sets the name attribute of the CheckboxHtmlHandler object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the value attribute of the CheckboxHtmlHandler object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the checked attribute of the CheckboxHtmlHandler object
   *
   *@param  tmp  The new checked value
   */
  public final void setChecked(boolean tmp) {
    checked = tmp;
  }


  /**
   *  Sets the checked attribute of the CheckboxHtmlHandler object
   *
   *@param  tmp  The new checked value
   */
  public final void setChecked(String tmp) {
    checked = "true".equalsIgnoreCase(tmp);
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      this.pageContext.getOut().write(
          "<input type=\"checkbox\" name=\"" + name + "\"" +
          (value != null ? " value=\"" + value + "\"" : "") +
          (checked ? " checked" : "") + " />");
    } catch (Exception e) {
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

