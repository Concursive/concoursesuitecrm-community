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

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 27, 2003
 *@version    $Id: OpportunityForm.java,v 1.5 2003/08/25 15:13:06 mrajkowski Exp
 *      $
 */
public final class OpportunityForm extends CFSModule {

  /**
   *  Prepares supporting form data required for any Opportunity object.
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepare(ActionContext context) {
    Connection db = null;
    OpportunityComponent thisComponent = null;
    //Get opportunity object from the request (bean is not accessible here)
    OpportunityBean thisOpp = (OpportunityBean) context.getRequest().getAttribute("OppDetails");
    if (thisOpp == null) {
      thisComponent = (OpportunityComponent) context.getRequest().getAttribute("ComponentDetails");
    }
    //Business type list
    HtmlSelect busTypeSelect = new HtmlSelect();
    busTypeSelect.setSelectName("type");
    busTypeSelect.addItem("N", "New");
    busTypeSelect.addItem("E", "Existing");
    busTypeSelect.build();
    context.getRequest().setAttribute("BusTypeList", busTypeSelect);
    //Opporunity units list
    HtmlSelect unitSelect = new HtmlSelect();
    unitSelect.setSelectName("units");
    unitSelect.addItem("M", "Months");
    unitSelect.build();
    context.getRequest().setAttribute("UnitTypeList", unitSelect);
    try {
      db = this.getConnection(context);
      LookupList stageSelect = new LookupList(db, "lookup_stage");
      context.getRequest().setAttribute("StageList", stageSelect);
    } catch (Exception errorMessage) {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    boolean popup = "true".equals(context.getRequest().getParameter("popup"));
    if ((thisOpp != null && thisOpp.getHeader().getId() > 0) || (thisComponent != null && thisComponent.getId() > 0)) {
      if (popup) {
        return "PrepareModifyOppPopupOK";
      }
      return "PrepareModifyOppOK";
    } else {
      if (popup) {
        return "PrepareAddOppPopupOK";
      }
      return "PrepareAddOppOK";
    }
  }
}

