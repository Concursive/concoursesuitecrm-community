package org.aspcfs.modules.service.base;

import java.sql.*;
import java.util.HashMap;

/**
 *  An interface for processing custom sync api actions.
 *
 *@author     matt rajkowski
 *@created    April 29, 2003
 *@version    $Id$
 */
public interface CustomActionHandler {

  /**
   *  The sync API will execute the process method when an
   *  "execute" action is called
   *
   *@param  packet  Description of the Parameter
   *@return         Description of the Return Value
   */
  public boolean process(PacketContext packetContext, Connection db, HashMap values) throws SQLException;

}

