/*
 *  Copyright(c) 2005 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.admin.jobs;

import com.darkhorseventures.database.ConnectionPool;
import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.ProjectList;
import org.aspcfs.controller.ApplicationPrefs;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.assets.base.AssetList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.communications.base.CampaignList;
import org.aspcfs.modules.contacts.base.CallList;
import org.aspcfs.modules.contacts.base.ContactList;
import org.aspcfs.modules.documents.base.DocumentStoreList;
import org.aspcfs.modules.pipeline.base.OpportunityList;
import org.aspcfs.modules.products.base.ProductCatalogList;
import org.aspcfs.modules.quotes.base.QuoteList;
import org.aspcfs.modules.relationships.base.RelationshipList;
import org.aspcfs.modules.servicecontracts.base.ServiceContractList;
import org.aspcfs.modules.system.base.Site;
import org.aspcfs.modules.system.base.SiteList;
import org.aspcfs.modules.tasks.base.TaskList;
import org.aspcfs.modules.troubletickets.base.TicketList;
import org.aspcfs.utils.SiteUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;


/**
 * A job for deleting records that have been marked as Trash.
 *
 * @author mrajkowski
 * @version $Id$
 * @created Jun 29, 2005
 */

public class DeleteTrashJob implements StatefulJob {

  public void execute(JobExecutionContext context) throws JobExecutionException {
    SchedulerContext schedulerContext = null;
    ConnectionPool cp = null;
    Connection db = null;
    try {
      // Prepare items from context
      schedulerContext = context.getScheduler().getContext();
      ApplicationPrefs prefs = (ApplicationPrefs) schedulerContext.get(
          "ApplicationPrefs");
      cp = (ConnectionPool) schedulerContext.get("ConnectionPool");
      Hashtable systemStatusList = (Hashtable) schedulerContext.get(
          "SystemStatus");
      if (System.getProperty("DEBUG") != null) {
        System.out.println("DeleteTrashJob-> Cleaning up removed users...");
      }
      SiteList siteList = SiteUtils.getSiteList(prefs, cp);
      Iterator i = siteList.iterator();
      while (i.hasNext()) {
        Site thisSite = (Site) i.next();
        SystemStatus thisSystem = (SystemStatus) systemStatusList.get(
            thisSite.getConnectionElement().getUrl());
        if (thisSite != null) {
          db = cp.getConnection(thisSite.getConnectionElement());
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "DeleteTrashJob-> Cleaning up the site " + thisSite.getSiteCode() + " at " + (new Timestamp(
                    Calendar.getInstance().getTimeInMillis()).toString()));
          }
          // Delete trashed items
          String fileLibraryPath = thisSystem.getFileLibraryPath();
          CampaignList campaignList = new CampaignList();
          campaignList.setIncludeOnlyTrashed(true);
          campaignList.buildList(db);
          campaignList.delete(db, fileLibraryPath);
          
          //Calls
          CallList callList = new CallList();
          callList.setIncludeOnlyTrashed(true);
          callList.buildList(db);
          callList.delete(db);
          
          //Tasks
          TaskList taskList = new TaskList();
          taskList.setIncludeOnlyTrashed(true);
          taskList.buildList(db);
          taskList.delete(db);

          ProjectList projectList = new ProjectList();
          projectList.setIncludeOnlyTrashed(true);
          projectList.buildList(db);
          projectList.delete(db, fileLibraryPath);

          DocumentStoreList documentStoreList = new DocumentStoreList();
          documentStoreList.setIncludeOnlyTrashed(true);
          documentStoreList.buildList(db);
          documentStoreList.delete(db, fileLibraryPath);

          TicketList ticketList = new TicketList();
          ticketList.setIncludeOnlyTrashed(true);
          ticketList.buildList(db);
          ticketList.delete(db, fileLibraryPath);

          QuoteList quoteList = new QuoteList();
          quoteList.setIncludeOnlyTrashed(true);
          quoteList.buildList(db);
          quoteList.delete(db);

          AssetList assetList = new AssetList();
          assetList.setIncludeOnlyTrashed(true);
          assetList.buildList(db);
          assetList.delete(db, fileLibraryPath);

          ServiceContractList serviceContractList = new ServiceContractList();
          serviceContractList.setIncludeOnlyTrashed(true);
          serviceContractList.buildList(db);
          serviceContractList.delete(db, fileLibraryPath);

          ProductCatalogList productCatalogList = new ProductCatalogList();
          productCatalogList.setIncludeOnlyTrashed(true);
          productCatalogList.buildList(db);
          productCatalogList.delete(db, fileLibraryPath);

          OpportunityList opportunityList = new OpportunityList();
          opportunityList.setIncludeOnlyTrashed(true);
          opportunityList.buildList(db);
          opportunityList.delete(db, null, fileLibraryPath);

          RelationshipList relationships = new RelationshipList();
          relationships.setIncludeOnlyTrashed(true);
          relationships.buildList(db);
          relationships.delete(db);

          ContactList contactList = new ContactList();
          contactList.setIncludeOnlyTrashed(true);
//          contactList.setEmployeesOnly(Constants.FALSE); //Employees are trashed too
          contactList.setLeadsOnly(Constants.FALSE);
          contactList.buildList(db);
          if (System.getProperty("DEBUG") != null) {
            System.out.println(
                "DeleteTrashJob-> The contact list size is " + contactList.size());
          }
          contactList.delete(db, fileLibraryPath, true);

          OrganizationList organizationList = new OrganizationList();
          organizationList.setIncludeOnlyTrashed(true);
          organizationList.buildList(db);
          organizationList.delete(
              db, (ActionContext) null, fileLibraryPath, true);
          cp.free(db);
          db = null;
        }
      }
    } catch (Exception e) {
      throw new JobExecutionException(e.getMessage());
    } finally {
      if (cp != null && db != null) {
        cp.free(db);
      }
    }
  }
}
