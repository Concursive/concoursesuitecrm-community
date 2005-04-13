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
package org.aspcfs.modules.products.configurator;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.HTTPUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *  Description of the Class
 *
 *@author     ananth
 *@created    August 31, 2004
 *@version    $Id: OptionProperty.java,v 1.1.4.1 2004/10/18 19:56:27 mrajkowski
 *      Exp $
 */
public class OptionProperty extends GenericBean {
  public final static int LISTSELECT = 1;
  // property type constants
  public final static int SIMPLE_PROPERTY = 1;
  public final static int BASEADJUST_PROPERTY = 2;
  public final static int LOOKUP_PROPERTY = 3;

  private String name = null;
  private String value = null;
  private String display = null;
  private boolean isForPrompting = false;
  private boolean isRequired = false;
  private int type = -1;
  private String note = null;
  private String errorMsg = null;


  /**
   *  Gets the isRequired attribute of the OptionProperty object
   *
   *@return    The isRequired value
   */
  public boolean getIsRequired() {
    return isRequired;
  }


  /**
   *  Sets the isRequired attribute of the OptionProperty object
   *
   *@param  tmp  The new isRequired value
   */
  public void setIsRequired(boolean tmp) {
    this.isRequired = tmp;
  }


  /**
   *  Sets the isRequired attribute of the OptionProperty object
   *
   *@param  tmp  The new isRequired value
   */
  public void setIsRequired(String tmp) {
    this.isRequired = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the type attribute of the OptionProperty object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Sets the type attribute of the OptionProperty object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the type attribute of the OptionProperty object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Sets the errorMsg attribute of the OptionProperty object
   *
   *@param  tmp  The new errorMsg value
   */
  public void setErrorMsg(String tmp) {
    this.errorMsg = tmp;
  }


  /**
   *  Gets the errorMsg attribute of the OptionProperty object
   *
   *@return    The errorMsg value
   */
  public String getErrorMsg() {
    return errorMsg;
  }


  /**
   *  Sets the note attribute of the OptionProperty object
   *
   *@param  tmp  The new note value
   */
  public void setNote(String tmp) {
    this.note = tmp;
  }


  /**
   *  Gets the note attribute of the OptionProperty object
   *
   *@return    The note value
   */
  public String getNote() {
    return note;
  }


  /**
   *  Sets the isForPrompting attribute of the OptionProperty object
   *
   *@param  tmp  The new isForPrompting value
   */
  public void setIsForPrompting(boolean tmp) {
    this.isForPrompting = tmp;
  }


  /**
   *  Sets the isForPrompting attribute of the OptionProperty object
   *
   *@param  tmp  The new isForPrompting value
   */
  public void setIsForPrompting(String tmp) {
    this.isForPrompting = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the isForPrompting attribute of the OptionProperty object
   *
   *@return    The isForPrompting value
   */
  public boolean getIsForPrompting() {
    return isForPrompting;
  }


  /**
   *  Sets the name attribute of the OptionProperty object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the value attribute of the OptionProperty object
   *
   *@param  tmp  The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }


  /**
   *  Sets the display attribute of the OptionProperty object
   *
   *@param  tmp  The new display value
   */
  public void setDisplay(String tmp) {
    this.display = tmp;
  }


  /**
   *  Gets the name attribute of the OptionProperty object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the value attribute of the OptionProperty object
   *
   *@return    The value value
   */
  public String getValue() {
    return value;
  }


  /**
   *  Gets the display attribute of the OptionProperty object
   *
   *@return    The display value
   */
  public String getDisplay() {
    return display;
  }


  /**
   *  Constructor for the OptionProperty object
   */
  public OptionProperty() { }


  /**
   *  Constructor for the OptionProperty object
   *
   *@param  name   Description of the Parameter
   *@param  value  Description of the Parameter
   */
  public OptionProperty(String name, String value) {
    this.name = name;
    this.value = value;
  }


  /**
   *  Constructor for the OptionProperty object
   *
   *@param  name     Description of the Parameter
   *@param  value    Description of the Parameter
   *@param  display  Description of the Parameter
   *@param  prompt   Description of the Parameter
   */
  public OptionProperty(String name, String value, String display, boolean prompt) {
    this.name = name;
    this.value = value;
    this.display = display;
    this.isForPrompting = prompt;
  }


  /**
   *  Description of the Method
   *
   *@param  request           Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void prepareContext(Connection db, HttpServletRequest request) throws SQLException {
    /*
    if (name.startsWith("boolean_")) {
      HtmlSelect select = new HtmlSelect();
      select.addItem("1", "Yes");
      select.addItem("0", "No");
      request.setAttribute(name, select);
    }*/
  }


  /**
   *  Gets the html attribute of the OptionProperty object
   *
   *@param  request  Description of the Parameter
   *@return          The html value
   */
  public String getHtml(HttpServletRequest request) {
    if (name.startsWith("text_")) {
      //Text Field
      int defaultSize = 25;
      return table("<input type=\"text\" size=\"" + defaultSize + "\" " +
          "name=\"" + name + "\" " +
          "value=\"" + HTTPUtils.toHtmlValue(value) + "\" />");
    } else if (name.startsWith("number_")) {
      int defaultSize = 10;
      return table("<input type=\"text\" size=\"" + defaultSize + "\" " +
          "name=\"" + name + "\" value=\"" + HTTPUtils.toHtmlValue(value) + "\" />");
    } else if (name.startsWith("lookup_")) {
      return table("<textarea cols=\"28\" rows=\"5\" name=\"" + name + "\" wrap=\"SOFT\">" + value + "</textarea>");
    } else if (name.startsWith("boolean_")) {
      return table("<input type=\"checkbox\" name=\"chk" + name + "\" onclick=\"javascript:setField('" + name + "', document.paramForm.chk" + name + ".checked, 'paramForm');\"" + (value != null && Boolean.valueOf(value).booleanValue() ? "checked" : "") + " >" + 
                          "<input type=\"hidden\" name=\"" + name + "\" value=\"" + (value != null && Boolean.valueOf(value).booleanValue() ? "true" : "false") + "\">");
    } else if (name.startsWith("double_")) {
      //Text Field for float input
      int defaultSize = 7;
      return table("<input type=\"text\" size=\"" + defaultSize + "\" " +
          "name=\"" + name + "\" " +
          "value=\"" + HTTPUtils.toHtmlValue(value) + "\" />");
    } 
    return "Property Not Supported";
  }


  /**
   *  Description of the Method
   *
   *@param  html  Description of the Parameter
   *@return       Description of the Return Value
   */
  private String table(String html) {
    return "<table cellspacing=\"1\" cellpadding=\"1\" border=\"0\">" +
        "	<tr>" +
        "		<td nowrap width=\"30%\">" + html + (isRequired ? "<font color=\"red\">*</font>" : "") + "</td>" +
        "		<td nowrap><font color=\"green\">" + note + "</font></td>" +
        "   <td><font color=\"#006699\">" + HTTPUtils.toHtmlValue(errorMsg) + "</font></td>" +
        "  </tr>" +
        "</table>";
  }


  /**
   *  Gets the valid attribute of the OptionProperty object
   *
   *@param  systemStatus  Description of the Parameter
   *@return               The valid value
   */
  public boolean isValid(SystemStatus systemStatus) {
    if (name.startsWith("text_")) {
      if ((value == null || value.trim().equals("")) && isRequired) {
        errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.required") : "Text value cannot be empty");
      }
    } else if (name.startsWith("number_")) {
      try {
        if (value != null && !"".equals(value.trim())) {
          int n = Integer.parseInt(value);
          if (n < 0) {
            errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.valueCanNotBeNegative") : "Value cannot be negative");
          }
        } else if (isRequired) {
          errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.incorrectNumberFormat") : "Integer value required");
        } 
      } catch (NumberFormatException nfe) {
        errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.incorrectNumberFormat") : "Integer value required");
      }
    } else if (name.startsWith("lookup_")) {
      if ((value == null || value.trim().equals("")) && isRequired) {
        errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.atleastOneItemToBeSpecified") : "At least one item needs to be specified");
      }
    } else if (name.startsWith("double_")) {
      try {
        if (value != null || !"".equals(value.trim())) {
          double val = Double.parseDouble(value);
          if (val < 0) {
            errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.incorrectNumberFormat") : "float error");
          }
        } else if (isRequired) {
          errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.incorrectNumberFormat") : "float error");
        }
      } catch (NumberFormatException nfe) {
        errorMsg = (systemStatus != null ? systemStatus.getLabel("object.validation.incorrectNumberFormat") : "float error");
      }
    }
    return (errorMsg == null);
  }
}

