/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.controller;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.servlets.ControllerMainMenuHook;
import org.aspcfs.modules.beans.ModuleBean;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * When a template requiring navigation is requested, this class generates the
 * HTML for the navigation. <p>
 * <p/>
 * - 3 types of menus are generated: a tabbed version, a graphic version, and a
 * text version. Each is placed in the request for later retrieval.<br>
 * - The configuration is stored in XML
 *
 * @author mrajkowski
 * @version $Id: MainMenuHook.java,v 1.15.90.1 2004/08/05 21:23:18 ananth Exp
 *          $
 * @created July 12, 2001
 */
public class MainMenuHook implements ControllerMainMenuHook {

  private ArrayList menuItems;
  private URL url;
  private ServletContext context;


  /**
   * Called only once by the servlet controller during initialization,
   * processes the config parameters
   *
   * @param config Description of Parameter
   * @return Description of the Returned Value
   * @since 1.1
   */
  public String executeControllerMainMenu(ServletConfig config) {
    context = config.getServletContext();
    menuItems = new ArrayList();
    if (config.getInitParameter("ModuleConfig") != null) {
      try {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("MainMenuHook-> Loading menu configuration");
        }
        url = context.getResource("/WEB-INF/" + config.getInitParameter("ModuleConfig"));
        load();
      } catch (Exception e) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("MainMenuHook-> Error: " + e.getStackTrace());
        }
      }
    }
    return ("");
  }


  /**
   * Description of the Method
   */
  public void reload() {
    if (menuItems != null) {
      menuItems.clear();
      load();
    }
  }


  /**
   * Reads in the Menus to be used from the XML file
   *
   * @since 1.1
   */
  public void load() {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("MainMenuHook-> Loading menu configuration");
    }
    if (url == null) {
      return;
    }
    try {
      XMLUtils xml = new XMLUtils(url);
      parseAllMenus(xml.getDocument());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Generate the module specific information, including HTML page attributes,
   * the shared main menu, and submenus, based on permissions.
   *
   * @param request    Description of Parameter
   * @param actionPath Description of Parameter
   * @since 1.1
   */
  public void generateMenu(HttpServletRequest request, String actionPath) {
    UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
    if (thisUser == null) {
      return;
    }
    //Use the connection element and system status to access the cached permissions table
    ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute(
        "ConnectionElement");
    SystemStatus systemStatus = null;
    if (ce != null) {
      systemStatus = (SystemStatus) ((Hashtable) context.getAttribute(
          "SystemStatus")).get(ce.getUrl());
    }
    ModuleBean thisModule = (ModuleBean) request.getAttribute("ModuleBean");
    if (thisModule == null) {
      thisModule = new ModuleBean();
      request.setAttribute("ModuleBean", thisModule);
    }
    //Build the menus
    int menuWidth = 0;
    StringBuffer menuTops = new StringBuffer();
    StringBuffer menu = new StringBuffer();
    StringBuffer graphicMenu = new StringBuffer();
    StringBuffer smallMenu = new StringBuffer();
    ArrayList smallMenuList = new ArrayList();

    //Build the graphic menu and the module submenu
    Iterator menuItemsList = menuItems.iterator();
    boolean isOfflineMode = Boolean.parseBoolean(ApplicationPrefs.getPref(context, "OFFLINE_MODE"));
    while (menuItemsList.hasNext()) {
      MainMenuItem thisMenu = (MainMenuItem) menuItemsList.next();
      if ("".equals(thisMenu.getPermission()) || (systemStatus != null && systemStatus.hasPermission(
          thisUser.getUserId(), thisMenu.getPermission() + (isOfflineMode?"-offline":"")))) {
        //Check if the system status has a preference for this menu
        String pageTitle = null;
        String shortHtml = null;
        String longHtml = null;
        if (systemStatus != null) {
          pageTitle = systemStatus.getMenuProperty(
              thisMenu.getPageTitle(), "page_title");
          shortHtml = systemStatus.getMenuProperty(
              thisMenu.getPageTitle(), "menu_title");
          longHtml = systemStatus.getMenuProperty(
              thisMenu.getPageTitle(), "page_title");
        }
        if ((thisModule.getMenuKey() != null && thisMenu.hasActionName(thisModule.getMenuKey())) ||
            (thisModule.getMenuKey() == null && thisMenu.hasActionName(actionPath))) {
        	String actionName = thisModule.getMenuKey() == null ? actionPath : thisModule.getMenuKey();        	
        	String lastAction = (!actionPath.equalsIgnoreCase("CustomTabs")) ? actionPath: actionName;
        	request.setAttribute("lastAction", lastAction);
          //The user is on this link/module
          if (pageTitle != null) {
            thisModule.setName(pageTitle);
          } else {
            thisModule.setName(thisMenu.getPageTitle());
          }
          //Set the on state of the menu
          menuTops.append(
              "<th class=\"mtab-ls\"><img border=\"0\" src=\"images/blank.gif\" /></th><th class=\"mtab-rs\"><img border=\"0\" src=\"images/blank.gif\" /></th>");
          menu.append("<th colspan=\"2\" class=\"menutabs-th\" nowrap>");
          menu.append("<a href=\"" + thisMenu.getLink() + "\">");
          if (shortHtml != null) {
            menu.append(shortHtml);
          } else {
            menu.append(thisMenu.getShortHtml());
          }
          menu.append("</a>");
          menu.append("</th>");
          graphicMenu.append(
              "<a href='" + thisMenu.getLink() + "'><img border='0' src='images/" + thisMenu.getGraphicOn() + "' width='" + thisMenu.getGraphicWidth() + "' height='" + thisMenu.getGraphicHeight() + "'></a>");
          if (longHtml != null) {
            smallMenuList.add(
                "<a " + addSelectedClass(thisMenu) + "href='" + thisMenu.getLink() + "'>" + longHtml + "</a>");
          } else {
            smallMenuList.add(
                "<a " + addSelectedClass(thisMenu) + "href='" + thisMenu.getLink() + "'>" + thisMenu.getLongHtml() + "</a>");
          }
          //Build the submenu
          Iterator j = thisMenu.getSubmenuItems().iterator();
          while (j.hasNext()) {
            SubmenuItem thisItem = (SubmenuItem) j.next();
            //If user has permission then...
            SubmenuItem newItem = new SubmenuItem(thisItem);
            if (newItem.getName().equals(thisModule.getSubmenuKey())) {
              newItem.setIsActive(true);
              newItem.setHtmlClass("submenuItemSelected");
            }
            thisModule.addMenuItem(newItem);
          }
        } else {
          //The user is not on this link, set the off state of the menu
          menuTops.append(
              "<td class=\"mtab-l\"><img border=\"0\" src=\"images/blank.gif\" /></td><td class=\"mtab-r\"><img border=\"0\" src=\"images/blank.gif\" /></td>");
          menu.append("<td class=\"menutabs-td\" colspan=\"2\" nowrap");
          if (thisMenu.getShortHtmlRollover()) {
            menu.append(" class=\"menutabUnselectedLinkOff\"");
            menu.append(
                " onmouseover=\"swapClass(this,'menutabUnselectedLinkOn')\" onmouseout=\"swapClass(this,'menutabUnselectedLinkOff')\"");
          }
          menu.append(">");
          menu.append("<a href=\"" + thisMenu.getLink() + "\">");
          if (shortHtml != null) {
            menu.append(shortHtml);
          } else {
            menu.append(thisMenu.getShortHtml());
          }
          menu.append("</a>");
          menu.append("</td>");

          graphicMenu.append("<a href='" + thisMenu.getLink() + "'");
          if (thisMenu.hasGraphicRollover()) {
            if (shortHtml != null) {
              graphicMenu.append(
                  " onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('" + shortHtml + "','','images/" + thisMenu.getGraphicRollover() + "',1)\"");
            } else {
              graphicMenu.append(
                  " onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('" + thisMenu.getShortHtml() + "','','images/" + thisMenu.getGraphicRollover() + "',1)\"");
            }
          }
          graphicMenu.append(">");
          graphicMenu.append("<img ");
          graphicMenu.append("border='0' ");
          graphicMenu.append("src='images/" + thisMenu.getGraphicOff() + "' ");
          graphicMenu.append("width='" + thisMenu.getGraphicWidth() + "' ");
          graphicMenu.append("height='" + thisMenu.getGraphicHeight() + "' ");

          graphicMenu.append(">");
          graphicMenu.append("</a>");
          if (pageTitle != null) {
            smallMenuList.add(
                "<a " + addNormalClass(thisMenu) + "href='" + thisMenu.getLink() + "'>" + pageTitle + "</a>");
          } else {
            smallMenuList.add(
                "<a " + addNormalClass(thisMenu) + "href='" + thisMenu.getLink() + "'>" + thisMenu.getLongHtml() + "</a>");
          }
        }
        menuWidth += Integer.parseInt(thisMenu.getGraphicWidth());
      }
    }

    //Build the small menu
    Iterator i = smallMenuList.iterator();
    while (i.hasNext()) {
      String tmp = (String) i.next();
      if (i.hasNext()) {
        smallMenu.append(tmp + " | ");
      } else {
        smallMenu.append(tmp);
      }
    }

    //Output several types of menus
    String[] theMenus = new String[5];
    theMenus[0] = menu.toString();// + "<td style=\"width:100%; border: 0px; background-image: none !important; background: #EDEDED; border-bottom: 1px #000 solid; cursor: default\"><img border=\"0\" src=\"images/blank.gif\" /></td>";
    theMenus[1] = "<td width=\"" + menuWidth + "\" nowrap>" + graphicMenu.toString() + "</td>";
    theMenus[2] = smallMenu.toString();
    theMenus[3] = menu.toString();
    theMenus[4] = menuTops.toString();// + "<td style=\"width:100%; border: 0px; background-image: none !important; background: #FFF; cursor: default\"><img border=\"0\" src=\"images/blank.gif\" /></td>";
    request.setAttribute("MainMenu", theMenus[0]);
    request.setAttribute("MainMenuGraphic", theMenus[1]);
    request.setAttribute("MainMenuWidth", String.valueOf(menuWidth));
    request.setAttribute("MainMenuSmall", theMenus[2]);
    request.setAttribute("MainMenuTableCells", theMenus[3]);
    request.setAttribute("MainMenuTops", theMenus[4]);
  }


  /**
   * Takes the in-memory Document object and creates nodes of the module items
   *
   * @param document Description of Parameter
   * @since 1.17
   */
  private void parseAllMenus(Document document) {
    //Process the menu tags
    menuItems.clear();
    NodeList menuTags = document.getElementsByTagName("menu");
    for (int i = 0; i < menuTags.getLength(); i++) {
      Element menuTag = (Element) menuTags.item(i);
      MainMenuItem thisMenu = parseMenu(menuTag);
      menuItems.add(thisMenu);
    }
  }


  /**
   * Once the in-memory document has split out the module items using
   * parseAllMenus(), each menu is processed for items and submenu items.
   *
   * @param e Description of Parameter
   * @return Description of the Returned Value
   * @since 1.17
   */
  private MainMenuItem parseMenu(Element e) {
    MainMenuItem mainItem = new MainMenuItem();
    //mainItem.setName(e.getAttribute("name"));
    ArrayList submenuTable = mainItem.getSubmenuItems();

    NodeList children = e.getChildNodes();
    int len = children.getLength();
    for (int i = 0; i < len; i++) {
      if (children.item(i).getNodeType() != Element.ELEMENT_NODE) {
        continue;
      }
      Element child = (Element) children.item(i);
      String childName = child.getTagName();

      if (childName.equals("action")) {
        mainItem.addActionName(child.getAttribute("name"));
      } else if (childName.equals("page")) {
        mainItem.setPageTitle(child.getAttribute("title"));
      } else if (childName.equals("permission")) {
        mainItem.setPermission(child.getAttribute("value"));
      } else if (childName.equals("long_html")) {
        mainItem.setLongHtml(child.getAttribute("value"));
        mainItem.setLongHtmlRollover(child.getAttribute("rollover"));
      } else if (childName.equals("short_html")) {
        mainItem.setShortHtml(child.getAttribute("value"));
        mainItem.setShortHtmlRollover(child.getAttribute("rollover"));
      } else if (childName.equals("link")) {
        mainItem.setLink(child.getAttribute("value"));
        mainItem.setClassNormal(child.getAttribute("classNormal"));
        mainItem.setClassSelected(child.getAttribute("classSelected"));
      } else if (childName.equals("graphic")) {
        mainItem.setGraphicWidth(child.getAttribute("width"));
        mainItem.setGraphicHeight(child.getAttribute("height"));
        mainItem.setGraphicOn(child.getAttribute("on"));
        mainItem.setGraphicOff(child.getAttribute("off"));
        mainItem.setGraphicRollover(child.getAttribute("rollover"));
      } else if (childName.equals("submenu")) {
        SubmenuItem submenuItem = new SubmenuItem();
        submenuItem.setName(child.getAttribute("name"));
        NodeList submenu = child.getChildNodes();
        int submenuLen = submenu.getLength();
        for (int j = 0; j < submenuLen; j++) {
          if (submenu.item(j).getNodeType() != Element.ELEMENT_NODE) {
            continue;
          }
          Element submenuChild = (Element) submenu.item(j);
          String tagName = submenuChild.getTagName();

          if (tagName.equals("permission")) {
            submenuItem.setPermission(submenuChild.getAttribute("value"));
          } else if (tagName.equals("long_html")) {
            submenuItem.setLongHtml(submenuChild.getAttribute("value"));
          } else if (tagName.equals("short_html")) {
            submenuItem.setShortHtml(submenuChild.getAttribute("value"));
          } else if (tagName.equals("link")) {
            submenuItem.setLink(submenuChild.getAttribute("value"));
          } else if (tagName.equals("graphic")) {
            submenuItem.setGraphicWidth(submenuChild.getAttribute("width"));
            submenuItem.setGraphicHeight(submenuChild.getAttribute("height"));
            submenuItem.setGraphicOn(submenuChild.getAttribute("on"));
            submenuItem.setGraphicOff(submenuChild.getAttribute("off"));
            submenuItem.setGraphicRollover(
                submenuChild.getAttribute("rollover"));
          }
        }
        submenuTable.add(submenuItem);
      }
    }

    return mainItem;
  }


  /**
   * Adds a feature to the NormalClass attribute of the MainMenuHook class
   *
   * @param thisItem The feature to be added to the NormalClass attribute
   * @return Description of the Return Value
   */
  private static String addNormalClass(MainMenuItem thisItem) {
    if (thisItem.getClassNormal() != null && !"".equals(
        thisItem.getClassNormal())) {
      return "class=\"" + thisItem.getClassNormal() + "\" ";
    }
    return "";
  }


  /**
   * Adds a feature to the SelectedClass attribute of the MainMenuHook class
   *
   * @param thisItem The feature to be added to the SelectedClass attribute
   * @return Description of the Return Value
   */
  private static String addSelectedClass(MainMenuItem thisItem) {
    if (thisItem.getClassSelected() != null && !"".equals(
        thisItem.getClassSelected())) {
      return "class=\"" + thisItem.getClassSelected() + "\" ";
    }
    return "";
  }
}

