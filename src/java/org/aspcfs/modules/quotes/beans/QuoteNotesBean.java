package org.aspcfs.modules.quotes.beans;

import com.darkhorseventures.framework.beans.*;
import java.util.*;
import java.sql.*;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.DateUtils;
import org.aspcfs.modules.base.Dependency;
import org.aspcfs.modules.base.DependencyList;
import org.aspcfs.modules.troubletickets.base.*;
/**
 *  This represents a Quote in the Quote Entry System
 *
 *@author     ananth
 *@created    March 24, 2004
 *@version    $Id$
 */
public class QuoteNotesBean extends GenericBean {
  private int id = -1;
  private String notes = null;


  /**
   *  Gets the id attribute of the QuoteNotesBean object
   *
   *@return    The id value
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the notes attribute of the QuoteNotesBean object
   *
   *@return    The notes value
   */
  public String getNotes() {
    return notes;
  }


  /**
   *  Sets the id attribute of the QuoteNotesBean object
   *
   *@param  tmp  The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   *  Sets the id attribute of the QuoteNotesBean object
   *
   *@param  tmp  The new id value
   */
  public void setId(String tmp) {
    this.id = Integer.parseInt(tmp);
  }


  /**
   *  Sets the notes attribute of the QuoteNotesBean object
   *
   *@param  tmp  The new notes value
   */
  public void setNotes(String tmp) {
    this.notes = tmp;
  }
}

