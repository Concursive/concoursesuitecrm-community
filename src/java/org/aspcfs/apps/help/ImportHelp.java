package org.aspcfs.apps.help;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.database.*;
import org.aspcfs.modules.admin.base.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    November 26, 2003
 *@version    $Id$
 */
public class ImportHelp {

  ArrayList helpModules;
  HelpModule helpModule = null;
  HelpContent helpContent = null;

  //Place holder for permission categories existing in the new database
  ArrayList permissionCategories;

  //Place holder for items in the table of contents
  ArrayList tableOfContents;


  /**
   *  Constructor for the ImportHelp object
   */
  public ImportHelp() { }


  /**
   *  Constructor for the ImportHelp object
   *
   *@param  Args  Description of the Parameter
   */
  public ImportHelp(String[] args) {

    helpModules = new ArrayList();
    permissionCategories = new ArrayList();
    tableOfContents = new ArrayList();

    ConnectionPool sqlDriver = null;
    System.out.println("Starting...");

    try {
      sqlDriver = new ConnectionPool();
    } catch (SQLException e) {
      System.err.println(e);
    }
    Connection db = null;

    try {
     String filePath = args[0];
 
      String driver = args[1];
      String uri = args[2];
      String username = args[3];
      String passwd = "";
      if (args.length == 5)
        passwd = args[4];
 
      
      sqlDriver.setForceClose(false);
      sqlDriver.setMaxConnections(5);
      //Test a single connection
      ConnectionElement thisElement = new ConnectionElement(uri, username, passwd);
      thisElement.setDriver(driver);

      db = sqlDriver.getConnection(thisElement);

      System.out.println("Reading Help Date from XML");
      buildHelpInformation(filePath);
      buildExistingPermissionCategories(db);

      System.out.println("Insert data into help_module, help_contents, features, tips, etc.");
      insertHelpRecords(db);

      System.out.println("Building Table of Contents");
      buildTableOfContents();
      System.out.println("Inserting table of content records");
      insertTableOfContents(db);

      sqlDriver.free(db);
      System.out.println("Inserted records and released database connection");
    } catch (Exception e) {
      System.err.println(e);
    }
    finally{
     System.exit(0); 
    }
  }


  /**
   *  The main program for the ImportHelp class
   *
   *@param  Args  The command line arguments
   */
  public static void main(String[] args) {

    if (( args.length != 4) && ( args.length != 5))
      System.out.println("Synopsis: java ExportHelp [filepath][driver][uri][user] <passwd>");
    else
      new ImportHelp(args);

  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean insertHelpRecords(Connection db) throws SQLException {
    // Inserting a records correspoding to a permission category
    Iterator itr = helpModules.iterator();
    while (itr.hasNext()) {
      HelpModule tempHelpModule = (HelpModule) itr.next();
      System.out.println("Trying to insert records for " + tempHelpModule.getCategory());

      // Verifying if the permission category exists in the new database
      int categoryId = -1;
      if ((categoryId = existPermissionCategory(tempHelpModule.getCategory())) != -1) {
        tempHelpModule.setId(categoryId);
        tempHelpModule.setCategoryId(categoryId);

        tempHelpModule.insertModule(db);
      } else {
        System.out.println(tempHelpModule.getCategory() + " Does not exist in the new database");
      }
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean insertTableOfContents(Connection db) throws SQLException {

    HashMap map = new HashMap();
    Iterator itr = tableOfContents.iterator();

    //fetching Parent in the Table of contents for each item based on its content level
    while (itr.hasNext()) {
      TableOfContentItem tmpItem = (TableOfContentItem) itr.next();

      if (tmpItem.getLevel() > 1) {
        tmpItem.setParent(((Integer) map.get(new Integer(tmpItem.getLevel() - 1))).intValue());
      }
      map.put(new Integer(tmpItem.getLevel()), new Integer(tmpItem.insert(db)));
    }

    return true;
  }


  /**
   *  Finds whether a permission category in the exported xml data exists in the
   *  new database
   *
   *@param  category  Category name as it exists in the exported xml data
   *@return           the id of the permission category in the new database
   */
  int existPermissionCategory(String category) {
    Iterator itr = permissionCategories.iterator();
    while (itr.hasNext()) {
      PermissionCategory pc = (PermissionCategory) itr.next();
      if (category.trim().equals(pc.getCategory().trim())) {
        return pc.getId();
      }
    }

    return -1;
  }


  /**
   *  Fetch all the existing permission categories from the new database
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean buildExistingPermissionCategories(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM permission_category ");

    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      PermissionCategory tempPermissionCategory = new PermissionCategory(rs);
      permissionCategories.add(tempPermissionCategory);
    }

    rs.close();
    pst.close();

    return true;
  }


  /**
   *  Description of the Method
   */
  public void buildHelpInformation(String filePath) {

    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document d = db.parse(filePath);

      Node root = d.getDocumentElement();
      processNode(root);

    } catch (Exception e) {
      System.err.println(e);
    }
  }


  /**
   *  Builds the data structure from the XMl document
   *
   *@param  node  Description of the Parameter
   */
  void processNode(Node node) {
    NodeList moduleList = ((Element) node).getElementsByTagName("module");

    for (int i = 0; i < moduleList.getLength(); i++) {
      Node module = moduleList.item(i);
      NamedNodeMap moduleNMP = module.getAttributes();

      HelpModule helpModule = new HelpModule();
      helpModule.setCategory(moduleNMP.getNamedItem("name").getNodeValue());
      System.out.println("Getting for module: " + helpModule.getCategory());

      helpModule.setContentLevel(moduleNMP.getNamedItem("contentLevel").getNodeValue());

      NodeList briefDescription = ((Element) module).getElementsByTagName("briefDescription");
      helpModule.setBriefDescription(briefDescription.item(0).getFirstChild().getNodeValue());

      NodeList detailDescription = ((Element) module).getElementsByTagName("detailDescription");
      helpModule.setDetailDescription(detailDescription.item(0).getFirstChild().getNodeValue());

      NodeList pageList = ((Element) module).getElementsByTagName("pageDescription");

      System.out.println("Number of pages in " + helpModule.getCategory() + ":" + pageList.getLength());
      for (int j = 0; j < pageList.getLength(); j++) {
        Node page = pageList.item(j);
        NamedNodeMap pageNMP = page.getAttributes();

        HelpContent helpContent = new HelpContent();
        helpContent.setContentLevel(pageNMP.getNamedItem("contentLevel").getNodeValue());

        NodeList action = ((Element) page).getElementsByTagName("action");
        helpContent.setAction(action.item(0).getFirstChild().getNodeValue());

        NodeList section = ((Element) page).getElementsByTagName("section");
        helpContent.setSection(((section.item(0).getFirstChild() != null) ? section.item(0).getFirstChild().getNodeValue() : null));

        NodeList subSection = ((Element) page).getElementsByTagName("subSection");
        helpContent.setSub(((subSection.item(0).getFirstChild() != null) ? subSection.item(0).getFirstChild().getNodeValue() : null));

        NodeList title = ((Element) page).getElementsByTagName("title");
        helpContent.setTitle(((title.item(0).getFirstChild() != null) ? title.item(0).getFirstChild().getNodeValue() : null));

        NodeList description = ((Element) page).getElementsByTagName("description");
        helpContent.setDescription(((description.item(0).getFirstChild() != null) ? description.item(0).getFirstChild().getNodeValue() : null));

        int k = 0;
        NodeList featureList = ((Element) page).getElementsByTagName("featureDescription");
        for (k = 0; k < featureList.getLength(); k++) {
          helpContent.getFeatures().add(featureList.item(k).getFirstChild().getNodeValue());
        }

        NodeList ruleList = ((Element) page).getElementsByTagName("ruleDescription");
        for (k = 0; k < ruleList.getLength(); k++) {
          helpContent.getRules().add(ruleList.item(k).getFirstChild().getNodeValue());
        }

        NodeList noteList = ((Element) page).getElementsByTagName("noteDescription");
        for (k = 0; k < noteList.getLength(); k++) {
          helpContent.getNotes().add(noteList.item(k).getFirstChild().getNodeValue());
        }

        NodeList tipList = ((Element) page).getElementsByTagName("tipDescription");
        for (k = 0; k < tipList.getLength(); k++) {
          helpContent.getTips().add(tipList.item(k).getFirstChild().getNodeValue());
        }

        helpModule.getHelpContents().add(helpContent);
      }

      helpModules.add(helpModule);
    }
  }


  /**
   *  Description of the Method
   */
  void buildTableOfContents() {
    Iterator moduleItr = helpModules.iterator();
    int moduleOrder = 5;

    TableOfContentItem tempTopTOCItem = new TableOfContentItem();
    tempTopTOCItem.buildRecord();
    tableOfContents.add(tempTopTOCItem);

    while (moduleItr.hasNext()) {
      HelpModule tempHelpModule = (HelpModule) moduleItr.next();
      if ((tempHelpModule.getInsert() == false) || (tempHelpModule.getContentLevel() == 0)) {
        continue;
      }

      Iterator contentItr = tempHelpModule.getHelpContents().iterator();

      TableOfContentItem tempModuleTOCItem = new TableOfContentItem();
      tempModuleTOCItem.buildRecord(tempHelpModule.getCategory(),
          tempHelpModule.getCategoryId(),
          moduleOrder);

      tableOfContents.add(tempModuleTOCItem);
      moduleOrder = moduleOrder + 5;

      int contentOrder = 5;
      while (contentItr.hasNext()) {
        HelpContent tempHelpContent = (HelpContent) contentItr.next();
        if (tempHelpContent.getInsert() == false || (tempHelpContent.getContentLevel() == 0)) {
          continue;
        }

        TableOfContentItem tempContentTOCItem = new TableOfContentItem();
        tempContentTOCItem.buildRecord(tempHelpModule.getCategoryId(),
            tempHelpContent.fetchTitle(),
            contentOrder,
            tempHelpContent.getContentLevel(),
            tempHelpContent.getId());

        tableOfContents.add(tempContentTOCItem);
        contentOrder = contentOrder + 5;
      }
    }
  }
}

