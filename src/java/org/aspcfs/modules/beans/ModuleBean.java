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
package org.aspcfs.modules.beans;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SubmenuItem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Contains information that a JSP template can access when displaying the page
 *
 * @author matt rajkowski
 * @version $Id$
 * @created July 13, 2001
 */
public class ModuleBean {

  private String name = "";
  private String currentAction = "";
  private String menuKey = null;
  private String submenuKey = "";
  private ArrayList menuItems = new ArrayList();


  /**
   * Constructor for the ModuleBean object
   *
   * @since 1.0
   */
  public ModuleBean() {
  }


  /**
   * Sets the Name attribute of the ModuleBean object
   *
   * @param tmp The new Name value
   * @since 1.0
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the CurrentAction attribute of the ModuleBean object
   *
   * @param tmp The new CurrentAction value
   * @since 1.0
   */
  public void setCurrentAction(String tmp) {
    this.currentAction = tmp;
  }


  /**
   * Sets the menuKey attribute of the ModuleBean object
   *
   * @param tmp The new menuKey value
   */
  public void setMenuKey(String tmp) {
    this.menuKey = tmp;
  }


  /**
   * Sets the submenuKey attribute of the ModuleBean object
   *
   * @param tmp The new submenuKey value
   */
  public void setSubmenuKey(String tmp) {
    this.submenuKey = tmp;
  }


  /**
   * Sets the SubmenuKey attribute of the ModuleBean object
   *
   * @param tmp     The new SubmenuKey value
   * @param context The new submenuKey value
   */
  public void setSubmenuKey(ActionContext context, String tmp) {
    this.submenuKey = tmp;
  }


  /**
   * Gets the Name attribute of the ModuleBean object
   *
   * @return The Name value
   * @since 1.0
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the MenuItems attribute of the ModuleBean object
   *
   * @return The MenuItems value
   * @since 1.0
   */
  public ArrayList getMenuItems() {
    return menuItems;
  }


  /**
   * Gets the CurrentAction attribute of the ModuleBean object
   *
   * @return The CurrentAction value
   * @since 1.0
   */
  public String getCurrentAction() {
    return currentAction;
  }


  /**
   * Gets the menuKey attribute of the ModuleBean object
   *
   * @return The menuKey value
   */
  public String getMenuKey() {
    return menuKey;
  }


  /**
   * Gets the submenuKey attribute of the ModuleBean object
   *
   * @return The submenuKey value
   */
  public String getSubmenuKey() {
    return this.submenuKey;
  }


  /**
   * Gets the Menu attribute of the ModuleBean object
   *
   * @return The Menu value
   * @since 1.0
   */
  public String getMenu() {
    StringBuffer menu = new StringBuffer();
    Iterator i = menuItems.iterator();

    while (i.hasNext()) {
      SubmenuItem thisItem = (SubmenuItem) i.next();
      if (menu.length() != 0) {
        menu.append(" | " + thisItem.getHtml());
      } else {
        menu.append(thisItem.getHtml());
      }
    }
    return menu.toString();
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   * @since 1.0
   */
  public boolean hasName() {
    return (!name.equals(""));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   * @since 1.0
   */
  public boolean hasMenu() {
    return (!menuItems.isEmpty());
  }


  /**
   * Adds a feature to the MenuItem attribute of the ModuleBean object
   *
   * @param thisItem The feature to be added to the MenuItem attribute
   */
  public void addMenuItem(SubmenuItem thisItem) {
    menuItems.add(thisItem);
  }

}

