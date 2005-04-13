package com.zeroio.iteam.utils;

import org.aspcfs.modules.accounts.base.OrganizationList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.aspcfs.modules.base.Constants;

/**
 *  Description
 *
 *@author     mrajkowski
 *@created    Mar 1, 2005
 *@version    $Id: ProjectUtils.java,v 1.1.2.1 2005/03/02 18:43:59 mrajkowski
 *      Exp $
 */

public class ProjectUtils {
  /**
   *  Adds a feature to the Account attribute of the ProjectUtils class
   *
   *@param  db                The feature to be added to the Account attribute
   *@param  projectId         The feature to be added to the Account attribute
   *@param  orgId             The feature to be added to the Account attribute
   *@exception  SQLException  Description of the Exception
   */
  public static synchronized void addAccount(Connection db, int projectId, int orgId) throws SQLException {
    OrganizationList organizationList = new OrganizationList();
    organizationList.setProjectId(projectId);
    organizationList.setOrgId(orgId);
    organizationList.buildList(db);
    if (organizationList.size() == 0) {
      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO project_accounts " +
          "(project_id, org_id) VALUES (?, ?) ");
      pst.setInt(1, projectId);
      pst.setInt(2, orgId);
      pst.execute();
      pst.close();
    }
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  projectId         Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void removeAccount(Connection db, int projectId, int orgId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_accounts " +
        "WHERE project_id = ? AND org_id = ? ");
    pst.setInt(1, projectId);
    pst.setInt(2, orgId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  orgId             Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public static void removeAccounts(Connection db, int orgId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM project_accounts " +
        "WHERE org_id = ? ");
    pst.setInt(1, orgId);
    pst.execute();
    pst.close();
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  moduleId          Description of the Parameter
   *@param  itemId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static int retrieveRecordCount(Connection db, int moduleId, int itemId) throws SQLException {
    int count = 0;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT COUNT(*) as itemcount " +
        "FROM project_accounts ");

    if (moduleId == Constants.ACCOUNTS) {
      sql.append("WHERE org_id = ? ");
    }
    PreparedStatement pst = db.prepareStatement(sql.toString());
    if (moduleId == Constants.ACCOUNTS) {
      pst.setInt(1, itemId);
    }
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      count = rs.getInt("itemcount");
    }
    rs.close();
    pst.close();
    return count;
  }

}
