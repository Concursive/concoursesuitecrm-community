//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.sql.*;
import java.text.*;
import java.util.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.utils.DatabaseUtils;

public class SurveyList extends Vector {

  private PagedListInfo pagedListInfo = null;
  private int itemLength = -1;
  private int type = -1;
  private int enteredBy = -1;
  private String enteredByIdRange = null;
  private String jsEvent = null;

  public SurveyList() { }

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
      "SELECT COUNT(*) AS recordcount " +
        "FROM survey s " +
        "LEFT JOIN contact ct_eb ON (s.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (s.modifiedby = ct_mb.user_id) " +
	"LEFT JOIN lookup_survey_types st ON (s.type = st.code) " +
        "WHERE s.id > -1 ");

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
      pst.close();
      rs.close();

      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND s.name < ? ");
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("s.name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY s.name ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append(
        "s.*, " +
        "ct_eb.namelast as eb_namelast, ct_eb.namefirst as eb_namefirst, " +
        "ct_mb.namelast as mb_namelast, ct_mb.namefirst as mb_namefirst, st.description as typename " +
        "FROM survey s " +
        "LEFT JOIN contact ct_eb ON (s.enteredby = ct_eb.user_id) " +
        "LEFT JOIN contact ct_mb ON (s.modifiedby = ct_mb.user_id) " +
	"LEFT JOIN lookup_survey_types st ON (s.type = st.code) " +
        "WHERE s.id > -1 ");

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
      Survey thisSurvey = new Survey(rs);
      this.addElement(thisSurvey);
    }
    rs.close();
    pst.close();

    //buildResources(db);
  }
public PagedListInfo getPagedListInfo() { return pagedListInfo; }
public int getItemLength() { return itemLength; }
public int getType() { return type; }
public void setPagedListInfo(PagedListInfo tmp) { this.pagedListInfo = tmp; }
public void setItemLength(int tmp) { this.itemLength = tmp; }
public void setType(int tmp) { this.type = tmp; }
public void setEnteredBy(int tmp) { this.enteredBy = tmp; }
public void setEnteredByIdRange(String tmp) { this.enteredByIdRange = tmp; }
public int getEnteredBy() { return enteredBy; }
public String getEnteredByIdRange() { return enteredByIdRange; }
public void setEnteredBy(String tmp) { this.enteredBy = Integer.parseInt(tmp); }

public String getJsEvent() {
	return jsEvent;
}
public void setJsEvent(String jsEvent) {
	this.jsEvent = jsEvent;
}

  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    
      if (enteredBy != -1) {
        sqlFilter.append("AND s.enteredby = ? ");
      }
      if (enteredByIdRange != null) {
        sqlFilter.append("AND s.enteredby IN (" + enteredByIdRange + ") ");
      }
      
  }

  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
      
      if (enteredBy != -1) {
        pst.setInt(++i, enteredBy);
      }
      
    return i;
  }

  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect surveyListSelect = new HtmlSelect();
    surveyListSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Survey thisSurvey = (Survey) i.next();
      surveyListSelect.addItem(
          thisSurvey.getId(),
          thisSurvey.getName());
    }
    return surveyListSelect.getHtml(selectName, defaultKey);
  }

  public void addItem(int key, String name) {
    Survey thisSurvey = new Survey();
    thisSurvey.setId(key);
    thisSurvey.setName(name);
    if (this.size() == 0) {
      this.add(thisSurvey);
    } else {
      this.add(0, thisSurvey);
    }
  }

}

