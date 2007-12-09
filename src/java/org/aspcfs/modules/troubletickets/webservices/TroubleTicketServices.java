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
package org.aspcfs.modules.troubletickets.webservices;

import org.aspcfs.modules.login.base.AuthenticationItem;
import org.aspcfs.utils.CRMConnection;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @version
 * @created    July 21, 2006
 */
public class TroubleTicketServices {
  private CRMConnection crm = new CRMConnection();

  /**
   *  Sets the authenticationInfo attribute of the CentricServices object
   *
   * @param  auth  The new authenticationInfo value
   */
  public void setAuthenticationInfo(AuthenticationItem auth) {
    crm.setUrl(auth.getUrl());
    crm.setId(auth.getId());
    crm.setSystemId(auth.getSystemId());
    crm.setClientId(auth.getClientId());
    crm.setUsername(auth.getUsername());
    crm.setCode(auth.getCode());
  }
}

