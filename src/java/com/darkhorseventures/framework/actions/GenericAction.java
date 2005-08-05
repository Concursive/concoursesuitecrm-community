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
package com.darkhorseventures.framework.actions;

/**
 * This class is provided as a means to descend from, so that common generic
 * methods and fields can be placed here and all other action classes (that
 * descend from this one), inherit the common functionality. You may want to
 * provide a simple logging mechanism, helper methods that action classes might
 * find useful, and so on. note: If an action class you define will not be
 * descended by other classes, you should make it final, for the performance
 * gains a final class achieves.
 *
 * @author kevin duffey
 * @version $Id$
 * @created July 9, 2001
 */

public class GenericAction implements java.io.Serializable {

  final static long serialVersionUID = -1388329390981035172L;


  /**
   * 0-arg constructor
   */
  public GenericAction() {
  }


  /**
   * This is the default call by all actions if a command= paramter is not
   * passed along with the request. Descendant classes should override this
   * method to carry out default behavior that should occure BEFORE the initial
   * JSP page is displayed. This is most useful when the initial page needs to
   * show a list of items that are populated from the database, such as a
   * drop-down list of activity for a client, etc.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return "DefaultOK";
  }

}

