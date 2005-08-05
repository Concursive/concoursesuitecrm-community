/**
 *  This file is part of the jcrontab package Copyright (C) 2001-2002 Israel
 *  Olalla This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or (at your
 *  option) any later version. This library is distributed in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 *  General Public License for more details. You should have received a copy of
 *  the GNU Lesser General Public License along with this library; if not, write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *  MA 02111-1307, USA For questions, suggestions: iolalla@yahoo.com
 */

package org.jcrontab.data;

import java.io.*;

/**
 * This class is a utility to make easier the instalation and use of jcrontab
 * What it does is to create the default files "jcrontab.properties" and
 * "crontab" and set a default initial properties ready to work in any system
 * The reason why this class was added was to make it easier to integrate with
 * jEdit
 *
 * @author $Author$
 * @version $Revision$
 * @created February 4, 2003
 */
public class DefaultFiles {

  private static String home;
  private static String FileSeparator;

  static {
    home = System.getProperty("user.home");
    FileSeparator = System.getProperty("file.separator");
  }

  private static String jcrontabDir = ".jcrontab";
  private static String propertiesFile = "jcrontab.properties";
  private static String log4jFile = "log4j.properties";
  private static String crontabFile = "crontab";
  private static String dir = home + FileSeparator + jcrontabDir;


  /**
   * This method loads creates the default Properties file in order to have
   * jcrontab working. The other option was to use getResourceAsStream with the
   * properties but with this solution to change the properties was more
   * complicated
   *
   * @throws Exception
   */
  public static void createPropertiesFile() throws Exception {
    File propFile = new File(dir + FileSeparator + propertiesFile);
    //System.out.println(" created : " + dir + FileSeparator + propertiesFile);
    propFile.createNewFile();
    Class cla = DefaultFiles.class;
    BufferedReader input = new BufferedReader(
        new InputStreamReader(cla.getResourceAsStream(propertiesFile)));
    BufferedWriter output = new BufferedWriter(new FileWriter(propFile));
    String strLine;

    while ((strLine = input.readLine()) != null) {
      //System.out.println(strLine);
      //strLine = strLine.trim();
      if (strLine.indexOf("{$HOME}") != -1) {
        StringBuffer strbLine = new StringBuffer(strLine);
        StringBuffer resultLine = strbLine.replace(
            strLine.indexOf("{$HOME}"),
            strLine.indexOf("{$HOME}") + 7,
            home + "/");
        strLine = resultLine.toString();
        if (strLine.indexOf("\\") != -1) {
          strLine = strLine.replace('\\', '/');
          //System.out.println(strLine);
        }
      }
      strLine += "\n";
      output.write(strLine);
    }
    input.close();
    output.close();
  }


  /**
   * This method creates the default crontabFile This file is void to Avoid
   * launching undesired tasks.
   *
   * @throws Exception Description of the Exception
   */
  public static void createCrontabFile() throws Exception {
    File evFile = new File(dir + FileSeparator + crontabFile);
    evFile.createNewFile();
    BufferedWriter output = new BufferedWriter(new FileWriter(evFile));
    output.write("#");
    output.close();
    //System.out.println(" created : " + dir + FileSeparator+ crontabFile);
  }


  /**
   * This method creates the default jcrontabDir
   */
  public static void createJcrontabDir() {
    File distDir = new File(dir);
    distDir.mkdir();
    //System.out.println(" created : " + dir );
  }


  /**
   * This method loads creates the default log4j.properties file in order to
   * have jcrontab working. The other option was to use getResourceAsStream
   * with the properties but with this solution to change the properties was
   * more complicated
   *
   * @throws Exception
   */
  public static void createLog4jFile() throws Exception {
    File logFile = new File(dir + FileSeparator + log4jFile);
    //System.out.println(" created : " + dir + FileSeparator + propertiesFile);
    logFile.createNewFile();
    Class cla = DefaultFiles.class;
    BufferedReader input = new BufferedReader(
        new InputStreamReader(cla.getResourceAsStream(log4jFile)));
    BufferedWriter output = new BufferedWriter(new FileWriter(logFile));
    String strLine;

    while ((strLine = input.readLine()) != null) {
      //System.out.println(strLine);
      strLine += "\n";
      output.write(strLine);
    }
    input.close();
    output.close();
  }
}

