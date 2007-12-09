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
package org.aspcfs.apps.transfer.reader.cfsdatabasereader;

import org.apache.log4j.Logger;
import org.aspcfs.apps.transfer.DataWriter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for all modules that will be executed by the CFSDatabaseReader
 *
 * @author matt rajkowski
 * @version $Id: CFSDatabaseReaderImportModule.java,v 1.3 2003/01/14 22:53:33
 *          akhi_m Exp $
 * @created September 4, 2002
 */
public interface CFSDatabaseReaderImportModule {
  public static Logger logger = Logger.getLogger(org.aspcfs.apps.transfer.reader.cfsdatabasereader.CFSDatabaseReaderImportModule.class.getName());

  /**
   * Description of the Method
   *
   * @param writer   Description of the Parameter
   * @param db       Description of the Parameter
   * @param mappings Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  boolean process(DataWriter writer, Connection db, PropertyMapList mappings) throws SQLException;
}

