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

/**
 *  Exception thrown when an entry in the crontab table cannot be parsed
 *
 *@author     iolalla
 *@created    February 4, 2003
 *@version    $Revision$
 */

public class CrontabEntryException extends Exception {

  /**
   *  Creates a new bad contrab entry exception
   */
  public CrontabEntryException() {
    super();
  }


  /**
   *  Creates a new bad crontab entry exception given a message string
   *
   *@param  str  Message string associated to the exception
   */
  public CrontabEntryException(String str) {
    super(str);
  }
}


