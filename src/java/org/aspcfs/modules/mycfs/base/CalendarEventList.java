package com.darkhorseventures.cfsbase;

import java.util.*;

public class CalendarEventList extends ArrayList {

	private java.util.Date date = null;
  public final static String[] EVENT_TYPES = {"Tasks","Calls","Opportunity","Accounts","Assignments","Contact Calls","Opportunity Calls","Holiday"};  
  public CalendarEventList() {}
  
  public void setDate(java.util.Date tmp) { this.date = tmp; }
  public java.util.Date getDate() { return date; }

}

