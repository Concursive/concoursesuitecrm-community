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


  public void setSize(Integer size) {
    this.size =  size.intValue();
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

