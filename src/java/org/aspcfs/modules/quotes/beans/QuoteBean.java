package org.aspcfs.modules.quotes.beans;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.troubletickets.base.*;

/**
 *  This bean is used to capture the fields modified by the designer
 *
 *@author     partha
 *@created    May 4, 2004
 *@version    $Id$
 */
public class QuoteBean extends GenericBean {
  private int id = -1;
  private String notes = null;
  private Timestamp expiryDate = null;


  /**
   *  Constructor for the QuoteBean object
   */
  public QuoteBean() { }


  /**
   *  Sets the id attribute of the QuoteBean object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteBean object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the notes attribute of the QuoteBean object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }


  /**
   *  Sets the expiryDate attribute of the QuoteBean object
   *
   *@param  tmp  The new expiryDate value
   */
  public void setExpiryDate(Timestamp tmp) {
    this.expiryDate = tmp;
  }


  /**
   *  Sets the expiryDate attribute of the QuoteBean object
   *
   *@param  tmp  The new expiryDate value
   */
  public void setExpiryDate(String tmp) {
    this.expiryDate = DateUtils.parseTimestampString(tmp);
  }


  /**
   *  Gets the id attribute of the QuoteBean object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the notes attribute of the QuoteBean object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Gets the expiryDate attribute of the QuoteBean object
   *
   *@return    The expiryDate value
   */
  public Timestamp getExpiryDate() {
    return expiryDate;
  }


  /**
   *  Gets the timeZoneParams attribute of the QuoteBean class
   *
   *@return    The timeZoneParams value
   */
  public static ArrayList getTimeZoneParams() {

    ArrayList thisList = new ArrayList();
    thisList.add("expiryDate");
    return thisList;
  }

}

