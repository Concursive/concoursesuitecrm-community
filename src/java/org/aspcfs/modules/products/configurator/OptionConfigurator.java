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

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *  The option configurator interface defines the basic functionality of any
 *  option configurator
 *
 *@author     ananth
 *@created    March 31, 2004
 *@version    $Id: OptionConfigurator.java,v 1.2 2004/05/04 15:52:27 mrajkowski
 *      Exp $
 */
public interface OptionConfigurator {
  // constants
  public final static String INTEGER = "integer";
  public final static String BOOLEAN = "boolean";
  public final static String FLOAT = "float";
  public final static String TIMESTAMP = "timestamp";
  public final static String TEXT = "text";


  /**
   *  Gets the description attribute of the OptionConfigurator object
   *
   *@return    The description value
   */
  public String getDescription();


  /**
   *  Description of the Method
   */
  public void buildProperties();


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public boolean arePropertiesConfigured();


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean saveProperties(Connection db) throws SQLException;


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  optionId          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryProperties(Connection db, int optionId) throws SQLException;


  /**
   *  Description of the Method
   *
   *@param  request           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void prepareContext(HttpServletRequest request, Connection db) throws SQLException;


  /**
   *  Gets the html attribute of the OptionConfigurator object
   *
   *@param  request  Description of the Parameter
   *@return          The html value
   */
  public String getHtml(HttpServletRequest request);


  /**
   *  Description of the Method
   *
   *@param  optionId  Description of the Parameter
   *@return           Description of the Return Value
   */
  public boolean validate(int optionId);


  /**
   *  Gets the resultType attribute of the OptionConfigurator object
   *
   *@return    The resultType value
   */
  public int getResultType();
}

