package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import com.darkhorseventures.database.*;
import java.util.*;

/**
 *  This Class evaluates whether a SystemStatus preference exists for the
 *  supplied label.
 *
 *@author     Matt Rajkowski
 *@created    February 25, 2002
 *@version    $Id: LabelHandler.java,v 1.3.180.2 2004/08/30 14:13:43 mrajkowski
 *      Exp $
 */
public class LabelHandler extends TagSupport {
  private String labelName = null;
  private HashMap params = null;


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
   *  Sets the param attribute of the LabelHandler object
   *
   *@param  tmp  The new params value
   */
  public void setParam(String tmp) {
    params = new HashMap();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while (tokens.hasMoreTokens()) {
      String pair = tokens.nextToken();
      String param = pair.substring(0, pair.indexOf("="));
      String value = pair.substring(pair.indexOf("=") + 1);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("LabelHandler: Param-> " + param);
        System.out.println("LabelHandler: Value-> " + value);
      }
      params.put("${" + param + "}", value);
    }
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
    // Look up the label key in system status to get the value
    if (systemStatus != null) {
      newLabel = systemStatus.getLabel(labelName);
      // If there are any parameters to substitute then do so
      if (newLabel != null && params != null) {
        Template labelText = new Template(newLabel);
        labelText.setParseElements(params);
        newLabel = labelText.getParsedText();
      }
    }
    // Output the label value, else output the body of the tag
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

