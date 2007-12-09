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
package org.aspcfs.modules.products.base;

import com.darkhorseventures.database.ConnectionElement;
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.apps.transfer.reader.mapreader.Property;
import org.aspcfs.apps.transfer.reader.mapreader.PropertyMap;
import org.aspcfs.controller.ImportManager;
import org.aspcfs.controller.ObjectValidator;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.base.Import;
import org.aspcfs.utils.CFSFileReader;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Represents importer for ProductCatalog
 *
 * @author Olga.Kaptyug
 * @created May 25, 2006
 */
public class ProductCatalogImport extends Import implements Runnable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public final static String fs = System.getProperty("file.separator");

  private PropertyMap propertyMap = null;

  private String filePath = null;

  private int owner = -1;

  private int userId = -1;

  private int accessTypeId = -1;

  private boolean lookupAccount = false;

  private ImportManager manager = null;

  private Connection db = null;

  private ConnectionElement connectionElement = null;

  private File errorFile = null;

  private BufferedWriter fos = null;

  private FileItem fileItem = null;

  private FileItem zipFileItem = null;

  private Thread importThread = null;

  private boolean lead = false;

  private int leadStatus = -1;

  private int imageId = -1;

  private ProductCatalogImages imageList = new ProductCatalogImages();

  /**
   * Gets the imageList attribute of the ProductCatalogImport object
   *
   * @return imageList The imageList value
   */
  public ProductCatalogImages getImageList() {
    return imageList;
  }

  /**
   * Sets the imageList attribute of the ProductCatalogImport object
   *
   * @param imageList The new imageList value
   */
  public void setImageList(ProductCatalogImages imageList) {
    this.imageList = imageList;
  }

  /**
   * Gets the imageId attribute of the ProductCatalogImport object
   *
   * @return The imageId value
   */
  public int getImageId() {
    return imageId;
  }

  /**
   * Sets the id attribute of the Import object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.imageId = Integer.parseInt(tmp);
  }

  /**
   * Sets the imageId attribute of the ProductCatalogImport object
   *
   * @param imageId The new imageId value
   */
  public void setImageId(int imageId) {
    this.imageId = imageId;
  }

  /**
   * Constructor for the ContactImport object
   */
  public ProductCatalogImport() {
  }

  /**
   * Constructor for the ContactImport object
   *
   * @param db       Description of the Parameter
   * @param importId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public ProductCatalogImport(Connection db, int importId) throws SQLException {
    super(db, importId);
  }

  /**
   * Sets the properties attribute of the ContactImport object
   *
   * @param request The new properties value
   */
  public void setProperties(HttpServletRequest request) {
    if (request.getParameter("owner") != null) {
      setOwner(request.getParameter("owner"));
    }
    if (request.getParameter("accessType") != null) {
      setAccessTypeId(request.getParameter("accessType"));
    }
  }

  /**
   * Sets the owner attribute of the ContactImport object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }

  /**
   * Sets the owner attribute of the ContactImport object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }

  /**
   * Sets the accessTypeId attribute of the ContactImport object
   *
   * @param tmp The new accessTypeId value
   */
  public void setAccessTypeId(int tmp) {
    this.accessTypeId = tmp;
  }

  /**
   * Sets the accessTypeId attribute of the ContactImport object
   *
   * @param tmp The new accessTypeId value
   */
  public void setAccessTypeId(String tmp) {
    this.accessTypeId = Integer.parseInt(tmp);
  }

  /**
   * Sets the userId attribute of the ContactImport object
   *
   * @param tmp The new userId value
   */
  public void setUserId(int tmp) {
    this.userId = tmp;
  }

  /**
   * Sets the userId attribute of the ContactImport object
   *
   * @param tmp The new userId value
   */
  public void setUserId(String tmp) {
    this.userId = Integer.parseInt(tmp);
  }

  /**
   * Sets the lookupAccount attribute of the ContactImport object
   *
   * @param tmp The new lookupAccount value
   */
  public void setLookupAccount(boolean tmp) {
    this.lookupAccount = tmp;
  }

  /**
   * Sets the lookupAccount attribute of the ContactImport object
   *
   * @param tmp The new lookupAccount value
   */
  public void setLookupAccount(String tmp) {
    this.lookupAccount = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the propertyMap attribute of the ContactImport object
   *
   * @param tmp The new propertyMap value
   */
  public void setPropertyMap(PropertyMap tmp) {
    this.propertyMap = tmp;
  }

  /**
   * Sets the filePath attribute of the ContactImport object
   *
   * @param tmp The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }

  /**
   * Sets the db attribute of the ContactImport object
   *
   * @param tmp The new db value
   */
  public void setDb(Connection tmp) {
    this.db = tmp;
  }

  /**
   * Sets the manager attribute of the ContactImport object
   *
   * @param tmp The new manager value
   */
  public void setManager(ImportManager tmp) {
    this.manager = tmp;
  }

  /**
   * Sets the errorFile attribute of the ContactImport object
   *
   * @param tmp The new errorFile value
   */
  public void setErrorFile(File tmp) {
    this.errorFile = tmp;
  }

  /**
   * Sets the fileItem attribute of the ContactImport object
   *
   * @param tmp The new fileItem value
   */
  public void setFileItem(FileItem tmp) {
    this.fileItem = tmp;
  }

  /**
   * Sets the importThread attribute of the ContactImport object
   *
   * @param tmp The new importThread value
   */
  public void setImportThread(Thread tmp) {
    this.importThread = tmp;
  }

  /**
   * Gets the importThread attribute of the ContactImport object
   *
   * @return The importThread value
   */
  public Thread getImportThread() {
    return importThread;
  }

  /**
   * Gets the fileItem attribute of the ContactImport object
   *
   * @return The fileItem value
   */
  public FileItem getFileItem() {
    return fileItem;
  }

  /**
   * Gets the errorFile attribute of the ContactImport object
   *
   * @return The errorFile value
   */
  public File getErrorFile() {
    return errorFile;
  }

  /**
   * Gets the manager attribute of the ContactImport object
   *
   * @return The manager value
   */
  public ImportManager getManager() {
    return manager;
  }

  /**
   * Gets the db attribute of the ContactImport object
   *
   * @return The db value
   */
  public Connection getDb() {
    return db;
  }

  /**
   * Gets the filePath attribute of the ContactImport object
   *
   * @return The filePath value
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Gets the propertyMap attribute of the ContactImport object
   *
   * @return The propertyMap value
   */
  public PropertyMap getPropertyMap() {
    return propertyMap;
  }

  /**
   * Gets the lookupAccount attribute of the ContactImport object
   *
   * @return The lookupAccount value
   */
  public boolean getLookupAccount() {
    return lookupAccount;
  }

  /**
   * Gets the userId attribute of the ContactImport object
   *
   * @return The userId value
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Gets the owner attribute of the ContactImport object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }

  /**
   * Gets the accessTypeId attribute of the ContactImport object
   *
   * @return The accessTypeId value
   */
  public int getAccessTypeId() {
    return accessTypeId;
  }

  /**
   * Sets the connectionElement attribute of the ContactImport object
   *
   * @param tmp The new connectionElement value
   */
  public void setConnectionElement(ConnectionElement tmp) {
    this.connectionElement = tmp;
  }

  /**
   * Gets the connectionElement attribute of the ContactImport object
   *
   * @return The connectionElement value
   */
  public ConnectionElement getConnectionElement() {
    return connectionElement;
  }

  /**
   * Gets the lead attribute of the ContactImport object
   *
   * @return The lead value
   */
  public boolean getLead() {
    return lead;
  }

  /**
   * Sets the lead attribute of the ContactImport object
   *
   * @param tmp The new lead value
   */
  public void setLead(boolean tmp) {
    this.lead = tmp;
  }

  /**
   * Sets the lead attribute of the ContactImport object
   *
   * @param tmp The new lead value
   */
  public void setLead(String tmp) {
    this.lead = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Gets the leadStatus attribute of the ContactImport object
   *
   * @return The leadStatus value
   */
  public int getLeadStatus() {
    return leadStatus;
  }

  /**
   * Sets the leadStatus attribute of the ContactImport object
   *
   * @param tmp The new leadStatus value
   */
  public void setLeadStatus(int tmp) {
    this.leadStatus = tmp;
  }

  /**
   * Sets the leadStatus attribute of the ContactImport object
   *
   * @param tmp The new leadStatus value
   */
  public void setLeadStatus(String tmp) {
    this.leadStatus = Integer.parseInt(tmp);
  }

  /**
   * Description of the Method
   */
  public void start() {
    importThread = new Thread(this);
    importThread.start();
  }

  /**
   * Process the import
   */
  public void run() {
    ArrayList thisRecord = new ArrayList();
    String line = null;
    StringBuffer error = new StringBuffer();
    int recordCount = 1;
    boolean recordInserted = false;
    boolean done = false;
    ArrayList header = null;
    Thread currentThread = Thread.currentThread();
    HashMap images = new HashMap();
    try {
      // get connection from the manager
      db = manager.getConnection(connectionElement);

      // create a temporary log file under the $FILELIBRARY/database
      errorFile = new File(filePath + "_error");
      fos = new BufferedWriter(new FileWriter(errorFile));

      if (db == null) {
        // FATAL
        throw new Exception("FATAL: Could not get database connection!");
      }

      // update status
      if (updateStatus(db, RUNNING) < 1) {
        // FATAL
        throw new Exception("Error updating status to RUNNING");
      }

      // Read the file in
      CFSFileReader fileReader = new CFSFileReader(filePath, this.getFileType());

      // header
      CFSFileReader.Record record = fileReader.nextLine();
      header = record.data;

      // add the header to the log file
      recordError(null, record.line, 0);

      // get categories list
      ProductCategoryList productCategoryList = new ProductCategoryList();
      productCategoryList.setExcludeUnapprovedCategories(false);
      productCategoryList.buildList(db);

      UserList userList = new UserList();
      userList.setSiteId(this.getSiteId());
      userList.buildList(db);

      // process data
      while (importThread == currentThread && !done) {
        if ((record = fileReader.nextLine()) != null) {
          if (error.length() > 0) {
            error.delete(0, error.length());
          }
          recordInserted = false;
          ++recordCount;

          try {

            if (!record.isEmpty()) {
              // get the record
              thisRecord = record.data;

              // get the line and pad it if necessary for missing columns
              line = fileReader.padLine(record.line, header.size()
                  - thisRecord.size());

              ProductCatalog thisProductCatalog = new ProductCatalog();
              thisProductCatalog.setImportId(this.getId());
              thisProductCatalog.setStatusId(Import.PROCESSED_UNAPPROVED);
              thisProductCatalog.setComments(this.getComments());
              // set product properties


              String productName = this.getValue(thisRecord, propertyMap
                  .getProperty("productName"));
              if (!"".equals(StringUtils.toString(productName))) {
                thisProductCatalog.setProductName(productName);
              } else {

                if ("".equals(StringUtils.toString(thisProductCatalog
                    .getProductName()))) {
                  // SKU not found.
                  if (getType() == Constants.IMPORT_PRODUCT_CATALOG) {
                    error.append("; Product name is a required field");
                  }
                }
              }

              ArrayList priceInstances = propertyMap
                  .getDependencyMapList("productCatalogPricing");
              Iterator i = priceInstances.iterator();
              while (i.hasNext()) {
                PropertyMap thisMap = (PropertyMap) i.next();
                String price = this.getValue(thisRecord, thisMap
                    .getProperty("priceAmount"));
                price = StringUtils.replace(price, "$", "");
                price = StringUtils.replace(price, ",", ".");
                ProductCatalogPricing thisProductCatalogPricing = new ProductCatalogPricing();
                if (!"".equals(StringUtils.toString(price))) {
                  thisProductCatalogPricing.setPriceAmount(price);
                  thisProductCatalog.getPriceList().add(
                      thisProductCatalogPricing);
                }
              }
              ArrayList categoryInstances = propertyMap
                  .getDependencyMapList("productCategory");
              i = categoryInstances.iterator();
              while (i.hasNext()) {
                PropertyMap thisMap = (PropertyMap) i.next();
                String categoryName = this.getValue(thisRecord, thisMap
                    .getProperty("name"));
                if (!"".equals(StringUtils.toString(categoryName))) {
                  StringTokenizer categoryNamesList = new StringTokenizer(
                      categoryName, ",", false);
                  while (categoryNamesList.hasMoreTokens()) {
                    String tmp = categoryNamesList.nextToken();
                    ProductCategory tmpProductCategory = new ProductCategory();
                    tmpProductCategory.setImportId(this.getId());
                    tmpProductCategory.setStatusId(Import.PROCESSED_UNAPPROVED);
                    if (productCategoryList.getCategoryByName(tmp) != null) {
                      tmpProductCategory = productCategoryList
                          .getCategoryByName(tmp);
                    }
                    tmpProductCategory.setName(tmp);
                    thisProductCatalog.getCategoryList()
                        .add(tmpProductCategory);
                  }
                }
              }
              String smallImageName = this.getValue(thisRecord, propertyMap
                  .getProperty("smallImageName"));
              if (!"".equals(StringUtils.toString(smallImageName))) {
                if (!images.isEmpty()) {
                  if (imageList.containsKey(smallImageName)) {
                    images.put(smallImageName, null);
                    thisProductCatalog.setSmallImageName(smallImageName);
                  } else {
                    error.append("; Small Image Name is absent in archive");
                  }
                } else {
                  if (imageList.containsKey(smallImageName)) {
                    images.put(smallImageName, null);
                    thisProductCatalog.setSmallImageName(smallImageName);
                  } else {
                    error.append("; Small Image Name is absent in archive");
                  }
                }
              }

              String largeImageName = this.getValue(thisRecord, propertyMap
                  .getProperty("largeImageName"));
              if (!"".equals(StringUtils.toString(largeImageName))) {
                if (!images.isEmpty()) {
                  if (imageList.containsKey(largeImageName)) {
                    images.put(largeImageName, null);
                    thisProductCatalog.setLargeImageName(largeImageName);
                  } else {
                    error.append("; Large Image Name is absent in archive");
                  }
                } else {
                  if (imageList.containsKey(largeImageName)) {
                    images.put(largeImageName, null);
                    thisProductCatalog.setLargeImageName(largeImageName);
                  } else {
                    error.append("; Large Image Name is absent in archive");
                  }
                }
              }
              String thumbnailImageName = this.getValue(thisRecord, propertyMap
                  .getProperty("thumbnailImageName"));
              if (!"".equals(StringUtils.toString(thumbnailImageName))) {
                if (!images.isEmpty()) {
                  if (imageList.containsKey(thumbnailImageName)) {
                    images.put(thumbnailImageName, null);
                    thisProductCatalog.setThumbnailImageName(thumbnailImageName);
                  } else {
                    error.append("; Thumbnail Image Name is absent in archive");
                  }
                } else {
                  if (imageList.containsKey(thumbnailImageName)) {
                    images.put(thumbnailImageName, null);
                    thisProductCatalog.setThumbnailImageName(thumbnailImageName);
                  } else {
                    error.append("; Thumbnail Image Name is absent in archive");
                  }
                }
              }
              String shortDescription = this.getValue(thisRecord, propertyMap
                  .getProperty("shortDescription"));
              if (!"".equals(StringUtils.toString(shortDescription))) {
                thisProductCatalog.setShortDescription(shortDescription);
              }

              String longDescription = this.getValue(thisRecord, propertyMap
                  .getProperty("longDescription"));
              if (!"".equals(StringUtils.toString(longDescription))) {
                thisProductCatalog.setLongDescription(longDescription);
              }
              thisProductCatalog.setAbbreviation(this.getValue(thisRecord,
                  propertyMap.getProperty("abbreviation")));
              if (this.getValue(thisRecord, propertyMap
                  .getProperty("estimatedShipTime")) != null) {
                thisProductCatalog.setEstimatedShipTime(this.getValue(
                    thisRecord, propertyMap.getProperty("estimatedShipTime")));
              }
              // List Order              
              if (this.getValue(thisRecord, propertyMap
                  .getProperty("listOrder")) != null) {
                thisProductCatalog.setListOrder(this.getValue(thisRecord,
                    propertyMap.getProperty("listOrder")));
              }
              String productCode = this.getValue(thisRecord, propertyMap
                  .getProperty("sku"));
              // Product Code
              if (!"".equals(StringUtils.toString(productCode))) {
                thisProductCatalog.setSku(productCode);
              }
              thisProductCatalog.setSpecialNotes(this.getValue(thisRecord,
                  propertyMap.getProperty("specialNotes")));
              thisProductCatalog.setInStock(this.getValue(thisRecord,
                  propertyMap.getProperty("inStock")));
              thisProductCatalog.setStartDate(this.getValue(thisRecord,
                  propertyMap.getProperty("startDate")));
              thisProductCatalog.setExpirationDate(this.getValue(thisRecord,
                  propertyMap.getProperty("expirationDate")));
              thisProductCatalog.setTrashedDate(this.getValue(thisRecord,
                  propertyMap.getProperty("trashedDate")));

              // entered by
              String propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("enteredBy"));
              if (!"".equals(StringUtils.toString(propertyValue))) {
                try {
                  User user = userList.getUserById(Integer
                      .parseInt(propertyValue));
                  thisProductCatalog.setEnteredBy((user != null ? user.getId()
                      : -1));

                } catch (NumberFormatException nfe) {
                  thisProductCatalog.setEnteredBy(userList
                      .getUserIdByName(propertyValue));

                }
                if (thisProductCatalog.getEnteredBy() == -1) {
                  thisProductCatalog.setEnteredBy(userId);
                }
              } else {
                thisProductCatalog.setEnteredBy(userId);

              }

              // modified by
              propertyValue = this.getValue(thisRecord, propertyMap
                  .getProperty("modifiedBy"));
              if (!"".equals(StringUtils.toString(propertyValue))) {
                try {
                  User user = userList.getUserById(Integer
                      .parseInt(propertyValue));
                  thisProductCatalog.setModifiedBy((user != null ? user.getId()
                      : -1));

                } catch (NumberFormatException nfe) {
                  thisProductCatalog.setModifiedBy(userList
                      .getUserIdByName(propertyValue));
                }
                if (thisProductCatalog.getModifiedBy() == -1) {
                  thisProductCatalog.setModifiedBy(userId);
                }
              } else {
                thisProductCatalog.setModifiedBy(userId);
              }
              thisProductCatalog.setEnabled(true);
              thisProductCatalog.setInStock(true);

              if (error.length() == 0) {
                // insert the record
                boolean isValid = ObjectValidator.validate(null, db, thisProductCatalog);
                if (isValid) {

                  recordInserted = thisProductCatalog.insert(db);
                  if (recordInserted) {
                    // saving smallImage
                    if (!imageList.isEmpty()) {
                      FileItem smallImage = null;
                      FileItem largeImage = null;
                      FileItem thumbnailImage = null;
                      if (!"".equals(StringUtils.toString(thisProductCatalog.getSmallImageName())))
                      {
                        smallImage = imageList.saveImage(db, thisProductCatalog.getSmallImageName(), thisProductCatalog.getId(), userId);
                      }
                      // saving LargeImage
                      if (!"".equals(StringUtils.toString(thisProductCatalog.getLargeImageName())))
                      {
                        largeImage = imageList.saveImage(db, thisProductCatalog.getLargeImageName(), thisProductCatalog.getId(), userId);
                      }
                      // saving ThumbnailImage
                      if (!"".equals(StringUtils.toString(thisProductCatalog
                          .getThumbnailImageName()))) {
                        thumbnailImage = imageList.saveImage(db,
                            thisProductCatalog.getThumbnailImageName(),
                            thisProductCatalog.getId(), userId);
                      }
                      if (smallImage != null) {
                        thisProductCatalog.setSmallImageId(smallImage.getId());
                      }
                      if (largeImage != null) {
                        thisProductCatalog.setLargeImageId(largeImage.getId());
                      }
                      if (thumbnailImage != null) {
                        thisProductCatalog.setThumbnailImageId(thumbnailImage
                            .getId());
                      }
                      
                      if (smallImage != null || largeImage != null || thumbnailImage != null) {
                        int count = thisProductCatalog.updateImages(db);
                        if (count <= 0) {
                          recordInserted = false;
                        }
                      }
                    }
                  }
                }
                if (recordInserted) {
                  incrementTotalImportedRecords();
                } else {
                  incrementTotalFailedRecords();
                }
              } else {
                recordError(error.toString(), line, recordCount);
              }
            }
          } catch (Exception unknownException) {
            unknownException.printStackTrace();
            if (error.length() == 0) {
              recordError(unknownException.toString(), line, recordCount);
            } else {
              recordError(
                  error.toString() + "; " + unknownException.toString(), line,
                  recordCount);
            }
          }
        } else {
          done = true;
        }
      }

      if (done) {
        this.setStatusId(Import.PROCESSED_UNAPPROVED);
      }
    } catch (Exception e) {
      e.printStackTrace();
      recordError(e.toString(), "", -1);
      try {
        // update status
        this.setStatusId(FAILED);
      } catch (Exception statusException) {
        e.printStackTrace();
        // error updating status
      }
    } finally {
      if (importThread == currentThread) {
        stop();
      }
    }
  }

  /**
   * Cancel the import
   */
  public void cancel() {
    try {
      // do not use stop to stop the thread, nullify it
      importThread = null;

      // set the status to canceled
      this.setStatusId(Import.CANCELED);

      // perform clean up
      destroy();
    } catch (Exception e) {
    }
  }

  /**
   * Stops the thread
   */
  public void stop() {
    // do not use stop() to stop the thread, nullify it
    importThread = null;

    // perform clean up
    destroy();
  }

  /**
   * Description of the Method
   */
  public void destroy() {
    // set status of the import and clean up thread pool
    if (System.getProperty("DEBUG") != null) {
      System.out
          .println("Product Catalog Import -> Starting cleanup for ImportId: "
              + this.getId());
    }

    try {
      // update status, total imported/failed records
      recordResults(db);

      // flush the log file
      fos.flush();
      fos.close();

      if (this.getTotalFailedRecords() > 0) {
        // Store the error file as a version
        fileItem.setSubject("Error file");
        fileItem.setFilename(fileItem.getFilename() + "_error");
        fileItem.setClientFilename(this.getId() + "_error.csv");
        fileItem.setVersion(ERROR_FILE_VERSION);
        fileItem.setSize((int) errorFile.length());
        fileItem.insertVersion(db);
      } else {
        errorFile.delete();
      }

      // report back to manager
      manager.free(this);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace();
      }
    } finally {
      // release the database connection
      manager.free(db);
    }
  }

  /**
   * Logs an error when a record fails
   *
   * @param error      Description of the Parameter
   * @param line       Description of the Parameter
   * @param lineNumber Description of the Parameter
   */
  private void recordError(String error, String line, int lineNumber) {
    try {
      // log errors in the temp file created under $FILELIBRARY/_imports/
      if (lineNumber == 0) {
        // header:append error column
        line += "," + "\"_ERROR\"";
      } else if (lineNumber == -1) {
        // general error, mostly before import started
        line += error;
      } else if (lineNumber > 0) {
        // append the error
        line += ",\"" + error + "\"";

        // a record has failed, increment the failure count
        this.incrementTotalFailedRecords();
      }

      // add next line character
      // TODO: Change this to a CUSTOM row delimiter if import type is CUSTOM
      line += "\n";

      fos.write(line);
    } catch (IOException e) {
      // import should not fail because of logging error
    }
  }

  /**
   * Gets the value attribute of the ProductCatalogImport object
   *
   * @param thisRecord Description of the Parameter
   * @param type       Description of the Parameter
   * @return The value value
   */
  private String getValue(ArrayList thisRecord, Property type) {
    String value = null;
    if (type.getMappedColumn() > -1
        && thisRecord.size() > type.getMappedColumn()) {
      value = (String) thisRecord.get(type.getMappedColumn());
    } else if (!"".equals(StringUtils.toString(type.getDefaultValue()))) {
      value = type.getDefaultValue();
    }
    return value;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public DependencyList processDependencies(Connection db) throws SQLException {
    DependencyList dependencyList = new DependencyList();
    int i = 0;
    int recordCount = 0;
    PreparedStatement pst = db
        .prepareStatement("SELECT count(*) as recordcount "
            + "FROM product_catalog " + "WHERE import_id = ? ");
    pst.setInt(++i, this.getId());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      recordCount = rs.getInt("recordCount");
    }
    rs.close();
    pst.close();
    Dependency thisDependency = new Dependency();
    thisDependency.setName("products");
    thisDependency.setCount(recordCount);
    thisDependency.setCanDelete(true);
    dependencyList.add(thisDependency);
    return dependencyList;
  }

  /**
   * Deletes the import
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean delete(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);

      // delete all imported records
      if (this.getStatusId() != UNPROCESSED || this.getStatusId() != QUEUED) {
        deleteImportedRecords(db, this.getId());
      }

      // delete import record
      PreparedStatement pst = db.prepareStatement("DELETE from import "
          + "WHERE import_id = ? ");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();

      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }

  /**
   * Deletes all imported records
   *
   * @param db           Description of the Parameter
   * @param thisImportId Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean deleteImportedRecords(Connection db, int thisImportId)
      throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db
          .prepareStatement("DELETE FROM product_catalog "
              + "WHERE import_id = ?");
      pst.setInt(1, thisImportId);
      pst.executeUpdate();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildFileDetails(Connection db) throws SQLException {
    this.setFile(new FileItem(db, "%.csv", this.getId(), this.getType()));
    this.getFile().buildVersionList(db);
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildZipFileDetails(Connection db) throws SQLException {
    zipFileItem = new FileItem(db, "%.zip", this.getId(), this.getType());
    zipFileItem.buildVersionList(db);
  }

  /**
   * Gets the zipFileItem attribute of the ProductCatalogImport object
   *
   * @return The zipFileItem value
   */
  public FileItem getZipFileItem() {
    return zipFileItem;
  }

  /**
   * Sets the zipFileItem attribute of the ProductCatalogImport object
   *
   * @param zipFileItem The new zipFileItem value
   */
  public void setZipFileItem(FileItem zipFileItem) {
    this.zipFileItem = zipFileItem;
  }

}
