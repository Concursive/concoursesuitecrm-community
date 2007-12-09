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
package org.aspcfs.modules.contacts.base;

import org.aspcfs.apps.transfer.reader.mapreader.Property;
import org.aspcfs.apps.transfer.reader.mapreader.PropertyMap;
import org.aspcfs.utils.CFSFileReader;
import org.aspcfs.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Validates the import data against the properties
 *
 * @author     Mathur
 * @version $id:exp$
 * @created April 6, 2004
 */
public class ContactImportValidate {
  PropertyMap propertyMap = null;
  LinkedHashMap fieldMappings = new LinkedHashMap();
  ArrayList sampleRecords = new ArrayList();
  ContactImport contactImport = null;
  HashMap errors = new HashMap();
  String filePath = null;


  /**
   * Constructor for the ContactImportValidate object
   */
  public ContactImportValidate() {
  }


  /**
   * Sets the propertyMapList attribute of the ContactImportValidate object
   *
   * @param  tmp  The new propertyMapList value
   */
  public void setPropertyMap(PropertyMap tmp) {
    this.propertyMap = tmp;
  }


  /**
   * Sets the filePath attribute of the ContactImportValidate object
   *
   * @param  tmp  The new filePath value
   */
  public void setFilePath(String tmp) {
    this.filePath = tmp;
  }


  /**
   * Sets the fieldMappings attribute of the ContactImportValidate object
   *
   * @param  tmp  The new fieldMappings value
   */
  public void setFieldMappings(LinkedHashMap tmp) {
    this.fieldMappings = tmp;
  }


  /**
   * Gets the fieldMappings attribute of the ContactImportValidate object
   *
   * @return    The fieldMappings value
   */
  public LinkedHashMap getFieldMappings() {
    return fieldMappings;
  }


  /**
   * Sets the contactImport attribute of the ContactImportValidate object
   *
   * @param  tmp  The new contactImport value
   */
  public void setContactImport(ContactImport tmp) {
    this.contactImport = tmp;
  }


  /**
   * Sets the sampleRecords attribute of the ContactImportValidate object
   *
   * @param  tmp  The new sampleRecords value
   */
  public void setSampleRecords(ArrayList tmp) {
    this.sampleRecords = tmp;
  }


  /**
   * Sets the errors attribute of the ContactImportValidate object
   *
   * @param  tmp  The new errors value
   */
  public void setErrors(HashMap tmp) {
    this.errors = tmp;
  }


  /**
   * Gets the errors attribute of the ContactImportValidate object
   *
   * @return    The errors value
   */
  public HashMap getErrors() {
    return errors;
  }


  /**
   * Gets the generalErrors attribute of the ContactImportValidate object
   *
   * @return    The generalErrors value
   */
  public ArrayList getGeneralErrors() {
    if (errors.containsKey("generalErrors")) {
      return (ArrayList) errors.get("generalErrors");
    }
    return null;
  }


  /**
   * Gets the fieldErrors attribute of the ContactImportValidate object
   *
   * @return    The fieldErrors value
   */
  public HashMap getFieldErrors() {
    if (errors.containsKey("fieldErrors")) {
      return (HashMap) errors.get("fieldErrors");
    }
    return null;
  }


  /**
   * Gets the sampleRecords attribute of the ContactImportValidate object
   *
   * @return    The sampleRecords value
   */
  public ArrayList getSampleRecords() {
    return sampleRecords;
  }


  /**
   * Gets the contactImport attribute of the ContactImportValidate object
   *
   * @return    The contactImport value
   */
  public ContactImport getContactImport() {
    return contactImport;
  }


  /**
   * Gets the propertyMap attribute of the ContactImportValidate object
   *
   * @return    The propertyMap value
   */
  public PropertyMap getPropertyMap() {
    return propertyMap;
  }


  /**
   * Gets the filePath attribute of the ContactImportValidate object
   *
   * @return    The filePath value
   */
  public String getFilePath() {
    return filePath;
  }


  /**
   * Description of the Method
   */
  public void initialize() {
    autoMapProperties();
    buildImportSample(5);
  }


  /**
   * Auto maps fields of a import file to contact properties
   */
  public void autoMapProperties() {
    ArrayList thisRecord = null;
    try {
      CFSFileReader fileReader = new CFSFileReader(
          filePath, contactImport.getFileType());
      CFSFileReader.Record record = null;
      if ((record = fileReader.nextLine()) != null) {
        //Get the record
        thisRecord = record.data;

        if (thisRecord == null || thisRecord.size() < 1) {
          addGeneralError(
              "Invalid Header: Could not parse line based on data or there data is insufficient");
          return;
        }
      }

      int fieldNumber = -1;
      Iterator fields = thisRecord.iterator();
      while (fields.hasNext()) {
        ++fieldNumber;
        String field = (String) fields.next();
        if ("_ERROR".equals(field)) {
          continue;
        }
        if (!"".equals(StringUtils.toString(field))) {
          if (fieldMappings.containsKey(field)) {
            addGeneralError(
                "Duplicate Field: Field \"" + field + "\" occurs twice in the header record");
          } else {
            Property thisProperty = propertyMap.mapProperty(
                field, fieldNumber);
            fieldMappings.put(field, thisProperty);
          }
        } else {
          addGeneralError(
              "Invalid Field: Field Number " + fieldNumber + "is blank");
        }
      }

      if (System.getProperty("DEBUG") != null) {
        printHeaderMappings();
      }
    } catch (java.io.FileNotFoundException e) {
      addGeneralError("File Not Found: Import file does not exist");
    } catch (Exception e) {
      System.out.println("ContactImportValidate -> EXCEPTION " + e);
    }
  }


  /**
   * Retrieves first "n" lines from the import file
   *
   * @param  count  Description of the Parameter
   */
  public void buildImportSample(int count) {
    try {
      CFSFileReader fileReader = new CFSFileReader(
          filePath, contactImport.getFileType());
      CFSFileReader.Record record = null;
      ArrayList header = null;
      while ((record = fileReader.nextLine()) != null && count > 0) {
        if (record.isEmpty()) {
          continue;
        }
        sampleRecords.add(record.line);
        if (sampleRecords.size() == 1) {
          header = record.data;
        }
        if (sampleRecords.size() == 2) {
          if (header.size() != record.data.size()) {
            addGeneralError(
                "Insufficient Data: Count of columns in header and data do not match");
          }
        }
        count--;
      }
    } catch (java.io.FileNotFoundException e) {
      addGeneralError("File Not Found: Import file does not exist");
    } catch (Exception e) {
      System.out.println("ContactImportValidatea -> Sampling Exception " + e);
    }
  }


  /**
   * Run the validation
   *
   * @param  request  Description of the Parameter
   */
  public void validate(HttpServletRequest request) {
    //get field mappings from file
    ArrayList thisRecord = null;
    try {
      CFSFileReader fileReader = new CFSFileReader(
          filePath, contactImport.getFileType());
      CFSFileReader.Record record = null;
      if ((record = fileReader.nextLine()) != null) {
        //Get the record
        thisRecord = record.data;

        if (thisRecord == null || thisRecord.size() < 1) {
          addGeneralError(
              "Invalid Header: Could not parse line based on data or there data is insufficient");
          return;
        }
      }

      int fieldNumber = -1;
      Iterator fields = thisRecord.iterator();
      while (fields.hasNext()) {
        ++fieldNumber;
        String field = (String) fields.next();
        if ("_ERROR".equals(field)) {
          continue;
        }
        if (fieldMappings.containsKey(field)) {
          addGeneralError(
              "Duplicate Field: Field \"" + field + "\" occurs twice in the header record");
        } else {
          Property mappedProperty = null;

          //get value from request
          if (request.getParameter(field) != null) {
            String propertyName = request.getParameter(field);
            String groupId = request.getParameter(field + "groupId");
            if (!"-1".equals(propertyName)) {
              Property thisProperty = propertyMap.getProperty(
                  field, propertyName, groupId);
              if (thisProperty.getMappedColumn() > 0) {
                if (thisProperty.getGroupId() > 0) {
                  addFieldError(
                      field, "Multiple Property Map: The property \"" + thisProperty.getDisplayName() + " has already been mapped to another field");
                } else {
                  addFieldError(
                      field, "Multiple Property Map: The property \"" + thisProperty.getDisplayName() + "\" has already been mapped to another field");
                }
              } else {
                if (System.getProperty("DEBUG") != null) {
                  System.out.println(
                      "** Mapping " + field + " to " + fieldNumber);
                }
                thisProperty.setMappedColumn(fieldNumber);
                mappedProperty = thisProperty;

                //check for email type
                if (mappedProperty.getName().equals("email")) {
                  String emailType = request.getParameter(
                      field + "_hiddenemailtype");
                  Property emailTypeProperty = propertyMap.getProperty(
                      field, "contactEmail.type", groupId);
                  emailTypeProperty.setDefaultValue(emailType);
                }

                //check for phone
                if (mappedProperty.getName().equals("number")) {
                  String phoneType = request.getParameter(
                      field + "_hiddenphonetype");
                  Property phoneTypeProperty = propertyMap.getProperty(
                      field, "contactPhone.type", groupId);
                  phoneTypeProperty.setDefaultValue(phoneType);
                }
                //check for address
                if (mappedProperty.getName().equals("streetAddressLine1")) {
                  String addressType = request.getParameter(
                      field + "_hiddenaddresstype");
                  Property addressTypeProperty = propertyMap.getProperty(
                      field, "contactAddress.type", groupId);
                  addressTypeProperty.setDefaultValue(addressType);
                }
              }
            }
          }
          fieldMappings.put(field, mappedProperty);
        }
      }

      //required properties
      checkRequiredProperties();

      //build first five lines
      buildImportSample(5);
    } catch (java.io.FileNotFoundException e) {
      addGeneralError("File Not Found: Import file does not exist");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("ContactImportValidate -> Sampling Exception " + e);
    }
  }


  /**
   * Checks to see if all required properties have a mapping
   */
  public void checkRequiredProperties() {
    ArrayList thisList = propertyMap.getRequiredProperties();
    if (thisList.size() > 0) {
      Iterator i = thisList.iterator();
      while (i.hasNext()) {
        Property p = (Property) i.next();
        if (p.getMappedColumn() < 0) {
          //check to see if this property is required
          if (p.checkIsRequired(fieldMappings)) {
            //check to see if there are any substitute properties
            if (!"".equals(StringUtils.toString(p.getSubstitute()))) {
              String substitute = p.getSubstitute();
              Property thisProp = propertyMap.getProperty(substitute);
              if (thisProp.getMappedColumn() < 0) {
                addGeneralError("Required Property: The property " + p.getDisplayName() + " or " + thisProp.getDisplayName() + " is required");
              }
            } else {
              addGeneralError("Required Property: The property " + p.getDisplayName() + " is required");
            }
          }
        }
      }
    }
  }


  /**
   * Adds a general error
   *
   * @param  error  The feature to be added to the FileError attribute
   */
  public void addGeneralError(String error) {
    if (!errors.containsKey("generalErrors")) {
      errors.put("generalErrors", new ArrayList());
    }
    ArrayList thisList = (ArrayList) errors.get("generalErrors");
    thisList.add(error);
  }


  /**
   * Adds a field specific error
   *
   * @param error The feature to be added to the FieldError attribute
   * @param field The feature to be added to the FieldError attribute
   */
  public void addFieldError(String field, String error) {
    if (!errors.containsKey("fieldErrors")) {
      errors.put("fieldErrors", new HashMap());
    }
    HashMap thisMap = (HashMap) errors.get("fieldErrors");
    thisMap.put(field, error);
  }


  /**
   * Description of the Method
   */
  public void printHeaderMappings() {
    System.out.println(
        "================= Auto Mapped Fields =====================");
    Iterator props = fieldMappings.keySet().iterator();
    while (props.hasNext()) {
      String field = (String) props.next();
      Property property = (Property) fieldMappings.get(field);
      if (property != null && property.getMappedColumn() > -1) {
        System.out.println(
            property.getUniqueName() + ":" + property.getMappedColumn());
      }
    }
    System.out.println(
        "===========================================================");
  }
}

