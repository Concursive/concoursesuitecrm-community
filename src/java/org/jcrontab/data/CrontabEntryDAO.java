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

import java.util.Vector;
import org.jcrontab.log.Log;

/**
 *  This DAO Gives all the methods necesary to build CrontabEntries This class
 *  is an abstraction to make esaier the integration of new DataSources that
 *  help to access CrontabEntries in new ways
 *
 *@author     $Author$
 *@created    February 4, 2003
 *@version    $Revision$
 */
public class CrontabEntryDAO {
  /**
   *  This insntance grants only an instance of this DAO in every system
   */
  private static CrontabEntryDAO instance;

  /**
   *  This DataSource is the reason of this class
   */
  private static DataSource dao = null;


  /**
   *  Default constructor This one initializes everything maybe could use lazy
   *  inizialization
   */
  private CrontabEntryDAO() {
    if (dao == null) {
      try {
        dao = DataFactory.getInstance().getDAO();
      } catch (Exception e) {
        Log.error(e.toString(), e);
      }
    }
  }


  /**
   *  This method returns the singleton is very important to grant That only a
   *  Thread accesses at a time
   *
   *@return    The instance value
   */
  public static synchronized CrontabEntryDAO getInstance() {
    if (instance == null) {
      instance = new CrontabEntryDAO();
    }
    return instance;
  }


  /**
   *  Gets all the CrontabEntryBean from the DataSource
   *
   *@return             CrontabEntryBean[]
   *@throws  Exception
   */
  public CrontabEntryBean[] findAll() throws Exception {
    return dao.findAll();
  }


  /**
   *  searches the CrontabEntryBean from the DataSource
   *
   *@param  ceb         Description of the Parameter
   *@return             CrontabEntryBean
   *@throws  Exception
   */
  public CrontabEntryBean find(CrontabEntryBean ceb) throws Exception {
    return dao.find(ceb);
  }


  /**
   *  stores CrontabEntryBean in the DataSource
   *
   *@param  list              Description of the Parameter
   *@throws  Exception
   */
  public void store(CrontabEntryBean[] list) throws Exception {
    dao.store(list);
  }


  /**
   *  stores CrontabEntryBean in the DataSource
   *
   *@param  bean              Description of the Parameter
   *@throws  Exception
   */
  public void store(CrontabEntryBean bean) throws Exception {
    dao.store(bean);
  }


  /**
   *  removes CrontabEntryBean from the DataSource
   *
   *@param  list              Description of the Parameter
   *@throws  Exception
   */
  public void remove(CrontabEntryBean[] list) throws Exception {
    dao.remove(list);
  }
}

