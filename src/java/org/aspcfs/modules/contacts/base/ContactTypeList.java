package org.aspcfs.modules.contacts.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.LookupList;

/**
 *  Contains a list of contact types built from the database
 *
 *@author     mrajkowski
 *@created    August 29, 2001
 *@version    $Id: ContactTypeList.java,v 1.1 2001/08/29 18:00:17 mrajkowski Exp
 *      $
 */
public class ContactTypeList extends ArrayList {

  private final static int EMPLOYEE_TYPE = 1;
  private boolean showEmployees = false;
  private boolean showPersonal = false;
  private String jsEvent = "";
  private int defaultKey = -1;
  private int size = 1;
  private int category = -1;
  private boolean multiple = false;
  private boolean showDisabled = true;
  private int includeDefinedByUser = -1;
  //includes types already selected by user for ContactTypeList , contactId to be used not userId
  private int includeSelectedByUser = -1;
  protected PagedListInfo pagedListInfo = null;


  /**
   *  Constructor for the ContactTypeList object
   *
   *@since    1.1
   */
  public ContactTypeList() { }


  /**
   *  Constructor for the ContactTypeList object
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.1
   */
  public ContactTypeList(Connection db) throws SQLException {
    buildList(db);
  }


  /**
   *  Constructor for creating a List from code's & description's passed as
   *  arrays.
   *
   *@param  vals              Description of the Parameter
   *@param  names             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ContactTypeList(String[] vals, String[] names) throws SQLException {

    for (int i = 0; i < vals.length; i++) {
      ContactType thisType = new ContactType();
      thisType.setDescription(names[i]);

      //as long as it is not a new entry

      if (!(vals[i].startsWith("*"))) {
        thisType.setId(Integer.parseInt(vals[i]));
      }

      thisType.setLevel(i);
      this.add(thisType);
    }
  }


  /**
   *  Sets the Size attribute of the ContactTypeList object
   *
   *@param  size  The new Size value
   *@since
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   *  Sets the showPersonal attribute of the ContactTypeList object
   *
   *@param  showPersonal  The new showPersonal value
   */
  public void setShowPersonal(boolean showPersonal) {
    this.showPersonal = showPersonal;
  }


  /**
   *  Sets the Multiple attribute of the ContactTypeList object
   *
   *@param  multiple  The new Multiple value
   *@since
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }


  /**
   *  Sets the ShowEmployees attribute of the ContactTypeList object
   *
   *@param  tmp  The new ShowEmployees value
   *@since       1.2
   */
  public void setShowEmployees(boolean tmp) {
    this.showEmployees = tmp;
  }


  /**
   *  Sets the JsEvent attribute of the ContactTypeList object
   *
   *@param  tmp  The new JsEvent value
   *@since       1.3
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Sets the DefaultKey attribute of the ContactTypeList object
   *
   *@param  tmp  The new DefaultKey value
   *@since       1.3
   */
  public void setDefaultKey(int tmp) {
    this.defaultKey = tmp;
  }


  /**
   *  Sets the DefaultKey attribute of the ContactTypeList object
   *
   *@param  tmp  The new DefaultKey value
   *@since       1.3
   */
  public void setDefaultKey(String tmp) {
    this.defaultKey = Integer.parseInt(tmp);
  }


  /**
   *  Sets the category attribute of the ContactTypeList object
   *
   *@param  category  The new category value
   */
  public void setCategory(int category) {
    this.category = category;
  }


  /**
   *  Sets the category attribute of the ContactTypeList object
   *
   *@param  category  The new category value
   */
  public void setCategory(String category) {
    this.category = Integer.parseInt(category);
  }


  /**
   *  Sets the pagedListInfo attribute of the ContactTypeList object
   *
   *@param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Sets the includeDefinedByUser attribute of the ContactTypeList object
   *
   *@param  includeDefinedByUser  The new includeDefinedByUser value
   */
  public void setIncludeDefinedByUser(int includeDefinedByUser) {
    this.includeDefinedByUser = includeDefinedByUser;
  }


  /**
   *  Sets the includeSelectedByUser attribute of the ContactTypeList object
   *
   *@param  includeSelectedByUser  The new includeSelectedByUser value
   */
  public void setIncludeSelectedByUser(int includeSelectedByUser) {
    this.includeSelectedByUser = includeSelectedByUser;
  }


  /**
   *  Sets the showDisabled attribute of the ContactTypeList object
   *
   *@param  showDisabled  The new showDisabled value
   */
  public void setShowDisabled(boolean showDisabled) {
    this.showDisabled = showDisabled;
  }


  /**
   *  Gets the showDisabled attribute of the ContactTypeList object
   *
   *@return    The showDisabled value
   */
  public boolean getShowDisabled() {
    return showDisabled;
  }


  /**
   *  Gets the includeSelectedByUser attribute of the ContactTypeList object
   *
   *@return    The includeSelectedByUser value
   */
  public int getIncludeSelectedByUser() {
    return includeSelectedByUser;
  }


  /**
   *  Gets the typesDefinedByUser attribute of the ContactTypeList object
   *
   *@return    The typesDefinedByUser value
   */
  public int getIncludeDefinedByUser() {
    return includeDefinedByUser;
  }


  /**
   *  Gets the pagedListInfo attribute of the ContactTypeList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the category attribute of the ContactTypeList object
   *
   *@return    The category value
   */
  public int getCategory() {
    return category;
  }


  /**
   *  Gets the showPersonal attribute of the ContactTypeList object
   *
   *@return    The showPersonal value
   */
  public boolean getShowPersonal() {
    return showPersonal;
  }


  /**
   *  Gets the Size attribute of the ContactTypeList object
   *
   *@return    The Size value
   *@since
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the Multiple attribute of the ContactTypeList object
   *
   *@return    The Multiple value
   *@since
   */
  public boolean getMultiple() {
    return multiple;
  }


  /**
   *  Gets the ShowEmployees attribute of the ContactTypeList object
   *
   *@return    The ShowEmployees value
   *@since     1.2
   */
  public boolean getShowEmployees() {
    return showEmployees;
  }


  /**
   *  Gets the JsEvent attribute of the ContactTypeList object
   *
   *@return    The JsEvent value
   *@since     1.3
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Gets the DefaultKey attribute of the ContactTypeList object
   *
   *@return    The DefaultKey value
   *@since     1.3
   */
  public int getDefaultKey() {
    return defaultKey;
  }


  /**
   *  Gets the HtmlSelect attribute of the ContactTypeList object
   *
   *@param  selectName  Description of Parameter
   *@return             The HtmlSelect value
   *@since              1.2
   */
  public String getHtmlSelect(String selectName) {
    return getHtmlSelect(selectName, defaultKey);
  }


  /**
   *  Gets the HtmlSelect attribute of the ContactTypeList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The HtmlSelect value
   *@since              1.1
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    LookupList contactTypeSelect = getLookupList(selectName, defaultKey);
    return contactTypeSelect.getHtmlSelect(selectName, defaultKey);
  }


  /**
   *  Gets the lookupList attribute of the ContactTypeList object
   *
   *@param  selectName  Description of Parameter
   *@param  defaultKey  Description of Parameter
   *@return             The lookupList value
   */
  public LookupList getLookupList(String selectName, int defaultKey) {
    LookupList contactTypeSelect = new LookupList();
    contactTypeSelect.setTableName("lookup_contact_types");
    contactTypeSelect.setJsEvent(jsEvent);
    contactTypeSelect.setSelectSize(this.getSize());
    contactTypeSelect.setMultiple(this.getMultiple());

    Iterator i = this.iterator();
    while (i.hasNext()) {
      ContactType thisContactType = (ContactType) i.next();

      if (thisContactType.getEnabled() == true || thisContactType.getId() == EMPLOYEE_TYPE) {
        contactTypeSelect.appendItem(thisContactType.getId(), thisContactType.getDescription());
      } else if (thisContactType.getId() == defaultKey) {
        contactTypeSelect.appendItem(thisContactType.getId(), thisContactType.getDescription() + " (X)");
      }
    }

    return contactTypeSelect;
  }


  /**
   *  Adds a feature to the Item attribute of the ContactTypeList object
   *
   *@param  key   The feature to be added to the Item attribute
   *@param  name  The feature to be added to the Item attribute
   *@since
   */
  public void addItem(int key, String name) {
    ContactType thisContactType = new ContactType();
    thisContactType.setId(key);
    thisContactType.setDescription(name);
    this.add(thisContactType);
  }


  /**
   *  Gets the enabledElementCount attribute of the ContactTypeList object
   *
   *@return    The enabledElementCount value
   */
  public int getEnabledElementCount() {
    int count = 0;

    Iterator i = this.iterator();
    while (i.hasNext()) {
      ContactType thisElement = (ContactType) i.next();
      if (thisElement.getEnabled()) {
        count++;
      }
    }
    return count;
  }


  /**
   *  Gets the element attribute of the ContactTypeList object
   *
   *@param  code  Description of the Parameter
   *@return       The element value
   */
  public ContactType getElement(int code) {
    ContactType thisType = null;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      ContactType thisElement = (ContactType) i.next();
      if (thisElement.getId() == code) {
        thisType = thisElement;
      }
    }
    return thisType;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.2
   */
  public void buildList(Connection db) throws SQLException {

    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlFilterTail = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM lookup_contact_types lct " +
        "WHERE code > -1 ");

    createFilter(db, sqlFilter);
    addSqlFilterTail(sqlFilterTail);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString() + sqlFilterTail.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();
      
      //Determine the offset, based on the filter, for the first record to show
      if (!pagedListInfo.getCurrentLetter().equals("")) {
        pst = db.prepareStatement(sqlCount.toString() +
            sqlFilter.toString() +
            "AND lct.description < ? " + sqlFilterTail.toString());
        items = prepareFilter(pst);
        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
        rs = pst.executeQuery();
        if (rs.next()) {
          int offsetCount = rs.getInt("recordcount");
          pagedListInfo.setCurrentOffset(offsetCount);
        }
        rs.close();
        pst.close();
      }

      //Determine column to sort by
      pagedListInfo.setDefaultSort("lct.description ", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY lct.level, lct.description ");
    }

    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append("SELECT ");
    }
    sqlSelect.append("lct.* FROM lookup_contact_types lct " +
        "WHERE lct.code > -1 ");
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlFilterTail.toString() + sqlOrder.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }

    int count = 0;
    while (rs.next()) {
      if (pagedListInfo != null && pagedListInfo.getItemsPerPage() > 0 &&
          DatabaseUtils.getType(db) == DatabaseUtils.MSSQL &&
          count >= pagedListInfo.getItemsPerPage()) {
        break;
      }
      ++count;
      ContactType thisContactType = new ContactType(rs);
      this.add(thisContactType);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   *@param  db         Description of the Parameter
   */
  protected void createFilter(Connection db, StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }

    if (!showEmployees) {
      sqlFilter.append("AND lct.code NOT IN (" + EMPLOYEE_TYPE + ") ");
    }

    if (!showPersonal) {
      sqlFilter.append("AND lct.code != 2 ");
    }

    if (category != -1) {
      sqlFilter.append("AND lct.category = ? ");
    }

    if (!showDisabled) {
      sqlFilter.append("AND lct.enabled = '" + DatabaseUtils.getTrue(db) + "' ");
    }

    if (includeDefinedByUser > 0) {
      sqlFilter.append("AND (lct.user_id = ? OR lct.user_id IS NULL) ");
    } else {
      sqlFilter.append("AND lct.user_id IS NULL ");
    }

  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  protected int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;

    if (category != -1) {
      pst.setInt(++i, category);
    }

    if (includeDefinedByUser > 0) {
      pst.setInt(++i, includeDefinedByUser);
    }

    return i;
  }


  /**
   *  Adds a feature to the SqlFilterTail attribute of the ContactTypeList
   *  object
   *
   *@param  sqlFilterTail  The feature to be added to the SqlFilterTail
   *      attribute
   */
  private void addSqlFilterTail(StringBuffer sqlFilterTail) {
    if (sqlFilterTail == null) {
      sqlFilterTail = new StringBuffer();
    }
    int i = 0;
    if (includeSelectedByUser != -1) {
      sqlFilterTail.append("OR (lct.code in (select type_id from contact_type_levels where contact_id = " + includeSelectedByUser + ") ) ");
    }
  }
}

