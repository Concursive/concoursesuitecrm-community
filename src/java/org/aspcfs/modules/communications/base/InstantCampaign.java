package org.aspcfs.modules.communications.base;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;

/**
 *  This class bypasses the typical campaign process.  Now a campaign
 *  can be quickly created with a message and recipients without having
 *  to build criteria.
 *
 * @author     akhi_m
 * @created    May 13, 2003
 */
public class InstantCampaign extends Campaign {
  private Message instantMessage = null;
  private ContactList recipients = null;


  /**
   *  Sets the instantMessage attribute of the InstantCampaign object
   *
   * @param  instantMessage  The new instantMessage value
   */
  public void setMessage(Message instantMessage) {
    this.instantMessage = instantMessage;
  }


  /**
   *  Sets the recipients attribute of the InstantCampaign object
   *
   * @param  recipients  The new recipients value
   */
  public void setRecipients(ContactList recipients) {
    this.recipients = recipients;
  }


  /**
   *  Gets the recipients attribute of the InstantCampaign object
   *
   * @return    The recipients value
   */
  public ContactList getRecipients() {
    return recipients;
  }


  /**
   *  Gets the instantMessage attribute of the InstantCampaign object
   *
   * @return    The instantMessage value
   */
  public Message getInstantMessage() {
    return instantMessage;
  }


  /**
   *  Adds a feature to the Recipient attribute of the InstantCampaign object
   *
   * @param  db                The feature to be added to the Recipient attribute
   * @param  contactId         The feature to be added to the Recipient attribute
   * @exception  SQLException  Description of the Exception
   */
  public void addRecipient(Connection db, int contactId) throws SQLException {
    if (recipients == null) {
      recipients = new ContactList();
    }
    recipients.add(new Contact(db, contactId));
  }


  /**
   *  Description of the Method
   *
   * @param  db                Description of the Parameter
   * @return                   Description of the Return Value
   * @exception  SQLException  Description of the Exception
   */
  public boolean activate(Connection db) throws SQLException {
    int resultCount = 0;
    SQLException errorMessage = null;
    PreparedStatement pst = null;

    try {
      db.setAutoCommit(false);
      //Replace tags
      Template template = new Template();
      template.setText(instantMessage.getMessageText());

      //insert the campaign
      java.util.Date dtNow = new java.util.Date(); 
      java.sql.Date today = new java.sql.Date(dtNow.getTime()); 
      this.setMessageId(instantMessage.getId());
      this.setReplyTo(instantMessage.getReplyTo());
      this.setSubject(instantMessage.getMessageSubject());
      this.setMessage(template.getParsedText());
      this.setSendMethodId(1);
      this.setActiveDate(today);
      this.setType(Campaign.INSTANT);
      this.insert(db);

      //activate it
      //See if the campaign is not already active
      pst = db.prepareStatement(
          "UPDATE campaign " +
          "SET status_id = ?, " +
          "status = ?, " +
          "modifiedby = ?, " +
          "modified = CURRENT_TIMESTAMP " +
          "WHERE campaign_id = ? ");
      int i = 0;
      pst.setInt(++i, QUEUE);
      pst.setString(++i, QUEUE_TEXT);
      pst.setInt(++i, this.getModifiedBy());
      pst.setInt(++i, this.getId());
      resultCount = pst.executeUpdate();
      pst.close();

      //Activate the campaign...
      if (resultCount == 1) {
        this.setActive(true);
        //Lock in the recipient
        Iterator j = recipients.iterator();
        while (j.hasNext()) {
          Contact thisContact = (Contact) j.next();
          Recipient thisRecipient = new Recipient();
          thisRecipient.setCampaignId(this.getId());
          thisRecipient.setContactId(thisContact.getId());
          thisRecipient.insert(db);
        }

        //Finalize the campaign activation
        pst = db.prepareStatement(
            "UPDATE campaign " +
            "SET active = ?, " +
            "reply_addr = ?, " +
            "subject = ?, " +
            "message = ?, " +
            "modifiedby = ?, " +
            "modified = CURRENT_TIMESTAMP " +
            "WHERE campaign_id = ? ");
        i = 0;
        pst.setBoolean(++i, true);
        pst.setString(++i, instantMessage.getReplyTo());
        pst.setString(++i, instantMessage.getMessageSubject());
        pst.setString(++i, template.getParsedText());
        pst.setInt(++i, this.getModifiedBy());
        pst.setInt(++i, this.getId());
        resultCount = pst.executeUpdate();
        pst.close();
        db.commit();
      }
      return true;
    } catch (SQLException e) {
      errorMessage = e;
      db.rollback();
    } catch (Exception ee) {
      db.rollback();
      ee.printStackTrace(System.out);
    } finally {
      db.setAutoCommit(true);
    }
    return false;
  }
}

