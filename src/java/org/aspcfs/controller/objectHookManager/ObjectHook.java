package com.darkhorseventures.controller;

import com.darkhorseventures.utils.*;
import java.sql.*;

/**
 *  A class that allows hooks to be run in a thread
 *
 *@author     matt rajkowski
 *@created    October 14, 2002
 *@version    $Id$
 */
public class ObjectHook extends Thread {
  /**
   *  The method state for this object hook
   */
  public final static int INSERT = 1;
  /**
   *  The method state for this object hook
   */
  public final static int UPDATE = 2;
  /**
   *  The method state for this object hook
   */
  public final static int DELETE = 3;

  private ConnectionPool sqlDriver = null;
  private ConnectionElement ce = null;
  private String classHook = null;
  private Object object = null;
  private Object previousObject = null;
  private int method = 0;


  /**
   *  Sets the ObjectHook properties
   *
   *@param  sqlDriver  Description of the Parameter
   *@param  ce         Description of the Parameter
   *@param  classHook  Description of the Parameter
   *@param  object     Description of the Parameter
   */
  public ObjectHook(ConnectionPool sqlDriver, ConnectionElement ce, String classHook, Object object) {
    this.sqlDriver = sqlDriver;
    this.ce = ce;
    this.object = object;
    this.classHook = classHook;
  }


  /**
   *  Sets the ObjectHook properties
   *
   *@param  sqlDriver       Description of the Parameter
   *@param  ce              Description of the Parameter
   *@param  classHook       Description of the Parameter
   *@param  previousObject  Description of the Parameter
   *@param  object          Description of the Parameter
   */
  public ObjectHook(ConnectionPool sqlDriver, ConnectionElement ce, String classHook, Object previousObject, Object object) {
    this.sqlDriver = sqlDriver;
    this.ce = ce;
    this.object = object;
    this.previousObject = previousObject;
    this.classHook = classHook;
  }


  /**
   *  Sets the method attribute of the ObjectHook object
   *
   *@param  tmp  The new method value
   */
  public void setMethod(int tmp) {
    this.method = tmp;
  }


  /**
   *  Main processing method for the ObjectHook object
   */
  public void run() {
    Connection db = null;
    try {
      //Thread.sleep(1000);
      Object hook = ObjectUtils.constructObject(Class.forName(classHook));
      if (hook instanceof BaseObjectHook) {
        ((BaseObjectHook) hook).setCurrentObject(object);
        ((BaseObjectHook) hook).setPreviousObject(previousObject);
        if (((BaseObjectHook) hook).requiresConnection()) {
          db = sqlDriver.getConnection(ce);
          ((BaseObjectHook) hook).setConnection(db);
        }
        switch (method) {
            case INSERT:
              ((BaseObjectHook) hook).processInsert();
              break;
            case UPDATE:
              ((BaseObjectHook) hook).processUpdate();
              break;
            case DELETE:
              ((BaseObjectHook) hook).processDelete();
              break;
            default:
              break;
        }
      }
    } catch (Exception e) {
    } finally {
      if (db != null) {
        sqlDriver.free(db);
      }
    }
  }
}

