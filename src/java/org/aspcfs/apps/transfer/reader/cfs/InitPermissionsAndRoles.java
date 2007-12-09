/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.apps.transfer.reader.cfs;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataReader;
import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.util.*;
import java.net.URL;

/**
 * Processes the permissions.xml file, turns the data into objects and can be
 * used by a writer. Specifically, this file is used during the installation of
 * a new database.
 *
 * @author matt rajkowski
 * @version $Id: InitPermissionsAndRoles.java,v 1.19 2004/06/15 14:27:26
 *          mrajkowski Exp $
 * @created January 23, 2003
 */
public class InitPermissionsAndRoles implements DataReader {

  private static final Logger logger = Logger.getLogger(org.aspcfs.apps.transfer.reader.cfs.InitPermissionsAndRoles.class);

  public final static String fs = System.getProperty("file.separator");
  private String processConfigFile = "InitPermissions.xml";
  private URL processConfigURL = null;


  /**
   * Sets the processConfigFile attribute of the InitPermissionsAndRoles object
   *
   * @param tmp The new processConfigFile value
   */
  public void setProcessConfigFile(String tmp) {
    this.processConfigFile = tmp;
  }


  /**
   * Gets the processConfigFile attribute of the InitPermissionsAndRoles object
   *
   * @return The processConfigFile value
   */
  public String getProcessConfigFile() {
    return processConfigFile;
  }

  public URL getProcessConfigURL() {
    return processConfigURL;
  }

  public void setProcessConfigURL(URL processConfigURL) {
    this.processConfigURL = processConfigURL;
  }

  /**
   * Gets the version attribute of the InitPermissionsAndRoles object
   *
   * @return The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   * Gets the name attribute of the InitPermissionsAndRoles object
   *
   * @return The name value
   */
  public String getName() {
    return "Concourse Suite Community Edition Permissions and Roles XML Reader";
  }


  /**
   * Gets the description attribute of the InitPermissionsAndRoles object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Reads permissions and categories; reads in associated roles";
  }


  /**
   * Gets the configured attribute of the InitPermissionsAndRoles object
   *
   * @return The configured value
   */
  public boolean isConfigured() {
    boolean configOK = true;

    String tmpFile = System.getProperty("processConfigFile");
    if (tmpFile != null && !"".equals(tmpFile)) {
      processConfigFile = tmpFile;
    }

    File configFile = new File(processConfigFile);
    if (!configFile.exists()) {
      logger.info(
          "InitPermissionsAndRoles-> Config: process config file not found: " + processConfigFile);
      configOK = false;
    }

    return configOK;
  }


  /**
   * Description of the Method
   *
   * @param writer Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    boolean processOK = true;

    try {
      XMLUtils xml = null;
      if (processConfigURL != null) {
        xml = new XMLUtils(processConfigURL);
      } else {
        File configFile = new File(processConfigFile);
        xml = new XMLUtils(configFile);
      }

      //Read in all of the permission categories (categories and permissions)
      ArrayList categoryList = new ArrayList();
      XMLUtils.getAllChildren(
          xml.getFirstChild("permissions"), "category", categoryList);
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
        String uniqueCategoryId = (String) category.getAttribute("id");
        int categoryLevel = (categoryList.indexOf(category) + 1) * 100;
        DataRecord thisRecord = new DataRecord();
        thisRecord.setName("permissionCategory");
        thisRecord.setAction("insert");
        thisRecord.addField("constant", (String) category.getAttribute("id"));
        thisRecord.addField(
            "category", (String) category.getAttribute("name"));
        thisRecord.addField("level", String.valueOf(categoryLevel));
        //The default is true, if not set in the XML
        if ("false".equals((String) category.getAttribute("enabled"))) {
          thisRecord.addField("enabled", "false");
          thisRecord.addField("active", "false");
        } else {
          thisRecord.addField("enabled", "true");
          thisRecord.addField("active", "true");
        }
        thisRecord.addField(
            "folders", (String) category.getAttribute("folders"));
        thisRecord.addField(
            "lookups", (String) category.getAttribute("lookups"));
        thisRecord.addField(
            "viewpoints", (String) category.getAttribute("viewpoints"));
        thisRecord.addField(
            "reports", (String) category.getAttribute("reports"));
        thisRecord.addField(
            "scheduledEvents", (String) category.getAttribute(
            "scheduledEvents"));
        thisRecord.addField(
            "objectEvents", (String) category.getAttribute("objectEvents"));
        thisRecord.addField(
            "categories", (String) category.getAttribute("categories"));
        thisRecord.addField(
            "products", (String) category.getAttribute("products"));
        thisRecord.addField(
            "importer", (String) category.getAttribute("importer"));
        thisRecord.addField(
            "webdav", (String) category.getAttribute("webdav"));
        thisRecord.addField("logos", (String) category.getAttribute("logos"));
        thisRecord.addField("actionPlans", (String) category.getAttribute("actionPlans"));
        thisRecord.addField("dashboards", (String) category.getAttribute("dashboards"));
        thisRecord.addField("customtabs", (String) category.getAttribute("customtabs"));
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
          permissionRecord.addField(
              "permissionLevel", String.valueOf(permissionLevel));
          permissionRecord.addField(
              "name", (String) permission.getAttribute("name"));
          permissionRecord.addField(
              "description", (String) permission.getAttribute("description"));
          String attributes = (String) permission.getAttribute("attributes");
          permissionRecord.addField(
              "view", String.valueOf(attributes.indexOf("v") > -1));
          permissionRecord.addField(
              "add", String.valueOf(attributes.indexOf("a") > -1));
          permissionRecord.addField(
              "edit", String.valueOf(attributes.indexOf("e") > -1));
          permissionRecord.addField(
              "delete", String.valueOf(attributes.indexOf("d") > -1));
          String offline = (String) permission.getAttribute("offline");
          permissionRecord.addField(
              "offlineView", String.valueOf(offline.indexOf("v") > -1));
          permissionRecord.addField(
              "offlineAdd", String.valueOf(offline.indexOf("a") > -1));
          permissionRecord.addField(
              "offlineEdit", String.valueOf(offline.indexOf("e") > -1));
          permissionRecord.addField(
              "offlineDelete", String.valueOf(offline.indexOf("d") > -1));
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
          permissionIds.put(
              (String) permission.getAttribute("name"), new Integer(
              permissionId));
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
          folderRecord.addField(
              "categoryId", (String) folder.getAttribute("constantId"));
          folderRecord.addField("level", String.valueOf(folderLevel));
          folderRecord.addField(
              "description", (String) folder.getAttribute("description"));
          writer.save(folderRecord);
        }

        //Insert any action plan editors under this category
        ArrayList actionPlanEditorList = new ArrayList();
        XMLUtils.getAllChildren(category, "actionPlanEditor", actionPlanEditorList);
        Iterator planEditorItems = actionPlanEditorList.iterator();
        int editorLevel = 0;
        while (planEditorItems.hasNext()) {
          editorLevel = editorLevel + 10;
          Element planEditor = (Element) planEditorItems.next();
          DataRecord planEditorRecord = new DataRecord();
          planEditorRecord.setName("planEditor");
          planEditorRecord.setAction("insert");
          planEditorRecord.addField("moduleId", String.valueOf(categoryId));
          planEditorRecord.addField("categoryId", uniqueCategoryId);
          planEditorRecord.addField("constantId", (String) planEditor.getAttribute("constantId"));
          planEditorRecord.addField("level", String.valueOf(editorLevel));
          planEditorRecord.addField("description", (String) planEditor.getAttribute("description"));
          writer.save(planEditorRecord);
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
          lookupRecord.addField(
              "lookupId", (String) lookup.getAttribute("constantId"));
          lookupRecord.addField(
              "class", (String) lookup.getAttribute("class"));
          lookupRecord.addField(
              "table", (String) lookup.getAttribute("table"));
          lookupRecord.addField("level", String.valueOf(lookupLevel));
          lookupRecord.addField(
              "description", (String) lookup.getAttribute("description"));
          lookupRecord.addField("categoryId", uniqueCategoryId);
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
          reportRecord.addField(
              "permissionId", ((Integer) permissionIds.get(
              report.getAttribute("permission"))).intValue());
          reportRecord.addField("file", (String) report.getAttribute("file"));
          String type = report.getAttribute("type");
          if ("admin".equals(type)) {
            reportRecord.addField("type", "2");
          } else {
            reportRecord.addField("type", "1");
          }
          reportRecord.addField(
              "title", (String) report.getAttribute("title"));
          reportRecord.addField(
              "description", (String) report.getAttribute("description"));
          reportRecord.addField("enteredBy", "0");
          reportRecord.addField("modifiedBy", "0");
          writer.save(reportRecord);
        }

        //Insert any multiple categories under this category
        ArrayList multipleCategory = new ArrayList();
        XMLUtils.getAllChildren(
            category, "multipleCategory", multipleCategory);
        Iterator multipleItems = multipleCategory.iterator();
        int multipleLevel = 0;
        while (multipleItems.hasNext()) {
          multipleLevel = multipleLevel + 10;
          Element multiple = (Element) multipleItems.next();
          DataRecord multipleRecord = new DataRecord();
          multipleRecord.setName("multipleCategory");
          multipleRecord.setAction("insert");
          multipleRecord.addField("moduleId", String.valueOf(categoryId));
          multipleRecord.addField("categoryId", uniqueCategoryId);
          multipleRecord.addField(
              "constantId", (String) multiple.getAttribute("constantId"));
          multipleRecord.addField(
              "table", (String) multiple.getAttribute("table"));
          multipleRecord.addField("level", String.valueOf(multipleLevel));
          multipleRecord.addField(
              "description", (String) multiple.getAttribute("description"));
          multipleRecord.addField(
              "maxLevels", (String) multiple.getAttribute("maxLevels"));
          writer.save(multipleRecord);
        }

        //Insert any webdav modules under this category
        ArrayList webdavList = new ArrayList();
        XMLUtils.getAllChildren(category, "webdav", webdavList);
        Iterator webdavItems = webdavList.iterator();
        while (webdavItems.hasNext()) {
          Element webdav = (Element) webdavItems.next();
          DataRecord webdavRecord = new DataRecord();
          webdavRecord.setName("webdav");
          webdavRecord.setAction("insert");
          webdavRecord.addField("categoryId", String.valueOf(categoryId));
          webdavRecord.addField(
              "class", (String) webdav.getAttribute("class"));
          writer.save(webdavRecord);
        }
      }

      //Read in all of the roles and associate with previously read in permissions so IDs match
      ArrayList roleList = new ArrayList();
      XMLUtils.getAllChildren(xml.getFirstChild("roles"), "role", roleList);
      logger.info("Roles: " + roleList.size());
      Iterator roleItems = roleList.iterator();
      while (roleItems.hasNext()) {
        Element role = (Element) roleItems.next();
        DataRecord thisRecord = new DataRecord();
        thisRecord.setName("role");
        thisRecord.setAction("insert");
        thisRecord.addField("role", (String) role.getAttribute("name"));
        thisRecord.addField(
            "description", (String) role.getAttribute("description"));
        thisRecord.addField(
            "type", (((role.getAttribute("type") == null) || ("".equals(
            role.getAttribute("type")))) ? "0" : (String) role.getAttribute(
            "type")));
        if ("false".equals((String) role.getAttribute("enabled"))) {
          thisRecord.addField("enabled", "false");
        } else {
          thisRecord.addField("enabled", "true");
        }
        writer.save(thisRecord);
        int roleId = Integer.parseInt(writer.getLastResponse());

        //Process the permissions within the role
        ArrayList rolePermissionList = new ArrayList();
        XMLUtils.getAllChildren(role, "permission", rolePermissionList);
        Iterator permissionList = rolePermissionList.iterator();
        while (permissionList.hasNext()) {
          Element rolePermission = (Element) permissionList.next();
          DataRecord rolePermissionRecord = new DataRecord();
          rolePermissionRecord.setName("rolePermission");
          rolePermissionRecord.setAction("insert");
          rolePermissionRecord.addField("roleId", String.valueOf(roleId));
          rolePermissionRecord.addField(
              "permissionId", ((Integer) permissionIds.get(
              (String) rolePermission.getAttribute("name"))).intValue());
          String attributes = (String) rolePermission.getAttribute(
              "attributes");
          rolePermissionRecord.addField(
              "view", String.valueOf(attributes.indexOf("v") > -1));
          rolePermissionRecord.addField(
              "add", String.valueOf(attributes.indexOf("a") > -1));
          rolePermissionRecord.addField(
              "edit", String.valueOf(attributes.indexOf("e") > -1));
          rolePermissionRecord.addField(
              "delete", String.valueOf(attributes.indexOf("d") > -1));
          String offline = (String) rolePermission.getAttribute(
          "attributes");
          rolePermissionRecord.addField(
              "offlineView", String.valueOf(offline.indexOf("v") > -1));
          rolePermissionRecord.addField(
              "offlineAdd", String.valueOf(offline.indexOf("a") > -1));
          rolePermissionRecord.addField(
              "offlineEdit", String.valueOf(offline.indexOf("e") > -1));
          rolePermissionRecord.addField(
              "offlineDelete", String.valueOf(offline.indexOf("d") > -1));
          writer.save(rolePermissionRecord);
        }
      }
    } catch (Exception e) {
      logger.info("Error reading parsing configuration-> " + e.toString());
      processOK = false;
    }
    return processOK;
  }


  /**
   * Used for comparing this object by id
   *
   * @author matt rajkowski
   * @version $Id: InitPermissionsAndRoles.java,v 1.19 2004/06/15 14:27:26
   *          mrajkowski Exp $
   * @created January 23, 2003
   */
  class CategoryElementComparator implements Comparator {
    /**
     * Description of the Method
     *
     * @param left  Description of the Parameter
     * @param right Description of the Parameter
     * @return Description of the Return Value
     */
    public int compare(Object left, Object right) {
      int a = Integer.parseInt((String) ((Element) left).getAttribute("id"));
      int b = Integer.parseInt((String) ((Element) right).getAttribute("id"));
      return (new Integer(a).compareTo(new Integer(b)));
    }
  }
}

