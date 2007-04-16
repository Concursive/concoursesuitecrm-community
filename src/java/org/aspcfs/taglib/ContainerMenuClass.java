/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.taglib;

/**
 * Description of the ContainerMenuClass
 *
 * @author Artem.Zakolodkin
 * @created Feb 27, 2007
 */
public class ContainerMenuClass {
  private String name = null;
  private String selected = null;
  private String stringParams = null;
  private String object = null;
  private Object item = null;
  private ContainerMenuClass parent = null;
  
	/**
	 * @return the item
	 */
	public String getObject() {
		return object;
	}
	/**
	 * @param object the item to set
	 */
	public void setObject(String object) {
		this.object = object;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the selected
	 */
	public String getSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(String selected) {
		this.selected = selected;
	}
	/**
	 * @return the stringParams
	 */
	public String getStringParams() {
		return stringParams;
	}
	/**
	 * @param stringParams the stringParams to set
	 */
	public void setStringParams(String stringParams) {
		this.stringParams = stringParams;
	}
	/**
	 * @return the item
	 */
	public Object getItem() {
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Object item) {
		this.item = item;
	}
	/**
	 * @return the parent
	 */
	public ContainerMenuClass getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(ContainerMenuClass parent) {
		this.parent = parent;
	}
}
