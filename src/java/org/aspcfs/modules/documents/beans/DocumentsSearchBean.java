/*
 */
package org.aspcfs.modules.documents.beans;

import com.darkhorseventures.framework.beans.SearchBean;

/**
 * Contains the properties of a search form
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 17, 2004
 */
public class DocumentsSearchBean extends SearchBean {
  public final static int UNDEFINED = -1;
  // constant scopes
  public final static int ALL = 1;
  public final static int THIS = 2;
  // constant sections
  public final static int DOCUMENTS = 1;
  public final static int DETAILS = 2;
  // search form
  private int documentStoreId = -1;


  /**
   * Constructor for the SearchBean object
   */
  public DocumentsSearchBean() {
  }


  /**
   * Sets the scope attribute of the SearchBean object
   *
   * @param tmp The new scope value
   */
  public void setScope(String tmp) {
    // scope
    if (tmp.startsWith("this")) {
      scope = THIS;
    } else if (tmp.startsWith("all")) {
      scope = ALL;
    } else {
      scope = UNDEFINED;
    }
    // section
    if (tmp.endsWith("Documents")) {
      section = DOCUMENTS;
    } else if (tmp.endsWith("Details")) {
      section = DETAILS;
    } else {
      section = UNDEFINED;
    }
  }


  /**
   * Gets the projectId attribute of the SearchBean object
   *
   * @return The projectId value
   */
  public int getDocumentStoreId() {
    return documentStoreId;
  }


  /**
   * Sets the projectId attribute of the SearchBean object
   *
   * @param tmp The new projectId value
   */
  public void setDocumentStoreId(int tmp) {
    this.documentStoreId = tmp;
  }


  /**
   * Sets the projectId attribute of the SearchBean object
   *
   * @param tmp The new projectId value
   */
  public void setDocumentStoreId(String tmp) {
    this.documentStoreId = Integer.parseInt(tmp);
  }
}

