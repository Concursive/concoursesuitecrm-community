<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<%@ include file="../initPage.jsp" %>
<a href="<%= request.getParameter("return") %>">Back</a>
<p>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      Your message has been sent to the following recipients:
    </td>
  </tr>
<%
	Set s = finalContacts.keySet();
	Iterator i = s.iterator();
	int rowid = 0;
	while (i.hasNext()) {
		rowid = (rowid != 1?1:2);
		Object st = finalContacts.get(i.next());
		String email = st.toString();
		if(email.startsWith("P:")) {
			email = email.substring(2);
		}
%>
  <tr class="row<%= rowid %>">
    <td>
      <%= email %>
    </td>
  </tr>
<%
	}
	session.removeAttribute("finalContacts");
  session.removeAttribute("selectedContacts");
%>
</table>
