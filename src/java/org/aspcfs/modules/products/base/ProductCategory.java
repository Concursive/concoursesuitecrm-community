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
 *  This is a generic Product Category that contains Product Catalogs.
 *
 *@author     partha
 *@created    March 18, 2004
 *@version    $Id: ProductCategory.java,v 1.1.2.2 2004/03/18 22:10:36 partha Exp
 *      $
 */
public class ProductCategory extends GenericBean {

  private int id = -1;
  private int parentId = -1;
  private String name = null;
  private String abbreviation = null;
  private String shortDescription = null;
  private String longDescription = null;
  private int typeId = -1;
  private int thumbnailImageId = -1;
  private int smallImageId = -1;
  private int largeImageId = -1;
  private int listOrder = -1;
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private Timestamp entered = null;
  private Timestamp modified = null;
  private Timestamp startDate = null;
  private Timestamp expirationDate = null;
  private boolean enabled = false;

  //other supplimentary fields
  private String parentName = null;
  private String typeName = null;

  /**
   *  Gets the id attribute of the ProductCategory object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the parentId attribute of the ProductCategory object
   *
   *@return    The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   *  Gets the name attribute of the ProductCategory object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the abbreviation attribute of the ProductCategory object
   *
   *@return    The abbreviation value
   */
  public String getAbbreviation() {
    return abbreviation;
  }


  /**
   *  Gets the shortDescription attribute of the ProductCategory object
   *
   *@return    The shortDescription value
   */
  public String getShortDescription() {
    return shortDescription;
  }


  /**
   *  Gets the longDescription attribute of the ProductCategory object
   *
   *@return    The longDescription value
   */
  public String getLongDescription() {
    return longDescription;
  }


  /**
   *  Gets the typeId attribute of the ProductCategory object
   *
   *@return    The typeId value
   */
  public int getTypeId() {
    return typeId;
  }


  /**
   *  Gets the thumbnailImageId attribute of the ProductCategory object
   *
   *@return    The thumbnailImageId value
   */
  public int getThumbnailImageId() {
    return thumbnailImageId;
  }


  /**
   *  Gets the smallImageId attribute of the ProductCategory object
   *
   *@return    The smallImageId value
   */
  public int getSmallImageId() {
    return smallImageId;
  }


  /**
   *  Gets the largeImageId attribute of the ProductCategory object
   *
   *@return    The largeImageId value
   */
  public int getLargeImageId() {
    return largeImageId;
  }


  /**
   *  Gets the listOrder attribute of the ProductCategory object
   *
   *@return    The listOrder value
   */
  public int getListOrder() {
    return listOrder;
  }


  /**
   *  Gets the enteredBy attribute of the ProductCategory object
   *
   *@return    The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the modifiedBy attribute of the ProductCategory object
   *
   *@return    The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   *  Gets the entered attribute of the ProductCategory object
   *
   *@return    The entered value
   */
  public Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the ProductCategory object
   *
   *@return    The modified value
   */
  public Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the startDate attribute of the ProductCategory object
   *
   *@return    The startDate value
   */
  public Timestamp getStartDate() {
    return startDate;
  }


  /**
   *  Gets the expirationDate attribute of the ProductCategory object
   *
   *@return    The expirationDate value
   */
  public Timestamp getExpirationDate() {
    return expirationDate;
  }


  /**
   *  Gets the enabled attribute of the ProductCategory object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the parentName attribute of the ProductCategory object
   *
   *@return    The parentName value
   */
  public String getParentName() {
    return parentName;
  }


  /**
   *  Sets the id attribute of the ProductCategory object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Gets the typeName attribute of the ProductCategory object
   *
   *@return    The typeName value
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   *  Sets the id attribute of the ProductCategory object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the parentId attribute of the ProductCategory object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   *  Sets the parentId attribute of the ProductCategory object
   *
   *@param  tmp  The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the ProductCategory object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the abbreviation attribute of the ProductCategory object
   *
   *@param  tmp  The new abbreviation value
   */
  public void setAbbreviation(String tmp) {
    this.abbreviation = tmp;
  }


  /**
   *  Sets the shortDescription attribute of the ProductCategory object
   *
   *@param  tmp  The new shortDescription value
   */
  public void setShortDescription(String tmp) {
    this.shortDescription = tmp;
  }


  /**
   *  Sets the longDescription attribute of the ProductCategory object
   *
   *@param  tmp  The new longDescription value
   */
  public void setLongDescription(String tmp) {
    this.longDescription = tmp;
  }


  /**
   *  Sets the typeId attribute of the ProductCategory object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(int tmp) {
    this.typeId = tmp;
  }


  /**
   *  Sets the typeId attribute of the ProductCategory object
   *
   *@param  tmp  The new typeId value
   */
  public void setTypeId(String tmp) {
    this.typeId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the thumbnailImageId attribute of the ProductCategory object
   *
   *@param  tmp  The new thumbnailImageId value
   */
  public void setThumbnailImageId(int tmp) {
    this.thumbnailImageId = tmp;
  }


  /**
   *  Sets the thumbnailImageId attribute of the ProductCategory object
   *
   *@param  tmp  The new thumbnailImageId value
   */
  public void setThumbnailImageId(String tmp) {
    this.thumbnailImageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the smallImageId attribute of the ProductCategory object
   *
   *@param  tmp  The new smallImageId value
   */
  public void setSmallImageId(int tmp) {
    this.smallImageId = tmp;
  }


  /**
   *  Sets the smallImageId attribute of the ProductCategory object
   *
   *@param  tmp  The new smallImageId value
   */
  public void setSmallImageId(String tmp) {
    this.smallImageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the largeImageId attribute of the ProductCategory object
   *
   *@param  tmp  The new largeImageId value
   */
  public void setLargeImageId(int tmp) {
    this.largeImageId = tmp;
  }


  /**
   *  Sets the largeImageId attribute of the ProductCategory object
   *
   *@param  tmp  The new largeImageId value
   */
  public void setLargeImageId(String tmp) {
    this.largeImageId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the listOrder attribute of the ProductCategory object
   *
   *@param  tmp  The new listOrder value
   */
  public void setListOrder(int tmp) {
    this.listOrder = tmp;
  }


  /**
   *  Sets the listOrder attribute of the ProductCategory object
   *
   *@param  tmp  The new listOrder value
   */
  public void setListOrder(String tmp) {
    this.listOrder = Integer.parseInt(tmp);
  }


  /**
   *  Sets the enteredBy attribute of the ProductCategory object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }


  /**
   *  Sets the enteredBy attribute of the ProductCategory object
   *
   *@param  tmp  The new enteredBy value
   */
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the modifiedBy attribute of the ProductCategory object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }


  /**
   *  Sets the modifiedBy attribute of the ProductCategory object
   *
   *@param  tmp  The new modifiedBy value
   */
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the entered attribute of the ProductCategory object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the ProductCategory object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modified attribute of the ProductCategory object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the ProductCategory object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the startDate attribute of the ProductCategory object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(Timestamp tmp) {
    this.startDate = tmp;
  }


  /**
   *  Sets the startDate attribute of the ProductCategory object
   *
   *@param  tmp  The new startDate value
   */
  public void setStartDate(String tmp) {
    this.startDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the expirationDate attribute of the ProductCategory object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(Timestamp tmp) {
    this.expirationDate = tmp;
  }


  /**
   *  Sets the expirationDate attribute of the ProductCategory object
   *
   *@param  tmp  The new expirationDate value
   */
  public void setExpirationDate(String tmp) {
    this.expirationDate = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enabled attribute of the ProductCategory object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the ProductCategory object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   *  Sets the parentName attribute of the ProductCategory object
   *
   *@param  tmp  The new parentName value
   */
  public void setParentName(String tmp) {
    this.parentName = tmp;
  }


  /**
   *  Sets the typeName attribute of the ProductCategory object
   *
   *@param  tmp  The new typeName value
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }

  /**
   *  Constructor for the ProductCategory object
   */
  public ProductCategory() { }


  /**
   *  Constructor for the ProductCategory object
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProductCategory(Connection db, int id) throws SQLException {
    queryRecord(db, id);
  }


  /**
   *  Constructor for the ProductCategory object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ProductCategory(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  id                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int id) throws SQLException {
    if (id == -1) {
      throw new SQLException("Invalid Product Category Number");
    }
    
    PreparedStatement pst = db.prepareStatement(
        "SELECT " +
        " pctgy.*, " +
        " pctgy2.category_name AS parent_name, " +
        " pctgytype.description AS type_name " +
        " FROM product_category AS pctgy " +
        " LEFT JOIN product_category AS pctgy2 " +
        " ON ( pctgy.parent_id = pctgy2.category_id ) " +
        " LEFT JOIN lookup_product_category_type AS pctgytype " +
        " ON ( pctgy.type_id = pctgytype.code ) " +
        " WHERE pctgy.category_id = ? "+
        " ORDER BY pctgy.category_name "
        );
    pst.setInt(1, id);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (this.id == -1) {
      throw new SQLException("Product Category not found");
    }
  }


  /**
   *  Adds a feature to the Category attribute of the ProductCategory object
   *
   *@param  db                The feature to be added to the Category attribute
   *@param  cat2id            The feature to be added to the Category attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int addCategory(Connection db, int cat2id) throws SQLException {
    int result = -1;
    int i=0;
    if (cat2id == -1 || this.getId() == -1) {
      throw new SQLException("Invalid category ID ");
    }
    //To simplify the query procedure, insert two records for category_map
    //Insert the first record
    PreparedStatement pst = db.prepareStatement(
      " INSERT INTO product_category_map(category1_id, category2_id) " +
      " VALUES( ? , ? );"
    );
    pst.setInt(++i,this.getId());
    pst.setInt(++i,cat2id);
    result = pst.executeUpdate();
    pst.close();
    
    i=0;
    //Insert the second record
    pst = db.prepareStatement(
      " INSERT INTO product_category_map(category1_id, category2_id) " +
      " VALUES( ? , ? );"
    );
    pst.setInt(++i,cat2id);
    pst.setInt(++i,this.getId());
    result += pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Adds a feature to the Catalog attribute of the ProductCategory object
   *
   *@param  db                The feature to be added to the Catalog attribute
   *@param  catalog_id        The feature to be added to the Catalog attribute
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int addCatalog(Connection db, int catalogId) throws SQLException {
    int result = -1;
    int i=0;
    if (catalogId == -1 || this.getId() == -1) {
      throw new SQLException("Invalid catalog id or category id ");
    }
    PreparedStatement pst = db.prepareStatement(
      " INSERT INTO product_catalog_category_map( product_id, category_id) " +
      " VALUES ( ? , ? );"
    );
    pst.setInt(++i, catalogId);
    pst.setInt(++i, this.getId());
    result = pst.executeUpdate();
    pst.close();
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void buildRecord(ResultSet rs) throws SQLException {
    // product_category table
    this.setId(rs.getInt("category_id"));
    parentId = DatabaseUtils.getInt(rs, "parent_id");
    name = rs.getString("category_name");
    abbreviation = rs.getString("abbreviation");
    shortDescription = rs.getString("short_description");
    longDescription = rs.getString("long_description");
    typeId = DatabaseUtils.getInt(rs, "type_id");
    thumbnailImageId = DatabaseUtils.getInt(rs, "thumbnail_image_id");
    smallImageId = DatabaseUtils.getInt(rs, "small_image_id");
    largeImageId = DatabaseUtils.getInt(rs, "large_image_id");
    listOrder = DatabaseUtils.getInt(rs, "list_order");
    enteredBy = rs.getInt("enteredby");
    entered = rs.getTimestamp("entered");
    modifiedBy = rs.getInt("modifiedby");
    modified = rs.getTimestamp("modified");
    startDate = rs.getTimestamp("start_date");
    expirationDate = rs.getTimestamp("expiration_date");
    enabled = rs.getBoolean("enabled");

    // product_category parent table
    parentName = rs.getString("parent_name");

    // product_category_type table
    typeName = rs.getString("type_name");

  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  baseFilePath      Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean delete(Connection db, String baseFilePath) throws SQLException {
    boolean result = false;
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified.");
    }
    int i = 0;
    try {
      db.setAutoCommit(false);
      /*
       *  /Delete any documents
       *  FileItemList fileList = new FileItemList();
       *  fileList.setLinkModuleId(Constants.DOCUMENTS_PRODUCT_CATEGORY);
       *  fileList.setLinkItemId(this.getId());
       *  fileList.buildList(db);
       *  fileList.delete(db, baseFilePath);
       *  fileList = null;
       *  /Delete any folder data
       *  CustomFieldRecordList folderList = new CustomFieldRecordList();
       *  folderList.setLinkModuleId(Constants.FOLDERS_PRODUCT_CATEGORY);
       *  folderList.setLinkItemId(this.getId());
       *  folderList.buildList(db);
       *  folderList.delete(db);
       *  folderList = null;
       */
      //delete all the dependencies that contain the product_category id

      //delete the product_category_map s that have category1_id = id
      PreparedStatement pst = db.prepareStatement(
          " DELETE from product_category_map " +
          " WHERE category1_id = ? "
          );
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();
      //delete the product_catalog_category_map that contain the category_id = id
      i = 0;
      pst = db.prepareStatement(
          " DELETE from product_catalog_category_map " +
          " WHERE category_id = ? "
          );
      pst.setInt(++i, this.getId());
      pst.execute();
      pst.close();

      //delete the product_category with category_id = id
      i = 0;
      pst = db.prepareStatement(
          "DELETE FROM product_category WHERE category_id = ?"
          );
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      //delete any associated product_category s with the same parent_id
      // also delete all the mappings associated with the current id
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
   *  Gets the timeZoneParams attribute of the ProductCategory class
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    ArrayList thisList = new ArrayList();
    thisList.add("startDate");
    thisList.add("expirationDate");
    return thisList;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    boolean result = false;
    StringBuffer sql = new StringBuffer();
    try {
      db.setAutoCommit(false);
      sql.append(
          " INSERT INTO product_category( " +
          " parent_id, " +
          " category_name,abbreviation, short_description, " +
          " long_description,type_id,thumbnail_image_id, " +
          " small_image_id, large_image_id,list_order, " +
          " enteredby,"
          );
      if (entered != null) {
        sql.append(" entered, ");
      }
      sql.append(" modifiedBy, ");
      if (modified != null) {
        sql.append(" modified, ");
      }
      sql.append(" start_date,expiration_date,enabled)");
      sql.append("VALUES ( ?,?,?,?,?,?,?,?,?,?,?,");
      if (entered != null) {
        sql.append("?, ");
      }
      sql.append("?, ");
      if (modified != null) {
        sql.append(" ?, ");
      }
      sql.append("?,?,?)");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      DatabaseUtils.setInt(pst, ++i, this.getParentId());
      pst.setString(++i, this.getName());
      pst.setString(++i, this.getAbbreviation());
      pst.setString(++i, this.getShortDescription());
      pst.setString(++i, this.getLongDescription());
      DatabaseUtils.setInt(pst, ++i, this.getTypeId());
      DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
      DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
      DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
      DatabaseUtils.setInt(pst, ++i, this.getListOrder());
      DatabaseUtils.setInt(pst, ++i, this.getEnteredBy());
      if (entered != null) {
        pst.setTimestamp(++i, this.getEntered());
      }
      DatabaseUtils.setInt(pst, ++i, this.getModifiedBy());
      if (modified != null) {
        pst.setTimestamp(++i, this.getModified());
      }
      DatabaseUtils.setTimestamp(pst, ++i, this.getStartDate());
      DatabaseUtils.setTimestamp(pst, ++i, this.getExpirationDate());
      pst.setBoolean(++i, this.getEnabled());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "product_category_category_id_seq");
      db.commit();
      result = true;
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = 0;
    if (!isValid(db)) {
      return -1;
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        " UPDATE product_category SET parent_id = ?, abbreviation = ?, " +
        " short_description = ?, long_description = ?, thumbnail_image_id = ?, " +
        " small_image_id = ?, large_image_id = ?, list_order = ?, "
        );
    sql.append(" modifiedBy = ? , modified = " + DatabaseUtils.getCurrentTimestamp(db));
    sql.append(" , start_date = ?, expiration_date = ?, enabled = ? ");
    sql.append(" WHERE category_id = ? ");
    sql.append(" AND modified = ? ");

    int i = 0;
    pst = db.prepareStatement(sql.toString());
    DatabaseUtils.setInt(pst, ++i, this.getParentId());
    pst.setString(++i, this.getAbbreviation());
    pst.setString(++i, this.getShortDescription());
    pst.setString(++i, this.getLongDescription());
    DatabaseUtils.setInt(pst, ++i, this.getThumbnailImageId());
    DatabaseUtils.setInt(pst, ++i, this.getSmallImageId());
    DatabaseUtils.setInt(pst, ++i, this.getLargeImageId());
    DatabaseUtils.setInt(pst, ++i, this.getListOrder());
    pst.setInt(++i, this.getModifiedBy());
    pst.setTimestamp(++i, this.getStartDate());
    pst.setTimestamp(++i, this.getExpirationDate());
    pst.setBoolean(++i, this.getEnabled());
    pst.setInt(++i, this.getId());
    pst.setTimestamp(++i, this.getModified());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }


  /**
   *  Gets the valid attribute of the ProductCategory object
   *
   *@param  db                Description of the Parameter
   *@return                   The valid value
   *@exception  SQLException  Description of the Exception
   */
  public boolean isValid(Connection db) throws SQLException {
// This method contains additional error catching statements
    if (this.getId() == -1) {
      return false;
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
  public DependencyList processDependencies(Connection db) throws SQLException {
    // This method checks all the mappings for any product_category with the current id
    // The currently known mappings are product_category_map, product_catalog_category_map
    if (this.getId() == -1) {
      throw new SQLException("Product Category ID not specified");
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
     *  docDependency.setCount(FileItemList.retrieveRecordCount(db, Constants.DOCUMENTS_PRODUCT_CATEGORY, this.getId()));
     *  docDependency.setCanDelete(true);
     *  dependencyList.add(docDependency);
     *  /Check for folders
     *  Dependency folderDependency = new Dependency();
     *  folderDependency.setName("Folders");
     *  folderDependency.setCount(CustomFieldRecordList.retrieveRecordCount(db, Constants.FOLDERS_PRODUCT_CATEGORY, this.getId()));
     *  folderDependency.setCanDelete(true);
     *  dependencyList.add(folderDependency);
     */
    //Check for product_category with parent_id = id
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as parentcount " +
          " FROM product_category " +
          "WHERE parent_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int categoryCount = rs.getInt("parentcount");
        if (categoryCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of children of this category ");
          thisDependency.setCount(categoryCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }

    //Check the products that belong ONLY to this category
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) AS categorycount " +
          " FROM product_catalog_category_map AS map " +
          " WHERE map.category_id = ? " +
          " AND map.product_id NOT IN ( " +
          " SELECT product_id " +
          " FROM product_catalog_category_map " +
          " WHERE category_id <> ? ) "
          );
      pst.setInt(++i, this.getId());
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int categoryCount = rs.getInt("categorycount");
        if (categoryCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of products belong only to this category ");
          thisDependency.setCount(categoryCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }

    //Check for other categories that link to the current category
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as categorycount " +
          " FROM product_category_map " +
          "WHERE category1_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int categoryCount = rs.getInt("categorycount");
        if (categoryCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of other categories that this category links to ");
          thisDependency.setCount(categoryCount);
          thisDependency.setCanDelete(true);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }

    //Check for other categories that are linked with the current category
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as categorycount " +
          " FROM product_category_map " +
          "WHERE category2_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int categoryCount = rs.getInt("categorycount");
        if (categoryCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of categories that have link to this category");
          thisDependency.setCount(categoryCount);
          thisDependency.setCanDelete(false);
          dependencyList.add(thisDependency);
        }
      }
      rs.close();
      pst.close();
    } catch (SQLException e) {
    }

    //Check for product catalogs linked to this category
    try {
      i = 0;
      pst = db.prepareStatement(
          "SELECT count(*) as categorycount " +
          " FROM product_catalog_category_map " +
          "WHERE category_id = ?"
          );
      pst.setInt(++i, this.getId());
      rs = pst.executeQuery();
      if (rs.next()) {
        int categoryCount = rs.getInt("categorycount");
        if (categoryCount != 0) {
          Dependency thisDependency = new Dependency();
          thisDependency.setName("Number of products that are related to this category ");
          thisDependency.setCount(categoryCount);
          thisDependency.setCanDelete(true);
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
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  catName           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean lookupId(Connection db, String catName) throws SQLException {
    boolean result = false;
    try {
      int i = 0;
      PreparedStatement pst = db.prepareStatement(
          " SELECT count(*) as counter " +
          " FROM product_category " +
          " WHERE category_name = ? "
          );
      pst.setString(++i, catName);
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
