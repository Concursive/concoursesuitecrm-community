package com.darkhorseventures.controller;

/**
 *  All component classes must implement this class in order to be executed
 *  properly by the WorkflowManager
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
 */
public interface ComponentInterface {
  /**
   *  Provides a description of the component, if an action complete use a
   *  statement, otherwise use a question.<br>
   *  Ex. "Was the ticket just closed?"<br>
   *      "Send a user notification message."
   *
   *@return    The description value
   */
  public String getDescription();


  /**
   *  This method performs the component operation
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context);
}

