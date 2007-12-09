/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.taglib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Description of the ContainersMenuHandler
 *
 * @author Artem.Zakolodkin
 * @created Feb 28, 2007
 */
public class ContainersMenuHandler extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack containersstack = null;
	private ArrayList tmpList = new ArrayList();

	public final int doStartTag() {
		tmpList.clear();
		Stack tmp = (Stack) containersstack.clone();
		while (tmp.size() > 0) {
			ContainerMenuClass cmt = (ContainerMenuClass) tmp.pop();
			ContainerMenuHandler cmh = new ContainerMenuHandler();
			cmh.setName(cmt.getName());
			cmh.setSelected(cmt.getSelected());
			cmh.setObject(cmt.getObject());
			cmh.setParam(cmt.getStringParams());
			cmh.setItem(cmt.getItem());
			cmh.setPageContext(pageContext);
			pageContext.getRequest().setAttribute(cmt.getObject(), cmt.getItem());
			cmh.doStartTag();
			tmpList.add(cmh);
		}
		return EVAL_BODY_INCLUDE;
	}
	
	public final int doAfterBody() {
		Iterator i = tmpList.iterator();
		while (i.hasNext()) {
			((ContainerMenuHandler)i.next()).doAfterBody();
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() {
		Iterator i = tmpList.iterator();
		while (i.hasNext()) {
			((ContainerMenuHandler)i.next()).doEndTag();
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the containersstack
	 */
	public Stack getContainersstack() {
		return containersstack;
	}

	/**
	 * @param containersstack the containersstack to set
	 */
	public void setContainersstack(Stack containersstack) {
		this.containersstack = containersstack;
	}
}
