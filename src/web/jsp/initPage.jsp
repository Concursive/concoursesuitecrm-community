<%@ page import="java.util.*,java.text.*" %>
<%@ page import="org.aspcfs.controller.ApplicationPrefs" %>
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
    
  public static String toJavaScript(String s) {
    if (s != null) {
      String jsReady = s.trim();
      jsReady = replace(jsReady, ", ", ",");
      jsReady = replace(jsReady, " ,", ",");
      jsReady = replace(jsReady, " , ", ",");
      jsReady = replace(jsReady, "\\", "\\\\");
      jsReady = replace(jsReady, "'", "\\'");
      jsReady = replace(jsReady, "\"", "\\\"");
      jsReady = replace(jsReady, " ", "&nbsp;");
      return(jsReady);
    } else {
      return("");
    }
  }
  
  public static String toHtmlValue(int tmp) {
    return (toHtmlValue(String.valueOf(tmp)));
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
    
    private static String getLongTimestamp(java.sql.Timestamp tmp) {
      SimpleDateFormat formatter = new SimpleDateFormat ("MMMMM d, yyyy 'at' hh:mm a");
      return(formatter.format(tmp));
    }
    
    private static String getShortDate(java.sql.Date tmp) {
      SimpleDateFormat formatter = new SimpleDateFormat ("MM/dd/yy");
      return(formatter.format(tmp));
    }
    
    private static String getShortDate(java.sql.Timestamp tmpTimestamp) {
      SimpleDateFormat formatter1 = new SimpleDateFormat ("MM/dd/yy");
      return(formatter1.format(tmpTimestamp));
    }
    
    public static String showAttribute(HttpServletRequest request, String errorEntry) {
      return "<font color='#006699'>" + toHtml((String)request.getAttribute(errorEntry)) + "</font>";
    }
    
    public static String showError(HttpServletRequest request, String errorEntry) {
      return showError(request, errorEntry, true);
    }

    public static String showWarning(HttpServletRequest request, String warningEntry) {
      if (request.getAttribute(warningEntry) != null) {
        return "&nbsp;<br /><img src=\"images/box-hold.gif\" border=\"0\" align=\"absmiddle\"/><font color='#FF9933'>" + toHtml((String)request.getAttribute(warningEntry)) + "</font><br>";
      }else if(request.getParameter(warningEntry) != null){
        return "&nbsp;<br /><img src=\"images/box-hold.gif\" border=\"0\" align=\"absmiddle\"/><font color='#FF9933'>" + toHtml((String)request.getParameter(warningEntry)) + "</font><br>";
      }else{
        return "&nbsp;";
      }
    }

    public static String showWarningAttribute(HttpServletRequest request, String warningEntry) {
      if (request.getAttribute(warningEntry) != null) {
        return "<font color='#FF9933'>" + toHtml((String)request.getAttribute(warningEntry)) + "</font>";
      }else if(request.getParameter(warningEntry) != null){
        return "<font color='#FF9933'>" + toHtml((String)request.getParameter(warningEntry)) + "</font>";
      }else{
        return "";
      }
    }
    
    public static String showError(HttpServletRequest request, String errorEntry, boolean showSpace) {
      if (request.getAttribute(errorEntry) != null) {
        return (showSpace ? "&nbsp;<br>" : "") + "<img src=\"images/error.gif\" border=\"0\" align=\"absmiddle\"/> <font color='red'>" + toHtml((String)request.getAttribute(errorEntry)) + "</font><br>&nbsp;<br>";
      } else if (request.getParameter(errorEntry) != null) {
        return (showSpace ? "&nbsp;<br>" : "") + "<img src=\"images/error.gif\" border=\"0\" align=\"absmiddle\"/> <font color='red'>" + toHtml((String)request.getParameter(errorEntry)) + "</font><br>&nbsp;<br>";
      } else {
        return (showSpace ? "&nbsp;" : "");
      }
    }
    
    public static boolean hasText(String in) {
      return (in != null && !("".equals(in)));
    }

    public static String toDateTimeString(java.sql.Timestamp inDate) {
      return toDateTimeString(inDate, "");
    }
    
    public static String toDateTimeString(java.sql.Timestamp inDate, String defaultText) {
      try {
        return java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.LONG).format(inDate);
      } catch (NullPointerException e) {
      }
      return defaultText;
    }

  public static String addHiddenParams(HttpServletRequest request, String tmp){
    StringBuffer sb = new StringBuffer();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while (tokens.hasMoreTokens()){
       String param = tokens.nextToken();
       if (request.getParameter(param) != null){
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
     if (request.getParameter(param) != null){
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
  
  public static String addContainerParams(HttpServletRequest request, String tmp){
    StringBuffer sb = new StringBuffer();
    StringTokenizer tokens = new StringTokenizer(tmp, "|");
    while(tokens.hasMoreTokens()){
     String param = tokens.nextToken();
     if (request.getParameter(param) != null){
       sb.append("|" + param + "=" + request.getParameter(param));
     }
    }
    return sb.toString();
  }
    
    public static String getServerPort(HttpServletRequest request) {
      int port = request.getServerPort();
      if (port != 80 && port != 443) {
        return ":" + String.valueOf(port);
      }
      return "";
    }
    
    public static String getServerUrl(HttpServletRequest request) {
      return request.getServerName() + getServerPort(request) + request.getContextPath();
    }
  
  public static String getPref(ServletContext context, String param) {
    ApplicationPrefs prefs = (ApplicationPrefs) context.getAttribute("applicationPrefs");
    if (prefs != null) {
      return prefs.get(param);
    } else {
      return null;
    }
  }
%>
