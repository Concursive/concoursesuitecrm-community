/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.contacts.base;

import org.aspcfs.modules.base.Address;
import org.aspcfs.modules.base.EmailAddress;
import org.aspcfs.modules.base.PhoneNumber;
import org.aspcfs.modules.base.TextMessageAddress;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.util.Iterator;

/**
 *  Represents importer for Contacts
 *
 *@author
 *@created
 *@version    $Id$
 */
public class ContactInformationFormatter {

  /**
   *  Constructor for the ContactInformationFormatter object
   */
  public ContactInformationFormatter() { }


  /**
   *  Gets the contactInformation attribute of the ContactInformationFormatter
   *  class
   *
   *@param  contact        Description of the Parameter
   *@param  propertyFile   Description of the Parameter
   *@return                The contactInformation value
   *@exception  Exception  Description of the Exception
   */
  public static String getContactInformation(Contact contact, String propertyFile) throws Exception {
    File configFile = new File(propertyFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");

    ContactEmailAddressList tmpContactEmailAddressList = contact.getEmailAddressList();
    ContactTextMessageAddressList tmpContactTextMessageAddressList = contact.getTextMessageAddressList();
    ContactPhoneNumberList tmpContactPhoneNumberList = contact.getPhoneNumberList();
    ContactAddressList tmpContactAddressList = contact.getAddressList();

    Template template = new Template();
    template.setText(XMLUtils.getNodeText(XMLUtils.getElement(mappings, "map", "id", "contactInformation.details")));

    //Adding contact name
    template.addParseElement("${name}", contact.getNameFull());

    //Adding email addresses
    StringBuffer infoString = new StringBuffer();
    Iterator itr = tmpContactEmailAddressList.iterator();
    String noInformationString = "";
    noInformationString = template.getValue("noEmailAddresses");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        EmailAddress tmpEmailAddress = (EmailAddress) itr.next();
        infoString.append("<b>" + tmpEmailAddress.getTypeName() + ":</b> " + tmpEmailAddress.getEmail() + "<br />");
      }
      template.addParseElement("${emailAddresses}", infoString.toString());
      template.addParseElement("${noEmailAddresses=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${emailAddresses}", "");
      template.addParseElement("${noEmailAddresses=" + noInformationString + "}", noInformationString);
    }

    //Adding text message addresses
    infoString = new StringBuffer();
    itr = tmpContactTextMessageAddressList.iterator();
    noInformationString = template.getValue("noTextMessageAddresses");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        TextMessageAddress tmpTextMessageAddress = (TextMessageAddress) itr.next();
        infoString.append("<b>" + tmpTextMessageAddress.getTypeName() + ":</b> " + tmpTextMessageAddress.getTextMessageAddress() + "<br />");
      }
      template.addParseElement("${textMessageAddresses}", infoString.toString());
      template.addParseElement("${noTextMessageAddresses=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${textMessageAddresses}", "");
      template.addParseElement("${noTextMessageAddresses=" + noInformationString + "}", noInformationString);
    }

    //Adding phone numbers
    infoString = new StringBuffer();
    itr = tmpContactPhoneNumberList.iterator();
    noInformationString = template.getValue("noPhoneNumbers");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        PhoneNumber tmpPhoneNumber = (PhoneNumber) itr.next();
        infoString.append("<b>" + tmpPhoneNumber.getTypeName() + ":</b> " + tmpPhoneNumber.getPhoneNumber() + "<br />");
      }
      template.addParseElement("${phoneNumbers}", infoString.toString());
      template.addParseElement("${noPhoneNumbers=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${phoneNumbers}", "");
      template.addParseElement("${noPhoneNumbers=" + noInformationString + "}", noInformationString);
    }

    //Adding postal addresses
    infoString = new StringBuffer();
    itr = tmpContactAddressList.iterator();
    noInformationString = template.getValue("noPostalAddresses");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        Address tmpAddress = (Address) itr.next();
        infoString.append(
            "<b>" + tmpAddress.getTypeName() + ":</b><br />" +
            tmpAddress.toString() + "<br /><br />");
      }
      template.addParseElement("${postalAddresses}", infoString.toString());
      template.addParseElement("${noPostalAddresses=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${postalAddresses}", "");
      template.addParseElement("${noPostalAddresses=" + noInformationString + "}", noInformationString);
    }
    return template.getParsedText();
  }
}

