package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 *  Takes a number of bytes as input and returns a shortened displayable
 *  value... in "K" or "M" depending on scale.
 *
 *@author     matt rajkowski
 *@created    October 6, 2003
 *@version    $Id$
 */
public class FileSizeHandler extends TagSupport {

  private long bytes = -1;


  /**
   *  Sets the bytes attribute of the FileSizeHandler object
   *
   *@param  tmp  The new bytes value
   */
  public void setBytes(long tmp) {
    this.bytes = tmp;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      if (bytes < 0) {
        this.pageContext.getOut().write("&nbsp;");
      } else {
        bytes = (bytes / 1000);
        if (bytes == 0) {
          this.pageContext.getOut().write("1 K");
/*
        } else if (bytes > 1000) {
          //Process to M
*/
        } else {
          //Proecess to K
          this.pageContext.getOut().write(String.valueOf(bytes) + " K");
        }
      }
    } catch (Exception e) {
      return SKIP_BODY;
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

