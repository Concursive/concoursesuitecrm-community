package org.aspcfs.utils;

import javax.servlet.http.*;
import javax.servlet.*;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  Methods for working with the User object
 *
 *@author     matt rajkowski
 *@created    October 13, 2003
 *@version    $Id$
 */
public class UserUtils {

  /**
   *  Gets the userId attribute of the UserUtils class
   *
   *@param  request  Description of the Parameter
   *@return          The userId value
   */
  public static int getUserId(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getUserId();
  }


  /**
   *  Gets the userRangeId attribute of the UserUtils class
   *
   *@param  request  Description of the Parameter
   *@return          The userRangeId value
   */
  public static String getUserIdRange(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getIdRange();
  }


  /**
   *  Gets the userRoleType attribute of the UserUtils class
   *
   *@param  request  Description of the Parameter
   *@return          The userRoleType value
   */
  public static int getUserRoleType(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getRoleType();

  }


  /**
   *  Gets the userOrganization attribute of the UserUtils class
   *
   *@param  request  Description of the Parameter
   *@return          The userOrganization value
   */
  public static int getUserOrganization(HttpServletRequest request) {
    return ((UserBean) request.getSession().getAttribute("User")).getOrgId();
  }

}

