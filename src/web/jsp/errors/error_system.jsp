<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description:
  --%>
<%@page import="java.io.*"%>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color='red'>An Error Has Occurred</font>
<hr color="#BFBFBB" noshade>
Please report the following error to <a href="http://www.darkhorsecrm.com" target="_blank">Dark Horse CRM</a><p>
You may be able to hit the back button on your browser, review your selection, and try your request again.<p>
<pre>
<%
  Object errorObject = request.getAttribute("Error");
  
  String errorMessage = "";
  
  if (errorObject instanceof java.lang.String) {
    errorMessage = (String)errorObject;
  } else if (errorObject instanceof java.lang.Exception) {
    Exception e = (Exception)errorObject;
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    e.printStackTrace(new PrintStream(outStream));
    errorMessage = outStream.toString();
  }
  if (!errorMessage.equals("")) {
%>
The actual error is:<br><br><%= errorMessage %>
<%
  } else {
%>
An error message was not provided by this action.
<%
  }
%>
</pre>
