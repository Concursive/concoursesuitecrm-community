package com.darkhorseventures.framework.actions;

import java.io.*;
import java.util.*;

/**
 *  This class represents a single resource that is stored in the <code>Map
 *  </code>for resources resource table. Each resource is the name of a resource
 *  to forward to, and possibly the name of an XSL style-sheet to use to
 *  transform the resource into a specific format, such as HTML, or XML
 *
 *@author     kevin
 *@created    October 8, 2000
 *@version    $Id$
 */
public class Resource
     implements Serializable {
  private String resourceName = "";
  private String resourceXSL = "";
  private String layoutType = null;
  final static long serialVersionUID = 7269086432553620255L;


  /**
   *  Constructor for the Resource object
   *
   *@param  name  Description of Parameter
   *@param  xsl   Description of Parameter
   *@since
   */
  public Resource(String name, String xsl) {
    resourceName = name;
    resourceXSL = xsl;
  }


  /**
   *  Constructor for the Resource object
   *
   *@param  name    Description of Parameter
   *@param  xsl     Description of Parameter
   *@param  layout  Description of Parameter
   *@since          1.1
   */
  public Resource(String name, String xsl, String layout) {
    resourceName = name;
    resourceXSL = xsl;
    layoutType = layout;
  }


  /**
   *  Return the name of the resource (usually the path/*.jsp JSP page name)
   *
   *@return    The Name value
   *@since
   */
  public String getName() {
    return resourceName;
  }



  /**
   *  Returns the name of the XSL to use (usually the path/*.xsl name)
   *
   *@return    The XSL value
   *@since
   */
  public String getXSL() {
    return resourceXSL;
  }


  /**
   *  Returns the name of the layout to use
   *
   *@return    The layout value
   *@since
   */
  public String getLayout() {
    return layoutType;
  }
}

