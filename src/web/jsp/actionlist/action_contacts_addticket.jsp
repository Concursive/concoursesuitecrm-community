<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<form name="addticket" action="TroubleTickets.do?command=Insert&auto-populate=true&actionSource=MyActionContacts" method="post">
<input type="submit" value="Insert" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Cancel" onClick="javascript:window.close();">
<input type="reset" value="Reset">	
<br>
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>

<%-- include basic troubleticket add form --%>
<%@ include file="../troubletickets/troubletickets_form.jsp" %>

<br>
<input type="submit" value="Insert" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Cancel" onClick="javascript:window.close();">
<input type="reset" value="Reset">
</form>
