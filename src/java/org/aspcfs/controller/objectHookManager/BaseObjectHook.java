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
package org.aspcfs.controller.objectHookManager;

/**
 *  All base object hooks to be executed need to implement this class
 *
 *@author     matt rajkowski
 *@created    October 14, 2002
 *@version    $Id: BaseObjectHook.java,v 1.2 2002/10/21 19:47:58 mrajkowski Exp
 *      $
 */
public interface BaseObjectHook {
  /**
   *  Let the hook manager know if a connection is required for this hook.
   *
   *@return    Description of the Return Value
   */
  public boolean requiresConnection();


  /**
   *  Sets the connection attribute of the BaseObjectHook object if needed
   *
   *@param  tmp  The new connection value
   */
  public void setConnection(java.sql.Connection tmp);


  /**
   *  Sets the previousObject attribute of the BaseObjectHook object for
   *  comparing previous properties
   *
   *@param  tmp  The new previousObject value
   */
  public void setPreviousObject(Object tmp);


  /**
   *  Sets the currentObject attribute of the BaseObjectHook object
   *
   *@param  tmp  The new currentObject value
   */
  public void setCurrentObject(Object tmp);


  /**
   *  Sets the fileLibraryPath attribute of the BaseObjectHook object
   *
   *@param  tmp  The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp);


  /**
   *  Method that gets executed when an object has been inserted
   */
  public void processInsert();


  /**
   *  Method that gets executed when an object has been updated
   */
  public void processUpdate();


  /**
   *  Method that gets executed when an object has been deleted
   */
  public void processDelete();
}

