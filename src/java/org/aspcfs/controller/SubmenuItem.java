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
package org.aspcfs.controller;

import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.utils.StringUtils;

/**
 * Description of the Class
 *
 * @author mrajkowski
 * @version $Id$
 * @created August 8, 2001
 */
public class SubmenuItem {

  String name = "";
  String longHtml = "";
  String shortHtml = "";
  String alternateName = "";
  String link = "";
  String htmlClass = "submenuItemUnselected";
  String permission = "";
	String label = "";

  String graphicWidth = "";
  String graphicHeight = "";
  String graphicOn = "";
  String graphicOff = "";
  String graphicRollover = "";

  boolean isActive = false;
  
  private RoleList roleList = null;
  private boolean isDashboard = false;
  private boolean isCustomTab = false;


  /**
   * Constructor for the SubmenuItem object
   *
   * @since 1.0
   */
  public SubmenuItem() {
  }


  /**
   * Constructor for the SubmenuItem object, copies an existing submenu item
   *
   * @param otherItem Description of Parameter
   * @since 1.2
   */
  public SubmenuItem(SubmenuItem otherItem) {
    this.setName(new String(otherItem.getName()));
    this.setLongHtml(new String(otherItem.getLongHtml()));
    this.setShortHtml(new String(otherItem.getShortHtml()));
    this.setAlternateName(new String(otherItem.getAlternateName()));
    this.setLink(new String(otherItem.getLink()));
    this.setHtmlClass(new String(otherItem.getHtmlClass()));
    this.setPermission(new String(otherItem.getPermission()));

    this.setGraphicOn(new String(otherItem.getGraphicOn()));
    this.setGraphicOff(new String(otherItem.getGraphicOff()));
    this.setGraphicRollover(new String(otherItem.getGraphicRollover()));
    this.setGraphicWidth(new String(otherItem.getGraphicRollover()));
    this.setGraphicHeight(new String(otherItem.getGraphicRollover()));
  }


  /**
   * Gets the isActive attribute of the SubmenuItem object
   *
   * @return The isActive value
   */
  public boolean getIsActive() {
    return isActive;
  }


  /**
   * Sets the isActive attribute of the SubmenuItem object
   *
   * @param isActive The new isActive value
   */
  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }


  /**
   * Sets the Name attribute of the SubmenuItem object
   *
   * @param tmp The new Name value
   * @since 1.0
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Gets the graphicWidth attribute of the SubmenuItem object
   *
   * @return The graphicWidth value
   */
  public String getGraphicWidth() {
    return graphicWidth;
  }


  /**
   * Gets the graphicHeight attribute of the SubmenuItem object
   *
   * @return The graphicHeight value
   */
  public String getGraphicHeight() {
    return graphicHeight;
  }


  /**
   * Gets the graphicOn attribute of the SubmenuItem object
   *
   * @return The graphicOn value
   */
  public String getGraphicOn() {
    return graphicOn;
  }


  /**
   * Gets the graphicOff attribute of the SubmenuItem object
   *
   * @return The graphicOff value
   */
  public String getGraphicOff() {
    return graphicOff;
  }


  /**
   * Gets the graphicRollover attribute of the SubmenuItem object
   *
   * @return The graphicRollover value
   */
  public String getGraphicRollover() {
    return graphicRollover;
  }


  /**
   * Sets the graphicWidth attribute of the SubmenuItem object
   *
   * @param tmp The new graphicWidth value
   */
  public void setGraphicWidth(String tmp) {
    this.graphicWidth = tmp;
  }


  /**
   * Sets the graphicHeight attribute of the SubmenuItem object
   *
   * @param tmp The new graphicHeight value
   */
  public void setGraphicHeight(String tmp) {
    this.graphicHeight = tmp;
  }


  /**
   * Sets the graphicOn attribute of the SubmenuItem object
   *
   * @param tmp The new graphicOn value
   */
  public void setGraphicOn(String tmp) {
    this.graphicOn = tmp;
  }


  /**
   * Sets the graphicOff attribute of the SubmenuItem object
   *
   * @param tmp The new graphicOff value
   */
  public void setGraphicOff(String tmp) {
    this.graphicOff = tmp;
  }


  /**
   * Sets the graphicRollover attribute of the SubmenuItem object
   *
   * @param tmp The new graphicRollover value
   */
  public void setGraphicRollover(String tmp) {
    this.graphicRollover = tmp;
  }


  /**
   * Sets the Html attribute of the SubmenuItem object
   *
   * @param tmp The new Html value
   * @since 1.2
   */
  public void setLongHtml(String tmp) {
    this.longHtml = tmp;
  }


  /**
   * Sets the ShortHtml attribute of the SubmenuItem object
   *
   * @param tmp The new ShortHtml value
   * @since 1.2
   */
  public void setShortHtml(String tmp) {
    this.shortHtml = tmp;
  }


  /**
   * Sets the AlternateName attribute of the SubmenuItem object
   *
   * @param tmp The new AlternateName value
   * @since 1.0
   */
  public void setAlternateName(String tmp) {
    this.alternateName = tmp;
  }


  /**
   * Sets the Link attribute of the SubmenuItem object
   *
   * @param tmp The new Link value
   * @since 1.0
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   * Sets the HtmlClass attribute of the SubmenuItem object
   *
   * @param tmp The new HtmlClass value
   * @since 1.0
   */
  public void setHtmlClass(String tmp) {
    this.htmlClass = tmp;
  }


  /**
   * Sets the Permission attribute of the SubmenuItem object
   *
   * @param tmp The new Permission value
   * @since 1.0
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }

public void setLabel(String tmp) { this.label = tmp; }

  /**
   * Gets the Html attribute of the SubmenuItem object
   *
   * @return The Html value
   * @since 1.0
   */
  public String getHtml() {
    if (link == null || link.equals("")) {
      return longHtml;
    } else {
      return ("<a " + (htmlClass.equals("") ? "" : "class='" + htmlClass + "' ") +
          "href='" + link + "'>" + longHtml + "</a>");
    }
  }


  /**
   * Gets the Html attribute of the SubmenuItem object
   *
   * @return The Html value
   * @since 1.2
   */
  public String getLongHtml() {
    return longHtml;
  }


  /**
   * Gets the ShortHtml attribute of the SubmenuItem object
   *
   * @return The ShortHtml value
   * @since 1.2
   */
  public String getShortHtml() {
    return shortHtml;
  }


  /**
   * Gets the AlternateHtml attribute of the SubmenuItem object
   *
   * @param systemStatus Description of the Parameter
   * @return The AlternateHtml value
   * @since 1.0
   */
  public String getAlternateHtml(SystemStatus systemStatus) {
    String value = systemStatus.getSubMenuProperty(shortHtml);
    if (value == null) {
      value = shortHtml;
    }
    if (link == null || link.equals("")) {
      return StringUtils.toHtml(value);
    } else {
      if (!value.startsWith("&nbsp;")) {
        return ("<a " + (htmlClass.equals("") ? "" : "class='" + htmlClass + "' ") +
            "href='" + link + "'>" + StringUtils.toHtml(value) + "</a>");
      } else {
        int count = 0;
        while (value.startsWith("&nbsp;")) {
          ++count;
          value = value.substring(6);
        }
        return (addSpace(count) + "<a " + (htmlClass.equals("") ? "" : "class='" + htmlClass + "' ") +
            "href='" + link + "'>" + StringUtils.toHtml(value) + "</a>");
      }
    }
  }


  /**
   * Adds a feature to the Space attribute of the SubmenuItem object
   *
   * @param count The feature to be added to the Space attribute
   * @return Description of the Return Value
   */
  private String addSpace(int count) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < count; i++) {
      sb.append("&nbsp;");
    }
    return sb.toString();
  }


  /**
   * Gets the imageHtml attribute of the SubmenuItem object
   *
   * @return The imageHtml value
   */
  public String getImageHtml() {
    if (link == null || link.equals("")) {
      return shortHtml;
    } else {
      return ("<a href='" + link + "'><img name='" + shortHtml + "' src='images/" + ((isActive) ? graphicOn : graphicOff) + "' border=0 onMouseOut=\"MM_swapImgRestore()\" onMouseOver=\"MM_swapImage('" + shortHtml + "','','images/" + graphicRollover + "',1)\"></a>");
    }
  }


  /**
   * Gets the Name attribute of the SubmenuItem object
   *
   * @return The Name value
   * @since 1.0
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the AlternateName attribute of the SubmenuItem object
   *
   * @return The AlternateName value
   * @since 1.0
   */
  public String getAlternateName() {
    return alternateName;
  }


  /**
   * Gets the Link attribute of the SubmenuItem object
   *
   * @return The Link value
   * @since 1.0
   */
  public String getLink() {
    return link;
  }


  /**
   * Gets the HtmlClass attribute of the SubmenuItem object
   *
   * @return The HtmlClass value
   * @since 1.0
   */
  public String getHtmlClass() {
    return htmlClass;
  }


  /**
   * Gets the Permission attribute of the SubmenuItem object
   *
   * @return The Permission value
   * @since 1.0
   */
  public String getPermission() {
    return permission;
  }


  /**
   * Description of the Method
   *
   * @param thisPermission Description of Parameter
   * @return Description of the Returned Value
   * @since 1.0
   */
  public boolean hasPermission(String thisPermission) {
    return (permission.equals(thisPermission));
  }
public String getLabel() { return label; }


	/**
	 * @return the roleList
	 */
	public RoleList getRoleList() {
		return roleList;
	}


	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(RoleList roleList) {
		this.roleList = roleList;
	}


	/**
	 * @return the isCustomTab
	 */
	public boolean isCustomTab() {
		return isCustomTab;
	}


	/**
	 * @param isCustomTab the isCustomTab to set
	 */
	public void setCustomTab(boolean isCustomTab) {
		this.isCustomTab = isCustomTab;
	}


	/**
	 * @return the isDashboard
	 */
	public boolean isDashboard() {
		return isDashboard;
	}


	/**
	 * @param isDashboard the isDashboard to set
	 */
	public void setDashboard(boolean isDashboard) {
		this.isDashboard = isDashboard;
	}

}

