package com.darkhorseventures.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import com.darkhorseventures.cfsbase.UserBean;
import com.darkhorseventures.utils.XMLUtils;
import com.darkhorseventures.utils.Template;
import com.darkhorseventures.controller.SubmenuItem;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ContainerMenuHandler extends TagSupport {
  private String name = null;
  private String selected = null;
  private HashMap params = new HashMap();

  public void setName(String tmp) { this.name = tmp; }
  public void setSelected(String tmp) { this.selected = tmp; }
  public void setParam(String tmp) {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ContainerMenuHandler: Input-> " + tmp);
    }
    StringTokenizer st = new StringTokenizer(tmp, "=");
    if (st.hasMoreTokens()) {
      String param = "$" + st.nextToken();
      if (st.hasMoreTokens()) {
        String value = st.nextToken();
        params.put(param, value);
      }
    }
  }

  public final int doStartTag() throws JspException {
    try {
      LinkedHashMap containerMenu = (LinkedHashMap)pageContext.getServletContext().getAttribute("ContainerMenu");
      if (containerMenu == null) {
        synchronized (this) {
          containerMenu = (LinkedHashMap)pageContext.getServletContext().getAttribute("ContainerMenu");
          if (containerMenu == null) {
            containerMenu = loadXML();
            pageContext.getServletContext().setAttribute("ContainerMenu", containerMenu);
          }
        }
      }
      UserBean thisUser = (UserBean)pageContext.getSession().getAttribute("User");
      if (containerMenu.containsKey(this.name)) {
        LinkedList submenuItems = (LinkedList)containerMenu.get(this.name);
        Iterator i = submenuItems.iterator();
        boolean itemOutput = false;
        while (i.hasNext()) {
          SubmenuItem thisItem = (SubmenuItem)i.next();
          if (thisItem.getPermission() == null ||
             (thisItem.getPermission() != null && thisItem.getPermission().equals("")) ||
             (thisUser != null && thisUser.hasPermission(thisItem.getPermission()))) {
            if (itemOutput) {
              this.pageContext.getOut().write(" | ");
            }
            if (thisItem.getName().equals(selected)) {
              this.pageContext.getOut().write(thisItem.getLongHtml());
            } else {
              Template linkText = new Template(thisItem.getLink());
              linkText.setParseElements(params);
              this.pageContext.getOut().write("<a href=\"" + linkText.getParsedText() + "\">");
              this.pageContext.getOut().write(thisItem.getLongHtml());
              this.pageContext.getOut().write("</a>");
            }
            itemOutput = true;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }
  
  public int doEndTag() {	
    return EVAL_PAGE;	
  }
  
  private LinkedHashMap loadXML() {
    LinkedHashMap menu = new LinkedHashMap();
    try {
      XMLUtils xml = new XMLUtils(
        new File(pageContext.getServletContext().getRealPath(
          "/WEB-INF/" + (String)pageContext.getServletContext().getAttribute("ContainerMenuConfig"))));
      LinkedList containerList = new LinkedList();
      xml.getAllChildren(xml.getDocumentElement(), "container", containerList);
      Iterator list = containerList.iterator();
      while (list.hasNext()) {
        Element container = (Element)list.next();
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
  
  private LinkedList buildMenu(Element container) {
    LinkedList menuItems = new LinkedList();
    LinkedList menuList = new LinkedList();
    XMLUtils.getAllChildren(container, "submenu", menuList);
    Iterator list = menuList.iterator();
    while (list.hasNext()) {
      Element submenu = (Element)list.next();
      SubmenuItem thisSubmenu = new SubmenuItem();
      thisSubmenu.setName(submenu.getAttribute("name"));
      thisSubmenu.setLongHtml(XMLUtils.getFirstChild(submenu, "long_html").getAttribute("value"));
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

