package com.darkhorseventures.apps.dataimport;

import java.util.*;
import java.util.logging.*;
import com.darkhorseventures.utils.*;

public class CFSWriter implements DataWriter {
  private String url = null;
  private String id = null;
  private String code = null;
  private int systemId = -1;
  
  public void setUrl(String tmp) { this.url = tmp; }
  public void setId(String tmp) { this.id = tmp; }
  public void setCode(String tmp) { this.code = tmp; }
  public void setSystemId(int tmp) { this.systemId = tmp; }
  public void setSystemId(String tmp) { this.systemId = Integer.parseInt(tmp); }
  public String getUrl() { return url; }
  public String getId() { return id; }
  public String getCode() { return code; }
  public int getSystemId() { return systemId; }
  
  public double getVersion() {
    return 1.0d;
  }

  public String getName() {
    return "ASPCFS Web XML Data Writer";
  }
  
  public String getDescription() {
    return "Writes data to ASPCFS using the XML HTTP Web API";
  }
  
  public boolean isConfigured() {
    if (url == null || id == null || code == null || systemId == -1) {
      return false;
    }
    return true;
  }
  
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
