<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.*" %>
<html>
<script>
<% int level = Integer.parseInt(request.getParameter("level"));
  if(level > 0){
%>
  window.opener.loadCategories('<%= (level - 1)  %>');
 <% }else{ %>
  window.opener.loadTopCategories();
 <% } %>
  window.close();
</script>
</html>
