package com.darkhorseventures.framework.servlets;

import com.darkhorseventures.framework.actions.Action;
import com.darkhorseventures.framework.actions.Beans;
import com.darkhorseventures.framework.actions.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * This class is used to load the Hashtable of ActionActions from an XML file.
 * <p/>
 * <p>The load() method should be called whenever the latest version of the Actions
 * is needed. If the XML file has not been modified since last call to load(), the
 * file will not be reparsed.</p>
 *
 * @author Joe Walnes
 * @version 1.0
 */
public class XMLConfigLoader
    implements java.io.Serializable {
  static final long serialVersionUID = 536435325324169646L;
  private Map actions;
  private List files = new ArrayList();


  /**
   * Creates a new empty config loader. The values for
   * Actions and file must be set before a call can be made.
   */
  public XMLConfigLoader(Map actions) {
    this.actions = actions;
  }


  /**
   * Adds a config file to the list of config files for this web-app
   */
  public synchronized void addFile(String fileName) {
    files.add(new Files(fileName));
  }


  /**
   * Load the Actions from the XML config file(s).
   * If the file has not been modified since last load(), file will not
   * be reparsed. All exception stacktraces will be written to System.err.
   */
  public void load(ServletContext context) {
    try {
      Iterator i = files.iterator();
      while (i.hasNext()) {
        Files file = (Files) i.next();
        //if( file.getFile().lastModified() > file.getLastModified() )
        //{
        Document document = parseDocument(file.getFile(), context);
        loadAllActions(actions, document);
        //file.setLastModified(file.getFile().lastModified());
        //}
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Parse XML in <code>file</code> and return Document.
   */
  private Document parseDocument(String file, ServletContext context)
      throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    InputStream is = context.getResourceAsStream("WEB-INF/" + file);
    Document document = builder.parse(is);
    return document;
  }


  /**
   * Load all ActionActions into Hashtable from Document
   */
  private void loadAllActions(Map Actions, Document document) {
    NodeList actionTags = document.getElementsByTagName("action");
    for (int i = 0; i < actionTags.getLength(); i++) {
      Element actionTag = (Element) actionTags.item(i);
      Action a = loadAction(actionTag);
      actions.put(a.getActionName(), a); // only store it if it doesn't already exist.
    }
  }


  /**
   * Return an Action based on an 'action' tag.
   */
  private Action loadAction(Element e) {
    String aName = e.getAttribute("name");
    String aClass = e.getAttribute("class");
    String bName = null;
    String bScope = null;
    String bClass = null;
    Object firstBean = null;
    Map forwardTable = new HashMap();
    Map beansTable = new HashMap();

    NodeList children = e.getChildNodes();
    int len = children.getLength();
    for (int i = 0, cntr = 0; i < len; i++) {
      if (children.item(i).getNodeType() != Element.ELEMENT_NODE)
        continue;
      Element child = (Element) children.item(i);
      String childName = child.getTagName();
      if (childName.equals("bean")) {
        bName = child.getAttribute("name");
        bScope = child.getAttribute("scope").toLowerCase();
        int beanScope = 2;
        if (bScope.indexOf("request") >= 0)
          beanScope = 1;
        if (bScope.indexOf("session") >= 0)
          beanScope = 2;
        if (bScope.indexOf("application") >= 0)
          beanScope = 3;
        bClass = child.getAttribute("class");
        Beans beans = null;
        if (cntr++ == 0)
          beans = new Beans(bName, beanScope, bClass, true);
        else
          beans = new Beans(bName, beanScope, bClass, false);

        beansTable.put(bName, beans);
      } else if (childName.equals("forward")) {
        boolean redirect = false;
        if (child.getAttribute("redirect") != null && child.getAttribute("redirect").equals("true"))
          redirect = true;
        Resource r = new Resource(child.getAttribute("resource"), child.getAttribute("xsl"), child.getAttribute("context"), redirect, child.getAttribute("layout"));
        forwardTable.put(child.getAttribute("name"), r);
      }
    }

    Action action = new Action(aName, aClass);
    Iterator i = forwardTable.keySet().iterator();

    // add all the forwards to this action
    while (i.hasNext()) {
      String fKey = (String) i.next();
      action.addResource(fKey, (Resource) forwardTable.get(fKey));
    }

    // add the beans to this action
    i = beansTable.keySet().iterator();
    while (i.hasNext()) {
      String fKey = (String) i.next();
      action.addBean(fKey, (Beans) beansTable.get(fKey));
    }

    return action;
  }
}