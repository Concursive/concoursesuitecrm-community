//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import org.theseus.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *  Description of the Class
 *
 *@author     chris
 *@created    December 11, 2001
 */
public class TicketCategory extends GenericBean {

	private int id = -1;
	private int parentCode = -1;
	private String description = "";
	private boolean enabled = true;
	private int level = -1;

	/**
	 *  Constructor for the TicketCategory object
	 *
	 *@since
	 */
	public TicketCategory() { }


	/**
	 *  Constructor for the TicketCategory object
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public TicketCategory(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	/**
	 *  Constructor for the TicketCategory object
	 *
	 *@param  db                Description of Parameter
	 *@param  code              Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public TicketCategory(Connection db, String id) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT tc.* " +
				"FROM ticket_category tc " +
				"WHERE tc.id > -1 ");

		if (id != null && !id.equals("")) {
			sql.append("AND tc.id = " + id + " ");
		}
		else {
			throw new SQLException("Ticket Category code not specified.");
		}

		st = db.createStatement();
		rs = st.executeQuery(sql.toString());
		if (rs.next()) {
			buildRecord(rs);
		}
		else {
			rs.close();
			st.close();
			throw new SQLException("Ticket Category record not found.");
		}
		rs.close();
		st.close();
	}


	/**
	 *  Sets the Code attribute of the TicketCategory object
	 *
	 *@param  tmp  The new Code value
	 *@since
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the Level attribute of the TicketCategory object
	 *
	 *@param  level  The new Level value
	 *@since
	 */
	public void setLevel(int level) {
		this.level = level;
	}


	/**
	 *  Sets the Level attribute of the TicketCategory object
	 *
	 *@param  level  The new Level value
	 *@since
	 */
	public void setLevel(String level) {
		this.level = Integer.parseInt(level);
	}


	/**
	 *  Sets the Code attribute of the TicketCategory object
	 *
	 *@param  tmp  The new Code value
	 *@since
	 */
	public void setId(String tmp) {
		this.id = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the ParentCode attribute of the TicketCategory object
	 *
	 *@param  tmp  The new ParentCode value
	 *@since
	 */
	public void setParentCode(int tmp) {
		this.parentCode = tmp;
	}


	/**
	 *  Sets the ParentCode attribute of the TicketCategory object
	 *
	 *@param  tmp  The new ParentCode value
	 *@since
	 */
	public void setParentCode(String tmp) {
		this.parentCode = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the Description attribute of the TicketCategory object
	 *
	 *@param  tmp  The new Description value
	 *@since
	 */
	public void setDescription(String tmp) {
		this.description = tmp;
	}


	/**
	 *  Sets the Enabled attribute of the TicketCategory object
	 *
	 *@param  tmp  The new Enabled value
	 *@since
	 */
	public void setEnabled(boolean tmp) {
		this.enabled = tmp;
	}


	/**
	 *  Gets the Level attribute of the TicketCategory object
	 *
	 *@return    The Level value
	 *@since
	 */
	public int getLevel() {
		return level;
	}


	/**
	 *  Gets the Code attribute of the TicketCategory object
	 *
	 *@return    The Code value
	 *@since
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the ParentCode attribute of the TicketCategory object
	 *
	 *@return    The ParentCode value
	 *@since
	 */
	public int getParentCode() {
		return parentCode;
	}


	/**
	 *  Gets the Description attribute of the TicketCategory object
	 *
	 *@return    The Description value
	 *@since
	 */
	public String getDescription() {
		return description;
	}


	/**
	 *  Gets the Enabled attribute of the TicketCategory object
	 *
	 *@return    The Enabled value
	 *@since
	 */
	public boolean getEnabled() {
		return enabled;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  db                Description of Parameter
	 *@return                   Description of the Returned Value
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	public boolean insert(Connection db) throws SQLException {

		StringBuffer sql = new StringBuffer();

		try {
			db.setAutoCommit(false);
			sql.append(
					"INSERT INTO TICKET_CATEGORY " +
					"(parent_cat_code, description, enabled, cat_level) " +
					"VALUES (?, ?, ?, ?) ");

			int i = 0;
			
			PreparedStatement pst = db.prepareStatement(sql.toString());
			pst.setInt(++i, this.getParentCode());
			pst.setString(++i, this.getDescription());
			pst.setBoolean(++i, this.getEnabled());
			pst.setInt(++i, this.getLevel());
			
			pst.execute();
			pst.close();
			
			Statement st = db.createStatement();
			ResultSet rs = st.executeQuery("select currval('ticket_category_id_seq')");
			if (rs.next()) {
				this.setId(rs.getInt(1));
			}
			rs.close();
			st.close();
			
			//this.update(db, true);
			db.commit();
		}
		catch (SQLException e) {
			db.rollback();
			db.setAutoCommit(true);
			throw new SQLException(e.getMessage());
		}
		finally {
			db.setAutoCommit(true);
		}
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  rs                Description of Parameter
	 *@exception  SQLException  Description of Exception
	 *@since
	 */
	protected void buildRecord(ResultSet rs) throws SQLException {
		id = rs.getInt("id");
		level = rs.getInt("cat_level");
		parentCode = rs.getInt("parent_cat_code");
		description = rs.getString("description");
		enabled = rs.getBoolean("enabled");
	}
}

