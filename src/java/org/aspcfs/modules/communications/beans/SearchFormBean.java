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
package org.aspcfs.modules.communications.beans;

import com.darkhorseventures.framework.beans.GenericBean;
import org.aspcfs.modules.communications.base.SearchCriteriaList;

/**
 * A bean for populating search criteria from a web form
 *
 * @author Wesley S. Gillette
 * @version $Id: SearchFormBean.java,v 1.1 2001/11/13 20:36:35 mrajkowski Exp
 *          $
 * @created November 7, 2001
 */
public class SearchFormBean extends GenericBean {

  private String groupName = null;
  private String searchCriteriaText = "";
  private int contactSource = -1;
  private int id = -1;
  private int owner = -1;
  private SearchCriteriaList searchCriteriaList = null;


  /**
   * Constructor for the SearchForm object
   *
   * @since 1.1
   */
  public SearchFormBean() {
  }


  /**
   * Sets the searchCriteriaText attribute of the SearchForm object
   *
   * @param tmp The new SearchCriteriaText value
   * @since 1.1
   */
  public void setSearchCriteriaText(String tmp) {
    this.searchCriteriaText = tmp;
    parseText();
  }


  /**
   * Sets the Id attribute of the SearchFormBean object
   *
   * @param id The new Id value
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * Sets the Id attribute of the SearchFormBean object
   *
   * @param id The new Id value
   */
  public void setId(String id) {
    this.id = Integer.parseInt(id);
  }


  /**
   * Sets the groupName attribute of the SearchFormBean object
   *
   * @param tmp The new groupName value
   */
  public void setGroupName(String tmp) {
    this.groupName = tmp;
  }


  /**
   * Sets the contactSource attribute of the SearchFormBean object
   *
   * @param tmp The new contactSource value
   */
  public void setContactSource(String tmp) {
    this.contactSource = Integer.parseInt(tmp);
  }


  /**
   * Sets the owner attribute of the SearchFormBean object
   *
   * @param tmp The new owner value
   */
  public void setOwner(int tmp) {
    this.owner = tmp;
  }


  /**
   * Sets the owner attribute of the SearchFormBean object
   *
   * @param tmp The new owner value
   */
  public void setOwner(String tmp) {
    this.owner = Integer.parseInt(tmp);
  }


  /**
   * Gets the Id attribute of the SearchFormBean object
   *
   * @return The Id value
   */
  public int getId() {
    return id;
  }


  /**
   * Gets the searchCriteriaText attribute of the SearchForm object
   *
   * @return The searchCriteriaText value
   * @since 1.1
   */
  public String getSearchCriteriaText() {
    return searchCriteriaText;
  }


  /**
   * Gets the SearchCriteriaList attribute of the SearchForm object
   *
   * @return The SearchCriteriaList value
   * @since 1.1
   */
  public SearchCriteriaList getSearchCriteriaList() {
    return searchCriteriaList;
  }


  /**
   * Gets the groupName attribute of the SearchFormBean object
   *
   * @return The groupName value
   */
  public String getGroupName() {
    return groupName;
  }


  /**
   * Gets the contactSource attribute of the SearchFormBean object
   *
   * @return The contactSource value
   */
  public int getContactSource() {
    return contactSource;
  }


  /**
   * Gets the owner attribute of the SearchFormBean object
   *
   * @return The owner value
   */
  public int getOwner() {
    return owner;
  }


  /**
   * Description of the Method
   *
   * @since 1.1
   */
  protected void parseText() {
    searchCriteriaList = new SearchCriteriaList(searchCriteriaText);
  }
}

