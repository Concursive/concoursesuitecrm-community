package com.darkhorseventures.framework.actions;

/**
 *  Description of the Class
 *
 *@version    $Id$
 */
public final class Beans {
  private String beanName = "";
  private String className = "";
  private int beanScope = 0;
  private boolean defaultBean = false;
  // true if default bean


  /**
   *  Constructor for the Beans object
   *
   *@param  beanName     Description of the Parameter
   *@param  beanScope    Description of the Parameter
   *@param  className    Description of the Parameter
   *@param  defaultBean  Description of the Parameter
   */
  public Beans(String beanName, int beanScope, String className, boolean defaultBean) {
    this.beanName = beanName;
    this.beanScope = beanScope;
    this.className = className;
    this.defaultBean = defaultBean;
  }


  /**
   *  Gets the defaultBean attribute of the Beans object
   *
   *@return    The defaultBean value
   */
  public boolean isDefaultBean() {
    return defaultBean;
  }


  /**
   *  Gets the className attribute of the Beans object
   *
   *@return    The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   *  Gets the beanName attribute of the Beans object
   *
   *@return    The beanName value
   */
  public String getBeanName() {
    return beanName;
  }


  /**
   *  Gets the beanScope attribute of the Beans object
   *
   *@return    The beanScope value
   */
  public int getBeanScope() {
    return beanScope;
  }
}
