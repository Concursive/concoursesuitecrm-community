/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.controller;

import com.darkhorseventures.framework.beans.GenericBean;
import javax.servlet.http.*;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;
import java.util.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.sql.Timestamp;

/**
 *  Enhanced web app capabilities
 *
 *@author     matt rajkowski
 *@created    March 14, 2004
 *@version    $Id: AutoPopulate.java,v 1.1.2.1 2004/07/15 19:32:46 mrajkowski
 *      Exp $
 */
public class AutoPopulate {

  private HttpServletRequest request = null;
  private ArrayList timeParams = null;
  private ArrayList numberParams = null;
  private User user = null;
  private Calendar cal = null;
  private NumberFormat nf = null;
  private String name = null;


  /**
   *  Constructor for the AutoPopulate object
   *
   *@param  thisRequest  Description of the Parameter
   *@param  bean         Description of the Parameter
   */
  public AutoPopulate(HttpServletRequest thisRequest, Object bean) {
    request = thisRequest;
    // user information will be used for locale prefs
    UserBean userBean = (UserBean) request.getSession().getAttribute("User");
    if (userBean != null) {
      user = userBean.getUserRecord();
    } else {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoPopulate-> user IS NULL");
      }
    }
    // get the arraylists ready
    timeParams = (ArrayList) ObjectUtils.getObject(bean, "TimeZoneParams");
    if (timeParams != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoPopulate-> Found timeParams");
        System.out.println("AutoPopulate-> User has timezone: " + user.getTimeZone());
      }
      cal = Calendar.getInstance();
    }
    numberParams = (ArrayList) ObjectUtils.getObject(bean, "NumberParams");
    if (numberParams != null && user != null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoPopulate-> Found numberParams");
        System.out.println("AutoPopulate-> User has locale: " + user.getLocale());
      }
      nf = NumberFormat.getInstance(user.getLocale());
    }
  }


  /**
   *  Sets the name attribute of the AutoPopulate object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  When objects are being auto-populated from an HTML request, additional
   *  properties can be populated from multiple fields
   *
   *@param  param  Description of the Parameter
   *@param  value  Description of the Parameter
   *@param  bean   Description of the Parameter
   *@return        Description of the Return Value
   */
  public boolean populateObject(Object bean, String param, String value) {
    if (user == null) {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("AutoPopulate-> User session not set");
      }
      return false;
    }
    boolean modified = false;
    // Populate date/time fields using the user's timezone and locale
    if (user.getTimeZone() != null && timeParams != null) {
      String timeZone = user.getTimeZone();
      if (request.getParameter(param + "TimeZone") != null) {
        timeZone = (String) request.getParameter(param + "TimeZone");
      }
      if (timeParams.contains(param)) {
        // See if time is in request too
        String hourValue = (String) request.getParameter(name + "Hour");
        if (hourValue == null) {
          // Date fields: 1-1 mapping between HTML field and Java property
          if (System.getProperty("DEBUG") != null) {
            System.out.println("AutoPopulate-> timeParams trying to set: " + param);
          }
          Timestamp tmp = DateUtils.getUserToServerDateTime(TimeZone.getTimeZone(timeZone), DateFormat.SHORT, DateFormat.LONG, value, user.getLocale());
          if (tmp != null) {
            modified = ObjectUtils.setParam(bean, param, tmp);
          }
        } else {
          // Date & Time fields: 4-1 mapping between HTML fields and Java property
          try {
            Timestamp timestamp = DatabaseUtils.parseDateToTimestamp(value, user.getLocale());
            cal.setTimeInMillis(timestamp.getTime());
            int hour = Integer.parseInt(hourValue);
            int minute = Integer.parseInt((String) request.getParameter(name + "Minute"));
            String ampmString = (String) request.getParameter(name + "AMPM");
            if (ampmString != null) {
              int ampm = Integer.parseInt(ampmString);
              if (ampm == Calendar.AM) {
                if (hour == 12) {
                  hour = 0;
                }
              } else {
                if (hour < 12) {
                  hour += 12;
                }
              }
            }
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.setTimeZone(TimeZone.getTimeZone(timeZone));
            if (System.getProperty("DEBUG") != null) {
              System.out.println("AutoPopulate-> timeParams trying to set date/time: " + param);
            }
            modified = ObjectUtils.setParam(bean, param, new Timestamp(cal.getTimeInMillis()));
          } catch (Exception dateE) {
            //e.printStackTrace(System.out);
          }
        }
        if (!modified && value != null && !"".equals(value)) {
          addError(bean, param, "Date is invalid");
        } else {
          return true;
        }
      }
    }

    // Populate number fields using the user's locale
    if (user.getLocale() != null && numberParams != null) {
      if (numberParams.contains(param)) {
        try {
          // Parse the value
          if (System.getProperty("DEBUG") != null) {
            System.out.println("AutoPopulate-> numberParams trying to set number: " + param);
          }
          modified = ObjectUtils.setParam(bean, param, nf.parse(value).doubleValue());
          return true;
        } catch (Exception e) {
          //e.printStackTrace(System.out);
        }
        if (!modified && value != null && !"".equals(value)) {
          addError(bean, param, "Number is invalid");
        } else {
          return true;
        }
      }
    }
    return false;
  }


  /**
   *  Adds a feature to the Error attribute of the AutoPopulate class
   *
   *@param  bean     The feature to be added to the Error attribute
   *@param  param    The feature to be added to the Error attribute
   *@param  message  The feature to be added to the Error attribute
   */
  private static void addError(Object bean, String param, String message) {
    try {
      ((GenericBean) bean).getErrors().put(param + "Error", message);
    } catch (Exception e) {
    }
  }
}

