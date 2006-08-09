package org.aspcfs.modules.webservices.beans;

import com.darkhorseventures.framework.beans.GenericBean;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    July 18, 2006
 */
public class WSCustomFolder extends GenericBean {
  private int id = -1;
  private int linkItemId = -1;
  private String name = "";
  private String description = "";


  /**
   *  Gets the id attribute of the WSCustomFolder object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the id attribute of the WSCustomFolder object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the WSCustomFolder object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Gets the linkItemId attribute of the WSCustomFolder object
   *
   * @return    The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   *  Sets the linkItemId attribute of the WSCustomFolder object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the linkItemId attribute of the WSCustomFolder object
   *
   * @param  tmp  The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.linkItemId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the name attribute of the WSCustomFolder object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the WSCustomFolder object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the description attribute of the WSCustomFolder object
   *
   * @return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Sets the description attribute of the WSCustomFolder object
   *
   * @param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


}

