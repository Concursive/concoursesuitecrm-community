/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.Template;

import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author -
 * @version $Id: CustomFormGroup.java,v 1.2 2003/01/13 21:24:01 mrajkowski Exp
 *          $
 * @created January 13, 2003
 */
public class CustomFormGroup extends ArrayList {

  String name = "";


  /**
   * Constructor for the CustomFormGroup object
   */
  public CustomFormGroup() {
  }


  /**
   * Sets the name attribute of the CustomFormGroup object
   *
   * @param name The new name value
   */
  public void setName(String name) {
    this.name = name;
  }


  /**
   * Gets the name attribute of the CustomFormGroup object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   */
  public void parseTemplateText(SystemStatus thisSystem) {
    if (name != null) {
      Template template = new Template(name);
      template.populateSystemVariables(thisSystem);
      name = template.getParsedText();
    }
  }
}

