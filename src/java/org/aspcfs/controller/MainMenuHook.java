package com.darkhorseventures.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.servlets.ControllerMainMenuHook;
import java.io.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.cfsmodule.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *  When a template requiring navigation is requested, this class generates the
 *  HTML for the navigation. <p>
 *
 *  The configuration is stored in XML
 *
 *@author     mrajkowski
 *@created    July 12, 2001
 *@version    $Id$
 */
public class MainMenuHook implements ControllerMainMenuHook {

  private ArrayList menuItems;
  private File file;
  private ServletContext context;


  /**
   *  Called only once by the servlet controller during initialization,
   *  processes the config parameters
   *
   *@param  config  Description of Parameter
   *@return         Description of the Returned Value
   *@since          1.1
   */
  public String executeControllerMainMenu(ServletConfig config) {
    context = config.getServletContext();
    menuItems = new ArrayList();
    if (config.getInitParameter("ModuleConfig") != null) {
      file = new File(context.getRealPath("/WEB-INF/" + config.getInitParameter("ModuleConfig")));
      load();
    }
    return ("");
  }
  
  public void reload() {
    if (menuItems != null) {
      menuItems.clear();
      load();
    }
  }


  /**
   *  Reads in the Menus to be used from the XML file
   *
   *@since    1.1
   */
  public void load() {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("MainMenuHook-> Loading menu configuration");
    }
    if (file == null) {
      return;
    }
    try {
      Document document = parseDocument();
      parseAllMenus(document);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *  Generate the module specific information, including HTML page attributes,
   *  the shared main menu, and submenus, based on permissions.
   *
   *@param  request     Description of Parameter
   *@param  actionPath  Description of Parameter
   *@since              1.1
   */
  public void generateMenu(HttpServletRequest request, String actionPath) {

    UserBean thisUser = (UserBean)request.getSession().getAttribute("User");
    if (thisUser == null) {
      return;
    }

    ModuleBean thisModule = (ModuleBean)request.getAttribute("ModuleBean");
    if (thisModule == null) {
      thisModule = new ModuleBean();
      request.setAttribute("ModuleBean", thisModule);
    }

    //Build the menus
    int menuWidth = 0;
    StringBuffer menu = new StringBuffer();
    StringBuffer smallMenu = new StringBuffer();
    ArrayList smallMenuList = new ArrayList();

    //Build the graphic menu and the module submenu
    Iterator menuItemsList = menuItems.iterator();
    while (menuItemsList.hasNext()) {
      MainMenuItem thisMenu = (MainMenuItem)menuItemsList.next();
      if ("".equals(thisMenu.getPermission()) || thisUser.hasPermission(thisMenu.getPermission())) {
        if (thisMenu.hasActionName(actionPath)) {
          //The user is on this link/module
          thisModule.setName(thisMenu.getPageTitle());
  
          //Set the on state of the menu
          menu.append("<a href='" + thisMenu.getLink() + "'><img border='0' src='images/" + thisMenu.getGraphicOn() + "' width='" + thisMenu.getGraphicWidth() + "' height='" + thisMenu.getGraphicHeight() + "'></a>");
          smallMenuList.add("<a href='" + thisMenu.getLink() + "'>" + thisMenu.getLongHtml() + "</a>");

          //Build the submenu
          Iterator j = thisMenu.getSubmenuItems().iterator();
          while (j.hasNext()) {
            SubmenuItem thisItem = (SubmenuItem)j.next();
            //If user has permission then...
            SubmenuItem newItem = new SubmenuItem(thisItem);
            if (newItem.getName().equals(thisModule.getSubmenuKey())) {
              newItem.setIsActive(true);
              newItem.setHtmlClass("rs");
            }
            thisModule.addMenuItem(newItem);
          }

        } else {
          //The user is not on this link, set the off state of the menu
          menu.append("<a href='" + thisMenu.getLink() + "'");
          if (thisMenu.hasRollover()) {
            menu.append(" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('" + thisMenu.getShortHtml() + "','','images/" + thisMenu.getGraphicRollover() + "',1)\"");
          }
          menu.append(">");
          menu.append("<img ");
          menu.append("border='0' ");
          menu.append("src='images/" + thisMenu.getGraphicOff() + "' ");
          menu.append("width='" + thisMenu.getGraphicWidth() + "' ");
          menu.append("height='" + thisMenu.getGraphicHeight() + "' ");
          
          menu.append(">");
          menu.append("</a>");
          smallMenuList.add("<a href='" + thisMenu.getLink() + "'>" + thisMenu.getLongHtml() + "</a>");
        }
        menuWidth += Integer.parseInt(thisMenu.getGraphicWidth());
      }
    }

    //Build the small menu
    Iterator i = smallMenuList.iterator();
    while (i.hasNext()) {
      String tmp = (String)i.next();
      if (i.hasNext()) {
        smallMenu.append(tmp + " | ");
      } else {
        smallMenu.append(tmp);
      }
    }

    //Output the menus
    String[] theMenus = new String[2];
    //TODO: Update the CFS templates to work with the following commented out line instead
    //theMenus[0] = menu.toString();
    theMenus[0] = "<td width=" + menuWidth + ">" + menu.toString() + "</td>";
    theMenus[1] = smallMenu.toString();
    request.setAttribute("MainMenu", theMenus[0]);
    request.setAttribute("MainMenuWidth", String.valueOf(menuWidth));
    request.setAttribute("MainMenuSmall", theMenus[1]);
  }


  /**
   *  Reads an XML file into a Document object with nodes
   *
   *@return                                   Description of the Returned Value
   *@exception  FactoryConfigurationError     Description of Exception
   *@exception  ParserConfigurationException  Description of Exception
   *@exception  SAXException                  Description of Exception
   *@exception  IOException                   Description of Exception
   *@since                                    1.17
   */
  private Document parseDocument()
       throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(file);
    return document;
  }


  /**
   *  Takes the in-memory Document object and creates nodes of the module items
   *
   *@param  document  Description of Parameter
   *@since            1.17
   */
  private void parseAllMenus(Document document) {
    menuItems.clear();
    NodeList menuTags = document.getElementsByTagName("menu");
    for (int i = 0; i < menuTags.getLength(); i++) {
      Element menuTag = (Element)menuTags.item(i);
      MainMenuItem thisMenu = parseMenu(menuTag);
      menuItems.add(thisMenu);
    }
  }


  /**
   *  Once the in-memory document has split out the module items using
   *  parseAllMenus(), each menu is processed for items and submenu items.
   *
   *@param  e                                 Description of Parameter
   *@return                                   Description of the Returned Value
   *@since                                    1.17
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
      Element child = (Element)children.item(i);
      String childName = child.getTagName();

      if (childName.equals("action")) {
        mainItem.addActionName(child.getAttribute("name"));
      } else if (childName.equals("page")) {
        mainItem.setPageTitle(child.getAttribute("title"));
      } else if (childName.equals("permission")) {
        mainItem.setPermission(child.getAttribute("value"));
      } else if (childName.equals("long_html")) {
        mainItem.setLongHtml(child.getAttribute("value"));
      } else if (childName.equals("short_html")) {
        mainItem.setShortHtml(child.getAttribute("value"));
      } else if (childName.equals("link")) {
        mainItem.setLink(child.getAttribute("value"));
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
          Element submenuChild = (Element)submenu.item(j);
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
            submenuItem.setGraphicRollover(submenuChild.getAttribute("rollover"));
      	  }
        }
        submenuTable.add(submenuItem);
      }
    }

    return mainItem;
  }

}

