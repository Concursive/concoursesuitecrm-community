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
package org.aspcfs.modules.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.web.LookupElement;

/**
 * Description of the ContainerMenu
 * 
 * @author Artem.Zakolodkin
 * @created Jan 18, 2007
 */
public class ContainerMenu extends LookupElement {
    int linkModuleId = -1;
    String cname = null;

    public ContainerMenu() {
        super.setTableName("lookup_container_menu");
    }

    /**
     * @param db
     * @param code
     * @throws SQLException
     */
    public ContainerMenu(Connection db, int code) throws SQLException {
        PreparedStatement pst = db.prepareStatement("SELECT * "
                + "FROM lookup_container_menu " + "WHERE code = ? ");
        pst.setInt(1, code);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            build(rs);
        }
        rs.close();
        pst.close();
    }

    /**
     * @param rs
     * @throws SQLException
     */
    public ContainerMenu(ResultSet rs) throws SQLException {
        build(rs);
    }

    /* (non-Javadoc)
     * @see org.aspcfs.utils.web.LookupElement#build(java.sql.ResultSet)
     */
    public void build(ResultSet rs) throws SQLException {
        super.build(rs);
        cname = rs.getString("cname");
        linkModuleId = rs.getInt("link_module_id");
    }

    /* (non-Javadoc)
     * @see org.aspcfs.utils.web.LookupElement#insert(java.sql.Connection)
     */
    public boolean insert(Connection db) throws SQLException {
        int id = DatabaseUtils.getNextSeq(db, "lookup_container_menu_code_seq");
        int i = 0;
        PreparedStatement pst = db
                .prepareStatement("INSERT INTO lookup_container_menu " + 
                                  "(" + (id > -1 ? "code, " : "") + 
                                  " link_module_id, description, default_item, "  + DatabaseUtils.addQuotes(db, "level") + 
                                  ", enabled, cname) " + 
                                  "VALUES ("+ (id > -1 ? "?, " : "") + 
                                  "?, ?, ?, ?, ?, ?) ");
        if (id > -1) {
            pst.setInt(++i, id);
        }
        pst.setInt(++i, linkModuleId);
        pst.setString(++i, this.getDescription());
        pst.setBoolean(++i, defaultItem);
        pst.setInt(++i, level);
        pst.setBoolean(++i, enabled);
        pst.setString(++i, cname);
        pst.execute();
        pst.close();
        code = DatabaseUtils.getCurrVal(db, "lookup_container_menu_code_seq", id);
        return true;
    }

    /**
     * @return the cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname
     *            the cname to set
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the linkModuleId
     */
    public int getLinkModuleId() {
        return linkModuleId;
    }

    /**
     * @param linkModuleId
     *            the linkModuleId to set
     */
    public void setLinkModuleId(int linkModuleId) {
        this.linkModuleId = linkModuleId;
    }
}
