package org.aspcfs.apps.help;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.database.*;
import org.aspcfs.utils.DatabaseUtils;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *  Description of the Class
 *
 *@author     kbhoopal
 *@created    November 25, 2003
 *@version    $Id$
 */
public class HelpContent {

  private int id = -1;
  //corresponds to the helpid in the database
  private String action = null;
  private String section = null;
  private String sub = null;
  private String title = null;
  private String description = null;

  private int tocId = -1;
  private int parent = -1;
  private int contentLevel = -1;
  private int contentOrder = -1;

  private ArrayList features;
  private ArrayList rules;
  private ArrayList notes;
  private ArrayList tips;

  private boolean insert = false;


  /**
   *  Constructor for the HelpContent object
   */
  public HelpContent() {

    features = new ArrayList();
    rules = new ArrayList();
    notes = new ArrayList();
    tips = new ArrayList();

  }


  /**
   *  Constructor for the HelpContent object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public HelpContent(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   *  Constructor for the buildRecord object
   *
   *@param  rs                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildRecord(ResultSet rs) throws SQLException {
    // from  help_contents table
    id = rs.getInt("help_id");
    action = rs.getString("module");
    section = rs.getString("section");
    sub = rs.getString("subsection");
    title = rs.getString("title");
    description = rs.getString("description");
    
    //from help_tableof_contents table
    tocId = rs.getInt("content_id");
    parent = rs.getInt("parent");
    contentLevel = rs.getInt("contentlevel");
    contentOrder = rs.getInt("contentorder");
  }


  /**
   *  Sets the id attribute of the HelpContent object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the HelpContent object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the action attribute of the HelpContent object
   *
   *@param  tmp  The new action value
   */
  public void setAction(String tmp) {
    this.action = tmp;
  }


  /**
   *  Sets the section attribute of the HelpContent object
   *
   *@param  tmp  The new section value
   */
  public void setSection(String tmp) {
    this.section = tmp;
  }


  /**
   *  Sets the sub attribute of the HelpContent object
   *
   *@param  tmp  The new sub value
   */
  public void setSub(String tmp) {
    this.sub = tmp;
  }


  /**
   *  Sets the title attribute of the HelpContent object
   *
   *@param  tmp  The new title value
   */
  public void setTitle(String tmp) {
    this.title = tmp;
  }


  /**
   *  Sets the description attribute of the HelpContent object
   *
   *@param  tmp  The new description value
   */
  public void setDescription(String tmp) {
    this.description = tmp;
  }



  /**
   *  Sets the tocId attribute of the HelpContent object
   *
   *@param  tmp  The new tocId value
   */
  public void setTocId(int tmp) {
    this.tocId = tmp;
  }


  /**
   *  Sets the parent attribute of the HelpContent object
   *
   *@param  tmp  The new parent value
   */
  public void setParent(int tmp) {
    this.parent = tmp;
  }


  /**
   *  Sets the contentlevel attribute of the HelpContent object
   *
   *@param  tmp  The new contentlevel value
   */
  public void setContentLevel(int tmp) {
    this.contentLevel = tmp;
  }


  /**
   *  Sets the contentLevel attribute of the HelpContent object
   *
   *@param  tmp  The new contentLevel value
   */
  public void setContentLevel(String tmp) {
    this.contentLevel = Integer.parseInt(tmp);
  }


  /**
   *  Sets the contentorder attribute of the HelpContent object
   *
   *@param  tmp  The new contentorder value
   */
  public void setContentOrder(int tmp) {
    this.contentOrder = tmp;
  }


  /**
   *  Sets the contentOrder attribute of the HelpContent object
   *
   *@param  tmp  The new contentOrder value
   */
  public void setContentOrder(String tmp) {
    this.contentOrder = Integer.parseInt(tmp);
  }


  /**
   *  Sets the features attribute of the HelpContent object
   *
   *@param  tmp  The new features value
   */
  public void setFeatures(ArrayList tmp) {
    this.features = tmp;
  }


  /**
   *  Sets the rules attribute of the HelpContent object
   *
   *@param  tmp  The new rules value
   */
  public void setRules(ArrayList tmp) {
    this.rules = tmp;
  }


  /**
   *  Sets the notes attribute of the HelpContent object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(ArrayList tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the tips attribute of the HelpContent object
   *
   *@param  tmp  The new tips value
   */
  public void setTips(ArrayList tmp) {
    this.tips = tmp;
  }



  /**
   *  Gets the id attribute of the HelpContent object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the action attribute of the HelpContent object
   *
   *@return    The action value
   */
  public String getAction() {
    return action;
  }


  /**
   *  Gets the section attribute of the HelpContent object
   *
   *@return    The section value
   */
  public String getSection() {
    return section;
  }


  /**
   *  Gets the sub attribute of the HelpContent object
   *
   *@return    The sub value
   */
  public String getSub() {
    return sub;
  }


  /**
   *  Gets the title attribute of the HelpContent object
   *
   *@return    The title value
   */
  public String getTitle() {
    return title;
  }


  /**
   *  Gets the description attribute of the HelpContent object
   *
   *@return    The description value
   */
  public String getDescription() {
    return description;
  }


  /**
   *  Gets the tocId attribute of the HelpContent object
   *
   *@return    The tocId value
   */
  public int getTocId() {
    return tocId;
  }


  /**
   *  Gets the parent attribute of the HelpContent object
   *
   *@return    The parent value
   */
  public int getParent() {
    return parent;
  }


  /**
   *  Gets the contentlevel attribute of the HelpContent object
   *
   *@return    The contentlevel value
   */
  public int getContentLevel() {
    return contentLevel;
  }


  /**
   *  Gets the contentorder attribute of the HelpContent object
   *
   *@return    The contentorder value
   */
  public int getContentOrder() {
    return contentOrder;
  }


  /**
   *  Gets the features attribute of the HelpContent object
   *
   *@return    The features value
   */
  public ArrayList getFeatures() {
    return features;
  }


  /**
   *  Gets the rules attribute of the HelpContent object
   *
   *@return    The rules value
   */
  public ArrayList getRules() {
    return rules;
  }


  /**
   *  Gets the notes attribute of the HelpContent object
   *
   *@return    The notes value
   */
  public ArrayList getNotes() {
    return notes;
  }


  /**
   *  Gets the tips attribute of the HelpContent object
   *
   *@return    The tips value
   */
  public ArrayList getTips() {
    return tips;
  }


  /**
   *  Gets the insert attribute of the HelpContent object
   *
   *@return    The insert value
   */
  public boolean getInsert() {
    return insert;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  linkId            Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public boolean insertHelpContent(Connection db, int linkId) throws SQLException {

    insert = true;
    PreparedStatement pst = db.prepareStatement(
        "INSERT INTO help_contents " +
        "(link_module_id, category_id, module, section, subsection, title, description, enteredby, modifiedby) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");

    int i = 0;
    pst.setInt(++i, linkId);
    pst.setInt(++i, linkId);
    pst.setString(++i, action);
    pst.setString(++i, section);
    pst.setString(++i, sub);
    pst.setString(++i, title);
    pst.setString(++i, description);
    pst.setInt(++i, 0);
    pst.setInt(++i, 0);
    pst.execute();
    pst.close();
    id = DatabaseUtils.getCurrVal(db, "help_contents_help_id_seq");

    insertFeatures(db);
    insertNotes(db);
    insertRules(db);
    insertTips(db);

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean insertFeatures(Connection db) throws SQLException {
    Iterator itr = features.iterator();
    int level = 1;

    while (itr.hasNext()) {
      String featureDesc = (String) itr.next();

      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO help_features " +
          "(link_help_id, description, level, enteredby, modifiedby, enabled) " +
          "VALUES (?, ?, ?, ?, ?, ?) ");

      int i = 0;
      pst.setInt(++i, id);
      pst.setString(++i, featureDesc);
      pst.setInt(++i, level);
      pst.setInt(++i, 0);
      pst.setInt(++i, 0);
      pst.setBoolean(++i, true);

      pst.execute();
      pst.close();

      level++;
    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean insertNotes(Connection db) throws SQLException {
    Iterator itr = notes.iterator();

    while (itr.hasNext()) {
      String noteDesc = (String) itr.next();

      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO help_notes " +
          "(link_help_id, description, enteredby, modifiedby, enabled) " +
          "VALUES (?, ?, ?, ?, ?) ");

      int i = 0;
      pst.setInt(++i, id);
      pst.setString(++i, noteDesc);
      pst.setInt(++i, 0);
      pst.setInt(++i, 0);
      pst.setBoolean(++i, true);

      pst.execute();
      pst.close();

    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean insertRules(Connection db) throws SQLException {
    Iterator itr = rules.iterator();

    while (itr.hasNext()) {
      String ruleDesc = (String) itr.next();

      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO help_business_rules " +
          "(link_help_id, description, enteredby, modifiedby, enabled) " +
          "VALUES (?, ?, ?, ?, ?) ");

      int i = 0;
      pst.setInt(++i, id);
      pst.setString(++i, ruleDesc);
      pst.setInt(++i, 0);
      pst.setInt(++i, 0);
      pst.setBoolean(++i, true);

      pst.execute();
      pst.close();

    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  boolean insertTips(Connection db) throws SQLException {
    Iterator itr = tips.iterator();

    while (itr.hasNext()) {
      String tipDesc = (String) itr.next();

      PreparedStatement pst = db.prepareStatement(
          "INSERT INTO help_tips " +
          "(link_help_id, description, enteredby, modifiedby, enabled) " +
          "VALUES (?, ?, ?, ?, ?) ");

      int i = 0;
      pst.setInt(++i, id);
      pst.setString(++i, tipDesc);
      pst.setInt(++i, 0);
      pst.setInt(++i, 0);
      pst.setBoolean(++i, true);

      pst.execute();
      pst.close();

    }

    return true;
  }


  /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   */
  public void buildDescription(Connection db) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT description " +
        "FROM help_features " +
        "WHERE link_help_id = ? " +
        "ORDER BY level");

    pst.setInt(1, getId());
    ResultSet rs = pst.executeQuery();

    if (features == null) {
      features = new ArrayList();
    }

    while (rs.next()) {
      features.add(rs.getString("description"));
    }
    rs.close();
    pst.close();

    if (notes == null) {
      notes = new ArrayList();
    }

    pst = db.prepareStatement(
        "SELECT description " +
        "FROM help_notes " +
        "WHERE link_help_id = ?");

    pst.setInt(1, getId());
    rs = pst.executeQuery();
    while (rs.next()) {
      notes.add(rs.getString("description"));
    }
    rs.close();
    pst.close();

    if (rules == null) {
      rules = new ArrayList();
    }

    pst = db.prepareStatement(
        "SELECT description " +
        "FROM help_business_rules " +
        "WHERE link_help_id = ?");

    pst.setInt(1, getId());
    rs = pst.executeQuery();
    while (rs.next()) {
      rules.add(rs.getString("description"));
    }
    rs.close();
    pst.close();

    if (tips == null) {
      tips = new ArrayList();
    }

    pst = db.prepareStatement(
        "SELECT description " +
        "FROM help_tips " +
        "WHERE link_help_id = ?");

    pst.setInt(1, getId());
    rs = pst.executeQuery();
    while (rs.next()) {
      tips.add(rs.getString("description"));
    }
    rs.close();
    pst.close();

  }


  /**
   *  Builds the context related help information
   *
   *@param  d  Description of the Parameter
   *@return    Description of the Return Value
   */
  public Node buildXML(Document d) {

    Element pageDescription = d.createElement("pageDescription");
    pageDescription.setAttribute("contentLevel", String.valueOf(getContentLevel()));

    Node action = d.createElement("action");
    Node section = d.createElement("section");
    Node subSection = d.createElement("subSection");
    Node title = d.createElement("title");
    Node description = d.createElement("description");

    action.appendChild(d.createTextNode(getAction()));
    if (getSection() != null) {
      section.appendChild(d.createTextNode(getSection()));
    }
    if (getSub() != null) {
      subSection.appendChild(d.createTextNode(getSub()));
    }
    if (getTitle() != null) {
      title.appendChild(d.createTextNode(getTitle()));
    }
    if (getDescription() != null) {
      description.appendChild(d.createTextNode(getDescription()));
    }

    pageDescription.appendChild(action);
    pageDescription.appendChild(section);
    pageDescription.appendChild(subSection);
    pageDescription.appendChild(title);
    pageDescription.appendChild(description);

    buildDescriptionXML(d, pageDescription);
    return pageDescription;
  }


  /**
   *  Builds the features, notes, rules and tips elements of the XML document
   *
   *@param  d                Description of the Parameter
   *@param  pageDescription  Description of the Parameter
   */
  public void buildDescriptionXML(Document d, Node pageDescription) {
    Node features = d.createElement("features");
    Node notes = d.createElement("notes");
    Node rules = d.createElement("rules");
    Node tips = d.createElement("tips");

    Iterator i = getFeatures().iterator();
    while (i.hasNext()) {
      Node description = d.createElement("featureDescription");
      description.appendChild(d.createTextNode((String) i.next()));
      features.appendChild(description);
    }

    i = getNotes().iterator();
    while (i.hasNext()) {
      Node description = d.createElement("noteDescription");
      description.appendChild(d.createTextNode((String) i.next()));
      notes.appendChild(description);
    }

    i = getRules().iterator();
    while (i.hasNext()) {
      Node description = d.createElement("ruleDescription");
      description.appendChild(d.createTextNode((String) i.next()));
      rules.appendChild(description);
    }

    i = getTips().iterator();
    while (i.hasNext()) {
      Node description = d.createElement("tipDescription");
      description.appendChild(d.createTextNode((String) i.next()));
      tips.appendChild(description);
    }

    pageDescription.appendChild(features);
    pageDescription.appendChild(notes);
    pageDescription.appendChild(rules);
    pageDescription.appendChild(tips);
  }


  /**
   *  Formulates a title for the help page if one doues not exist
   *
   *@return    Description of the Return Value
   */
  public String fetchTitle() {

    if (title != null) {
      return title;
    }

    if (sub != null) {
      return (section + " - " + sub);
    }

    if ((section != null) && (sub == null)) {
      return section;
    }

    if ((action != null) && (section == null)) {
      return action;
    }

    return "DUMMY";
  }
}

