/*
 *  Copyright 2000-2003 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public class FileDownloadLogList extends ArrayList {

  /**
   *  Constructor for the FileDownloadLogList object
   */
  public FileDownloadLogList() { }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT item_id, version, user_download_id, download_date " +
        "FROM project_files_download d " +
        "WHERE d.item_id > -1 ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      FileDownloadLog logItem = new FileDownloadLog(rs);
      this.add(logItem);
    }
    rs.close();
    pst.close();
  }

}

