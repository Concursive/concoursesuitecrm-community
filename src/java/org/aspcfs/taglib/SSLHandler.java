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
    String scheme = (String)pageContext.getRequest().getScheme();
    
    if ("true".equals((String)pageContext.getAttribute("ForceSSL")) && scheme.equals("http")) {
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

