package com.darkhorseventures.framework.beans;

import java.util.*;
import java.lang.reflect.*;
import javax.servlet.http.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import org.aspcfs.utils.*;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.login.beans.UserBean;

/**
 *  This class is used by the ControllerServlet (and the extended
 *  XSLControllerServlet) to auto-populate a bean instance with the values of
 *  matching method name/form element pairs if they exist. <p>
 *
 *  Using Reflection, if the setXXX() method exists in the bean and a matching
 *  XXX parameter in the HttpServletRequest is found, it calls the setXXX()
 *  method with the value of the request parameter. <p>
 *
 *  Alternatively, this class supports nested population of objects, so if the
 *  object to be populated contains an object, it is possible to populate that
 *  object automatically. For example, class A has an instance of class B called
 *  bClass. For the auto population to populate the bClass instance, you would
 *  have <input type="text" name="bClass_Name">. The _ indicates the use of a
 *  nested class to be populated. The _ was chosen because when using JavaScript
 *  and DOM (client-side), the use of a . can cause some problems for JavaScript
 *  to work with specific elements. There is a fix for this..but I have not
 *  implemented it at this time. So, what happens in the above case is the
 *  populateObject() method will call getBClass().setName(value) instead of
 *  setName(value). This way, the nested object gets populated as well. This is
 *  probably used in the case where you want to have the ability to "pass thru"
 *  a bean to an underlying entity, so that you don't have to write the
 *  getter/setter methods twice. This means if you have an entity class such as
 *  Address that contains name, street, city, zip and state fields, with
 *  getter/setter methods in that class, but you don't want to recode the
 *  getter/setter methods in the bean class. By using nested autopopulation, you
 *  can "pass thru" to the entity and any sub-classes that entity contains. <p>
 *
 *  As a last note, the GenericBean class in this framework has two reference
 *  instances it maintains. One is entity, with a getEntity() and setEntity()
 *  method. The other is ejbRef with getEjbRef() and setEjbRef() methods. You
 *  don't need to duplicate these methods and instances in your own descendant
 *  bean classes, unless you do not descend from GenericBean. <p>
 *
 *  One last thing about this class. Notice it implements Populate. This
 *  interface allows you to implement your own populate class for special
 *  purposes, then set a servlet init parameter in the web.xml file indicating
 *  what populate class you would like to use. The default uses this class, but
 *  you can override this option and use your own class if you have a more
 *  special need for population. Feel free to use any code from this class in
 *  your own routine and add to it or modify it however you please.
 *
 *@author     Kevin Duffey
 *@created    June 1, 2001
 *@version    $Id$
 */
public class BeanUtils
     implements Populate, java.io.Serializable {
  final static long serialVersionUID = -7214731327958254043L;


  /**
   *  Description of the Method
   *
   *@param  bean             Description of Parameter
   *@param  request          Description of Parameter
   *@param  nestedAttribute  Description of Parameter
   *@param  indexAttribute   Description of Parameter
   *@since
   */
  public void populateObject(Object bean, HttpServletRequest request, String nestedAttribute, String indexAttribute) {
    Enumeration e = request.getParameterNames();
    String paramName = null;
    //get list of time sensitive properties of the bean
    ArrayList timeParams = (ArrayList) ObjectUtils.getObject(bean, "TimeZoneParams");
    while (e.hasMoreElements()) {
      paramName = (String) e.nextElement();
      // a form has been submitted and requested to be auto-populated,
      // so we do that here..going through every element and trying
      // to call a setXXX() method on the bean object passed in for the value
      // of the request parameter currently being checked.
      String[] paramValues = request.getParameterValues(paramName);
      if (paramValues.length > 1) {
        populateParameter(paramName, request, paramValues, bean, nestedAttribute, indexAttribute, timeParams);
      } else {
        populateParameter(paramName, request, paramValues[0], bean, nestedAttribute, indexAttribute, timeParams);
      }
    }
  }


  /**
   *  The purpose of this method is to get a list of all public accessor methods
   *  from the source object using reflection. Then it will attempt to call the
   *  mutator method of the target object that match any of the accessor methods
   *  of the source object with the values from the call to the accessor methods
   *  of the source object.
   *
   *@param  source  Description of Parameter
   *@param  target  Description of Parameter
   */
  public static void populateObject(Object source, Object target) {
  }



  /**
   *  A helper method for the populateObject() method.
   *
   *@param  param            Description of Parameter
   *@param  value            Description of Parameter
   *@param  target           Description of Parameter
   *@param  nestedAttribute  Description of Parameter
   *@param  indexAttribute   Description of Parameter
   *@param  request          Description of the Parameter
   *@param  timeParams       Description of the Parameter
   */
  private static void populateParameter(String param, HttpServletRequest request, Object value, Object target, String nestedAttribute, String indexAttribute, ArrayList timeParams) {
    if ((param == null) || (param.length() == 0) || (value == null) || (target == null)) {
      return;
    }
    String paramName = param.substring(0, 1).toUpperCase() + param.substring(1);
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
        if (!value.getClass().isArray() && timeParams != null) {
          //check for timezone if it is a time sensitive parameter
          value = adjustTimeZone(request, timeParams, param, value);
        }
        if (index >= 0) {
          argTypes = new Class[]{Integer.TYPE, value.getClass()};
          method = target.getClass().getMethod("set" + paramName, argTypes);
          method.invoke(target, new Object[]{new Integer(index), value});
        } else {
          argTypes = new Class[]{value.getClass()};
          method = target.getClass().getMethod("set" + paramName, argTypes);
          method.invoke(target, new Object[]{value});
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
          method = target.getClass().getMethod("get" + nestedMethod, new Class[]{Integer.TYPE});
          nestedTarget = method.invoke(target, new Object[]{new Integer(index)});
        } else {
          method = target.getClass().getMethod("get" + nestedMethod, new Class[0]);
          nestedTarget = method.invoke(target, new Object[0]);
        }

        populateParameter(paramName.substring(dotPos + 1, paramName.length()), request, value, nestedTarget, nestedAttribute, indexAttribute, timeParams);
      }
    } catch (Throwable t) {
    }
  }


  /**
   *  Checks to see if the bean wants the specified value, from the request,
   *  converted from the user's timezone
   *
   *@param  request     Description of the Parameter
   *@param  paramName   Description of the Parameter
   *@param  paramValue  Description of the Parameter
   *@param  timeParams  Description of the Parameter
   *@return             Description of the Return Value
   */
  private static Object adjustTimeZone(HttpServletRequest request, ArrayList timeParams, String paramName, Object paramValue) {
    if (timeParams != null && paramValue instanceof java.lang.String) {
      String adjustedDate = (String) paramValue;
      if (timeParams.contains(paramName) && !"".equals(adjustedDate)) {
        UserBean userBean = (UserBean) request.getSession().getAttribute("User");
        if (userBean != null) {
          User thisUser = userBean.getUserRecord();
          if (thisUser.getTimeZone() != null) {
            adjustedDate = DateUtils.getUserToServerDateTimeString(TimeZone.getTimeZone(thisUser.getTimeZone()), DateFormat.SHORT, DateFormat.LONG, adjustedDate);
          }
        }
      }
      return adjustedDate;
    }
    return paramValue;
  }
}

