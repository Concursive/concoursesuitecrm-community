package com.darkhorseventures.cfsmodule;

import java.util.*;
import org.theseus.beans.*;

/**
 *  Used by the MyCFS module for passing formatted data to the MyCFS JSP
 *
 *@author     mrajkowski
 *@created    July 9, 2001
 *@version    1.1
 */
public class MyCFSBean extends GenericBean {

  //Use the User session object instead of firstName here
  private String firstName = "";
  //Use the User session object instead of lastName here
  private String lastName = "";
  private Vector items = new Vector();


  /**
   *  Constructor for the myCFSBean object
   *
   *@since    1.0
   */
  public MyCFSBean() {
  }


  /**
   *  Sets the FirstName attribute of the myCFSBean object
   *
   *@param  tmp    The new FirstName value
   *@since         1.0
   *@deprecated
   */
  public void setFirstName(String tmp) {
    this.firstName = tmp;
  }


  /**
   *  Sets the LastName attribute of the myCFSBean object
   *
   *@param  tmp    The new LastName value
   *@since         1.0
   *@deprecated
   */
  public void setLastName(String tmp) {
    this.lastName = tmp;
  }


  /**
   *  Gets the FirstName attribute of the myCFSBean object
   *
   *@return        The FirstName value
   *@since         1.0
   *@deprecated
   */
  public String getFirstName() {
    return firstName;
  }


  /**
   *  Gets the LastName attribute of the myCFSBean object
   *
   *@return        The LastName value
   *@since         1.0
   *@deprecated
   */
  public String getLastName() {
    return lastName;
  }


  /**
   *  Gets the Items attribute of the MyCFSBean object, this should be a Vector
   *  of HTML String objects
   *
   *@return    The Items value
   *@since     1.1
   */
  public Vector getItems() {
    return items;
  }


  /**
   *  Adds a feature to the Item attribute of the MyCFSBean object, this should
   *  be a String object formatted with HTML
   *
   *@param  tmp  The feature to be added to the Item attribute
   *@since       1.1
   */
  public void addItem(String tmp) {
    this.items.addElement(tmp);
  }

}

