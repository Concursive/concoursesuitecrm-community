/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */

package org.aspcfs.modules.contacts.utils;

import java.sql.Date;

/**
 * A generic class QualifiedLeadsCounter.
 *
 * @author Aliaksei.Yarotski
 * @created Oct 11, 2006
 */

public class QualifiedLeadsCount {

  private int userId;
  private int managerId;
  private double countOfConversion;
  private Date conversionDate;

  /**
   * @return the conversionDate
   */
  public Date getConversionDate() {
    return conversionDate;
  }

  /**
   * @param conversionDate the conversionDate to set
   */
  public void setConversionDate(Date conversionDate) {
    this.conversionDate = conversionDate;
  }

  /**
   * @return the countOfConversion
   */
  public double getCountOfConversion() {
    return countOfConversion;
  }

  /**
   * @param countOfConversion the countOfConversion to set
   */
  public void setCountOfConversion(double countOfConversion) {
    this.countOfConversion = countOfConversion;
  }


  /**
   * @return the managerId
   */
  public int getManagerId() {
    return managerId;
  }

  /**
   * @param managerId the managerId to set
   */
  public void setManagerId(int managerId) {
    this.managerId = managerId;
  }

  /**
   * @return the userId
   */
  public int getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

}
