<font color='red'>An Error Has Occurred</font>
<hr color="#BFBFBB" noshade>
<%
  String errorMessage = (String)request.getAttribute("Error");
  if (errorMessage != null) {
%>
<%= errorMessage %>
<%
  } else {
%>
No further information is available.
<%
  }
%>
