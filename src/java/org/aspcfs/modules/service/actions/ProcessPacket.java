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
//import org.xml.sax.*;
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
			
			NodeList rootElements = document.getElementsByTagName("aspcfs");
			for (int i = 0; i < rootElements.getLength(); i++) {
				Element aspcfsElement = (Element)rootElements.item(i);
				NodeList objectElements = aspcfsElement.getChildNodes();
				for (int j = 0; j < objectElements.getLength(); j++) {
				  Node theObject = (Node)objectElements.item(j);
					if (theObject.getNodeType() == 1) {
						
						if ("auth-id".equals(theObject.getNodeName())) {
							System.out.println("ProcessPacket-> Node Element: " + theObject.getNodeName());
							Node thisObject = 
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
    } catch (Exception e) {
      e.printStackTrace();
    }
		return objects;
  }
	
}

