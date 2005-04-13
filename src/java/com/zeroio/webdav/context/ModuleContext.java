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
package com.zeroio.webdav.context;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.io.FileNotFoundException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.apache.naming.resources.ResourceAttributes;
import org.aspcfs.controller.SystemStatus;

/**
 *  Description of the Interface
 *
 *@author     ananth
 *@created    November 3, 2004
 *@version    $Id$
 */
public interface ModuleContext {

  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  fileLibraryPath   Description of the Parameter
   * @param  userId            Description of the Parameter
   * @param  thisSystem        Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException;


  /**
   *  Description of the Method
   *
   * @param  name                 Description of the Parameter
   * @return                      Description of the Return Value
   * @exception  NamingException  Description of the Exception
   */
  public Object lookup(String name) throws NamingException;


  /**
   *  Description of the Method
   *
   * @param  db                         Description of the Parameter
   * @param  name                       Description of the Parameter
   * @param  thisSystem                 Description of the Parameter
   * @return                            Description of the Return Value
   * @exception  NamingException        Description of the Exception
   * @exception  SQLException           Description of the Exception
   * @exception  FileNotFoundException  Description of the Exception
   */
  public Object lookup(SystemStatus thisSystem, Connection db, String name) throws NamingException, SQLException, FileNotFoundException;


  /**
   *  Gets the bindings attribute of the ModuleContext object
   *
   * @return    The bindings value
   */
  public Hashtable getBindings();


  /**
   *  Gets the attributes attribute of the ModuleContext object
   *
   * @return    The attributes value
   */
  public Hashtable getAttributes();


  /**
   *  Gets the attributes attribute of the ModuleContext object
   *
   *@param  path                       Description of the Parameter
   *@param  db                         Description of the Parameter
   *@param  thisSystem                 Description of the Parameter
   *@return                            The attributes value
   *@exception  NamingException        Description of the Exception
   *@exception  SQLException           Description of the Exception
   *@exception  FileNotFoundException  Description of the Exception
   */
  public ResourceAttributes getAttributes(SystemStatus thisSystem, Connection db, String path)
       throws NamingException, SQLException, FileNotFoundException;


  /**
   *  Description of the Method
   *
   * @param  name                 Description of the Parameter
   * @return                      Description of the Return Value
   * @exception  NamingException  Description of the Exception
   */
  public NamingEnumeration list(String name) throws NamingException;


  /**
   *  Gets the permission attribute of the ModuleContext object
   *
   * @return    The permission value
   */
  public String getPermission();
}

