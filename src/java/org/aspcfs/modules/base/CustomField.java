//Copyright 2001 Dark Horse Ventures
//TODO: Currency entry using k, m, b for 50,000, etc.
//TODO: Phone numbers -- When you enter phone numbers in various phone fields, 
//preserve whatever phone number format you enter. 
//However, if your Locale is set to English (United States) or English (Canada), 
//ten digit phone numbers and eleven digit numbers that start with '1' are 
//automatically formatted as (800) 555-1212 when you save the record. 
//If you do not want this formatting for a ten or eleven digit number, 
//enter a '+' before the number, e.g., +49 8178 94 07-0.

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.sql.*;
import com.darkhorseventures.webutils.*;
import java.util.*;
import java.text.*;

/**
 *  Represents a CustomField, used for both the definition of a custom field and
 *  also the data included in the custom field.
 *
 *@author     mrajkowski
 *@created    December 27, 2001
 *@version    $Id$
 */
public class CustomField extends GenericBean {

  //Properties for a Field
  private int id = -1;
  private int groupId = -1;
  private String name = null;
  private int level = -1;
  private int type = -1;
  private int validationType = -1;
  private boolean required = false;
  private Hashtable parameters = new Hashtable();
  private java.sql.Timestamp startDate = null;
  private java.sql.Timestamp endDate = null;
  private java.sql.Timestamp entered = null;
  private boolean enabled = false;
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

  //Types of custom fields
  public final static int TEXT = 1;
  public final static int SELECT = 2;
  public final static int TEXTAREA = 3;
  public final static int CHECKBOX = 4;
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


  /**
   *  Constructor for the CustomField object
   *
   *@since    1.1
   */
  public CustomField() { }


  /**
   *  Constructor for the CustomField object
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public CustomField(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the CustomField object
   *
   *@param  rs                Description of Parameter
   *@param  populated         Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public CustomField(ResultSet rs, boolean populated) throws SQLException {
    if (populated) {
      buildPopulatedRecord(rs);
    }
    buildRecord(rs);
  }


  /**
   *  Sets the Id attribute of the CustomField object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Id attribute of the CustomField object
   *
   *@param  tmp  The new Id value
   *@since
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the GroupId attribute of the CustomField object
   *
   *@param  tmp  The new GroupId value
   *@since
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the GroupId attribute of the CustomField object
   *
   *@param  tmp  The new GroupId value
   *@since
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Name attribute of the CustomField object
   *
   *@param  tmp  The new Name value
   *@since
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the Level attribute of the CustomField object
   *
   *@param  tmp  The new Level value
   *@since
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the Type attribute of the CustomField object
   *
   *@param  tmp  The new Type value
   *@since
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the Type attribute of the CustomField object
   *
   *@param  tmp  The new Type value
   *@since
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ValidationType attribute of the CustomField object
   *
   *@param  tmp  The new ValidationType value
   *@since
   */
  public void setValidationType(int tmp) {
    this.validationType = tmp;
  }


  /**
   *  Sets the Required attribute of the CustomField object
   *
   *@param  tmp  The new Required value
   *@since
   */
  public void setRequired(boolean tmp) {
    this.required = tmp;
  }


  /**
   *  Sets the Required attribute of the CustomField object
   *
   *@param  tmp  The new Required value
   *@since
   */
  public void setRequired(String tmp) {
    this.required = tmp.equalsIgnoreCase("ON");
  }


  /**
   *  Sets the StartDate attribute of the CustomField object
   *
   *@param  tmp  The new StartDate value
   *@since
   */
  public void setStartDate(java.sql.Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the EndDate attribute of the CustomField object
   *
   *@param  tmp  The new EndDate value
   *@since
   */
  public void setEndDate(java.sql.Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the Entered attribute of the CustomField object
   *
   *@param  tmp  The new Entered value
   *@since
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the Enabled attribute of the CustomField object
   *
   *@param  tmp  The new Enabled value
   *@since
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the Error attribute of the CustomField object
   *
   *@param  tmp  The new Error value
   *@since
   */
  public void setError(String tmp) {
    this.error = tmp;
  }


  /**
   *  Sets the LinkModuleId attribute of the CustomField object
   *
   *@param  tmp  The new LinkModuleId value
   *@since
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the LinkItemId attribute of the CustomField object
   *
   *@param  tmp  The new LinkItemId value
   *@since
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }


  /**
   *  Sets the RecordId attribute of the CustomField object
   *
   *@param  tmp  The new RecordId value
   *@since
   */
  public void setRecordId(int tmp) {
    this.recordId = tmp;
  }


  /**
   *  Sets the SelectedItemId attribute of the CustomField object
   *
   *@param  tmp  The new SelectedItemId value
   *@since
   */
  public void setSelectedItemId(int tmp) {
    this.selectedItemId = tmp;
  }


  /**
   *  Sets the EnteredValue attribute of the CustomField object
   *
   *@param  tmp  The new EnteredValue value
   *@since
   */
  public void setEnteredValue(String tmp) {
    this.enteredValue = (tmp.trim());
  }


  /**
   *  Sets the Length attribute of the CustomField object
   *
   *@param  tmp  The new Length value
   *@since
   */
  public void setMaxLength(String tmp) {
    if (tmp != null && !tmp.equals("")) {
      parameters.put("maxlength", tmp);
    }
  }


  /**
   *  Sets the LookupList attribute of the CustomField object
   *
   *@param  tmp  The new LookupList value
   *@since
   */
  public void setLookupList(String tmp) {
    elementData = new LookupList();
    int count = 0;
    StringTokenizer st = new StringTokenizer(tmp, "\r\n");
    while (st.hasMoreTokens()) {
      String listField = st.nextToken();
      if (!listField.trim().equals("")) {
        ++count;
        LookupElement thisElement = new LookupElement();
        thisElement.setDescription(listField.trim());
        thisElement.setCode(count);
        thisElement.setLevel(count);
        thisElement.setDefaultItem(false);
        ((LookupList)elementData).add(thisElement);
      }
    }
  }


  /**
   *  Sets the Parameters attribute of the CustomField object
   *
   *@param  context  The new Parameters value
   *@since
   */
  public void setParameters(ActionContext context) {
    String newValue = context.getRequest().getParameter("cf" + id);
    if (newValue != null) {
      newValue = newValue.trim();
      switch (type) {
        case SELECT:
          selectedItemId = Integer.parseInt(newValue);
          enteredValue = ((LookupList)elementData).getSelectedValue(selectedItemId);
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
        default:
          enteredValue = newValue;
          break;
      }
    }
  }


  /**
   *  Gets the LookupList attribute of the CustomField object
   *
   *@return    The LookupList value
   *@since
   */
  public String getLookupList() {
    StringBuffer sb = new StringBuffer();
    if (elementData != null) {
      Iterator i = ((LookupList)elementData).iterator();
      while (i.hasNext()) {
        LookupElement thisElement = (LookupElement)i.next();
        sb.append(thisElement.getDescription());
        if (i.hasNext()) {
          sb.append("\r\n");
        }
      }
    }
    return sb.toString();
  }


  /**
   *  Gets the Id attribute of the CustomField object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the GroupId attribute of the CustomField object
   *
   *@return    The GroupId value
   *@since
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   *  Gets the Name attribute of the CustomField object
   *
   *@return    The Name value
   *@since
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the NameHtml attribute of the CustomField object
   *
   *@return    The NameHtml value
   *@since
   */
  public String getNameHtml() {
    return toHtml(name);
  }


  /**
   *  Gets the Level attribute of the CustomField object
   *
   *@return    The Level value
   *@since
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the Type attribute of the CustomField object
   *
   *@return    The Type value
   *@since
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the TypeString attribute of the CustomField object
   *
   *@return    The TypeString value
   *@since
   */
  public String getTypeString() {
    return getTypeString(type, true);
  }


  /**
   *  Gets the ValidationType attribute of the CustomField object
   *
   *@return    The ValidationType value
   *@since
   */
  public int getValidationType() {
    return validationType;
  }


  /**
   *  Gets the Required attribute of the CustomField object
   *
   *@return    The Required value
   *@since
   */
  public boolean getRequired() {
    return required;
  }


  /**
   *  Gets the StartDate attribute of the CustomField object
   *
   *@return    The StartDate value
   *@since
   */
  public java.sql.Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the EndDate attribute of the CustomField object
   *
   *@return    The EndDate value
   *@since
   */
  public java.sql.Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the Entered attribute of the CustomField object
   *
   *@return    The Entered value
   *@since
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the Enabled attribute of the CustomField object
   *
   *@return    The Enabled value
   *@since
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the Error attribute of the CustomField object
   *
   *@return    The Error value
   *@since
   */
  public String getError() {
    return error;
  }


  /**
   *  Gets the SelectedItemId attribute of the CustomField object
   *
   *@return    The SelectedItemId value
   *@since
   */
  public int getSelectedItemId() {
    return selectedItemId;
  }


  /**
   *  Gets the EnteredValue attribute of the CustomField object
   *
   *@return    The EnteredValue value
   *@since
   */
  public String getEnteredValue() {
    return enteredValue;
  }


  /**
   *  Gets the ElementData attribute of the CustomField object
   *
   *@return    The ElementData value
   *@since
   */
  public Object getElementData() {
    return elementData;
  }


  /**
   *  Gets the LengthRequired attribute of the CustomField object for HTML
   *  forms.
   *
   *@return    The LengthRequired value
   *@since
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
   *  Gets the LookupListRequired attribute of the CustomField object
   *
   *@return    The LookupListRequired value
   *@since
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
   *  Gets the Parameter attribute of the CustomField object
   *
   *@param  tmp  Description of Parameter
   *@return      The Parameter value
   *@since
   */
  public String getParameter(String tmp) {
    if (parameters.containsKey(tmp.toLowerCase())) {
      return (String)parameters.get(tmp.toLowerCase());
    } else {
      return "";
    }
  }


  /**
   *  Gets the Value attribute of the CustomField object
   *
   *@return    The Value value
   *@since
   */
  public String getValueHtml() {
    if (type != SELECT && (enteredValue == null || enteredValue.equals(""))) {
      return toHtml(enteredValue);
    }
    switch (type) {
      case URL:
        return "<a href=\"" + ((enteredValue.indexOf(":") > -1) ? "" : "http://") + enteredValue + "\" target=\"_new\">" + enteredValue + "</a>";
      case EMAIL:
        if (enteredValue.indexOf("@") > 0) {
          return "<a href=\"mailto:" + enteredValue + "\">" + enteredValue + "</a>";
        } else {
          return toHtml(enteredValue);
        }
      case SELECT:
        if (elementData != null) {
          return toHtml(((LookupList)elementData).getSelectedValue(selectedItemId));
        } else {
          return toHtml(enteredValue);
        }
      case CHECKBOX:
        return (selectedItemId == 1 ? "Yes" : "No");
      case CURRENCY:
        try {
          double thisAmount = Double.parseDouble(enteredValue);
          NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.US);
          return ("$" + numberFormatter.format(thisAmount));
        } catch (Exception e) {
          return ("$" + toHtml(enteredValue));
        }
      case PERCENT:
        return (toHtml(enteredValue) + "%");
      default:
        return (toHtml(enteredValue));
    }
  }


  /**
   *  Gets the HtmlElement attribute of the CustomField object when drawing
   *  forms.
   *
   *@return    The HtmlElement value
   *@since
   */
  public String getHtmlElement() {
    switch (type) {
      case TEXTAREA:
        return ("<textarea cols=\"70\" rows=\"8\" name=\"" + "cf" + id + "\">" + toString(enteredValue) + "</textarea>");
      case SELECT:
        ((LookupList)elementData).addItem(-1, "-- None --");
        return ((LookupList)elementData).getHtmlSelect("cf" + id, selectedItemId);
      case CHECKBOX:
        return ("<input type=\"checkbox\" name=\"" + "cf" + id + "\" value=\"ON\" " + (selectedItemId == 1 ? "checked" : "") + ">");
      case DATE:
        return ("<input type=\"text\" name=\"" + "cf" + id + "\" value=\"" + toHtmlValue(enteredValue) + "\"> " +
            "<a href=\"javascript:popCalendar('forms[0]', 'cf" + id + "');\">Date</a> (mm/dd/yyyy)");
      case PERCENT:
        return ("<input type=\"text\" name=\"" + "cf" + id + "\" size=\"8\" value=\"" + toHtmlValue(enteredValue) + "\"> " + "%");
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
            "name=\"" + "cf" + id + "\" " +
            (maxlength.equals("") ? "" : "maxlength=\"" + maxlength + "\" ") +
            (size.equals("") ? "" : "size=\"" + size + "\" ") +
            "value=\"" + toHtmlValue(enteredValue) + "\">");
    }
  }


  /**
   *  Gets the HtmlSelect attribute of the CustomField object
   *
   *@param  selectName  Description of Parameter
   *@return             The HtmlSelect value
   *@since
   */
  public String getHtmlSelect(String selectName) {
    return this.getHtmlSelect(selectName, null);
  }


  /**
   *  Gets the HtmlSelect attribute of the CustomField object to display a list
   *  of available custom field data types.
   *
   *@param  selectName  Description of Parameter
   *@param  jsEvent     Description of Parameter
   *@return             The HtmlSelect value
   *@since
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
    if (type == -1) {
      type = TEXT;
    }
    dataTypes.setJsEvent(jsEvent);
    return dataTypes.getHtml(selectName, type);
  }


  /**
   *  Gets the TypeString attribute of the CustomField class
   *
   *@param  dataType  Description of Parameter
   *@param  dynamic   Description of Parameter
   *@return           The TypeString value
   *@since
   */
  public String getTypeString(int dataType, boolean dynamic) {
    switch (dataType) {
      case TEXT:
        if ((getParameter("maxlength") == null || getParameter("maxlength").equals("")) || !dynamic) {
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
      default:
        return "";
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void buildResources(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomField-> buildResources");
    }
    if (recordId > -1) {
      Statement st = db.createStatement();
      ResultSet rs = st.executeQuery(
          "SELECT * " +
          "FROM custom_field_data " +
          "WHERE record_id = " + this.recordId + " " +
          "AND field_id = " + this.id + " ");
      //"AND link_module_id = " + this.linkModuleId + " " +
      //"AND link_item_id = " + this.linkItemId + " ");
      if (rs.next()) {
        buildPopulatedRecord(rs);
      }
      rs.close();
      st.close();
    }

    buildElementData(db);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since
   */
  public void buildElementData(Connection db) throws SQLException {
    if (type == SELECT) {
      elementData = new LookupList(db, "custom_field_lookup", id);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public int insert(Connection db) throws SQLException {
    int result = 1;
    if (!this.isValid()) {
      return -1;
    }
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO custom_field_data " +
        "(record_id, field_id, selected_item_id, entered_value, entered_number, entered_float ) " +
        "VALUES (?, ?, ?, ?, ?, ?) ");
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
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean insertField(Connection db) throws SQLException {
    boolean result = false;
    if (!isFieldValid()) {
      return result;
    }

    try {
      db.setAutoCommit(false);
      String sql =
          "INSERT INTO custom_field_info " +
          "(group_id, field_name, field_type, required, parameters ) " +
          "VALUES (?, ?, ?, ?, ?) ";
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql);
      pst.setInt(++i, groupId);
      pst.setString(++i, name);
      pst.setInt(++i, type);
      pst.setBoolean(++i, required);
      pst.setString(++i, this.getParameterData());
      pst.execute();
      pst.close();

      if (type == SELECT) {
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery(
            "select currval('custom_field_info_field_id_seq')");
        if (rs.next()) {
          id = rs.getInt(1);
        }
        rs.close();
        st.close();

        sql =
            "INSERT INTO custom_field_lookup " +
            "(field_id, description, default_item, level, enabled) " +
            "values (?, ?, ?, ?, ? ) ";
        Iterator lookupItems = ((LookupList)elementData).iterator();
        int count = 0;
        while (lookupItems.hasNext()) {
          i = 0;
          ++count;
          LookupElement thisElement = (LookupElement)lookupItems.next();
          pst = db.prepareStatement(sql);
          pst.setInt(++i, id);
          pst.setString(++i, thisElement.getDescription());
          pst.setBoolean(++i, false);
          pst.setInt(++i, count);
          //pst.setTimestamp(++i, );
          //pst.setTimestamp(++i, );
          pst.setBoolean(++i, true);
          pst.execute();
          pst.close();
        }

      }
      db.commit();
      result = true;
    } catch (SQLException e) {
      result = false;
      db.rollback();
    }
    db.setAutoCommit(true);

    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean updateField(Connection db) throws SQLException {
    if (!isFieldValid() || id == -1) {
      return false;
    }

    String sql =
        "UPDATE custom_field_info " +
        "SET field_name = ?, field_type = ?, required = ?, parameters = ? " +
        "WHERE group_id = ? " +
        "AND field_id = ? ";
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql);
    pst.setString(++i, this.getName());
    pst.setInt(++i, type);
    pst.setBoolean(++i, required);
    pst.setString(++i, this.getParameterData());
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
      System.out.println("           -> Parameters: " + this.getParameterData());
    }
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  public boolean deleteField(Connection db) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CustomField-> deleteField: " + id + " group: " + groupId);
    }

    boolean result = false;
    if (groupId == -1 || id == -1) {
      errors.put("actionError", "Form data error");
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
    }
    db.setAutoCommit(true);

    return result;
  }


  /**
   *  Gets the FieldValid attribute of the CustomField object
   *
   *@return    The FieldValid value
   *@since
   */
  private boolean isFieldValid() {
    if (groupId == -1) {
      errors.put("actionError", "Form data is missing");
    }
    if (type == -1) {
      errors.put("typeError", "Type is required");
    }
    if (name == null || name.equals("")) {
      errors.put("nameError", "Name is required");
    }
    if (getLengthRequired()) {
      if (getParameter("maxlength").equals("")) {
        errors.put("maxLengthError", "Length is required");
      } else {
        try {
          if (Integer.parseInt(getParameter("maxlength")) > 255) {
            errors.put("maxLengthError", "Max length is too high");
          }
        } catch (Exception e) {
          errors.put("maxLengthError", "Max length must be a number");
        }
      }
    }
    if (type == SELECT &&
        (elementData == null || (((LookupList)elementData).size() == 0))
        ) {
      errors.put("lookupListError", "Items are required");
    }

    return (errors.size() == 0);
  }


  /**
   *  Gets the Valid attribute of the CustomField object
   *
   *@return    The Valid value
   *@since
   */
  private boolean isValid() {
    if (recordId == -1) {
      error = "Form record id error";
    }
    if (type == -1) {
      error = "Form field type error";
    }

    //Required Fields
    if (this.getRequired() && (this.getEnteredValue() == null || this.getEnteredValue().equals(""))) {
      error = "Required field";
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
          String testString = replace(this.getEnteredValue(), ",", "");
          double testNumber = Double.parseDouble(testString);
          this.setEnteredValue(testString);
          enteredDouble = testNumber;
        } catch (Exception e) {
          error = "Value should be a number";
        }
      }

      if (type == DATE) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        formatter.setLenient(false);
        java.util.Date testDate = new java.util.Date();
        try {
          testDate = formatter.parse(this.getEnteredValue());
          SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
          this.setEnteredValue(formatter2.format(testDate));
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
   *  Gets the ParameterData attribute of the CustomField object
   *
   *@return    The ParameterData value
   *@since
   */
  private String getParameterData() {
    StringBuffer parameterData = new StringBuffer();
    Iterator params = (parameters.keySet()).iterator();
    while (params.hasNext()) {
      String key = (String)params.next();
      parameterData.append(key + "|" + (String)parameters.get(key));
      if (params.hasNext()) {
        parameterData.append("^");
      }
    }
    return parameterData.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("field_id");
    groupId = rs.getInt("group_id");
    name = rs.getString("field_name");
    level = rs.getInt("level");
    type = rs.getInt("field_type");
    validationType = rs.getInt("validation_type");
    required = rs.getBoolean("required");
    String param = rs.getString("parameters");
    startDate = rs.getTimestamp("start_date");
    endDate = rs.getTimestamp("end_date");
    entered = rs.getTimestamp("entered");
    enabled = rs.getBoolean("enabled");

    StringTokenizer st = new StringTokenizer(param, "^");
    while (st.hasMoreTokens()) {
      StringTokenizer kv = new StringTokenizer(st.nextToken(), "|");
      if (kv.hasMoreTokens()) {
        parameters.put(kv.nextToken(), kv.nextToken());
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  private void buildPopulatedRecord(ResultSet rs) throws SQLException {
    selectedItemId = rs.getInt("selected_item_id");
    enteredValue = rs.getString("entered_value");
  }

}

