package com.darkhorseventures.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


public class SSLHandler extends TagSupport {
  private boolean result = false;
  private boolean hasNone = false;
  
  public void setNone(String tmp) {
    Boolean checkNone = new Boolean(tmp);
    this.hasNone = checkNone.booleanValue();
  }

  public final int doStartTag() throws JspException {
    
    if ("true".equals((String)pageContext.getAttribute("ForceSSL"))) {
            result = true;
    }
    
    if (hasNone) {
      result = !result;
    }
    
    if (result) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }

}

