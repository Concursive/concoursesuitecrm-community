package com.zeroio.webdav.context;

import com.zeroio.webdav.utils.VCard;
import org.apache.naming.resources.Resource;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.admin.base.AccessType;
import org.aspcfs.modules.admin.base.AccessTypeList;
import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.admin.base.UserList;
import org.aspcfs.modules.contacts.base.Contact;
import org.aspcfs.modules.contacts.base.ContactList;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Description of the Class
 *
 * @author Ananth
 * @created April 20, 2005
 */
public class ContactContext extends BaseWebdavContext
    implements ModuleContext {

  private User user = null;


  /**
   * Constructor for the ContactContext object
   *
   * @param name Description of the Parameter
   */
  public ContactContext(String name) {
    this.contextName = name;
  }


  /**
   * Description of the Method
   *
   * @param thisSystem      Description of the Parameter
   * @param db              Description of the Parameter
   * @param userId          Description of the Parameter
   * @param fileLibraryPath Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void buildResources(SystemStatus thisSystem, Connection db, int userId, String fileLibraryPath) throws SQLException {
    //fileLibraryPath is not needed by the ContactContext
    bindings.clear();
    user = new User();
    user.setBuildContact(true);
    user.setBuildHierarchy(true);
    user.buildRecord(db, userId);
    populateBindings(db, thisSystem);
  }


  /**
   * Description of the Method
   *
   * @param db         Description of the Parameter
   * @param thisSystem Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  private void populateBindings(Connection db, SystemStatus thisSystem) throws SQLException {
    UserList shortChildList = user.getShortChildList();
    UserList fullChildList = user.getFullChildList(
        shortChildList, new UserList());
    String userRange = fullChildList.getUserListIds(user.getId());

    ContactList contactList = new ContactList();
    //make sure user has access to view account contacts
    if (!(hasPermission(
        thisSystem, user.getId(), "accounts-accounts-contacts-view"))) {
      contactList.setExcludeAccountContacts(true);
    }

    contactList.setAllContacts(true, user.getId(), userRange);
    contactList.addIgnoreTypeId(Contact.LEAD_TYPE);
    if (user.getSiteId() != -1) {
      contactList.setSiteId(user.getSiteId());
    }
    contactList.buildList(db);
    //Add this user's contact record
    contactList.add(user.getContact());
    Iterator i = contactList.iterator();
    while (i.hasNext()) {
      Contact thisContact = (Contact) i.next();
      VCard card = new VCard(thisContact);
      //System.out.println("BUILT VCARD FOR CONTACT: " + card.getFormattedName());
      
      Resource resource = new Resource(card.getBytes());
      resource.setContactId(thisContact.getId());

      String name = card.getFormattedName() + ".vcf";
      bindings.put(name, resource);
      buildProperties(
          name, thisContact.getEntered(), thisContact.getModified(), new Integer(
              card.getBytes().length));
    }
  }


  /**
   * Description of the Method
   *
   * @param thisSystem Description of the Parameter
   * @param db         Description of the Parameter
   * @param object     Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException    Description of the Exception
   * @throws IOException     Description of the Exception
   * @throws NamingException Description of the Exception
   */
  public Object copyResource(SystemStatus thisSystem, Connection db, Object object) throws SQLException,
      IOException, NamingException {
    if (!hasPermission(
        thisSystem, user.getId(), "contacts-external_contacts-add")) {
      return null;
    }

    AccessTypeList accessTypeList = thisSystem.getAccessTypeList(
        db, AccessType.GENERAL_CONTACTS);

    if (object instanceof Resource) {
      Resource resource = (Resource) object;
      VCard card = new VCard();
      if (resource.getContactId() != -1) {
        //Contact already exists
        Contact thisContact = new Contact(db, resource.getContactId());
        card.setContact(thisContact);
        card.parseResource(db, resource);
        if (card.isValid()) {
          card.getContact().setModifiedBy(user.getId());
          card.update(db);
        }
      } else {
        card.parseResource(db, resource);
        if (card.isValid()) {
          card.getContact().setAccessType(accessTypeList.getDefaultItem());
          card.getContact().setOwner(user.getId());
          card.getContact().setEnteredBy(user.getId());
          card.getContact().setModifiedBy(user.getId());
          card.save(db);
        }
      }
      Contact contact = new Contact(db, card.getContact().getId());
      resource.setContactId(card.getContact().getId());

      bindings.put(card.getFormattedName() + ".vcf", resource);
      buildProperties(
          card.getFormattedName() + ".vcf", contact.getEntered(),
          contact.getModified(), new Integer(card.getBytes().length));
      return contact;
    }
    return null;
  }
}

