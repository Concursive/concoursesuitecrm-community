/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 6, 2004
 *@version    $Id$
 */
public class MoveCounter {

  private int amount = 0;
  private int total = 0;


  /**
   *  Sets the amount attribute of the MoveCounter object
   *
   *@param  tmp  The new amount value
   */
  public void setAmount(int tmp) {
    this.amount = tmp;
  }


  /**
   *  Gets the amount attribute of the MoveCounter object
   *
   *@return    The amount value
   */
  public int getAmount() {
    return amount;
  }


  /**
   *  Sets the total attribute of the MoveCounter object
   *
   *@param  tmp  The new total value
   */
  public void setTotal(int tmp) {
    this.total = tmp;
  }


  /**
   *  Gets the total attribute of the MoveCounter object
   *
   *@return    The total value
   */
  public int getTotal() {
    return total;
  }


  /**
   *  Constructor for the MoveCounter object
   */
  public MoveCounter() { }


  /**
   *  Description of the Method
   *
   *@param  tmp  Description of the Parameter
   */
  public void add(int tmp) {
    total += tmp;
  }
}

