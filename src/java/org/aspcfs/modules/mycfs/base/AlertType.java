package org.aspcfs.modules.mycfs.base;

/**
 *  Represents an Alert in CFS.
 *
 *@author     Mathur
 *@created    January 20, 2003
 *@version    $Id$
 */
public class AlertType {

  protected String name = null;
  protected String displayName = null;
  protected String className = null;



  /**
   *  Constructor for the AlertType object
   *
   *@param  name         Description of the Parameter
   *@param  className    Description of the Parameter
   *@param  displayName  Description of the Parameter
   */
  public AlertType(String name, String className, String displayName) {
    this.name = name;
    this.displayName = displayName;
    this.className = className;
  }


  /**
   *  Sets the name attribute of the AlertType object
   *
   *@param  name  The new name value
   */
  public void setName(String name) {
    this.name = name;
  }



  /**
   *  Sets the className attribute of the AlertType object
   *
   *@param  className  The new className value
   */
  public void setClassName(String className) {
    this.className = className;
  }


  /**
   *  Sets the displayName attribute of the AlertType object
   *
   *@param  displayName  The new displayName value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  /**
   *  Gets the displayName attribute of the AlertType object
   *
   *@return    The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }


  /**
   *  Gets the name attribute of the AlertType object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }



  /**
   *  Gets the className attribute of the AlertType object
   *
   *@return    The className value
   */
  public String getClassName() {
    return className;
  }

}


