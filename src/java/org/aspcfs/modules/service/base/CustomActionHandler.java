/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.service.base;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  An interface for processing custom sync api actions.
 *
 * @author     matt rajkowski
 * @version    $Id: CustomActionHandler.java,v 1.2 2003/05/08 13:50:18
 *      mrajkowski Exp $
 * @created    April 29, 2003
 */
public interface CustomActionHandler {

  /**
   *  The sync API will execute the process method when an "execute" action is
   *  called
   *
   * @param  packetContext  Description of the Parameter
   * @param  db             Description of the Parameter
   * @param  values         Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public boolean process(PacketContext packetContext, Connection db, HashMap values) throws SQLException;


  /**
   *  The sync API will execute this method when a "status" action is
   *  called
   *
   * @param  packetContext     Description of the Parameter
   * @param  db                Description of the Parameter
   * @param  values            Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean status(PacketContext packetContext, Connection db, HashMap values) throws SQLException;
}

