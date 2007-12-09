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

package org.aspcfs.modules.industry.spirit.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;
import org.aspcfs.modules.industry.spirit.base.MaresaClientList;

/**
 *  Description of the Class
 *
 *@author     Dan P. Marsh
 *@created    Sept. 10, 2002
 *@version    $Id$
 */
public class MaresaClient extends GenericBean {

  private int code = -1;
  private int siteId = -1;
  private int clientId = -1;
  private String siteClient = null;
  private String maresaId = null;
  //private String siteClient = null;

  /**
   *  Constructor for the Segment object
   */
  public MaresaClient() { }


  /**
   *  Constructor for the Segment object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public MaresaClient(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the code attribute of the Segment object
   *
   *@param  code  The new code value
   */
  public void setCode(int code) {
    this.code = code;
  }


  /**
   *  Sets the code attribute of the Segment object
   *
   *@param  tmp  The new code value
   */
  public void setSiteId(String tmp) {
    this.siteId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the siteClient attribute of the MaresaClient object
   *
   *@param  siteClient  The new siteClient value
   */
  public void setSiteClient(String siteClient) {
    this.siteClient = siteClient;
  }


  /**
   *  Gets the siteClient attribute of the MaresaClient object
   *
   *@return    The siteClient value
   */
  public String getSiteClient() {
    return siteClient;
  }


  /**
   *  Sets the siteId attribute of the MaresaClient object
   *
   *@param  siteId  The new siteId value
   */
  public void setSiteId(int siteId) {
    this.siteId = siteId;
  }


  /**
   *  Gets the siteId attribute of the MaresaClient object
   *
   *@return    The siteId value
   */
  public int getSiteId() {
    return siteId;
  }


  /**
   *  Sets the segmetnId attribute of the Segment object
   *
   *@param  segmentId  The new segmentId value
   
  public void setSegmentId(int segmentId) {
    this.segmentId = segmentId;
  }
*/

  /**
   *  Sets the segmentDesc attribute of the Segment object
   *
   *@param  segmentDesc  The new segmentDesc value
   
  public void setSegmentDesc(String segmentDesc) {
    this.segmentDesc = segmentDesc;
  }
  */


  /**
   *  Gets the segmentDesc attribute of the Segment object
   *
   *@return    The segmentDesc value
   
  public String getSegmentDesc() {
    return segmentDesc;
  }
*/

  /**
   *  Sets the segmetnId attribute of the Segment object
   *
   *@param  tmp  The new segmetnId value
   
  public void setSegmentId(String tmp) {
    this.segmentId = Integer.parseInt(tmp);
  }
*/

  /**
   *  Gets the code attribute of the Segment object
   *
   *@return    The code value
   */
  public int getCode() {
    return code;
  }


  /**
   *  Gets the segmetnId attribute of the Segment object
   *
   *@return    The segmetnId value
   
  public int getSegmentId() {
    return segmentId;
  }
*/

  /**
   *  Description of the Method
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //call_log table
    //code = rs.getInt("code");
    siteId = DatabaseUtils.getInt(rs, "site_id");
    clientId = DatabaseUtils.getInt(rs, "client_id");
    maresaId = rs.getString("maresa_id");
    siteClient = maresaId + clientId;
    if (System.getProperty("DEBUG") != null) {
          System.out.println("siteClient-> value is " + siteClient);
        }

  }

}

