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

import java.util.*;


/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    December 18, 2002
 *@version    $Id$
 */
public class DependencyList extends ArrayList {
  private String title = null;


  /**
   *  Sets the title attribute of the DependencyList object
   *
   *@param  title  The new title value
   */
  public void setTitle(String title) {
    this.title = title;
  }


  /**
   *  Gets the title attribute of the DependencyList object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
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
   *  Gets the htmlString attribute of the DependencyList object
   *
   *@return    The htmlString value
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
        if (thisDependency.getCanDelete()){
          html.append("- ");
        }else{
          html.append("* ");
          canDelete = false;
        }
        html.append(thisDependency.getName() + " (" + thisDependency.getCount() + ")");
        html.append("<br />");
      }
    }
    if (count == 0) {
      html.append("&nbsp;&nbsp;There are no dependencies for this action.<br />");
    }
    if (!canDelete){
      html.append("<br />(*) Indicates any dependency preventing this item from being deleted.");
      html.append("<br />NOTE: Some of these may not be accessible if they are not owned by you.<br />");
    }
    return html.toString();
  }
}

