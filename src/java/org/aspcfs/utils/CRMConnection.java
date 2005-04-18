package org.aspcfs.utils;

import org.aspcfs.apps.transfer.writer.cfshttpxmlwriter.CFSHttpXMLWriter;

/**
 * Transfers data to/from a Centric CRM server
 *
 * @author mrajkowski
 * @version $Id$
 * @created Apr 11, 2005
 */

public class CRMConnection extends CFSHttpXMLWriter {

  public CRMConnection() {
    this.setId("CRMConnection");
    this.setSystemId(4);
  }
}
