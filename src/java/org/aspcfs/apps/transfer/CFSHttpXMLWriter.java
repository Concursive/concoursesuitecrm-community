package com.darkhorseventures.apps.dataimport;

import java.util.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    September 3, 2002
 *@version    $Id$
 */
public class CFSHttpXMLWriter implements DataWriter {
  private String url = null;
  private String id = null;
  private String code = null;
  private int systemId = -1;


  /**
   *  Sets the url attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new url value
   */
  public void setUrl(String tmp) {
    this.url = tmp;
  }


  /**
   *  Sets the id attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the code attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new code value
   */
  public void setCode(String tmp) {
    this.code = tmp;
  }


  /**
   *  Sets the systemId attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(int tmp) {
    this.systemId = tmp;
  }


  /**
   *  Sets the systemId attribute of the CFSHttpXMLWriter object
   *
   *@param  tmp  The new systemId value
   */
  public void setSystemId(String tmp) {
    this.systemId = Integer.parseInt(tmp);
  }


  /**
   *  Gets the url attribute of the CFSHttpXMLWriter object
   *
   *@return    The url value
   */
  public String getUrl() {
    return url;
  }


  /**
   *  Gets the id attribute of the CFSHttpXMLWriter object
   *
   *@return    The id value
   */
  public String getId() {
    return id;
  }


  /**
   *  Gets the code attribute of the CFSHttpXMLWriter object
   *
   *@return    The code value
   */
  public String getCode() {
    return code;
  }


  /**
   *  Gets the systemId attribute of the CFSHttpXMLWriter object
   *
   *@return    The systemId value
   */
  public int getSystemId() {
    return systemId;
  }


  /**
   *  Gets the version attribute of the CFSHttpXMLWriter object
   *
   *@return    The version value
   */
  public double getVersion() {
    return 1.0d;
  }


  /**
   *  Gets the name attribute of the CFSHttpXMLWriter object
   *
   *@return    The name value
   */
  public String getName() {
    return "ASPCFS Web XML Data Writer";
  }


  /**
   *  Gets the description attribute of the CFSHttpXMLWriter object
   *
   *@return    The description value
   */
  public String getDescription() {
    return "Writes data to ASPCFS using the XML HTTP Web API";
  }


  /**
   *  Gets the configured attribute of the CFSHttpXMLWriter object
   *
   *@return    The configured value
   */
  public boolean isConfigured() {
    if (url == null || id == null || code == null || systemId == -1) {
      return false;
    }
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  data  Description of the Parameter
   *@return       Description of the Return Value
   */
  public boolean save(HashMap data) {
    Exception errorMessage = null;
    String filename = "aspcfs-copier.xml";
    try {
      String xmlPacket = StringUtils.loadText(filename);
      String response = HTTPUtils.sendPacket("address", xmlPacket);
      System.out.println(response);
    } catch (java.io.IOException io) {
      logger.info(io.toString());
      return false;
    }
    return true;
  }
}

