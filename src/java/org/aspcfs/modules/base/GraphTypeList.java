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

import org.aspcfs.utils.web.PagedListInfo;
import org.aspcfs.utils.web.HtmlSelect;

import java.util.Vector;
import java.util.Iterator;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Contains GraphType items for displaying to the user
 *
 * @author     dharmas
 * @version $Id: GraphTypeList.java 4.1 2007-03-30 11:32:36 +0530 (Fri, 30 Mar 2007) dharmas $
 * @created    Mar 30, 2007
 *
 */
public class GraphTypeList extends Vector {

    private PagedListInfo pagedListInfo = null;
    private int enabledState = -1;
    private String tableName = null;

    /**
     *  Sets the pagedListInfo attribute of the GraphTypeList object
     *
     * @param  tmp  The new pagedListInfo value
     */
    public void setPagedListInfo(PagedListInfo tmp) {
        this.pagedListInfo = tmp;
    }

    /**
     *  Gets the pagedListInfo attribute of the GraphTypeList object
     *
     * @return    The pagedListInfo value
     */
    public PagedListInfo getPagedListInfo() {
        return pagedListInfo;
    }

    /**
     *  Sets the enabledState attribute of the GraphTypeList object
     *
     * @param  tmp  The new enabledState value
     */
    public void setEnabledState(int tmp) {
        this.enabledState = tmp;
    }

    /**
     *  Gets the enabledState attribute of the GraphTypeList object
     *
     * @return    The enabledState value
     */
    public int getEnabledState() {
        return enabledState;
    }
    /**
     *  Sets the tableName attribute of the GraphTypeList object
     *
     * @param  tmp  The new tableName value
     */
    public void setTableName(String tmp) {
        this.tableName = tmp;
    }


    /**
     *  Gets the tableName attribute of the GraphTypeList object
     *
     * @return    The tableName value
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Gets the htmlSelect attribute of the GraphTypeList object
     *
     * @param  selectName  Description of the Parameter
     * @param  defaultKey  Description of the Parameter
     * @return             The htmlSelect value
     */
    public String getHtmlSelect(String selectName, int defaultKey) {
        HtmlSelect graphListSelect = new HtmlSelect();
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            GraphType graph = (GraphType) iter.next();
            graphListSelect.addItem(graph.getId(), graph.getDescription());
        }
        return graphListSelect.getHtml(selectName, defaultKey);
    }
    public void buildList(Connection db) throws SQLException {
        if (tableName == null) {
            throw new SQLException("Not configured");
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        int items = -1;
        StringBuffer sqlSelect = new StringBuffer();
        StringBuffer sqlCount = new StringBuffer();
        StringBuffer sqlFilter = new StringBuffer();
        StringBuffer sqlOrder = new StringBuffer();

        //Need to build a base SQL statement for counting records
        sqlCount.append(
                "SELECT COUNT(*) AS recordcount " +
                        "FROM " + tableName + " g " +
                        "WHERE g.code > -1 ");
        createFilter(sqlFilter);
        if (pagedListInfo != null) {
            //Get the total number of records matching filter
            pst = db.prepareStatement(
                    sqlCount.toString() +
                            sqlFilter.toString());
            items = prepareFilter(pst);
            rs = pst.executeQuery();
            if (rs.next()) {
                int maxRecords = rs.getInt("recordcount");
                pagedListInfo.setMaxRecords(maxRecords);
            }
            rs.close();
            pst.close();
            //Determine the offset, based on the filter, for the first record to show
            if (!pagedListInfo.getCurrentLetter().equals("")) {
                pst = db.prepareStatement(
                        sqlCount.toString() +
                                sqlFilter.toString() +
                                "AND g.id < ? ");
                items = prepareFilter(pst);
                pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
                rs = pst.executeQuery();
                if (rs.next()) {
                    int offsetCount = rs.getInt("recordcount");
                    pagedListInfo.setCurrentOffset(offsetCount);
                }
                rs.close();
                pst.close();
            }
            //Determine column to sort by
            pagedListInfo.setDefaultSort("g.description", null);
            pagedListInfo.appendSqlTail(db, sqlOrder);
        } else {
            sqlOrder.append("ORDER BY g.description");
        }
        //Need to build a base SQL statement for returning records
        if (pagedListInfo != null) {
            pagedListInfo.appendSqlSelectHead(db, sqlSelect);
        } else {
            sqlSelect.append("SELECT ");
        }
        sqlSelect.append(
                "g.* " +
                        "FROM " + tableName + " g " +
                        "WHERE g.code > -1 ");
        pst = db.prepareStatement(
                sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
        items = prepareFilter(pst);
        if (pagedListInfo != null) {
            pagedListInfo.doManualOffset(db, pst);
        }
        rs = pst.executeQuery();
        if (pagedListInfo != null) {
            pagedListInfo.doManualOffset(db, rs);
        }
        while (rs.next()) {
            GraphType thisGraph = new GraphType(rs);
            this.add(thisGraph);
        }
        rs.close();
        pst.close();
    }


    /**
     *  Description of the Method
     *
     * @param  sqlFilter  Description of the Parameter
     */
    private void createFilter(StringBuffer sqlFilter) {
        if (sqlFilter == null) {
            sqlFilter = new StringBuffer();
        }
        if (enabledState != -1) {
            sqlFilter.append("AND g.enabled = ? ");
        }

    }


    /**
     *  Description of the Method
     *
     * @param  pst            Description of the Parameter
     * @return                Description of the Return Value
     * @throws  SQLException  Description of the Exception
     */
    private int prepareFilter(PreparedStatement pst) throws SQLException {
        int i = 0;
        if (enabledState != -1) {
            pst.setBoolean(++i, enabledState == Constants.TRUE);
        }

        return i;
    }
}
