//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.pipeline.beans;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.aspcfs.controller.*;
import org.aspcfs.modules.pipeline.base.*;
import org.aspcfs.modules.base.Constants;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    January 15, 2003
 *@version    $Id: OpportunityBean.java,v 1.7 2003/09/26 18:49:27 mrajkowski Exp
 *      $
 */
public class OpportunityBean extends GenericBean {
  private OpportunityHeader header = null;
  private OpportunityComponent component = null;
  //action list properties
  private int actionId = -1;


  /**
   *  Constructor for the OpportunityBean object
   */
  public OpportunityBean() {
    header = new OpportunityHeader();
    component = new OpportunityComponent();
  }


  /**
   *  Constructor for the OpportunityBean object
   *
   *@param  tmp1  Description of the Parameter
   *@param  tmp2  Description of the Parameter
   */
  public OpportunityBean(OpportunityHeader tmp1, OpportunityComponent tmp2) {
    header = tmp1;
    component = tmp2;
  }


  /**
   *  Sets the actionId attribute of the OpportunityBean object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(int actionId) {
    this.actionId = actionId;
  }


  /**
   *  Sets the actionId attribute of the OpportunityBean object
   *
   *@param  actionId  The new actionId value
   */
  public void setActionId(String actionId) {
    this.actionId = Integer.parseInt(actionId);
  }


  /**
   *  Gets the actionId attribute of the OpportunityBean object
   *
   *@return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Gets the component attribute of the OpportunityBean object
   *
   *@return    The component value
   */
  public OpportunityComponent getComponent() {
    return component;
  }


  /**
   *  Gets the header attribute of the OpportunityBean object
   *
   *@return    The header value
   */
  public OpportunityHeader getHeader() {
    return header;
  }


  /**
   *  Sets the component attribute of the OpportunityBean object
   *
   *@param  tmp  The new component value
   */
  public void setComponent(OpportunityComponent tmp) {
    this.component = tmp;
  }


  /**
   *  Sets the header attribute of the OpportunityBean object
   *
   *@param  tmp  The new header value
   */
  public void setHeader(OpportunityHeader tmp) {
    this.header = tmp;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db) throws SQLException {
    header.insert(db);
    component.insert(db);
    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  context           Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    boolean headerInserted = false;
    boolean componentInserted = false;
    boolean isComponentValid = component.isValid();
    boolean isHeaderValid = header.isValid();
    if (!isComponentValid || !isHeaderValid) {
      return false;
    }
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
   *  Gets the properties that are TimeZone sensitive for a Call
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {
    return OpportunityComponent.getTimeZoneParams();
  }


  /**
   *  Gets the numberParams attribute of the OpportunityBean class
   *
   *@return    The numberParams value
   */
  public static ArrayList getNumberParams() {
    return OpportunityComponent.getNumberParams();
  }
}

