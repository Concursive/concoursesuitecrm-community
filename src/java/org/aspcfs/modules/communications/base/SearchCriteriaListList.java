//Copyright 2001 Dark Horse Ventures
//The createFilter method and the prepareFilter method need to have the same
//number of parameters if modified.

package com.darkhorseventures.cfsbase;

import java.util.Vector;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import com.darkhorseventures.webutils.PagedListInfo;
import com.darkhorseventures.webutils.HtmlSelect;


public class SearchCriteriaListList extends Vector {

	private PagedListInfo pagedListInfo = null;
	private int owner = -1;
  private String ownerIdRange = null;
  private int campaignId = -1;

	public SearchCriteriaListList() { }
	public PagedListInfo getPagedListInfo() { return pagedListInfo; }
	public int getOwner() { return owner; }
	public void setPagedListInfo(PagedListInfo tmp) { this.pagedListInfo = tmp; }
	public void setOwner(int tmp) { this.owner = tmp; }
	public void setOwner(String tmp) { this.owner = Integer.parseInt(tmp); }
  public void setOwnerIdRange(String tmp) { this.ownerIdRange = tmp; }
  public void setCampaignId(int tmp) { this.campaignId = tmp; }

	public void buildList(Connection db) throws SQLException {

		PreparedStatement pst = null;
		ResultSet rs = null;
		int items = -1;

		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlCount = new StringBuffer();
		StringBuffer sqlFilter = new StringBuffer();
		StringBuffer sqlOrder = new StringBuffer();

		//Need to build a base SQL statement for returning records
		sqlSelect.append(
			"SELECT scl.* " +
			"FROM saved_criterialist scl " +
			"WHERE scl.id > -1 ");

		//Need to build a base SQL statement for counting records
		sqlCount.append(
				"SELECT COUNT(*) AS recordcount " +
				"FROM saved_criterialist scl " +
				"WHERE scl.id > -1 ");

		createFilter(sqlFilter);

		if (pagedListInfo != null) {
			//Get the total number of records matching filter
			pst = db.prepareStatement(sqlCount.toString() +
					sqlFilter.toString());
			items = prepareFilter(pst);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxRecords = rs.getInt("recordcount");
				pagedListInfo.setMaxRecords(maxRecords);
			}
			pst.close();
			rs.close();

			//Determine the offset, based on the filter, for the first record to show
			if (!pagedListInfo.getCurrentLetter().equals("")) {
				pst = db.prepareStatement(sqlCount.toString() +
						sqlFilter.toString() +
						"AND name < ? ");
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
			if (pagedListInfo.getColumnToSortBy() == null || pagedListInfo.getColumnToSortBy().equals("")) {
        pagedListInfo.setColumnToSortBy("name");
      }
      sqlOrder.append("ORDER BY " + pagedListInfo.getColumnToSortBy() + " ");
      if (pagedListInfo.getSortOrder() != null && !pagedListInfo.getSortOrder().equals("")) {
        sqlOrder.append(pagedListInfo.getSortOrder() + " ");
      }

			//Determine items per page
			if (pagedListInfo.getItemsPerPage() > 0) {
				sqlOrder.append("LIMIT " + pagedListInfo.getItemsPerPage() + " ");
			}

			sqlOrder.append("OFFSET " + pagedListInfo.getCurrentOffset() + " ");
		}
		else {
			sqlOrder.append("ORDER BY name ");
		}

		pst = db.prepareStatement(sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
		items = prepareFilter(pst);
		rs = pst.executeQuery();
		while (rs.next()) {
			SearchCriteriaList thisList = new SearchCriteriaList(rs);
			this.addElement(thisList);
		}
		rs.close();
		pst.close();
    
    Iterator i = this.iterator();
    while (i.hasNext()) {
      SearchCriteriaList thisList = (SearchCriteriaList)i.next();
      thisList.buildResources(db);
    }
	}

	/**
	 *  Builds a base SQL where statement for filtering records to be used by
	 *  sqlSelect and sqlCount
	 *
	 *@param  sqlFilter  Description of Parameter
	 *@since             1.3
	 */
	private void createFilter(StringBuffer sqlFilter) {
		if (sqlFilter == null) {
			sqlFilter = new StringBuffer();
		}

		if (owner != -1) {
			sqlFilter.append("AND scl.owner = ? ");
		}
    
    if (ownerIdRange != null) {
      sqlFilter.append("AND scl.owner IN (" + ownerIdRange + ") ");
    }
    
    if (campaignId != -1) {
      sqlFilter.append("AND id in (SELECT group_id FROM campaign_list_groups WHERE campaign_id = " + campaignId + ") ");
    }
	}


	/**
	 *  Sets the parameters for the preparedStatement - these items must correspond
	 *  with the createFilter statement
	 *
	 *@param  pst               Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since                    1.3
	 */
	private int prepareFilter(PreparedStatement pst) throws SQLException {
		int i = 0;

		if (owner != -1) {
			pst.setInt(++i, owner);
		}

		return i;
	}

}

