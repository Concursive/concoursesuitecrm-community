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

import org.aspcfs.modules.base.*;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.util.Iterator;

/**
 * Represents importer for Contacts
 *
 * @author
 * @version $Id$
 * @created
 */
public class ContactInformationFormatter {

  /**
   * Constructor for the ContactInformationFormatter object
   */
  public ContactInformationFormatter() { }


  /**
   * Gets the contactInformation attribute of the ContactInformationFormatter
   * class
   *
   * @param contact      Description of the Parameter
   * @param propertyFile Description of the Parameter
   * @return The contactInformation value
   * @throws Exception Description of the Exception
   */
  public static String getContactInformation(Contact contact, String propertyFile) throws Exception {
    File configFile = new File(propertyFile);
    XMLUtils xml = new XMLUtils(configFile);
    Element mappings = xml.getFirstChild("mappings");

    ContactEmailAddressList tmpContactEmailAddressList = contact.getEmailAddressList();
    ContactInstantMessageAddressList tmpContactInstantMessageAddressList = contact.getInstantMessageAddressList();
    ContactTextMessageAddressList tmpContactTextMessageAddressList = contact.getTextMessageAddressList();
    ContactPhoneNumberList tmpContactPhoneNumberList = contact.getPhoneNumberList();
    ContactAddressList tmpContactAddressList = contact.getAddressList();

    Template template = new Template();
    template.setText(
        XMLUtils.getNodeText(
            XMLUtils.getElement(
                mappings, "map", "id", "contactInformation.details")));

    //Add system info
    addSystemInfo(template, contact);

    //Adding contact name
    template.addParseElement("${name}", contact.getNameFull());

    //Adding contact additional names
    template.addParseElement("${additionalNames}", contact.getAdditionalNames());

    //Adding contact nickname
    template.addParseElement("${nickname}", contact.getNickname());

    //Adding contact date of birth
    template.addParseElement("${birthDate}", StringUtils.toDateString(contact.getBirthDate()));

    //Adding contact title
    template.addParseElement("${title}", contact.getTitle());

    //Adding contact role
    template.addParseElement("${role}", contact.getRole());

    //Adding email addresses
    StringBuffer infoString = new StringBuffer();
    Iterator itr = tmpContactEmailAddressList.iterator();
    String noInformationString = "";
    noInformationString = template.getValue("noEmailAddresses");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        EmailAddress tmpEmailAddress = (EmailAddress) itr.next();
        infoString.append(
            "<b>" + tmpEmailAddress.getTypeName() + ":</b> " + tmpEmailAddress.getEmail() + "<br />");
      }
      template.addParseElement("${emailAddresses}", infoString.toString());
      template.addParseElement(
          "${noEmailAddresses=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${emailAddresses}", "");
      template.addParseElement(
          "${noEmailAddresses=" + noInformationString + "}", noInformationString);
    }

    //Adding instant message addresses
    infoString = new StringBuffer();
    itr = tmpContactInstantMessageAddressList.iterator();
    noInformationString = template.getValue("noInstantMessageAddresses");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        InstantMessageAddress tmpInstantMessageAddress = (InstantMessageAddress) itr.next();
        infoString.append(
            "<b>" + tmpInstantMessageAddress.getAddressIMServiceName() + " (" + tmpInstantMessageAddress.getAddressIMTypeName() + "):</b> " + tmpInstantMessageAddress.getAddressIM() + "<br />");
      }
      template.addParseElement(
          "${instantMessageAddresses}", infoString.toString());
      template.addParseElement(
          "${noInstantMessageAddresses=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${instantMessageAddresses}", "");
      template.addParseElement(
          "${noInstantMessageAddresses=" + noInformationString + "}", noInformationString);
    }

    //Adding text message addresses
    infoString = new StringBuffer();
    itr = tmpContactTextMessageAddressList.iterator();
    noInformationString = template.getValue("noTextMessageAddresses");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        TextMessageAddress tmpTextMessageAddress = (TextMessageAddress) itr.next();
        infoString.append(
            "<b>" + tmpTextMessageAddress.getTypeName() + ":</b> " + tmpTextMessageAddress.getTextMessageAddress() + "<br />");
      }
      template.addParseElement(
          "${textMessageAddresses}", infoString.toString());
      template.addParseElement(
          "${noTextMessageAddresses=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${textMessageAddresses}", "");
      template.addParseElement(
          "${noTextMessageAddresses=" + noInformationString + "}", noInformationString);
    }

    //Adding phone numbers
    infoString = new StringBuffer();
    itr = tmpContactPhoneNumberList.iterator();
    noInformationString = template.getValue("noPhoneNumbers");
    if (itr.hasNext()) {
      while (itr.hasNext()) {
        PhoneNumber tmpPhoneNumber = (PhoneNumber) itr.next();
        infoString.append(
            "<b>" + tmpPhoneNumber.getTypeName() + ":</b> " + tmpPhoneNumber.getPhoneNumber() + "<br />");
      }
      template.addParseElement("${phoneNumbers}", infoString.toString());
      template.addParseElement(
          "${noPhoneNumbers=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${phoneNumbers}", "");
      template.addParseElement(
          "${noPhoneNumbers=" + noInformationString + "}", noInformationString);
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
      template.addParseElement(
          "${noPostalAddresses=" + noInformationString + "}", "");
    } else {
      template.addParseElement("${postalAddresses}", "");
      template.addParseElement(
          "${noPostalAddresses=" + noInformationString + "}", noInformationString);
    }
    return template.getParsedText();
  }


  /**
   * Adds a feature to the SystemInfo attribute of the
   * ContactInformationFormatter class
   *
   * @param template The feature to be added to the SystemInfo attribute
   * @param contact  The feature to be added to the SystemInfo attribute
   * @throws Exception Description of the Exception
   */
  private static void addSystemInfo(Template template, Contact contact) throws Exception {
    //Adding contact secret word based on ignore preference
    if (StringUtils.hasText(contact.getSecretWord())) {
      //Append ${secret_word} at the begining. If "Your Secret Word" needs to be translated, then
      //move it to templates-en_US.xml' with the rest of contact information
      String text = "<b>Your Secret Word:</b> ${secret_word}" + template.getText();
      template.addParseElement("${secret_word}", StringUtils.toString(contact.getSecretWord()) + "<br /><br />");
      template.setText(text);
    }
  }
}

