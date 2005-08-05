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
package org.aspcfs.utils.web;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * HtmlDialog.java creates a pop up dialog box and can be either extended or
 * used as is for a dialog with the user, specifically it is best used for a
 * dialog which conveys certain dependencies or relationships. <p>
 * <p/>
 * Usage :<p>
 * <p/>
 * DependencyList thisList = thisTask.processDependencies(db);
 * htmlDialog.addMessage(thisList.getHtmlString()); <br>
 * if (htmlDialog.getRelationships().size() == 0) {
 * htmlDialog.setTitle("Confirm"); htmlDialog.setDeleteUrl("MyTasks.do?command=Delete&id=)
 * }<br>
 * else { <br>
 * htmlDialog.setTitle("Confirm");<br>
 * htmlDialog.setHeader("Are you sure you want to delete this item:");<br>
 * htmlDialog.addButton("Delete All", "MyTasks.do?command=Delete&id=");<br>
 * } <p>
 *
 * @author akhi_m
 * @version $Id$
 * @created August 22, 2002
 */
public class HtmlDialog {

  //static variables
  final static int TOP = 1;
  final static int MIDDLE = 2;
  final static int BOTTOM = 3;

  //other
  private int height = 90;
  private int width = 218;
  private int synchFrameCounter = 3;
  private String text = "";
  private String title = "";
  private String header = "";
  private String deleteUrl = "";
  private LinkedHashMap links = new LinkedHashMap();
  private StringBuffer message = null;
  private LinkedHashMap buttons = new LinkedHashMap();
  boolean showAndConfirm = true;


  /**
   * Constructor for the HtmlDialog object
   */
  public HtmlDialog() {
  }


  /**
   * Sets the text attribute of the HtmlDialog object
   *
   * @param tmp The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   * Sets the buttons attribute of the HtmlDialog object
   *
   * @param tmp The new buttons value
   */
  public void setButtons(LinkedHashMap tmp) {
    this.buttons = tmp;
  }


  /**
   * Sets the showAndConfirm attribute of the HtmlDialog object
   *
   * @param tmp The new showAndConfirm value
   */
  public void setShowAndConfirm(boolean tmp) {
    this.showAndConfirm = tmp;
  }


  /**
   * Sets the header attribute of the HtmlDialog object
   *
   * @param header The new header value
   */
  public void setHeader(String header) {
    this.header = header;
  }


  /**
   * Sets the links attribute of the HtmlDialog object
   *
   * @param links The new links value
   */
  public void setLinks(LinkedHashMap links) {
    this.links = links;
  }


  /**
   * Sets the size attribute of the HtmlDialog object
   *
   * @param height The new size value
   * @param width  The new size value
   */
  public void setSize(int height, int width) {
    this.height = height;
    this.width = width;
  }


  /**
   * Sets the deleteUrl attribute of the HtmlDialog object
   *
   * @param deleteUrl The new deleteUrl value
   */
  public void setDeleteUrl(String deleteUrl) {
    this.deleteUrl = deleteUrl;
  }


  /**
   * Sets the title attribute of the HtmlDialog object
   *
   * @param title The new title value
   */
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   * Sets the message attribute of the HtmlDialog object
   *
   * @param message The new message value
   */
  public void setMessage(StringBuffer message) {
    this.message = message;
  }


  /**
   * Gets the message attribute of the HtmlDialog object
   *
   * @return The message value
   */
  public StringBuffer getMessage() {
    return message;
  }


  /**
   * Gets the title attribute of the HtmlDialog object
   *
   * @return The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Gets the deleteUrl attribute of the HtmlDialog object
   *
   * @return The deleteUrl value
   */
  public String getDeleteUrl() {
    return deleteUrl;
  }


  /**
   * Gets the height attribute of the HtmlDialog object
   *
   * @return The height value
   */
  public int getHeight() {
    return height;
  }


  /**
   * Gets the width attribute of the HtmlDialog object
   *
   * @return The width value
   */
  public int getWidth() {
    return width;
  }


  /**
   * Gets the links attribute of the HtmlDialog object
   *
   * @return The links value
   */
  public LinkedHashMap getLinks() {
    return links;
  }


  /**
   * Gets the header attribute of the HtmlDialog object
   *
   * @return The header value
   */
  public String getHeader() {
    return header;
  }


  /**
   * Gets the text attribute of the HtmlDialog object
   *
   * @return The text value
   */
  public String getText() {
    return text;
  }


  /**
   * Gets the buttons attribute of the HtmlDialog object
   *
   * @return The buttons value
   */
  public LinkedHashMap getButtons() {
    return buttons;
  }


  /**
   * Gets the showAndConfirm attribute of the HtmlDialog object
   *
   * @return The showAndConfirm value
   */
  public boolean getShowAndConfirm() {
    return showAndConfirm;
  }


  /**
   * Gets the synchFrameCounter attribute of the HtmlDialog object
   *
   * @return The synchFrameCounter value
   */
  public int getSynchFrameCounter() {
    return synchFrameCounter;
  }


  /**
   * Adds a feature to the Message attribute of the HtmlDialog object
   *
   * @param msg The feature to be added to the Message attribute
   */
  public void addMessage(String msg) {
    if (message == null) {
      message = new StringBuffer();
    }
    message.append(msg);
  }


  /**
   * Gets the buttonString attribute of the HtmlDialog object
   *
   * @return The buttonString value
   */
  public String getButtonString() {
    StringBuffer buttonString = new StringBuffer();
    try {
      Set s = buttons.keySet();
      Iterator i = s.iterator();
      buttonString.append("&nbsp;");
      while (i.hasNext()) {
        Object id = i.next();
        Object st = buttons.get(id);
        buttonString.append(
            "<input type=\"button\" name=\"" + id.toString() + "\" value=\"" + id.toString() + "\" onClick=\"" + st.toString() + "\" />");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return buttonString.toString();
  }


  /**
   * Gets the linkString attribute of the HtmlDialog object
   *
   * @return The linkString value
   */
  public String getLinkString() {
    Set s = links.keySet();
    Iterator i = s.iterator();
    StringBuffer linkString = new StringBuffer();
    linkString.append("<br>");
    while (i.hasNext()) {
      Object name = i.next();
      linkString.append("- ");
      linkString.append(name + " (" + links.get(name) + ")");
      linkString.append("<br />");
    }
    return linkString.toString();
  }


  /**
   * Adds a feature to the Button attribute of the HtmlDialog object
   *
   * @param displayName The feature to be added to the Button attribute
   * @param action      The feature to be added to the Button attribute
   */
  public void addButton(String displayName, String action) {
    buttons.put(displayName, action);
  }


  /**
   * Gets the frameHtml attribute of the HtmlDialog object
   *
   * @param frameId Description of the Parameter
   * @return The frameHtml value
   */
  public String getFrameHtml(int frameId) {
    StringBuffer htmlString = new StringBuffer();

    switch (frameId) {
      case HtmlDialog.TOP:
        htmlString.append("<strong>" + this.getHeader() + "</strong>");
        break;
      case HtmlDialog.MIDDLE:
        if (this.getMessage() != null) {
          htmlString.append(
              "<table align=\"center\" cellpadding=\"2\" cellspacing=\"0\" border=\"0\" width=\"94%\"");
          htmlString.append("<tr><td valign=\"center\">");
          htmlString.append(this.getMessage().toString());
          htmlString.append("</td></tr></table>");
        }
        break;
      case HtmlDialog.BOTTOM:
        htmlString.append("<center>" + this.getButtonString() + "</center>");
        break;
    }
    decrementSynchFrameCounter();
    return htmlString.toString();
  }


  /**
   * Description of the Method
   */
  private synchronized void decrementSynchFrameCounter() {
    --synchFrameCounter;
  }
}

