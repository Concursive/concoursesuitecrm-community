package org.aspcfs.utils.web;

/**
 *  Description of the Interface
 *
 *@author     akhi_m
 *@created    October 22, 2002
 *@version    $Id$
 */
public interface HtmlCoreAttributes {
  /**
   *  Sets the id attribute of the ScheduledActions object
   *
   *@param  id  The new id value
   */
  public void setId(String id);


  /**
   *  Sets the class attribute of the ScheduledActions object
   *
   *@param  rowClass  The new rowClass value
   */
  public void setElementClass(String rowClass);


  /**
   *  Sets the style attribute of the ScheduledActions object
   *
   *@param  style  The new style value
   */
  public void setStyle(String style);


  /**
   *  Sets the title attribute of the ScheduledActions object
   *
   *@param  title  The new title value
   */
  public void setTitle(String title);


  /**
   *  Gets the coreAttributes attribute of the ScheduledActions object
   *
   *@return    The coreAttributes value
   */
  public String getCoreAttributes();
}

