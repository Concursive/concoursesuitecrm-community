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

import org.aspcfs.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a main menu item, along with any submenu items.
 *
 * @author mrajkowski
 * @version $Id$
 * @created August 21, 2001
 */
public class MainMenuItem {

  ArrayList actionNames = new ArrayList();
  String pageTitle = "";
  String permission = "";
  String longHtml = "";
  boolean longHtmlRollover = false;
  String shortHtml = "";
  boolean shortHtmlRollover = false;
  String link = "";
  String classNormal = null;
  String classSelected = null;
  String graphicWidth = "";
  String graphicHeight = "";
  String graphicOn = "";
  String graphicOff = "";
  String graphicRollover = "";
  ArrayList submenuItems = new ArrayList();


  /**
   * Constructor for the MainMenuItem object
   *
   * @since 1.1
   */
  public MainMenuItem() {
  }


  /**
   * Sets the PageTitle attribute of the MainMenuItem object
   *
   * @param tmp The new PageTitle value
   * @since 1.1
   */
  public void setPageTitle(String tmp) {
    this.pageTitle = tmp;
  }


  /**
   * Sets the ActionNames attribute of the MainMenuItem object
   *
   * @param tmp The new Name value
   * @since 1.1
   */
  public void setActionNames(ArrayList tmp) {
    this.actionNames = tmp;
  }


  /**
   * Sets the Permission attribute of the MainMenuItem object
   *
   * @param tmp The new Permission value
   * @since 1.1
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   * Sets the Html attribute of the MainMenuItem object
   *
   * @param tmp The new Html value
   * @since 1.1
   */
  public void setLongHtml(String tmp) {
    this.longHtml = tmp;
  }


  /**
   * Sets the longHtmlRollover attribute of the MainMenuItem object
   *
   * @param tmp The new longHtmlRollover value
   */
  public void setLongHtmlRollover(boolean tmp) {
    this.longHtmlRollover = tmp;
  }


  /**
   * Sets the longHtmlRollover attribute of the MainMenuItem object
   *
   * @param tmp The new longHtmlRollover value
   */
  public void setLongHtmlRollover(String tmp) {
    this.longHtmlRollover = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the ShortHtml attribute of the MainMenuItem object
   *
   * @param tmp The new ShortHtml value
   * @since 1.1
   */
  public void setShortHtml(String tmp) {
    this.shortHtml = tmp;
  }


  /**
   * Sets the shortHtmlRollover attribute of the MainMenuItem object
   *
   * @param tmp The new shortHtmlRollover value
   */
  public void setShortHtmlRollover(boolean tmp) {
    this.shortHtmlRollover = tmp;
  }


  /**
   * Sets the shortHtmlRollover attribute of the MainMenuItem object
   *
   * @param tmp The new shortHtmlRollover value
   */
  public void setShortHtmlRollover(String tmp) {
    this.shortHtmlRollover = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the Link attribute of the MainMenuItem object
   *
   * @param tmp The new Link value
   * @since 1.1
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   * Sets the classNormal attribute of the MainMenuItem object
   *
   * @param tmp The new classNormal value
   */
  public void setClassNormal(String tmp) {
    this.classNormal = tmp;
  }


  /**
   * Sets the classSelected attribute of the MainMenuItem object
   *
   * @param tmp The new classSelected value
   */
  public void setClassSelected(String tmp) {
    this.classSelected = tmp;
  }


  /**
   * Sets the GraphicWidth attribute of the MainMenuItem object
   *
   * @param tmp The new GraphicWidth value
   * @since 1.1
   */
  public void setGraphicWidth(String tmp) {
    this.graphicWidth = tmp;
  }


  /**
   * Sets the GraphicHeight attribute of the MainMenuItem object
   *
   * @param tmp The new GraphicHeight value
   * @since 1.1
   */
  public void setGraphicHeight(String tmp) {
    this.graphicHeight = tmp;
  }


  /**
   * Sets the GraphicOn attribute of the MainMenuItem object
   *
   * @param tmp The new GraphicOn value
   * @since 1.1
   */
  public void setGraphicOn(String tmp) {
    this.graphicOn = tmp;
  }


  /**
   * Sets the GraphicOff attribute of the MainMenuItem object
   *
   * @param tmp The new GraphicOff value
   * @since 1.1
   */
  public void setGraphicOff(String tmp) {
    this.graphicOff = tmp;
  }


  /**
   * Sets the GraphicRollover attribute of the MainMenuItem object
   *
   * @param tmp The new GraphicRollover value
   * @since 1.1
   */
  public void setGraphicRollover(String tmp) {
    this.graphicRollover = tmp;
  }


  /**
   * Sets the SubmenuItems attribute of the MainMenuItem object
   *
   * @param tmp The new SubmenuItems value
   * @since 1.1
   */
  public void setSubmenuItems(ArrayList tmp) {
    this.submenuItems = tmp;
  }


  /**
   * Gets the PageTitle attribute of the MainMenuItem object
   *
   * @return The PageTitle value
   * @since 1.1
   */
  public String getPageTitle() {
    return pageTitle;
  }


  /**
   * Gets the actionNames attribute of the MainMenuItem object
   *
   * @return The Names value
   * @since 1.2
   */
  public ArrayList getActionNames() {
    return actionNames;
  }


  /**
   * Gets the Permission attribute of the MainMenuItem object
   *
   * @return The Permission value
   * @since 1.1
   */
  public String getPermission() {
    return permission;
  }


  /**
   * Gets the Html attribute of the MainMenuItem object
   *
   * @return The Html value
   * @since 1.1
   */
  public String getLongHtml() {
    return longHtml;
  }


  /**
   * Gets the longHtmlRollover attribute of the MainMenuItem object
   *
   * @return The longHtmlRollover value
   */
  public boolean getLongHtmlRollover() {
    return longHtmlRollover;
  }


  /**
   * Gets the ShortHtml attribute of the MainMenuItem object
   *
   * @return The ShortHtml value
   * @since 1.1
   */
  public String getShortHtml() {
    return shortHtml;
  }


  /**
   * Gets the shortHtmlRollover attribute of the MainMenuItem object
   *
   * @return The shortHtmlRollover value
   */
  public boolean getShortHtmlRollover() {
    return shortHtmlRollover;
  }


  /**
   * Gets the Link attribute of the MainMenuItem object
   *
   * @return The Link value
   * @since 1.1
   */
  public String getLink() {
    return link;
  }


  /**
   * Gets the classNormal attribute of the MainMenuItem object
   *
   * @return The classNormal value
   */
  public String getClassNormal() {
    return classNormal;
  }


  /**
   * Gets the classSelected attribute of the MainMenuItem object
   *
   * @return The classSelected value
   */
  public String getClassSelected() {
    return classSelected;
  }


  /**
   * Gets the GraphicWidth attribute of the MainMenuItem object
   *
   * @return The GraphicWidth value
   * @since 1.1
   */
  public String getGraphicWidth() {
    return graphicWidth;
  }


  /**
   * Gets the GraphicHeight attribute of the MainMenuItem object
   *
   * @return The GraphicHeight value
   * @since 1.1
   */
  public String getGraphicHeight() {
    return graphicHeight;
  }


  /**
   * Gets the GraphicOn attribute of the MainMenuItem object
   *
   * @return The GraphicOn value
   * @since 1.1
   */
  public String getGraphicOn() {
    return graphicOn;
  }


  /**
   * Gets the GraphicOff attribute of the MainMenuItem object
   *
   * @return The GraphicOff value
   * @since 1.1
   */
  public String getGraphicOff() {
    return graphicOff;
  }


  /**
   * Gets the GraphicRollover attribute of the MainMenuItem object
   *
   * @return The GraphicRollover value
   * @since 1.1
   */
  public String getGraphicRollover() {
    return graphicRollover;
  }


  /**
   * Gets the SubmenuItems attribute of the MainMenuItem object
   *
   * @return The SubmenuItems value
   * @since 1.1
   */
  public ArrayList getSubmenuItems() {
    return submenuItems;
  }


  /**
   * Gets the SubmenuSize attribute of the MainMenuItem object
   *
   * @return The SubmenuSize value
   * @since 1.1
   */
  public int getSubmenuSize() {
    return submenuItems.size();
  }


  /**
   * Adds a feature to the Name attribute of the MainMenuItem object
   *
   * @param tmp The feature to be added to the Name attribute
   * @since 1.2
   */
  public void addActionName(String tmp) {
    this.actionNames.add(tmp);
  }


  /**
   * If this main menu item contains
   *
   * @param tmp Description of Parameter
   * @return Description of the Returned Value
   * @since 1.2
   */
  public boolean hasActionName(String tmp) {
    return actionNames.contains(tmp);
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasGraphicRollover() {
    return (graphicRollover != null && !graphicRollover.equals(""));
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String toString() {
    StringBuffer out = new StringBuffer();
    out.append("=============================\r\n");
    out.append("Page Title: " + pageTitle + "\r\n");
    out.append("Actions:");
    Iterator i = actionNames.iterator();
    while (i.hasNext()) {
      out.append(" " + (String) i.next());
    }
    out.append("\r\n");
    out.append("Submenu:");
    i = submenuItems.iterator();
    while (i.hasNext()) {
      out.append(" " + ((SubmenuItem) i.next()).getName());
    }
    out.append("\r\n");

    return out.toString();
  }

}

