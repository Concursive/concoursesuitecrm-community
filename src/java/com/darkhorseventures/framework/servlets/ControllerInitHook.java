package org.theseus.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

public interface ControllerInitHook {
  public String executeControllerInit(ServletConfig config);
}

