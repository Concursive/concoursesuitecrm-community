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
 *  This interface says which methods a DataSource should have in order to be
 *  compatible with the DataFactory
 *
 *@author     Israel Olalla
 *@created    November, 2002
 *@version    $Id$
 */

public interface DataSource {
  /**
   *  returns the only valid DataSource of this kind
   *
   *@return    DataSource
   */
  abstract DataSource getInstance();


  /**
   *  searches the CrontabEntryBean from the DataSource
   *
   *@param  ceb         Description of the Parameter
   *@return             CrontabEntryBean
   *@throws  Exception
   */
  abstract CrontabEntryBean find(CrontabEntryBean ceb) throws Exception;


  /**
   *  Gets all the CrontabEntryBean from the DataSource
   *
   *@return             CrontabEntryBean[]
   *@throws  Exception
   */
  abstract CrontabEntryBean[] findAll() throws Exception;


  /**
   *  Description of the Method
   *
   *@param  cp             Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  abstract CrontabEntryBean[] findAll(Object cp) throws Exception;


  /**
   *  stores CrontabEntryBean in the DataSource
   *
   *@param  ceb                        Description of the Parameter
   *@exception  DataNotFoundException  Description of the Exception
   *@throws  Exception
   */
  abstract void store(CrontabEntryBean ceb) throws Exception,
      DataNotFoundException;


  /**
   *  stores CrontabEntryBean in the DataSource
   *
   *@param  ceb                        Description of the Parameter
   *@exception  DataNotFoundException  Description of the Exception
   *@throws  Exception
   */
  abstract void store(CrontabEntryBean[] ceb) throws Exception,
      DataNotFoundException;


  /**
   *  removes CrontabEntryBean from the DataSource
   *
   *@param  ceb         Description of the Parameter
   *@throws  Exception
   */
  abstract void remove(CrontabEntryBean[] ceb) throws Exception;
}


