package com.darkhorseventures.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.lang.reflect.*;
import java.util.*;

public class XMLUtils {
  
  private Document document = null;
  private StringBuffer XMLString = null;
  private boolean cacheXML = false;

  public void setCacheXML(boolean tmp) { cacheXML = tmp; }
  
  public Document getDocument() { return document; }
  public Element getDocumentElement() { return document.getDocumentElement(); }
  public String getXMLString() { 
    if (XMLString == null) return null;
    return XMLString.toString();
  }
  

  public XMLUtils(HttpServletRequest request) throws Exception {
    StringBuffer data = new StringBuffer();
    BufferedReader br = request.getReader();
    String line = null;
    if (System.getProperty("DEBUG") != null) System.out.println("XMLUtils->Reading XML from request");
    while ((line = br.readLine()) != null) {
      data.append(line);
      if (System.getProperty("DEBUG") != null) System.out.println("  ++Line: " + line);
      if (cacheXML) {
        if (XMLString == null) XMLString = new StringBuffer();
        XMLString.append(line);
      }
    }
    this.parseXML(data.toString());
  }
  
  private void parseXML(String xmlToParse) throws Exception {
    if (System.getProperty("DEBUG") != null) System.out.println("XMLUtils-> Parsing XML string");
    StringReader strXML = new StringReader(xmlToParse);
    InputSource isXML = new InputSource(strXML);
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    this.document = builder.parse(isXML);
  }
  
  public Element getFirstChild(String name) {
    return this.getFirstChild(this.document, name);
  }
  
  public static Element getFirstChild(Document document, String name) {
    Element element = document.getDocumentElement();
    return getFirstChild(element, name);
  }
  
  public static Element getFirstChild(Element e, String name) {
    NodeList nl = e.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element)n).getTagName().equals(name)) {
        return (Element)n;
      }
    }
    return null;
  }
  
  public static void getAllChildren(Element e, Vector elementList) {
    NodeList nl = e.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE) {
        elementList.add((Element)n);
      }
    }
  }
  
  public static void getAllChildren(Element e, String name, Vector elementList) {
    NodeList nl = e.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element)n).getTagName().equals(name)) {
        elementList.add((Element)n);
      }
    }
  }
  
  public static Element getFirstElement(Element e, String name) {
    NodeList nl = e.getElementsByTagName(name);
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element)n).getTagName().equals(name)) {
        return (Element)n;
      }
    }
    return null;
  }
  
  public static Vector getElements(Element e, String name) {
    Vector list = new Vector();
    NodeList nl = e.getElementsByTagName(name);
    for (int i = 0; i < nl.getLength(); i++) {
      Node n = nl.item(i);
      if (n.getNodeType() == Node.ELEMENT_NODE && ((Element)n).getTagName().equals(name)) {
        list.add((Element)n);
      }
    }
    return list;
  }

  public static String getNodeText(Node element) {
    NodeList nodeList = element.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node thisNode = nodeList.item(i);
      if (thisNode.getNodeType() == Node.TEXT_NODE) {
        return thisNode.getNodeValue();
      }
    }
    return null;
  }
  
  public static void populateObject(Object target, Element element) {
    if (target != null) {
      NodeList objectElements = element.getChildNodes();
      for (int j = 0; j < objectElements.getLength(); j++) {
        Node theObject = (Node)objectElements.item(j);
        if (theObject.getNodeType() == Node.ELEMENT_NODE) {
          String param = theObject.getNodeName();
          param = param.substring(0, 1).toUpperCase() + param.substring(1);
          String value = getNodeText(theObject);
          try {
            Method method = null;
            Class[] argTypes = new Class[]{value.getClass()};
            method = target.getClass().getMethod("set" + param, argTypes);
            method.invoke(target, new Object[]{value});
            if (System.getProperty("DEBUG") != null) System.out.println("ProcessPacket-> set" + param + "(" + value + ")");
          } catch (Exception e) {
            if (System.getProperty("DEBUG") != null) System.out.println("ProcessPacket-> set" + param + "(" + value + ") **INVALID"); 
          }
        }
      }
    }
  }
}
