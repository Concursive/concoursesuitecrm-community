/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski and Team Elements
 */
package com.zeroio.utils;

import java.util.*;

/**
 *  Utilities to break of a search string
 *
 *@author     matt rajkowski
 *@created    June 11, 2004
 *@version    $Id$
 */
public class SearchUtils {

  private final static String DOUBLE_QUOTE = "\"";
  private final static String WHITESPACE_AND_QUOTES = " \t\r\n\"";
  private final static String QUOTES_ONLY = "\"";


  /**
   *  Extracts the keywords into tokens, and then either concats them with AND
   *  if all words are required, or leaves the tokens alone
   *
   *@param  searchText  Description of the Parameter
   *@param  allWords    Description of the Parameter
   *@return             Description of the Return Value
   */
  public static String parseSearchText(String searchText, boolean allWords) {
    StringBuffer sb = new StringBuffer();
    boolean returnTokens = true;
    String currentDelims = WHITESPACE_AND_QUOTES;
    StringTokenizer parser = new StringTokenizer(searchText, currentDelims, returnTokens);
    String token = null;
    boolean spacer = false;
    while (parser.hasMoreTokens()) {
      token = parser.nextToken(currentDelims);
      if (!isDoubleQuote(token)) {
        if (hasText(token)) {
          String gotToken = token.trim().toLowerCase();
          if ("and".equals(gotToken) || "or".equals(gotToken) || "not".equals(gotToken)) {
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
   *  Description of the Method
   *
   *@param  searchText  Description of the Parameter
   *@return             Description of the Return Value
   */
  public static ArrayList parseSearchTerms(String searchText) {
    ArrayList terms = new ArrayList();
    StringBuffer sb = new StringBuffer();
    boolean returnTokens = true;
    String currentDelims = WHITESPACE_AND_QUOTES;
    StringTokenizer parser = new StringTokenizer(searchText, currentDelims, returnTokens);
    String token = null;
    while (parser.hasMoreTokens()) {
      token = parser.nextToken(currentDelims);
      if (!isDoubleQuote(token)) {
        if (hasText(token)) {
          String gotToken = token.trim().toLowerCase();
          if ("and".equals(gotToken) || "or".equals(gotToken) || "not".equals(gotToken)) {

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
   *  Description of the Method
   *
   *@param  text  Description of the Parameter
   *@return       Description of the Return Value
   */
  private static boolean hasText(String text) {
    return (text != null && !text.trim().equals(""));
  }


  /**
   *  Gets the doubleQuote attribute of the SearchUtils object
   *
   *@param  text  Description of the Parameter
   *@return       The doubleQuote value
   */
  private static boolean isDoubleQuote(String text) {
    return text.equals(DOUBLE_QUOTE);
  }


  /**
   *  Description of the Method
   *
   *@param  delims  Description of the Parameter
   *@return         Description of the Return Value
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

