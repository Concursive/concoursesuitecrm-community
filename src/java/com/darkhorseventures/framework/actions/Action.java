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

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a single action
 *
 * @author Kevin Duffey
 * @version 1.0
 * @created June 1, 2001
 */
public class Action
    implements java.io.Serializable {
  private String actionName = "";
  private String actionClassName = "";
  private Map beans = new HashMap();
  private Map resources = new HashMap();
  private Object firstBean = null;
  final static long serialVersionUID = -8484048371911908893L;


  /**
   * Create an immutable ActionAction object for any given action class. This
   * can be used in a class manually coded, or read in from an XML
   * configuration file, or a Properties file.
   *
   * @param aName      Description of Parameter
   * @param aClassName Description of Parameter
   */
  public Action(String aName, String aClassName) {
    try {
      actionName = aName;
      actionClassName = aClassName;
    } catch (Throwable t) {
    }
  }


  /**
   * Returns a <code>Beans</code> reference if found in the Map with the passed
   * in key name to look for.
   *
   * @param beanName the key to look up for a <code>Beans</code> reference
   * @return Beans the found <code>Beans</code> reference or null if
   *         not found
   */
  public Beans getBean(String beanName) {
    return (Beans) beans.get(beanName);
  }


  /**
   * Returns a resource if the passed in resourceName is found in the resources
   * Map.
   *
   * @param resourceName the name to look for in the resources Map
   * @return Resource the returned reference to a Resource, if
   *         found, or null if not.
   */
  public Resource getResource(String resourceName) {
    return (Resource) resources.get(resourceName);
  }


  /**
   * Gets the Resources attribute of the Action object
   *
   * @return The Resources value
   */
  public Map getResources() {
    return resources;
  }


  /**
   * Gets the Beans attribute of the Action object
   *
   * @return The Beans value
   */
  public Map getBeans() {
    return beans;
  }


  /**
   * Gets the BeanCount attribute of the Action object
   *
   * @return The BeanCount value
   */
  public long getBeanCount() {
    return beans.size();
  }


  /**
   * Gets the ResourceCount attribute of the Action object
   *
   * @return The ResourceCount value
   */
  public long getResourceCount() {
    return resources.size();
  }


  /**
   * returns the name of this action
   *
   * @return The ActionName value
   */
  public String getActionName() {
    return actionName;
  }


  /**
   * Returns the name of the action class. This should be a fully qualified
   * package name and class name
   *
   * @return The ActionClassName value
   */
  public String getActionClassName() {
    return actionClassName;
  }


  /**
   * Adds a feature to the Bean attribute of the Action object
   *
   * @param key  The feature to be added to the Bean attribute
   * @param bean The feature to be added to the Bean attribute
   */
  public void addBean(String key, Beans bean) {
    beans.put(key, bean);
  }


  /**
   * Adds a feature to the Resource attribute of the Action object
   *
   * @param key      The feature to be added to the Resource attribute
   * @param resource The feature to be added to the Resource attribute
   */
  public void addResource(String key, Resource resource) {
    resources.put(key, resource);
  }
}

