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
package org.aspcfs.modules.admin.base;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Category;
import org.aspcfs.modules.base.CategoryList;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraft;
import org.aspcfs.modules.troubletickets.base.TicketCategoryDraftList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 *  Maintains Category Editors for various components
 *
 *@author     akhi_m
 *@created    May 22, 2003
 *@version    $Id: CategoryEditor.java,v 1.5 2004/04/01 16:14:05 mrajkowski Exp
 *      $
 */
public class CategoryEditor {

  // Basic properties of an editor
  private int id = -1;
  private int moduleId = -1;
  private int categoryId = -1;
  private int constantId = -1;
  private String tableName = null;
  private int level = -1;
  private String description = null;
  private int maxLevels = -1;
  // Helper properties for working with editors
  private HashMap categoryList = new HashMap();
  private TicketCategoryDraftList topCategoryList = new TicketCategoryDraftList();
  private java.util.Date hierarchyCheck = new java.util.Date();
  private boolean hierarchyUpdating = false;
  private SystemStatus systemStatus = null;


  /**
   *  Constructor for the CategoryEditor object
   */
  public CategoryEditor() { }


  /**
   *  Constructor for the CategoryEditor object
   *
   *@param  db                Description of the Parameter
   *@param  constantId        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public CategoryEditor(Connection db, int constantId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM category_editor_lookup " +
        "WHERE constant_id = ? ");
    pst.setInt(1, constantId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Constructor for the CategoryEditor object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public CategoryEditor(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    moduleId = rs.getInt("module_id");
    constantId = rs.getInt("constant_id");
    tableName = rs.getString("table_name");
    level = rs.getInt("level");
    description = rs.getString("description");
    //entered
    categoryId = rs.getInt("category_id");
    maxLevels = rs.getInt("max_levels");
  }


  /**
   *  Sets the id attribute of the CategoryEditor object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the CategoryEditor object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the moduleId attribute of the CategoryEditor object
   *
   *@param  tmp  The new moduleId value
   */
  public void setModuleId(int tmp) {
    this.moduleId = tmp;
  }


  /**
   *  Sets the moduleId attribute of the CategoryEditor object
   *
   *@param  tmp  The new moduleId value
   */
  public void setModuleId(String tmp) {
    this.moduleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryId attribute of the CategoryEditor object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(int tmp) {
    this.categoryId = tmp;
  }


  /**
   *  Sets the categoryId attribute of the CategoryEditor object
   *
   *@param  tmp  The new categoryId value
   */
  public void setCategoryId(String tmp) {
    this.categoryId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the constantId attribute of the CategoryEditor object
   *
   *@param  tmp  The new constantId value
   */
  public void setConstantId(int tmp) {
    this.constantId = tmp;
  }


  /**
   *  Sets the constantId attribute of the CategoryEditor object
   *
   *@param  tmp  The new constantId value
   */
  public void setConstantId(String tmp) {
    this.constantId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the tableName attribute of the CategoryEditor object
   *
   *@param  tmp  The new tableName value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Sets the level attribute of the CategoryEditor object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(int tmp) {
    this.level = tmp;
  }


  /**
   *  Sets the level attribute of the CategoryEditor object
   *
   *@param  tmp  The new level value
   */
  public void setLevel(String tmp) {
    this.level = Integer.parseInt(tmp);
  }


  /**
   *  Sets the description attribute of the CategoryEditor object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the maxLevels attribute of the CategoryEditor object
   *
   *@param  tmp  The new maxLevels value
   */
  public void setMaxLevels(int tmp) {
    this.maxLevels = tmp;
  }


  /**
   *  Sets the maxLevels attribute of the CategoryEditor object
   *
   *@param  tmp  The new maxLevels value
   */
  public void setMaxLevels(String tmp) {
    this.maxLevels = Integer.parseInt(tmp);
  }


  /**
   *  Sets the systemStatus attribute of the CategoryEditor object
   *
   *@param  tmp  The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   *  Gets the systemStatus attribute of the CategoryEditor object
   *
   *@return    The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   *  Gets the id attribute of the CategoryEditor object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the moduleId attribute of the CategoryEditor object
   *
   *@return    The moduleId value
   */
  public int getModuleId() {
    return moduleId;
  }


  /**
   *  Gets the categoryId attribute of the CategoryEditor object
   *
   *@return    The categoryId value
   */
  public int getCategoryId() {
    return categoryId;
  }


  /**
   *  Gets the constantId attribute of the CategoryEditor object
   *
   *@return    The constantId value
   */
  public int getConstantId() {
    return constantId;
  }


  /**
   *  Gets the tableName attribute of the CategoryEditor object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the level attribute of the CategoryEditor object
   *
   *@return    The level value
   */
  public int getLevel() {
    return level;
  }


  /**
   *  Gets the description attribute of the CategoryEditor object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the maxLevels attribute of the CategoryEditor object
   *
   *@return    The maxLevels value
   */
  public int getMaxLevels() {
    return maxLevels;
  }


  /**
   *  Sets the categoryList attribute of the CategoryEditor object
   *
   *@param  categoryList  The new categoryList value
   */
  public void setCategoryList(HashMap categoryList) {
    this.categoryList = categoryList;
  }


  /**
   *  Sets the hierarchyCheck attribute of the CategoryEditor object
   *
   *@param  hierarchyCheck  The new hierarchyCheck value
   */
  public void setHierarchyCheck(java.util.Date hierarchyCheck) {
    this.hierarchyCheck = hierarchyCheck;
  }


  /**
   *  Sets the hierarchyUpdating attribute of the CategoryEditor object
   *
   *@param  hierarchyUpdating  The new hierarchyUpdating value
   */
  public void setHierarchyUpdating(boolean hierarchyUpdating) {
    this.hierarchyUpdating = hierarchyUpdating;
  }


  /**
   *  Sets the topCategoryList attribute of the CategoryEditor object
   *
   *@param  topCategoryList  The new topCategoryList value
   */
  public void setTopCategoryList(TicketCategoryDraftList topCategoryList) {
    this.topCategoryList = topCategoryList;
  }


  /**
   *  Gets the topCategoryList attribute of the CategoryEditor object
   *
   *@return    The topCategoryList value
   */
  public TicketCategoryDraftList getTopCategoryList() {
    return topCategoryList;
  }


  /**
   *  Gets the hierarchyCheck attribute of the CategoryEditor object
   *
   *@return    The hierarchyCheck value
   */
  public java.util.Date getHierarchyCheck() {
    return hierarchyCheck;
  }


  /**
   *  Gets the hierarchyUpdating attribute of the CategoryEditor object
   *
   *@return    The hierarchyUpdating value
   */
  public boolean getHierarchyUpdating() {
    return hierarchyUpdating;
  }


  /**
   *  Gets the category attribute of the CategoryEditor object
   *
   *@param  categoryId  Description of the Parameter
   *@return             The category value
   */
  public TicketCategoryDraft getCategory(int categoryId) {
    while (hierarchyUpdating) {
    }
    return (TicketCategoryDraft) categoryList.get(new Integer(categoryId));
  }


  /**
   *  Gets the categoryList attribute of the CategoryEditor object
   *
   *@return    The categoryList value
   */
  public HashMap getCategoryList() {
    while (hierarchyUpdating) {
    }
    return categoryList;
  }


  /**
   *  Build the draft categories
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void build(Connection db) throws SQLException {
    categoryList.clear();
    topCategoryList.clear();
    //Get the top level categories
    topCategoryList.setBuildHierarchy(false);
    topCategoryList.setTopLevelOnly(true);
    topCategoryList.buildList(db, tableName);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CategoryEditor -> buildList: Top Level Size " + topCategoryList.size());
    }
    //Build a list of all categories
    TicketCategoryDraftList fullCategoryList = new TicketCategoryDraftList();
    fullCategoryList.setBuildHierarchy(false);
    fullCategoryList.setTopLevelOnly(false);
    fullCategoryList.buildList(db, tableName);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildList: Full List " + fullCategoryList.size());
    }
    //Combine the lists
    Iterator listA = topCategoryList.iterator();
    while (listA.hasNext()) {
      TicketCategoryDraft thisCategory = (TicketCategoryDraft) listA.next();
      if (thisCategory != null) {
        categoryList.put(new Integer(thisCategory.getId()), thisCategory);
        this.addSubCategories(thisCategory, fullCategoryList);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> Total categories added : " + categoryList.size());
    }
  }


  /**
   *  Recursively add draft subcategories to a given draft category
   *
   *@param  parentCategory  The feature to be added to the SubCategories
   *      attribute
   *@param  fullList        The feature to be added to the SubCategories
   *      attribute
   */
  private void addSubCategories(TicketCategoryDraft parentCategory, TicketCategoryDraftList fullList) {
    if (parentCategory.getShortChildList() == null) {
      parentCategory.setShortChildList(new TicketCategoryDraftList());
    }
    Iterator i = fullList.iterator();
    while (i.hasNext()) {
      TicketCategoryDraft tmpCategory = (TicketCategoryDraft) i.next();
      if (tmpCategory.getParentCode() == parentCategory.getId()) {
        categoryList.put(new Integer(tmpCategory.getId()), tmpCategory);
        parentCategory.getShortChildList().add(tmpCategory);
        this.addSubCategories(tmpCategory, fullList);
      }
    }
  }


  /**
   *  Rebuilds the draft category list
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
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


  /**
   *  Updates all categories at a given level
   *
   *@param  catList           Description of the Parameter
   *@param  parentCode        Description of the Parameter
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean updateCategory(Connection db, String catList, int parentCode) throws SQLException {
    java.util.Date checkDate = new java.util.Date();
    if (checkDate.after(this.getHierarchyCheck())) {
      synchronized (this) {
        try {
          hierarchyUpdating = true;
          if (checkDate.after(hierarchyCheck)) {
            TicketCategoryDraft parentCategory = null;
            if (categoryList.containsKey(new Integer(parentCode))) {
              parentCategory = (TicketCategoryDraft) categoryList.get(new Integer(parentCode));
            }
            StringTokenizer st1 = new StringTokenizer(catList, "|");
            while (st1.hasMoreTokens()) {
              int catId = Integer.parseInt(st1.nextToken());
              String description = st1.nextToken();
              boolean enabled = "true".equals(st1.nextToken());
              TicketCategoryDraft thisCat = null;
              if (categoryList.containsKey(new Integer(catId))) {
                //update category
                thisCat = (TicketCategoryDraft) categoryList.get(new Integer(catId));
                thisCat.setDescription(description);
                if ((thisCat.getEnabled() && !enabled) || (!thisCat.getEnabled() && enabled)) {
                  toggleSubCategories(db, thisCat, enabled);
                } else {
                  thisCat.setEnabled(enabled);
                  thisCat.update(db, tableName);
                }
              } else {
                //add new category
                thisCat = new TicketCategoryDraft();
                thisCat.setDescription(description);
                if (parentCategory != null) {
                  thisCat.setParentCode(parentCategory.getId());
                  thisCat.setCategoryLevel(parentCategory.getCategoryLevel() + 1);
                  parentCategory.getShortChildList().add(thisCat);
                } else {
                  thisCat.setParentCode(0);
                  thisCat.setCategoryLevel(0);
                  topCategoryList.add(thisCat);
                }
                thisCat.insert(db, tableName);
                categoryList.put(new Integer(thisCat.getId()), thisCat);
              }
            }
            this.setHierarchyCheck(new java.util.Date());
          }
        } catch (Exception e) {
          System.out.println(e.toString());
        } finally {
          hierarchyUpdating = false;
        }
      }
    }
    return true;
  }


  /**
   *  Recursively disables all categories under a given category
   *
   *@param  thisCategory      Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  enabled           Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void toggleSubCategories(Connection db, TicketCategoryDraft thisCategory, boolean enabled) throws SQLException {
    //remove from universal list
    boolean recordDeleted = false;
    if (thisCategory.getActualCatId() == -1) {
      categoryList.remove(new Integer(thisCategory.getId()));
      //remove from parent
      if (thisCategory.getParentCode() > 0) {
        TicketCategoryDraft parentCategory = (TicketCategoryDraft) categoryList.get(new Integer(thisCategory.getParentCode()));
        parentCategory.removeChild(thisCategory.getId());
      }
      //check if it is a top level category
      if (thisCategory.getParentCode() == 0) {
        topCategoryList.remove(thisCategory);
      }
      recordDeleted = thisCategory.delete(db, tableName);
      if (!recordDeleted) {
        thisCategory.getErrors().put("actionError", systemStatus.getLabel("object.validation.actionError.ticketCategoryDeletion"));
      }
    } else {
      thisCategory.setEnabled(enabled);
      thisCategory.update(db, tableName);
    }
    if (thisCategory.getShortChildList() != null) {
      Iterator i = thisCategory.getShortChildList().iterator();
      while (i.hasNext()) {
        TicketCategoryDraft tmpCategory = (TicketCategoryDraft) i.next();
        this.toggleSubCategories(db, tmpCategory, enabled);
      }
    }
  }


  /**
   *  Resets the draft list to the active one.
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void reset(Connection db) throws SQLException {
    CategoryList activeTopCategoryList = new CategoryList(tableName);
    activeTopCategoryList.setParentCode(0);
    activeTopCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CategoryEditor -> buildList: Top Level Size " + activeTopCategoryList.size());
    }
    //Build a list of all categories
    CategoryList fullCategoryList = new CategoryList(tableName);
    fullCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildList: Full List " + fullCategoryList.size());
    }
    //Combine the lists
    Iterator listA = activeTopCategoryList.iterator();
    while (listA.hasNext()) {
      Category thisCategory = (Category) listA.next();
      if (thisCategory != null) {
        this.addActiveSubCategories(thisCategory, fullCategoryList);
      }
    }
    //delete the current draft
    boolean draftDeleted = TicketCategoryDraftList.deleteDraft(db, tableName);
    //Clone the active entries as a draft
    if (draftDeleted) {
      Iterator topList = activeTopCategoryList.iterator();
      while (topList.hasNext()) {
        Category thisCategory = (Category) topList.next();
        copyCategory(db, thisCategory, 0);
      }
      //now rebuild the draft category list
      update(db);
    }
  }


  /**
   *  Recursively copies data from an active category to a draft category at the
   *  database level.
   *
   *@param  db                Description of the Parameter
   *@param  activeCategory    Description of the Parameter
   *@param  parentCode        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void copyCategory(Connection db, Category activeCategory, int parentCode) throws SQLException {
    TicketCategoryDraft draftCategory = new TicketCategoryDraft();
    draftCategory.setDescription(activeCategory.getDescription());
    draftCategory.setActualCatId(activeCategory.getId());
    draftCategory.setLevel(activeCategory.getLevel());
    draftCategory.setCategoryLevel(activeCategory.getCategoryLevel());
    draftCategory.setEnabled(activeCategory.getEnabled());
    draftCategory.setParentCode(parentCode);
    draftCategory.insert(db, tableName);

    Iterator subCategories = activeCategory.getShortChildList().iterator();
    while (subCategories.hasNext()) {
      Category tmpCategory = (Category) subCategories.next();
      copyCategory(db, tmpCategory, draftCategory.getId());
    }
  }


  /**
   *  Builds active subcategories for a given category
   *
   *@param  parentCategory  The feature to be added to the ActiveSubCategories
   *      attribute
   *@param  fullList        The feature to be added to the ActiveSubCategories
   *      attribute
   */
  private void addActiveSubCategories(Category parentCategory, CategoryList fullList) {
    if (parentCategory.getShortChildList() == null) {
      parentCategory.setShortChildList(new TicketCategoryDraftList());
    }
    Iterator i = fullList.iterator();
    while (i.hasNext()) {
      Category tmpCategory = (Category) i.next();
      if (tmpCategory.getParentCode() == parentCategory.getId()) {
        parentCategory.getShortChildList().add(tmpCategory);
        this.addActiveSubCategories(tmpCategory, fullList);
      }
    }
  }


  /**
   *  Activates the draft categories
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean activate(Connection db) throws SQLException {
    Iterator i = topCategoryList.iterator();
    while (i.hasNext()) {
      TicketCategoryDraft thisCategory = (TicketCategoryDraft) i.next();
      activateCategory(db, thisCategory, 0);
    }
    return true;
  }


  /**
   *  Recursively activate a given category
   *
   *@param  db                Description of the Parameter
   *@param  draftCategory     Description of the Parameter
   *@param  parentCode        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void activateCategory(Connection db, TicketCategoryDraft draftCategory, int parentCode) throws SQLException {
    Category activeCategory = null;
    if (draftCategory.getActualCatId() > 0) {
      activeCategory = new Category(db, draftCategory.getActualCatId(), tableName);
      activeCategory.setDescription(draftCategory.getDescription());
      activeCategory.setLevel(draftCategory.getLevel());
      activeCategory.setCategoryLevel(draftCategory.getCategoryLevel());
      activeCategory.setEnabled(draftCategory.getEnabled());
      activeCategory.setParentCode(parentCode);
      activeCategory.update(db);
    } else {
      activeCategory = new Category(tableName);
      activeCategory.setDescription(draftCategory.getDescription());
      activeCategory.setLevel(draftCategory.getLevel());
      activeCategory.setCategoryLevel(draftCategory.getCategoryLevel());
      activeCategory.setEnabled(draftCategory.getEnabled());
      activeCategory.setParentCode(parentCode);
      activeCategory.insert(db);
    }

    //link draft to active category
    draftCategory.setActualCatId(activeCategory.getId());
    draftCategory.update(db, tableName);

    Iterator subCategories = draftCategory.getShortChildList().iterator();
    while (subCategories.hasNext()) {
      TicketCategoryDraft tmpCategory = (TicketCategoryDraft) subCategories.next();
      activateCategory(db, tmpCategory, activeCategory.getId());
    }
  }


  /**
   *  Returns a list of hierarchy from the top of a given category
   *
   *@param  categoryId  Description of the Parameter
   *@return             The hierarchyAsList value
   */
  public HashMap getHierarchyAsList(int categoryId) {
    int parentCode = categoryId;
    HashMap thisHierarchy = new HashMap();
    while (hierarchyUpdating) {
    }
    do {
      TicketCategoryDraft tmpCategory = (TicketCategoryDraft) categoryList.get(new Integer(parentCode));
      thisHierarchy.put(new Integer(tmpCategory.getCategoryLevel()), new Integer(parentCode));
      parentCode = tmpCategory.getParentCode();
    } while (parentCode != 0);

    return thisHierarchy;
  }


  /**
   *  Returns a list of immediate dependencies that a category has
   *
   *@param  catList  Description of the Parameter
   *@return          Description of the Return Value
   */
  public DependencyList processDependencies(String catList) {
    DependencyList dependencyList = new DependencyList();
    StringTokenizer st1 = new StringTokenizer(catList, "|");
    while (st1.hasMoreTokens()) {
      int catId = Integer.parseInt(st1.nextToken());
      st1.nextToken();
      boolean enabled = "true".equals(st1.nextToken());
      TicketCategoryDraft thisCat = null;
      if (categoryList.containsKey(new Integer(catId)) && !enabled) {
        thisCat = (TicketCategoryDraft) categoryList.get(new Integer(catId));
        if (thisCat.getEnabled() && thisCat.getShortChildList().size() > 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName(thisCat.getDescription());
          thisDependency.setCount(thisCat.getShortChildList().size());
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
    }
    return dependencyList;
  }
}


