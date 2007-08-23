/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.about.actions;

import org.aspcfs.modules.actions.CFSModule;
import com.darkhorseventures.framework.actions.ActionContext;
import org.aspcfs.modules.system.base.ApplicationVersion;
import org.aspcfs.controller.ApplicationPrefs;

/*import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileNotFoundException;*/
import java.io.*;
/**
 *  Description of the Class
 *
 * @author     zhenya.zhidok
 * @created    02.05.2007
 * @version    $Id: Exp$
 */
public class About extends CFSModule {

  /**
   * Default action.
   *
   * @param context Description of Parameter
   * @return Description of the Returned Value
   */
  public String executeCommandDefault(ActionContext context) {
   this.getPath(context);
   String filePath = context.getServletContext().getRealPath("/") + "WEB-INF" + fs + "readme-libraries.txt";
   try {
     
     File file = new File(filePath);
     FileReader fileReader = new FileReader(file);

     String ls = System.getProperty("line.separator");
     StringBuffer software = new StringBuffer();
     StringBuffer license = new StringBuffer();
     BufferedReader in = new BufferedReader(fileReader);
     String line = null;
     boolean aboutSoftware = false;
     String aboutVersion = null;
     boolean hasLine = false;
     String[] table = null;
     
       while ((line = in.readLine()) != null) {
         if (aboutSoftware && !line.startsWith("---")) {
           
           if (hasLine) {
             software.append(ls);
             license.append(ls);
           }
           table = line.split("  ");
           software.append(table[0]);
           license.append(
               (table.length != 1)?table[table.length-1]:"" );
           
           hasLine = true;
         }
         
         if (line.startsWith("Project Name                      License")) {
           aboutSoftware = true;
         }
       }  
     
     in.close();
     
     ApplicationPrefs prefs = (ApplicationPrefs) context.getServletContext().getAttribute(
     "applicationPrefs");
     aboutVersion = ApplicationVersion.getInstalledVersion(prefs);
     context.getRequest().setAttribute("SUPPORT.TEXT", prefs.get("SUPPORT.TEXT"));
     context.getRequest().setAttribute("SUPPORT.URL", prefs.get("SUPPORT.URL"));
     context.getRequest().setAttribute("SUPPORT.IMAGE", prefs.get("SUPPORT.IMAGE"));
     context.getRequest().setAttribute("version", aboutVersion);
     context.getRequest().setAttribute("software", software);
     context.getRequest().setAttribute("license", license);
   } catch (FileNotFoundException e) {
     e.printStackTrace();
   } catch (IOException e) {
     e.printStackTrace();
   }
   
    return "AboutOK";
  } 
}


