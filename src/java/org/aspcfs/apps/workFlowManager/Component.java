//Copyright 2002 Dark Horse Ventures
package com.darkhorseventures.controller;

import com.darkhorseventures.utils.*;

/**
 *  Components should extend this class for basic component functionality
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
 */
public class Component {
  /**
   *  Gets the fileLibraryPath attribute of the Component class
   *
   *@param  context  Description of the Parameter
   *@return          The fileLibraryPath value
   */
  protected static String getFileLibraryPath(ComponentContext context) {
    return context.getParameter("FileLibraryPath");
  }
}

