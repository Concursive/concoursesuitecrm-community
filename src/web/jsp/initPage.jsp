<%@ page import="java.util.StringTokenizer" %>
<%!

public static String replace(String str, String o, String n) {
    boolean all = true;
    if (str != null && o != null && o.length() > 0 && n != null) { 
      StringBuffer result = null;
      int oldpos = 0;
      do {
          int pos = str.indexOf(o, oldpos);
          if (pos < 0)
              break;
          if (result == null)
              result = new StringBuffer();
          result.append(str.substring(oldpos, pos));
          result.append(n);
          pos += o.length();
          oldpos = pos;
      } while (all);
      if (oldpos == 0) {
          return str;
      } else {
          result.append(str.substring(oldpos));
          return new String(result);
      }
    } else {
      return str;
    }
  }
  
  public static String toString(String s) {
    if (s != null) {
      return(s);
    } else {
      return("");
    }
  }
  
  public static String toHtml(String s) {
    if (s != null) {
      if (s.trim().equals("")) {
        return ("&nbsp;");
      } else {
        return toHtmlValue(s);
      }
    } else {
      return("&nbsp;");
    }
  }
  
  public static String toHtmlValue(String s) {
    if (s != null) {
      String htmlReady = s.trim();
      htmlReady = replace(htmlReady, "\"", "&quot;");
      htmlReady = replace(htmlReady, "<", "&lt;");
      htmlReady = replace(htmlReady, ">", "&gt;");
      htmlReady = replace(htmlReady, "\r\n", "<br>");
      htmlReady = replace(htmlReady, "\n\r", "<br>");
      htmlReady = replace(htmlReady, "\n", "<br>");
      htmlReady = replace(htmlReady, "\r", "<br>");
      htmlReady = replace(htmlReady, "/&lt;", "<");
      htmlReady = replace(htmlReady, "/&gt;", ">");
      return(htmlReady);
    } else {
      return("");
    }
  }
  
  public static String toDateTimeString(java.sql.Timestamp inDate) {
    try {
      return java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.LONG).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toDateString(java.sql.Timestamp inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toDateString(java.sql.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toLongDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toFullDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String toMediumDateString(java.util.Date inDate) {
    try {
      return java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM).format(inDate);
    } catch (NullPointerException e) {
    }
    return "";
  }
  
  public static String sqlReplace(String s) {
    //s = replace(s, "<br>", "\r");
    String newString = "";
    char[] input = s.toCharArray();
    int arraySize = input.length;
    for (int i=0; i < arraySize; i++) {
      if (input[i] == '\'') {
        newString += "\\\'";
      } else if (input[i] == '\\') {
        newString += "\\\\";
      } else {
        newString += input[i];
      }
    }
    return newString;
  }
  
  public static String showAttribute(HttpServletRequest request, String errorEntry) {
    return "<font color='#006699'>" + toHtml((String)request.getAttribute(errorEntry)) + "</font>";
  }
  
  public static String showError(HttpServletRequest request, String errorEntry) {
    if (request.getAttribute(errorEntry) != null) {
      return "&nbsp;<br><img src=\"images/error.gif\" border=\"0\" align=\"absmiddle\"/> <font color='red'>" + toHtml((String)request.getAttribute(errorEntry)) + "</font><br>&nbsp;<br>";
    } else if (request.getParameter(errorEntry) != null) {
      return "&nbsp;<br><img src=\"images/error.gif\" border=\"0\" align=\"absmiddle\"/> <font color='red'>" + toHtml((String)request.getParameter(errorEntry)) + "</font><br>&nbsp;<br>";
    } else {
      return "&nbsp;";
    }
  }
  
  public static boolean hasText(String in) {
    return (in != null && !("".equals(in)));
  }
  
  public static String addHiddenParams(HttpServletRequest request, String tmp){
    StringBuffer sb = new StringBuffer();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while(tokens.hasMoreTokens()){
       String param = tokens.nextToken();
       if(request.getParameter(param) != null){
        sb.append("<input type=\"hidden\" name=\"" + param + "\" value=\"" + request.getParameter(param) + "\">");
       }
    }
    return sb.toString();
  }
  
  public static String addLinkParams(HttpServletRequest request, String tmp){
    StringBuffer sb = new StringBuffer();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while(tokens.hasMoreTokens()){
     String param = tokens.nextToken();
     if(request.getParameter(param) != null){
       sb.append("&" + param + "=" + request.getParameter(param));
     }
    }
    return sb.toString();
  }
  
  public static boolean isPopup(HttpServletRequest request){
    return "true".equals(request.getParameter("popup"));
  }
  
  public static boolean isInLinePopup(HttpServletRequest request){
    return "inline".equals(request.getParameter("popupType"));
  }
%>  
