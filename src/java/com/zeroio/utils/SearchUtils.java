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

import com.darkhorseventures.framework.actions.ActionContext;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.aspcfs.utils.web.PagedListInfo;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Utilities to break of a search string
 *
 * @author matt rajkowski
 * @version $Id: SearchUtils.java,v 1.3.26.1 2004/12/06 19:09:07 kbhoopal Exp
 *          $
 * @created June 11, 2004
 */
public class SearchUtils {

  private final static String DOUBLE_QUOTE = "\"";
  private final static String WHITESPACE_AND_QUOTES = " \t\r\n\"";
  private final static String QUOTES_ONLY = "\"";


  /**
   * Extracts the keywords into tokens, and then either concats them with AND
   * if all words are required, or leaves the tokens alone
   *
   * @param searchText Description of the Parameter
   * @param allWords   Description of the Parameter
   * @return Description of the Return Value
   */
  public static String parseSearchText(String searchText, boolean allWords) {
    StringBuffer sb = new StringBuffer();
    boolean returnTokens = true;
    String currentDelims = WHITESPACE_AND_QUOTES;
    StringTokenizer parser = new StringTokenizer(
        searchText, currentDelims, returnTokens);
    String token = null;
    boolean spacer = false;
    while (parser.hasMoreTokens()) {
      token = parser.nextToken(currentDelims);
      if (!isDoubleQuote(token)) {
        if (hasText(token)) {
          String gotToken = token.trim().toLowerCase();
          if ("and".equals(gotToken) || "or".equals(gotToken) || "not".equals(
              gotToken)) {
            if (sb.length() > 0) {
              sb.append(" ");
            }
            sb.append(gotToken.toUpperCase());
            spacer = true;
          } else {
            if (spacer) {
              if (sb.length() > 0) {
                sb.append(" ");
              }
              spacer = false;
            } else {
              if (sb.length() > 0) {
                if (allWords) {
                  sb.append(" AND ");
                } else {
                  sb.append(" ");
                }
              }
            }
            if (gotToken.indexOf(" ") > -1) {
              sb.append("\"" + gotToken + "\"");
            } else {
              sb.append(gotToken);
            }
          }
        }
      } else {
        currentDelims = flipDelimiters(currentDelims);
      }
    }
    return sb.toString();
  }


  /**
   * Description of the Method
   *
   * @param searchText Description of the Parameter
   * @return Description of the Return Value
   */
  public static ArrayList parseSearchTerms(String searchText) {
    ArrayList terms = new ArrayList();
    StringBuffer sb = new StringBuffer();
    boolean returnTokens = true;
    String currentDelims = WHITESPACE_AND_QUOTES;
    StringTokenizer parser = new StringTokenizer(
        searchText, currentDelims, returnTokens);
    String token = null;
    while (parser.hasMoreTokens()) {
      token = parser.nextToken(currentDelims);
      if (!isDoubleQuote(token)) {
        if (hasText(token)) {
          String gotToken = token.trim().toLowerCase();
          if ("and".equals(gotToken) || "or".equals(gotToken) || "not".equals(
              gotToken)) {

          } else {
            if (sb.length() > 0) {
              sb.append(" ");
            }
            sb.append(gotToken);
            terms.add(sb.toString());
            sb.setLength(0);
          }
        }
      } else {
        currentDelims = flipDelimiters(currentDelims);
      }
    }
    return terms;
  }


  /**
   * Gets the sharedSearcher attribute of the SearchUtils class
   *
   * @param context Description of the Parameter
   * @param index   Description of the Parameter
   * @return The sharedSearcher value
   * @throws Exception Description of the Exception
   */
  public static synchronized IndexSearcher getSharedSearcher(ActionContext context, Directory index) throws Exception {
    IndexSearcher searcher = (IndexSearcher) context.getServletContext().getAttribute(
        "indexSearcher");
    if (searcher == null) {
      searcher = (IndexSearcher) context.getServletContext().getAttribute(
          "indexSearcher");
      if (searcher == null) {
        searcher = new IndexSearcher(index);
        context.getServletContext().setAttribute("indexSearcher", searcher);
      }
    }
    return searcher;
  }


  /**
   * Description of the Method
   *
   * @param context        Description of the Parameter
   * @param queryString    Description of the Parameter
   * @param searcher       Description of the Parameter
   * @param searchBeanInfo Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public static void buildSearchResults(ActionContext context, String queryString, IndexSearcher searcher, PagedListInfo searchBeanInfo) throws Exception {
    long start = System.currentTimeMillis();
    Query query = QueryParser.parse(
        queryString, "contents", new StandardAnalyzer());
    Hits hits = searcher.search(query);
    //Sort sort = new Sort("type");
    //Hits hits = searcher.search(query, sort);
    long end = System.currentTimeMillis();
    context.getRequest().setAttribute("hits", hits);
    context.getRequest().setAttribute("duration", new Long(end - start));
    //System.out.println("Found " + hits.length() + " document(s) that matched query '" + queryString + "':");
    // configure the paged list info
    searchBeanInfo.setMaxRecords(hits.length());
    String tmpItemsPerPage = context.getRequest().getParameter("items");
    if (tmpItemsPerPage != null) {
      searchBeanInfo.setItemsPerPage(tmpItemsPerPage);
    }
    if ("true".equals(context.getRequest().getParameter("auto-populate"))) {
      searchBeanInfo.setCurrentOffset(0);
    }
  }


  /**
   * Description of the Method
   *
   * @param text Description of the Parameter
   * @return Description of the Return Value
   */
  private static boolean hasText(String text) {
    return (text != null && !text.trim().equals(""));
  }


  /**
   * Gets the doubleQuote attribute of the SearchUtils object
   *
   * @param text Description of the Parameter
   * @return The doubleQuote value
   */
  private static boolean isDoubleQuote(String text) {
    return text.equals(DOUBLE_QUOTE);
  }


  /**
   * Description of the Method
   *
   * @param delims Description of the Parameter
   * @return Description of the Return Value
   */
  private static String flipDelimiters(String delims) {
    String result = null;
    if (delims.equals(WHITESPACE_AND_QUOTES)) {
      result = QUOTES_ONLY;
    } else {
      result = WHITESPACE_AND_QUOTES;
    }
    return result;
  }

}

