//Copyright 2001-2002 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.util.*;


 public class OrganizationYTDComparator implements Comparator {
    public int compare(Object left, Object right) {
      Double a = new Double(((Organization) left).getYTD());
      Double b = new Double(((Organization) right).getYTD());
	    
      int compareResult = b.compareTo(a);
      return (compareResult);
    }
  }
