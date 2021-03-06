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
package org.aspcfs.modules.reports.base;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actionplans.base.ActionPlan;
import org.aspcfs.modules.admin.base.PermissionCategory;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.web.HtmlSelectProbabilityRange;
import org.aspcfs.utils.web.PagedListInfo;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;

/**
 *  A collection of Parameter objects.
 *
 * @author     matt rajkowski
 * @created    September 15, 2003
 * @version    $Id: ParameterList.java,v 1.1.2.1 2003/09/15 20:58:21 mrajkowski
 *      Exp $
 */
public class ParameterList extends ArrayList {

  private int criteriaId = -1;
  protected PagedListInfo pagedListInfo = null;
  public HashMap errors = new HashMap();
  public SystemStatus systemStatus = null;


  /**
   *  Constructor for the ParameterList object
   */
  public ParameterList() { }


  /**
   *  Sets the criteriaId attribute of the ParameterList object
   *
   * @param  tmp  The new criteriaId value
   */
  public void setCriteriaId(int tmp) {
    this.criteriaId = tmp;
  }


  /**
   *  Sets the criteriaId attribute of the ParameterList object
   *
   * @param  tmp  The new criteriaId value
   */
  public void setCriteriaId(String tmp) {
    this.criteriaId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the systemStatus attribute of the ParameterList object
   *
   * @param  tmp  The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   *  Gets the systemStatus attribute of the ParameterList object
   *
   * @return    The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   *  Gets the criteriaId attribute of the ParameterList object
   *
   * @return    The criteriaId value
   */
  public int getCriteriaId() {
    return criteriaId;
  }


  /**
   *  Sets the errors attribute of the ParameterList object
   *
   * @param  tmp  The new errors value
   */
  public void setErrors(HashMap tmp) {
    this.errors = tmp;
  }


  /**
   *  Gets the errors attribute of the ParameterList object
   *
   * @return    The errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public boolean hasErrors() {
    return (errors.size() > 0);
  }


  /**
   *  Initially loads the parameters from the JasperReport
   *
   * @param  report  The new parameters value
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
   *  Takes the user input and expands the data into the corresponding jasper
   *  parameters
   *
   * @param  request  The new parameters value
   * @return          Description of the Return Value
   */
  public boolean setParameters(HttpServletRequest request) {
    Timestamp startDate = null;
    Timestamp endDate = null;
    Calendar today = Calendar.getInstance();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Parameter param = (Parameter) i.next();
      //For each parameter the user is prompted for or value provided to the parameter
      //while preparing its context, evaluate the answer
      if (param.getIsForPrompting() || param.getRequired()) {
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
            //check to see if the description needs to be parsed
            if (whereParam.getDescription().indexOf("$P{") > -1) {
              Template where = new Template(whereParam.getDescription());
              Parameter dbTrue = this.getParameter("DB-TRUE");
              Parameter dbFalse = this.getParameter("DB-FALSE");
              if (dbTrue != null && dbFalse != null) {
                if ("1".equals(param.getValue())) {
                  where.addParseElement("$P{" + param.getName() + "}", dbTrue.getValue());
                  addParam(whereParam.getName(), where.getParsedText());
                } else if ("0".equals(param.getValue())) {
                  where.addParseElement("$P{" + param.getName() + "}", dbFalse.getValue());
                  addParam(whereParam.getName(), where.getParsedText());
                } else {
                  //Blank out the default
                  addParam(whereParam.getName(), "");
                }
              }
            } else {
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
          }
          addParam(param.getName(), param.getValue());
        }
        //HtmlSelect date range. Proceed only if both start and end dates are null
        if (param.getName().equals("range_date")) {
					Parameter startParam = this.getParameter("date_start");
					Parameter endParam = this.getParameter("date_end");
					if (startParam != null && endParam != null) {
						// set the values available in the request
						startParam.setValue(request.getParameter(startParam.getName()));
						endParam.setValue(request.getParameter(endParam.getName()));
						if (startParam.getValue() != null && endParam.getValue() != null) {
							// No dates provided. So determine the range. End date should be a
							// timestamp to include
							// all the records entered today until this time.
							String end = DateUtils.roundUpToNextFive(System.currentTimeMillis()).toString();
							String start = "";
							if ("7".equals(param.getValue())) {
								// Replace the start and end dates to match last 7 days
								today.add(Calendar.DATE, -7);
								start = getDateAsString(request, today);
							} else if ("14".equals(param.getValue())) {
								// Replace the start and end dates to match last 14 days
								today.add(Calendar.DATE, -14);
								start = getDateAsString(request, today);
							} else if ("30".equals(param.getValue())) {
								// Replace the start and end dates to match last 30 days
								today.add(Calendar.DATE, -30);
								start = getDateAsString(request, today);
							} else if ("0".equals(param.getValue())) {
								start = getDateAsString(request, today);
							} else if ("24".equals(param.getValue())) {
								today.add(Calendar.HOUR, -24);
								start = getDateAsString(request, today);
							} else if ("48".equals(param.getValue())) {
								today.add(Calendar.HOUR, -48);
								start = getDateAsString(request, today);
							} else if ("1".equals(param.getValue())) {
								today.add(Calendar.YEAR, -1);
								start = getDateAsString(request, today);
							} else if ("10".equals(param.getValue())) {
								startDate = this.getTimestamp(request, startParam.getValue());
								start = this.getDateAsString(startDate);
								endDate = this.getTimestamp(request, endParam.getValue());
								end = endDate == null ? this.getDateAsString(DateUtils.roundUpToNextFive(System.currentTimeMillis())) : this.getDateAsString(endDate);
							}
							if (!"-1".equals(param.getValue()) && errors.size() == 0) {
								this.setDateRange(start, end);
								addParam(startParam.getName(), start);
								addParam(endParam.getName(), end);
							} else {
								// All records need to be displayed
								Parameter rangeStartParam = this.getParameter("range_date_start");
								Parameter rangeEndParam = this.getParameter("range_date_end");
								addParam(rangeStartParam.getName(), "");
								addParam(rangeEndParam.getName(), "");
							}
						}
					}
					addParam(param.getName(), param.getValue());
				}
        if (param.getName().startsWith("textlookup_")) {
          Parameter whereParam = this.getParameter(param.getName() + "_where");
          if (whereParam != null) {
            if (!"".equals(param.getValue())) {
              // New case, replace query param with another param and parse
              Template where = new Template(whereParam.getDescription());
              where.addParseElement("$P{" + param.getName() + "}", param.getValue());
              addParam(whereParam.getName(), where.getParsedText());
            } else {
              addParam(whereParam.getName(), " ");
            }
          }
          addParam(param.getName(), param.getValue());
        }
        //Where clause for text fields
        if (param.getName().startsWith("text_")) {
          Parameter whereParam = this.getParameter(param.getName() + "_where");
          if (whereParam != null) {
            if (!"".equals(param.getValue())) {
              //New case, replace query param with another param and parse
              Template where = new Template(whereParam.getDescription());
              where.addParseElement("$P{" + param.getName() + "}", param.getValue());
              addParam(whereParam.getName(), where.getParsedText());
            } else {
              addParam(whereParam.getName(), " ");
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
        //Integer siteid lookup
        if (param.getName().startsWith("siteid")) {
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
          //proceed only if date range has NOT been specified
          if (this.getParameter("range_date") == null) {
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

      //reports can have hidden parameters which don't required user input but the
      // default value provided during design time will be used
      //Auto-populate the user's range based on selected type
      if (param.getName().startsWith("hidden_")) {
        //TODO: Currently Report parameter default value is ignored. Determine a scheme to consider the
        //parameter's default value
      }

      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "ParameterList-> " + param.getName() + "=" + param.getValue());
      }
    }
    if (startDate != null && endDate != null) {
      if (startDate.after(endDate)) {
        if (systemStatus != null) {
          errors.put("date_startError", systemStatus.getLabel("object.validation.firstDateNotAfterSecondDate"));
        } else {
          errors.put("date_startError", "The first date can not be after second date.");
        }
      }
    }
    this.addParam("currency", UserUtils.getUserCurrency(request));
    this.addParam("country", UserUtils.getUserLocale(request).getCountry());
    this.addParam("language", UserUtils.getUserLocale(request).getLanguage());
    this.addParam("userid", String.valueOf(UserUtils.getUserId(request)));
    this.addParam("user_hierarchy", UserUtils.getUserIdRange(request));
    this.addParam("user_contact_name", UserUtils.getUserContactName(request));
    //default output type
    this.addParam("REPORT_OUTPUT_TYPE", String.valueOf(
            org.aspcfs.modules.reports.base.ReportQueue.REPORT_TYPE_PDF));

    //Determine the module and add the constants required
    PermissionCategory thisCategory = (PermissionCategory) request.getAttribute("category");
    if (thisCategory != null) {
      switch (thisCategory.getConstant()) {
        case PermissionCategory.PERMISSION_CAT_ACCOUNTS:
        case PermissionCategory.PERMISSION_CAT_SALES:
          this.addParam("actionplan_module_constant", String.valueOf(ActionPlan.ACCOUNTS));
          this.addParam("actionplan_module_opp_constant", String.valueOf(ActionPlan.PIPELINE_COMPONENT));
          break;
        case PermissionCategory.PERMISSION_CAT_TICKETS:
          this.addParam("actionplan_module_constant", String.valueOf(ActionPlan.TICKETS));
          break;
      }
    }

    //Add constants
    this.addParam("COMPLETE_FOLLOWUP_PENDING", String.valueOf(Call.COMPLETE_FOLLOWUP_PENDING));
    
    //User ID Ranges
    try {
      if (this.getParameter("user_id_ranges") != null) {
        if (systemStatus != null) {
          StringTokenizer st =
              new StringTokenizer(UserUtils.getUserIdRange(request), ",");
          while (st.hasMoreTokens()) {
            String userId = st.nextToken().trim();
            User userRecord = systemStatus.getUser(Integer.parseInt(userId));
            if (userRecord != null) {
              this.addParameter("user_id_range_" + userId, 
                  userRecord.getIdRange(), Class.forName("java.lang.String"));
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
      
    if (hasErrors()) {
      return false;
    }
    return true;
  }


  /**
   *  Updates the parameters with values from previously saved Criteria
   *
   * @param  criteria  The new parameters value
   */
  public void setParameters(Criteria criteria) {
    Iterator i = criteria.getParameters().iterator();
    while (i.hasNext()) {
      Parameter param = (Parameter) i.next();
      addParam(param.getName(), param.getValue());
    }
  }


  /**
   *  Builds a list of Parameter objects based on the filter properties of this
   *  list object.
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, pst);
    }
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
   *  Defines additional parameters to be added to the query
   *
   * @param  sqlFilter  Description of the Parameter
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
   *  Populates the additional parameters that have been added to the query
   *
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (criteriaId != -1) {
      pst.setInt(++i, criteriaId);
    }
    return i;
  }


  /**
   *  Inserts all of the parameters contained in this object to the database
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
   *  Updates all of the parameters contained in this object against the
   *  database
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
   *  Deletes all of the parameters contained in this object from the database
   *
   * @param  db             Description of the Parameter
   * @throws  SQLException  Description of the Exception
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
   *  Adds a feature to the Parameter attribute of the ParameterList object
   *
   * @param  param  The feature to be added to the Parameter attribute
   * @param  value  The feature to be added to the Parameter attribute
   */
  private void addParameter(String param, String value, Class className) {
    boolean added = addParam(param, value);
    if (!added) {
      Parameter parameter = new Parameter();
      parameter.setName(param);
      parameter.setValue(value);
      parameter.setValueClass(className);
      this.add(parameter);
    }
  }


  /**
   *  Adds a feature to the Param attribute of the ParameterList object
   *
   * @param  param  The feature to be added to the Param attribute
   * @param  value  The feature to be added to the Param attribute
   * @return        Description of the Return Value
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
   *  Gets the displayValues attribute of the ParameterList object
   *
   * @return    The displayValues value
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
   *  Gets the classValue attribute of the ParameterList object
   *
   * @param  param  Description of the Parameter
   * @return        The valueClass value
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
   *  Gets the parameter attribute of the ParameterList object
   *
   * @param  param  Description of the Parameter
   * @return        The parameter value
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


  /**
   *  Gets the timestamp attribute of the ParameterList object
   *
   * @param  request  Description of the Parameter
   * @param  value    Description of the Parameter
   * @return          The timestamp value
   */
  private Timestamp getTimestamp(HttpServletRequest request, String value) {
    Timestamp tmpTimestamp = DateUtils.getUserToServerDateTime(
        TimeZone.getTimeZone(UserUtils.getUserTimeZone(request)), DateFormat.SHORT, DateFormat.LONG, value, UserUtils.getUserLocale(
        request));
    return tmpTimestamp;
  }


  /**
   *  Gets the dateAsString attribute of the ParameterList object
   *
   * @param  ts  Description of the Parameter
   * @return     The dateAsString value
   */
  private String getDateAsString(Timestamp ts) {
    SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
        DateFormat.SHORT, Locale.getDefault());
    return (formatter.format(ts));
  }


  /**
   *  Gets the dateAsString attribute of the ParameterList object
   *
   * @param  cal      Description of the Parameter
   * @param  request  Description of the Parameter
   * @return          The dateAsString value
   */
  private String getDateAsString(HttpServletRequest request, Calendar cal) {
    String date =
        DateUtils.getServerToUserDateString(
        TimeZone.getTimeZone(UserUtils.getUserTimeZone(request)),
        DateFormat.SHORT,
        DateUtils.getUserToServerDateTime(cal,
        TimeZone.getTimeZone(UserUtils.getUserTimeZone(request))));
    return date;
  }


  /**
   *  Sets the dateRange attribute of the ParameterList object
   *
   * @param  start  The new dateRange value
   * @param  end    The new dateRange value
   */
  private void setDateRange(String start, String end) {
    Parameter rangeStartParam = this.getParameter("range_date_start");
    Template t1 = new Template(rangeStartParam.getDescription());
    t1.addParseElement("$P{date_start}", "'" + start + "'");
    addParam(rangeStartParam.getName(), t1.getParsedText());

    Parameter rangeEndParam = this.getParameter("range_date_end");
    Template t2 = new Template(rangeEndParam.getDescription());
    t2.addParseElement("$P{date_end}", "'" + end + "'");
    addParam(rangeEndParam.getName(), t2.getParsedText());
  }
  
  public String groupSpanedParameters(ArrayList parameters, String spanName, String spanId, HttpServletRequest request, SystemStatus systemStatus) {
  	String result = "";
  	if (parameters != null && parameters.size() > 0) {
  		Iterator i = parameters.iterator();
  		String style = "";
  		while (i.hasNext()) {
  			Parameter parameter = (Parameter) i.next();
  			if (request.getAttribute(parameter.getName() + "Error") != null) {
  				style = "";
  				break;
  			}
  		}
  		result = "<span name='" + spanName + "' ID='" + spanId + "' style = '" + style + "'><table cellpadding='4' cellspacing='0'  width='100%'>";
  		i = parameters.iterator();
  		while (i.hasNext()) {
  			Parameter parameter = (Parameter) i.next();
  			result += "<tr>";
  			result += "<td class='formLabel'>" + StringUtils.toHtml(parameter.getDisplayName(systemStatus));
  			result += "</td><td>";
  			result += parameter.getHtml(systemStatus, request, this);
  			if (parameter.getRequired()) {
          result += "<font color=\"red\">*</font>";
          result += "<font color='#006699'>" + StringUtils.toHtml((String)request.getAttribute(parameter.getName() + "Error"))+ "</font>";
  			}
  			result += "</td></tr>";
  		}
  		result += "</table></span>";
  	}
  	return result;
  }
}
