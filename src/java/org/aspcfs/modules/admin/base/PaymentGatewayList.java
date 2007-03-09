/**
 * 
 */
package org.aspcfs.modules.admin.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;

import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.utils.web.HtmlSelect;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;

/**
 * @author Olga.Kaptyug
 * 
 * @created Sep 28, 2006
 * 
 */
public class PaymentGatewayList extends HtmlSelect implements SyncableList {
  private int code = -1;

  private String description = "";

  private int constantId = -1;

  protected PagedListInfo pagedListInfo = null;

  /**
   * Gets the code attribute of the PaymentGatewayList object
   * 
   * @return code The code value
   */
  public int getCode() {
    return this.code;
  }

  /**
   * Sets the code attribute of the PaymentGatewayList object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * Sets the code attribute of the PaymentGatewayList object
   * 
   * @param code
   *          The new code value
   */
  public void setCode(String code) {
    this.code = Integer.parseInt(code);
  }

  /**
   * Gets the constantId attribute of the PaymentGatewayList object
   * 
   * @return constantId The constantId value
   */
  public int getConstantId() {
    return this.constantId;
  }

  /**
   * Sets the constantId attribute of the PaymentGatewayList object
   * 
   * @param constantId
   *          The new constantId value
   */
  public void setConstantId(int constantId) {
    this.constantId = constantId;
  }

  /**
   * Sets the constantId attribute of the PaymentGatewayList object
   * 
   * @param constantId
   *          The new constantId value
   */
  public void setConstantId(String constantId) {
    this.constantId = Integer.parseInt(constantId);
  }

  /**
   * Gets the description attribute of the PaymentGatewayList object
   * 
   * @return description The description value
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Sets the description attribute of the PaymentGatewayList object
   * 
   * @param description
   *          The new description value
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * This method is required for synchronization, it allows for the resultset to
   * be streamed with lower overhead
   * 
   * @param db
   *          Description of the Parameter
   * @param pst
   *          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException
   *           Description of the Exception
   */
  /**
   * Gets the object attribute of the LookupList object
   * 
   * @param rs
   *          Description of Parameter
   * @return The object value
   * @throws SQLException
   *           Description of Exception
   */
  public PaymentGateway getObject(ResultSet rs) throws SQLException {
    PaymentGateway thisElement = new PaymentGateway(rs);
    return thisElement;
  }

  public ResultSet queryList(Connection db, PreparedStatement pst)
      throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("LookupList-> lookup_payment_gateway");
    }
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append("SELECT * " +
        "FROM lookup_payment_gateway " +
        "ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", description ");
    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    //TODO: check if this gets closed
    return rs;
  }

  /**
   * Description of the Method
   * 
   * @param db
   *          Description of Parameter
   * @throws SQLException
   *           Description of Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      PaymentGateway thisElement = this.getObject(rs);
      this.add(thisElement);
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }

  /**
   * Gets the htmlSelect attribute of the LookupList object
   * 
   * @param selectName
   *          Description of the Parameter
   * @param defaultKey
   *          Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    return getHtmlSelect(selectName, defaultKey, false);
  }

  /**
   * Gets the HtmlSelect attribute of the ContactEmailTypeList object
   * 
   * @param selectName
   *          Description of Parameter
   * @param defaultKey
   *          Description of Parameter
   * @param disabled
   *          Description of the Parameter
   * @return The HtmlSelect value
   * @since 1.1
   */
  public String getHtmlSelect(String selectName, int defaultKey,
      boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setMultiple(multiple);
    thisSelect.setDisabled(disabled);
    thisSelect.setJsEvent(jsEvent);
    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;
    while (i.hasNext()) {
      PaymentGateway thisElement = (PaymentGateway) i.next();

      // Add the item to the list
      if (thisElement.getEnabled() == true) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getCode();
        }
      } else if (thisElement.getCode() == defaultKey) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getCode() == defaultKey) {
        keyFound = true;
      }

    }
    if (keyFound) {
      return thisSelect.getHtml(selectName, defaultKey);
    } else {
      return thisSelect.getHtml(selectName, lookupDefault);
    }
  }

  /**
   * Gets the htmlSelectObj attribute of the LookupList object
   * 
   * @param defaultKey
   *          Description of the Parameter
   * @return The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(int defaultKey) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setMultiple(multiple);
    thisSelect.setJsEvent(jsEvent);

    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;

    while (i.hasNext()) {
      PaymentGateway thisElement = (PaymentGateway) i.next();

      // Add the item
      if (thisElement.getEnabled() == true) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getCode();
        }
      } else if (thisElement.getCode() == defaultKey) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getCode() == defaultKey) {
        keyFound = true;
      }

    }
    if (keyFound) {
      thisSelect.setDefaultKey(defaultKey);
    } else {
      thisSelect.setDefaultKey(lookupDefault);
    }
    return thisSelect;
  }

  /**
   * Gets the htmlSelect attribute of the LookupList object
   * 
   * @param selectName
   *          Description of the Parameter
   * @param defaultValue
   *          Description of the Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String defaultValue) {
    return getHtmlSelect(selectName, defaultValue, false);
  }

  /**
   * Gets the HtmlSelect attribute of the ContactEmailTypeList object
   * 
   * @param selectName
   *          Description of Parameter
   * @param defaultValue
   *          Description of Parameter
   * @param disabled
   *          Description of the Parameter
   * @return The HtmlSelect value
   * @since 1.1
   */
  public String getHtmlSelect(String selectName, String defaultValue,
      boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setDisabled(disabled);
    Iterator i = this.iterator();
    boolean keyFound = false;
    String lookupDefault = null;
    while (i.hasNext()) {
      PaymentGateway thisElement = (PaymentGateway) i.next();

      // Add the item
      if (thisElement.getEnabled()) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      } else if (thisElement.getDescription().equals(defaultValue)) {
        keyFound = true;
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getDescription().equals(defaultValue)) {
        keyFound = true;
      }
      if (thisElement.getDefaultItem()) {
        lookupDefault = thisElement.getDescription();

      }
    }
    return thisSelect.getHtml(selectName, defaultValue);
  }

  /**
   * Gets the htmlSelect attribute of the LookupList object
   * 
   * @param selectName
   *          Description of Parameter
   * @param ms
   *          Description of Parameter
   * @return The htmlSelect value
   */
  public String getHtmlSelect(String selectName, PaymentGatewayList ms) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setMultiple(multiple);
    Iterator i = this.iterator();
    boolean keyFound = false;
    String lookupDefault = null;
    while (i.hasNext()) {
      PaymentGateway thisElement = (PaymentGateway) i.next();
      // Add the item
      if (thisElement.getEnabled()) {
        thisSelect.addItem(thisElement.getCode(), thisElement.getDescription());
      }
      if (thisElement.getDefaultItem()) {
        lookupDefault = thisElement.getDescription();
      }

    }
    return thisSelect.getHtml(selectName);
  }

  /**
   * @param sqlFilter
   */
  private void createFilter(StringBuffer sqlFilter) {
    // TODO Auto-generated method stub

  }
  /**
   *  Gets the idFromValue attribute of the LookupList object
   *
   * @param  value  Description of the Parameter
   * @return        The idFromValue value
   */
  public int getIdFromValue(String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      PaymentGateway thisElement = (PaymentGateway) i.next();
      if (value.equals(thisElement.getDescription())) {
        return thisElement.getCode();
      }
    }
    return -1;
  }
  

  /**
   *  Gets the selectedValue attribute of the LookupList object
   *
   * @param  selectedId  Description of Parameter
   * @return             The selectedValue value
   */
  public String getValueFromId(String selectedId) {
    return getSelectedValue(selectedId);
  }

  /**
   * @param pst
   * @return
   */
  private int prepareFilter(PreparedStatement pst) {
    // TODO Auto-generated method stub
    return 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.base.SyncableList#getTableName()
   */
  public String getTableName() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.base.SyncableList#getUniqueField()
   */
  public String getUniqueField() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.sql.Timestamp)
   */
  public void setLastAnchor(Timestamp tmp) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.base.SyncableList#setLastAnchor(java.lang.String)
   */
  public void setLastAnchor(String tmp) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.sql.Timestamp)
   */
  public void setNextAnchor(Timestamp tmp) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.base.SyncableList#setNextAnchor(java.lang.String)
   */
  public void setNextAnchor(String tmp) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.modules.base.SyncableList#setSyncType(int)
   */
  public void setSyncType(int tmp) {
    // TODO Auto-generated method stub

  }

}
