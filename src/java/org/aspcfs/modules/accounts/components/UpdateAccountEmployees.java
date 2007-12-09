/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.accounts.components;

import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;
import org.aspcfs.modules.accounts.base.Organization;
import org.aspcfs.modules.accounts.base.OrganizationList;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.relationships.base.Relationship;
import org.aspcfs.modules.relationships.base.RelationshipList;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author partha
 * @version $Id$
 * @created October 25, 2005
 */
public class UpdateAccountEmployees extends ObjectHookComponent implements ComponentInterface {
  /**
   * Gets the description attribute of the UpdateAccountEmployees object
   *
   * @return The description value
   */
  public String getDescription() {
    return "Update the parent account employee count.";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public boolean execute(ComponentContext context) {
    boolean result = false;
    Connection db = null;
    try {
      db = getConnection(context);
      HashMap combinedAccounts = OrganizationList.getParentAndLeafAccounts(db, 1, false);
      HashMap parentAccounts = (HashMap) combinedAccounts.get("parentNodes");
      HashMap leafAccounts = (HashMap) combinedAccounts.get("leafNodes");
      Iterator iter = (Iterator) parentAccounts.keySet().iterator();
      while (iter.hasNext()) {
        Integer account = (Integer) iter.next();
        int totalEmployees = processTotalEmployees(db, leafAccounts, account.intValue(), 1, false, new HashMap());
      }
      if (parentAccounts.size() > 0) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace(System.out);
    } finally {
      freeConnection(context, db);
    }
    return result;
  }


  /**
   * Gets the totalEmployees attribute of the UpdateAccountEmployees object
   *
   * @param db         Description of the Parameter
   * @param parentId   Description of the Parameter
   * @param typeId     Description of the Parameter
   * @param reciprocal Description of the Parameter
   * @return The totalEmployees value
   * @throws SQLException Description of the Exception
   */
  public int processTotalEmployees(Connection db, HashMap leafAccounts, int parentId, int typeId, boolean reciprocal, HashMap existingAccounts) throws SQLException {
    int parentEmployeeCount = -1;
    int actualEmployeeCount = Organization.countEmployees(db, parentId);
    RelationshipList thisList = new RelationshipList();
    thisList.setCategoryIdMapsFrom(Constants.ACCOUNT_OBJECT);
    thisList.setCategoryIdMapsTo(Constants.ACCOUNT_OBJECT);
    if (reciprocal) {
      thisList.setObjectIdMapsFrom(parentId);
    } else {
      thisList.setObjectIdMapsTo(parentId);
    }
    thisList.setTypeId(1);
    thisList.buildList(db);
    Iterator iter = (Iterator) thisList.keySet().iterator();
    if (iter.hasNext()) {
      parentEmployeeCount = 0;
    }
    while (iter.hasNext()) {
      String relType = (String) iter.next();
      ArrayList tmpList = (ArrayList) thisList.get(relType);
      Iterator j = tmpList.iterator();
      while (j.hasNext()) {
        Relationship rel = (Relationship) j.next();
        int childEmployeeCount = 0;
        if (existingAccounts.get(String.valueOf(rel.getObjectIdMapsFrom())) == null) {
          childEmployeeCount = processTotalEmployees(db, leafAccounts,
            (reciprocal ? rel.getObjectIdMapsTo() : rel.getObjectIdMapsFrom()), typeId, reciprocal, existingAccounts);
        }
        if (childEmployeeCount > 0) {
          parentEmployeeCount += childEmployeeCount;
        }
      }
    }
    if (actualEmployeeCount != parentEmployeeCount && leafAccounts.get(new Integer(parentId)) == null) {
      Organization.updateEmployeeCount(db, parentId, parentEmployeeCount);
      return parentEmployeeCount;
    }
    return actualEmployeeCount;
  }
}

