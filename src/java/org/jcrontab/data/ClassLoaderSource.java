/**
 *  This file is part of the jcrontab package Copyright (C) 2001-2002 Sergey V.
 *  Oudaltsov This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2 of the License,
 *  or (at your option) any later version. This library is distributed in the
 *  hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 *  the GNU Lesser General Public License for more details. You should have
 *  received a copy of the GNU Lesser General Public License along with this
 *  library; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307, USA For questions, suggestions:
 *  svu@users.sourceforge.net
 */

package org.jcrontab.data;

import java.io.InputStream;
import java.io.IOException;

/**
 *  This class Is the implementation of DataSource to access Info in a
 *  FileSystem
 *
 *@author     iolalla
 *@created    February 4, 2003
 *@version    $Revision$
 */
public class ClassLoaderSource extends FileSource {

  private static ClassLoaderSource instance;


  /**
   *  Gets the instance attribute of the ClassLoaderSource object
   *
   *@return    The instance value
   */
  public synchronized DataSource getInstance() {
    if (instance == null) {
      instance = new ClassLoaderSource();
    }
    return instance;
  }


  /**
   *  Description of the Method
   *
   *@param  name             Description of the Parameter
   *@return                  Description of the Return Value
   *@exception  IOException  Description of the Exception
   */
  protected InputStream createCrontabStream(String name) throws IOException {
    final InputStream input = ClassLoaderSource.class.getClassLoader().getResourceAsStream(name);
    if (input == null) {
      throw new IOException("Resource " + name + " not found");
    }
    return input;
  }


  /**
   *  Gets the changed attribute of the ClassLoaderSource object
   *
   *@param  name  Description of the Parameter
   *@return       The changed value
   */
  protected boolean isChanged(String name) {
    final boolean rv = lastModified == 0L;
    if (rv) {
      // This line is added to avoid reading the file if it didn't
      // change
      lastModified = System.currentTimeMillis();
    }
    return rv;
  }
}
