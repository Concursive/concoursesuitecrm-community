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
