package org.aspcfs.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.servlets.ControllerMainMenuHook;
import org.aspcfs.modules.beans.ModuleBean;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  When a template requiring navigation is requested, this class generates the
 *  HTML for the navigation. <p>
 *
 *  - 3 types of menus are generated: a tabbed version, a graphic version, and a
 *  text version. Each is placed in the request for later retrieval.<br>
 *  - The configuration is stored in XML
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


  /**
   *  Description of the Method
   */
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
    UserBean thisUser = (UserBean) request.getSession().getAttribute("User");
    if (thisUser == null) {
      return;
    }
    //Use the connection element and system status to access the cached permissions table
    ConnectionElement ce = (ConnectionElement) request.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = null;
    if (ce != null) {
      systemStatus = (SystemStatus) ((Hashtable) context.getAttribute("SystemStatus")).get(ce.getUrl());
    }

    ModuleBean thisModule = (ModuleBean) request.getAttribute("ModuleBean");
    if (thisModule == null) {
      thisModule = new ModuleBean();
      request.setAttribute("ModuleBean", thisModule);
    }

    //Build the menus
    int menuWidth = 0;
    StringBuffer menu = new StringBuffer();
    StringBuffer graphicMenu = new StringBuffer();
    StringBuffer smallMenu = new StringBuffer();
    ArrayList smallMenuList = new ArrayList();

    //Build the graphic menu and the module submenu
    Iterator menuItemsList = menuItems.iterator();
    while (menuItemsList.hasNext()) {
      MainMenuItem thisMenu = (MainMenuItem) menuItemsList.next();
      if ("".equals(thisMenu.getPermission()) || (systemStatus != null && systemStatus.hasPermission(thisUser.getUserId(), thisMenu.getPermission()))) {
        if ((thisModule.getMenuKey() != null && thisMenu.hasActionName(thisModule.getMenuKey())) ||
            (thisModule.getMenuKey() == null && thisMenu.hasActionName(actionPath))) {
          //The user is on this link/module
          thisModule.setName(thisMenu.getPageTitle());
          //Set the on state of the menu
          menu.append("<th nowrap onClick=\"javascript:window.location.href='" + thisMenu.getLink() + "'\">");
          menu.append(thisMenu.getShortHtml());
          menu.append("</th>");
          graphicMenu.append("<a href='" + thisMenu.getLink() + "'><img border='0' src='images/" + thisMenu.getGraphicOn() + "' width='" + thisMenu.getGraphicWidth() + "' height='" + thisMenu.getGraphicHeight() + "'></a>");
          smallMenuList.add("<a " + addSelectedClass(thisMenu) + "href='" + thisMenu.getLink() + "'>" + thisMenu.getLongHtml() + "</a>");
          //Build the submenu
          Iterator j = thisMenu.getSubmenuItems().iterator();
          while (j.hasNext()) {
            SubmenuItem thisItem = (SubmenuItem) j.next();
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
          menu.append("<td nowrap onClick=\"javascript:window.location.href='" + thisMenu.getLink() + "'\">");
          menu.append(thisMenu.getShortHtml());
          //menu.append("<a href='" + thisMenu.getLink() + "'>" + thisMenu.getShortHtml() + "</a>");
          menu.append("</td>");

          graphicMenu.append("<a href='" + thisMenu.getLink() + "'");
          if (thisMenu.hasRollover()) {
            graphicMenu.append(" onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('" + thisMenu.getShortHtml() + "','','images/" + thisMenu.getGraphicRollover() + "',1)\"");
          }
          graphicMenu.append(">");
          graphicMenu.append("<img ");
          graphicMenu.append("border='0' ");
          graphicMenu.append("src='images/" + thisMenu.getGraphicOff() + "' ");
          graphicMenu.append("width='" + thisMenu.getGraphicWidth() + "' ");
          graphicMenu.append("height='" + thisMenu.getGraphicHeight() + "' ");

          graphicMenu.append(">");
          graphicMenu.append("</a>");
          smallMenuList.add("<a " + addNormalClass(thisMenu) + "href='" + thisMenu.getLink() + "'>" + thisMenu.getLongHtml() + "</a>");
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
    String[] theMenus = new String[4];
    theMenus[0] = menu.toString() + "<td style=\"width:100%; border: 0px; background-image: none !important; background: #fff; border-bottom: 1px #000 solid; cursor: default\">&nbsp;</td>";
    theMenus[1] = "<td width=\"" + menuWidth + "\" nowrap>" + graphicMenu.toString() + "</td>";
    theMenus[2] = smallMenu.toString();
    theMenus[3] = menu.toString();
    request.setAttribute("MainMenu", theMenus[0]);
    request.setAttribute("MainMenuGraphic", theMenus[1]);
    request.setAttribute("MainMenuWidth", String.valueOf(menuWidth));
    request.setAttribute("MainMenuSmall", theMenus[2]);
    request.setAttribute("MainMenuTableCells", theMenus[3]);
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
   *  Once the in-memory document has split out the module items using
   *  parseAllMenus(), each menu is processed for items and submenu items.
   *
   *@param  e  Description of Parameter
   *@return    Description of the Returned Value
   *@since     1.17
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
      } else if (childName.equals("short_html")) {
        mainItem.setShortHtml(child.getAttribute("value"));
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
            submenuItem.setGraphicRollover(submenuChild.getAttribute("rollover"));
          }
        }
        submenuTable.add(submenuItem);
      }
    }

    return mainItem;
  }


  /**
   *  Adds a feature to the NormalClass attribute of the MainMenuHook class
   *
   *@param  thisItem  The feature to be added to the NormalClass attribute
   *@return           Description of the Return Value
   */
  private static String addNormalClass(MainMenuItem thisItem) {
    if (thisItem.getClassNormal() != null && !"".equals(thisItem.getClassNormal())) {
      return "class=\"" + thisItem.getClassNormal() + "\" ";
    }
    return "";
  }


  /**
   *  Adds a feature to the SelectedClass attribute of the MainMenuHook class
   *
   *@param  thisItem  The feature to be added to the SelectedClass attribute
   *@return           Description of the Return Value
   */
  private static String addSelectedClass(MainMenuItem thisItem) {
    if (thisItem.getClassSelected() != null && !"".equals(thisItem.getClassSelected())) {
      return "class=\"" + thisItem.getClassSelected() + "\" ";
    }
    return "";
  }
}

