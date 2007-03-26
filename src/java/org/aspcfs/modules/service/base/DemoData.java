package org.aspcfs.modules.service.base;

import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.*;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationAddress;
import org.aspcfs.modules.accounts.base.OrganizationEmailAddress;
import org.aspcfs.modules.accounts.base.OrganizationPhoneNumber;
import org.aspcfs.modules.actionlist.base.ActionItem;
import org.aspcfs.modules.actionlist.base.ActionList;
import org.aspcfs.modules.admin.base.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.CustomField;
import org.aspcfs.modules.base.CustomFieldCategory;
import org.aspcfs.modules.base.CustomFieldGroup;
import org.aspcfs.modules.communications.base.*;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.troubletickets.base.Ticket;
import org.aspcfs.modules.troubletickets.base.TicketLog;
import org.aspcfs.modules.troubletickets.base.TicketTask;
import org.aspcfs.utils.formatter.AddressFormatter;
import org.aspcfs.utils.formatter.EmailAddressFormatter;
import org.aspcfs.utils.formatter.PhoneNumberFormatter;
import org.aspcfs.utils.web.LookupList;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Locale;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id: DemoData.java 14619 2006-03-29 17:20:58 -0500 (Wed, 29 Mar 2006) mrajkowski $
 * @created June 11, 2004
 */
public class DemoData {

  /**
   * Adds a feature to the CurrentTimestamp attribute of the DemoData class
   *
   * @param days The feature to be added to the CurrentTimestamp attribute
   * @return Description of the Return Value
   */
  public static Timestamp addCurrentTimestamp(int days) {
    if (days != 0) {
      return new Timestamp(System.currentTimeMillis() + (days * 1000 * 60 * 60 * 24));
    } else {
      return new Timestamp(System.currentTimeMillis());
    }
  }


  /**
   * Description of the Method
   *
   * @param value  Description of the Parameter
   * @param object Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public static void checkIfInserted(boolean value, Object object) throws SQLException {
    if (!value) {
      // Check errors
      if (true) {
        Iterator i = ((GenericBean) object).getErrors().keySet().iterator();
        while (i.hasNext()) {
          String warningKey = (String) i.next();
          String warningMsg = (String) ((GenericBean) object).getErrors().get(warningKey);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Object Validation Error-> " + warningKey + "=" + warningMsg);
          }
        }
      }
      // Check warnings
      if (true) {
        Iterator i = ((GenericBean) object).getWarnings().keySet().iterator();
        while (i.hasNext()) {
          String warningKey = (String) i.next();
          String warningMsg = (String) ((GenericBean) object).getWarnings().get(warningKey);
          if (System.getProperty("DEBUG") != null) {
            System.out.println("Object Validation Warning-> " + warningKey + "=" + warningMsg);
          }
        }
      }
      throw new SQLException("Record not inserted for: " + object.getClass().getName());
    }
  }


  /**
   * Description of the Method
   *
   * @param db          Description of the Parameter
   * @param demoAccount Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public static boolean install(Connection db, DemoAccount demoAccount) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("DemoData-> Installing data");
    }
    //Prepare the formatters
    EmailAddressFormatter emailFormatter = new EmailAddressFormatter();
    PhoneNumberFormatter phoneFormatter = new PhoneNumberFormatter();
    AddressFormatter addressFormatter = new AddressFormatter();
    Locale thisLocale = Locale.US;
    String timeZone = "America/New_York";

    // Rename my company
    Organization.renameMyCompany(db, demoAccount.getOrganization());

    // Add a demo role w/role permissions
    Role role = new Role();
    role.setRole("Demo User (Non-administrative)");
    role.setDescription("Non-administrative demo user");
    role.setRoleType(Constants.ROLETYPE_REGULAR);
    role.setEnteredBy(0);
    role.setModifiedBy(0);
    checkIfInserted(role.insert(db), role);
    //add, view, edit, delete
    role.addPermission(db, Permission.lookupId(db, "myhomepage"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-dashboard"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-inbox"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-tasks"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-profile"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-profile-personal"), false, true, true, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-profile-settings"), false, true, true, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-profile-password"), false, false, true, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-action-lists"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-action-plans"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "myhomepage-reassign"), false, true, true, false, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "sales"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "sales-dashboard"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "sales-leads"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "sales-import"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "sales-reports"), false, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "contacts"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts-reports"), true, true, false, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts-folders"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts-calls"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts-messages"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts-opportunities"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts-history"), true, true, true, true, false, false, false, false);
    //role.addPermission(db, Permission.lookupId(db, "contacts-external_contacts-imports"), true, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "pipeline"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "pipeline-opportunities"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "pipeline-dashboard"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "pipeline-reports"), true, true, false, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "pipeline-opportunities-calls"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "pipeline-opportunities-documents"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "pipeline-folders"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "pipeline-quotes"), true, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "accounts"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-folders"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-opportunities"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-calls"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-completed-calls"), false, false, true, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-messages"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-move"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-opportunities"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-tickets"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-tickets-tasks"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-tickets-folders"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-tickets-documents"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-documents"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-reports"), true, true, false, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-history"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-service-contracts"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-assets"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-tickets-maintenance-report"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-tickets-activity-log"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-relationships"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-projects"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "portal-user"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-quotes"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-action-plans"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-opportunities-quotes"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-folders"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contacts-opps-folders"), true, true, true, true, false, false, false, false);
    //role.addPermission(db, Permission.lookupId(db, "accounts-accounts-contact-updater"), false, true, false, false, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "quotes"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "quotes-quotes"), true, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "product-catalog"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "product-catalog-product"), true, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "campaign"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "campaign-dashboard"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "campaign-campaigns"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "campaign-campaigns-groups"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "campaign-campaigns-messages"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "campaign-campaigns-surveys"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "campaign-campaign-contact-updater"), false, true, false, false, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "projects"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "projects-personal"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "projects-enterprise"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "projects-projects"), true, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "tickets"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-tickets"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-reports"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-tickets-tasks"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-maintenance-report"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-activity-log"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-knowledge-base"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-defects"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "tickets-action-plans"), true, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "employees"), true, true, true, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-internal_contacts"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-internal_contacts-projects"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "contacts-internal_contacts-folders"), true, true, true, true, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "reports"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-users"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-roles"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-sysconfig"), false, true, true, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-usage"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-sysconfig-lists"), false, true, true, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-sysconfig-folders"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-object-workflow"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-sysconfig-categories"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-sysconfig-products"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-sysconfig-logos"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "admin-actionplans"), true, true, true, true, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "help"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "globalitems-search"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "globalitems-myitems"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "globalitems-recentitems"), false, true, false, false, false, false, false, false);
    //role.addPermission(db, Permission.lookupId(db, "contacts-external-contact-updater"), false, true, false, false, false, false, false, false);

    role.addPermission(db, Permission.lookupId(db, "documents"), false, true, false, false, false, false, false, false);
    role.addPermission(db, Permission.lookupId(db, "documents_documentstore"), true, true, true, true, false, false, false, false);

    // Load the employee access types
    AccessTypeList employeeAccessTypes = new AccessTypeList(db, AccessType.EMPLOYEES);
    AccessTypeList accountContactAccessTypes = new AccessTypeList(db, AccessType.ACCOUNT_CONTACTS);
    AccessTypeList messageAccessTypes = new AccessTypeList(db, AccessType.COMMUNICATION_MESSAGES);
    AccessTypeList contactAccessTypes = new AccessTypeList(db, AccessType.GENERAL_CONTACTS);
    AccessTypeList oppAccessTypes = new AccessTypeList(db, AccessType.OPPORTUNITIES);

    // Some lookup information
    //LookupList departmentList = new LookupList(db, "lookup_department");
    int departmentList_SALES = 12;
    int departmentList_ENGINEERING = 5;
    int departmentList_LEGAL = 8;
    //LookupList ticketPriorityList = new LookupList(db, "ticket_priority");
    int ticketPriorityList_URGENT = 3;
    int ticketPriorityList_AS_SCHEDULED = 2;
    int ticketPriorityList_CRITICAL = 4;
    //LookupList ticketSeverityList = new LookupList(db, "ticket_severity");
    int ticketSeverityList_NORMAL = 1;
    int ticketSeverityList_IMPORTANT = 2;
    int ticketSeverityList_CRITICAL = 3;
    //LookupList ticketSourceList = new LookupList(db, "lookup_ticketsource");
    int ticketSourceList_PHONE = 1;
    int ticketSourceList_EMAIL = 2;
    //TicketCategoryList ticketCategoryList = new TicketCategoryList();
    //ticketCategoryList.buildList(db);
    //int ticketCategoryList_TECHNICAL =
    //LookupList callTypeList = new LookupList(db, "lookup_call_types");
    int callTypeList_OUTGOING_CALL = 2;
    int callTypeList_OUTSIDE_APPT = 5;
    //CallResultList callResultList = new CallResultList();
    //callResultList.buildList(db);
    int callResultList_YES_BUS_PROGRESSING = 1;
    //LookupList callPriorityList = new LookupList(db, "lookup_call_priority");
    int callPriorityList_HIGH = 3;
    //LookupList callReminderList = new LookupList(db, "lookup_call_reminder");
    int callReminderList_HOURS = 2;
    //LookupList stageList = new LookupList(db, "lookup_stage");
    int stageList_PROSPECTING = 1;
    int stageList_PROPOSAL = 6;
    int stageList_NEGOTIATION = 7;
    int stageList_NEEDS_ANALYSIS = 3;
    int stageList_VALUE_PROP = 4;
    //LookupList industryList = new LookupList(db, "lookup_industry");
    int industryList_REAL_ESTATE = 18;
    int industryList_AUTOMOTIVE = 1;
    int industryList_CONSULTING = 5;
    int industryList_FINANCIAL_SERVICES = 9;
    int industryList_FOOD = 10;
    int industryList_COMPUTER = 4;
    int industryList_RETAIL = 19;
    //LookupList accountTypeList = new LookupList(db, "lookup_account_types");
    int accountTypeList_CONTRACT = 4;
    int accountTypeList_NON_CONTRACT = 5;
    int accountTypeList_TERRITORY1 = 6;
    int accountTypeList_TERRITORY3 = 8;
    int accountTypeList_TERRITORY5 = 9;
    int accountTypeList_LARGE = 3;
    LookupList contactTypeList = new LookupList(db, "lookup_contact_types");
    int contactTypeList_ACQUAINTANCE = 1;
    int contactTypeList_CUSTOMER = 3;
    int contactTypeList_FRIEND = 4;
    int contactTypeList_PROSPECT = 5;
    int contactTypeList_VENDOR = 7;
    int contactTypeList_EXECUTIVE = 13;
    int contactTypeList_ACCOUNTING = 8;
    int contactTypeList_SALES = 18;
    int contactTypeList_ADMINISTRATIVE = 9;
    int contactTypeList_BUSINESS_DEV = 10;
    //LookupList opportunityTypeList = new LookupList(db, "lookup_opportunity_types");
    int opportunityTypeList_MAINTENANCE = 4;
    int opportunityTypeList_SERVICES = 6;
    int opportunityTypeList_PRODUCT_SALES = 5;
    int opportunityTypeList_CONSULTATION = 2;
    int opportunityTypeList_DEVELOPMENT = 3;
    //LookupList projectRoleList = new LookupList(db, "lookup_project_role");
    int projectRoleList_LEAD = 1;
    int projectRoleList_CONTRIBUTOR = 2;
    int projectRoleList_OBSERVER = 3;

    // Add 4 new contacts (user contacts)
    int contact1Id = -1;
    int user1Id = -1;
    if (true) {
      // Primary demo contact
      Contact contact = new Contact();
      contact.setAccessType(employeeAccessTypes.getDefaultItem());
      contact.setEmployee(true);
      contact.setEnteredBy(0);
      contact.setModifiedBy(0);
      contact.setNameFirst(demoAccount.getNameFirst());
      contact.setNameLast(demoAccount.getNameLast());
      contact.setTitle("VP / Sales");
      contact.setDepartment(departmentList_SALES);
      contact.setOrgId(0);
      ContactPhoneNumber businessPhone = new ContactPhoneNumber();
      //businessPhone.setContactId(contactId);
      businessPhone.setType(1);
      businessPhone.setEnteredBy(0);
      businessPhone.setModifiedBy(0);
      businessPhone.setNumber(demoAccount.getPhone());
      businessPhone.setExtension(demoAccount.getExtension());
      if (businessPhone.isValid()) {
        phoneFormatter.format(businessPhone, thisLocale);
        contact.getPhoneNumberList().add(businessPhone);
      } else {
        System.err.println("Business1 phone is invalid: " + businessPhone.toString());
      }
      ContactEmailAddress businessEmail = new ContactEmailAddress();
      //businessEmail.setContactId(contactId);
      businessEmail.setType(1);
      businessEmail.setEnteredBy(0);
      businessEmail.setModifiedBy(0);
      businessEmail.setEmail(demoAccount.getEmail());
      if (businessEmail.isValid()) {
        emailFormatter.format(businessEmail);
        contact.getEmailAddressList().add(businessEmail);
      } else {
        System.err.println("Business1 email is invalid: " + businessEmail.toString());
      }
      checkIfInserted(contact.insert(db), contact);
      contact1Id = contact.getId();
      // Primary demo user
      User user = new User();
      user.setContact(contact);
      user.setUsername(demoAccount.getLoginName());
      user.setPassword1(demoAccount.getGeneratedPassword());
      user.setContactId(contact1Id);
      if (demoAccount.getRoleId() > -1) {
        user.setRoleId(demoAccount.getRoleId());
      } else {
        user.setRoleId(role.getId());
      }
      user.setExpires(addCurrentTimestamp(+5));
      checkIfInserted(user.insert(db), user);
      user1Id = user.getId();
    }
    if (true) {
      // Task
      Task task = new Task();
      task.setEnteredBy(user1Id);
      task.setModifiedBy(user1Id);
      task.setPriority(1);
      task.setDescription("Meeting prep for next week with Newco");
      task.setNotes("Use existing powerpoint");
      task.setSharing(-1);
      task.setOwner(user1Id);
      task.setDueDate(addCurrentTimestamp(0));
      task.setDueDateTimeZone(timeZone);
      task.setEstimatedLOE(1);
      //task.setEstimatedLOEType(1);
      task.setEstimatedLOEType(2);
      task.setType(1);
      //task.setType(Constants.TICKET_OBJECT);
      //task.setContactId(contact.getId());
      checkIfInserted(task.insert(db), task);
    }

    int contact2Id = -1;
    int user2Id = -1;
    if (true) {
      // extra contact to assign items to
      Contact contact = new Contact();
      contact.setAccessType(employeeAccessTypes.getDefaultItem());
      contact.setEmployee(true);
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setNameFirst("Matt");
      contact.setNameLast("Rajkowski");
      contact.setTitle("Chief Architect");
      contact.setOrgId(0);
      contact.setDepartment(departmentList_ENGINEERING);
      ContactAddress businessAddress = new ContactAddress();
      //businessAddress.setContactId(contactId);
      businessAddress.setType(1);
      businessAddress.setEnteredBy(user1Id);
      businessAddress.setModifiedBy(user1Id);
      businessAddress.setStreetAddressLine1("200 Main Street");
      businessAddress.setStreetAddressLine2("");
      businessAddress.setStreetAddressLine3("");
      businessAddress.setCity("Iowa City");
      businessAddress.setState("HI");
      businessAddress.setZip("");
      businessAddress.setCountry("UNITED STATES");
      if (businessAddress.isValid()) {
        addressFormatter.format(businessAddress);
        contact.getAddressList().add(businessAddress);
      } else {
        System.err.println("Business2 address is invalid: " + businessAddress.toString());
      }
      ContactPhoneNumber businessPhone = new ContactPhoneNumber();
      //businessPhone.setContactId(contactId);
      businessPhone.setType(1);
      businessPhone.setEnteredBy(user1Id);
      businessPhone.setModifiedBy(user1Id);
      businessPhone.setNumber("(800) 555-1212");
      businessPhone.setExtension("");
      if (businessPhone.isValid()) {
        phoneFormatter.format(businessPhone, thisLocale);
        contact.getPhoneNumberList().add(businessPhone);
      } else {
        System.err.println("Business2 phone is invalid: " + businessPhone.toString());
      }
      ContactEmailAddress businessEmail = new ContactEmailAddress();
      //businessEmail.setContactId(contactId);
      businessEmail.setType(1);
      businessEmail.setEnteredBy(user1Id);
      businessEmail.setModifiedBy(user1Id);
      businessEmail.setEmail("matt@company.com");
      if (businessEmail.isValid()) {
        emailFormatter.format(businessEmail);
        contact.getEmailAddressList().add(businessEmail);
      } else {
        System.err.println("Business2 email is invalid: " + businessEmail.toString());
      }
      checkIfInserted(contact.insert(db), contact);
      contact2Id = contact.getId();
      // Primary demo user
      User user = new User();
      user.setContact(contact);
      user.setUsername("matt-unused");
      user.setPassword1("--none--");
      user.setContactId(contact2Id);
      user.setRoleId(role.getId());
      user.setExpires(addCurrentTimestamp(+2));
      checkIfInserted(user.insert(db), user);
      user2Id = user.getId();
    }
    int contact3Id = -1;
    int user3Id = -1;
    if (true) {
      // extra contact to assign items to
      Contact contact = new Contact();
      contact.setAccessType(employeeAccessTypes.getDefaultItem());
      contact.setEmployee(true);
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setNameFirst("Tom");
      contact.setNameLast("Manos");
      contact.setTitle("CTO");
      contact.setDepartment(departmentList_ENGINEERING);
      contact.setOrgId(0);
      ContactAddress businessAddress = new ContactAddress();
      //businessAddress.setContactId(contactId);
      businessAddress.setType(1);
      businessAddress.setEnteredBy(user1Id);
      businessAddress.setModifiedBy(user1Id);
      businessAddress.setStreetAddressLine1("200 Main Street");
      businessAddress.setStreetAddressLine2("");
      businessAddress.setStreetAddressLine3("");
      businessAddress.setCity("Iowa City");
      businessAddress.setState("HI");
      businessAddress.setZip("");
      businessAddress.setCountry("UNITED STATES");
      if (businessAddress.isValid()) {
        addressFormatter.format(businessAddress);
        contact.getAddressList().add(businessAddress);
      } else {
        System.err.println("Business3 address is invalid: " + businessAddress.toString());
      }
      ContactPhoneNumber businessPhone = new ContactPhoneNumber();
      //businessPhone.setContactId(contactId);
      businessPhone.setType(1);
      businessPhone.setEnteredBy(user1Id);
      businessPhone.setModifiedBy(user1Id);
      businessPhone.setNumber("(800) 555-1212");
      businessPhone.setExtension("");
      if (businessPhone.isValid()) {
        phoneFormatter.format(businessPhone, thisLocale);
        contact.getPhoneNumberList().add(businessPhone);
      } else {
        System.err.println("Business3 phone is invalid: " + businessPhone.toString());
      }
      ContactEmailAddress businessEmail = new ContactEmailAddress();
      //businessEmail.setContactId(contactId);
      businessEmail.setType(1);
      businessEmail.setEnteredBy(user1Id);
      businessEmail.setModifiedBy(user1Id);
      businessEmail.setEmail("tom@company.com");
      if (businessEmail.isValid()) {
        emailFormatter.format(businessEmail);
        contact.getEmailAddressList().add(businessEmail);
      } else {
        System.err.println("Business3 email is invalid: " + businessEmail.toString());
      }
      checkIfInserted(contact.insert(db), contact);
      contact3Id = contact.getId();
      // Primary demo user
      User user = new User();
      user.setContact(contact);
      user.setUsername("tom-unused");
      user.setPassword1("--none--");
      user.setManagerId(user1Id);
      user.setContactId(contact3Id);
      user.setRoleId(role.getId());
      user.setExpires(addCurrentTimestamp(+2));
      checkIfInserted(user.insert(db), user);
      user3Id = user.getId();
    }
    int contact4Id = -1;
    int user4Id = -1;
    if (true) {
      // extra contact to assign items to
      Contact contact = new Contact();
      contact.setAccessType(employeeAccessTypes.getDefaultItem());
      contact.setEmployee(true);
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setNameFirst("Larry");
      contact.setNameLast("Stanford");
      contact.setTitle("Partner");
      contact.setDepartment(departmentList_LEGAL);
      contact.setOrgId(0);
      ContactPhoneNumber businessPhone = new ContactPhoneNumber();
      //businessPhone.setContactId(contactId);
      businessPhone.setType(1);
      businessPhone.setEnteredBy(user1Id);
      businessPhone.setModifiedBy(user1Id);
      businessPhone.setNumber("(334) 331-9898");
      businessPhone.setExtension("");
      if (businessPhone.isValid()) {
        phoneFormatter.format(businessPhone, thisLocale);
        contact.getPhoneNumberList().add(businessPhone);
      } else {
        System.err.println("Business4 phone is invalid: " + businessPhone.toString());
      }
      ContactEmailAddress businessEmail = new ContactEmailAddress();
      //businessEmail.setContactId(contactId);
      businessEmail.setType(1);
      businessEmail.setEnteredBy(user1Id);
      businessEmail.setModifiedBy(user1Id);
      businessEmail.setEmail("larry@stanfordco.com");
      if (businessEmail.isValid()) {
        emailFormatter.format(businessEmail);
        contact.getEmailAddressList().add(businessEmail);
      } else {
        System.err.println("Business4 email is invalid: " + businessEmail.toString());
      }
      checkIfInserted(contact.insert(db), contact);
      contact4Id = contact.getId();
      // Primary demo user
      User user = new User();
      user.setContact(contact);
      user.setUsername("larry-unused");
      user.setPassword1("--none--");
      user.setContactId(contact4Id);
      user.setRoleId(role.getId());
      user.setExpires(addCurrentTimestamp(+2));
      checkIfInserted(user.insert(db), user);
      user4Id = user.getId();
    }

    // Insert new folder
    if (true) {
      CustomFieldCategory category = new CustomFieldCategory();
      category.setModuleId(Constants.FOLDERS_CONTACTS);
      category.setName("Personal Background");
      category.setEnabled(true);
      category.setAllowMultipleRecords(false);
      category.insertCategory(db);

      // Insert a data group
      if (true) {
        CustomFieldGroup group = new CustomFieldGroup();
        group.setCategoryId(category.getId());
        group.setName("Education");
        group.setLevel(1);
        group.setEnabled(true);
        group.insertGroup(db);
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Highest level attained");
          field.setType(CustomField.SELECT);
          field.setRequired(false);
          field.setLookupListText(
              "K-12" +
                  "\r\nJunior College" +
                  "\r\nCollege" +
                  "\r\nPost Graduate - Masters" +
                  "\r\nPost Graduate - Doctorate" +
                  ""
          );
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Institutions Attended");
          field.setType(CustomField.TEXT);
          field.setMaxLength("250");
          field.setRequired(false);
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Degree");
          field.setType(CustomField.SELECT);
          field.setRequired(false);
          field.setLookupListText(
              "AB" +
                  "\r\nBA" +
                  "\r\nBS" +
                  "\r\nMBA" +
                  "\r\nJD" +
                  ""
          );
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Grade Point");
          field.setType(CustomField.TEXT);
          field.setMaxLength("4");
          field.setRequired(false);
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Field of Study");
          field.setType(CustomField.TEXT);
          field.setMaxLength("50");
          field.setRequired(false);
          field.insertField(db);
        }
      }

      // Insert a data group
      if (true) {
        CustomFieldGroup group = new CustomFieldGroup();
        group.setCategoryId(category.getId());
        group.setName("Personal");
        group.setLevel(1);
        group.setEnabled(true);
        group.insertGroup(db);
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Sex");
          field.setType(CustomField.SELECT);
          field.setRequired(false);
          field.setLookupListText(
              "Female" +
                  "\r\nMale" +
                  ""
          );
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Age");
          field.setType(CustomField.INTEGER);
          field.setMaxLength("3");
          field.setRequired(false);
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Marital Status");
          field.setType(CustomField.SELECT);
          field.setRequired(false);
          field.setLookupListText(
              "Single" +
                  "\r\nMarried" +
                  "\r\nDivorced" +
                  ""
          );
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Hobbies");
          field.setType(CustomField.TEXT);
          field.setMaxLength("250");
          field.setRequired(false);
          field.insertField(db);
        }
        // add a field
        if (true) {
          CustomField field = new CustomField();
          field.setGroupId(group.getId());
          field.setName("Member of...");
          field.setType(CustomField.SELECT);
          field.setRequired(false);
          field.setLookupListText(
              "American Society of Pediatrics" +
                  "\r\n" + "American Society of Oncolologists" +
                  "\r\n" + "American Medical Association" +
                  "\r\n" + "American Juvenile Diabetes Federation" +
                  ""
          );
          field.insertField(db);
        }
      }
    }

    // Action Lists
    ActionList actionList1 = new ActionList();
    actionList1.setDescription("Weekly call list for early in week");
    actionList1.setOwner(user1Id);
    actionList1.setLinkModuleId(Constants.ACTIONLISTS_CONTACTS);
    actionList1.setEnteredBy(user1Id);
    actionList1.setModifiedBy(user1Id);
    actionList1.setEnabled(true);
    checkIfInserted(actionList1.insert(db), actionList1);

    ActionList actionList2 = new ActionList();
    actionList2.setDescription("Weekly call list for late in week");
    actionList2.setOwner(user1Id);
    actionList2.setLinkModuleId(Constants.ACTIONLISTS_CONTACTS);
    actionList2.setEnteredBy(user1Id);
    actionList2.setModifiedBy(user1Id);
    actionList2.setEnabled(true);
    checkIfInserted(actionList2.insert(db), actionList2);

    // Generic messages
    int message1Id = -1;
    if (true) {
      Message message = new Message();
      message.setName("Large Account Newsletter Update - 1 January");
      message.setDescription("January newsletter to biggest customers");
      message.setReplyTo("boballen@fictitiousco.com");
      message.setMessageSubject("January happenings at Shoe World");
      message.setMessageText(
          "<img src=\"images/demo/master_logo_sdcwinter.gif\" width=\"300\" height=\"65\" alt=\"Shoes.com\" border=\"0\" />" +
              "<br /><br />Dear ${firstname},<br /><br />    Below are a few of the new things we thought " +
              "you might want to know about as a member of our Gold Shoe Club.<br /><br /><ul><li>New " +
              "store ours on Friday:  Now from 8:00 AM to 7 PM</li><li>The cocktail bar will even " +
              "be open on Sundays</li><li>Our new price list will be out on the 12th</li><li>Sign-up " +
              "for pre-ordering can be done by clicking on the survey link below</li></ul>Sincerely,<br />" +
              "<br />Frank Jones<br />VP/Key Accounts<br />"
      );
      message.setEnteredBy(user1Id);
      message.setModifiedBy(user1Id);
      message.setAccessType(messageAccessTypes.getDefaultItem());
      checkIfInserted(message.insert(db), message);
      message1Id = message.getId();
    }
    // Create campign groups
    int group1Id = -1;
    SearchCriteriaList campaignGroup = new SearchCriteriaList();
    campaignGroup.setGroupName("\"Executives\" in Accounts in Zipcode 23510 and 06006");
    campaignGroup.setContactSource(2);
    campaignGroup.setEnteredBy(user1Id);
    campaignGroup.setModifiedBy(user1Id);
    campaignGroup.setOwner(user1Id);
    checkIfInserted(campaignGroup.insert(db), campaignGroup);
    group1Id = campaignGroup.getId();
    if (true) {
      SearchCriteriaElement element = new SearchCriteriaElement();
      //element.setListId(campaignGroup.getId());
      element.setFieldId(8);
      element.setOperator("=");
      element.setOperatorId(1);
      element.setText(contactTypeList.getValueFromId(contactTypeList_EXECUTIVE));
      element.setSourceId(3);
      checkIfInserted(element.insert(campaignGroup.getId(), db), element);
    }
    if (true) {
      SearchCriteriaElement element = new SearchCriteriaElement();
      //element.setListId(campaignGroup.getId());
      element.setFieldId(5);
      element.setOperator("=");
      element.setOperatorId(1);
      element.setText("23510");
      element.setSourceId(3);
      checkIfInserted(element.insert(campaignGroup.getId(), db), element);
    }
    if (true) {
      SearchCriteriaElement element = new SearchCriteriaElement();
      //element.setListId(campaignGroup.getId());
      element.setFieldId(5);
      element.setOperator("=");
      element.setOperatorId(1);
      element.setText("06006");
      element.setSourceId(3);
      checkIfInserted(element.insert(campaignGroup.getId(), db), element);
    }

    // Survey with 1 question
    Survey survey = new Survey();
    survey.setEnteredBy(user1Id);
    survey.setModifiedBy(user1Id);
    survey.setStatus(Survey.COMPLETE);
    survey.setName("Gold Shoe Club - Pre-Season Survey");
    survey.setDescription("Sent to large accounts for them to do a pre-season order");
    survey.setIntro("Thank you for giving us your \"Pre-Season\" order.  As you know, this order entitles you to a 10% discount because it allows us to better manage our production output over the coming year.<br><br>Just click next to any of the packages your interested in and a key account rep will call you back in the next few days and additional marketing information will be automatically e-mailed to you.");
    survey.setOutro("Thanks for giving us your \"Pre-Season\" order and your key account rep will be back to your shortly");
    survey.setType(1);
    checkIfInserted(survey.insert(db), survey);
    // now append question
    SurveyQuestion question = new SurveyQuestion();
    question.setSurveyId(survey.getId());
    question.setType(SurveyQuestion.ITEMLIST);
    question.setDescription("Put a check box next to any of the packages you're interested in");
    question.setPosition(1);
    survey.getQuestions().add(question);
    ItemList itemList = new ItemList();
    question.setItemList(itemList);
    if (true) {
      Item item = new Item();
      item.setDescription("Package 1:  10,000 Units - Mixed Allotment");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 2: 50,000 Units - Mixed Allotment");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 3: 100,000 Units - Mixed Allotment");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 4: Bergdorf Line - Mixed");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 5:  Bergdorf Line - Premium");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 6: Charlie Brown Line - Mixed");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 7: Charlie Brown Line - Premium");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 8: Nike Line - Mixed");
      itemList.add(item);
    }
    if (true) {
      Item item = new Item();
      item.setDescription("Package 9: Nike Line - Sports");
      itemList.add(item);
    }
    checkIfInserted(question.insert(db, survey.getId()), question);

    Campaign campaign = new Campaign();
    campaign.setName("Gold Shoe Club Pre-Order Survey to Executives in Zips-23510/06006");
    campaign.setDescription("Sent to all executives in target zipcodes 23510 and 06006 who are in the gold club to get a feel for this year's \"pre-order\"");
    campaign.setMessageId(message1Id);
    campaign.setReplyTo("boballen@fictitiousco.com");
    campaign.setSubject("This month's happenings at Shoe World");
    campaign.setMessage("<img src=\"images/demo/master_logo_sdcwinter.gif\" width=\"300\" height=\"65\" alt=\"Shoes.com\" border=\"0\" /><br /><br />Dear ${firstname},<br /><br />    Below are a few of the new things we thought you might want to know about as a member of our Gold Shoe Club.<br /><br /><ul><li>New store ours on Friday:  Now from 8:00 AM to 7 PM</li><li>The cocktail bar will even be open on Sundays</li><li>Our new price list will be out on the 12'th</li><li>Sign-up for pre-ordering can be done by clicking on the survey link below</li></ul>Sincerely,<br /><br />Frank Jones<br />VP/Key Accounts<br /><br><br>You can take the survey at the following web-site: <a href=\"ProcessSurvey.do?id=${surveyId=1}\">http://demo.centriccrm.com/ProcessSurvey.do?id=${surveyId=1}</a>");
    campaign.setStatusId(Campaign.FINISHED);
    campaign.setStatus("Messages Sent");
    campaign.setActive(true);
    campaign.setActiveDate(addCurrentTimestamp(-2));
    campaign.setActiveDateTimeZone(timeZone);
    campaign.setSendMethodId(1);
    campaign.setEnteredBy(user1Id);
    campaign.setModifiedBy(user1Id);
    campaign.setType(Campaign.GENERAL);
    checkIfInserted(campaign.insert(db), campaign);

    // When a campaign has been executed, a run is created
    CampaignRun campaignRun = new CampaignRun();
    campaignRun.setCampaignId(campaign.getId());
    campaignRun.setStatus(0);
    campaignRun.setTotalContacts(2);
    campaignRun.setTotalSent(2);
    campaignRun.setTotalReplied(2);
    campaignRun.setRunDate(addCurrentTimestamp(-3));
    checkIfInserted(campaignRun.insert(db), campaignRun);

    // There should now be a complete survey, which can be used to create an active survey
    ActiveSurvey activeSurvey = new ActiveSurvey(survey);
    activeSurvey.setCampaignId(campaign.getId());
    activeSurvey.setEnteredBy(user1Id);
    activeSurvey.setModifiedBy(user1Id);
    checkIfInserted(activeSurvey.insert(db), activeSurvey);
    ActiveSurveyQuestion activeQuestion = (ActiveSurveyQuestion) activeSurvey.getQuestions().get(0);

    // Lock in the campaign groups
    SearchCriteriaListList thisList = new SearchCriteriaListList();
    thisList.setBuildCriteria(true);
    thisList.buildList(db);
    campaign.lockGroupCriteria(thisList, db);

    // Organizations
    if (true) {
      Organization organization = new Organization();
      organization.setName("Southside Realty");
      organization.setOwner(user2Id);
      organization.setIndustry(industryList_REAL_ESTATE);
      organization.setEnteredBy(user2Id);
      organization.setModifiedBy(user2Id);
      //Main Phone
      OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
      mainPhone.setOrgId(organization.getOrgId());
      mainPhone.setType(1);
      mainPhone.setEnteredBy(user1Id);
      mainPhone.setModifiedBy(user1Id);
      mainPhone.setNumber("(777) 888-9898");
      if (mainPhone.isValid()) {
        phoneFormatter.format(mainPhone, thisLocale);
        organization.getPhoneNumberList().add(mainPhone);
      }
      checkIfInserted(organization.insert(db), organization);
      organization.insertType(db, accountTypeList_NON_CONTRACT, 1);
      organization.insertType(db, accountTypeList_TERRITORY3, 2);
    }

    // ADD ACCOUNT #1
    if (true) {
      Organization organization = new Organization();
      organization.setName("AAA Automotive Repair");
      organization.setOwner(user4Id);
      organization.setIndustry(industryList_AUTOMOTIVE);
      organization.setAccountNumber("10001");
      organization.setAlertText("Contract renewal alert");
      organization.setAlertDate(addCurrentTimestamp(7));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(14));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user4Id);
      organization.setModifiedBy(user4Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user4Id);
        mainPhone.setModifiedBy(user4Id);
        mainPhone.setNumber("(555) 555-1212");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Fax Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(2);
        mainPhone.setEnteredBy(user4Id);
        mainPhone.setModifiedBy(user4Id);
        mainPhone.setNumber("(555) 555-1213");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Primary address
        OrganizationAddress organizationAddress = new OrganizationAddress();
        //organizationAddress.setOrgId(thisOrganization.getOrgId());
        organizationAddress.setType(1);
        organizationAddress.setEnteredBy(user4Id);
        organizationAddress.setModifiedBy(user4Id);
        //OrganizationAddress Fields
        organizationAddress.setStreetAddressLine1("100 Main Street");
        organizationAddress.setStreetAddressLine2("");
        organizationAddress.setStreetAddressLine3("");
        organizationAddress.setCity("Ocean City");
        organizationAddress.setState("FL");
        organizationAddress.setZip("");
        organizationAddress.setCountry("UNITED STATES");
        if (organizationAddress.isValid()) {
          addressFormatter.format(organizationAddress);
          organization.getAddressList().add(organizationAddress);
        }
      }
      if (true) {
        // Org email
        OrganizationEmailAddress primaryEmail = new OrganizationEmailAddress();
        //primaryEmail.setOrgId(orgId);
        primaryEmail.setType(1);
        primaryEmail.setEnteredBy(user4Id);
        primaryEmail.setModifiedBy(user4Id);
        primaryEmail.setEmail("support@automotiveA1.com");
        if (primaryEmail.isValid()) {
          emailFormatter.format(primaryEmail);
          organization.getEmailAddressList().add(primaryEmail);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      organization.insertType(db, accountTypeList_LARGE, 1);
      organization.insertType(db, accountTypeList_NON_CONTRACT, 2);

      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user4Id);
        contact.setModifiedBy(user4Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Bobby");
        contact.setNameLast("Allison");
        contact.setTitle("President");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user4Id);
          businessPhone.setModifiedBy(user4Id);
          businessPhone.setNumber("(414) 667-0906");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(7);
          businessPhone.setEnteredBy(user4Id);
          businessPhone.setModifiedBy(user4Id);
          businessPhone.setNumber("(414) 556-2312");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user4Id);
        businessEmail.setModifiedBy(user4Id);
        businessEmail.setEmail("test3@anothercompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business4 email is invalid: " + businessEmail.toString());
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_EXECUTIVE, 1);
        // add some opportunities
        OpportunityHeader opportunity = new OpportunityHeader();
        opportunity.setDescription("2006 Contract for Services");
        opportunity.setContactLink(contact.getId());
        opportunity.setEnteredBy(user3Id);
        opportunity.setModifiedBy(user3Id);
        opportunity.setManager(user3Id);
        opportunity.setAccessType(oppAccessTypes.getDefaultItem());
        checkIfInserted(opportunity.insert(db), opportunity);
        if (true) {
          // add a component
          OpportunityComponent component = new OpportunityComponent();
          component.setHeaderId(opportunity.getId());
          component.setOwner(user3Id);
          component.setDescription("Annual Maintenance");
          component.setCloseProb("33");
          component.setCloseDate(addCurrentTimestamp(13));
          component.setCloseDateTimeZone(timeZone);
          component.setGuess("240000");
          component.setTerms(12);
          component.setUnits("M");
          component.setType("N");
          component.setStage(stageList_PROPOSAL);
          component.setStageDate(addCurrentTimestamp(-1));
          component.setAlertText("Check to see if service commenced");
          component.setAlertDate(addCurrentTimestamp(10));
          component.setAlertDateTimeZone(timeZone);
          component.setEnteredBy(user4Id);
          component.setModifiedBy(user4Id);
          checkIfInserted(component.insert(db), component);
          component.insertType(db, opportunityTypeList_MAINTENANCE, 1);
        }
        if (true) {
          // add a component
          OpportunityComponent component = new OpportunityComponent();
          component.setHeaderId(opportunity.getId());
          component.setOwner(user1Id);
          component.setDescription("One-time setup");
          //TODO: component.setTypes();
          component.setCloseProb("50");
          component.setCloseDate(addCurrentTimestamp(9));
          component.setCloseDateTimeZone(timeZone);
          component.setGuess("50000");
          component.setTerms(2);
          component.setUnits("M");
          component.setType("N");
          component.setStage(stageList_NEGOTIATION);
          component.setStageDate(addCurrentTimestamp(0));
          component.setAlertText("Send final contract revision");
          component.setAlertDate(addCurrentTimestamp(0));
          component.setAlertDateTimeZone(timeZone);
          component.setOnlyWarnings(true);
          component.setEnteredBy(user4Id);
          component.setModifiedBy(user4Id);
          checkIfInserted(component.insert(db), component);
          component.insertType(db, opportunityTypeList_SERVICES, 1);
        }
        //TODO: Add a message (message1Id)
        int parentCallId = -1;
        if (true) {
          Call call = new Call();
          call.setContactId(contact.getId());
          call.setOrgId(organization.getId());
          call.setCallTypeId(callTypeList_OUTGOING_CALL);
          call.setLength(0);
          call.setSubject("Left message w/Secretary to set up Lunch");
          call.setNotes("Want to talk about next years products and services early");
          call.setAlertDate(addCurrentTimestamp(8));
          call.setAlertDateTimeZone(timeZone);
          //call.setOnlyWarnings(true);
          call.setFollowupNotes("Sandy is her name.  If she doens't get back to me I'll call");
          call.setEntered(addCurrentTimestamp(0));
          call.setEnteredBy(user1Id);
          call.setModified(addCurrentTimestamp(0));
          call.setModifiedBy(user1Id);
          call.setAlertText("Follow up w/Secretary to set up Lunch");
          call.setAlertCallTypeId(callTypeList_OUTSIDE_APPT);
          call.setOwner(user1Id);
          call.setAssignDate(addCurrentTimestamp(0));
          call.setCompletedBy(user1Id);
          call.setCompleteDate(addCurrentTimestamp(0));
          call.setResultId(callResultList_YES_BUS_PROGRESSING);
          call.setPriorityId(callPriorityList_HIGH);
          call.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
          call.setReminderId(1);
          call.setReminderTypeId(callReminderList_HOURS);
          call.setAlertDateTimeZone(timeZone);
          checkIfInserted(call.insert(db), call);
          parentCallId = call.getId();
        }
        if (true) {
          Call call = new Call();
          call.setContactId(contact.getId());
          call.setOrgId(organization.getId());
          call.setCallTypeId(callTypeList_OUTGOING_CALL);
          call.setLength(0);
          call.setSubject("Spoke w/Secretary");
          call.setNotes("Lunch is on for the 12'th at Harry's Bar & Grill");
          call.setAlertDate(addCurrentTimestamp(8));
          call.setAlertDateTimeZone(timeZone);
          call.setFollowupNotes("- To meet just before noon");
          call.setEntered(addCurrentTimestamp(0));
          call.setEnteredBy(user1Id);
          call.setModified(addCurrentTimestamp(0));
          call.setModifiedBy(user1Id);
          call.setAlertText("Lunch w/the President");
          call.setAlertCallTypeId(callTypeList_OUTSIDE_APPT);
          call.setParentId(parentCallId);
          call.setOwner(user1Id);
          call.setAssignDate(addCurrentTimestamp(0));
          call.setCompletedBy(user1Id);
          call.setCompleteDate(addCurrentTimestamp(0));
          call.setResultId(callResultList_YES_BUS_PROGRESSING);
          call.setPriorityId(callPriorityList_HIGH);
          call.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
          call.setReminderId(3);
          call.setReminderTypeId(callReminderList_HOURS);
          checkIfInserted(call.insert(db), call);
        }
        // Service Contract
        /*
         *  if (true) {
         *  ServiceContract serviceContract = new ServiceContract();
         *  serviceContract.setContractNumber("2004-001");
         *  serviceContract.setOrgId(organization.getId());
         *  serviceContract.setInitialStartDate();
         *  serviceContract.setCurrentStartDate();
         *  serviceContract.setCurrentEndDate();
         *  serviceContract.setCategory();
         *  serviceContract.setType();
         *  serviceContract.setContactId(contact.getId());
         *  serviceContract.setDescription();
         *  serviceContract.setResponseTime();
         *  serviceContract.setTelephoneResponseModel();
         *  serviceContract.setOnsiteResponseModel();
         *  serviceContract.setEmailResponseModel();
         *  serviceContract.setEnteredBy(user1Id);
         *  serviceContract.setModifiedBy(user1Id);
         *  serviceContract.setContractValue(10000);
         *  serviceContract.setTotalHoursRemaining(100);
         *  checkIfInserted(serviceContract.insert(db), serviceContract);
         *  }
         */
      }

      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user4Id);
        contact.setModifiedBy(user4Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Colin");
        contact.setNameLast("Morgan");
        contact.setTitle("Operations Manager");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(414) 331-9898");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(7);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(414) 908-8909");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user4Id);
        businessEmail.setModifiedBy(user4Id);
        businessEmail.setEmail("test7@anothercompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business4 email is invalid: " + businessEmail.toString());
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_ACCOUNTING, 1);
      }
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user4Id);
        contact.setModifiedBy(user4Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Mary");
        contact.setNameLast("Smith");
        contact.setTitle("Administrative Assistant");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(414) 998-9999");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(7);
          businessPhone.setEnteredBy(user4Id);
          businessPhone.setModifiedBy(user4Id);
          businessPhone.setNumber("(414) 888-8888");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(4);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(414) 555-9999");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user4Id);
        businessEmail.setModifiedBy(user4Id);
        businessEmail.setEmail("test2@anothercompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business4 email is invalid: " + businessEmail.toString());
        }
        checkIfInserted(contact.insert(db), contact);
      }
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user4Id);
        contact.setModifiedBy(user4Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Willie");
        contact.setNameLast("Wilborn");
        contact.setTitle("SVP / Sales");
        contact.setOrgId(organization.getId());
        contact.setNotes("Decision maker");
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(414) 777-8765");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user4Id);
        businessEmail.setModifiedBy(user4Id);
        businessEmail.setEmail("test5@anothercompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business4 email is invalid: " + businessEmail.toString());
        }
        if (true) {
          ContactAddress businessAddress = new ContactAddress();
          //businessAddress.setContactId(contactId);
          businessAddress.setType(1);
          businessAddress.setEnteredBy(user1Id);
          businessAddress.setModifiedBy(user1Id);
          businessAddress.setStreetAddressLine1("220 W. Main Street");
          businessAddress.setStreetAddressLine2("");
          businessAddress.setStreetAddressLine3("");
          businessAddress.setCity("Hampton");
          businessAddress.setState("VA");
          businessAddress.setZip("23510");
          businessAddress.setCountry("UNITED STATES");
          if (businessAddress.isValid()) {
            addressFormatter.format(businessAddress);
            contact.getAddressList().add(businessAddress);
          } else {
            System.err.println("Business2 address is invalid: " + businessAddress.toString());
          }
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_EXECUTIVE, 1);
        //TODO: Add a message (message1Id)
        //Add a ticket
        if (true) {
          Ticket ticket = new Ticket();
          ticket.setSourceCode(ticketSourceList_PHONE);
          ticket.setProblem("Please take care of his laptop. It doesn't seem to work as well when there is full moon. He thinks it may have to do with the power in his office...or a prank");
          ticket.setComment("Can you give him a call back ASAP. He wasn't happy and he's going out of town tomorrow");
          //ticket.setCatCode(ticketCategoryList.getIdFromValue("Technical"));
          //ticket.setSubCat1(ticketCategoryList.getIdFromValue("Hardware"));
          ticket.setSeverityCode(ticketSeverityList_IMPORTANT);
          ticket.setOrgId(organization.getId());
          ticket.setContactId(contact.getId());
          ticket.setPriorityCode(ticketPriorityList_URGENT);
          ticket.setDepartmentCode(departmentList_ENGINEERING);
          ticket.setAssignedTo(user2Id);
          ticket.setAssignedDate(addCurrentTimestamp(-3));
          ticket.setEntered(addCurrentTimestamp(-3));
          ticket.setEnteredBy(user1Id);
          ticket.setModified(addCurrentTimestamp(-3));
          ticket.setModifiedBy(user1Id);
          checkIfInserted(ticket.insert(db), ticket);
        }
        //Add a ticket
        if (true) {
          Ticket ticket = new Ticket();
          ticket.setSourceCode(ticketSourceList_PHONE);
          ticket.setProblem("Need to coordinate with engineering the project to get started");
          //ticket.setCatCode(ticketCategoryList.getIdFromValue("Sales"));
          ticket.setSeverityCode(ticketSeverityList_NORMAL);
          ticket.setOrgId(organization.getId());
          ticket.setContactId(contact.getId());
          ticket.setPriorityCode(ticketPriorityList_AS_SCHEDULED);
          ticket.setDepartmentCode(departmentList_ENGINEERING);
          ticket.setAssignedTo(user3Id);
          ticket.setAssignedDate(addCurrentTimestamp(0));
          ticket.setEntered(addCurrentTimestamp(0));
          ticket.setEnteredBy(user1Id);
          ticket.setModified(addCurrentTimestamp(0));
          ticket.setModifiedBy(user1Id);
          checkIfInserted(ticket.insert(db), ticket);
        }
        // campaign recipient
        ScheduledRecipient recipient = new ScheduledRecipient();
        recipient.setCampaignId(campaign.getId());
        recipient.setContactId(contact.getId());
        recipient.setRunId(campaignRun.getId());
        recipient.setStatusId(1);
        recipient.setStatus("Email Sent");
        recipient.setStatusDate(addCurrentTimestamp(-1));
        recipient.setScheduledDate(addCurrentTimestamp(-1));
        recipient.setSentDate(addCurrentTimestamp(-1));
        checkIfInserted(recipient.insert(db), recipient);
        // answered campaign survey
        SurveyResponse response = new SurveyResponse();
        response.setActiveSurveyId(activeSurvey.getId());
        response.setContactId(contact.getId());
        response.setUniqueCode("-- not needed --");
        response.setIpAddress("127.0.0.1");
        checkIfInserted(response.insert(db), response);
        //answer, answer items, answer avg
        SurveyAnswer surveyAnswer = new SurveyAnswer();
        surveyAnswer.setResponseId(response.getId());
        surveyAnswer.setQuestionId(activeQuestion.getId());
        surveyAnswer.setComments("");
        checkIfInserted(surveyAnswer.insert(db, response.getId()), surveyAnswer);
        //SurveyAnswerItem answerItem = new SurveyAnswerItem();
        //answerItem.setId(1);
        //answerItem.set
      }
    }

    // ADD ACCOUNT #2
    if (true) {
      Organization organization = new Organization();
      organization.setName("Ad Department");
      organization.setOwner(user3Id);
      organization.setIndustry(industryList_CONSULTING);
      organization.setAlertText("New contact signing");
      organization.setAlertDate(addCurrentTimestamp(50));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(55));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user3Id);
      organization.setModifiedBy(user3Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user4Id);
        mainPhone.setModifiedBy(user4Id);
        mainPhone.setNumber("(757) 555-1812");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Primary address
        OrganizationAddress organizationAddress = new OrganizationAddress();
        //organizationAddress.setOrgId(thisOrganization.getOrgId());
        organizationAddress.setType(1);
        organizationAddress.setEnteredBy(user3Id);
        organizationAddress.setModifiedBy(user3Id);
        //OrganizationAddress Fields
        organizationAddress.setStreetAddressLine1("150 W. Rambleton Street");
        organizationAddress.setStreetAddressLine2("");
        organizationAddress.setStreetAddressLine3("");
        organizationAddress.setCity("Norwalk");
        organizationAddress.setState("CT");
        organizationAddress.setZip("06006");
        organizationAddress.setCountry("UNITED STATES");
        if (organizationAddress.isValid()) {
          addressFormatter.format(organizationAddress);
          organization.getAddressList().add(organizationAddress);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      // CONTACTS
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user1Id);
        contact.setModifiedBy(user1Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Ken");
        contact.setNameLast("Bees");
        contact.setTitle("Sales Rep");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(767) 889-4521");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(7);
          businessPhone.setEnteredBy(user4Id);
          businessPhone.setModifiedBy(user4Id);
          businessPhone.setNumber("(767) 990-9765");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user4Id);
        businessEmail.setModifiedBy(user4Id);
        businessEmail.setEmail("test8@andanothercompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business4 email is invalid: " + businessEmail.toString());
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_SALES, 1);
        //Add a ticket
        if (true) {
          Ticket ticket = new Ticket();
          ticket.setSourceCode(ticketSourceList_PHONE);
          ticket.setProblem("Would like some training on how to use the new Tork converter reciprocator");
          ticket.setComment("He needs help quickly or else his trip to a key account is worthless");
          //ticket.setCatCode(ticketCategoryList.getIdFromValue("Technical"));
          ticket.setSeverityCode(ticketSeverityList_NORMAL);
          ticket.setOrgId(organization.getId());
          ticket.setContactId(contact.getId());
          ticket.setPriorityCode(ticketPriorityList_CRITICAL);
          ticket.setDepartmentCode(departmentList_ENGINEERING);
          ticket.setAssignedTo(user2Id);
          ticket.setAssignedDate(addCurrentTimestamp(-1));
          ticket.setEntered(addCurrentTimestamp(-1));
          ticket.setEnteredBy(user1Id);
          ticket.setModified(addCurrentTimestamp(-1));
          ticket.setModifiedBy(user1Id);
          checkIfInserted(ticket.insert(db), ticket);
          // Ticket log entry
          if (true) {
            TicketLog log = new TicketLog();
            log.setTicketId(ticket.getId());
            log.setEntryText("His questions were too much for me, Tom, can you handle");
            log.setAssignedTo(user3Id);
            log.setEnteredBy(user2Id);
            log.setModifiedBy(user2Id);
            log.setPriorityCode(ticketPriorityList_CRITICAL);
            log.setDepartmentCode(departmentList_ENGINEERING);
            //log.setCatCode(ticketCategoryList.getIdFromValue("Technical"));
            log.setSeverityCode(ticketSeverityList_IMPORTANT);
            checkIfInserted(log.insert(db), log);
          }
          // Ticket log entry
          if (true) {
            TicketLog log = new TicketLog();
            log.setTicketId(ticket.getId());
            log.setEntryText("This contract needs to be resolved");
            log.setAssignedTo(user4Id);
            log.setEnteredBy(user3Id);
            log.setModifiedBy(user3Id);
            log.setPriorityCode(ticketPriorityList_CRITICAL);
            log.setDepartmentCode(departmentList_LEGAL);
            //log.setCatCode(ticketCategoryList.getIdFromValue("Other"));
            log.setSeverityCode(ticketSeverityList_IMPORTANT);
            checkIfInserted(log.insert(db), log);
          }
          if (false) {
            // NOTE: removed until this can be verified and fixed
            TicketTask task = new TicketTask();
            task.setEnteredBy(user3Id);
            task.setModifiedBy(user3Id);
            task.setPriority(1);
            task.setDescription("Send reply note to CSR for final response notice");
            task.setNotes("Make sure this is done with the input of our marketing department because this is a big account.  Probably he guy to speak with is Drew Jackson");
            task.setSharing(-1);
            task.setOwner(user3Id);
            task.setDueDate(addCurrentTimestamp(-1));
            task.setDueDateTimeZone(timeZone);
            task.setEstimatedLOE(1);
            //task.setEstimatedLOEType(1);
            task.setEstimatedLOEType(2);
            //task.setType(1);
            task.setType(Constants.TICKET_OBJECT);
            task.setTicketId(ticket.getId());
            task.setContactId(contact.getId());
            checkIfInserted(task.insert(db), task);
          }
          if (false) {
            // NOTE: removed until this can be verified and fixed
            TicketTask task = new TicketTask();
            task.setEnteredBy(user1Id);
            task.setModifiedBy(user1Id);
            task.setPriority(1);
            task.setDescription("Call to setup a time");
            task.setNotes("Standard presentation");
            task.setSharing(-1);
            task.setOwner(user1Id);
            task.setDueDate(addCurrentTimestamp(0));
            task.setDueDateTimeZone(timeZone);
            task.setEstimatedLOE(30);
            //task.setEstimatedLOEType(1);
            task.setEstimatedLOEType(3);
            //task.setType(1);
            task.setType(Constants.TICKET_OBJECT);
            task.setTicketId(ticket.getId());
            task.setContactId(contact.getId());
            checkIfInserted(task.insert(db), task);
          }
          if (false) {
            // NOTE: removed until this can be verified and fixed
            TicketTask task = new TicketTask();
            task.setEnteredBy(user1Id);
            task.setModifiedBy(user1Id);
            task.setPriority(1);
            task.setDescription("Send survey to collateral stakeholders in the process");
            task.setNotes("The template customer service survey should suffice");
            task.setSharing(-1);
            task.setOwner(user2Id);
            task.setDueDate(addCurrentTimestamp(0));
            task.setDueDateTimeZone(timeZone);
            task.setEstimatedLOE(-1);
            //task.setEstimatedLOEType(1);
            task.setEstimatedLOEType(3);
            //task.setType(1);
            task.setType(Constants.TICKET_OBJECT);
            task.setTicketId(ticket.getId());
            task.setContactId(contact.getId());
            checkIfInserted(task.insert(db), task);
          }
          if (false) {
            // NOTE: removed until this can be verified and fixed
            TicketTask task = new TicketTask();
            task.setEnteredBy(user1Id);
            task.setModifiedBy(user1Id);
            task.setPriority(1);
            task.setDescription("Double check to see if the survey numbers parallel last time");
            task.setNotes("Last time there was a problem with Stock form #12a.  Shouldn't have happened this time because Brantley changed it");
            task.setSharing(-1);
            task.setOwner(user1Id);
            task.setDueDate(addCurrentTimestamp(-1));
            task.setDueDateTimeZone(timeZone);
            task.setEstimatedLOE(1);
            task.setEstimatedLOEType(2);
            task.setType(Constants.TICKET_OBJECT);
            task.setTicketId(ticket.getId());
            task.setContactId(contact.getId());
            checkIfInserted(task.insert(db), task);
          }
        }
      }
      //CONTACT
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user1Id);
        contact.setModifiedBy(user1Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Kimberly");
        contact.setNameLast("Clarkson");
        contact.setTitle("Account Manager");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(767) 888-9999");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user4Id);
        businessEmail.setModifiedBy(user4Id);
        businessEmail.setEmail("test4@andanothercompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business4 email is invalid: " + businessEmail.toString());
        }
        if (true) {
          ContactAddress businessAddress = new ContactAddress();
          //businessAddress.setContactId(contactId);
          businessAddress.setType(1);
          businessAddress.setEnteredBy(user1Id);
          businessAddress.setModifiedBy(user1Id);
          businessAddress.setStreetAddressLine1("180 Richmond Street");
          businessAddress.setStreetAddressLine2("");
          businessAddress.setStreetAddressLine3("");
          businessAddress.setCity("Hampton");
          businessAddress.setState("VA");
          businessAddress.setZip("23510");
          businessAddress.setCountry("UNITED STATES");
          if (businessAddress.isValid()) {
            addressFormatter.format(businessAddress);
            contact.getAddressList().add(businessAddress);
          } else {
            System.err.println("Business2 address is invalid: " + businessAddress.toString());
          }
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_EXECUTIVE, 1);
        // Opportunity
        OpportunityHeader opportunity = new OpportunityHeader();
        opportunity.setDescription("Software training for Kim and her team");
        opportunity.setContactLink(contact.getId());
        opportunity.setEnteredBy(user1Id);
        opportunity.setModifiedBy(user1Id);
        opportunity.setManager(user1Id);
        opportunity.setAccessType(oppAccessTypes.getDefaultItem());
        checkIfInserted(opportunity.insert(db), opportunity);
        if (true) {
          // add a component
          OpportunityComponent component = new OpportunityComponent();
          component.setHeaderId(opportunity.getId());
          component.setOwner(user1Id);
          component.setDescription("One-time training on CRM for Ad team");
          //TODO: component.setTypes();
          component.setCloseProb("80");
          component.setCloseDate(addCurrentTimestamp(39));
          component.setCloseDateTimeZone(timeZone);
          component.setGuess("25000");
          component.setTerms(2);
          component.setUnits("M");
          component.setType("N");
          component.setStage(stageList_NEGOTIATION);
          component.setStageDate(addCurrentTimestamp(0));
          component.setAlertText("Send Kim flowers for her Birthday");
          component.setAlertDate(addCurrentTimestamp(0));
          component.setAlertDateTimeZone(timeZone);
          component.setOnlyWarnings(true);
          component.setEnteredBy(user1Id);
          component.setModifiedBy(user1Id);
          checkIfInserted(component.insert(db), component);
          component.insertType(db, opportunityTypeList_SERVICES, 1);
        }
        // campaign recipient
        ScheduledRecipient recipient = new ScheduledRecipient();
        recipient.setCampaignId(campaign.getId());
        recipient.setContactId(contact.getId());
        recipient.setRunId(campaignRun.getId());
        recipient.setStatusId(1);
        recipient.setStatus("Email Sent");
        recipient.setStatusDate(addCurrentTimestamp(-1));
        recipient.setScheduledDate(addCurrentTimestamp(-1));
        recipient.setSentDate(addCurrentTimestamp(-1));
        checkIfInserted(recipient.insert(db), recipient);
        // answered campaign survey
        SurveyResponse response = new SurveyResponse();
        response.setActiveSurveyId(activeSurvey.getId());
        response.setContactId(contact.getId());
        response.setUniqueCode("-- not needed --");
        response.setIpAddress("127.0.0.1");
        checkIfInserted(response.insert(db), response);
        //answer, answer items, answer avg
        SurveyAnswer surveyAnswer = new SurveyAnswer();
        surveyAnswer.setResponseId(response.getId());
        surveyAnswer.setQuestionId(activeQuestion.getId());
        surveyAnswer.setComments("");
        checkIfInserted(surveyAnswer.insert(db, response.getId()), surveyAnswer);
        //SurveyAnswerItem answerItem = new SurveyAnswerItem();
        //answerItem.setId(1);
        //answerItem.set

        // Task
        Task task = new Task();
        task.setEnteredBy(user1Id);
        task.setModifiedBy(user1Id);
        task.setPriority(1);
        task.setDescription("Call Carter to set up meeting");
        task.setNotes("");
        task.setSharing(-1);
        task.setOwner(user1Id);
        task.setDueDate(addCurrentTimestamp(-10));
        task.setDueDateTimeZone(timeZone);
        task.setEstimatedLOE(-1);
        //task.setEstimatedLOEType(1);
        task.setEstimatedLOEType(2);
        task.setType(1);
        //task.setType(Constants.TICKET_OBJECT);
        task.setContactId(contact.getId());
        checkIfInserted(task.insert(db), task);
      }

      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user1Id);
        contact.setModifiedBy(user1Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Barbara");
        contact.setNameLast("Roxy");
        contact.setTitle("Administrative Assistant");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(767) 777-8787");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user4Id);
        businessEmail.setModifiedBy(user4Id);
        businessEmail.setEmail("test6@andanothercompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business4 email is invalid: " + businessEmail.toString());
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_ADMINISTRATIVE, 1);
      }
    }

    // ADD ACCOUNT #3
    if (true) {
      Organization organization = new Organization();
      organization.setName("Bank of the Commonwealth");
      organization.setOwner(user4Id);
      organization.setAccountNumber("10022");
      organization.setIndustry(industryList_FINANCIAL_SERVICES);
      organization.setAlertText("Contract renewal negotiation");
      organization.setAlertDate(addCurrentTimestamp(13));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(20));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user4Id);
      organization.setModifiedBy(user4Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user4Id);
        mainPhone.setModifiedBy(user4Id);
        mainPhone.setNumber("(999) 888-7878");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Fax Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(2);
        mainPhone.setEnteredBy(user4Id);
        mainPhone.setModifiedBy(user4Id);
        mainPhone.setNumber("(999) 878-4490");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user1Id);
        contact.setModifiedBy(user1Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Frank");
        contact.setNameLast("George");
        contact.setTitle("Customer Service");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(800) 443-2121");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactAddress businessAddress = new ContactAddress();
          //businessAddress.setContactId(contactId);
          businessAddress.setType(1);
          businessAddress.setEnteredBy(user1Id);
          businessAddress.setModifiedBy(user1Id);
          businessAddress.setStreetAddressLine1("220 W. Main Street");
          businessAddress.setStreetAddressLine2("");
          businessAddress.setStreetAddressLine3("");
          businessAddress.setCity("Iowa City");
          businessAddress.setState("HI");
          businessAddress.setZip("");
          businessAddress.setCountry("UNITED STATES");
          if (businessAddress.isValid()) {
            addressFormatter.format(businessAddress);
            contact.getAddressList().add(businessAddress);
          } else {
            System.err.println("Business2 address is invalid: " + businessAddress.toString());
          }
        }
        checkIfInserted(contact.insert(db), contact);
        // Add an action item
        ActionItem actionItem = new ActionItem();
        actionItem.setActionId(actionList2.getId());
        actionItem.setLinkItemId(contact.getId());
        actionItem.setEnteredBy(user1Id);
        actionItem.setModifiedBy(user1Id);
        actionItem.setEnabled(true);
        checkIfInserted(actionItem.insert(db), actionItem);
      }
      // Add account opportunities
      OpportunityHeader opportunity = new OpportunityHeader();
      opportunity.setDescription("2000 Main Street Location");
      opportunity.setAccountLink(organization.getId());
      opportunity.setEnteredBy(user3Id);
      opportunity.setModifiedBy(user3Id);
      opportunity.setManager(user3Id);
      opportunity.setAccessType(oppAccessTypes.getDefaultItem());
      checkIfInserted(opportunity.insert(db), opportunity);
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user3Id);
        component.setDescription("Residential House Sale Recon");
        //TODO: component.setTypes();
        component.setCloseProb("85");
        component.setCloseDate(addCurrentTimestamp(0));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("150000");
        component.setTerms(6);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_NEGOTIATION);
        component.setStageDate(addCurrentTimestamp(0));
        component.setAlertText("Call back to confirm close");
        component.setAlertDate(addCurrentTimestamp(0));
        component.setAlertDateTimeZone(timeZone);
        component.setOnlyWarnings(true);
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
      }
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("One-time renovation");
        //TODO: component.setTypes();
        component.setCloseProb("40");
        component.setCloseDate(addCurrentTimestamp(-2));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("60000");
        component.setTerms(3);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_PROPOSAL);
        component.setStageDate(addCurrentTimestamp(-1));
        //component.setAlertText("Send final contract revision");
        //component.setAlertDate(addCurrentTimestamp(0));
        //component.setAlertDateTimeZone(timeZone);
        //component.setOnlyWarnings(true);
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_SERVICES, 1);
      }
    }

    // ADD ACCOUNT #4
    if (true) {
      Organization organization = new Organization();
      organization.setName("Freemason Restaurant");
      organization.setOwner(user1Id);
      organization.setAccountNumber("10017");
      organization.setIndustry(industryList_FOOD);
      organization.setAlertText("Contract renewal reminder");
      organization.setAlertDate(addCurrentTimestamp(4));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(24));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user1Id);
      organization.setModifiedBy(user1Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(777) 777-7777");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Fax Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(2);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(777) 777-7778");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Primary address
        OrganizationAddress organizationAddress = new OrganizationAddress();
        //organizationAddress.setOrgId(thisOrganization.getOrgId());
        organizationAddress.setType(1);
        organizationAddress.setEnteredBy(user1Id);
        organizationAddress.setModifiedBy(user1Id);
        //OrganizationAddress Fields
        organizationAddress.setStreetAddressLine1("46 Liken Lane");
        organizationAddress.setStreetAddressLine2("");
        organizationAddress.setStreetAddressLine3("");
        organizationAddress.setCity("Springfield");
        organizationAddress.setState("VT");
        organizationAddress.setZip("");
        organizationAddress.setCountry("UNITED STATES");
        if (organizationAddress.isValid()) {
          addressFormatter.format(organizationAddress);
          organization.getAddressList().add(organizationAddress);
        }
      }
      if (true) {
        // Org email
        OrganizationEmailAddress primaryEmail = new OrganizationEmailAddress();
        //primaryEmail.setOrgId(orgId);
        primaryEmail.setType(1);
        primaryEmail.setEnteredBy(user1Id);
        primaryEmail.setModifiedBy(user1Id);
        primaryEmail.setEmail("support@freemason_restaurant.com");
        if (primaryEmail.isValid()) {
          emailFormatter.format(primaryEmail);
          organization.getEmailAddressList().add(primaryEmail);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user1Id);
        contact.setModifiedBy(user1Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Mary");
        contact.setNameLast("Redhair");
        contact.setTitle("Waitress");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(767) 554-4444");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactEmailAddress businessEmail = new ContactEmailAddress();
          //businessEmail.setContactId(contactId);
          businessEmail.setType(1);
          businessEmail.setEnteredBy(user1Id);
          businessEmail.setModifiedBy(user1Id);
          businessEmail.setEmail("mary@freemason_restaurant.com");
          if (businessEmail.isValid()) {
            emailFormatter.format(businessEmail);
            contact.getEmailAddressList().add(businessEmail);
          } else {
            System.err.println("Business1 email is invalid: " + businessEmail.toString());
          }
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_BUSINESS_DEV, 1);
        // Add an action item
        ActionItem actionItem = new ActionItem();
        actionItem.setActionId(actionList2.getId());
        actionItem.setLinkItemId(contact.getId());
        actionItem.setEnteredBy(user1Id);
        actionItem.setModifiedBy(user1Id);
        actionItem.setEnabled(true);
        checkIfInserted(actionItem.insert(db), actionItem);
        // Task
        Task task = new Task();
        task.setEnteredBy(user1Id);
        task.setModifiedBy(user1Id);
        task.setPriority(1);
        task.setDescription("Meeting with Mary preparation - send contract");
        task.setNotes("Modify existing document in Account folder");
        task.setSharing(-1);
        task.setOwner(user1Id);
        task.setDueDate(addCurrentTimestamp(0));
        task.setDueDateTimeZone(timeZone);
        task.setEstimatedLOE(-1);
        //task.setEstimatedLOEType(1);
        task.setEstimatedLOEType(2);
        task.setType(1);
        //task.setType(Constants.TICKET_OBJECT);
        task.setContactId(contact.getId());
        checkIfInserted(task.insert(db), task);
      }
      // Add account opportunities
      OpportunityHeader opportunity = new OpportunityHeader();
      opportunity.setDescription("2006 Tork Converter");
      opportunity.setAccountLink(organization.getId());
      opportunity.setEnteredBy(user1Id);
      opportunity.setModifiedBy(user1Id);
      opportunity.setManager(user1Id);
      opportunity.setAccessType(oppAccessTypes.getDefaultItem());
      checkIfInserted(opportunity.insert(db), opportunity);
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("Maintenance agreement");
        //TODO: component.setTypes();
        component.setCloseProb("33");
        component.setCloseDate(addCurrentTimestamp(14));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("96000");
        component.setTerms(12);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_NEGOTIATION);
        component.setStageDate(addCurrentTimestamp(0));
        component.setAlertText("Last contract to attorney's confirmation");
        component.setAlertDate(addCurrentTimestamp(4));
        component.setAlertDateTimeZone(timeZone);
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_MAINTENANCE, 1);
      }
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("The new converter for their premises");
        //TODO: component.setTypes();
        component.setCloseProb("25");
        component.setCloseDate(addCurrentTimestamp(34));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("75000");
        component.setTerms(1);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_NEEDS_ANALYSIS);
        component.setStageDate(addCurrentTimestamp(0));
        component.setAlertText("Lunch meeting to revisit deal points with Bob");
        component.setAlertDate(addCurrentTimestamp(4));
        component.setAlertDateTimeZone(timeZone);
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_PRODUCT_SALES, 1);
        component.insertType(db, opportunityTypeList_MAINTENANCE, 2);
      }
    }

    // ADD ACCOUNT #5
    if (true) {
      Organization organization = new Organization();
      organization.setName("Harry's Diner");
      organization.setOwner(user1Id);
      organization.setAccountNumber("10006");
      organization.setIndustry(industryList_FOOD);
      organization.setAlertText("Contract renewal alert");
      organization.setAlertDate(addCurrentTimestamp(60));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(67));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user1Id);
      organization.setModifiedBy(user1Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(888) 888-8881");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Primary address
        OrganizationAddress organizationAddress = new OrganizationAddress();
        //organizationAddress.setOrgId(thisOrganization.getOrgId());
        organizationAddress.setType(1);
        organizationAddress.setEnteredBy(user1Id);
        organizationAddress.setModifiedBy(user1Id);
        //OrganizationAddress Fields
        organizationAddress.setStreetAddressLine1("4096 Fish Street");
        organizationAddress.setStreetAddressLine2("");
        organizationAddress.setStreetAddressLine3("");
        organizationAddress.setCity("St. Lucie");
        organizationAddress.setState("FL");
        organizationAddress.setZip("");
        organizationAddress.setCountry("UNITED STATES");
        if (organizationAddress.isValid()) {
          addressFormatter.format(organizationAddress);
          organization.getAddressList().add(organizationAddress);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user1Id);
        contact.setModifiedBy(user1Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Harold");
        contact.setNameLast("Potter");
        contact.setTitle("SVP / Sales");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(667) 776-7624");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactEmailAddress businessEmail = new ContactEmailAddress();
          //businessEmail.setContactId(contactId);
          businessEmail.setType(1);
          businessEmail.setEnteredBy(user1Id);
          businessEmail.setModifiedBy(user1Id);
          businessEmail.setEmail("harry@thisrestaurant.com");
          if (businessEmail.isValid()) {
            emailFormatter.format(businessEmail);
            contact.getEmailAddressList().add(businessEmail);
          } else {
            System.err.println("Business1 email is invalid: " + businessEmail.toString());
          }
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_EXECUTIVE, 1);
        // Add an action item
        ActionItem actionItem = new ActionItem();
        actionItem.setActionId(actionList1.getId());
        actionItem.setLinkItemId(contact.getId());
        actionItem.setEnteredBy(user1Id);
        actionItem.setModifiedBy(user1Id);
        actionItem.setEnabled(true);
        checkIfInserted(actionItem.insert(db), actionItem);
      }
      if (true) {
        // add contacts to this account
        Contact contact = new Contact();
        contact.setAccessType(accountContactAccessTypes.getDefaultItem());
        contact.setEnteredBy(user1Id);
        contact.setModifiedBy(user1Id);
        contact.setOwner(user1Id);
        contact.setNameFirst("Winona");
        contact.setNameLast("Weldon");
        contact.setTitle("Sales Rep");
        contact.setOrgId(organization.getId());
        if (true) {
          ContactPhoneNumber businessPhone = new ContactPhoneNumber();
          //businessPhone.setContactId(contactId);
          businessPhone.setType(1);
          businessPhone.setEnteredBy(user1Id);
          businessPhone.setModifiedBy(user1Id);
          businessPhone.setNumber("(667) 776-7644");
          businessPhone.setExtension("");
          if (businessPhone.isValid()) {
            phoneFormatter.format(businessPhone, thisLocale);
            contact.getPhoneNumberList().add(businessPhone);
          } else {
            System.err.println("Business4 phone is invalid: " + businessPhone.toString());
          }
        }
        if (true) {
          ContactEmailAddress businessEmail = new ContactEmailAddress();
          //businessEmail.setContactId(contactId);
          businessEmail.setType(1);
          businessEmail.setEnteredBy(user1Id);
          businessEmail.setModifiedBy(user1Id);
          businessEmail.setEmail("winona@thisrestaurant.com");
          if (businessEmail.isValid()) {
            emailFormatter.format(businessEmail);
            contact.getEmailAddressList().add(businessEmail);
          } else {
            System.err.println("Business1 email is invalid: " + businessEmail.toString());
          }
        }
        checkIfInserted(contact.insert(db), contact);
        contact.insertType(db, contactTypeList_SALES, 1);
      }
      // Add account opportunities
      OpportunityHeader opportunity = new OpportunityHeader();
      opportunity.setDescription("New Bar & Grill");
      opportunity.setAccountLink(organization.getId());
      opportunity.setEnteredBy(user1Id);
      opportunity.setModifiedBy(user1Id);
      opportunity.setManager(user1Id);
      opportunity.setAccessType(oppAccessTypes.getDefaultItem());
      checkIfInserted(opportunity.insert(db), opportunity);
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user3Id);
        component.setDescription("Installation of equipment");
        //TODO: component.setTypes();
        component.setCloseProb("50");
        component.setCloseDate(addCurrentTimestamp(62));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("36000");
        component.setTerms(3);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_PROPOSAL);
        component.setStageDate(addCurrentTimestamp(0));
        component.setAlertText("Double check on installer's availability");
        component.setAlertDate(addCurrentTimestamp(59));
        component.setAlertDateTimeZone(timeZone);
        component.setEnteredBy(user3Id);
        component.setModifiedBy(user3Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_SERVICES, 1);
      }
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("One-time design of grill");
        //TODO: component.setTypes();
        component.setCloseProb("50");
        component.setCloseDate(addCurrentTimestamp(11));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("35000");
        component.setTerms(2);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_VALUE_PROP);
        component.setStageDate(addCurrentTimestamp(0));
        component.setAlertText("Call back to set up lunch meeting");
        component.setAlertDate(addCurrentTimestamp(10));
        component.setAlertDateTimeZone(timeZone);
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_CONSULTATION, 1);
      }
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("Bar & Grill equipment purchase");
        //TODO: component.setTypes();
        component.setCloseProb("50");
        component.setCloseDate(addCurrentTimestamp(30));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("125000");
        component.setTerms(3);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_VALUE_PROP);
        component.setStageDate(addCurrentTimestamp(0));
        component.setAlertText("Contact ATL equipment for brochure");
        component.setAlertDate(addCurrentTimestamp(6));
        component.setAlertDateTimeZone(timeZone);
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_PRODUCT_SALES, 1);
      }
    }

    // ADD ACCOUNT #6
    if (true) {
      Organization organization = new Organization();
      organization.setName("Hollerin Hank's Pizza");
      organization.setOwner(user1Id);
      organization.setAccountNumber("10021");
      organization.setIndustry(industryList_FOOD);
      organization.setAlertText("Contract renewal alert");
      organization.setAlertDate(addCurrentTimestamp(70));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(77));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user1Id);
      organization.setModifiedBy(user1Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(998) 889-9898");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      // Add account opportunities
      OpportunityHeader opportunity = new OpportunityHeader();
      opportunity.setDescription("New Pizza Truck");
      opportunity.setAccountLink(organization.getId());
      opportunity.setEnteredBy(user1Id);
      opportunity.setModifiedBy(user1Id);
      opportunity.setManager(user1Id);
      opportunity.setAccessType(oppAccessTypes.getDefaultItem());
      checkIfInserted(opportunity.insert(db), opportunity);
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("Converted F350 delivery");
        //TODO: component.setTypes();
        component.setCloseProb("95");
        component.setCloseDate(addCurrentTimestamp(41));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("27500");
        component.setTerms(1);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_NEGOTIATION);
        component.setStageDate(addCurrentTimestamp(0));
        component.setAlertText("Delivery follow-up with Ford factory");
        component.setAlertDate(addCurrentTimestamp(6));
        component.setAlertDateTimeZone(timeZone);
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
      }
    }

    // ADD ACCOUNT #6
    if (true) {
      Organization organization = new Organization();
      organization.setName("Joan's Tractor Sales");
      organization.setOwner(user1Id);
      organization.setAccountNumber("10012");
      organization.setAlertText("Lunch meeting contract signing");
      organization.setAlertDate(addCurrentTimestamp(4));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(44));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user1Id);
      organization.setModifiedBy(user1Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(523) 555-0890");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(2);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(523) 555-0897");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Primary address
        OrganizationAddress organizationAddress = new OrganizationAddress();
        //organizationAddress.setOrgId(thisOrganization.getOrgId());
        organizationAddress.setType(1);
        organizationAddress.setEnteredBy(user1Id);
        organizationAddress.setModifiedBy(user1Id);
        //OrganizationAddress Fields
        organizationAddress.setStreetAddressLine1("286 Corn Row");
        organizationAddress.setStreetAddressLine2("Suite 200");
        organizationAddress.setStreetAddressLine3("");
        organizationAddress.setCity("Iowa City");
        organizationAddress.setState("IA");
        organizationAddress.setZip("");
        organizationAddress.setCountry("UNITED STATES");
        if (organizationAddress.isValid()) {
          addressFormatter.format(organizationAddress);
          organization.getAddressList().add(organizationAddress);
        }
      }
      checkIfInserted(organization.insert(db), organization);
    }

    // ADD ACCOUNT #7
    if (true) {
      Organization organization = new Organization();
      organization.setName("Ma & Pa Software");
      organization.setOwner(user4Id);
      organization.setAccountNumber("10033");
      organization.setIndustry(industryList_COMPUTER);
      organization.setAlertText("Contract renewal alert");
      organization.setAlertDate(addCurrentTimestamp(32));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(35));
      organization.setEnteredBy(user4Id);
      organization.setModifiedBy(user4Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(323) 443-4444");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      organization.setOnlyWarnings(true);
      checkIfInserted(organization.insert(db), organization);
      organization.insertType(db, accountTypeList_LARGE, 1);
    }

    // ADD ACCOUNT #8
    if (true) {
      Organization organization = new Organization();
      organization.setName("New City Mall");
      organization.setOwner(user3Id);
      organization.setAccountNumber("10045");
      organization.setIndustry(industryList_RETAIL);
      organization.setAlertText("Renewal signing at PLC");
      organization.setAlertDate(addCurrentTimestamp(0));
      organization.setAlertDateTimeZone(timeZone);
      organization.setOnlyWarnings(true);
      organization.setContractEndDate(addCurrentTimestamp(23));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user3Id);
      organization.setModifiedBy(user3Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(624) 555-0222");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(2);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(624) 555-0981");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Primary address
        OrganizationAddress organizationAddress = new OrganizationAddress();
        //organizationAddress.setOrgId(thisOrganization.getOrgId());
        organizationAddress.setType(1);
        organizationAddress.setEnteredBy(user3Id);
        organizationAddress.setModifiedBy(user3Id);
        //OrganizationAddress Fields
        organizationAddress.setStreetAddressLine1("Big Mall Road");
        organizationAddress.setStreetAddressLine2("");
        organizationAddress.setStreetAddressLine3("");
        organizationAddress.setCity("Norfolk");
        organizationAddress.setState("CT");
        organizationAddress.setZip("");
        organizationAddress.setCountry("UNITED STATES");
        if (organizationAddress.isValid()) {
          addressFormatter.format(organizationAddress);
          organization.getAddressList().add(organizationAddress);
        }
      }
      if (true) {
        // Org email
        OrganizationEmailAddress primaryEmail = new OrganizationEmailAddress();
        //primaryEmail.setOrgId(orgId);
        primaryEmail.setType(1);
        primaryEmail.setEnteredBy(user4Id);
        primaryEmail.setModifiedBy(user4Id);
        primaryEmail.setEmail("info@citymall.com");
        if (primaryEmail.isValid()) {
          emailFormatter.format(primaryEmail);
          organization.getEmailAddressList().add(primaryEmail);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      organization.insertType(db, accountTypeList_LARGE, 1);
      organization.insertType(db, accountTypeList_CONTRACT, 2);
      organization.insertType(db, accountTypeList_TERRITORY1, 3);
    }

    // ADD ACCOUNT #9
    if (true) {
      Organization organization = new Organization();
      organization.setName("New Shoes Co.");
      organization.setOwner(user1Id);
      organization.setAccountNumber("10011");
      organization.setIndustry(industryList_RETAIL);
      organization.setAlertText("Send renewal contract");
      organization.setAlertDate(addCurrentTimestamp(42));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(48));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user1Id);
      organization.setModifiedBy(user1Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(888) 525-7794");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Primary address
        OrganizationAddress organizationAddress = new OrganizationAddress();
        //organizationAddress.setOrgId(thisOrganization.getOrgId());
        organizationAddress.setType(1);
        organizationAddress.setEnteredBy(user3Id);
        organizationAddress.setModifiedBy(user3Id);
        //OrganizationAddress Fields
        organizationAddress.setStreetAddressLine1("2042 Cobbler Parkway");
        organizationAddress.setStreetAddressLine2("");
        organizationAddress.setStreetAddressLine3("");
        organizationAddress.setCity("Peninsula City");
        organizationAddress.setState("AZ");
        organizationAddress.setZip("");
        organizationAddress.setCountry("UNITED STATES");
        if (organizationAddress.isValid()) {
          addressFormatter.format(organizationAddress);
          organization.getAddressList().add(organizationAddress);
        }
      }
      organization.setOnlyWarnings(true);
      checkIfInserted(organization.insert(db), organization);
    }

    // ADD ACCOUNT #10
    if (true) {
      Organization organization = new Organization();
      organization.setName("Signature Services");
      organization.setOwner(user1Id);
      organization.setIndustry(industryList_FINANCIAL_SERVICES);
      organization.setAlertText("Contract renewal alert");
      organization.setAlertDate(addCurrentTimestamp(11));
      organization.setAlertDateTimeZone(timeZone);
      organization.setContractEndDate(addCurrentTimestamp(28));
      organization.setContractEndDateTimeZone(timeZone);
      organization.setEnteredBy(user1Id);
      organization.setModifiedBy(user1Id);
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(778) 887-6222");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        //Main Phone
        OrganizationPhoneNumber mainPhone = new OrganizationPhoneNumber();
        //mainPhone.setOrgId(organization.getOrgId());
        mainPhone.setType(1);
        mainPhone.setEnteredBy(user1Id);
        mainPhone.setModifiedBy(user1Id);
        mainPhone.setNumber("(778) 887-6213");
        if (mainPhone.isValid()) {
          phoneFormatter.format(mainPhone, thisLocale);
          organization.getPhoneNumberList().add(mainPhone);
        }
      }
      if (true) {
        // Org email
        OrganizationEmailAddress primaryEmail = new OrganizationEmailAddress();
        //primaryEmail.setOrgId(orgId);
        primaryEmail.setType(1);
        primaryEmail.setEnteredBy(user1Id);
        primaryEmail.setModifiedBy(user1Id);
        primaryEmail.setEmail("bossman@signatureservicesco.com");
        if (primaryEmail.isValid()) {
          emailFormatter.format(primaryEmail);
          organization.getEmailAddressList().add(primaryEmail);
        }
      }
      checkIfInserted(organization.insert(db), organization);
      organization.insertType(db, accountTypeList_TERRITORY5, 1);
    }

    // Create some internal tickets
    if (true) {
      Ticket ticket = new Ticket();
      ticket.setSourceCode(ticketSourceList_PHONE);
      ticket.setProblem("I'm unable to connect to the file server. I can connect to the staging server.");
      ticket.setComment("If you can't make it work, we're in serious trouble. But let me dust off my networking skills and I'll take a wack at it. Maybe Tom can give it a shot. Tom?");
      //ticket.setCatCode(ticketCategoryList.getIdFromValue("Technical"));
      ticket.setSeverityCode(ticketSeverityList_CRITICAL);
      ticket.setOrgId(0);
      ticket.setContactId(contact2Id);
      ticket.setPriorityCode(ticketPriorityList_URGENT);
      ticket.setDepartmentCode(departmentList_ENGINEERING);
      ticket.setAssignedTo(user3Id);
      ticket.setAssignedDate(addCurrentTimestamp(-2));
      ticket.setEntered(addCurrentTimestamp(-2));
      ticket.setEnteredBy(user1Id);
      ticket.setModified(addCurrentTimestamp(-2));
      ticket.setModifiedBy(user1Id);
      checkIfInserted(ticket.insert(db), ticket);
    }
    // Create some internal tickets
    if (true) {
      Ticket ticket = new Ticket();
      ticket.setSourceCode(ticketSourceList_EMAIL);
      ticket.setProblem("Printer doesn't work.");
      //ticket.setCatCode(ticketCategoryList.getIdFromValue("Technical"));
      ticket.setSeverityCode(ticketSeverityList_NORMAL);
      ticket.setOrgId(0);
      ticket.setContactId(contact3Id);
      ticket.setPriorityCode(ticketPriorityList_AS_SCHEDULED);
      ticket.setDepartmentCode(departmentList_ENGINEERING);
      ticket.setAssignedTo(user2Id);
      ticket.setAssignedDate(addCurrentTimestamp(-2));
      ticket.setEntered(addCurrentTimestamp(-2));
      ticket.setEnteredBy(user2Id);
      ticket.setModified(addCurrentTimestamp(-2));
      ticket.setModifiedBy(user2Id);
      checkIfInserted(ticket.insert(db), ticket);
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("Gordon");
      contact.setNameLast("Allenson");
      contact.setCompany("Anderson Village");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(323) 223-2121");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user1Id);
        businessEmail.setModifiedBy(user1Id);
        businessEmail.setEmail("gman@fictitiouscompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business1 email is invalid: " + businessEmail.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      contact.insertType(db, contactTypeList_ACQUAINTANCE, 1);
      if (true) {
        // insert a call
        Call call = new Call();
        call.setContactId(contact.getId());
        call.setCallTypeId(callTypeList_OUTGOING_CALL);
        call.setLength(30);
        call.setSubject("first meeting");
        call.setEnteredBy(user1Id);
        call.setModifiedBy(user1Id);
        call.setEntered(addCurrentTimestamp(-7));
        call.setModified(addCurrentTimestamp(-7));
        call.setOwner(user1Id);
        call.setAssignDate(addCurrentTimestamp(0));
        call.setCompletedBy(user1Id);
        call.setCompleteDate(addCurrentTimestamp(0));
        call.setResultId(callResultList_YES_BUS_PROGRESSING);
        call.setPriorityId(callPriorityList_HIGH);
        call.setStatusId(Call.COMPLETE);
        //call.setAlertDate();
        //call.setAlertDateTimeZone(timeZone);
        //call.setAlertText();
        checkIfInserted(call.insert(db), call);
      }
      if (true) {
        // insert a call
        Call call = new Call();
        call.setContactId(contact.getId());
        call.setCallTypeId(callTypeList_OUTSIDE_APPT);
        call.setLength(15);
        call.setSubject("second meeting");
        call.setEnteredBy(user1Id);
        call.setModifiedBy(user1Id);
        call.setEntered(addCurrentTimestamp(-1));
        call.setModified(addCurrentTimestamp(-1));
        call.setOwner(user1Id);
        call.setAssignDate(addCurrentTimestamp(0));
        call.setCompletedBy(user1Id);
        call.setCompleteDate(addCurrentTimestamp(0));
        call.setResultId(callResultList_YES_BUS_PROGRESSING);
        call.setPriorityId(callPriorityList_HIGH);
        call.setStatusId(Call.COMPLETE);
        //call.setAlertDate();
        //call.setAlertText();
        checkIfInserted(call.insert(db), call);
      }
      if (true) {
        // insert a call
        Call call = new Call();
        call.setContactId(contact.getId());
        call.setCallTypeId(callTypeList_OUTGOING_CALL);
        call.setLength(0);
        call.setSubject("Set up meeting for next Week");
        call.setNotes("- Need to bring a projector for the meeting and donuts");
        call.setAlertDate(addCurrentTimestamp(7));
        call.setAlertDateTimeZone(timeZone);
        call.setFollowupNotes("Don't forget the donuts");
        call.setEntered(addCurrentTimestamp(0));
        call.setEnteredBy(user1Id);
        call.setModified(addCurrentTimestamp(0));
        call.setModifiedBy(user1Id);
        call.setAlertText("Set up meeting for next Week");
        call.setAlertCallTypeId(callTypeList_OUTSIDE_APPT);
        call.setOwner(user1Id);
        call.setAssignDate(addCurrentTimestamp(0));
        call.setCompletedBy(user1Id);
        call.setCompleteDate(addCurrentTimestamp(0));
        call.setResultId(callResultList_YES_BUS_PROGRESSING);
        call.setPriorityId(callPriorityList_HIGH);
        call.setStatusId(3);
        call.setReminderId(1);
        call.setReminderTypeId(callReminderList_HOURS);
        checkIfInserted(call.insert(db), call);
      }
      // add some opportunities
      OpportunityHeader opportunity = new OpportunityHeader();
      opportunity.setDescription("Series 5000 to Westside Location");
      opportunity.setContactLink(contact.getId());
      opportunity.setEnteredBy(user3Id);
      opportunity.setModifiedBy(user3Id);
      opportunity.setManager(user3Id);
      opportunity.setAccessType(oppAccessTypes.getDefaultItem());
      checkIfInserted(opportunity.insert(db), opportunity);
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user3Id);
        component.setDescription("One-time Installation");
        component.setCloseProb("50");
        component.setCloseDate(addCurrentTimestamp(60));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("50000");
        component.setTerms(2);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_VALUE_PROP);
        component.setStageDate(addCurrentTimestamp(-1));
        component.setAlertText("Set up meeting next week");
        component.setAlertDate(addCurrentTimestamp(3));
        component.setAlertDateTimeZone(timeZone);
        component.setEnteredBy(user4Id);
        component.setModifiedBy(user4Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_DEVELOPMENT, 1);
      }
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("Series 5000 Hardware");
        //TODO: component.setTypes();
        component.setCloseProb("50");
        component.setCloseDate(addCurrentTimestamp(25));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("75000");
        component.setTerms(1);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_PROSPECTING);
        component.setStageDate(addCurrentTimestamp(0));
        component.setEnteredBy(user4Id);
        component.setModifiedBy(user4Id);
        checkIfInserted(component.insert(db), component);
        component.insertType(db, opportunityTypeList_PRODUCT_SALES, 1);
      }
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("George");
      contact.setNameLast("Anderson");
      contact.setTitle("Founder");
      contact.setCompany("Anderson Village");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(323) 224-2121");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(7);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(323) 224-2121");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user1Id);
        businessEmail.setModifiedBy(user1Id);
        businessEmail.setEmail("ganderson@fictitiouscompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business1 email is invalid: " + businessEmail.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("Fran");
      contact.setNameLast("Baker");
      contact.setTitle("Operations Manager");
      contact.setCompany("Baker Company");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(567) 890-6543");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      if (true) {
        // insert a call
        Call call = new Call();
        call.setContactId(contact.getId());
        call.setCallTypeId(callTypeList_OUTGOING_CALL);
        call.setLength(15);
        call.setSubject("Set up meeting for next week");
        call.setNotes("We'll meet at Silvers");
        call.setEnteredBy(user1Id);
        call.setModifiedBy(user1Id);
        call.setEntered(addCurrentTimestamp(-7));
        call.setModified(addCurrentTimestamp(-7));
        call.setAlertDate(addCurrentTimestamp(1));
        call.setAlertDateTimeZone(timeZone);
        call.setAlertText("Check reservations at Silvers");
        call.setAlertCallTypeId(callTypeList_OUTSIDE_APPT);
        call.setOwner(user1Id);
        call.setAssignDate(addCurrentTimestamp(0));
        call.setCompletedBy(user1Id);
        call.setCompleteDate(addCurrentTimestamp(0));
        call.setResultId(callResultList_YES_BUS_PROGRESSING);
        call.setPriorityId(callPriorityList_HIGH);
        call.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
        call.setReminderId(1);
        call.setReminderTypeId(callReminderList_HOURS);
        checkIfInserted(call.insert(db), call);
      }
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("Slick");
      contact.setNameLast("Crutchfield");
      //contact.setTitle("Waitress");
      contact.setCompany("Motor Sports USA");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(919) 556-4453");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(7);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(919) 889-8878");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user1Id);
        businessEmail.setModifiedBy(user1Id);
        businessEmail.setEmail("slick@fictitiouscompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business1 email is invalid: " + businessEmail.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      // Add an action item
      ActionItem actionItem = new ActionItem();
      actionItem.setActionId(actionList2.getId());
      actionItem.setLinkItemId(contact.getId());
      actionItem.setEnteredBy(user1Id);
      actionItem.setModifiedBy(user1Id);
      actionItem.setEnabled(true);
      checkIfInserted(actionItem.insert(db), actionItem);
      if (true) {
        // insert a call
        Call call = new Call();
        call.setContactId(contact.getId());
        call.setCallTypeId(callTypeList_OUTGOING_CALL);
        //call.setLength(0);
        call.setSubject("Confirm he got presentation");
        call.setEnteredBy(user1Id);
        call.setModifiedBy(user1Id);
        call.setEntered(addCurrentTimestamp(0));
        call.setModified(addCurrentTimestamp(0));
        call.setAlertDate(addCurrentTimestamp(7));
        call.setAlertDateTimeZone(timeZone);
        call.setAlertText("Call back");
        call.setActionId(actionItem.getId());
        call.setAlertCallTypeId(callTypeList_OUTGOING_CALL);
        call.setOwner(user1Id);
        call.setAssignDate(addCurrentTimestamp(0));
        call.setCompletedBy(user1Id);
        call.setCompleteDate(addCurrentTimestamp(0));
        call.setResultId(callResultList_YES_BUS_PROGRESSING);
        call.setPriorityId(callPriorityList_HIGH);
        call.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
        call.setReminderId(1);
        call.setReminderTypeId(callReminderList_HOURS);
        checkIfInserted(call.insert(db), call);
      }
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("Fred");
      contact.setNameLast("Davis");
      contact.setCompany("Boating World");
      contact.setTitle("Sales Rep");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(443) 378-7765");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user1Id);
        businessEmail.setModifiedBy(user1Id);
        businessEmail.setEmail("davis@fictitiouscompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business1 email is invalid: " + businessEmail.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      contact.insertType(db, contactTypeList_CUSTOMER, 1);
      contact.insertType(db, contactTypeList_FRIEND, 2);
      // Task
      Task task = new Task();
      task.setEnteredBy(user1Id);
      task.setModifiedBy(user1Id);
      task.setPriority(1);
      task.setDescription("Call Fred re: meeting on the 4th");
      task.setNotes("Confirm location and agenda");
      task.setSharing(-1);
      task.setOwner(user1Id);
      task.setDueDate(addCurrentTimestamp(1));
      task.setDueDateTimeZone(timeZone);
      task.setEstimatedLOE(-1);
      //task.setEstimatedLOEType(1);
      task.setEstimatedLOEType(2);
      task.setType(1);
      //task.setType(Constants.TICKET_OBJECT);
      task.setContactId(contact.getId());
      checkIfInserted(task.insert(db), task);
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("Manny");
      contact.setNameLast("Delmonico");
      contact.setTitle("Taster");
      contact.setCompany("The Great Steak Co.");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(667) 220-4952");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(7);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(667) 775-4312");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user1Id);
        businessEmail.setModifiedBy(user1Id);
        businessEmail.setEmail("taster@fictitiouscompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business1 email is invalid: " + businessEmail.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      // Add an action item
      ActionItem actionItem = new ActionItem();
      actionItem.setActionId(actionList1.getId());
      actionItem.setLinkItemId(contact.getId());
      actionItem.setEnteredBy(user1Id);
      actionItem.setModifiedBy(user1Id);
      actionItem.setEnabled(true);
      checkIfInserted(actionItem.insert(db), actionItem);
      if (true) {
        // insert a call
        Call call = new Call();
        call.setContactId(contact.getId());
        call.setCallTypeId(callTypeList_OUTSIDE_APPT);
        //call.setLength(15);
        call.setSubject("To set up presentation");
        call.setNotes("Wasn't in but managed to talk with assistant. I'll leave a ticket for Tom to follow up and coordinate with engineering. Assistant said he was almost sure to buy. Will also start an opportunity");
        call.setEnteredBy(user1Id);
        call.setModifiedBy(user1Id);
        call.setEntered(addCurrentTimestamp(0));
        call.setModified(addCurrentTimestamp(0));
        call.setAlertDate(addCurrentTimestamp(1));
        call.setAlertDateTimeZone(timeZone);
        call.setAlertText("Call back to confirm sale and send contract");
        call.setActionId(actionItem.getId());
        call.setAlertCallTypeId(callTypeList_OUTSIDE_APPT);
        call.setOwner(user1Id);
        call.setAssignDate(addCurrentTimestamp(0));
        call.setCompletedBy(user1Id);
        call.setCompleteDate(addCurrentTimestamp(0));
        call.setResultId(callResultList_YES_BUS_PROGRESSING);
        call.setPriorityId(callPriorityList_HIGH);
        call.setStatusId(Call.COMPLETE_FOLLOWUP_PENDING);
        call.setReminderId(1);
        call.setReminderTypeId(callReminderList_HOURS);
        checkIfInserted(call.insert(db), call);
      }
      // add some opportunities
      OpportunityHeader opportunity = new OpportunityHeader();
      opportunity.setDescription("Version Defib sale");
      opportunity.setContactLink(contact.getId());
      opportunity.setEnteredBy(user1Id);
      opportunity.setModifiedBy(user1Id);
      opportunity.setManager(user1Id);
      opportunity.setAccessType(oppAccessTypes.getDefaultItem());
      checkIfInserted(opportunity.insert(db), opportunity);
      if (true) {
        // add a component
        OpportunityComponent component = new OpportunityComponent();
        component.setHeaderId(opportunity.getId());
        component.setOwner(user1Id);
        component.setDescription("Latest model");
        //TODO: component.setTypes();
        component.setCloseProb("80");
        component.setCloseDate(addCurrentTimestamp(23));
        component.setCloseDateTimeZone(timeZone);
        component.setGuess("28000");
        component.setTerms(3);
        component.setUnits("M");
        component.setType("N");
        component.setStage(stageList_NEGOTIATION);
        component.setStageDate(addCurrentTimestamp(-1));
        //component.setAlertText("Check to see if service commenced");
        //component.setAlertDate(addCurrentTimestamp(10));
        component.setEnteredBy(user1Id);
        component.setModifiedBy(user1Id);
        checkIfInserted(component.insert(db), component);
      }
      opportunity.updateLog(db, actionItem.getId());
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("David");
      contact.setNameLast("Jones");
      contact.setTitle("President");
      contact.setCompany("Jones Co.");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(757) 555-0812");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      if (true) {
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user1Id);
        businessEmail.setModifiedBy(user1Id);
        businessEmail.setEmail("david@jonesco.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business1 email is invalid: " + businessEmail.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      contact.insertType(db, contactTypeList_FRIEND, 1);
      contact.insertType(db, contactTypeList_PROSPECT, 2);
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("Alice");
      contact.setNameLast("Silver");
      contact.setTitle("Operations Assistant");
      contact.setCompany("Baker Company");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactPhoneNumber businessPhone = new ContactPhoneNumber();
        //businessPhone.setContactId(contactId);
        businessPhone.setType(1);
        businessPhone.setEnteredBy(user1Id);
        businessPhone.setModifiedBy(user1Id);
        businessPhone.setNumber("(567) 890-6009");
        businessPhone.setExtension("");
        if (businessPhone.isValid()) {
          phoneFormatter.format(businessPhone, thisLocale);
          contact.getPhoneNumberList().add(businessPhone);
        } else {
          System.err.println("Business4 phone is invalid: " + businessPhone.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      contact.insertType(db, contactTypeList_PROSPECT, 1);
      // Add an action item
      if (true) {
        ActionItem actionItem = new ActionItem();
        actionItem.setActionId(actionList1.getId());
        actionItem.setLinkItemId(contact.getId());
        actionItem.setEnteredBy(user1Id);
        actionItem.setModifiedBy(user1Id);
        actionItem.setEnabled(true);
        checkIfInserted(actionItem.insert(db), actionItem);
      }
    }

    // Insert a contact without an account
    if (true) {
      // add contacts to this account
      Contact contact = new Contact();
      contact.setAccessType(contactAccessTypes.getDefaultItem());
      contact.setEnteredBy(user1Id);
      contact.setModifiedBy(user1Id);
      contact.setOwner(user1Id);
      contact.setNameFirst("Willie");
      contact.setNameLast("Sosa");
      contact.setTitle("Delivery man");
      contact.setCompany("Chicago Pizza");
      //contact.setOrgId(organization.getId());
      if (true) {
        ContactEmailAddress businessEmail = new ContactEmailAddress();
        //businessEmail.setContactId(contactId);
        businessEmail.setType(1);
        businessEmail.setEnteredBy(user1Id);
        businessEmail.setModifiedBy(user1Id);
        businessEmail.setEmail("willie.s@fictitiouscompany.com");
        if (businessEmail.isValid()) {
          emailFormatter.format(businessEmail);
          contact.getEmailAddressList().add(businessEmail);
        } else {
          System.err.println("Business1 email is invalid: " + businessEmail.toString());
        }
      }
      checkIfInserted(contact.insert(db), contact);
      contact.insertType(db, contactTypeList_VENDOR, 1);
      // Add an action item
      if (true) {
        ActionItem actionItem = new ActionItem();
        actionItem.setActionId(actionList1.getId());
        actionItem.setLinkItemId(contact.getId());
        actionItem.setEnteredBy(user1Id);
        actionItem.setModifiedBy(user1Id);
        actionItem.setEnabled(true);
        checkIfInserted(actionItem.insert(db), actionItem);
      }
    }

    // Insert projects
    Project project = new Project();
    project.setTitle("AAA Automotive Gork Implementation Timeline");
    project.setShortDescription("The overview project plan to implement the Gork for AAA Automotive");
    project.setRequestedBy("John Robinson");
    project.setRequestedByDept("Robinson Electronics");
    project.setRequestDate(addCurrentTimestamp(0));
    project.setRequestDateTimeZone(timeZone);
    project.setApprovalDate(addCurrentTimestamp(0));
    project.setEntered(addCurrentTimestamp(0));
    project.setEnteredBy(user1Id);
    project.setModified(addCurrentTimestamp(0));
    project.setModifiedBy(user1Id);
    project.setShowNews(true);
    project.setShowDetails(true);
    project.setShowTeam(true);
    project.setShowPlan(true);
    project.setShowLists(true);
    project.setShowDiscussion(true);
    project.setShowTickets(true);
    project.setShowDocuments(true);
    project.setEstimatedCloseDate(addCurrentTimestamp(60));
    project.setEstimatedCloseDateTimeZone(timeZone);
    project.setBudget(20000);
    project.setBudgetCurrency("USD");
    checkIfInserted(project.insert(db), project);
    // Team Members
    if (true) {
      TeamMember teamMember = new TeamMember();
      teamMember.setProjectId(project.getId());
      teamMember.setUserId(user1Id);
      teamMember.setUserLevel(projectRoleList_LEAD);
      teamMember.setEnteredBy(user1Id);
      teamMember.setModifiedBy(user1Id);
      teamMember.setStatus(TeamMember.STATUS_ADDED);
      checkIfInserted(teamMember.insert(db), teamMember);
    }
    if (true) {
      TeamMember teamMember = new TeamMember();
      teamMember.setProjectId(project.getId());
      teamMember.setUserId(user2Id);
      teamMember.setUserLevel(projectRoleList_OBSERVER);
      teamMember.setEnteredBy(user1Id);
      teamMember.setModifiedBy(user1Id);
      teamMember.setStatus(TeamMember.STATUS_INVITING);
      checkIfInserted(teamMember.insert(db), teamMember);
    }
    if (true) {
      TeamMember teamMember = new TeamMember();
      teamMember.setProjectId(project.getId());
      teamMember.setUserId(user3Id);
      teamMember.setUserLevel(projectRoleList_CONTRIBUTOR);
      teamMember.setEnteredBy(user1Id);
      teamMember.setModifiedBy(user1Id);
      teamMember.setStatus(TeamMember.STATUS_INVITING);
      checkIfInserted(teamMember.insert(db), teamMember);
    }
    // News
    if (true) {
      com.zeroio.iteam.base.NewsArticle newsArticle = new com.zeroio.iteam.base.NewsArticle();
      newsArticle.setProjectId(project.getId());
      newsArticle.setSubject("Welcome new team members");
      newsArticle.setIntro("Welcome to the project new team members.  We look forward to providing AAA Automotive with the very best experience as they're one of our most valuable customers.  We'll make them happy if we all remember:<br /><br /><ul><li>Leave the weapons at home<br /></li><li>No drinking on the job</li><li>Get a good night sleep</li></ul>Thanks,<br /><br />Your team leader<br />");
      newsArticle.setEnteredBy(user1Id);
      newsArticle.setModifiedBy(user1Id);
      newsArticle.setStartDate(addCurrentTimestamp(0));
      newsArticle.setStartDateTimeZone(timeZone);
      newsArticle.setPriorityId(com.zeroio.iteam.base.NewsArticle.NORMAL);
      newsArticle.setStatus(com.zeroio.iteam.base.NewsArticle.PUBLISHED);
      newsArticle.setEndDateTimeZone(timeZone);
      checkIfInserted(newsArticle.insert(db), newsArticle);
    }
    // Requirements
    if (true) {
      Requirement requirement = new Requirement();
      requirement.setProjectId(project.getId());
      requirement.setSubmittedBy("B. Franklin");
      requirement.setDepartmentBy("Postal");
      requirement.setShortDescription("Delivery");
      requirement.setDescription("These are the 10 -15 steps of the delivery process.  If we do these well we'll succeed");
      requirement.setEstimatedLoe("100");
      requirement.setEstimatedLoeTypeId(2);
      requirement.setActualLoeType("2");
      requirement.setDeadline(addCurrentTimestamp(60));
      requirement.setApprovedBy(user1Id);
      requirement.setApprovalDate(addCurrentTimestamp(0));
      requirement.setEntered(addCurrentTimestamp(0));
      requirement.setEnteredBy(user1Id);
      requirement.setModified(addCurrentTimestamp(0));
      requirement.setModifiedBy(user1Id);
      requirement.setStartDate(addCurrentTimestamp(3));
      requirement.setStartDateTimeZone(timeZone);
      requirement.setDeadlineTimeZone(timeZone);
      checkIfInserted(requirement.insert(db), requirement);
      // first node
      if (true) {
        AssignmentFolder assignmentFolder = new AssignmentFolder();
        assignmentFolder.setProjectId(project.getId());
        assignmentFolder.setRequirementId(requirement.getId());
        assignmentFolder.setIndent(0);
        assignmentFolder.setName("Hardware Ordering");
        assignmentFolder.setDescription(null);
        assignmentFolder.setEnteredBy(user1Id);
        assignmentFolder.setModifiedBy(user1Id);
        checkIfInserted(assignmentFolder.insert(db), assignmentFolder);
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user1Id);
          assignment.setRole("Order materials");
          assignment.setEstimatedLoe(5);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(6));
          assignment.setStatusId(5);
          assignment.setStatusDate(addCurrentTimestamp(0));
          assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user3Id);
          assignment.setRole("Check to see if order received");
          assignment.setEstimatedLoe(1);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          //assignment.setDueDate(addCurrentTimestamp(6));
          assignment.setStatusId(5);
          assignment.setStatusDate(addCurrentTimestamp(0));
          assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user2Id);
          assignment.setRole("Send received equipment to project lead");
          assignment.setEstimatedLoe(1);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          //assignment.setDueDate(addCurrentTimestamp(6));
          assignment.setStatusId(3);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user1Id);
          assignment.setRole("Confirm delivery");
          assignment.setEstimatedLoe(2);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          //assignment.setStartDate(addCurrentTimestamp(14));
          assignment.setDueDate(addCurrentTimestamp(14));
          assignment.setStatusId(1);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user1Id);
          assignment.setRole("Complete asset information form in Centric CRM");
          assignment.setEstimatedLoe(1);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          //assignment.setStartDate(addCurrentTimestamp(14));
          assignment.setDueDate(addCurrentTimestamp(15));
          assignment.setStatusId(1);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
      }
      // second node
      if (true) {
        AssignmentFolder assignmentFolder = new AssignmentFolder();
        assignmentFolder.setProjectId(project.getId());
        assignmentFolder.setRequirementId(requirement.getId());
        assignmentFolder.setIndent(0);
        assignmentFolder.setName("Installation");
        assignmentFolder.setDescription(null);
        assignmentFolder.setEnteredBy(user1Id);
        assignmentFolder.setModifiedBy(user1Id);
        checkIfInserted(assignmentFolder.insert(db), assignmentFolder);
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user3Id);
          assignment.setRole("Site prep'd");
          assignment.setEstimatedLoe(16);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(10));
          assignment.setStatusId(5);
          assignment.setStatusDate(addCurrentTimestamp(0));
          assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user2Id);
          assignment.setRole("Hardware installed");
          assignment.setEstimatedLoe(12);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          //assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(11));
          assignment.setStatusId(1);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user1Id);
          assignment.setRole("Hardware QA'd");
          assignment.setEstimatedLoe(12);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          //assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(15));
          assignment.setStatusId(1);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user3Id);
          assignment.setRole("Project Manager notified");
          assignment.setEstimatedLoe(1);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          //assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(15));
          assignment.setStatusId(1);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
      }
    }
    if (true) {
      Requirement requirement = new Requirement();
      requirement.setProjectId(project.getId());
      requirement.setSubmittedBy("T. Jefferson");
      requirement.setDepartmentBy("Louisiana Warehouse");
      requirement.setShortDescription("Training");
      requirement.setDescription("Two basic groups have to be trained.  The administrator and the \"Train-the-Trainor\"");
      requirement.setEstimatedLoe("24");
      requirement.setEstimatedLoeTypeId(2);
      requirement.setActualLoeType("2");
      requirement.setDeadline(addCurrentTimestamp(35));
      requirement.setApprovedBy(user1Id);
      requirement.setApprovalDate(addCurrentTimestamp(0));
      requirement.setEntered(addCurrentTimestamp(0));
      requirement.setEnteredBy(user1Id);
      requirement.setModified(addCurrentTimestamp(0));
      requirement.setModifiedBy(user1Id);
      requirement.setStartDate(addCurrentTimestamp(18));
      requirement.setStartDateTimeZone(timeZone);
      requirement.setDeadlineTimeZone(timeZone);
      checkIfInserted(requirement.insert(db), requirement);
      // first node
      if (true) {
        AssignmentFolder assignmentFolder = new AssignmentFolder();
        assignmentFolder.setProjectId(project.getId());
        assignmentFolder.setRequirementId(requirement.getId());
        assignmentFolder.setIndent(0);
        assignmentFolder.setName("Administrator");
        assignmentFolder.setDescription(null);
        assignmentFolder.setEnteredBy(user1Id);
        assignmentFolder.setModifiedBy(user1Id);
        checkIfInserted(assignmentFolder.insert(db), assignmentFolder);
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user3Id);
          assignment.setRole("Send data capture worksheet to administrator/s");
          assignment.setEstimatedLoe(2);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(3));
          assignment.setStatusId(5);
          assignment.setStatusDate(addCurrentTimestamp(0));
          assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user1Id);
          assignment.setRole("Enter data from worksheets into CentricCRM document mgr");
          assignment.setEstimatedLoe(1);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(6));
          assignment.setStatusId(5);
          assignment.setStatusDate(addCurrentTimestamp(0));
          assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user2Id);
          assignment.setRole("Train administrator based on feedback after installation");
          assignment.setEstimatedLoe(8);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          //assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(25));
          assignment.setStatusId(1);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
      }
      // second node
      if (true) {
        AssignmentFolder assignmentFolder = new AssignmentFolder();
        assignmentFolder.setProjectId(project.getId());
        assignmentFolder.setRequirementId(requirement.getId());
        assignmentFolder.setIndent(0);
        assignmentFolder.setName("Train-the-Trainor");
        assignmentFolder.setDescription(null);
        assignmentFolder.setEnteredBy(user1Id);
        assignmentFolder.setModifiedBy(user1Id);
        checkIfInserted(assignmentFolder.insert(db), assignmentFolder);
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user1Id);
          assignment.setRole("Send data worksheets to capture info on users");
          assignment.setEstimatedLoe(2);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(6));
          assignment.setStatusId(5);
          assignment.setStatusDate(addCurrentTimestamp(0));
          assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user3Id);
          assignment.setRole("Analyze data and format into training materials");
          assignment.setEstimatedLoe(8);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(14));
          assignment.setStatusId(2);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          //assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
        if (true) {
          Assignment assignment = new Assignment();
          assignment.setProjectId(project.getId());
          assignment.setRequirementId(requirement.getId());
          assignment.setIndent(1);
          assignment.setUserAssignedId(user2Id);
          assignment.setRole("Train-the-trainer");
          assignment.setEstimatedLoe(16);
          assignment.setEstimatedLoeTypeId(2);
          assignment.setActualLoeTypeId(2);
          assignment.setPriorityId(2);
          assignment.setAssignDate(addCurrentTimestamp(0));
          //assignment.setStartDate(addCurrentTimestamp(0));
          assignment.setDueDate(addCurrentTimestamp(28));
          assignment.setStatusId(1);
          assignment.setStatusDate(addCurrentTimestamp(0));
          //assignment.setCompleteDate(addCurrentTimestamp(0));
          assignment.setEntered(addCurrentTimestamp(0));
          assignment.setEnteredBy(user1Id);
          assignment.setModified(addCurrentTimestamp(0));
          assignment.setModifiedBy(user1Id);
          assignment.setFolderId(assignmentFolder.getId());
          assignment.setDueDateTimeZone(timeZone);
          checkIfInserted(assignment.insert(db), assignment);
        }
      }
    }
    if (true) {
      IssueCategory issueCategory = new IssueCategory();
      issueCategory.setProjectId(project.getId());
      issueCategory.setSubject("Technical Musings");
      issueCategory.setEnteredBy(user1Id);
      issueCategory.setModifiedBy(user1Id);
      checkIfInserted(issueCategory.insert(db), issueCategory);
    }
    if (true) {
      FileFolder fileFolder = new FileFolder();
      fileFolder.setLinkModuleId(Constants.DOCUMENTS_PROJECTS);
      fileFolder.setLinkItemId(project.getId());
      fileFolder.setSubject("Technical Docs");
      fileFolder.setEnteredBy(user1Id);
      fileFolder.setModifiedBy(user1Id);
      checkIfInserted(fileFolder.insert(db), fileFolder);
    }
    if (true) {
      FileFolder fileFolder = new FileFolder();
      fileFolder.setLinkModuleId(Constants.DOCUMENTS_PROJECTS);
      fileFolder.setLinkItemId(project.getId());
      fileFolder.setSubject("Marketing Docs");
      fileFolder.setEnteredBy(user1Id);
      fileFolder.setModifiedBy(user1Id);
      checkIfInserted(fileFolder.insert(db), fileFolder);
    }
    if (true) {
      FileFolder fileFolder = new FileFolder();
      fileFolder.setLinkModuleId(Constants.DOCUMENTS_PROJECTS);
      fileFolder.setLinkItemId(project.getId());
      fileFolder.setSubject("Specifications");
      fileFolder.setEnteredBy(user1Id);
      fileFolder.setModifiedBy(user1Id);
      checkIfInserted(fileFolder.insert(db), fileFolder);
    }
    if (true) {
      FileFolder fileFolder = new FileFolder();
      fileFolder.setLinkModuleId(Constants.DOCUMENTS_PROJECTS);
      fileFolder.setLinkItemId(project.getId());
      fileFolder.setSubject("Group Time Sheets");
      fileFolder.setEnteredBy(user1Id);
      fileFolder.setModifiedBy(user1Id);
      checkIfInserted(fileFolder.insert(db), fileFolder);
    }

    return true;
  }
}

