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
package org.aspcfs.modules.mycfs.base;

import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteList;

/**
 * Description of the Class
 *
 * @author Mathur
 * @version $id:exp$
 * @created July 16, 2004
 */
public class QuoteEventList {
  QuoteList todaysQuotes = new QuoteList();
  int size = 0;


  /**
   * Sets the size attribute of the TaskEventList object
   *
   * @param size The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   * Sets the todaysQuotes attribute of the QuoteEventList object
   *
   * @param tmp The new todaysQuotes value
   */
  public void setTodaysQuotes(QuoteList tmp) {
    this.todaysQuotes = tmp;
  }


  /**
   * Gets the todaysQuotes attribute of the QuoteEventList object
   *
   * @return The todaysQuotes value
   */
  public QuoteList getTodaysQuotes() {
    return todaysQuotes;
  }


  /**
   * Gets the size attribute of the TaskEventList object
   *
   * @return The size value
   */
  public int getSize() {
    return size;
  }


  /**
   * Gets the sizeString attribute of the TaskEventList object
   *
   * @return The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   * Adds a feature to the Event attribute of the QuoteEventList object
   *
   * @param thisQuote The feature to be added to the Event attribute
   */
  public void addEvent(Quote thisQuote) {
    if (thisQuote != null) {
      todaysQuotes.add(thisQuote);
    }
  }
}

