/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
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

import org.aspcfs.modules.communications.actions.ProcessFileItemImage;
import org.aspcfs.utils.DatabaseUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.ArrayList;

/**
 * This Class permits the user to have access to a specific fileItem for
 * download by adding the id to a session array and then the image will
 * evaluate the id later and stream the image.
 *
 * @author matt rajkowski
 * @version $Id: FileItemHandler.java,v 1.2 2004/05/04 15:46:14 mrajkowski Exp
 *          $
 * @created April 1, 2004
 */
public class FileItemHandler extends TagSupport implements TryCatchFinally {

  private String id = null;
  private String path = null;
  private String version = null;
  private String name = null;
  private boolean thumbnail = false;

  public void doCatch(Throwable throwable) throws Throwable {
    // Required but not needed
  }

  public void doFinally() {
    // Reset each property or else the value gets reused
    id = null;
    path = null;
    version = null;
    name = null;
    thumbnail = false;
  }


  /**
   * Sets the thumbnail attribute of the FileItemHandler object
   *
   * @param tmp The new thumbnail value
   */
  public void setThumbnail(String tmp) {
    this.thumbnail = DatabaseUtils.parseBoolean(tmp);
  }

  /**
   * Sets the thumbnail attribute of the FileItemHandler object
   *
   * @param tmp The new thumbnail value
   */
  public void setThumbnail(boolean tmp) {
    thumbnail = tmp;
  }


  /**
   * Sets the name attribute of the FileItemHandler object
   *
   * @param tmp The new name value
   */
  public void setName(String tmp) {
    this.name = tmp;
  }


  /**
   * Sets the version attribute of the FileItemHandler object
   *
   * @param tmp The new version value
   */
  public void setVersion(String tmp) {
    this.version = tmp;
  }


  /**
   * Sets the id attribute of the FileItemHandler object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the FileItemHandler object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = String.valueOf(tmp);
  }


  /**
   * Sets the path attribute of the FileItemHandler object
   *
   * @param tmp The new path value
   */
  public void setPath(String tmp) {
    this.path = tmp;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   * @throws JspException Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      ArrayList allowedImages = (ArrayList) pageContext.getSession().getAttribute(
          ProcessFileItemImage.PROCESS_FILE_ITEM_NAME);
      if (allowedImages == null) {
        synchronized (pageContext.getSession()) {
          allowedImages = (ArrayList) pageContext.getSession().getAttribute(
              ProcessFileItemImage.PROCESS_FILE_ITEM_NAME);
          if (allowedImages == null) {
            allowedImages = new ArrayList();
            pageContext.getSession().setAttribute(
                ProcessFileItemImage.PROCESS_FILE_ITEM_NAME, allowedImages);
          }
        }
      }
			allowedImages.add(
					id + (version != null ? "-" + version : "") + (thumbnail ? "TH" : ""));
      this.pageContext.getOut().write(
          "<img src=\"ProcessFileItemImage.do?id=" + id + "&path=" + path + (version != null ? "&version=" + version : "") + (thumbnail ? "&thumbnail=true" : "") + "\" " + (name != null ? "name=\"" + name + "\"" : "") + " border=\"0\"/>");
    } catch (Exception e) {
      System.err.println("EXCEPTION: FileItemHandler-> " + e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

