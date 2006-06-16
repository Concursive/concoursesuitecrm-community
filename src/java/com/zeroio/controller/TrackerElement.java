package com.zeroio.controller;

import java.sql.Timestamp;

/**
 * The following are kept track of
 *
 * @author matt rajkowski
 * @version $Id: TrackerElement.java,v 2.1 2005/12/29 13:38:47 matt Exp $
 * @created December 6, 2005
 */
public class TrackerElement {

  private int userId = -1;
  private Timestamp entered = null;


  public TrackerElement() {
  }

  public TrackerElement(int id) {
    userId = id;
    entered = new Timestamp(System.currentTimeMillis());
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public Timestamp getEntered() {
    return entered;
  }

  public void setEntered(Timestamp entered) {
    this.entered = entered;
  }

}

