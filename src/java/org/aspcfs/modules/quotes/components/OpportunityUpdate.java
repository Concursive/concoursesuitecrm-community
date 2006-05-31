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
package org.aspcfs.modules.quotes.components;

import com.darkhorseventures.database.ConnectionElement;
import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityComponentList;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteProduct;
import org.aspcfs.modules.quotes.base.QuoteProductList;

import java.sql.Connection;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author
 *@created
 *@version    $Id$
 */
public class OpportunityUpdate extends ObjectHookComponent implements ComponentInterface {


  /**
   *  Gets the description attribute of the LoadTicketDetails object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Update Opportunity Information related to a quote";
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Quote thisQuote = (Quote) context.getThisObject();
    Quote previousQuote = (Quote) context.getPreviousObject();
    Connection db = null;
    try {
      db = this.getConnection(context);
      if (thisQuote != null){
        if (thisQuote.getId() > -1) {
          if (thisQuote.getHeaderId() != -1) {
            //Update best guess estimate
            OpportunityComponentList opportunityComponentList = new OpportunityComponentList();
            opportunityComponentList.setHeaderId(thisQuote.getHeaderId());
            opportunityComponentList.buildList(db);
            if (opportunityComponentList.size() > 0) {
              OpportunityComponent opportunityComponent = (OpportunityComponent) opportunityComponentList.get(0);
              opportunityComponent.setGuess(thisQuote.getGrandTotal());
              opportunityComponent.update(db);
              invalidateUserData(context, opportunityComponent.getOwner());
            }
  
            //Update custom integer of opportunity header (group size) with the ticket quantity
            boolean foundTicketProduct = false;
            QuoteProductList quoteProductList = thisQuote.getProductList();
            Iterator quoteProductIterator = quoteProductList.iterator();
            int groupSize = 0;
            while (quoteProductIterator.hasNext()) {
              QuoteProduct tmpQuoteProduct = (QuoteProduct) quoteProductIterator.next();
              ProductCatalog product = new ProductCatalog(db, tmpQuoteProduct.getProductId());
              // Is the product a ticket, if so set custom1Integer
              if ("00010".equals(product.getSku())) {
                foundTicketProduct = true;
                groupSize = groupSize + tmpQuoteProduct.getQuantity();
              }
            }
            if (!foundTicketProduct) {
              OpportunityHeader opportunityHeader = new OpportunityHeader(db, thisQuote.getHeaderId());
              opportunityHeader.setCustom1Integer(-1);
              opportunityHeader.update(db);
            } else {
              OpportunityHeader opportunityHeader = new OpportunityHeader(db, thisQuote.getHeaderId());
              opportunityHeader.setCustom1Integer(groupSize);
              opportunityHeader.update(db);
            }
          }
        }
      }
      if (previousQuote != null){
        if (previousQuote.getHeaderId() != thisQuote.getHeaderId()) {
          
          //Set best guess of the previous headers component to zero
          OpportunityComponentList newOpportunityComponentList = new OpportunityComponentList();
          newOpportunityComponentList.setHeaderId(previousQuote.getHeaderId());
          newOpportunityComponentList.buildList(db);
          if (newOpportunityComponentList.size() > 0) {
            OpportunityComponent opportunityComponent = (OpportunityComponent) newOpportunityComponentList.get(0);
            opportunityComponent.setGuess(0.0);
            opportunityComponent.update(db);
            invalidateUserData(context, opportunityComponent.getOwner());
          }
        
          //Reset the custom integer (number of passengers)
          OpportunityHeader opportunityHeader = new OpportunityHeader(db, previousQuote.getHeaderId());
          opportunityHeader.setCustom1Integer(-1);
          opportunityHeader.update(db);

        }
      }
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@param  userId   Description of the Parameter
   */
  private void invalidateUserData(ComponentContext context, int userId) {
    ConnectionElement ce = (ConnectionElement) context.getAttribute("ConnectionElement");
    SystemStatus systemStatus = (SystemStatus) context.getAttribute("SystemStatus");
    systemStatus.getHierarchyList().getUser(userId).setIsValid(false, true);
    //systemStatus.getHierarchyList().getUser(userId).setIsValidLead(false, true);
  }
}

