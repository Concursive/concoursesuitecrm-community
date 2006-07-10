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
package org.aspcfs.modules.website.base;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.w3c.dom.Element;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author kailash
 * @version $Id: Exp $
 * @created February 10, 2006 $Id: Exp $
 */
public class IceletList extends ArrayList {

  private PagedListInfo pagedListInfo = null;

  private int id = -1;
  private String name = null;
  private String configuratorClass = null;
  private int enabled = Constants.UNDEFINED;

  // Html drop-down helper properties
  private String emptyHtmlSelectRecord = null;
  private String jsEvent = null;


  /**
   * Constructor for the IceletList object
   */
  public IceletList() {
  }


  /**
   * Sets the pagedListInfo attribute of the IceletList object
   *
   * @param tmp The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   * Sets the id attribute of the IceletList object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the IceletList object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   * Sets the iceletName attribute of the IceletList object
   *
   * @param tmp The new iceletName value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the iceletConfiguratorClass attribute of the IceletList object
   *
   * @param tmp The new iceletConfiguratorClass value
   */
  public void setConfiguratorClass(String tmp) {
    this.configuratorClass = tmp;
  }


  /**
   * Sets the enabled attribute of the IceletList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the IceletList object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   * Sets the emptyHtmlSelectRecord attribute of the IceletList object
   *
   * @param tmp The new emptyHtmlSelectRecord value
   */
  public void setEmptyHtmlSelectRecord(String tmp) {
    this.emptyHtmlSelectRecord = tmp;
  }

  /**
   * Sets the jsEvent attribute of the IceletList object
   *
   * @param tmp The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }

  /**
   * Gets the pagedListInfo attribute of the IceletList object
   *
   * @return The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }

  /**
   * Gets the id attribute of the IceletList object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the iceletName attribute of the IceletList object
   *
   * @return The iceletName value
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the iceletConfiguratorClass attribute of the IceletList object
   *
   * @return The iceletConfiguratorClass value
   */
  public String getConfiguratorClass() {
    return configuratorClass;
  }

  /**
   * Gets the enabled attribute of the IceletList object
   *
   * @return The enabled value
   */
  public int getEnabled() {
    return enabled;
  }

  /**
   * Gets the emptyHtmlSelectRecord attribute of the IceletList object
   *
   * @return The emptyHtmlSelectRecord value
   */
  public String getEmptyHtmlSelectRecord() {
    return emptyHtmlSelectRecord;
  }

  /**
   * Gets the jsEvent attribute of the IceletList object
   *
   * @return The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    while (rs.next()) {
      Icelet thisIcelet = this.getObject(rs);
      this.add(thisIcelet);
    }
    rs.close();
    // TODO: pst is always null here
    if (pst != null) {
      pst.close();
    }
  }

  public void buildList(HashMap map) {
    Iterator iter = (Iterator) map.keySet().iterator();
    while (iter.hasNext()) {
      Icelet thisIcelet = (Icelet) map.get(iter.next());
      this.add(thisIcelet);
    }
  }

  /**
   * Description of the Method
   *
   * @param db  Description of the Parameter
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst)
      throws SQLException {

    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    // Need to build a base SQL statement for counting records
    sqlCount.append("SELECT COUNT(*) AS recordcount " + "FROM web_icelet "
        + "WHERE icelet_id > -1 ");

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      // Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString()
          + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      // Determine column to sort by
      pagedListInfo.setDefaultSort("icelet_id", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY icelet_id ");
    }

    // Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("wi.* " + "FROM web_icelet wi "
        + "WHERE icelet_id > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
        + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    return rs;
  }

  /**
   * Description of the Method
   *
   * @param sqlFilter Description of the Parameter
   * @param db        Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void createFilter(StringBuffer sqlFilter, Connection db)
      throws SQLException {
    if (id != -1) {
      sqlFilter.append("AND icelet_id = ? ");
    }
    if (name != null) {
      sqlFilter.append("AND icelet_name = ? ");
    }
    if (configuratorClass != null) {
      sqlFilter.append("AND icelet_configurator_class = ? ");
    }
    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append("AND enabled = ? ");
    }
  }


  /**
   * Description of the Method
   *
   * @param pst Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (id != -1) {
      pst.setInt(++i, id);
    }
    if (name != null) {
      pst.setString(++i, name);
    }
    if (configuratorClass != null) {
      pst.setString(++i, configuratorClass);
    }
    if (enabled != Constants.UNDEFINED) {
      pst.setBoolean(++i, (enabled == Constants.TRUE));
    }
    return i;
  }

  /**
   * Gets the htmlSelect attribute of the IceletList object
   *
   * @param selectName Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, -1);
  }

  /**
   * Gets the emptyHtmlSelect attribute of the IceletList object
   *
   * @param thisSystem Description of the Parameter
   * @param selectName Description of the Parameter
   * @return The emptyHtmlSelect value
   */
  public String getEmptyHtmlSelect(SystemStatus thisSystem, String selectName) {
    HtmlSelect iceletListSelect = new HtmlSelect();
    iceletListSelect.addItem(-1, thisSystem
        .getLabel("calendar.none.4dashes"));

    return iceletListSelect.getHtml(selectName);
  }

  /**
   * Gets the htmlSelect attribute of the IceletList object
   *
   * @param selectName Description of the Parameter
   * @param defaultKey Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    HtmlSelect iceletListSelect = new HtmlSelect();
    iceletListSelect.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      iceletListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    boolean foundDefaultIcelet = false;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Icelet thisIcelet = (Icelet) i.next();
      if (!thisIcelet.getEnabled()) {
        if (thisIcelet.getId() != defaultKey) {
          continue;
        }
      }
      iceletListSelect.addItem(thisIcelet.getId(), thisIcelet.getName());
    }

    return iceletListSelect.getHtml(selectName, defaultKey);
  }

  /**
   * Gets the htmlSelectObj attribute of the IceletList object
   *
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj() {
    HtmlSelect iceletListSelect = new HtmlSelect();
    iceletListSelect.setJsEvent(jsEvent);

    if (emptyHtmlSelectRecord != null) {
      iceletListSelect.addItem(-1, emptyHtmlSelectRecord);
    }
    boolean foundDefaultIcelet = false;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      Icelet thisIcelet = (Icelet) i.next();
      if (!thisIcelet.getEnabled()) {
        continue;
      }
      iceletListSelect.addItem(thisIcelet.getId(), thisIcelet.getName());
    }

    return iceletListSelect;
  }

  /**
   * Gets the idFromConfiguratorClass attribute of the IceletList object
   *
   * @param tmpConfiguratorClass Description of the Parameter
   * @return The idFromConfiguratorClass value
   */
  public int getIdFromConfiguratorClass(String tmpConfiguratorClass) {
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      Icelet icelet = (Icelet) iterator.next();
      if (tmpConfiguratorClass.equals(icelet.getConfiguratorClass())) {
        return icelet.getId();
      }
    }
    return -1;
  }

  /**
   * Gets the object attribute of the IceletList object
   *
   * @param rs Description of the Parameter
   * @return The object value
   * @throws SQLException Description of the Exception
   */
  public Icelet getObject(ResultSet rs) throws SQLException {
    Icelet thisIcelet = new Icelet(rs);
    return thisIcelet;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {
    Iterator iceletIterator = this.iterator();
    while (iceletIterator.hasNext()) {
      Icelet thisIcelet = (Icelet) iceletIterator.next();
      thisIcelet.delete(db);
    }
  }

  /**
   * Description of the Method
   *
   * @param filePath Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public static HashMap load(String filePath) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("Reading from file:" + filePath);
    }
    File configFile = new File(filePath);
    XMLUtils xml = new XMLUtils(configFile);
    HashMap iceletMap = new HashMap();
    ArrayList iceletElements = new ArrayList();

    // fetching data by table name
    XMLUtils.getAllChildren(xml.getDocumentElement(), "icelet",
        iceletElements);

    Iterator iceletIterator = iceletElements.iterator();
    System.out.println("Reading values for icelets");
    while (iceletIterator.hasNext()) {

      Element iceletElement = (Element) iceletIterator.next();
      Element descriptionElement = XMLUtils.getFirstChild(iceletElement,
          "description");
      Icelet icelet = new Icelet();
      String value = "";

      if (iceletElement.hasAttribute("name")) {
        icelet.setName(iceletElement.getAttribute("name"));
      }
      if (iceletElement.hasAttribute("class")) {
        icelet
            .setConfiguratorClass(iceletElement
                .getAttribute("class"));
      }
      if (iceletElement.hasAttribute("version")) {
        icelet.setVersion(iceletElement.getAttribute("version"));
      }

      icelet.setDescription(XMLUtils.getNodeText(descriptionElement));

      IceletPropertyMap iceletPropertyMap = new IceletPropertyMap();
      ArrayList propertyElementList = new ArrayList();
      XMLUtils.getAllChildren(iceletElement, "property",
          propertyElementList);
      Iterator propertyElementIterator = propertyElementList.iterator();
      while (propertyElementIterator.hasNext()) {
        IceletProperty iceletProperty = new IceletProperty();
        Element propertyElement = (Element) propertyElementIterator
            .next();
        iceletProperty.setTypeConstant(propertyElement
            .getAttribute("constant"));
        iceletProperty.setDescription(propertyElement
            .getAttribute("description"));
        iceletProperty.setLabel(propertyElement.getAttribute("label"));
        iceletProperty.setType(propertyElement.getAttribute("type"));
        Element defaultValue = XMLUtils.getFirstChild(propertyElement, "defaultValue");
        if (defaultValue != null) {
          iceletProperty.setDefaultValue(XMLUtils.getNodeText(defaultValue));
        } else {
          iceletProperty.setDefaultValue(propertyElement.getAttribute("defaultValue"));
        }
        iceletProperty.setAdditionalText(propertyElement.getAttribute("additionalText"));
        iceletProperty.setAutoAdd(propertyElement.getAttribute("autoAdd"));
        iceletPropertyMap.put(new Integer(iceletProperty.getTypeConstant()), iceletProperty);
      }
      icelet.setIceletPropertyMap(iceletPropertyMap);
      iceletMap.put(icelet.getConfiguratorClass(), icelet);
    }
    return iceletMap;
  }

  public Icelet getIceletById(int id) {
    Icelet result = null;
    Iterator iterator = this.iterator();
    while (iterator.hasNext()) {
      Icelet icelet = (Icelet) iterator.next();
      // System.out.println("IceletList::getIceletById the checkIceletId
      // is "+ icelet.getId()+" ?= "+ id);
      if (icelet.getId() == id) {
        result = icelet;
        // System.out.println("IceletList::getIceletById the id match
        // found is "+ icelet.getId());
      }
    }
    // System.out.println("IceletList::getIceletById the icelet name is "+
    // (result != null?result.getConfiguratorClass():" icelet is null") );
    return result;
  }
}
