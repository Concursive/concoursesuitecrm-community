package com.darkhorseventures.framework.actions;

/**
 *  This class is provided as a means to descend from, so that common generic
 *  methods and fields can be placed here and all other action classes (that
 *  descend from this one), inherit the common functionality. You may want to
 *  provide a simple logging mechanism, helper methods that action classes might
 *  find useful, and so on. note: If an action class you define will not be
 *  descended by other classes, you should make it final, for the performance
 *  gains a final class achieves.
 *
 *@author     kevin
 *@created    July 9, 2001
 *@version    $Id$
 */

public class GenericAction implements java.io.Serializable {

  final static long serialVersionUID = -1388329390981035172L;


  /**
   *  0-arg constructor
   */
  public GenericAction() { }


  /**
   *  This is the default call by all actions if a command= paramter is not
   *  passed along with the request. Descendant classes should override this
   *  method to carry out default behavior that should occure BEFORE the initial
   *  JSP page is displayed. This is most useful when the initial page needs to
   *  show a list of items that are populated from the database, such as a
   *  drop-down list of activity for a client, etc.
   *
   *@param  context  Description of Parameter
   *@return          Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
    return "DefaultOK";
  }

}

