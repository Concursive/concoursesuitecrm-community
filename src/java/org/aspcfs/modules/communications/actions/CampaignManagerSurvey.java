package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;


public final class CampaignManagerSurvey extends CFSModule {

  public String executeCommandView(ActionContext context) {
	  
	  	if (!(hasPermission(context, "campaign-campaigns-surveys-view"))) {
	    		return ("PermissionError");
    		}
		
    Exception errorMessage = null;

    PagedListInfo pagedListInfo = this.getPagedListInfo(context, "CampaignSurveyListInfo");
    pagedListInfo.setLink("/CampaignManagerSurvey.do?command=View");

    Connection db = null;
    
    /**
    MessageList messageList = new MessageList();

    try {
      db = this.getConnection(context);
      messageList.setPagedListInfo(pagedListInfo);
      if ("all".equals(pagedListInfo.getListView())) {
        messageList.setOwnerIdRange(this.getUserRange(context));
      } else {
        messageList.setOwner(this.getUserId(context));
      }
      messageList.buildList(db);
    } catch (Exception e) {
      errorMessage = e;
    } finally {
      this.freeConnection(context, db);
    }
    */
    
    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String)context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "View Surveys");

    if (errorMessage == null) {
      //context.getRequest().setAttribute("MessageList", messageList);
      return ("ViewOK");
    } else {
      context.getRequest().setAttribute("Error", errorMessage);
      return ("SystemError");
    }
  }
  
  public String executeCommandAdd(ActionContext context) {
	  
	if (!(hasPermission(context, "campaign-campaigns-surveys-add"))) {
		return ("PermissionError");
	}
	
    HtmlSelect surveyType = new HtmlSelect();
    surveyType.setSelectName("type");
    surveyType.addItem(0, "--Please Select--");
    surveyType.addItem(1, "Open-Ended");
    surveyType.addItem(2, "Quantitative (no comments)");
    surveyType.addItem(3, "Quantitative (with comments)");
    surveyType.build();
    
    HtmlSelect surveyLength = new HtmlSelect();
    surveyLength.setSelectName("length");
    surveyLength.addItem(0, "--Please Select--");
    surveyLength.addItem(5, "5");
    surveyLength.addItem(9, "9");
    surveyLength.build();
		
    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String)context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }
    context.getRequest().setAttribute("submenu", submenu);
    context.getRequest().setAttribute("SurveyType", surveyType);
    context.getRequest().setAttribute("SurveyLength", surveyLength);
    addModuleBean(context, submenu, "Add Message");
    return ("AddOK");
  }
  
  public String executeCommandAdd2(ActionContext context) {
	  
	if (!(hasPermission(context, "campaign-campaigns-surveys-add"))) {
		return ("PermissionError");
	}
	
	java.util.Enumeration e = context.getRequest().getParameterNames();
	

	
	Survey thisSurvey = new Survey();
	
	//java.lang.reflect.Field[] f = new java.lang.reflect.Field[5];
	java.lang.reflect.Field[] f = thisSurvey.getClass().getDeclaredFields();
	
	for (int i=0; i<f.length; i++) {
		//System.out.println(f[i].getName());
	}
		
	while (e.hasMoreElements()) {
		System.out.println(e.nextElement());
	}
	
    String submenu = context.getRequest().getParameter("submenu");
    if (submenu == null) {
      submenu = (String)context.getRequest().getAttribute("submenu");
    }
    if (submenu == null) {
      submenu = "ManageSurveys";
    }
    context.getRequest().setAttribute("submenu", submenu);
    addModuleBean(context, submenu, "Add Message");
    return ("Add2OK");
  }

}

