<% String includePage = (String) request.getAttribute("IncludePage"); %>          
<jsp:include page="<%= includePage %>" flush="true"/>
