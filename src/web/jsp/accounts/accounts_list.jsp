<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*" %>
<jsp:useBean id="OrgList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Initialize the drop-down menus --%>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="accounts_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<script language="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
</script>
<%-- Trails --%>
<table class="trails">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
Search Results
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:permission name="accounts-accounts-add"><a href="Accounts.do?command=Add">Add an Account</a></dhv:permission>
<dhv:permission name="accounts-accounts-add" none="true"><br></dhv:permission>
<center><%= SearchOrgListInfo.getAlphabeticalPageLinks() %></center>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="SearchOrgListInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="30%" nowrap>
      <strong><a href="Accounts.do?command=Search&column=o.name">Account Name</a></strong>
      <%= SearchOrgListInfo.getSortIcon("o.name") %>
    </th>
    <th width="20%" nowrap>
      <strong>Phone</strong>
    </th>
    <th width="20%" nowrap>
      <strong>Fax</strong>
    </th>
    <th width="30%" nowrap>
      <strong>Email</strong>
    </th>
  </tr>
<%
	Iterator j = OrgList.iterator();
	if ( j.hasNext() ) {
    int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
    i++;
    rowid = (rowid != 1 ? 1 : 2);
    Organization thisOrg = (Organization)j.next();
%>      
  <tr>
    <td width=8 valign="center" nowrap class="row<%= rowid %>">
        <% int status = -1;%>
        <dhv:permission name="accounts-accounts-edit"><% status = thisOrg.getEnabled() ? 1 : 0; %></dhv:permission>
      	<%-- Use the unique id for opening the menu, and toggling the graphics --%>
         <a href="javascript:displayMenu('menuAccount', '<%= thisOrg.getOrgId() %>', '<%= status %>');"
         onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>)"><img src="images/select.gif" name="select<%= i %>" align="absmiddle" border="0"></a>
      </td>
    
		<td width="30%" class="row<%= rowid %>">
      <a href="Accounts.do?command=Details&orgId=<%=thisOrg.getOrgId()%>"><%= toHtml(thisOrg.getName()) %></a>
		</td>
		<td width="20%" valign="center" class="row<%= rowid %>" nowrap>
    <dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() == null)%>">
      <%= toHtml(thisOrg.getPhoneNumber("Main")) %>
    </dhv:evaluate>    
    <dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() != null)%>">
      <%= toHtml(thisOrg.getPrimaryContact().getPhoneNumber("Business")) %>
    </dhv:evaluate>
    </td>
		<td width="20%" valign="center" class="row<%= rowid %>" nowrap>
      <%= toHtml(thisOrg.getPhoneNumber("Fax")) %>
    </td>
		<td width="30%" valign="center" class="row<%= rowid %>" nowrap>
    <dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() == null)%>">
		<% if ( (thisOrg.getEmailAddress("Primary")).length() > 0 ) { %>
			<a href="mailto:<%= toHtml(thisOrg.getEmailAddress("Primary")) %>"><%= toHtml(thisOrg.getEmailAddress("Primary")) %></a>
		<%} else {%>
			&nbsp;
		<%}%>
    </dhv:evaluate>
    <dhv:evaluate exp="<%=(thisOrg.getPrimaryContact() != null)%>">
		<% if ( (thisOrg.getPrimaryContact().getEmailAddress("Business")).length() > 0 ) { %>
			<a href="mailto:<%= toHtml(thisOrg.getPrimaryContact().getEmailAddress("Business")) %>"><%= toHtml(thisOrg.getPrimaryContact().getEmailAddress("Business")) %></a>
		<%} else {%>
			&nbsp;
		<%}%>    
    </dhv:evaluate>
		</td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      No  Accounts found for the specified search parameters. <a href="Accounts.do?command=SearchForm">Modify Search</a>. 
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="SearchOrgListInfo" tdClass="row1"/>

