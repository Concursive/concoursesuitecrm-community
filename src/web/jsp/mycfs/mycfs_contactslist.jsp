<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
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
      document.contactListView.lastName.value = label("label.contact.lastName", "Last Name");
			document.contactListView.firstName.value = label("label.contact.firstName", "First Name");
	<%
		}
	%>	
	}
	/**
    The Search fields' default values displayed to the user are translated. "Last Name" and "First Name"
    literals must be translated in both the xml and javascript dictionary files for the 'Search' feature to 
    work correctly.
  */
	function clearSearchFields(clear, field) {
		if (clear) {
			// Clear the search fields since clear button was clicked
			document.contactListView.lastName.value = label("label.contact.lastName", "Last Name");
			document.contactListView.firstName.value = label("label.contact.firstName", "First Name");
		} else {
			// One of the search fields recieved focus
			if (field.value == label("label.contact.lastName", "Last Name") || field.value == label("label.contact.firstName", "First Name")) {
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
				<strong><dhv:label name="button.search">Search</dhv:label></strong>
				<input type="text" name="lastName" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("lastName")) %>"> <b>, </b>
				<input type="text" name="firstName" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("firstName")) %>">
				<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
				<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="clearSearchFields(true, '')">
			</td>
		</tr>
	</table>
	&nbsp;<br>
  <dhv:evaluate if="<%= "true".equals(request.getParameter("addNewContact")) %>">
    <a href="javascript:window.location.href='Contacts.do?command=Prepare&popup=true&hiddensource=<%= request.getParameter("hiddensource") %>&actionStepWork=<%= request.getParameter("actionStepWork") %>&orgId=<%= request.getParameter("orgId")%><%= "true".equals(request.getParameter("actionplan"))?"&actionplan=true":"" %>';">Create New Contact</a>
  </dhv:evaluate>
  &nbsp;<br>
	<center><%= ContactListInfo.getAlphabeticalPageLinks("setFieldSubmit","contactListView") %></center>
  <input type="hidden" name="letter">
  <input type="hidden" name="tasks" value="<%= toHtmlValue((String) request.getAttribute("tasks")) %>">
  <input type="hidden" name="leads" value="<%= (String) request.getAttribute("leads") %>"/>
  <input type="hidden" name="type" value="<%= (request.getAttribute("type") != null? (String) request.getAttribute("type"):"") %>"/>
  <input type="hidden" name="hiddensource" value="<%= (request.getAttribute("hiddensource") != null? (String) request.getAttribute("hiddensource"):"") %>"/>
  <input type="hidden" name="ticketId" value="<%=request.getAttribute("ticketId")%>"/>
  <input type="hidden" name="departmentId" value="<%= request.getAttribute("departmentId") %>"/>
  <input type="hidden" name="actionItemId" value="<%= (request.getAttribute("actionItemId") != null? (String) request.getAttribute("actionItemId"):"") %>"/>
  <input type="hidden" name="recipient" value="<%= (request.getAttribute("recipient") != null? (String) request.getAttribute("recipient"):"") %>"/>
  <input type="hidden" name="allowDuplicateRecipient" value="<%= (request.getAttribute("allowDuplicateRecipient") != null? (String) request.getAttribute("allowDuplicateRecipient"):"") %>"/>
  <%@ include file="contactlist_include.jsp" %>

<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','contactListView');">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
  [<a href="javascript:SetChecked(1,'checkcontact','contactListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
  [<a href="javascript:SetChecked(0,'checkcontact','contactListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
  <br>
<%}else{%>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
<%}%>
  &nbsp;<br>
</form>
</body>
<%} else {%>
<%-- The final submit --%>
<% System.out.println("JSP:: the value of tasks is "+ (String) request.getAttribute("tasks")); %>
  <% if ("true".equals((String) request.getParameter("campaign"))) {%>
  <body onLoad="javascript:setParentListCampaign(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%=User.getBrowserId()%>');window.close()">
  <%} else if ("true".equals((String) request.getAttribute("recipient"))) {%>
  <body onLoad="javascript:setParentListRecipients(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= request.getAttribute("hiddensource") %>','<%= request.getParameter("allowDuplicateRecipient") %>','<%= (request.getAttribute("actionItemId") != null? (String)request.getAttribute("actionItemId"):"-1") %>');window.close()">
  <%} else if ("true".equals((String) request.getParameter("actionplan"))) {%>
  <body onLoad="javascript:setParentListActionPlan(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= request.getParameter("hiddensource") %>','<%= request.getParameter("actionPlanWork") %>','<%= request.getParameter("actionStepWork") %>','<%=User.getBrowserId()%>');window.close()">
  <%} else if ("true".equals((String) request.getAttribute("tasks"))) {%>
<% System.out.println("JSP:: just before opening the setParentListTask() the value of tasks is "+ (String) request.getAttribute("tasks")); %>
  <body onLoad="javascript:setParentListTask(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= request.getParameter("hiddensource") %>','<%=User.getBrowserId()%>');window.close();">
  <%} else if ("true".equals((String) request.getAttribute("leads"))) {%>
  <body onLoad="javascript:setParentListLead(recipientEmails,recipientIds,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= request.getParameter("source") %>','<%= request.getParameter("from") %>','<%= request.getParameter("last") %>','<%=User.getBrowserId()%>');window.close();">
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
    recipientEmails[<%= count %>] = "<%= toJavaScript(email) %>";
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

