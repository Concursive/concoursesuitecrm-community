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
package com.darkhorseventures.framework.actions;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Description of the Class
 *
 * @author kevin duffey
 * @version $Id$
 * @created June 1, 2001
 */
public class Forward
    implements Serializable {
  final static long serialVersionUID = 3699281129673395436L;
  private Hashtable forwards = null;


  /**
   * Constructor for the Forward object
   */
  public Forward() {
    if (forwards == null) {
      forwards = new Hashtable();
    }
  }


  /**
   * Adds a forwarding resource to the hashtable
   *
   * @param forwardName The feature to be added to the Forward attribute
   * @param resource    The feature to be added to the Forward attribute
   */
  public void addForward(String forwardName, Resource resource) {
    forwards.put(forwardName, resource);
  }


  /**
   * Returns the Hashtable of forwards
   *
   * @return The forwards value
   */
  public Hashtable getForwards() {
    return forwards;
  }


  /**
   * Retunrs the <code>Forward</code> object at the specified location or null
   * if the location is too large.
   *
   * @param pos Description of the Parameter
   * @return The forwardAt value
   */
  public Forward getForwardAt(int pos) {
    if (forwards.size() > pos) {
      int cntr = 0;
      Enumeration e = forwards.keys();
      while (e.hasMoreElements() && cntr++ < (pos - 1)) {
        e.nextElement();
      }

      return (Forward) e.nextElement();
    }

    return null;
  }


  /**
   * Looks up a forward resource name and if it exists, returns the attribute
   * equated to the resource name.
   *
   * @param resourceName Description of the Parameter
   * @return Description of the Return Value
   */
  public Resource findResource(String resourceName) {
    if (forwards.containsKey(resourceName)) {
      return (Resource) forwards.get(resourceName);
    } else {
      return null;
    }
  }


  /**
   * Looks for a resource, and if found, returns the XSL file associated with
   * that resource to handle conversion of the xml output of the resource to
   * another format, specified via the xsl xpath language
   *
   * @param resourceName Description of the Parameter
   * @return Description of the Return Value
   */
  public String findXSL(String resourceName) {
    if (forwards.containsKey(resourceName)) {
      return (String) ((Resource) forwards.get(resourceName)).getXSL();
    } else {
      return null;
    }
  }
}

