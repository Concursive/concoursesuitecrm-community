package org.aspcfs.modules.base;
/**
 *  Custom Contact Filter for the Contacts Popup
 *
 *@author     Mathur
 *@created    March 5, 2003
 *@version    $Id$
 */
public class Filter {
  String value = null;
  String displayName = null;


  public Filter(String value, String displayName){
    this.value = value;
    this.displayName = displayName;
  }
  
  /**
   *  Sets the value attribute of the Filter object
   *
   *@param  value  The new value value
   */
  public void setValue(String value) {
    this.value = value;
  }


  /**
   *  Sets the displayName attribute of the Filter object
   *
   *@param  displayName  The new displayName value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  /**
   *  Gets the value attribute of the Filter object
   *
   *@return    The value value
   */
  public String getValue() {
    return value;
  }


  /**
   *  Gets the displayName attribute of the Filter object
   *
   *@return    The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }

}

