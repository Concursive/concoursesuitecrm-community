package com.darkhorseventures.cfsbase;

import java.util.*;

public class CalendarEventList extends Vector {

	private java.util.Date date = null;
  
  public CalendarEventList() {}
  
  public void setDate(java.util.Date tmp) { this.date = tmp; }
  public java.util.Date getDate() { return date; }

}

