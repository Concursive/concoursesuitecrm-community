package com.darkhorseventures.controller;

import java.util.*;
import javax.servlet.*;
import com.darkhorseventures.cfsbase.UserBean;
import com.darkhorseventures.cfsbase.CustomField;
import com.darkhorseventures.utils.XMLUtils;
import com.darkhorseventures.utils.Template;
import com.darkhorseventures.controller.SubmenuItem;
import com.darkhorseventures.controller.CustomForm;
import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class CustomFormList extends HashMap {

  public CustomFormList() { }

  public CustomFormList(ServletContext context, String fn) {
    loadXML(context, fn);
  }

  private LinkedHashMap loadXML(ServletContext context, String file) {
    LinkedHashMap form = new LinkedHashMap();
    try {
      XMLUtils xml = new XMLUtils(
          new File(context.getRealPath("/WEB-INF/" + file )));
	  
      LinkedList formList = new LinkedList();
      xml.getAllChildren(xml.getDocumentElement(), "form", formList);
      
      Iterator list = formList.iterator();
      while (list.hasNext()) {
	Element f = (Element) list.next();
        if (System.getProperty("DEBUG") != null) {
	  System.out.println("CustomFormList-> Form Added: " + f.getAttribute("name"));
        }
	
        CustomForm newForm = this.buildForm(f);
	this.put(newForm.getName(), newForm);
      }

    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return form;
  }


  private CustomForm buildForm(Element container) {
    LinkedList tabList = new LinkedList();
    
    CustomForm thisForm = new CustomForm();
    thisForm.setName(container.getAttribute("name"));
    thisForm.setReturnLink(container.getAttribute("returnLink"));
    thisForm.setReturnLinkText(container.getAttribute("returnLinkText"));
    thisForm.setDefaultField(container.getAttribute("defaultField"));
    thisForm.setAction(container.getAttribute("action"));
    thisForm.setCancel(container.getAttribute("cancel"));
    
    for (int buttonCount = 0; buttonCount < 5; buttonCount++) {
   	thisForm.getButtonNames().add(buttonCount, container.getAttribute("button" + buttonCount + "text"));
	thisForm.getButtonLinks().add(buttonCount, container.getAttribute("button" + buttonCount + "link"));
    }
    
    XMLUtils.getAllChildren(container, "tab", tabList);
    Iterator list1 = tabList.iterator();
    while (list1.hasNext()) {
      Element tab = (Element) list1.next();
      
      CustomFormTab thisTab = new CustomFormTab();
      thisTab.setName(tab.getAttribute("name"));
      thisTab.setNext(tab.getAttribute("next"));
      thisTab.setPrev(tab.getAttribute("prev"));
      thisTab.setId(tab.getAttribute("id"));
      
	LinkedList groupList = new LinkedList();
	XMLUtils.getAllChildren(tab, "group", groupList);
	Iterator list2 = groupList.iterator();
	
	while (list2.hasNext()) {
		Element group = (Element) list2.next();
	
		CustomFormGroup thisGroup = new CustomFormGroup();
		thisGroup.setName(group.getAttribute("name"));
		
			LinkedList fieldList = new LinkedList();
			XMLUtils.getAllChildren(group, "field", fieldList);
			Iterator list3 = fieldList.iterator();
			
			while (list3.hasNext()) {
				Element field = (Element) list3.next();
			
				CustomField thisField = new CustomField();
				thisField.setName(field.getAttribute("name"));
				thisField.setDisplay(field.getAttribute("display"));
				thisField.setType(field.getAttribute("type"));
				thisField.setLengthVar(field.getAttribute("lengthVar"));
				
				thisField.setDelimiter("^");
				
				thisField.setTextAsCode(field.getAttribute("textAsCode"));
				thisField.setLookupList(field.getAttribute("lookupList"));
				
				StringTokenizer st = new StringTokenizer(field.getAttribute("parameters"), "^");
				
				if (st.hasMoreTokens()) {
					while(st.hasMoreTokens()) {
						StringTokenizer b = new StringTokenizer(st.nextToken(), "=");
						if (b.hasMoreTokens()) {
							while (b.hasMoreTokens()) {
								thisField.setParameter(b.nextToken(), b.nextToken());
								//System.out.println(b.nextToken());
							}
						}
					}
				}
				
				thisField.setRequired(field.getAttribute("required"));
				thisGroup.add(thisField);
				
				if (System.getProperty("DEBUG") != null) {
					System.out.println("---CustomFormList-> Field Added: " + thisField.getName());
				}
			}
		
		
		thisTab.add(thisGroup);
		
		if (System.getProperty("DEBUG") != null) {
			System.out.println("--CustomFormList-> Group Added: " + thisGroup.getName());
		}
	}
      
      thisForm.add(thisTab);
      
      if (System.getProperty("DEBUG") != null) {
        System.out.println("-CustomFormList-> Tab Added: " + thisTab.getName());
      }
      
    }
    return thisForm;
  }
}

