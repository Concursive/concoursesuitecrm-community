//Copyright 2001-2002 Dark Horse Ventures

package org.aspcfs.modules.accounts.base;

import java.util.*;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    January 13, 2003
 *@version    $Id$
 */
public class OrganizationYTDComparator implements Comparator {
  /**
   *  Description of the Method
   *
   *@param  left   Description of the Parameter
   *@param  right  Description of the Parameter
   *@return        Description of the Return Value
   */
  public int compare(Object left, Object right) {
    Double a = new Double(((Organization) left).getYTD());
    Double b = new Double(((Organization) right).getYTD());

    int compareResult = b.compareTo(a);
    return (compareResult);
  }
}

