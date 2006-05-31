package org.aspcfs.modules.quotes.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.products.base.ProductCatalogPricing;
import org.aspcfs.modules.products.base.ProductOptionConfigurator;
import org.aspcfs.modules.products.configurator.OptionConfigurator;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteProduct;
import org.aspcfs.modules.quotes.base.QuoteProductBean;
import org.aspcfs.modules.quotes.base.QuoteProductOption;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.util.Iterator;


/**
 * Description of the Class
 *
 * @author partha
 * @version $Id: QuotesProducts.java,v 1.1.6.1 2004/10/19 20:19:03 mrajkowski
 *          Exp $
 * @created July 13, 2004
 */
public final class QuotesProducts extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String action = context.getAction().getActionName();
    addModuleBean(context, action, action);
    return (action + "OK");
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandModify(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    String quoteProductIdString = (String) context.getRequest().getParameter(
        "quoteProductId");
    int quoteId = Integer.parseInt(quoteIdString);
    int quoteProductId = Integer.parseInt(quoteProductIdString);
    Quote quote = null;
    QuoteProduct quoteProduct = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      //retrieve the quote from the database
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      quoteProduct = new QuoteProduct();
      quoteProduct.setBuildProduct(true);
      quoteProduct.setBuildProductOptions(true);
      quoteProduct.queryRecord(db, quoteProductId);
      context.getRequest().setAttribute("quoteProduct", quoteProduct);
      //Check access permission to organization record
      if (quoteProduct.getQuoteId() == quote.getId()){
        if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
          return ("PermissionError");
        }
      } else {
        return ("PermissionError");
      }

    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "ModifyOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    String quoteProductIdString = (String) context.getRequest().getParameter(
        "quoteProductId");
    String quantity = (String) context.getRequest().getParameter("quantity");
    String priceAmount = (String) context.getRequest().getParameter(
        "priceAmount");
    String comment = (String) context.getRequest().getParameter("comment");
    String estimatedDelivery = (String) context.getRequest().getParameter(
        "estimatedDelivery");

    QuoteProductBean bean = (QuoteProductBean) context.getFormBean();
    java.sql.Timestamp estimatedDeliveryDate = bean.getEstimatedDeliveryDate();
    boolean isValid = false;
    int quoteId = Integer.parseInt(quoteIdString);
    int quoteProductId = Integer.parseInt(quoteProductIdString);
    int resultCount = -1;
    Quote previousQuote = null;
    QuoteProduct quoteProduct = null;
    Connection db = null;
    try {
      db = this.getConnection(context);
      previousQuote = new Quote();
      previousQuote.setBuildProducts(true);
      previousQuote.queryRecord(db, quoteId);
      previousQuote.retrieveTicket(db);


      quoteProduct = new QuoteProduct();
      quoteProduct.setBuildProductOptions(true);
      quoteProduct.setBuildProduct(true);
      quoteProduct.queryRecord(db, quoteProductId);

      //Check access permission to organization record
      if (quoteProduct.getQuoteId() == previousQuote.getId()){
        if (!isRecordAccessPermitted(context, db, previousQuote.getOrgId())) {
          return ("PermissionError");
        }
      } else {
        return ("PermissionError");
      }

      quoteProduct.setPriceAmount(bean.getPriceAmount());
      quoteProduct.setComment(comment);
      quoteProduct.setQuantity(Integer.parseInt(quantity));
      quoteProduct.setEstimatedDelivery(estimatedDelivery);
      quoteProduct.setEstimatedDeliveryDate(estimatedDeliveryDate);
      isValid = this.validateObject(context, db, quoteProduct);
      //Check if the options are valid
      boolean optionsValid = true;
      Iterator i = quoteProduct.getProductOptionList().iterator();
      while (i.hasNext()) {
        QuoteProductOption thisOption = (QuoteProductOption) i.next();
        OptionConfigurator configurator =
            (OptionConfigurator) ProductOptionConfigurator.getConfigurator(
                db, thisOption.getConfiguratorId());

        configurator.queryProperties(db, thisOption.getOptionId(), false);
        if (!configurator.validateUserInput(context.getRequest())) {
          if (configurator.hasUserInput(context.getRequest())) {
            optionsValid = false;
            break;
          }
        }
      }
      if (isValid && optionsValid) {
        resultCount = quoteProduct.update(db, context.getRequest());
      }
      
      Quote quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      quote.retrieveTicket(db);
      processUpdateHook(context, previousQuote, quote);

      context.getRequest().setAttribute("quote", quote);
      context.getRequest().setAttribute("quoteProduct", quoteProduct);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    if (resultCount <= 0 || !isValid) {
      SystemStatus systemStatus = this.getSystemStatus(context);
      context.getRequest().setAttribute(
          "actionError", systemStatus.getLabel(
              "quoteProduct.option.validation.actionError"));
      return "ModifyOK";
    }
    return "SaveOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCreateForm(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit")) && !hasPermission(
            context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    int quoteId = Integer.parseInt(quoteIdString);
    Quote quote = null;
    Connection db = null;
    try {
      db = getConnection(context);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList currencySelect = systemStatus.getLookupList(
          db, "lookup_currency");
      currencySelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("CurrencySelect", currencySelect);

      LookupList recurringTypeSelect = systemStatus.getLookupList(
          db, "lookup_recurring_type");
      recurringTypeSelect.addItem(
          -1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute(
          "RecurringTypeSelect", recurringTypeSelect);

      quote = new Quote();
      quote.queryRecord(db, quoteId);
      //Check access permission to organization record
      if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
        return ("PermissionError");
      }
      quote.buildProducts(db);
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      QuoteProductBean bean = (QuoteProductBean) context.getFormBean();
      if (bean == null) {
        bean = new QuoteProductBean();
      }
      bean.setQuoteId(quoteId);
      context.getRequest().setAttribute("quoteProductBean", bean);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return "CreateFormOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandCreate(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit")) && !hasPermission(
            context, "admin-sysconfig-products-add")) {
      return ("PermissionError");
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    int quoteId = Integer.parseInt(quoteIdString);
    Quote previousQuote = null;
    boolean productInserted = false;
    boolean priceInserted = false;
    boolean itemInserted = false;
    boolean isValid = false;
    int userId = this.getUserId(context);
    ProductCatalog product = new ProductCatalog();
    ProductCatalogPricing pricing = new ProductCatalogPricing();
    QuoteProduct item = new QuoteProduct();
    Connection db = null;
    try {
      db = getConnection(context);
      previousQuote = new Quote();
      previousQuote.setBuildProducts(true);
      previousQuote.queryRecord(db, quoteId);
      previousQuote.retrieveTicket(db);

      QuoteProductBean bean = (QuoteProductBean) context.getFormBean();
      product = bean.getProduct();
      product.setEnteredBy(userId);
      product.setModifiedBy(userId);
      db.setAutoCommit(false);
      isValid = this.validateObject(context, db, product);
      if (isValid) {
        productInserted = product.insert(db);
      }

      if (productInserted && isValid) {
        pricing = bean.getPricing();
        pricing.setEnteredBy(userId);
        pricing.setModifiedBy(userId);
        isValid = this.validateObject(context, db, pricing);
        if (isValid) {
          priceInserted = pricing.insert(db);
        }
      }

      Quote quote = new Quote();
      quote.queryRecord(db, quoteId);
      if (priceInserted && isValid) {
        item = bean.getQuoteProduct();
        //Check access permission to organization record
        if (item.getQuoteId() == quote.getId()){
          if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
            return ("PermissionError");
          }
        } else {
          return ("PermissionError");
        }
        isValid = this.validateObject(context, db, item);
        if (isValid) {
          itemInserted = item.insert(db);
        }
      }
      quote.buildProducts(db);
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      // Update quote and opportunity Related Information
      processUpdateHook(context, previousQuote, quote);

      if (!itemInserted && !isValid) {
        db.rollback();
      } else {
        db.commit();
      }
    } catch (Exception e) {
      try {
        db.rollback();
      } catch (Exception e1) {
      }
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace();
      return ("SystemError");
    } finally {
      try {
        db.setAutoCommit(true);
      } catch (Exception e2) {
      }
      this.freeConnection(context, db);
    }
    if (!itemInserted || !isValid) {
      return executeCommandCreateForm(context);
    }
    return "CreateOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandClone(ActionContext context) {
    if (!(hasPermission(context, "quotes-quotes-edit") || hasPermission(
        context, "accounts-quotes-edit")
        || hasPermission(context, "leads-opportunities-edit"))) {
      return ("PermissionError");
    }
    String quoteIdString = (String) context.getRequest().getParameter(
        "quoteId");
    String quoteProductIdString = (String) context.getRequest().getParameter(
        "quoteProductId");
    int quoteId = Integer.parseInt(quoteIdString);
    int quoteProductId = Integer.parseInt(quoteProductIdString);
    Quote quote = null;
    QuoteProduct quoteProduct = null;
    int clone = -1;
    Connection db = null;
    try {
      db = this.getConnection(context);
      //retrieve the quote from the database
      quote = new Quote();
      quote.setBuildProducts(true);
      quote.queryRecord(db, quoteId);
      quote.retrieveTicket(db);
      context.getRequest().setAttribute("quote", quote);

      quoteProduct = new QuoteProduct();
      quoteProduct.setBuildProduct(true);
      quoteProduct.setBuildProductOptions(true);
      quoteProduct.queryRecord(db, quoteProductId);
      //Check access permission to organization record
      if (quoteProduct.getQuoteId() == quote.getId()){
        if (!isRecordAccessPermitted(context, db, quote.getOrgId())) {
          return ("PermissionError");
        }
      } else {
        return ("PermissionError");
      }
      clone = quoteProduct.clone(db);
      context.getRequest().setAttribute("quoteProduct", quoteProduct);
    } catch (Exception e) {
      e.printStackTrace();
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return "ModifyOK";
  }
}

