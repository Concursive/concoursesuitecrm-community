package org.aspcfs.utils.web;

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
  public final static int NETSCAPE = 1;
  public final static int IE = 2;
  public final static int POCKETIE = 3;
  public final static int OPERA = 4;
  //Client Browser Types
  public final static int HTML_BROWSER = 1;
  public final static int WAP_BROWSER = 2;
  //Client OS
  public final static int WINDOWS = 1;
  public final static int MAC = 2;
  public final static int LINUX = 3;

  private int type = -1;
  private int id = -1;
  private double version = -1;
  private int os = 1;

  final static long serialVersionUID = 8345658414124283569L;


  /**
   *  Constructor for the ClientType object
   */
  public ClientType() { }


  /**
   *  Constructor for the ClientType object
   *
   *@param  request  Description of the Parameter
   */
  public ClientType(HttpServletRequest request) {
    this.setParameters(request);
  }


  /**
   *  Sets the parameters attribute of the ClientType object
   *
   *@param  request  The new parameters value
   */
  public void setParameters(HttpServletRequest request) {
    this.type = HTML_BROWSER;
    String wapCheck = request.getHeader("x-up-subno");
    if (System.getProperty("DEBUG") != null) {
      System.out.println("ClientType-> WAP String: " + wapCheck);
    }
    if (wapCheck != null) {
      this.type = WAP_BROWSER;
    }

    String header = request.getHeader("USER-AGENT");
    if (header == null) {
      header = request.getHeader("User-Agent");
    }
    if (header == null) {
      header = request.getHeader("user-agent");
    }
    if (header != null) {
      header = header.toLowerCase();
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ClientType-> Header String: " + header);
      }
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
        if (header.indexOf("netscape6") > -1) {
          version = 6;
        } else if (header.indexOf("gecko") > -1) {
          version = 6;
        } else {
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


  /**
   *  Sets the type attribute of the ClientType object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the id attribute of the ClientType object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the version attribute of the ClientType object
   *
   *@param  tmp  The new version value
   */
  public void setVersion(double tmp) {
    this.version = tmp;
  }


  /**
   *  Sets the os attribute of the ClientType object
   *
   *@param  tmp  The new os value
   */
  public void setOs(int tmp) {
    this.os = tmp;
  }


  /**
   *  Gets the type attribute of the ClientType object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the id attribute of the ClientType object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the version attribute of the ClientType object
   *
   *@return    The version value
   */
  public double getVersion() {
    return version;
  }


  /**
   *  Gets the os attribute of the ClientType object
   *
   *@return    The os value
   */
  public int getOs() {
    return os;
  }


  /**
   *  Gets the browserId attribute of the ClientType object
   *
   *@return    The browserId value
   */
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


  /**
   *  Gets the browserIdAndOS attribute of the ClientType object
   *
   *@return    The browserIdAndOS value
   */
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


  /**
   *  Gets the browserVersion attribute of the ClientType object
   *
   *@return    The browserVersion value
   */
  public double getBrowserVersion() {
    return version;
  }


  /**
   *  Gets the osString attribute of the ClientType object
   *
   *@return    The osString value
   */
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

