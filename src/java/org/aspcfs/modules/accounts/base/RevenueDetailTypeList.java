package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.sql.*;
import com.darkhorseventures.webutils.HtmlSelect;
import com.darkhorseventures.webutils.LookupList;

public class RevenueDetailTypeList extends Vector {
  private String jsEvent = "";
  private int defaultKey = -1;
  private int size = 1;
  private boolean multiple = false;

  public RevenueDetailTypeList() { }

  public RevenueDetailTypeList(Connection db) throws SQLException {
    buildList(db);
  }

  public void setSize(int size) {
    this.size = size;
  }

  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }

  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }

  public void setDefaultKey(int tmp) {
    this.defaultKey = tmp;
  }

  public void setDefaultKey(String tmp) {
    this.defaultKey = Integer.parseInt(tmp);
  }

  public int getSize() {
    return size;
  }

  public boolean getMultiple() {
    return multiple;
  }

  public String getJsEvent() {
    return jsEvent;
  }

  public int getDefaultKey() {
    return defaultKey;
  }

  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, defaultKey);
  }

  public String getHtmlSelect(String selectName, int defaultKey) {
    LookupList revenueTypeSelect = new LookupList();
    revenueTypeSelect = getLookupList(selectName, defaultKey);
    return revenueTypeSelect.getHtmlSelect(selectName, defaultKey);
  }

  public LookupList getLookupList(String selectName, int defaultKey) {
    LookupList revenueTypeSelect = new LookupList();
    revenueTypeSelect.setJsEvent(jsEvent);
    revenueTypeSelect.setSelectSize(this.getSize());
    revenueTypeSelect.setMultiple(this.getMultiple());

    Iterator i = this.iterator();
    while (i.hasNext()) {
      RevenueDetailType thisRevenueType = (RevenueDetailType) i.next();

      if (thisRevenueType.getEnabled() == true) {
        revenueTypeSelect.appendItem(thisRevenueType.getId(), thisRevenueType.getDescription());
      } else if (thisRevenueType.getEnabled() == false && thisRevenueType.getId() == defaultKey) {
        revenueTypeSelect.appendItem(thisRevenueType.getId(), thisRevenueType.getDescription() + " (X)");
      }
    }

    return revenueTypeSelect;
  }

  public void addItem(int key, String name) {
    RevenueDetailType thisRevenueType = new RevenueDetailType();
    thisRevenueType.setId(key);
    thisRevenueType.setDescription(name);
    this.add(0, thisRevenueType);
  }

  public void buildList(Connection db) throws SQLException {
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * FROM lookup_revenuedetail_types WHERE code > -1 ");
    sql.append("ORDER BY level, description ");

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    while (rs.next()) {
      RevenueDetailType thisRevenueType = new RevenueDetailType(rs);
      this.addElement(thisRevenueType);
    }
    rs.close();
    st.close();
  }

}

