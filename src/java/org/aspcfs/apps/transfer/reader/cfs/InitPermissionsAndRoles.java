package org.aspcfs.apps.transfer.reader.cfs;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.aspcfs.apps.transfer.*;
import org.aspcfs.apps.transfer.writer.cfshttpxmlwriter.CFSHttpXMLWriter;
import org.aspcfs.utils.*;

public class InitPermissionsAndRoles implements DataReader {
  public final static String fs = System.getProperty("file.separator");
  private String processConfigFile = "InitPermissions.xml";
  
  public void setProcessConfigFile(String tmp) { this.processConfigFile = tmp; }
  public String getProcessConfigFile() { return processConfigFile; }
  
  public double getVersion() {
    return 1.0d;
  }


  public String getName() {
    return "Dark Horse CRM Permissions and Roles XML Reader";
  }


  public String getDescription() {
    return "Reads permissions and categories; reads in associated roles";
  }


  public boolean isConfigured() {
    boolean configOK = true;

    String tmpFile = System.getProperty("processConfigFile");
    if (tmpFile != null && !"".equals(tmpFile)) {
      processConfigFile = tmpFile;
    }
    
    File configFile = new File(processConfigFile);
    if (!configFile.exists()) {
      logger.info("InitPermissionsAndRoles-> Config: process config file not found: " + processConfigFile);
      configOK = false;
    }

    return configOK;
  }


  public boolean execute(DataWriter writer) {
    boolean processOK = true;
    
    try {
      File configFile = new File(processConfigFile);
      XMLUtils xml = new XMLUtils(configFile);
      
      //Read in all of the permission categories (categories and permissions)
      ArrayList categoryList = new ArrayList();
      xml.getAllChildren(xml.getFirstChild("permissions"), "category", categoryList);
      logger.info("Categories: " + categoryList.size());
      Comparator comparator = new CategoryElementComparator();
      Object sortArray[] = categoryList.toArray();
      Arrays.sort(sortArray, comparator);
      
      ArrayList sortedCategoryList = new ArrayList();
      for (int i = 0; i < sortArray.length; i++) {
        sortedCategoryList.add((Element) sortArray[i]);
      }
      
      //Insert the category, then insert the permission, keep a hashmap of permission IDs for
      //processing roles
      HashMap permissionIds = new HashMap();
      Iterator sortedCategoryItems = sortedCategoryList.iterator();
      while (sortedCategoryItems.hasNext()) {
        Element category = (Element) sortedCategoryItems.next();
        int categoryLevel = (categoryList.indexOf(category) + 1) * 100;
        DataRecord thisRecord = new DataRecord();
        thisRecord.setName("permissionCategory");
        thisRecord.setAction("insert");
        thisRecord.addField("category", (String) category.getAttribute("name"));
        thisRecord.addField("level", String.valueOf(categoryLevel));
        //The default is true, if not set in the XML
        if ("false".equals((String) category.getAttribute("enabled"))) {
          thisRecord.addField("enabled", "false");
          thisRecord.addField("active", "false");
        } else {
          thisRecord.addField("enabled", "true");
          thisRecord.addField("active", "true");
        }
        thisRecord.addField("folders", (String) category.getAttribute("folders"));
        thisRecord.addField("lookups", (String) category.getAttribute("lookups"));
        thisRecord.addField("viewpoints", (String) category.getAttribute("viewpoints"));
        thisRecord.addField("reports", (String) category.getAttribute("reports"));
        processOK = writer.save(thisRecord);
        int categoryId = Integer.parseInt(writer.getLastResponse());
        
        //Insert any permissions under this category
        ArrayList permissionList = new ArrayList();
        XMLUtils.getAllChildren(category, "permission", permissionList);
        Iterator permissionItems = permissionList.iterator();
        int permissionLevel = 0;
        while (permissionItems.hasNext()) {
          permissionLevel = permissionLevel + 10;
          Element permission = (Element) permissionItems.next();
          DataRecord permissionRecord = new DataRecord();
          permissionRecord.setName("permission");
          permissionRecord.setAction("insert");
          permissionRecord.addField("categoryId", String.valueOf(categoryId));
          permissionRecord.addField("permissionLevel", String.valueOf(permissionLevel));
          permissionRecord.addField("name", (String) permission.getAttribute("name"));
          permissionRecord.addField("description", (String) permission.getAttribute("description"));
          String attributes = (String) permission.getAttribute("attributes");
          permissionRecord.addField("view", String.valueOf(attributes.indexOf("v") > -1));
          permissionRecord.addField("add", String.valueOf(attributes.indexOf("a") > -1));
          permissionRecord.addField("edit", String.valueOf(attributes.indexOf("e") > -1));
          permissionRecord.addField("delete", String.valueOf(attributes.indexOf("d") > -1));
          //The default is true, if not set in the XML
          if ("false".equals((String) permission.getAttribute("enabled"))) {
            permissionRecord.addField("enabled", "false");
            permissionRecord.addField("active", "false");
          } else {
            permissionRecord.addField("enabled", "true");
            permissionRecord.addField("active", "true");
          }
          if ("true".equals((String) permission.getAttribute("viewpoints"))) {
            permissionRecord.addField("viewpoints", "true");
          } else {
            permissionRecord.addField("viewpoints", "false");
          }
          writer.save(permissionRecord);
          int permissionId = Integer.parseInt(writer.getLastResponse());
          permissionIds.put((String) permission.getAttribute("name"), new Integer(permissionId));
        }
        
        //Insert any folders under this category
        ArrayList folderList = new ArrayList();
        XMLUtils.getAllChildren(category, "folder", folderList);
        Iterator folderItems = folderList.iterator();
        int folderLevel = 0;
        while (folderItems.hasNext()) {
          folderLevel = folderLevel + 10;
          Element folder = (Element) folderItems.next();
          DataRecord folderRecord = new DataRecord();
          folderRecord.setName("folder");
          folderRecord.setAction("insert");
          folderRecord.addField("moduleId", String.valueOf(categoryId));
          folderRecord.addField("categoryId", (String) folder.getAttribute("constantId"));
          folderRecord.addField("level", String.valueOf(folderLevel));
          folderRecord.addField("description", (String) folder.getAttribute("description"));
          writer.save(folderRecord);
        }
        
        //Insert any lookups under this category
        ArrayList lookupList = new ArrayList();
        XMLUtils.getAllChildren(category, "lookup", lookupList);
        Iterator lookupItems = lookupList.iterator();
        int lookupLevel = 0;
        while (lookupItems.hasNext()) {
          lookupLevel = lookupLevel + 10;
          Element lookup = (Element) lookupItems.next();
          DataRecord lookupRecord = new DataRecord();
          lookupRecord.setName("lookup");
          lookupRecord.setAction("insert");
          lookupRecord.addField("moduleId", String.valueOf(categoryId));
          lookupRecord.addField("lookupId", (String) lookup.getAttribute("constantId"));
          lookupRecord.addField("class", (String) lookup.getAttribute("class"));
          lookupRecord.addField("table", (String) lookup.getAttribute("table"));
          lookupRecord.addField("level", String.valueOf(lookupLevel));
          lookupRecord.addField("description", (String) lookup.getAttribute("description"));
          lookupRecord.addField("categoryId", String.valueOf(categoryId));
          writer.save(lookupRecord);
        }
        
        //Insert any reports under this category
        ArrayList reportList = new ArrayList();
        XMLUtils.getAllChildren(category, "report", reportList);
        Iterator reportItems = reportList.iterator();
        while (reportItems.hasNext()) {
          Element report = (Element) reportItems.next();
          DataRecord reportRecord = new DataRecord();
          reportRecord.setName("report");
          reportRecord.setAction("insert");
          reportRecord.addField("categoryId", String.valueOf(categoryId));
          //TODO: Lookup the id if there is one
          //reportRecord.addField("permissionId", String.valueOf(permissionId));
          reportRecord.addField("permissionId", -1);
          reportRecord.addField("file", (String) report.getAttribute("file"));
          reportRecord.addField("type", (String) report.getAttribute("type"));
          reportRecord.addField("title", (String) report.getAttribute("title"));
          reportRecord.addField("description", (String) report.getAttribute("description"));
          writer.save(reportRecord);
        }
      }
      
      //Read in all of the roles and associate with previously read in permissions so IDs match
      ArrayList roleList = new ArrayList();
      xml.getAllChildren(xml.getFirstChild("roles"), "role", roleList);
      logger.info("Roles: " + roleList.size());
      Iterator roleItems = roleList.iterator();
      while (roleItems.hasNext()) {
        Element role = (Element) roleItems.next();
        DataRecord thisRecord = new DataRecord();
        thisRecord.setName("role");
        thisRecord.setAction("insert");
        thisRecord.addField("role", (String) role.getAttribute("name"));
        thisRecord.addField("description", (String) role.getAttribute("description"));
        writer.save(thisRecord);
        int roleId = Integer.parseInt(writer.getLastResponse());
        
        //Process the permissions within the role
        ArrayList rolePermissionList = new ArrayList();
        xml.getAllChildren(role, "permission", rolePermissionList);
        Iterator permissionList = rolePermissionList.iterator();
        while (permissionList.hasNext()) {
          Element rolePermission = (Element) permissionList.next();
          DataRecord rolePermissionRecord = new DataRecord();
          rolePermissionRecord.setName("rolePermission");
          rolePermissionRecord.setAction("insert");
          rolePermissionRecord.addField("roleId", String.valueOf(roleId));
          rolePermissionRecord.addField("permissionId", ((Integer) permissionIds.get((String) rolePermission.getAttribute("name"))).intValue());
          String attributes = (String) rolePermission.getAttribute("attributes");
          rolePermissionRecord.addField("view", String.valueOf(attributes.indexOf("v") > -1));
          rolePermissionRecord.addField("add", String.valueOf(attributes.indexOf("a") > -1));
          rolePermissionRecord.addField("edit", String.valueOf(attributes.indexOf("e") > -1));
          rolePermissionRecord.addField("delete", String.valueOf(attributes.indexOf("d") > -1));
          writer.save(rolePermissionRecord);
        }
      }
    } catch (Exception e) {
      logger.info("Error reading parsing configuration-> " + e.toString());
      processOK = false;
    }
    return processOK;
  }
  
  class CategoryElementComparator implements Comparator {
    public int compare(Object left, Object right) {
      int a = Integer.parseInt((String) ((Element) left).getAttribute("id"));
      int b = Integer.parseInt((String) ((Element) right).getAttribute("id"));
      return (new Integer(a).compareTo(new Integer(b)));
    }
  }
}

