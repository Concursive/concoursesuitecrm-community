package org.aspcfs.modules.products.configurator;

import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *  The option configurator interface defines the basic functionality 
 *  of any option configurator 
 *
 * @author     ananth
 * @created    March 31, 2004
 * @version    $Id$
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
   * @return    The description value
   */
  public String getDescription();


  /**
   *  Description of the Method
   */
  public void buildProperties();


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean arePropertiesConfigured();


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean saveProperties(Connection db) throws SQLException;


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  optionId          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryProperties(Connection db, int optionId) throws SQLException;


  /**
   *  Description of the Method
   *
   * @param  request           Description of the Parameter
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void prepareContext(HttpServletRequest request, Connection db) throws SQLException;


  /**
   *  Gets the html attribute of the OptionConfigurator object
   *
   * @param  request  Description of the Parameter
   * @return          The html value
   */
  public String getHtml(HttpServletRequest request);


  /**
   *  Description of the Method
   *
   * @param  optionId  Description of the Parameter
   * @return           Description of the Return Value
   */
  public boolean validate(int optionId);


  /**
   *  Gets the resultType attribute of the OptionConfigurator object
   *
   * @return    The resultType value
   */
  public int getResultType();
}

