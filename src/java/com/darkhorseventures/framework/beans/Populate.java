package com.darkhorseventures.framework.beans;

import java.util.*;
import java.lang.reflect.*;
import javax.servlet.http.*;

/**
 *  Description of the Interface
 *
 *@version    $Id$
 */
public interface Populate {
  /**
   *  Description of the Method
   *
   *@param  bean             Description of the Parameter
   *@param  request          Description of the Parameter
   *@param  nestedAttribute  Description of the Parameter
   *@param  indexAttribute   Description of the Parameter
   */
  public void populateObject(Object bean, HttpServletRequest request, String nestedAttribute, String indexAttribute);
}

