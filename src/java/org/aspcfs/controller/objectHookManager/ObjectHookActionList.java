//Copyright 2002 Dark Horse Ventures
package com.darkhorseventures.controller;

import java.util.*;
import org.w3c.dom.Element;
import com.darkhorseventures.utils.*;
import com.darkhorseventures.cfsbase.Constants;

/**
 *  Maintains a list of action mappings for the class trigger... Ex. A ticket
 *  can be triggered by at least the following actions: insert, update, delete
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id$
 */
public class ObjectHookActionList extends HashMap {
  private int enabled = -1;


  /**
   *  Constructor for the ObjectHookActionList object
   */
  public ObjectHookActionList() { }


  /**
   *  Constructor for the ObjectHookActionList object
   *
   *@param  hookElement  Description of the Parameter
   *@param  enabled      Description of the Parameter
   */
  public ObjectHookActionList(Element hookElement, int enabled) {
    Element actions = XMLUtils.getFirstElement(hookElement, "actions");
    if (actions != null) {
      ArrayList actionNodes = XMLUtils.getElements(actions, "action");
      Iterator actionElements = actionNodes.iterator();
      while (actionElements.hasNext()) {
        Element actionElement = (Element) actionElements.next();
        String actionEnabled = (String) actionElement.getAttribute("enabled");
        if (enabled == Constants.TRUE && actionEnabled != null && "false".equals(actionEnabled)) {
          break;
        }
        ObjectHookAction thisAction = new ObjectHookAction(actionElement);
        this.put(new Integer(thisAction.getTypeId()), thisAction);
      }
    }
  }
}

