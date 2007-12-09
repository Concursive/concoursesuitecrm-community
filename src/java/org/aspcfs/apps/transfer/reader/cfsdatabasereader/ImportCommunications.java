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
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import org.aspcfs.apps.transfer.DataRecord;
import org.aspcfs.apps.transfer.DataWriter;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.communications.base.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Processes Communications
 *
 * @author mrajkowski
 * @version $Id: ImportCommunications.java,v 1.35 2003/01/14 22:53:33 akhi_m
 *          Exp $
 * @created January 14, 2003
 */
public class ImportCommunications implements CFSDatabaseReaderImportModule {

  DataWriter writer = null;
  PropertyMapList mappings = null;


  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException {
    this.writer = writer;
    this.mappings = mappings;
    boolean processOK = true;

    logger.info("ImportCommunications-> Inserting Search Criteria List");
    writer.setAutoCommit(false);
    SearchCriteriaListList searchCriteriaList = new SearchCriteriaListList();
    searchCriteriaList.setEnabled(Constants.UNDEFINED);
    searchCriteriaList.buildList(db);
    mappings.saveList(writer, searchCriteriaList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Campaign List");
    writer.setAutoCommit(false);
    CampaignList campaigns = new CampaignList();
    campaigns.buildList(db);
    mappings.saveList(writer, campaigns, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Campaign Run");
    writer.setAutoCommit(false);
    CampaignRunList campaignRun = new CampaignRunList();
    campaignRun.buildList(db);
    mappings.saveList(writer, campaignRun, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Excluded Recipients");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "excludedRecipient");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Campaign List Groups");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "campaignListGroups");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Active Campaign Groups");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "activeCampaignGroups");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Scheduled Recipient Records");
    writer.setAutoCommit(false);
    ScheduledRecipientList scheduledRecipient = new ScheduledRecipientList();
    scheduledRecipient.buildList(db);
    mappings.saveList(writer, scheduledRecipient, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Survey List");
    writer.setAutoCommit(false);
    SurveyList surveys = new SurveyList();
    surveys.buildList(db);
    mappings.saveList(writer, surveys, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Campaign Survey Link Records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "campaignSurveyLink");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Survey Question List");
    writer.setAutoCommit(false);
    SurveyQuestionList surveyQuestions = new SurveyQuestionList();
    surveyQuestions.buildList(db);
    this.saveSurveyQuestions(db, surveyQuestions);
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Survey Item List");
    writer.setAutoCommit(false);
    ItemList surveyItems = new ItemList();
    surveyItems.buildList(db);
    mappings.saveList(writer, surveyItems, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Active Survey Records");
    writer.setAutoCommit(false);
    ActiveSurveyList activeSurveys = new ActiveSurveyList();
    activeSurveys.buildList(db);
    mappings.saveList(writer, activeSurveys, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Survey Question List");
    writer.setAutoCommit(false);
    ActiveSurveyQuestionList activeSurveyQuestions = new ActiveSurveyQuestionList();
    activeSurveyQuestions.buildList(db);
    this.saveActiveSurveyQuestions(db, activeSurveyQuestions);
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Survey Question Item List");
    writer.setAutoCommit(false);
    ActiveSurveyQuestionItemList activeSurveyQuestionItems = new ActiveSurveyQuestionItemList();
    activeSurveyQuestionItems.buildList(db);
    this.saveActiveSurveyQuestionsItems(db, activeSurveyQuestionItems);
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Active Survey Response List");
    writer.setAutoCommit(false);
    SurveyResponseList activeSurveyResponses = new SurveyResponseList();
    activeSurveyResponses.buildList(db);
    mappings.saveList(writer, activeSurveyResponses, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Active Survey Answer List");
    writer.setAutoCommit(false);
    SurveyAnswerList activeSurveyAnswers = new SurveyAnswerList();
    activeSurveyAnswers.buildList(db);
    mappings.saveList(writer, activeSurveyAnswers, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Active Survey Answer Item List");
    writer.setAutoCommit(false);
    ActiveSurveyAnswerItemList activeSurveyAnswerItems = new ActiveSurveyAnswerItemList();
    activeSurveyAnswerItems.buildList(db);
    mappings.saveList(writer, activeSurveyAnswerItems, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Active Survey Answer Averages");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "activeSurveyAnswerAvg");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Field Types");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "fieldTypes");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Search Field Elements");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "searchFieldElement");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Messages");
    writer.setAutoCommit(false);
    MessageList messageList = new MessageList();
    messageList.buildList(db);
    mappings.saveList(writer, messageList, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Message Template Records");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "messageTemplate");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Saved Criteria Elements");
    processOK = ImportLookupTables.saveCustomLookupList(
        writer, db, mappings, "savedCriteriaElement");
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Contact Messages");
    writer.setAutoCommit(false);
    ContactMessageList contactMessages = new ContactMessageList();
    contactMessages.buildList(db);
    mappings.saveList(writer, contactMessages, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    logger.info("ImportCommunications-> Inserting Campaign User Group Maps");
    writer.setAutoCommit(false);
    CampaignUserGroupMapList campaignUserGroupMaps = new CampaignUserGroupMapList();
    campaignUserGroupMaps.buildList(db);
    mappings.saveList(writer, campaignUserGroupMaps, "insert");
    processOK = writer.commit();
    if (!processOK) {
      return false;
    }

    return true;
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param questions Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveSurveyQuestions(Connection db, SurveyQuestionList questions) throws SQLException {
    Iterator i = questions.iterator();
    while (i.hasNext()) {
      SurveyQuestion thisQuestion = (SurveyQuestion) i.next();
      DataRecord thisRecord = mappings.createDataRecord(thisQuestion, "insert");
      thisRecord.addField("recordSurveyItems", "false");
      writer.save(thisRecord);
    }
  }


  /**
   * Description of the Method
   *
   * @param db        Description of the Parameter
   * @param questions Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveActiveSurveyQuestions(Connection db, ActiveSurveyQuestionList questions) throws SQLException {
    Iterator i = questions.iterator();
    while (i.hasNext()) {
      ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) i.next();
      DataRecord thisRecord = mappings.createDataRecord(thisQuestion, "insert");
      thisRecord.addField("recordItems", "false");
      writer.save(thisRecord);
    }
  }


  /**
   * Description of the Method
   *
   * @param db    Description of the Parameter
   * @param items Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void saveActiveSurveyQuestionsItems(Connection db, ActiveSurveyQuestionItemList items) throws SQLException {
    Iterator i = items.iterator();
    while (i.hasNext()) {
      ActiveSurveyQuestionItem thisItem = (ActiveSurveyQuestionItem) i.next();
      DataRecord thisRecord = mappings.createDataRecord(thisItem, "insert");
      thisRecord.addField("populateAvg", "false");
      writer.save(thisRecord);
    }
  }
}

