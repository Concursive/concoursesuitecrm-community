package org.aspcfs.modules.mycfs.base;

import java.util.*;
import org.aspcfs.modules.quotes.base.Quote;
import org.aspcfs.modules.quotes.base.QuoteList;

/**
 *  Description of the Class
 *
 *@author     Mathur
 *@created    July 16, 2004
 *@version    $id:exp$
 */
public class QuoteEventList {
  QuoteList todaysQuotes = new QuoteList();
  int size = 0;


  /**
   *  Sets the size attribute of the TaskEventList object
   *
   *@param  size  The new size value
   */
  public void setSize(Integer size) {
    this.size = size.intValue();
  }


  /**
   *  Sets the todaysQuotes attribute of the QuoteEventList object
   *
   *@param  tmp  The new todaysQuotes value
   */
  public void setTodaysQuotes(QuoteList tmp) {
    this.todaysQuotes = tmp;
  }


  /**
   *  Gets the todaysQuotes attribute of the QuoteEventList object
   *
   *@return    The todaysQuotes value
   */
  public QuoteList getTodaysQuotes() {
    return todaysQuotes;
  }


  /**
   *  Gets the size attribute of the TaskEventList object
   *
   *@return    The size value
   */
  public int getSize() {
    return size;
  }


  /**
   *  Gets the sizeString attribute of the TaskEventList object
   *
   *@return    The sizeString value
   */
  public String getSizeString() {
    return String.valueOf(size);
  }


  /**
   *  Adds a feature to the Event attribute of the QuoteEventList object
   *
   *@param  thisQuote  The feature to be added to the Event attribute
   */
  public void addEvent(Quote thisQuote) {
    if (thisQuote != null) {
      todaysQuotes.add(thisQuote);
    }
  }
}

