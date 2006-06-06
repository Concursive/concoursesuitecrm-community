/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.products.base;

import junit.framework.TestCase;

public class ProductCatalogImportTest extends TestCase {
  private ProductCatalogImport productCatalogImport = new ProductCatalogImport();

  /*
   * Test method for 'org.aspcfs.modules.products.base.ProductCatalogImport.setOwner(int)'
   */
  public void testSetOwnerInt() {
    this.productCatalogImport.setOwner(123);
    TestCase.assertEquals(this.productCatalogImport.getOwner(), 123);
  }

  /*
   * Test method for 'org.aspcfs.modules.products.base.ProductCatalogImport.setOwner(String)'
   */
  public void testSetOwnerString() {
    this.productCatalogImport.setOwner("123");
    TestCase.assertEquals(this.productCatalogImport.getOwner(), 123);
  }

}
