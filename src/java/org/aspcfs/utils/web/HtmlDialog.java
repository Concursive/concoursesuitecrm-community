package com.darkhorseventures.webutils;

import java.util.*;
import java.text.*;

/**
 *  Description of the Class
 *
 *@author     akhi_m
 *@created    August 22, 2002
 *@version    $Id$
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
  private HashMap links = new HashMap();
  private HashMap relationships = new HashMap();
  private HashMap buttons = new HashMap();
  boolean showAndConfirm = true;


  /**
   *  Constructor for the HtmlDialog object
   */
  public HtmlDialog() { }


  /**
   *  Sets the text attribute of the HtmlDialog object
   *
   *@param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }


  /**
   *  Sets the relationships attribute of the HtmlDialog object
   *
   *@param  tmp  The new relationships value
   */
  public void setRelationships(HashMap tmp) {
    this.relationships = tmp;
  }


  /**
   *  Sets the buttons attribute of the HtmlDialog object
   *
   *@param  tmp  The new buttons value
   */
  public void setButtons(HashMap tmp) {
    this.buttons = tmp;
  }


  /**
   *  Sets the showAndConfirm attribute of the HtmlDialog object
   *
   *@param  tmp  The new showAndConfirm value
   */
  public void setShowAndConfirm(boolean tmp) {
    this.showAndConfirm = tmp;
  }


  /**
   *  Sets the header attribute of the HtmlDialog object
   *
   *@param  header  The new header value
   */
  public void setHeader(String header) {
    this.header = header;
  }


  /**
   *  Sets the links attribute of the HtmlDialog object
   *
   *@param  links  The new links value
   */
  public void setLinks(HashMap links) {
    this.links = links;
  }


  /**
   *  Sets the size attribute of the HtmlDialog object
   *
   *@param  height  The new size value
   *@param  width   The new size value
   */
  public void setSize(int height, int width) {
    this.height = height;
    this.width = width;
  }


  /**
   *  Sets the deleteUrl attribute of the HtmlDialog object
   *
   *@param  deleteUrl  The new deleteUrl value
   */
  public void setDeleteUrl(String deleteUrl) {
    this.deleteUrl = deleteUrl;
  }


  /**
   *  Sets the title attribute of the HtmlDialog object
   *
   *@param  title  The new title value
   */
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   *  Gets the title attribute of the HtmlDialog object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Gets the deleteUrl attribute of the HtmlDialog object
   *
   *@return    The deleteUrl value
   */
  public String getDeleteUrl() {
    return deleteUrl;
  }


  /**
   *  Gets the height attribute of the HtmlDialog object
   *
   *@return    The height value
   */
  public int getHeight() {
    return height;
  }


  /**
   *  Gets the width attribute of the HtmlDialog object
   *
   *@return    The width value
   */
  public int getWidth() {
    return width;
  }



  /**
   *  Gets the links attribute of the HtmlDialog object
   *
   *@return    The links value
   */
  public HashMap getLinks() {
    return links;
  }


  /**
   *  Gets the header attribute of the HtmlDialog object
   *
   *@return    The header value
   */
  public String getHeader() {
    return header;
  }


  /**
   *  Gets the text attribute of the HtmlDialog object
   *
   *@return    The text value
   */
  public String getText() {
    return text;
  }


  /**
   *  Gets the relationships attribute of the HtmlDialog object
   *
   *@return    The relationships value
   */
  public HashMap getRelationships() {
    return relationships;
  }


  /**
   *  Gets the buttons attribute of the HtmlDialog object
   *
   *@return    The buttons value
   */
  public HashMap getButtons() {
    return buttons;
  }


  /**
   *  Gets the showAndConfirm attribute of the HtmlDialog object
   *
   *@return    The showAndConfirm value
   */
  public boolean getShowAndConfirm() {
    return showAndConfirm;
  }


  /**
   *  Gets the synchFrameCounter attribute of the HtmlDialog object
   *
   *@return    The synchFrameCounter value
   */
  public int getSynchFrameCounter() {
    return synchFrameCounter;
  }


  /**
   *  Gets the buttonString attribute of the HtmlDialog object
   *
   *@return    The buttonString value
   */
  public String getButtonString() {
    Set s = buttons.keySet();
    Iterator i = s.iterator();
    StringBuffer buttonString = new StringBuffer();
    buttonString.append("&nbsp;");
    while (i.hasNext()) {
      Object id = i.next();
      Object st = buttons.get(id);
      buttonString.append("<input type=button name=\"" + id.toString() + "\" value=\"" + id.toString() + "\" onClick=\"" + st.toString() + "\">");
      buttonString.append("&nbsp;&nbsp;");
    }
    return buttonString.toString();
  }


  /**
   *  Gets the relationshipString attribute of the HtmlDialog object
   *
   *@return    The relationshipString value
   */
  public String getRelationshipString() {
    Set s = relationships.keySet();
    Iterator i = s.iterator();
    StringBuffer relations = new StringBuffer();
    relations.append("<br>");
    while (i.hasNext()) {
      Object name = i.next();
      relations.append("&nbsp;&nbsp;");
      relations.append("- ");
      relations.append(name + " (" + relationships.get(name) + ")");
      relations.append("<br>");
    }
    return relations.toString();
  }


  /**
   *  Gets the linkString attribute of the HtmlDialog object
   *
   *@return    The linkString value
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
      linkString.append("<br>");
    }
    return linkString.toString();
  }


  /**
   *  Adds a feature to the Button attribute of the HtmlDialog object
   *
   *@param  displayName  The feature to be added to the Button attribute
   *@param  action       The feature to be added to the Button attribute
   */
  public void addButton(String displayName, String action) {
    buttons.put(displayName, action);
  }


  /**
   *  Gets the frameHtml attribute of the HtmlDialog object
   *
   *@param  frameId  Description of the Parameter
   *@return          The frameHtml value
   */
  public String getFrameHtml(int frameId) {
    StringBuffer htmlString = new StringBuffer();

    switch (frameId) {
        case HtmlDialog.TOP:
          htmlString.append("<strong>" + this.getHeader() + "</strong>");
          break;
        case HtmlDialog.MIDDLE:
          if (this.getRelationships().size() != 0) {
            htmlString.append("The following Relationships will be deleted ");
            htmlString.append(this.getRelationshipString());
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
   *  Description of the Method
   */
  private synchronized void decrementSynchFrameCounter() {
    --synchFrameCounter;
  }
}

