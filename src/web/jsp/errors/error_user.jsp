<%@ page  import="java.util.*" %>
<jsp:useBean id="errors" class="java.util.HashMap" scope="request"/>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color='red'>An Error Has Occurred</font>
<hr color="#BFBFBB" noshade>
<%
  String errorMessage = (String)request.getAttribute("Error");
  if (errorMessage != null) {
%>
<%= errorMessage %>
<%
  } else {
    Iterator errorList = errors.values().iterator();
    while (errorList.hasNext()) {
%>
  <%= (String)errorList.next() %><br>
<%  
    }
%>
No further information is available.
<%
  }
%>
