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

package org.jcrontab;

import java.io.InputStream;
import org.jcrontab.log.Log;

/**
 *  This class executes a native command
 *
 *@author     iolalla
 *@created    February 4, 2003
 *@version    $Revision$
 */
public class NativeExec {
  /**
   *  main method
   *
   *@param  args  String[] the params passed from the console
   */
  public static void main(String args[]) {
    if (args.length < 1) {
      System.out.println("java org.jcrontab.NativeExec <cmd>");
    } else {
      String[] cmd = null;

      try {
        //with this variable will be done the swithcing
        String osName = System.getProperty("os.name");

        //only will work with Windows NT
        if (osName.equals("Windows NT")) {
          if (cmd == null) {
            cmd = new String[args.length + 2];
          }
          cmd[0] = "cmd.exe";
          cmd[1] = "/C";
          for (int i = 0; i < args.length; i++) {
            cmd[i + 2] = args[i];
          }
        }
        //only will work with Windows 95
        else if (osName.equals("Windows 95")) {
          if (cmd == null) {
            cmd = new String[args.length + 2];
          }
          cmd[0] = "command.com";
          cmd[1] = "/C";
          for (int i = 0; i < args.length; i++) {
            cmd[i + 2] = args[i];
          }
        }
        //only will work with Windows 2000
        else if (osName.equals("Windows 2000")) {
          if (cmd == null) {
            cmd = new String[args.length + 2];
          }
          cmd[0] = "cmd.exe";
          cmd[1] = "/C";

          for (int i = 0; i < args.length; i++) {
            cmd[i + 2] = args[i];
          }
        }
        //only will work with Windows XP
        else if (osName.equals("Windows XP")) {
          if (cmd == null) {
            cmd = new String[args.length + 2];
          }
          cmd[0] = "cmd.exe";
          cmd[1] = "/C";

          for (int i = 0; i < args.length; i++) {
            cmd[i + 2] = args[i];
          }
        }
        //only will work with Linux
        else if (osName.equals("Linux")) {
          if (cmd == null) {
            cmd = new String[args.length];
          }
          cmd = args;
        }
        //will work with the rest
        else {
          if (cmd == null) {
            cmd = new String[args.length];
          }
          cmd = args;
        }

        Runtime rt = Runtime.getRuntime();
        // Executes the command
        Process proc = rt.exec(cmd);
        // any error message?
        StreamGobbler errorGobbler = new
            StreamGobbler(proc.getErrorStream(), "ERROR");

        // any output?
        StreamGobbler outputGobbler = new
            StreamGobbler(proc.getInputStream(), "OUTPUT");

        // kick them off
        errorGobbler.start();
        outputGobbler.start();

        // any error???
        int exitVal = proc.waitFor();
        System.out.println("ExitValue: " + exitVal);
      } catch (Throwable t) {
        Log.error(t.toString(), t);
      }
    }
  }
}
