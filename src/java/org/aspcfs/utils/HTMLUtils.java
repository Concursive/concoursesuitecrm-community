package org.aspcfs.utils;

import javax.swing.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import javax.swing.text.*;
import java.io.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    February 5, 2003
 *@version    $Id$
 */
public class HTMLUtils {

  /**
   *  Strips tags from HTML text and returns a new string
   *
   *@param  html  Description of the Parameter
   *@return       Description of the Return Value
   */
  public static String htmlToText(String html) {
    if (html.length() == 0) {
      return html;
    }
    //Some things that will make the text look similar to the Html
    html = StringUtils.replace(html, "<br>", "\r\n");
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
    return st.toString();
  }
}

