//Copyright 2001-2003 Dark Horse Ventures

package org.aspcfs.modules.setup.beans;

import com.darkhorseventures.framework.beans.*;
import java.util.StringTokenizer;

/**
 *  Bean to encapsulate the Configure Database HTML form
 *
 *@author     mrajkowski
 *@created    August 19, 2003
 *@version    $Id: DatabaseBean.java,v 1.1.2.1 2003/08/19 20:18:47 mrajkowski
 *      Exp $
 */
public class DatabaseBean extends GenericBean {

  private int configured = -1;
  private String type = "PostgreSQL";
  private String driver = null;
  private String ip = "127.0.0.1";
  private int port = 5432;
  private String name = "darkhorse_crm";
  private String user = "darkhorse_crm";
  private String password = null;


  /**
   *  Sets the configured attribute of the DatabaseBean object
   *
   *@param  tmp  The new configured value
   */
  public void setConfigured(int tmp) {
    this.configured = tmp;
  }


  /**
   *  Sets the configured attribute of the DatabaseBean object
   *
   *@param  tmp  The new configured value
   */
  public void setConfigured(String tmp) {
    this.configured = Integer.parseInt(tmp);
  }


  /**
   *  Sets the type attribute of the DatabaseBean object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the ip attribute of the DatabaseBean object
   *
   *@param  tmp  The new ip value
   */
  public void setIp(String tmp) {
    this.ip = tmp;
  }


  /**
   *  Sets the port attribute of the DatabaseBean object
   *
   *@param  tmp  The new port value
   */
  public void setPort(int tmp) {
    this.port = tmp;
  }


  /**
   *  Sets the port attribute of the DatabaseBean object
   *
   *@param  tmp  The new port value
   */
  public void setPort(String tmp) {
    this.port = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the DatabaseBean object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the user attribute of the DatabaseBean object
   *
   *@param  tmp  The new user value
   */
  public void setUser(String tmp) {
    this.user = tmp;
  }


  /**
   *  Sets the password attribute of the DatabaseBean object
   *
   *@param  tmp  The new password value
   */
  public void setPassword(String tmp) {
    this.password = tmp;
  }


  /**
   *  Gets the configured attribute of the DatabaseBean object
   *
   *@return    The configured value
   */
  public int getConfigured() {
    return configured;
  }


  /**
   *  Gets the type attribute of the DatabaseBean object
   *
   *@return    The type value
   */
  public String getType() {
    return type;
  }


  /**
   *  Gets the driver attribute of the DatabaseBean object
   *
   *@return    The driver value
   */
  public String getDriver() {
    if (driver == null) {
      if ("PostgreSQL".equals(type)) {
        driver = "org.postgresql.Driver";
      }
      if ("MSSQL".equals(type)) {
        driver = "net.sourceforge.jtds.jdbc.Driver";
      }
    }
    return driver;
  }


  /**
   *  Gets the ip attribute of the DatabaseBean object
   *
   *@return    The ip value
   */
  public String getIp() {
    return ip;
  }


  /**
   *  Gets the port attribute of the DatabaseBean object
   *
   *@return    The port value
   */
  public int getPort() {
    return port;
  }


  /**
   *  Gets the name attribute of the DatabaseBean object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the user attribute of the DatabaseBean object
   *
   *@return    The user value
   */
  public String getUser() {
    return user;
  }


  /**
   *  Gets the password attribute of the DatabaseBean object
   *
   *@return    The password value
   */
  public String getPassword() {
    return password;
  }


  /**
   *  Gets the valid attribute of the DatabaseBean object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    errors.clear();
    if (ip == null || "".equals(ip.trim())) {
      errors.put("ipError", "IP address is a required field");
    }
    if (port <= 0) {
      errors.put("portError", "Database port is a required field");
    }
    if (name == null || "".equals(name.trim())) {
      errors.put("nameError", "Database name is a required field");
    }
    if (user == null || "".equals(user.trim())) {
      errors.put("userError", "Database user name is a required field");
    }
    return (!hasErrors());
  }


  /**
   *  Gets the url attribute of the DatabaseBean object
   *
   *@return    The url value
   */
  public String getUrl() {
    if ("org.postgresql.Driver".equals(this.getDriver())) {
      return "jdbc:postgresql://" + this.getIp() + ":" + this.getPort() + "/" + this.getName();
    }
    if ("net.sourceforge.jtds.jdbc.Driver".equals(this.getDriver())) {
      return "jdbc:jtds:sqlserver://" + this.getIp() + ":" + this.getPort() + "/" + this.getName();
    }
    if ("com.microsoft.jdbc.sqlserver.SQLServerDriver".equals(this.getDriver())) {
      return "jdbc:microsoft:sqlserver://" + this.getIp() + ":" + this.getPort() + ";SelectMethod=cursor;DatabaseName=" + this.getName();
    }
    return "";
  }


  /**
   *  Sets the connection attribute of the DatabaseBean object
   *
   *@param  tmp  The new connection value
   */
  public void setConnection(String tmp) {
    StringTokenizer st = new StringTokenizer(tmp, "|");
    type = st.nextToken();
    driver = st.nextToken();
    ip = st.nextToken();
    port = Integer.parseInt(st.nextToken());
    name = st.nextToken();
    user = st.nextToken();
    password = st.nextToken();
  }


  /**
   *  Gets the connection attribute of the DatabaseBean object
   *
   *@return    The connection value
   */
  public String getConnection() {
    return type + "|" +
        driver + "|" +
        ip + "|" +
        port + "|" +
        name + "|" +
        user + "|" +
        password;
  }


  /**
   *  Gets the typeValue attribute of the DatabaseBean object
   *
   *@return    The typeValue value
   */
  public String getTypeValue() {
    if ("PostgreSQL".equals(type)) {
      return "postgresql";
    }
    if ("MSSQL".equals(type)) {
      return "mssql";
    }
    return null;
  }
}

