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
package com.darkhorseventures.framework.beans;

import com.zeroio.controller.AutoPopulate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * This class is used by the ControllerServlet (and the extended
 * XSLControllerServlet) to auto-populate a bean instance with the values of
 * matching method name/form element pairs if they exist. <p>
 * <p/>
 * Using Reflection, if the setXXX() method exists in the bean and a matching
 * XXX parameter in the HttpServletRequest is found, it calls the setXXX()
 * method with the value of the request parameter. <p>
 * <p/>
 * Alternatively, this class supports nested population of objects, so if the
 * object to be populated contains an object, it is possible to populate that
 * object automatically. For example, class A has an instance of class B called
 * bClass. For the auto population to populate the bClass instance, you would
 * have <input type="text" name="bClass_Name">. The _ indicates the use of a
 * nested class to be populated. The _ was chosen because when using JavaScript
 * and DOM (client-side), the use of a . can cause some problems for JavaScript
 * to work with specific elements. There is a fix for this..but I have not
 * implemented it at this time. So, what happens in the above case is the
 * populateObject() method will call getBClass().setName(value) instead of
 * setName(value). This way, the nested object gets populated as well. This is
 * probably used in the case where you want to have the ability to "pass thru"
 * a bean to an underlying entity, so that you don't have to write the
 * getter/setter methods twice. This means if you have an entity class such as
 * Address that contains name, street, city, zip and state fields, with
 * getter/setter methods in that class, but you don't want to recode the
 * getter/setter methods in the bean class. By using nested autopopulation, you
 * can "pass thru" to the entity and any sub-classes that entity contains. <p>
 * <p/>
 * As a last note, the GenericBean class in this framework has two reference
 * instances it maintains. One is entity, with a getEntity() and setEntity()
 * method. The other is ejbRef with getEjbRef() and setEjbRef() methods. You
 * don't need to duplicate these methods and instances in your own descendant
 * bean classes, unless you do not descend from GenericBean. <p>
 * <p/>
 * One last thing about this class. Notice it implements Populate. This
 * interface allows you to implement your own populate class for special
 * purposes, then set a servlet init parameter in the web.xml file indicating
 * what populate class you would like to use. The default uses this class, but
 * you can override this option and use your own class if you have a more
 * special need for population. Feel free to use any code from this class in
 * your own routine and add to it or modify it however you please.
 *
 * @author Kevin Duffey
 * @author Matt Rajkowski
 * @version $Id$
 * @created June 1, 2001
 */
public class BeanUtils
    implements Populate, java.io.Serializable {
  final static long serialVersionUID = -7214731327958254043L;


  /**
   * Description of the Method
   *
   * @param bean            Description of the Parameter
   * @param request         Description of the Parameter
   * @param nestedAttribute Description of the Parameter
   * @param indexAttribute  Description of the Parameter
   */
  public void populateObject(Object bean, HttpServletRequest request, String nestedAttribute, String indexAttribute) {
    // Object to help populating
    AutoPopulate autoPopulate = new AutoPopulate(request, bean);
    //Before the object is populated with values available in the request, populate fields
    //that need to have default values (eg: record keeping fields like enteredby, modifiedby etc)
    autoPopulate.populateDefaults(bean);
    // Populate the request parameters
    Enumeration e = request.getParameterNames();
    String paramName = null;
    while (e.hasMoreElements()) {
      paramName = (String) e.nextElement();
      autoPopulate.setName(paramName);
      // a form has been submitted and requested to be auto-populated,
      // so we do that here..going through every element and trying
      // to call a setXXX() method on the bean object passed in for the value
      // of the request parameter currently being checked.
      String[] paramValues = request.getParameterValues(paramName);
      if (paramValues.length > 1) {
        populateParameter(
            autoPopulate, paramName, paramValues, bean, nestedAttribute, indexAttribute);
      } else {
        populateParameter(
            autoPopulate, paramName, paramValues[0], bean, nestedAttribute, indexAttribute);
      }
    }
  }


  /**
   * A helper method for the populateObject() method.
   *
   * @param param           Description of the Parameter
   * @param value           Description of the Parameter
   * @param target          Description of the Parameter
   * @param nestedAttribute Description of the Parameter
   * @param indexAttribute  Description of the Parameter
   * @param autoPopulate    Description of the Parameter
   */
  public static void populateParameter(AutoPopulate autoPopulate, String param, Object value, Object target, String nestedAttribute, String indexAttribute) {
    if ((param == null) || (param.length() == 0) || (value == null) || (target == null))
    {
      return;
    }
    String paramName = param.substring(0, 1).toUpperCase() + param.substring(
        1);
    try {
      int dotPos = paramName.indexOf(nestedAttribute);
      int index = -1;
      // -1 = no index value. 0 or > = index number has been specified
      if (dotPos < 0) {
        int indexPos = paramName.indexOf(indexAttribute);
        Class[] argTypes = null;
        if (indexPos > dotPos) {
          // there is an index value after the last item, so be sure to grab
          // that index and create the call appropriately.
          try {
            index = Integer.parseInt(paramName.substring(indexPos + 1));
          } catch (Exception e) {
            // no index after all
          }
        }
        Method method = null;
        if (indexPos >= 0) {
          paramName = paramName.substring(0, indexPos);
        }
        if (!autoPopulate.populateObject(target, param, (String) value)) {
          if (index >= 0) {
            argTypes = new Class[]{Integer.TYPE, value.getClass()};
            method = target.getClass().getMethod("set" + paramName, argTypes);
            method.invoke(target, new Object[]{new Integer(index), value});
          } else {
            argTypes = new Class[]{value.getClass()};
            method = target.getClass().getMethod("set" + paramName, argTypes);
            method.invoke(target, new Object[]{value});
          }
        }
      } else {
        String nestedMethod = paramName.substring(0, dotPos);
        int indexPos = nestedMethod.indexOf(indexAttribute);
        if (indexPos >= 0) {
          // there is an index value after the last item, so be sure to grab
          // that index and create the call appropriately.
          try {
            index = Integer.parseInt(nestedMethod.substring(indexPos + 1));
          } catch (Exception e) {
            // no index after all
          }
          // now we need to chop off the indexAttribute and index value from the nested method
          nestedMethod = nestedMethod.substring(0, indexPos);
        }

        Method method = null;
        Object nestedTarget = null;
        if (indexPos >= 0) {
          method = target.getClass().getMethod(
              "get" + nestedMethod, new Class[]{Integer.TYPE});
          nestedTarget = method.invoke(
              target, new Object[]{new Integer(index)});
        } else {
          method = target.getClass().getMethod(
              "get" + nestedMethod, new Class[0]);
          nestedTarget = method.invoke(target, new Object[0]);
        }

        populateParameter(
            autoPopulate, paramName.substring(dotPos + 1, paramName.length()), value, nestedTarget, nestedAttribute, indexAttribute);
      }
    } catch (Throwable t) {
    }
  }


  /**
   * The purpose of this method is to get a list of all public accessor methods
   * from the source object using reflection. Then it will attempt to call the
   * mutator method of the target object that match any of the accessor methods
   * of the source object with the values from the call to the accessor methods
   * of the source object.
   *
   * @param source Description of the Parameter
   * @param target Description of the Parameter
   */
  public static void populateObject(Object source, Object target) {
  }
}

