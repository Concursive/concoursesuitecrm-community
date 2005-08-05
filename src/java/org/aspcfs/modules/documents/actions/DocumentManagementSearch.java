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
package org.aspcfs.modules.documents.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.FileItemIndexer;
import com.zeroio.utils.SearchUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.FilterIndexReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.documents.base.DocumentStore;
import org.aspcfs.modules.documents.base.DocumentStoreIndexer;
import org.aspcfs.modules.documents.beans.DocumentsSearchBean;
import org.aspcfs.modules.documents.search.DocumentsSearchQuery;
import org.aspcfs.utils.web.PagedListInfo;

import java.sql.Connection;

/**
 * Actions for working with the search page
 *
 * @author
 * @version $Id$
 * @created
 */
public final class DocumentManagementSearch extends CFSModule {

  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public synchronized String executeCommandIndex(ActionContext context) {
    //setMaximized(context);
    if (!hasPermission(context, "admin-view")) {
      return ("PermissionError");
    }
    Connection db = null;
    Directory index = null;
    IndexWriter writer = null;
    try {
      index = getDirectory(context, true);
      // Create the index
      writer = new IndexWriter(index, new StandardAnalyzer(), true);
      // Add some data
      db = getConnection(context);
      DocumentStoreIndexer.add(writer, db, context);
      FileItemIndexer.add(
          writer, db, this.getPath(context, "documents"), context);
      // Finish up
      writer.optimize();
      // Update the shared searcher
      IndexSearcher searcher = new IndexSearcher(index);
      context.getServletContext().setAttribute("indexSearcher", searcher);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      freeConnection(context, db);
      // always close the index writer
      try {
        if (writer != null) {
          writer.close();
        }
        writer = null;
      } catch (Exception ie) {
      }
      try {
        if (index != null) {
          index.close();
        }
        index = null;
      } catch (Exception ie) {
      }
    }
    return "IndexOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandShowForm(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    return "SearchFormOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (getUserId(context) < 0) {
      return "PermissionError";
    }
    //setMaximized(context);
    DocumentsSearchBean search = (DocumentsSearchBean) context.getFormBean();
    PagedListInfo searchBeanInfo = this.getPagedListInfo(
        context, "searchBeanInfo");
    searchBeanInfo.setLink("DocumentManagementSearch.do?command=Default");
    Connection db = null;
    try {
      search.parseQuery();
      if (!search.isValid()) {
        return "SearchResultsERROR";
      }
      // Get the shared searcher
      IndexSearcher searcher = SearchUtils.getSharedSearcher(
          context, this.getDirectory(context));

      db = getConnection(context);

      if (System.getProperty("DEBUG") != null) {
        FilterIndexReader fir = new FilterIndexReader(
            IndexReader.open(getDirectory(context)));
        int nd = fir.numDocs();
        int i = 0;
        while (i < nd) {
          org.apache.lucene.document.Document d = fir.document(i++);
        }
      }

      String queryString = null;
      if (search.getScope() != DocumentsSearchBean.THIS) {
        search.setDocumentStoreId(-1);
      }
      // Check for project access and get acceptable query string
      if (search.getDocumentStoreId() > -1) {
        DocumentStore thisDocumentStore = new DocumentStore(
            db, search.getDocumentStoreId());
        context.getRequest().setAttribute("DocumentStore", thisDocumentStore);
        queryString = "(" + DocumentsSearchQuery.buildDocumentStoreSearchQuery(
            context, search, db, getUserId(context), search.getDocumentStoreId()) + ") AND (" + search.getParsedQuery() + ")";
      } else {
        queryString = "(" + DocumentsSearchQuery.buildDocumentStoreSearchQuery(
            context, search, db, getUserId(context), -1) + ") AND (" + search.getParsedQuery() + ")";
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println("Query String --> " + queryString);
      }
      // Execute the query and build search results
      SearchUtils.buildSearchResults(
          context, queryString, searcher, searchBeanInfo);

    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return "SearchResultsERROR";
    } finally {
      freeConnection(context, db);
    }
    return "SearchResultsOK";
  }


  /**
   * Description of the Method
   *
   * @param context Description of the Parameter
   * @return Description of the Return Value
   */
  public String executeCommandTips(ActionContext context) {
    return "TipsOK";
  }
}

