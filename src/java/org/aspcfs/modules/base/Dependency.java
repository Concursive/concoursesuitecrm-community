//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;

/**
 *  Signifies a dependency of a item in CFS with the other.<br>
 *  e.g Accounts -- Contacts; Tickets -- Contacts .
 *
 *@author     Mathur
 *@created    December 18, 2002
 *@version    $Id$
 */
public class Dependency extends GenericBean {
  private String name = null;
  private boolean canDelete = false;
  private int count = 0;


  /**
   *  Sets the title attribute of the Dependency object
   *
   *@param  name  The new name value
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   *  Sets the canDelete attribute of the Dependency object
   *
   *@param  canDelete  The new canDelete value
   */
  public void setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
  }


  /**
   *  Sets the count attribute of the Dependency object
   *
   *@param  count  The new count value
   */
  public void setCount(int count) {
    this.count = count;
  }


  /**
   *  Gets the title attribute of the Dependency object
   *
   *@return    The title value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the canDelete attribute of the Dependency object
   *
   *@return    The canDelete value
   */
  public boolean getCanDelete() {
    return canDelete;
  }


  /**
   *  Gets the count attribute of the Dependency object
   *
   *@return    The count value
   */
  public int getCount() {
    return count;
  }

}

