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
package org.aspcfs.modules.reports.base;

import com.darkhorseventures.framework.beans.GenericBean;
import net.sf.jasperreports.engine.JRParameter;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.RoleList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Represents a configurable report parameter as specified in a Jasper Report.
 * A parameter is part of a report's Criteria.
 *
 * @author matt rajkowski
 * @version $Id$
 * @created September 15, 2003
 */
public class Parameter
    extends GenericBean {

  private int id = -1;
  private int criteriaId = -1;
  private int type = -1;
  private String name = null;
  private String value = null;
  private String displayValue = null;
  private java.lang.Class valueClass = null;
  private String description = null;
  private boolean required = false;
  private boolean isForPrompting = false;
  private boolean isSystemDefined = false;

  /**
   * Constructor for the Parameter object
   */
  public Parameter() {
  }

  public Parameter(Connection db, int parameterId) throws SQLException {
    queryRecord(db, parameterId);
  }

  public void queryRecord(Connection db, int parameterId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT p.* " +
            "FROM report_criteria_parameter p " +
            "WHERE p.parameter_id = ? ");
    pst.setInt(1, parameterId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      build(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Record Not Found");
    }
  }

  /**
   * Constructor for the Parameter object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public Parameter(ResultSet rs) throws SQLException {
    build(rs);
  }

  /**
   * Sets the id attribute of the Parameter object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }

  /**
   * Sets the id attribute of the Parameter object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }

  /**
   * Sets the criteriaId attribute of the Parameter object
   *
   * @param tmp The new criteriaId value
   */
  public void setCriteriaId(int tmp) {
    this.criteriaId = tmp;
  }

  /**
   * Sets the criteriaId attribute of the Parameter object
   *
   * @param tmp The new criteriaId value
   */
  public void setCriteriaId(String tmp) {
    this.criteriaId = Integer.parseInt(tmp);
  }

  /**
   * Sets the type attribute of the Parameter object
   *
   * @param tmp The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }

  /**
   * Sets the type attribute of the Parameter object
   *
   * @param tmp The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }

  /**
   * Sets the name attribute of the Parameter object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }

  /**
   * Sets the value attribute of the Parameter object
   *
   * @param tmp The new value value
   */
  public void setValue(String tmp) {
    this.value = tmp;
  }

  /**
   * Sets the displayValue attribute of the Parameter object
   *
   * @param tmp The new displayValue value
   */
  public void setDisplayValue(String tmp) {
    this.displayValue = tmp;
  }

  /**
   * Sets the valueClass attribute of the Parameter object
   *
   * @param tmp The new valueClass value
   */
  public void setValueClass(java.lang.Class tmp) {
    this.valueClass = tmp;
  }

  /**
   * Sets the description attribute of the Parameter object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }

  /**
   * Sets the required attribute of the Parameter object
   *
   * @param tmp The new required value
   */
  public void setRequired(boolean tmp) {
    this.required = tmp;
  }

  /**
   * Sets the required attribute of the Parameter object
   *
   * @param tmp The new required value
   */
  public void setRequired(String tmp) {
    this.required = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the isForPrompting attribute of the Parameter object
   *
   * @param tmp The new isForPrompting value
   */
  public void setIsForPrompting(boolean tmp) {
    this.isForPrompting = tmp;
  }

  /**
   * Sets the isForPrompting attribute of the Parameter object
   *
   * @param tmp The new isForPrompting value
   */
  public void setIsForPrompting(String tmp) {
    this.isForPrompting = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the isSystemDefined attribute of the Parameter object
   *
   * @param tmp The new isSystemDefined value
   */
  public void setIsSystemDefined(boolean tmp) {
    this.isSystemDefined = tmp;
  }

  /**
   * Sets the isSystemDefined attribute of the Parameter object
   *
   * @param tmp The new isSystemDefined value
   */
  public void setIsSystemDefined(String tmp) {
    this.isSystemDefined = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the id attribute of the Parameter object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the criteriaId attribute of the Parameter object
   *
   * @return The criteriaId value
   */
  public int getCriteriaId() {
    return criteriaId;
  }

  /**
   * Gets the type attribute of the Parameter object
   *
   * @return The type value
   */
  public int getType() {
    return type;
  }

  /**
   * Gets the name attribute of the Parameter object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the value attribute of the Parameter object
   *
   * @return The value value
   */
  public String getValue() {
    return value;
  }

  /**
   * Gets the displayValue attribute of the Parameter object
   *
   * @return The displayValue value
   */
  public String getDisplayValue() {
    return displayValue;
  }

  /**
   * Gets the valueClass attribute of the Parameter object
   *
   * @return The valueClass value
   */
  public java.lang.Class getValueClass() {
    return valueClass;
  }

  /**
   * Gets the description attribute of the Parameter object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the displayName attribute of the Parameter object
   *
   * @return The displayName value
   */
  public String getDisplayName() {
    if (description != null && !"".equals(description)) {
      return description;
    } else {
      return name;
    }
  }

  /**
   * Translates the description based on user locale and displays the parameter
   * display label
   *
   * @param thisSystem Description of the Parameter
   * @return The displayName value
   */
  public String getDisplayName(SystemStatus thisSystem) {
    if (description != null && !"".equals(description)) {
      return thisSystem.getLabel(description);
    } else {
      return name;
    }
  }

  /**
   * Gets the required attribute of the Parameter object
   *
   * @return The required value
   */
  public boolean getRequired() {
    return required;
  }

  /**
   * Gets the isForPrompting attribute of the Parameter object
   *
   * @return The isForPrompting value
   */
  public boolean getIsForPrompting() {
    return isForPrompting;
  }

  /**
   * Gets the isSystemDefined attribute of the Parameter object
   *
   * @return The isSystemDefined value
   */
  public boolean getIsSystemDefined() {
    return isSystemDefined;
  }

  /**
   * Sets the param attribute of the Parameter object
   *
   * @param param The new param value
   */
  public void setParam(JRParameter param) {
    name = param.getName();
    value = null;
    description = param.getDescription();
    required = param.isForPrompting();
    valueClass = param.getValueClass();
    isForPrompting = param.isForPrompting();
    isSystemDefined = param.isSystemDefined();
  }

  /**
   * Populates this parameter from a database record
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void build(ResultSet rs) throws SQLException {
    id = rs.getInt("parameter_id");
    criteriaId = rs.getInt("criteria_id");
    name = rs.getString("parameter");
    value = rs.getString("value");
  }

  /**
   * Gets the valid attribute of the Parameter object
   *
   * @return The valid value
   */
  public boolean isValid() {
    if (criteriaId == -1) {
      errors.put("criteriaIdError", "Criteria Id is required");
    }
    return !hasErrors();
  }

  /**
   * Insert this parameter into the report_criteria_parameter table so the
   * report can execute based on this parameter data
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    if (!isValid()) {
      return false;
    }
    int i = 0;
    id = DatabaseUtils.getNextSeq(
        db,
        "report_criteria_parameter_parameter_id_seq");
    StringBuffer sql = new StringBuffer();
    sql.append(
        "INSERT INTO report_criteria_parameter " +
            "(" + (id > -1 ? "parameter_id, " : "") + "criteria_id, " + DatabaseUtils.addQuotes(db, "parameter") + ", " + DatabaseUtils.addQuotes(db, "value") + ") ");
    sql.append("VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?) ");
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, criteriaId);
    pst.setString(++i, name);
    pst.setString(++i, value);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(
        db,
        "report_criteria_parameter_parameter_id_seq",
        id);
    return true;
  }

  /**
   * If this parameter requires additional data from the user, then the form
   * element is generated here, but only if the isPromptable is set.
   *
   * @param request Description of the Parameter
   * @param db      Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void prepareContext(HttpServletRequest request, Connection db) throws
      SQLException {
    if (name.startsWith("lookup_state")) {
      //Nothing, this object is static
    } else if (name.startsWith("lookup_ticket_severity") &&
        !name.endsWith("_where")) {
      //Special case because of table name
      LookupList select = new LookupList(db, "ticket_severity");
      select.addItem(-1, "Any");
      request.setAttribute(name, select);
    } else if (name.startsWith("lookup_ticket_priority") &&
        !name.endsWith("_where")) {
      //Special case because of table name
      LookupList select = new LookupList(db, "ticket_priority");
      select.addItem(-1, "Any");
      request.setAttribute(name, select);
    } else if (name.startsWith("lookup_call_result") && !name.endsWith(
        "_where")) {
      //TODO: Add call result object here

    } else if (name.startsWith("lookup_role") && !name.endsWith("_where")) {
      RoleList roles = new RoleList();
      roles.buildList(db);
      LookupList select = roles.getLookupList();
      select.addItem(-1, "Any");
      request.setAttribute(name, select);
    } else if (name.startsWith("lookup_stage") && !name.endsWith("_where")) {
      LookupList select = new LookupList(db, "lookup_stage");
      select.addItem(-1, "Any");
      request.setAttribute(name, select);
    } else if (name.startsWith("lookup_") && !name.endsWith("_where")) {
      //Perform this one last, just in case other names start with lookup_
      LookupList select = new LookupList(db, name);
      select.addItem(-1, "Any");
      request.setAttribute(name, select);
    } else if (name.startsWith("textlookup_") && !name.endsWith("_where")) {
      //Perform this one last, just in case other names start with lookup_
      LookupList select = new LookupList(db, name.substring(4));
      //select.addItem(-1, "Any");
      request.setAttribute(name, select);
    } else if (name.startsWith("boolean_") && !name.endsWith("_where")) {
      HtmlSelect select = new HtmlSelect();
      select.addItem("-1", "Any");
      select.addItem("1", "Yes");
      select.addItem("0", "No");
      request.setAttribute(name, select);
    } else if (name.startsWith("orgid")) {
      try {
        int id = Integer.parseInt(value);
        Organization org = new Organization(db, id);
        displayValue = org.getName();
      } catch (Exception e) {
        displayValue = "All";
        value = "-1";
      }
    } else if (name.startsWith("siteid")) {
      //proceed only if user does not belong to just one specific site
      if (UserUtils.getUserSiteId(request) <= -1) {
        try {
          int id = Integer.parseInt(value);
          LookupList sites = new LookupList(db, "lookup_site_id");
          displayValue = sites.getValueFromId(id);
          if ("".equals(displayValue.trim())) {
            displayValue = "All";
          }
        } catch (Exception e) {
          displayValue = "All";
          value = "-1";
        }
      } else {
        //user belongs to a specific site, then override the siteid parameter
        value = String.valueOf(UserUtils.getUserSiteId(request));
        isForPrompting = false;
      }
    } else if (name.startsWith("date_")) {
      try {
        Timestamp tmpTimestamp = DateUtils.getUserToServerDateTime(
            null,
            DateFormat.SHORT, DateFormat.LONG, value, Locale.getDefault());
        SimpleDateFormat formatter = (SimpleDateFormat)
            SimpleDateFormat.getDateInstance(
                DateFormat.SHORT, UserUtils.getUserLocale(request));
        formatter.applyPattern(
            DateUtils.get4DigitYearDateFormat(

                formatter.toPattern()));
        value = formatter.format(tmpTimestamp);
      } catch (Exception e) {
      }
    } else if (name.startsWith("range_date")) {
      HtmlSelect select = new HtmlSelect();
      select.addItem("-1", "All");
      select.addItem("0", "Today");
      select.addItem("24", "Last 24 hours");
      select.addItem("48", "Last 48 hours");
      select.addItem("7", "Last 7 days");
      select.addItem("14", "Last 14 days");
      select.addItem("30", "Last 30 days");
      select.addItem("1", "This year");
      select.addItem("10", "Custom...");
      select.setJsEvent("onChange=\"javascript:"+name+"Change('" + name + "');\"");
      request.setAttribute(name, select);
    } else if (name.startsWith("hidden_")) {
      //Nothing, this object is static, but behaviour could change in the future
    }
  }

  /**
   * If this parameter requires additional data from the user, then the custom
   * HTML input field is returned
   *
   * @param request    Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @param params     Description of the Parameter
   * @return The html value
   */
  public String getHtml(SystemStatus thisSystem, HttpServletRequest request, ParameterList params) {
    if (name.equals("userid_range_source")) {
      //User Id combo box
      return HtmlSelectRecordSource.getSelect(name, value).getHtml();
    } else if (name.startsWith("date_")) {
      //Date Field
      User user = ((UserBean) request.getSession().getAttribute("User")).
          getUserRecord();
      String language = user.getLocale().getLanguage();
      String country = user.getLocale().getCountry();

      return "<input type='text' size='10' name='" + name + "' " +
          "value='" + HTTPUtils.toHtmlValue(value) + "'/>\r\n" +
          "<a href=\"javascript:popCalendar('paramForm', '" + name + "','" +
          language + "','" + country + "');\">" +
          "<img src=\"images/icons/stock_form-date-field-16.gif\" " +
          "border=\"0\" align=\"absmiddle\" height=\"16\" width=\"16\"/></a>";
    } else if (name.startsWith("lookup_state")) {
      User user = ((UserBean) request.getSession().getAttribute("User")).getUserRecord();
      String country = user.getLocale().getCountry();
      //State/Province drop-down
      return (new StateSelect(thisSystem, country)).getHtmlSelect(name, country);
    } else if (name.startsWith("lookup_") && !name.endsWith("_where")) {
      //Lookup Lists
      LookupList select = (LookupList) request.getAttribute(name);
      if (value != null) {
        return select.getHtmlSelect(name, Integer.parseInt(value));
      } else {
        return select.getHtmlSelect(name, -1);
      }
    } else if (name.startsWith("textlookup_") && !name.endsWith("_where")) {
      //Text Field
      int defaultSize = 30;
      int maxLength = -1;
      if (name.indexOf(":") > -1) {
        maxLength = Integer.parseInt(name.substring(name.indexOf(":") + 1));
        if (maxLength > 40) {
          defaultSize = 40;
        } else {
          defaultSize = maxLength;
        }
      }
      String textBox = "<input type=\"text\" size=\"" + defaultSize + "\" " +
          (maxLength == -1 ? "" : "maxlength=\"" + maxLength + "\" ") +
          "name=\"" + name + "\" " +
          "value=\"" + HTTPUtils.toHtmlValue(value) + "\"/>";
      //Lookup Lists
      LookupList select = (LookupList) request.getAttribute(name);
      if (select == null) {
        return textBox;
      } else {
        select.setJsEvent("onClick=\"javascript:document.forms['paramForm'].elements['" + name + "'].value=this.options[this.selectedIndex].text\"");
        if (value != null) {
          return textBox + " " + select.getHtmlSelect(name + System.currentTimeMillis(), Integer.parseInt(value));
        } else {
          return textBox + " " + select.getHtmlSelect(name + System.currentTimeMillis(), -1);
        }
      }
    } else if (name.startsWith("boolean_") && !name.endsWith("_where")) {
      //Html Selects
      HtmlSelect select = (HtmlSelect) request.getAttribute(name);
      if (value != null) {
        return select.getHtml(name, Integer.parseInt(value));
      } else {
        return select.getHtml(name, -1);
      }
    } else if (name.startsWith("percent_") && !name.endsWith("_max") &&
        !name.endsWith("_min")) {
      //Percent drop-down
      return HtmlSelectProbabilityRange.getSelect(name, value).getHtml();
    } else if (name.startsWith("text_")) {
      //Text Field
      int defaultSize = 30;
      int maxLength = -1;
      if (name.indexOf(":") > -1) {
        maxLength = Integer.parseInt(name.substring(name.indexOf(":") + 1));
        if (maxLength > 40) {
          defaultSize = 40;
        } else {
          defaultSize = maxLength;
        }
      }
      return "<input type=\"text\" size=\"" + defaultSize + "\" " +
          (maxLength == -1 ? "" : "maxlength=\"" + maxLength + "\" ") +
          "name=\"" + name + "\" " +
          "value=\"" + HTTPUtils.toHtmlValue(value) + "\"/>";
    } else if (name.startsWith("number_")) {
      //Number Field
      int defaultSize = 30;
      int maxLength = -1;
      if (name.indexOf(":") > -1) {
        maxLength = Integer.parseInt(name.substring(name.indexOf(":") + 1));
        if (maxLength > 40) {
          defaultSize = 40;
        } else {
          defaultSize = maxLength;
        }
      }
      return "<input type=\"text\" size=\"" + defaultSize + "\" " +
          (maxLength == -1 ? "" : "maxlength=\"" + maxLength + "\" ") +
          "name=\"" + name + "\" " +
          "value=\"" + HTTPUtils.toHtmlValue(value) + "\"/>";
    } else if (name.startsWith("orgid")) {
      return "<div id=\"change" + name + "\" style=\"display:inline\">" +
          displayValue + "</div>" +
          "<input type=\"hidden\" name=\"" + name + "\" id=\"" + name +
          "\" value=\"" + value + "\">" +
          "&nbsp; [<a href=\"javascript:popSiteAccountsListSingle('" + name +
          "','change" + name + "','siteid" +
          "', 'showMyCompany=true&filters=all|my|disabled');\">Select</a>]" +
          "&nbsp; [<a href=\"javascript:changeDivContent('change" + name +
          "','All');javascript:resetNumericFieldValue('" + name +
          "');\">Clear</a>]";
    } else if (name.startsWith("siteid")) {
      String resetOrg = "";
      Parameter orgParam = params.getParameter("orgid");
      if (orgParam != null) {
        resetOrg += "javascript:changeDivContent('change" + orgParam.getName() + "', 'All');";
        resetOrg += "javascript:resetNumericFieldValue('" + orgParam.getName() + "');";
      }
      return "<div id=\"change" + name + "\" style=\"display:inline\">" +
          displayValue + "</div>" +
          "<input type=\"hidden\" name=\"" + name + "\" id=\"" + name +
          "\" value=\"" + value + "\">" +
          "&nbsp; [<a href=\"javascript:popLookupSelector('" + name +
          "','change" + name +
          "', 'lookup_site_id', '&listType=single');" + (orgParam != null ? resetOrg : "") + "\">Select</a>]" +
          "&nbsp; [<a href=\"javascript:changeDivContent('change" + name +
          "','All');javascript:resetNumericFieldValue('" + name +
          "');" + (orgParam != null ? resetOrg : "") + "\">Clear</a>]";
    } else if (name.startsWith("range_date")) {
      //Html Selects
      HtmlSelect select = (HtmlSelect) request.getAttribute(name);
      if (value != null) {
        return select.getHtml(name, Integer.parseInt(value));
      } else {
        return select.getHtml(name, -1);
      }
    } else if (name.startsWith("hidden_")) {
      return "<input type=\"hidden\" name=\"" + name + "\" " +
          "value=\"" + HTTPUtils.toHtmlValue(value) + "\"/>";
    }
    return "Parameter type not supported";
  }
}
