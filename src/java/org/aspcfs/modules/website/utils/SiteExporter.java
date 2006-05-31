/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.utils;

import com.zeroio.iteam.base.FileItem;

import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.XMLUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *  Description of the Class
 *
 *@author     kailash
 *@created    February 22, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class SiteExporter {

  private Site site = null;
  private Layout layout = null;
  private Style style = null;
  private int imageCounter = 0;
  private ArrayList imageIds = null;
  private String fileLibraryPath = null;


  /**
   *  Constructor for the SiteExporter object
   */
  public SiteExporter() { }


  /**
   *  Constructor for the SiteExporter object
   *
   *@param  filePath       Description of the Parameter
   *@param  db             Description of the Parameter
   *@param  tmpSiteId      Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public SiteExporter(String tmpSiteId, String filePath, Connection db) throws Exception {
    imageIds = new ArrayList();
    readSiteFromDatabase(db, Integer.parseInt(tmpSiteId));
    Document d = buildXMLContent(filePath);
    String manifest = prepareManifest(filePath, db);
    preparePackage(filePath, d, manifest, db);
  }


  /**
   *  Description of the Method
   *
   *@param  db             Description of the Parameter
   *@param  tmpSiteId      Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void readSiteFromDatabase(Connection db, int tmpSiteId) throws Exception {
    site = new Site(db, tmpSiteId);
    if (site.getLayoutId() != -1) {
      layout = new Layout(db, site.getLayoutId());
    }
    if (site.getStyleId() != -1) {
      style = new Style(db, site.getStyleId());
    }
    site.buildTabList(db);

    Iterator tabIterator = site.getTabList().iterator();
    while (tabIterator.hasNext()) {
      Tab tab = (Tab) tabIterator.next();

      tab.buildTabBanner(db);
      tab.setMode(Site.EDIT_MODE);
      tab.buildPageGroupList(db);

      Iterator pageGroupIterator = tab.getPageGroupList().iterator();
      while (pageGroupIterator.hasNext()) {
        PageGroup pageGroup = (PageGroup) pageGroupIterator.next();
        pageGroup.setMode(Site.EDIT_MODE);
        pageGroup.buildPageList(db);

        Iterator pageIterator = pageGroup.getPageList().iterator();
        while (pageIterator.hasNext()) {
          Page page = (Page) pageIterator.next();
          page.buildTabBanner(db);
          page.buildPageVersionList(db);

          Iterator pageVersionIterator = page.getPageVersionList().iterator();
          while (pageVersionIterator.hasNext()) {
            PageVersion pageVersion = (PageVersion) pageVersionIterator.next();
            pageVersion.setMode(Site.EDIT_MODE);
            pageVersion.buildPageRowList(db);

            Iterator pageRowIterator = pageVersion.getPageRowList().iterator();
            while (pageRowIterator.hasNext()) {
              PageRow pageRow = (PageRow) pageRowIterator.next();
              pageRow.buildRowColumnList(db);

              Iterator rowColumnIterator = pageRow.getRowColumnList().iterator();
              while (rowColumnIterator.hasNext()) {
                RowColumn rowColumn = (RowColumn) rowColumnIterator.next();
                rowColumn.buildIcelet(db);
                rowColumn.buildIceletPropertyMap(db);
              }
            }
          }
        }
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  filePath       Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  private Document buildXMLContent(String filePath) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document d = db.newDocument();

    Element siteElement = d.createElement("site");
    siteElement.setAttribute("name", site.getName());
    siteElement.setAttribute("enabled", String.valueOf(site.getEnabled()));
    Element siteInternalDescriptionElement = d.createElement("internalDescription");
    if (site.getInternalDescription() != null) {
      siteInternalDescriptionElement.appendChild(
          d.createTextNode(XMLUtils.toXMLValue(site.getInternalDescription())));
    }
    siteElement.appendChild(siteInternalDescriptionElement);

    TabList tabList = site.getTabList();
    Iterator tabIterator = tabList.iterator();
    while (tabIterator.hasNext()) {
      Tab tab = (Tab) tabIterator.next();
      Element tabElement = d.createElement("tab");
      tabElement.setAttribute("displayText", tab.getDisplayText());
      tabElement.setAttribute("enabled", String.valueOf(tab.getEnabled()));
      Element tabInternalDescriptionElement = d.createElement("internalDescription");
      if (tab.getInternalDescription() != null) {
        tabInternalDescriptionElement.appendChild(
            d.createTextNode(XMLUtils.toXMLValue(tab.getInternalDescription())));
      }
      tabElement.appendChild(tabInternalDescriptionElement);

      TabBanner tabBannerForTab = tab.getTabBanner();
      Element tabBannerForTabElement = d.createElement("tabBanner");
      tabElement.appendChild(tabBannerForTabElement);

      PageGroupList pageGroupList = tab.getPageGroupList();
      Iterator pageGroupIterator = pageGroupList.iterator();
      while (pageGroupIterator.hasNext()) {
        PageGroup pageGroup = (PageGroup) pageGroupIterator.next();
        Element pageGroupElement = d.createElement("pageGroup");
        pageGroupElement.setAttribute("name", XMLUtils.toXMLValue(pageGroup.getName()));

        Element pageGroupInternalDescriptionElement = d.createElement("internalDescription");
        if (pageGroup.getInternalDescription() != null) {
          pageGroupInternalDescriptionElement.appendChild(
              d.createTextNode(XMLUtils.toXMLValue(pageGroup.getInternalDescription())));
        }
        pageGroupElement.appendChild(pageGroupInternalDescriptionElement);

        PageList pageList = pageGroup.getPageList();
        Iterator pageIterator = pageList.iterator();
        while (pageIterator.hasNext()) {
          Page page = (Page) pageIterator.next();
          Element pageElement = d.createElement("page");
          pageElement.setAttribute("name", XMLUtils.toXMLValue(page.getName()));
          pageElement.setAttribute("enabled", String.valueOf(page.getEnabled()));

          TabBanner tabBannerForPage = page.getTabBanner();
          Element tabBannerForPageElement = d.createElement("tabBanner");
          pageElement.appendChild(tabBannerForPageElement);

          PageVersionList pageVersionList = page.getPageVersionList();
          Iterator pageVersionIterator = pageVersionList.iterator();
          while (pageVersionIterator.hasNext()) {
            PageVersion pageVersion = (PageVersion) pageVersionIterator.next();
            Element pageVersionElement = d.createElement("pageVersion");
            if (pageVersion.getVersionNumber() != -1) {
              pageVersionElement.setAttribute("versionNumber", String.valueOf(pageVersion.getVersionNumber()));
            } else {
              pageVersionElement.setAttribute("versionNumber", "");
            }
            StringBuffer type = new StringBuffer();
            boolean isActive = false;
            if (page.getActivePageVersionId() == pageVersion.getId()) {
              type.append("active");
              isActive = true;
            }
            if (page.getConstructionPageVersionId() == pageVersion.getId()) {
              if (isActive) {
                type.append(",");
              }
              type.append("construction");
            }
            pageVersionElement.setAttribute("type", type.toString());
            Element pageVersionInternalDescriptionElement = d.createElement("internalDescription");
            if (pageVersion.getInternalDescription() != null) {
              pageVersionInternalDescriptionElement.appendChild(
                  d.createTextNode(XMLUtils.toXMLValue(pageVersion.getInternalDescription())));
            }
            pageVersionElement.appendChild(pageVersionInternalDescriptionElement);
            Element pageVersionNotesElement = d.createElement("notes");
            if (pageVersion.getNotes() != null) {
              pageVersionNotesElement.appendChild(
                  d.createTextNode(XMLUtils.toXMLValue(pageVersion.getNotes())));
            }
            pageVersionElement.appendChild(pageVersionNotesElement);

            PageRowList pageRowList = pageVersion.getPageRowList();
            Iterator pageRowIterator = pageRowList.iterator();
            while (pageRowIterator.hasNext()) {
              PageRow pageRow = (PageRow) pageRowIterator.next();
              Element pageRowElement = d.createElement("row");
              pageRowElement.setAttribute("enabled", String.valueOf(pageRow.getEnabled()));

              RowColumnList rowColumnList = pageRow.getRowColumnList();
              Iterator rowColumnIterator = rowColumnList.iterator();
              while (rowColumnIterator.hasNext()) {
                RowColumn rowColumn = (RowColumn) rowColumnIterator.next();
                Element rowColumnElement = d.createElement("column");
                rowColumnElement.setAttribute("enabled", String.valueOf(rowColumn.getEnabled()));
                if (rowColumn.getWidth() != -1) {
                  rowColumnElement.setAttribute("width", String.valueOf(rowColumn.getWidth()));
                } else {
                  rowColumnElement.setAttribute("width", "");
                }
                if (rowColumn.getIcelet() != null) {
                  rowColumnElement.setAttribute("iceletClass", XMLUtils.toXMLValue(rowColumn.getIcelet().getConfiguratorClass()));
                } else {
                  rowColumnElement.setAttribute("iceletClass", "");
                }

                IceletPropertyMap iceletPropertyMap = rowColumn.getIceletPropertyMap();
                Set iceletPropertySet = iceletPropertyMap.keySet();
                Iterator iceletPropertySetIterator = iceletPropertySet.iterator();
                while (iceletPropertySetIterator.hasNext()) {
                  Integer key = (Integer) iceletPropertySetIterator.next();
                  IceletProperty iceletProperty = (IceletProperty) iceletPropertyMap.get(key);
                  Element propertyElement = d.createElement("property");
                  propertyElement.setAttribute("constant", String.valueOf(iceletProperty.getTypeConstant()));
                  /*
                   *  InputStream inputStream = new ByteArrayInputStream(iceletProperty.getValue().getBytes());
                   *  /inputStream.read(iceletProperty.getValue().getBytes());
                   *  InputSource inputSource = new InputSource(inputStream);
                   *  parseHtml(inputSource);
                   *  /parseHtml(iceletProperty.getValue());
                   */
                  String tmpPropertyValue = StringUtils.replace(iceletProperty.getValue(), "&", "&amp;");
                  String valueToStore = tmpPropertyValue;
                  
                  if (iceletProperty.getValue().indexOf("<img") != -1) {
                    valueToStore = replaceTags(tmpPropertyValue);
                  }
                  /*
                  propertyElement.appendChild(
                      d.createTextNode(XMLUtils.toXMLValue(iceletProperty.getValue())));
                   */
                  propertyElement.appendChild(
                      d.createTextNode(XMLUtils.toXMLValue(valueToStore)));
                  rowColumnElement.appendChild(propertyElement);
                }
                pageRowElement.appendChild(rowColumnElement);
              }
              pageVersionElement.appendChild(pageRowElement);
            }
            pageElement.appendChild(pageVersionElement);
          }
          pageGroupElement.appendChild(pageElement);
        }
        tabElement.appendChild(pageGroupElement);
      }
      siteElement.appendChild(tabElement);
    }
    d.appendChild(siteElement);

    return d;
  }


  /**
   *  Description of the Method
   *
   *@param  filePath       Description of the Parameter
   *@param  db             Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  private String prepareManifest(String filePath, Connection db) throws Exception {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("Manifest-Version: 1");
    Organization myOrg = new Organization(db, 0);

    stringBuffer.append("\nCreated-By: " + myOrg.getName());
    stringBuffer.append("\nDescription: " + site.getName());

    Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
    stringBuffer.append("\nCreated: " + timestamp.toString());
    stringBuffer.append("\nModified: " + timestamp.toString());
    stringBuffer.append("\nLayout: " + layout.getName());
    stringBuffer.append("\nStyle: " + style.getName());

    return stringBuffer.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  filePath       Description of the Parameter
   *@param  d              Description of the Parameter
   *@param  manifest       Description of the Parameter
   *@param  db             Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void preparePackage(String filePath, Document d, String manifest, Connection db) throws Exception {

    String fs = System.getProperty("file.separator");

    //Create temporary directory (NOTE:PATH SUBJECT TO CHANGE)
    Random generator = new Random(Calendar.getInstance().getTimeInMillis());
    int directory = generator.nextInt(100000) + 1;
    String tmpFilePath = filePath + fs + directory + fs + "website";
    String tmpParentFilePath = filePath + fs + directory;
    String resourcesPath = tmpFilePath + fs + "resources";
    String imagesPath = resourcesPath + fs + "images";
    String layoutPath = resourcesPath + fs + "layout";
    String stylePath = resourcesPath + fs + "style";

    boolean success = (new File(tmpFilePath)).mkdirs();
    if (success) {
      success = (new File(resourcesPath)).mkdir();
      if (success) {
        success = (new File(imagesPath)).mkdir();
        if (success) {
          success = (new File(layoutPath)).mkdir();
          if (success) {
            success = (new File(stylePath)).mkdir();
          }
        }
      }
    }
    if (!success) {
      throw new Exception("Could not create package");
    }

    //copying content.xml
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    Result result = new StreamResult(new File(tmpFilePath + fs + "content.xml"));
    Source source = new DOMSource(d);
    transformer.transform(source, result);

    //copying MANIFEST.MF
    BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath + fs + "MANIFEST.MF"));
    out.write(manifest);
    out.close();

    //copying layout files (jsp and thumbnail)
    String sourceLayoutPath = filePath + fs + "layout_" + layout.getId() + ".jsp";
    FileUtils.copyFile(new File(sourceLayoutPath), new File(layoutPath + fs + "layout.jsp"), true);
    String sourceLayoutThumbnailPath = filePath + fs + "images" + fs + "layout_" + layout.getId() + ".jpg";
    FileUtils.copyFile(new File(sourceLayoutThumbnailPath), new File(layoutPath + fs + "layout.jpg"), true);

    //copying style files (style and thumbnail)
    String sourceStylePath = filePath + fs + "css" + fs + "style_" + style.getId() + ".css";
    FileUtils.copyFile(new File(sourceStylePath), new File(stylePath + fs + "style.css"), true);
    String sourceStyleThumbnailPath = filePath + fs + "images" + fs + "style_" + style.getId() + ".jpg";
    FileUtils.copyFile(new File(sourceStyleThumbnailPath), new File(stylePath + fs + "style.jpg"), true);

    //copying images
    if (imageIds.size() > 0) {
      Iterator imageIdIterator = imageIds.iterator();
      int tmpCounter = 0;
      while (imageIdIterator.hasNext()) {
        int imageId = Integer.parseInt((String) imageIdIterator.next());
        // 45 is the link item id... it may not be neccessary
        // Constants.ACCOUNTS is being used temporarily. It needs a Constants.WEBSITE
        FileItem fileItem = new FileItem(db, imageId, 45, Constants.ACCOUNTS);

        // Configuring temporary path
        if (fileLibraryPath == null){
          // ONLY FOR TESTING IN DEVELOPMENT ENVIRONMENT
          fileLibraryPath = filePath + "/WEB-INF/fileLibrary/cdb_actionplans/accounts/";
        }
        String sourceCompleteImagefilePath = fileLibraryPath + DateUtils.getDatePath(
            fileItem.getModified()) + fileItem.getFilename();
        //System.out.println("Complete Image File Path ==> " + sourceCompleteImagefilePath);

        String extension = "";
        if (fileItem.getClientFilename().indexOf(".") != -1) {
          extension = fileItem.getClientFilename().substring(fileItem.getClientFilename().indexOf("."));
        }
        String imageFilePath = imagesPath + fs + tmpCounter + extension;
        FileUtils.copyFile(new File(sourceCompleteImagefilePath), new File(imageFilePath), true);
        tmpCounter++;
      }
    }

    //Configuring location of zip (NOTE:PATH SUBJECT TO CHANGE)
    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(filePath + fs + "website.zip"));
    zipOutputStream.setLevel(Deflater.DEFAULT_COMPRESSION);
    zipAllFiles(new File(tmpFilePath), zipOutputStream);
    zipOutputStream.close();

    // delete temporary directory
    FileUtils.deleteDirectory(new File(tmpParentFilePath));
  }


  /**
   *  Description of the Method
   *
   *@param  dir            Description of the Parameter
   *@param  out            Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  private void zipAllFiles(File dir, ZipOutputStream out) throws Exception {

    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        zipAllFiles(new File(dir, children[i]), out);
      }
    } else {
      FileInputStream in = new FileInputStream(dir);
      byte[] buffer = new byte[in.available()];

      // determine name of zip entry
      String dirPath = dir.getPath();
      String pathInZip = "";
      String[] paths = dir.getPath().split("website");
      if (paths.length != 2) {
        throw new Exception("Could not create package");
      } else {
        pathInZip = "website" + paths[1];
      }

      // Add ZIP entry to output stream.
      out.putNextEntry(new ZipEntry(pathInZip));
      // Transfer bytes from the current file to the ZIP file
      int len;
      while ((len = in.read(buffer)) > 0) {
        out.write(buffer, 0, len);
      }
      // Close the current entry
      out.closeEntry();
      // Close the current file input stream
      in.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  htmlString     Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  private String replaceTags(String htmlString) throws Exception {
    //System.out.println("1. ==> " + htmlString);
    XMLUtils html = new XMLUtils(htmlString);

    Element htmlElement = (Element) html.getDocumentElement();
    replaceImgTags(htmlElement);
    //System.out.println("2. ==> " + XMLUtils.toString(htmlElement));
    return XMLUtils.toString(htmlElement);
  }


  /**
   *  Description of the Method
   *
   *@param  node    Description of the Parameter
   *@param  indent  Description of the Parameter
   */
  private void replaceImgTags(Node node) {
    if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("img")) {
      Element e = (Element) node;
      String srcAttributeValue = e.getAttribute("src");
      //System.out.println("srcAttributeValue ==> " + srcAttributeValue);
      if (srcAttributeValue.indexOf("id=") != -1) {
        //System.out.println("POSITION ==> " + srcAttributeValue.indexOf("id="));
        String[] srcParameters = e.getAttribute("src").split("&");
        if (srcParameters.length > 0) {
          int numOfParams = srcParameters.length;
          int counter = 0;
          while (counter < numOfParams) {
            String paramAndValue = srcParameters[counter];
            String param = paramAndValue.split("=")[0];
            if (param.equals("id")) {
              String value = paramAndValue.split("=")[1];
              //System.out.println("====DOCUMENT ID ===> " + value);
              imageIds.add(value);
              e.setAttribute("src", "TEMP_" + imageCounter + ".jpg");
              imageCounter++;
            }
            counter++;
          }
        }
      }
    }
    Node child = node.getFirstChild();
    while (child != null) {
      replaceImgTags(child);
      child = child.getNextSibling();
    }
  }


  /**
   *  Sets the fileLibraryPath attribute of the SiteExporter object
   *
   *@param  tmp  The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   *  Gets the site attribute of the SiteExporter object
   *
   *@return    The site value
   */
  public Site getSite() {
    return site;
  }


  /**
   *  Gets the layout attribute of the SiteExporter object
   *
   *@return    The layout value
   */
  public Layout getLayout() {
    return layout;
  }


  /**
   *  Gets the style attribute of the SiteExporter object
   *
   *@return    The style value
   */
  public Style getStyle() {
    return style;
  }


  /**
   *  Gets the imageCounter attribute of the SiteExporter object
   *
   *@return    The imageCounter value
   */
  public int getImageCounter() {
    return imageCounter;
  }


  /**
   *  Gets the imageIds attribute of the SiteExporter object
   *
   *@return    The imageIds value
   */
  public ArrayList getImageIds() {
    return imageIds;
  }


  /**
   *  Gets the fileLibraryPath attribute of the SiteExporter object
   *
   *@return    The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }

}

