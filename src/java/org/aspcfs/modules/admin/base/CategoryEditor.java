//Copyright 2002-2003 Dark Horse Ventures

package org.aspcfs.modules.admin.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.troubletickets.base.*;

/**
 *  Maintains Category Editors for various components
 *
 *@author     akhi_m
 *@created    May 22, 2003
 *@version    $id: exp$
 */
public class CategoryEditor {

  private HashMap categoryList = new HashMap();
  private TicketCategoryDraftList topCategoryList = new TicketCategoryDraftList();
  private java.util.Date hierarchyCheck = new java.util.Date();
  private boolean hierarchyUpdating = false;


  /**
   *Constructor for the CategoryEditor object
   */
  public CategoryEditor() { }


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
    //Get the top level categories
    topCategoryList = new TicketCategoryDraftList();
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
   *@param  parentCategory  The feature to be added to the SubCategories attribute
   *@param  fullList        The feature to be added to the SubCategories attribute
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
                  thisCat.update(db);
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
                thisCat.insert(db);
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
    if (thisCategory.getActualCatId() == -1) {
      categoryList.remove(new Integer(thisCategory.getId()));
      //remove from parent
      if (thisCategory.getParentCode() > 0) {
        TicketCategoryDraft parentCategory = (TicketCategoryDraft) categoryList.get(new Integer(thisCategory.getParentCode()));
        parentCategory.removeChild(thisCategory.getId());
      }

      //check if it is a top level category
      if (thisCategory.getParentCode() == 0) {
        topCategoryList.remove(new Integer(thisCategory.getId()));
      }
      thisCategory.delete(db);
    } else {
      thisCategory.setEnabled(enabled);
      thisCategory.update(db);
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
    TicketCategoryList activeTopCategoryList = new TicketCategoryList();
    activeTopCategoryList.setParentCode(0);
    activeTopCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("CategoryEditor -> buildList: Top Level Size " + activeTopCategoryList.size());
    }

    //Build a list of all categories
    TicketCategoryList fullCategoryList = new TicketCategoryList();
    fullCategoryList.buildList(db);
    if (System.getProperty("DEBUG") != null) {
      System.out.println("SystemStatus-> buildList: Full List " + fullCategoryList.size());
    }

    //Combine the lists
    Iterator listA = activeTopCategoryList.iterator();
    while (listA.hasNext()) {
      TicketCategory thisCategory = (TicketCategory) listA.next();
      if (thisCategory != null) {
        this.addActiveSubCategories(thisCategory, fullCategoryList);
      }
    }

    //delete the current draft
    boolean draftDeleted = TicketCategoryDraftList.deleteDraft(db);

    //Clone the active entries as a draft
    if (draftDeleted) {
      Iterator topList = activeTopCategoryList.iterator();
      while (topList.hasNext()) {
        TicketCategory thisCategory = (TicketCategory) topList.next();
        copyCategory(db, thisCategory, 0);
      }
      //now rebuild the draft category list
      update(db);
    }
  }


  /**
   *  Recursively copies data from an active category to  a draft category at the database level.
   *
   *@param  db                Description of the Parameter
   *@param  activeCategory    Description of the Parameter
   *@param  parentCode        Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void copyCategory(Connection db, TicketCategory activeCategory, int parentCode) throws SQLException {
    TicketCategoryDraft draftCategory = new TicketCategoryDraft();
    draftCategory.setDescription(activeCategory.getDescription());
    draftCategory.setActualCatId(activeCategory.getId());
    draftCategory.setLevel(activeCategory.getLevel());
    draftCategory.setCategoryLevel(activeCategory.getCategoryLevel());
    draftCategory.setEnabled(activeCategory.getEnabled());
    draftCategory.setParentCode(parentCode);
    draftCategory.insert(db);

    Iterator subCategories = activeCategory.getShortChildList().iterator();
    while (subCategories.hasNext()) {
      TicketCategory tmpCategory = (TicketCategory) subCategories.next();
      copyCategory(db, tmpCategory, draftCategory.getId());
    }
  }


  /**
   *  Builds active subcategories for a given category
   *
   *@param  parentCategory  The feature to be added to the ActiveSubCategories attribute
   *@param  fullList        The feature to be added to the ActiveSubCategories attribute
   */
  private void addActiveSubCategories(TicketCategory parentCategory, TicketCategoryList fullList) {
    if (parentCategory.getShortChildList() == null) {
      parentCategory.setShortChildList(new TicketCategoryDraftList());
    }
    Iterator i = fullList.iterator();
    while (i.hasNext()) {
      TicketCategory tmpCategory = (TicketCategory) i.next();
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

    TicketCategory activeCategory = null;
    if (draftCategory.getActualCatId() > 0) {
      activeCategory = new TicketCategory(db, draftCategory.getActualCatId());
      activeCategory.setDescription(draftCategory.getDescription());
      activeCategory.setLevel(draftCategory.getLevel());
      activeCategory.setCategoryLevel(draftCategory.getCategoryLevel());
      activeCategory.setEnabled(draftCategory.getEnabled());
      activeCategory.setParentCode(parentCode);
      activeCategory.update(db);
    } else {
      activeCategory = new TicketCategory();
      activeCategory.setDescription(draftCategory.getDescription());
      activeCategory.setLevel(draftCategory.getLevel());
      activeCategory.setCategoryLevel(draftCategory.getCategoryLevel());
      activeCategory.setEnabled(draftCategory.getEnabled());
      activeCategory.setParentCode(parentCode);
      activeCategory.insert(db);
    }
    
    //link draft to active category
    draftCategory.setActualCatId(activeCategory.getId());
    draftCategory.update(db);

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


