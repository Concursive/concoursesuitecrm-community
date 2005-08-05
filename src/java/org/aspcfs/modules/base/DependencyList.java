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
package org.aspcfs.modules.base;

import org.aspcfs.controller.SystemStatus;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $Id$
 *          $
 * @created December 18, 2002
 */
public class DependencyList extends ArrayList {
  private String title = null;
  private SystemStatus systemStatus = null;


  /**
   * Sets the title attribute of the DependencyList object
   *
   * @param title The new title value
   */
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   * Sets the systemStatus attribute of the DependencyList object
   *
   * @param tmp The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   * Gets the systemStatus attribute of the DependencyList object
   *
   * @return The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   * Gets the title attribute of the DependencyList object
   *
   * @return The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public boolean canDelete() {
    Iterator thisList = this.iterator();
    boolean canDelete = true;
    while (thisList.hasNext()) {
      Dependency thisDependency = (Dependency) thisList.next();
      if (!thisDependency.getCanDelete() && thisDependency.getCount() > 0) {
        canDelete = false;
      }
    }
    return canDelete;
  }


  /**
   * Gets the htmlString attribute of the DependencyList object
   *
   * @return The htmlString value
   */
  public String getHtmlString() {
    boolean canDelete = true;
    Iterator i = this.iterator();
    StringBuffer html = new StringBuffer();
    html.append("<br />");
    int count = 0;
    while (i.hasNext()) {
      Dependency thisDependency = (Dependency) i.next();
      if (thisDependency.getCount() > 0) {
        ++count;
        html.append("&nbsp;&nbsp;");
        if (thisDependency.getCanDelete()) {
          html.append("- ");
        } else {
          html.append("- ");
          canDelete = false;
        }
        if (systemStatus != null) {
          html.append(
              systemStatus.getLabel("dependency." + thisDependency.getName()) + " (" + thisDependency.getCount() + ")");
        }
        html.append("<br />");
      }
    }
    if (count == 0) {
      if (systemStatus != null) {
        html.append(
            "&nbsp;&nbsp;" + systemStatus.getLabel(
                "dependency.noDependencyForAction") + "<br />");
      }
    }
    if (!canDelete) {
      if (systemStatus != null) {
        //html.append("<br />(*) "+systemStatus.getLabel("dependency.preventingDeletion"));
        //html.append("<br />"+systemStatus.getLabel("dependency.note")+"<br />");
      }
    }
    return html.toString();
  }


  /**
   * Gets the dependency attribute of the DependencyList object
   *
   * @param name Description of the Parameter
   * @return The dependency value
   */
  public int getDependencyCount(String name) {
    Iterator iterator = (Iterator) this.iterator();
    int result = 0;
    while (iterator.hasNext()) {
      Dependency dependency = (Dependency) iterator.next();
      if (dependency.getName().equals(name)) {
        result = dependency.getCount();
        break;
      }
    }
    return result;
  }
}

