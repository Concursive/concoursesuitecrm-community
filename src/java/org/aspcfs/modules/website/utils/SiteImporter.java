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
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.website.base.*;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.FileUtils;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.ZipUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

/**
 * Description of the Class
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 22, 2006 $Id: Exp $
 */
public class SiteImporter {

  private Site site = null;
  private Layout layout = null;
  private Style style = null;
  private String zipfilePath = null;
  private String webappPath = null;
  private String fileLibraryPath = null;
  private int userId = -1;

  /**
   * Constructor for the SiteExporter object
   */
  public SiteImporter() {
  }


  /**
   * Constructor for the SiteImporter object
   *
   * @param db            Description of the Parameter
   * @param tmpFilePath   Description of the Parameter
   * @param tmpWebappPath Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public SiteImporter(String tmpFilePath, String tmpWebappPath, Connection db) throws Exception {
    // Default to root user
    userId = 0;
    zipfilePath = tmpFilePath;
    webappPath = tmpWebappPath;
    readWebsiteFromZIP();
    insertWebSite(db);
    //copyLayoutAndStyle(db);
  }


  public SiteImporter(String tmpFilePath, String tmpWebappPath, Connection db, int thisUserId) throws Exception {
    zipfilePath = tmpFilePath;
    webappPath = tmpWebappPath;
    userId = thisUserId;
    readWebsiteFromZIP();
    insertWebSite(db);
    //copyLayoutAndStyle(db);
  }


  /**
   * Description of the Method
   *
   * @throws Exception Description of the Exception
   */
  private void readWebsiteFromZIP() throws Exception {
    readManifest();
    readContents();
  }


  /**
   * Description of the Method
   *
   * @throws Exception Description of the Exception
   */
  private void readManifest() throws Exception {
    String fs = System.getProperty("file.separator");
    ZipFile zipFile = new ZipFile(zipfilePath);
    Template template = new Template(zipFile);
    //
    layout = new Layout();
    if (template.hasLayout()) {
      layout.setConstant(template.getLayout());
      layout.setName(String.valueOf(template.getLayout()));
      layout.setCustom(false);
    } else {
      layout.setName("custom");
      layout.setJsp("standard" + fs + "layout" + fs + "layout.jsp");
      layout.setThumbnail(zipfilePath + "|website" + fs + "resources" + fs + "layout" + fs + "layout.jpg");
      layout.setCustom(true);
    }

    style = new Style();
    if (template.hasStyle()) {
      style.setConstant(template.getStyle());
      style.setName(String.valueOf(template.getStyle()));
      style.setCustom(false);
    } else {
      style.setName("custom");
      style.setCss("standard" + fs + "layout" + fs + "style.css");
      style.setThumbnail(zipfilePath + "|website" + fs + "resources" + fs + "style" + fs + "style.jpg");
      style.setCustom(true);
    }
    zipFile.close();
  }


  /**
   * Description of the Method
   *
   * @throws Exception Description of the Exception
   */
  private void readContents() throws Exception {
    String fs = System.getProperty("file.separator");
    ZipFile zipFile = new ZipFile(zipfilePath);
    //Reading from content.xml (archive is OS dependent when created)
    ZipEntry zipEntry = zipFile.getEntry("website/content.xml");
    if (zipEntry == null) {
      zipEntry = zipFile.getEntry("website\\content.xml");
    }
    InputStream xmlStream = zipFile.getInputStream(zipEntry);
    byte[] contents = new byte[xmlStream.available()];
    //reading xml into a byte array
    int offset = 0;
    int numRead = 0;
    while (offset < contents.length
        && (numRead = xmlStream.read(contents, offset, contents.length - offset)) >= 0)
    {
      offset += numRead;
    }
    xmlStream.close();
    zipFile.close();

    XMLUtils xml = new XMLUtils(new String(contents));

    site = new Site();
    Element siteElement = (Element) xml.getDocumentElement();
    Element siteDescriptionElement = XMLUtils.getFirstChild(siteElement, "internalDescription");
    site.setName(siteElement.getAttribute("name"));
    site.setInternalDescription(XMLUtils.getNodeText(siteDescriptionElement));
    site.setEnabled(siteElement.getAttribute("enabled"));

    ArrayList tabElementList = new ArrayList();
    TabList tabList = new TabList();
    XMLUtils.getAllChildren(siteElement, "tab", tabElementList);
    Iterator tabIterator = tabElementList.iterator();
    //System.out.println("Reading values for tabs");
    while (tabIterator.hasNext()) {
      Tab tab = new Tab();
      Element tabElement = (Element) tabIterator.next();
      Element tabDescriptionElement = XMLUtils.getFirstChild(tabElement, "internalDescription");
      tab.setDisplayText(tabElement.getAttribute("displayText"));
      tab.setInternalDescription(XMLUtils.getNodeText(tabDescriptionElement));
      tab.setEnabled(tabElement.getAttribute("enabled"));

      ArrayList tabBannerForTabElementList = new ArrayList();
      TabBannerList tabBannerForTabList = new TabBannerList();
      XMLUtils.getAllChildren(tabElement, "tabBanner", tabBannerForTabElementList);
      Iterator tabBannerForTabIterator = tabBannerForTabElementList.iterator();
      if (tabBannerForTabIterator.hasNext()) {
        TabBanner tabBanner = new TabBanner();
        Element tabBannerElement = (Element) tabBannerForTabIterator.next();
        try {
          tabBanner.setImageId(tabBannerElement.getAttribute("image"));
        } catch (NumberFormatException e) {
          // do nothing
        }
        tab.setTabBanner(tabBanner);
      }

      ArrayList pageGroupElementList = new ArrayList();
      PageGroupList pageGroupList = new PageGroupList();
      XMLUtils.getAllChildren(tabElement, "pageGroup", pageGroupElementList);
      Iterator pageGroupIterator = pageGroupElementList.iterator();
      //System.out.println("Reading values for pagegroups");
      while (pageGroupIterator.hasNext()) {
        PageGroup pageGroup = new PageGroup();
        Element pageGroupElement = (Element) pageGroupIterator.next();
        Element pageGroupDescriptionElement = XMLUtils.getFirstChild(pageGroupElement, "internalDescription");
        pageGroup.setInternalDescription(XMLUtils.getNodeText(pageGroupDescriptionElement));

        ArrayList pageElementList = new ArrayList();
        PageList pageList = new PageList();
        XMLUtils.getAllChildren(pageGroupElement, "page", pageElementList);
        Iterator pageIterator = pageElementList.iterator();
        //System.out.println("Reading values for pages");
        while (pageIterator.hasNext()) {
          Page page = new Page();
          Element pageElement = (Element) pageIterator.next();
          page.setName(pageElement.getAttribute("name"));
          page.setEnabled(pageElement.getAttribute("enabled"));

          ArrayList tabBannerElementList = new ArrayList();
          TabBannerList tabBannerList = new TabBannerList();
          XMLUtils.getAllChildren(pageElement, "tabBanner", tabBannerElementList);
          Iterator tabBannerIterator = tabBannerElementList.iterator();
          if (tabBannerIterator.hasNext()) {
            TabBanner tabBanner = new TabBanner();
            Element tabBannerElement = (Element) tabBannerIterator.next();
            try {
              tabBanner.setImageId(tabBannerElement.getAttribute("image"));
            } catch (NumberFormatException e) {
              // do nothing
            }
            page.setTabBanner(tabBanner);
          }

          ArrayList pageVersionElementList = new ArrayList();
          PageVersionList pageVersionList = new PageVersionList();
          XMLUtils.getAllChildren(pageElement, "pageVersion", pageVersionElementList);
          Iterator pageVersionIterator = pageVersionElementList.iterator();
          //System.out.println("Reading values for pageversions");
          while (pageVersionIterator.hasNext()) {
            PageVersion pageVersion = new PageVersion();
            Element pageVersionElement = (Element) pageVersionIterator.next();
            Element pageVersionDescriptionElement = XMLUtils.getFirstChild(pageVersionElement, "internalDescription");
            Element pageVersionNotesElement = XMLUtils.getFirstChild(pageVersionElement, "notes");
            pageVersion.setVersionNumber(pageVersionElement.getAttribute("versionNumber"));
            pageVersion.setInternalDescription(XMLUtils.getNodeText(pageVersionDescriptionElement));
            pageVersion.setNotes(XMLUtils.getNodeText(pageVersionNotesElement));
            // store whether the page is a construction or active version or neither
            String typeAttribute = pageVersionElement.getAttribute("type");
            if (typeAttribute != null) {
              if (typeAttribute.indexOf("active") != -1) {
                pageVersion.setIsActive(true);
              }
              if (typeAttribute.indexOf("construction") != -1) {
                pageVersion.setIsConstruction(true);
              }
            }

            ArrayList pageRowElementList = new ArrayList();
            PageRowList pageRowList = new PageRowList();
            XMLUtils.getAllChildren(pageVersionElement, "row", pageRowElementList);
            Iterator pageRowIterator = pageRowElementList.iterator();
            //System.out.println("Reading values for pagerows");
            while (pageRowIterator.hasNext()) {
              PageRow pageRow = new PageRow();
              Element pageRowElement = (Element) pageRowIterator.next();
              pageRow.setEnabled(pageRowElement.getAttribute("enabled"));

              ArrayList rowColumnElementList = new ArrayList();
              RowColumnList rowColumnList = new RowColumnList();
              XMLUtils.getAllChildren(pageRowElement, "column", rowColumnElementList);
              Iterator rowColumnIterator = rowColumnElementList.iterator();
              //System.out.println("Reading values for rowcolumns");
              while (rowColumnIterator.hasNext()) {
                RowColumn rowColumn = new RowColumn();
                Element rowColumnElement = (Element) rowColumnIterator.next();
                rowColumn.setEnabled(rowColumnElement.getAttribute("enabled"));
                try {
                  rowColumn.setWidth(rowColumnElement.getAttribute("width"));
                } catch (NumberFormatException e) {
                  // do nothing
                }
                rowColumn.setIceletConfiguratorClass(rowColumnElement.getAttribute("iceletClass"));

                ArrayList propertyElementList = new ArrayList();
                IceletPropertyMap iceletPropertyMap = new IceletPropertyMap();
                XMLUtils.getAllChildren(rowColumnElement, "property", propertyElementList);
                Iterator propertyIterator = propertyElementList.iterator();
                //System.out.println("Reading values for properties");
                while (propertyIterator.hasNext()) {
                  IceletProperty iceletProperty = new IceletProperty();
                  Element iceletPropertyElement = (Element) propertyIterator.next();
                  try {
                    iceletProperty.setTypeConstant(iceletPropertyElement.getAttribute("constant"));
                    iceletProperty.setValue(XMLUtils.toString(XMLUtils.getNodeText(iceletPropertyElement)));

                    iceletPropertyMap.put(new Integer(iceletProperty.getTypeConstant()), iceletProperty);
                  } catch (NumberFormatException e) {
                    // do nothing, either the constant is not specified or is not in a valid format
                  }
                }
                rowColumn.setIceletPropertyMap(iceletPropertyMap);
                rowColumnList.add(rowColumn);
              }
              pageRow.setRowColumnList(rowColumnList);
              pageRowList.add(pageRow);
            }
            pageVersion.setPageRowList(pageRowList);
            pageVersionList.add(pageVersion);
          }
          page.setPageVersionList(pageVersionList);
          pageList.add(page);
        }
        pageGroup.setPageList(pageList);
        pageGroupList.add(pageGroup);
      }
      tab.setPageGroupList(pageGroupList);
      tabList.add(tab);
    }
    site.setTabList(tabList);
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void insertWebSite(Connection db) throws Exception {
    boolean autoCommit = db.getAutoCommit();
    try {
      if (autoCommit) {
        db.setAutoCommit(false);
      }
      //Insert layout if it does not exist
      LayoutList layoutList = new LayoutList();
      if (layout.getConstant() != -1) {
        layoutList.setConstant(layout.getConstant());
        layoutList.buildList(db);
        if (layoutList.size() > 0) {
          layout = (Layout) layoutList.get(0);
        } else {
          //layout.insert(db);
        }
      } else {
        layout.insert(db);
      }

      //Insert style if it does not exist
      StyleList styleList = new StyleList();
      if (style.getConstant() != -1) {
        styleList.setConstant(style.getConstant());
        styleList.buildList(db);
        if (styleList.size() > 0) {
          style = (Style) styleList.get(0);
        } else {
          //style.setLayoutId(layout.getId());
          //style.insert(db);
        }
      } else {
        style.setLayoutId(layout.getId());
        style.insert(db);
      }

      //build icelet list for reference
      IceletList iceletList = new IceletList();
      iceletList.buildList(db);

      //Insert website contents
      site.setEnteredBy(userId);
      site.setModifiedBy(userId);
      site.setLayoutId(layout.getId());
      site.setStyleId(style.getId());
      site.insert(db);

      TabList tabList = site.getTabList();
      Iterator tabIterator = tabList.iterator();
      int tabPosition = 0;
      while (tabIterator.hasNext()) {
        Tab tab = (Tab) tabIterator.next();
        tab.setEnteredBy(userId);
        tab.setModifiedBy(userId);
        tab.setSiteId(site.getId());
        tab.setPosition(tabPosition);
        tab.insert(db);
        tabPosition += 1;

        TabBanner tabBannerForTab = tab.getTabBanner();
        if (tabBannerForTab != null) {
          tabBannerForTab.setTabId(tab.getId());
          tabBannerForTab.setEnteredBy(userId);
          tabBannerForTab.setModifiedBy(userId);
          tabBannerForTab.insert(db);
        }

        PageGroupList pageGroupList = tab.getPageGroupList();
        Iterator pageGroupIterator = pageGroupList.iterator();
        int pageGroupPosition = 0;
        while (pageGroupIterator.hasNext()) {
          PageGroup pageGroup = (PageGroup) pageGroupIterator.next();
          pageGroup.setEnteredBy(userId);
          pageGroup.setModifiedBy(userId);
          pageGroup.setTabId(tab.getId());
          pageGroup.setPosition(pageGroupPosition);
          pageGroup.insert(db);
          pageGroupPosition += 1;

          PageList pageList = pageGroup.getPageList();
          Iterator pageIterator = pageList.iterator();
          int pagePosition = 0;
          while (pageIterator.hasNext()) {
            Page page = (Page) pageIterator.next();
            page.setEnteredBy(userId);
            page.setModifiedBy(userId);
            page.setPageGroupId(pageGroup.getId());
            page.setPosition(pagePosition);
            pagePosition += 1;

            TabBanner tabBannerForPage = page.getTabBanner();
            if (tabBannerForPage != null) {
              tabBannerForPage.setTabId(tab.getId());
              tabBannerForPage.setEnteredBy(userId);
              tabBannerForPage.setModifiedBy(userId);
              tabBannerForPage.setTabId(tab.getId());
              tabBannerForPage.insert(db);

              page.setTabBannerId(tabBannerForPage.getId());
            }

            page.insert(db);

            PageVersionList pageVersionList = page.getPageVersionList();
            Iterator pageVersionIterator = pageVersionList.iterator();
            int parentPageVersionId = -1;
            while (pageVersionIterator.hasNext()) {
              PageVersion pageVersion = (PageVersion) pageVersionIterator.next();
              pageVersion.setEnteredBy(userId);
              pageVersion.setModifiedBy(userId);
              pageVersion.setPageId(page.getId());
              pageVersion.setParentPageVersionId(parentPageVersionId);
              pageVersion.insert(db);
              parentPageVersionId = pageVersion.getId();
              if (pageVersion.getIsActive() || pageVersion.getIsConstruction())
              {
                if (pageVersion.getIsActive()) {
                  page.setActivePageVersionId(pageVersion.getId());
                }
                if (pageVersion.getIsConstruction()) {
                  page.setConstructionPageVersionId(pageVersion.getId());
                  if (page.getActivePageVersionId() == -1) {
                    page.setActivePageVersionId(pageVersion.getId());
                  }
                }
              }
              page.setOverride(true);
              page.update(db);

              PageRowList pageRowList = pageVersion.getPageRowList();
              Iterator pageRowIterator = pageRowList.iterator();
              int pageRowPosition = 0;
              while (pageRowIterator.hasNext()) {
                PageRow pageRow = (PageRow) pageRowIterator.next();
                pageRow.setEnteredBy(userId);
                pageRow.setModifiedBy(userId);
                pageRow.setPageVersionId(pageVersion.getId());
                pageRow.setPosition(pageRowPosition);
                pageRow.insert(db);
                pageRowPosition += 1;

                RowColumnList rowColumnList = pageRow.getRowColumnList();
                Iterator rowColumnIterator = rowColumnList.iterator();
                int rowColumnPosition = 0;
                while (rowColumnIterator.hasNext()) {
                  RowColumn rowColumn = (RowColumn) rowColumnIterator.next();
                  rowColumn.setEnteredBy(userId);
                  rowColumn.setModifiedBy(userId);
                  rowColumn.setPageRowId(pageRow.getId());
                  rowColumn.setPosition(rowColumnPosition);
                  rowColumn.setIceletId(iceletList.getIdFromConfiguratorClass(rowColumn.getIceletConfiguratorClass()));
                  rowColumn.insert(db);
                  rowColumnPosition += 1;

                  IceletPropertyMap iceletPropertyMap = rowColumn.getIceletPropertyMap();
                  Set iceletPropertySet = iceletPropertyMap.keySet();
                  Iterator iceletPropertySetIterator = iceletPropertySet.iterator();
                  while (iceletPropertySetIterator.hasNext()) {
                    Integer key = (Integer) iceletPropertySetIterator.next();
                    IceletProperty iceletProperty = (IceletProperty) iceletPropertyMap.get(key);
                    if (iceletProperty.getValue().indexOf("<img") != -1) {
                      iceletProperty.setValue(replaceTags(iceletProperty.getValue(), pageRow.getId(), db));
                    }
                    iceletProperty.setEnteredBy(userId);
                    iceletProperty.setModifiedBy(userId);
                    iceletProperty.setRowColumnId(rowColumn.getId());
                    iceletProperty.insert(db);
                  }
                }
              }
            }
          }
        }
      }
      if (autoCommit) {
        db.commit();
      }
    } catch (Exception e) {
      if (autoCommit) {
        db.rollback();
      }
      throw new Exception("SiteImporter-> Error: " + e.getMessage());
    } finally {
      if (autoCommit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void copyLayoutAndStyle(Connection db) throws Exception {
    ZipFile zipFile = new ZipFile(zipfilePath);
    String fs = System.getProperty("file.separator");

    String layoutDestinationPath = webappPath + fs + "layout_" + layout.getId() + ".jsp";
    String layoutThumbnailDestinationPath = webappPath + fs + "images" + fs + "layout_" + layout.getId() + ".jpg";
    String styleDestinationPath = webappPath + fs + "css" + fs + "style_" + style.getId() + ".css";
    String styleThumbnailDestinationPath = webappPath + fs + "images" + fs + "style_" + style.getId() + ".jpg";
    if (layout.getConstant() == -1) {
      //Copying layout.jsp
      String zipEntry = "website/resources/layout/layout.jsp";
      ZipUtils.extract(zipFile, zipEntry, layoutDestinationPath);

      //Copying layout.jpg
      zipEntry = "website/resources/layout/layout.jpg";
      ZipUtils.extract(zipFile, zipEntry, layoutThumbnailDestinationPath);
    }
    layout.setJsp(layoutDestinationPath);
    layout.setThumbnail(layoutThumbnailDestinationPath);
    layout.update(db);

    if (style.getConstant() == -1) {
      //Copying style.css
      String zipEntry = "website/resources/style/style.css";
      ZipUtils.extract(zipFile, zipEntry, styleDestinationPath);

      //Copying style.jpg
      zipEntry = "website/resources/style/style.jpg";
      ZipUtils.extract(zipFile, zipEntry, styleThumbnailDestinationPath);

    }
    style.setCss(styleDestinationPath);
    style.setThumbnail(styleThumbnailDestinationPath);
    style.update(db);
    zipFile.close();
  }


  /**
   * Description of the Method
   *
   * @param htmlString Description of the Parameter
   * @param db         Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  private String replaceTags(String htmlString, int pageRowId, Connection db) throws Exception {
    //System.out.println("1. ==> " + htmlString);
    XMLUtils html = new XMLUtils(htmlString);

    Element htmlElement = (Element) html.getDocumentElement();
    replaceImgTags(htmlElement, pageRowId, db);
    //System.out.println("2. ==> " + XMLUtils.toString(htmlElement));
    return XMLUtils.toString(htmlElement);
  }


  /**
   * Description of the Method
   *
   * @param node Description of the Parameter
   * @param db   Description of the Parameter
   */
  private void replaceImgTags(Node node, int pageRowId, Connection db) throws Exception {
    if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("img"))
    {
      Element e = (Element) node;
      String srcAttributeValue = e.getAttribute("src");
      if (srcAttributeValue.indexOf("id=") != -1) {
        //src attributes for images are of the form TEMP_<number>
        String[] srcParameters = e.getAttribute("src").split("_");
        if (srcParameters.length > 0) {
          String value = srcParameters[1];
          int imageId = copyImage(db, value);
          String attributeValue = "ProcessFileItemImage.do?command=StreamImage&path=website&row=" + pageRowId + "&id=" + imageId;
          e.setAttribute("src", attributeValue);
        }
      }
    }
    Node child = node.getFirstChild();
    while (child != null) {
      replaceImgTags(child, pageRowId, db);
      child = child.getNextSibling();
    }
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param imageName Description of the Parameter
   * @return Description of the Return Value
   */
  private int copyImage(Connection db, String imageName) throws Exception {
    String fs = System.getProperty("file.separator");
    FileItem thisItem = new FileItem();
    thisItem.setLinkModuleId(Constants.DOCUMENTS_WEBSITE);
    thisItem.setLinkItemId(0);
    thisItem.setEnteredBy(0);
    thisItem.setModifiedBy(0);
    thisItem.setSubject(imageName);
    thisItem.setClientFilename(imageName);
    thisItem.setFilename(imageName);
    thisItem.setVersion(1.0);

    //thisItem.setSize(newFileInfo.getSize());
    if (thisItem.insert(db)) {
      int imageId = thisItem.getId();

      ZipFile zipFile = new ZipFile(zipfilePath);
      ZipEntry zipEntry = zipFile.getEntry("website/resources/images/" + imageName);
      if (zipEntry == null) {
        zipEntry = zipFile.getEntry("website\\resources\\images\\" + imageName);
      }
      InputStream imageStream = zipFile.getInputStream(zipEntry);
      byte[] image = new byte[imageStream.available()];
      int offset = 0;
      int numRead = 0;
      while (offset < image.length
          && (numRead = imageStream.read(image, offset, image.length - offset)) >= 0)
      {
        offset += numRead;
      }
      imageStream.close();
      zipFile.close();

      if (fileLibraryPath != null) {
      } else {
        //Only For testing
        fileLibraryPath = webappPath + "/WEB-INF/fileLibrary/cdb_actionplans/accounts/";
      }
      String completeImagefilePath = fileLibraryPath + DateUtils.getDatePath(
          new Timestamp(Calendar.getInstance().getTimeInMillis())) + thisItem.getFilename();

      boolean success = (new File(fileLibraryPath + DateUtils.getDatePath(
          new Timestamp(Calendar.getInstance().getTimeInMillis())))).mkdirs();

      System.out.println("IMAGE PATH IN THE FILE LIBRARY ==> " + completeImagefilePath);
      if (success) {
        FileUtils.copyBytesToFile(image, new File(completeImagefilePath), true);
      }

      return imageId;
    }
    return -1;
  }


  /**
   * Sets the zipfilePath attribute of the SiteImporter object
   *
   * @param tmp The new zipfilePath value
   */
  public void setZipfilePath(String tmp) {
    this.zipfilePath = tmp;
  }


  /**
   * Sets the webappPath attribute of the SiteImporter object
   *
   * @param tmp The new webappPath value
   */
  public void setWebappPath(String tmp) {
    this.webappPath = tmp;
  }


  /**
   * Sets the fileLibraryPath attribute of the SiteImporter object
   *
   * @param tmp The new fileLibraryPath value
   */
  public void setFileLibraryPath(String tmp) {
    this.fileLibraryPath = tmp;
  }


  /**
   * Gets the zipfilePath attribute of the SiteImporter object
   *
   * @return The zipfilePath value
   */
  public String getZipfilePath() {
    return zipfilePath;
  }


  /**
   * Gets the webappPath attribute of the SiteImporter object
   *
   * @return The webappPath value
   */
  public String getWebappPath() {
    return webappPath;
  }


  /**
   * Gets the fileLibraryPath attribute of the SiteImporter object
   *
   * @return The fileLibraryPath value
   */
  public String getFileLibraryPath() {
    return fileLibraryPath;
  }

  public Site getSite() {
    return site;
  }
}