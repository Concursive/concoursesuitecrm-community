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
package org.aspcfs.controller.objectHookManager;

import org.aspcfs.apps.workFlowManager.BusinessProcess;
import org.aspcfs.apps.workFlowManager.BusinessProcessList;
import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.WorkflowManager;

import java.util.*;

/**
 * A class that coordinates running business processes in a thread with the
 * WorkflowManager.<p>
 * <p/>
 * A business process can be triggered in 3 ways:<br>
 * 1. Inserting, updating, deleting, or selecting a hooked object triggers a
 * business process<br>
 * 2. A cron event triggers a business process<br>
 * 3. An event manually triggers a business process
 *
 * @author matt rajkowski
 * @version $Id: ObjectHook.java,v 1.2.2.4 2002/11/12 19:10:57 mrajkowski Exp
 *          $
 * @created October 14, 2002
 */
public class ObjectHook extends Thread {

  private ComponentContext context = null;
  private ObjectHookList objectHookList = null;
  private BusinessProcessList businessProcessList = null;
  private int actionId = -1;
  private WorkflowManager manager = null;
  private String businessProcess = null;


  /**
   * Sets the objectHookList attribute of the ObjectHook object
   *
   * @param tmp The new objectHookList value
   */
  public void setObjectHookList(ObjectHookList tmp) {
    this.objectHookList = tmp;
  }


  /**
   * Sets the businessProcessList attribute of the ObjectHook object
   *
   * @param tmp The new businessProcessList value
   */
  public void setBusinessProcessList(BusinessProcessList tmp) {
    this.businessProcessList = tmp;
  }


  /**
   * Sets the actionId attribute of the ObjectHook object
   *
   * @param tmp The new actionId value
   */
  public void setActionId(int tmp) {
    this.actionId = tmp;
  }


  /**
   * Sets the manager attribute of the ObjectHook object
   *
   * @param tmp The new manager value
   */
  public void setManager(WorkflowManager tmp) {
    this.manager = tmp;
  }


  /**
   * Sets the businessProcess attribute of the ObjectHook object
   *
   * @param tmp The new businessProcess value
   */
  public void setBusinessProcess(String tmp) {
    this.businessProcess = tmp;
  }


  /**
   * Gets the businessProcessList attribute of the ObjectHook object
   *
   * @return The businessProcessList value
   */
  public BusinessProcessList getBusinessProcessList() {
    return businessProcessList;
  }


  /**
   * Gets the objectHookList attribute of the ObjectHook object
   *
   * @return The objectHookList value
   */
  public ObjectHookList getObjectHookList() {
    return objectHookList;
  }


  /**
   * Gets the actionId attribute of the ObjectHook object
   *
   * @return The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   * Gets the manager attribute of the ObjectHook object
   *
   * @return The manager value
   */
  public WorkflowManager getManager() {
    return manager;
  }


  /**
   * Gets the businessProcess attribute of the ObjectHook object
   *
   * @return The businessProcess value
   */
  public String getBusinessProcess() {
    return businessProcess;
  }


  /**
   * Constructor for the ObjectHook object
   *
   * @param context Description of the Parameter
   */
  public ObjectHook(ComponentContext context) {
    this.context = context;
  }


  /**
   * Main processing method for the ObjectHook object
   */
  public void run() {
    try {
      if (System.getProperty("DEBUG") != null) {
        //Pause the thread so that the debug output is easier to read
        Thread.sleep(2000);
      }
      //If a business process is not specified, it must be looked up
      if (businessProcess == null) {
        //Lookup the business process to execute for this object and hook action
        ObjectHookActionList actionList =
            (ObjectHookActionList) objectHookList.get(context.getClassName());
        if (actionList != null) {
          boolean applicationActions = true;
          HashMap orderedAppActionKeys = new HashMap();
          HashMap orderedUserActionKeys = new HashMap();
          Iterator iterator = actionList.values().iterator();
          while (iterator.hasNext()) {
            ObjectHookAction thisAction = (ObjectHookAction) iterator.next();
            if (thisAction != null && thisAction.getApplication() && thisAction.getTypeId() == actionId && thisAction.getEnabled()) {
              orderedAppActionKeys.put(
                  new Integer(thisAction.getPriority()), "" + thisAction.getApplication() + "|" + thisAction.getPriority() + "|" + thisAction.getTypeId());
            }
            if (thisAction != null && !thisAction.getApplication() && thisAction.getTypeId() == actionId && thisAction.getEnabled()) {
              orderedUserActionKeys.put(
                  new Integer(thisAction.getPriority()), "" + thisAction.getApplication() + "|" + thisAction.getPriority() + "|" + thisAction.getTypeId());
            }
          }
          ArrayList applKeySet = new ArrayList(
              (Set) orderedAppActionKeys.keySet());
          Object[] array = applKeySet.toArray();
          Arrays.sort(array);
          for (int i = 0; i < array.length; i++) {
            ObjectHookAction thisAction = (ObjectHookAction) actionList.get(
                (String) orderedAppActionKeys.get(array[i]));
            businessProcess = null;
            if (thisAction != null && thisAction.getEnabled() && thisAction.getTypeId() == actionId && thisAction.getApplication()) {
              businessProcess = thisAction.getProcessName();
            }
            //Execute the specified business process
            if (businessProcess != null) {
              context.setProcessName(businessProcess);
              if (businessProcessList != null) {
                BusinessProcess thisProcess = (BusinessProcess) businessProcessList.get(
                    businessProcess);
                if (thisProcess != null) {
                  context.setProcess(thisProcess);
                  manager.execute(context);
                }
              }
            }
          }
          //Run the user BusinessProcesses
          applicationActions = false;
          applKeySet = new ArrayList((Set) orderedUserActionKeys.keySet());
          array = applKeySet.toArray();
          Arrays.sort(array);
          for (int i = 0; i < array.length; i++) {
            ObjectHookAction thisAction = (ObjectHookAction) actionList.get(
                (String) orderedUserActionKeys.get(array[i]));
            businessProcess = null;
            if (thisAction != null && thisAction.getEnabled() && thisAction.getTypeId() == actionId && !thisAction.getApplication()) {
              businessProcess = thisAction.getProcessName();
            }
            //Execute the specified business process
            if (businessProcess != null) {
              context.setProcessName(businessProcess);
              if (businessProcessList != null) {
                BusinessProcess thisProcess = (BusinessProcess) businessProcessList.get(
                    businessProcess);
                if (thisProcess != null) {
                  context.setProcess(thisProcess);
                  manager.execute(context);
                }
              }
            }
          }
        }
      } else {
        context.setProcessName(businessProcess);
        if (businessProcessList != null) {
          BusinessProcess thisProcess = (BusinessProcess) businessProcessList.get(
              businessProcess);
          if (thisProcess != null) {
            context.setProcess(thisProcess);
            manager.execute(context);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
    }
  }
}

