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
package org.aspcfs.modules.mycfs.base;

import java.util.*;
import org.aspcfs.modules.contacts.base.Call;
import org.aspcfs.modules.contacts.base.CallList;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    October 21, 2003
 *@version    $Id$
 */
public class CallEventList {
  CallList pendingCalls = new CallList();
  CallList completedCalls = new CallList();
  CallList canceledCalls = new CallList();
  int size = 0;


  /**
   *  Sets the pendingCalls attribute of the CallEventList object
   *
   *@param  pendingCalls  The new pendingCalls value
   */
  public void setPendingCalls(CallList pendingCalls) {
    this.pendingCalls = pendingCalls;
  }


  /**
   *  Sets the completedCalls attribute of the CallEventList object
   *
   *@param  completedCalls  The new completedCalls value
   */
  public void setCompletedCalls(CallList completedCalls) {
    this.completedCalls = completedCalls;
  }


  /**
   *  Sets the canceledCalls attribute of the CallEventList object
   *
   *@param  canceledCalls  The new canceledCalls value
   */
  public void setCanceledCalls(CallList canceledCalls) {
    this.canceledCalls = canceledCalls;
  }


  /**
   *  Sets the size attribute of the CallEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   *  Sets the size attribute of the CallEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   *  Gets the size attribute of the CallEventList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the sizeString attribute of the CallEventList object
   *
   *@return    The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   *  Gets the pendingCalls attribute of the CallEventList object
   *
   *@return    The pendingCalls value
   */
  public CallList getPendingCalls() {
    return pendingCalls;
  }


  /**
   *  Gets the completedCalls attribute of the CallEventList object
   *
   *@return    The completedCalls value
   */
  public CallList getCompletedCalls() {
    return completedCalls;
  }


  /**
   *  Gets the canceledCalls attribute of the CallEventList object
   *
   *@return    The canceledCalls value
   */
  public CallList getCanceledCalls() {
    return canceledCalls;
  }
}

