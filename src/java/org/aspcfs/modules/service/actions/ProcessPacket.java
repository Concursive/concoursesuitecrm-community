/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.lang.reflect.*;


public final class ProcessPacket extends CFSModule {

  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    int statusCode = 1;
    String errorText = null;
    StringBuffer data = new StringBuffer();
    
    //Process the posted data: content-type must be text/plain
    try {
      BufferedReader br = context.getRequest().getReader();
      String line = null;
      while ((line = br.readLine()) != null) {
        data.append(line);
        System.out.println("  ++Line: " + line);
      }
    } catch(Exception e) {
      System.out.println("Read error: " + e.toString());
    }
    
    //Parse the data as XML
    if (data.length() > 0) {
      try {
        String siteId = null;
        Vector cfsObjects = new Vector();
        errorText = this.parseXML(cfsObjects, data.toString());
        if (System.getProperty("DEBUG") != null) System.out.println("Site: " + siteId);
        if (System.getProperty("DEBUG") != null) System.out.println("Objects created: " + cfsObjects.size());
        if (errorText == null && cfsObjects.size() > 0) {
          statusCode = 0;
        }
        if (errorText == null && cfsObjects.size() == 0) {
          errorText = "No object elements found";
        }
      } catch (Exception e) {
        errorMessage = e;
        e.printStackTrace(System.out);
        errorText = "Internal application error";
      }
      
    } else {
      errorText = "Posted data not found -- possibly incorrect content type";
    }
    
    //Return the status
    context.getRequest().setAttribute("statusCode", "" + statusCode);
    context.getRequest().setAttribute("errorText", errorText);
    
/*     try {
      context.getResponse().setContentType("text/plain");
      PrintWriter out = context.getResponse().getWriter();
      out.println("Echo");
      out.println(data.toString());
      out.flush();
      out.close();
    } catch (Exception e) {
      System.out.println("Write error: " + e.toString());
    }
*/   
    return ("PacketOK");
  }

  public static String parseXML(Vector objects, String xmlToParse) {
    try {
      if (System.getProperty("DEBUG") != null) System.out.println("ProcessPacket-> Parsing data");
      StringReader strXML = new StringReader(xmlToParse);

      InputSource isXML = new InputSource(strXML);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(isXML);

      Element element = document.getDocumentElement();
      Element authElement = getFirstElement(element, "auth-id");
      if (authElement != null) {
        //siteId = getNodeText(authElement); 
        //System.out.println(siteId);
        Vector cfsElements = getCFSElements(element);
        if (cfsElements.size() > 0) {
          Iterator elements = cfsElements.iterator();
          while (elements.hasNext()) {
            Object thisObject = createObject((Element)elements.next());
            //insert the object... but just testing for now
            if (thisObject != null) objects.add(thisObject);
          }
        }
      } else {
        return ("Authentication element not found");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return("Error in XML: " + e.getMessage());
    }
    return null;
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
  
  public static Vector getCFSElements(Element e) {
    Vector elementList = new Vector();
    elementList.addAll(getElements(e, "account"));
    elementList.addAll(getElements(e, "contact"));
    elementList.addAll(getElements(e, "ticket"));
    return elementList;
  }
  
  public static Object createObject(Element element) {
    Object target = null;
    try {
      if (element.getTagName().equals("ticket")) target = new Ticket();
      //if (element.getTagName().equals("contact")) target = new Contact();
      
      if (System.getProperty("DEBUG") != null && target != null) { 
        System.out.println("ProcessPacket-> New: " + target.getClass().getName());
      }
      populateObject(target, element);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return target;
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
}

