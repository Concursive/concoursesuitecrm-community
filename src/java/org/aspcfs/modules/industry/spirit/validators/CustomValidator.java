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

package org.aspcfs.modules.industry.spirit.validators;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.quotes.base.QuoteProduct;
import org.aspcfs.modules.quotes.base.QuoteProductList;
import org.aspcfs.utils.ObjectUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Custom Validator for spirit
 *
 * @author kbhoopal
 * @version $Id$
 * @created June 18, 2005
 */
public class CustomValidator {

  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param db           Description of the Parameter
   * @param object       Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean validate(SystemStatus systemStatus, Connection db, Object object) throws SQLException {
    //  OpportunityComponent
    if (object.getClass().getName().equals("org.aspcfs.modules.pipeline.base.OpportunityComponent")) {
      OpportunityComponent oppComponent = (OpportunityComponent) object;
      if (oppComponent.getCloseProb() >= 0.8) {
        HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
        errors.put("closeProb" + "Error", " Close probability should be less than 80%");
      }

      //Setting term to 1 month
      oppComponent.setTerms(1.0);
      oppComponent.setUnits("M");
    }

    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.QuoteProductList")) {
      QuoteProductList quoteProductList = (QuoteProductList) object;
      Iterator productItr = quoteProductList.iterator();
      boolean foundTicket = false;
      while (productItr.hasNext()) {
        QuoteProduct quoteProduct = (QuoteProduct) productItr.next();
        ProductCatalog productCatalog = new ProductCatalog(db, quoteProduct.getProductId());
        if ((productCatalog.getSku().equals("00010")) &&
            (quoteProduct.getQuantity() > 0)) {
          foundTicket = true;
          break;
        }
      }
      if (!foundTicket) {
        HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
        //HashMap errors = new HashMap();
        errors.put("actionError", "This quote should have a ticket.");
      }
    }

    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.Quote")) {
      Quote quote = (Quote) object;
      OpportunityHeader header = new OpportunityHeader(db, quote.getHeaderId());
      if (header.getLock()) {
        QuoteList quoteList = new QuoteList();
        quoteList.setHeaderId(quote.getHeaderId());
        quoteList.buildList(db);
        if (quoteList.size() > 0) {
          Quote tmpQuote = (Quote) quoteList.get(0);
          //if the quote being modified is the same one it is compared against, then ignore
          //else throw error.
          if (tmpQuote.getId() != quote.getId()) {
            HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
            errors.put("headerId" + "Error", " A quote cannot be added to a locked opportunity");
          }
        } else {
          HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
          errors.put("headerId" + "Error", " A quote cannot be added to a locked opportunity");
        }
      }
    }
    return true;
  }

}

