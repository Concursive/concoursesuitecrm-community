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
package org.aspcfs.taglib;

import com.darkhorseventures.database.ConnectionElement;

import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SubmenuItem;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.io.File;
import java.util.*;

/**
 * Generates a submenu from an XML config file.
 *
 * @author matt rajkowski
 * @version $Id: ContainerMenuHandler.java,v 1.6 2002/12/23 16:12:28
 *          mrajkowski Exp $
 * @created May 7, 2002
 */
public class ContainerMenuHandler extends TagSupport implements TryCatchFinally {
  public final static int LINKS = 0;
  public final static int TABS = 1;
  public final static int SIDE_TABS = 2;
  public final static int SUB_TABS = 3;

  private String object = null;
  private String name = null;
  private String selected = null;
  private HashMap params = null;
  private String appendToUrl = "";
  private int style = SIDE_TABS;
  private boolean hideContainer = false;
  private Object item = null;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    object = null;
    name = null;
    selected = null;
    params = null;
    appendToUrl = "";
    style = SIDE_TABS;
    hideContainer = false;
    item = null;
  }


  /**
   * Sets the object attribute of the ContainerMenuHandler object
   *
   * @param objectName The new object value
   */
  public void setObject(String objectName) {
    this.object = objectName;
  }


  /**
   * Sets the name attribute of the ContainerMenuHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the selected attribute of the ContainerMenuHandler object
   *
   * @param tmp The new selected value
   */
  public void setSelected(String tmp) {
    this.selected = tmp;
  }


  /**
   * Sets the item attribute of the ContainerMenuHandler object
   *
   * @param tmp The new item value
   */
  public void setItem(Object tmp) {
    this.item = tmp;
  }


  /**
   * Sets the param attribute of the ContainerMenuHandler object.<br>
   * If a variable is needed in the html link for the submenu, multiple
   * parameters can be specified using a param tag. Then when the submenu is
   * generated, all text matching the parameter tag is replaced. Ex. <p>
   * </p>
   * Link: Accounts.do?command=View&id=$id<br>
   * In the JSP, do something like:<br>
   * String param1 = "id=<%= Org.getId() %>";<br>
   * <dhv:container name="accounts" selected="details" param="<%= param1 %>" />
   *
   * @param tmp The new param value
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
   * Sets the appendToUrl attribute of the ContainerMenuHandler object
   *
   * @param tmp The new appendToUrl value
   */
  public void setAppendToUrl(String tmp) {
    this.appendToUrl = tmp;
  }


  /**
   * Sets the style attribute of the ContainerMenuHandler object
   *
   * @param tmp The new style value
   */
  public void setStyle(String tmp) {
    if ("sidetabs".equals(tmp.toLowerCase())) {
      style = SIDE_TABS;
    } else if ("tabs".equals(tmp.toLowerCase())) {
      style = TABS;
    } else {
      style = LINKS;
    }
  }


  /**
   * Sets the hideContainer attribute of the ContainerMenuHandler object
   *
   * @param hideContainer The new hideContainer value
   */
  public void setHideContainer(boolean hideContainer) {
    this.hideContainer = hideContainer;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public final int doStartTag() {
    try {
      // Determine the style for embedded containers
      if (style == SIDE_TABS) {
        String currentStyle = (String) pageContext.getRequest().getAttribute(
            "ContainerMenuHandlerStyle");
        if (currentStyle != null) {
          style = SUB_TABS;
        } else {
          pageContext.getRequest().setAttribute(
              "ContainerMenuHandlerStyle", "SIDETABS");
        }
        if (pageContext.getRequest().getParameter("container") != null) {
          hideContainer = "false".equals(
              pageContext.getRequest().getParameter("container"));
        }
      }
      // Load the container data from XML
      LinkedHashMap containerMenu = (LinkedHashMap) pageContext.getServletContext().getAttribute(
          "ContainerMenu");
      if (containerMenu == null || containerMenu.size() == 0) {
        synchronized (this) {
          containerMenu = (LinkedHashMap) pageContext.getServletContext().getAttribute(
              "ContainerMenu");
          if (containerMenu == null || containerMenu.size() == 0) {
            loadXML(pageContext.getServletContext());
          }
        }
      }
      containerMenu = (LinkedHashMap) pageContext.getServletContext().getAttribute(
          "ContainerMenu");
      HashMap properties = null;
      HashMap containerProperties = (HashMap) pageContext.getServletContext().getAttribute(
          "ContainerProperties");
      if (containerProperties.containsKey(this.name)) {
        properties = (HashMap) containerProperties.get(this.name);
      } else {
        properties = new HashMap();
      }
      String label = null;
      // Lookup in XML for label pattern
      if (object != null && properties.containsKey("label")) {
        label = (String) properties.get("label");
        if (System.getProperty("DEBUG") != null) {
          System.out.println("ContainerMenuHandler-> Label: " + label);
        }
      }
      if (label != null && label.indexOf("${") > -1) {
        Object thisObject = pageContext.getRequest().getAttribute(object);
        if (thisObject == null) {
          thisObject = item;
        }
        if (thisObject != null) {
          Template template = new Template(label);
          template.populateVariables(thisObject);
          label = template.getParsedText();
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "ContainerMenuHandler-> Template label: " + label);
          }
        }
      }
      if (style == SIDE_TABS) {
        this.pageContext.getOut().write(
            "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\"><tr><td width=\"100%\">\n" +
                "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" height=\"100%\">\n" +
                "  <tr>\n" +
                "    <td width=\"100%\">" +
                "      <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "        <tr>\n" +
                "          <td class=\"tabStart\">&nbsp;</td>\n" +
                "          <td class=\"tabSelected-l\"><img border=\"0\" src=\"images/blank.gif\" /></td>\n" +
                "          <td class=\"tabSelected\" nowrap>" +
                (properties.containsKey("icon") ? "<img src=\"" + (String) properties.get(
                    "icon") + "\" align=\"absMiddle\" border=\"0\" />&nbsp;" : "") +
                StringUtils.toHtml(label) +
                "</td>\n" +
                "          <td class=\"tabSelected-r\"><img border=\"0\" src=\"images/blank.gif\" /></td>\n" +
                "          <td width=\"100%\" class=\"tabSpace\" nowrap>&nbsp;</td>\n" +
                "        </tr>\n" +
                "      </table>" +
                "    </td>\n" +
                "    <td class=\"tabSpace2\">" +
                "      &nbsp;" +
                "    </td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td class=\"containerBackSide\" height=\"100%\">");
      } else if (style == SUB_TABS) {
        // Draw the label
        this.pageContext.getOut().write(
            "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "  <tr>\n" +
                "    <td nowrap class=\"containerSubtabLabel\">" +
                (properties.containsKey("icon") ? "<img src=\"" + (String) properties.get(
                    "icon") + "\" align=\"absMiddle\" border=\"0\" />&nbsp;" : "") +
                StringUtils.toHtml(label) + "</td>\n" +
                "    <td width=\"100%\" class=\"containerSubtabSpace\">&nbsp;</td>\n" +
                "  </tr>\n" +
                "</table>");
        // Draw any tabs
        UserBean thisUser = (UserBean) pageContext.getSession().getAttribute(
            "User");
        ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
            "ConnectionElement");
        SystemStatus systemStatus = null;
        if (ce != null) {
          systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
              "SystemStatus")).get(ce.getUrl());
        }
        if (containerMenu.containsKey(this.name)) {
          LinkedList submenuItems = (LinkedList) containerMenu.get(this.name);
          if (submenuItems.size() > 0) {
            if (!hideContainer) {
              this.pageContext.getOut().write(
                  "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"conSubs\">\n" +
                      "  <tr>\n");
              // Draw the menu tabs
              Iterator i = submenuItems.iterator();
              boolean isOfflineMode = Boolean.parseBoolean(ApplicationPrefs.getPref(pageContext.getServletContext(), "OFFLINE_MODE"));
              while (i.hasNext()) {
                SubmenuItem thisItem = (SubmenuItem) i.next();
                if (thisItem.getPermission() == null ||
                    (thisItem.getPermission() != null && thisItem.getPermission().equals(
                        "")) ||
                    (thisUser != null && systemStatus != null &&
                    systemStatus.hasPermission(
                    thisUser.getUserId(), thisItem.getPermission() + (isOfflineMode ? "-offline" : "")))) {
                  Template linkText = new Template(thisItem.getLink());
                  linkText.setParseElements(params);
                  if (thisItem.getName().equals(selected)) {
                    // Selected tab
                    this.pageContext.getOut().write(
                        "<td class=\"conSubOn\" nowrap>");
                    this.pageContext.getOut().write(
                        "<a href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                    this.pageContext.getOut().write(getDisplayLabel(thisItem, systemStatus));
                    this.pageContext.getOut().write("</a>");
                    this.pageContext.getOut().write("</td>");
                  } else {
                    // Unselected Tabs
                    this.pageContext.getOut().write(
                        "<td class=\"conSubOff\" nowrap>");
                    this.pageContext.getOut().write(
                        "<a href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                    this.pageContext.getOut().write(getDisplayLabel(thisItem, systemStatus));
                    this.pageContext.getOut().write("</a>");
                    this.pageContext.getOut().write("</td>");
                  }
                }
              }
              this.pageContext.getOut().write("  </tr>\n");
              this.pageContext.getOut().write("  <tr>\n");

              // Draw the focus line
              i = submenuItems.iterator();
              while (i.hasNext()) {
                SubmenuItem thisItem = (SubmenuItem) i.next();
                if (thisItem.getPermission() == null ||
                    (thisItem.getPermission() != null && thisItem.getPermission().equals(
                        "")) ||
                    (thisUser != null && systemStatus != null &&
                    systemStatus.hasPermission(
                    thisUser.getUserId(), thisItem.getPermission() + (isOfflineMode ? "-offline" : "")))) {
                  Template linkText = new Template(thisItem.getLink());
                  linkText.setParseElements(params);
                  if (thisItem.getName().equals(selected)) {
                    // Selected tab
                    this.pageContext.getOut().write(
                        "<td class=\"conSubLine\" nowrap>");
                    this.pageContext.getOut().write(
                        "<img src=\"images/blank.gif\" border=\"0\" />");
                    this.pageContext.getOut().write("</td>");
                  } else {
                    // Unselected Tabs
                    this.pageContext.getOut().write("<td nowrap>");
                    this.pageContext.getOut().write(
                        "<img src=\"images/blank.gif\" border=\"0\" />");
                    this.pageContext.getOut().write("</td>");
                  }
                }
              }
              this.pageContext.getOut().write("  </tr>\n");
              this.pageContext.getOut().write("</table>\n");
            }
          }
        }
      } else if (style == TABS) {
        // Draw header for tabs
        this.pageContext.getOut().write("<div class=\"tabs\" id=\"toptabs\">");
        this.pageContext.getOut().write(
            "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">");
        this.pageContext.getOut().write("<tr>");
        // Draw any tabs
        UserBean thisUser = (UserBean) pageContext.getSession().getAttribute(
            "User");
        ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
            "ConnectionElement");
        SystemStatus systemStatus = null;
        if (ce != null) {
          systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
              "SystemStatus")).get(ce.getUrl());
        }
        if (containerMenu.containsKey(this.name)) {
          LinkedList submenuItems = (LinkedList) containerMenu.get(this.name);
          // Draw the menu tabs
          Iterator i = submenuItems.iterator();
          boolean isOfflineMode = Boolean.parseBoolean(ApplicationPrefs.getPref(pageContext.getServletContext(), "OFFLINE_MODE"));
          while (i.hasNext()) {
            SubmenuItem thisItem = (SubmenuItem) i.next();
            if (thisItem.getPermission() == null ||
                (thisItem.getPermission() != null && thisItem.getPermission().equals(
                    "")) ||
                (thisUser != null && systemStatus != null &&
                systemStatus.hasPermission(
                thisUser.getUserId(), thisItem.getPermission() + (isOfflineMode ? "-offline" : "")))) {
              Template linkText = new Template(thisItem.getLink());
              linkText.setParseElements(params);
              if (thisItem.getName().equals(selected)) {
                // Selected tab
                this.pageContext.getOut().write("<th nowrap>");
                this.pageContext.getOut().write(
                    "<a href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                this.pageContext.getOut().write(getDisplayLabel(thisItem, systemStatus));
                this.pageContext.getOut().write("</a>");
                this.pageContext.getOut().write("</th>");
              } else {
                // Unselected Tabs
                this.pageContext.getOut().write("<td nowrap>");
                this.pageContext.getOut().write(
                    "<a href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                this.pageContext.getOut().write(getDisplayLabel(thisItem, systemStatus));
                this.pageContext.getOut().write("</a>");
                this.pageContext.getOut().write("</td>");
              }
            }
          }
        }
        this.pageContext.getOut().write(
            "<td style=\"width:100%; background-image: none; background-color: transparent; border: 0px; border-bottom: 1px solid #666; cursor: default\">&nbsp;</td>");
        this.pageContext.getOut().write("</tr></table></div>");
        this.pageContext.getOut().write(
            "<table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n" +
                "  <tr>\n" +
                "    <td class=\"containerBack\" align=\"center\">");
      }
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ContainerMenuHandler-> Start tag exception: " + e.getMessage());
      }
    }
    return EVAL_BODY_INCLUDE;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public final int doAfterBody() {
    if (style == TABS) {
      return SKIP_BODY;
    }
    if (style == SIDE_TABS && hideContainer) {
      try {
        this.pageContext.getOut().write(
            "</td>\n" +
                "<td class=\"containerRight\" height=\"100%\"><img src=\"images/blank.gif\" border=\"0\" height=\"1\" />");
      } catch (Exception e) {

      }
      return SKIP_BODY;
    }
    try {
      LinkedHashMap containerMenu = (LinkedHashMap) pageContext.getServletContext().getAttribute(
          "ContainerMenu");
      UserBean thisUser = (UserBean) pageContext.getSession().getAttribute(
          "User");
      ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
          "ConnectionElement");
      SystemStatus systemStatus = null;
      if (ce != null) {
        systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
            "SystemStatus")).get(ce.getUrl());
      }
      if (containerMenu.containsKey(this.name)) {
        LinkedList submenuItems = (LinkedList) containerMenu.get(this.name);
        boolean itemOutput = false;
        if (style == SIDE_TABS) {
          this.pageContext.getOut().write(
              "</td>\n" +
                  "<td valign=\"top\" height=\"100%\">");
          this.pageContext.getOut().write(
              "<table height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
          this.pageContext.getOut().write(
              "<tr><td class=\"sidetabTop\" nowrap><img src=\"images/blank.gif\" border=\"0\" height=\"1\" /></td><td><img src=\"images/blank.gif\" border=\"0\" height=\"1\" /></td><td><img src=\"images/blank.gif\" border=\"0\" height=\"1\" /></td></tr>");
          this.pageContext.getOut().write(
              "<tr><td class=\"sidetab-left-sp\"><img src=\"images/blank.gif\" border=\"0\" height=\"1\" /></td>" +
                  "<td class=\"sidetab-midtop-sp\"><img src=\"images/blank.gif\" border=\"0\" height=\"1\" /></td>" +
                  "<td class=\"sidetab-right-sp\"><img src=\"images/blank.gif\" border=\"0\" height=\"1\" /></td></tr>");
        }
        Iterator i = submenuItems.iterator();
        boolean isOfflineMode = Boolean.parseBoolean(ApplicationPrefs.getPref(pageContext.getServletContext(), "OFFLINE_MODE"));
        while (i.hasNext()) {
          SubmenuItem thisItem = (SubmenuItem) i.next();
          if (thisItem.getPermission() == null ||
              (thisItem.getPermission() != null && thisItem.getPermission().equals(
                  "")) ||
              (thisUser != null && systemStatus != null &&
              systemStatus.hasPermission(
              thisUser.getUserId(), thisItem.getPermission() + (isOfflineMode ? "-offline" : "")))) {
            if (style == LINKS && itemOutput) {
              this.pageContext.getOut().write(" | ");
            }
            if (thisItem.getName().equals(selected)) {
              // Selected tab
              Template linkText = new Template(thisItem.getLink());
              linkText.setParseElements(params);
              switch (style) {
                case SIDE_TABS:
                  if (!hideContainer) {
                    this.pageContext.getOut().write(
                        "<tr><td class=\"sidetab-left-sel\">&nbsp;</td><td class=\"sidetab-mid-sel\">&nbsp;</td><td class=\"sidetab-right-sel\">" +
                            "<a href=\"" + linkText.getParsedText() + appendToUrl + "\">" +
                            getDisplayLabel(thisItem, systemStatus) +
                            "</a>" +
                            "</td></tr>");
                  }
                  break;
                case LINKS:
                  this.pageContext.getOut().write(
                      "<a class=\"containerOn\" href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                  this.pageContext.getOut().write(getDisplayLabel(thisItem, systemStatus));
                  this.pageContext.getOut().write("</a>");
                  break;
                default:
                  break;
              }
            } else {
              // Unselected Tabs
              Template linkText = new Template(thisItem.getLink());
              linkText.setParseElements(params);
              switch (style) {
                case SIDE_TABS:
                  if (!hideContainer) {
                    this.pageContext.getOut().write(
                        "<tr><td class=\"sidetab-left\">&nbsp;</td>" +
                            "<td class=\"sidetab-mid\">&nbsp;</td>" +
                            "<td class=\"sidetab-right\">" +
                            "<a href=\"" + linkText.getParsedText() + appendToUrl + "\">" +
                            getDisplayLabel(thisItem, systemStatus) +
                            "</a>" +
                            "</td></tr>");
                  }
                  break;
                case LINKS:
                  this.pageContext.getOut().write(
                      "<a class=\"containerOff\" href=\"" + linkText.getParsedText() + appendToUrl + "\">");
                  this.pageContext.getOut().write(getDisplayLabel(thisItem, systemStatus));
                  this.pageContext.getOut().write("</a>");
                  break;
                default:
                  break;
              }
            }
            if (style == SIDE_TABS) {
              this.pageContext.getOut().write(
                  "<tr><td class=\"sidetab-left-sp\"><img src=\"images/blank.gif\" border=\"0\" height=\"1\"/></td>" +
                      "<td class=\"sidetab-mid-sp\"><img src=\"images/blank.gif\" border=\"0\" height=\"1\"/></td>" +
                      "<td class=\"sidetab-right-sp\"><img src=\"images/blank.gif\" border=\"0\" height=\"1\"/></td></tr>");
            }
            itemOutput = true;
          }
        }
        if (style == SIDE_TABS) {
          this.pageContext.getOut().write(
              "<tr><td class=\"sidetabBottom\" height=\"100%\" nowrap>&nbsp;</td><td class=\"sidetabBottom-mid\">&nbsp;</td><td><span height=\"100%\">&nbsp;</span></td></tr>");
          this.pageContext.getOut().write("</table>");
          this.pageContext.getOut().write("</td></tr></table>");
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Returned Value
   */
  public int doEndTag() {
    try {
      if (style == SIDE_TABS) {
        this.pageContext.getOut().write(
            "</td>\n" +
                "</tr>\n" +
                "</table>");
      } else if (style == TABS) {
        this.pageContext.getOut().write(
            "    </td>\n" +
                "  </tr>\n" +
                "</table>");
      }
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ContainerMenuHandler-> End tag exception: " + e.getMessage());
      }
    }
    return EVAL_PAGE;
  }


  /**
   * Reads the submenu XML config file specified in web.xml.
   *
   * @param servletContext
   */
  private void loadXML(ServletContext servletContext) {
    LinkedHashMap menu = new LinkedHashMap();
    HashMap properties = new HashMap();
    try {
      XMLUtils xml = new XMLUtils(
          new File(
              pageContext.getServletContext().getRealPath(
                  "/WEB-INF/" + (String) pageContext.getServletContext().getAttribute(
                      "ContainerMenuConfig"))));
      LinkedList containerList = new LinkedList();
      XMLUtils.getAllChildren(
          xml.getDocumentElement(), "container", containerList);
      Iterator list = containerList.iterator();
      while (list.hasNext()) {
        Element container = (Element) list.next();
        if (System.getProperty("DEBUG") != null) {
          System.out.println(
              "ContainerMenuHandler-> Container Added: " + container.getAttribute(
                  "name"));
        }
        LinkedList menuItems = this.buildMenu(container);
        menu.put(container.getAttribute("name"), menuItems);
        HashMap propertyItems = this.buildProperties(container);
        properties.put(container.getAttribute("name"), propertyItems);
      }

    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    servletContext.setAttribute("ContainerMenu", menu);
    servletContext.setAttribute("ContainerProperties", properties);
  }


  /**
   * Parses the XML submenu element.
   *
   * @param container Description of Parameter
   * @return Description of the Returned Value
   */
  private LinkedList buildMenu(Element container) {
    LinkedList menuItems = new LinkedList();
    LinkedList menuList = new LinkedList();
    XMLUtils.getAllChildren(container, "submenu", menuList);

    ConnectionElement ce = (ConnectionElement) pageContext.getSession().getAttribute(
        "ConnectionElement");
    SystemStatus systemStatus = null;
    if (ce != null) {
      systemStatus = (SystemStatus) ((Hashtable) pageContext.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl());
    }

    Iterator list = menuList.iterator();
    while (list.hasNext()) {
      Element submenu = (Element) list.next();
      SubmenuItem thisSubmenu = new SubmenuItem();
      thisSubmenu.setName(submenu.getAttribute("name"));

      //check if custom value is defined in preferences
      String containerName = container.getAttribute("name");
      String label =  containerName + "." + submenu.getAttribute("name") + ".long_html";

      thisSubmenu.setLabel(label);
      String labelValue = systemStatus.getContainerMenuProperty("system.container.menu.label", label);
      thisSubmenu.setLongHtml(
          !"".equals(StringUtils.toString(labelValue)) ? labelValue : (XMLUtils.getFirstChild(
              submenu, "long_html").getAttribute("value")));
      //thisSubmenu.setShortHtml();
      //thisSubmenu.setAlternateName();
      thisSubmenu.setLink(
          XMLUtils.getFirstChild(submenu, "link").getAttribute("value"));
      //thisSubmenu.setHtmlClass();
      thisSubmenu.setPermission(
          XMLUtils.getFirstChild(submenu, "permission").getAttribute("value"));
      //thisSubmenu.setIsActive(true);
      menuItems.add(thisSubmenu);
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ContainerMenuHandler-> Submenu Added: " + thisSubmenu.getLongHtml());
      }
    }
    return menuItems;
  }


  /**
   * Description of the Method
   *
   * @param container Description of the Parameter
   * @return Description of the Return Value
   */
  private HashMap buildProperties(Element container) {
    HashMap propertyList = new HashMap();
    Element properties = XMLUtils.getFirstElement(container, "properties");
    if (properties != null) {
      String icon = XMLUtils.getNodeText(
          XMLUtils.getFirstElement(properties, "icon"));
      if (icon != null) {
        propertyList.put("icon", icon);
      }
      String label = XMLUtils.getNodeText(
          XMLUtils.getFirstElement(properties, "label"));
      if (label != null) {
        propertyList.put("label", label);
      }
    }
    return propertyList;
  }
	
	private String getDisplayLabel(SubmenuItem thisItem, SystemStatus systemStatus) {
			String label = thisItem.getLabel();
			String labelValue = systemStatus.getContainerMenuProperty("system.container.menu.label", label);
			if ("".equals(StringUtils.toString(labelValue))){
				labelValue = thisItem.getLongHtml();
			}
			
			return labelValue;
	}
}
