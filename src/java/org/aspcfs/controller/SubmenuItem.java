package com.darkhorseventures.controller;

/**
 *  Description of the Class
 *
 *@author     mrajkowski
 *@created    August 8, 2001
 *@version    $Id: SubmenuItem.java,v 1.1 2001/08/08 15:34:19 mrajkowski Exp
 *      $
 */
public class SubmenuItem {

  String name = "";
  String longHtml = "";
  String shortHtml = "";
  String alternateName = "";
  String link = "";
  String htmlClass = "r";
  String permission = "";


  /**
   *  Constructor for the SubmenuItem object
   *
   *@since    1.0
   */
  public SubmenuItem() {
  }


  /**
   *  Constructor for the SubmenuItem object, copies an existing
   *  submenu item
   *
   *@param  otherItem  Description of Parameter
   *@since 1.2
   */
  public SubmenuItem(SubmenuItem otherItem) {
    this.setName(new String(otherItem.getName()));
    this.setLongHtml(new String(otherItem.getLongHtml()));
    this.setShortHtml(new String(otherItem.getShortHtml()));
    this.setAlternateName(new String(otherItem.getAlternateName()));
    this.setLink(new String(otherItem.getLink()));
    this.setHtmlClass(new String(otherItem.getHtmlClass()));
    this.setPermission(new String(otherItem.getPermission()));
  }


  /**
   *  Sets the Name attribute of the SubmenuItem object
   *
   *@param  tmp  The new Name value
   *@since       1.0
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Html attribute of the SubmenuItem object
   *
   *@param  tmp  The new Html value
   *@since       1.2
   */
  public void setLongHtml(String tmp) {
    this.longHtml = tmp;
  }


  /**
   *  Sets the ShortHtml attribute of the SubmenuItem object
   *
   *@param  tmp  The new ShortHtml value
   *@since       1.2
   */
  public void setShortHtml(String tmp) {
    this.shortHtml = tmp;
  }



  /**
   *  Sets the AlternateName attribute of the SubmenuItem object
   *
   *@param  tmp  The new AlternateName value
   *@since       1.0
   */
  public void setAlternateName(String tmp) {
    this.alternateName = tmp;
  }


  /**
   *  Sets the Link attribute of the SubmenuItem object
   *
   *@param  tmp  The new Link value
   *@since       1.0
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   *  Sets the HtmlClass attribute of the SubmenuItem object
   *
   *@param  tmp  The new HtmlClass value
   *@since       1.0
   */
  public void setHtmlClass(String tmp) {
    this.htmlClass = tmp;
  }


  /**
   *  Sets the Permission attribute of the SubmenuItem object
   *
   *@param  tmp  The new Permission value
   *@since       1.0
   */
  public void setPermission(String tmp) {
    this.permission = tmp;
  }


  /**
   *  Gets the Html attribute of the SubmenuItem object
   *
   *@return    The Html value
   *@since     1.0
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
   *  Gets the Html attribute of the SubmenuItem object
   *
   *@return    The Html value
   *@since     1.2
   */
  public String getLongHtml() {
    return longHtml;
  }


  /**
   *  Gets the ShortHtml attribute of the SubmenuItem object
   *
   *@return    The ShortHtml value
   *@since     1.2
   */
  public String getShortHtml() {
    return shortHtml;
  }



  /**
   *  Gets the AlternateHtml attribute of the SubmenuItem object
   *
   *@return    The AlternateHtml value
   *@since     1.0
   */
  public String getAlternateHtml() {
    if (link == null || link.equals("")) {
      return shortHtml;
    } else {
      return ("<a " + (htmlClass.equals("") ? "" : "class='" + htmlClass + "' ") +
        "href='" + link + "'>" + shortHtml + "</a>");
    }
  }


  /**
   *  Gets the Name attribute of the SubmenuItem object
   *
   *@return    The Name value
   *@since     1.0
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the AlternateName attribute of the SubmenuItem object
   *
   *@return    The AlternateName value
   *@since     1.0
   */
  public String getAlternateName() {
    return alternateName;
  }


  /**
   *  Gets the Link attribute of the SubmenuItem object
   *
   *@return    The Link value
   *@since     1.0
   */
  public String getLink() {
    return link;
  }


  /**
   *  Gets the HtmlClass attribute of the SubmenuItem object
   *
   *@return    The HtmlClass value
   *@since     1.0
   */
  public String getHtmlClass() {
    return htmlClass;
  }


  /**
   *  Gets the Permission attribute of the SubmenuItem object
   *
   *@return    The Permission value
   *@since     1.0
   */
  public String getPermission() {
    return permission;
  }


  /**
   *  Description of the Method
   *
   *@param  thisPermission  Description of Parameter
   *@return                 Description of the Returned Value
   *@since                  1.0
   */
  public boolean hasPermission(String thisPermission) {
    return (permission.equals(thisPermission));
  }

}

