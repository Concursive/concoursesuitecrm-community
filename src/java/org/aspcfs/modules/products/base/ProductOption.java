package org.aspcfs.modules.products.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import com.zeroio.iteam.base.FileItem;
import com.zeroio.iteam.base.FileItemList;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.actionlist.base.ActionList;
import org.aspcfs.modules.actionlist.base.ActionItemLog;
import org.aspcfs.modules.actionlist.base.ActionItemLogList;
import org.aspcfs.modules.base.CustomFieldRecordList;

/**
 *  Options for a Product Catalog.
 *
 * @author     partha
 * @created    March 19, 2004
 * @version    $Id$
 */
public class ProductOption extends GenericBean {

  private int id = -1;
  private int configuratorId = -1;
  private int parentId = -1;
  private String shortDescription = null;
  private String longDescription = null;
  private boolean allowCustomerConfigure = false;
  private boolean allowUserConfigure = false;
  private boolean required = false;
  private boolean enabled = true;
  private Timestamp startDate = null;
  private Timestamp endDate = null;
  //other supplimentary fields
  private int productId = -1;
  
  private String productName = null;
  private int resultType = -1;
  private String parentName = null;
  // resources
  private boolean buildOptionValues = true;
  private ProductOptionValuesList optionValuesList = null;
 

  /**
   *  Sets the productId attribute of the ProductOption object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(int tmp) {
    this.productId = tmp;
  }


  /**
   *  Sets the productId attribute of the ProductOption object
   *
   * @param  tmp  The new productId value
   */
  public void setProductId(String tmp) {
    this.productId = Integer.parseInt(tmp);
  }



  /**
   *  Gets the productId attribute of the ProductOption object
   *
   * @return    The productId value
   */
  public int getProductId() {
    return productId;
  }


  /**
   *  Sets the buildOptionValues attribute of the ProductOption object
   *
   * @param  tmp  The new buildOptionValues value
   */
  public void setBuildOptionValues(boolean tmp) {
    this.buildOptionValues = tmp;
  }


  /**
   *  Sets the buildOptionValues attribute of the ProductOption object
   *
   * @param  tmp  The new buildOptionValues value
   */
  public void setBuildOptionValues(String tmp) {
    this.buildOptionValues = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the optionValuesList attribute of the ProductOption object
   *
   * @param  tmp  The new optionValuesList value
   */
  public void setOptionValuesList(ProductOptionValuesList tmp) {
    this.optionValuesList = tmp;
  }


  /**
   *  Gets the buildOptionValues attribute of the ProductOption object
   *
   * @return    The buildOptionValues value
   */
  public boolean getBuildOptionValues() {
    return buildOptionValues;
  }


  /**
   *  Gets the optionValuesList attribute of the ProductOption object
   *
   * @return    The optionValuesList value
   */
  public ProductOptionValuesList getOptionValuesList() {
    return optionValuesList;
  }


  /**
   *  Gets the id attribute of the ProductOption object
   *
   * @return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the configuratorId attribute of the ProductOption object
   *
   * @return    The configuratorId value
   */
  public int getConfiguratorId() {
    return configuratorId;
  }


  /**
   *  Gets the parentId attribute of the ProductOption object
   *
   * @return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the shortDescription attribute of the ProductOption object
   *
   * @return    The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   *  Gets the longDescription attribute of the ProductOption object
   *
   * @return    The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }


  /**
   *  Gets the allowCustomerConfigure attribute of the ProductOption object
   *
   * @return    The allowCustomerConfigure value
   */
  public boolean getAllowCustomerConfigure() {
    return allowCustomerConfigure;
  }


  /**
   *  Gets the allowUserConfigure attribute of the ProductOption object
   *
   * @return    The allowUserConfigure value
   */
  public boolean getAllowUserConfigure() {
    return allowUserConfigure;
  }


  /**
   *  Gets the required attribute of the ProductOption object
   *
   * @return    The required value
   */
  public boolean getRequired() {
    return required;
  }


  /**
   *  Gets the enabled attribute of the ProductOption object
   *
   * @return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the startDate attribute of the ProductOption object
   *
   * @return    The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the endDate attribute of the ProductOption object
   *
   * @return    The endDate value
   */
  public Timestamp getEndDate() {
    return endDate;
  }


  /**
   *  Gets the productName attribute of the ProductOption object
   *
   * @return    The productName value
   */
  public String getProductName() {
    return productName;
  }


  /**
   *  Gets the resultType attribute of the ProductOption object
   *
   * @return    The resultType value
   */
  public int getResultType() {
    return resultType;
  }


  /**
   *  Gets the parentName attribute of the ProductOption object
   *
   * @return    The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   *  Sets the id attribute of the ProductOption object
   *
   * @param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ProductOption object
   *
   * @param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the configuratorId attribute of the ProductOption object
   *
   * @param  tmp  The new configuratorId value
   */
  public void setConfiguratorId(int tmp) {
    this.configuratorId = tmp;
  }


  /**
   *  Sets the configuratorId attribute of the ProductOption object
   *
   * @param  tmp  The new configuratorId value
   */
  public void setConfiguratorId(String tmp) {
    this.configuratorId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the ProductOption object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ProductOption object
   *
   * @param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the shortDescription attribute of the ProductOption object
   *
   * @param  tmp  The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   *  Sets the longDescription attribute of the ProductOption object
   *
   * @param  tmp  The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }


  /**
   *  Sets the allowCustomerConfigure attribute of the ProductOption object
   *
   * @param  tmp  The new allowCustomerConfigure value
   */
  public void setAllowCustomerConfigure(boolean tmp) {
    this.allowCustomerConfigure = tmp;
  }


  /**
   *  Sets the allowCustomerConfigure attribute of the ProductOption object
   *
   * @param  tmp  The new allowCustomerConfigure value
   */
  public void setAllowCustomerConfigure(String tmp) {
    this.allowCustomerConfigure = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the allowUserConfigure attribute of the ProductOption object
   *
   * @param  tmp  The new allowUserConfigure value
   */
  public void setAllowUserConfigure(boolean tmp) {
    this.allowUserConfigure = tmp;
  }


  /**
   *  Sets the allowUserConfigure attribute of the ProductOption object
   *
   * @param  tmp  The new allowUserConfigure value
   */
  public void setAllowUserConfigure(String tmp) {
    this.allowUserConfigure = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the required attribute of the ProductOption object
   *
   * @param  tmp  The new required value
   */
  public void setRequired(boolean tmp) {
    this.required = tmp;
  }


  /**
   *  Sets the required attribute of the ProductOption object
   *
   * @param  tmp  The new required value
   */
  public void setRequired(String tmp) {
    this.required = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the enabled attribute of the ProductOption object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductOption object
   *
   * @param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the startDate attribute of the ProductOption object
   *
   * @param  tmp  The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the endDate attribute of the ProductOption object
   *
   * @param  tmp  The new endDate value
   */
  public void setEndDate(Timestamp tmp) {
    this.endDate = tmp;
  }


  /**
   *  Sets the productName attribute of the ProductOption object
   *
   * @param  tmp  The new productName value
   */
  public void setProductName(String tmp) {
    this.productName = tmp;
  }


  /**
   *  Sets the resultType attribute of the ProductOption object
   *
   * @param  tmp  The new resultType value
   */
  public void setResultType(int tmp) {
    this.resultType = tmp;
  }


  /**
   *  Sets the resultType attribute of the ProductOption object
   *
   * @param  tmp  The new resultType value
   */
  public void setResultType(String tmp) {
    this.resultType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentName attribute of the ProductOption object
   *
   * @param  tmp  The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   *  Constructor for the ProductOption object
   */
  public ProductOption() { }


  /**
   *  Constructor for the ProductOption object
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ProductOption(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the ProductOption object
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public ProductOption(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  id                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (this.getId() == -1) {
      throw new SQLException("Invalid Product Option ID");
    }
    PreparedStatement pst = db.prepareStatement(
        " SELECT " +
        "   popt.*, " +
        "   poptconf.result_type as result_type, " +
        "   popt2.short_description as parent_name " +
        " FROM product_option AS popt " +
        " LEFT JOIN product_option_configurator as poptconf ON ( popt.configurator_id = poptconf.configurator_id ) " +
        " LEFT JOIN product_option as popt2 ON ( popt.parent_id = popt2.option_id ) " +
        " WHERE option_id = ? "
        );
    pst.setInt(1, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.getId() == -1) {
      throw new SQLException("Product Catalog not found");
    }
    //build resources
    if (buildOptionValues) {
      this.buildOptionValues(db);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildOptionValues(Connection db) throws SQLException {
    optionValuesList = new ProductOptionValuesList();
    optionValuesList.setProductId(this.productId);
    optionValuesList.setOptionId(this.id);
    optionValuesList.buildList(db);
  }


  /**
   *  Adds a feature to the Catalog attribute of the ProductOption object
   *
   * @param  db                The feature to be added to the Catalog attribute
   * @param  product_id        The feature to be added to the Catalog attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int addCatalog(Connection db, int productId) throws SQLException {
    int result = -1;
    int i = 0;
    if (productId == -1 || this.getId() == -1) {
      throw new SQLException("Invalid catalog id or option id ");
    }
    PreparedStatement pst = db.prepareStatement(
        " INSERT INTO product_option_map( product_id, option_id,  value_id ) " +
        " VALUES ( ? , ? , 0 ); "
        );
    pst.setInt(++i, productId);
    pst.setInt(++i, this.getId());
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Adds a feature to the Catalog attribute of the ProductOption object
   *
   * @param  db                The feature to be added to the Catalog attribute
   * @param  product_id        The feature to be added to the Catalog attribute
   * @param  value_id          The feature to be added to the Catalog attribute
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int addCatalog(Connection db, int productId, int valueId) throws SQLException {
    int result = -1;
    int i = 0;
    if (productId == -1 || this.getId() == -1 || valueId == -1) {
      throw new SQLException("Invalid catalog id or option id or value id");
    }
    PreparedStatement pst = db.prepareStatement(
        " INSERT INTO product_option_map( product_id, option_id, value_id ) " +
        " VALUES ( ? , ? , ? ); "
        );
    pst.setInt(++i, productId);
    pst.setInt(++i, this.getId());
    pst.setInt(++i, valueId);
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  rs                Description of the Parameter
   * @exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    // product_option table
    this.setId(rs.getInt("option_id"));
    this.setConfiguratorId(DatabaseUtils.getInt(rs, "configurator_id"));
    this.setParentId(DatabaseUtils.getInt(rs, "parent_id"));
    this.setShortDescription(rs.getString("short_description"));
    this.setLongDescription(rs.getString("long_description"));
    this.setAllowCustomerConfigure(rs.getBoolean("allow_customer_configure"));
    this.setAllowCustomerConfigure(rs.getBoolean("allow_customer_configure"));
    this.setRequired(rs.getBoolean("required"));
    this.setStartDate(rs.getTimestamp("start_date"));
    this.setEndDate(rs.getTimestamp("end_date"));
    this.setEnabled(rs.getBoolean("enabled"));
    // product_option_configurator table
    this.setResultType(rs.getInt("result_type"));
    // product_option parent table
    this.setParentName(rs.getString("parent_name"));
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  baseFilePath      Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Option ID not specified.");
    }
    try {
      int i = 0;
      db.setAutoCommit(false);
      PreparedStatement pst = null;
      /*
       *  /Delete any documents
       *  FileItemList fileList = new FileItemList();
       *  fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_OPTION);
       *  fileList.setLinkItemId(this.getId());
       *  fileList.buildList(db);
       *  fileList.delete(db, baseFilePath);
       *  fileList = null;
       *  /Delete any folder data
       *  CustomFieldRecordList folderList = new CustomFieldRecordList();
       *  folderList.setLinkModuleId(Constants.FOLDERS_PRODUCT_OPTION);
       *  folderList.setLinkItemId(this.getId());
       *  folderList.buildList(db);
       *  folderList.delete(db);
       *  folderList = null;
       */
      //delete all the dependencies
      //delete all records that contain option_id in the product_option_map
      pst = db.prepareStatement(
          " DELETE from product_option_map " +
          " WHERE option_id = ? "
          );
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      //delete the product from the catalog
      i = 0;
      pst = db.prepareStatement(
          " DELETE from product_option " +
          " WHERE option_id = ? "
          );
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      db.commit();
      result = true;
    } catch (SQLException e) {
      db.rollback();
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   *  Gets the timeZoneParams attribute of the ProductOption class
   *
   * @return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("endDate");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " INSERT INTO product_option( configurator_id, " +
        " parent_id, short_description, " +
        " long_description, allow_customer_configure,  " +
        " allow_user_configure, required, " +
        " start_date, end_date, enabled ) " +
        " VALUES( ?,?,?,?,?,?,?,?,?,? )");
    int i = 0;
    PreparedStatement pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getConfiguratorId());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    pst.setBoolean(++i, this.getAllowCustomerConfigure());
    pst.setBoolean(++i, this.getAllowUserConfigure());
    pst.setBoolean(++i, this.getRequired());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getEndDate());
    pst.setBoolean(++i, this.getEnabled());
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "product_option_option_id_seq");
    result = true;
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE product_option " +
        " SET configurator_id = ?, parent_id = ?, " +
        " short_description = ?, long_description = ?, " +
        " allow_customer_configure = ?, allow_user_configure = ?, " +
        " required = ?, start_date = ?, end_date = ?, enabled = ? " +
        " WHERE option_id = ? "
        );

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getConfiguratorId());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    pst.setBoolean(++i, this.getAllowCustomerConfigure());
    pst.setBoolean(++i, this.getAllowUserConfigure());
    pst.setBoolean(++i, this.getRequired());
    DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
    DatabaseUtils.setTimestamp(pst, ++i, this.getEndDate());
    pst.setBoolean(++i, this.getEnabled());
    DatabaseUtils.setInt(pst, ++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    // This method checks all the mappings for any product_option with the current id
    // The currently known mappings are product_option_map
    if (this.getId() == -1) {
      throw new SQLException("Product Option ID not specified");
    }
    String sql = null;
    DependencyList dependencyList = new DependencyList();
    PreparedStatement pst = null;
    ResultSet rs = null;
    int i = 0;
    /*
     *  /Check for documents
     *  Dependency docDependency = new Dependency();
     *  docDependency.setName("Documents");
     *  docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_PRODUCT_CATALOG, this.getId()));
     *  docDependency.setCanDelete(true);
     *  dependencyList.add(docDependency);
     *  /Check for folders
     *  Dependency folderDependency = new Dependency();
     *  folderDependency.setName("Folders");
     *  folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.FOLDERS_PRODUCT_CATALOG, this.getId()));
     *  folderDependency.setCanDelete(true);
     *  dependencyList.add(folderDependency);
     */
    //Check for product_catalog with parentId = id
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as parentcount " +
          " FROM product_option " +
          "WHERE parent_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int catalogCount = rs.getInt("parentcount");
        if (catalogCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of children of this catalog ");
          thisDependency.setCount(catalogCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }

    //Check for the current option mappings in product_option_map
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as catalogcount " +
          " FROM product_option_map " +
          "WHERE option_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int catalogCount = rs.getInt("catalogcount");
        if (catalogCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of catalog_option mappings are ");
          thisDependency.setCount(catalogCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }
    return dependencyList;
  }


  /**
   *  Gets the valid attribute of the ProductOption object
   *
   * @param  db                Description of the Parameter
   * @return                   The valid value
   * @exception  SQLException  Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
    if (id == -1) {
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @param  optName           Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public static boolean lookupId(Connection db, String optName) throws SQLException {
    boolean result = false;
    try {
      PreparedStatement pst = db.prepareStatement(
          " SELECT count(*) as counter " +
          " FROM product_option " +
          " WHERE short_description = ? "
          );
      pst.setString(1, optName);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        int buffer = rs.getInt("counter");
        if (buffer != 0) {
          result = true;
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }
    return result;
  }
}

