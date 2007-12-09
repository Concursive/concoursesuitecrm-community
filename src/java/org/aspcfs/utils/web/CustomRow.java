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
package org.aspcfs.utils.web;

import org.aspcfs.utils.ObjectUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.ArrayList;

/**
 * Builds a Html Column element ... opeionally builds html from XML input.
 * usage : <row class="containerBody"> <br>
 * Options : Can be used to build a list of similar row types given template of
 * a row<br>
 * usage : <row multiple="true" listName="questions" listId="id"> <br>
 * multiple : specifies if the row is a rowList .. like a TaskList <br>
 * listName : the name of method to be invoked on object used to populate the
 * row id : attribute used to differentiate the rows. <br>
 * <br>
 * Note : Class attribute in the xml has to be defined as elementClass as class
 * is a reserved word
 *
 * @author akhi_m
 * @version $Id$
 * @created October 22, 2002
 */
public class CustomRow extends ArrayList implements HtmlCoreAttributes {

  private boolean multiple = false;
  private String startTag = null;
  private final static String endTag = "</TR>";

  //core attributes
  private String id = null;
  private String style = null;
  private String title = null;
  private String elementClass = null;

  //custom attributes
  private String align = null;
  private String valign = null;
  private String bgColor = null;
  private String type = null;

  //to process multiple instances of the row
  private String listName = null;
  private String listId = null;
  private Object listObject = null;


  /**
   * Constructor for the CustomRow object
   */
  public CustomRow() {
  }


  /**
   * Constructor for the CustomRow object
   *
   * @param row Description of the Parameter
   */
  public CustomRow(Element row) {
    processXMLRow(row);
  }


  /**
   * Sets the multiple attribute of the CustomRow object
   *
   * @param multiple The new multiple value
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }


  /**
   * Sets the multiple attribute of the CustomRow object
   *
   * @param multiple The new multiple value
   */
  public void setMultiple(String multiple) {
    this.multiple = "true".equalsIgnoreCase(multiple);
  }


  /**
   * Sets the startTag attribute of the CustomRow object
   *
   * @param startTag The new startTag value
   */
  public void setStartTag(String startTag) {
    this.startTag = startTag;
  }


  /**
   * Sets the id attribute of the CustomRow object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   * Sets the style attribute of the CustomRow object
   *
   * @param tmp The new style value
   */
  public void setStyle(String tmp) {
    this.style = tmp;
  }


  /**
   * Sets the title attribute of the CustomRow object
   *
   * @param tmp The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   * Sets the elementClass attribute of the CustomRow object
   *
   * @param tmp The new elementClass value
   */
  public void setElementClass(String tmp) {
    this.elementClass = tmp;
  }


  /**
   * Sets the align attribute of the CustomRow object
   *
   * @param tmp The new align value
   */
  public void setAlign(String tmp) {
    this.align = tmp;
  }


  /**
   * Sets the valign attribute of the CustomRow object
   *
   * @param tmp The new valign value
   */
  public void setValign(String tmp) {
    this.valign = tmp;
  }


  /**
   * Sets the bgColor attribute of the CustomRow object
   *
   * @param tmp The new bgColor value
   */
  public void setBgColor(String tmp) {
    this.bgColor = tmp;
  }


  /**
   * Sets the type attribute of the CustomRow object
   *
   * @param type The new type value
   */
  public void setType(String type) {
    this.type = type;
  }


  /**
   * Sets the listName attribute of the CustomRow object
   *
   * @param tmp The new listName value
   */
  public void setListName(String tmp) {
    this.listName = tmp;
  }


  /**
   * Sets the listId attribute of the CustomRow object
   *
   * @param tmp The new listId value
   */
  public void setListId(String tmp) {
    this.listId = tmp;
  }


  /**
   * Sets the listObject attribute of the CustomRow object
   *
   * @param listObject The new listObject value
   */
  public void setListObject(Object listObject) {
    this.listObject = listObject;
  }


  /**
   * Gets the listObject attribute of the CustomRow object
   *
   * @return The listObject value
   */
  public Object getListObject() {
    return listObject;
  }


  /**
   * Gets the listName attribute of the CustomRow object
   *
   * @return The listName value
   */
  public String getListName() {
    return listName;
  }


  /**
   * Gets the listId attribute of the CustomRow object
   *
   * @return The listId value
   */
  public String getListId() {
    return listId;
  }


  /**
   * Gets the type attribute of the CustomRow object
   *
   * @return The type value
   */
  public String getType() {
    return type;
  }


  /**
   * Gets the align attribute of the CustomRow object
   *
   * @return The align value
   */
  public String getAlign() {
    return align;
  }


  /**
   * Gets the valign attribute of the CustomRow object
   *
   * @return The valign value
   */
  public String getValign() {
    return valign;
  }


  /**
   * Gets the bgColor attribute of the CustomRow object
   *
   * @return The bgColor value
   */
  public String getBgColor() {
    return bgColor;
  }


  /**
   * Gets the id attribute of the CustomRow object
   *
   * @return The id value
   */
  public String getId() {
    return id;
  }


  /**
   * Gets the style attribute of the CustomRow object
   *
   * @return The style value
   */
  public String getStyle() {
    return style;
  }


  /**
   * Gets the title attribute of the CustomRow object
   *
   * @return The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Gets the elementClass attribute of the CustomRow object
   *
   * @return The elementClass value
   */
  public String getElementClass() {
    return elementClass;
  }


  /**
   * Gets the startTag attribute of the CustomRow object
   *
   * @return The startTag value
   */
  public String getStartTag() {
    return startTag;
  }


  /**
   * Gets the endTag attribute of the CustomRow object
   *
   * @return The endTag value
   */
  public String getEndTag() {
    return endTag;
  }


  /**
   * Gets the multiple attribute of the CustomRow object
   *
   * @return The multiple value
   */
  public boolean getMultiple() {
    return multiple;
  }


  /**
   * Gets the coreAttributes attribute of the CustomRow object
   *
   * @return The coreAttributes value
   */
  public String getCoreAttributes() {
    StringBuffer tmpString = new StringBuffer();
    tmpString.append(id != null ? " id=\"" + id + "\" " : "");
    tmpString.append(title != null ? " title=\"" + title + "\" " : "");
    tmpString.append(style != null ? " style=\"" + style + "\" " : "");
    tmpString.append(
        elementClass != null ? " class=\"" + elementClass + "\" " : "");
    return tmpString.toString();
  }


  /**
   * Gets the customAttributes attribute of the CustomRow object
   *
   * @return The customAttributes value
   */
  public String getCustomAttributes() {
    StringBuffer tmpString = new StringBuffer();
    tmpString.append(align != null ? " align=\"" + align + "\" " : "");
    tmpString.append(valign != null ? " valign=\"" + valign + "\" " : "");
    tmpString.append(bgColor != null ? " bgcolor=\"" + bgColor + "\" " : "");
    return tmpString.toString();
  }


  /**
   * Description of the Method
   *
   * @param row Description of the Parameter
   */
  public void processXMLRow(Element row) {
    //process XML to fill object and call  buildRow
    NamedNodeMap nnm = row.getAttributes();
    if (nnm != null) {
      for (int i = 0; i < nnm.getLength(); i++) {
        ObjectUtils.setParam(
            this, nnm.item(i).getNodeName().trim(), nnm.item(i).getNodeValue().trim());
      }
      if (row.getAttribute("class") != null && !"".equals(
          row.getAttribute("class"))) {
        this.elementClass = row.getAttribute("class");
      }
    }
  }


  /**
   * Description of the Method
   */
  public void build() {
    startTag = "<TR " + this.getCoreAttributes() + this.getCustomAttributes() + ">";
  }

}

