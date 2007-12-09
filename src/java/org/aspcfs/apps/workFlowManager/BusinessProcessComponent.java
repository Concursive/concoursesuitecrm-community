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
package org.aspcfs.apps.workFlowManager;

import org.aspcfs.modules.base.Constants;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.XMLUtils;
import org.w3c.dom.Element;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A BusinessProcessComponent is a definition of where and when a class will be
 * executed within a BusinessProcess hierarchy.
 *
 * @author matt rajkowski
 * @version $Id: BusinessProcessComponent.java,v 1.3 2003/01/13 21:41:16
 *          mrajkowski Exp $
 * @created November 11, 2002
 */
public class BusinessProcessComponent {

  //properties mapped to database
  private int id = -1;
  private int parentId = 0;
  private int parentResult = -1;
  private int processId = -1;
  private int componentId = -1;
  private boolean enabled = true;
  //references
  private int processType = -1;
  private ComponentParameterList parameters = null;
  private BusinessProcessComponent parent = null;
  private HashMap children = null;
  private String name = null;
  private String className = null;
  private String description = null;


  /**
   * Constructor for the BusinessProcessComponent object
   */
  public BusinessProcessComponent() {
  }


  /**
   * Constructor for the BusinessProcessComponent object
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public BusinessProcessComponent(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the BusinessProcessComponent object
   *
   * @param componentElement Description of the Parameter
   */
  public BusinessProcessComponent(Element componentElement) {
    //Set the base data
    this.setId((String) componentElement.getAttribute("id"));
    this.setParentId((String) componentElement.getAttribute("parent"));
    this.setParentResult((String) componentElement.getAttribute("if"));
    //this.setName((String) componentElement.getAttribute("name"));
    this.setClassName((String) componentElement.getAttribute("class"));
    this.setName(className);
    this.setDescription((String) componentElement.getAttribute("description"));
    this.setEnabled((String) componentElement.getAttribute("enabled"));
    //Add the parameters
    Element parameters = XMLUtils.getFirstElement(
        componentElement, "parameters");
    if (parameters != null) {
      ArrayList parameterNodes = XMLUtils.getElements(parameters, "parameter");
      Iterator parameterElements = parameterNodes.iterator();
      while (parameterElements.hasNext()) {
        Element parameterElement = (Element) parameterElements.next();
        String parameterEnabled = (String) parameterElement.getAttribute(
            "enabled");
        ComponentParameter thisParameter = new ComponentParameter(
            parameterElement);
        if (this.parameters == null) {
          this.parameters = new ComponentParameterList();
        }
        this.parameters.add(thisParameter);
      }
    }
  }


  /**
   * Sets the id attribute of the BusinessProcessComponent object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the BusinessProcessComponent object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = DatabaseUtils.parseInt(tmp, -1);
  }


  /**
   * Sets the parentId attribute of the BusinessProcessComponent object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(int tmp) {
    this.parentId = tmp;
  }


  /**
   * Sets the parentId attribute of the BusinessProcessComponent object
   *
   * @param tmp The new parentId value
   */
  public void setParentId(String tmp) {
    this.parentId = DatabaseUtils.parseInt(tmp, 0);
  }


  /**
   * Sets the parentResult attribute of the BusinessProcessComponent object
   *
   * @param tmp The new parentResult value
   */
  public void setParentResult(int tmp) {
    this.parentResult = tmp;
  }


  /**
   * Sets the parentResult attribute of the BusinessProcessComponent object
   *
   * @param tmp The new parentResult value
   */
  public void setParentResult(String tmp) {
    if ("false".equals(tmp)) {
      this.parentResult = Constants.FALSE;
    } else if ("true".equals(tmp)) {
      this.parentResult = Constants.TRUE;
    } else {
      this.parentResult = -1;
    }
  }


  /**
   * Sets the processId attribute of the BusinessProcessComponent object
   *
   * @param tmp The new processId value
   */
  public void setProcessId(int tmp) {
    this.processId = tmp;
  }


  /**
   * Sets the processId attribute of the BusinessProcessComponent object
   *
   * @param tmp The new processId value
   */
  public void setProcessId(String tmp) {
    this.processId = Integer.parseInt(tmp);
  }


  /**
   * Sets the componentId attribute of the BusinessProcessComponent object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(int tmp) {
    this.componentId = tmp;
  }


  /**
   * Sets the processType attribute of the BusinessProcessComponent object
   *
   * @param tmp The new processType value
   */
  public void setProcessType(int tmp) {
    this.processType = tmp;
  }


  /**
   * Sets the processType attribute of the BusinessProcessComponent object
   *
   * @param tmp The new processType value
   */
  public void setProcessType(String tmp) {
    this.processType = Integer.parseInt(tmp);
  }


  /**
   * Sets the componentId attribute of the BusinessProcessComponent object
   *
   * @param tmp The new componentId value
   */
  public void setComponentId(String tmp) {
    this.componentId = Integer.parseInt(tmp);
  }


  /**
   * Sets the name attribute of the BusinessProcessComponent object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the className attribute of the BusinessProcessComponent object
   *
   * @param tmp The new className value
   */
  public void setClassName(String tmp) {
    this.className = tmp;
  }


  /**
   * Sets the description attribute of the BusinessProcessComponent object
   *
   * @param tmp The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   * Sets the enabled attribute of the BusinessProcessComponent object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   * Sets the enabled attribute of the BusinessProcessComponent object
   *
   * @param tmp The new enabled value
   */
  public void setEnabled(String tmp) {
    this.enabled = (DatabaseUtils.parseBoolean(tmp) || tmp == null || "".equals(
        tmp));
  }


  /**
   * Sets the parameters attribute of the BusinessProcessComponent object
   *
   * @param tmp The new parameters value
   */
  public void setParameters(ComponentParameterList tmp) {
    this.parameters = tmp;
  }


  /**
   * Sets the parent attribute of the BusinessProcessComponent object
   *
   * @param tmp The new parent value
   */
  public void setParent(BusinessProcessComponent tmp) {
    this.parent = tmp;
  }


  /**
   * Sets the children attribute of the BusinessProcessComponent object
   *
   * @param tmp The new children value
   */
  public void setChildren(HashMap tmp) {
    this.children = tmp;
  }


  /**
   * Gets the id attribute of the BusinessProcessComponent object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the parentId attribute of the BusinessProcessComponent object
   *
   * @return The parentId value
   */
  public int getParentId() {
    return parentId;
  }


  /**
   * Gets the parentResult attribute of the BusinessProcessComponent object
   *
   * @return The parentResult value
   */
  public int getParentResult() {
    return parentResult;
  }


  /**
   * Gets the processId attribute of the BusinessProcessComponent object
   *
   * @return The processId value
   */
  public int getProcessId() {
    return processId;
  }


  /**
   * Gets the componentId attribute of the BusinessProcessComponent object
   *
   * @return The componentId value
   */
  public int getComponentId() {
    return componentId;
  }


  /**
   * Gets the processType attribute of the BusinessProcessComponent object
   *
   * @return The processType value
   */
  public int getProcessType() {
    return processType;
  }


  /**
   * Gets the name attribute of the BusinessProcessComponent object
   *
   * @return The name value
   */
  public String getName() {
    return name;
  }


  /**
   * Gets the className attribute of the BusinessProcessComponent object
   *
   * @return The className value
   */
  public String getClassName() {
    return className;
  }


  /**
   * Gets the description attribute of the BusinessProcessComponent object
   *
   * @return The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the enabled attribute of the BusinessProcessComponent object
   *
   * @return The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   * Gets the parameters attribute of the BusinessProcessComponent object
   *
   * @return The parameters value
   */
  public ComponentParameterList getParameters() {
    return parameters;
  }


  /**
   * Gets the parent attribute of the BusinessProcessComponent object
   *
   * @return The parent value
   */
  public BusinessProcessComponent getParent() {
    return parent;
  }


  /**
   * Gets the children attribute of the BusinessProcessComponent object
   *
   * @return The children value
   */
  public HashMap getChildren() {
    if (children == null) {
      children = new HashMap();
    }
    return children;
  }


  /**
   * Gets the children attribute of the BusinessProcessComponent object
   *
   * @param result Description of the Parameter
   * @return The children value
   */
  public ArrayList getChildren(boolean result) {
    //Return the components that don't have a resultType and return the correct result ones
    ArrayList listToReturn = new ArrayList();
    if (children != null) {
      ArrayList indifferent = (ArrayList) children.get(new Integer(-1));
      if (indifferent != null) {
        listToReturn.addAll(indifferent);
      }
      ArrayList matching = (ArrayList) children.get(
          new Integer(result == true ? Constants.TRUE : Constants.FALSE));
      if (matching != null) {
        listToReturn.addAll(matching);
      }
    }
    return listToReturn;
  }


  /**
   * Gets the allChildren attribute of the BusinessProcessComponent object
   *
   * @return The allChildren value
   */
  public ArrayList getAllChildren() {
    ArrayList listToReturn = new ArrayList();
    if (children != null) {
      Iterator i = children.values().iterator();
      while (i.hasNext()) {
        ArrayList resultChildren = (ArrayList) i.next();
        listToReturn.addAll(resultChildren);
      }
    }
    return listToReturn;
  }


  /**
   * Returns whether this component has any associated parameters
   *
   * @return Description of the Return Value
   */
  public boolean hasParameters() {
    return (parameters != null && parameters.size() > 0);
  }


  /**
   * Adds a feature to the Child attribute of the BusinessProcessComponent
   * object
   *
   * @param childComponent The feature to be added to the Child attribute
   */
  public void addChild(BusinessProcessComponent childComponent) {
    if (children == null) {
      children = new HashMap();
    }
    ArrayList results = (ArrayList) children.get(
        new Integer(childComponent.getParentResult()));
    if (results == null) {
      results = new ArrayList();
      children.put(new Integer(childComponent.getParentResult()), results);
    }
    results.add(childComponent);
  }


  /**
   * Populates this object from a database recordset
   *
   * @param rs Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    //business_process_component
    id = rs.getInt("id");
    processId = rs.getInt("process_id");
    componentId = rs.getInt("component_id");
    parentId = DatabaseUtils.getInt(rs, "parent_id", 0);
    parentResult = DatabaseUtils.getInt(rs, "parent_result_id");
    enabled = rs.getBoolean("enabled");
    //business_process_component_library
    name = rs.getString("component_name");
    className = rs.getString("class_name");
    description = rs.getString("description");
  }


  /**
   * Builds related data for this component
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  protected void buildResources(Connection db) throws SQLException {
    //Add local parameters to the component
    parameters = new ComponentParameterList();
    parameters.setComponentId(id);
    parameters.buildList(db);
  }


  /**
   * Inserts this business process component along with any related data. A
   * business process must exist in the library first, so that is seamlessly
   * verified and inserted if necessary.
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    PreparedStatement pst;
    //Insert the related component library item
    if (componentId == -1) {
      try {
        //Access the component object for its description
        Object classRef = Class.forName(className).newInstance();
        description = ObjectUtils.getParam(classRef, "description");
        classRef = null;
      } catch (Exception classEx) {
        //Use the class name when the class does not exist... this might be
        //a prototype class
        description = className;
      }
      LibraryComponent libraryComponent = new LibraryComponent();
      libraryComponent.setName(name);
      libraryComponent.setTypeId(processType);
      libraryComponent.setClassName(className);
      libraryComponent.setDescription(description);
      libraryComponent.setEnabled(true);
      libraryComponent.insert(db);
      componentId = libraryComponent.getId();
    }
    boolean doInsert = false;
    //Insert a result type for the parent
    if (parent != null) {
      if (parentResult > -1) {
        //Store the parent's result id in the database if it's not already there
        ComponentResult result = new ComponentResult();
        result.setComponentId(parent.getComponentId());
        result.setReturnId(parentResult);
        //NOTE: This will change once parent result is no longer boolean
        result.setDescription(parentResult == Constants.TRUE ? "Yes" : "No");
        //NOTE: This will change once parent result is no longer boolean
        result.setReturnId(parentResult == Constants.TRUE ? 1 : 0);
        result.setLevel(parentResult == Constants.TRUE ? 0 : 1);
        result.setEnabled(true);
        result.insert(db);
        doInsert = true;
      } else {
        //Some children are not triggered by a parentResult and are always executed,
        //these need to be checked before inserting a duplicate component
        pst = db.prepareStatement(
            "SELECT id " +
            "FROM business_process_component " +
            "WHERE process_id = ? " +
            "AND component_id = ? " +
            "AND parent_id = ? " +
            "AND parent_result_id IS NULL ");
        pst.setInt(1, processId);
        pst.setInt(2, componentId);
        pst.setInt(3, parent.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
          id = rs.getInt("id");
          doInsert = false;
        } else {
          doInsert = true;
        }
        rs.close();
        pst.close();
      }
    } else {
      doInsert = true;
    }
    if (doInsert) {
      //Insert the component
      id = DatabaseUtils.getNextSeq(db, "business_process_compone_id_seq");
      pst = db.prepareStatement(
          "INSERT INTO business_process_component " +
          "(" + (id > -1 ? "id, " : "") + "process_id, component_id, parent_id, parent_result_id, enabled) " +
          "VALUES " +
          "(" + (id > -1 ? "?, " : "") + "?, ?, ?, ?, ?)");
      int i = 0;
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, processId);
      pst.setInt(++i, componentId);
      if (parent != null) {
        pst.setInt(++i, parent.getId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      DatabaseUtils.setInt(pst, ++i, parentResult);
      pst.setBoolean(++i, enabled);
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "business_process_compone_id_seq", id);
      //Insert component parameters
      if (parameters != null) {
        parameters.setComponentId(id);
        parameters.insert(db);
      }
    }
  }


  /**
   * Deletes this object into a database
   *
   * @param db Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void delete(Connection db) throws SQLException {

    //delete component paramters
    parameters = new ComponentParameterList();
    parameters.setComponentId(this.id);
    parameters.buildList(db);
    parameters.delete(db);
    System.out.println(" Deleting parameter list ");

    PreparedStatement pst = db.prepareStatement(
        "DELETE FROM business_process_component " +
        "WHERE id = ? ");

    pst.setInt(1, this.id);
    pst.execute();

    pst.close();
  }


  /**
   * Outputs the object for debugging
   *
   * @return Description of the Return Value
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(
        "=[Business Process Component]================================\r\n");
    sb.append(" id: " + id + "\r\n");
    sb.append(" parentId: " + parentId + "\r\n");
    sb.append(" parentResult: " + parentResult + "\r\n");
    sb.append(" processId: " + processId + "\r\n");
    sb.append(" componentId: " + componentId + "\r\n");
    sb.append(" enabled: " + enabled + "\r\n");
    sb.append(" processType: " + processType + "\r\n");
    sb.append(" name: " + name + "\r\n");
    sb.append(" className: " + className + "\r\n");
    sb.append(" description: " + description + "\r\n");
    if (children != null) {
      sb.append(" children:");
      Iterator i = children.keySet().iterator();
      while (i.hasNext()) {
        BusinessProcessComponent child = (BusinessProcessComponent) i.next();
        sb.append(" " + child.getId());
      }
      sb.append("\r\n");
    }
    return sb.toString();
  }
}

