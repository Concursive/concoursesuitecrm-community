<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*" %>
<jsp:useBean id="InboxInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<%@ include file="initPage.jsp" %>
<br>

<a href="/MyCFSInbox.do?command=Inbox">Back to Inbox</a>
<p>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <tr class="">
    <td colspan=2 valign=center align=left width=50>
      Your message has been sent to the following recipients :
      <br><br>
      	<%
	Set s = finalContacts.keySet();
	Iterator i = s.iterator();
	int count = -1;
	while (i.hasNext()) {
		count++;
		Object st = finalContacts.get(i.next());
		String email = st.toString();
		if(email.startsWith("P:")){
			email = email.substring(2);
		}
	%>
		--> <strong><%=email%></strong><br>
	<%	
	}
	session.removeAttribute("finalContacts");
  session.removeAttribute("selectedContacts");
	%>
      </td>
  </tr>
</table>




