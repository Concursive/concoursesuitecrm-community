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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.aspcfs.utils.DatabaseUtils;
import com.darkhorseventures.framework.beans.GenericBean;
import com.darkhorseventures.framework.servlets.ControllerServlet;

/**
 * Description of the PageRoleMap
 *
 * @author Artem.Zakolodkin
 * @created Jan 18, 2007
 */
public class PageRoleMap extends GenericBean {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger
            .getLogger(PageRoleMap.class.getName());
    static {
        if (System.getProperty("DEBUG") != null) {
            LOGGER.setLevel(Level.DEBUG);
        }
    }
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int pageRoleMapId = -1;
    private int webPageId = -1;
    private int roleId = -1;
    private int enteredBy = -1;
    private java.sql.Timestamp entered = null;
    private int modifiedBy = -1;
    private java.sql.Timestamp modified = null;
    private boolean override = false;
    
    public PageRoleMap() {
    }

    
    /**
     * @param rs
     * @throws SQLException
     */
    public PageRoleMap(ResultSet rs) throws SQLException {
        buildRecord(rs);
    }

    /**
     * @param db
     * @param tmpPageRoleMapId
     * @return
     * @throws SQLException
     */
    public boolean queryRecord(Connection db, int tmpPageRoleMapId)
            throws SQLException {
        try {
        PreparedStatement pst = null;
        ResultSet rs = null;
        pst = db.prepareStatement(" SELECT * " + 
                                  "    FROM web_page_role_map " + 
                                  " WHERE page_role_map_id = ? ");
        pst.setInt(1, tmpPageRoleMapId);
        rs = pst.executeQuery();
        if (rs.next()) {
            buildRecord(rs);
        }
        rs.close();
        pst.close();
        if (pageRoleMapId == -1) {
            LOGGER.error("Web page role record not found.");
            throw new SQLException("Web page role record not found.");
        }
        }
        catch(Exception e) {
            LOGGER.error(e, e);
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    /**
     * @param db
     * @return
     * @throws SQLException
     */
    public boolean insert(Connection db) throws SQLException {
        try {
            PreparedStatement pst = db
                    .prepareStatement( "INSERT INTO web_page_role_map " + 
                                       "  ( web_page_id, " + 
                                       "    role_id , " + 
                                       "    enteredby , " + 
                                       "    modifiedby ) " + 
                                       " VALUES (?,?,?,?)");
            int i = 0;
            pst.setInt(++i, webPageId);
            pst.setInt(++i, roleId);
            pst.setInt(++i, enteredBy);
            pst.setInt(++i, modifiedBy);
            pst.execute();
            pst.close();
            pageRoleMapId = DatabaseUtils.getCurrVal(db,
                "web_page_role_map_page_role_map_id_seq", pageRoleMapId);
            
        } catch (Exception e) {
            LOGGER.error(e, e);
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    /**
     * @param db
     * @return
     * @throws SQLException
     */
    public boolean update(Connection db) throws SQLException {
        PreparedStatement pst = null;
        StringBuffer sql = new StringBuffer();
        try {
            sql.append( "UPDATE web_page_role_map " + 
                        "   SET " + 
                        "      web_page_id = ?," + 
                        "      role_id = ?");
            if (!override) {
                sql.append(" , modified = "
                        + DatabaseUtils.getCurrentTimestamp(db)
                        + " , modifiedby = ? ");
            }
            sql.append("WHERE page_role_map_id = ? ");
            if (!override) {
                sql.append("AND modified "
                        + ((this.getModified() == null) ? "IS NULL " : "= ? "));
            }
            pst = db.prepareStatement(sql.toString());
            int i = 0;
            pst.setInt(++i, webPageId);
            pst.setInt(++i, roleId);
            if (!override) {
                pst.setInt(++i, modifiedBy);
            }
            pst.setInt(++i, pageRoleMapId);
            if (!override && this.getModified() != null) {
                pst.setTimestamp(++i, modified);
            }
            pst.close();
            pageRoleMapId = DatabaseUtils.getCurrVal(db,
                "web_page_role_map_page_role_map_id_seq", pageRoleMapId);            
        } catch (Exception e) {
            LOGGER.error(e, e);
            throw new SQLException(e.getMessage());
        }
        return true;
    }

    /**
     * @param db
     * @return
     * @throws SQLException
     */
    public boolean delete(Connection db) throws SQLException {
        boolean commit = db.getAutoCommit();
        try {
            if (commit) {
                db.setAutoCommit(false);
            }
            PreparedStatement pst = db
                    .prepareStatement("DELETE FROM web_page_role_map WHERE page_role_map_id = ?");
            pst.setInt(1, pageRoleMapId);
            pst.execute();
            pst.close();
            if (commit) {
                db.commit();
            }
        } catch (SQLException e) {
            if (commit) {
                db.rollback();
            }
            LOGGER.error(e, e);
            throw new SQLException(e.getMessage());
        } finally {
            if (commit) {
                db.setAutoCommit(true);
            }
        }
        return true;
    }

    /**
     * @param rs
     * @throws SQLException
     */
    public void buildRecord(ResultSet rs) throws SQLException {
        pageRoleMapId = rs.getInt("page_role_map_id");
        webPageId = rs.getInt("web_page_id");
        roleId = rs.getInt("role_id");
        entered = rs.getTimestamp("entered");
        enteredBy = rs.getInt("enteredby");
        modified = rs.getTimestamp("modified");
        modifiedBy = rs.getInt("modifiedby");
    }

    /**
     * @return the entered
     */
    public java.sql.Timestamp getEntered() {
        return entered;
    }

    /**
     * @param entered the entered to set
     */
    public void setEntered(java.sql.Timestamp entered) {
        this.entered = entered;
    }

    /**
     * @return the enteredBy
     */
    public int getEnteredBy() {
        return enteredBy;
    }

    /**
     * @param enteredBy the enteredBy to set
     */
    public void setEnteredBy(int enteredBy) {
        this.enteredBy = enteredBy;
    }

    /**
     * @return the modified
     */
    public java.sql.Timestamp getModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(java.sql.Timestamp modified) {
        this.modified = modified;
    }

    /**
     * @return the modifiedBy
     */
    public int getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    /**
     * @return the override
     */
    public boolean isOverride() {
        return override;
    }

    /**
     * @param override the override to set
     */
    public void setOverride(boolean override) {
        this.override = override;
    }
}
