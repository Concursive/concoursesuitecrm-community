/**
 * 
 */
package org.aspcfs.modules.admin.actions;

import java.sql.Connection;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.MerchantPaymentGateway;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * @author Olga.Kaptyug
 * 
 * @created Aug 23, 2006
 * 
 */
public class MerchantPaymentGateways extends CFSModule {

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDetails(ActionContext context) {

    Connection db = null;
    MerchantPaymentGateway thisGateway = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      thisGateway = new MerchantPaymentGateway(db);
    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      context.getRequest().setAttribute("merchantPaymentGateway", thisGateway);
      return "DetailsOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDelete(ActionContext context) {

    Exception errorMessage = null;
    boolean recordDeleted = false;
    MerchantPaymentGateway thisGateway = null;
    Connection db = null;
    String returnUrl = context.getRequest().getParameter("return");
    try {

      db = this.getConnection(context);
      thisGateway = new MerchantPaymentGateway(db);
      if (thisGateway.getId() > -1) {
        recordDeleted = thisGateway.delete(db);
      }
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(errorMessage.getMessage());
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if (recordDeleted) {
        return "DeleteOK";
      } else {
        processErrors(context, thisGateway.getErrors());
        return "SystemError";
      }
    } else {
      context.getRequest().setAttribute(
          "actionError",
          this.getSystemStatus(context).getLabel(
              "object.validation.actionError.gatewayDeletion"));
      context.getRequest().setAttribute("refreshUrl", returnUrl);
      return ("DeleteError");
    }
  }

  /**
   * Description of the Method
   * 
   * @param context
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandSave(ActionContext context) {

    Connection db = null;
    int recordUpdated = -1;
    MerchantPaymentGateway updatedGateway = null;
    MerchantPaymentGateway gateway = (MerchantPaymentGateway) context
        .getFormBean();
    try {
      db = this.getConnection(context);

      if (gateway.getId() > -1) {
        gateway.setModifiedBy(getUserId(context));
        recordUpdated = gateway.update(db);
      } else {
        gateway.setEnteredBy(getUserId(context));
        recordUpdated = gateway.insert(db);
      }
      if (recordUpdated > 0) {
        updatedGateway = new MerchantPaymentGateway(db);
        context.getRequest().setAttribute("merchantPaymentGateway",
            updatedGateway);
      } else {
        context.getRequest().setAttribute("merchantPaymentGateway", gateway);
        return (executeCommandDefault(context));
      }
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      e.printStackTrace(System.out);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    if (recordUpdated > 0) {
      return (executeCommandDetails(context));

    }
    return "SystemError";
  }

  /**
   * @param context
   * @return
   */
  public String executeCommandDefault(ActionContext context) {
    Connection db = null;
    MerchantPaymentGateway gateway = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);
      gateway = new MerchantPaymentGateway(db);
      if (gateway.getId() > -1) {
        return (executeCommandDetails(context));
      }
      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList paymentSelect = systemStatus.getLookupList(db,
          "lookup_payment_gateway");
      paymentSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("paymentGatewayList", paymentSelect);

      gateway = new MerchantPaymentGateway(db);
      context.getRequest().setAttribute("merchantPaymentGateway", gateway);

    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return "ModifyOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

  /**
   * @param context
   * @return
   */
  public String executeCommandModify(ActionContext context) {
    Connection db = null;
    MerchantPaymentGateway gateway = null;
    Exception errorMessage = null;

    try {
      db = this.getConnection(context);

      SystemStatus systemStatus = this.getSystemStatus(context);
      LookupList paymentSelect = systemStatus.getLookupList(db,
          "lookup_payment_gateway");
      paymentSelect.addItem(-1, systemStatus.getLabel("calendar.none.4dashes"));
      context.getRequest().setAttribute("paymentGatewayList", paymentSelect);

      gateway = new MerchantPaymentGateway(db);
      context.getRequest().setAttribute("merchantPaymentGateway", gateway);

    } catch (Exception e) {
      errorMessage = e;
      e.printStackTrace();
    } finally {
      this.freeConnection(context, db);
    }

    if (errorMessage == null) {
      return "ModifyOK";
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }

}
