package org.aspcfs.modules.contacts.base;
/**
 *  Custom Contact Filter for the Contacts Popup
 *
 *@author     Mathur
 *@created    March 5, 2003
 *@version    $Id$
 */
public class ContactFilter {
  String value = null;
  String displayName = null;


  public ContactFilter(String value, String displayName){
    this.value = value;
    this.displayName = displayName;
  }
  
  /**
   *  Sets the value attribute of the ContactFilter object
   *
   *@param  value  The new value value
   */
  public void setValue(String value) {
    this.value = value;
  }


  /**
   *  Sets the displayName attribute of the ContactFilter object
   *
   *@param  displayName  The new displayName value
   */
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }


  /**
   *  Gets the value attribute of the ContactFilter object
   *
   *@return    The value value
   */
  public String getValue() {
    return value;
  }


  /**
   *  Gets the displayName attribute of the ContactFilter object
   *
   *@return    The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }

}

