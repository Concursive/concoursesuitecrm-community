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

  private ArrayList picturesToProcess = new ArrayList();


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
    return "Pilot Online Reader";
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
      logger.info("Could not get database connection" + e.toString());
      return false;
    }

    //Process the vehicle data
    try {
      OrganizationList organizationList = new OrganizationList();
      organizationList.buildList(db);
      Iterator organizations = organizationList.iterator();
      while (organizations.hasNext()) {
        Organization dealer = (Organization) organizations.next();
        InventoryList inventoryList = new InventoryList();
        inventoryList.setOrgId(dealer.getId());
        inventoryList.setShowSold(Constants.FALSE);
        Calendar runDate = Calendar.getInstance();
        while (runDate.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
          runDate.add(Calendar.DATE, 1);
        }
        inventoryList.setAdRunDate(new java.sql.Date(runDate.getTime().getTime()));
        inventoryList.setBuildPictureId(true);
        inventoryList.buildList(db);
        Iterator inventory = inventoryList.iterator();
        while (inventory.hasNext()) {
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
          //logger.info(writer.getLastResponse());
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace(System.out);
      processOK = false;
    } finally {
      writer.close();
      sqlDriver.free(db);
      sqlDriver.destroy();
      sqlDriver = null;
    }

    //Process the vehicle pictures
    if (processOK && picturesToProcess.size() > 0) {
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
          if (sourcePicture.exists()) {
            ImageUtils.saveThumbnail(sourcePicture, destinationPicture, 285.0, -1.0);
          }
        }
      } catch (Exception e) {
        e.printStackTrace(System.out);
        processOK = false;
      }
    }

    if (processOK) {
      NCFTPApp ftp = new NCFTPApp();
      ftp.setDeleteSourceFilesAfterSend(true);
      ftp.setMakeRemoteDir(true);
      ftp.addFile(pictureDestinationPath + "*.jpg");
      processOK = (ftp.put(ftpPictures) == 0);
      if (!processOK) {
        logger.info(ftp.getStdErr());
      }
    }

    if (processOK) {
      NCFTPApp ftp = new NCFTPApp();
      ftp.setDeleteSourceFilesAfterSend(true);
      ftp.setMakeRemoteDir(true);
      ftp.addFile(((TextWriter) writer).getFilename());
      processOK = (ftp.put(ftpData) == 0);
      if (!processOK) {
        logger.info(ftp.getStdErr());
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

