<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<%@page import="java.io.*, org.aspcfs.modules.base.Constants"%>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color='red'><dhv:label name="errors.anErrorHasOccured">An Error Has Occurred</dhv:label></font>
<hr color="#BFBFBB" noshade>
<dhv:label name="errors.reportErrorTo">Please report the following error to</dhv:label> <a href="http://www.centriccrm.com" target="_blank">Centric CRM</a><p>
<dhv:label name="errors.backButtonBrowser.text">You may be able to hit the back button on your browser, review your selection, and try your request again.</dhv:label><p>
<pre>
<%
  Object errorObject = request.getAttribute("Error");
  
  String errorMessage = "";
  
  if (errorObject instanceof java.lang.String) {
    errorMessage = (String)errorObject;
  } else if (errorObject instanceof java.lang.Exception) {
    Exception e = (Exception)errorObject;
    if (!e.getMessage().equals(Constants.NOT_FOUND_ERROR)){
      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      e.printStackTrace(new PrintStream(outStream));
      errorMessage = outStream.toString();
    } else{
      errorMessage = "This Object does not exist or has been deleted";
    }
  }
  if (!errorMessage.equals("")) {
%>
<dhv:label name="errors.actualErrorIs.colon" param='<%= "errorMessage="+errorMessage %>'>The actual error is:<br /><br /><%= errorMessage %></dhv:label>
<%
  } else {
%>
<dhv:label name="errors.noErrorMessageFromAction">An error message was not provided by this action.</dhv:label>
<%
  }
%>
</pre>
