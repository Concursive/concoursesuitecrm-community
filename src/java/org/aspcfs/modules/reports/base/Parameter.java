//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.reports.base;

import com.darkhorseventures.framework.beans.*;
import com.darkhorseventures.framework.actions.*;
import org.aspcfs.modules.accounts.base.Organization;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.actionlist.base.*;
import dori.jasper.engine.*;
import org.aspcfs.utils.HTTPUtils;
import javax.servlet.http.*;
import javax.servlet.*;
import org.aspcfs.utils.web.*;
import org.aspcfs.modules.login.beans.*;
import org.aspcfs.modules.admin.base.*;

/**
 *  Represents a configurable report parameter as specified in a Jasper Report.
 *  A parameter is part of a report's Criteria.
 *
 *@author     matt rajkowski
 *@created    September 15, 2003
 *@version    $Id$
 */
public class Parameter extends GenericBean {

	private int id = -1;
	private int criteriaId = -1;
	private int type = -1;
	private String name = null;
	private String value = null;
	private String displayValue = null;
	private java.lang.Class valueClass = null;
	private String description = null;
	private boolean required = false;
	private boolean isForPrompting = false;
	private boolean isSystemDefined = false;


	/**
	 *  Constructor for the Parameter object
	 */
	public Parameter() { }


	/**
	 *  Constructor for the Parameter object
	 *
	 *@param  rs                Description of the Parameter
	 *@exception  SQLException  Description of the Exception
	 */
	public Parameter(ResultSet rs) throws SQLException {
		build(rs);
	}


	/**
	 *  Sets the id attribute of the Parameter object
	 *
	 *@param  tmp  The new id value
	 */
	public void setId(int tmp) {
		this.id = tmp;
	}


	/**
	 *  Sets the id attribute of the Parameter object
	 *
	 *@param  tmp  The new id value
	 */
	public void setId(String tmp) {
		this.id = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the criteriaId attribute of the Parameter object
	 *
	 *@param  tmp  The new criteriaId value
	 */
	public void setCriteriaId(int tmp) {
		this.criteriaId = tmp;
	}


	/**
	 *  Sets the criteriaId attribute of the Parameter object
	 *
	 *@param  tmp  The new criteriaId value
	 */
	public void setCriteriaId(String tmp) {
		this.criteriaId = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the type attribute of the Parameter object
	 *
	 *@param  tmp  The new type value
	 */
	public void setType(int tmp) {
		this.type = tmp;
	}


	/**
	 *  Sets the type attribute of the Parameter object
	 *
	 *@param  tmp  The new type value
	 */
	public void setType(String tmp) {
		this.type = Integer.parseInt(tmp);
	}


	/**
	 *  Sets the name attribute of the Parameter object
	 *
	 *@param  tmp  The new name value
	 */
	public void setName(String tmp) {
		this.name = tmp;
	}


	/**
	 *  Sets the value attribute of the Parameter object
	 *
	 *@param  tmp  The new value value
	 */
	public void setValue(String tmp) {
		this.value = tmp;
	}


	/**
	 *  Sets the displayValue attribute of the Parameter object
	 *
	 *@param  tmp  The new displayValue value
	 */
	public void setDisplayValue(String tmp) {
		this.displayValue = tmp;
	}


	/**
	 *  Sets the valueClass attribute of the Parameter object
	 *
	 *@param  tmp  The new valueClass value
	 */
	public void setValueClass(java.lang.Class tmp) {
		this.valueClass = tmp;
	}


	/**
	 *  Sets the description attribute of the Parameter object
	 *
	 *@param  tmp  The new description value
	 */
	public void setDescription(String tmp) {
		this.description = tmp;
	}


	/**
	 *  Sets the required attribute of the Parameter object
	 *
	 *@param  tmp  The new required value
	 */
	public void setRequired(boolean tmp) {
		this.required = tmp;
	}


	/**
	 *  Sets the required attribute of the Parameter object
	 *
	 *@param  tmp  The new required value
	 */
	public void setRequired(String tmp) {
		this.required = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 *  Sets the isForPrompting attribute of the Parameter object
	 *
	 *@param  tmp  The new isForPrompting value
	 */
	public void setIsForPrompting(boolean tmp) {
		this.isForPrompting = tmp;
	}


	/**
	 *  Sets the isForPrompting attribute of the Parameter object
	 *
	 *@param  tmp  The new isForPrompting value
	 */
	public void setIsForPrompting(String tmp) {
		this.isForPrompting = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 *  Sets the isSystemDefined attribute of the Parameter object
	 *
	 *@param  tmp  The new isSystemDefined value
	 */
	public void setIsSystemDefined(boolean tmp) {
		this.isSystemDefined = tmp;
	}


	/**
	 *  Sets the isSystemDefined attribute of the Parameter object
	 *
	 *@param  tmp  The new isSystemDefined value
	 */
	public void setIsSystemDefined(String tmp) {
		this.isSystemDefined = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 *  Gets the id attribute of the Parameter object
	 *
	 *@return    The id value
	 */
	public int getId() {
		return id;
	}


	/**
	 *  Gets the criteriaId attribute of the Parameter object
	 *
	 *@return    The criteriaId value
	 */
	public int getCriteriaId() {
		return criteriaId;
	}


	/**
	 *  Gets the type attribute of the Parameter object
	 *
	 *@return    The type value
	 */
	public int getType() {
		return type;
	}


	/**
	 *  Gets the name attribute of the Parameter object
	 *
	 *@return    The name value
	 */
	public String getName() {
		return name;
	}


	/**
	 *  Gets the value attribute of the Parameter object
	 *
	 *@return    The value value
	 */
	public String getValue() {
		return value;
	}


	/**
	 *  Gets the displayValue attribute of the Parameter object
	 *
	 *@return    The displayValue value
	 */
	public String getDisplayValue() {
		return displayValue;
	}


	/**
	 *  Gets the valueClass attribute of the Parameter object
	 *
	 *@return    The valueClass value
	 */
	public java.lang.Class getValueClass() {
		return valueClass;
	}


	/**
	 *  Gets the description attribute of the Parameter object
	 *
	 *@return    The description value
	 */
	public String getDescription() {
		return description;
	}


	/**
	 *  Gets the displayName attribute of the Parameter object
	 *
	 *@return    The displayName value
	 */
	public String getDisplayName() {
		if (description != null && !"".equals(description)) {
			return description;
		} else {
			return name;
		}
	}


	/**
	 *  Gets the required attribute of the Parameter object
	 *
	 *@return    The required value
	 */
	public boolean getRequired() {
		return required;
	}


	/**
	 *  Gets the isForPrompting attribute of the Parameter object
	 *
	 *@return    The isForPrompting value
	 */
	public boolean getIsForPrompting() {
		return isForPrompting;
	}


	/**
	 *  Gets the isSystemDefined attribute of the Parameter object
	 *
	 *@return    The isSystemDefined value
	 */
	public boolean getIsSystemDefined() {
		return isSystemDefined;
	}


	/**
	 *  Sets the param attribute of the Parameter object
	 *
	 *@param  param  The new param value
	 */
	public void setParam(JRParameter param) {
		name = param.getName();
		value = null;
		description = param.getDescription();
		required = param.isForPrompting();
		valueClass = param.getValueClass();
		isForPrompting = param.isForPrompting();
		isSystemDefined = param.isSystemDefined();
	}


	/**
	 *  Populates this parameter from a database record
	 *
	 *@param  rs                Description of the Parameter
	 *@exception  SQLException  Description of the Exception
	 */
	public void build(ResultSet rs) throws SQLException {
		id = rs.getInt("parameter_id");
		criteriaId = rs.getInt("criteria_id");
		name = rs.getString("parameter");
		value = rs.getString("value");
	}


	/**
	 *  Gets the valid attribute of the Parameter object
	 *
	 *@return    The valid value
	 */
	public boolean isValid() {
		if (criteriaId == -1) {
			errors.put("criteriaIdError", "Criteria Id is required");
		}
		return !hasErrors();
	}


	/**
	 *  Insert this parameter into the report_criteria_parameter table so the
	 *  report can execute based on this parameter data
	 *
	 *@param  db                Description of the Parameter
	 *@return                   Description of the Return Value
	 *@exception  SQLException  Description of the Exception
	 */
	public boolean insert(Connection db) throws SQLException {
		if (!isValid()) {
			return false;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(
				"INSERT INTO report_criteria_parameter " +
				"(criteria_id, parameter, value) ");
		sql.append("VALUES (?, ?, ?) ");
		int i = 0;
		PreparedStatement pst = db.prepareStatement(sql.toString());
		pst.setInt(++i, criteriaId);
		pst.setString(++i, name);
		pst.setString(++i, value);
		pst.execute();
		pst.close();
		id = DatabaseUtils.getCurrVal(db, "report_criteria_parameter_parameter_id_seq");
		return true;
	}


	/**
	 *  If this parameter requires additional data from the user, then the form
	 *  element is generated here, but only if the isPromptable is set.
	 *
	 *@param  request           Description of the Parameter
	 *@param  db                Description of the Parameter
	 *@exception  SQLException  Description of the Exception
	 */
	public void prepareContext(HttpServletRequest request, Connection db) throws SQLException {
		if (name.startsWith("lookup_state")) {
			//Nothing, this object is static
		} else if (name.startsWith("lookup_ticket_severity") && !name.endsWith("_where")) {
			//Special case because of table name
			LookupList select = new LookupList(db, "ticket_severity");
			select.addItem(-1, "Any");
			request.setAttribute(name, select);
		} else if (name.startsWith("lookup_ticket_priority") && !name.endsWith("_where")) {
			//Special case because of table name
			LookupList select = new LookupList(db, "ticket_priority");
			select.addItem(-1, "Any");
			request.setAttribute(name, select);
		} else if (name.startsWith("lookup_call_result") && !name.endsWith("_where")) {
			//TODO: Add call result object here

		} else if (name.startsWith("lookup_") && !name.endsWith("_where")) {
			//Perform this one last, just in case other names start with lookup_
			LookupList select = new LookupList(db, name);
			select.addItem(-1, "Any");
			request.setAttribute(name, select);
		} else if (name.startsWith("boolean_") && !name.endsWith("_where")) {
			HtmlSelect select = new HtmlSelect();
			select.addItem("-1", "Any");
			select.addItem("1", "Yes");
			select.addItem("0", "No");
			request.setAttribute(name, select);
		} else if (name.startsWith("orgid")) {
			try {
				int id = Integer.parseInt(value);
				Organization org = new Organization(db, id);
				displayValue = org.getName();
			} catch (Exception e) {
				displayValue = "All";
				value = "-1";
			}
		}
	}


	/**
	 *  If this parameter requires additional data from the user, then the custom
	 *  HTML input field is returned
	 *
	 *@param  request  Description of the Parameter
	 *@return          The html value
	 */
	public String getHtml(HttpServletRequest request) {
		if (name.equals("userid_range_source")) {
			//User Id combo box
			return HtmlSelectRecordSource.getSelect(name, value).getHtml();
		} else if (name.startsWith("date_")) {
			//Date Field
      User user = ((UserBean) request.getSession().getAttribute("User")).getUserRecord();
      String language = user.getLocale().getLanguage();
      String country = user.getLocale().getCountry();

			return "<input type='text' size='10' name='" + name + "' " +
					"value='" + HTTPUtils.toHtmlValue(value) + "'/>\r\n" +
          "<a href=\"javascript:popCalendar('paramForm', '" + name + "','" + language + "','" + country +"');\">" +
					"<img src=\"images/icons/stock_form-date-field-16.gif\" " +
          "border=\"0\" align=\"absmiddle\" height=\"16\" width=\"16\"/></a>";
		} else if (name.startsWith("lookup_state")) {
			//State/Province drop-down
			return (new StateSelect()).getHtml(name);
		} else if (name.startsWith("lookup_") && !name.endsWith("_where")) {
			//Lookup Lists
			LookupList select = (LookupList) request.getAttribute(name);
			if (value != null) {
				return select.getHtmlSelect(name, Integer.parseInt(value));
			} else {
				return select.getHtmlSelect(name, -1);
			}
		} else if (name.startsWith("boolean_") && !name.endsWith("_where")) {
			//Html Selects
			HtmlSelect select = (HtmlSelect) request.getAttribute(name);
			if (value != null) {
				return select.getHtml(name, Integer.parseInt(value));
			} else {
				return select.getHtml(name, -1);
			}
		} else if (name.startsWith("percent_") && !name.endsWith("_max") && !name.endsWith("_min")) {
			//Percent drop-down
			return HtmlSelectProbabilityRange.getSelect(name, value).getHtml();
		} else if (name.startsWith("text_")) {
			//Text Field
			int defaultSize = 30;
			int maxLength = -1;
			if (name.indexOf(":") > -1) {
				maxLength = Integer.parseInt(name.substring(name.indexOf(":") + 1));
				if (maxLength > 40) {
					defaultSize = 40;
				} else {
					defaultSize = maxLength;
				}
			}
			return "<input type=\"text\" size=\"" + defaultSize + "\" " +
					(maxLength == -1 ? "" : "maxlength=\"" + maxLength + "\" ") +
					"name=\"" + name + "\" " +
					"value=\"" + HTTPUtils.toHtmlValue(value) + "\"/>";
		} else if (name.startsWith("number_")) {
			//Number Field
			int defaultSize = 30;
			int maxLength = -1;
			if (name.indexOf(":") > -1) {
				maxLength = Integer.parseInt(name.substring(name.indexOf(":") + 1));
				if (maxLength > 40) {
					defaultSize = 40;
				} else {
					defaultSize = maxLength;
				}
			}
			return "<input type=\"text\" size=\"" + defaultSize + "\" " +
					(maxLength == -1 ? "" : "maxlength=\"" + maxLength + "\" ") +
					"name=\"" + name + "\" " +
					"value=\"" + HTTPUtils.toHtmlValue(value) + "\"/>";
		} else if (name.startsWith("orgid")) {
			return "<div id=\"change" + name + "\" style=\"display:inline\">" + displayValue + "</div>" +
					"<input type=\"hidden\" name=\"" + name + "\" id=\"" + name + "\" value=\"" + value + "\">" +
					"&nbsp; [<a href=\"javascript:popAccountsListSingle('" + name + "','change" + name + "', 'showMyCompany=true&filters=all|my|disabled');\">Select</a>]" +
					"&nbsp; [<a href=\"javascript:changeDivContent('change" + name + "','All');javascript:resetNumericFieldValue('" + name + "');\">Clear</a>]";
		}
		return "Parameter type not supported";
	}
}

