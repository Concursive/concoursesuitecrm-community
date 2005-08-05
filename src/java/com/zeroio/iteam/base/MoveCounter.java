/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created July 6, 2004
 */
public class MoveCounter {

  private int amount = 0;
  private int total = 0;


  /**
   * Sets the amount attribute of the MoveCounter object
   *
   * @param tmp The new amount value
   */
  public void setAmount(int tmp) {
    this.amount = tmp;
  }


  /**
   * Gets the amount attribute of the MoveCounter object
   *
   * @return The amount value
   */
  public int getAmount() {
    return amount;
  }


  /**
   * Sets the total attribute of the MoveCounter object
   *
   * @param tmp The new total value
   */
  public void setTotal(int tmp) {
    this.total = tmp;
  }


  /**
   * Gets the total attribute of the MoveCounter object
   *
   * @return The total value
   */
  public int getTotal() {
    return total;
  }


  /**
   * Constructor for the MoveCounter object
   */
  public MoveCounter() {
  }


  /**
   * Description of the Method
   *
   * @param tmp Description of the Parameter
   */
  public void add(int tmp) {
    total += tmp;
  }
}

