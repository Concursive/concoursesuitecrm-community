/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.taglib;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.StringUtils;

/**
 *  Description of the Class
 *
 * @author     matt rajkowski
 * @created    April 20, 2003
 * @version    $Id: FolderHierarchyHandler.java,v 1.4 2003/09/04 03:49:45 matt
 *      Exp $
 */
public class FolderHierarchyHandler extends TagSupport {
  private boolean showLastLink = false;
  private String module = null;
  private String link = null;


  /**
   *  Sets the showLastLink attribute of the FolderHierarchyHandler object
   *
   * @param  tmp  The new showLastLink value
   */
  public void setShowLastLink(String tmp) {
    this.showLastLink = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Sets the showLastLink attribute of the FolderHierarchyHandler object
   *
   * @param  tmp  The new showLastLink value
   */
  public void setShowLastLink(boolean tmp) {
    this.showLastLink = tmp;
  }


  /**
   *  Sets the module attribute of the FolderHierarchyHandler object
   *
   * @param  tmp  The new module value
   */
  public void setModule(String tmp) {
    this.module = tmp;
  }


  /**
   *  Sets the link attribute of the FolderHierarchyHandler object
   *
   * @param  tmp  The new link value
   */
  public void setLink(String tmp) {
    this.link = tmp;
  }


  /**
   *  Gets the module attribute of the FolderHierarchyHandler object
   *
   * @return    The module value
   */
  public String getModule() {
    return module;
  }


  /**
   *  Gets the link attribute of the FolderHierarchyHandler object
   *
   * @return    The link value
   */
  public String getLink() {
    return link;
  }


  /**
   *  Description of the Method
   *
   * @return                   Description of the Return Value
   * @exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      String projectId = (String) pageContext.getRequest().getParameter("pid");
      //Show the open folder image
      this.pageContext.getOut().write("<img border=\"0\" src=\"images/icons/stock_home-16.gif\" align=\"absmiddle\"> ");
      //Generate the folder path
      LinkedHashMap folderLevels = (LinkedHashMap) pageContext.getRequest().getAttribute("folderLevels");
      if (folderLevels == null) {
        if (showLastLink) {
          if (module == null || "".equals(module)) {
            this.pageContext.getOut().write("<a href=\"ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=" + projectId + "&folderId=-1\">");
          } else {
            this.pageContext.getOut().write("<a href=\""+ link + "&folderId=-1\">");
          }
        }
        this.pageContext.getOut().write("Top Folder");
        if (showLastLink) {
          this.pageContext.getOut().write("</a>");
        }
      } else {
        Object[] hierarchy = folderLevels.keySet().toArray();
        if (hierarchy.length > 0) {
          String folderId = (String) pageContext.getRequest().getParameter("folderId");
          //Show a Home link
          if (module == null || "".equals(module)) {
            this.pageContext.getOut().write("<a href=\"ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=" + projectId + "&folderId=-1\">");
          } else {
            this.pageContext.getOut().write("<a href=\""+link+"&folderId=-1\">");
          }
          this.pageContext.getOut().write("Top Folder");
          this.pageContext.getOut().write("</a>");
          //Show the rest of the links, except the last, unless specified
          this.pageContext.getOut().write(" > ");
          for (int i = hierarchy.length - 1; i >= 0; i--) {
            Integer thisId = (Integer) hierarchy[i];
            String[] sa = (String[]) folderLevels.get(thisId);
            String subject = sa[0];
            String display = sa[1];
            if (i > 0 || showLastLink) {
              if (module == null || "".equals(module)) {
                this.pageContext.getOut().write("<a href=\"ProjectManagement.do?command=ProjectCenter&section=File_" + ("-1".equals(display) ? "Library" : "Gallery") + "&pid=" + projectId + "&folderId=" + thisId.intValue() + ("2".equals(display) ? "&details=true" : "") + "\">");
              } else {
                this.pageContext.getOut().write("<a href=\""+link+ "&folderId=" + thisId.intValue() + "\">");
              }
            }
            this.pageContext.getOut().write(StringUtils.toHtml(subject));
            if (i > 0 || showLastLink) {
              this.pageContext.getOut().write("</a>");
            }
            if (i > 0) {
              this.pageContext.getOut().write(" > ");
            }
          }
        } else {
          //Show home link
          if (showLastLink) {
            if (module == null || "".equals(module)) {
              this.pageContext.getOut().write("<a href=\"ProjectManagement.do?command=ProjectCenter&section=File_Library&pid=" + projectId + "&folderId=-1\">");
            } else {
              this.pageContext.getOut().write("<a href=\""+ link + "&folderId=-1\">");
            }
          }
          this.pageContext.getOut().write("Top Folder");
          if (showLastLink) {
            this.pageContext.getOut().write("</a>");
          }
        }
      }
    } catch (Exception e) {
      throw new JspException("FolderHierarchyHandler Error: " + e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }
}

