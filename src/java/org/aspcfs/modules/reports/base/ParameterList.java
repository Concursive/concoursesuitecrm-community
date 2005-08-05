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

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.HtmlSelectProbabilityRange;
import org.aspcfs.utils.web.PagedListInfo;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * A collection of Parameter objects.
 *
 * @author matt rajkowski
 * @version $Id: ParameterList.java,v 1.1.2.1 2003/09/15 20:58:21 mrajkowski
 *          Exp $
 * @created September 15, 2003
 */
public class ParameterList extends ArrayList {

  private int criteriaId = -1;
  protected PagedListInfo pagedListInfo = null;
  public HashMap errors = new HashMap();
  public SystemStatus systemStatus = null;


  /**
   * Constructor for the ParameterList object
   */
  public ParameterList() {
  }


  /**
   * Sets the criteriaId attribute of the ParameterList object
   *
   * @param tmp The new criteriaId value
   */
  public void setCriteriaId(int tmp) {
    this.criteriaId = tmp;
  }


  /**
   * Sets the criteriaId attribute of the ParameterList object
   *
   * @param tmp The new criteriaId value
   */
  public void setCriteriaId(String tmp) {
    this.criteriaId = Integer.parseInt(tmp);
  }


  /**
   * Sets the systemStatus attribute of the ParameterList object
   *
   * @param tmp The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   * Gets the systemStatus attribute of the ParameterList object
   *
   * @return The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   * Gets the criteriaId attribute of the ParameterList object
   *
   * @return The criteriaId value
   */
  public int getCriteriaId() {
    return criteriaId;
  }


  /**
   * Sets the errors attribute of the ParameterList object
   *
   * @param tmp The new errors value
   */
  public void setErrors(HashMap tmp) {
    this.errors = tmp;
  }


  /**
   * Gets the errors attribute of the ParameterList object
   *
   * @return The errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasErrors() {
    return (errors.size() > 0);
  }


  /**
   * Initially loads the parameters from the JasperReport
   *
   * @param report The new parameters value
   */
  public void setParameters(JasperReport report) {
    JRParameter[] params = report.getParameters();
    for (int i = 0; i < params.length; i++) {
      JRParameter param = params[i];
      Parameter thisParam = new Parameter();
      thisParam.setParam(param);
      if (!thisParam.getIsSystemDefined()) {
        this.add(thisParam);
      }
    }
  }


  /**
   * Takes the user input and expands the data into the corresponding jasper
   * parameters
   *
   * @param request The new parameters value
   * @return Description of the Return Value
   */
  public boolean setParameters(HttpServletRequest request) {
    Timestamp startDate = null;
    Timestamp endDate = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter param = (Parameter) i.next();
      //For each parameter the user is prompted for, evaluate the answer
      if (param.getIsForPrompting()) {
        param.setValue(request.getParameter(param.getName()));
        //Auto-populate the user's range based on selected type
        if (param.getName().equals("userid_range_source")) {
          if (param.getValue().equals("all")) {
            //TODO:"All" wouldn't work, need to swap the _where clause
            addParam("userid_range", "");
          } else if (param.getValue().equals("hierarchy")) {
            addParam("userid_range", UserUtils.getUserIdRange(request));
          } else {
            addParam(
                "userid_range", String.valueOf(UserUtils.getUserId(request)));
          }
        }
        //Lookup lists will result in -1, > -1
        //if other than -1 then use the additional WHERE clause included in
        //the report
        if (param.getName().startsWith("lookup_")) {
          Parameter whereParam = this.getParameter(param.getName() + "_where");
          if (whereParam != null) {
            if (!"-1".equals(param.getValue())) {
              //New case, replace query param with another param and parse
              Template where = new Template(whereParam.getDescription());
              where.addParseElement(
                  "$P{" + param.getName() + "}", param.getValue());
              addParam(whereParam.getName(), where.getParsedText());
            } else {
              addParam(whereParam.getName(), " ");
            }
          }
          addParam(param.getName(), param.getValue());
        }
        //HtmlSelect boolean
        if (param.getName().startsWith("boolean_")) {
          Parameter whereParam = this.getParameter(param.getName() + "_where");
          if (whereParam != null) {
            if ("1".equals(param.getValue())) {
              //Replace the default with the description value
              addParam(whereParam.getName(), whereParam.getDescription());
            } else if ("0".equals(param.getValue())) {
              //Leave the default
            } else {
              //Blank out the default
              addParam(whereParam.getName(), "");
            }
          }
          addParam(param.getName(), param.getValue());
        }
        //Percent lookup uses a range from the HtmlSelectProbabilityRange object
        if (param.getName().startsWith("percent_")) {
          //The range will be specified as -0.01|1.01 for the query
          StringTokenizer st = new StringTokenizer(param.getValue(), "|");
          addParam(param.getName() + "_min", st.nextToken());
          addParam(param.getName() + "_max", st.nextToken());
          //Clean up the name for output on the report
          param.setValue(
              HtmlSelectProbabilityRange.getValueFromId(param.getValue()));
        }
        //Integer orgid lookup
        if (param.getName().startsWith("orgid")) {
          Parameter whereParam = this.getParameter(param.getName() + "_where");
          if (whereParam != null) {
            if (!"-1".equals(param.getValue())) {
              //New case, replace query param with another param and parse
              Template where = new Template(whereParam.getDescription());
              where.addParseElement(
                  "$P{" + param.getName() + "}", param.getValue());
              addParam(whereParam.getName(), where.getParsedText());
            } else {
              addParam(whereParam.getName(), " ");
            }
          }
          addParam(param.getName(), param.getValue());
        }
        try {
          if (param.getName().startsWith("date_")) {
            Timestamp tmpTimestamp = DateUtils.getUserToServerDateTime(
                TimeZone.getTimeZone(UserUtils.getUserTimeZone(request)), DateFormat.SHORT, DateFormat.LONG, param.getValue(), UserUtils.getUserLocale(
                    request));
            SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
                DateFormat.SHORT, Locale.getDefault());
            String date = formatter.format(tmpTimestamp);
            param.setValue(date);
            if (param.getName().equals("date_start")) {
              startDate = tmpTimestamp;
            }
            if (param.getName().equals("date_end")) {
              endDate = tmpTimestamp;
            }
          }
        } catch (Exception e) {
          if (systemStatus != null) {
            errors.put(
                param.getName() + "Error", systemStatus.getLabel(
                    "object.validation.invalidInput"));
          } else {
            errors.put(param.getName() + "Error", "no input or invalid date");
          }
        }
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ParameterList-> " + param.getName() + "=" + param.getValue());
      }
    }
    if (startDate != null && endDate != null) {
      if (startDate.after(endDate)) {
        if (systemStatus != null) {
          errors.put(
              "date_startError", systemStatus.getLabel(
                  "object.validation.firstDateNotAfterSecondDate"));
        } else {
          errors.put(
              "date_startError", "The first date can not be after second date.");
        }
      }
    }
    this.addParam("currency", UserUtils.getUserCurrency(request));
    this.addParam("country", UserUtils.getUserLocale(request).getCountry());
    this.addParam("language", UserUtils.getUserLocale(request).getLanguage());
    this.addParam("userid", String.valueOf(UserUtils.getUserId(request)));
    if (hasErrors()) {
      return false;
    }
    return true;
  }


  /**
   * Updates the parameters with values from previously saved Criteria
   *
   * @param criteria The new parameters value
   */
  public void setParameters(Criteria criteria) {
    Iterator i = criteria.getParameters().iterator();
    while (i.hasNext()) {
      Parameter param = (Parameter) i.next();
      addParam(param.getName(), param.getValue());
    }
  }


  /**
   * Builds a list of Parameter objects based on the filter properties of this
   * list object.
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) as recordcount " +
        "FROM report_criteria_parameter p " +
        "WHERE parameter_id > -1 ");
    createFilter(sqlFilter);
    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      //Determine column to sort by
      pagedListInfo.setDefaultSort("parameter_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY parameter_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "p.* " +
        "FROM report_criteria_parameter p " +
        "WHERE parameter_id > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      Parameter thisParameter = new Parameter(rs);
      this.add(thisParameter);
    }
    rs.close();
    pst.close();
  }


  /**
   * Defines additional parameters to be added to the query
   *
   * @param sqlFilter Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (criteriaId != -1) {
      sqlFilter.append("AND p.criteria_id = ? ");
    }
  }


  /**
   * Populates the additional parameters that have been added to the query
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (criteriaId != -1) {
      pst.setInt(++i, criteriaId);
    }
    return i;
  }


  /**
   * Inserts all of the parameters contained in this object to the database
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter thisParameter = (Parameter) i.next();
      thisParameter.setCriteriaId(criteriaId);
      thisParameter.insert(db);
    }
  }


  /**
   * Updates all of the parameters contained in this object against the
   * database
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void update(Connection db) throws SQLException {
    //Delete all parameters already saved for this criteriaId
    delete(db);
    //Insert the new parameters
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter thisParameter = (Parameter) i.next();
      thisParameter.setCriteriaId(criteriaId);
      thisParameter.insert(db);
    }
  }


  /**
   * Deletes all of the parameters contained in this object from the database
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM report_criteria_parameter " +
        "WHERE criteria_id = ? ");
    pst.setInt(1, criteriaId);
    pst.execute();
    pst.close();
  }


  /**
   * Adds a feature to the Param attribute of the ParameterList object
   *
   * @param param The feature to be added to the Param attribute
   * @param value The feature to be added to the Param attribute
   * @return Description of the Return Value
   */
  public boolean addParam(String param, String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter thisParameter = (Parameter) i.next();
      if (param.equals(thisParameter.getName())) {
        thisParameter.setValue(value);
        return true;
      }
    }
    return false;
  }


  /**
   * Gets the displayValues attribute of the ParameterList object
   *
   * @return The displayValues value
   */
  public String getDisplayValues() {
    StringBuffer sb = new StringBuffer();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter thisParameter = (Parameter) i.next();
      if (thisParameter.getIsForPrompting()) {
        if (sb.length() > 0) {
          sb.append(", ");
        }
        sb.append(
            thisParameter.getDescription() + "=" + thisParameter.getValue());
      }
    }
    return sb.toString();
  }


  /**
   * Gets the classValue attribute of the ParameterList object
   *
   * @param param Description of the Parameter
   * @return The valueClass value
   */
  public java.lang.Class getValueClass(String param) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter thisParam = (Parameter) i.next();
      if (param.equals(thisParam.getName())) {
        return thisParam.getValueClass();
      }
    }
    return null;
  }


  /**
   * Gets the parameter attribute of the ParameterList object
   *
   * @param param Description of the Parameter
   * @return The parameter value
   */
  public Parameter getParameter(String param) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter thisParam = (Parameter) i.next();
      if (param.equals(thisParam.getName())) {
        return thisParam;
      }
    }
    return null;
  }
}


