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
package org.aspcfs.utils.web;

/**
 * Description of the Interface
 *
 * @author akhi_m
 * @version $Id: HtmlCoreAttributes.java,v 1.3 2003/01/13 21:24:01 mrajkowski
 *          Exp $
 * @created October 22, 2002
 */
public interface HtmlCoreAttributes {
  /**
   * Sets the id attribute of the ScheduledActions object
   *
   * @param id The new id value
   */
  public void setId(String id);


  /**
   * Sets the class attribute of the ScheduledActions object
   *
   * @param rowClass The new rowClass value
   */
  public void setElementClass(String rowClass);


  /**
   * Sets the style attribute of the ScheduledActions object
   *
   * @param style The new style value
   */
  public void setStyle(String style);


  /**
   * Sets the title attribute of the ScheduledActions object
   *
   * @param title The new title value
   */
  public void setTitle(String title);


  /**
   * Gets the coreAttributes attribute of the ScheduledActions object
   *
   * @return The coreAttributes value
   */
  public String getCoreAttributes();
}

