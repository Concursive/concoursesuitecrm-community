package com.darkhorseventures.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import java.util.*;

/**
 *  Provides essential methods for working with XML.  XMLUtils is also
 *  a class representing an XML document.
 *
 *@author     matt
 *@created    April 10, 2002
 *@version    $Id$
 */
public class XMLUtils {

  private Document document = null;
  private StringBuffer XMLString = null;
  private boolean cacheXML = false;


  /**
   *  Constructor for the XMLUtils object
   *
   *@param  xmlData        Description of Parameter
   *@exception  Exception  Description of Exception
   */
  public XMLUtils(String xmlData) throws Exception {
    this.parseXML(xmlData);
  }


  /**
   *  Constructor for the XMLUtils object
   *
   *@param  request        Description of Parameter
   *@exception  Exception  Description of Exception
   */
  public XMLUtils(HttpServletRequest request) throws Exception {
    StringBuffer data = new StringBuffer();
    BufferedReader br = request.getReader();
    String line = null;
    if (System.getProperty("DEBUG") != null) {
      System.out.println("XMLUtils->Reading XML from request");
    }
    boolean startElement = true;
    boolean endElement = true;
    while ((line = br.readLine()) != null) {
      data.append(line.trim() + System.getProperty("line.separator"));
      if (System.getProperty("DEBUG") != null) {
        System.out.println("  ++Line: " + line);
      }
      if (cacheXML) {
        if (XMLString == null) {
          XMLString = new StringBuffer();
        }
        XMLString.append(line);
      }
    }
    if (System.getProperty("DEBUG") != null) {
      System.out.println("  XML: " + data.toString());
    }
    this.parseXML(data.toString());
  }


  /**
   *  Gets the firstChild attribute of the XMLUtils class
   *
   *@param  document  Description of Parameter
   *@param  name      Description of Parameter
   *@return           The firstChild value
   */
  public static Element getFirstChild(Document document, String name) {
    Element element = document.getDocumentElement();
    return getFirstChild(element, name);
  }


  /**
   *  Gets the firstChild attribute of the XMLUtils class
   *
   *@param  e     Description of Parameter
   *@param  name  Description of Parameter
   *@return       The firstChild value
   */
  public static Element getFirstChild(Element e, String name) {
    NodeList nl = e.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(name)) {
        return (Element) n;
      }
    }
    return null;
  }


  /**
   *  Gets the allChildren attribute of the XMLUtils class
   *
   *@param  e            Description of Parameter
   *@param  elementList  Description of Parameter
   */
  public static void getAllChildren(Element e, AbstractList elementList) {
    NodeList nl = e.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE) {
        elementList.add((Element) n);
      }
    }
  }


  /**
   *  Gets the allChildren attribute of the XMLUtils class
   *
   *@param  e            Description of Parameter
   *@param  name         Description of Parameter
   *@param  elementList  Description of Parameter
   */
  public static void getAllChildren(Element e, String name, AbstractList elementList) {
    NodeList nl = e.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(name)) {
        elementList.add((Element) n);
      }
    }
  }


  /**
   *  Gets the allChildrenText attribute of the XMLUtils class
   *
   *@param  e         Description of Parameter
   *@param  name      Description of Parameter
   *@param  textList  Description of Parameter
   */
  public static void getAllChildrenText(Element e, String name, AbstractList textList) {
    NodeList nl = e.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(name)) {
        String nodeText = getNodeText((Element) n);
        if (nodeText != null) {
          textList.add(nodeText);
        }
      }
    }
  }


  /**
   *  Gets the firstElement attribute of the XMLUtils class
   *
   *@param  e     Description of Parameter
   *@param  name  Description of Parameter
   *@return       The firstElement value
   */
  public static Element getFirstElement(Element e, String name) {
    NodeList nl = e.getElementsByTagName(name);
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(name)) {
        return (Element) n;
      }
    }
    return null;
  }


  /**
   *  Gets the elements attribute of the XMLUtils class
   *
   *@param  e     Description of Parameter
   *@param  name  Description of Parameter
   *@return       The elements value
   */
  public static ArrayList getElements(Element e, String name) {
    ArrayList list = new ArrayList();
    NodeList nl = e.getElementsByTagName(name);
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element) n).getTagName().equals(name)) {
        list.add((Element) n);
      }
    }
    return list;
  }


  /**
   *  Gets the nodeText attribute of the XMLUtils class
   *
   *@param  element  Description of Parameter
   *@return          The nodeText value
   */
  public static String getNodeText(Node element) {
    String nodeText = null;
    NodeList nodeList = element.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node thisNode = nodeList.item(i);
      if (thisNode.getNodeType() == Node.TEXT_NODE) {
        nodeText = thisNode.getNodeValue();
      }
    }
    if (nodeText == null) {
      return null;
    } else {
      int begin = 0;
      int end = nodeText.length();
      if (nodeText.startsWith(System.getProperty("line.separator"))) {
        begin = (System.getProperty("line.separator").length());
      }
      if (nodeText.endsWith(System.getProperty("line.separator"))) {
        end = end - (System.getProperty("line.separator").length());
      }
      return nodeText.substring(begin, end);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  target   Description of Parameter
   *@param  element  Description of Parameter
   */
  public static HashMap populateObject(Object target, Element element) {
    if (target != null) {
      HashMap ignoredProperties = new HashMap();
      NodeList objectElements = element.getChildNodes();
      for (int j = 0; j < objectElements.getLength(); j++) {
        Node theObject = (Node) objectElements.item(j);
        if (theObject.getNodeType() == Node.ELEMENT_NODE) {
          String param = theObject.getNodeName();
          String value = getNodeText(theObject);
          if (ObjectUtils.setParam(target, param, value)) {
            if (System.getProperty("DEBUG") != null) {
              System.out.println("XMLUtils-> set" + param + "(" + value + ")");
            }
          } else {
            ignoredProperties.put(param, value);
            if (System.getProperty("DEBUG") != null) {
              System.out.println("XMLUtils-> set" + param + "(" + value + ") **INVALID");
            }
          }
        }
      }
      return ignoredProperties;
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  s  Description of Parameter
   *@return    Description of the Returned Value
   */
  public static String toXMLValue(String s) {
    if (s != null) {
      String xmlReady = s.trim();
      xmlReady = StringHelper.replace(xmlReady, "\"", "&quot;");
      xmlReady = StringHelper.replace(xmlReady, "<", "&lt;");
      xmlReady = StringHelper.replace(xmlReady, ">", "&gt;");
      return (xmlReady);
    } else {
      return ("");
    }
  }


  /**
   *  Description of the Method
   *
   *@param  node  Description of Parameter
   *@return       Description of the Returned Value
   */
  public static String toString(Node node) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      Source source = new DOMSource(node);

      StringWriter writer = new StringWriter();
      Result result = new StreamResult(writer);

      transformer.transform(source, result);

      return writer.toString();
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  node  Description of Parameter
   *@return       Description of the Returned Value
   */
  public static boolean debug(Node node) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      Source source = new DOMSource(node);

      StreamResult result = new StreamResult(System.out);

      transformer.transform(source, result);
      return true;
    } catch (Exception e) {
      e.printStackTrace(System.out);
      return false;
    }
  }


  /**
   *  Sets the cacheXML attribute of the XMLUtils object
   *
   *@param  tmp  The new cacheXML value
   */
  public void setCacheXML(boolean tmp) {
    cacheXML = tmp;
  }


  /**
   *  Gets the document attribute of the XMLUtils object
   *
   *@return    The document value
   */
  public Document getDocument() {
    return document;
  }


  /**
   *  Gets the documentElement attribute of the XMLUtils object
   *
   *@return    The documentElement value
   */
  public Element getDocumentElement() {
    return document.getDocumentElement();
  }


  /**
   *  Gets the xMLString attribute of the XMLUtils object
   *
   *@return    The xMLString value
   */
  public String getXMLString() {
    if (XMLString == null) {
      return null;
    }
    return XMLString.toString();
  }


  /**
   *  Gets the firstChild attribute of the XMLUtils object
   *
   *@param  name  Description of Parameter
   *@return       The firstChild value
   */
  public Element getFirstChild(String name) {
    return this.getFirstChild(this.document, name);
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Returned Value
   */
  public String toString() {
    return XMLUtils.toString(document);
  }


  /**
   *  Description of the Method
   *
   *@param  xmlToParse     Description of Parameter
   *@exception  Exception  Description of Exception
   */
  private void parseXML(String xmlToParse) throws Exception {
    if (System.getProperty("DEBUG") != null) {
      System.out.println("XMLUtils-> Parsing XML string");
    }
    StringReader strXML = new StringReader(xmlToParse);
    InputSource isXML = new InputSource(strXML);
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //factory.setIgnoringElementContentWhitespace(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    this.document = builder.parse(isXML);
  }
}

