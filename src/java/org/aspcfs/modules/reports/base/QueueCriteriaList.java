//Copyright 2003 Dark Horse Ventures

package org.aspcfs.modules.reports.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import dori.jasper.engine.*;
import java.lang.reflect.*;

/**
 *  Contains all criteria items for a report queue
 *
 *@author     matt rajkowski
 *@created    October 3, 2003
 *@version    $Id: QueueCriteriaList.java,v 1.1.2.1 2003/10/03 20:54:54
 *      mrajkowski Exp $
 */
public class QueueCriteriaList extends ArrayList {

  protected PagedListInfo pagedListInfo = null;
  protected int queueId = -1;


  /**
   *  Constructor for the QueueCriteriaList object
   */
  public QueueCriteriaList() { }


  /**
   *  Sets the pagedListInfo attribute of the QueueCriteriaList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Gets the pagedListInfo attribute of the QueueCriteriaList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the queueId attribute of the QueueCriteriaList object
   *
   *@param  tmp  The new queueId value
   */
  public void setQueueId(int tmp) {
    this.queueId = tmp;
  }


  /**
   *  Sets the queueId attribute of the QueueCriteriaList object
   *
   *@param  tmp  The new queueId value
   */
  public void setQueueId(String tmp) {
    this.queueId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the queueId attribute of the QueueCriteriaList object
   *
   *@return    The queueId value
   */
  public int getQueueId() {
    return queueId;
  }


  /**
   *  Builds a list of objects by querying a database
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
        "FROM report_queue_criteria qc " +
        "WHERE criteria_id > -1 ");
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
      pagedListInfo.setDefaultSort("qc.criteria_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY qc.criteria_id ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "qc.* " +
        "FROM report_queue_criteria qc " +
        "WHERE criteria_id > -1 ");
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
      QueueCriteria thisCriteria = new QueueCriteria(rs);
      this.add(thisCriteria);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Defines additional parameters to be used in the query
   *
   *@param  sqlFilter  Description of the Parameter
   */
  protected void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (queueId != -1) {
      sqlFilter.append("AND qc.queue_id = ? ");
    }
  }


  /**
   *  Sets the additional parameters used in the query
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (queueId != -1) {
      pst.setInt(++i, queueId);
    }
    return i;
  }


  /**
   *  Gets the parameters attribute of the QueueCriteriaList object
   *
   *@param  jasperReport                                     Description of the
   *      Parameter
   *@return                                                  The parameters
   *      value
   *@exception  java.lang.NoSuchMethodException              Description of the
   *      Exception
   *@exception  java.lang.IllegalAccessException             Description of the
   *      Exception
   *@exception  java.lang.reflect.InvocationTargetException  Description of the
   *      Exception
   */
  public Map getParameters(JasperReport jasperReport) throws java.lang.NoSuchMethodException,
      java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException {
    //Get a list of the parameter definitions
    ParameterList params = new ParameterList();
    params.setParameters(jasperReport);
    //Add the parameters for jasper reports based on the definitions
    Map parameters = new HashMap();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      QueueCriteria thisCriteria = (QueueCriteria) i.next();
      //Add to map based on parameter type, converting from a string
      Class classValue = params.getValueClass(thisCriteria.getParameter());
      if (classValue.getName().equals("java.sql.Timestamp") && thisCriteria.getParameter().startsWith("date_")) {
        parameters.put(thisCriteria.getParameter(), DatabaseUtils.parseDateToTimestamp(thisCriteria.getValue()));
      } else if (classValue.getName().equals("java.sql.Timestamp")) {
        parameters.put(thisCriteria.getParameter(), DatabaseUtils.parseTimestamp(thisCriteria.getValue()));
      } else if (!classValue.getName().equals("java.lang.String")) {
        Class[] argTypes = new Class[]{String.class};
        Method method = classValue.getMethod("valueOf", argTypes);
        Object result = method.invoke(null, new Object[]{thisCriteria.getValue()});
        parameters.put(thisCriteria.getParameter(), result);
      } else {
        parameters.put(thisCriteria.getParameter(), thisCriteria.getValue());
      }
    }
    return parameters;
  }
}


