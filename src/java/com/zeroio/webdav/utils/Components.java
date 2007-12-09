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
package com.zeroio.webdav.utils;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * vCard specification and iCal specification specifies names that are made up
 * of one or more components delimited by a delimiter (usually semi-colon
 * char). This is a utility class which helps parse through such a name.<p>
 * <p/>
 * A component value must be a valid string object. <p>
 * <p/>
 * eg: Address type specified in the vCard specification is made of 7 address
 * components that are specified in a sequence. When a component value is
 * missing the corresponding component separator must still be specified.<p>
 * <p/>
 * ADR;TYPE=dom,home,postal,parcel:;;123 Main Street;Any Town;CA;91921;USA
 *
 * @author Ananth
 * @created May 26, 2005
 */
class Components implements Enumeration {
  protected Enumeration components;


  /**
   * Constructor for the Components object
   *
   * @param components Description of the Parameter
   */
  Components(Enumeration components) {
    this.components = components;
  }


  /**
   * Constructor for the Components object
   *
   * @param input Description of the Parameter
   */
  Components(String input) {
    build(input, ";");
  }


  /**
   * Constructor for the Components object
   *
   * @param input     Description of the Parameter
   * @param delimiter Description of the Parameter
   */
  Components(String input, String delimiter) {
    build(input, delimiter);
  }


  /**
   * Description of the Method
   *
   * @param input     Description of the Parameter
   * @param delimiter Description of the Parameter
   */
  protected void build(String input, String delimiter) {
    Vector items = new Vector();
    String[] result = input.split(delimiter);
    for (int i = 0; i < result.length; ++i) {
      items.addElement(new String(result[i]));
    }
    this.components = items.elements();
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasMoreElements() {
    return components.hasMoreElements();
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean hasMoreComponents() {
    return hasMoreElements();
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws NoSuchElementException Description of the Exception
   */
  public Object nextElement() throws NoSuchElementException {
    return components.nextElement();
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws NoSuchElementException Description of the Exception
   */
  public String nextComponent() throws NoSuchElementException {
    return (String) nextElement();
  }
}

