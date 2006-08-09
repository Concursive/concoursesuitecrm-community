package org.aspcfs.modules.webservices.beans;

import com.darkhorseventures.framework.beans.GenericBean;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    March 30, 2006
 */
public class WSUserBean extends GenericBean {
  private int id = -1;
  private String username = null;
  private String nameFirst = null;
  private String nameLast = null;


  /**
   *  Gets the id attribute of the WSUserBean object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the WSUserBean object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the WSUserBean object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the username attribute of the UserBean object
   *
   * @return    The username value
   */
  public String getUsername() {
    return username;
  }


  /**
   *  Sets the username attribute of the UserBean object
   *
   * @param  tmp  The new username value
   */
  public void setUsername(String tmp) {
    this.username = tmp;
  }


  /**
   *  Gets the nameFirst attribute of the UserBean object
   *
   * @return    The nameFirst value
   */
  public String getNameFirst() {
    return nameFirst;
  }


  /**
   *  Sets the nameFirst attribute of the UserBean object
   *
   * @param  tmp  The new nameFirst value
   */
  public void setNameFirst(String tmp) {
    this.nameFirst = tmp;
  }


  /**
   *  Gets the nameLast attribute of the UserBean object
   *
   * @return    The nameLast value
   */
  public String getNameLast() {
    return nameLast;
  }


  /**
   *  Sets the nameLast attribute of the UserBean object
   *
   * @param  tmp  The new nameLast value
   */
  public void setNameLast(String tmp) {
    this.nameLast = tmp;
  }
}

