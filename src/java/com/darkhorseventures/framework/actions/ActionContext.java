package com.darkhorseventures.framework.actions;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.naming.*;
import javax.servlet.http.*;
import javax.servlet.*;
import com.darkhorseventures.framework.beans.*;

/**
 *  ActionContext is created in the <code>DefaultAction</code> so that a single
 *  object is passed around to actions. ActionContext provides a place holder
 *  for the <code>HttpServletRequest</code> , <code>HttpServletResponse</code> ,
 *  <code>HttpSession</code> , and the Struts variables as well... <code>
 *  ActionServlet</code> , <code>ActionAction</code> and <code>ActionForm
 *  </code>.
 *
 *@author     Kevin Duffey
 *@created    June 1, 2001
 *@version    1.0
 */
public class ActionContext
     implements Serializable {
  private String command = "Default";
  private HttpServletRequest request = null;
  private HttpServletResponse response = null;
  private HttpSession session = null;
  private Servlet servlet = null;
  private Action action = null;
  private Object bean = null;
  public final static String MESSAGE = "MESSAGE";
  final static long serialVersionUID = 215434482513634196L;


  /**
   *  Constructor that creates the ActionContext object and sets the needed
   *  object references that actions will need access to.
   *
   *@param  servlet   Description of Parameter
   *@param  bean      Description of Parameter
   *@param  action    Description of Parameter
   *@param  request   Description of Parameter
   *@param  response  Description of Parameter
   */
  public ActionContext(Servlet servlet, Object bean, Action action, HttpServletRequest request, HttpServletResponse response) {
    this.bean = bean;
    this.request = request;
    this.response = response;
    this.servlet = servlet;
    this.action = action;

    // set the HttpSession for this context, making it easier to get a HttpSession
    // reference, instead of always having to call request.getSession().
    session = request.getSession(true);

    // If a 'command' parameter is in the request scope, then a command
    // is available and we must set this context instance' command value.
    // If a command parameter is not found, then the default command is
    // always assumed.
    if (request.getParameter("command") != null) {
      command = request.getParameter("command");
    }
  }



  /**
   *  Sets a HttpSession attribute
   *
   *@param  name   The new Attribute value
   *@param  value  The new Attribute value
   */
  public void setAttribute(String name, Object value) {
    getSession().setAttribute(name, value);
  }



  /**
   *  Sets a request attribute that can be used by JSP pages to display an error
   *  message or take some other course of action if the attribute exists when
   *  the JSP page is forwarded to.
   *
   *@param  value  the message to set into the request attribute
   */
  public void setMessage(String value) {
    // if the bean object is an instance of the GenericBean class, set the message
    // on the bean, as well as a request attribute
    if (bean instanceof GenericBean) {
      ((GenericBean) bean).setMessage(value);
    }
    getRequest().setAttribute(MESSAGE, value);
  }



  /**
   *  Returns the ServletContext if this servlet is an HttpServlet instance
   *
   *@return    ServletContext the ServletContext for this servlet
   */
  public ServletContext getServletContext() {
    return servlet.getServletConfig().getServletContext();
  }



  /**
   *  Returns a reference to a bean that is mapped to this action. The reference
   *  is an HttpSession bean reference.
   *
   *@return    Object the bean reference mapped to this action
   */
  public Object getFormBean() {
    return bean;
  }



  /**
   *  Gets the FormBean attribute of the ActionContext object
   *
   *@param  beanName  Description of Parameter
   *@return           The FormBean value
   */
  public Object getFormBean(String beanName) {
    return action.getBeans().get(beanName);
  }


  /**
   *  Returns the Action for this action. This Action contains the action name
   *  and fully qualifed class, the bean name and fully qualified class (if
   *  specified), zero or more Resources to forward to which each contain the
   *  name of the resource (which is what the action class methods would return
   *  as a String for the lookup process), the JSP page to forward to, and
   *  alternatively a XSL file to be used to transform the JSP output if the
   *  XSLControllerServlet is used.
   *
   *@return    Action the Action for this action
   */
  public Action getAction() {
    return action;
  }



  /**
   *  Returns the clients browser information.
   *
   *@return    String The description of the clients browser
   */
  public String getBrowser() {
    return getRequest().getHeader("USER-AGENT");
  }



  /**
   *  Returns the command value associated with this request
   *
   *@return    int The value of the command for this request
   */
  public String getCommand() {
    return command;
  }



  /**
   *  A helper method that returns a request parameter of the passed in param
   *  name if it exists, or null otherwise.
   *
   *@param  paramName  Description of Parameter
   *@return            String the value of the parameter or null if the
   *      parameter does not exist
   */
  public String getParameter(String paramName) {
    return getRequest().getParameter(paramName);
  }



  /**
   *  Returns the clients remote IP address
   *
   *@return    String The clients ip address to their computer.
   */
  public String getIpAddress() {
    return getRequest().getRemoteAddr();
  }



  /**
   *  Returns the current request object associated with a client
   *
   *@return    HttpServletRequest The request of this client
   */
  public HttpServletRequest getRequest() {
    return request;
  }


  /**
   *  Returns the current response object associated with a client
   *
   *@return    HttpServletResponse The response of this client
   */
  public HttpServletResponse getResponse() {
    return response;
  }



  /**
   *  Returns the session associated with a client
   *
   *@return    HttpSession The session context for this client
   */
  public HttpSession getSession() {
    return session;
  }



  /**
   *  Returns an HttpSession attribute
   *
   *@param  name  Description of Parameter
   *@return       The Attribute value
   */
  public Object getAttribute(String name) {
    return getSession().getAttribute(name);
  }



  /**
   *  Removes an HttpSession attribute. Returns true if the attribute was
   *  removed, or false if no attribute by the name exists.
   *
   *@param  name  Description of Parameter
   *@return       Description of the Returned Value
   */
  public boolean removeAttribute(String name) {
    if (getSession().getAttribute(name) != null) {
      getSession().removeAttribute(name);
      return true;
    }
    return false;
  }
}

