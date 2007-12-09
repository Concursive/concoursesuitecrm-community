/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.aspcfs.utils.web.LookupList;

/**
 * Description of the ContainerMenuList
 * 
 * @author Artem.Zakolodkin
 * @created Jan 18, 2007
 */
public class ContainerMenuList extends LookupList {
    
    int linkModuleId = -1;
    int containerId = -1;
    String cname = "";
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ContainerMenuList() {
    }

    public ContainerMenuList(Connection db, int linkModuleId)
            throws SQLException {
        queryRecord(db, linkModuleId);
    }

    public void queryRecord(Connection db, int linkModuleId)
            throws SQLException {
        this.linkModuleId = linkModuleId;
        buildList(db);
    }

    public void buildList(Connection db) throws SQLException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        StringBuffer sqlOrder = new StringBuffer();
        StringBuffer sqlFilter = new StringBuffer();
        StringBuffer sqlSelect = new StringBuffer();
        createFilter(sqlFilter);
        sqlSelect.append("SELECT lat.* " + "FROM lookup_container_menu lat "
                + "WHERE code > -1 ");
        pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString()
                + sqlOrder.toString());
        prepareFilter(pst);
        rs = pst.executeQuery();
        while (rs.next()) {
            ContainerMenu thisContainerMenu = new ContainerMenu(rs);
            this.add(thisContainerMenu);
        }
        rs.close();
        pst.close();
    }

    private void createFilter(StringBuffer sqlFilter) {
        if (sqlFilter == null) {
            sqlFilter = new StringBuffer();
        }
        if (linkModuleId > -1) {
            sqlFilter.append("AND link_module_id = ? ");
        }
        if (cname != "") {
          sqlFilter.append("AND cname = ? ");
        }
        if (containerId > -1) {
          sqlFilter.append("AND code = ? ");
        }
    }

    private int prepareFilter(PreparedStatement pst) throws SQLException {
        int i = 0;
        if (linkModuleId > -1) {
            pst.setInt(++i, linkModuleId);
        }
        if (cname != "") {
          pst.setString(++i, cname);
        }
        if (containerId > -1) {
          pst.setInt(++i, containerId);
        }
        return i;
    }

    public static boolean exists(Connection db, int linkModuleId, String cname)
            throws SQLException {
        boolean result = false;
        PreparedStatement pst = db.prepareStatement("SELECT code "
                + "FROM lookup_container_menu " 
                + "  WHERE link_module_id = ? "
                + "  AND cname = ? ");
        pst.setInt(1, linkModuleId);
        pst.setString(2, cname);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            result = true;
        }
        rs.close();
        pst.close();
        return result;
    }

    /**
     * @return the linkModuleId
     */
    public int getLinkModuleId() {
        return linkModuleId;
    }

    /**
     * @param linkModuleId the linkModuleId to set
     */
    public void setLinkModuleId(int linkModuleId) {
        this.linkModuleId = linkModuleId;
    }

    /**
     * @return the cname
     */
    public String getCname() {
      return cname;
    }

    /**
     * @param cname the cname to set
     */
    public void setCname(String cname) {
      this.cname = cname;
    }

		/**
		 * @return the containerId
		 */
		public int getContainerId() {
			return containerId;
		}

		/**
		 * @param containerId the containerId to set
		 */
		public void setContainerId(int containerId) {
			this.containerId = containerId;
		}
}
