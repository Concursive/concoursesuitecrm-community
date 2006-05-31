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
package org.aspcfs.modules.base;

import com.darkhorseventures.database.ConnectionElement;
import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.StateSelect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;

/**
 * Represents a CustomField, used for both the definition of a custom field and
 * also the data included in the custom field.
 *
 * @author mrajkowski
 * @version $Id$
 * @created December 27, 2001
 */
public class CustomField extends GenericBean implements Cloneable {

  //Types of custom fields
  public final static int TEXT = 1;
  public final static int SELECT = 2;
  public final static int TEXTAREA = 3;
  public final static int CHECKBOX = 4;
  public final static int BUTTON = 5;
  public final static int LABEL = 6;
  public final static int LINK = 7;

  //public final static int RADIOBOX = 5;
  //public final static int LISTSELECT = 6;
  //public final static int MULTILISTSELECT = 7;
  public final static int DATE = 8;
  public final static int INTEGER = 9;
  public final static int FLOAT = 10;
  public final static int PERCENT = 11;
  public final static int CURRENCY = 12;
  public final static int EMAIL = 13;
  public final static int URL = 14;
  public final static int PHONE = 15;
  public final static int ROWLIST = 16;
  public final static int HIDDEN = 17;
  public final static int DISPLAYTEXT = 18;

  //survey preview stuff
  public final static int DISPLAYROWLIST = 20;

  //lookup context ids
  public final static int LOOKUP_USERID = 21;

  public final static int HTMLAREA = 22;
  public final static int STATE_SELECT = 23;

  //Properties for a Field
  private int id = -1;
  private int groupId = -1;
  private String name = null;
  private String additionalText = null;
  private int level = -1;
  private int type = -1;
  private int validationType = -1;
  private boolean required = false;
  private Hashtable parameters = new Hashtable();
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private boolean enabled = true;
  private String error = null;

  //Properties for related data
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int recordId = -1;
  private int selectedItemId = -1;
  private String enteredValue = null;
  private int enteredNumber = 0;
  private double enteredDouble = 0;
  private Object elementData = null;
  private String lookupList = null;
  //used if field needs to be populated from a particular item of a list e.g SurveyQuestion of SurveyQuestionList
  private String listName = null;
  private String listItemName = null;

  private String jsEvent = null;
  private String onChange = null;
  private String display = null;
  private String lengthVar = null;
  private int maxRowItems = 0;
  private String delimiter = "\r\n";
  private boolean textAsCode = false;

  //static field i.e label
  private boolean isStatic = false;
  private boolean validateData = false;


  /**
   * Constructor for the CustomField object
   *
   * @since 1.1
   */
  public CustomField() {
  }


  /**
   * Constructor for the CustomField object
   *
   * @param db     Description of the Parameter
   * @param thisId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public CustomField(Connection db, int thisId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM custom_field_info cf " +
        "WHERE cf.field_id = ? ");
    pst.setInt(1, thisId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getType() == CustomField.SELECT) {
      this.buildElementData(db);
    }
  }


  /**
   * Constructor for the CustomField object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public CustomField(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Gets the linkItemId attribute of the CustomField object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }


  /**
   * Gets the validateData attribute of the CustomField object
   *
   * @return The validateData value
   */
  public boolean getValidateData() {
    return validateData;
  }


  /**
   * Sets the validateData attribute of the CustomField object
   *
   * @param tmp The new validateData value
   */
  public void setValidateData(boolean tmp) {
    this.validateData = tmp;
  }


  /**
   * Sets the validateData attribute of the CustomField object
   *
   * @param tmp The new validateData value
   */
  public void setValidateData(String tmp) {
    this.validateData = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   * Sets the elementData attribute of the CustomField object
   *
   * @param elementData The new elementData value
   */
  public void setElementData(Object elementData) {
    this.elementData = elementData;
  }


  /**
   * Constructor for the CustomField object
   *
   * @param rs        Description of Parameter
   * @param populated Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  public CustomField(ResultSet rs, boolean populated) throws SQLException {
    if (populated) {
      buildPopulatedRecord(rs);
    }
    buildRecord(rs);
  }


  /**
   * Sets the textAsCode attribute of the CustomField object
   *
   * @param textAsCode The new textAsCode value
   */
  public void setTextAsCode(boolean textAsCode) {
    this.textAsCode = textAsCode;
  }


  /**
   * Sets the Id attribute of the CustomField object
   *
   * @param tmp The new Id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Gets the lengthVar attribute of the CustomField object
   *
   * @return The lengthVar value
   */
  public String getLengthVar() {
    return lengthVar;
  }


  /**
   * Sets the lengthVar attribute of the CustomField object
   *
   * @param lengthVar The new lengthVar value
   */
  public void setLengthVar(String lengthVar) {
    this.lengthVar = lengthVar;
  }


  /**
   * Sets the parameter attribute of the CustomField object
   *
   * @param parameterName The new parameter value
   * @param value         The new parameter value
   */
  public void setParameter(String parameterName, String value) {
    parameters.put(parameterName, value);
  }


  /**
   * Sets the Id attribute of the CustomField object
   *
   * @param tmp The new Id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the onChange attribute of the CustomField object
   *
   * @param onChange The new onChange value
   */
  public void setOnChange(String onChange) {
    this.onChange = onChange;
  }


  /**
   * Sets the listItemName attribute of the CustomField object
   *
   * @param listItemName The new listItemName value
   */
  public void setListItemName(String listItemName) {
    this.listItemName = listItemName;
  }


  /**
   * Gets the listItemName attribute of the CustomField object
   *
   * @return The listItemName value
   */
  public String getListItemName() {
    return listItemName;
  }


  /**
   * Gets the onChange attribute of the CustomField object
   *
   * @return The onChange value
   */
  public String getOnChange() {
    return onChange;
  }


  /**
   * Gets the delimiter attribute of the CustomField object
   *
   * @return The delimiter value
   */
  public String getDelimiter() {
    return delimiter;
  }


  /**
   * Sets the delimiter attribute of the CustomField object
   *
   * @param delimiter The new delimiter value
   */
  public void setDelimiter(String delimiter) {
    this.delimiter = delimiter;
  }


  /**
   * Gets the textAsCode attribute of the CustomField object
   *
   * @return The textAsCode value
   */
  public boolean getTextAsCode() {
    return textAsCode;
  }


  /**
   * Sets the textAsCode attribute of the CustomField object
   *
   * @param textAsCode The new textAsCode value
   */
  public void setTextAsCode(String textAsCode) {
    this.textAsCode = textAsCode.equalsIgnoreCase("ON");
  }


  /**
   * Sets the GroupId attribute of the CustomField object
   *
   * @param tmp The new GroupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   * Sets the GroupId attribute of the CustomField object
   *
   * @param tmp The new GroupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   * Sets the maxRowItems attribute of the CustomField object
   *
   * @param maxRowItems The new maxRowItems value
   */
  public void setMaxRowItems(int maxRowItems) {
    this.maxRowItems = maxRowItems;
  }


  /**
   * Sets the listName attribute of the CustomField object
   *
   * @param listName The new listName value
   */
  public void setListName(String listName) {
    this.listName = listName;
  }


  /**
   * Gets the listName attribute of the CustomField object
   *
   * @return The listName value
   */
  public String getListName() {
    return listName;
  }


  /**
   * Gets the maxRowItems attribute of the CustomField object
   *
   * @return The maxRowItems value
   */
  public int getMaxRowItems() {
    if (maxRowItems == 0) {
      try {
        return ((ArrayList) elementData).size();
      } catch (Exception e) {
      }
    }
    return maxRowItems;
  }


  /**
   * Sets the maxRowItems attribute of the CustomField object
   *
   * @param maxRowItems The new maxRowItems value
   */
  public void setMaxRowItems(String maxRowItems) {
    if (maxRowItems != null) {
      this.maxRowItems = Integer.parseInt(maxRowItems);
    }
  }


  /**
   * Sets the Name attribute of the CustomField object
   *
   * @param tmp The new Name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Gets the display attribute of the CustomField object
   *
   * @return The display value
   */
  public String getDisplay() {
    return display;
  }


  /**
   * Gets the displayHtml attribute of the CustomField object
   *
   * @return The displayHtml value
   */
  public String getDisplayHtml() {
    return StringUtils.toHtml(display);
  }


  /**
   * Sets the display attribute of the CustomField object
   *
   * @param display The new display value
   */
  public void setDisplay(String display) {
    this.display = display;
  }


  /**
   * Sets the additionalText attribute of the CustomField object
   *
   * @param additionalText The new additionalText value
   */
  public void setAdditionalText(String additionalText) {
    this.additionalText = additionalText;
  }


  /**
   * Sets the Level attribute of the CustomField object
   *
   * @param tmp The new Level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   * Sets the level attribute of the CustomField object
   *
   * @param tmp The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   * Sets the Type attribute of the CustomField object
   *
   * @param tmp The new Type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   * Sets the Type attribute of the CustomField object
   *
   * @param tmp The new Type value
   */
  public void setType(String tmp) {
    try {
      this.type = Integer.parseInt(tmp);
    } catch (Exception e) {
      if (tmp.equalsIgnoreCase("text")) {
        this.type = TEXT;
      } else if (tmp.equalsIgnoreCase("select")) {
        this.type = SELECT;
      } else if (tmp.equalsIgnoreCase("checkbox")) {
        this.type = CHECKBOX;
      } else if (tmp.equalsIgnoreCase("textarea")) {
        this.type = TEXTAREA;
      } else if (tmp.equalsIgnoreCase("htmlarea")) {
        this.type = HTMLAREA;
      } else if (tmp.equalsIgnoreCase("rowlist")) {
        this.type = ROWLIST;
      } else if (tmp.equalsIgnoreCase("hidden")) {
        this.type = HIDDEN;
      } else if (tmp.equalsIgnoreCase("displaytext")) {
        this.type = DISPLAYTEXT;
      } else if (tmp.equalsIgnoreCase("lookupuserid")) {
        this.type = LOOKUP_USERID;
      } else if (tmp.equalsIgnoreCase("displayrowlist")) {
        this.type = DISPLAYROWLIST;
      } else if (tmp.equalsIgnoreCase("button")) {
        this.type = BUTTON;
      } else if (tmp.equalsIgnoreCase("label")) {
        this.type = LABEL;
      } else if (tmp.equalsIgnoreCase("link")) {
        this.type = LINK;
      } else if (tmp.equalsIgnoreCase("stateselect")) {
        this.type = STATE_SELECT;
      }
    }
  }


  /**
   * Sets the ValidationType attribute of the CustomField object
   *
   * @param tmp The new ValidationType value
   */
  public void setValidationType(int tmp) {
    this.validationType = tmp;
  }


  /**
   * Sets the validationType attribute of the CustomField object
   *
   * @param tmp The new validationType value
   */
  public void setValidationType(String tmp) {
    this.validationType = Integer.parseInt(tmp);
  }


  /**
   * Sets the Required attribute of the CustomField object
   *
   * @param tmp The new Required value
   */
  public void setRequired(boolean tmp) {
    this.required = tmp;
  }


  /**
   * Sets the Required attribute of the CustomField object
   *
   * @param tmp The new Required value
   */
  public void setRequired(String tmp) {
    this.required = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(
        tmp));
  }


  /**
   * Sets the StartDate attribute of the CustomField object
   *
   * @param tmp The new StartDate value
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   * Sets the startDate attribute of the CustomField object
   *
   * @param tmp The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the EndDate attribute of the CustomField object
   *
   * @param tmp The new EndDate value
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   * Sets the endDate attribute of the CustomField object
   *
   * @param tmp The new endDate value
   */
  public void setEndDate(String tmp) {
    this.endDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the Entered attribute of the CustomField object
   *
   * @param tmp The new Entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   * Sets the entered attribute of the CustomField object
   *
   * @param tmp The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the modified attribute of the CustomField object
   *
   * @param tmp The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   * Sets the modified attribute of the CustomField object
   *
   * @param tmp The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }


  /**
   * Sets the Enabled attribute of the CustomField object
   *
   * @param tmp The new Enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the CustomField object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   * Sets the Error attribute of the CustomField object
   *
   * @param tmp The new Error value
   */
  public void setError(String tmp) {
    this.error = tmp;
  }


  /**
   * Sets the LinkModuleId attribute of the CustomField object
   *
   * @param tmp The new LinkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the LinkItemId attribute of the CustomField object
   *
   * @param tmp The new LinkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   * Sets the RecordId attribute of the CustomField object
   *
   * @param tmp The new RecordId value
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   * Sets the recordId attribute of the CustomField object
   *
   * @param tmp The new recordId value
   */
  public void setRecordId(String tmp) {
    this.recordId = Integer.parseInt(tmp);
  }


  /**
   * Sets the SelectedItemId attribute of the CustomField object
   *
   * @param tmp The new SelectedItemId value
   */
  public void setSelectedItemId(int tmp) {
    this.selectedItemId = tmp;
  }


  /**
   * Sets the selectedItemId attribute of the CustomField object
   *
   * @param tmp The new selectedItemId value
   */
  public void setSelectedItemId(String tmp) {
    if (tmp != null) {
      if (tmp.equalsIgnoreCase("true")) {
        this.selectedItemId = 1;
      } else if (tmp.equalsIgnoreCase("false")) {
        this.selectedItemId = 0;
      } else {
        this.selectedItemId = Integer.parseInt(tmp);
      }
    }
  }


  /**
   * Sets the EnteredValue attribute of the CustomField object
   *
   * @param tmp The new EnteredValue value
   */
  public void setEnteredValue(String tmp) {
    if (tmp != null) {
      this.enteredValue = (tmp.trim());
    } else {
      this.enteredValue = null;
    }
  }


  /**
   * Sets the enteredNumber attribute of the CustomField object
   *
   * @param tmp The new enteredNumber value
   */
  public void setEnteredNumber(int tmp) {
    this.enteredNumber = tmp;
  }


  /**
   * Sets the enteredDouble attribute of the CustomField object
   *
   * @param tmp The new enteredDouble value
   */
  public void setEnteredDouble(double tmp) {
    this.enteredDouble = tmp;
  }


  /**
   * Sets the Length attribute of the CustomField object
   *
   * @param tmp The new Length value
   */
  public void setMaxLength(String tmp) {
    if (tmp != null && !tmp.equals("")) {
      parameters.put("maxlength", tmp);
    }
  }


  /**
   * Builds the lookupList from using the System Status.
   *
   * @param context The new lookupList value
   * @param db      Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean buildLookupList(Connection db, ActionContext context) throws SQLException {
    elementData = new LookupList();
    if (lookupList != null && !lookupList.equals("")) {
      ConnectionElement ce = (ConnectionElement) context.getSession().getAttribute(
          "ConnectionElement");
      elementData = ((SystemStatus) ((Hashtable) context.getServletContext().getAttribute(
          "SystemStatus")).get(ce.getUrl())).getLookupList(db, lookupList);
    }
    return true;
  }


  /**
   * Sets the Parameters attribute of the CustomField object
   *
   * @param context The new Parameters value
   */
  public void setParameters(ActionContext context) {
    String newValue = null;

    if (context.getRequest().getParameter("cf" + id) != null) {
      newValue = context.getRequest().getParameter("cf" + id);
    } else if (context.getRequest().getAttribute("cf" + id) != null) {
      newValue = (String) context.getRequest().getAttribute("cf" + id);
    }

    if (newValue != null) {
      newValue = newValue.trim();
      switch (type) {
        case SELECT:
          selectedItemId = Integer.parseInt(newValue);
          enteredValue = ((LookupList) elementData).getSelectedValue(
              selectedItemId);
          break;
        case CHECKBOX:
          if ("ON".equalsIgnoreCase(newValue)) {
            selectedItemId = 1;
            enteredValue = "Yes";
          } else {
            selectedItemId = 0;
            enteredValue = "No";
          }
          break;
        case STATE_SELECT:
          if ("-1".equals(newValue)) {
            enteredValue = "--"; //displays -1 otherwise
          } else {
            enteredValue = newValue;
          }
          break;
        default:
          enteredValue = newValue;
          break;
      }
    } else {
      // If the field is now found in the request, the item is either no
      // longer enabled (so leave the existing value) or it is a CHECKBOX
      if (enabled && type == CHECKBOX) {
        selectedItemId = 0;
        enteredValue = "No";
      }
    }
  }


  /**
   * Sets the parameters attribute of the CustomField object
   *
   * @param param The new parameters value
   */
  public void setParameters(String param) {
    StringTokenizer st = new StringTokenizer(param, "^");
    while (st.hasMoreTokens()) {
      StringTokenizer kv = new StringTokenizer(st.nextToken(), "|");
      if (kv.hasMoreTokens()) {
        parameters.put(kv.nextToken(), kv.nextToken());
      }
    }
  }


  /**
   * Sets the jsEvent attribute of the CustomField object
   *
   * @param jsEvent The new jsEvent value
   */
  public void setJsEvent(String jsEvent) {
    this.jsEvent = jsEvent;
  }


  /**
   * Sets the isStatic attribute of the CustomField object
   *
   * @param isStatic The new isStatic value
   */
  public void setIsStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }


  /**
   * Sets the lookupList attribute of the CustomField object
   *
   * @param tmp The new lookupList value
   */
  public void setLookupList(String tmp) {
    lookupList = tmp;
  }


  /**
   * Sets the lookupList attribute of the CustomField object
   *
   * @param tmp The new lookupList value
   */
  public void setLookupListText(String tmp) {
    elementData = new LookupList();
    int count = 0;
    StringTokenizer st = new StringTokenizer(tmp, delimiter);
    while (st.hasMoreTokens()) {
      String listField = st.nextToken();
      if (!listField.trim().equals("")) {
        ++count;
        LookupElement thisElement = new LookupElement();
        thisElement.setDescription(listField.trim());

        if (textAsCode) {
          thisElement.setCode(Integer.parseInt(thisElement.getDescription()));
        } else {
          thisElement.setCode(count);
        }

        thisElement.setLevel(count);
        thisElement.setDefaultItem(false);
        ((LookupList) elementData).add(thisElement);
      }
    }
  }


  /**
   * Gets the enteredDouble attribute of the CustomField object
   *
   * @return The enteredDouble value
   */
  public double getEnteredDouble() {
    return enteredDouble;
  }


  /**
   * Gets the isStatic attribute of the CustomField object
   *
   * @return The isStatic value
   */
  public boolean getIsStatic() {
    return isStatic;
  }


  /**
   * Gets the jsEvent attribute of the CustomField object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   * Gets the recordId attribute of the CustomField object
   *
   * @return The recordId value
   */
  public int getRecordId() {
    return recordId;
  }


  /**
   * Gets the lookupList attribute of the CustomField object
   *
   * @return The lookupList value
   */
  public String getLookupList() {
    return lookupList;
  }


  /**
   * When building a folder, this method generates the text for the HTML form
   * for a new LookupList
   *
   * @return The LookupList value
   */
  public String getLookupListText() {
    StringBuffer sb = new StringBuffer();
    if (elementData != null) {
      Iterator i = ((LookupList) elementData).iterator();
      while (i.hasNext()) {
        LookupElement thisElement = (LookupElement) i.next();
        sb.append(thisElement.getDescription());
        if (i.hasNext()) {
          sb.append(delimiter);
        }
      }
    }
    return sb.toString();
  }


  /**
   * Gets the Id attribute of the CustomField object
   *
   * @return The Id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the GroupId attribute of the CustomField object
   *
   * @return The GroupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   * Gets the Name attribute of the CustomField object
   *
   * @return The Name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the additionalText attribute of the CustomField object
   *
   * @return The additionalText value
   */
  public String getAdditionalText() {
    return additionalText;
  }


  /**
   * Gets the NameHtml attribute of the CustomField object
   *
   * @return The NameHtml value
   */
  public String getNameHtml() {
    return StringUtils.toHtml(name);
  }


  /**
   * Gets the Level attribute of the CustomField object
   *
   * @return The Level value
   */
  public int getLevel() {
    return level;
  }


  /**
   * Gets the Type attribute of the CustomField object
   *
   * @return The Type value
   */
  public int getType() {
    return type;
  }


  /**
   * Gets the TypeString attribute of the CustomField object
   *
   * @return The TypeString value
   */
  public String getTypeString() {
    return getTypeString(type, true);
  }


  /**
   * Gets the ValidationType attribute of the CustomField object
   *
   * @return The ValidationType value
   */
  public int getValidationType() {
    return validationType;
  }


  /**
   * Gets the Required attribute of the CustomField object
   *
   * @return The Required value
   */
  public boolean getRequired() {
    return required;
  }


  /**
   * Gets the StartDate attribute of the CustomField object
   *
   * @return The StartDate value
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   * Gets the EndDate attribute of the CustomField object
   *
   * @return The EndDate value
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   * Gets the Entered attribute of the CustomField object
   *
   * @return The Entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   * Gets the modified attribute of the CustomField object
   *
   * @return The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   * Gets the Enabled attribute of the CustomField object
   *
   * @return The Enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the Error attribute of the CustomField object
   *
   * @return The Error value
   */
  public String getError() {
    return error;
  }


  /**
   * Gets the SelectedItemId attribute of the CustomField object
   *
   * @return The SelectedItemId value
   */
  public int getSelectedItemId() {
    return selectedItemId;
  }


  /**
   * Gets the EnteredValue attribute of the CustomField object
   *
   * @return The EnteredValue value
   */
  public String getEnteredValue() {
    return enteredValue;
  }


  /**
   * Gets the ElementData attribute of the CustomField object
   *
   * @return The ElementData value
   */
  public Object getElementData() {
    return elementData;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws CloneNotSupportedException Description of the Exception
   */
  public CustomField duplicate() throws CloneNotSupportedException {
    return (CustomField) this.clone();
  }


  /**
   * Gets the LengthRequired attribute of the CustomField object for HTML
   * forms.
   *
   * @return The LengthRequired value
   */
  public boolean getLengthRequired() {
    switch (type) {
      case TEXT:
        return true;
      case INTEGER:
        return true;
      case FLOAT:
        return true;
      case CURRENCY:
        return true;
      default:
        return false;
    }
  }


  /**
   * Gets the LookupListRequired attribute of the CustomField object
   *
   * @return The LookupListRequired value
   */
  public boolean getLookupListRequired() {
    switch (type) {
      case SELECT:
        return true;
      default:
        return false;
    }
  }


  /**
   * Gets the Parameter attribute of the CustomField object
   *
   * @param tmp Description of Parameter
   * @return The Parameter value
   */
  public String getParameter(String tmp) {
    if (parameters.containsKey(tmp.toLowerCase())) {
      return (String) parameters.get(tmp.toLowerCase());
    } else {
      return "";
    }
  }


  /**
   * Gets the valueHtml attribute of the CustomField object
   *
   * @return The valueHtml value
   */
  public String getValueHtml() {
    return getValueHtml(true);
  }


  /**
   * Gets the valueHtml attribute of the CustomField object
   *
   * @param enableLinks Description of the Parameter
   * @return The valueHtml value
   */
  public String getValueHtml(boolean enableLinks) {
    if (type != SELECT && (enteredValue == null || enteredValue.equals(""))) {
      return StringUtils.toHtml(enteredValue);
    }
    switch (type) {
      case URL:
        if (enableLinks) {
          return "<a href=\"" + ((enteredValue.indexOf(":") > -1) ? "" : "http://") + enteredValue + "\" target=\"_new\">" + enteredValue + "</a>";
        } else {
          return StringUtils.toHtml(enteredValue);
        }
      case EMAIL:
        if (enableLinks && enteredValue.indexOf("@") > 0) {
          return "<a href=\"mailto:" + enteredValue + "\">" + enteredValue + "</a>";
        } else {
          return StringUtils.toHtml(enteredValue);
        }
      case SELECT:
        if (elementData != null) {
          return StringUtils.toHtml(
              ((LookupList) elementData).getSelectedValue(selectedItemId));
        } else {
          return StringUtils.toHtml(enteredValue);
        }
      case CHECKBOX:
        return (selectedItemId == 1 ? "Yes" : "No");
      case CURRENCY:
        try {
          double thisAmount = Double.parseDouble(enteredValue);
          NumberFormat numberFormatter = NumberFormat.getNumberInstance(
              Locale.US);
          return (numberFormatter.format(thisAmount));
          /*
          Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
          NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
          Currency currency = Currency.getInstance(System.getProperty("CURRENCY"));
          currencyFormatter.setCurrency(currency);
          return (currencyFormatter.format(thisAmount));
          */
        } catch (Exception e) {
          return (StringUtils.toHtml(enteredValue));
          //return ("$" + StringUtils.toHtml(enteredValue));
        }
      case PERCENT:
        return (StringUtils.toHtml(enteredValue) + "%");
      default:
        return (StringUtils.toHtml(enteredValue));
    }
  }


  /**
   * Gets the rowListElement attribute of the CustomField object
   *
   * @param index    Description of the Parameter
   * @param editable Description of the Parameter
   * @return The rowListElement value
   */
  public String getRowListElement(int index, boolean editable) {
    String hiddenElementName = name + index + "id";
    String textElementName = name + index + "text";
    Object tmpResult = null;

    String maxlength = this.getParameter("maxlength");
    String size = "";
    if (!maxlength.equals("")) {
      if (Integer.parseInt(maxlength) > 40) {
        size = "40";
      } else {
        size = maxlength;
      }
    }

    if (((ArrayList) elementData).size() >= index) {
      tmpResult = ((ArrayList) elementData).get(index - 1);
    } else {
      tmpResult = new Object();
    }

    int rowElementId = -1;
    try {
      rowElementId = Integer.parseInt(ObjectUtils.getParam(tmpResult, "id"));
    } catch (Exception e) {
      //supposed to be an int
    }
    if (editable) {
      return ("<input type=\"hidden\" name=\"" + hiddenElementName + "\" value=\"" + rowElementId + "\">\n<input type=\"text\" name=\"" + textElementName + "\" " + (maxlength.equals(
          "") ? "" : "maxlength=\"" + maxlength + "\" ") +
          (size.equals("") ? "" : "size=\"" + size + "\" ") + " value=\"" + StringUtils.toHtmlValue(
              ObjectUtils.getParam(tmpResult, "description")) + "\"> ");
    } else {
      return (StringUtils.toHtmlValue(
          ObjectUtils.getParam(tmpResult, "description")));
    }
  }


  /**
   * Gets the HtmlElement attribute of the CustomField object when drawing
   * forms.
   *
   * @return The HtmlElement value
   */
  public String getHtmlElement(SystemStatus thisSystem) {
    String elementName = "";
    if (id > -1) {
      elementName = "cf" + id;
    } else {
      //Not tested
      elementName = name;
    }

    String language = System.getProperty("LANGUAGE");
    String country = System.getProperty("COUNTRY");

    switch (type) {
      case TEXTAREA:
        return ("<textarea cols=\"50\" rows=\"4\" name=\"" + elementName + "\">" + StringUtils.toString(
            enteredValue) + "</textarea>");
      case HTMLAREA:
        return ("<textarea cols=\"50\" rows=\"4\" name=\"" + elementName + "\">" + StringUtils.fromHtmlValue(
            enteredValue) + "</textarea>");
      case SELECT:
        if (!(((LookupList) elementData).containsKey(-1))) {
          if (thisSystem != null) {
            ((LookupList) elementData).addItem(
                -1, thisSystem.getLabel("calendar.none.4dashes"));
          } else {
            ((LookupList) elementData).addItem(-1, "-- None --");
          }
        }
        LookupList tmpList = (LookupList) elementData;
        tmpList.setJsEvent(
            this.getOnChange() != null ? " onchange=\"" + this.getOnChange() + "\"" : "");
        return tmpList.getHtmlSelect(elementName, selectedItemId);
      case BUTTON:
        return ("<input type=\"button\" name=\"" + elementName + "\" value=\"" + display + "\" " + (jsEvent != null ? " onClick=\"" + jsEvent + "\"" : "") + (enabled ? "" : " disabled") + ">");
      case CHECKBOX:
        return ("<input type=\"checkbox\" name=\"" + elementName + "\" value=\"ON\" " + (selectedItemId == 1 ? "checked" : "") + ">");
      case DATE:
        return ("<input type=\"text\" name=\"" + elementName + "\" size=\"10\" value=\"" + StringUtils.toHtmlValue(
            enteredValue) + "\"> " +
            "<a href=\"javascript:popCalendar('details', '" + elementName + "','" + language + "','" + country + "');\">" +
            "<img src=\"images/icons/stock_form-date-field-16.gif\" " +
            "border=\"0\" align=\"absmiddle\" height=\"16\" width=\"16\"/></a>");
      case PERCENT:
        return ("<input type=\"text\" name=\"" + elementName + "\" size=\"8\" value=\"" + StringUtils.toHtmlValue(
            enteredValue) + "\"> " + "%");
      case HIDDEN:
        return ("<input type=\"hidden\" name=\"" + elementName + "\" value=\"" + StringUtils.toHtmlValue(
            enteredValue) + "\">");
      case DISPLAYTEXT:
        return (StringUtils.toHtmlValue(enteredValue));
      case LABEL:
        return this.getDisplayHtml();
      case LINK:
        return ("<a href=\"" + jsEvent + "\" >" + display + "</a>");
      case STATE_SELECT:
        StateSelect stateSelect = new StateSelect(thisSystem,"UNITED STATES");
        return (stateSelect.getHtmlSelect(elementName, "UNITED STATES", StringUtils.toHtmlValue(enteredValue)));
      default:
        String maxlength = this.getParameter("maxlength");
        String size = "";
        if (!maxlength.equals("")) {
          if (Integer.parseInt(maxlength) > 40) {
            size = "40";
          } else {
            size = maxlength;
          }
        }
        return ("<input type=\"text\" " +
            "name=\"" + elementName + "\" " +
            (maxlength.equals("") ? "" : "maxlength=\"" + maxlength + "\" ") +
            (size.equals("") ? "" : "size=\"" + size + "\" ") +
            "value=\"" + StringUtils.toHtmlValue(enteredValue) + "\">");
    }
  }


  /**
   * Gets the HtmlSelect attribute of the CustomField object
   *
   * @param selectName Description of Parameter
   * @return The HtmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return this.getHtmlSelect(selectName, null);
  }


  /**
   * Gets the HtmlSelect attribute of the CustomField object to display a list
   * of available custom field data types.
   *
   * @param selectName Description of Parameter
   * @param jsEvent    Description of Parameter
   * @return The HtmlSelect value
   */
  public String getHtmlSelect(String selectName, String jsEvent) {
    HtmlSelect dataTypes = new HtmlSelect();
    dataTypes.addItem(TEXT, getTypeString(TEXT, false));
    dataTypes.addItem(TEXTAREA, getTypeString(TEXTAREA, false));
    dataTypes.addItem(SELECT, getTypeString(SELECT, false));
    dataTypes.addItem(DATE, getTypeString(DATE, false));
    dataTypes.addItem(INTEGER, getTypeString(INTEGER, false));
    dataTypes.addItem(FLOAT, getTypeString(FLOAT, false));
    dataTypes.addItem(PERCENT, getTypeString(PERCENT, false));
    dataTypes.addItem(CURRENCY, getTypeString(CURRENCY, false));
    dataTypes.addItem(CHECKBOX, getTypeString(CHECKBOX, false));
    dataTypes.addItem(EMAIL, getTypeString(EMAIL, false));
    dataTypes.addItem(URL, getTypeString(URL, false));
    dataTypes.addItem(PHONE, getTypeString(PHONE, false));
    dataTypes.addItem(STATE_SELECT, getTypeString(STATE_SELECT, false));
    if (type == -1) {
      type = TEXT;
    }
    dataTypes.setJsEvent(jsEvent);
    return dataTypes.getHtml(selectName, type);
  }


  /**
   * Gets the TypeString attribute of the CustomField class
   *
   * @param dataType Description of Parameter
   * @param dynamic  Description of Parameter
   * @return The TypeString value
   */
  public String getTypeString(int dataType, boolean dynamic) {
    switch (dataType) {
      case TEXT:
        if ((getParameter("maxlength") == null || getParameter("maxlength").equals(
            "")) || !dynamic) {
          return "Text (up to 255 characters)";
        } else {
          return "Text (" + getParameter("maxlength") + ")";
        }
      case TEXTAREA:
        return "Text Area (unlimited)";
      case SELECT:
        return "Lookup List";
      case CHECKBOX:
        return "Check Box";
      case DATE:
        return "Date";
      case INTEGER:
        return "Number";
      case FLOAT:
        return "Decimal Number";
      case PERCENT:
        return "Percent";
      case CURRENCY:
        return "Currency";
      case EMAIL:
        return "Email Address";
      case URL:
        return "URL";
      case PHONE:
        return "Phone Number";
      case STATE_SELECT:
        return "US State List";
      default:
        return "";
    }
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasDisplay() {
    return (display != null && !"".equals(display));
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildResources(Connection db) throws SQLException {
    if (recordId > -1) {
      PreparedStatement pst = db.prepareStatement(
          "SELECT * " +
          "FROM custom_field_data " +
          "WHERE record_id = ? " +
          "AND field_id = ? ");
      pst.setInt(1, this.recordId);
      pst.setInt(2, this.id);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        buildPopulatedRecord(rs);
      }
      rs.close();
      pst.close();
    }
    buildElementData(db);
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @throws SQLException Description of Exception
   */
  public void buildElementData(Connection db) throws SQLException {
    if (type == SELECT) {
      elementData = new LookupList(db, "custom_field_lookup", id);
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int insert(Connection db) throws SQLException {
    int result = 1;
    if (!this.isValid()) {
      return -1;
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO custom_field_data " +
        "(record_id, field_id, selected_item_id, entered_value, entered_number, ");
    sql.append("entered_float ) ");
    sql.append("VALUES (?, ?, ?, ?, ?, ");
    sql.append("?) ");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, recordId);
    pst.setInt(++i, id);
    pst.setInt(++i, selectedItemId);
    pst.setString(++i, enteredValue);
    pst.setInt(++i, enteredNumber);
    pst.setDouble(++i, enteredDouble);
    pst.execute();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int updateLevel(Connection db) throws SQLException {
    if (id < 0) {
      return 0;
    }
    int result = 1;
    String sql =
        "UPDATE custom_field_info " +
        "SET \"level\" = ?, group_id = ? " +
        "WHERE field_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setInt(++i, level);
    pst.setInt(++i, groupId);
    pst.setInt(++i, id);
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insertField(Connection db) throws SQLException {
    boolean result = false;

    try {
      db.setAutoCommit(false);
      id = DatabaseUtils.getNextSeq(db, "custom_field_info_field_id_seq");
      String sql = "INSERT INTO custom_field_info " +
          "(" + (id > -1 ? "field_id, " : "") + "group_id, field_name, field_type, required, parameters, additional_text ) " +
          "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?, ?) ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, groupId);
      pst.setString(++i, name);
      pst.setInt(++i, type);
      pst.setBoolean(++i, required);
      pst.setString(++i, this.getParameterData());
      pst.setString(++i, additionalText);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "custom_field_info_field_id_seq", id);
      if (type == SELECT && elementData != null && elementData instanceof LookupList) {
        insertLookupList(db);
      }
      db.commit();
      result = true;
    } catch (SQLException e) {
      result = false;
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insertLookupList(Connection db) throws SQLException {
    if (elementData == null || !(elementData instanceof LookupList)) {
      return false;
    }
    Iterator lookupItems = ((LookupList) elementData).iterator();
    while (lookupItems.hasNext()) {
      LookupElement thisElement = (LookupElement) lookupItems.next();
      thisElement.setTableName("custom_field_lookup");
      thisElement.setFieldId(id);
      thisElement.setDefaultItem(false);
      thisElement.setEnabled(true);
      thisElement.insert(db);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean updateField(Connection db) throws SQLException {
    if (id == -1) {
      return false;
    }

    String sql =
        "UPDATE custom_field_info " +
        "SET field_name = ?, field_type = ?, required = ?, parameters = ?, " +
        "additional_text = ? " +
        "WHERE group_id = ? " +
        "AND field_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, this.getName());
    pst.setInt(++i, type);
    pst.setBoolean(++i, required);
    pst.setString(++i, this.getParameterData());
    pst.setString(++i, additionalText);
    pst.setInt(++i, this.getGroupId());
    pst.setInt(++i, this.getId());
    pst.execute();
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomField-> Updating CustomField");
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("           -> Name: " + this.getName());
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("           -> Type: " + this.getTypeString());
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "           -> Parameters: " + this.getParameterData());
    }
    pst.close();

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean deleteField(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "CustomField-> deleteField: " + id + " group: " + groupId);
    }

    boolean result = false;
    if (groupId == -1 || id == -1) {
      return result;
    }

    try {
      db.setAutoCommit(false);

      //Delete the field from any records
      String sql =
          "DELETE FROM custom_field_data " +
          "WHERE field_id = ? ";
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(1, id);
      pst.execute();
      pst.close();

      //Delete the Lookup table
      if (type == SELECT) {
        sql =
            "DELETE FROM custom_field_lookup " +
            "WHERE field_id = ? ";
        pst = db.prepareStatement(sql);
        pst.setInt(1, id);
        pst.execute();
        pst.close();
      }

      //Delete the custom field
      sql =
          "DELETE FROM custom_field_info " +
          "WHERE field_id = ? ";
      pst = db.prepareStatement(sql);
      pst.setInt(1, id);
      pst.execute();
      pst.close();
      db.commit();
      result = true;
    } catch (SQLException e) {
      result = false;
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   * Gets the Valid attribute of the CustomField object
   *
   * @return The Valid value
   */
  private boolean isValid() {
    if (recordId == -1) {
      error = "Form record id error";
    }
    if (type == -1) {
      error = "Form field type error";
    }

    //Required Fields
    if (this.getRequired() && (this.getEnteredValue() == null || this.getEnteredValue().equals(
        ""))) {
      error = this.getName() + ": Required field";
    }
    if (type == SELECT && this.getRequired() && this.getSelectedItemId() == -1) {
      error = "Required field";
    }

    //Type mis-match
    if ((error == null || error.equals("")) &&
        (this.getEnteredValue() != null && !this.getEnteredValue().equals(""))) {
      if (type == INTEGER) {
        try {
          int testNumber = Integer.parseInt(this.getEnteredValue());
          enteredNumber = testNumber;
          enteredDouble = Double.parseDouble("" + testNumber);
        } catch (Exception e) {
          error = "Value should be a whole number";
        }
      }

      if (type == FLOAT) {
        try {
          double testNumber = Double.parseDouble(this.getEnteredValue());
          enteredDouble = testNumber;
        } catch (Exception e) {
          error = "Value should be a number";
        }
      }

      if (type == PERCENT) {
        try {
          double testNumber = Double.parseDouble(this.getEnteredValue());
          enteredDouble = testNumber;
        } catch (Exception e) {
          error = "Value should be a number";
        }
      }

      if (type == CURRENCY) {
        try {
          String testString = StringUtils.replace(
              this.getEnteredValue(), ",", "");
          testString = StringUtils.replace(testString, "$", "");
          double testNumber = Double.parseDouble(testString);
          this.setEnteredValue(testString);
          enteredDouble = testNumber;
          /*
          Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
          NumberFormat nf = NumberFormat.getInstance(locale);
          enteredDouble = nf.parse(this.getEnteredValue()).doubleValue();
          Double tmpDouble = new Double(enteredDouble);
          this.setEnteredValue(tmpDouble.toString());
          */
        } catch (Exception e) {
          error = "Value should be a number";
        }
      }

      if (type == DATE) {
        // a temporary and insufficient fix for dates in custom fields
        try {
          /*
          Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
          DateFormat localeFormatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
          localeFormatter.setLenient(false);
          localeFormatter.parse(this.getEnteredValue());
          */
          String[] sep = null;
          if (this.getEnteredValue().indexOf("/") != -1) {
            sep = this.getEnteredValue().split("/");
          } else if (this.getEnteredValue().indexOf("-") != -1) {
            sep = this.getEnteredValue().split("-");
          } else if (this.getEnteredValue().indexOf(".") != -1) {
            sep = this.getEnteredValue().split(".");
          }
          if (sep == null) {
            throw new java.text.ParseException("invalid date", 0);
          } else if (sep.length != 3) {
            throw new java.text.ParseException("invalid date", 0);
          } else {
            int md1 = -1;
            int md2 = -1;
            int year = -1;
            md1 = Integer.parseInt(sep[0]);
            md2 = Integer.parseInt(sep[1]);
            year = Integer.parseInt(sep[2]);
            //This check is not suitable if locale is ja_JP (i.e., Japan)
            //The only check that is made is to ensure that all entries are numbers
            /*
            if ((md1 <= 0 || md1 > 31) ||
                (md2 <= 0 || md2 > 31) ||
                (year < 0 || (year > 99 && year < 999) || year > 2200)) {
                  throw new java.text.ParseException("invalid date", 0);
            }
            */
          }
        } catch (java.text.ParseException e) {
          error = "Value should be a valid date";
        }
      }

      if (type == EMAIL) {
        if ((this.getEnteredValue().indexOf("@") < 1) ||
            (this.getEnteredValue().indexOf(" ") > -1) ||
            (this.getEnteredValue().indexOf(".") < 0)) {
          error = "Email address format error";
        }
      }

      if (type == URL) {
        if (this.getEnteredValue().indexOf(".") < 0) {
          error = "URL format error";
        }
      }
    }

    if (System.getProperty("DEBUG") != null && error != null) {
      System.out.println("CustomField-> isValid Error: " + error);
    }

    return (error == null);
  }


  /**
   * Gets the ParameterData attribute of the CustomField object
   *
   * @return The ParameterData value
   */
  public String getParameterData() {
    StringBuffer parameterData = new StringBuffer();
    Iterator params = (parameters.keySet()).iterator();
    while (params.hasNext()) {
      String key = (String) params.next();
      parameterData.append(key + "|" + (String) parameters.get(key));
      if (params.hasNext()) {
        parameterData.append("^");
      }
    }
    return parameterData.toString();
  }


  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    //custom_field_info table
    groupId = rs.getInt("group_id");
    id = rs.getInt("field_id");
    name = rs.getString("field_name");
    level = rs.getInt("level");
    type = rs.getInt("field_type");
    validationType = rs.getInt("validation_type");
    required = rs.getBoolean("required");
    String param = rs.getString("parameters");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    entered = rs.getTimestamp("entered");
    modified = entered;
    enabled = rs.getBoolean("enabled");
    additionalText = rs.getString("additional_text");

    this.setParameters(param);
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("CustomField::groupId "+groupId);
    str.append("\nCustomField::id "+id);
    str.append("\nCustomField::name "+name);
    str.append("\nCustomField::level "+level);
    str.append("\nCustomField::enteredValue "+enteredValue);
    str.append("\nCustomField::enteredNumber "+enteredNumber);
    str.append("\nCustomField::enteredDouble "+enteredDouble);
    str.append("\nCustomField::selectedItemId "+selectedItemId);
    str.append("\nCustomField::type "+type);
    str.append("\nCustomField::validationType "+validationType);
    str.append("\nCustomField::required "+required);
    str.append("\nCustomField::startDate "+startDate);
    str.append("\nCustomField::endDate "+endDate);
    str.append("\nCustomField::entered "+entered);
    str.append("\nCustomField::enabled "+enabled);
    str.append("\nCustomField::additionalText "+additionalText);
    return str.toString();
  }

  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   * @since 1.1
   */
  private void buildPopulatedRecord(ResultSet rs) throws SQLException {
    selectedItemId = rs.getInt("selected_item_id");
    enteredValue = rs.getString("entered_value");
  }


  /**
   * Description of the Method
   *
   * @param db   Description of the Parameter
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static int locateIdByName(Connection db, String name) throws SQLException {
    int id = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT field_id " +
        "FROM custom_field_info cf " +
        "WHERE cf.field_name = ? ");
    pst.setString(1, name);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      id = rs.getInt("field_id");
    }
    rs.close();
    pst.close();
    return id;
  }

  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   */
  public void parseTemplateText(SystemStatus thisSystem) {
    if (display != null) {
      Template template = new Template(display);
      template.populateSystemVariables(thisSystem);
      display = template.getParsedText();
    }

    if (additionalText != null) {
      Template template = new Template(additionalText);
      template.populateSystemVariables(thisSystem);
      additionalText = template.getParsedText();
    }
  }
}

