package org.aspcfs.modules.pipeline.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.aspcfs.utils.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.pipeline.beans.*;
import org.aspcfs.modules.pipeline.base.*;


public final class OpportunityForm extends CFSModule {
  /**
   *  Prepares supporting form data required for any Opportunity object.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Exception errorMessage = null;
    Connection db = null;
    OpportunityComponent thisComponent = null;
    //Get opportunity object from the request (bean is not accessible here)
    OpportunityBean thisOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");
    if(thisOpp == null){
	thisComponent = (OpportunityComponent) context.getRequest().getAttribute("ComponentDetails");
    }
    
    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();

    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    unitSelect.build();

    context.getRequest().setAttribute("BusTypeList", busTypeSelect);
    context.getRequest().setAttribute("UnitTypeList", unitSelect);
    try {
      db = this.getConnection(context);
      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);
    } catch (SQLException e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    if (errorMessage == null) {
      if ("true".equals(context.getRequest().getParameter("popup"))) {
        return "PreparePopupAddOK";
      }
      if ((thisOpp != null && thisOpp.getHeader().getId() > 0) || (thisComponent !=null && thisComponent.getId() >0)) {
        return "PrepareModifyOK";
      } else {
        return "PrepareAddOK";
      }
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
}

