package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  LookupTable generates an HTML SELECT dropdown or list box, optionally from a
 *  set of database properties. HtmlSelect can perform the database query.
 *
 *@author     Matt Rajkowski
 *@created    August 3, 2001
 *@version    $Id: HtmlSelect.java,v 1.19.36.1 2004/01/30 15:02:15 mrajkowski
 *      Exp $
 */
public class HtmlSelect extends ArrayList {

  protected String selectName = "";
  protected int selectSize = 1;
  protected String selectStyle = null;

  protected String firstEntry = "";
  protected String firstKey = "";

  protected String defaultKey = "";
  protected String defaultValue = "";
  protected String jsEvent = null;
  protected boolean multiple = false;

  protected int processedRowCount = 0;
  protected boolean checkboxOutput = false;

  protected StringBuffer rowList = new StringBuffer();

  protected boolean built = false;
  protected LookupList multipleSelects = null;
  protected String idName = null;
  protected HashMap attributeList = new HashMap();


  /**
   *  Constructor for an HTML select box. Creates an HTML select box or series
   *  of checkboxes from the supplied items, either added manually or from a
   *  resultset. <br>
   *  A couple of built-in lists can also be retrieved. <p>
   *
   *  Usage: <p>
   *
   *  - Construct the object <br>
   *  - Then manually add items to the list by calling addItem() <br>
   *  - Then specify a default key or value <br>
   *  - Then build the list by calling build() or build(resultset...) <br>
   *  - To get the resulting HTML, use getHtml() <p>
   *
   *  HtmlSelect stateSelect = new HtmlSelect(); <br>
   *  stateSelect.addItem("VA"); <br>
   *  stateSelect.addItem("VT"); <br>
   *  stateSelect.setDefaultKey("VT"); <br>
   *  stateSelect.setSelectName("state"); <br>
   *  stateSelect.build(); <br>
   *  context.getRequest().setAttribute("StateSelect", stateSelect); <br>
   *
   *
   *@since    1.0
   */

  public HtmlSelect() { }


  /**
   *  Constructor for the HtmlSelect object
   *
   *@param  itemsToAdd  Description of the Parameter
   */
  public HtmlSelect(ArrayList itemsToAdd) {
    Iterator i = itemsToAdd.iterator();
    while (i.hasNext()) {
      String thisItem = (String) i.next();
      this.addItem(thisItem);
    }
  }


  /**
   *  Set the name of the HTML Select element
   *
   *@param  tmp  The new SelectName value
   *@since       1.0
   */
  public void setSelectName(String tmp) {
    this.selectName = tmp;
  }


  /**
   *  Set the default value for the HTML Select, by value name
   *
   *@param  tmp  The new DefaultValue value
   *@since       1.0
   */
  public void setDefaultValue(String tmp) {
    if (tmp == null) {
      this.defaultValue = "";
    } else {
      this.defaultValue = tmp;
    }
  }


  /**
   *  Sets the Multiple attribute of the HtmlSelect object
   *
   *@param  multiple  The new multiple value
   *@since
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }


  /**
   *  Set the default value for the HTML Select, by value name
   *
   *@param  tmp  The new DefaultValue value
   *@since       1.0
   */
  public void setDefaultValue(int tmp) {
    this.defaultValue = "" + tmp;
  }


  /**
   *  Set the default value for the HTML Select, by key name
   *
   *@param  tmp  The new DefaultKey value
   *@since       1.0
   */
  public void setDefaultKey(String tmp) {
    if (tmp != null) {
      if (!tmp.equals(defaultKey)) {
        built = false;
      }
      if (defaultKey != null) {
        this.defaultKey = tmp;
      } else {
        this.defaultKey = "";
      }
    }
  }


  /**
   *  Set the default value for the HTML Select, by key name
   *
   *@param  tmp  The new DefaultKey value
   *@since       1.0
   */
  public void setDefaultKey(int tmp) {
    setDefaultKey(String.valueOf(tmp));
  }


  /**
   *  Set the visible size of the HTML Select, default 1 for combo box
   *
   *@param  tmp  The new SelectSize value
   *@since       1.0
   */
  public void setSelectSize(int tmp) {
    this.selectSize = tmp;
  }


  /**
   *  Sets the selectStyle attribute of the HtmlSelect object
   *
   *@param  tmp  The new selectStyle value
   */
  public void setSelectStyle(String tmp) {
    this.selectStyle = tmp;
  }


  /**
   *  Gets the selectSize attribute of the HtmlSelect object
   *
   *@return    The selectSize value
   */
  public int getSelectSize() {
    return selectSize;
  }


  /**
   *  Sets the built attribute of the HtmlSelect object
   *
   *@param  built  The new built value
   */
  public void setBuilt(boolean built) {
    this.built = built;
  }


  /**
   *  Gets the built attribute of the HtmlSelect object
   *
   *@return    The built value
   */
  public boolean getBuilt() {
    return built;
  }


  /**
   *  Gets the idName attribute of the HtmlSelect object
   *
   *@return    The idName value
   */
  public String getIdName() {
    return idName;
  }


  /**
   *  Sets the idName attribute of the HtmlSelect object
   *
   *@param  idName  The new idName value
   */
  public void setIdName(String idName) {
    this.idName = idName;
  }


  /**
   *  Set a manual entry that appears first in the list, like Any or None, etc.
   *
   *@param  tmp  The new FirstEntry value
   *@since       1.0
   */
  public void setFirstEntry(String tmp) {
    this.firstEntry = tmp;
  }


  /**
   *  Sets the FirstEntry attribute of the HtmlSelect object
   *
   *@param  tmp1  The new FirstEntry value
   *@param  tmp2  The new FirstEntry value
   *@since        1.0
   */
  public void setFirstEntry(String tmp1, String tmp2) {
    this.firstKey = tmp1;
    this.firstEntry = tmp2;
  }


  /**
   *  Sets the CheckboxOutput attribute of the HtmlSelect object
   *
   *@param  tmp  The new CheckboxOutput value
   *@since       1.0
   */
  public void setCheckboxOutput(boolean tmp) {
    checkboxOutput = tmp;
  }


  /**
   *  Pre-programmed lookup types that can be used
   *
   *@since    1.0
   */
  public void setTypeHours() {
    this.addItem("12");
    this.addItem("1");
    this.addItem("2");
    this.addItem("3");
    this.addItem("4");
    this.addItem("5");
    this.addItem("6");
    this.addItem("7");
    this.addItem("8");
    this.addItem("9");
    this.addItem("10");
    this.addItem("11");
  }


  /**
   *  Sets the TypeLocale attribute of the HtmlSelect object
   *
   *@since
   */
  public void setTypeLocale() {
    Locale[] locales;
    locales = Locale.getAvailableLocales();
    for (int i = 0; i < locales.length; i++) {
      if (locales[i].getCountry().length() > 0) {
        this.addItem(i, locales[i].getDisplayName());
      }
    }
  }


  /**
   *  Gets the multipleSelects attribute of the HtmlSelect object
   *
   *@return    The multipleSelects value
   */
  public LookupList getMultipleSelects() {
    return multipleSelects;
  }


  /**
   *  Sets the multipleSelects attribute of the HtmlSelect object
   *
   *@param  multipleSelects  The new multipleSelects value
   */
  public void setMultipleSelects(LookupList multipleSelects) {
    this.multipleSelects = multipleSelects;
  }


  /**
   *  Sets the TypeTimeZone attribute of the HtmlSelect object
   *
   *@since
   */
  public void setTypeTimeZone() {
    String[] timeZones;
    timeZones = TimeZone.getAvailableIDs();
    for (int i = 0; i < timeZones.length; i++) {
      TimeZone tz = TimeZone.getTimeZone(timeZones[i]);
      int rawOffset = tz.getRawOffset();
      double gmt = (rawOffset / 1000 / 60 / 60);
      int gmtInt = (rawOffset / 1000 / 60 / 60);

      if (gmt != Math.round(gmt)) {
        System.out.println("HtmlSelect-> GMT Offset: " + gmt);
      }
      int fraction = 0;
      if (gmt != gmtInt) {
        fraction = (int) ((Math.abs(gmt) - Math.abs(gmtInt)) * 60);
      }

      String gmtId = tz.getID();
      String gmtString = "(GMT" + (gmtInt < 0 ? "-" : "+") + (Math.abs(gmtInt) > 9 ? "" : "0") + Math.abs(gmtInt) + ":" + (fraction != 0 ? "" + fraction : "00") + ") ";
      String timeZone = gmtString + tz.getDisplayName();
      if (!tz.getDisplayName().startsWith("GMT")) {
        this.addItem(gmtId, timeZone + " (" + gmtId + ")");
      }
    }
  }


  /**
   *  Sets the TypeMinutesQuarterly attribute of the HtmlSelect object
   *
   *@since    1.0
   */
  public void setTypeMinutesQuarterly() {
    this.addItem("00");
    this.addItem("15");
    this.addItem("30");
    this.addItem("45");
  }


  /**
   *  Sets the TypeAMPM attribute of the HtmlSelect object
   *
   *@since    1.0
   */
  public void setTypeAMPM() {
    this.addItem("AM");
    this.addItem("PM");
  }


  /**
   *  Sets the typeUSMonths attribute of the HtmlSelect object
   */
  public void setTypeUSMonths() {
    this.addItem(1, "January");
    this.addItem(2, "February");
    this.addItem(3, "March");
    this.addItem(4, "April");
    this.addItem(5, "May");
    this.addItem(6, "June");
    this.addItem(7, "July");
    this.addItem(8, "August");
    this.addItem(9, "September");
    this.addItem(10, "October");
    this.addItem(11, "November");
    this.addItem(12, "December");
  }


  /**
   *  Sets the typeYears attribute of the HtmlSelect object
   *
   *@param  bottomLimitYear  The new typeYears value
   */
  public void setTypeYears(int bottomLimitYear) {
    java.util.Date d = new java.util.Date();
    java.util.Calendar iteratorDate = java.util.Calendar.getInstance();
    int counter = iteratorDate.get(java.util.Calendar.YEAR);
    while (counter >= bottomLimitYear) {
      this.addItem("" + counter);
      counter--;
    }
    this.setDefaultValue("" + iteratorDate.get(java.util.Calendar.YEAR));
  }


  /**
   *  Sets the JsEvent attribute of the HtmlSelect object
   *
   *@param  tmp  The new JsEvent value
   *@since       1.7
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Sets the attribute attribute of the HtmlSelect object
   *
   *@param  attrName   The new attribute value
   *@param  attrValue  The new attribute value
   */
  public void addAttribute(String attrName, String attrValue) {
    attributeList.put(attrName, attrValue);
  }


  /**
   *  Gets the Multiple attribute of the HtmlSelect object
   *
   *@return    The Mutiple value
   *@since
   */
  public boolean getMultiple() {
    return multiple;
  }


  /**
   *  Gets the attributeList attribute of the HtmlSelect object
   *
   *@return    The attributeList value
   */
  public String getAttributeList() {
    StringBuffer tmpList = new StringBuffer();
    Set s = attributeList.keySet();
    Iterator i = s.iterator();
    tmpList.append(" ");
    while (i.hasNext()) {
      Object name = i.next();
      tmpList.append(name.toString() + "=\"" + attributeList.get(name).toString() + "\" ");
    }
    return tmpList.toString();
  }


  /**
   *  Returns the HTML list box with the specified name, must call build() first
   *
   *@param  thisSelectName  Description of Parameter
   *@return                 The HTML value
   *@since                  1.0
   */
  public String getHtml(String thisSelectName) {
    selectName = thisSelectName;
    if (!built) {
      build();
    }
    return getHtml();
  }


  /**
   *  Returns the HTML list box with the specified name, defaulting to the
   *  specified default value, must call build() first
   *
   *@param  thisSelectName    Description of Parameter
   *@param  thisDefaultValue  Description of Parameter
   *@return                   The HTML value
   *@since                    1.0
   */
  public String getHtml(String thisSelectName, String thisDefaultValue) {
    selectName = thisSelectName;
    setDefaultValue(thisDefaultValue);
    if (!built) {
      build();
    }
    return getHtml();
  }


  /**
   *  Returns the HTML list box with the specified name, defaulting to the
   *  specified key value, must call build() first
   *
   *@param  thisSelectName  Description of Parameter
   *@param  thisDefaultKey  Description of Parameter
   *@return                 The HTML value
   *@since                  1.0
   */
  public String getHtml(String thisSelectName, int thisDefaultKey) {
    selectName = thisSelectName;
    setDefaultKey(thisDefaultKey);
    if (!built) {
      build();
    }
    return getHtml();
  }


  /**
   *  Returns the HTML list box, must call build() first
   *
   *@return    The Html value
   *@since     1.0
   */
  public String getHtml() {
    //Build the html for the results
    if (!built) {
      build();
    }
    StringBuffer outputHtml = new StringBuffer();
    if (!checkboxOutput) {
      outputHtml.append(
          "<select size='" + this.selectSize + "' " +
          (selectStyle == null ? "" : "style='" + selectStyle + "' ") +
          "name='" + this.selectName + "'" +
          (jsEvent != null ? " " + this.jsEvent : "") +
          (idName != null ? " " + "id='" + this.idName + "' " : "") +
          (multiple != false ? " multiple " : "") +
          this.getAttributeList() + ">");
    }

    //Process the rows
    outputHtml.append(rowList.toString());

    if (!checkboxOutput) {
      outputHtml.append("</select>");
    }

    return (outputHtml.toString());
  }


  /**
   *  Gets the JsEvent attribute of the HtmlSelect object
   *
   *@return    The JsEvent value
   *@since     1.7
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   *@since     1.7
   */
  public String toString() {
    return this.getHtml();
  }


  /**
   *  Adds a feature to the Group attribute of the HtmlSelect object
   *
   *@param  category  The feature to be added to the Group attribute
   */
  protected void addGroup(String category) {
    HtmlOption thisGroup = new HtmlOption();
    thisGroup.setGroup(true);
    thisGroup.setText(category);
    this.add(thisGroup);
  }


  /**
   *  Adds a feature to the Item attribute of the HtmlSelect object
   *
   *@param  tmp1      The feature to be added to the Item attribute
   *@param  tmp2      The feature to be added to the Item attribute
   *@param  position  The feature to be added to the Item attribute
   */
  protected void addItem(String tmp1, String tmp2, int position) {
    this.add(position, new HtmlOption(tmp1, tmp2));
  }


  /**
   *  Adds an option at a particular position with specified attributes
   *
   *@param  tmp1        The feature to be added to the Item attribute
   *@param  tmp2        The feature to be added to the Item attribute
   *@param  attributes  The feature to be added to the Item attribute
   */
  public void addItem(int tmp1, String tmp2, HashMap attributes) {
    this.add(new HtmlOption(String.valueOf(tmp1), tmp2, attributes));
  }


  /**
   *  Add a string to the HTML select, supplying two strings, the first String
   *  is the value, the second String is the display name
   *
   *@param  tmp1  The feature to be added to the Item attribute
   *@param  tmp2  The feature to be added to the Item attribute
   *@since        1.0
   */
  public void addItem(String tmp1, String tmp2) {
    this.add(new HtmlOption(String.valueOf(tmp1), tmp2));
  }


  /**
   *  Add a string to the HTML select, supplying one string that will be used as
   *  both the value and display name
   *
   *@param  tmp  The feature to be added to the Item attribute
   *@since       1.0
   */
  public void addItem(String tmp) {
    this.add(new HtmlOption(String.valueOf(tmp), String.valueOf(tmp)));
  }


  /**
   *  Add a string to the HTML select, supplying 1 int and 1 String, the first
   *  int is the value, the second String is the display name
   *
   *@param  tmp1  The feature to be added to the Item attribute
   *@param  tmp2  The feature to be added to the Item attribute
   *@since        1.0
   */
  public void addItem(int tmp1, String tmp2) {
    this.add(new HtmlOption(String.valueOf(tmp1), tmp2));
  }


  /**
   *  Adds a feature to the Item attribute of the HtmlSelect object
   *
   *@param  tmp1      The feature to be added to the Item attribute
   *@param  tmp2      The feature to be added to the Item attribute
   *@param  position  The feature to be added to the Item attribute
   */
  public void addItem(int tmp1, String tmp2, int position) {
    this.add(position, new HtmlOption(String.valueOf(tmp1), tmp2));
  }


  /**
   *  Adds a string to the Items attribute of the HtmlSelect object supplying a
   *  ResultSet along with the field names, and types to use for building the
   *  object.
   *
   *@param  rs                The feature to be added to the Items attribute
   *@param  valueField        The feature to be added to the Items attribute
   *@param  valueType         The feature to be added to the Items attribute
   *@param  displayField      The feature to be added to the Items attribute
   *@param  displayType       The feature to be added to the Items attribute
   *@exception  SQLException  Description of Exception
   *@since                    1.7
   */
  public void addItems(ResultSet rs, String valueField, String valueType, String displayField, String displayType) throws SQLException {
    while (rs.next()) {
      ++processedRowCount;
      String tmp1 = getColumn(rs, valueField, valueType);
      String tmp2 = getColumn(rs, displayField, displayType);
      addItem(tmp1, tmp2);
    }
  }


  /**
   *  Builds the HTML based on the items that were added to the rowList, and
   *  from the default entries and/or keys
   *
   *@since    1.0
   */
  public void build() {
    rowList.setLength(0);
    built = true;
    int rowSelect = -1;
    //If a default has not been set, then default the list to the first entry
    if ((this.defaultValue.equals("")) && (this.defaultKey.equals(""))) {
      rowSelect = 1;
    }
    //Check to see if an entry needs to be added before the rows are processed and whether it should be default
    if (!this.firstEntry.equals("")) {
      if (((this.defaultValue.equals("")) && (this.defaultKey.equals(""))) ||
          (this.defaultValue.equals(this.firstEntry)) ||
          (this.defaultKey.equals(this.firstKey))) {
        rowSelect = -1;
        if (!checkboxOutput) {
          rowList.append("<option selected value='" + 0 + "'>" + this.firstEntry + "</option>");
        } else {
          rowList.append("<input type='radio' checked>" + this.firstEntry + "&nbsp;");
        }
      } else {
        if (!checkboxOutput) {
          rowList.append("<option value='" + 0 + "'>" + this.firstEntry + "</option>");
        } else {
          rowList.append("<input type='radio'>" + this.firstEntry + "&nbsp;");
        }
      }
    }

    //Process a Vector
    boolean groupOpen = false;
    for (int i = 0; i < this.size(); i++) {
      ++processedRowCount;
      HtmlOption thisOption = (HtmlOption) this.get(i);
      String tmp1 = thisOption.getValue();
      String tmp2 = thisOption.getText();
      String attributes = thisOption.getAttributes();
      if (thisOption.isGroup()) {
        if (groupOpen) {
          rowList.append("</optgroup>");
        }
        rowList.append("<optgroup label=\"" + tmp2 + "\">");
      } else {
        String optionChecked = "";
        String optionSelected = "";
        if (multipleSelects != null && multipleSelects.containsKey(Integer.parseInt(tmp1))) {
          optionSelected = "selected ";
          optionChecked = " checked";
        } else if (multipleSelects == null && ((tmp2.equals(this.defaultValue)) ||
            (tmp1.equals(this.defaultValue)) ||
            (rowSelect == processedRowCount) ||
            (tmp1.equals(this.defaultKey)))) {
          optionSelected = "selected ";
          optionChecked = " checked";
        }
        //Build the option row
        if (!checkboxOutput) {
          rowList.append("<option " + optionSelected + "value='" + tmp1 + "' " + attributes + ">" + toHtml(tmp2) + "</option>");
        } else {
          rowList.append("<input type='radio'" + optionChecked + ">" + toHtml(tmp2) + "&nbsp;");
        }
      }
    }
    if (groupOpen) {
      rowList.append("</optgroup>");
    }
  }


  /**
   *  Builds an HTML select list based on a resultSet and the resultSet
   *  properties
   *
   *@param  rs            Description of Parameter
   *@param  valueField    Description of Parameter
   *@param  valueType     Description of Parameter
   *@param  displayField  Description of Parameter
   *@param  displayType   Description of Parameter
   *@since                1.0
   */
  public void build(ResultSet rs, String valueField, String valueType, String displayField, String displayType) {
    built = true;
    int rowSelect = -1;
    //If a default has not been set, then default the list to the first entry
    if ((this.defaultValue.equals("")) && (this.defaultKey.equals(""))) {
      rowSelect = 1;
    }
    //Check to see if an entry needs to be added before the rows are processed and whether it should be default
    if (!this.firstEntry.equals("")) {
      if (((this.defaultValue.equals("")) && (this.defaultKey.equals(""))) ||
          (this.defaultValue.equals(this.firstEntry)) ||
          (this.defaultKey.equals(this.firstKey))) {
        rowSelect = -1;
        if (!checkboxOutput) {
          rowList.append("<option selected value='" + 0 + "'>" + this.firstEntry + "</option>");
        } else {
          rowList.append("<input type='radio' checked>" + this.firstEntry + "&nbsp;");
        }
      } else {
        if (!checkboxOutput) {
          rowList.append("<option value='" + 0 + "'>" + this.firstEntry + "</option>");
        } else {
          rowList.append("<input type='radio'>" + this.firstEntry + "&nbsp;");
        }
      }
    }
    //Process a ResultSet
    try {
      while (rs.next()) {
        ++processedRowCount;
        //Check to see if option should be selected
        String tmp1 = getColumn(rs, valueField, valueType);
        String tmp2 = getColumn(rs, displayField, displayType);
        String optionSelected = "";
        String optionChecked = "";
        if ((tmp2.equals(this.defaultValue)) ||
            (rowSelect == processedRowCount) ||
            (tmp1.equals(this.defaultKey))) {
          optionSelected = "selected ";
          optionChecked = " checked";
        }
        //Build the option row
        if (!checkboxOutput) {
          rowList.append("<option " + optionSelected + "value='" + tmp1 + "'>" + toHtml(tmp2) + "</option>");
        } else {
          rowList.append("<input type='radio'" + optionChecked + ">" + toHtml(tmp2) + "&nbsp;");
        }
      }
    } catch (SQLException e) {
    }
  }


  /**
   *  Retrieves the requested field as a String from the resultset, fieldname,
   *  and fieldtype
   *
   *@param  thisRS         Description of Parameter
   *@param  thisField      Description of Parameter
   *@param  thisFieldType  Description of Parameter
   *@return                The Column value
   *@since                 1.0
   */
  private String getColumn(ResultSet thisRS, String thisField, String thisFieldType) {
    String returnValue = "not found";
    try {

      if (thisFieldType.toUpperCase().equals("STRING")) {
        returnValue = thisRS.getString(thisField);
      } else if (thisFieldType.toUpperCase().equals("BOOLEAN")) {
        boolean tmp = thisRS.getBoolean(thisField);
        if (tmp == false) {
          returnValue = "No";
        } else {
          returnValue = "Yes";
        }
      } else if ((thisFieldType.toUpperCase().equals("INT")) || (thisFieldType.toUpperCase().equals("INTEGER"))) {
        int tmp = thisRS.getInt(thisField);
        returnValue = "" + tmp;
      } else if (thisFieldType.toUpperCase().equals("TIMESTAMP")) {
        java.sql.Timestamp tmp = thisRS.getTimestamp(thisField);
        returnValue = tmp.toString();
      } else if (thisFieldType.toUpperCase().equals("DATE")) {
        java.sql.Date tmp = thisRS.getDate(thisField);
        returnValue = tmp.toString();
      } else {
        returnValue = "Field type not supported";
      }

    } catch (SQLException e) {
      returnValue = "Field error";
    }
    return (returnValue);
  }


  /**
   *  Base functions for this class
   *
   *@param  str  Description of Parameter
   *@param  o    Description of Parameter
   *@param  n    Description of Parameter
   *@return      Description of the Returned Value
   *@since       1.0
   */
  private static String replace(String str, String o, String n) {
    boolean all = true;
    if (str != null && o != null && o.length() > 0 && n != null) {
      StringBuffer result = null;
      int oldpos = 0;
      do {
        int pos = str.indexOf(o, oldpos);
        if (pos < 0) {
          break;
        }
        if (result == null) {
          result = new StringBuffer();
        }
        result.append(str.substring(oldpos, pos));
        result.append(n);
        pos += o.length();
        oldpos = pos;
      } while (all);
      if (oldpos == 0) {
        return str;
      } else {
        result.append(str.substring(oldpos));
        return result.toString();
      }
    } else {
      return str;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of Parameter
   *@return    Description of the Returned Value
   *@since     1.0
   */
  private static String toHtml(String s) {
    String htmlReady = s;
    htmlReady = replace(htmlReady, "\"", "&quot;");
    htmlReady = replace(htmlReady, "<", "&lt;");
    htmlReady = replace(htmlReady, ">", "&gt;");
    htmlReady = replace(htmlReady, "\r", "<br>");
    htmlReady = replace(htmlReady, "/&lt;", "<");
    htmlReady = replace(htmlReady, "/&gt;", ">");
    return (htmlReady);
  }


  /**
   *  Returns the text of the specified key
   *
   *@param  key  Description of the Parameter
   *@return      The valueFromId value
   */
  public String getValueFromId(String key) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      HtmlOption thisOption = (HtmlOption) i.next();
      if (key.equals(thisOption.getValue())) {
        return thisOption.getText();
      }
    }
    return key;
  }
}

