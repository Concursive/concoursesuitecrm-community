package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Vector;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import org.theseus.actions.*;
import com.darkhorseventures.utils.*;

public final class ForwardNote extends CFSModule {


	public String executeCommandShowForm(ActionContext context) {
    		Exception errorMessage = null;

    		String linkModId = (String)context.getRequest().getParameter("linkModuleId");
    		String linkRecId = (String)context.getRequest().getParameter("linkRecordId");
		
		CFSNote thisNote = new CFSNote();
		UserBean thisUser = (UserBean) context.getSession().getAttribute("User");
		
		UserList list = new UserList();
		list.setEnabled(UserList.TRUE);
		list.setBuildContact(false);
		list.setBuildHierarchy(false);
		list.setBuildPermissions(false);
    
    		Connection db = null;
    		try {
     			 db = getConnection(context);
			 
			 if (Integer.parseInt(linkModId) == Constants.CONTACTS_CALLS) {
				Call thisCall = new Call(db, linkRecId);
				thisNote.setBody("Type: " + thisCall.getCallType() + "\nLength: " + thisCall.getLengthText() + 
					"\nSubject: " + thisCall.getSubject() + "\nNotes: " + thisCall.getNotes() + "\nEntered: " 
					+ thisCall.getEnteredName() + " - " + thisCall.getEnteredString() + "\nModified: " + 
					thisCall.getModifiedName() + " - " + thisCall.getModifiedString());
				context.getRequest().setAttribute("CallDetails", thisCall);
			 }
			 
			 else if (Integer.parseInt(linkModId) == Constants.CFSNOTE) {
				thisNote = new CFSNote(db, linkRecId);
				thisNote.setBody("----Original Message----\nFrom: " + thisNote.getSentName() + "\nTo: " +
					thisUser.getNameFirst() + " " + thisUser.getNameLast() + 
					"\nSubject: " + thisNote.getSubject() + "\n\n" + thisNote.getBody());
				thisNote.setSubject("Fwd: " + thisNote.getSubject());
			 }
		 
			 list.buildList(db);
      
      
    		} catch (Exception e) {
      			errorMessage = e;
    		} finally {
      			this.freeConnection(context, db);
    		}

    		if (errorMessage == null) {
			context.getRequest().setAttribute("UserList", list);
			context.getRequest().setAttribute("NoteDetails", thisNote);
      			if (context.getRequest().getParameter("popup") != null) {
        			return ("PopupOK");
      			} else {
       				 return ("ProjectCenterOK");
      			}
    		} else {
      			context.getRequest().setAttribute("Error", errorMessage);
      			return ("SystemError");
    		}
  	}
	
	public String executeCommandForward(ActionContext context) {
		Exception errorMessage = null;
		boolean recordInserted = false;
	
		CFSNote thisNote = (CFSNote)context.getFormBean();
		thisNote.setEnteredBy(getUserId(context));
		thisNote.setModifiedBy(getUserId(context));
		thisNote.setReplyId(getUserId(context));
		thisNote.setType(CFSNote.CALL);
		
		UserList list = new UserList();
		list.setEnabled(UserList.TRUE);
		list.setBuildContact(false);
		list.setBuildHierarchy(false);
		list.setBuildPermissions(false);
	
		Connection db = null;
		try {
			db = this.getConnection(context);
			recordInserted = thisNote.insert(db);
			context.getRequest().setAttribute("NoteDetails", thisNote);
			list.buildList(db);
	
			if (!recordInserted) {
				processErrors(context, thisNote.getErrors());
			}
		} catch (SQLException e) {
			errorMessage = e;
		} finally {
			this.freeConnection(context, db);
		}
	
		if (errorMessage == null) {
			context.getRequest().setAttribute("UserList", list);
		if (!recordInserted) {
			return ("PopupOK");
		} else {
			return ("PopupCloseOK");
		}
		} else {
			context.getRequest().setAttribute("Error", errorMessage);
			return ("SystemError");
		}
	}

}
