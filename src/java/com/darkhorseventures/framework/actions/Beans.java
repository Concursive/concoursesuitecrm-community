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
 * Description of the Class
 *
 * @author kevin duffey
 * @author matt rajkowski
 * @version $Id$
 * @created July 1, 2001
 */
public final class Beans {
  private String beanName = "";
  private String className = "";
  private int beanScope = 0;
  private boolean defaultBean = false;

  // true if default bean


  /**
   * Constructor for the Beans object
   *
   * @param beanName    Description of the Parameter
   * @param beanScope   Description of the Parameter
   * @param className   Description of the Parameter
   * @param defaultBean Description of the Parameter
   */
  public Beans(String beanName, int beanScope, String className, boolean defaultBean) {
    this.beanName = beanName;
    this.beanScope = beanScope;
    this.className = className;
    this.defaultBean = defaultBean;
  }


  /**
   * Gets the defaultBean attribute of the Beans object
   *
   * @return The defaultBean value
   */
  public boolean isDefaultBean() {
    return defaultBean;
  }


  /**
   * Gets the className attribute of the Beans object
   *
   * @return The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   * Gets the beanName attribute of the Beans object
   *
   * @return The beanName value
   */
  public String getBeanName() {
    return beanName;
  }


  /**
   * Gets the beanScope attribute of the Beans object
   *
   * @return The beanScope value
   */
  public int getBeanScope() {
    return beanScope;
  }
}

