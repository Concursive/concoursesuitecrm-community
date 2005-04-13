/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.utils;

import java.util.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 19, 2004
 *@version    $Id: SearchHTMLUtils.java,v 1.2 2004/07/21 19:00:46 mrajkowski Exp
 *      $
 */
public class SearchHTMLUtils {

  public final static int MAX_WORDS = 15;
  public final static int MAX_CHARS = 50;
  public final static int MAX_PHRASES = 4;


  /**
   *  Description of the Method
   *
   *@param  terms    Description of the Parameter
   *@param  content  Description of the Parameter
   *@return          Description of the Return Value
   */
  public static String highlightText(ArrayList terms, String content) {
    HashMap positions = new HashMap();
    HashMap highlights = new HashMap();
    int low = -1;
    int high = -1;
    // Convert to lowercase, find index of word in content
    String body = content.toLowerCase();
    Iterator i = terms.iterator();
    while (i.hasNext()) {
      String term = (String) i.next();
      // Handle words with wildcard at end (should highlight to end of word)
      if (term.endsWith("*")) {
        term = term.substring(0, term.length() - 1);
      }
      // TODO: Implement rel*sed
      /* if (term.indexOf("*") > 0) {
        terms.add(term.substring(term.indexOf("*") + 1));
        term = term.substring(0, term.indexOf("*"));
      } */
      if (System.getProperty("DEBUG") != null) {
        System.out.println("SearchHTMLUtils-> Term: " + term);
      }
      // Find each index/range of occurrence
      int begin = 0;
      int pos = -1;
      while ((pos = body.indexOf(term, begin)) > -1) {
        // TODO: Check to see if pos already added, if so choose the longer value
        int beginWord = findBeginWord(body, pos);
        int endWord = findEndWord(body, pos + term.length());
        // Store the longest block
        if (positions.containsKey(new Integer(beginWord))) {
          HighlightElement tmp = (HighlightElement) positions.get(new Integer(beginWord));
          if (endWord > tmp.getEndWord()) {
            positions.put(new Integer(beginWord), new HighlightElement(beginWord, pos, pos + term.length() - 1, endWord));
          }
        } else {
          positions.put(new Integer(beginWord), new HighlightElement(beginWord, pos, pos + term.length() - 1, endWord));
        }
        // Store the highlight
        highlights.put(new Integer(pos), new HighlightElement(beginWord, pos, pos + term.length() - 1, endWord));
        if (low == -1 || beginWord < low) {
          low = beginWord;
        }
        if (high == -1 || endWord > high) {
          high = endWord;
        }
        begin = (pos + term.length());
      }
    }
    // print ranges of text, highlighting (exact) words from search terms
    StringBuffer sb = new StringBuffer();
    int startWord = -1;
    int startHighlight = -1;
    int endHighlight = -1;
    int endWord = -1;
    int phraseCount = 0;
    boolean isOpen = false;
    boolean isFirst = true;
    int outputCount = 0;
    for (int count = low; count <= high; count++) {
      Integer thisPosition = new Integer(count);
      // Step through the content, looking for highlighted items
      if (positions.containsKey(thisPosition)) {
        // See if an item has output around here
        HighlightElement element = (HighlightElement) positions.get(thisPosition);
        // Determine when printing will start
        if (startWord == -1 || (startWord != -1 && element.getBeginWord() > startWord)) {
          startWord = element.getBeginWord();
        }
        // Determine when printing will end
        if (endWord == -1 || (endWord != -1 && element.getEndWord() > endWord)) {
          endWord = element.getEndWord();
        }
      }
      if (highlights.containsKey(thisPosition)) {
        // See if an item has highlighting around here
        HighlightElement element = (HighlightElement) highlights.get(thisPosition);
        // Determine when highlighting will start
        if (startHighlight == -1 || element.getStart() > startHighlight) {
          startHighlight = element.getStart();
        }
        // Determine when highlighting will end
        if (endHighlight == -1 || element.getEnd() > endHighlight) {
          endHighlight = element.getEnd();
        }
      }
      // Begin highlighting
      if (startHighlight == count && !isOpen) {
        sb.append("//lt;b//gt;");
        isOpen = true;
      }
      // Append the character if in total range
      if (startWord > -1 && endWord > -1 && count >= startWord && count <= endWord) {
        if (!isFirst && (count == startWord)) {
          if (phraseCount > MAX_PHRASES) {
            return sb.toString();
          }
          ++phraseCount;
          sb.append(" ... ");
        }
        // Split up really long text
        ++outputCount;
        sb.append(content.charAt(count));
        if (outputCount > 100) {
          sb.append(" ");
        }
        if (content.charAt(count) == ' ') {
          outputCount = 0;
        }
      }
      // End highlighting
      if (endHighlight == count && isOpen) {
        sb.append("//lt;/b//gt;");
        isOpen = false;
        isFirst = false;
      }
    }
    return sb.toString();
  }


  /**
   *  Description of the Method
   *
   *@param  pos   Description of the Parameter
   *@param  body  Description of the Parameter
   *@return       Description of the Return Value
   */
  public static int findBeginWord(String body, int pos) {
    if (pos < MAX_CHARS) {
      return 0;
    }
    int words = 0;
    boolean startWord = false;
    for (int i = (pos - 1); i > 0; i--) {
      if (body.charAt(i) == '.' && body.charAt(i + 1) == ' ') {
        return (i + 2);
      }
      if (body.charAt(i) == '?' && body.charAt(i + 1) == ' ') {
        return (i + 2);
      }
      if (body.charAt(i) == '!' && body.charAt(i + 1) == ' ') {
        return (i + 2);
      }
      if (body.charAt(i) == ' ' && body.charAt(i + 1) == ' ') {
        return (i + 2);
      }
      if (!startWord && body.charAt(i) != ' ') {
        startWord = true;
        words++;
        if (words > MAX_WORDS) {
          return (i + 1);
        }
      }
      if (startWord && body.charAt(i) == ' ') {
        startWord = false;
      }
      if (words == 0 && (i + MAX_CHARS == pos)) {
        return i;
      }
    }
    return pos;
  }


  /**
   *  Description of the Method
   *
   *@param  body  Description of the Parameter
   *@param  pos   Description of the Parameter
   *@return       Description of the Return Value
   */
  public static int findEndWord(String body, int pos) {
    if (pos + MAX_CHARS > body.length()) {
      return body.length() - 1;
    }
    int words = 0;
    boolean endWord = true;
    for (int i = (pos + 1); i < body.length() - 1; i++) {
      if (body.charAt(i) == '.' && body.charAt(i + 1) == ' ') {
        if (i < body.length()) {
          return (i);
        } else {
          return (body.length() - 1);
        }
      }
      if (body.charAt(i) == '?' && body.charAt(i + 1) == ' ') {
        if (i < body.length()) {
          return (i);
        } else {
          return (body.length() - 1);
        }
      }
      if (body.charAt(i) == '!' && body.charAt(i + 1) == ' ') {
        if (i < body.length()) {
          return (i);
        } else {
          return (body.length() - 1);
        }
      }
      if (!endWord && body.charAt(i + 1) == ' ') {
        endWord = true;
        words++;
        if (words > MAX_WORDS) {
          if (i < body.length()) {
            return (i);
          } else {
            return (body.length() - 1);
          }
        }
      }
      if (endWord && body.charAt(i) != ' ') {
        endWord = false;
      }

      if (words == 0 && (i - MAX_CHARS == pos)) {
        if (i < body.length()) {
          return (i);
        } else {
          return (body.length() - 1);
        }
      }
    }
    return pos;
  }
}

