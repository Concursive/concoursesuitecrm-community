//Copyright 2002 Dark Horse Ventures
package org.aspcfs.apps.workFlowManager;

import java.util.*;
import org.w3c.dom.Element;
import java.sql.*;
import org.aspcfs.utils.*;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.admin.base.PermissionCategory;

/**
 *  Contains a mapping of BusinessComponent objects that can be executed in a
 *  hierarchical order by the WorkflowManager
 *
 *@author     matt rajkowski
 *@created    November 11, 2002
 *@version    $Id: BusinessProcess.java,v 1.3 2003/01/13 21:41:16 mrajkowski Exp
 *      $
 */
public class BusinessProcess {
  //Business process types
  public final static int OBJECT_EVENT = 1;
  public final static int SCHEDULED_EVENT = 2;
  //Business process properties
  private int id = -1;
  private String name = null;
  private String description = null;
  private int type = OBJECT_EVENT;
  private int linkModuleId = -1;
  private String linkModule = null;
  private int startId = -1;
  private java.sql.Timestamp entered = null;
  private java.sql.Timestamp modified = null;
  private boolean enabled = false;
  private BusinessProcessComponentList components = null;
  private ProcessParameterList parameters = null;
  private ScheduledEventList events = null;
  //Build resources
  private boolean buildScheduledEvents = false;


  /**
   *  Constructor for the BusinessProcess object
   */
  public BusinessProcess() { }


  /**
   *  Constructor for the BusinessProcess object
   *
   *@param  db                Description of the Parameter
   *@param  processId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public BusinessProcess(Connection db, int processId) throws SQLException {
    queryRecord(db, processId);
  }


  /**
   *  Constructor for the BusinessProcess object
   *
   *@param  processElement  Description of the Parameter
   */
  public BusinessProcess(Element processElement) {
    //Set the base data
    this.setName((String) processElement.getAttribute("name"));
    this.setDescription((String) processElement.getAttribute("description"));
    this.setType((String) processElement.getAttribute("type"));
    linkModule = ((String) processElement.getAttribute("module"));
    this.setStartId((String) processElement.getAttribute("startId"));
    this.setEntered((String) processElement.getAttribute("entered"));
    this.setModified((String) processElement.getAttribute("modified"));
    this.setEnabled((String) processElement.getAttribute("enabled"));
    //Add all the enabled components
    components = new BusinessProcessComponentList();
    Element componentElements = XMLUtils.getFirstElement(processElement, "components");
    if (componentElements != null) {
      ArrayList componentNodes = XMLUtils.getElements(componentElements, "component");
      Iterator componentElementIterator = componentNodes.iterator();
      while (componentElementIterator.hasNext()) {
        Element componentElement = (Element) componentElementIterator.next();
        BusinessProcessComponent thisComponent = new BusinessProcessComponent(componentElement);
        components.put(new Integer(thisComponent.getId()), thisComponent);
      }
      //Link the components together
      linkComponents();
    }
    //Add the global parameters for this process
    Element parameters = XMLUtils.getFirstElement(processElement, "parameters");
    if (parameters != null) {
      ArrayList parameterNodes = XMLUtils.getElements(parameters, "parameter");
      Iterator parameterElements = parameterNodes.iterator();
      while (parameterElements.hasNext()) {
        Element parameterElement = (Element) parameterElements.next();
        String parameterEnabled = (String) parameterElement.getAttribute("enabled");
        ProcessParameter thisParameter = new ProcessParameter(parameterElement);
        if (this.parameters == null) {
          this.parameters = new ProcessParameterList();
        }
        this.parameters.add(thisParameter);
      }
    }
  }


  /**
   *  Constructor for the BusinessProcess object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public BusinessProcess(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Sets the id attribute of the BusinessProcess object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the BusinessProcess object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the name attribute of the BusinessProcess object
   *
   *@param  tmp  The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   *  Sets the description attribute of the BusinessProcess object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }


  /**
   *  Sets the type attribute of the BusinessProcess object
   *
   *@param  tmp  The new type value
   */
  public void setType(int tmp) {
    this.type = tmp;
  }


  /**
   *  Sets the type attribute of the BusinessProcess object
   *
   *@param  tmp  The new type value
   */
  public void setType(String tmp) {
    if (tmp == null) {
      type = OBJECT_EVENT;
    } else {
      if ("OBJECT_EVENT".equals(tmp)) {
        this.type = OBJECT_EVENT;
      } else if ("SCHEDULED_EVENT".equals(tmp)) {
        this.type = SCHEDULED_EVENT;
      } else {
        this.type = Integer.parseInt(tmp);
      }
    }
  }


  /**
   *  Sets the linkModuleId attribute of the BusinessProcess object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   *  Sets the linkModuleId attribute of the BusinessProcess object
   *
   *@param  tmp  The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.linkModuleId = Integer.parseInt(tmp);
  }


  /**
   *  Sets the startId attribute of the BusinessProcess object
   *
   *@param  tmp  The new startId value
   */
  public void setStartId(int tmp) {
    this.startId = tmp;
  }


  /**
   *  Sets the startId attribute of the BusinessProcess object
   *
   *@param  tmp  The new startId value
   */
  public void setStartId(String tmp) {
    this.startId = DatabaseUtils.parseInt(tmp, -1);
  }


  /**
   *  Sets the entered attribute of the BusinessProcess object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(java.sql.Timestamp tmp) {
    this.entered = tmp;
  }


  /**
   *  Sets the entered attribute of the BusinessProcess object
   *
   *@param  tmp  The new entered value
   */
  public void setEntered(String tmp) {
    this.entered = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the modified attribute of the BusinessProcess object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(java.sql.Timestamp tmp) {
    this.modified = tmp;
  }


  /**
   *  Sets the modified attribute of the BusinessProcess object
   *
   *@param  tmp  The new modified value
   */
  public void setModified(String tmp) {
    this.modified = DatabaseUtils.parseTimestamp(tmp);
  }


  /**
   *  Sets the enabled attribute of the BusinessProcess object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(boolean tmp) {
    this.enabled = tmp;
  }


  /**
   *  Sets the enabled attribute of the BusinessProcess object
   *
   *@param  tmp  The new enabled value
   */
  public void setEnabled(String tmp) {
    //Assume the enabled field is true by default
    enabled = !("OFF".equalsIgnoreCase(tmp) ||
        "FALSE".equalsIgnoreCase(tmp) ||
        "0".equals(tmp));
  }


  /**
   *  Sets the components attribute of the BusinessProcess object
   *
   *@param  tmp  The new components value
   */
  public void setComponents(BusinessProcessComponentList tmp) {
    this.components = tmp;
  }


  /**
   *  Sets the parameters attribute of the BusinessProcess object
   *
   *@param  tmp  The new parameters value
   */
  public void setParameters(ProcessParameterList tmp) {
    this.parameters = tmp;
  }


  /**
   *  Sets the events attribute of the BusinessProcess object
   *
   *@param  tmp  The new events value
   */
  public void setEvents(ScheduledEventList tmp) {
    this.events = tmp;
  }


  /**
   *  Sets the buildScheduledEvents attribute of the BusinessProcess object
   *
   *@param  tmp  The new buildScheduledEvents value
   */
  public void setBuildScheduledEvents(boolean tmp) {
    this.buildScheduledEvents = tmp;
  }


  /**
   *  Sets the buildScheduledEvents attribute of the BusinessProcess object
   *
   *@param  tmp  The new buildScheduledEvents value
   */
  public void setBuildScheduledEvents(String tmp) {
    this.buildScheduledEvents = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the id attribute of the BusinessProcess object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the name attribute of the BusinessProcess object
   *
   *@return    The name value
   */
  public String getName() {
    return name;
  }


  /**
   *  Gets the description attribute of the BusinessProcess object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the type attribute of the BusinessProcess object
   *
   *@return    The type value
   */
  public int getType() {
    return type;
  }


  /**
   *  Gets the linkModuleId attribute of the BusinessProcess object
   *
   *@return    The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }


  /**
   *  Gets the startId attribute of the BusinessProcess object
   *
   *@return    The startId value
   */
  public int getStartId() {
    return startId;
  }


  /**
   *  Gets the entered attribute of the BusinessProcess object
   *
   *@return    The entered value
   */
  public java.sql.Timestamp getEntered() {
    return entered;
  }


  /**
   *  Gets the modified attribute of the BusinessProcess object
   *
   *@return    The modified value
   */
  public java.sql.Timestamp getModified() {
    return modified;
  }


  /**
   *  Gets the enabled attribute of the BusinessProcess object
   *
   *@return    The enabled value
   */
  public boolean getEnabled() {
    return enabled;
  }


  /**
   *  Gets the components attribute of the BusinessProcess object
   *
   *@return    The components value
   */
  public BusinessProcessComponentList getComponents() {
    return components;
  }


  /**
   *  Gets the parameters attribute of the BusinessProcess object
   *
   *@return    The parameters value
   */
  public ProcessParameterList getParameters() {
    return parameters;
  }


  /**
   *  Gets the events attribute of the BusinessProcess object
   *
   *@return    The events value
   */
  public ScheduledEventList getEvents() {
    return events;
  }


  /**
   *  Gets the buildScheduledEvents attribute of the BusinessProcess object
   *
   *@return    The buildScheduledEvents value
   */
  public boolean getBuildScheduledEvents() {
    return buildScheduledEvents;
  }


  /**
   *  Returns if this business process has any parameters
   *
   *@return    Description of the Return Value
   */
  public boolean hasParameters() {
    return (parameters != null && parameters.size() > 0);
  }


  /**
   *  Gets the component attribute of the BusinessProcess object
   *
   *@param  componentId  Description of the Parameter
   *@return              The component value
   */
  public BusinessProcessComponent getComponent(int componentId) {
    return (BusinessProcessComponent) components.get(new Integer(componentId));
  }


  /**
   *  Creates references between parent components and childern components to
   *  optimize processing
   */
  private void linkComponents() {
    Iterator cList = components.values().iterator();
    while (cList.hasNext()) {
      BusinessProcessComponent thisComponent = (BusinessProcessComponent) cList.next();
      thisComponent.setProcessType(type);
      if (thisComponent.getParentId() > 0) {
        //Locate the parent
        BusinessProcessComponent parent = (BusinessProcessComponent) components.get(new Integer(thisComponent.getParentId()));
        if (parent != null) {
          //The parent will execute this child if the child accepts true, false, or neither
          parent.addChild(thisComponent);
          thisComponent.setParent(parent);
        }
      }
    }
  }


  /**
   *  Loads a single business process from the database
   *
   *@param  db                Description of the Parameter
   *@param  processId         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void queryRecord(Connection db, int processId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT * " +
        "FROM business_process " +
        "WHERE process_id = ? ");
    pst.setInt(1, processId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
  }


  /**
   *  Populates this object from a database record set
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    id = rs.getInt("process_id");
    name = rs.getString("process_name");
    description = rs.getString("description");
    type = rs.getInt("type_id");
    linkModuleId = rs.getInt("link_module_id");
    startId = rs.getInt("component_start_id");
    enabled = rs.getBoolean("enabled");
  }


  /**
   *  Builds all related information for this process
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildResources(Connection db) throws SQLException {
    //Add components to the process
    components = new BusinessProcessComponentList();
    components.setProcessId(id);
    components.buildList(db);
    //Link the components together
    linkComponents();
    //Add global parameters to the process
    parameters = new ProcessParameterList();
    parameters.setProcessId(id);
    parameters.buildList(db);
    if (buildScheduledEvents) {
      events = new ScheduledEventList();
      events.setBusinessProcessId(id);
      events.buildList(db);
    }
  }


  /**
   *  Inserts this process and any related information into a database
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void insert(Connection db) throws SQLException {
    boolean autoCommit = db.getAutoCommit();
    try {
      if (autoCommit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst;
      pst = db.prepareStatement(
          "SELECT process_id " +
          "FROM business_process " +
          "WHERE process_name = ? ");
      pst.setString(1, name);
      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        id = rs.getInt("process_id");
      }
      rs.close();
      pst.close();
      if (linkModuleId == -1 && linkModule != null && !linkModule.equals("")) {
        linkModuleId = PermissionCategory.lookupId(db, linkModule);
      }
      if (id == -1) {
        pst = db.prepareStatement(
            "INSERT INTO business_process " +
            "(process_name, description, type_id, link_module_id, " +
            "component_start_id, enabled) VALUES " +
            "(?, ?, ?, ?, ?, ?)");
        int i = 0;
        pst.setString(++i, name);
        pst.setString(++i, description);
        pst.setInt(++i, type);
        pst.setInt(++i, linkModuleId);
        DatabaseUtils.setInt(pst, ++i, -1);
        pst.setBoolean(++i, enabled);
        pst.execute();
        pst.close();
        id = DatabaseUtils.getCurrVal(db, "business_process_process_id_seq");
        //Insert the parameters
        parameters.setProcessId(id);
        parameters.insert(db);
        //Insert the components, must be done using tree
        BusinessProcessComponent startComponent = (BusinessProcessComponent) components.get(new Integer(startId));
        startComponent.setProcessId(id);
        startComponent.insert(db);
        insertChildren(db, startComponent);
        //Update the startId with the newly inserted value
        pst = db.prepareStatement(
            "UPDATE business_process " +
            "SET component_start_id = ? " +
            "WHERE process_id = ? ");
        pst.setInt(1, startComponent.getId());
        pst.setInt(2, id);
        pst.executeUpdate();
        pst.close();
      }
      if (autoCommit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (autoCommit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (autoCommit) {
        db.setAutoCommit(true);
      }
    }
  }


  /**
   *  Helper method to iterate through the children components for inserting
   *
   *@param  db                Description of the Parameter
   *@param  component         Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  private void insertChildren(Connection db, BusinessProcessComponent component) throws SQLException {
    if (component.getChildren() != null) {
      Iterator children = component.getAllChildren().iterator();
      while (children.hasNext()) {
        BusinessProcessComponent child = (BusinessProcessComponent) children.next();
        child.setProcessId(id);
        //The parent id has changed after insert, so update the child
        child.setParentId(component.getId());
        child.insert(db);
        insertChildren(db, child);
      }
    }
  }
}

