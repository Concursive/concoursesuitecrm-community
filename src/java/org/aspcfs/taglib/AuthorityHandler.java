package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.aspcfs.utils.*;
import org.aspcfs.controller.*;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.admin.base.User;
import java.util.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    June 19, 2003
 *@version    $id: exp$
 */
public class AuthorityHandler extends TagSupport {

  private int owner = -1;


  /**
   *  Sets the owner attribute of the AuthorityHandler object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(int owner) {
    this.owner = owner;
  }


  /**
   *  Sets the owner attribute of the AuthorityHandler object
   *
   *@param  owner  The new owner value
   */
  public void setOwner(String owner) {
    this.owner = Integer.parseInt(owner);
  }


  /**
   *  Gets the owner attribute of the AuthorityHandler object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   *  Description of the Method
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public final int doStartTag() throws JspException {
    try {
      //get system status
      System.out.println("Getting system status ");
      UserBean thisUser = (UserBean) pageContext.getSession().getAttribute("User");
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
      SystemStatus systemStatus = null;
      if (ce != null) {
        systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
        System.out.println("Got system status ");
      }

      int userId = ((UserBean) pageContext.getSession().getAttribute("User")).getUserId();
      if (userId == owner) {
        return EVAL_BODY_INCLUDE;
      }
      User userRecord = systemStatus.getUser(userId);
      User childRecord = userRecord.getChild(owner);
      if (childRecord != null) {
        return EVAL_BODY_INCLUDE;
      }
    } catch (Exception e) {
      throw new JspException("ContactNameHandler-> Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }

}

