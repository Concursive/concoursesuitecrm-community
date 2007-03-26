package org.aspcfs.modules.base;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Jun 6, 2005
 */

public class ModuleFieldCategoryLink extends GenericBean {
  private int id = -1;
  private int moduleId = -1;
  private int categoryId = -1;;
  private int level = -1;;
  private String description = null;
  private Timestamp entered = null;
  private Timestamp modified = null;

  public ModuleFieldCategoryLink() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getModuleId() {
    return moduleId;
  }

  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }

  public void setModuleId(String moduleId) {
    this.moduleId = Integer.parseInt(moduleId);
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = Integer.parseInt(categoryId);
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setLevel(String level) {
    this.level = Integer.parseInt(level);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void insert(Connection db) throws SQLException {
    id = DatabaseUtils.getNextSeq(db, "module_field_categorylin_id_seq");
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO module_field_categorylink " +
        "(" + (id > -1 ? "id, " : "") + "module_id, category_id, " + DatabaseUtils.addQuotes(db, "level") +
        ", entered, modified" +
        ", description) " +
        "VALUES (" + (id > -1 ? "?, " : "") + "?, ?, ?, " +
        (entered != null ? "?, " : (DatabaseUtils.getCurrentTimestamp(db) + ", ")) +
        (modified != null ? "?, " : (DatabaseUtils.getCurrentTimestamp(db) + ", ")) +
        "?) ");
    
    int i = 0;
    if (id > -1) {
      pst.setInt(++i, id);
    }
    pst.setInt(++i, moduleId);
    pst.setInt(++i, categoryId);
    pst.setInt(++i, level);
    if (entered != null) {
      pst.setTimestamp(i++, entered);
    }
    if (modified != null) {
      pst.setTimestamp(i++, modified);
    }
    pst.setString(++i, description);
    pst.executeUpdate();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "module_field_categorylin_id_seq", id);
  }
  
  public Timestamp getEntered() {
    return entered;
  }
  public Timestamp getModified() {
    return modified;
  }
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }
}