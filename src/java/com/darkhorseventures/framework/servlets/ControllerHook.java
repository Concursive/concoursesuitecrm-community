package org.theseus.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

public interface ControllerHook {
  public String securityCheck(Servlet servlet, HttpServletRequest request);
}

