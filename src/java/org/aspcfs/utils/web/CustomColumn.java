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

import java.util.*;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.web.HtmlCoreAttributes;

/**
 *  Builds a Html Column element ... opeionally builds html from XML input.
 *  usage : <column valign="top" class="formLabel" width="80" nowrap="true">
 *
 *@author     Akhilesh Mathur
 *@created    October 22, 2002
 *@version    $Id$
 *      Note : Class attribute in the xml has to be defined as elementClass as
 *      class is a reserved word
 */
public class CustomColumn extends ArrayList implements HtmlCoreAttributes {

  private boolean multiple = false;
  private String startTag = null;
  private final static String endTag = "</TD>";

  //core attributes
  private String id = null;
  private String style = null;
  private String title = null;
  private String elementClass = null;

  //custom attributes
  private String align = null;
  private String valign = null;
  private String bgColor = null;
  private String colspan = null;
  private String rowspan = null;
  private String height = null;
  private String width = null;
  private String type = null;
  private boolean nowrap = false;


  /**
   *  Constructor for the CustomColumn object
   */
  public CustomColumn() { }


  /**
   *  Constructor for the CustomColumn object
   *
   *@param  column  Description of the Parameter
   */
  public CustomColumn(Element column) {
    processXMLColumn(column);
  }


  /**
   *  Sets the multiple attribute of the CustomColumn object
   *
   *@param  multiple  The new multiple value
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }


  /**
   *  Sets the multiple attribute of the CustomColumn object
   *
   *@param  multiple  The new multiple value
   */
  public void setMultiple(String multiple) {
    this.multiple = "true".equalsIgnoreCase(multiple);
  }


  /**
   *  Sets the startTag attribute of the CustomColumn object
   *
   *@param  startTag  The new startTag value
   */
  public void setStartTag(String startTag) {
    this.startTag = startTag;
  }


  /**
   *  Sets the id attribute of the CustomColumn object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the style attribute of the CustomColumn object
   *
   *@param  tmp  The new style value
   */
  public void setStyle(String tmp) {
    this.style = tmp;
  }


  /**
   *  Sets the title attribute of the CustomColumn object
   *
   *@param  tmp  The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   *  Sets the elementClass attribute of the CustomColumn object
   *
   *@param  tmp  The new elementClass value
   */
  public void setElementClass(String tmp) {
    this.elementClass = tmp;
  }


  /**
   *  Sets the align attribute of the CustomColumn object
   *
   *@param  tmp  The new align value
   */
  public void setAlign(String tmp) {
    this.align = tmp;
  }


  /**
   *  Sets the valign attribute of the CustomColumn object
   *
   *@param  tmp  The new valign value
   */
  public void setValign(String tmp) {
    this.valign = tmp;
  }


  /**
   *  Sets the bgColor attribute of the CustomColumn object
   *
   *@param  tmp  The new bgColor value
   */
  public void setBgColor(String tmp) {
    this.bgColor = tmp;
  }


  /**
   *  Sets the colspan attribute of the CustomColumn object
   *
   *@param  tmp  The new colSpan value
   */
  public void setColspan(String tmp) {
    this.colspan = tmp;
  }


  /**
   *  Sets the rowspan attribute of the CustomColumn object
   *
   *@param  tmp  The new rowSpan value
   */
  public void setRowspan(String tmp) {
    this.rowspan = tmp;
  }


  /**
   *  Sets the height attribute of the CustomColumn object
   *
   *@param  tmp  The new height value
   */
  public void setHeight(String tmp) {
    this.height = tmp;
  }


  /**
   *  Sets the width attribute of the CustomColumn object
   *
   *@param  tmp  The new width value
   */
  public void setWidth(String tmp) {
    this.width = tmp;
  }


  /**
   *  Sets the type attribute of the CustomColumn object
   *
   *@param  type  The new type value
   */
  public void setType(String type) {
    this.type = type;
  }


  /**
   *  Sets the nowrap attribute of the CustomColumn object
   *
   *@param  nowrap  The new nowrap value
   */
  public void setNowrap(boolean nowrap) {
    this.nowrap = nowrap;
  }


  /**
   *  Sets the nowrap attribute of the CustomColumn object
   *
   *@param  nowrap  The new nowrap value
   */
  public void setNowrap(String nowrap) {
    this.nowrap = "true".equalsIgnoreCase(nowrap);
  }


  /**
   *  Gets the nowrap attribute of the CustomColumn object
   *
   *@return    The nowrap value
   */
  public boolean getNowrap() {
    return nowrap;
  }


  /**
   *  Gets the type attribute of the CustomColumn object
   *
   *@return    The type value
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the colspan attribute of the CustomColumn object
   *
   *@return    The colSpan value
   */
  public String getColspan() {
    return colspan;
  }


  /**
   *  Gets the rowSpan attribute of the CustomColumn object
   *
   *@return    The rowSpan value
   */
  public String getRowspan() {
    return rowspan;
  }


  /**
   *  Gets the height attribute of the CustomColumn object
   *
   *@return    The height value
   */
  public String getHeight() {
    return height;
  }


  /**
   *  Gets the width attribute of the CustomColumn object
   *
   *@return    The width value
   */
  public String getWidth() {
    return width;
  }


  /**
   *  Gets the align attribute of the CustomColumn object
   *
   *@return    The align value
   */
  public String getAlign() {
    return align;
  }


  /**
   *  Gets the valign attribute of the CustomColumn object
   *
   *@return    The valign value
   */
  public String getValign() {
    return valign;
  }


  /**
   *  Gets the bgColor attribute of the CustomColumn object
   *
   *@return    The bgColor value
   */
  public String getBgColor() {
    return bgColor;
  }


  /**
   *  Gets the id attribute of the CustomColumn object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Gets the style attribute of the CustomColumn object
   *
   *@return    The style value
   */
  public String getStyle() {
    return style;
  }


  /**
   *  Gets the title attribute of the CustomColumn object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Gets the elementClass attribute of the CustomColumn object
   *
   *@return    The elementClass value
   */
  public String getElementClass() {
    return elementClass;
  }


  /**
   *  Gets the startTag attribute of the CustomColumn object
   *
   *@return    The startTag value
   */
  public String getStartTag() {
    return startTag;
  }


  /**
   *  Gets the endTag attribute of the CustomColumn object
   *
   *@return    The endTag value
   */
  public String getEndTag() {
    return endTag;
  }


  /**
   *  Gets the multiple attribute of the CustomColumn object
   *
   *@return    The multiple value
   */
  public boolean getMultiple() {
    return multiple;
  }


  /**
   *  Gets the coreAttributes attribute of the CustomColumn object
   *
   *@return    The coreAttributes value
   */
  public String getCoreAttributes() {
    StringBuffer tmpString = new StringBuffer();
    tmpString.append(id != null ? " id=\"" + id + "\" " : "");
    tmpString.append(title != null ? " title=\"" + title + "\" " : "");
    tmpString.append(style != null ? " style=\"" + style + "\" " : "");
    tmpString.append(elementClass != null ? " class=\"" + elementClass + "\" " : "");
    return tmpString.toString();
  }


  /**
   *  Gets the customAttributes attribute of the CustomColumn object
   *
   *@return    The customAttributes value
   */
  public String getCustomAttributes() {
    StringBuffer tmpString = new StringBuffer();
    tmpString.append(align != null ? " align=\"" + align + "\" " : "");
    tmpString.append(valign != null ? " valign=\"" + valign + "\" " : "");
    tmpString.append(bgColor != null ? " bgcolor=\"" + bgColor + "\" " : "");
    tmpString.append(colspan != null ? " colspan=\"" + colspan + "\" " : "");
    tmpString.append(rowspan != null ? " rowspan=\"" + rowspan + "\" " : "");
    tmpString.append(height != null ? " height=\"" + height + "\" " : "");
    tmpString.append(width != null ? " width=\"" + width + "\" " : "");
    return tmpString.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  column  Description of the Parameter
   */
  public void processXMLColumn(Element column) {
    //process XML to fill object and call  buildColumn
    //Note : name in the xml should map exactly to the corresponding properties defined in the object
    NamedNodeMap nnm = column.getAttributes();
    if (nnm != null) {
      for (int i = 0; i < nnm.getLength(); i++) {
        ObjectUtils.setParam(this, nnm.item(i).getNodeName().trim(), nnm.item(i).getNodeValue().trim());
      }
      if (column.getAttribute("class") != null && !"".equals(column.getAttribute("class"))) {
        this.elementClass = column.getAttribute("class");
      }
    }
  }


  /**
   *  Description of the Method
   */
  public void build() {
    startTag = "<TD" + this.getCoreAttributes() + this.getCustomAttributes() + (nowrap ? " nowrap " : "") + ">";
  }
}

