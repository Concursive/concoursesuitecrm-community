<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.mycfs.base.*,org.aspcfs.modules.contacts.base.Contact,org.aspcfs.modules.base.Filter,org.aspcfs.modules.base.EmailAddress" %>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedContacts" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popContacts.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	function init() {
	<% 
		String lastName = request.getParameter("lastName") ;
		String firstName = request.getParameter("firstName");
		if ((lastName == null && firstName == null) || ("".equals(lastName.trim()) && "".equals(firstName.trim()))) {
	%>
			document.contactListView.lastName.value = "Last Name";
			document.contactListView.firstName.value = "First Name";
	<%
		}
	%>	
	}
	
	function clearSearchFields(clear, field) {
		if (clear) {
			// Clear the search fields since clear button was clicked
			document.contactListView.lastName.value = "Last Name";
			document.contactListView.firstName.value = "First Name";
		} else {
			// One of the search fields recieved focus
			if (field.value == "Last Name" || field.value == "First Name") {
				field.value = "" ;
			}
		}
	}
</SCRIPT>
<%
  if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) {
    String source = "";
    if(request.getParameter("source") != null){
      source = request.getParameter("source");
    }
%>
<%-- Navigating the contact list, not the final submit --%>
<body onLoad="init()">
<form name="contactListView" method="post" action="ContactsList.do?command=ContactList">
  <table cellpadding="6" cellspacing="0" width="100%" border="0">
		<tr>
			<td align="center" valign="center" bgcolor="#d3d1d1">
				<strong>Search</strong>
				<input type="text" name="lastName" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("lastName")) %>"> <b>, </b>
				<input type="text" name="firstName" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("firstName")) %>">
				<input type="submit" value="search">
				<input type="button" value="clear" onClick="clearSearchFields(true, '')">
			</td>
		</tr>
	</table>
	&nbsp;<br>
	<center><%= ContactListInfo.getAlphabeticalPageLinks("setFieldSubmit","contactListView") %></center>
  <input type="hidden" name="letter">
  
  <%@ include file="contactlist_include.jsp" %>
  
<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','contactListView');">
  <input type="button" value="Cancel" onClick="javascript:window.close();">
  [<a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Check All</a>]
  [<a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');">Clear All</a>]
  <br>
<%}else{%>
  <input type="button" value="Cancel" onClick="javascript:window.close();">
<%}%>
  &nbsp;<br>
</form>
<%} else {%>
<%-- The final submit --%>
  <% if ("true".equals((String) request.getParameter("campaign"))) { %>
  <body onLoad="javascript:setParentListCampaign(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%=User.getBrowserId()%>');window.close()">
  <%} else {%>
  <body onLoad="javascript:setParentList(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">
  <%}%>
  <script>recipientEmails = new Array();recipientIds = new Array();</script>
<%
  Set s = selectedContacts.keySet();
  Iterator i = s.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    Object id = i.next();
    Object st = selectedContacts.get(id);
    String email = st.toString();
    if(email.startsWith("P:")){
      email = email.substring(2);
    }
%>
  <script>
    recipientEmails[<%= count %>] = '<%= toJavaScript(email) %>';
    recipientIds[<%= count %>] = '<%=id%>';
  </script>
<%	
  }
%>
  </body>
<%
  //Cleanup the session objects used
    if (((String) request.getParameter("campaign")).equalsIgnoreCase("true")) {
      session.removeAttribute("selectedContacts");
      session.removeAttribute("finalContacts");
    }
  }
%>


