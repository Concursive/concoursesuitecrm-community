<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="ContactListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_contacts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/moveContact.js"></script>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">Search Results</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Contacts
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="accounts_details_header_include.jsp" %>
<dhv:container name="accounts" selected="contacts" param="<%= "orgId=" + OrgDetails.getOrgId() %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<%-- Begin the container contents --%>
<dhv:permission name="accounts-accounts-contacts-add"><a href="Contacts.do?command=Prepare&orgId=<%=request.getParameter("orgId")%>">Add a Contact</a></dhv:permission>
<center><%= ContactListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ContactListInfo"/>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
<tr>
  <th width="8">
    <strong>Action</strong>
  </th>
  <th>
    <strong><a href="Contacts.do?command=View&orgId=<%= OrgDetails.getOrgId() %>&column=c.namelast">Name</a></strong>
    <%= ContactListInfo.getSortIcon("c.namelast") %>
  </th>
  <th>
    <strong>Title</strong>
  </th>   
  <th>
    <strong>Phone</strong>
  </th>
  <th>
    <strong>Email</strong>
  </th>
</tr>
<%
	Iterator j = ContactList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      Contact thisContact = (Contact)j.next();
%>      
		<tr class="containerBody">
      <td valign="center" nowrap class="row<%= rowid %>">
        <%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('select<%= i %>', 'menuContact', '<%= OrgDetails.getOrgId() %>', '<%= thisContact.getId() %>', '<%= thisContact.getPrimaryContact() %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuContact');"><img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
      <td valign="center" width="50%" class="row<%= rowid %>">
        <a href="Contacts.do?command=Details&id=<%=thisContact.getId()%>"><%= toHtml(thisContact.getNameLastFirst()) %></a>
      </td>
      <td valign="center" width="50%" class="row<%= rowid %>">
        <%= toHtml(thisContact.getTitle()) %>
      </td>
      <td valign="center" class="row<%= rowid %>" nowrap>
        <%= toHtml(thisContact.getPhoneNumber("Business")) %>
      </td>
      <td valign="center" class="row<%= rowid %>" nowrap>
        <dhv:evaluate exp="<%= hasText(thisContact.getEmailAddress("Business")) %>">
          <a href="mailto:<%= toHtml(thisContact.getEmailAddress("Business")) %>"><%= toHtml(thisContact.getEmailAddress("Business")) %></a>
        </dhv:evaluate>
        &nbsp;
      </td>
		</tr>
<%}%>
<%} else {%>
		<tr class="containerBody">
      <td colspan="5">
        No contacts found.
      </td>
    </tr>
<%}%>
	</table>
	<br>
  <dhv:pagedListControl object="ContactListInfo"/>
</td>
</tr>
</table>

