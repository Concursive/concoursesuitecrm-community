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
package com.zeroio.webdav.context;

import org.apache.naming.resources.ResourceAttributes;
import org.aspcfs.controller.SystemStatus;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * Description of the Interface
 *
 * @author ananth
 * @version $Id: ModuleContext.java,v 1.2 2005/04/13 20:04:30 mrajkowski Exp
 *          $
 * @created November 3, 2004
 */
public interface ModuleContext {

  /**
   * Description of the Method
   *
   * @param db              Description of the Parameter
   * @param fileLibraryPath Description of the Parameter
   * @param userId          Description of the Parameter
   * @param thisSystem      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException;


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object lookup(String name) throws NamingException;


  /**
   * Gets the name attribute of the ModuleContext object
   *
   * @return The name value
   */
  public String getContextName();


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String toString();


  /**
   * Sets the name attribute of the ModuleContext object
   *
   * @param name The new name value
   */
  public void setContextName(String name);


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param name       Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException       Description of the Exception
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public Object lookup(SystemStatus thisSystem, Connection db, String name) throws NamingException, SQLException, FileNotFoundException;


  /**
   * Gets the bindings attribute of the ModuleContext object
   *
   * @return The bindings value
   */
  public Hashtable getBindings();


  /**
   *  Description of the Method
   *
   * @param  db                   Description of the Parameter
   * @param  path                 Description of the Parameter
   * @return                      Description of the Return Value
   * @exception  SQLException     Description of the Exception
   * @exception  NamingException  Description of the Exception
   */
  //public boolean createSubcontext(Connection db, String path) throws SQLException, NamingException;


  /**
   * Description of the Method
   *
   * @param path       Description of the Parameter
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException       Description of the Exception
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public boolean createSubcontext(SystemStatus thisSystem, Connection db, String path) throws SQLException,
      FileNotFoundException, NamingException;


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param path   Description of the Parameter
   * @param object Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public Object copyResource(SystemStatus thisSystem, Connection db, String path, Object object) throws SQLException,
      IOException, NamingException;


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param object Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public Object copyResource(SystemStatus thisSystem, Connection db, Object object) throws SQLException,
      IOException, NamingException;


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param dest   Description of the Parameter
   * @param object Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public boolean bind(SystemStatus thisSystem, Connection db, String dest, Object object) throws SQLException, NamingException;


  /**
   * Description of the Method
   *
   * @param db     Description of the Parameter
   * @param object Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public boolean bind(SystemStatus thisSystem, Connection db, Object object) throws SQLException, NamingException;


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param dest       Description of the Parameter
   * @param object     Description of the Parameter
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   * @throws NamingException       Description of the Exception
   */
  public boolean move(SystemStatus thisSystem, Connection db, String dest, Object object) throws SQLException,
      FileNotFoundException, NamingException;


  /**
   * Description of the Method
   *
   * @param db   Description of the Parameter
   * @param path Description of the Parameter
   * @throws SQLException    Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public void unbind(SystemStatus thisSystem, Connection db, String path) throws SQLException, NamingException;


  /**
   * Gets the attributes attribute of the ModuleContext object
   *
   * @return The attributes value
   */
  public Hashtable getAttributes();


  /**
   * Gets the attributes attribute of the ModuleContext object
   *
   * @param name Description of the Parameter
   * @return The attributes value
   * @throws NamingException Description of the Exception
   */
  public ResourceAttributes getAttributes(String name) throws NamingException;


  /**
   * Gets the attributes attribute of the ModuleContext object
   *
   * @param path       Description of the Parameter
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @return The attributes value
   * @throws NamingException       Description of the Exception
   * @throws SQLException          Description of the Exception
   * @throws FileNotFoundException Description of the Exception
   */
  public ResourceAttributes getAttributes(SystemStatus thisSystem, Connection db, String path)
      throws NamingException, SQLException, FileNotFoundException;


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public NamingEnumeration list(String name) throws NamingException;


  /**
   * Gets the permission attribute of the ModuleContext object
   *
   * @return The permission value
   */
  public String getPermission();
}

