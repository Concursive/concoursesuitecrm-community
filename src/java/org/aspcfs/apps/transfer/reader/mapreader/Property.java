package org.aspcfs.apps.transfer.reader.mapreader;

import org.aspcfs.utils.*;
import java.util.*;
import org.w3c.dom.*;

/**
 *  Represents a definition for a property of a map in XML
 *
 *@author     Akhilesh Mathur
 *@created    April 5, 2004
 *@version    $id:exp$
 */
public class Property implements Cloneable {

  private String name = null;
  private String delimiter = null;
  private String lookupTable = null;
  private String displayName = null;
  private String uniqueName = null;
  private String defaultValue = null;
  private String substitute = null;
  private int mappedColumn = -1;
  private int groupId = -1;
  private boolean required = false;
  private boolean multiple = false;
  private boolean isForPrompting = true;
  private ArrayList aliases = null;


  /**
   *  Constructor for the Property object
   */
  public Property() { }


  /**
   *  Constructor for the Property object
   *
   *@param  name  Description of the Parameter
   */
  public Property(String name) {
    this.name = name;
  }


  /**
   *  Sets the attributes of a property based on a XML node
   *
   *@param  n  Description of the Parameter
   */
  public Property(Node n) {
    //name
    String nodeText = XMLUtils.getNodeText((Element) n);
    if (nodeText != null) {
      this.name = nodeText;
    }

    //aliases
    String tmpValue = ((Element) n).getAttribute("aliases");
    if (!"".equals(StringUtils.toString(tmpValue))) {
      this.setAliases(StringUtils.toArrayList(tmpValue, ","));
    }

    //required
    tmpValue = ((Element) n).getAttribute("required");
    if (!"".equals(StringUtils.toString(tmpValue))) {
      this.setRequired(tmpValue);
    }

    //lookup
    tmpValue = ((Element) n).getAttribute("lookup_table");
    if (!"".equals(StringUtils.toString(tmpValue))) {
      this.setLookupTable(tmpValue);
    }

    //multiple
    tmpValue = ((Element) n).getAttribute("multiple");
    if (!"".equals(StringUtils.toString(tmpValue))) {
      this.setMultiple(tmpValue);
      tmpValue = ((Element) n).getAttribute("delimiter");
      if (!"".equals(StringUtils.toString(tmpValue))) {
        this.setDelimiter(tmpValue);
      }
    }

    //display name
    tmpValue = ((Element) n).getAttribute("displayName");
    if (!"".equals(StringUtils.toString(tmpValue))) {
      this.setDisplayName(tmpValue);
    }

    //hidden
    tmpValue = ((Element) n).getAttribute("isForPrompting");
    if (!"".equals(StringUtils.toString(tmpValue))) {
      this.setIsForPrompting(tmpValue);
    }

    //substitute properties, if any
    tmpValue = ((Element) n).getAttribute("substitute");
    if (!"".equals(StringUtils.toString(tmpValue))) {
      this.setSubstitute(tmpValue);
    }
  }


  /**
   *  Sets the name attribute of the Property object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the aliases attribute of the Property object
   *
   *@param  tmp  The new aliases value
   */
  public void setAliases(ArrayList tmp) {
    this.aliases = tmp;
  }


  /**
   *  Sets the defaultValue attribute of the Property object
   *
   *@param  tmp  The new defaultValue value
   */
  public void setDefaultValue(String tmp) {
    this.defaultValue = tmp;
  }


  /**
   *  Sets the required attribute of the Property object
   *
   *@param  tmp  The new required value
   */
  public void setRequired(boolean tmp) {
    this.required = tmp;
  }


  /**
   *  Sets the required attribute of the Property object
   *
   *@param  tmp  The new required value
   */
  public void setRequired(String tmp) {
    this.required = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the multiple attribute of the Property object
   *
   *@param  tmp  The new multiple value
   */
  public void setMultiple(boolean tmp) {
    this.multiple = tmp;
  }


  /**
   *  Sets the multiple attribute of the Property object
   *
   *@param  tmp  The new multiple value
   */
  public void setMultiple(String tmp) {
    this.multiple = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the mappedColumn attribute of the Property object
   *
   *@param  tmp  The new mappedColumn value
   */
  public void setMappedColumn(int tmp) {
    this.mappedColumn = tmp;
  }


  /**
   *  Sets the mappedColumn attribute of the Property object
   *
   *@param  tmp  The new mappedColumn value
   */
  public void setMappedColumn(String tmp) {
    this.mappedColumn = Integer.parseInt(tmp);
  }


  /**
   *  Sets the lookupTable attribute of the Property object
   *
   *@param  tmp  The new lookupTable value
   */
  public void setLookupTable(String tmp) {
    this.lookupTable = tmp;
  }


  /**
   *  Sets the delimiter attribute of the Property object
   *
   *@param  tmp  The new delimiter value
   */
  public void setDelimiter(String tmp) {
    this.delimiter = tmp;
  }


  /**
   *  Sets the displayName attribute of the Property object
   *
   *@param  tmp  The new displayName value
   */
  public void setDisplayName(String tmp) {
    this.displayName = tmp;
  }


  /**
   *  Sets the uniqueName attribute of the Property object
   *
   *@param  tmp  The new uniqueName value
   */
  public void setUniqueName(String tmp) {
    this.uniqueName = tmp;
  }


  /**
   *  Sets the isForPrompting attribute of the Property object
   *
   *@param  tmp  The new isForPrompting value
   */
  public void setIsForPrompting(boolean tmp) {
    this.isForPrompting = tmp;
  }


  /**
   *  Sets the isForPrompting attribute of the Property object
   *
   *@param  tmp  The new isForPrompting value
   */
  public void setIsForPrompting(String tmp) {
    this.isForPrompting = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the groupId attribute of the Property object
   *
   *@param  tmp  The new groupId value
   */
  public void setGroupId(int tmp) {
    this.groupId = tmp;
  }


  /**
   *  Sets the groupId attribute of the Property object
   *
   *@param  tmp  The new groupId value
   */
  public void setGroupId(String tmp) {
    this.groupId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the substitute attribute of the Property object
   *
   *@param  tmp  The new substitute value
   */
  public void setSubstitute(String tmp) {
    this.substitute = tmp;
  }


  /**
   *  Gets the substitute attribute of the Property object
   *
   *@return    The substitute value
   */
  public String getSubstitute() {
    return substitute;
  }


  /**
   *  Gets the groupId attribute of the Property object
   *
   *@return    The groupId value
   */
  public int getGroupId() {
    return groupId;
  }


  /**
   *  Gets the isForPrompting attribute of the Property object
   *
   *@return    The isForPrompting value
   */
  public boolean getIsForPrompting() {
    return isForPrompting;
  }


  /**
   *  Gets the uniqueName attribute of the Property object
   *
   *@return    The uniqueName value
   */
  public String getUniqueName() {
    return uniqueName;
  }


  /**
   *  Gets the dependency name of this property<br>
   *  NOTE: Returns NULL if this is not a dependency property
   *
   *@return    The dependencyName value
   */
  public String getDependencyName() {
    if ("".equals(StringUtils.toString(uniqueName)) || uniqueName.indexOf(".") == -1) {
      return null;
    }

    StringTokenizer tokens = new StringTokenizer(uniqueName
        , ".");
    String tmp = tokens.nextToken();
    if (tokens.hasMoreTokens()) {
      return tmp;
    }
    return null;
  }


  /**
   *  Gets the displayName attribute of the Property object
   *
   *@return    The displayName value
   */
  public String getDisplayName() {
    return displayName;
  }


  /**
   *  Gets the delimiter attribute of the Property object
   *
   *@return    The delimiter value
   */
  public String getDelimiter() {
    return delimiter;
  }


  /**
   *  Gets the name attribute of the Property object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the aliases attribute of the Property object
   *
   *@return    The aliases value
   */
  public ArrayList getAliases() {
    return aliases;
  }


  /**
   *  Gets the defaultValue attribute of the Property object
   *
   *@return    The defaultValue value
   */
  public String getDefaultValue() {
    return defaultValue;
  }


  /**
   *  Gets the required attribute of the Property object
   *
   *@return    The required value
   */
  public boolean getRequired() {
    return required;
  }


  /**
   *  Gets the multiple attribute of the Property object
   *
   *@return    The multiple value
   */
  public boolean getMultiple() {
    return multiple;
  }


  /**
   *  Gets the mappedColumn attribute of the Property object
   *
   *@return    The mappedColumn value
   */
  public int getMappedColumn() {
    return mappedColumn;
  }


  /**
   *  Gets the lookupTable attribute of the Property object
   *
   *@return    The lookupTable value
   */
  public String getLookupTable() {
    return lookupTable;
  }


  /**
   *  Gets the valid attribute of the Property object
   *
   *@return    The valid value
   */
  public boolean isValid() {
    if ("".equals(StringUtils.toString(name))) {
      return false;
    }
    return true;
  }


  /**
   *  Creates a clone of this property
   *
   *@return                                 Description of the Return Value
   *@exception  CloneNotSupportedException  Description of the Exception
   */
  public Property duplicate() throws CloneNotSupportedException {
    return (Property) this.clone();
  }


  /**
   *  Checks to see if an alias exists for the parameter passed
   *
   *@param  tmp  Description of the Parameter
   *@return      Description of the Return Value
   */
  public boolean hasAlias(String tmp) {
    if (aliases == null || tmp == null) {
      return false;
    }
    boolean found = false;
    String field = tmp;

    Iterator i = aliases.iterator();
    while (i.hasNext() && !found) {
      String alias = (String) i.next();
      if (alias.equals(tmp.toLowerCase())) {
        found = true;
      }
    }
    return found;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public String toString() {
    StringBuffer info = new StringBuffer();
    info.append("======================================\n");
    info.append("Name: " + this.getName() + "\n");
    info.append("Aliases: " + this.getAliases() + "\n");
    info.append("Required: " + this.getRequired() + "\n");
    info.append("MappedColumn: " + this.getMappedColumn() + "\n");
    info.append("======================================\n");
    return info.toString();
  }
}

