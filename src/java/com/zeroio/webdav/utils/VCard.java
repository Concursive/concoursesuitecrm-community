/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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
package com.zeroio.webdav.utils;

import org.apache.naming.resources.Resource;
import org.aspcfs.modules.contacts.base.*;
import org.aspcfs.utils.DatabaseUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Represents a Virtual Business Card (vCard) for a particular Concourse Suite Community Edition
 * contact.
 *
 * @author Ananth
 * @created April 18, 2005
 */
public class VCard {
  public final static String version = "3.0";
  //vcard specification as per RFC2426
  private String formattedName = "";
  private String structuredName = "";
  private Contact contact = new Contact();
  private StringBuffer buffer = new StringBuffer();

  private final static String CRLF = System.getProperty("line.separator");


  /**
   * Gets the version attribute of the VCard object
   *
   * @return The version value
   */
  public String getVersion() {
    return version;
  }


  /**
   * Gets the formattedName attribute of the VCard object
   *
   * @return The formattedName value
   */
  public String getFormattedName() {
    return formattedName;
  }


  /**
   * Sets the formattedName attribute of the VCard object
   *
   * @param tmp The new formattedName value
   */
  public void setFormattedName(String tmp) {
    this.formattedName = tmp;
  }


  /**
   * Gets the structuredName attribute of the VCard object
   *
   * @return The structuredName value
   */
  public String getStructuredName() {
    return structuredName;
  }


  /**
   * Sets the structuredName attribute of the VCard object
   *
   * @param tmp The new structuredName value
   */
  public void setStructuredName(String tmp) {
    this.structuredName = tmp;
  }


  /**
   * Gets the contact attribute of the VCard object
   *
   * @return The contact value
   */
  public Contact getContact() {
    return contact;
  }


  /**
   * Sets the contact attribute of the VCard object
   *
   * @param tmp The new contact value
   */
  public void setContact(Contact tmp) {
    this.contact = tmp;
  }


  /**
   * Gets the buffer attribute of the VCard object
   *
   * @return The buffer value
   */
  public StringBuffer getBuffer() {
    return buffer;
  }


  /**
   * Sets the buffer attribute of the VCard object
   *
   * @param tmp The new buffer value
   */
  public void setBuffer(StringBuffer tmp) {
    this.buffer = tmp;
  }


  /**
   * Constructor for the VCard object
   */
  public VCard() {
  }


  /**
   * Constructor for the VCard object
   *
   * @param db        Description of the Parameter
   * @param contactId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public VCard(Connection db, int contactId) throws SQLException {
    contact.queryRecord(db, contactId);
    buildVCardDetails();
    generateVCard();
  }


  /**
   * Constructor for the VCard object
   *
   * @param contact Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public VCard(Contact contact) throws SQLException {
    this.contact = contact;
    buildVCardDetails();
    generateVCard();
  }


  /**
   * Constructor for the VCard object
   *
   * @param resource Description of the Parameter
   * @throws IOException Description of the Exception
   */
  public VCard(Connection db, Resource resource) throws SQLException, IOException {
    parseResource(db, resource);
  }


  /**
   * Description of the Method
   *
   * @throws SQLException Description of the Exception
   */
  private void buildVCardDetails() throws SQLException {
    if (contact.getId() == -1) {
      throw new SQLException("Contact ID not specified");
    }
    buildFormattedName();
    buildStructuredName();
  }


  /**
   * Formatted name is the name by which a person is commonly known and
   * conforms to the naming conventions of the country with which the contact
   * is associated
   */
  private void buildFormattedName() {
    formattedName += contact.getNameFirst() + " ";
    formattedName += contact.getNameMiddle() + " ";
    formattedName += contact.getNameLast();

    if ("".equals(formattedName.trim())) {
      formattedName = contact.getCompany();
    }
  }


  /**
   * Structured name is a list of components seperated by a semicolon in the
   * sequence Family name; Given name; Additional names; Honorific prefixes;
   * Honorific sufixes. Individual text components can include multiple text
   * values seperated by a comma character <p>
   * <p/>
   * eg: N:Stevenson;John;Philip,Paul;Dr.;Jr.,M.D.,A.C.P
   */
  private void buildStructuredName() {
    structuredName += contact.getNameLast() + ";";  //Family Name: Last name/Surname
    structuredName += contact.getNameFirst();            //Given Name: First/Middle name

    if ("".equals(contact.getNameFirst())) {
      structuredName += contact.getNameMiddle() + ";";
    }

    structuredName += contact.getAdditionalNames() + ";";    //Additional Names
    structuredName += contact.getNameSalutation() + ";";      //Honorary prefixes
    structuredName += contact.getNameSuffix() + ";";            //Honorary suffixes
  }


  /**
   * Description of the Method
   *
   * @throws SQLException Description of the Exception
   */
  protected void generateVCard() throws SQLException {
    if (contact.getId() == -1) {
      throw new SQLException("Contact ID not specified");
    }
    //Begin vcard output
    buffer.append("BEGIN:VCARD" + CRLF);
    buffer.append("VERSION:" + version + CRLF);
    buffer.append("N:" + structuredName + CRLF);
    buffer.append("FN:" + formattedName + CRLF);

    if (contact.getNickname() != null && contact.getNickname().length() > 0) {
      buffer.append("NICKNAME:" + contact.getNickname() + CRLF);
    }

    if (contact.getOrgName() != null && contact.getOrgName().length() > 0) {
      buffer.append("ORG:" + contact.getOrgName() + CRLF);
    }

    if (contact.getBirthDate() != null) {
      buffer.append("BDAY:" + contact.getBirthDate() + CRLF);
    }

    if (contact.getTitle() != null && contact.getTitle().length() > 0) {
      buffer.append("TITLE:" + contact.getTitle() + CRLF);
    }

    if (contact.getRole() != null && contact.getRole().length() > 0) {
      buffer.append("ROLE:" + contact.getRole() + CRLF);
    }
    
    //output email ids
    Iterator emailIds = contact.getEmailAddressList().iterator();
    while (emailIds.hasNext()) {
      ContactEmailAddress thisAddress = (ContactEmailAddress) emailIds.next();
      buffer.append("EMAIL;TYPE=INTERNET;");
      ArrayList typeList = getTypes(thisAddress.getTypeName());
      Iterator types = typeList.iterator();
      while (types.hasNext()) {
        String type = (String) types.next();
        buffer.append("TYPE=" + type + ";");
      }
      buffer.append(
          (thisAddress.getPrimaryEmail() ? "TYPE=pref" : "") + ":" + thisAddress.getEmail() + CRLF);
    }
    
    //output telephone numbers
    Iterator numbers = contact.getPhoneNumberList().iterator();
    while (numbers.hasNext()) {
      ContactPhoneNumber thisNumber = (ContactPhoneNumber) numbers.next();
      buffer.append("TEL;");
      ArrayList typeList = getTypes(thisNumber.getTypeName());
      Iterator types = typeList.iterator();
      while (types.hasNext()) {
        String type = (String) types.next();
        buffer.append("TYPE=" + type + ";");
      }
      buffer.append(
          (thisNumber.getPrimaryNumber() ? "TYPE=pref" : "") + ":" + thisNumber.getNumber() + CRLF);
    }
    
    //output instant messaging addresses
    Iterator imaddr = contact.getInstantMessageAddressList().iterator();
    while (imaddr.hasNext()) {
      ContactInstantMessageAddress thisAddress = (ContactInstantMessageAddress) imaddr.next();
      buffer.append(
          "X-" + thisAddress.getAddressIMServiceName().toUpperCase() + ";");
      ArrayList typeList = getTypes(thisAddress.getAddressIMTypeName());
      Iterator types = typeList.iterator();
      while (types.hasNext()) {
        String type = (String) types.next();
        buffer.append("TYPE=" + type + ";");
      }
      buffer.append(
          (thisAddress.getPrimaryIM() ? "TYPE=pref" : "") + ":" + thisAddress.getAddressIM() + CRLF);
    }
    
    //output the addresses
    Iterator addresses = contact.getAddressList().iterator();
    while (addresses.hasNext()) {
      ContactAddress thisAddress = (ContactAddress) addresses.next();
      buffer.append("ADR;");
      ArrayList typeList = getTypes(thisAddress.getTypeName());
      Iterator types = typeList.iterator();
      while (types.hasNext()) {
        String type = (String) types.next();
        buffer.append("TYPE=" + type + ";");
      }
      buffer.append(
          (thisAddress.getPrimaryAddress() ? "TYPE=pref" : "") + ":");
      //out put the address value
      /*
       *  Address value consists of a sequence of address components. The component values MUST be specified in
       *  their corresponding position. The structured type value corresponds, in sequence, to the post office box;
       *  the extended address; the street address; the locality (e.g., city); the region (e.g., state or province);
       *  the postal code; the country name. When a component value is missing, the associated component
       *  separator MUST still be specified.
       */
      buffer.append(";"); // post office box
      buffer.append(";"); // extended address
      buffer.append(
          thisAddress.getStreetAddressLine1() + " " + thisAddress.getStreetAddressLine2() + " "
          + thisAddress.getStreetAddressLine3() + ";"); // street address
      buffer.append(thisAddress.getCity() + ";"); //locality
      buffer.append(thisAddress.getState() + ";"); //region
      buffer.append(thisAddress.getZip() + ";"); //postal code
      buffer.append(thisAddress.getCountry()); //country
      buffer.append(CRLF);
    }

    if (contact.getUrl() != null && contact.getUrl().length() > 0) {
      buffer.append("URL:" + contact.getUrl() + CRLF);
    }

    if (contact.getNotes() != null && contact.getNotes().length() > 0) {
      buffer.append("NOTE:" + contact.getNotes() + CRLF);
    }

    buffer.append("END:VCARD");
  }


  /**
   * Gets the type attribute of the VCard object
   *
   * @param typeName Description of the Parameter
   * @return The type value
   */
  private ArrayList getTypes(String typeName) {
    ArrayList types = new ArrayList();

    if (typeName.toUpperCase().indexOf("BUSINESS") > -1 ||
        typeName.toUpperCase().indexOf("WORK") > -1) {
      types.add("WORK");
    }

    if (typeName.toUpperCase().indexOf("PERSONAL") > -1 ||
        typeName.toUpperCase().indexOf("HOME") > -1) {
      types.add("HOME");
    }

    if (typeName.toUpperCase().indexOf("FAX") > -1) {
      types.add("FAX");
    }

    if (typeName.toUpperCase().indexOf("PAGER") > -1) {
      types.add("PAGER");
    }

    if (typeName.toUpperCase().indexOf("MOBILE") > -1 ||
        typeName.toUpperCase().indexOf("CELL") > -1) {
      types.add("CELL");
    }

    if (typeName.toUpperCase().indexOf("OTHER") > -1) {
      //TODO: determine what to do with this kind
      types.add("WORK");
    }

    return types;
  }


  /**
   * Gets the bytes attribute of the VCard object
   *
   * @return The bytes value
   */
  public byte[] getBytes() {
    return buffer.toString().getBytes();
  }


  /**
   * parse the input stream and populate a contact object
   *
   * @param resource Description of the Parameter
   * @throws IOException Description of the Exception
   */
  public void parseResource(Connection db, Resource resource) throws SQLException, IOException {
    String name = resource.getName().substring(
        0, resource.getName().indexOf("."));
    StringTokenizer st = new StringTokenizer(name);

    if (st.hasMoreTokens()) {
      contact.setNameFirst(st.nextToken());
    }
    if (st.hasMoreTokens()) {
      contact.setNameMiddle(st.nextToken());
    }
    if (st.hasMoreTokens()) {
      contact.setNameLast(st.nextToken());
    }

    if ("".equals(contact.getNameLast()) && !"".equals(
        contact.getNameMiddle())) {
      contact.setNameLast(contact.getNameMiddle());
      contact.setNameMiddle("");
    }

    if ("".equals(contact.getNameLast()) && !"".equals(contact.getNameFirst())) {
      contact.setNameLast(contact.getNameFirst());
      contact.setNameFirst("");
    }

    contact.setId(resource.getContactId());

    buildFormattedName();
    buildStructuredName();

    BufferedReader in = new BufferedReader(
        new InputStreamReader(resource.streamContent()));
            
    //First line should read BEGIN:VCARD
    String line = in.readLine();
    System.out.println("LINE: " + line);
    if (line != null && !line.toUpperCase().equals("BEGIN:VCARD")) {
      return;
    }
    
    //Second line should read the version VERSION:3
    line = in.readLine();
    System.out.println("LINE: " + line);
    if (line != null && !line.toUpperCase().equals("VERSION:3.0")) {
      return;
    }

    while ((line = in.readLine()) != null) {
      System.out.println("LINE: " + line);
      parseLine(db, line);
    }
  }

  /**
   * parse the input stream and populate a contact object
   *
   * @param line Description of the Parameter
   */
  private void parseLine(Connection db, String line) throws SQLException {
    try {
      if (line.startsWith("N:")) {
        Components components = new Components(line);
        //Structured Name [Last Name/Surname; First Name/Middle Name; Additional Names; Honorary Prefixes; Honorary Suffixes
        if (components.hasMoreComponents()) {
          contact.setNameLast(components.nextComponent().substring(2));
        }
        if (components.hasMoreComponents()) {
          contact.setNameFirst(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          contact.setAdditionalNames(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          contact.setNameSalutation(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          contact.setNameSuffix(components.nextComponent());
        }
      } else if (line.startsWith("FN:")) {
        Components components = new Components(line, " ");
        //Formatted Name [First Name Middle Name Last Name]
        if (components.hasMoreComponents()) {
          contact.setNameFirst(components.nextComponent().substring(3));
        }
        if (components.hasMoreComponents()) {
          contact.setNameMiddle(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          contact.setNameLast(components.nextComponent());
        } else {
          contact.setNameLast(contact.getNameMiddle());
          contact.setNameMiddle("");
        }
      } else if (line.startsWith("ORG:")) {
        Components components = new Components(line);
        if (components.hasMoreComponents()) {
          contact.setCompany(components.nextComponent().substring(4));
        }
      } else if (line.startsWith("NICKNAME:")) {
        contact.setNickname(line.substring(9));
      } else if (line.startsWith("TITLE:")) {
        contact.setTitle(line.substring(6));
      } else if (line.startsWith("BDAY:")) {
        contact.setBirthDate(line.substring(5));
      } else if (line.startsWith("ROLE:")) {
        contact.setRole(line.substring(5));
      } else if (line.indexOf("URL") > -1) {
        contact.setUrl(line.substring(line.indexOf(":") + 1));
      } else if (line.indexOf("NOTE") > -1) {
        contact.setNotes(line.substring(line.indexOf(":") + 1));
      } else if (line.indexOf("EMAIL") > -1) {
        ContactEmailAddress email = new ContactEmailAddress();
        if (line.toUpperCase().indexOf("HOME") > -1) {
          email.setType(getTypeCode(db, "lookup_contactemail_types", "Home"));
        } else if (line.toUpperCase().indexOf("WORK") > -1) {
          email.setType(getTypeCode(db, "lookup_contactemail_types", "Work"));
        } else {
          email.setType(getTypeCode(db, "lookup_contactemail_types", "Other"));
        }
        if (line.indexOf("pref") > -1) {
          email.setPrimaryEmail(true);
        }
        email.setEmail(line.substring(line.indexOf(":") + 1));
        contact.getEmailAddressList().add(email);
      } else if (line.indexOf("TEL") > -1) {
        ContactPhoneNumber number = new ContactPhoneNumber();
        if (line.toUpperCase().indexOf("HOME") > -1) {
          number.setType(getTypeCode(db, "lookup_contactphone_types", "Home"));
        } else if (line.toUpperCase().indexOf("WORK") > -1) {
          number.setType(getTypeCode(db, "lookup_contactphone_types", "Work"));
        } else if (line.toUpperCase().indexOf("CELL") > -1) {
          number.setType(
              getTypeCode(db, "lookup_contactphone_types", "Mobile"));
        } else if (line.toUpperCase().indexOf("PAGER") > -1) {
          number.setType(
              getTypeCode(db, "lookup_contactphone_types", "Pager"));
        } else if (line.toUpperCase().indexOf("MAIN") > -1) {
          number.setType(getTypeCode(db, "lookup_contactphone_types", "Main"));
        } else {
          number.setType(
              getTypeCode(db, "lookup_contactphone_types", "Other"));
        }
        if (line.indexOf("pref") > -1) {
          number.setPrimaryNumber(true);
        }
        number.setNumber(line.substring(line.indexOf(":") + 1));
        contact.getPhoneNumberList().add(number);
      } else if (line.indexOf("ADR") > -1 && ((line.indexOf("type") > -1) || (line.indexOf(
          "TYPE") > -1))) {
        //Address is valid only if it has a type. Some lines specific to Mac Address Book has ADR but no useful information
        //Such lines can be ignored. Line with a type has useful information. Hence this check.
        ContactAddress contactAddress = new ContactAddress();

        String meta = line.substring(0, line.indexOf(":"));
        if (meta.toUpperCase().indexOf("WORK") > -1) {
          contactAddress.setType(
              getTypeCode(db, "lookup_contactaddress_types", "Work"));
        } else if (meta.toUpperCase().indexOf("HOME") > -1) {
          contactAddress.setType(
              getTypeCode(db, "lookup_contactaddress_types", "Home"));
        } else {
          contactAddress.setType(
              getTypeCode(db, "lookup_contactaddress_types", "Other"));
        }
        if (meta.indexOf("pref") > -1) {
          contactAddress.setPrimaryAddress(true);
        }

        String address = line.substring(line.indexOf(":") + 1);
        Components components = new Components(address);
        //Address [po box;extended addr;street addr;city;state;postal code;country]
        
        if (components.hasMoreComponents()) {
          components.nextComponent(); //ignore po box
        }
        if (components.hasMoreComponents()) {
          components.nextComponent(); //ignore extended addr
        }
        if (components.hasMoreComponents()) {
          contactAddress.setStreetAddressLine1(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          contactAddress.setCity(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          contactAddress.setState(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          contactAddress.setZip(components.nextComponent());
        }
        if (components.hasMoreComponents()) {
          String country = components.nextComponent().toUpperCase();
          if (country != null) {
            if ("USA".equals(country) || "US".equals(country)) {
              country = "UNITED STATES";
            }
          }
          contactAddress.setCountry(country);
        }
        //System.out.println("ADDRESS LINE 1: " + contactAddress.getStreetAddressLine1());
        //System.out.println("CITY: " + contactAddress.getCity());
        //System.out.println("STATE: " + contactAddress.getState());
        //System.out.println("ZIP: " + contactAddress.getZip());
        //System.out.println("COUNTRY: " + contactAddress.getCountry());
        contact.getAddressList().add(contactAddress);
      } else if (line.indexOf("AIM") > -1 || line.indexOf("MSN") > -1 ||
          line.indexOf("YAHOO") > -1 || line.indexOf("JABBER") > -1 || line.indexOf(
              "ICQ") > -1) {
        //This code is specific to Mac OSX Address Book Application.
        ContactInstantMessageAddress imAddress = new ContactInstantMessageAddress();
        if (line.indexOf("WORK") > -1) {
          imAddress.setAddressIMType(
              getTypeCode(db, "lookup_im_types", "Work"));
        } else if (line.indexOf("HOME") > -1) {
          imAddress.setAddressIMType(
              getTypeCode(db, "lookup_im_types", "Home"));
        } else {
          imAddress.setAddressIMType(
              getTypeCode(db, "lookup_im_types", "Other"));
        }
        if (line.indexOf("pref") > -1) {
          imAddress.setPrimaryIM(true);
        }

        if (line.indexOf("ICQ") > -1) {
          imAddress.setAddressIMService(
              getTypeCode(db, "lookup_im_services", "ICQ"));
        } else if (line.indexOf("YAHOO") > -1) {
          imAddress.setAddressIMService(
              getTypeCode(db, "lookup_im_services", "Yahoo"));
        } else if (line.indexOf("MSN") > -1) {
          imAddress.setAddressIMService(
              getTypeCode(db, "lookup_im_services", "MSN"));
        } else if (line.indexOf("JABBER") > -1) {
          imAddress.setAddressIMService(
              getTypeCode(db, "lookup_im_services", "Jabber"));
        }
        //defaults to AIM.??
        else {
          imAddress.setAddressIMService(
              getTypeCode(db, "lookup_im_services", "AIM"));
        }

        imAddress.setAddressIM(line.substring(line.indexOf(":") + 1));
        contact.getInstantMessageAddressList().add(imAddress);
      }
    } catch (Exception e) {
      //should not happen. Silent catch
      e.printStackTrace(System.out);
    }
  }

  /**
   * Gets the typeCode attribute of the VCard object
   *
   * @param db        Description of the Parameter
   * @param tableName Description of the Parameter
   * @param typeName  Description of the Parameter
   * @return The typeCode value
   * @throws SQLException Description of the Exception
   */
  public int getTypeCode(Connection db, String tableName, String typeName) throws SQLException {
    int code = -1;
    PreparedStatement pst = db.prepareStatement(
        "SELECT code " +
        "FROM " + DatabaseUtils.getTableName(db, tableName) + " " +
        "WHERE " + DatabaseUtils.toLowerCase(db) + "(description) = ? ");
    pst.setString(1, typeName.toLowerCase());
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      code = rs.getInt("code");
    } else {
      //typeName does not exist. Determine the default item
      rs.close();
      pst = db.prepareStatement(
          "SELECT code " +
          "FROM " + DatabaseUtils.getTableName(db, tableName) + " " +
          "WHERE default_item = ? ");
      pst.setBoolean(1, true);
      rs = pst.executeQuery();
      if (rs.next()) {
        code = rs.getInt("code");
      } else {
        //Default Item does not exist
        rs.close();
        pst = db.prepareStatement("SELECT code FROM " + DatabaseUtils.getTableName(db, tableName));
        rs = pst.executeQuery();
        if (rs.next()) {
          code = rs.getInt("code");
        }
      }
    }
    rs.close();
    pst.close();
    return code;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public boolean save(Connection db) throws SQLException {
    if (contact != null) {
      return (contact.insert(db));
    }
    return false;
  }

  /**
   * Description of the Method
   *
   * @param db Description of the Parameter
   * @return Description of the Return Value
   * @throws SQLException Description of the Exception
   */
  public boolean update(Connection db) throws SQLException {
    if (contact != null) {
      return (contact.update(db) > 0);
    }
    return false;
  }

  /**
   * Description of the Method
   */
  public boolean isValid() {
    if (contact != null) {
      boolean valid = true;
      if (contact.getNameLast() == null) {
        valid = false;
      }
      return valid;
    }
    return false;
  }
}

