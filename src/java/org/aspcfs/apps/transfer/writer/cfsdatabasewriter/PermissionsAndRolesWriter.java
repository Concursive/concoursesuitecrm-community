package org.aspcfs.apps.transfer.writer.cfsdatabasewriter;

import org.aspcfs.apps.transfer.*;
import java.util.*;
import java.util.logging.*;
import org.aspcfs.utils.*;
import java.sql.*;
import org.aspcfs.modules.admin.base.*;
import com.darkhorseventures.database.*;

public class PermissionsAndRolesWriter implements DataWriter {
  private ConnectionPool sqlDriver = null;
  private Connection db = null;
  private int id = -1;

  private String driver = null;
  private String url = null;
  private String user = null;
  private String pass = null;


  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  public void setUrl(String tmp) {
    this.url = tmp;
  }


  public void setUser(String tmp) {
    this.user = tmp;
  }


  public void setPass(String tmp) {
    this.pass = tmp;
  }


  public void setAutoCommit(boolean flag) {
  }


  public String getDriver() {
    return driver;
  }


  public String getUrl() {
    return url;
  }


  public String getUser() {
    return user;
  }


  public String getPass() {
    return pass;
  }


  public double getVersion() {
    return 1.0d;
  }


  public String getName() {
    return "Permissions and Roles Writer";
  }


  public String getDescription() {
    return "Inserts data directly into a CFS database";
  }


  public String getLastResponse() {
    return String.valueOf(id);
  }


  public boolean isConfigured() {
    String tmpUrl = System.getProperty("url");
    if (tmpUrl != null) {
      url = tmpUrl;
    }
    
    String tmpDriver = System.getProperty("driver");
    if (tmpDriver != null) {
      driver = tmpDriver;
    }
    
    String tmpUser = System.getProperty("user");
    if (tmpUser != null) {
      user = tmpUser;
    }
    
    String tmpPass = System.getProperty("pass");
    if (tmpPass != null) {
      pass = tmpPass;
    }
    
    if (url == null) {
      return false;
    }

    //Connect to database
    try {
      sqlDriver = new ConnectionPool();
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(1);
      ConnectionElement thisElement = new ConnectionElement(
          url, user, pass);
      thisElement.setDriver(driver);
      db = sqlDriver.getConnection(thisElement);
    } catch (SQLException e) {
      logger.info("Could not get database connection" + e.toString());
      return false;
    }
    return true;
  }


  public boolean save(DataRecord record) {
    try {
      if (record.getName().equals("permissionCategory")) {
        PermissionCategory thisCategory = new PermissionCategory();
        thisCategory.setCategory(record.getValue("category"));
        thisCategory.setLevel(record.getValue("level"));
        thisCategory.setEnabled(record.getValue("enabled"));
        thisCategory.setActive(record.getValue("active"));
        thisCategory.setFolders(record.getValue("folders"));
        thisCategory.setLookups(record.getValue("lookups"));
        thisCategory.insert(db);
        id = thisCategory.getId();
        return true;
      }
      
      if (record.getName().equals("permission")) {
        Permission thisPermission = new Permission();
        thisPermission.setCategoryId(record.getValue("categoryId"));
        thisPermission.setPermissionLevel(record.getValue("permissionLevel"));
        thisPermission.setName(record.getValue("name"));
        thisPermission.setDescription(record.getValue("description"));
        thisPermission.setView(record.getValue("view"));
        thisPermission.setAdd(record.getValue("add"));
        thisPermission.setEdit(record.getValue("edit"));
        thisPermission.setDelete(record.getValue("delete"));
        thisPermission.setEnabled(record.getValue("enabled"));
        thisPermission.setActive(record.getValue("active"));
        thisPermission.insert(db);
        id = thisPermission.getId();
        return true;
      }
      
      if (record.getName().equals("role")) {
        Role thisRole = new Role();
        thisRole.setRole(record.getValue("role"));
        thisRole.setDescription(record.getValue("description"));
        thisRole.setEnteredBy(0);
        thisRole.setModifiedBy(0);
        thisRole.insert(db);
        id = thisRole.getId();
        return true;
      }
      
      if (record.getName().equals("rolePermission")) {
        RolePermission thisRolePermission = new RolePermission();
        thisRolePermission.setRoleId(record.getValue("roleId"));
        thisRolePermission.setPermissionId(record.getValue("permissionId"));
        thisRolePermission.setView(record.getValue("view"));
        thisRolePermission.setAdd(record.getValue("add"));
        thisRolePermission.setEdit(record.getValue("edit"));
        thisRolePermission.setDelete(record.getValue("delete"));
        thisRolePermission.insert(db);
        id = thisRolePermission.getId();
        return true;
      }
    } catch (Exception ex) {
      ex.printStackTrace(System.out);
      logger.info(ex.toString());
      return false;
    }
    return true;
  }


  public boolean commit() {
    return true;
  }


  public boolean rollback() {
    return true;
  }
  
  public boolean load(DataRecord record) {
    return false;
  }
  
  public boolean close() {
    if (db != null) {
      try {
        db.close();
      } catch (Exception e) {
      }
    }
    return true;
  }
}


