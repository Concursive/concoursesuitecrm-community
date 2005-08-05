/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.communications.base;

import com.zeroio.iteam.base.FileItem;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.contacts.base.Contact;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $id:exp$
 * @created September 29, 2003
 */
public class CampaignReport {

  protected int campaignId = -1;
  protected int enteredBy = -1;
  protected int modifiedBy = -1;
  protected String subject = null;
  protected String filePath = null;
  protected String filenameToUse = null;
  protected FileItem thisItem = new FileItem();
  protected HSSFWorkbook workBook = null;
  public final static String[] OPEN_ENDED_HEADER = {"Item", "Name", "Comments", "Email Address", "Address", "Phone Number(s)", "Company", "Title"};
  public final static String[] QUANTITATIVE_HEADER = {"Item", "Name", "1", "2", "3", "4", "5", "6", "7", "Email Address", "Address", "Phone Number(s)", "Company", "Title"};
  public final static String[] QUANTITATIVE_COMMENTS_HEADER = {"Item", "Name", "1", "2", "3", "4", "5", "6", "7", "Comments", "Email Address", "Address", "Phone Number(s)", "Company", "Title"};
  public final static String[] ITEMLIST_HEADER = {"Item", "Name", "ItemList", "Email Address", "Address", "Phone Number(s)", "Company", "Title"};


  /**
   * Constructor for the CampaignReport object
   */
  public CampaignReport() {
  }


  /**
   * Sets the campaignId attribute of the CampaignReport object
   *
   * @param campaignId The new campaignId value
   */
  public void setCampaignId(int campaignId) {
    this.campaignId = campaignId;
  }


  /**
   * Sets the workBook attribute of the CampaignReport object
   *
   * @param workBook The new workBook value
   */
  public void setWorkBook(HSSFWorkbook workBook) {
    this.workBook = workBook;
  }


  /**
   * Sets the enteredBy attribute of the CampaignReport object
   *
   * @param enteredBy The new enteredBy value
   */
  public void setEnteredBy(int enteredBy) {
    this.enteredBy = enteredBy;
  }


  /**
   * Sets the modifiedBy attribute of the CampaignReport object
   *
   * @param modifiedBy The new modifiedBy value
   */
  public void setModifiedBy(int modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  /**
   * Sets the subject attribute of the CampaignReport object
   *
   * @param subject The new subject value
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }


  /**
   * Sets the filePath attribute of the CampaignReport object
   *
   * @param filePath The new filePath value
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }


  /**
   * Sets the filenameToUse attribute of the CampaignReport object
   *
   * @param filenameToUse The new filenameToUse value
   */
  public void setFilenameToUse(String filenameToUse) {
    this.filenameToUse = filenameToUse;
  }


  /**
   * Sets the thisItem attribute of the CampaignReport object
   *
   * @param thisItem The new thisItem value
   */
  public void setThisItem(FileItem thisItem) {
    this.thisItem = thisItem;
  }


  /**
   * Gets the filenameToUse attribute of the CampaignReport object
   *
   * @return The filenameToUse value
   */
  public String getFilenameToUse() {
    return filenameToUse;
  }


  /**
   * Gets the thisItem attribute of the CampaignReport object
   *
   * @return The thisItem value
   */
  public FileItem getThisItem() {
    return thisItem;
  }


  /**
   * Gets the filePath attribute of the CampaignReport object
   *
   * @return The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   * Gets the enteredBy attribute of the CampaignReport object
   *
   * @return The enteredBy value
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   * Gets the modifiedBy attribute of the CampaignReport object
   *
   * @return The modifiedBy value
   */
  public int getModifiedBy() {
    return modifiedBy;
  }


  /**
   * Gets the subject attribute of the CampaignReport object
   *
   * @return The subject value
   */
  public String getSubject() {
    return subject;
  }


  /**
   * Gets the workBook attribute of the CampaignReport object
   *
   * @return The workBook value
   */
  public HSSFWorkbook getWorkBook() {
    return workBook;
  }


  /**
   * Gets the campaignId attribute of the CampaignReport object
   *
   * @return The campaignId value
   */
  public int getCampaignId() {
    return campaignId;
  }


  /**
   * Gets the headerStyle attribute of the CampaignReport object
   *
   * @return The headerStyle value
   */
  private HSSFCellStyle getHeaderStyle() {
    //create a style for the header cell
    HSSFCellStyle headerStyle =
        workBook.createCellStyle();

    //align it left
    headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

    //set a font
    HSSFFont font = workBook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    headerStyle.setFont(font);

    return headerStyle;
  }


  /**
   * Gets the caption attribute of the CampaignReport object
   *
   * @param questionType Description of the Parameter
   * @return The caption value
   */
  private String getCaption(int questionType) {
    String caption = null;
    if (questionType == SurveyQuestion.OPEN_ENDED) {
      caption = "The following is a Summary Report for a Open Ended question.";
    } else if (questionType == SurveyQuestion.QUANT_NOCOMMENTS) {
      caption = "The following is a Summary Report for a Quantitative question.";
    } else if (questionType == SurveyQuestion.QUANT_COMMENTS) {
      caption = "The following is a Summary Report for a Quantitative question with Comments.";
    } else if (questionType == SurveyQuestion.ITEMLIST) {
      caption = "The following is a Summary Report for an ItemList.";
    }
    return caption;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void build(Connection db) throws SQLException {

    if (campaignId == -1) {
      throw new SQLException("CampaignReport -- > CampaignId not found");
    }

    try {
      //get the campaign
      Campaign thisCampaign = new Campaign(db, campaignId);

      // create a new workbook
      workBook = new HSSFWorkbook();

      int surveyId = thisCampaign.getSurveyId();
      ActiveSurveyQuestionList questionList = new ActiveSurveyQuestionList();
      if (surveyId != -1) {
        //build the questions
        questionList.setActiveSurveyId(surveyId);
        questionList.buildList(db);
      }
      //add summary report
      addSummaryReport(db, thisCampaign, questionList);

      Iterator i = questionList.iterator();
      while (i.hasNext()) {

        //create a new worksheet
        HSSFSheet sheet = workBook.createSheet();

        //add campaign header
        addCampaignHeader(sheet, thisCampaign);

        //add questions
        ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) i.next();
        addQuestionToReport(sheet, thisQuestion);

        //add possible answer if applicable to each question
        addPossibleAnswersToReport(sheet, thisQuestion);

        //add responses
        addResponsesToReport(db, sheet, thisQuestion);
      }
    } catch (Exception e) {
      System.out.println("Exception " + e.toString());
    }
  }


  /**
   * Adds the Campaign Header
   *
   * @param sheet        The feature to be added to the CampaignHeader
   *                     attribute
   * @param thisCampaign The feature to be added to the CampaignHeader
   *                     attribute
   */
  private void addCampaignHeader(HSSFSheet sheet, Campaign thisCampaign) {
    // ***************header data******************//
    //create a header row
    HSSFRow headerRow = sheet.createRow((short) 0);

    //create the header data cell
    HSSFCell headerCell =
        headerRow.createCell((short) 0);

    headerCell.setCellStyle(getHeaderStyle());

    //add the value to the header cell
    headerCell.setCellValue(thisCampaign.getName());

    //add the date
    headerRow =
        sheet.createRow((short) (sheet.getLastRowNum() + 1));

    //create the header date cell
    headerCell =
        headerRow.createCell((short) 0);

    //set the date
    SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance
        (DateFormat.SHORT, DateFormat.LONG);
    headerCell.setCellStyle(getHeaderStyle());
    headerCell.setCellValue(formatter.format(new java.util.Date()));

    //add stamp
    headerRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
    headerCell =
        headerRow.createCell((short) 0);
    headerCell.setCellStyle(getHeaderStyle());
    headerCell.setCellValue("Generated By Centric CRM");
  }


  /**
   * Adds the Summary Report sheet
   *
   * @param db           The feature to be added to the SummaryReport
   *                     attribute
   * @param campaign     The feature to be added to the SummaryReport
   *                     attribute
   * @param questionList The feature to be added to the SummaryReport
   *                     attribute
   * @throws SQLException Description of the Exception
   */
  public void addSummaryReport(Connection db, Campaign campaign, ActiveSurveyQuestionList questionList) throws SQLException {

    //create a new worksheet
    HSSFSheet sheet = workBook.createSheet();

    //add campaign header
    addCampaignHeader(sheet, campaign);

    //total Recipients
    HSSFRow thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 2));

    HSSFCell thisCell = thisRow.createCell((short) 0);
    thisCell.setCellValue("Total Recipients");

    thisCell = thisRow.createCell((short) 1);
    thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    thisCell.setCellValue(campaign.getRecipientCount());

    //total responses
    thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));

    thisCell = thisRow.createCell((short) 0);
    thisCell.setCellValue("Total Responses");

    thisCell = thisRow.createCell((short) 1);
    thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    thisCell.setCellValue(campaign.getResponseCount());

    //last response received(time)
    thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));

    thisCell = thisRow.createCell((short) 0);
    thisCell.setCellValue("Last Response Received");
    thisCell = thisRow.createCell((short) 1);
    thisCell.setCellValue(campaign.getLastResponseString());

    if (!questionList.isEmpty()) {
      //survey questions
      thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 3));

      thisCell = thisRow.createCell((short) 0);

      //create a style for the survey details
      HSSFCellStyle headerStyle = workBook.createCellStyle();
      HSSFFont font = workBook.createFont();
      font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
      thisCell.setCellStyle(headerStyle);
      thisCell.setCellValue("Survey Details");

      //add questions
      addQuestionSummary(db, sheet, questionList);

      //add note
      thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 3));

      thisCell = thisRow.createCell((short) 0);
      thisCell.setCellValue(
          "Note: Detailed reports for each question are available in the corresponding sheets contained in this report.");
    }
  }


  /**
   * Adds Summary Report for each question
   *
   * @param db           The feature to be added to the QuestionsSummary
   *                     attribute
   * @param sheet        The feature to be added to the QuestionsSummary
   *                     attribute
   * @param questionList The feature to be added to the QuestionsSummary
   *                     attribute
   * @throws SQLException Description of the Exception
   */
  public void addQuestionSummary(Connection db, HSSFSheet sheet, ActiveSurveyQuestionList questionList) throws SQLException {

    int count = 0;
    Iterator i = questionList.iterator();
    while (i.hasNext()) {
      ActiveSurveyQuestion thisQuestion = (ActiveSurveyQuestion) i.next();
      //build answers for the question
      PreparedStatement pst = null;
      SurveyAnswerList answerList = new SurveyAnswerList();
      answerList.setQuestionId(thisQuestion.getId());
      ResultSet rs = answerList.queryList(db, pst);
      while (rs.next()) {
        SurveyAnswer thisAnswer = buildResponseRecord(rs);
        answerList.add(thisAnswer);
      }
      rs.close();
      if (pst != null) {
        pst.close();
      }
      thisQuestion.setAnswerList(answerList);

      //total Recipients
      int spaceBtwQuestions = 3;
      if (count == 0) {
        spaceBtwQuestions = 1;
      }
      HSSFRow thisRow = sheet.createRow(
          (short) (sheet.getLastRowNum() + spaceBtwQuestions));

      //create a style for the questions
      HSSFCellStyle questionStyle =
          workBook.createCellStyle();

      HSSFFont font = workBook.createFont();
      font.setColor(HSSFFont.COLOR_RED);
      questionStyle.setFont(font);

      HSSFCell thisCell = thisRow.createCell((short) 0);

      thisCell.setCellStyle(questionStyle);
      thisCell.setCellValue((++count) + ". " + thisQuestion.getDescription());

      //add type
      HSSFRow typeRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
      thisCell = typeRow.createCell((short) 0);
      thisCell.setCellValue("Type: " + thisQuestion.getTypeString());

      //determine the headers based on the question type
      int type = thisQuestion.getType();

      if (type == SurveyQuestion.OPEN_ENDED) {
        thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
        thisCell = thisRow.createCell((short) 0);
        thisCell.setCellValue("Response: " + answerList.size() + " comments");
      } else if (type == SurveyQuestion.QUANT_NOCOMMENTS) {
        thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
        thisCell = thisRow.createCell((short) 0);
        thisCell.setCellValue(
            "Response: Avg " + thisQuestion.getAverageValue());
      } else if (type == SurveyQuestion.QUANT_COMMENTS) {
        thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
        thisCell = thisRow.createCell((short) 0);
        thisCell.setCellValue(
            "Response: Avg " + thisQuestion.getAverageValue());
      } else if (type == SurveyQuestion.ITEMLIST) {
        //add itemlist header row
        thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));

        //add item column
        thisCell = thisRow.createCell((short) 0);
        thisCell.setCellValue("Item");

        //add response column
        thisCell = typeRow.createCell((short) (thisRow.getLastCellNum() + 1));
        thisCell.setCellValue("Response");

        //add response
        int itemCount = 0;
        Iterator j = thisQuestion.getItemList().iterator();
        while (j.hasNext()) {
          ActiveSurveyQuestionItem tmp = (ActiveSurveyQuestionItem) j.next();
          thisRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
          //add item description
          thisCell = thisRow.createCell((short) 0);
          thisCell.setCellValue((++itemCount) + ". " + tmp.getDescription());

          //get count of answers
          int answerCount = ActiveSurveyAnswerItemList.getItemCount(
              db, tmp.getId());

          //add item description
          thisCell = thisRow.createCell(
              (short) (thisRow.getLastCellNum() + 1));
          thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
          thisCell.setCellValue(answerCount);
        }
      }
    }
  }


  /**
   * Adds question description to the report
   *
   * @param sheet    The feature to be added to the QuestionToReport attribute
   * @param question The feature to be added to the QuestionToReport attribute
   */
  public void addQuestionToReport(HSSFSheet sheet, ActiveSurveyQuestion question) {

    //add caption
    HSSFRow headerRow = sheet.createRow((short) (sheet.getLastRowNum() + 2));
    HSSFCell headerCell = headerRow.createCell((short) 0);
    headerCell.setCellStyle(getHeaderStyle());
    headerCell.setCellValue(getCaption(question.getType()));

    HSSFRow questionRow = sheet.createRow((short) (sheet.getLastRowNum() + 2));

    //create a style for this header columns
    HSSFCellStyle columnHeaderStyle =
        workBook.createCellStyle();

    columnHeaderStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);

    columnHeaderStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);

    HSSFFont font = workBook.createFont();
    font.setColor(HSSFFont.COLOR_RED);
    columnHeaderStyle.setFont(font);

    HSSFCell colHeading1 =
        questionRow.createCell((short) 0);

    colHeading1.setCellStyle(columnHeaderStyle);
    colHeading1.setCellValue(question.getDescription());
  }


  /**
   * Adds all possible answers to a question(if it applies)
   *
   * @param sheet    The feature to be added to the PossibleAnswersToReport
   *                 attribute
   * @param question The feature to be added to the PossibleAnswersToReport
   *                 attribute
   */
  public void addPossibleAnswersToReport(HSSFSheet sheet, ActiveSurveyQuestion question) {
    //create a header row
    HSSFRow answerTypeRow = sheet.createRow(
        (short) (sheet.getLastRowNum() + 2));

    HSSFCell colHeading1 =
        answerTypeRow.createCell((short) 0);

    if (question.getType() == SurveyQuestion.ITEMLIST) {
      HSSFRow optionsRow = sheet.createRow(
          (short) (sheet.getLastRowNum() + 1));
      //add index for this entry
      HSSFCell options =
          optionsRow.createCell((short) 0);
      options.setCellValue("Options");

      ActiveSurveyQuestionItemList itemList = question.getItemList();
      int index = 0;
      Iterator i = itemList.iterator();
      while (i.hasNext()) {
        ActiveSurveyQuestionItem tmp = (ActiveSurveyQuestionItem) i.next();
        HSSFRow itemListRow = sheet.createRow(
            (short) (sheet.getLastRowNum() + 1));

        //add the item description
        HSSFCell thisItem =
            itemListRow.createCell((short) 0);
        thisItem.setCellValue((++index) + ". " + tmp.getDescription());
      }
    }
    colHeading1.setCellValue(question.getTypeString());
  }


  /**
   * Adds response to a question
   *
   * @param db       The feature to be added to the ResponsesToReport
   *                 attribute
   * @param sheet    The feature to be added to the ResponsesToReport
   *                 attribute
   * @param question The feature to be added to the ResponsesToReport
   *                 attribute
   * @throws SQLException Description of the Exception
   */
  public void addResponsesToReport(Connection db, HSSFSheet sheet, ActiveSurveyQuestion question) throws SQLException {

    //build answers for the question(NOTE: this is used for generating the detailed responses for each question also)
    if (question.getAnswerList() == null) {
      PreparedStatement pst = null;
      SurveyAnswerList answerList = new SurveyAnswerList();
      answerList.setQuestionId(question.getId());
      ResultSet rs = answerList.queryList(db, pst);
      while (rs.next()) {
        SurveyAnswer thisAnswer = buildResponseRecord(rs);
        answerList.add(thisAnswer);
      }
      rs.close();
      if (pst != null) {
        pst.close();
      }
      question.setAnswerList(answerList);
    }
    //determine the headers based on the question type
    int type = question.getType();
    if (type == SurveyQuestion.OPEN_ENDED) {
      addOpenEndedResponse(db, sheet, question);
    } else if (type == SurveyQuestion.QUANT_NOCOMMENTS) {
      addQuantResponse(db, sheet, question);
    } else if (type == SurveyQuestion.QUANT_COMMENTS) {
      addQuantWithCommentsResponse(db, sheet, question);
    } else if (type == SurveyQuestion.ITEMLIST) {
      addItemListResponse(db, sheet, question);
    }
  }


  /**
   * Adds an Open Ended response
   *
   * @param db       The feature to be added to the OpenEndedResponse
   *                 attribute
   * @param sheet    The feature to be added to the OpenEndedResponse
   *                 attribute
   * @param question The feature to be added to the OpenEndedResponse
   *                 attribute
   * @throws SQLException Description of the Exception
   */
  public void addOpenEndedResponse(Connection db, HSSFSheet sheet, ActiveSurveyQuestion question) throws SQLException {
    //add header
    addHeader(sheet, OPEN_ENDED_HEADER);
    Iterator i = question.getAnswerList().iterator();
    int itemNumber = 0;
    while (i.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) i.next();
      Contact thisContact = new Contact();
      thisContact.setBuildDetails(true);
      thisContact.queryRecord(db, thisAnswer.getContactId());

      //add data row
      HSSFRow dataRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
      HSSFCell thisCell = null;

      int startIndex = 0;

      //item number
      thisCell = dataRow.createCell((short) startIndex);
      thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      thisCell.setCellValue(++itemNumber);

      //name
      thisCell = dataRow.createCell((short) (++startIndex));
      thisCell.setCellValue(thisContact.getNameLastFirst());

      //comments
      thisCell = dataRow.createCell((short) (++startIndex));
      thisCell.setCellValue(thisAnswer.getComments());

      //add contact details
      addContactDetails(dataRow, thisContact);
    }
  }


  /**
   * Adds a Response to a Quantitative question
   *
   * @param db       The feature to be added to the QuantResponse
   *                 attribute
   * @param sheet    The feature to be added to the QuantResponse
   *                 attribute
   * @param question The feature to be added to the QuantResponse
   *                 attribute
   * @throws SQLException Description of the Exception
   */
  public void addQuantResponse(Connection db, HSSFSheet sheet, ActiveSurveyQuestion question) throws SQLException {
    //add header
    addHeader(sheet, QUANTITATIVE_HEADER);

    Iterator i = question.getAnswerList().iterator();
    int itemNumber = 0;
    while (i.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) i.next();
      Contact thisContact = new Contact();
      thisContact.setBuildDetails(true);
      thisContact.queryRecord(db, thisAnswer.getContactId());

      //add data row
      HSSFRow dataRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
      HSSFCell thisCell = null;

      int startIndex = 0;

      //item number
      thisCell = dataRow.createCell((short) startIndex);
      thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      thisCell.setCellValue(++itemNumber);

      //name
      thisCell = dataRow.createCell((short) (++startIndex));
      thisCell.setCellValue(thisContact.getNameLastFirst());

      //quantitative answer
      for (int j = 1; j <= SurveyQuestion.QUANTITATIVE_SELECT_SIZE; j++) {
        thisCell = dataRow.createCell((short) (++startIndex));
        thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        thisCell.setCellValue((j == thisAnswer.getQuantAns() ? 1 : 0));
      }

      //add contact details
      addContactDetails(dataRow, thisContact);
    }
  }


  /**
   * Adds a Response to a Quantitative question with Comments
   *
   * @param db       The feature to be added to the
   *                 QuantWithCommentsResponse attribute
   * @param sheet    The feature to be added to the
   *                 QuantWithCommentsResponse attribute
   * @param question The feature to be added to the
   *                 QuantWithCommentsResponse attribute
   * @throws SQLException Description of the Exception
   */
  public void addQuantWithCommentsResponse(Connection db, HSSFSheet sheet, ActiveSurveyQuestion question) throws SQLException {
    //add header
    addHeader(sheet, QUANTITATIVE_COMMENTS_HEADER);

    Iterator i = question.getAnswerList().iterator();
    int itemNumber = 0;
    while (i.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) i.next();
      Contact thisContact = new Contact();
      thisContact.setBuildDetails(true);
      thisContact.queryRecord(db, thisAnswer.getContactId());

      //add data row
      HSSFRow dataRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
      HSSFCell thisCell = null;

      int startIndex = 0;

      //item number
      thisCell = dataRow.createCell((short) startIndex);
      thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      thisCell.setCellValue(++itemNumber);

      //name
      thisCell = dataRow.createCell((short) (++startIndex));
      thisCell.setCellValue(thisContact.getNameLastFirst());

      //quantitative answer
      for (int j = 1; j <= SurveyQuestion.QUANTITATIVE_SELECT_SIZE; j++) {
        thisCell = dataRow.createCell((short) (++startIndex));
        thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        thisCell.setCellValue((j == thisAnswer.getQuantAns() ? 1 : 0));
      }

      //comments
      thisCell = dataRow.createCell((short) (++startIndex));
      thisCell.setCellValue(thisAnswer.getComments());

      //add contact details
      addContactDetails(dataRow, thisContact);
    }
  }


  /**
   * Adds a Response to an ItemList
   *
   * @param db       The feature to be added to the ItemListResponse
   *                 attribute
   * @param sheet    The feature to be added to the ItemListResponse
   *                 attribute
   * @param question The feature to be added to the ItemListResponse
   *                 attribute
   * @throws SQLException Description of the Exception
   */
  public void addItemListResponse(Connection db, HSSFSheet sheet, ActiveSurveyQuestion question) throws SQLException {
    PreparedStatement pst = null;
    //add header
    addItemListHeader(sheet, question);

    ActiveSurveyAnswerItemList answers = null;
    int itemNumber = 0;
    Iterator i = question.getAnswerList().iterator();
    while (i.hasNext()) {
      SurveyAnswer thisAnswer = (SurveyAnswer) i.next();
      answers = new ActiveSurveyAnswerItemList();
      answers.setContactId(thisAnswer.getContactId());
      answers.setAnswerId(thisAnswer.getId());
      ResultSet rs = answers.queryList(db, pst);
      while (rs.next()) {
        SurveyAnswerItem thisItem = buildItemRecord(rs);
        answers.add(thisItem);
      }
      if (pst != null) {
        pst.close();
      }
      rs.close();

      //build contact
      Contact thisContact = new Contact();
      thisContact.setBuildDetails(true);
      thisContact.queryRecord(db, thisAnswer.getContactId());

      //add data row
      HSSFRow dataRow = sheet.createRow((short) (sheet.getLastRowNum() + 1));
      HSSFCell thisCell = null;

      int startIndex = 0;

      //item number
      thisCell = dataRow.createCell((short) startIndex);
      thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
      thisCell.setCellValue(++itemNumber);

      //name
      thisCell = dataRow.createCell((short) (++startIndex));
      thisCell.setCellValue(thisContact.getNameLastFirst());

      //item selections
      Iterator j = question.getItemList().iterator();
      while (j.hasNext()) {
        ActiveSurveyQuestionItem tmp = (ActiveSurveyQuestionItem) j.next();
        thisCell = dataRow.createCell((short) (++startIndex));
        thisCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        if (answers.hasItem(tmp.getId())) {
          thisCell.setCellValue("1");
        } else {
          thisCell.setCellValue("0");
        }
      }

      //add contact details
      addContactDetails(dataRow, thisContact);
    }
  }


  /**
   * Adds the details of the recipient to the report
   *
   * @param dataRow     The feature to be added to the ContactDetails attribute
   * @param thisContact The feature to be added to the ContactDetails attribute
   */
  private void addContactDetails(HSSFRow dataRow, Contact thisContact) {
    int startIndex = dataRow.getLastCellNum() + 1;
    //email address
    HSSFCell thisCell = dataRow.createCell((short) startIndex);
    thisCell.setCellValue(thisContact.getPrimaryEmailAddress());

    //address
    thisCell = dataRow.createCell((short) (++startIndex));
    thisCell.setCellValue(thisContact.getPrimaryAddress().toString());

    //phone
    thisCell = dataRow.createCell((short) (++startIndex));
    thisCell.setCellValue(thisContact.getPrimaryPhoneNumber());

    //company
    thisCell = dataRow.createCell((short) (++startIndex));
    thisCell.setCellValue(thisContact.getCompany());

    //title
    thisCell = dataRow.createCell((short) (++startIndex));
    thisCell.setCellValue(thisContact.getTitle());
  }


  /**
   * Adds the header for the Open Ended and Quantitative questions
   *
   * @param sheet      The feature to be added to the Header attribute
   * @param headerType The feature to be added to the Header attribute
   */
  public void addHeader(HSSFSheet sheet, String[] headerType) {
    HSSFRow headerRow = sheet.createRow((short) (sheet.getLastRowNum() + 2));
    HSSFCell headerValue = null;
    int columnIndex = 0;
    for (int i = 0; i < headerType.length; i++) {
      headerValue = headerRow.createCell((short) columnIndex++);
      headerValue.setCellValue(headerType[i]);
    }
  }


  /**
   * Adds the header for an ItemList
   *
   * @param sheet    The feature to be added to the ItemListHeader attribute
   * @param question The feature to be added to the ItemListHeader attribute
   */
  public void addItemListHeader(HSSFSheet sheet, ActiveSurveyQuestion question) {
    HSSFRow headerRow = sheet.createRow((short) (sheet.getLastRowNum() + 2));
    HSSFCell headerValue = null;
    int columnIndex = 0;
    for (int i = 0; i < ITEMLIST_HEADER.length; i++) {
      headerValue = headerRow.createCell((short) columnIndex++);
      if ("ItemList".equals(ITEMLIST_HEADER[i])) {
        Iterator j = question.getItemList().iterator();
        while (j.hasNext()) {
          ActiveSurveyQuestionItem thisItem = (ActiveSurveyQuestionItem) j.next();
          headerValue.setCellValue(thisItem.getDescription());
          if (j.hasNext()) {
            headerValue = headerRow.createCell((short) columnIndex++);
          }
        }
      } else {
        headerValue.setCellValue(ITEMLIST_HEADER[i]);
      }
    }
  }


  /**
   * Save and Insert the report
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public boolean saveAndInsert(Connection db) throws Exception {
    int fileSize = save();
    thisItem.setLinkModuleId(Constants.DOCUMENTS_COMMUNICATIONS);
    thisItem.setLinkItemId(campaignId);
    thisItem.setEnteredBy(enteredBy);
    thisItem.setModifiedBy(modifiedBy);
    thisItem.setSubject("Exported Campaign Report");
    thisItem.setClientFilename("campaignreports-" + filenameToUse + ".xls");
    thisItem.setFilename(filenameToUse + "-" + enteredBy);
    thisItem.setVersion(1.0);
    thisItem.setSize(fileSize);
    thisItem.insert(db);
    return true;
  }


  /**
   * Save the report
   *
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public int save() throws Exception {
    this.generateFilename();
    File f = new File(filePath);
    f.mkdirs();
    FileOutputStream stream = new
        FileOutputStream(filePath + filenameToUse + "-" + enteredBy);
    workBook.write(stream);
    File fileLink = new File(filePath + filenameToUse + "-" + enteredBy);
    return ((int) fileLink.length());
  }


  /**
   * Generate the filename of the report
   *
   * @return Description of the Return Value
   * @throws Exception Description of the Exception
   */
  public String generateFilename() throws Exception {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    filenameToUse = formatter.format(new java.util.Date());
    return filenameToUse;
  }


  /**
   * Build a response record from the ResultSet
   *
   * @param rs Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected SurveyAnswer buildResponseRecord(ResultSet rs) throws SQLException {
    SurveyAnswer thisAnswer = new SurveyAnswer();
    thisAnswer.setId(rs.getInt("answer_id"));
    thisAnswer.setResponseId(rs.getInt("response_id"));
    thisAnswer.setComments(rs.getString("comments"));
    thisAnswer.setQuantAns(rs.getInt("quant_ans"));
    thisAnswer.setTextAns(rs.getString("text_ans"));
    thisAnswer.setEntered(rs.getTimestamp("entered"));
    thisAnswer.setContactId(rs.getInt("contactid"));
    return thisAnswer;
  }


  /**
   * Build a Item Record from the ResultSet
   *
   * @param rs Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  protected SurveyAnswerItem buildItemRecord(ResultSet rs) throws SQLException {
    SurveyAnswerItem thisAnswer = new SurveyAnswerItem();
    thisAnswer.setId(rs.getInt("item_id"));
    return thisAnswer;
  }
}

