//Copyright 2002-2003 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.troubletickets.base.*;

/**
 *  Description of the Class
 *
 * @author     akhi_m
 * @created    May 22, 2003
 */
public class CategoryEditor {

  private int id = -1;
  private HashMap categoryList = new HashMap();
  private java.util.Date hierarchyCheck = new java.util.Date();
  private boolean hierarchyUpdating = false;


  /**
   *Constructor for the CategoryEditor object
   */
  public CategoryEditor() { }


  /**
   *  Sets the id attribute of the CategoryEditor object
   *
   * @param  id  The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the id attribute of the CategoryEditor object
   *
   * @param  id  The new id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Sets the categoryList attribute of the CategoryEditor object
   *
   * @param  categoryList  The new categoryList value
   */
  public void setCategoryList(HashMap categoryList) {
    this.categoryList = categoryList;
  }


  /**
   *  Sets the hierarchyCheck attribute of the CategoryEditor object
   *
   * @param  hierarchyCheck  The new hierarchyCheck value
   */
  public void setHierarchyCheck(java.util.Date hierarchyCheck) {
    this.hierarchyCheck = hierarchyCheck;
  }


  /**
   *  Sets the hierarchyUpdating attribute of the CategoryEditor object
   *
   * @param  hierarchyUpdating  The new hierarchyUpdating value
   */
  public void setHierarchyUpdating(boolean hierarchyUpdating) {
    this.hierarchyUpdating = hierarchyUpdating;
  }


  /**
   *  Gets the hierarchyCheck attribute of the CategoryEditor object
   *
   * @return    The hierarchyCheck value
   */
  public java.util.Date getHierarchyCheck() {
    return hierarchyCheck;
  }


  /**
   *  Gets the hierarchyUpdating attribute of the CategoryEditor object
   *
   * @return    The hierarchyUpdating value
   */
  public boolean getHierarchyUpdating() {
    return hierarchyUpdating;
  }


  /**
   *  Gets the id attribute of the CategoryEditor object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  public TicketCategoryDraft getCategory(int categoryId){
    while (hierarchyUpdating) {
    }
    return (TicketCategoryDraft) categoryList.get(new Integer(categoryId));
  }
  /**
   *  Gets the categoryList attribute of the CategoryEditor object
   *
   * @return    The categoryList value
   */
  public HashMap getCategoryList() {
    while (hierarchyUpdating) {
    }
    return categoryList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    categoryList.clear();
    //Get the top level categories
    TicketCategoryDraftList topCategoryList = new TicketCategoryDraftList();
    topCategoryList.setBuildHierarchy(false);
    topCategoryList.setTopLevelOnly(true);
    topCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CategoryEditor -> buildList: Top Level Size " + topCategoryList.size());
    }
    //Build a list of all categories
    TicketCategoryDraftList fullCategoryList = new TicketCategoryDraftList();
    fullCategoryList.setBuildHierarchy(false);
    fullCategoryList.setTopLevelOnly(false);
    fullCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildList: Full List " + fullCategoryList.size());
    }
    //Combine the lists
    Iterator listA = topCategoryList.iterator();
    while (listA.hasNext()) {
      TicketCategoryDraft thisCategory = (TicketCategoryDraft) listA.next();
      if (thisCategory != null) {
        categoryList.put(new Integer(++id), thisCategory);
        this.addSubCategories(thisCategory, fullCategoryList);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> Total categories added : " + categoryList.size());
    }
  }


  /**
   *  Adds a feature to the SubCategories attribute of the CategoryEditor object
   *
   * @param  parentCategory  The feature to be added to the SubCategories attribute
   * @param  fullList        The feature to be added to the SubCategories attribute
   */
  private void addSubCategories(TicketCategoryDraft parentCategory, TicketCategoryDraftList fullList) {
    if (parentCategory.getShortChildList() == null) {
      parentCategory.setShortChildList(new TicketCategoryDraftList());
    }
    Iterator i = fullList.iterator();
    while (i.hasNext()) {
      TicketCategoryDraft tmpCategory = (TicketCategoryDraft) i.next();
      if (tmpCategory.getParentCode() == parentCategory.getId()) {
        categoryList.put(new Integer(++id), tmpCategory);
        parentCategory.getShortChildList().add(tmpCategory);
        this.addSubCategories(tmpCategory, fullList);
      }
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void update(Connection db) throws SQLException {
    java.util.Date checkDate = new java.util.Date();
    if (checkDate.after(this.getHierarchyCheck())) {
      synchronized (this) {
        try {
          hierarchyUpdating = true;
          if (checkDate.after(hierarchyCheck)) {
            this.build(db);
            this.setHierarchyCheck(new java.util.Date());
          }
        } catch (SQLException e) {
          throw e;
        } finally {
          hierarchyUpdating = false;
        }
      }
    }
  }

}


