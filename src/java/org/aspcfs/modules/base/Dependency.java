/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.base;

import com.darkhorseventures.framework.beans.*;
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

