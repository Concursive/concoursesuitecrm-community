//Copyright 2001 Dark Horse Ventures

package org.aspcfs.modules.communications.beans;

import com.darkhorseventures.framework.beans.*;
import org.aspcfs.modules.communications.base.SearchCriteriaList;
import java.util.*;
import java.sql.*;

/**
 *  A bean for populating search criteria from a web form
 *
 *@author     Wesley S. Gillette
 *@created    November 7, 2001
 *@version    $Id: SearchFormBean.java,v 1.1 2001/11/13 20:36:35 mrajkowski Exp
 *      $
 */
public class SearchFormBean extends GenericBean {

  private String groupName = null;
  private String searchCriteriaText = "";
  private int contactSource = -1;
  private int id = -1;
  private int owner = -1;
  private SearchCriteriaList searchCriteriaList = null;


  /**
   *  Constructor for the SearchForm object
   *
   *@since    1.1
   */
  public SearchFormBean() { }


  /**
   *  Sets the searchCriteriaText attribute of the SearchForm object
   *
   *@param  tmp  The new SearchCriteriaText value
   *@since       1.1
   */
  public void setSearchCriteriaText(String tmp) {
    this.searchCriteriaText = tmp;
    parseText();
  }


  /**
   *  Sets the Id attribute of the SearchFormBean object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   *  Sets the Id attribute of the SearchFormBean object
   *
   *@param  id  The new Id value
   *@since
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   *  Sets the groupName attribute of the SearchFormBean object
   *
   *@param  tmp  The new groupName value
   */
  public void setGroupName(String tmp) {
    this.groupName = tmp;
  }


  /**
   *  Sets the contactSource attribute of the SearchFormBean object
   *
   *@param  tmp  The new contactSource value
   */
  public void setContactSource(String tmp) {
    this.contactSource = Integer.parseInt(tmp);
  }


  /**
   *  Sets the owner attribute of the SearchFormBean object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   *  Sets the owner attribute of the SearchFormBean object
   *
   *@param  tmp  The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   *  Gets the Id attribute of the SearchFormBean object
   *
   *@return    The Id value
   *@since
   */
  public int getId() {
    return id;
  }


  /**
   *  Gets the searchCriteriaText attribute of the SearchForm object
   *
   *@return    The searchCriteriaText value
   *@since     1.1
   */
  public String getSearchCriteriaText() {
    return searchCriteriaText;
  }


  /**
   *  Gets the SearchCriteriaList attribute of the SearchForm object
   *
   *@return    The SearchCriteriaList value
   *@since     1.1
   */
  public SearchCriteriaList getSearchCriteriaList() {
    return searchCriteriaList;
  }


  /**
   *  Gets the groupName attribute of the SearchFormBean object
   *
   *@return    The groupName value
   */
  public String getGroupName() {
    return groupName;
  }


  /**
   *  Gets the contactSource attribute of the SearchFormBean object
   *
   *@return    The contactSource value
   */
  public int getContactSource() {
    return contactSource;
  }


  /**
   *  Gets the owner attribute of the SearchFormBean object
   *
   *@return    The owner value
   */
  public int getOwner() {
    return owner;
  }



  /**
   *  Description of the Method
   *
   *@since    1.1
   */
  protected void parseText() {
    searchCriteriaList = new SearchCriteriaList(searchCriteriaText);
  }
}

