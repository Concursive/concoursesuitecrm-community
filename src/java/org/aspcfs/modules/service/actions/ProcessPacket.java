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
//import org.xml.sax.helpers.DefaultHandler;


public final class ProcessPacket extends CFSModule {

  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    String data = (String)context.getRequest().getParameter("data");

    if (data != null) {
      try {
        Vector cfsObjects = this.parseXML(data);
      } catch (Exception e) {
        errorMessage = e;
        System.out.println(e.toString());
        return ("PacketERROR");
      }
      return ("PacketOK");
    } else {
      return ("PacketERROR");
    }
  }

  private Vector parseXML(String xmlToParse) {
    Vector objects = new Vector();
    try {
      System.out.println("ProcessPacket-> Parsing data");
      StringReader strXML = new StringReader(xmlToParse);

      InputSource isXML = new InputSource(strXML);
      //DOMParser parser = new DOMParser();
      //parser.parse(isXML);
      //Document document = parser.getDocument();
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(isXML);

      Element element = document.getDocumentElement();
      Element authElement = getFirstElement(element, "auth-id");
      if (authElement != null) {
        Vector tickets = getElements(element, "ticket");
        if (tickets.size() > 0) {
          Iterator iTicket = tickets.iterator();
          while (iTicket.hasNext()) {
            Element ticketElement = (Element)iTicket.next();
            Ticket thisTicket = new Ticket();
            populateObject(thisTicket, ticketElement);
            
            //Ticket thisTicket = populateObject((Element)iTicket.next());
            
            //thisTicket.insert(db);
          }
        }
      }
      
      /*
      NodeList rootElements = document.getElementsByTagName("aspcfs");
      NodeList objectElements = rootElements.item(0);
      Vector elementList = new Vector();
      for (int i = 0; i < objectElements.getLength(); i++) {
        Node n = objectElements.item(i);
        if (n.getNodeType() == Node.ELEMENT_NODE) {
          Element e = (Element)n;
          System.out.println(e.getTagName());
          
          NodeList objectElements = aspcfsElement.getChildNodes();
          for (int j = 0; j < objectElements.getLength(); j++) {
            Node theObject = (Node)objectElements.item(j);
            if (theObject.getNodeType() == 1) {

              if ("auth-id".equals(theObject.getNodeName())) {
                System.out.println("ProcessPacket-> Node Element: " + theObject.getNodeName());
                //Node thisObject = 
                  if (theObject.getNodeType() == 3) {
                    System.out.println("ProcessPacket->     Value: " + theObject.getNodeValue());
                  }



                } else if ("ticket".equals(theObject.getNodeName())) {
                  System.out.println("got a ticket");
                  //Ticket thisTicket = new Ticket();
                  //objects.add(thisTicket);
                }
              }
              
        }
      }
      */
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objects;
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
  
  public static void populateObject(Object target, Element element) {
    try {
      Method method = null;
      String param = "Id";
      String value = "1";
      Class[] argTypes = new Class[]{value.getClass()};
      method = target.getClass().getMethod("set" + param, argTypes);
      method.invoke(target, new Object[]{value});
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }
}

