package org.aspcfs.modules.communications.base;

import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import java.util.ArrayList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.communications.base.ActiveSurveyQuestionItem;

/**
 *  A Survey Answer Item.
 *
 *@author     Mathur
 *@created    February 4, 2003
 *@version    $Id: ActiveSurveyAnswerItem.java,v 1.1 2003/02/17 14:38:17 akhi_m
 *      Exp $
 */
public class ActiveSurveyAnswerItem {
  private int id = -1;
  private int itemId = -1;
  private int contactId = -1;
  private Contact recipient = null;
  private ActiveSurveyQuestionItem item = null;
  private java.sql.Timestamp entered = null;
  


  /**
   *  Constructor for the ActiveSurveyAnswerItem object
   */
  public ActiveSurveyAnswerItem() { }


  /**
   *  Constructor for the ActiveSurveyAnswerItem object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public ActiveSurveyAnswerItem(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the entered attribute of the ActiveSurveyAnswerItem object
   *
   *@param  entered  The new entered value
   */
  public void setEntered(java.sql.Timestamp entered) {
    this.entered = entered;
  }


  /**
   *  Gets the entered attribute of the ActiveSurveyAnswerItem object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the id attribute of the ActiveSurveyAnswerItem object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Sets the contactId attribute of the ActiveSurveyAnswerItem object
   *
   *@param  contactId  The new contactId value
   */
  public void setContactId(int contactId) {
    this.contactId = contactId;
  }


  /**
   *  Sets the contactId attribute of the ActiveSurveyAnswerItem object
   *
   *@param  contactId  The new contactId value
   */
  public void setContactId(String contactId) {
    this.contactId = Integer.parseInt(contactId);
  }


  /**
   *  Sets the recipient attribute of the ActiveSurveyAnswerItem object
   *
   *@param  recipient  The new recipient value
   */
  public void setRecipient(Contact recipient) {
    this.recipient = recipient;
  }


  /**
   *  Sets the item attribute of the ActiveSurveyAnswerItem object
   *
   *@param  item  The new item value
   */
  public void setItem(ActiveSurveyQuestionItem item) {
    this.item = item;
  }


  /**
   *  Sets the itemId attribute of the ActiveSurveyAnswerItem object
   *
   *@param  itemId  The new itemId value
   */
  public void setItemId(int itemId) {
    this.itemId = itemId;
  }


  /**
   *  Sets the itemId attribute of the ActiveSurveyAnswerItem object
   *
   *@param  itemId  The new itemId value
   */
  public void setItemId(String itemId) {
    this.itemId = Integer.parseInt(itemId);
  }


  /**
   *  Gets the itemId attribute of the ActiveSurveyAnswerItem object
   *
   *@return    The itemId value
   */
  public int getItemId() {
    return itemId;
  }


  /**
   *  Gets the contactId attribute of the ActiveSurveyAnswerItem object
   *
   *@return    The contactId value
   */
  public int getContactId() {
    return contactId;
  }


  /**
   *  Gets the recipient attribute of the ActiveSurveyAnswerItem object
   *
   *@return    The recipient value
   */
  public Contact getRecipient() {
    return recipient;
  }


  /**
   *  Gets the item attribute of the ActiveSurveyAnswerItem object
   *
   *@return    The item value
   */
  public ActiveSurveyQuestionItem getItem() {
    return item;
  }


  /**
   *  Sets the id attribute of the ActiveSurveyAnswerItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the ActiveSurveyAnswerItem object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildItemDetails(Connection db) throws SQLException {
    if (itemId == -1) {
      throw new SQLException("Item ID not specified");
    }
    item = new ActiveSurveyQuestionItem(db, itemId);
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecipientInfo(Connection db) throws SQLException {
    if (contactId == -1) {
      throw new SQLException("Contact ID not specified");
    }
    recipient = new Contact(db, contactId);
  }


  /**
   *  Build Item record
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("id");
    itemId = rs.getInt("item_id");
    contactId = rs.getInt("contact_id");
    entered = rs.getTimestamp("entered");
  }
}

