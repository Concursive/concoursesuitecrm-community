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

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    June 19, 2004
 *@version    $Id: HighlightElement.java,v 1.2 2004/07/21 19:00:46 mrajkowski
 *      Exp $
 */
public class HighlightElement {

  private int beginWord = -1;
  private int endWord = -1;
  private int start = -1;
  private int end = 1;


  /**
   *  Constructor for the HighlightElement object
   */
  public HighlightElement() { }


  /**
   *  Constructor for the HighlightElement object
   *
   *@param  bw  Description of the Parameter
   *@param  ew  Description of the Parameter
   *@param  s   Description of the Parameter
   *@param  e   Description of the Parameter
   */
  public HighlightElement(int bw, int s, int e, int ew) {
    beginWord = bw;
    start = s;
    end = e;
    endWord = ew;
    System.out.println("HighlightElement-> " + bw + "..." + s + "..." + e + "..." + ew);
  }


  /**
   *  Gets the beginWord attribute of the HighlightElement object
   *
   *@return    The beginWord value
   */
  public int getBeginWord() {
    return beginWord;
  }


  /**
   *  Sets the beginWord attribute of the HighlightElement object
   *
   *@param  tmp  The new beginWord value
   */
  public void setBeginWord(int tmp) {
    this.beginWord = tmp;
  }


  /**
   *  Sets the beginWord attribute of the HighlightElement object
   *
   *@param  tmp  The new beginWord value
   */
  public void setBeginWord(String tmp) {
    this.beginWord = Integer.parseInt(tmp);
  }


  /**
   *  Gets the endWord attribute of the HighlightElement object
   *
   *@return    The endWord value
   */
  public int getEndWord() {
    return endWord;
  }


  /**
   *  Sets the endWord attribute of the HighlightElement object
   *
   *@param  tmp  The new endWord value
   */
  public void setEndWord(int tmp) {
    this.endWord = tmp;
  }


  /**
   *  Sets the endWord attribute of the HighlightElement object
   *
   *@param  tmp  The new endWord value
   */
  public void setEndWord(String tmp) {
    this.endWord = Integer.parseInt(tmp);
  }


  /**
   *  Gets the start attribute of the HighlightElement object
   *
   *@return    The start value
   */
  public int getStart() {
    return start;
  }


  /**
   *  Sets the start attribute of the HighlightElement object
   *
   *@param  tmp  The new start value
   */
  public void setStart(int tmp) {
    this.start = tmp;
  }


  /**
   *  Sets the start attribute of the HighlightElement object
   *
   *@param  tmp  The new start value
   */
  public void setStart(String tmp) {
    this.start = Integer.parseInt(tmp);
  }


  /**
   *  Gets the end attribute of the HighlightElement object
   *
   *@return    The end value
   */
  public int getEnd() {
    return end;
  }


  /**
   *  Sets the end attribute of the HighlightElement object
   *
   *@param  tmp  The new end value
   */
  public void setEnd(int tmp) {
    this.end = tmp;
  }


  /**
   *  Sets the end attribute of the HighlightElement object
   *
   *@param  tmp  The new end value
   */
  public void setEnd(String tmp) {
    this.end = Integer.parseInt(tmp);
  }

}

