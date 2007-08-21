package org.aspcfs.controller;

import com.zeroio.iteam.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.accounts.base.Revenue;
import org.aspcfs.modules.accounts.base.RevenueList;
import org.aspcfs.modules.actionlist.base.ActionLists;
import org.aspcfs.modules.actionplans.base.*;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.assets.base.Asset;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMember;
import org.aspcfs.modules.documents.base.DocumentStoreTeamMemberList;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.pipeline.beans.OpportunityBean;
import org.aspcfs.modules.products.base.*;
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
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.troubletickets.base.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.StringUtils;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: ObjectValidator.java,v 1.1.2.3 2004/09/08 16:37:11 partha
 *          Exp $
 * @created September 3, 2004
 */
public class ObjectValidator {

  private static int REQUIRED_FIELD = 2004090301;
  private static int IS_BEFORE_TODAY = 2004090302;
  private static int PUBLIC_ACCESS_REQUIRED = 2004090303;
  private static int INVALID_DATE = 2004090801;
  private static int INVALID_NUMBER = 2004091001;
  private static int INVALID_EMAIL = 2004091002;
  private static int INVALID_NOT_REQUIRED_DATE = 2004091003;
  private static int INVALID_EMAIL_NOT_REQUIRED = 2005060801;
  private static int PAST_DATE = 2006110117;

  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param db           Description of the Parameter
   * @param object       Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean validate(SystemStatus systemStatus, Connection db, Object object) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "ObjectValidator-> Checking object: " + object.getClass().getName());
    }
    // TODO: use required fields and warnings from systemStatus; this might
    //       be a list of ValidationTasks that perform complex tasks

    // NOTE: For now, just code the validation tasks in

    // Organization
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.accounts.base.Organization")) {
      Organization organization = (Organization) object;
      if (organization.getIsIndividual()) {
        if (organization.getNameLast() == null || "".equals(
            organization.getNameLast().trim())) {
          addError(systemStatus, object, "nameLast", REQUIRED_FIELD);
        }
      } else {
        if (organization.getName() == null || "".equals(
            organization.getName().trim())) {
          addError(systemStatus, object, "name", REQUIRED_FIELD);
        }
      }
      checkWarning(systemStatus, object, "alertDate", IS_BEFORE_TODAY);
      if(organization.getYearStarted() > Calendar.getInstance().get(Calendar.YEAR)){
        addWarning(systemStatus, object, "yearStarted", "object.validation.afterCurrentYear");
      }
    }

    // Contact
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.contacts.base.Contact")) {
      Contact thisContact = (Contact) object;
      //Check the character limit for fields.
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "company", 255);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "title", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "nameLast", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "nameSalutation", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "nameFirst", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "nameMiddle", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "nameSuffix", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "employeeId", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "startOfDay", 10);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "endOfDay", 10);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "url", 100);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "orgName", 255);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "comments", 255);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "additionalNames", 255);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "nickname", 80);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "role", 255);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "secretWord", 255);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "accountNumber", 50);
      // Check for site consistancy with account
      if (thisContact.getOrgId() > 0) {
        if (thisContact.getSiteId() != Organization.getOrganizationSiteId(db, thisContact.getOrgId()))
        {
          addError(
              systemStatus, object, "siteId", "object.validation.contact.siteIncompatibleWithAccount");
        }
      }
      // Check for either last name or company
      if (thisContact.getNameLast() == null || thisContact.getNameLast().trim().equals(
          "")) {
        if (thisContact.getOrgId() == -1) {
          if (thisContact.getCompany() == null || thisContact.getCompanyOnly().trim().equals(
              "")) {
            addError(systemStatus, object, "nameLast", REQUIRED_FIELD);
            addError(
                systemStatus, object, "lastCompany", "object.validation.contact.lastCompanyRequired");
          }
        } else {
          addError(systemStatus, object, "nameLast", REQUIRED_FIELD);
        }
      }
      if (thisContact.getSiteId() < -1) {
        addError(
            systemStatus, object, "siteId", REQUIRED_FIELD);
      }
      // Custom check
      if (thisContact.getAccessType() != -1) {
        AccessType thisType = new AccessType(db, thisContact.getAccessType());
        //all account contacts are public
        if (thisContact.getOrgId() > 0 && thisType.getRuleId() != AccessType.PUBLIC)
        {
          addError(
              systemStatus, object, "accountAccess", PUBLIC_ACCESS_REQUIRED);
        }
        //personal contacts should be owned by the user who enters it i.e they cannot be reassigned
        if (thisType.getRuleId() == AccessType.PERSONAL && thisContact.getOwner() != thisContact.getEnteredBy())
        {
          addError(
              systemStatus, object, "accessReassign", "object.validation.OwnerMustBeEnteredBy");
        }
      } else {
        addError(systemStatus, object, "access", REQUIRED_FIELD);
      }
    }

    //  Ticket
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.Ticket")) {
      Ticket thisTicket = (Ticket) object;
      if (thisTicket.getProblem() == null || thisTicket.getProblem().trim().equals(
          "")) {
        addError(systemStatus, object, "problem", REQUIRED_FIELD);
      }
      if (thisTicket.getCloseIt() == true && (thisTicket.getSolution() == null || thisTicket.getSolution().trim().equals(
          ""))) {
        addError(
            systemStatus, object, "solution", "object.validation.ticket.solutionRequired");
      }
      if (thisTicket.getContactId() == -1) {
        addError(
            systemStatus, object, "contactId", "object.validation.ticket.contactRequired");
      }
      if (thisTicket.getOrgId() == -1) {
        addError(
            systemStatus, object, "orgId", "object.validation.ticket.organizationRequired");
      }
      // If ticket is being closed, check if resolution date is before assignmented date
      if (thisTicket.getEstimatedResolutionDate() != null && thisTicket.getAssignedDate() != null)
      {
        if (thisTicket.getEstimatedResolutionDate().before(
            thisTicket.getAssignedDate())) {
          addError(
              systemStatus, object, "estimatedResolutionDate", "object.validation.ticket.estResolutionBeforeAssignment");
        }
      }
      TicketCategoryList categoryList = new TicketCategoryList();
      categoryList.setExclusiveToSite(true);
      categoryList.setSiteId(thisTicket.getSiteId());
      categoryList.buildList(db);
      if (thisTicket.getCatCode() > 0 && categoryList.getValueFromId(thisTicket.getCatCode()) == null)
      {
        addError(systemStatus, object, "catCode", "tickets.invalidCategorySelected.text");
      }
      if (thisTicket.getSubCat1() > 0 && categoryList.getValueFromId(thisTicket.getSubCat1()) == null)
      {
        addError(systemStatus, object, "subCat1", "tickets.invalidCategorySelected.text");
      }
      if (thisTicket.getSubCat2() > 0 && categoryList.getValueFromId(thisTicket.getSubCat2()) == null)
      {
        addError(systemStatus, object, "subCat2", "tickets.invalidCategorySelected.text");
      }
      if (thisTicket.getSubCat3() > 0 && categoryList.getValueFromId(thisTicket.getSubCat3()) == null)
      {
        addError(systemStatus, object, "subCat3", "tickets.invalidCategorySelected.text");
      }
      if (thisTicket.getAssignedTo() > -1) {
        User user = new User(db, thisTicket.getAssignedTo());
        if (user.getSiteId() != -1 && thisTicket.getSiteId() != user.getSiteId() && thisTicket.getProjectId() == -1)
        {
          addError(systemStatus, object, "assignedTo", "tickets.ticketCategoryDraftAssignment.invalidUserAssignment.text");
        }
      }
      if (thisTicket.getUserGroupId() > -1) {
        UserGroup group = new UserGroup(db, thisTicket.getUserGroupId());
        if (group.getSiteId() != -1 && group.getSiteId() != thisTicket.getSiteId())
        {
          addError(systemStatus, object, "userGroupId", "tickets.ticketCategoryDraftAssignment.invalidUserGroupAssignment.text");
        }
      }
    }

    //  TicketLog
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketLog")) {
      TicketLog thisTicketLog = (TicketLog) object;
      if (thisTicketLog.getTicketId() == -1 || (thisTicketLog.getEntryText() == null || thisTicketLog.getEntryText().trim().equals(
          "")) || thisTicketLog.getEnteredBy() == -1 || thisTicketLog.getModifiedBy() == -1)
      {
        return false;
      }
    }

    //  TicketActivityLog
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketActivityLog")) {
      TicketActivityLog thisLog = (TicketActivityLog) object;
      if (thisLog.getFollowUpRequired() || (thisLog.getAlertDate() != null && !"".equals(
          thisLog.getAlertDate())) ||
          (thisLog.getFollowUpDescription() != null && !"".equals(
              thisLog.getFollowUpDescription()))) {
        if (!thisLog.getFollowUpRequired()) {
          addError(systemStatus, object, "followUpRequired", REQUIRED_FIELD);
        }
        checkError(systemStatus, object, "alertDate", INVALID_DATE);
        checkError(
            systemStatus, object, "followUpDescription", REQUIRED_FIELD);
      } else
      if (!thisLog.getFollowUpRequired() && (thisLog.getAlertDate() == null || "".equals(
          thisLog.getAlertDate())) &&
          (thisLog.getFollowUpDescription() == null || "".equals(
              thisLog.getFollowUpDescription()))) {
        if (thisLog.getTicketPerDayDescriptionList() == null || thisLog.getTicketPerDayDescriptionList().size() == 0)
        {
          addError(
              systemStatus, object, "action", "object.validation.actionError.blankFormCanNotBeSaved");
        }
      }
      checkWarning(systemStatus, object, "alertDate", IS_BEFORE_TODAY);
    }

    //  OpportunityHeader
    if (object.getClass().getName().equals("org.aspcfs.modules.pipeline.base.OpportunityHeader"))
    {
      OpportunityHeader oppHeader = (OpportunityHeader) object;

      if (oppHeader.getManager() == -1) {
        addError(systemStatus, object, "manager", "object.validation.pipeline.noManagerSelected.text");
      }
      if (oppHeader.getDescription() == null || "".equals(oppHeader.getDescription().trim()))
      {
        addError(systemStatus, object, "description", REQUIRED_FIELD);
      }
      if (oppHeader.getContactLink() == -1 && oppHeader.getAccountLink() == -1)
      {
        addError(systemStatus, object, "acctContact", "object.validation.opportunity.accountContactRequired");
      }
      // 1. site compliance check between oppheader (i.e., account and contact link) and manager
      if (oppHeader.getContactLink() != -1 || oppHeader.getAccountLink() != -1)
      {
        int oppSiteId = -1;
        if (oppHeader.getContactLink() != -1) {
          oppSiteId = Contact.getContactSiteId(db, oppHeader.getContactLink());
        } else {
          oppSiteId = Organization.getOrganizationSiteId(db, oppHeader.getAccountLink());
        }

        // if opp has a site
        if (oppSiteId != -1) {
          //the manager can be from null site or from the same site
          if (oppHeader.getManager() != -1) {
            int managerSiteId = systemStatus.getUser(oppHeader.getManager()).getSiteId();
            if (managerSiteId != -1 && (managerSiteId != oppSiteId)) {
              addError(systemStatus, object, "manager", "object.validation.opportunity.permissionNotAllowedForThisSite");
            }
          }
        }
        // if link contact is null site
        if (oppSiteId == -1) {
          //the manager can only be from null site
          if (oppHeader.getManager() != -1) {
            int managerSiteId = systemStatus.getUser(oppHeader.getManager()).getSiteId();
            if (managerSiteId != -1) {
              addError(systemStatus, object, "manager", "object.validation.opportunity.permissionNotAllowedForThisSite");
            }
          }
        }
      }
    }

    // OpportunityBean
    if (object.getClass().getName().equals("org.aspcfs.modules.pipeline.beans.OpportunityBean"))
    {
      OpportunityBean bean = (OpportunityBean) object;
      OpportunityComponent oppComponent = bean.getComponent();
      OpportunityHeader oppHeader = bean.getHeader();
      AccessTypeList accessTypes = systemStatus.getAccessTypeList(db, AccessType.OPPORTUNITIES);
      if (oppHeader.getAccessType() == accessTypes.getCode(AccessType.CONTROLLED_HIERARCHY))
      {
        if (oppHeader.getManager() != -1) {
          User userRecord = systemStatus.getUser(oppHeader.getManager());
          userRecord.setBuildHierarchy(true);
          userRecord.buildResources(db);
          UserList shortChildList = userRecord.getShortChildList();
          UserList fullChildList = userRecord.getFullChildList(shortChildList, new UserList());
          fullChildList.add(userRecord);
          Iterator iterator = (Iterator) fullChildList.iterator();
          boolean flag = false;
          while (iterator.hasNext()) {
            User tempUser = (User) iterator.next();
            if (tempUser.getId() == oppComponent.getOwner()) {
              flag = true;
              break;
            }
          }
          if (!flag) {
            addError(systemStatus, object, "owner", "object.validation.pipeline.invalidOwner.text");
          }
        }
      }
      //Check that the 'assigned to/owner' person has the same site Id as
      //the contact or the account.
      if (oppComponent != null) {
        int ownerSiteId = -1;
        if (oppComponent.getOwner() != -1) {
          ownerSiteId = systemStatus.getUser(oppComponent.getOwner()).getSiteId();

          if (oppHeader.getContactLink() != -1 || oppHeader.getAccountLink() != -1)
          {

            int oppSiteId = -1;
            if (oppHeader.getContactLink() != -1) {
              oppSiteId = Contact.getContactSiteId(db, oppHeader.getContactLink());
            } else {
              oppSiteId = Organization.getOrganizationSiteId(db, oppHeader.getAccountLink());
            }
            // if link contact has a site
            if (oppSiteId != -1) {
              //the owner can be from null site or from the same site
              if (ownerSiteId != -1 && (ownerSiteId != oppSiteId)) {
                addError(systemStatus, object, "owner", "object.validation.opportunity.permissionNotAllowedForThisSite");
              }
            }
            // if link contact is null site
            if (oppSiteId == -1) {
              //the owner can only be from null site
              if (ownerSiteId != -1) {
                addError(systemStatus, object, "owner", "object.validation.opportunity.permissionNotAllowedForThisSite");
              }
            }
          }
        }
      }
    }

    //  OpportunityComponent
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.pipeline.base.OpportunityComponent")) {
      OpportunityComponent oppComponent = (OpportunityComponent) object;

      User owner = null;
      if (oppComponent.getOwner() == -1) {
        addError(systemStatus, object, "owner", "object.validation.pipeline.noOwnerSelected.text");
      }
      if (oppComponent.getOwner() != -1 && systemStatus != null) {
        owner = systemStatus.getUser(oppComponent.getOwner());
      }
      if (owner != null && owner.getExpires() != null && owner.getExpires().before(
          new Timestamp(Calendar.getInstance().getTimeInMillis()))) {
        addWarning(
            systemStatus, object, "owner", "object.validation.expiredUser");
      }
      if (owner != null && !owner.getEnabled()) {
        addWarning(
            systemStatus, object, "owner", "object.validation.disabledUser");
      }
      if (!systemStatus.hasField("pipeline-compDescription")) {
        if (oppComponent.getDescription() == null || "".equals(oppComponent.getDescription().trim()))
        {
          addError(systemStatus, object, "componentDescription", REQUIRED_FIELD);
        }
      }
      if (oppComponent.getCloseProb() == 0) {
        addError(systemStatus, object, "closeProb", REQUIRED_FIELD);
      } else {
        if (oppComponent.getCloseProb() > 1.0) {
          addError(
              systemStatus, object, "closeProb", "object.validation.opportunityComponent.closeProbNotGTOneHundred");
        } else if (oppComponent.getCloseProb() < 0) {
          addError(
              systemStatus, object, "closeProb", "object.validation.opportunityComponent.closeProbNotLTZero");
        }
      }
      if (!systemStatus.hasField("pipeline-lowEstimate") && !systemStatus.hasField("opportunity.lowEstimateCanNotBeZero"))
      {
        if (oppComponent.getLow() > oppComponent.getHigh()) {
          addError(
              systemStatus, object, "low", "object.validation.opportunityComponent.lowNotGTHigh");
        }
      }
      if (oppComponent.getCloseDate() == null || oppComponent.getCloseDateString().trim().equals(
          "")) {
        addError(systemStatus, object, "closeDate", REQUIRED_FIELD);
      }
      if (!systemStatus.hasField("pipeline-bestGuessEstimate")) {
        if (oppComponent.getGuess() == 0) {
          addError(systemStatus, object, "guess", REQUIRED_FIELD);
        }
      }
      if (systemStatus.hasField("opportunity.lowEstimateCanNotBeZero")) {
        if (oppComponent.getLow() == 0) {
          addError(systemStatus, object, "low", REQUIRED_FIELD);
        }
        if (oppComponent.getLow() < oppComponent.getGuess()) {
          addError(
              systemStatus, object, "guess", "object.validation.opportunityComponent.lowNotGTGuess");
        }
      }
      if (oppComponent.getGuess() == 0) {
        addError(systemStatus, object, "guess", REQUIRED_FIELD);
      }
      if (!systemStatus.hasField("opportunity.termsAndUnits")) {
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
      }
      if (!systemStatus.hasField("opportunity.source")) {
        if (oppComponent.getType() == null) {
          addError(systemStatus, object, "terms", REQUIRED_FIELD);
        }
      }
      //Check warning for alert date
      checkWarning(systemStatus, object, "alertDate", IS_BEFORE_TODAY);
    }

    //  OpportunityReport
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.pipeline.base.OpportunityReport")) {
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
    }

    //  Calls
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.contacts.base.Call")) {
      Call call = (Call) object;
      User callOwner = null;
      if (call.getOwner() != -1 && systemStatus != null) {
        callOwner = systemStatus.getUser(call.getOwner());
      }
      if (callOwner != null && !callOwner.getEnabled()) {
        addWarning(
            systemStatus, object, "owner", "object.validation.disabledUser");
      }
      if (callOwner != null && callOwner.getExpires() != null && callOwner.getExpires().after(
          new Timestamp(Calendar.getInstance().getTimeInMillis()))) {
        addWarning(
            systemStatus, object, "owner", "object.validation.expiredUser");
      }
      if (call.getFollowupContactId() == -1 && call.getContactId() == -1 && call.getOrgId() == -1 && call.getOppHeaderId() == -1)
      {
        addError(
            systemStatus, object, "link", "object.validation.call.notAssociated");
      }
      if (call.getLength() < 0) {
        addError(
            systemStatus, object, "length", "object.validation.call.lengthNotLTZero");
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
    if (object.getClass().getName().equals("com.zeroio.iteam.base.FileFolder"))
    {
      FileFolder fileFolder = (FileFolder) object;
      if (fileFolder.getLinkModuleId() == -1 || fileFolder.getLinkItemId() == -1)
      {
        addError(systemStatus, object, "action", "object.validation.noId");
      }
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "subject", 255);
    }

    //  ServiceContract
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.servicecontracts.base.ServiceContract")) {
      ServiceContract thisContract = (ServiceContract) object;
      checkError(
          systemStatus, object, "serviceContractNumber", REQUIRED_FIELD);
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
            (thisContract.getCurrentEndDate().before(
                thisContract.getCurrentStartDate()))) {
          addError(
              systemStatus, object, "currentEndDate", "object.validation.serviceContract.currentEndDateNotGTCurrentContractDate");
        }
        if ((initialStartDateExists) &&
            (!currentStartDateExists) &&
            (thisContract.getCurrentEndDate().before(
                thisContract.getInitialStartDate()))) {
          addError(
              systemStatus, object, "currentEndDate", "object.validation.serviceContract.currentEndDateNotLTInitialContractDate");
        }
      }
    }

    //  ServiceContractHours
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.servicecontracts.base.ServiceContractHours")) {
      ServiceContractHours thisContractHours = (ServiceContractHours) object;
      if (thisContractHours.getAdjustmentReason() == -1) {
        addError(systemStatus, object, "adjustmentReason", REQUIRED_FIELD);
      }
    }
    // Asset
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.assets.base.Asset")) {
      Asset asset = (Asset) object;
      checkError(systemStatus, object, "serialNumber", REQUIRED_FIELD);
      if (asset.getDateListed() == null) {
        addError(systemStatus, object, "dateListed", REQUIRED_FIELD);
      }
    }

    //  TicketMaintenanceNotes
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketMaintenanceNote")) {
      //TicketMaintenanceNote maintenanceNote = (TicketMaintenanceNote) object;
      checkError(systemStatus, object, "descriptionOfService", REQUIRED_FIELD);
    }

    //  TicketReplacementPart
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketReplacementPart")) {
      TicketReplacementPart replacement = (TicketReplacementPart) object;
      if ((replacement.getPartNumber() != null && !"".equals(
          replacement.getPartNumber()))) {
        checkError(systemStatus, object, "partDescription", REQUIRED_FIELD);
      }
      if (replacement.getPartDescription() != null && !"".equals(
          replacement.getPartDescription())) {
        checkError(
            systemStatus, object, "partNumber", "partNumber", REQUIRED_FIELD);
      }
    }

    //  Campaign
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.base.Campaign")) {
      Campaign campaign = (Campaign) object;
      if (campaign.getActiveDate() != null
              && campaign.getActiveDate().before(new java.util.Date())) {
        addError(systemStatus, object, "activeDate", PAST_DATE);
      }
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  InstantCampaign
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.base.InstantCampaign")) {
//      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      InstantCampaign campaign = (InstantCampaign) object;
      if (campaign.getInstantMessage() != null) {
        checkError(
            systemStatus, campaign.getInstantMessage(), "messageSubject", REQUIRED_FIELD);
        checkError(
            systemStatus, campaign.getInstantMessage(), "replyTo", INVALID_EMAIL);
      }
      checkError(systemStatus, object, "cc", INVALID_EMAIL_NOT_REQUIRED);
      checkError(systemStatus, object, "bcc", INVALID_EMAIL_NOT_REQUIRED);
    }

    //  SearchCriteriaList
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.base.SearchCriteriaList")) {
      //checkError(systemStatus, object, "groupName", REQUIRED_FIELD);
    }

    //  SearchCriteriaElement
    if (object.getClass().getName().equals("org.aspcfs.modules.communications.base.SearchCriteriaElement"))
    {
      //checkError(systemStatus, object, "groupName", REQUIRED_FIELD);
      SearchCriteriaElement element = (SearchCriteriaElement) object;
      StringTokenizer splitted = new StringTokenizer(element.getText(), "[*^|]");
      if (splitted.countTokens() > 1) {
        addError(systemStatus, object, "text", REQUIRED_FIELD);
      }
      if (element.getFieldId() == 4) {
        try {
          Timestamp tmp = DateUtils.getUserToServerDateTime((TimeZone) null, DateFormat.SHORT, DateFormat.LONG, element.getText(), new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY")));
          String date = null;
          if (tmp == null) {
            addError(systemStatus, object, "text", INVALID_DATE);
          } else {
            date = tmp.toString();
            try {
              date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
              Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
              SimpleDateFormat localeFormatter = new SimpleDateFormat("yyyy-MM-dd", locale);
              localeFormatter.applyPattern("yyyy-MM-dd");
              localeFormatter.setLenient(false);
              localeFormatter.parse(date);
            } catch (java.text.ParseException e1) {
              addError(systemStatus, object, "text", INVALID_DATE);
            }
          }
        } catch (Exception e) {
          addError(systemStatus, object, "text", INVALID_DATE);
        }
      } else if (element.getFieldId() == 5) {
        checkError(systemStatus, object, "text", INVALID_NUMBER);
      } else if (element.getFieldId() == 6) {
        checkError(systemStatus, object, "text", INVALID_NUMBER);
      } else if (element.getFieldId() != 8 || element.getFieldId() != 11) {
        checkError(systemStatus, object, "text", REQUIRED_FIELD);
      }
    }

    //  SearchFormBean
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.beans.SearchFormBean")) {
      checkError(systemStatus, object, "groupName", REQUIRED_FIELD);
      checkError(systemStatus, object, "searchCriteriaText", REQUIRED_FIELD);
    }

    //  Message
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.base.Message")) {
      Message message = (Message) object;
      if ((message.getName() == null || message.getName().trim().equals("")) && !message.getDisableNameValidation())
      {
        addError(systemStatus, object, "name", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "messageSubject", REQUIRED_FIELD);
      checkError(systemStatus, object, "replyTo", INVALID_EMAIL);
    }

    //  Survey
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.base.Survey")) {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  SurveyQuestion
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.base.SurveyQuestion")) {
      SurveyQuestion question = (SurveyQuestion) object;
      if (question.getRequired() && question.getDescription() == null || "".equals(question.getDescription().trim()))
      {
        addError(systemStatus, object, "description", REQUIRED_FIELD);
      }
      if (question.getType() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.formIncomplete");
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
      if ((project.getEstimatedCloseDate() != null) && (project.getRequestDate() != null))
      {
        if (project.getEstimatedCloseDate().before(project.getRequestDate())) {
          addWarning(
              systemStatus, object, "estimatedCloseDate", "object.validation.project.estimatedCloseDateNotLTRequestDate");
        }
      }
    }

    //  NewsArticle
    if (object.getClass().getName().equals(
        "com.zeroio.iteam.base.NewsArticle")) {
      com.zeroio.iteam.base.NewsArticle newsArticle = (com.zeroio.iteam.base.NewsArticle) object;
      if (newsArticle.getProjectId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }

      if (newsArticle.getIntro() != null) {
        checkError(systemStatus, object, "subject", REQUIRED_FIELD);
        if (newsArticle.getIntro() == null || newsArticle.getIntro().equals(
            "") || newsArticle.getIntro().equals(" \r\n<br />\r\n ")) {
          addError(systemStatus, object, "intro", REQUIRED_FIELD);
        }
        if ((newsArticle.getEndDate() != null) && (newsArticle.getStartDate() != null))
        {
          if (newsArticle.getEndDate().before(newsArticle.getStartDate())) {
            // TODO: Should be archive date is earlier than start date
            addWarning(
                systemStatus, object, "endDate", "object.validation.project.endDateNotLTStartDate");
          }
        }
      } else {
        if (newsArticle.getMessage() == null || newsArticle.getMessage().equals(
            "") || newsArticle.getMessage().equals(" \r\n<br />\r\n ")) {
          addError(systemStatus, object, "message", REQUIRED_FIELD);
        }
      }
    }

    //  IssueCategory
    if (object.getClass().getName().equals(
        "com.zeroio.iteam.base.IssueCategory")) {
      IssueCategory category = (IssueCategory) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      if (category.getProjectId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }
    }

    //Action Plan Work
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.actionplans.base.ActionPlanWork")) {
      ActionPlanWork planWork = (ActionPlanWork) object;
      if (planWork.getActionPlanId() == -1) {
        addError(systemStatus, object, "actionPlan", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "assignedTo", REQUIRED_FIELD);
      checkError(systemStatus, object, "managerId", REQUIRED_FIELD);
    }

    //Action Plan Work Note
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.actionplans.base.ActionPlanWorkNote")) {
      ActionPlanWorkNote thisNote = (ActionPlanWorkNote) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      checkError(systemStatus, object, "submitted", REQUIRED_FIELD);
      if (thisNote.getDescription() != null && thisNote.getDescription().length() > 300)
      {
        addError(systemStatus, object, "description", "object.validation.exceedsMaxLength.300.text");
      }
    }

    //Action Item Work Note
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.actionplans.base.ActionItemWorkNote")) {
      ActionItemWorkNote thisNote = (ActionItemWorkNote) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      checkError(systemStatus, object, "submitted", REQUIRED_FIELD);
    }

    //  Task
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.tasks.base.Task")) {
      Task thisTask = (Task) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (thisTask.getCategoryId() == -1 && thisTask.getOwner() == -1) {
        addError(systemStatus, object, "owner", REQUIRED_FIELD);
      }
      //If task is marked personal and the owner is different from the user entering the task, throw an error
      if (thisTask.getSharing() == 1 && thisTask.getOwner() != thisTask.getModifiedBy())
      {
        addError(
            systemStatus, object, "sharing", "object.validation.task.ownerPersonal");
      }
      checkWarning(systemStatus, object, "dueDate", IS_BEFORE_TODAY);
    }

    //  TicketTask
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketTask")) {
      TicketTask thisTask = (TicketTask) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (thisTask.getCategoryId() == -1 && thisTask.getOwner() == -1) {
        addError(systemStatus, object, "owner", REQUIRED_FIELD);
      }
      checkWarning(systemStatus, object, "dueDate", IS_BEFORE_TODAY);
    }

    //  TaskCategory
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.tasks.base.TaskCategory")) {
      TaskCategory thisCategory = (TaskCategory) object;
      if ((thisCategory.getLinkModuleId() == -1) || (thisCategory.getLinkItemId() == -1))
      {
        addError(systemStatus, object, "linkModuleId", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
    }

    //  Requirement
    if (object.getClass().getName().equals(
        "com.zeroio.iteam.base.Requirement")) {
      Requirement requirement = (Requirement) object;
      if (requirement.getProjectId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }
      checkError(systemStatus, object, "shortDescription", REQUIRED_FIELD);
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (requirement.getDeadline() != null && requirement.getStartDate() != null)
      {
        if (requirement.getDeadline().before(requirement.getStartDate())) {
          addWarning(
              systemStatus, object, "deadline", "object.validation.project.requirements.deadlineNotLTStartDate");
        }
      }
    }

    // Assignment
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Assignment"))
    {
      Assignment assignment = (Assignment) object;
      if (assignment.getProjectId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.project.projectIdRequired");
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
    if (object.getClass().getName().equals(
        "com.zeroio.iteam.base.AssignmentFolder")) {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  CFSNote
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.mycfs.base.CFSNote")) {
      checkError(systemStatus, object, "body", REQUIRED_FIELD);
      checkError(
          systemStatus, object, "subject", "messageSubject", REQUIRED_FIELD);
    }

    //  Revenue
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.accounts.base.Revenue")) {
      Revenue revenue = (Revenue) object;
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (revenue.getAmount() == 0) {
        addError(systemStatus, object, "amount", REQUIRED_FIELD);
      }
    }

    //  CustomFieldCategory
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.base.CustomFieldCategory")) {
      CustomFieldCategory thisCategory = (CustomFieldCategory) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisCategory.getModuleId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.formDataMissing");
      }
      //If this folder is being updated such that it can have only one record per link_item, then
      //make sure it previously does not have multiple records for any of the link_items
      if (thisCategory.getId() != -1 && !thisCategory.getAllowMultipleRecords())
      {
        if (thisCategory.hasMultipleRecords(db)) {
          addError(
              systemStatus, object, "allowMultipleRecords", "object.validation.customFieldCategory.hasMultipleRecords");
        }
      }
    }

    //  CustomFieldGroup
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.base.CustomFieldGroup")) {
      CustomFieldGroup group = (CustomFieldGroup) object;
      if (group.getCategoryId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.formDataMissing");
      }
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //  CustomField
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.base.CustomField")) {
      CustomField thisField = (CustomField) object;
      if (thisField.getGroupId() == -1) {
        addError(
            systemStatus, object, "recordId", "object.validation.customField.groupIdNotPresent");
      }
      if (thisField.getType() == -1) {
        addError(
            systemStatus, object, "type", "object.validation.customField.typeNotPresent");
      }
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisField.getLengthRequired()) {
        if (thisField.getParameter("maxlength").equals("")) {
          addError(
              systemStatus, object, "maxLength", "object.validation.customField.maxLength");
        } else {
          try {
            if (Integer.parseInt(thisField.getParameter("maxlength")) > 255) {
              addError(
                  systemStatus, object, "maxLength", "object.validation.customField.maxLengthTooHigh");
            }
          } catch (Exception e) {
            addError(
                systemStatus, object, "maxLength", "object.validation.customField.maxLengthNumber");
          }
        }
      }

			//checking for data types mis-match for default values
			if (thisField.getDefaultValue() != null && !thisField.getDefaultValue().equals(
			"")) {
				if (thisField.getType() == CustomField.INTEGER) {
					try {
						int testNumber = Integer.parseInt(thisField.getDefaultValue());
						thisField.setEnteredNumber(testNumber);
						thisField.setEnteredDouble(Double.parseDouble("" + testNumber));
					} catch (Exception e) {
						addError(
								systemStatus, object, "defaultValue" + thisField.getId(), "object.validation.incorrectWholeNumberFormat");
						thisField.setError(
								systemStatus.getLabel(
								"object.validation.incorrectWholeNumberFormat"));
					}
				}
				if (thisField.getType() == CustomField.FLOAT) {
					try {
						double testNumber = Double.parseDouble(
								thisField.getDefaultValue());
						thisField.setEnteredDouble(testNumber);
					} catch (Exception e) {
						addError(systemStatus, object, "defaultValue" + thisField.getId(), INVALID_NUMBER);
						thisField.setError(
								systemStatus.getLabel(
								"object.validation.incorrectNumberFormat"));
					}
				}
				if (thisField.getType() == CustomField.PERCENT) {
					try {
						double testNumber = Double.parseDouble(
								thisField.getDefaultValue());
						thisField.setEnteredDouble(testNumber);
					} catch (Exception e) {
						addError(systemStatus, object, "defaultValue" + thisField.getId(), INVALID_NUMBER);
						thisField.setError(systemStatus.getLabel("object.validation.incorrectNumberFormat"));
					}
				}
				if (thisField.getType() == CustomField.CURRENCY) {
					try {
						Locale locale = new Locale(
								System.getProperty("LANGUAGE"), System.getProperty(
								"COUNTRY"));
						NumberFormat nf = NumberFormat.getInstance(locale);
						DecimalFormat df = (DecimalFormat)nf;
						df.setMinimumFractionDigits(2);
						df.setMaximumFractionDigits(2);
						df.setDecimalSeparatorAlwaysShown(true);
						thisField.setEnteredDouble(
								nf.parse(thisField.getDefaultValue()).doubleValue());
						String formattedValue = df.format(thisField.getEnteredDouble());
						thisField.setDefaultValue(formattedValue);
					} catch (Exception e) {
						addError(systemStatus, object, "defaultValue" + thisField.getId(), INVALID_NUMBER);
						thisField.setError(
								systemStatus.getLabel(
								"object.validation.incorrectNumberFormat"));
					}
				}
				if (thisField.getType() == CustomField.DATE) {
					try {
						Locale locale = new Locale(
								System.getProperty("LANGUAGE"), System.getProperty(
								"COUNTRY"));
						DateFormat localeFormatter = DateFormat.getDateInstance(
								DateFormat.SHORT, locale);
						localeFormatter.setLenient(false);
						localeFormatter.parse(thisField.getDefaultValue());
					} catch (java.text.ParseException e) {
						addError(systemStatus, object, "defaultValue" + thisField.getId(), INVALID_DATE);
						thisField.setError(
								systemStatus.getLabel(
								"object.validation.incorrectDateFormat"));
					}
				}
				if (thisField.getType() == CustomField.EMAIL) {
					if (!checkError(
							systemStatus, object, "defaultValue" + thisField.getId(), INVALID_EMAIL_NOT_REQUIRED)) {
						thisField.setError(
								systemStatus.getLabel(
								"object.validation.communications.fullEmailAddress"));
					}
				}
				if (thisField.getType() == CustomField.URL) {
					if (thisField.getDefaultValue().indexOf(".") < 0) {
						addError(
								systemStatus, object, "defaultValue" + thisField.getId(), "object.validation.customField.incorrectUrlFormat");
						thisField.setError(
								systemStatus.getLabel(
								"object.validation.customField.incorrectUrlFormat"));
					}
				}
			}

			// Validate the data
      if (thisField.getValidateData()) {
        if (thisField.getRecordId() == -1) {
          addError(
              systemStatus, object, "recordId", "object.validation.customField.recordIdNotPresent");
          thisField.setError(
              systemStatus.getLabel(
                  "object.validation.customField.recordIdNotPresent"));
        }
        if (thisField.getType() == -1) {
          addError(
              systemStatus, object, "type", "object.validation.customField.typeNotPresent");
          thisField.setError(
              systemStatus.getLabel(
                  "object.validation.customField.typeNotPresent"));
        }

        //Required Fields
        if (thisField.getRequired() && (thisField.getEnteredValue() == null || thisField.getEnteredValue().equals(
            ""))) {
          addError(systemStatus, object, "enteredValue", REQUIRED_FIELD);
          thisField.setError(
              systemStatus.getLabel("object.validation.required"));
        }
        if (thisField.getType() == CustomField.SELECT && thisField.getRequired() && thisField.getSelectedItemId() == -1)
        {
          addError(systemStatus, object, "selectedItemId", REQUIRED_FIELD);
          thisField.setError(
              systemStatus.getLabel("object.validation.required"));
        }
        if (thisField.getRequired() &&
            thisField.getType() == CustomField.STATE_SELECT && (
            thisField.getEnteredValue() == null ||
                "".equals(thisField.getEnteredValue().trim()) ||
                "-1".equals(thisField.getEnteredValue().trim()) ||
                "--".equals(thisField.getEnteredValue().trim()))) {
          addError(systemStatus, object, "enteredValue", REQUIRED_FIELD);
          thisField.setError(systemStatus.getLabel("object.validation.required"));
        }
        //Type mis-match
        if (thisField.getEnteredValue() != null && !thisField.getEnteredValue().equals(
            "")) {
          if (thisField.getType() == CustomField.INTEGER) {
            try {
              int testNumber = Integer.parseInt(thisField.getEnteredValue());
              thisField.setEnteredNumber(testNumber);
              thisField.setEnteredDouble(Double.parseDouble("" + testNumber));
            } catch (Exception e) {
              addError(
                  systemStatus, object, "enteredValue", "object.validation.incorrectWholeNumberFormat");
              thisField.setError(
                  systemStatus.getLabel(
                      "object.validation.incorrectWholeNumberFormat"));
            }
          }

          if (thisField.getType() == CustomField.FLOAT) {
            try {
              double testNumber = Double.parseDouble(
                  thisField.getEnteredValue());
              thisField.setEnteredDouble(testNumber);
            } catch (Exception e) {
              addError(systemStatus, object, "enteredValue", INVALID_NUMBER);
              thisField.setError(
                  systemStatus.getLabel(
                      "object.validation.incorrectNumberFormat"));
            }
          }

          if (thisField.getType() == CustomField.PERCENT) {
            try {
              double testNumber = Double.parseDouble(
                  thisField.getEnteredValue());
              thisField.setEnteredDouble(testNumber);
            } catch (Exception e) {
              addError(systemStatus, object, "enteredValue", INVALID_NUMBER);
              thisField.setError(systemStatus.getLabel("object.validation.incorrectNumberFormat"));
            }
          }

          if (thisField.getRequired() && thisField.getType() == CustomField.CHECKBOX)
          {
            boolean testFlag = DatabaseUtils.parseBoolean(thisField.getEnteredValue());
            if (!testFlag) {
              addError(systemStatus, object, "enteredValue", REQUIRED_FIELD);
              thisField.setError(systemStatus.getLabel("object.validation.required"));
            }
          }

          if (thisField.getType() == CustomField.CURRENCY) {
            try {
              Locale locale = new Locale(
                  System.getProperty("LANGUAGE"), System.getProperty(
                  "COUNTRY"));
              NumberFormat nf = NumberFormat.getInstance(locale);
              thisField.setEnteredDouble(
                  nf.parse(thisField.getEnteredValue()).doubleValue());
              Double tmpDouble = new Double(thisField.getEnteredDouble());
              thisField.setEnteredValue(tmpDouble.toString());
            } catch (Exception e) {
              addError(systemStatus, object, "enteredValue", INVALID_NUMBER);
              thisField.setError(
                  systemStatus.getLabel(
                      "object.validation.incorrectNumberFormat"));
            }
          }

          if (thisField.getType() == CustomField.DATE) {
            try {
              Locale locale = new Locale(
                  System.getProperty("LANGUAGE"), System.getProperty(
                  "COUNTRY"));
              DateFormat localeFormatter = DateFormat.getDateInstance(
                  DateFormat.SHORT, locale);
              localeFormatter.setLenient(false);
              localeFormatter.parse(thisField.getEnteredValue());
            } catch (java.text.ParseException e) {
              addError(systemStatus, object, "enteredValue", INVALID_DATE);
              thisField.setError(
                  systemStatus.getLabel(
                      "object.validation.incorrectDateFormat"));
            }
          }

          if (thisField.getType() == CustomField.EMAIL) {
            /*
             *  if ((thisField.getEnteredValue().indexOf("@") < 1) ||
             *  (thisField.getEnteredValue().indexOf(" ") > -1) ||
             *  (thisField.getEnteredValue().indexOf(".") < 0)) {
             *  addError(systemStatus, object, "enteredValue", "object.validation.communications.fullEmailAddress");
             *  thisField.setError(systemStatus.getLabel("object.validation.communications.fullEmailAddress"));
             *  }
            */
            if (!checkError(
                systemStatus, object, "enteredValue", INVALID_EMAIL_NOT_REQUIRED))
            {
              thisField.setError(
                  systemStatus.getLabel(
                      "object.validation.communications.fullEmailAddress"));
            }
          }

          if (thisField.getType() == CustomField.URL) {
            if (thisField.getEnteredValue().indexOf(".") < 0) {
              addError(
                  systemStatus, object, "enteredValue", "object.validation.customField.incorrectUrlFormat");
              thisField.setError(
                  systemStatus.getLabel(
                      "object.validation.customField.incorrectUrlFormat"));
            }
          }
        }
      }
    }

    //  Role
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.admin.base.Role")) {
      Role role = (Role) object;
      checkError(systemStatus, object, "role", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "role", 80);
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      if (role.isDuplicate(db)) {
        addError(
            systemStatus, object, "role", "object.validation.roleNameAlreadyInUse");
      }
    }

    //  Viewpoint
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.admin.base.Viewpoint")) {
      Viewpoint view = (Viewpoint) object;
      if (view.getVpUserId() == -1) {
        addError(systemStatus, object, "Contact", REQUIRED_FIELD);
      }
      if (view.getVpUserId() == view.getUserId()) {
        addError(
            systemStatus, object, "Contact", "object.validation.ownViewpointNotAllowed");
      }
    }

    //  RegistrationBean
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.setup.base.RegistrationBean")) {
      //RegistrationBean thisBean = (RegistrationBean) object;
      checkError(systemStatus, object, "profile", REQUIRED_FIELD);
      checkError(systemStatus, object, "nameFirst", REQUIRED_FIELD);
      checkError(systemStatus, object, "nameLast", REQUIRED_FIELD);
      checkError(systemStatus, object, "company", REQUIRED_FIELD);
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
    }

    //  Invitation
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Invitation"))
    {
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
      checkError(systemStatus, object, "firstName", REQUIRED_FIELD);
      checkError(systemStatus, object, "lastName", REQUIRED_FIELD);
      return false;
    }

    //  FileItemVersion
    if (object.getClass().getName().equals(
        "com.zeroio.iteam.base.FileItemVersion")) {
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "filename", REQUIRED_FIELD);
    }

    //  Issue
    if (object.getClass().getName().equals("com.zeroio.iteam.base.Issue")) {
      Issue issue = (Issue) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "body", REQUIRED_FIELD);
      if (issue.getProjectId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.project.projectIdRequired");
      }
      if (issue.getCategoryId() == -1) {
        addError(systemStatus, object, "categoryId", REQUIRED_FIELD);
      }
    }

    //  IssueReply
    if (object.getClass().getName().equals("com.zeroio.iteam.base.IssueReply"))
    {
      IssueReply issueReply = (IssueReply) object;
      checkError(systemStatus, object, "subject", REQUIRED_FIELD);
      checkError(systemStatus, object, "body", REQUIRED_FIELD);
      if (issueReply.getIssueId() == -1) {
        addError(
            systemStatus, object, "action", "object.validation.issueIdNotSpecified");
      }
    }

    //  ActionList
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.actionlist.base.ActionList")) {
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
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.contacts.base.ContactImport")) {
      ContactImport thisImport = (ContactImport) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisImport.getType() < 0) {
        addError(systemStatus, object, "type", REQUIRED_FIELD);
      }
      if (thisImport.getSiteId() == Constants.INVALID_SITE) {
        addError(systemStatus, object, "siteId", REQUIRED_FIELD);
      }
    }

    //  Recipient
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.communications.base.Recipient")) {
      Recipient recipient = (Recipient) object;
      if (recipient.getCampaignId() == -1) {
        addError(systemStatus, object, "campaign", REQUIRED_FIELD);
      }
      if (recipient.getContactId() == -1) {
        addError(systemStatus, object, "contact", REQUIRED_FIELD);
      }
    }

    //  Criteria
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.reports.base.Criteria")) {
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
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.reports.base.Parameter")) {
      Parameter parameter = (Parameter) object;
      if (parameter.getCriteriaId() == -1) {
        addError(systemStatus, object, "criteriaId", REQUIRED_FIELD);
      }
    }

    //  DatabaseBean
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.setup.beans.DatabaseBean")) {
      DatabaseBean bean = (DatabaseBean) object;
      if (!bean.isEmbedded()) {
        checkError(systemStatus, object, "ip", REQUIRED_FIELD);
        if (bean.getPort() <= 0) {
          addError(systemStatus, object, "port", REQUIRED_FIELD);
        }
      }
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      checkError(systemStatus, object, "user", REQUIRED_FIELD);
    }

    //  ServerBean
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.setup.beans.ServerBean")) {
      ServerBean bean = (ServerBean) object;
      checkError(systemStatus, object, "url", REQUIRED_FIELD);
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
      checkError(systemStatus, object, "emailAddress", REQUIRED_FIELD);
      if (bean.getTimeZone() == null || "".equals(bean.getTimeZone().trim()) || "-1".equals(
          bean.getTimeZone())) {
        addError(systemStatus, object, "timeZone", REQUIRED_FIELD);
      }
    }

    //  UserSetupBean
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.setup.beans.UserSetupBean")) {
      //UserSetupBean bean = (UserSetupBean) object;
      checkError(systemStatus, object, "nameFirst", REQUIRED_FIELD);
      checkError(systemStatus, object, "nameLast", REQUIRED_FIELD);
      checkError(systemStatus, object, "email", REQUIRED_FIELD);
      checkError(systemStatus, object, "username", REQUIRED_FIELD);
      checkError(systemStatus, object, "password1", REQUIRED_FIELD);
      checkError(systemStatus, object, "password2", REQUIRED_FIELD);
    }

    // ProductCatalog
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.products.base.ProductCatalog")) {
      ProductCatalog productCatalog = (ProductCatalog) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (productCatalog.getStartDate() != null && productCatalog.getExpirationDate() != null)
      {
        if (productCatalog.getStartDate().after(
            productCatalog.getExpirationDate())) {
          addError(
              systemStatus, object, "startDate", "object.validation.startAfterExpiration");
        }
      }
      if (productCatalog.getStartDate() == null && productCatalog.getExpirationDate() != null)
      {
        addError(
            systemStatus, object, "startDate", "object.validation.startAfterExpiration");
      }
      if (productCatalog.getActive()) {
        if (productCatalog.getActivePrice() == null || productCatalog.getActivePrice().getPriceAmount() <= 0 ) {
          addWarning(
              systemStatus, object, "active", "object.validation.product.activePriceNotFound");
        }
      }
    }
    //  ProductCatalogImport
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.products.base.ProductCatalogImport")) {
      ProductCatalogImport thisImport = (ProductCatalogImport) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (thisImport.getType() < 0) {
        addError(systemStatus, object, "type", REQUIRED_FIELD);
      }
    }
    // ProductOption
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.products.base.ProductOption")) {
      ProductOption option = (ProductOption) object;
      if (option.getStartDate() != null && option.getEndDate() != null) {
        if (option.getStartDate().after(option.getEndDate())) {
          addError(
              systemStatus, object, "startDate", "object.validation.productPricing.startDateNotGTExpirationDate");
        }
      }
      if (option.getStartDate() == null && option.getEndDate() != null) {
        addError(
            systemStatus, object, "startDate", "object.validation.productPricing.startDateNotGTExpirationDate");
      }
    }

    // ProductCategory
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.products.base.ProductCategory")) {
      ProductCategory productCategory = (ProductCategory) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (productCategory.getStartDate() != null && productCategory.getExpirationDate() != null)
      {
        if (productCategory.getStartDate().after(
            productCategory.getExpirationDate())) {
          addError(
              systemStatus, object, "startDate", "object.validation.startAfterExpiration");
        }
      }
    }

    //Document Store
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.documents.base.DocumentStore")) {
      checkError(systemStatus, object, "title", REQUIRED_FIELD);
      checkError(systemStatus, object, "shortDescription", REQUIRED_FIELD);
      checkError(systemStatus, object, "requestDate", REQUIRED_FIELD);
    }
    // Quote
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.quotes.base.Quote")) {
      Quote quote = (Quote) object;
      if (quote.getOrgId() == -1) {
        addError(systemStatus, object, "orgId", REQUIRED_FIELD);
      }
      if (quote.getContactId() == -1) {
        addError(systemStatus, object, "contactId", REQUIRED_FIELD);
      }
      if (quote.getCloseIt() && quote.getStatusId() == -1) {
        addError(
            systemStatus, object, "statusId", "object.validation.quoteClosedWithoutExplanation");
      }
      checkError(systemStatus, object, "shortDescription", REQUIRED_FIELD);

      if (quote.getHeaderId() != -1) {
        String allowMultipleQuotes = systemStatus.getValue(Quote.QUOTE_CONFIG_NAME, Quote.MULTIPLE_QUOTE_CONFIG_PARAM);
        if (allowMultipleQuotes != null) {
          if (!Quote.allowMultipleQuotesPerOpportunity(allowMultipleQuotes)) {
            QuoteList tmpQuoteList = new QuoteList();
            tmpQuoteList.setHeaderId(quote.getHeaderId());
            tmpQuoteList.buildList(db);
            if (quote.getId() == -1) {
              // Check when a quote is created
              if (tmpQuoteList.size() > 0) {
                addError(systemStatus, object, "headerId", "object.validation.opportunityHasQuotes");
              }
            } else {
              // Check when a quote is modified
              Iterator tmpQuoteIterator = tmpQuoteList.iterator();
              while (tmpQuoteIterator.hasNext()) {
                Quote tmpQuote = (Quote) tmpQuoteIterator.next();
                if (tmpQuote.getId() != quote.getId()) {
                  addError(systemStatus, object, "headerId", "object.validation.opportunityHasQuotes");
                }
              }
            }
          }
        }
        String allowMultipleVersion = systemStatus.getValue(Quote.QUOTE_CONFIG_NAME, Quote.MULTIPLE_VERSION_CONFIG_PARAM);
        if (allowMultipleVersion != null) {
          if (quote.getVersionNumber() > 1) {
            addError(systemStatus, object, "headerId", "object.validation.noMultipleVersionQuoteForOpportunity");
          }
        }
      }
    }

    // Quote Product
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.quotes.base.QuoteProduct")) {
      QuoteProduct product = (QuoteProduct) object;
      if (product.getProductId() == -1) {
        addError(
            systemStatus, object, "productId", "object.validation.emptyQuoteProduct");
      }
      if (product.getQuoteId() == -1) {
        addError(
            systemStatus, object, "quoteId", "object.validation.quoteProductWithoutQuote");
      }
      if (product.getQuantity() < 0) {
        addError(
            systemStatus, object, "quantity", "object.validation.negativeQuoteProductQuantity");
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

    if (object.getClass().getName().equals(
        "org.aspcfs.modules.quotes.base.QuoteProductBean")) {
      //QuoteProductBean product = (QuoteProductBean) object;
    }

    // Quote Product Option
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.quotes.base.QuoteProductOption")) {
    }

    // Quote Condition
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.quotes.base.QuoteCondition")) {
      QuoteCondition condition = (QuoteCondition) object;
      if (condition.getConditionName() == null || "".equals(
          condition.getConditionName().trim())) {
        addError(systemStatus, object, "description", REQUIRED_FIELD);
      }
      if (condition.getConditionName().length() > 300) {
        addError(
            systemStatus, object, "description", "object.validation.descriptionNotGT300Characters");
      }
    }

    // Quote Remark
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.quotes.base.QuoteRemark")) {
      QuoteRemark remark = (QuoteRemark) object;
      if (remark.getRemarkName() == null || "".equals(
          remark.getRemarkName().trim())) {
        addError(systemStatus, object, "description", REQUIRED_FIELD);
      }
      if (remark.getRemarkName().length() > 300) {
        addError(
            systemStatus, object, "description", "object.validation.descriptionNotGT300Characters");
      }
    }

    // Quote Note
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.quotes.base.QuoteNote")) {
      QuoteNote note = (QuoteNote) object;
      if (note.getQuoteId() == -1) {
        addError(systemStatus, object, "quoteId", REQUIRED_FIELD);
      }
      checkError(systemStatus, object, "notes", REQUIRED_FIELD);
    }

    //Custom List View
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.admin.base.CustomListView")) {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    // Product Catalog Pricing
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.products.base.ProductCatalogPricing")) {
      ProductCatalogPricing pricing = (ProductCatalogPricing) object;
      if (pricing.getStartDate() != null && pricing.getExpirationDate() != null)
      {
        if (pricing.getStartDate().after(pricing.getExpirationDate())) {
          addError(
              systemStatus, object, "startDate", "object.validation.productPricing.startDateNotGTExpirationDate");
        }
      }
    }

    //Numerical Configurator
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.products.configurator.NumericalConfigurator")) {
      NumericalConfigurator configurator = (NumericalConfigurator) object;
      boolean valid = true;
      if (configurator.getMinNum() != -1 && configurator.getMaxNum() != -1 && configurator.getMinNum() > configurator.getMaxNum())
      {
        configurator.getPropertyList().getOptionProperty("number_max").setErrorMsg(
            systemStatus.getLabel("object.validation.maxNumberNotLTMinNumber"));//"max number cannot be lesser than min number");
        valid = false;
      }
      if (configurator.getMinNum() != -1 && configurator.getDefaultNum() < configurator.getMinNum())
      {
        if (configurator.getPropertyList().getOptionProperty("number_default") != null)
        {
          configurator.getPropertyList().getOptionProperty("number_default").setErrorMsg(
              systemStatus.getLabel(
                  "object.validation.defaultNumberNotLTMinNumber"));//"default number value cannot be lesser than min number");
          valid = false;
        }
      }
      if (configurator.getMaxNum() != -1 && configurator.getDefaultNum() > configurator.getMaxNum())
      {
        if (configurator.getPropertyList().getOptionProperty("number_default") != null)
        {
          configurator.getPropertyList().getOptionProperty("number_default").setErrorMsg(
              systemStatus.getLabel(
                  "object.validation.defaultNumberNotGTMaxNumber"));//"default number value cannot be greater than max number");
          valid = false;
        }
      }
      return valid;
    }

    //String Configurator
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.products.configurator.StringConfigurator")) {
      StringConfigurator configurator = (StringConfigurator) object;
      boolean valid = true;
      if (configurator.getDefaultText().length() < configurator.getMinChars()) {
        if (configurator.getPropertyList().getOptionProperty("text_default") != null)
        {
          //"default text length cannot be lesser than min chars");
          configurator.getPropertyList().getOptionProperty("text_default").setErrorMsg(
              systemStatus.getLabel(
                  "object.validation.defaultTextLengthNotLTMinChars"));
          valid = false;
        }
      }
      if (configurator.getDefaultText().length() > configurator.getMaxChars()) {
        if (configurator.getPropertyList().getOptionProperty("text_default") != null)
        {
          //"default text length cannot be greater than max chars");
          configurator.getPropertyList().getOptionProperty("text_default").setErrorMsg(
              systemStatus.getLabel(
                  "object.validation.defaultTextLengthNotGTMaxChars"));
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
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.relationships.base.Relationship")) {
      Relationship relationship = (Relationship) object;
      if (relationship.getObjectIdMapsFrom() == -1 || relationship.getObjectIdMapsTo() == -1)
      {
        addError(
            systemStatus, object, "objectIdMapsTo", "relationships.bothObjectsAreRequired");
      } else
      if (relationship.getObjectIdMapsFrom() == relationship.getObjectIdMapsTo())
      {
        addError(
            systemStatus, object, "objectIdMapsTo", "relationships.orgCanNotBeRelatedToItself");
      }

      if ((relationship.getCategoryIdMapsFrom() == Constants.ACCOUNT_OBJECT) &&
          (relationship.getCategoryIdMapsTo() == Constants.ACCOUNT_OBJECT)) {

        int fromSiteId = Organization.getOrganizationSiteId(db, relationship.getObjectIdMapsFrom());
        int toSiteId = Organization.getOrganizationSiteId(db, relationship.getObjectIdMapsTo());
        if (fromSiteId != toSiteId) {
          addError(
              systemStatus, object, "objectIdMapsTo", "relationships.orgFromDifferentSitesCanNotBeRelated");
        }
      }
    }

    //ContactHistory
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.contacts.base.ContactHistory")) {
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
    }

    //ActionPlan
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.actionplans.base.ActionPlan")) {
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "name", 255);
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //ActionPhase
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.actionplans.base.ActionPhase")) {
      ActionPhase phase = (ActionPhase) object;
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      if (phase.getId() != -1 && phase.getId() == phase.getParentId()) {
        addError(systemStatus, object, "parentId", "object.validation.actionObjectCanNotBeItsOwnParentError.text");
      }
    }

    //ActionStep
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.actionplans.base.ActionStep")) {
      checkError(systemStatus, object, "description", REQUIRED_FIELD);
      ActionStep step = (ActionStep) object;
      if (step.getId() != -1 && step.getId() == step.getParentId()) {
        addError(systemStatus, object, "parentId", "object.validation.actionObjectCanNotBeItsOwnParentError.text");
      }
      if (step.getPermissionType() == ActionStep.SPECIFIC_USER_GROUP && (step.getUserGroupId() == -1 || step.getDepartmentId() > -1 || step.getRoleId() > -1))
      {
        addError(systemStatus, object, "userGroupId", "object.validation.invalidUserGroupIdError.text");
      }
      if (step.getPermissionType() == ActionStep.DEPARTMENT && (step.getUserGroupId() > -1 || step.getDepartmentId() == -1 || step.getRoleId() > -1))
      {
        addError(systemStatus, object, "departmentId", "object.validation.invalidDepartmentIdError.text");
      }
      if (step.getPermissionType() == ActionStep.ROLE && (step.getUserGroupId() > -1 || step.getDepartmentId() > -1 || step.getRoleId() == -1))
      {
        addError(systemStatus, object, "roleId", "object.validation.invalidRoleIdError.text");
      }
			if ((!step.getDisplayInPlanList() && !(step.getPlanListLabel() == null || "".equals(step.getPlanListLabel()))) || (step.getDisplayInPlanList() && (step.getPlanListLabel() == null || "".equals(step.getPlanListLabel()))))
			{
				addError(systemStatus, object, "displayInPlanList", "object.validation.required");
    }

		}

    //User
    if (object.getClass().getName().equals("org.aspcfs.modules.admin.base.User"))
    {
      User user = (User) object;
      int userId = user.getId();
      OrganizationList sourceAccounts = new OrganizationList();
      sourceAccounts.setOwnerId(userId);
      sourceAccounts.buildList(db);
      if (sourceAccounts.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      ContactList sourcePublicContacts = new ContactList();
      sourcePublicContacts.setOwner(userId);
      sourcePublicContacts.setLeadsOnly(Constants.FALSE);
      sourcePublicContacts.setEmployeesOnly(Constants.FALSE);
      sourcePublicContacts.setBuildDetails(false);
      sourcePublicContacts.setBuildTypes(false);
      sourcePublicContacts.setRuleId(AccessType.PUBLIC);
      sourcePublicContacts.setExcludeAccountContacts(true);
      sourcePublicContacts.setIncludeAllSites(true);
      sourcePublicContacts.buildList(db);
      if (sourcePublicContacts.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      ContactList sourceHierarchyContacts = new ContactList();
      sourceHierarchyContacts.setOwner(userId);
      sourceHierarchyContacts.setLeadsOnly(Constants.FALSE);
      sourceHierarchyContacts.setEmployeesOnly(Constants.FALSE);
      sourceHierarchyContacts.setBuildDetails(false);
      sourceHierarchyContacts.setBuildTypes(false);
      sourceHierarchyContacts.setRuleId(AccessType.CONTROLLED_HIERARCHY);
      sourceHierarchyContacts.setIncludeAllSites(true);
      sourceHierarchyContacts.buildList(db);
      if (sourceHierarchyContacts.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      ContactList sourceAccountContacts = new ContactList();
      sourceAccountContacts.setOwner(userId);
      sourceAccountContacts.setLeadsOnly(Constants.FALSE);
      sourceAccountContacts.setEmployeesOnly(Constants.FALSE);
      sourceAccountContacts.setBuildDetails(false);
      sourceAccountContacts.setBuildTypes(false);
      sourceAccountContacts.setWithAccountsOnly(true);
      sourceAccountContacts.setIncludeAllSites(true);
      sourceAccountContacts.buildList(db);
      if (sourceAccountContacts.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      ContactList sourceLeads = new ContactList();
      sourceLeads.setOwner(userId);
      sourceLeads.setLeadsOnly(Constants.TRUE);
      sourceLeads.setEmployeesOnly(Constants.FALSE);
      sourceLeads.setBuildDetails(false);
      sourceLeads.setBuildTypes(false);
      sourceLeads.setIncludeAllSites(true);
      sourceLeads.buildList(db);
      if (sourceLeads.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      UserList sourceUsers = new UserList();
      sourceUsers.setManagerId(userId);
      sourceUsers.buildList(db);
      if (sourceUsers.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      TicketList sourceOpenTickets = new TicketList();
      sourceOpenTickets.setAssignedTo(userId);
      sourceOpenTickets.setIncludeAllSites(true);
      sourceOpenTickets.setOnlyOpen(true);
      sourceOpenTickets.buildList(db);
      if (sourceOpenTickets.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      TaskList incompleteTicketTasks = new TaskList();
      incompleteTicketTasks.setOwner(userId);
      incompleteTicketTasks.setHasLinkedTicket(Constants.TRUE);
      incompleteTicketTasks.setComplete(Constants.FALSE);
      incompleteTicketTasks.buildList(db);
      if (incompleteTicketTasks.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      RevenueList sourceRevenue = new RevenueList();
      sourceRevenue.setOwner(userId);
      sourceRevenue.buildList(db);
      if (sourceRevenue.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      AssignmentList sourceAssignments = new AssignmentList();
      sourceAssignments.setAssignmentsForUser(userId);
      sourceAssignments.setIncompleteOnly(true);
      sourceAssignments.buildList(db);
      if (sourceAssignments.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      OpportunityList managingOpportunities = new OpportunityList();
      managingOpportunities.setManager(userId);
      managingOpportunities.setQueryOpenOnly(true);
      managingOpportunities.setControlledHierarchyOnly(Constants.FALSE);
      AccessTypeList accessTypeList = systemStatus.getAccessTypeList(db, AccessType.OPPORTUNITIES);
      managingOpportunities.setAccessType(accessTypeList.getCode(AccessType.PUBLIC));
      managingOpportunities.buildList(db);
      if (managingOpportunities.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      OpportunityList sourceOpportunities = new OpportunityList();
      sourceOpportunities.setOwner(userId);
      sourceOpportunities.buildList(db);
      if (sourceOpportunities.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      OpportunityList sourceOpenOpportunities = new OpportunityList();
      sourceOpenOpportunities.setOwner(userId);
      sourceOpenOpportunities.setQueryOpenOnly(true);
      sourceOpenOpportunities.buildList(db);
      if (sourceOpenOpportunities.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      DocumentStoreTeamMemberList sourceDocumentStoreTeamMemberList = new DocumentStoreTeamMemberList();
      sourceDocumentStoreTeamMemberList.setForDocumentStoreUser(userId);
      sourceDocumentStoreTeamMemberList.setMemberType(
          DocumentStoreTeamMemberList.USER);
      sourceDocumentStoreTeamMemberList.setUserLevel(
          DocumentStoreTeamMember.DOCUMENTSTORE_MANAGER);
      sourceDocumentStoreTeamMemberList.buildList(db);
      if (sourceDocumentStoreTeamMemberList.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      CallList sourcePendingActivities = new CallList();
      sourcePendingActivities.setOwner(userId);
      sourcePendingActivities.setOnlyPending(true);
      sourcePendingActivities.buildList(db);
      if (sourcePendingActivities.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }

      ActionLists sourceActionLists = new ActionLists();
      sourceActionLists.setOwner(userId);
      sourceActionLists.setInProgressOnly(true);
      sourceActionLists.buildList(db);
      if (sourceActionLists.size() > 0) {
        addError(systemStatus, object, "siteId", "object.validation.userHasAssociations.text");
      }
    }
    //UserGroup
    if (object.getClass().getName().equals("org.aspcfs.modules.admin.base.UserGroup"))
    {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
    }

    //TicketDefect
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketDefect"))
    {
      checkError(systemStatus, object, "title", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "title", 255);
      checkError(systemStatus, object, "startDate", INVALID_DATE);
      checkError(systemStatus, object, "endDate", INVALID_NOT_REQUIRED_DATE);
      TicketDefect defect = (TicketDefect) object;
      if (defect.getStartDate() != null && defect.getEndDate() != null &&
          defect.getStartDate().after(defect.getEndDate())) {
        addError(systemStatus, object, "endDate", "object.validation.project.estimatedEndDateNotLTStartDate");
      }
      if (defect.getSiteId() == Constants.INVALID_SITE) {
        addError(systemStatus, object, "siteId", REQUIRED_FIELD);
      }
    }

    //SyncClient
    if (object.getClass().getName().equals("org.aspcfs.modules.service.base.SyncClient"))
    {
      checkError(systemStatus, object, "type", REQUIRED_FIELD);
      checkError(systemStatus, object, "code", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "type", 100);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "code", 255);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "version", 50);
    }

    //KnowledgeBase
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.KnowledgeBase"))
    {
      checkError(systemStatus, object, "title", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "title", 255);
    }

    //TicketCategoryDraftAssignment
    if (object.getClass().getName().equals("org.aspcfs.modules.troubletickets.base.TicketCategoryDraftAssignment"))
    {
      TicketCategoryDraftAssignment assignment = (TicketCategoryDraftAssignment) object;
      if (assignment.getCategoryId() == -1) {
        addError(systemStatus, object, "categoryId", REQUIRED_FIELD);
      } else {
        TicketCategoryDraft draftCategory = new TicketCategoryDraft(db, assignment.getCategoryId(), "ticket_category");
        if (assignment.getAssignedTo() > -1) {
          User user = new User(db, assignment.getAssignedTo());
          if (draftCategory.getSiteId() != user.getSiteId() && user.getSiteId() != -1)
          {
            addError(systemStatus, object, "assignedTo", "tickets.ticketCategoryDraftAssignment.invalidUserAssignment.text");
          }
        }
        if (assignment.getUserGroupId() > -1) {
          UserGroup group = new UserGroup(db, assignment.getUserGroupId());
          if (draftCategory.getSiteId() != group.getSiteId() && group.getSiteId() != -1)
          {
            addError(systemStatus, object, "userGroupId", "tickets.ticketCategoryDraftAssignment.invalidUserGroupAssignment.text");
          }
        }
        if (assignment.getAssignedTo() == -1 && assignment.getUserGroupId() == -1 && assignment.getDepartmentId() == -1)
        {
          addError(systemStatus, object, "categoryId", "tickets.ticketCategoryDraftAssignment.invalidAssignment.text");
        }
      }
    }

    if (object.getClass().getName().equals("org.aspcfs.modules.website.base.Site"))
    {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "name", 300);
    }

    if (object.getClass().getName().equals("org.aspcfs.modules.website.base.Tab"))
    {
      checkError(systemStatus, object, "displayText", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "displayText", 300);
    }

    if (object.getClass().getName().equals("org.aspcfs.modules.website.base.PageGroup")) {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "name", 300);
    }

    if (object.getClass().getName().equals("org.aspcfs.modules.website.base.Page")) {
      checkError(systemStatus, object, "name", REQUIRED_FIELD);
      checkLength(systemStatus, object, "object.validation.exceedsLengthLimit", "name", 300);
    }

    // Invoke custom validators
    try {
      Map customValidators = systemStatus.getCustomValidators();
      Set keySet = customValidators.keySet();
      Iterator customValidatorIterator = keySet.iterator();
      while (customValidatorIterator.hasNext()) {
        String validator = (String) customValidatorIterator.next();
        Class customValidatorClass = Class.forName(validator);

        Class[] argTypes = new Class[]{Class.forName("org.aspcfs.controller.SystemStatus"), Class.forName("java.sql.Connection"), Class.forName("java.lang.Object")};
        Object[] params = new Object[]{systemStatus, db, object};

        Method method = customValidatorClass.getMethod("validate", argTypes);
        method.invoke(customValidatorClass, params);
      }
    } catch (Exception e) {
      //e.printStackTrace();
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param db           Description of the Parameter
   * @param object       Description of the Parameter
   * @param map          Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean validate(SystemStatus systemStatus, Connection db, Object object, HashMap map) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println(
          "ObjectValidator-> Checking object: " + object.getClass().getName());
    }
    //  TicketReplacementPart
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketReplacementPart")) {
      //TicketReplacementPart replacement = (TicketReplacementPart) object;
      String parseItem = (String) map.get("parseItem");
      String partNumber = (String) map.get("partNumber");
      String partDescription = (String) map.get("partDescription");
      if ((partNumber != null && !"".equals(partNumber)) &&
          (partDescription == null || "".equals(partDescription))) {
        addError(
            systemStatus, object, "partDescription" + parseItem, REQUIRED_FIELD);
        System.out.println("Adding Errror -->-0> partDescription is required");
      }
      if ((partDescription != null && !"".equals(partDescription)) &&
          (partNumber == null || "".equals(partNumber))) {
        addError(
            systemStatus, object, "partNumber" + parseItem, REQUIRED_FIELD);
        System.out.println("Adding Errror -->-0> partNumber is required");
      }
    }

    //TicketPerDayDescription
    if (object.getClass().getName().equals(
        "org.aspcfs.modules.troubletickets.base.TicketPerDayDescription")) {
      //TicketPerDayDescription thisDescription = (TicketPerDayDescription) object;
      String parseItem = (String) map.get("parseItem");
      String descriptionOfService = (String) map.get("descriptionOfService");
      if (descriptionOfService == null || "".equals(
          descriptionOfService.trim())) {
        addError(
            systemStatus, object, "descriptionOfService" + parseItem, REQUIRED_FIELD);
        addError(
            systemStatus, object, "action", "object.validation.genericActionError");
      }
      String activityDateTimeZone = (String) map.get("activityDateTimeZone");
      String activityDate = (String) map.get("activityDate");
      HttpServletRequest request = (HttpServletRequest) map.get("request");

      UserBean userBean = (UserBean) request.getSession().getAttribute("User");
      User user = userBean.getUserRecord();
      //String timeZone = user.getTimeZone();
      try {
        Timestamp tmp = DateUtils.getUserToServerDateTime(
            TimeZone.getTimeZone(activityDateTimeZone), DateFormat.SHORT, DateFormat.LONG, activityDate, user.getLocale());
        if (tmp == null) {
          addError(
              systemStatus, object, "activityDate" + parseItem, INVALID_DATE);
          addError(
              systemStatus, object, "action", "object.validation.genericActionError");
        }
      } catch (Exception e) {
      }
    }

    return true;
  }


  /**
   * Adds a feature to the Error attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Error attribute
   * @param object       The feature to be added to the Error attribute
   * @param field        The feature to be added to the Error attribute
   * @param errorType    The feature to be added to the Error attribute
   * @return Description of the Return Value
   */
  public static boolean checkError(SystemStatus systemStatus, Object object, String field, int errorType) {
    boolean returnValue = true;
    if (errorType == REQUIRED_FIELD) {
      String result = ObjectUtils.getParam(object, field);
      if (result == null || "".equals(result.trim())) {
        returnValue = false;
        addError(systemStatus, object, field, REQUIRED_FIELD);
      }
    } else if (errorType == INVALID_DATE) {
      try {
        String date = ObjectUtils.getParam(object, field);
        if (date == null || "".equals(date.trim())) {
          returnValue = false;
          addError(systemStatus, object, field, REQUIRED_FIELD);
        } else {
          try {
            date = new java.sql.Date(Timestamp.valueOf(date).getTime()).toString();
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            returnValue = false;
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
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
            localeFormatter.applyPattern("yyyy-MM-dd");
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            returnValue = false;
            addError(systemStatus, object, field, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            returnValue = false;
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        } else {
          returnValue = false;
          addError(systemStatus, object, field, INVALID_EMAIL);
        }
      } catch (Exception e) {
        returnValue = false;
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL_NOT_REQUIRED) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            returnValue = false;
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return returnValue;
  }


  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param object       Description of the Parameter
   * @param field        Description of the Parameter
   * @param errorName    Description of the Parameter
   * @param errorType    Description of the Parameter
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
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
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
            Locale locale = new Locale(
                System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
            SimpleDateFormat localeFormatter = new SimpleDateFormat(
                "yyyy-MM-dd", locale);
            localeFormatter.setLenient(false);
            localeFormatter.parse(date);
          } catch (java.text.ParseException e1) {
            addError(systemStatus, object, errorName, INVALID_DATE);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        } else {
          addError(systemStatus, object, field, INVALID_EMAIL);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if (errorType == INVALID_EMAIL_NOT_REQUIRED) {
      try {
        String email = ObjectUtils.getParam(object, field);
        if (email != null && !"".equals(email.trim())) {
          try {
            StringTokenizer str = new StringTokenizer(email, ",");
            if (str.hasMoreTokens()) {
              String temp = str.nextToken();
              InternetAddress inetAddress = new InternetAddress(
                  temp.trim(), true);
            } else {
              InternetAddress inetAddress = new InternetAddress(
                  email.trim(), true);
            }
          } catch (AddressException e1) {
            addError(systemStatus, object, field, INVALID_EMAIL);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Adds a feature to the Error attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Error attribute
   * @param field        The feature to be added to the Error attribute
   * @param errorType    The feature to be added to the Error attribute
   * @param object       The feature to be added to the Error attribute
   */
  public static void addError(SystemStatus systemStatus, Object object, String field, int errorType) {
    if (errorType == REQUIRED_FIELD) {
      addError(
          systemStatus, object, field, "object.validation.required");
    } else if (errorType == PAST_DATE) {
      addError(
          systemStatus, object, field, "object.validation.dateInThePast");
    } else if (errorType == INVALID_DATE || errorType == INVALID_NOT_REQUIRED_DATE) {
      addError(
          systemStatus, object, field, "object.validation.incorrectDateFormat");
    } else if (errorType == INVALID_NUMBER) {
      addError(
          systemStatus, object, field, "object.validation.incorrectNumberFormat");
    } else if (errorType == INVALID_EMAIL || errorType == INVALID_EMAIL_NOT_REQUIRED) {
      addError(
          systemStatus, object, field, "object.validation.invalidEmailAddress");
    }
  }


  /**
   * Adds a feature to the Error attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Error attribute
   * @param field        The feature to be added to the Error attribute
   * @param errorKey     The feature to be added to the Error attribute
   * @param object       The feature to be added to the Error attribute
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
   * Adds a feature to the Warning attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Warning attribute
   * @param object       The feature to be added to the Warning attribute
   * @param field        The feature to be added to the Warning attribute
   * @param warningType  The feature to be added to the Warning attribute
   */
  public static void checkWarning(SystemStatus systemStatus, Object object, String field, int warningType) {
    if (warningType == IS_BEFORE_TODAY) {
      Timestamp result = (Timestamp) ObjectUtils.getObject(object, field);
      if (result != null && result.before(new java.util.Date())) {
        addWarning(
            systemStatus, object, field, "object.validation.beforeToday");
      }
    }
  }


  /**
   * Adds a feature to the Warning attribute of the ObjectValidator class
   *
   * @param systemStatus The feature to be added to the Warning attribute
   * @param object       The feature to be added to the Warning attribute
   * @param field        The feature to be added to the Warning attribute
   * @param warningKey   The feature to be added to the Warning attribute
   */
  public static void addWarning(SystemStatus systemStatus, Object object, String field, String warningKey) {
    HashMap warnings = (HashMap) ObjectUtils.getObject(object, "warnings");
    if (systemStatus != null) {
      warnings.put(field + "Warning", systemStatus.getLabel(warningKey));
    } else {
      warnings.put(field + "Warning", "field warning");
    }
  }


  /**
   * Description of the Method
   *
   * @param systemStatus Description of the Parameter
   * @param object       Description of the Parameter
   * @param errorName    Description of the Parameter
   * @param fieldName    Description of the Parameter
   * @param length       Description of the Parameter
   */
  public static void checkLength(SystemStatus systemStatus, Object object, String errorName, String fieldName, int length) {
    String value = (String) ObjectUtils.getObject(object, fieldName);
    if (value != null && value.length() > length) {
      addError(systemStatus, object, fieldName, errorName);
    }
  }

}

