package com.darkhorseventures.webutils;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

/**
 *  Utility to determine the client browser type.<br>
 *  <br>
 *  Windows is expected, Mac detection not implemented.
 *
 *@author     Matt Rajkowski
 *@created    March 5, 2002
 *@version    $Id$
 */
public class ClientType implements Serializable {
  //Client Browser Products
  public static final int NETSCAPE = 1;
  public static final int IE = 2;
  public static final int POCKETIE = 3;
  public static final int OPERA = 4;
  //Client Browser Types
  public static final int HTML_BROWSER = 1;
  public static final int WAP_BROWSER = 2;
  //Client OS
  public static final int WINDOWS = 1;
  public static final int MAC = 2;
  public static final int LINUX = 3;
  
  private int type = -1;
  private int id = -1;
  private double version = -1;
  private int os = 1;
  
  final static long serialVersionUID = 8345658414124283569L;
  
  public ClientType() { }
  
  public ClientType(HttpServletRequest request) {
    this.setParameters(request);
  }
  
  public void setParameters(HttpServletRequest request) {
    this.type = HTML_BROWSER;
    String wapCheck = request.getHeader("x-up-subno");
    if (System.getProperty("DEBUG") != null) System.out.println("ClientType-> WAP String: " + wapCheck);
    if (wapCheck != null) {
      this.type = WAP_BROWSER;
    }
    
    String header = request.getHeader("USER-AGENT");
    if (header == null) header = request.getHeader("User-Agent");
    if (header == null) header = request.getHeader("user-agent");
    if (header != null) {
      header = header.toLowerCase();
      if (System.getProperty("DEBUG") != null) System.out.println("ClientType-> Header String: " + header);
      //Determine OS
      if (header.indexOf("linux") > -1) {
        os = LINUX;
      } else if (header.indexOf("mac_powerpc") > -1) {
        os = MAC;
      }
      
      if (header.indexOf("opera") > -1) {
        this.id = OPERA;
        //Search for "opera/x" or "opera x" first
        try {
          this.version = 
            Double.parseDouble(header.substring(header.indexOf("opera") + 6,
            header.indexOf("opera") + 9).trim());
        } catch (Exception e) {
          this.version = 
            Double.parseDouble(header.substring(header.indexOf("opera") + 6,
            header.indexOf("opera") + 8).trim());
        }
      } else if (header.indexOf("msie") > -1) {
        this.id = IE;
        //Search for "msie x"
        try {
          this.version = 
            Double.parseDouble(header.substring(header.indexOf("msie ") + 5,
            header.indexOf("msie ") + 8).trim());
        } catch (Exception e) {
          this.version = 
            Double.parseDouble(header.substring(header.indexOf("msie ") + 5,
            header.indexOf("msie ") + 9).trim());
        }
      } else if (header.indexOf("mozilla") > -1) {
        this.id = NETSCAPE;
        if (header.indexOf("netscape6") > -1) version = 6;
        else if (header.indexOf("gecko") > -1) version = 6;
        else {
          //Search for "mozilla/x" or "mozilla x"
          try {
            this.version = 
              Double.parseDouble(header.substring(header.indexOf("mozilla") + 8,
              header.indexOf("mozilla") + 11).trim());
          } catch (Exception e) {
            this.version = 
              Double.parseDouble(header.substring(header.indexOf("mozilla") + 8,
              header.indexOf("mozilla") + 10).trim());
          }
        }
      } else {
        //Can narrow down browser even more... or just make a default
        this.id = NETSCAPE;
        this.version = 4;
      }
      
      //Cleanup old versions...
      if (version > 4.0 && version < 5.0) {
        version = 4;
      } else if (version > 3.0 && version < 4.0) {
        version = 3;
      } else if (version > 2.0 && version < 3.0) {
        version = 2;
      }
      
      
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ClientType-> Browser Id: " + getBrowserId());
        System.out.println("ClientType-> Browser Version: " + getBrowserVersion());
      }
    }
  }
  
  public void setType(int tmp) { this.type = tmp; }
  public void setId(int tmp) { this.id = tmp; }
  public void setVersion(double tmp) { this.version = tmp; }
  public void setOs(int tmp) { this.os = tmp; }
  public int getType() { return type; }
  public int getId() { return id; }
  public double getVersion() { return version; }
  public int getOs() { return os; }
  
  public String getBrowserId() {
    String thisId = null;
    switch (id) {
      case NETSCAPE: 
        thisId = "ns";
        break;
      case IE:
        thisId = "ie";
        break;
      case POCKETIE:
        thisId = "pie";
        break;
      case OPERA:
        thisId = "opera";
        break;
      default:
        break;
    }
    return thisId;
  }
  
  public String getBrowserIdAndOS() {
    String thisId = null;
    switch (id) {
      case NETSCAPE: 
        thisId = "ns";
        if (os == LINUX) {
          thisId += "-linux";
        }
        break;
      case IE:
        thisId = "ie";
        break;
      case POCKETIE:
        thisId = "pie";
        break;
      case OPERA:
        thisId = "opera";
        break;
      default:
        break;
    }
    return thisId;
  }
  
  public double getBrowserVersion() {
    return version;
  }
  
  public String getOsString() {
    switch (os) {
      case LINUX:
        return "linux";
      case MAC:
        return "mac";
      case WINDOWS:
        return "win";
      default:
        return "win";
    }
  }
}

