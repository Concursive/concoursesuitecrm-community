package org.aspcfs.controller;

import com.zeroio.iteam.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.Revenue;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.Viewpoint;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.base.CustomFieldGroup;
import org.aspcfs.modules.base.Import;
import org.aspcfs.modules.communications.base.Message;
import org.aspcfs.modules.communications.base.Recipient;
import org.aspcfs.modules.communications.base.InstantCampaign;
import org.aspcfs.modules.communications.base.SurveyQuestion;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactImport;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.products.base.ProductCatalog;
import org.aspcfs.modules.products.base.ProductCatalogPricing;
import org.aspcfs.modules.products.base.ProductCategory;
import org.aspcfs.modules.products.base.ProductOption;
import org.aspcfs.modules.products.configurator.NumericalConfigurator;
import org.aspcfs.modules.products.configurator.StringConfigurator;
import org.aspcfs.modules.quotes.base.*;
import org.aspcfs.modules.relationships.base.Relationship;
import org.aspcfs.modules.reports.base.Criteria;
import org.aspcfs.modules.reports.base.Parameter;
import org.aspcfs.modules.servicecontracts.base.ServiceContract;
import org.aspcfs.modules.servicecontracts.base.ServiceContractHours;
import org.aspcfs.modules.setup.beans.DatabaseBean;
import org.aspcfs.modules.setup.beans.ServerBean;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.tasks.base.TaskCategory;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 3, 2004
 *@version    $Id: ObjectValidator.java,v 1.1.2.3 2004/09/08 16:37:11 partha Exp
 *      $
 */
public class ObjectValidator {

  private static int REQUIRED_FIELD = 2004090301;
  private static int IS_BEFORE_TODAY = 2004090302;
  private static int PUBLIC_ACCESS_REQUIRED = 2004090303;
  private static int INVALID_DATE = 2004090801;
  private static int INVALID_NUMBER = 2004091001;
  private static int INVALID_EMAIL = 2004091002;
  private static int INVALID_NOT_REQUIRED_DATE = 2004091003;


  /**
   *  Description of the Method
   *
   *@param  systemStatus      Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  object            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean validate(SystemStatus systemStatus, Connection db, Object object) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ObjectValidator-> Checking object: " + object.getClass().getName());
    }
    // TODO: use required fields and warnings from systemStatus; this might
    //       be a list of ValidationTasks that perform complex tasks

    // NOTE: For now, just code the validation tasks in

    // Organization
    if (object.getClass().getName().equals("org.aspcfs.modules.accounts.base.Organization")) {
      Organization organization = (Organization) object;
      if (organization.getPrimaryContact() != null) {
        if (organization.getNameLast() == null || "".equals(organization.getNameLast().trim())) {
          addError(systemStatus, object, "nameLast", REQUIRED_FIELD);
        }
      } else {
        if (organization.getName() == null || "".equals(organization.getName().trim())) {
          addError(systemStatus, object, "name", REQUIRED_FIELD);
        }
      }
      checkWarning(systemStatus, object, "alertDate", IS_BEFORE_TODAY);
    }

    // Contact
    if (object.getClass().getName().equals("org.aspcfs.modules.contacts.base.Contact")) {
      Contact thisContact = (Contact) object;
      // Check for either last name or company
      if (thisContact.getNameLast() == null || thisContact.getNameLast().trim().equals("")) {
        if (thisContact.getOrgId() == -1) {
          if (thisContact.getCompany() == null || thisContact.getCompanyOnly().trim().equals("")) {
            addError(systemStatus, object, "nameLast", REQUIRED_FIELD);
            addError(systemStatus, object, "lastCompany", "object.validation.contact.lastCompanyRequired");
          }
        } else {
          addError(systemStatus, object, "nameLast", REQUIRED_FIELD);
        }
      }
      // Custom check
      if (thisContact.getAccessType() != -1) {
        AccessType thisType = new AccessType(db, thisContact.getAccessType());
        //all account contacts are public
        if (thisContact.getOrgId() > 0 && thisType.getRuleId() != AccessType.PUBLIC) {
          addError(systemStatus, object, "accountAccess", PUBLIC_ACCESS_REQUIRED);
        }
        //personal contacts should be owned by the user who enters it i.e they cannot be reassigned
        if (thisType.getRuleId() == AccessType.PERSONAL && thisContact.getOwner() != thisContact.getEnteredBy()) {
          addError(systemStatus, object, "accessReassign", "object.validation.OwnerMustBeEnteredBy");
        }
      } else {
        addError(systemStatus, object, "access", REQUIRED_FIELD);
      }
    }

    //  Ticket
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.Ticket")) {
      Ticket thisTicket = (Ticket) object;
      if (thisTicket.getProblem() == null || thisTicket.getProblem().trim().equals("")) {
        addError(systemStatus, object, "problem", REQUIRED_FIELD);
      }
      if (thisTicket.getCloseIt() == true && (thisTicket.getSolution() == null || thisTicket.getSolution().trim().equals(""))) {
        addError(systemStatus, object, "solution", "object.validation.ticket.solutionRequired");
      }
      if (thisTicket.getContactId() == -1) {
        addError(systemStatus, object, "contactId", "object.validation.ticket.contactRequired");
      }
      if (thisTicket.getOrgId() == -1) {
        addError(systemStatus, object, "orgId", "object.validation.ticket.organizationRequired");
      }
      // If ticket is being closed, check if resolution date is before assignmented date
      if (thisTicket.getEstimatedResolutionDate() != null && thisTicket.getAssignedDate() != null) {
        if (thisTicket.getEstimatedResolutionDate().before(thisTicket.getAssignedDate())) {
          addError(systemStatus, object, "estimatedResolutionDate", "object.validation.ticket.estResolutionBeforeAssignment");
        }
      }
    }

    //  TicketLog
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketLog")) {
      TicketLog thisTicketLog = (TicketLog) object;
      if (thisTicketLog.getTicketId() == -1 || (thisTicketLog.getEntryText() == null || thisTicketLog.getEntryText().trim().equals("")) || thisTicketLog.getEnteredBy() == -1 || thisTicketLog.getModifiedBy() == -1) {
        return false;
      }
    }

    //  TicketActivityLog
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketActivityLog")) {
      TicketActivityLog thisLog = (TicketActivityLog) object;
      if (thisLog.getFollowUpRequired() || (thisLog.getAlertDate() != null && !"".equals(thisLog.getAlertDate())) ||
          (thisLog.getFollowUpDescription() != null && !"".equals(thisLog.getFollowUpDescription()))) {
        if (!thisLog.getFollowUpRequired()) {
          addError(systemStatus, object, "followUpRequired", REQUIRED_FIELD);
        }
        checkError(systemStatus, object, "alertDate", INVALID_DATE);
        checkError(systemStatus, object, "followUpDescription", REQUIRED_FIELD);
      } else if (!thisLog.getFollowUpRequired() && (thisLog.getAlertDate() == null || "".equals(thisLog.getAlertDate())) &&
          (thisLog.getFollowUpDescription() == null || "".equals(thisLog.getFollowUpDescription()))) {
        if (thisLog.getTicketPerDayDescriptionList() == null || thisLog.getTicketPerDayDescriptionList().size() == 0) {
          addError(systemStatus, object, "action", "object.validation.actionError.blankFormCanNotBeSaved");
        }
      }
      checkWarning(systemStatus, object, "alertDate", IS_BEFORE_TODAY);
    }

    //  OpportunityHeader
    if (object.getClass().getName().equals("org.aspcfs.modules.pipeline.base.OpportunityHeader")) {
      OpportunityHeader oppHeader = (OpportunityHeader) object;

      if (oppHeader.getDescription() == null || "".equals(oppHeader.getDescription().trim())) {
        addError(systemStatus, object, "description", REQUIRED_FIELD);
      }
      if (oppHeader.getContactLink() == -1 && oppHeader.getAccountLink() == -1) {
        addError(systemStatus, object, "acctContact", "object.validation.opportunity.accountContactRequired");
      }
    }

    //  OpportunityComponent
    if (object.getClass().getName().equals("org.aspcfs.modules.pipeline.base.OpportunityComponent")) {
      OpportunityComponent oppComponent = (OpportunityComponent) object;

      User owner = null;
      if (oppComponent.getOwner() != -1 && systemStatus != null) {
        owner = systemStatus.getUser(oppComponent.getOwner());
      }
      if (owner != null && owner.getExpires() != null && owner.getExpires().before(new Timestamp(Calendar.getInstance().getTimeInMillis()))) {
        addWarning(systemStatus, object, "owner", "object.validation.expiredUser");
      }
      if ( owner != null && !owner.getEnabled()) {
        addWarning(systemStatus, object, "owner", "object.validation.disabledUser");
      }
      if (oppComponent.getDescription() == null || "".equals(oppComponent.getDescription().trim())) {
        addError(systemStatus, object, "componentDescription", REQUIRED_FIELD);
      }
      if (oppComponent.getCloseProb() == 0) {
        addError(systemStatus, object, "closeProb", REQUIRED_FIELD);
      } else {
        if (oppComponent.getCloseProb() > 1.0) {
          addError(systemStatus, object, "closeProb", "object.validation.opportunityComponent.closeProbNotGTOneHundred");
        } else if (oppComponent.getCloseProb() < 0) {
          addError(systemStatus, object, "closeProb", "object.validation.opportunityComponent.closeProbNotLTZero");
        }
      }
      if (oppComponent.getLow() > oppComponent.getHigh()) {
        addError(systemStatus, object, "low", "object.validation.opportunityComponent.lowNotGTHigh");
      }
      if (oppComponent.getCloseDate() == null || oppComponent.getCloseDateString().trim().equals("")) {
        addError(systemStatus, object, "closeDate", REQUIRED_FIELD);
      }
      if (oppComponent.getGuess() == 0) {
        addError(systemStatus, object, "guess", REQUIRED_FIELD);
      }
      if (oppComponent.getTerms() == 0) {
        addError(systemStatus, object, "terms", REQUIRED_FIELD);
      } else {
        if (oppComponent.getTerms() < 0) {
          addError(systemStatus, object, "terms", "object.validation.opportunityComponent.termsNotLTZero");
        }
      }
      if (oppComponent.getUnits() == null) {
        addError(systemStatus, object, "terms", "object.validation.opportunityComponent.unitsNotNull");
      }
      if (oppComponent.getType() == null) {
        addError(systemStatus, object, "terms", REQUIRED_FIELD);
      }
      //Check warning for alert date
      checkWarning(systemStatus, object, "alertDate", IS_BEFORE_TODAY);
    }

    //  OpportunityReport
    if (object.getClass().getName().equals("org.aspcfs.modules.pipeline.base.OpportunityReport")) {
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
    }

    //  Calls
    if (object.getClass().getName().equals("org.aspcfs.modules.contacts.base.Call")) {
      Call call = (Call) object;
      User callOwner = null;
      if (call.getOwner() != -1 && systemStatus != null) {
        callOwner = systemStatus.getUser(call.getOwner());
      }
      if (callOwner != null && !callOwner.getEnabled()) {
        addWarning(systemStatus, object, "owner", "object.validation.disabledUser");
      }
      if (callOwner != null && callOwner.getExpires() != null && callOwner.getExpires().after(new Timestamp(Calendar.getInstance().getTimeInMillis()))) {
        addWarning(systemStatus, object, "owner", "object.validation.expiredUser");
      }

      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      if (call.getContactId() == -1 && call.getOrgId() == -1 && call.getOppHeaderId() == -1) {
        addError(systemStatus, object, "link", "object.validation.call.notAssociated");
      }
      if (call.getLength() < 0) {
        addError(systemStatus, object, "length", "object.validation.call.lengthNotLTZero");
      }
      if (call.getResultId() == -1) {
        addError(systemStatus, object, "result", REQUIRED_FIELD);
      }
      if (call.getCallTypeId() < 1) {
        addError(systemStatus, object, "type", REQUIRED_FIELD);
      }
      if (call.getAlertDate() != null || call.getHasFollowup()) {
        if ("".equals(StringUtils.toString(call.getAlertText().trim()))) {
          addError(systemStatus, object, "description", REQUIRED_FIELD);
        }
        if (call.getAlertCallTypeId() < 1) {
          addError(systemStatus, object, "followupType", REQUIRED_FIELD);
        }
        if (call.getPriorityId() == -1) {
          addError(systemStatus, object, "priority", REQUIRED_FIELD);
        }
        if (call.getAlertDate() == null) {
          addError(systemStatus, object, "alertDate", REQUIRED_FIELD);
        }
      } else {
        //reset priority Id as it does not have a "none" option
        call.setPriorityId(-1);
      }
      checkWarning(systemStatus, object, "alertDate", IS_BEFORE_TODAY);
    }

    //  FileItem
    if (object.getClass().getName().equals("com.zeroio.iteam.base.FileItem")) {
      FileItem fileItem = (FileItem) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "filename", REQUIRED_FIELD);
      if (fileItem.getLinkModuleId() == -1 || fileItem.getLinkItemId() == -1) {
        addError(systemStatus, object, "action", "object.validation.noId");
      }
    }

    //  FileFolder
    if (object.getClass().getName().equals("com.zeroio.iteam.base.FileFolder")) {
      FileFolder fileFolder = (FileFolder) object;
      if (fileFolder.getLinkModuleId() == -1 || fileFolder.getLinkItemId() == -1) {
        addError(systemStatus, object, "action", "object.validation.noId");
      }
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
    }

    //  ServiceContract
    if (object.getClass().getName().equals("org.aspcfs.modules.servicecontracts.base.ServiceContract")) {
      ServiceContract thisContract = (ServiceContract) object;
      checkError(systemStatus, object, "serviceContractNumber", REQUIRED_FIELD);
      boolean initialStartDateExists = true;
      boolean currentStartDateExists = true;
      boolean currentEndDateExists = true;
      if (thisContract.getInitialStartDate() == null) {
        addError(systemStatus, object, "initialStartDate", REQUIRED_FIELD);
        initialStartDateExists = false;
      }
      if (thisContract.getCurrentStartDate() == null) {
        currentStartDateExists = false;
      }
      if (thisContract.getCurrentEndDate() == null) {
        currentEndDateExists = false;
      }
      if (currentEndDateExists) {
        if ((currentStartDateExists) &&
            (thisContract.getCurrentEndDate().before(thisContract.getCurrentStartDate()))) {
          addError(systemStatus, object, "currentEndDate", "object.validation.serviceContract.currentEndDateNotGTCurrentContractDate");
        }
        if ((initialStartDateExists) &&
            (!currentStartDateExists) &&
            (thisContract.getCurrentEndDate().before(thisContract.getInitialStartDate()))) {
          addError(systemStatus, object, "currentEndDate", "object.validation.serviceContract.currentEndDateNotLTInitialContractDate");
        }
      }
    }

    //  ServiceContractHours
    if (object.getClass().getName().equals("org.aspcfs.modules.servicecontracts.base.ServiceContractHours")) {
      ServiceContractHours thisContractHours = (ServiceContractHours) object;
      if (thisContractHours.getAdjustmentReason() == -1) {
        addError(systemStatus, object, "adjustmentReason", REQUIRED_FIELD);
      }
    }
    // Asset
    if (object.getClass().getName().equals("org.aspcfs.modules.assets.base.Asset")) {
      Asset asset = (Asset) object;
      checkError(systemStatus, object, "serialNumber", REQUIRED_FIELD);
      if (asset.getDateListed() == null) {
        addError(systemStatus, object, "dateListed", REQUIRED_FIELD);
      }
    }

    //  TicketMaintenanceNotes
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketMaintenanceNote")) {
      //TicketMaintenanceNote maintenanceNote = (TicketMaintenanceNote) object;
      checkError(systemStatus, object, "descriptionOfService", REQUIRED_FIELD);
    }

    //  TicketReplacementPart
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketReplacementPart")) {
      TicketReplacementPart replacement = (TicketReplacementPart) object;
      if ((replacement.getPartNumber() != null && !"".equals(replacement.getPartNumber()))) {
        checkError(systemStatus, object, "partDescription", REQUIRED_FIELD);
      }
      if (replacement.getPartDescription() != null && !"".equals(replacement.getPartDescription())) {
        checkError(systemStatus, object, "partNumber", "partNumber", REQUIRED_FIELD);
      }
    }

    //  Campaign
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.Campaign")) {
      //Campaign campaign = (Campaign) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  InstantCampaign
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.InstantCampaign")) {
//      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      InstantCampaign campaign = (InstantCampaign) object;
      if (campaign.getInstantMessage() != null) {
        if (campaign.getInstantMessage().getMessageSubject() == null || campaign.getInstantMessage().getMessageSubject().trim().equals("")) {
         addError(systemStatus, object, "messageSubject", REQUIRED_FIELD);
        }
  
        if (campaign.getInstantMessage().getReplyTo() == null || campaign.getInstantMessage().getReplyTo().trim().equals("") ||
            campaign.getInstantMessage().getReplyTo().indexOf("@") == -1 || campaign.getInstantMessage().getReplyTo().indexOf("@") == campaign.getInstantMessage().getReplyTo().length() - 1) {
            addError(systemStatus, object, "replyTo", REQUIRED_FIELD);
        }
      }
    }

    //  SearchCriteriaList
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.SearchCriteriaList")) {
      //checkError(systemStatus, object, "groupName", REQUIRED_FIELD);
    }

    
    //  SearchFormBean
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.beans.SearchFormBean")) {
      checkError(systemStatus, object, "groupName", REQUIRED_FIELD);
      checkError(systemStatus, object, "searchCriteriaText", REQUIRED_FIELD);
    }
    
    //  Message
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.Message")) {
      Message message = (Message) object;
      if ((message.getName() == null || message.getName().trim().equals("")) && !message.getDisableNameValidation()) {
        addError(systemStatus, object, "name", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "messageSubject", REQUIRED_FIELD);
      if (message.getReplyTo() == null || message.getReplyTo().trim().equals("") ||
          message.getReplyTo().indexOf("@") == -1 || message.getReplyTo().indexOf("@") == message.getReplyTo().length() - 1) {
        addError(systemStatus, object, "replyTo", "object.validation.communications.fullEmailAddress");
      }
    }

    //  Survey
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.Survey")) {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  SurveyQuestion
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.SurveyQuestion")) {
      SurveyQuestion question = (SurveyQuestion) object;
      if (question.getType() == -1) {
        addError(systemStatus, object, "action", "object.validation.formIncomplete");
        addError(systemStatus, object, "type", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "questionText", REQUIRED_FIELD);
    }

    //  Project
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Project")) {
      Project project = (Project) object;
      checkError(systemStatus, object, "title", REQUIRED_FIELD);
      checkError(systemStatus, object, "shortDescription", REQUIRED_FIELD);
      if (project.getRequestDate() == null) {
        addError(systemStatus, object, "requestDate", REQUIRED_FIELD);
      }
      if ((project.getEstimatedCloseDate() != null) && (project.getRequestDate() != null)) {
        if (project.getEstimatedCloseDate().before(project.getRequestDate())) {
          addWarning(systemStatus, object, "estimatedCloseDate", "object.validation.project.estimatedCloseDateNotLTRequestDate");
        }
      }
    }

    //  NewsArticle
    if (object.getClass().getName().equals("com.zeroio.iteam.base.NewsArticle")) {
      com.zeroio.iteam.base.NewsArticle newsArticle = (com.zeroio.iteam.base.NewsArticle) object;
      if (newsArticle.getProjectId() == -1) {
        addError(systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }

      if (newsArticle.getIntro() != null) {
        checkError(systemStatus, object, "subject", REQUIRED_FIELD);
        if (newsArticle.getIntro() == null || newsArticle.getIntro().equals("") || newsArticle.getIntro().equals(" \r\n<br />\r\n ")) {
          addError(systemStatus, object, "intro", REQUIRED_FIELD);
        }
        if ((newsArticle.getEndDate() != null) && (newsArticle.getStartDate() != null)) {
          if (newsArticle.getEndDate().before(newsArticle.getStartDate())) {
            // TODO: Should be archive date is earlier than start date
            addWarning(systemStatus, object, "endDate", "object.validation.project.endDateNotLTStartDate");
          }
        }
      } else {
        if (newsArticle.getMessage() == null || newsArticle.getMessage().equals("") || newsArticle.getMessage().equals(" \r\n<br />\r\n ")) {
          addError(systemStatus, object, "message", REQUIRED_FIELD);
        }
      }
    }

    //  IssueCategory
    if (object.getClass().getName().equals("com.zeroio.iteam.base.IssueCategory")) {
      IssueCategory category = (IssueCategory) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      if (category.getProjectId() == -1) {
        addError(systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }
    }

    //  Task
    if (object.getClass().getName().equals("org.aspcfs.modules.tasks.base.Task")) {
      Task thisTask = (Task) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (thisTask.getCategoryId() == -1 && thisTask.getOwner() == -1) {
        addError(systemStatus, object, "owner", REQUIRED_FIELD);
      }
      //If task is marked personal and the owner is different from the user entering the task, throw an error
      if (thisTask.getSharing() == 1 && thisTask.getOwner() != thisTask.getModifiedBy()) {
        addError(systemStatus,  object, "sharing", "object.validation.task.ownerPersonal");
      }
      checkWarning(systemStatus, object, "dueDate", IS_BEFORE_TODAY);
    }

    //  TicketTask
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketTask")) {
      TicketTask thisTask = (TicketTask) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (thisTask.getCategoryId() == -1 && thisTask.getOwner() == -1) {
        addError(systemStatus, object, "owner", REQUIRED_FIELD);
      }
      checkWarning(systemStatus, object, "dueDate", IS_BEFORE_TODAY);
    }

    //  TaskCategory
    if (object.getClass().getName().equals("org.aspcfs.modules.tasks.base.TaskCategory")) {
      TaskCategory thisCategory = (TaskCategory) object;
      if ((thisCategory.getLinkModuleId() == -1) || (thisCategory.getLinkItemId() == -1)) {
        addError(systemStatus, object, "linkModuleId", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
    }

    //  Requirement
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Requirement")) {
      Requirement requirement = (Requirement) object;
      if (requirement.getProjectId() == -1) {
        addError(systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }
      checkError(systemStatus, object, "shortDescription", REQUIRED_FIELD);
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (requirement.getDeadline() != null && requirement.getStartDate() != null) {
        if (requirement.getDeadline().before(requirement.getStartDate())) {
          addWarning(systemStatus, object, "deadline", "object.validation.project.requirements.deadlineNotLTStartDate");
        }
      }
    }

    // Assignment
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Assignment")) {
      Assignment assignment = (Assignment) object;
      if (assignment.getProjectId() == -1) {
        addError(systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }
      checkError(systemStatus, object, "role", REQUIRED_FIELD);
      if (assignment.getStatusId() < 1) {
        addError(systemStatus, object, "statusId", REQUIRED_FIELD);
      }
      if (assignment.getRequirementId() == -1) {
        addError(systemStatus, object, "requirementId", REQUIRED_FIELD);
      }
    }

    //  AssignmentFolder
    if (object.getClass().getName().equals("com.zeroio.iteam.base.AssignmentFolder")) {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  CFSNote
    if (object.getClass().getName().equals("org.aspcfs.modules.mycfs.base.CFSNote")) {
      checkError(systemStatus, object, "body", REQUIRED_FIELD);
      checkError(systemStatus, object, "subject", "messageSubject", REQUIRED_FIELD);
    }

    //  Revenue
    if (object.getClass().getName().equals("org.aspcfs.modules.accounts.base.Revenue")) {
      Revenue revenue = (Revenue) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (revenue.getAmount() == 0) {
        addError(systemStatus, object, "amount", REQUIRED_FIELD);
      }
    }

    //  CustomFieldCategory
    if (object.getClass().getName().equals("org.aspcfs.modules.base.CustomFieldCategory")) {
      CustomFieldCategory thisCategory = (CustomFieldCategory) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisCategory.getModuleId() == -1) {
        addError(systemStatus, object, "action", "object.validation.formDataMissing");
      }
      //If this folder is being updated such that it can have only one record per link_item, then
      //make sure it previously does not have multiple records for any of the link_items
      if (thisCategory.getId() != -1 && !thisCategory.getAllowMultipleRecords()) {
        if (thisCategory.hasMultipleRecords(db)) {
          addError(systemStatus, object, "allowMultipleRecords", "object.validation.customFieldCategory.hasMultipleRecords");
        }
      }
    }

    //  CustomFieldGroup
    if (object.getClass().getName().equals("org.aspcfs.modules.base.CustomFieldGroup")) {
      CustomFieldGroup group = (CustomFieldGroup) object;
      if (group.getCategoryId() == -1) {
        addError(systemStatus, object, "action", "object.validation.formDataMissing");
      }
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  CustomField
    if (object.getClass().getName().equals("org.aspcfs.modules.base.CustomField")) {
      CustomField thisField = (CustomField) object;

      if (thisField.getGroupId() == -1) {
        addError(systemStatus, object, "recordId", "object.validation.customField.groupIdNotPresent");
      }
      if (thisField.getType() == -1) {
        addError(systemStatus, object, "type", "object.validation.customField.typeNotPresent");
      }
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisField.getLengthRequired()) {
        if (thisField.getParameter("maxlength").equals("")) {
          addError(systemStatus, object, "maxLength", "object.validation.customField.maxLength");
        } else {
          try {
            if (Integer.parseInt(thisField.getParameter("maxlength")) > 255) {
              addError(systemStatus, object, "maxLength", "object.validation.customField.maxLengthTooHigh");
            }
          } catch (Exception e) {
            addError(systemStatus, object, "maxLength", "object.validation.customField.maxLengthNumber");
          }
        }
      }
      /*
       *  /Removed because this doesn't have to happen here
       *  if (type == SELECT &&
       *  (elementData == null || (((LookupList) elementData).size() == 0))
       *  ) {
       *  errors.put("lookupListError", "Items are required");
       *  }
       */
      if (thisField.getValidateData()) {
        if (thisField.getRecordId() == -1) {
          addError(systemStatus, object, "recordId", "object.validation.customField.recordIdNotPresent");
          thisField.setError(systemStatus.getLabel("object.validation.customField.recordIdNotPresent"));
        }
        if (thisField.getType() == -1) {
          addError(systemStatus, object, "type", "object.validation.customField.typeNotPresent");
          thisField.setError(systemStatus.getLabel("object.validation.customField.typeNotPresent"));
        }

        //Required Fields
        if (thisField.getRequired() && (thisField.getEnteredValue() == null || thisField.getEnteredValue().equals(""))) {
          addError(systemStatus, object, "enteredValue", REQUIRED_FIELD);
          thisField.setError(systemStatus.getLabel("object.validation.required"));
        }
        if (thisField.getType() == CustomField.SELECT && thisField.getRequired() && thisField.getSelectedItemId() == -1) {
          addError(systemStatus, object, "selectedItemId", REQUIRED_FIELD);
          thisField.setError(systemStatus.getLabel("object.validation.required"));
        }

        //Type mis-match
        if (thisField.getEnteredValue() != null && !thisField.getEnteredValue().equals("")) {
          if (thisField.getType() == CustomField.INTEGER) {
            try {
              int testNumber = Integer.parseInt(thisField.getEnteredValue());
              thisField.setEnteredNumber(testNumber);
              thisField.setEnteredDouble(Double.parseDouble("" + testNumber));
            } catch (Exception e) {
              addError(systemStatus, object, "enteredValue", "object.validation.incorrectWholeNumberFormat");
              thisField.setError(systemStatus.getLabel("object.validation.incorrectWholeNumberFormat"));
            }
          }

          if (thisField.getType() == CustomField.FLOAT) {
            try {
              double testNumber = Double.parseDouble(thisField.getEnteredValue());
              thisField.setEnteredDouble(testNumber);
            } catch (Exception e) {
              addError(systemStatus, object, "enteredValue", INVALID_NUMBER);
              thisField.setError(systemStatus.getLabel("object.validation.incorrectNumberFormat"));
            }
          }

          if (thisField.getType() == CustomField.PERCENT) {
            try {
              double testNumber = Double.parseDouble(thisField.getEnteredValue());
              thisField.setEnteredDouble(testNumber);
            } catch (Exception e) {
              addError(systemStatus, object, "enteredValue", INVALID_NUMBER);
              thisField.setError(systemStatus.getLabel("object.validation.incorrectNumberFormat"));
            }
          }

          if (thisField.getType() == CustomField.CURRENCY) {
            try {
              Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
              NumberFormat nf = NumberFormat.getInstance(locale);
              thisField.setEnteredDouble(nf.parse(thisField.getEnteredValue()).doubleValue());
              Double tmpDouble = new Double(thisField.getEnteredDouble());
              thisField.setEnteredValue(tmpDouble.toString());
            } catch (Exception e) {
              addError(systemStatus, object, "enteredValue", INVALID_NUMBER);
              thisField.setError(systemStatus.getLabel("object.validation.incorrectNumberFormat"));
            }
          }

          if (thisField.getType() == CustomField.DATE) {
            try {
              Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
              DateFormat localeFormatter = DateFormat.getDateInstance(DateFormat.SHORT, locale);
              localeFormatter.setLenient(false);
              localeFormatter.parse(thisField.getEnteredValue());
            } catch (java.text.ParseException e) {
              addError(systemStatus, object, "enteredValue", INVALID_DATE);
              thisField.setError(systemStatus.getLabel("object.validation.incorrectDateFormat"));
            }
          }

          if (thisField.getType() == CustomField.EMAIL) {
            if ((thisField.getEnteredValue().indexOf("@") < 1) ||
                (thisField.getEnteredValue().indexOf(" ") > -1) ||
                (thisField.getEnteredValue().indexOf(".") < 0)) {
              addError(systemStatus, object, "enteredValue", "object.validation.communications.fullEmailAddress");
              thisField.setError(systemStatus.getLabel("object.validation.communications.fullEmailAddress"));
            }
          }

          if (thisField.getType() == CustomField.URL) {
            if (thisField.getEnteredValue().indexOf(".") < 0) {
              addError(systemStatus, object, "enteredValue", "object.validation.customField.incorrectUrlFormat");
              thisField.setError(systemStatus.getLabel("object.validation.customField.incorrectUrlFormat"));
            }
          }
        }
      }
    }

    //  Role
    if (object.getClass().getName().equals("org.aspcfs.modules.admin.base.Role")) {
      Role role = (Role) object;
      checkError(systemStatus, object, "role", REQUIRED_FIELD);
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (role.isDuplicate(db)) {
        addError(systemStatus, object, "role", "object.validation.roleNameAlreadyInUse");
      }
    }

    //  Viewpoint
    if (object.getClass().getName().equals("org.aspcfs.modules.admin.base.Viewpoint")) {
      Viewpoint view = (Viewpoint) object;
      if (view.getVpUserId() == -1) {
        addError(systemStatus, object, "Contact", REQUIRED_FIELD);
      }
      if (view.getVpUserId() == view.getUserId()) {
        addError(systemStatus, object, "Contact", "object.validation.ownViewpointNotAllowed");
      }
    }

    //  RegistrationBean
    if (object.getClass().getName().equals("org.aspcfs.modules.setup.base.RegistrationBean")) {
      //RegistrationBean thisBean = (RegistrationBean) object;
      checkError(systemStatus, object, "profile", REQUIRED_FIELD);
      checkError(systemStatus, object, "nameFirst", REQUIRED_FIELD);
      checkError(systemStatus, object, "nameLast", REQUIRED_FIELD);
      checkError(systemStatus, object, "company", REQUIRED_FIELD);
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
    }

    //  Invitation
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Invitation")) {
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
      checkError(systemStatus, object, "firstName", REQUIRED_FIELD);
      checkError(systemStatus, object, "lastName", REQUIRED_FIELD);
      return false;
    }

    //  FileItemVersion
    if (object.getClass().getName().equals("com.zeroio.iteam.base.FileItemVersion")) {
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "filename", REQUIRED_FIELD);
    }

    //  Issue
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Issue")) {
      Issue issue = (Issue) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "body", REQUIRED_FIELD);
      if (issue.getProjectId() == -1) {
        addError(systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }
      if (issue.getCategoryId() == -1) {
        addError(systemStatus, object, "categoryId", REQUIRED_FIELD);
      }
    }

    //  IssueReply
    if (object.getClass().getName().equals("com.zeroio.iteam.base.IssueReply")) {
      IssueReply issueReply = (IssueReply) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "body", REQUIRED_FIELD);
      if (issueReply.getIssueId() == -1) {
        addError(systemStatus, object, "action", "object.validation.issueIdNotSpecified");
      }
    }

    //  ActionList
    if (object.getClass().getName().equals("org.aspcfs.modules.actionlist.base.ActionList")) {
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
    }

    //  Import
    if (object.getClass().getName().equals("org.aspcfs.modules.base.Import")) {
      Import thisImport = (Import) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisImport.getType() < 0) {
        addError(systemStatus, object, "type", REQUIRED_FIELD);
      }
    }

    //  ContactImport
    if (object.getClass().getName().equals("org.aspcfs.modules.contacts.base.ContactImport")) {
      ContactImport thisImport = (ContactImport) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisImport.getType() < 0) {
        addError(systemStatus, object, "type", REQUIRED_FIELD);
      }
    }

    //  Recipient
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.Recipient")) {
      Recipient recipient = (Recipient) object;
      if (recipient.getCampaignId() == -1) {
        addError(systemStatus, object, "campaign", REQUIRED_FIELD);
      }
      if (recipient.getContactId() == -1) {
        addError(systemStatus, object, "contact", REQUIRED_FIELD);
      }
    }

    //  Criteria
    if (object.getClass().getName().equals("org.aspcfs.modules.reports.base.Criteria")) {
      Criteria criteria = (Criteria) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      if (criteria.getReportId() == -1) {
        addError(systemStatus, object, "reportId", REQUIRED_FIELD);
      }
      if (criteria.getOwner() == -1) {
        addError(systemStatus, object, "owner", REQUIRED_FIELD);
      }
      if (criteria.getEnteredBy() == -1) {
        addError(systemStatus, object, "enteredBy", REQUIRED_FIELD);
      }
      if (criteria.getModifiedBy() == -1) {
        addError(systemStatus, object, "modifiedBy", REQUIRED_FIELD);
      }
    }

    //  Parameter
    if (object.getClass().getName().equals("org.aspcfs.modules.reports.base.Parameter")) {
      Parameter parameter = (Parameter) object;
      if (parameter.getCriteriaId() == -1) {
        addError(systemStatus, object, "criteriaId", REQUIRED_FIELD);
      }
    }

    //  DatabaseBean
    if (object.getClass().getName().equals("org.aspcfs.modules.setup.beans.DatabaseBean")) {
      DatabaseBean bean = (DatabaseBean) object;
      checkError(systemStatus, object, "ip", REQUIRED_FIELD);
      if (bean.getPort() <= 0) {
        addError(systemStatus, object, "port", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      checkError(systemStatus, object, "user", REQUIRED_FIELD);
    }

    //  ServerBean
    if (object.getClass().getName().equals("org.aspcfs.modules.setup.beans.ServerBean")) {
      ServerBean bean = (ServerBean) object;
      checkError(systemStatus, object, "url", REQUIRED_FIELD);
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
      checkError(systemStatus, object, "emailAddress", REQUIRED_FIELD);
      if (bean.getTimeZone() == null || "".equals(bean.getTimeZone().trim()) || "-1".equals(bean.getTimeZone())) {
        addError(systemStatus, object, "timeZone", REQUIRED_FIELD);
      }
    }

    //  UserSetupBean
    if (object.getClass().getName().equals("org.aspcfs.modules.setup.beans.UserSetupBean")) {
      //UserSetupBean bean = (UserSetupBean) object;
      checkError(systemStatus, object, "nameFirst", REQUIRED_FIELD);
      checkError(systemStatus, object, "nameLast", REQUIRED_FIELD);
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
      checkError(systemStatus, object, "username", REQUIRED_FIELD);
      checkError(systemStatus, object, "password1", REQUIRED_FIELD);
      checkError(systemStatus, object, "password2", REQUIRED_FIELD);
    }

    // ProductCatalog
    if (object.getClass().getName().equals("org.aspcfs.modules.products.base.ProductCatalog")) {
      ProductCatalog productCatalog = (ProductCatalog) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (productCatalog.getStartDate() != null && productCatalog.getExpirationDate() != null) {
        if (productCatalog.getStartDate().after(productCatalog.getExpirationDate())) {
          addError(systemStatus, object, "startDate", "object.validation.startAfterExpiration");
        }
      }
      if (productCatalog.getStartDate() == null && productCatalog.getExpirationDate() != null) {
        addError(systemStatus, object, "startDate", "object.validation.startAfterExpiration");
      }
      if (productCatalog.getEnabled()) {
        if (productCatalog.getActivePrice() == null) {
          addWarning(systemStatus, object, "enabled", "object.validation.product.activePriceNotFound");
        }
      } 
    }
    
    // ProductOption
    if (object.getClass().getName().equals("org.aspcfs.modules.products.base.ProductOption")) {
      ProductOption option = (ProductOption) object;
      if (option.getStartDate() != null && option.getEndDate() != null) {
        if (option.getStartDate().after(option.getEndDate())) {
          addError(systemStatus, object, "startDate", "object.validation.productPricing.startDateNotGTExpirationDate");
        }
      }
      if (option.getStartDate() == null && option.getEndDate() != null) {
        addError(systemStatus, object, "startDate", "object.validation.productPricing.startDateNotGTExpirationDate");
      }
    }

    // ProductCategory
    if (object.getClass().getName().equals("org.aspcfs.modules.products.base.ProductCategory")) {
      ProductCategory productCategory = (ProductCategory) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (productCategory.getStartDate() != null && productCategory.getExpirationDate() != null) {
        if (productCategory.getStartDate().after(productCategory.getExpirationDate())) {
          addError(systemStatus, object, "startDate", "object.validation.startAfterExpiration");
        }
      }
    }

    //Document Store
    if (object.getClass().getName().equals("org.aspcfs.modules.documents.base.DocumentStore")) {
      checkError(systemStatus, object, "title", REQUIRED_FIELD);
      checkError(systemStatus, object, "shortDescription", REQUIRED_FIELD);
      checkError(systemStatus, object, "requestDate", REQUIRED_FIELD);
    }
    // Quote
    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.Quote")) {
      Quote quote = (Quote) object;
      if (quote.getOrgId() == -1) {
        addError(systemStatus, object, "orgId", REQUIRED_FIELD);
      }
      if (quote.getContactId() == -1) {
        addError(systemStatus, object, "contactId", REQUIRED_FIELD);
      }
      if (quote.getCloseIt() && quote.getStatusId() == -1) {
        addError(systemStatus, object, "statusId", "object.validation.quoteClosedWithoutExplanation");
      }
      checkError(systemStatus, object, "shortDescription", REQUIRED_FIELD);
    }

    // Quote Product
    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.QuoteProduct")) {
      QuoteProduct product = (QuoteProduct) object;
      if (product.getProductId() == -1) {
        addError(systemStatus, object, "productId", "object.validation.emptyQuoteProduct");
      }
      if (product.getQuoteId() == -1) {
        addError(systemStatus, object, "quoteId", "object.validation.quoteProductWithoutQuote");
      }
      if (product.getQuantity() < 0) {
        addError(systemStatus, object, "quantity", "object.validation.negativeQuoteProductQuantity");
      }
      /*
       *  if (Double.compare(product.getPriceAmount(),0.0) != 0 && product.getPriceCurrency() == -1) {
       *  addError(systemStatus, object, "priceAmountError", "Invalid price currency");
       *  }
       *  if (Double.compare(product.getRecurringAmount(),0.0) !=0 && (product.getRecurringCurrency() == -1 || product.getRecurringType() == -1)) {
       *  addError(systemStatus, object, "recurringAmountError", "Invalid recurring currency or type");
       *  }
       */
    }
    
    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.QuoteProductBean")) {
      //QuoteProductBean product = (QuoteProductBean) object;
    }

    // Quote Product Option
    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.QuoteProductOption")) {
    }
    
    // Quote Condition
    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.QuoteCondition")) {
      QuoteCondition condition = (QuoteCondition) object;
      if (condition.getConditionName() == null || "".equals(condition.getConditionName().trim())) {
        addError(systemStatus, object, "description", REQUIRED_FIELD);
      }
      if (condition.getConditionName().length() > 300) {
        addError(systemStatus, object, "description", "object.validation.descriptionNotGT300Characters");
      }
    }

    // Quote Remark
    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.QuoteRemark")) {
      QuoteRemark remark = (QuoteRemark) object;
      if (remark.getRemarkName() == null || "".equals(remark.getRemarkName().trim())) {
        addError(systemStatus, object, "description", REQUIRED_FIELD);
      }
      if (remark.getRemarkName().length() > 300) {
        addError(systemStatus, object, "description", "object.validation.descriptionNotGT300Characters");
      }
    }
    
    // Quote Note
    if (object.getClass().getName().equals("org.aspcfs.modules.quotes.base.QuoteNote")) {
      QuoteNote note = (QuoteNote) object;
      if (note.getQuoteId() == -1) {
        addError(systemStatus, object, "quoteId", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "notes", REQUIRED_FIELD);
    }
    
    // Product Catalog Pricing
    if (object.getClass().getName().equals("org.aspcfs.modules.products.base.ProductCatalogPricing")) {
      ProductCatalogPricing pricing = (ProductCatalogPricing) object;
      if (pricing.getStartDate() != null && pricing.getExpirationDate() != null) {
        if (pricing.getStartDate().after(pricing.getExpirationDate())) {
          addError(systemStatus, object, "startDate", "object.validation.productPricing.startDateNotGTExpirationDate");
        }
      }
    }

    //Numerical Configurator
    if (object.getClass().getName().equals("org.aspcfs.modules.products.configurator.NumericalConfigurator")) {
      NumericalConfigurator configurator = (NumericalConfigurator) object;
      boolean valid = true;
      if (configurator.getMinNum() == -1 || configurator.getMaxNum() == -1) {
        return true;
      }
      if (configurator.getMinNum() > configurator.getMaxNum()) {
        configurator.getPropertyList().getOptionProperty("number_max").setErrorMsg(systemStatus.getLabel("object.validation.maxNumberNotLTMinNumber"));//"max number cannot be lesser than min number");
        valid = false;
      }
      if (configurator.getDefaultNum() < configurator.getMinNum()) {
        if (configurator.getPropertyList().getOptionProperty("number_default") != null) {
          configurator.getPropertyList().getOptionProperty("number_default").setErrorMsg(systemStatus.getLabel("object.validation.defaultNumberNotLTMinNumber"));//"default number value cannot be lesser than min number");
          valid = false;
        }
      }
      if (configurator.getDefaultNum() > configurator.getMaxNum()) {
        if (configurator.getPropertyList().getOptionProperty("number_default") != null) {
          configurator.getPropertyList().getOptionProperty("number_default").setErrorMsg(systemStatus.getLabel("object.validation.defaultNumberNotGTMaxNumber"));//"default number value cannot be greater than max number");
          valid = false;
        }
      }
      return valid;
    }

    //String Configurator
    if (object.getClass().getName().equals("org.aspcfs.modules.products.configurator.StringConfigurator")) {
      StringConfigurator configurator = (StringConfigurator) object;
      boolean valid = true;
      if (configurator.getDefaultText().length() < configurator.getMinChars()) {
        if (configurator.getPropertyList().getOptionProperty("text_default") != null) {
          //"default text length cannot be lesser than min chars");
          configurator.getPropertyList().getOptionProperty("text_default").setErrorMsg(systemStatus.getLabel("object.validation.defaultTextLengthNotLTMinChars"));
          valid = false;
        }
      }
      if (configurator.getDefaultText().length() > configurator.getMaxChars()) {
        if (configurator.getPropertyList().getOptionProperty("text_default") != null) {
          //"default text length cannot be greater than max chars");
          configurator.getPropertyList().getOptionProperty("text_default").setErrorMsg(systemStatus.getLabel("object.validation.defaultTextLengthNotGTMaxChars"));
          valid = false;
        }
      }
      return valid;
    }

    //Thumbnail
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Thumbnail")) {
      checkError(systemStatus, object, "filename", REQUIRED_FIELD);
    }
    
    //Relationship
    if (object.getClass().getName().equals("org.aspcfs.modules.relationships.base.Relationship")) {
      Relationship relationship = (Relationship) object;
      if (relationship.getObjectIdMapsFrom() == -1 || relationship.getObjectIdMapsTo() == -1) {
        addError(systemStatus, object, "objectIdMapsTo", "relationships.bothObjectsAreRequired");
      } else if (relationship.getObjectIdMapsFrom() == relationship.getObjectIdMapsTo()) {
        addError(systemStatus, object, "objectIdMapsTo", "relationships.orgCanNotBeRelatedToItself");
      }
    }
    
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  systemStatus      Description of the Parameter
   *@param  db                Description of the Parameter
   *@param  object            Description of the Parameter
   *@param  map               Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static boolean validate(SystemStatus systemStatus, Connection db, Object object, HashMap map) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ObjectValidator-> Checking object: " + object.getClass().getName());
    }
    //  TicketReplacementPart
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketReplacementPart")) {
      //TicketReplacementPart replacement = (TicketReplacementPart) object;
      String parseItem = (String) map.get("parseItem");
      String partNumber = (String) map.get("partNumber");
      String partDescription = (String) map.get("partDescription");
      if ((partNumber != null && !"".equals(partNumber)) &&
          (partDescription == null || "".equals(partDescription))) {
        addError(systemStatus, object, "partDescription" + parseItem, REQUIRED_FIELD);
        System.out.println("Adding Errror -->-0> partDescription is required");
      }
      if ((partDescription != null && !"".equals(partDescription)) &&
          (partNumber == null || "".equals(partNumber))) {
        addError(systemStatus, object, "partNumber" + parseItem, REQUIRED_FIELD);
        System.out.println("Adding Errror -->-0> partNumber is required");
      }
    }

    //TicketPerDayDescription
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketPerDayDescription")) {
      //TicketPerDayDescription thisDescription = (TicketPerDayDescription) object;
      String parseItem = (String) map.get("parseItem");
      String descriptionOfService = (String) map.get("descriptionOfService");
      if (descriptionOfService == null || "".equals(descriptionOfService.trim())) {
        addError(systemStatus, object, "descriptionOfService" + parseItem, REQUIRED_FIELD);
        addError(systemStatus, object, "action", "object.validation.genericActionError");
      }
      String activityDateTimeZone = (String) map.get("activityDateTimeZone");
      String activityDate = (String) map.get("activityDate");
      System.out.println("Object Validator :: activityDate is " + activityDate);
      HttpServletRequest request = (HttpServletRequest) map.get("request");

      UserBean userBean = (UserBean) request.getSession().getAttribute("User");
      User user = userBean.getUserRecord();
      //String timeZone = user.getTimeZone();
      try {
        Timestamp tmp = DateUtils.getUserToServerDateTime(TimeZone.getTimeZone(activityDateTimeZone), DateFormat.SHORT, DateFormat.LONG, activityDate, user.getLocale());
        if (tmp == null) {
          addError(systemStatus, object, "activityDate" + parseItem, INVALID_DATE);
          addError(systemStatus, object, "action", "object.validation.genericActionError");
        }
      } catch (Exception e) {
      }
    }

    return true;
  }


  /**
   *  Adds a feature to the Error attribute of the ObjectValidator class
   *
   *@param  systemStatus  The feature to be added to the Error attribute
   *@param  object        The feature to be added to the Error attribute
   *@param  field         The feature to be added to the Error attribute
   *@param  errorType     The feature to be added to the Error attribute
   */
  public static void checkError(SystemStatus systemStatus, Object object, String field, int errorType) {
    if (errorType == REQUIRED_FIELD) {
      String result = ObjectUtils.getParam(object, field);
      if (result == null || "".equals(result.trim())) {
        addError(systemStatus, object, field, REQUIRED_FIELD);
      }
    } else if (errorType == INVALID_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date == null || "".equals(date.trim())) {
          addError(systemStatus, object, field, REQUIRED_FIELD);
        } else {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat("yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            addError(systemStatus, object, field, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_NOT_REQUIRED_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date != null && !"".equals(date.trim())) {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat("yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            addError(systemStatus, object, field, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  systemStatus  Description of the Parameter
   *@param  object        Description of the Parameter
   *@param  field         Description of the Parameter
   *@param  errorName     Description of the Parameter
   *@param  errorType     Description of the Parameter
   */
  public static void checkError(SystemStatus systemStatus, Object object, String field, String errorName, int errorType) {
    if (errorType == REQUIRED_FIELD) {
      String result = ObjectUtils.getParam(object, field);
      if (result == null || "".equals(result.trim())) {
        addError(systemStatus, object, errorName, REQUIRED_FIELD);
      }
    } else if (errorType == INVALID_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date == null || "".equals(date.trim())) {
          addError(systemStatus, object, errorName, REQUIRED_FIELD);
        } else {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat("yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            addError(systemStatus, object, errorName, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_NOT_REQUIRED_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date != null && !"".equals(date.trim())) {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat("yyyy-MM-dd", locale);
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            addError(systemStatus, object, errorName, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   *  Adds a feature to the Error attribute of the ObjectValidator class
   *
   *@param  systemStatus  The feature to be added to the Error attribute
   *@param  field         The feature to be added to the Error attribute
   *@param  errorType     The feature to be added to the Error attribute
   *@param  object        The feature to be added to the Error attribute
   */
  public static void addError(SystemStatus systemStatus, Object object, String field, int errorType) {
    if (errorType == REQUIRED_FIELD) {
      addError(systemStatus, object, field, "object.validation.required");
    }
    if (errorType == INVALID_DATE || errorType == INVALID_NOT_REQUIRED_DATE) {
      addError(systemStatus, object, field, "object.validation.incorrectDateFormat");
    }
    if (errorType == INVALID_NUMBER) {
      addError(systemStatus, object, field, "object.validation.incorrectNumberFormat");
    }
  }


  /**
   *  Adds a feature to the Error attribute of the ObjectValidator class
   *
   *@param  systemStatus  The feature to be added to the Error attribute
   *@param  field         The feature to be added to the Error attribute
   *@param  errorKey      The feature to be added to the Error attribute
   *@param  object        The feature to be added to the Error attribute
   */
  private static void addError(SystemStatus systemStatus, Object object, String field, String errorKey) {
    HashMap errors = (HashMap) ObjectUtils.getObject(object, "errors");
    if (systemStatus != null) {
      errors.put(field + "Error", systemStatus.getLabel(errorKey));
    } else {
      errors.put(field + "Error", "field error");
    }
  }


  /**
   *  Adds a feature to the Warning attribute of the ObjectValidator class
   *
   *@param  systemStatus  The feature to be added to the Warning attribute
   *@param  object        The feature to be added to the Warning attribute
   *@param  field         The feature to be added to the Warning attribute
   *@param  warningType   The feature to be added to the Warning attribute
   */
  public static void checkWarning(SystemStatus systemStatus, Object object, String field, int warningType) {
    if (warningType == IS_BEFORE_TODAY) {
      Timestamp result = (Timestamp) ObjectUtils.getObject(object, field);
      if (result != null && result.before(new java.util.Date())) {
        addWarning(systemStatus, object, field, "object.validation.beforeToday");
      }
    } 
  }


  /**
   *  Adds a feature to the Warning attribute of the ObjectValidator class
   *
   *@param  systemStatus  The feature to be added to the Warning attribute
   *@param  object        The feature to be added to the Warning attribute
   *@param  field         The feature to be added to the Warning attribute
   *@param  warningKey    The feature to be added to the Warning attribute
   */
  public static void addWarning(SystemStatus systemStatus, Object object, String field, String warningKey) {
    HashMap warnings = (HashMap) ObjectUtils.getObject(object, "warnings");
    if (systemStatus != null) {
      warnings.put(field + "Warning", systemStatus.getLabel(warningKey));
    } else {
      warnings.put(field + "Warning", "field warning");
    }
  }

}

