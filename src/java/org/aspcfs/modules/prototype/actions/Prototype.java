package org.aspcfs.modules.prototype.actions;

import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.contacts.base.Contact;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public final class Prototype extends CFSModule {

  /**
   *  Description of the Method
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    String module = context.getRequest().getParameter("module");
    String includePage = context.getRequest().getParameter("include");
    context.getRequest().setAttribute("IncludePage", includePage);
    Connection db = null;
    try {
      db = this.getConnection(context);
      addHtmlSelectElements(context, db);
      addContact(context, db);
      //addOpportunity(context, db);
      //addOrganization(context, db);
      addOpportunityList(context, db);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
      this.freeConnection(context, db);
    }
    if (module != null) {
      addModuleBean(context, module, module);
      return ("IncludeOK");
    } else {
      return ("IncludeStyleContainerOK");
    }
  }


  /**
   *  Adds a feature to the Contact attribute of the Prototype object
   *
   *@param  context           The feature to be added to the Contact attribute
   *@param  db                The feature to be added to the Contact attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addContact(ActionContext context, Connection db) throws SQLException {
    String contactId = (String) context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    if (contactId != null) {
      Contact thisContact = new Contact(db, Integer.parseInt(contactId));
      context.getRequest().setAttribute("ContactDetails", thisContact);
    }
  }


  /**
   *  Adds a feature to the HtmlSelectElements attribute of the Prototype object
   *
   *@param  context           The feature to be added to the HtmlSelectElements
   *      attribute
   *@param  db                The feature to be added to the HtmlSelectElements
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addHtmlSelectElements(ActionContext context, Connection db) throws SQLException {
    HtmlSelect relationshipTypeSelect = new HtmlSelect();

    String includePage = context.getRequest().getParameter("include");
    if (includePage != null && includePage.indexOf("_add") > 0) {
      relationshipTypeSelect.addItem("--Select--");
    } else {
      relationshipTypeSelect.addItem("All");
    }
    relationshipTypeSelect.addItem("Acquaintance of");
    relationshipTypeSelect.addItem("Advocate of");
    relationshipTypeSelect.addItem("Author of");
    relationshipTypeSelect.addItem("Close friend of");
    relationshipTypeSelect.addItem("Co-worker of");
    relationshipTypeSelect.addItem("Consultant to");
    relationshipTypeSelect.addItem("Customer of");
    relationshipTypeSelect.addItem("Employee of");
    relationshipTypeSelect.addItem("Ex-employee of");
    relationshipTypeSelect.addItem("Ex-member of");
    relationshipTypeSelect.addItem("Friend of");
    relationshipTypeSelect.addItem("Influencer of");
    relationshipTypeSelect.addItem("Inventor of");
    relationshipTypeSelect.addItem("Member of");
    relationshipTypeSelect.addItem("Relative of");
    relationshipTypeSelect.addItem("Owner of");
    relationshipTypeSelect.addItem("Spouse of");
    relationshipTypeSelect.addItem("Team member of");
    context.getRequest().setAttribute("relationshipTypeSelect", relationshipTypeSelect);

    HtmlSelect objectSelect = new HtmlSelect();
    objectSelect.addItem("--None--");
    objectSelect.addItem("Accounts");
    objectSelect.addItem("Contacts");
    objectSelect.addItem("Ideas");
    objectSelect.addItem("Organizations");
    objectSelect.addItem("Opportunities");
    objectSelect.addItem("Projects");
    context.getRequest().setAttribute("objectSelect", objectSelect);

    HtmlSelect objectSubSelect = new HtmlSelect();
    objectSubSelect.addItem("--None--");
    objectSubSelect.addItem("My Open Opportunities");
    objectSubSelect.addItem("All Open Opportunities");
    objectSubSelect.addItem("My Closed Opportunities");
    objectSubSelect.addItem("All Closed Opportunities");
    context.getRequest().setAttribute("objectSubSelect", objectSubSelect);

    HtmlSelect howDirectSelect = new HtmlSelect();
    howDirectSelect.addItem("--None--");
    howDirectSelect.addItem("1 Hop");
    howDirectSelect.addItem("2 Hops");
    howDirectSelect.addItem("3 Hops");
    howDirectSelect.addItem("4 Hops");
    howDirectSelect.addItem("5 Hops");
    howDirectSelect.addItem("6 Hops");
    howDirectSelect.addItem("7 Hops");
    howDirectSelect.addItem("> .90 Index");
    howDirectSelect.addItem("> .75 Index");
    howDirectSelect.addItem("> .60 Index");
    howDirectSelect.addItem("> .50 Index");
    howDirectSelect.addItem("> .30 Index");
    howDirectSelect.addItem("> .20 Index");
    howDirectSelect.addItem("> .10 Index");
    howDirectSelect.addItem("> .05 Index");
    context.getRequest().setAttribute("howDirectSelect", howDirectSelect);
  }


  /**
   *  Adds a feature to the OpportunityList attribute of the Prototype object
   *
   *@param  context           The feature to be added to the OpportunityList
   *      attribute
   *@param  db                The feature to be added to the OpportunityList
   *      attribute
   *@exception  SQLException  Description of the Exception
   */
  private void addOpportunityList(ActionContext context, Connection db) throws SQLException {
    OpportunityList thisList = new OpportunityList();
    thisList.setOwner(this.getUserId(context));
    String contactId = (String) context.getRequest().getParameter("contactId");
    if (contactId == null) {
      contactId = (String) context.getRequest().getAttribute("contactId");
    }
    if (contactId != null) {
      thisList.setContactId(contactId);
    }
    thisList.buildList(db);
    context.getRequest().setAttribute("opportunityList", thisList);
  }
}
