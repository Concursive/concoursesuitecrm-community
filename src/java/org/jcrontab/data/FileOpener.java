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

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
/**
 *  This class get an InputStream using the right method, File or
 *  getResourceAsStream, The idea of this way of openning files is from Sergey
 *  Udalstov
 *
 *@author     $Author$
 *@created    February 4, 2003
 *@version    $Revision$
 */

public class FileOpener {

  private static String type;

  static {
    type = org.jcrontab.Crontab.getInstance().getProperty("org.jcrontab.data.FileOpener");
  }


  /**
   *  This method is the reason of this class and basically loads the File with
   *  the given method.
   *
   *@param  name        String the name of the file to load
   *@param  cl          Description of the Parameter
   *@return             InputStream The inputStream to work with
   *@throws  Exception  the thrown exceptions
   */

  public InputStream getInputStream(Class cl,
      String name) throws Exception {
    InputStream is = null;
    if ("class".compareToIgnoreCase(type) == 0) {
      is = cl.getClassLoader().getResourceAsStream(name);
      if (is == null) {
        throw new IOException(name + " not Found.");
      }
    } else if ("file".compareToIgnoreCase(type) == 0) {
      File file = new File(name);
      is = new FileInputStream(file);
    } else {
      throw new UnsupportedOperationException("should call with class or file"
           + " no with : " + type);
    }
    return is;
  }
}

