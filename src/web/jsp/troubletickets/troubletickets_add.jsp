<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@ include file="../initPage.jsp" %>
<form name="addticket" action="TroubleTickets.do?command=Insert&auto-populate=true" method="post">
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="TroubleTickets.do">Help Desk</a> > 
Add Ticket
</td>
</tr>
</table>
<%-- End Trails --%>
<input type="submit" value="Insert" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
<br>
<%= showError(request, "actionError") %><iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<% if (request.getAttribute("closedError") != null) { %>
  <%= showAttribute(request, "closedError") %>
<%}%>
<%-- include basic troubleticket add form --%>
<%@ include file="troubletickets_include.jsp" %>
<br>
<input type="submit" value="Insert" name="Save" onClick="return checkForm(this.form)">
<input type="submit" value="Cancel" onClick="javascript:this.form.action='TroubleTickets.do?command=Home'">
</form>
