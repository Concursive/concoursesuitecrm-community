/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.contacts.components;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.components.EmailDigestUtil;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.contacts.base.CallParticipant;
import org.aspcfs.modules.contacts.base.CallParticipantList;
import org.aspcfs.modules.contacts.base.Contact;

/**
 * Description of the EmailActivityParticipants
 *
 * @author Artem.Zakolodkin
 * @created May 5, 2007
 */
public class EmailActivityParticipants extends ObjectHookComponent implements ComponentInterface {
	private static final String PROCESS_LENGTH = "process.length";
	private static final String PROCESS_START_DATE = "process.startDate";
	private static final String PROCESS_SUBJECT = "process.subject";
	public final static String SUBJECT = "notification.subject";
	public final static String PROCESS_PARTICIPANTS = "process.participants";
	public final static String PROCESS_NOTES = "process.notes";

	/*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.apps.workFlowManager.ComponentInterface#execute(org.aspcfs.apps.workFlowManager.ComponentContext)
   */
	public boolean execute(ComponentContext context) {
		boolean result = false;
		Connection db = null;
		Call thisCall = (Call) context.getThisObject();
		Call previousCall = (Call) context.getPreviousObject();
		try {
			db = ObjectHookComponent.getConnection(context);
			if (thisCall != null) {
				thisCall.buildParticipants(db);
				thisCall.setCallLengthDurationText(db);
				thisCall.setFollowupCallLengthDurationText(db);
				if (thisCall.getEmailFollowupParticipants()) {
					context.setAttribute(PROCESS_SUBJECT, thisCall.getAlertText());
					context.setAttribute(PROCESS_START_DATE, thisCall.getAlertDate().toString());
					context.setAttribute(PROCESS_LENGTH, thisCall.getFollowupLength() + " " + thisCall.getFollowupCallLengthDurationText());
					context.setAttribute(PROCESS_NOTES, thisCall.getFollowupNotes());
					sendEmailToList(context, thisCall.getFollowupParticipants(), previousCall != null ? previousCall.getFollowupParticipants() : null, db);
				}
				if (thisCall.getEmailParticipants()) {
					context.setAttribute(PROCESS_SUBJECT, thisCall.getSubject());
					context.setAttribute(PROCESS_START_DATE, thisCall.getCallStartDate().toString());
					context.setAttribute(PROCESS_LENGTH, thisCall.getLength() + " " + thisCall.getCallLengthDurationText());
					context.setAttribute(PROCESS_NOTES, thisCall.getNotes());
					sendEmailToList(context, thisCall.getParticipants(), previousCall != null ? previousCall.getParticipants(): null, db);
				}
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ObjectHookComponent.freeConnection(context, db);
		}
		return result;
	}

	private void sendEmailToList(ComponentContext context, CallParticipantList newParticipants, CallParticipantList previousParticipants,  Connection db) throws SQLException {
		HashMap mailList = new HashMap();
		String thisSubject = context.getParameter(EmailActivityParticipants.SUBJECT);
		context.setAttribute("previousParticipants", previousParticipants);
		context.setAttribute("newParticipants", newParticipants);
		if (previousParticipants == null)
			context.setAttribute(PROCESS_PARTICIPANTS, "");
		EmailDigestUtil.appendEmailContacts(db, mailList, newParticipants.getTokenizedStringUserIds(","), "", thisSubject);
		EmailDigestUtil.sendMail(context, mailList);
	}
	/*
   * (non-Javadoc)
   * 
   * @see org.aspcfs.apps.workFlowManager.ComponentInterface#getDescription()
   */
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
