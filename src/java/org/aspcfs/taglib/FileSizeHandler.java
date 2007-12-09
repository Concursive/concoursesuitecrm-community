/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Takes a number of bytes as input and returns a shortened displayable
 * value... in "K" or "M" depending on scale.
 *
 * @author matt rajkowski
 * @version $Id: FileSizeHandler.java,v 1.2 2003/10/10 12:42:48 mrajkowski Exp
 *          $
 * @created October 6, 2003
 */
public class FileSizeHandler extends TagSupport {

  private long bytes = -1;


  /**
   * Sets the bytes attribute of the FileSizeHandler object
   *
   * @param tmp The new bytes value
   */
  public void setBytes(long tmp) {
    this.bytes = tmp;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
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
           *  } else if (bytes > 1000) {
           *  /Process to M
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
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

