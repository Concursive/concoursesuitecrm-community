//Copyright 2001 Dark Horse Ventures

package com.darkhorseventures.cfsbase;

import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.darkhorseventures.utils.DateUtils;

/**
 *  Represents a phone number.
 *
 *@author     mrajkowski
 *@created    September 4, 2001
 *@version    $Id$
 */
public class PhoneNumber {

  protected boolean isContact = false;
  private int id = -1;
  private int orgId = -1;
  private int contactId = -1;
  private int type = -1;
  private String typeName = "Main";
  private String number = "";
  private String extension = "";
  private int enteredBy = -1;
  private int modifiedBy = -1;
  private boolean enabled = true;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;


  /**
   *  Sets the Id attribute of the PhoneNumber object
   *
   *@param  tmp  The new Id value
   *@since       1.1
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the Id attribute of the PhoneNumber object
   *
   *@param  tmp  The new Id value
   *@since       1.8
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   *  Sets the OrgId attribute of the PhoneNumber object
   *
   *@param  tmp  The new OrgId value
   *@since       1.1
   */
  public void setOrgId(int tmp) {
    this.orgId = tmp;
  }

  public void setOrgId(String tmp) {
    this.orgId = Integer.parseInt(tmp);
  }

  /**
   *  Sets the ContactId attribute of the PhoneNumber object
   *
   *@param  tmp  The new ContactId value
   *@since
   */
  public void setContactId(int tmp) {
    this.contactId = tmp;
  }

  public void setContactId(String tmp) {
    this.contactId = Integer.parseInt(tmp);
  }

  /**
   *  Sets the Type attribute of the PhoneNumber object
   *
   *@param  tmp  The new Type value
   *@since       1.1
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the Type attribute of the PhoneNumber object
   *
   *@param  tmp  The new Type value
   *@since
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Sets the TypeName attribute of the PhoneNumber object
   *
   *@param  tmp  The new TypeName value
   *@since       1.1
   */
  public void setTypeName(String tmp) {
    this.typeName = tmp;
  }


  /**
   *  Sets the Number attribute of the PhoneNumber object
   *
   *@param  tmp  The new Number value
   *@since       1.1
   */
  public void setNumber(String tmp) {
    this.number = tmp;
  }


  /**
   *  Sets the Extension attribute of the PhoneNumber object
   *
   *@param  tmp  The new Extension value
   *@since       1.1
   */
  public void setExtension(String tmp) {
    this.extension = tmp;
  }


  /**
   *  Sets the EnteredBy attribute of the PhoneNumber object
   *
   *@param  tmp  The new EnteredBy value
   *@since       1.1
   */
  public void setEnteredBy(int tmp) {
    this.enteredBy = tmp;
  }
  
  public void setEnteredBy(String tmp) {
    this.enteredBy = Integer.parseInt(tmp);
  }


  /**
   *  Sets the ModifiedBy attribute of the PhoneNumber object
   *
   *@param  tmp  The new ModifiedBy value
   *@since       1.1
   */
  public void setModifiedBy(int tmp) {
    this.modifiedBy = tmp;
  }
  
  public void setModifiedBy(String tmp) {
    this.modifiedBy = Integer.parseInt(tmp);
  }

public int getContactId() {
	return contactId;
}

  /**
   *  Sets the Enabled attribute of the PhoneNumber object
   *
   *@param  tmp  The new Enabled value
   *@since       1.8
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }
  
  public void setEnabled(String tmp) {
    enabled = ("on".equalsIgnoreCase(tmp) || "true".equalsIgnoreCase(tmp));
  }


  /**
   *  Gets the Valid attribute of the PhoneNumber object
   *
   *@return    The Valid value
   *@since     1.7
   */
  public boolean isValid() {
    return (type > -1 && number != null && !number.trim().equals(""));
  }


  /**
   *  Gets the Id attribute of the PhoneNumber object
   *
   *@return    The Id value
   *@since     1.1
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the OrgId attribute of the PhoneNumber object
   *
   *@return    The OrgId value
   *@since     1.1
   */
  public int getOrgId() {
    return orgId;
  }


  /**
   *  Gets the Type attribute of the PhoneNumber object
   *
   *@return    The Type value
   *@since     1.1
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the TypeName attribute of the PhoneNumber object
   *
   *@return    The TypeName value
   *@since     1.1
   */
  public String getTypeName() {
    return typeName;
  }


  /**
   *  Gets the Number attribute of the PhoneNumber object
   *
   *@return    The Number value
   *@since     1.1
   */
  public String getNumber() {
    return number;
  }


  /**
   *  Gets the Extension attribute of the PhoneNumber object
   *
   *@return    The Extension value
   *@since     1.1
   */
  public String getExtension() {
    return extension;
  }


  /**
   *  Gets the EnteredBy attribute of the PhoneNumber object
   *
   *@return    The EnteredBy value
   *@since     1.1
   */
  public int getEnteredBy() {
    return enteredBy;
  }


  /**
   *  Gets the ModifiedBy attribute of the PhoneNumber object
   *
   *@return    The ModifiedBy value
   *@since     1.1
   */
  public int getModifiedBy() {
    return modifiedBy;
  }

  public java.sql.Timestamp getEntered() { return entered; }
public java.sql.Timestamp getModified() { return modified; }
public void setEntered(java.sql.Timestamp tmp) { this.entered = tmp; }
public void setModified(java.sql.Timestamp tmp) { this.modified = tmp; }

  public void setEntered(String tmp) {
    this.entered = DateUtils.parseTimestampString(tmp);
  }
  
  public void setModified(String tmp) {
    this.modified = DateUtils.parseTimestampString(tmp);
  }

  /**
   *  Returns the phone number and an extension if it has one
   *
   *@return    The PhoneNumber value
   *@since     1.1
   */
  public String getPhoneNumber() {
    if (extension != null && !extension.equals("")) {
      return (getNumber() + " x" + getExtension());
    } else {
      return (getNumber());
    }
  }


  /**
   *  Gets the AreaCode attribute of the PhoneNumber object
   *
   *@return    The AreaCode value
   *@since 1.9
   */
  public String getAreaCode() {
    if (getNumber().length() == 12) {
      return getNumber().substring(0, 3);
    } else {
      return "";
    }
  }


  /**
   *  Gets the Prefix attribute of the PhoneNumber object
   *
   *@return    The Prefix value
   *@since 1.9
   */
  public String getPrefix() {
    if (getNumber().length() == 12) {
      return getNumber().substring(4, 7);
    } else if (getNumber().length() == 8) {
      return getNumber().substring(0, 3);
    } else {
      return "";
    }
  }


  /**
   *  Gets the Postfix (number) attribute of the PhoneNumber object
   *
   *@return    The Postfix value
   *@since 1.9
   */
  public String getPostfix() {
    if (getNumber().length() == 12) {
      return getNumber().substring(8, 12);
    } else if (getNumber().length() == 8) {
      return getNumber().substring(4, 8);
    } else if (getNumber().length() > 0) {
      return getNumber();
    } else {
      return "";
    }
  }


  /**
   *  Gets the Enabled attribute of the PhoneNumber object
   *
   *@return    The Enabled value
   *@since     1.8
   */
  public boolean getEnabled() {
    return enabled;
  }



  /**
   *  Populates the object fields from a ResultSet
   *
   *@param  rs                Description of Parameter
   *@exception  SQLException  Description of Exception
   *@since                    1.4
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("phone_id"));

    if (!isContact) {
      this.setOrgId(rs.getInt("org_id"));
      if (rs.wasNull()) {
              this.setOrgId(-1);
      }
    } else {
      this.setContactId(rs.getInt("contact_id"));
      if (rs.wasNull()) {
              this.setContactId(-1);
      }
    }

    this.setType(rs.getInt("phone_type"));
    if (rs.wasNull()) {
            this.setType(-1);
    }
    this.setTypeName(rs.getString("description"));
    this.setNumber(rs.getString("number"));
    this.setExtension(rs.getString("extension"));
    this.setEntered(rs.getTimestamp("entered"));
    this.setEnteredBy(rs.getInt("enteredby"));
    if (enteredBy == -1) {
            this.setEnteredBy(0);
    }
    this.setModified(rs.getTimestamp("modified"));
    this.setModifiedBy(rs.getInt("modifiedby"));
    if (modifiedBy == -1) {
            this.setModifiedBy(0);
    }
  }


  /**
   *  From an HttpRequest, the object can search to see if valid data is present
   *  to initialize the object.<p>
   *
   *  Each phone number should be defined as follows in the Http request,
   *  replace the number 1 with the item number corresponding with the Html
   *  entry:<p>
   *
   *  - phone1id (Corresponds with the db field id for updates/deletes) a number
   *  <br>
   *  - phone1type (Corresponds with the db table id LookupPhoneType) a number
   *  <br>
   *  - phone1ac (Area Code) 757<br>
   *  - phone1pre (Prefix) 555<br>
   *  - phone1number (Rest of number) 1212<br>
   *  - phone1ext (If the number has an extension) a string<br>
   *  - phone1delete (exists if record should be deleted)
   *
   *@param  request    An HttpServletRequest object
   *@param  parseItem  The phone number item to parse
   *@since             1.8
   */
  protected void buildRecord(HttpServletRequest request, int parseItem) {
    this.setType(request.getParameter("phone" + parseItem + "type"));

    StringBuffer thisString = new StringBuffer();
    
/**
    if (request.getParameter("phone" + parseItem + "ac") != null &&
        !request.getParameter("phone" + parseItem + "ac").trim().equals("")) {
      thisString.append(request.getParameter("phone" + parseItem + "ac"));
    }

    if (request.getParameter("phone" + parseItem + "pre") != null &&
        !request.getParameter("phone" + parseItem + "pre").trim().equals("")) {
      if (thisString.length() > 0) {
        thisString.append("-");
      }
      thisString.append(request.getParameter("phone" + parseItem + "pre"));
    }
*/

    if (request.getParameter("phone" + parseItem + "number") != null &&
        !request.getParameter("phone" + parseItem + "number").trim().equals("")) {
      thisString.append(convertToFormattedNumber(request.getParameter("phone" + parseItem + "number")));
    }

    this.setNumber(thisString.toString());
    this.setExtension(request.getParameter("phone" + parseItem + "ext"));

    if (request.getParameter("phone" + parseItem + "delete") != null) {
      String action = request.getParameter("phone" + parseItem + "delete").toLowerCase();
      if (action.equals("on")) {
        this.setEnabled(false);
      }
    }

    if (request.getParameter("phone" + parseItem + "id") != null) {
      this.setId(request.getParameter("phone" + parseItem + "id"));
    }
  }
	
	public static final String convertToNumber(String tmp) {
		tmp = tmp.trim();
		StringBuffer sb = new StringBuffer();
		String allowed = "0123456789";
		for (int i=0; i<tmp.length(); i++) {
			String theChar = tmp.substring(i, i+1);
			if (allowed.indexOf(theChar) > -1) {
				sb.append(theChar);
			}
			if (i==0 && theChar.equals("+")) {
				sb.append(theChar);
			}
		}
		return sb.toString();
	}
	
	public static final String convertToFormattedNumber(String tmp) {
		String tmpNum = "";
		
		if (tmp.indexOf("+") == 0) {
			tmpNum = tmp;
		} else {
			tmpNum = convertToNumber(tmp);
		}
			
		StringBuffer result = new StringBuffer();
		
		//it's a US number
		if (tmpNum.indexOf("+") == -1) {
			//1-XXX numbers, strip off the beginning "1"
			if (tmpNum.length() == 11) {
				tmpNum = tmpNum.substring(1);
			}
			
			result.append("(");
			result.append(tmpNum.substring(0,3));
			result.append(") ");
			result.append(tmpNum.substring(3,6));
			result.append("-");
			result.append(tmpNum.substring(6,10));
		} else {
			result.append(tmpNum);
		}
		
		return result.toString();
	}
}

