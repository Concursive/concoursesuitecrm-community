//Copyright 2002 Dark Horse Ventures
package org.aspcfs.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.StringUtils;
import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.controller.SubmenuItem;
import org.aspcfs.controller.SystemStatus;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 *  Generates a submenu from an XML config file.
 *
 *@author     matt rajkowski
 *@created    May 7, 2002
 *@version    $Id: ContainerMenuHandler.java,v 1.6 2002/12/23 16:12:28
 *      mrajkowski Exp $
 */
public class ContainerMenuHandler extends TagSupport {
  public final static int LINKS = 0;
  public final static int TABS = 1;

  private String name = null;
  private String selected = null;
  private HashMap params = null;
  private String appendToUrl = "";
  private int style = LINKS;


  /**
   *  Sets the name attribute of the ContainerMenuHandler object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the selected attribute of the ContainerMenuHandler object
   *
   *@param  tmp  The new selected value
   */
  public void setSelected(String tmp) {
    this.selected = tmp;
  }


  /**
   *  Sets the param attribute of the ContainerMenuHandler object.<br>
   *  If a variable is needed in the html link for the submenu, multiple
   *  parameters can be specified using a param tag. Then when the submenu is
   *  generated, all text matching the parameter tag is replaced. Ex. <p>
   *
   *  Link: Accounts.do?command=View&id=$id<br>
   *  In the JSP, do something like:<br>
   *  String param1 = "id=<%= Org.getId() %>";<br>
   *  <dhv:container name="accounts" selected="details" param="<%= param1 %>" />
   *
   *@param  tmp  The new param value
   */
  public void setParam(String tmp) {
    params = new HashMap();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while (tokens.hasMoreTokens()) {
      String pair = tokens.nextToken();
      String param = pair.substring(0, pair.indexOf("="));
      String value = pair.substring(pair.indexOf("=") + 1);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ContainerMenuHandler: Param-> " + param);
        System.out.println("ContainerMenuHandler: Value-> " + value);
      }
      params.put("${" + param + "}", value);
    }
  }


  /**
   *  Sets the appendToUrl attribute of the ContainerMenuHandler object
   *
   *@param  tmp  The new appendToUrl value
   */
  public void setAppendToUrl(String tmp) {
    this.appendToUrl = tmp;
  }


  /**
   *  Sets the style attribute of the ContainerMenuHandler object
   *
   *@param  tmp  The new style value
   */
  public void setStyle(String tmp) {
    if ("tabs".equals(tmp.toLowerCase())) {
      style = TABS;
    } else {
      style = LINKS;
    }
  }


  /**
   *  This will generate a submenu. Each submenu item will be added if it
   *  doesn't require a permission, or if the user has the required permission.
   *
   *@return                   Description of the Returned Value
   *@exception  JspException  Description of Exception
   */
  public final int doStartTag() throws JspException {
    try {
      LinkedHashMap containerMenu = (LinkedHashMap) pageContext.getServletContext().getAttribute("ContainerMenu");
      if (containerMenu == null) {
        synchronized (this) {
          containerMenu = (LinkedHashMap) pageContext.getServletContext().getAttribute("ContainerMenu");
          if (containerMenu == null) {
            containerMenu = loadXML();
            pageContext.getServletContext().setAttribute("ContainerMenu", containerMenu);
          }
        }
      }
      UserBean thisUser = (UserBean) pageContext.getSession().getAttribute("User");
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
      SystemStatus systemStatus = null;
      if (ce != null) {
        systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
      }
      if (containerMenu.containsKey(this.name)) {
        LinkedList submenuItems = (LinkedList) containerMenu.get(this.name);
        Iterator i = submenuItems.iterator();
        boolean itemOutput = false;
        if (style == TABS) {
          this.pageContext.getOut().write("<div class=\"tabs\" id=\"toptabs\">");
          this.pageContext.getOut().write("<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr>");
        }
        while (i.hasNext()) {
          SubmenuItem thisItem = (SubmenuItem) i.next();
          if (thisItem.getPermission() == null ||
              (thisItem.getPermission() != null && thisItem.getPermission().equals("")) ||
              (thisUser != null && systemStatus != null &&
              systemStatus.hasPermission(thisUser.getUserId(), thisItem.getPermission()))) {
            if (style != TABS && itemOutput) {
              this.pageContext.getOut().write(" | ");
            }
            if (thisItem.getName().equals(selected)) {
              Template linkText = new Template(thisItem.getLink());
              linkText.setParseElements(params);
              if (style == TABS) {
                this.pageContext.getOut().write("<th nowrap>");
                this.pageContext.getOut().write("<a href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                this.pageContext.getOut().write(thisItem.getLongHtml());
                this.pageContext.getOut().write("</a>");
                this.pageContext.getOut().write("</th>");
              } else {
                this.pageContext.getOut().write("<a class=\"containerOn\" href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                this.pageContext.getOut().write(thisItem.getLongHtml());
                this.pageContext.getOut().write("</a>");
              }
            } else {
              Template linkText = new Template(thisItem.getLink());
              linkText.setParseElements(params);
              if (style == TABS) {
                this.pageContext.getOut().write("<td nowrap>");
                this.pageContext.getOut().write("<a href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                this.pageContext.getOut().write(thisItem.getLongHtml());
                this.pageContext.getOut().write("</a>");
                this.pageContext.getOut().write("</td>");
              } else {
                this.pageContext.getOut().write("<a class=\"containerOff\" href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                this.pageContext.getOut().write(thisItem.getLongHtml());
                this.pageContext.getOut().write("</a>");
              }
            }
            itemOutput = true;
          }
        }
        if (style == TABS) {
          this.pageContext.getOut().write("<td style=\"width:100%; background-image: none; background-color: transparent; border: 0px; border-bottom: 1px solid #666; cursor: default\">&nbsp;</td>");
          this.pageContext.getOut().write("</tr></table></div>");
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }


  /**
   *  Reads the submenu XML config file specified in web.xml.
   *
   *@return    Description of the Returned Value
   */
  private LinkedHashMap loadXML() {
    LinkedHashMap menu = new LinkedHashMap();
    try {
      XMLUtils xml = new XMLUtils(
          new File(pageContext.getServletContext().getRealPath(
          "/WEB-INF/" + (String) pageContext.getServletContext().getAttribute("ContainerMenuConfig"))));
      LinkedList containerList = new LinkedList();
      xml.getAllChildren(xml.getDocumentElement(), "container", containerList);
      Iterator list = containerList.iterator();
      while (list.hasNext()) {
        Element container = (Element) list.next();
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ContainerMenuHandler-> Container Added: " + container.getAttribute("name"));
        }
        LinkedList menuItems = this.buildMenu(container);
        menu.put(container.getAttribute("name"), menuItems);
      }

    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return menu;
  }


  /**
   *  Parses the XML submenu element.
   *
   *@param  container  Description of Parameter
   *@return            Description of the Returned Value
   */
  private LinkedList buildMenu(Element container) {
    LinkedList menuItems = new LinkedList();
    LinkedList menuList = new LinkedList();
    XMLUtils.getAllChildren(container, "submenu", menuList);

    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute("ConnectionElement");
    SystemStatus systemStatus = null;
    if (ce != null) {
      systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute("SystemStatus")).get(ce.getUrl());
    }

    Iterator list = menuList.iterator();
    while (list.hasNext()) {
      Element submenu = (Element) list.next();
      SubmenuItem thisSubmenu = new SubmenuItem();
      thisSubmenu.setName(submenu.getAttribute("name"));

      //check if custom value is defined in preferences
      String containerName = container.getAttribute("name");
      String labelValue = systemStatus.getValue("system.container.menu.label", containerName + "." + submenu.getAttribute("name") + ".long_html");

      thisSubmenu.setLongHtml(!"".equals(StringUtils.toString(labelValue)) ? labelValue : (XMLUtils.getFirstChild(submenu, "long_html").getAttribute("value")));
      //thisSubmenu.setShortHtml();
      //thisSubmenu.setAlternateName();
      thisSubmenu.setLink(XMLUtils.getFirstChild(submenu, "link").getAttribute("value"));
      //thisSubmenu.setHtmlClass();
      thisSubmenu.setPermission(XMLUtils.getFirstChild(submenu, "permission").getAttribute("value"));
      //thisSubmenu.setIsActive(true);
      menuItems.add(thisSubmenu);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ContainerMenuHandler-> Submenu Added: " + thisSubmenu.getLongHtml());
      }
    }
    return menuItems;
  }
}

