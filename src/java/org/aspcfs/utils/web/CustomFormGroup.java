package org.aspcfs.utils.web;

import java.util.ArrayList;

/**
 *  Description of the Class
 *
 *@author     -
 *@created    January 13, 2003
 *@version    $Id$
 */
public class CustomFormGroup extends ArrayList {

  String name = "";

  /**
   *  Constructor for the CustomFormGroup object
   */
  public CustomFormGroup() { }


  /**
   *  Sets the name attribute of the CustomFormGroup object
   *
   *@param  name  The new name value
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   *  Gets the name attribute of the CustomFormGroup object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }
}

