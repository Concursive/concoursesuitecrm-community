/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.utils;

import java.sql.Types;

/**
 *  Description of the Class
 *
 * @author     Ananth
 * @created    March 16, 2007
 */
public class DatabaseColumn {
  private String name = "";
  private int type = Types.NULL;
  private boolean allowsNull = true;
  private boolean hasDefault = false;
  private boolean isPrimaryKey = false;
  private String defaultValue = "";
  private int size = 300;
  private boolean hasReference = false;
  private String referenceTable = "";
  private String referenceColumn = "";


  /**
   *  Gets the hasReference attribute of the DatabaseColumn object
   *
   * @return    The hasReference value
   */
  public boolean getHasReference() {
    return hasReference;
  }


  /**
   *  Sets the hasReference attribute of the DatabaseColumn object
   *
   * @param  tmp  The new hasReference value
   */
  public void setHasReference(boolean tmp) {
    this.hasReference = tmp;
  }


  /**
   *  Sets the hasReference attribute of the DatabaseColumn object
   *
   * @param  tmp  The new hasReference value
   */
  public void setHasReference(String tmp) {
    this.hasReference = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the isPrimaryKey attribute of the DatabaseColumn object
   *
   * @return    The isPrimaryKey value
   */
  public boolean getIsPrimaryKey() {
    return isPrimaryKey;
  }


  /**
   *  Sets the isPrimaryKey attribute of the DatabaseColumn object
   *
   * @param  tmp  The new isPrimaryKey value
   */
  public void setIsPrimaryKey(boolean tmp) {
    this.isPrimaryKey = tmp;
  }


  /**
   *  Sets the isPrimaryKey attribute of the DatabaseColumn object
   *
   * @param  tmp  The new isPrimaryKey value
   */
  public void setIsPrimaryKey(String tmp) {
    this.isPrimaryKey = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the referenceTable attribute of the DatabaseColumn object
   *
   * @return    The referenceTable value
   */
  public String getReferenceTable() {
    return referenceTable;
  }


  /**
   *  Sets the referenceTable attribute of the DatabaseColumn object
   *
   * @param  tmp  The new referenceTable value
   */
  public void setReferenceTable(String tmp) {
    this.referenceTable = tmp;
    if (referenceColumn.length() > 0) {
      this.hasReference = true;
    }
  }


  /**
   *  Gets the referenceColumn attribute of the DatabaseColumn object
   *
   * @return    The referenceColumn value
   */
  public String getReferenceColumn() {
    return referenceColumn;
  }


  /**
   *  Sets the referenceColumn attribute of the DatabaseColumn object
   *
   * @param  tmp  The new referenceColumn value
   */
  public void setReferenceColumn(String tmp) {
    this.referenceColumn = tmp;
    if (referenceTable.length() > 0) {
      this.hasReference = true;
    }
  }



  /**
   *  Gets the size attribute of the DatabaseColumn object
   *
   * @return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Sets the size attribute of the DatabaseColumn object
   *
   * @param  tmp  The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }


  /**
   *  Sets the size attribute of the DatabaseColumn object
   *
   * @param  tmp  The new size value
   */
  public void setSize(String tmp) {
    this.size = Integer.parseInt(tmp);
  }


  /**
   *  Gets the defaultValue attribute of the DatabaseColumn object
   *
   * @return    The defaultValue value
   */
  public String getDefaultValue() {
    return defaultValue;
  }


  /**
   *  Sets the defaultValue attribute of the DatabaseColumn object
   *
   * @param  tmp  The new defaultValue value
   */
  public void setDefaultValue(String tmp) {
    this.defaultValue = tmp;
    this.hasDefault = true;
  }


  /**
   *  Gets the name attribute of the DatabaseColumn object
   *
   * @return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Sets the name attribute of the DatabaseColumn object
   *
   * @param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Gets the type attribute of the DatabaseColumn object
   *
   * @return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Sets the type attribute of the DatabaseColumn object
   *
   * @param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the type attribute of the DatabaseColumn object
   *
   * @param  tmp  The new type value
   */
  public void setType(String tmp) {
    this.type = Integer.parseInt(tmp);
  }


  /**
   *  Gets the allowsNull attribute of the DatabaseColumn object
   *
   * @return    The allowsNull value
   */
  public boolean getAllowsNull() {
    return allowsNull;
  }


  /**
   *  Sets the allowsNull attribute of the DatabaseColumn object
   *
   * @param  tmp  The new allowsNull value
   */
  public void setAllowsNull(boolean tmp) {
    this.allowsNull = tmp;
  }


  /**
   *  Sets the allowsNull attribute of the DatabaseColumn object
   *
   * @param  tmp  The new allowsNull value
   */
  public void setAllowsNull(String tmp) {
    this.allowsNull = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the hasDefault attribute of the DatabaseColumn object
   *
   * @return    The hasDefault value
   */
  public boolean getHasDefault() {
    return hasDefault;
  }


  /**
   *  Sets the hasDefault attribute of the DatabaseColumn object
   *
   * @param  tmp  The new hasDefault value
   */
  public void setHasDefault(boolean tmp) {
    this.hasDefault = tmp;
  }


  /**
   *  Sets the hasDefault attribute of the DatabaseColumn object
   *
   * @param  tmp  The new hasDefault value
   */
  public void setHasDefault(String tmp) {
    this.hasDefault = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Constructor for the DatabaseColumn object
   */
  public DatabaseColumn() { }


  /**
   *  Constructor for the DatabaseColumn object
   *
   * @param  name  Description of the Parameter
   * @param  type  Description of the Parameter
   */
  public DatabaseColumn(String name, int type) {
    this.name = name;
    this.type = type;
  }


  /**
   *  Constructor for the DatabaseColumn object
   *
   * @param  column  Description of the Parameter
   */
  public DatabaseColumn(DatabaseColumn column) {
    this.name = column.getName();
    this.type = column.getType();
    this.allowsNull = column.getAllowsNull();
    this.hasDefault = column.getHasDefault();
    this.isPrimaryKey = column.getIsPrimaryKey();
    this.defaultValue = column.getDefaultValue();
    this.size = column.getSize();
    this.hasReference = column.getHasReference();
    this.referenceTable = column.getReferenceTable();
    this.referenceColumn = column.getReferenceColumn();
  }


  /**
   *  Gets the primaryKey attribute of the DatabaseColumn object
   *
   * @return    The primaryKey value
   */
  public boolean isPrimaryKey() {
    return isPrimaryKey;
  }


  /**
   *  Gets the createSQL attribute of the DatabaseColumn object
   *
   * @param  dbType  Description of the Parameter
   * @return         The createSQL value
   */
  public String getCreateSQL(int dbType) {
    return getCreateSQL(dbType, "");
  }


  /**
   *  Gets the createSQL attribute of the DatabaseColumn object
   *
   * @param  dbType        Description of the Parameter
   * @param  sequenceName  Description of the Parameter
   * @return               The createSQL value
   */
  public String getCreateSQL(int dbType, String sequenceName) {
    StringBuffer sql = new StringBuffer();
    sql.append(name);

    switch (dbType) {
        case DatabaseUtils.POSTGRESQL:
          if (isPrimaryKey) {
            if (StringUtils.hasText(sequenceName)) {
              sql.append(" INTEGER DEFAULT nextval('" + sequenceName + "') NOT NULL");
            } else {
              sql.append(" SERIAL");
            }
            sql.append(" PRIMARY KEY");
          } else {
            appendTypeSQL(sql, dbType);
            if (hasDefault) {
              sql.append(" DEFAULT " + defaultValue);
            }
            if (!allowsNull) {
              sql.append(" NOT NULL");
            }
            if (hasReference) {
              sql.append(" REFERENCES " + referenceTable + "(" + referenceColumn + ")");
            }
          }
          break;
        case DatabaseUtils.MSSQL:
          if (isPrimaryKey) {
            sql.append(" INT IDENTITY PRIMARY KEY");
          } else {
            appendTypeSQL(sql, dbType);
            if (hasDefault) {
              sql.append(" DEFAULT " + defaultValue);
            }
            if (!allowsNull) {
              sql.append(" NOT NULL");
            }
            if (hasReference) {
              sql.append(" REFERENCES " + referenceTable + "(" + referenceColumn + ")");
            }
          }
          break;
        case DatabaseUtils.ORACLE:
          if (isPrimaryKey) {
            sql.append(" INTEGER NOT NULL UNIQUE");
          } else {
            appendTypeSQL(sql, dbType);
            if (hasDefault) {
              sql.append(" DEFAULT " + defaultValue);
            }
            if (!allowsNull) {
              sql.append(" NOT NULL");
            }
            if (hasReference) {
              sql.append(" REFERENCES " + referenceTable + "(" + referenceColumn + ")");
            }
          }
          break;
        case DatabaseUtils.FIREBIRD:
        case DatabaseUtils.DAFFODILDB:
        case DatabaseUtils.DB2:
        case DatabaseUtils.MYSQL:
        case DatabaseUtils.DERBY:
        case DatabaseUtils.INTERBASE:
    }
    return sql.toString();
  }


  /**
   *  Description of the Method
   *
   * @param  dbType  Description of the Parameter
   * @param  sql     Description of the Parameter
   */
  private void appendTypeSQL(StringBuffer sql, int dbType) {
    switch (dbType) {
        case DatabaseUtils.POSTGRESQL:
          if (type == java.sql.Types.INTEGER) {
            sql.append(" INTEGER");
          } else if (type == java.sql.Types.VARCHAR) {
            sql.append(" VARCHAR(" + size + ")");
          } else if (type == java.sql.Types.LONGVARCHAR) {
            sql.append(" TEXT");
          } else if (type == java.sql.Types.BOOLEAN) {
            sql.append(" BOOLEAN");
          } else if (type == java.sql.Types.TIMESTAMP) {
            sql.append(" TIMESTAMP(3)");
          } else if (type == java.sql.Types.FLOAT) {
            sql.append(" FLOAT");
          }
          break;
        case DatabaseUtils.MSSQL:
          if (type == java.sql.Types.INTEGER) {
            sql.append(" INTEGER");
          } else if (type == java.sql.Types.VARCHAR) {
            sql.append(" VARCHAR(" + size + ")");
          } else if (type == java.sql.Types.LONGVARCHAR) {
            sql.append(" TEXT");
          } else if (type == java.sql.Types.BOOLEAN) {
            sql.append(" BIT");
          } else if (type == java.sql.Types.TIMESTAMP) {
            sql.append(" DATETIME");
          } else if (type == java.sql.Types.FLOAT) {
            sql.append(" FLOAT");
          }
          break;
        case DatabaseUtils.ORACLE:
          if (type == java.sql.Types.INTEGER) {
            sql.append(" INTEGER");
          } else if (type == java.sql.Types.VARCHAR) {
            sql.append(" NVARCHAR2(" + size + ")");
          } else if (type == java.sql.Types.LONGVARCHAR) {
            sql.append(" CLOB");
          } else if (type == java.sql.Types.BOOLEAN) {
            sql.append(" CHAR(1)");
          } else if (type == java.sql.Types.TIMESTAMP) {
            sql.append(" TIMESTAMP");
          } else if (type == java.sql.Types.FLOAT) {
            sql.append(" FLOAT");
          }
          break;
        case DatabaseUtils.FIREBIRD:
          break;
        case DatabaseUtils.DAFFODILDB:
          break;
        case DatabaseUtils.DB2:
          break;
        case DatabaseUtils.MYSQL:
          break;
        case DatabaseUtils.DERBY:
          break;
        case DatabaseUtils.INTERBASE:
          break;
    }
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("      name : " + name + "\n");
    sb.append("      type : " + type + "\n");
    if (!allowsNull) {
      sb.append("allowsNull : " + allowsNull + "\n");
    }
    if (hasDefault) {
      sb.append("   default : " + defaultValue + "\n");
    }
    sb.append("      size : " + size + "\n");
    if (hasReference) {
      sb.append("references : " + referenceTable + "(" + referenceColumn + ")\n");
    }
    return sb.toString();
  }
}

