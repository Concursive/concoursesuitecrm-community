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
/**
 * 
 */
package org.aspcfs.modules.products.utils;

/**
 * @author Olga.Kaptyug
 *
 * @created Oct 19, 2006
 *
 */
public class ProductCategoryCount {
    

    private int productCategoryId = -1;
    private int countOfProduct = -1;
    private int parentProductCategoryId = -1;



    public ProductCategoryCount() {
    }

    /**
     * @return the countOfProduct
     */
    public int getCountOfProduct() {
      return countOfProduct;
    }

    /**
     * @param coutOfProduct
     *          the coutOfProduct to set
     */
    public void setCountOfProduct(int countOfProduct) {
      this.countOfProduct = countOfProduct;
    }

    /**
     * @return the parentProductCategoryId
     */
    public int getParentProductCategoryId() {
      return parentProductCategoryId;
    }

    /**
     * @param parentProductCategoryId
     *          the parentProductCategoryId to set
     */
    public void setParentProductCategoryId(int parentProductCategoryId) {
      this.parentProductCategoryId = parentProductCategoryId;
    }

    /**
     * @return the productCategoryId
     */
    public int getProductCategoryId() {
      return productCategoryId;
    }

    /**
     * @param productCategoryId
     *          the productCategoryId to set
     */
    public void setProductCategoryId(int productCategoryId) {
      this.productCategoryId = productCategoryId;
    }


  }

