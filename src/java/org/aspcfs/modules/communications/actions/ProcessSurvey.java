/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.darkhorseventures.controller.CustomForm;
import com.darkhorseventures.utils.*;

public final class ProcessSurvey extends CFSModule {


  public String executeCommandDefault(ActionContext context) throws SQLException {
	Exception errorMessage = null;
	Survey thisSurvey = null;
	ConnectionPool sqlDriver = null;
	Connection db = null;
	
	CustomForm thisForm = getDynamicForm(context, "surveyview");
	String passedId = context.getRequest().getParameter("id");
	
	try {
		StringTokenizer st = new StringTokenizer(passedId, "|");
		String dbName = st.nextToken();
		String surveyId = st.nextToken();
		
		System.out.println("Getting driver");
		sqlDriver = (ConnectionPool)context.getServletContext().getAttribute("ConnectionPool");
		ConnectionElement ce = new ConnectionElement();
		ce.setUrl("jdbc:postgresql://127.0.0.1:5432/" + dbName);
		ce.setUsername("cfsdba");
		ce.setPassword("");
		ce.setDbName(dbName);
		System.out.println("Getting connection" + dbName);
		db = sqlDriver.getConnection(ce);
		System.out.println("Got connection");
      
		thisSurvey = new Survey(db, surveyId);
		thisForm.populate(thisSurvey);
	} catch (Exception e) {
		errorMessage = e;
	} finally {
		this.freeConnection(context, db);
	}
	
	
	if (errorMessage == null) {
		if (thisSurvey != null) {
			context.getRequest().setAttribute("Survey", thisSurvey);
			context.getRequest().setAttribute("CustomFormInfo", thisForm);
			return ("ViewOK");
		} else {
			context.getRequest().setAttribute("Error", "No Survey Found.");
			return ("SystemError");
		}
	} else {
		context.getRequest().setAttribute("Error", errorMessage);
		return ("SystemError");
	}
}

}

