package org.theseus.servlets;

import javax.servlet.http.*;

public interface ControllerLayoutManagerHook {
  public String generateLayout(HttpServletRequest request);
}

