//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import org.theseus.actions.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.controller.*;
import com.darkhorseventures.utils.*;

public class OpportunityBean extends GenericBean {
  private OpportunityComponent component = new OpportunityComponent();
  private OpportunityHeader header = new OpportunityHeader();
  
  public OpportunityComponent getComponent() { return component; }
  public OpportunityHeader getHeader() { return header; }
  public void setComponent(OpportunityComponent tmp) { this.component = tmp; }
  public void setHeader(OpportunityHeader tmp) { this.header = tmp; }
  
  
  public OpportunityBean() { }

  public boolean insert(Connection db) throws SQLException {
    header.insert(db);
    component.insert(db);
    return true;
  }
  
  public boolean insert(Connection db, ActionContext context) throws SQLException {
    boolean headerInserted = false;
    boolean componentInserted = false;
    
    if (!component.isValid(db) || !header.isValid(db)) {
      return false;
    }    
    try {
      db.setAutoCommit(false);
      headerInserted = header.insert(db, context);
      component.setOppId(header.getOppId());
      componentInserted = component.insert(db, context);
      
      db.commit();
    } catch (SQLException e) {
        db.rollback();
        db.setAutoCommit(true);
        throw new SQLException(e.getMessage());
    } finally {
      db.setAutoCommit(true);
    }
    
    return true;
  }  
}

