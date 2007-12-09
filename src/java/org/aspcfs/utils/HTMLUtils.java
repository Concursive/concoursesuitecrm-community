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
package org.aspcfs.utils;


/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created February 5, 2003
 */
public class HTMLUtils {

  /**
   * Strips tags from HTML text and returns a new string
   *
   * @param html Description of the Parameter
   * @return Description of the Return Value
   */
  public static String htmlToText(String html) {
    if (html.length() == 0) {
      return html;
    }
    //Some things that will make the text look similar to the Html
    html = StringUtils.replace(html, "&nbsp;", " ");
    html = StringUtils.replace(html, "<br>", "\r\n");
    html = StringUtils.replace(html, "<br />", "\r\n");
    //handle paragraphs
    html = StringUtils.replace(html, "<p>", "");
    html = StringUtils.replace(html, "</p>", "\r\n\r\n");
    //handle lists
    html = StringUtils.replace(html, "<ol>", "\r\n");
    html = StringUtils.replace(html, "<ul>", "\r\n");
    html = StringUtils.replace(html, "<li>", "* ");
    html = StringUtils.replace(html, "</li>", "\r\n");
    html = StringUtils.replace(html, "</ul>", "\r\n");
    html = StringUtils.replace(html, "</ol>", "\r\n");
    //Now strip the rest of the tags
    StringBuffer st = new StringBuffer();
    int max = html.length();
    boolean tag = false;
    for (int i = 0; i < max; i++) {
      char c = html.charAt(i);
      if (!tag && c == '<') {
        tag = true;
      }
      if (!tag) {
        st.append(c);
      }
      if (tag && c == '>') {
        tag = false;
      }
    }
    String finalHtml = st.toString();
    finalHtml = StringUtils.replace(finalHtml, "&lt;", "<");
    finalHtml = StringUtils.replace(finalHtml, "&gt;", ">");
    finalHtml = StringUtils.replace(finalHtml, "&amp;", "&");
    return finalHtml;
  }
}

