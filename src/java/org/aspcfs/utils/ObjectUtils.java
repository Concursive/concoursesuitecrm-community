package com.darkhorseventures.utils;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.sql.*;

/**
 *  Reflective utilities for working with objects
 *
 *@author     matt rajkowski
 *@created    June 13, 2002
 *@version    $Id$
 */
public class ObjectUtils {

  /**
   *  Sets the param attribute of the ObjectUtils class
   *
   *@param  target  The new param value
   *@param  param   The new param value
   *@param  value   The new param value
   *@return         Description of the Returned Value
   */
  public static boolean setParam(Object target, String param, Object value) {
    try {
      param = param.substring(0, 1).toUpperCase() + param.substring(1);
      Class[] argTypes = new Class[]{value.getClass()};
      Method method = target.getClass().getMethod("set" + param, argTypes);
      method.invoke(target, new Object[]{value});
    } catch (Exception e) {
      //e.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   *  Gets the param attribute of the ObjectUtils class
   *
   *@param  target  Description of Parameter
   *@param  param   Description of Parameter
   *@return         The param value
   */
  public static String getParam(Object target, String param) {
    try {
      int dotPos = param.indexOf(".");
      if (dotPos < 1) {
        dotPos = param.indexOf("_");
      }

      if (dotPos > 0) {
        Object innerObject = ObjectUtils.getObject(target, param.substring(0, dotPos));
        return ObjectUtils.getParam(innerObject, param.substring(dotPos + 1));
      } else {
        param = param.substring(0, 1).toUpperCase() + param.substring(1);
        Method method = target.getClass().getDeclaredMethod("get" + param, null);
        Object result = method.invoke(target, null);
        if (result == null) {
          return null;
        } else if (result instanceof String) {
          return (String) result;
        } else {
          return String.valueOf(result);
        }
      }
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Gets the object attribute of the ObjectUtils class
   *
   *@param  target  Description of Parameter
   *@param  param   Description of Parameter
   *@return         The object value
   */
  public static Object getObject(Object target, String param) {
    try {
      param = param.substring(0, 1).toUpperCase() + param.substring(1);
      Method method = target.getClass().getDeclaredMethod("get" + param, null);
      Object result = method.invoke(target, null);
      return result;
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  theClass  Description of Parameter
   *@param  db        Description of Parameter
   *@param  objectId  Description of Parameter
   *@return           Description of the Returned Value
   */
  public static Object constructObject(Class theClass, Connection db, int objectId) {
    try {
      Class[] paramClass = new Class[]{Class.forName("java.sql.Connection"), int.class};
      Constructor constructor = theClass.getConstructor(paramClass);
      Object[] paramObject = new Object[]{db, new Integer(objectId)};
      return constructor.newInstance(paramObject);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace(System.out);
      }
      return null;
    }
  }
}

