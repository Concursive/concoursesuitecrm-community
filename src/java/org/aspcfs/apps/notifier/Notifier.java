package com.darkhorseventures.apps;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.text.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.cfsmodule.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 *  Application that processes various kinds of Alerts in CFS, generating
 *  notifications for users.
 *
 *@author     matt
 *@created    October 16, 2001
 *@version    $Id$
 */
public class Notifier extends ReportBuilder {

  Hashtable config = new Hashtable();


  /**
   *  Constructor for the Notifier object
   *
   *@since
   */
  public Notifier() { }


  /**
   *  Starts the process. Processes all of the enabled sites in the database.
   *
   *@param  args  Description of Parameter
   *@since
   */
  public static void main(String args[]) {

    Notifier thisNotifier = new Notifier();
    thisNotifier.loadConfig();
    
    System.out.println("Generating and sending reports... ");

    thisNotifier.baseName = (String)thisNotifier.config.get("GKHOST");
    thisNotifier.dbUser = (String)thisNotifier.config.get("GKUSER");
    thisNotifier.dbPass = (String)thisNotifier.config.get("GKUSERPW");

    if (thisNotifier.baseName.equals("debug")) {
      thisNotifier.sendAdminReport("Notifier manual sendmail test");
    } else {
      try {
        Class.forName((String)thisNotifier.config.get("DatabaseDriver"));

        Vector siteList = new Vector();

        Connection dbSites = DriverManager.getConnection(
            thisNotifier.baseName, thisNotifier.dbUser, thisNotifier.dbPass);
        Statement stSites = dbSites.createStatement();
        ResultSet rsSites = stSites.executeQuery(
            "SELECT * " +
            "FROM sites " +
            "WHERE enabled = true ");
        while (rsSites.next()) {
          Hashtable siteInfo = new Hashtable();
          siteInfo.put("driver", rsSites.getString("driver"));
          siteInfo.put("host", rsSites.getString("dbhost"));
          siteInfo.put("name", rsSites.getString("dbname"));
          siteInfo.put("port", rsSites.getString("dbport"));
          siteInfo.put("user", rsSites.getString("dbuser"));
          siteInfo.put("password", rsSites.getString("dbpw"));
          siteInfo.put("sitecode", rsSites.getString("sitecode"));
          siteList.add(siteInfo);
        }
        rsSites.close();
        stSites.close();
        dbSites.close();

        Iterator i = siteList.iterator();
        while (i.hasNext()) {

          Hashtable siteInfo = (Hashtable) i.next();
          Class.forName((String) siteInfo.get("driver"));
          Connection db = DriverManager.getConnection(
              (String) siteInfo.get("host") + ":" +
              (String) siteInfo.get("port") + "/" +
              (String) siteInfo.get("name"),
              (String) siteInfo.get("user"),
              (String) siteInfo.get("password"));
          thisNotifier.baseName = (String) siteInfo.get("sitecode");

          System.out.println("Running Alerts...");
          thisNotifier.output.append(thisNotifier.buildOpportunityAlerts(db));
          //thisNotifier.output.append(thisNotifier.buildCallAlerts(db));
          thisNotifier.output.append("<br><hr><br>");

          System.out.println("Running Communications...");
          thisNotifier.output.append(thisNotifier.buildCommunications(db, (String) siteInfo.get("name")));
          thisNotifier.output.append("<br><hr><br>");

          db.close();
        }

        System.out.println(thisNotifier.output.toString());
        //thisNotifier.sendAdminReport(thisNotifier.output.toString());
        java.util.Date end = new java.util.Date();
      } catch (Exception exc) {
        System.out.println("Sending error email...");
        //thisNotifier.sendAdminReport(exc.toString());
        System.err.println("BuildReport Error: " + exc.toString());
      }
      System.exit(0);
    }
  }


  /**
   *  Scans Opportunities for an Alert that is due today. The notification is
   *  stored so that repeat notifications are not sent.
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  private String buildOpportunityAlerts(Connection db) throws SQLException {
    Report thisReport = new Report();
    thisReport.setBorderSize(0);
    thisReport.addColumn("User");

    Calendar thisCalendar = Calendar.getInstance();

    OpportunityList thisList = new OpportunityList();
    java.sql.Date thisDate = new java.sql.Date(System.currentTimeMillis());
    thisDate = thisDate.valueOf(
        thisCalendar.get(Calendar.YEAR) + "-" +
        (thisCalendar.get(Calendar.MONTH) + 1) + "-" +
        thisCalendar.get(Calendar.DAY_OF_MONTH));
    System.out.println("Notifier-> " +
        thisCalendar.get(Calendar.YEAR) + "-" +
        (thisCalendar.get(Calendar.MONTH) + 1) + "-" +
        thisCalendar.get(Calendar.DAY_OF_MONTH));
    thisList.setAlertDate(thisDate);
    thisList.buildList(db);

    int notifyCount = 0;
    Iterator i = thisList.iterator();
    while (i.hasNext()) {
      Opportunity thisOpportunity = (Opportunity) i.next();
      System.out.println(thisOpportunity.toString());
      Notification thisNotification = new Notification();
      thisNotification.setUserToNotify(thisOpportunity.getOwner());
      thisNotification.setModule("Opportunities");
      thisNotification.setItemId(thisOpportunity.getId());
      thisNotification.setItemModified(thisOpportunity.getModified());
      if (thisNotification.isNew(db)) {
        System.out.println("Notifier-> ...it's new");
        thisNotification.setSiteCode(baseName);
        thisNotification.setSubject("CFS Opportunity: " + thisOpportunity.getDescription());
        thisNotification.setMessageToSend(
            "The following opportunity in CFS has an alert set: <br>" +
            "<br>" +
            thisOpportunity.getDescription() + "<br>");
        thisNotification.setType(Notification.EMAIL);
        thisNotification.setTypeText(Notification.EMAIL_TEXT);
        thisNotification.notifyUser(db);
        ++notifyCount;
      } else {
        System.out.println("Notifier-> ...it's old");
      }
      if (thisNotification.hasErrors()) {
        System.out.println("Notifier Error-> " + thisNotification.getErrorMessage());
      }
    }
    thisReport.setHeader("Opportunity Alerts Report for " + start.toString() + "<br>" + "Total Records: " + notifyCount);
    return thisReport.getHtml();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   */
  private String buildCallAlerts(Connection db) throws SQLException {
    Report thisReport = new Report();
    thisReport.setBorderSize(0);
    thisReport.addColumn("User");

    Calendar thisCalendar = Calendar.getInstance();

    CallList thisList = new CallList();
    java.sql.Date thisDate = new java.sql.Date(System.currentTimeMillis());
    thisDate = thisDate.valueOf(
        thisCalendar.get(Calendar.YEAR) + "-" +
        (thisCalendar.get(Calendar.MONTH) + 1) + "-" +
        thisCalendar.get(Calendar.DAY_OF_MONTH));
    thisList.setAlertDate(thisDate);
    thisList.buildList(db);

    int notifyCount = 0;
    Iterator i = thisList.iterator();
    while (i.hasNext()) {
      Call thisCall = (Call) i.next();
      Notification thisNotification = new Notification();
      thisNotification.setUserToNotify(thisCall.getEnteredBy());
      thisNotification.setModule("Calls");
      thisNotification.setItemId(thisCall.getId());
      thisNotification.setItemModified(null);
      if (thisNotification.isNew(db)) {
        System.out.println("Notifier-> ...it's new");
        thisNotification.setSiteCode(baseName);
        thisNotification.setSubject(
            "Call Alert: " + thisCall.getSubject());
        thisNotification.setMessageToSend(
            "The following call in CFS has an alert set: <br>" +
            "<br>" +
            "Contact: " + thisCall.getContactName() + "<br>" +
            "<br>" +
            thisCall.getNotes() + "<br>" +
            "<br>");
        thisNotification.setType(Notification.EMAIL);
        thisNotification.setTypeText(Notification.EMAIL_TEXT);
        thisNotification.notifyUser(db);
        ++notifyCount;
      } else {
        System.out.println("Notifier-> ...it's old");
      }
      if (thisNotification.hasErrors()) {
        System.err.println("Notifier Error-> " + thisNotification.getErrorMessage());
      }
    }
    thisReport.setHeader("Opportunity Alerts Report for " + start.toString() + "<br>" + "Total Records: " + notifyCount);
    return thisReport.getHtml();
  }


  /**
   *  Scans the Communications module to see if there are any recipients that
   *  need to be sent a message.
   *
   *@param  db                Description of Parameter
   *@param  dbName            Description of Parameter
   *@return                   Description of the Returned Value
   *@exception  SQLException  Description of Exception
   *@since
   */
  private String buildCommunications(Connection db, String dbName) throws Exception {
    Report thisReport = new Report();
    thisReport.setBorderSize(0);
    thisReport.addColumn("Report");

    Calendar thisCalendar = Calendar.getInstance();

    System.out.print("Getting campaign list...");
    CampaignList thisList = new CampaignList();
    java.sql.Date thisDate = new java.sql.Date(System.currentTimeMillis());
    thisDate = thisDate.valueOf(
        thisCalendar.get(Calendar.YEAR) + "-" +
        (thisCalendar.get(Calendar.MONTH) + 1) + "-" +
        thisCalendar.get(Calendar.DAY_OF_MONTH));
    thisList.setActiveDate(thisDate);
    thisList.setActive(CampaignList.TRUE);
    thisList.setReady(CampaignList.TRUE);
    thisList.setEnabled(CampaignList.TRUE);
    thisList.buildList(db);
    System.out.println("...got the list: " + thisList.size() + " active");

    Iterator i = thisList.iterator();
    int notifyCount = 0;
    while (i.hasNext()) {
      int campaignCount = 0;
      int sentCount = 0;
      Vector faxLog = new Vector();
      ContactReport letterLog = new ContactReport();
      System.out.println("  Getting campaign ...");
      Campaign thisCampaign = (Campaign) i.next();
      System.out.println("  Getting recipient list ...");
      RecipientList recipientList = new RecipientList();
      recipientList.setCampaignId(thisCampaign.getId());
      recipientList.setHasNullSentDate(true);
      recipientList.setBuildContact(false);
      recipientList.buildList(db);

      int runId = -1;
      Iterator iList = recipientList.iterator();
      if (iList.hasNext()) {
        thisCampaign.setStatusId(Campaign.STARTED);
        thisCampaign.setStatus(Campaign.STARTED_TEXT);
        thisCampaign.update(db);
        runId = thisCampaign.insertRun(db);
      } else {
        thisCampaign.setStatusId(Campaign.ERROR);
        thisCampaign.setStatus("No Recipients");
        thisCampaign.update(db);
      }
      while (iList.hasNext()) {
        ++campaignCount;
        System.out.println("  Getting contact ...");
        Recipient thisRecipient = (Recipient) iList.next();
        Contact thisContact = new Contact(db, "" + thisRecipient.getContactId());

        Notification thisNotification = new Notification();
        thisNotification.setContactToNotify(thisContact.getId());
        thisNotification.setModule("Communications Manager");
        thisNotification.setDatabaseName(dbName);
        thisNotification.setItemId(thisCampaign.getId());
        //thisNotification.setItemModified(thisCampaign.getActiveDate());
        if (thisNotification.isNew(db)) {
          System.out.println("Sending message ...");
          thisNotification.setFrom(thisCampaign.getReplyTo());
          thisNotification.setSubject(thisCampaign.getSubject());
          thisNotification.setMessageIdToSend(thisCampaign.getMessageId());
          thisNotification.setMessageToSend(thisCampaign.getMessage());
          thisNotification.setType(thisCampaign.getSendMethodId());
          thisNotification.notifyContact(db);
          if (thisNotification.getFaxLogEntry() != null) {
            faxLog.add(thisNotification.getFaxLogEntry());
          } else if (thisNotification.getContact() != null) {
            letterLog.add(thisNotification.getContact());
          }
          ++notifyCount;
          ++sentCount;
          thisRecipient.setRunId(runId);
          thisRecipient.setSentDate(new java.sql.Timestamp(System.currentTimeMillis()));
          thisRecipient.setStatusDate(new java.sql.Timestamp(System.currentTimeMillis()));
          thisRecipient.setStatusId(1);
          thisRecipient.setStatus(thisNotification.getStatus());
          System.out.println("Notifier-> Notification status: " + thisNotification.getStatus());
          thisRecipient.update(db);
        } else {
          System.out.println("Notifier-> ...it's old");
        }
        if (thisNotification.hasErrors()) {
          System.out.println("Notifier Error-> " + thisNotification.getErrorMessage());
        }
      }
      if (campaignCount > 0) {
        outputLetterLog(thisCampaign, letterLog, dbName);
        outputFaxLog(faxLog);
        thisCampaign.setStatusId(Campaign.FINISHED);
        thisCampaign.setStatus(Campaign.FINISHED_TEXT);
        thisCampaign.update(db);
        thisCampaign.setRecipientCount(campaignCount);
        thisCampaign.setSentCount(sentCount);
      }
    }
    thisReport.setHeader("Communications Report for " + start.toString() + "<br>" + "Total Records: " + notifyCount);
    return thisReport.getHtml();
  }


  /**
   *  Description of the Method
   */
  private void loadConfig() {
    File file = new File("notifier.xml");
    if (file == null) {
      System.err.println("Notifier configuration file not found-> notifier.xml");
      return;
    }
    try {
      Document document = parseDocument(file);
      config.clear();
      NodeList tags = document.getElementsByTagName("init-param");
      for (int i = 0; i < tags.getLength(); i++) {
        Element tag = (Element) tags.item(i);
        NodeList params = tag.getChildNodes();
        String name = null;
        String value = null;
        for (int j = 0; j < params.getLength(); j++) {
          Node param = (Node) params.item(j);
          if (param.hasChildNodes()) {
            NodeList children = param.getChildNodes();
            Node thisNode = (Node) children.item(0);
            if (param.getNodeName().equals("param-name")) {
              name = thisNode.getNodeValue();
            }
            if (param.getNodeName().equals("param-value")) {
              value = thisNode.getNodeValue();
            }
          }
        }
        if (value == null) value = "";
        config.put(name, value);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  file                              Description of Parameter
   *@return                                   Description of the Returned Value
   *@exception  FactoryConfigurationError     Description of Exception
   *@exception  ParserConfigurationException  Description of Exception
   *@exception  SAXException                  Description of Exception
   *@exception  IOException                   Description of Exception
   */
  private Document parseDocument(File file)
       throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(file);
    return document;
  }


  /**
   *  Description of the Method
   *
   *@param  faxLog  Description of Parameter
   *@return         Description of the Returned Value
   */
  private boolean outputFaxLog(Vector faxLog) {
    System.out.println("Notifier-> Outputting fax log");
    String fs = System.getProperty("file.separator");
    if (faxLog == null || faxLog.size() == 0) {
      return false;
    }
    PrintWriter out = null;
    String baseDirectory = (String) config.get("BaseDirectory");
    if (baseDirectory != null && !baseDirectory.equals("")) {
      if (!baseDirectory.endsWith(fs)) {
        baseDirectory += fs;
      }
      File dir = new File(baseDirectory);
      dir.mkdirs();
    }
    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMddhhmmss");
    String uniqueScript = formatter1.format(new java.util.Date());
    try {
      out = new PrintWriter(new BufferedWriter(new FileWriter(baseDirectory + (String) config.get("BaseFilename") + uniqueScript + ".sh")));
      Iterator faxEntries = faxLog.iterator();
      while (faxEntries.hasNext()) {
        String thisEntry = (String) faxEntries.next();
        StringTokenizer st = new StringTokenizer(thisEntry, "|");
        String databaseName = st.nextToken();
        String messageId = st.nextToken();
        String faxNumber = st.nextToken();
        String uniqueId = formatter1.format(new java.util.Date());

        String baseFilename = baseDirectory + (String) config.get("BaseFilename") + uniqueId + messageId + "-" + faxNumber;
        out.println("perl /usr/local/bin/html2ps -o " + baseFilename + ".ps http://" + (String) config.get("CFSWebServer") + "/ProcessMessage.do?id=" + databaseName + "\\|" + messageId + " >/dev/null 2>&1");
        out.println("gs -q -sDEVICE=tiffg4 -dNOPAUSE -dBATCH -sOutputFile=" + baseFilename + ".tiff " + baseFilename + ".ps");
        out.println("rm " + baseFilename + ".ps");

        if (!"false".equals((String) config.get("FaxEnabled"))) {
          out.println("sendfax -n -h " + (String) config.get("FaxServer") + " -d " + faxNumber + " " + baseFilename + ".tiff");
        }
        out.println("rm " + baseFilename + ".tiff");
      }
    } catch (IOException e) {
      e.printStackTrace(System.err);
      return false;
    } finally {
      if (out != null) {
        out.close();
      }
    }

    try {
      java.lang.Process process = java.lang.Runtime.getRuntime().exec("/bin/sh " + baseDirectory + "cfsfax" + uniqueScript + ".sh");
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  thisCampaign  Description of Parameter
   *@param  letterLog     Description of Parameter
   *@return               Description of the Returned Value
   */
  private boolean outputLetterLog(Campaign thisCampaign, ContactReport contactReport, String dbName) throws Exception {
    System.out.println("Notifier-> Outputting letter log");
    if (contactReport == null || contactReport.size() == 0) {
      return false;
    }
    String fs = System.getProperty("file.separator");
    String filePath = (String)config.get("FileLibrary") + fs + dbName + fs + "communications" + fs + "id" + thisCampaign.getId() + fs + CFSModule.getDatePath(new java.util.Date()) + fs;
    String[] fields = {"nameLast", "nameMiddle", "nameFirst", "company", "title", "department", "businessPhone", "businessAddress", "city", "state", "zip", "country"};
    contactReport.setCriteria(fields);
    contactReport.setFilePath(filePath);
    contactReport.setEnteredBy(0);
    contactReport.setModifiedBy(0);
    contactReport.setHeader("Communications mail merge");
    contactReport.buildReportBaseInfo();
		contactReport.buildReportHeaders();
    contactReport.buildReportData(null);
    contactReport.save();
    CFSModule.saveTextFile(thisCampaign.getMessage(), filePath + contactReport.getFilenameToUse() + ".txt");
    return true;
  }

  /*
   *  private String buildReport1(Connection db) throws SQLException {
   *  /Customers LOS Passed
   *  String whereLosPassed =
   *  "WHERE org_id in " +
   *  " (SELECT DISTINCT org_id FROM location l, location_los los " +
   *  "  WHERE l.rec_id = los.location_id " +
   *  "  AND los.los_status = 2 " +
   *  "  AND los.date_completed >= CURRENT_TIMESTAMP - 1)";
   *  Report rep = new Report();
   *  rep.setBorderSize(0);
   *  rep.addColumn("Company");
   *  Customer ds2 = new Customer();
   *  Vector customerList = ds2.getCustomerList(db, whereLosPassed);
   *  rep.setHeader("LOS Passed Report for " + start.toString() + "<br>" + "Total Records: " + customerList.size());
   *  java.util.Iterator i = customerList.iterator();
   *  while (i.hasNext()) {
   *  Customer thisCustomer = (Customer)i.next();
   *  ReportRow thisRow = new ReportRow();
   *  thisRow.addCell(thisCustomer.getName());
   *  rep.addRow(thisRow);
   *  ++totalRecords;
   *  }
   *  return (rep.getHtml());
   *  }
   */
}

