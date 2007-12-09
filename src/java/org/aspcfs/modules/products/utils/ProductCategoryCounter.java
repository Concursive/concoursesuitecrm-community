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

package org.aspcfs.modules.products.utils;

import org.aspcfs.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Description of the Class
 *
 * @author Aliaksei.Yarotski
 * @version $Id:
 *          Exp $
 * @created October 04, 2006
 */
public class ProductCategoryCounter {

  private HashMap rawData;
  private HashMap result;
  private ProductCategoryCount productCategoryCounter;

  private ProductCategoryCounter() {
  }

  public void refreshStatistics(Connection db) throws SQLException {
    rawData = new HashMap();
    result = new HashMap();
    int itemId = 0;
    int count = 0;

    PreparedStatement ps = db.prepareStatement(
        "select pc.category_id as category_id," +
            "       pccm.count_of_product as count_of_product," +
            "       pc.parent_id as parent_id" +
            "    from product_category pc" +
            "    left join (select category_id, " +
            "                      count('x') as count_of_product" +
            "                    from product_catalog_category_map pccm" +
            "                          join product_catalog p on p.product_id = pccm.product_id and " + DatabaseUtils.parseReservedWord(db, "active") + " = ? and enabled = ?" +
            "                  group by category_id) pccm  on (pccm.category_id = pc.category_id)" +
            "order by pc.parent_id desc"
    );
    ps.setBoolean(1, true);
    ps.setBoolean(2, true);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      productCategoryCounter = new ProductCategoryCount();
      productCategoryCounter.setProductCategoryId(rs.getInt("category_id"));
      productCategoryCounter.setParentProductCategoryId(DatabaseUtils.getInt(rs, "parent_id"));
      productCategoryCounter.setCountOfProduct(DatabaseUtils.getInt(rs, "count_of_product", 0));
      rawData.put(new Integer(itemId++), productCategoryCounter);
    }
    rs.close();
    ps.close();
    Integer key;
    for (int i = 0; i <= itemId; i++) {
      key = new Integer(i);
      if (rawData.containsKey(key)) {
        productCategoryCounter = (ProductCategoryCount) rawData.get(key);
        if (productCategoryCounter.getParentProductCategoryId() == -1) {
          rawData.remove(key);
          count = this.buildHierarchy(itemId, productCategoryCounter.getProductCategoryId());
          productCategoryCounter.setCountOfProduct(productCategoryCounter.getCountOfProduct() + count);
          result.put(new Integer(productCategoryCounter.getProductCategoryId()), productCategoryCounter);
        }
      }
    }
  }

  private int buildHierarchy(int size, int parentItemId) throws SQLException {
    ProductCategoryCount pcs;
    int count = 0;
    int cCount = 0;
    Integer key;
    for (int i = 0; i <= size; i++) {
      key = new Integer(i);
      if (rawData.containsKey(key)) {
        pcs = (ProductCategoryCount) rawData.get(key);
        if (pcs.getParentProductCategoryId() == parentItemId) {
          rawData.remove(key);
          cCount = this.buildHierarchy(size, pcs.getProductCategoryId());
          pcs.setCountOfProduct(pcs.getCountOfProduct() + cCount);
          if (pcs.getCountOfProduct() > 0) {
            result.put(new Integer(pcs.getProductCategoryId()), pcs);
          }
          count = count + pcs.getCountOfProduct();
        }
      }
    }
    return count;
  }

  /**
   * @return the productCategoryCounter
   */
  public ProductCategoryCount getProductCategoryCountObject(int productCategoryId) {
    Integer id = new Integer(productCategoryId);
    if (result.containsKey(id))
      return (ProductCategoryCount) result.get(id);
    return null;
  }

  /**
   * @return the productCategoryCounter
   */
  public int getProductCategoryCount(int productCategoryId) {
    Integer id = new Integer(productCategoryId);
    if (result.containsKey(id))
      return ((ProductCategoryCount) result.get(id)).getCountOfProduct();
    return -1;
  }

  public static ProductCategoryCounter getProductCategoryCounter(Connection db) throws SQLException {
    ProductCategoryCounter genericProductCounter = new ProductCategoryCounter();
    genericProductCounter.refreshStatistics(db);
    return genericProductCounter;
  }
}


