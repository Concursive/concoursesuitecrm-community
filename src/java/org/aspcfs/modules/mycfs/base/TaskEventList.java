package org.aspcfs.modules.mycfs.base;

import java.util.*;
import org.aspcfs.modules.tasks.base.Task;
import org.aspcfs.modules.tasks.base.TaskList;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    October 22, 2003
 *@version    $Id$
 */
public class TaskEventList {
  TaskList pendingTasks = new TaskList();
  TaskList completedTasks = new TaskList();
  int size = 0;


  /**
   *  Sets the pendingTasks attribute of the TaskEventList object
   *
   *@param  pendingTasks  The new pendingTasks value
   */
  public void setPendingTasks(TaskList pendingTasks) {
    this.pendingTasks = pendingTasks;
  }


  /**
   *  Sets the completedTasks attribute of the TaskEventList object
   *
   *@param  completedTasks  The new completedTasks value
   */
  public void setCompletedTasks(TaskList completedTasks) {
    this.completedTasks = completedTasks;
  }


  /**
   *  Sets the size attribute of the TaskEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(int size) {
    this.size = size;
  }


  /**
   *  Sets the size attribute of the TaskEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   *  Gets the size attribute of the TaskEventList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the sizeString attribute of the TaskEventList object
   *
   *@return    The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   *  Gets the pendingTasks attribute of the TaskEventList object
   *
   *@return    The pendingTasks value
   */
  public TaskList getPendingTasks() {
    return pendingTasks;
  }


  /**
   *  Gets the completedTasks attribute of the TaskEventList object
   *
   *@return    The completedTasks value
   */
  public TaskList getCompletedTasks() {
    return completedTasks;
  }


  /**
   *  Adds a call to the list depending on the status of the Task
   *
   *@param  thisTask  The feature to be added to the Event attribute
   */
  public void addEvent(Task thisTask) {
    if (thisTask != null) {
      if (thisTask.getComplete()) {
        completedTasks.add(thisTask);
      } else {
        pendingTasks.add(thisTask);
      }
    }
  }
}

