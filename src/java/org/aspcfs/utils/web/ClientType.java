package org.aspcfs.utils.web;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

/**
 *  Utility to determine the client's browser type.<br>
 *  <br>
 *  Can be used for CSS or browser specific features.
 *
 *@author     Matt Rajkowski
 *@created    March 5, 2002
 *@version    $Id$
 */
public class ClientType implements Serializable {
  public final static String allowed = "0123456789.";

  //Client Browser Products, also define the text below
  public final static int NETSCAPE = 1;
  public final static int IE = 2;
  public final static int POCKETIE = 3;
  public final static int OPERA = 4;
  public final static int MOZILLA = 5;
  //Client Browser Types
  public final static int HTML_BROWSER = 1;
  public final static int WAP_BROWSER = 2;
  //Client OS
  public final static int WINDOWS = 1;
  public final static int MAC = 2;
  public final static int LINUX = 3;
  //Variables
  private int type = -1;
  private int id = -1;
  private double version = -1;
  private int os = WINDOWS;

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
      } else if (header.indexOf("macintosh") > -1) {
        os = MAC;
      }

      if (header.indexOf("msie") > -1) {
        //User-Agent: mozilla/4.0 (compatible; msie 6.0; windows 98; .net clr 1.0.3705)
        //User-Agent: mozilla/4.0 (compatible; msie 5.01; windows nt 5.0)
        this.id = IE;
        //Search for "msie x"
        this.version =
            Double.parseDouble(header.substring(header.indexOf("msie ") + 5,
            header.indexOf(";", header.indexOf("msie "))));
        cleanupVersion();
      } else if (header.indexOf("opera") > -1) {
        //Opera likes to impersonate other browsers
        //User-Agent: mozilla/4.0 (compatible; msie 6.0; msie 5.5; windows 98) opera 7.02  [en]
        //User-Agent: mozilla/3.0 (windows 98; u) opera 7.02  [en]
        this.id = OPERA;
        parseVersion(header.substring(header.indexOf("opera") + 5, header.indexOf("[")).trim());
      } else if (header.indexOf("mozilla") > -1) {
        //User-Agent: mozilla/5.0 (x11; u; linux i686; en-us; rv:1.3b) gecko/20030211
        //User-Agent: mozilla/5.0 (macintosh; u; ppc mac os x; en-us; rv:1.0.1) gecko/20021104 chimera/0.6
        //User-Agent: mozilla/5.0 (x11; u; linux i686; en-us; rv:1.0.1) gecko/20020830
        //User-Agent: mozilla/5.0 (windows; u; win98; en-us; rv:1.0.2) gecko/20030208 netscape/7.02
        if (header.indexOf("gecko/") > -1 && header.indexOf("rv:") > -1) {
          this.id = MOZILLA;
          version = parseVersion(header.substring(header.indexOf("rv:") + 3, header.indexOf(") gecko")));
        } else if (header.indexOf("gecko") > -1) {
          this.id = NETSCAPE;
          version = 6;
        } else {
          //Just make a default
          this.id = NETSCAPE;
          this.version = 4;
        }
      } else {
        this.id = NETSCAPE;
        this.version = 4;
      }

      if (System.getProperty("DEBUG") != null) {
        System.out.println("ClientType-> Browser Id: " + getBrowserId());
        System.out.println("ClientType-> Browser Version: " + getBrowserVersion());
        System.out.println("ClientType-> Browser O/S: " + getOsString());
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
        case IE:
          thisId = "ie";
          break;
        case MOZILLA:
          thisId = "moz";
          break;
        case NETSCAPE:
          thisId = "ns";
          break;
        case POCKETIE:
          thisId = "pie";
          break;
        case OPERA:
          thisId = "opera";
          break;
        default:
          thisId = "moz";
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
        case IE:
          thisId = "ie";
          break;
        case NETSCAPE:
          thisId = "ns";
          if (os == LINUX) {
            thisId += "-linux";
          }
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


  /**
   *  Description of the Method
   */
  public void cleanupVersion() {
    //Cleanup old versions...
    if (version > 4.0 && version < 5.0) {
      version = 4;
    } else if (version > 3.0 && version < 4.0) {
      version = 3;
    } else if (version > 2.0 && version < 3.0) {
      version = 2;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  versionText  Description of the Parameter
   *@return              Description of the Return Value
   */
  public double parseVersion(String versionText) {
    try {
      return Double.parseDouble(versionText);
    } catch (Exception e) {
      StringBuffer sb = new StringBuffer();
      boolean hasPoint = false;
      for (int i = 0; i < versionText.length(); i++) {
        char c = versionText.charAt(i);
        if (allowed.indexOf(c) > -1) {
          if (c == '.') {
            if (hasPoint) {
              break;
            } else {
              hasPoint = true;
            }
          }
          sb.append(c);
        }
      }
      return Double.parseDouble(sb.toString());
    }
  }
}

