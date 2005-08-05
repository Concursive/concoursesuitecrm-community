/**
 *  This file is part of the jcrontab package Copyright (C) 2001-2002 Israel
 *  Olalla<p>
 *
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as published by the
 *  Free Software Foundation; either version 2 of the License, or (at your
 *  option) any later version.<p>
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 *  for more details.<p>
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library; if not, write to the Free Software Foundation,
 *  Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA<p>
 *
 *  For questions, suggestions:<p>
 *
 *  iolalla@yahoo.com
 */

package org.jcrontab;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Calendar;

/**
 * This Bean represents an Event. Basically defines all the information
 * necesary. Extends Seriazable to be saved in binary format when neded.
 *
 * @author Israel Olalla
 * @version $Id$
 * @created November 2002
 */

public class CrontabBean implements Serializable {

  /**
   * This calendar defines the CrontabBean
   */
  public Calendar cal;
  /**
   * Time in milliseconds from 1970 to the execution of this CrontabBean
   */
  public long timeMillis;
  /**
   * This CrontabBean Id
   */
  public int id;
  /**
   * This CrontabBean name
   */
  public String className;
  /**
   * This CrontabBean methodName
   */
  public String methodName;
  /**
   * This CrontabBean bextraInfo to save time if the extraInfo Exists
   */
  public boolean bextraInfo = false;
  /**
   * This CrontabBean extraInfo I mean the parameters given :-)
   */
  public String[] extraInfo;
  public Object connectionContext = null;


  /**
   * Default void constuctor.
   */
  public CrontabBean() {
  }


  /**
   * This CrontabBean Id setter
   *
   * @param id The new id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * This CrontabBean timeMillis setter
   *
   * @param timeMillis Time in Milliseconds from 1970
   */
  public void setTime(long timeMillis) {
    this.timeMillis = timeMillis;
  }


  /**
   * This CrontabBean className setter
   *
   * @param className the name of the class
   */
  public void setClassName(String className) {
    this.className = className;
  }


  /**
   * This CrontabBean methodName setter
   *
   * @param methodName The name of the given Method
   */
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }


  /**
   * This CrontabBean extraInfo setter
   *
   * @param extraInfo the parameters given to the class
   */
  public void setExtraInfo(String[] extraInfo) {
    this.extraInfo = extraInfo;
    this.bextraInfo = true;
  }


  /**
   * This CrontabBean calendar setter
   *
   * @param cal the given calendar
   */
  public void setCalendar(Calendar cal) {
    this.cal = cal;
  }


  /**
   * Sets the connectionContext attribute of the CrontabBean object
   *
   * @param tmp The new connectionContext value
   */
  public void setConnectionContext(Object tmp) {
    this.connectionContext = tmp;
  }


  /**
   * This CrontabBean id getter
   *
   * @return id the bean id
   */
  public int getId() {
    return id;
  }


  /**
   * This CrontabBean timeMillis getter
   *
   * @return timeMillis the bean timeMillis
   */
  public long getTime() {
    return timeMillis;
  }


  /**
   * This CrontabBean calendar getter
   *
   * @return cal the beans calendar
   */
  public Calendar getCalendar() {
    return cal;
  }


  /**
   * This CrontabBean className getter
   *
   * @return className the beans className
   */
  public String getClassName() {
    return className;
  }


  /**
   * This CrontabBean methodName getter
   *
   * @return methodName the beans methodName
   */
  public String getMethodName() {
    return methodName;
  }


  /**
   * This CrontabBean extraInfo getter
   *
   * @return extraInfo the beans parameters
   */
  public String[] getExtraInfo() {
    return extraInfo;
  }


  /**
   * Gets the connectionContext attribute of the CrontabBean object
   *
   * @return The connectionContext value
   */
  public Object getConnectionContext() {
    return connectionContext;
  }


  /**
   * This Method returns this Bean in a String
   *
   * @return String that represents this bean
   */
  public String toString() {
    final StringBuffer sb = new StringBuffer();
    sb.append("\n [ Id: " + id + " ]");
    sb.append("\n [ ClassName: " + className + " ]");
    sb.append("\n [ MethodName : " + methodName + " ]");
    if (bextraInfo) {
      for (int i = 0; i < extraInfo.length; i++) {
        sb.append(
            "\n [ Parameter " + i + " : " + extraInfo[i]
            + " ]");
      }
    }
    sb.append("\n [ Calendar: " + cal + " ]");
    sb.append("\n [ TimeMillis: " + timeMillis + " ] ");
    sb.append("\n ");
    return sb.toString();
  }


  /**
   * This Method returns this Bean in a xml format This method is here to make
   * easier integration with web-apps and other systems
   *
   * @return String that represents this bean in xml
   */
  public String toXML() {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter, true);
    toXML(printWriter);
    return stringWriter.toString();
  }


  /**
   * This Method writes this Bean in the given PrintWriter. Can pick this from
   * a servlet Context or a HttpResponse
   *
   * @param pw PrintWriter where the xml eban will be written
   */
  public void toXML(PrintWriter pw) {
    pw.println("<crontabentry>");
    pw.println("<id>" + id + "</id> ");
    pw.println("<classname>" + className + "</classname> ");
    pw.println("<methodname>" + methodName + "</methodname> ");
    if (bextraInfo) {
      for (int i = 0; i < extraInfo.length; i++) {
        pw.println("<extrainfo parameter = \"" + i + "\" >");
        pw.println(extraInfo[i] + " </extrainfo>");
      }
    }
    pw.println("<calendar>" + cal + " </calendar>");
    pw.println("<timemillis>" + timeMillis + "</timemillis> ");
    pw.println("</crontabentry>");
  }


  /**
   * Helps to do the castings in a more simple way.
   *
   * @param obj Object to cast to CrontabEntryBean
   * @return The resulting array of CrontabEntryBean
   */
  public static CrontabBean[] toArray(Object[] obj) {
    CrontabBean[] ceb = new CrontabBean[obj.length];
    for (int i = 0; i < obj.length; i++) {
      ceb[i] = (CrontabBean) obj[i];
    }
    return ceb;
  }
}

