package com.darkhorseventures.apps.dataimport.reader.autoguide;

import com.darkhorseventures.apps.dataimport.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.autoguide.base.*;
import com.darkhorseventures.apps.dataimport.writer.TextWriter;
import java.io.*;
import com.darkhorseventures.apps.dataimport.writer.cfshttpxmlwriter.CFSHttpXMLWriter;

/**
 *  Queries vehicle data from the Auto Guide database, grabs and resizes any
 *  associated pictures, then FTPs all of the data to the pilotonline server.
 *
 *@author     matt rajkowski
 *@created    October 7, 2002
 *@version    $Id: PilotOnlineReader.java,v 1.5 2002/10/23 13:22:11 mrajkowski
 *      Exp $
 */
public class PilotOnlineReader implements DataReader {
  /**
   *  Description of the Field
   */
  public final static String fs = System.getProperty("file.separator");
  private String driver = null;
  private String url = null;
  private String user = null;
  private String password = null;
  private String pictureSourcePath = null;
  private String pictureDestinationPath = null;
  private String ftpData = null;
  private String ftpPictures = null;
  private String logUrl = null;
  private String logId = null;
  private String logCode = null;
  private int logSystemId = -1;
  private int logClientId = -1;

  private ArrayList picturesToProcess = new ArrayList();
  private ArrayList processLog = new ArrayList();

  /**
   *  Sets the driver attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new driver value
   */
  public void setDriver(String tmp) {
    this.driver = tmp;
  }


  /**
   *  Sets the url attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the user attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the password attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new password value
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Sets the pictureSourcePath attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new pictureSourcePath value
   */
  public void setPictureSourcePath(String tmp) {
    if (tmp.endsWith(fs)) {
      this.pictureSourcePath = tmp;
    } else {
      this.pictureSourcePath = tmp + fs;
    }
  }


  /**
   *  Sets the pictureDestinationPath attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new pictureDestinationPath value
   */
  public void setPictureDestinationPath(String tmp) {
    if (tmp.endsWith(fs)) {
      this.pictureDestinationPath = tmp;
    } else {
      this.pictureDestinationPath = tmp + fs;
    }
  }


  /**
   *  Sets the ftpData attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new ftpData value
   */
  public void setFtpData(String tmp) {
    this.ftpData = tmp;
  }


  /**
   *  Sets the ftpPictures attribute of the PilotOnlineReader object
   *
   *@param  tmp  The new ftpPictures value
   */
  public void setFtpPictures(String tmp) {
    this.ftpPictures = tmp;
  }

  public void setLogUrl(String tmp) { this.logUrl = tmp; }
  public void setLogId(String tmp) { this.logId = tmp; }
  public void setLogCode(String tmp) { this.logCode = tmp; }

  public void setLogSystemId(int tmp) { this.logSystemId = tmp; }
  public void setLogSystemId(String tmp) { this.logSystemId = Integer.parseInt(tmp); }
  
  public void setLogClientId(int tmp) { this.logClientId = tmp; }
  public void setLogClientId(String tmp) { this.logClientId = Integer.parseInt(tmp); }


  /**
   *  Gets the driver attribute of the PilotOnlineReader object
   *
   *@return    The driver value
   */
  public String getDriver() {
    return driver;
  }


  /**
   *  Gets the url attribute of the PilotOnlineReader object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the user attribute of the PilotOnlineReader object
   *
   *@return    The user value
   */
  public String getUser() {
    return user;
  }


  /**
   *  Gets the password attribute of the PilotOnlineReader object
   *
   *@return    The password value
   */
  public String getPassword() {
    return password;
  }


  /**
   *  Gets the pictureSourcePath attribute of the PilotOnlineReader object
   *
   *@return    The pictureSourcePath value
   */
  public String getPictureSourcePath() {
    return pictureSourcePath;
  }


  /**
   *  Gets the pictureDestinationPath attribute of the PilotOnlineReader object
   *
   *@return    The pictureDestinationPath value
   */
  public String getPictureDestinationPath() {
    return pictureDestinationPath;
  }


  /**
   *  Gets the ftpData attribute of the PilotOnlineReader object
   *
   *@return    The ftpData value
   */
  public String getFtpData() {
    return ftpData;
  }


  /**
   *  Gets the ftpPictures attribute of the PilotOnlineReader object
   *
   *@return    The ftpPictures value
   */
  public String getFtpPictures() {
    return ftpPictures;
  }


  /**
   *  Gets the version attribute of the PilotOnlineReader object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the PilotOnlineReader object
   *
   *@return    The name value
   */
  public String getName() {
    return "Pilot Online Reader/FTP";
  }



  /**
   *  Gets the description attribute of the PilotOnlineReader object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Reads dealer and vehicle data for PilotOnline";
  }



  /**
   *  Gets the configured attribute of the PilotOnlineReader object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    boolean configOK = true;

    if (driver == null) {
      logger.info("PilotOnlineReader-> Config: driver (db) not set");
      configOK = false;
    }

    if (url == null) {
      logger.info("PilotOnlineReader-> Config: url (db) not set");
      configOK = false;
    }

    if (user == null) {
      logger.info("PilotOnlineReader-> Config: user (db) not set");
      configOK = false;
    }

    if (pictureSourcePath == null) {
      logger.info("PilotOnlineReader-> Config: pictureSourcePath not set");
      configOK = false;
    }

    if (pictureDestinationPath == null) {
      logger.info("PilotOnlineReader-> Config: pictureDestinationPath not set");
      configOK = false;
    }

    if (ftpData == null) {
      logger.info("PilotOnlineReader-> Config: ftpData not set");
      configOK = false;
    }

    if (ftpPictures == null) {
      logger.info("PilotOnlineReader-> Config: ftpPictures not set");
      configOK = false;
    }

    return configOK;
  }



  /**
   *  Description of the Method
   *
   *@param  writer  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean execute(DataWriter writer) {
    boolean processOK = true;
    processLog.add("INFO: PilotOnlineReader-> Started " + new java.util.Date());
    
    //Connect to database
    ConnectionPool sqlDriver = null;
    Connection db = null;
    ConnectionElement connectionElement = null;
    try {
      sqlDriver = new ConnectionPool();
      sqlDriver.setMaxConnections(1);
      sqlDriver.setDebug(true);
      connectionElement = new ConnectionElement(url, user, password);
      connectionElement.setAllowCloseOnIdle(false);
      connectionElement.setDriver(driver);
      db = sqlDriver.getConnection(connectionElement);
    } catch (SQLException e) {
      processLog.add("ERROR: Could not get database connection: " + e.toString());
      processOK = false;
    }

    //Process the vehicle data
    try {
      OrganizationList organizationList = new OrganizationList();
      organizationList.buildList(db);
      Calendar runDateStart = Calendar.getInstance();
      Calendar runDateEnd = Calendar.getInstance();
      
      switch (runDateStart.get(Calendar.DAY_OF_WEEK)) {
        case Calendar.SATURDAY:
          runDateStart.add(Calendar.DATE, -3);
          runDateEnd.add(Calendar.DATE, -3);
          break;
        case Calendar.SUNDAY:
          runDateStart.add(Calendar.DATE, -4);
          runDateEnd.add(Calendar.DATE, -4);
          break;
        case Calendar.MONDAY:
          runDateStart.add(Calendar.DATE, -5);
          runDateEnd.add(Calendar.DATE, -5);
          break;
        case Calendar.TUESDAY: 
          runDateStart.add(Calendar.DATE, 1);
          runDateEnd.add(Calendar.DATE, 1);
          break;
        case Calendar.WEDNESDAY:
          break;
        case Calendar.THURSDAY:
          runDateStart.add(Calendar.DATE, -1);
          runDateEnd.add(Calendar.DATE, -1);
          break;
        case Calendar.FRIDAY:
          runDateStart.add(Calendar.DATE, -2);
          runDateEnd.add(Calendar.DATE, -2);
          break;
        default:
          while (runDateStart.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
            runDateStart.add(Calendar.DATE, 1);
            runDateEnd.add(Calendar.DATE, 1);
          }
          break;
      }
      //Define the final date range
      runDateStart.add(Calendar.DATE, -2);
      runDateEnd.add(Calendar.DATE, 2);
      //Zero out the time
      runDateStart.set(Calendar.HOUR, 0);
      runDateStart.set(Calendar.MINUTE, 0);
      runDateStart.set(Calendar.SECOND, 0);
      runDateStart.set(Calendar.MILLISECOND, 0);
      //Zero out the time
      runDateEnd.set(Calendar.HOUR, 0);
      runDateEnd.set(Calendar.MINUTE, 0);
      runDateEnd.set(Calendar.SECOND, 0);
      runDateEnd.set(Calendar.MILLISECOND, 0);
      
      processLog.add("INFO: Processing organizations/vehicles: " + organizationList.size() + " for " + runDateStart.getTime() + " through " + runDateEnd.getTime());
      int vehicleCount = 0;
      Iterator organizations = organizationList.iterator();
      while (organizations.hasNext()) {
        Organization dealer = (Organization) organizations.next();
        InventoryList inventoryList = new InventoryList();
        inventoryList.setOrgId(dealer.getId());
        inventoryList.setShowSold(Constants.FALSE);
        inventoryList.setAdRunDateStart(new java.sql.Date(runDateStart.getTime().getTime()));
        inventoryList.setAdRunDateEnd(new java.sql.Date(runDateEnd.getTime().getTime()));
        inventoryList.setBuildPictureId(true);
        inventoryList.buildList(db);
        Iterator inventory = inventoryList.iterator();
        while (inventory.hasNext()) {
          ++vehicleCount;
          Inventory vehicle = (Inventory) inventory.next();
          DataRecord thisRecord = new DataRecord();
          thisRecord.setName("vehicle");
          thisRecord.setAction("insert");
          //Dealer Info
          thisRecord.addField("accountId", dealer.getId());
          thisRecord.addField("accountNumber", dealer.getAccountNumber());
          thisRecord.addField("accountName", dealer.getName());
          //Vehicle Info
          thisRecord.addField("id", vehicle.getId());
          thisRecord.addField("make", vehicle.getVehicle().getMake().getName());
          thisRecord.addField("model", vehicle.getVehicle().getModel().getName());
          thisRecord.addField("year", vehicle.getVehicle().getYear());
          thisRecord.addField("color", vehicle.getExteriorColor());
          thisRecord.addField("mileage", vehicle.getMileageString());
          StringBuffer optionText = new StringBuffer();
          if (vehicle.hasOptions()) {
            Iterator options = vehicle.getOptions().iterator();
            while (options.hasNext()) {
              Option thisOption = (Option) options.next();
              optionText.append(thisOption.getName());
              if (options.hasNext()) {
                optionText.append(" ");
              }
            }
          }
          thisRecord.addField("options", optionText.toString());
          thisRecord.addField("comments", toOneLine(vehicle.getComments()));
          thisRecord.addField("condition", vehicle.getCondition());
          thisRecord.addField("sellingPrice", vehicle.getSellingPriceString());
          //Dealer Info
          thisRecord.addField("accountPhone", dealer.getPhoneNumber("Main"));
          thisRecord.addField("accountEmail", dealer.getEmailAddress("Primary"));
          //Picture
          if (vehicle.hasPicture()) {
            String pictureName = vehicle.getPicture().getFilename();
            if (pictureName.endsWith("TH")) {
              pictureName = pictureName.substring(0, pictureName.indexOf("TH"));
            }
            thisRecord.addField("pictureFilename", pictureName + ".jpg");
            picturesToProcess.add(pictureName);
          } else {
            thisRecord.addField("pictureFilename", "");
          }
          writer.save(thisRecord);
        }
      }
      processLog.add("INFO: Vehicles added-> " + vehicleCount);
    } catch (Exception ex) {
      ex.printStackTrace(System.out);
      processLog.add("ERROR: Querying organizations/vehicles-> " + ex.getMessage());
      processOK = false;
    } finally {
      writer.close();
      sqlDriver.free(db);
      sqlDriver.destroy();
      sqlDriver = null;
    }

    //Process the vehicle pictures
    if (processOK && picturesToProcess.size() > 0) {
      processLog.add("INFO: Processing pictures (resize/copy): " + picturesToProcess.size());
      try {
        Iterator pictures = picturesToProcess.iterator();
        while (pictures.hasNext()) {
          String thisPicture = (String) pictures.next();
          File sourcePicture = new File(
              pictureSourcePath +
              thisPicture.substring(0, 4) + fs +
              thisPicture.substring(4, 8) + fs +
              thisPicture);
          File destinationPicture = new File(pictureDestinationPath + thisPicture + ".jpg");
          if (sourcePicture.exists() && !destinationPicture.exists()) {
            ImageUtils.saveThumbnail(sourcePicture, destinationPicture, 285.0, -1.0);
          }
        }
      } catch (Exception e) {
        e.printStackTrace(System.out);
        processLog.add("ERROR: Processing pictures-> " + e.getMessage());
        processOK = false;
      }
    }

    if (processOK) {
      processLog.add("INFO: FTP Sending pictures");
      boolean sendComplete = false;
      int retryCount = 0;
      NCFTPApp ftp = new NCFTPApp();
      ftp.setDeleteSourceFilesAfterSend(true);
      ftp.setMakeRemoteDir(true);
      ftp.addFile(pictureDestinationPath + "*.jpg");
      while (!sendComplete && retryCount < 10) {
        ++retryCount;
        processOK = (ftp.put(ftpPictures) == 0);
        if (!processOK) {
          processLog.add("ERROR: FTP Sending pictures-> " + ftp.getStdErr());
        } else {
          sendComplete = true;
        }
      }
    } else {
      processLog.add("ERROR: FTP Sending pictures-> Skipped because of previous error");
    }

    if (processOK) {
      processLog.add("INFO: FTP Sending data");
      boolean sendComplete = false;
      int retryCount = 0;
      NCFTPApp ftp = new NCFTPApp();
      ftp.setDeleteSourceFilesAfterSend(true);
      ftp.setMakeRemoteDir(true);
      ftp.addFile(((TextWriter) writer).getFilename());
      while (!sendComplete && retryCount < 6) {
        ++retryCount;
        processOK = (ftp.put(ftpData) == 0);
        if (!processOK) {
          processLog.add("ERROR: FTP Sending data-> " + ftp.getStdErr());
        } else {
          sendComplete = true;
        }
      }
    } else {
      processLog.add("ERROR: FTP Sending data-> Skipped because of previous error");
    }
    
    processLog.add("INFO: PilotOnlineReader-> Finished " + new java.util.Date());
    
    if (logUrl != null) {
      try {
        CFSHttpXMLWriter serverLog = new CFSHttpXMLWriter();
        serverLog.setUrl(logUrl);
        serverLog.setId(logId);
        serverLog.setCode(logCode);
        serverLog.setSystemId(logSystemId);
        serverLog.setClientId(logClientId);
        //Add the record
        DataRecord logRecord = new DataRecord();
        logRecord.setName("processLog");
        logRecord.setAction("insert");
        logRecord.addField("name", this.getName());
        logRecord.addField("version", this.getVersion());
        logRecord.addField("systemId", logSystemId);
        logRecord.addField("clientId", logClientId);
        logRecord.addField("status", (processOK?0:1));
        //Add the data to the record
        StringBuffer logText = new StringBuffer();
        Iterator logData = processLog.iterator();
        while (logData.hasNext()) {
          String logItem = (String)logData.next();
          logText.append(logItem);
          if (logData.hasNext()) {
            logText.append(System.getProperty("line.separator"));
          }
        }
        logRecord.addField("message", logText.toString());
        serverLog.save(logRecord);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    //Local Debug
	if (!processOK) {
	Iterator logData = processLog.iterator();
	while (logData.hasNext()) {
	  String logItem = (String)logData.next();
	  System.out.println(logItem);
	}
	}

    return processOK;
  }


  /**
   *  Description of the Method
   *
   *@param  in  Description of the Parameter
   *@return     Description of the Return Value
   */
  private static String toOneLine(String in) {
    if (in != null && in.length() > 0) {
      String out = StringUtils.replace(in, "\r\n", " ");
      out = StringUtils.replace(out, "\r", " ");
      out = StringUtils.replace(out, "\n", " ");
      return out;
    } else {
      return "";
    }
  }

}

