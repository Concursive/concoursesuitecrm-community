//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.reports.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.UserUtils;
import org.aspcfs.utils.Template;
import dori.jasper.engine.*;
import javax.servlet.http.*;
import javax.servlet.*;

/**
 *  A collection of Parameter objects.
 *
 *@author     matt rajkowski
 *@created    September 15, 2003
 *@version    $Id: ParameterList.java,v 1.1.2.1 2003/09/15 20:58:21 mrajkowski
 *      Exp $
 */
public class ParameterList extends ArrayList {

  private int criteriaId = -1;
  protected PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the ParameterList object
   */
  public ParameterList() { }


  /**
   *  Sets the criteriaId attribute of the ParameterList object
   *
   *@param  tmp  The new criteriaId value
   */
  public void setCriteriaId(int tmp) {
    this.criteriaId = tmp;
  }


  /**
   *  Sets the criteriaId attribute of the ParameterList object
   *
   *@param  tmp  The new criteriaId value
   */
  public void setCriteriaId(String tmp) {
    this.criteriaId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the criteriaId attribute of the ParameterList object
   *
   *@return    The criteriaId value
   */
  public int getCriteriaId() {
    return criteriaId;
  }


  /**
   *  Initially loads the parameters from the JasperReport
   *
   *@param  report  The new parameters value
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
   *@param  request  The new parameters value
   */
  public void setParameters(HttpServletRequest request) {
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
            addParam("userid_range", String.valueOf(UserUtils.getUserId(request)));
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
          param.setValue(HtmlSelectProbabilityRange.getValueFromId(param.getValue()));
        }
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ParameterList-> " + param.getName() + "=" + param.getValue());
      }
    }
    this.addParam("userid", String.valueOf(UserUtils.getUserId(request)));
  }


  /**
   *  Updates the parameters with values from previously saved Criteria
   *
   *@param  criteria  The new parameters value
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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      Parameter thisParameter = new Parameter(rs);
      this.add(thisParameter);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Defines additional parameters to be added to the query
   *
   *@param  sqlFilter  Description of the Parameter
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
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM report_criteria_parameter " +
        "WHERE criteria_id = ? "
        );
    pst.setInt(1, criteriaId);
    pst.execute();
    pst.close();
  }


  /**
   *  Adds a feature to the Param attribute of the ParameterList object
   *
   *@param  param  The feature to be added to the Param attribute
   *@param  value  The feature to be added to the Param attribute
   *@return        Description of the Return Value
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
   *@return    The displayValues value
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
        sb.append(thisParameter.getDescription() + "=" + thisParameter.getValue());
      }
    }
    return sb.toString();
  }


  /**
   *  Gets the classValue attribute of the ParameterList object
   *
   *@param  param  Description of the Parameter
   *@return        The valueClass value
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


