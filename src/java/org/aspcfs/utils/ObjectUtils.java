/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.io.*;

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
      if (value != null) {
        param = param.substring(0, 1).toUpperCase() + param.substring(1);
        Class[] argTypes = new Class[]{value.getClass()};
        Method method = target.getClass().getMethod("set" + param, argTypes);
        method.invoke(target, new Object[]{value});
      }
    } catch (Exception e) {
      //e.printStackTrace(System.out);
      return false;
    }
    return true;
  }


  /**
   *  Sets the param attribute of the ObjectUtils class
   *
   *@param  target  The new param value
   *@param  param   The new param value
   *@param  value   The new param value
   *@return         Description of the Return Value
   */
  public static boolean setParam(Object target, String param, double value) {
    try {
      param = param.substring(0, 1).toUpperCase() + param.substring(1);
      Class[] argTypes = new Class[]{double.class};
      Method method = target.getClass().getMethod("set" + param, argTypes);
      method.invoke(target, new Object[]{new Double(value)});
    } catch (Exception e) {
      e.printStackTrace(System.out);
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
        Object result = null;
        if (param.indexOf("(") > -1) {
          if (param.indexOf("\"") > -1) {
            //treat as string
            String value = param.substring(param.indexOf("\"") + 1, param.lastIndexOf("\""));
            Class[] argTypes = new Class[]{String.class};
            Method method = target.getClass().getMethod("get" + param, argTypes);
            result = method.invoke(target, new Object[]{value});
          } else {
            //treat as int
            String value = param.substring(param.indexOf("(") + 1, param.indexOf(")"));
            Class[] argTypes = new Class[]{int.class};
            Method method = target.getClass().getMethod("get" + param, argTypes);
            result = method.invoke(target, new Object[]{value});
          }
        } else {
          Method method = target.getClass().getMethod("get" + param, null);
          result = method.invoke(target, null);
        }
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
   *  Invokes the specified method
   *
   *@param  target      The new param value
   *@param  value       The new param value
   *@param  thisMethod  Description of the Parameter
   *@return             Description of the Returned Value
   */
  public static boolean invokeMethod(Object target, String thisMethod, Object value) {
    try {
      if (value != null) {
        Class[] argTypes = new Class[]{value.getClass()};
        Method method = target.getClass().getMethod(thisMethod, argTypes);
        method.invoke(target, new Object[]{value});
      }
    } catch (Exception e) {
      //e.printStackTrace(System.out);
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ObjectUtils-> invokeMethod Exception");
      }
      return false;
    }
    return true;
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
      Method method = target.getClass().getMethod("get" + param, null);
      Object result = method.invoke(target, null);
      return result;
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Constructs an object with a null constructor
   *
   *@param  theClass  Description of the Parameter
   *@return           Description of the Return Value
   */
  public static Object constructObject(Class theClass) {
    try {
      Class[] paramClass = null;
      Constructor constructor = theClass.getConstructor(paramClass);
      Object[] paramObject = null;
      return constructor.newInstance(paramObject);
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Constructs a new object in which the object loads itself from a database
   *  given a parameter
   *
   *@param  theClass   Description of the Parameter
   *@param  parameter  Description of the Parameter
   *@param  db         Description of the Parameter
   *@return            Description of the Return Value
   */
  public static Object constructObject(Class theClass, Object parameter, Connection db) {
    try {
      Class[] paramClass = new Class[]{parameter.getClass(), Class.forName("java.sql.Connection")};
      Constructor constructor = theClass.getConstructor(paramClass);
      Object[] paramObject = new Object[]{parameter, db};
      return constructor.newInstance(paramObject);
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Constructs a new object in which the object loads itself from a database
   *  given an id for the object
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
      return null;
    }
  }


  /**
   *  Constructs a new object in which the object loads itself from a database
   *  given an id for the object and a String typically used as a tableName for
   *  a lookupElement object
   *
   *@param  theClass   Description of the Parameter
   *@param  db         Description of the Parameter
   *@param  objectId   Description of the Parameter
   *@param  tableName  Description of the Parameter
   *@return            Description of the Return Value
   */
  public static Object constructObject(Class theClass, Connection db, int objectId, String tableName) {
    try {
      Class[] paramClass = new Class[]{Class.forName("java.sql.Connection"), int.class, Class.forName("java.lang.String")};
      Constructor constructor = theClass.getConstructor(paramClass);
      Object[] paramObject = new Object[]{db, new Integer(objectId), tableName};
      return constructor.newInstance(paramObject);
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  theClass     Description of the Parameter
   *@param  db           Description of the Parameter
   *@param  objectId     Description of the Parameter
   *@param  tableName    Description of the Parameter
   *@param  uniqueField  Description of the Parameter
   *@return              Description of the Return Value
   */
  public static Object constructObject(Class theClass, Connection db, int objectId, String tableName, String uniqueField) {
    try {
      Class[] paramClass = new Class[]{
          Class.forName("java.sql.Connection"),
          int.class,
          Class.forName("java.lang.String"),
          Class.forName("java.lang.String")};
      Constructor constructor = theClass.getConstructor(paramClass);
      Object[] paramObject = new Object[]{db, new Integer(objectId), tableName, uniqueField};
      return constructor.newInstance(paramObject);
    } catch (Exception e) {
      if (System.getProperty("DEBUG") != null) {
        e.printStackTrace(System.out);
      }
      return null;
    }
  }


  /**
   *  Serialize an object to a byte array
   *
   *@param  object         Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public static byte[] toByteArray(Object object) throws Exception {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
    objOut.writeObject(object);
    objOut.flush();
    byte[] tmpArray = byteOut.toByteArray();
    objOut.close();
    return tmpArray;
  }


  /**
   *  Reconstruct on object from a byte array
   *
   *@param  byteArray      Description of the Parameter
   *@return                Description of the Return Value
   *@exception  Exception  Description of the Exception
   */
  public static Object toObject(byte[] byteArray) throws Exception {
    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArray);
    ObjectInputStream objIn = new ObjectInputStream(byteIn);
    Object tmpObj = objIn.readObject();
    objIn.close();
    return tmpObj;
  }
}

