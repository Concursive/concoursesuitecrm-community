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
package org.aspcfs.modules.pipeline.beans;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.pipeline.base.OpportunityComponent;
import org.aspcfs.modules.pipeline.base.OpportunityHeader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author chris
 * @version $Id: OpportunityBean.java,v 1.7 2003/09/26 18:49:27 mrajkowski Exp
 *          $
 * @created January 15, 2003
 */
public class OpportunityBean extends GenericBean {
  private OpportunityHeader header = null;
  private OpportunityComponent component = null;
  //action list properties
  private int actionId = -1;


  /**
   * Constructor for the OpportunityBean object
   */
  public OpportunityBean() {
    header = new OpportunityHeader();
    component = new OpportunityComponent();
  }


  /**
   * Constructor for the OpportunityBean object
   *
   * @param tmp1 Description of the Parameter
   * @param tmp2 Description of the Parameter
   */
  public OpportunityBean(OpportunityHeader tmp1, OpportunityComponent tmp2) {
    header = tmp1;
    component = tmp2;
  }


  /**
   * Sets the actionId attribute of the OpportunityBean object
   *
   * @param actionId The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   * Sets the actionId attribute of the OpportunityBean object
   *
   * @param actionId The new actionId value
   */
  public void setActionId(String actionId) {
    this.actionId = Integer.parseInt(actionId);
  }


  /**
   * Gets the actionId attribute of the OpportunityBean object
   *
   * @return The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   * Gets the component attribute of the OpportunityBean object
   *
   * @return The component value
   */
  public OpportunityComponent getComponent() {
    return component;
  }


  /**
   * Gets the header attribute of the OpportunityBean object
   *
   * @return The header value
   */
  public OpportunityHeader getHeader() {
    return header;
  }


  /**
   * Sets the component attribute of the OpportunityBean object
   *
   * @param tmp The new component value
   */
  public void setComponent(OpportunityComponent tmp) {
    this.component = tmp;
  }


  /**
   * Sets the header attribute of the OpportunityBean object
   *
   * @param tmp The new header value
   */
  public void setHeader(OpportunityHeader tmp) {
    this.header = tmp;
  }


  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    try {
      db.setAutoCommit(false);
      header.insert(db);
      component.insert(db);
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    return true;
  }


  /**
   * Description of the Method
   *
   * @param db      Description of the Parameter
   * @param context Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean insert(Connection db, ActionContext context) throws Exception {
    boolean headerInserted = false;
    boolean componentInserted = false;
    try {
      db.setAutoCommit(false);
      headerInserted = header.insert(db, context);
      component.setHeaderId(header.getId());
      componentInserted = component.insert(db, context);
      if (actionId > 0) {
        header.updateLog(db, actionId);
      }
      db.commit();
    } catch (SQLException e) {
      db.rollback();
      throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }

    return true;
  }


  /**
   * Gets the properties that are TimeZone sensitive for a Call
   *
   * @return The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    return OpportunityComponent.getTimeZoneParams();
  }


  /**
   * Gets the numberParams attribute of the OpportunityBean class
   *
   * @return The numberParams value
   */
  public static ArrayList getNumberParams() {
    return OpportunityComponent.getNumberParams();
  }
}

