package org.aspcfs.utils.web;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import com.darkhorseventures.framework.actions.*;
import java.util.Iterator;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;


/**
 *  Stores information about the Viewpoint selected in a module<br>
 *
 *
 *@author     Mathur
 *@created    February 24, 2003
 *@version    $Id$
 */
public class ViewpointInfo implements Serializable {
  private String id = null;
  private int vpUserId = -1;
  private String vpUserName = null;


  /**
   *  User Id of the viewpoint selected
   *
   *@param  vpUserId  The new vpUserId value
   */
  public void setVpUserId(int vpUserId) {
    this.vpUserId = vpUserId;
  }


  /**
   *  Sets the parameters attribute of the ViewpointInfo object
   *
   *@param  context  The new parameters value
   */
  public void setParameters(ActionContext context) {
    HttpServletRequest request = context.getRequest();
    if (request.getParameter("viewpointId") != null) {
      vpUserId = Integer.parseInt((String) request.getParameter("viewpointId"));
    }
  }


  /**
   *  Sets the id attribute of the ViewpointInfo object
   *
   *@param  id  The new id value
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   *  Sets the vpUserName attribute of the ViewpointInfo object
   *
   *@param  vpUserName  The new vpUserName value
   */
  public void setVpUserName(String vpUserName) {
    this.vpUserName = vpUserName;
  }


  /**
   *  Sets the vpUserName attribute of the ViewpointInfo object
   *
   *@param  vpUserList  The new vpUserName value
   */
  public void setVpUserName(UserList vpUserList) {
    if (vpUserId != -1) {
      Iterator i = vpUserList.iterator();
      while (i.hasNext()) {
        User vpUser = (User) i.next();
        if (vpUser.getId() == this.getVpUserId()) {
          vpUserName = vpUser.getContact().getNameFull();
        }
      }
    }
  }


  /**
   *  Gets the vpUserName attribute of the ViewpointInfo object
   *
   *@return    The vpUserName value
   */
  public String getVpUserName() {
    return vpUserName;
  }


  /**
   *  Gets the id attribute of the ViewpointInfo object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Gets the vpUserId attribute of the ViewpointInfo object
   *
   *@return    The vpUserId value
   */
  public int getVpUserId() {
    return vpUserId;
  }


  /**
   *  Gets the vpUserId attribute of the ViewpointInfo object
   *
   *@param  tmpId  Description of the Parameter
   *@return        The vpUserId value
   */
  public int getVpUserId(int tmpId) {
    if (vpUserId == -1) {
      return tmpId;
    }
    return vpUserId;
  }


  /**
   *  Gets the vpSelected attribute of the ViewpointInfo object
   *
   *@param  actualUser  Description of the Parameter
   *@return             The vpSelected value
   */
  public boolean isVpSelected(int actualUserId) {
    if (vpUserId == -1 || actualUserId == vpUserId) {
      return false;
    }
    return true;
  }
}

