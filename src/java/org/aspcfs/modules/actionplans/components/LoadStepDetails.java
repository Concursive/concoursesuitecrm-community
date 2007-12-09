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
package org.aspcfs.modules.actionplans.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.utils.web.LookupElement;

import java.sql.Connection;

/**
 *  Description of the Class
 *
 * @author     partha
 * @created    October 21, 2005
 * @version    $Id$
 */
public class LoadStepDetails extends ObjectHookComponent implements ComponentInterface {

  public final static String ORGANIZATION = "stepOrganization";
  public final static String CONTACT = "stepContact";
  public final static String TICKET = "stepTicket";
  public final static String ENTERED_BY_CONTACT = "stepEnteredByContact";
  public final static String MODIFIED_BY_CONTACT = "stepModifiedByContact";
  public final static String ASSIGNED_TO_CONTACT = "stepAssignedToContact";
  public final static String GROUP_USERS = "stepGroupUsers";
  public final static String STEP_DESCRIPTION = "stepDescription";
  public final static String STEP = "step";
  public final static String URL = "actionPlanURL";
  public final static String linkedOBJECT = "linkedObject";
  public final static String linkedTICKETObjectNAME = "linkedTicketObjectName";
  public final static String linkedACCOUNTObjectNAME = "linkedAccountObjectName";

  /**
   *  Gets the description attribute of the LoadStepDetails object
   *
   * @return    The description value
   */
  public String getDescription() {
    return "Load all Action Step information for use in other steps";
  }


  /**
   *  Description of the Method
   *
   * @param  context  Description of the Parameter
   * @return          Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    ActionItemWork thisWork = (ActionItemWork) context.getThisObject();
    ActionItemWork previousWork = (ActionItemWork) context.getPreviousObject();
    ActionPhaseWork phaseWork = new ActionPhaseWork();
    ActionPlanWork planWork = new ActionPlanWork();
    ActionPlan plan = new ActionPlan();
    Connection db = null;
    try {
      db = this.getConnection(context);
      phaseWork.setBuildPhase(true);
      phaseWork.queryRecord(db, thisWork.getPhaseWorkId());
      planWork.setBuildPhaseWork(true);
      planWork.setBuildLinkedObject(true);
      planWork.queryRecord(db, phaseWork.getPlanWorkId());
      plan.queryRecord(db, planWork.getActionPlanId());
      thisWork.setPlanWork(planWork);
      context.setThisObject(thisWork);
      if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.ACCOUNTS)) {
        ActionPlan url = new ActionPlan();
        url.setName("AccountActionPlans.do?command=Details&orgId="+planWork.getOrganization().getOrgId()+"&actionPlanId="+planWork.getId());
        url.setDescription((String)context.getAttribute("SERVERURL"));
        ActionPlan linkedObject = new ActionPlan();
        linkedObject.setName(context.getParameter(linkedACCOUNTObjectNAME));
        linkedObject.setDescription(planWork.getOrganization().getName());
        context.setAttribute(linkedOBJECT, linkedObject);
        context.setAttribute(URL,url);
      } else if (planWork.getLinkModuleId() == ActionPlan.getMapIdGivenConstantId(db, ActionPlan.TICKETS)) {
        ActionPlan url = new ActionPlan();
        url.setDescription((String)context.getAttribute("SERVERURL"));
        url.setName("TroubleTicketActionPlans.do?command=Details&ticketId="+planWork.getTicket().getId()+"&actionPlanId="+planWork.getId());
        ActionPlan linkedObject = new ActionPlan();
        linkedObject.setName(context.getParameter(linkedTICKETObjectNAME));
        linkedObject.setDescription(planWork.getTicket().getPaddedId());
        context.setAttribute(linkedOBJECT, linkedObject);
        context.setAttribute(URL,url);
      }

      if (planWork.getOrganization() != null) {
        context.setAttribute(ORGANIZATION, planWork.getOrganization());
      } else {
        context.setAttribute(ORGANIZATION, new Organization());
      }
      if (planWork.getTicket() != null) {
        context.setAttribute(TICKET, planWork.getTicket());
      } else {
        context.setAttribute(TICKET, new Ticket());
      }
      if (planWork.getContact() != null) {
        context.setAttribute(CONTACT, planWork.getContact());
      } else {
        context.setAttribute(CONTACT, new Contact());
      }

      if (thisWork.getOwnerId() > 0) {
        User user = new User();
        user.setBuildContact(true);
        user.buildRecord(db, thisWork.getOwnerId());
        context.setAttribute(ASSIGNED_TO_CONTACT, user.getContact());
      }
      if (thisWork.getModifiedBy() > 0) {
        User user = new User(db, thisWork.getModifiedBy());
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(MODIFIED_BY_CONTACT, contact);
      }
      if (thisWork.getEnteredBy() > 0) {
        User user = null;
        if (previousWork != null) {
          user = new User(db, previousWork.getEnteredBy());
        } else {
          user = new User(db, thisWork.getEnteredBy());
        }
        Contact contact = new Contact(db, user.getContactId());
        context.setAttribute(ENTERED_BY_CONTACT, contact);
      }

      thisWork.buildStep(db);
      context.setAttribute(STEP_DESCRIPTION, thisWork.getStep().getDescription());
      context.setAttribute(STEP, thisWork.getStep());
      result = true;
    } catch (Exception e) {

    } finally {
      this.freeConnection(context, db);
    }
    return result;
  }
}

