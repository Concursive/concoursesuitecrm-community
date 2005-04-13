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
package org.aspcfs.modules.products.configurator;

import org.aspcfs.controller.SystemStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    September 1, 2004
 *@version    $Id: OptionPropertyList.java,v 1.1.4.2 2005/02/24 13:54:44
 *      mrajkowski Exp $
 */
public class OptionPropertyList extends ArrayList {

  /**
   *  Constructor for the OptionPropertyList object
   */
  public OptionPropertyList() { }


  /**
   *  Sets the optionProperties attribute of the OptionPropertyList object
   *
   *@param  request  The new optionFields value
   */
  public void setOptionProperties(HttpServletRequest request) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      OptionProperty property = (OptionProperty) i.next();
      //For each option property the user is prompted for, evaluate the answer
      if (property.getIsForPrompting()) {
        property.setValue(request.getParameter(property.getName()));
        System.out.println("OptionPropertyList-> property " + property.getName() + "=" + property.getValue());
      }
    }
  }


  /**
   *  Gets the optionProperty attribute of the OptionPropertyList object
   *
   *@param  property  Description of the Parameter
   *@return           The optionField value
   */
  public OptionProperty getOptionProperty(String property) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      OptionProperty thisProperty = (OptionProperty) i.next();
      if (property.equals(thisProperty.getName())) {
        return thisProperty;
      }
    }
    return null;
  }


  /**
   *  Sets the optionProperty attribute of the OptionPropertyList object
   *
   *@param  property  The new optionProperty value
   *@param  value     The new optionProperty value
   */
  public void setOptionProperty(String property, String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      OptionProperty thisProperty = (OptionProperty) i.next();
      if (property.equals(thisProperty.getName())) {
        thisProperty.setValue(value.trim());
        break;
      }
    }
  }


  /**
   *  Gets the valid attribute of the OptionPropertyList object
   *
   *@param  systemStatus  Description of the Parameter
   *@return               The valid value
   */
  public boolean isValid(SystemStatus systemStatus) {
    Iterator i = this.iterator();
    boolean valid = true;
    while (i.hasNext()) {
      OptionProperty property = (OptionProperty) i.next();
      if (!property.isValid(systemStatus)) {
        valid = false;
      }
    }
    return valid;
  }


  /**
   *  Gets the errors attribute of the OptionPropertyList object
   *
   *@return    The errors value
   */
  public HashMap getErrors() {
    HashMap errors = new HashMap();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      OptionProperty property = (OptionProperty) i.next();
      if (property.getErrorMsg() != null) {
        errors.put(property.getName(), property.getErrorMsg());
      }
    }
    return errors;
  }
}

