/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import org.aspcfs.modules.admin.base.Role;
import org.aspcfs.modules.admin.base.RoleList;

/**
 * Description of the PageRoleMapList
 *
 * @author Artem.Zakolodkin
 * @created Jan 18, 2007
 */
public class PageRoleMapList extends ArrayList {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int pageRoleMapId = -1;
    private int webPageId = -1;
    private int roleId = -1;
    
    
    /**
     * @param db
     * @throws SQLException
     */
    public void buildList(Connection db) throws SQLException {
        PreparedStatement pst = null;
        ResultSet rs = queryList(db, pst);
        while (rs.next()) {
            PageRoleMap thisPageRow = this.getObject(rs);
            this.add(thisPageRow);
        }
        rs.close();
    }
    
    
    /**
     * @param db
     * @throws SQLException
     */
    public void delete(Connection db) throws SQLException {
        Iterator pageRoleMapIterator = this.iterator();
        while (pageRoleMapIterator.hasNext()) {
            PageRoleMap thisPageRoleMap = (PageRoleMap) pageRoleMapIterator.next();
            thisPageRoleMap.delete(db);
        }
        
    }
    
    /**
     * @param db
     * @param pst
     * @return
     * @throws SQLException
     */
    public ResultSet queryList(Connection db, PreparedStatement pst)
            throws SQLException {
        ResultSet rs = null;
        StringBuffer sqlSelect = new StringBuffer();
        StringBuffer sqlFilter = new StringBuffer();
        StringBuffer sqlOrder = new StringBuffer();
        createFilter(sqlFilter, db);
        sqlSelect.append("SELECT wprm.* " +
                         "  FROM web_page_role_map wprm " + 
                         " WHERE page_role_map_id > -1 ");
        pst = db.prepareStatement(sqlSelect.toString() + 
                                  sqlFilter.toString() + 
                                  sqlOrder.toString());
        prepareFilter(pst);
        rs = pst.executeQuery();
        return rs;
    }
    
    /**
     * @param pst
     * @return
     * @throws SQLException
     */
    private int prepareFilter(PreparedStatement pst) throws SQLException {
        int i = 0;
        if (pageRoleMapId > - 1) {
            pst.setInt(++i, pageRoleMapId);
        }
        if (webPageId > - 1) {
            pst.setInt(++i, webPageId);
        }
        if (roleId > - 1) {
            pst.setInt(++i, roleId);
        }
        return i;
    }
    
    /**
     * @param sqlFilter
     * @param db
     * @throws SQLException
     */
    private void createFilter(StringBuffer sqlFilter, Connection db) throws SQLException {
        if (pageRoleMapId > - 1) {
            sqlFilter.append("AND page_role_map_id = ? ");
        }
        if (webPageId > - 1) {
            sqlFilter.append("AND web_page_id = ? ");
        }
        if (roleId > - 1) {
            sqlFilter.append("AND role_id = ? ");
        }
    }
    
    public boolean getPermissionForRole(int roleId) {
    	boolean result = false;
    	for (int i = 0; i < this.size(); i++) {
    		if (((PageRoleMap)get(i)).getRoleId() == roleId) {
    			result = true;
    			break;
    		}
    	}
    	return result;
    }
    
    public boolean containsPageId(int pageId) {
    	boolean result = false;
    	for (int i = 0; i < this.size(); i++) {
    		if (((PageRoleMap)get(i)).getWebPageId() == pageId) {
    			result = true;
    			break;
    		}
    	}
    	return result;
    }
    
    public RoleList buildRoleList(Connection db) throws SQLException {
    	RoleList roleList = new RoleList();
			Iterator i = this.iterator();
			while (i.hasNext()) {
				PageRoleMap pageRoleMap = (PageRoleMap)i.next();
					Role role = new Role(db, pageRoleMap.getRoleId());
					roleList.add(role);
			}
    	return roleList;
    }
    
    public PageRoleMap getObject(ResultSet rs) throws SQLException {
        return new PageRoleMap(rs);
    }
    
    /**
     * @return the pageRoleMapId
     */
    public int getPageRoleMapId() {
        return pageRoleMapId;
    }
    /**
     * @param pageRoleMapId the pageRoleMapId to set
     */
    public void setPageRoleMapId(int pageRoleMapId) {
        this.pageRoleMapId = pageRoleMapId;
    }
    /**
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }
    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    /**
     * @return the webPageId
     */
    public int getWebPageId() {
        return webPageId;
    }
    /**
     * @param webPageId the webPageId to set
     */
    public void setWebPageId(int webPageId) {
        this.webPageId = webPageId;
    }
    
}
