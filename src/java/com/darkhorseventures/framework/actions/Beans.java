package org.theseus.actions;

public final class Beans
{
  private String   beanName     = "";
  private String   className    = "";
  private int      beanScope    = 0;
  private boolean  defaultBean  = false; // true if default bean

  public Beans(String beanName, int beanScope, String className, boolean defaultBean)
  {
    this.beanName = beanName;
    this.beanScope = beanScope;
    this.className = className;
    this.defaultBean = defaultBean;
  }

  public boolean isDefaultBean()
  {
    return defaultBean;
  }

  public String getClassName()
  {
    return className;
  }

  public String getBeanName()
  {
    return beanName;
  }

  public int getBeanScope()
  {
    return beanScope;
  }
}