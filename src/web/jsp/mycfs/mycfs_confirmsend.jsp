<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*" %>
<jsp:useBean id="InboxInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="finalContacts" class="java.util.HashMap" scope="session"/>
<%@ include file="../initPage.jsp" %>
<br>

<a href="<%=request.getParameter("return")%>">Back</a>
<p>
<br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" >
  <tr>
    <td colspan=2 valign=center align=left>
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




