<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<%@ include file="../initPage.jsp" %>
<p>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th>
      Your message has been sent to the following recipients:
    </th>
  </tr>
<%
	Set s = finalContacts.keySet();
	Iterator i = s.iterator();
	int rowid = 0;
	while (i.hasNext()) {
		rowid = (rowid != 1?1:2);
    Integer hashKey = (Integer) i.next();
    int contactId = hashKey.intValue();
		Object st = finalContacts.get(hashKey);
		String email = st.toString();
		if(email.startsWith("P:")) {
			email = email.substring(2);
		}
%>
  <tr class="row<%= rowid %>">
    <td>
      <%= toHtml(email) %> <%= showAttribute(request, "contact" + contactId) %>
    </td>
  </tr>
<%
	}
	session.removeAttribute("finalContacts");
  session.removeAttribute("selectedContacts");
%>
</table>
