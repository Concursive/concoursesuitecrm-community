package com.darkhorseventures.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.controller.*;
import java.util.*;

/**
 *  This Class evaluates whether a SystemStatus preference exists for the
 *  supplied label.
 *
 *@author     Matt Rajkowski
 *@created    February 25, 2002
 *@version    $Id$
 */
public class LabelHandler extends TagSupport {
  private String labelName = null;


  /**
   *  Sets the Name attribute of the LabelHandler object
   *
   *@param  tmp  The new Name value
   *@since       1.1
   */
  public final void setName(String tmp) {
    labelName = tmp;
  }


  /**
   *  Checks to see if the SystemStatus has a preference set for this label. If
   *  so, the found label will be used, otherwise the body tag will be used.
   *
   *@return                   Description of the Returned Value
   *@exception  JspException  Description of Exception
   *@since                    1.1
   */
  public final int doStartTag() throws JspException {
    boolean result = true;
    String newLabel = null;

    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
    if (ce == null) {
      System.out.println("FieldHandler-> ConnectionElement is null");
    }
    SystemStatus systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    if (systemStatus == null) {
      System.out.println("FieldHandler-> SystemStatus is null");
    }
    if (systemStatus != null) {
      newLabel = systemStatus.getLabel(labelName);
    }

    if (newLabel != null) {
      try {
        this.pageContext.getOut().write(newLabel);
        result = false;
      } catch (java.io.IOException e) {
        //Nowhere to output
      }
    }

    if (result) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }

}

