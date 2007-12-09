/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
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
package helpers.sqlHelper;

import java.util.HashMap;

import org.aspcfs.modules.service.sync.SyncPackagerTest;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Feb 7, 2007
 *
 */
public class SqlEntry {
  public enum Types {
    TYPE_STR,
    TYPE_BOOL,
    TYPE_INT,
    TYPE_BYTE,
    TYPE_FLOAT,
    TYPE_TIMESTAMP,
  }
  

  String tableName = "";
  String uniqueField = "";
  String primSequence = "";
  HashMap<String, Types> fields = new HashMap<String, Types>();
  HashMap<String, String> references = new HashMap<String, String>();

  public SqlEntry() {
  }
  
  /**
   * @return the fields
   */
  public HashMap<String, Types> getFields() {
    return fields;
  }

  public HashMap<String, String> getReferences() {
    return references;
  }

  public void addField(String fieldName, Types fieldType) {
    fields.put(fieldName, fieldType);
  }
  
  public void addReference(String fieldName, String referenceName) {
    references.put(fieldName, referenceName);
  }

  public void addField(String fieldName, String fieldType) {
    addField(fieldName, getType(fieldType));
  }
  
  public static Types getType(String fieldType){
    Types type = null;
    
    if(fieldType.toUpperCase().equals("VARCHAR") || fieldType.toUpperCase().equals("STRING")
        || fieldType.toUpperCase().equals("TEXT")|| fieldType.toUpperCase().equals("CHAR")){
      type = Types.TYPE_STR;
    }else if(fieldType.toUpperCase().equals("BOOLEAN")){
      type = Types.TYPE_BOOL;
    }else if(fieldType.toUpperCase().equals("INTEGER") || fieldType.toUpperCase().equals("INT")){
      type = Types.TYPE_INT;
    }else if(fieldType.toUpperCase().equals("SMALLINT")){
      type = Types.TYPE_BYTE;
    }else if(fieldType.toUpperCase().equals("FLOAT")){
      type = Types.TYPE_FLOAT;
    }else if(fieldType.toUpperCase().equals("TIMESTAMP") || fieldType.toUpperCase().equals("DATE")){
      type = Types.TYPE_TIMESTAMP;
    }
    if(type == null){
      System.out.println("Unknown type: " + fieldType);
    }
    return type;
  }

  /**
   * @return the tableName
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * @param tableName the tableName to set
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * @return the uniqueField
   */
  public String getUniqueField() {
    return uniqueField;
  }

  /**
   * @param uniqueField the uniqueField to set
   */
  public void setUniqueField(String uniqueField) {
    this.uniqueField = uniqueField;
  }

  /**
   * @return the sequence
   */
  public String getPrimSequence() {
    return primSequence;
  }

  /**
   * @param sequence the sequence to set
   */
  public void setPrimSequence(String sequence) {
    this.primSequence = sequence;
  }
}
