package org.aspcfs.controller.objectHookManager;

import org.aspcfs.apps.workFlowManager.*;

/**
 *  A class that allows hooks to be run in a thread, created by the
 *  WorkflowManager
 *
 *@author     matt rajkowski
 *@created    October 14, 2002
 *@version    $Id: ObjectHook.java,v 1.2.2.4 2002/11/12 19:10:57 mrajkowski Exp
 *      $
 */
public class ObjectHook extends Thread {

  private ComponentContext context = null;
  private ObjectHookList objectHookList = null;
  private BusinessProcessList businessProcessList = null;
  private int actionId = -1;
  private WorkflowManager manager = null;


  /**
   *  Sets the objectHookList attribute of the ObjectHook object
   *
   *@param  tmp  The new objectHookList value
   */
  public void setObjectHookList(ObjectHookList tmp) {
    this.objectHookList = tmp;
  }


  /**
   *  Sets the businessProcessList attribute of the ObjectHook object
   *
   *@param  tmp  The new businessProcessList value
   */
  public void setBusinessProcessList(BusinessProcessList tmp) {
    this.businessProcessList = tmp;
  }


  /**
   *  Sets the actionId attribute of the ObjectHook object
   *
   *@param  tmp  The new actionId value
   */
  public void setActionId(int tmp) {
    this.actionId = tmp;
  }


  /**
   *  Sets the manager attribute of the ObjectHook object
   *
   *@param  tmp  The new manager value
   */
  public void setManager(WorkflowManager tmp) {
    this.manager = tmp;
  }


  /**
   *  Gets the businessProcessList attribute of the ObjectHook object
   *
   *@return    The businessProcessList value
   */
  public BusinessProcessList getBusinessProcessList() {
    return businessProcessList;
  }


  /**
   *  Gets the objectHookList attribute of the ObjectHook object
   *
   *@return    The objectHookList value
   */
  public ObjectHookList getObjectHookList() {
    return objectHookList;
  }


  /**
   *  Gets the actionId attribute of the ObjectHook object
   *
   *@return    The actionId value
   */
  public int getActionId() {
    return actionId;
  }


  /**
   *  Gets the manager attribute of the ObjectHook object
   *
   *@return    The manager value
   */
  public WorkflowManager getManager() {
    return manager;
  }


  /**
   *  Constructor for the ObjectHook object
   *
   *@param  context  Description of the Parameter
   */
  public ObjectHook(ComponentContext context) {
    this.context = context;
  }


  /**
   *  Main processing method for the ObjectHook object
   */
  public void run() {
    try {
      if (System.getProperty("DEBUG") != null) {
        //Pause the thread so that the debug output is easier to read
        Thread.sleep(2000);
      }
      ObjectHookActionList actionList =
          (ObjectHookActionList) objectHookList.get(context.getClassName());
      if (actionList != null) {
        ObjectHookAction thisAction =
            (ObjectHookAction) actionList.get(new Integer(actionId));
        if (thisAction != null) {
          context.setProcessName(thisAction.getProcessName());
          if (businessProcessList != null) {
            BusinessProcess thisProcess = (BusinessProcess) businessProcessList.get(thisAction.getProcessName());
            if (thisProcess != null) {
              context.setProcess(thisProcess);
              manager.execute(context);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
    } finally {
    }
  }
}

