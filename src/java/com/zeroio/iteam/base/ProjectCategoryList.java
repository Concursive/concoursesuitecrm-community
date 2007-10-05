/*
 *  Copyright 2000-2004 Team Elements. All Rights Reserved.
 *  License: This source code cannot be modified, distributed or used without
 *  written permission from Team Elements. This notice must remain in
 *  place.
 */
package com.zeroio.iteam.base;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.HtmlSelect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Contains a collection of project categories
 *
 * @author matt rajkowski
 * @version $Id$
 * @created December 27, 2004
 */
public class ProjectCategoryList extends ArrayList {
  private int enabled = Constants.UNDEFINED;
  private int includeId = -1;
  private ArrayList categoryMap = null;
  private int categoriesForProjectUser = -1;
  private int categoryId = -1;

  public ProjectCategoryList() {
  }


  public int getEnabled() {
    return enabled;
  }


  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  public int getIncludeId() {
    return includeId;
  }


  public void setIncludeId(int tmp) {
    this.includeId = tmp;
  }


  public void setIncludeId(String tmp) {
    try {
      this.includeId = Integer.parseInt(tmp);
    } catch (Exception e) {
      this.includeId = -1;
    }
  }

  public void setCategoriesForProjectUser(int userId) {
    categoriesForProjectUser = userId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();
    //Set the order
    sqlOrder.append("ORDER BY pc." + DatabaseUtils.addQuotes(db, "level") + ", pc.description ");
    createFilter(sqlFilter);
    //Need to build a base SQL statement for returning records
    sqlSelect.append("SELECT ");
    sqlSelect.append(
        "pc.* " +
        "FROM lookup_project_category pc " +
        "WHERE pc.code > -1 ");
    pst = db.prepareStatement(
        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      ProjectCategory thisCategory = new ProjectCategory(rs);
      this.add(thisCategory);
    }
    rs.close();
    pst.close();
  }

  public void addCategories(ProjectList projects) {
    categoryMap = new ArrayList();
    Iterator i = projects.iterator();
    while (i.hasNext()) {
      Project thisProject = (Project) i.next();
      if (thisProject.getCategoryId() > -1) {
        Integer integerCategoryId = new Integer(thisProject.getCategoryId());
        if (!categoryMap.contains(integerCategoryId)) {
          categoryMap.add(integerCategoryId);
        }
      }
    }
  }

  protected void createFilter(StringBuffer sqlFilter) {
    if (enabled != Constants.UNDEFINED) {
      if (includeId == -1) {
        sqlFilter.append("AND pc.enabled = ? ");
      } else {
        sqlFilter.append("AND (pc.enabled = ? OR pc.code = ?) ");
      }
    }
    if (categoryMap != null && categoryMap.size() > 0) {
      sqlFilter.append("AND (pc.code IN (");
      Iterator i = categoryMap.iterator();
      while (i.hasNext()) {
        Integer integerCategoryId = (Integer) i.next();
        sqlFilter.append(integerCategoryId.intValue());
        if (i.hasNext()) {
          sqlFilter.append(", ");
        }
      }
      sqlFilter.append(") ");
      if (includeId > -1) {
        sqlFilter.append("OR pc.code = ? ");
      }
      sqlFilter.append(") ");
    }
    if (categoriesForProjectUser > -1) {
      sqlFilter.append(
          "AND (pc.code IN (SELECT category_id FROM projects WHERE project_id IN (SELECT DISTINCT project_id FROM project_team WHERE user_id = ? " +
          "AND status IS NULL) OR project_id IN (SELECT project_id FROM projects WHERE allow_guests = ? AND approvaldate IS NOT NULL)) ");
      if (includeId > -1) {
        sqlFilter.append("OR pc.code = ? ");
      }
      sqlFilter.append(") ");
    }
    if (categoryId > -1) {
      sqlFilter.append("AND pc.code = ? ");
    }
  }


  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (enabled != Constants.UNDEFINED) {
      if (includeId == -1) {
        pst.setBoolean(++i, enabled == Constants.TRUE);
      } else {
        pst.setBoolean(++i, enabled == Constants.TRUE);
        pst.setInt(++i, includeId);
      }
    }
    if (categoryMap != null && categoryMap.size() > 0) {
      if (includeId > -1) {
        pst.setInt(++i, includeId);
      }
    }
    if (categoriesForProjectUser > -1) {
      pst.setInt(++i, categoriesForProjectUser);
      pst.setBoolean(++i, true);
      if (includeId > -1) {
        pst.setInt(++i, includeId);
      }
    }
    if (categoryId > -1) {
      pst.setInt(++i, categoryId);
    }
    return i;
  }


  public String getValueFromId(int id) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProjectCategory thisCategory = (ProjectCategory) i.next();
      if (thisCategory.getId() == id) {
        return thisCategory.getDescription();
      }
    }
    return null;
  }


  public String getHtmlSelect(SystemStatus thisSystem, String selectName, int selectedId) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProjectCategory thisCategory = (ProjectCategory) i.next();
      thisSelect.addItem(
          thisCategory.getId(),
          thisCategory.getDescription());
    }
    return thisSelect.getHtml(selectName, selectedId);
  }


  public HtmlSelect getHtmlSelect() {
    HtmlSelect thisSelect = new HtmlSelect();
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ProjectCategory thisCategory = (ProjectCategory) i.next();
      thisSelect.addItem(
          thisCategory.getId(),
          thisCategory.getDescription());
    }
    return thisSelect;
  }


  public void updateValues(Connection db, String[] params, String[] names) throws SQLException {

    // Put into something manageable
    ArrayList arrayList = new ArrayList();
    for (int i = 0; i < params.length; i++) {
      System.out.println("ProjectCategoryList-> Name: " + names[i]);
      System.out.println("ProjectCategoryList-> Param: " + params[i]);
      arrayList.add(params[i]);
    }

    // BEGIN TRANSACTION

    // Iterate through this article list
    Iterator items = this.iterator();
    while (items.hasNext()) {
      ProjectCategory thisCategory = (ProjectCategory) items.next();
      // If item is not in the passed array, then disable the entry
      if (!arrayList.contains(String.valueOf(thisCategory.getId()))) {
        thisCategory.setEnabled(false);
        thisCategory.update(db);
        items.remove();
      }
    }

    // Iterate through the array
    for (int i = 0; i < params.length; i++) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProjectCategoryList-> Name: " + names[i]);
        System.out.println("ProjectCategoryList-> Param: " + params[i]);
      }
      if (params[i].startsWith("*")) {
        // TODO: Check to see if a previously disabled entry has the same name,
        // and enable it


        // New item, add it at the correct position
        ProjectCategory thisCategory = new ProjectCategory();
        thisCategory.setDescription(names[i]);
        thisCategory.setLevel(i);
        thisCategory.insert(db);
        this.add(thisCategory);
      } else {
        // Existing item, update the name and position
        updateName(db, Integer.parseInt(params[i]), names[i]);
        updateLevel(db, Integer.parseInt(params[i]), i);
      }
    }

    // END TRANSACTION
  }


  public void updateLevel(Connection db, int id, int level) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE lookup_project_category " +
        "SET modified = " + DatabaseUtils.getCurrentTimestamp(db) + ", " + DatabaseUtils.addQuotes(db, "level") + " = ? " +
        "WHERE code = ? ");
    int i = 0;
    pst.setInt(++i, level);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
  }


  public void updateName(Connection db, int id, String name) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "UPDATE lookup_project_category " +
        "SET description = ?, modified = " + DatabaseUtils.getCurrentTimestamp(db) + " " +
        "WHERE code = ? ");
    int i = 0;
    pst.setString(++i, name);
    pst.setInt(++i, id);
    pst.executeUpdate();
    pst.close();
  }

}

