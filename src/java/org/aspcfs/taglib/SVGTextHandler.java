/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.ArrayList;
import java.io.*;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.SVGUtils;
import javax.servlet.*;
import java.util.*;

/**
 *  This tag will dynamically build a graphic based on the specified svg
 *  template. If the file already exists it will not generate a new one.
 *
 *@author     ananth
 *@created    April 2, 2004
 *@version    $Id: SVGTextHandler.java,v 1.2 2004/05/04 15:46:14 mrajkowski Exp
 *      $
 */
public class SVGTextHandler extends TagSupport {

  private final static String fs = System.getProperty("file.separator");
  private String template = null;
  private String text = null;
  private final static String allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-.";


  /**
   *  Sets the template attribute of the SVGTextHandler object
   *
   *@param  tmp  The new template value
   */
  public void setTemplate(String tmp) {
    this.template = tmp;
  }


  /**
   *  Sets the text attribute of the SVGTextHandler object
   *
   *@param  tmp  The new text value
   */
  public void setText(String tmp) {
    this.text = tmp;
  }



  /**
   *  Description of the Method
   *
   *@param  str  Description of the Parameter
   *@return      Description of the Return Value
   */
  private static String toSafeName(String str) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < str.length(); ++i) {
      if (allowed.indexOf(str.charAt(i)) > -1) {
        sb.append(str.charAt(i));
      } else {
        sb.append("_");
      }
    }
    return sb.toString();
  }


  /**
   *  Gets the version attribute of the SVGTextHandler object
   *
   *@param  context  Description of the Parameter
   *@param  svgPath  Description of the Parameter
   *@return          The version value
   */
  private String getVersion(ServletContext context, String svgPath) {
    HashMap svgMap = (HashMap) context.getAttribute("svgTextHandlerMap");
    if (svgMap == null) {
      synchronized (this) {
        svgMap = (HashMap) context.getAttribute("svgTextHandlerMap");
        if (svgMap == null) {
          svgMap = new HashMap();
          context.setAttribute("svgTextHandlerMap", svgMap);
        }
      }
    }
    String version = (String) svgMap.get(template);
    if (version == null) {
      synchronized (this) {
        version = (String) svgMap.get(template);
        if (version == null) {
          try {
            String tmp = StringUtils.loadText(svgPath + template + ".version");
            version = "_" + tmp.substring(0, tmp.indexOf(System.getProperty("line.separator")));
          } catch (Exception e) {
            version = "";
          }
        }
        svgMap.put(template, version);
      }
    }
    return version;
  }


  /**
   *  Using the specified properties, an SVG is parsed and a bitmap image is
   *  output for display in a web browser.
   *
   *@return                   Description of the Return Value
   *@exception  JspException  Description of the Exception
   */
  public int doStartTag() throws JspException {
    try {
      // See if the file already exists
      String path = pageContext.getServletContext().getRealPath("/");
      String templateName = StringUtils.replace(template, " ", "_");
      String textName = toSafeName(text);
      String version = getVersion(pageContext.getServletContext(), path + "WEB-INF" + fs + "svg" + fs);
      String imageFilename = templateName + "_" + textName + version + ".jpg";
      String templateFilename = template + ".svg";
      File imageFile = new File(path + "svg" + fs + imageFilename);
      if (!imageFile.exists()) {
        // Generate an SVG based on the supplied settings
        File SVGFile = new File(path + "WEB-INF" + fs + "svg" + fs + templateFilename);
        if (SVGFile.exists()) {
          SVGUtils svg = new SVGUtils(SVGFile.toURL().toString());
          svg.setText(text);
          File imageDirectory = new File(path + "svg");
          if (!imageDirectory.exists()) {
            imageDirectory.mkdir();
          }
          svg.saveAsJPEG(imageFile);
        }
      }
      this.pageContext.getOut().write("<img src=\"svg/" + imageFilename + "\" border=\"0\"/>");
    } catch (Exception e) {
      System.err.println("EXCEPTION: SVGTextHandler-> " + e.getMessage());
    }
    return SKIP_BODY;
  }


  /**
   *  Description of the Method
   *
   *@return    Description of the Return Value
   */
  public int doEndTag() {
    return EVAL_PAGE;
  }

}

