package com.darkhorseventures.cfsmodule;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import com.darkhorseventures.controller.*;

/**
 *  Contains information that a JSP template can access when displaying the page
 *
 *@author     matt
 *@created    July 13, 2001
 */
public class ModuleBean {

  private String name = "";
  private String currentAction = "";
  private String submenuKey = "";
  private Vector menuItems = new Vector();


  /**
   *  Constructor for the ModuleBean object
   *
   *@since    1.0
   */
  public ModuleBean() {
  }


  /**
   *  Sets the Name attribute of the ModuleBean object
   *
   *@param  tmp  The new Name value
   *@since       1.0
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the CurrentAction attribute of the ModuleBean object
   *
   *@param  tmp  The new CurrentAction value
   *@since       1.0
   */
  public void setCurrentAction(String tmp) {
    this.currentAction = tmp;
  }


  /**
   *  Sets the SubmenuKey attribute of the ModuleBean object
   *
   *@param  tmp  The new SubmenuKey value
   *@since
   */
  public void setSubmenuKey(ActionContext context, String tmp) {
    this.submenuKey = tmp;
  }


  /**
   *  Gets the Name attribute of the ModuleBean object
   *
   *@return    The Name value
   *@since     1.0
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the MenuItems attribute of the ModuleBean object
   *
   *@return    The MenuItems value
   *@since     1.0
   */
  public Vector getMenuItems() {
    return menuItems;
  }


  /**
   *  Gets the CurrentAction attribute of the ModuleBean object
   *
   *@return    The CurrentAction value
   *@since     1.0
   */
  public String getCurrentAction() {
    return currentAction;
  }
  
  public String getSubmenuKey() {
    return this.submenuKey;
  }


  /**
   *  Gets the Menu attribute of the ModuleBean object
   *
   *@return    The Menu value
   *@since     1.0
   */
  public String getMenu() {
    StringBuffer menu = new StringBuffer();
    Iterator i = menuItems.iterator();

    while (i.hasNext()) {
      SubmenuItem thisItem = (SubmenuItem)i.next();
      if (menu.length() != 0) {
        menu.append(" | " + thisItem.getHtml());
      } else {
        menu.append(thisItem.getHtml());
      }
    }
    return menu.toString();
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.0
   */
  public boolean hasName() {
    return (!name.equals(""));
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.0
   */
  public boolean hasMenu() {
    return (!menuItems.isEmpty());
  }

  public void addMenuItem(SubmenuItem thisItem) {
    menuItems.add(thisItem);
  }

}

