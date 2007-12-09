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
package org.aspcfs.apps.workFlowManager;

/**
 * All component classes must implement this class in order to be executed
 * properly by the WorkflowManager
 *
 * @author matt rajkowski
 * @version $Id: ComponentInterface.java,v 1.2 2002/11/14 13:32:16 mrajkowski
 *          Exp $
 * @created November 11, 2002
 */
public interface ComponentInterface {
  /**
   * Provides a description of the component, if an action complete use a
   * statement, otherwise use a question.<br>
   * Ex. "Was the ticket just closed?"<br>
   * "Send a user notification message."
   *
   * @return The description value
   */
  public String getDescription();


  /**
   * This method performs the component operation
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context);
}

