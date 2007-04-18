<%--
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  --%>
<%--
  User: dharmas
  Date: Apr 6, 2007
  Time: 3:04:27 PM
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String includePage = "../../graphs/" + (String) request.getAttribute("GraphFileName") + ".map";
%>

<jsp:include page="<%= includePage %>" flush="true"/>
<html>
  <head><title>Folder Graph Page</title></head>
  <body>
  <table width="275" cellpadding="3" cellspacing="0" border="0" class="pagedList">  
  	<tr>
     <td>
          <img src="graphs/<%= request.getAttribute("GraphFileName") %>.jpg" width="600" height="400" border="0" usemap="#<%= request.getAttribute("GraphFileName") %>">
     </td>
  	</tr>
  </table>
  </body>
</html>