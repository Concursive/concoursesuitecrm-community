//Copyright 2001 Dark Horse Ventures
package com.darkhorseventures.controller;

import java.io.*;

/**
 *  Represents an item in the RecentItems list
 *
 *@author     mrajkowski
 *@created    December 19, 2001
 *@version    $Id$
 */
public class RecentItem implements Serializable {

  //Properties
  private int type = -1;
  private String displayText = null;
  private String url = null;

  //Constants used for describing the item type
  public final static int ACCOUNT = 1;
  public final static int CONTACT = 2;
  public final static int EMPLOYEE = 3;
  public final static int OPPORTUNITY = 4;
  public final static int CAMPAIGN = 5;
  public final static int PROJECT = 6;
  public final static int TICKET = 7;
  public final static int USER = 8;
  public final static int COMPONENT = 9;

  //Constants used for appending to the display name
  //These could be links to graphics at some point
  private final static String ACCOUNT_TEXT = "A: ";
  private final static String CONTACT_TEXT = "C: ";
  private final static String EMPLOYEE_TEXT = "E: ";
  private final static String OPPORTUNITY_TEXT = "O: ";
  private final static String CAMPAIGN_TEXT = "Campaign: ";
  private final static String PROJECT_TEXT = "Project: ";
  private final static String TICKET_TEXT = "Ticket: ";
  private final static String USER_TEXT = "U: ";
  private final static String COMPONENT_TEXT = "OC: ";


  /**
   *  Constructor for the RecentItem object
   *
   *@since    1.1
   */
  public RecentItem() { }


  /**
   *  Constructor for the RecentItem object
   *
   *@param  type         Description of Parameter
   *@param  displayText  Description of Parameter
   *@param  url          Description of Parameter
   *@since               1.1
   */
  public RecentItem(int type, String displayText, String url) {
    this.type = type;
    this.displayText = displayText;
    this.url = url;
  }


  /**
   *  Sets the Type attribute of the RecentItem object
   *
   *@param  tmp  The new Type value
   *@since       1.1
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the DisplayText attribute of the RecentItem object
   *
   *@param  tmp  The new DisplayText value
   *@since       1.1
   */
  public void setDisplayText(String tmp) {
    this.displayText = tmp;
  }


  /**
   *  Sets the Url attribute of the RecentItem object
   *
   *@param  tmp  The new Url value
   *@since       1.1
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Gets the Type attribute of the RecentItem object
   *
   *@return    The Type value
   *@since     1.1
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the DisplayText attribute of the RecentItem object
   *
   *@return    The DisplayText value
   *@since     1.1
   */
  public String getDisplayText() {
    return displayText;
  }


  /**
   *  Gets the Url attribute of the RecentItem object
   *
   *@return    The Url value
   *@since     1.1
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Builds and returns the Html link for accessing this item.<p>
   *
   *  An item is made up of the following:<br>
   *  - DisplayLabel: Anything that precedes the display text... including text
   *  or a graphic<br>
   *  - URL: The link for accessing the item<br>
   *  - DisplayText: Information that explains what the RecentItem is
   *
   *@return    The Html value
   *@since     1.1
   */
  public String getHtml() {
    return "<b>" + getDisplayLabel() + "</b><a href=\"" + url + "\"><u>" + displayText + "</u></a>";
  }


  /**
   *  Gets the DisplayLabel attribute of the RecentItem object
   *
   *@return    The DisplayLabel value
   *@since     1.1
   */
  private String getDisplayLabel() {
    switch (type) {
      case ACCOUNT:
        return ACCOUNT_TEXT;
      case CONTACT:
        return CONTACT_TEXT;
      case EMPLOYEE:
        return EMPLOYEE_TEXT;
      case OPPORTUNITY:
        return OPPORTUNITY_TEXT;
      case CAMPAIGN:
        return CAMPAIGN_TEXT;
      case PROJECT:
        return PROJECT_TEXT;
      case TICKET:
        return TICKET_TEXT;
      case USER:
        return USER_TEXT;
      case COMPONENT:
        return COMPONENT_TEXT;        
      default:
        return "";
    }
  }
}

