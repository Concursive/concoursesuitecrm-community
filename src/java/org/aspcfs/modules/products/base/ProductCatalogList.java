package org.aspcfs.modules.products.base;

import java.util.*;
import java.sql.*;
import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;

/**
 *  List class for the Product Catalog
 *
 *@author     partha
 *@created    March 19, 2004
 *@version    $Id: ProductCatalogList.java,v 1.1.2.2 2004/03/19 20:46:01 partha
 *      Exp $
 */
public class ProductCatalogList extends ArrayList implements SyncableList {
  //sync api
  /**
   *  Description of the Field
   */
  public final static String tableName = "product_catalog";
  /**
   *  Description of the Field
   */
  public final static String uniqueField = "product_id";
  private Timestamp lastAnchor = null;
  private Timestamp nextAnchor = null;
  private int syncType = Constants.NO_SYNC;
  //filters
  private PagedListInfo pagedListInfo = null;
  private int enteredBy = -1;
  private int id = -1;
  private int parentId = -1;
  private int typeId = -1;
  private int enabled = Constants.UNDEFINED;
  private int topOnly = Constants.UNDEFINED;
  //other supplimentary data
  private String categoryName = null;
  private double msrpAmount = 0.0;
  private double priceAmount = 0.0;
  private double recurringAmount = 0.0;
  private String parentName = null;
  private String typeName = null;
  private int publicationId = -1;
  //resources
  private boolean buildResources = false;
  private String optionText = null;
  // this will have a valid value if all the products have the same option
  private String optionPrice = null;
  // this will have a valid value if all the products have one
  // single option which has a price
  private String optionDefaultText = null;
  // This will have a valid value to display a default text for the option
  private boolean optionMatch = true;
  // determines if the products in the list have the same option
  private boolean optionPriceMatch = true;

  // determines if the products in the list have the same option which has a single price
  protected HashMap selectedItems = null;


  /**
   *  Sets the optionDefaultText attribute of the ProductCatalogList object
   *
   *@param  tmp  The new optionDefaultText value
   */
  public void setOptionDefaultText(String tmp) {
    this.optionDefaultText = tmp;
  }


  /**
   *  Gets the optionDefaultText attribute of the ProductCatalogList object
   *
   *@return    The optionDefaultText value
   */
  public String getOptionDefaultText() {
    return optionDefaultText;
  }


  /**
   *  Sets the optionPrice attribute of the ProductCatalogList object
   *
   *@param  tmp  The new optionPrice value
   */
  public void setOptionPrice(String tmp) {
    this.optionPrice = tmp;
  }


  /**
   *  Gets the optionPrice attribute of the ProductCatalogList object
   *
   *@return    The optionPrice value
   */
  public String getOptionPrice() {
    return optionPrice;
  }



  /**
   *  Sets the optionText attribute of the ProductCatalogList object
   *
   *@param  tmp  The new optionText value
   */
  public void setOptionText(String tmp) {
    this.optionText = tmp;
  }


  /**
   *  Gets the optionText attribute of the ProductCatalogList object
   *
   *@return    The optionText value
   */
  public String getOptionText() {
    return optionText;
  }


  /**
   *  Sets the optionMatch attribute of the ProductCatalogList object
   *
   *@param  tmp  The new optionMatch value
   */
  public void setOptionMatch(boolean tmp) {
    this.optionMatch = tmp;
  }


  /**
   *  Sets the optionMatch attribute of the ProductCatalogList object
   *
   *@param  tmp  The new optionMatch value
   */
  public void setOptionMatch(String tmp) {
    this.optionMatch = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the optionPriceMatch attribute of the ProductCatalogList object
   *
   *@param  tmp  The new optionPriceMatch value
   */
  public void setOptionPriceMatch(boolean tmp) {
    this.optionPriceMatch = tmp;
  }


  /**
   *  Sets the optionPriceMatch attribute of the ProductCatalogList object
   *
   *@param  tmp  The new optionPriceMatch value
   */
  public void setOptionPriceMatch(String tmp) {
    this.optionPriceMatch = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the optionMatch attribute of the ProductCatalogList object
   *
   *@return    The optionMatch value
   */
  public boolean getOptionMatch() {
    return optionMatch;
  }


  /**
   *  Gets the optionPriceMatch attribute of the ProductCatalogList object
   *
   *@return    The optionPriceMatch value
   */
  public boolean getOptionPriceMatch() {
    return optionPriceMatch;
  }


  /**
   *  Sets the buildResources attribute of the ProductCatalogList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(boolean tmp) {
    this.buildResources = tmp;
  }


  /**
   *  Sets the buildResources attribute of the ProductCatalogList object
   *
   *@param  tmp  The new buildResources value
   */
  public void setBuildResources(String tmp) {
    this.buildResources = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the buildResources attribute of the ProductCatalogList object
   *
   *@return    The buildResources value
   */
  public boolean getBuildResources() {
    return buildResources;
  }


  /**
   *  Gets the selectedItems attribute of the ProductCatalogList object
   *
   *@return    The selectedItems value
   */
  public HashMap getSelectedItems() {
    return selectedItems;
  }


  /**
   *  Sets the selectedItems attribute of the ProductCatalogList object
   *
   *@param  tmp  The new selectedItems value
   */
  public void setSelectedItems(HashMap tmp) {
    this.selectedItems = tmp;
  }


  /**
   *  Gets the tableName attribute of the ProductCatalogList object
   *
   *@return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the ProductCatalogList object
   *
   *@return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the lastAnchor attribute of the ProductCatalogList object
   *
   *@return    The lastAnchor value
   */
  public Timestamp getLastAnchor() {
    return lastAnchor;
  }


  /**
   *  Gets the nextAnchor attribute of the ProductCatalogList object
   *
   *@return    The nextAnchor value
   */
  public Timestamp getNextAnchor() {
    return nextAnchor;
  }


  /**
   *  Gets the syncType attribute of the ProductCatalogList object
   *
   *@return    The syncType value
   */
  public int getSyncType() {
    return syncType;
  }


  /**
   *  Gets the pagedListInfo attribute of the ProductCatalogList object
   *
   *@return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Gets the enteredBy attribute of the ProductCatalogList object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the id attribute of the ProductCatalogList object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the ProductCatalogList object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the typeId attribute of the ProductCatalogList object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the enabled attribute of the ProductCatalogList object
   *
   *@return    The enabled value
   */
  public int getEnabled() {
    return enabled;
  }


  /**
   *  Gets the categoryName attribute of the ProductCatalogList object
   *
   *@return    The categoryName value
   */
  public String getCategoryName() {
    return categoryName;
  }


  /**
   *  Gets the msrpAmount attribute of the ProductCatalogList object
   *
   *@return    The msrpAmount value
   */
  public double getMsrpAmount() {
    return msrpAmount;
  }


  /**
   *  Gets the priceAmount attribute of the ProductCatalogList object
   *
   *@return    The priceAmount value
   */
  public double getPriceAmount() {
    return priceAmount;
  }


  /**
   *  Gets the recurringAmount attribute of the ProductCatalogList object
   *
   *@return    The recurringAmount value
   */
  public double getRecurringAmount() {
    return recurringAmount;
  }


  /**
   *  Gets the parentName attribute of the ProductCatalogList object
   *
   *@return    The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   *  Gets the topOnly attribute of the ProductCatalogList object
   *
   *@return    The topOnly value
   */
  public int getTopOnly() {
    return topOnly;
  }


  /**
   *  Gets the typeName attribute of the ProductCatalogList object
   *
   *@return    The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   *  Gets the publicationId attribute of the ProductCatalogList object
   *
   *@return    The publicationId value
   */
  public int getPublicationId() {
    return publicationId;
  }


  /**
   *  Sets the lastAnchor attribute of the ProductCatalogList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the ProductCatalogList object
   *
   *@param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the ProductCatalogList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the ProductCatalogList object
   *
   *@param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the syncType attribute of the ProductCatalogList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the ProductCatalogList object
   *
   *@param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the pagedListInfo attribute of the ProductCatalogList object
   *
   *@param  tmp  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo tmp) {
    this.pagedListInfo = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductCatalogList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductCatalogList object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the id attribute of the ProductCatalogList object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ProductCatalogList object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the ProductCatalogList object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ProductCatalogList object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeId attribute of the ProductCatalogList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the ProductCatalogList object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enabled attribute of the ProductCatalogList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(int tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductCatalogList object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = Integer.parseInt(tmp);
  }


  /**
   *  Sets the categoryName attribute of the ProductCatalogList object
   *
   *@param  tmp  The new categoryName value
   */
  public void setCategoryName(String tmp) {
    this.categoryName = tmp;
  }


  /**
   *  Sets the msrpAmount attribute of the ProductCatalogList object
   *
   *@param  tmp  The new msrpAmount value
   */
  public void setMsrpAmount(double tmp) {
    this.msrpAmount = tmp;
  }


  /**
   *  Sets the priceAmount attribute of the ProductCatalogList object
   *
   *@param  tmp  The new priceAmount value
   */
  public void setPriceAmount(double tmp) {
    this.priceAmount = tmp;
  }


  /**
   *  Sets the recurringAmount attribute of the ProductCatalogList object
   *
   *@param  tmp  The new recurringAmount value
   */
  public void setRecurringAmount(double tmp) {
    this.recurringAmount = tmp;
  }


  /**
   *  Sets the parentName attribute of the ProductCatalogList object
   *
   *@param  tmp  The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   *  Sets the topOnly attribute of the ProductCatalogList object
   *
   *@param  tmp  The new topOnly value
   */
  public void setTopOnly(int tmp) {
    this.topOnly = tmp;
  }


  /**
   *  Sets the topOnly attribute of the ProductCatalogList object
   *
   *@param  tmp  The new topOnly value
   */
  public void setTopOnly(String tmp) {
    this.topOnly = Integer.parseInt(tmp);
  }


  /**
   *  Sets the typeName attribute of the ProductCatalogList object
   *
   *@param  tmp  The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   *  Sets the publicationId attribute of the ProductCatalogList object
   *
   *@param  tmp  The new publicationId value
   */
  public void setPublicationId(int tmp) {
    this.publicationId = tmp;
  }


  /**
   *  Sets the publicationId attribute of the ProductCatalogList object
   *
   *@param  tmp  The new publicationId value
   */
  public void setPublicationId(String tmp) {
    this.publicationId = Integer.parseInt(tmp);
  }


  /**
   *  Constructor for the ProductCatalogList object
   */
  public ProductCatalogList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;

    StringBuffer sqlSelect = new StringBuffer();
    StringBuffer sqlCount = new StringBuffer();
    StringBuffer sqlFilter = new StringBuffer();
    StringBuffer sqlOrder = new StringBuffer();

    //Need to build a base SQL statement for counting records
    sqlCount.append(
        "SELECT COUNT(*) AS recordcount " +
        "FROM product_catalog pctlg " +
        "WHERE pctlg.product_id > 0 "
        );

    createFilter(sqlFilter, db);

    if (pagedListInfo != null) {
      //Get the total number of records matching filter
      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
      items = prepareFilter(pst);
      rs = pst.executeQuery();
      if (rs.next()) {
        int maxRecords = rs.getInt("recordcount");
        pagedListInfo.setMaxRecords(maxRecords);
      }
      rs.close();
      pst.close();

      //Determine column to sort by
      pagedListInfo.setDefaultSort("product_name", null);
      pagedListInfo.appendSqlTail(db, sqlOrder);
    } else {
      sqlOrder.append("ORDER BY entered ");
    }
    //Need to build a base SQL statement for returning records
    if (pagedListInfo != null) {
      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
    } else {
      sqlSelect.append(" SELECT ");
    }
    sqlSelect.append(
        " pctlg.*, " +
        " pctgy.category_id AS category_id, " +
        " pctgy.category_name AS category_name, " +
        " pctlgpricing.msrp_amount AS msrp_amount, " +
        " pctlgpricing.price_amount AS price_amount, " +
        " pctlgpricing.recurring_amount AS recurring_amount, " +
        " pctlgpricing.start_date AS price_start_date, " +
        " pctlgpricing.expiration_date AS price_end_date, " +
        " pctlg2.product_name AS parent_name, " +
        " pctlgtype.description AS type_name " +
        " FROM product_catalog AS pctlg " +
        " LEFT JOIN product_catalog_category_map AS pccmap " +
        " ON ( pctlg.product_id = pccmap.product_id ) " +
        " LEFT JOIN product_category AS pctgy " +
        " ON ( pccmap.category_id =  pctgy.category_id ) " +
        " LEFT JOIN product_catalog_pricing AS pctlgpricing " +
        " ON ( pctlg.product_id = pctlgpricing.product_id ) " +
        " LEFT JOIN product_catalog AS pctlg2 " +
        " ON ( pctlg.parent_id = pctlg2.product_id ) " +
        " LEFT JOIN lookup_product_type AS pctlgtype " +
        " ON ( pctlg.type_id = pctlgtype.code ) " +
        " WHERE pctlg.product_id > 0  "
        );
    pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
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
      ProductCatalog productCatalog = new ProductCatalog(rs);
      this.add(productCatalog);
    }
    rs.close();
    pst.close();
    // Each product's option list is generated
    if (buildResources) {
      Iterator i = this.iterator();
      while (i.hasNext()) {
        ProductCatalog thisProduct = (ProductCatalog) i.next();
        thisProduct.buildProductOptions(db);
      }
      determineMatch();
    }
  }


  /**
   *  Description of the Method
   */
  public void determineMatch() {
    // list of products with every product's option list available
    // determine if all the options for all the products are the same
    // and also determine if all the options have the same values
    Iterator productIterator = this.iterator();
    int count = 0;
    int optionId = -1;
    int valueCount = 0;
    // determines the grand total number of values in all the value lists
    String value = "";
    while (productIterator.hasNext()) {
      // retrieve each product from this product list
      ProductCatalog thisProduct = (ProductCatalog) productIterator.next();
      Iterator optionIterator = thisProduct.getProductOptionList().iterator();
      int countOption = 0;
      // determines the total number of options a product has
      while (optionIterator.hasNext()) {
        // retrieve each option and determine if it is the same
        count++;
        countOption++;
        ProductOption thisOption = (ProductOption) optionIterator.next();
        if (count == 1) {
          optionId = thisOption.getId();
        } else {
          if (optionId != thisOption.getId()) {
            this.optionMatch = false;
          }
        }
        Iterator valueIterator = thisOption.getOptionValuesList().iterator();
        while (valueIterator.hasNext()) {
          valueCount++;
          ProductOptionValues thisValue = (ProductOptionValues) valueIterator.next();
          optionPrice = Double.toString(thisValue.getMsrpAmount());
          if (valueCount == 1) {
            value = Double.toString(thisValue.getMsrpAmount());
          } else {
            if (!value.equals(Double.toString(thisValue.getMsrpAmount()))) {
              this.optionPriceMatch = false;
            }
          }
          if (thisValue.getMsrpAmount() != 0) {
            this.optionText = thisValue.getDescription();
          } else {
            this.optionDefaultText = thisValue.getDescription();
          }
        }
      }
      // if any product in the list does not have options then
      // optionMatch should be false
      if (countOption == 0) {
        this.optionMatch = false;
        this.optionPriceMatch = false;
      }
    }
    if (!this.optionMatch) {
      this.optionText = null;
      this.optionPrice = null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  basePath          Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void delete(Connection db, String basePath) throws SQLException {
    Iterator catalogs = this.iterator();
    while (catalogs.hasNext()) {
      ProductCatalog productCatalog = (ProductCatalog) catalogs.next();
      productCatalog.delete(db, basePath);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  sqlFilter  Description of the Parameter
   *@param  db         Description of the Parameter
   */
  private void createFilter(StringBuffer sqlFilter, Connection db) {

    if (enteredBy > -1) {
      sqlFilter.append(" AND pctlg.enteredby = ? ");
    }

    if (id > -1) {
      sqlFilter.append(" AND pctlg.product_id = ? ");
    }

    if (topOnly == Constants.TRUE) {
      sqlFilter.append(" AND pctlg.parent_id IS NULL ");
    } else if (topOnly == Constants.FALSE) {
      sqlFilter.append(" AND pctlg.parent_id IS NOT NULL ");
    }

    if (parentId > -1) {
      sqlFilter.append(" AND pctlg.parent_id = ? ");
    }

    if (typeId > -1) {
      sqlFilter.append(" AND pctlg.type_id = ? ");
    }

    if (categoryName != null) {
      sqlFilter.append(" AND pctgy.category_name = ? ");
    }

    if (parentName != null) {
      sqlFilter.append(" AND pctlg2.product_name = ? ");
    }

    if (typeName != null) {
      sqlFilter.append(" AND pctlgtype.description = ? ");
    }

    if (publicationId != -1) {
      sqlFilter.append(" AND pctgy.category_id = ? ");
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      sqlFilter.append(" AND pctlg.entered < ? ");
    } else if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append(" AND pctlg.modified >= ? ");
      sqlFilter.append(" AND pctlg.entered < ? ");
      sqlFilter.append(" AND pctlg.modified < ? ");
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        sqlFilter.append("AND pctlg.entered >= ? ");
      }
      if (nextAnchor != null) {
        sqlFilter.append("AND pctlg.entered < ? ");
      }
    }

    if (enabled != Constants.UNDEFINED) {
      sqlFilter.append(" AND pctlg.enabled = ? ");
    }
    if (selectedItems != null) {
      if (selectedItems.size() > 0) {
        sqlFilter.append("AND (pctlg.enabled = ? OR pctlg.product_id IN (" + getItemsAsList() + ")) ");
      } else {
        sqlFilter.append("AND pctlg.enabled = ? ");
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pst               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {

    int i = 0;

    if (enteredBy > -1) {
      pst.setInt(++i, enteredBy);
    }

    if (id > -1) {
      pst.setInt(++i, id);
    }
    if (parentId > -1) {
      pst.setInt(++i, parentId);
    }

    if (typeId > -1) {
      pst.setInt(++i, typeId);
    }

    if (categoryName != null) {
      pst.setString(++i, categoryName);
    }

    if (parentName != null) {
      pst.setString(++i, parentName);
    }

    if (typeName != null) {
      pst.setString(++i, typeName);
    }

    if (publicationId != -1) {
      pst.setInt(++i, publicationId);
    }

    //Sync API
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    } else if (syncType == Constants.SYNC_QUERY) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      if (nextAnchor != null) {
        pst.setTimestamp(++i, nextAnchor);
      }
    }
    if (enabled == Constants.TRUE) {
      pst.setBoolean(++i, true);
    } else if (enabled == Constants.FALSE) {
      pst.setBoolean(++i, false);
    }
    if (selectedItems != null) {
      pst.setBoolean(++i, true);
    }

    return i;
  }


  /**
   *  Gets the itemsAsList attribute of the ProductCatalogList object
   *
   *@return    The itemsAsList value
   */
  private String getItemsAsList() {
    StringBuffer sb = new StringBuffer();
    if (selectedItems != null) {
      Iterator i = selectedItems.keySet().iterator();
      while (i.hasNext()) {
        sb.append(String.valueOf((Integer) i.next()));
        if (i.hasNext()) {
          sb.append(",");
        }
      }
    }
    return sb.toString();
  }
}

